package main

import (
    "fmt"
    "net/http"
    "io"
    "os"
    "log"
    "encoding/json"

    "github.com/docker/docker/client"
    dockertypes "github.com/docker/docker/api/types"
    "github.com/docker/docker/api/types/container"
    "golang.org/x/net/context"
    "time"
    "bytes"
    "regexp"
)

type Result struct {
    CompilationCode int
    RunCode int
    TestsPositive int
    TestsTotal int
    TimeTaken float64
    ErrorCode string
}

const COMPILATION_SUCCESS_CODE = 0
const COMPILATION_FAILURE_CODE = 1
const RUN_SUCCESS_CODE = 0
const RUN_FAILURE_CODE = 1
const TIMEOUT_CODE = 2
const PROCESSING_ERROR_CODE = -1
const TIMEOUT_VALUE_SECONDS = 10
const NO_TESTS = 0
const ZERO_TIME = 0.0

func upload(w http.ResponseWriter, r *http.Request) {
    log.Println("method:", r.Method)
    if r.Method == "POST" {
        log.Println("Processing new SUBMISSION.")
        // https://github.com/astaxie/build-web-application-with-golang/blob/master/de/04.5.md
        r.ParseMultipartForm(32 << 20)
        file, handler, err := r.FormFile("file")
        if err != nil {
            fmt.Println(err)
            return
        }

        defer file.Close()
        baseName:= os.Args[1]
        f, err := os.OpenFile(baseName+handler.Filename, os.O_WRONLY|os.O_CREATE, 777)
        if err != nil {
            fmt.Println(err)
            return
        }
        defer f.Close()
        io.Copy(f, file)
        if err != nil {
            fmt.Println(err)
            return
        }

        compilationCode, runCode, testsPositive, testsTotal, timeTaken, errorCode := processWithDocker(baseName + handler.Filename, handler.Filename)

        result := Result{
            CompilationCode: compilationCode,
            RunCode: runCode,
            TestsPositive: testsPositive,
            TestsTotal: testsTotal,
            TimeTaken: timeTaken,
            ErrorCode: errorCode,
        }
        resultMarshaled, _ := json.Marshal(result)
        w.Write(resultMarshaled)
    } else {
        w.Write([]byte("GO server is active. Use POST to submit your solution."))
    }
}

// there is assumption that docker is installed where server.go is running
// and the container is already pulled
// TODO: handle situation when container is not pulled
// TODO: somehow capture if compilation wasn't successful and
// TODO: distinguish it from possible execution / time limit / memory limit error
// http://stackoverflow.com/questions/18986943/in-golang-how-can-i-write-the-stdout-of-an-exec-cmd-to-a-file

func processWithDocker(filenameWithDir string, filenameWithoutDir string) (int, int, int, int, float64, string) {

    ctx, cancel := context.WithTimeout(context.Background(), 15*time.Second)
    defer cancel()
    cli, err := client.NewEnvClient()
    if err != nil {
        panic(err)
    }

    var hostVolumeString = filenameWithDir
    var hostConfigBindString = hostVolumeString  + ":/WORKING_FOLDER/" + filenameWithoutDir

    hostResources := container.Resources{
        PidsLimit: 10,
        MemoryReservation: 100 * 1024 * 1024,
    }

    var hostConfig = &container.HostConfig{
        Binds: []string{hostConfigBindString},
        AutoRemove: false,
        Resources: hostResources,
    }

    var timeoutPtr *int
    timeoutSec := 1
    timeoutPtr = &timeoutSec

    refString := "tusty53/ubuntu_c_runner:cpp"

    var pullOpts = dockertypes.ImagePullOptions{}

    _, err = cli.ImagePull(ctx, refString, pullOpts)

    resp, err := cli.ContainerCreate(ctx, &container.Config{
        Image: refString,
        NetworkDisabled: true,
        Tty: true,
        StopTimeout: timeoutPtr,
        Env: []string{"F00=" + filenameWithoutDir},
        Volumes: map[string]struct{}{
            hostVolumeString: {},
        },
    }, hostConfig, nil, "")
    if err != nil {
        panic(err)
    }

    if err := cli.ContainerStart(ctx, resp.ID, dockertypes.ContainerStartOptions{}); err != nil {
        panic(err)
    }

    fmt.Println(resp.ID)

    exited := false

    for !exited {

        json, err := cli.ContainerInspect(ctx, resp.ID)
        if err != nil {
            switch err {
            case context.DeadlineExceeded:
                fmt.Println(err.Error())
                return COMPILATION_SUCCESS_CODE, TIMEOUT_CODE, NO_TESTS, NO_TESTS, ZERO_TIME, err.Error()
            default:
                panic(err)
            }
        }

        exited = !json.State.Running

        if json.State.Status == "exited" {
            exited = true
        }
    }


    normalOut, err := cli.ContainerLogs(ctx, resp.ID, dockertypes.ContainerLogsOptions{ShowStdout: true, ShowStderr: false})
    if err != nil {
        panic(err)
    }

    errorOut, err := cli.ContainerLogs(ctx, resp.ID, dockertypes.ContainerLogsOptions{ShowStdout: false, ShowStderr: true})
    if err != nil {
        panic(err)
    }

    buf := new(bytes.Buffer)
    buf.ReadFrom(normalOut)
    sOut := buf.String()

    buf2 := new(bytes.Buffer)
    buf2.ReadFrom(errorOut)
    sErr := buf2.String()

    log.Printf("start\n")
    log.Printf(sOut)
    log.Printf("end\n")

    log.Printf("start error\n")
    log.Printf(sErr)
    log.Printf("end error\n")

    cli.ContainerRemove(ctx, resp.ID, dockertypes.ContainerRemoveOptions{RemoveVolumes: true, RemoveLinks: true})


    var testsPositive=0
    var testsTotal=0
    var timeTaken=0.0

    if sErr!="" {
        return COMPILATION_SUCCESS_CODE, RUN_FAILURE_CODE, NO_TESTS, NO_TESTS, ZERO_TIME, sErr
    }
    if sOut!="" {
        matched, err := regexp.MatchString(`^[0-9]+ [0-9]+`, sOut)
        if matched {
            fmt.Sscanf(sOut, "%d %d %f", &testsPositive, &testsTotal, &timeTaken)
            fmt.Printf("Working")
            return COMPILATION_SUCCESS_CODE, RUN_SUCCESS_CODE, testsPositive, testsTotal, timeTaken, ""
        }
        fmt.Println(matched, err)
        return COMPILATION_FAILURE_CODE, RUN_FAILURE_CODE, NO_TESTS, NO_TESTS, ZERO_TIME, sOut
    }

    return COMPILATION_FAILURE_CODE,RUN_FAILURE_CODE, NO_TESTS, NO_TESTS, ZERO_TIME, ""
}


// Checks if a directory with a given name already exists and can be used for submission processing.
// If there is no such a directory, it will be created.
func prepareDir() {
    var path = os.Args[1]
    mode := int(0777)
    _, err := os.Stat(path); os.IsNotExist(err)

    if err != nil {
        log.Println(err)
        log.Println("Directory with a given name will be created.")
        os.Mkdir(path, os.FileMode(mode))
    } else {
        log.Println("Directory with a given name already exists.")
    }
}

func main() {
    prepareDir()
    log.Println("method:")
    go http.HandleFunc("/submission", upload)
    log.Fatal(http.ListenAndServe(":8123", nil))
}
