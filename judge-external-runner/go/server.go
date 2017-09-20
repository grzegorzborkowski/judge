package main

import (
    "fmt"
    "net/http"
    "io"
    "os"
    "os/exec"
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
}

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

        compilationCode, runCode, testsPositive, testsTotal := processWithDocker(baseName + handler.Filename, handler.Filename)

        result := Result{
            CompilationCode: compilationCode,
            RunCode: runCode,
            TestsPositive:testsPositive,
            TestsTotal:testsTotal,
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

func processWithDocker(filenameWithDir string, filenameWithoutDir string) (int, int, int, int) {

    ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
    defer cancel()
    cli, err := client.NewEnvClient()
    if err != nil {
        panic(err)
    }

    var hostVolumeString = filenameWithDir
    var hostConfigBindString = hostVolumeString  + ":/WORKING_FOLDER/" + filenameWithoutDir

    var hostConfig = &container.HostConfig{
        Binds: []string{hostConfigBindString},
    }

    resp, err := cli.ContainerCreate(ctx, &container.Config{
        Image: "tusty53/ubuntu_c_runner:twelfth",
        Env: []string{"F00=" + filenameWithoutDir},
        Volumes: map[string]struct{}{
            hostVolumeString: struct{}{},
        },
    }, hostConfig, nil, "")
    if err != nil {
        panic(err)
    }

    if err := cli.ContainerStart(ctx, resp.ID, dockertypes.ContainerStartOptions{}); err != nil {
        panic(err)
    }

    fmt.Println(resp.ID)

    var exited = false

    for !exited {

        json, err := cli.ContainerInspect(ctx, resp.ID)
        if err != nil {
            panic(err)
        }

        exited = json.State.Running

        fmt.Println(json.State.Status)
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


    var testsPositive=0
    var testsTotal=0

    if(sErr!=""){
        matched, err := regexp.MatchString(`^[0-9]+ [0-9]+`, sErr[8:])
        if(matched){
            fmt.Sscanf(sErr[8:], "%d %d", &testsPositive, &testsTotal)
            fmt.Printf("Working")
            return 1,1,testsPositive,testsTotal
        }
        fmt.Println(matched, err)
        fmt.Println(sErr[8:])
        return 1,0,0,0
    }

    return 0,0,0,0

}


// Creates examine directory if it doesn't exist.
// If examine directory already exists, then comes an error.
func prepareDir() {
    cmdMkdir := exec.Command("mkdir", os.Args[1])
    errMkdir := cmdMkdir.Run()
    if errMkdir != nil {
        log.Println(errMkdir)
    }
}

func main() {
    prepareDir()
    log.Println("method:")
    go http.HandleFunc("/submission", upload)
    http.ListenAndServe(":8123", nil)
}