package main

import (
    "fmt"
    "net/http"
    "io"
    "os"
    "os/exec"
    "log"
    //"bytes"
    "io/ioutil"
    //"strings"
    "encoding/json"

    "github.com/docker/docker/client"
    dockertypes "github.com/docker/docker/api/types"
    "github.com/docker/docker/api/types/container"
    "golang.org/x/net/context"
    "path"
    "bytes"
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
        f, err := os.OpenFile("./examine/"+handler.Filename, os.O_WRONLY|os.O_CREATE, 0666)
        if err != nil {
            fmt.Println(err)
            return
        }
        defer f.Close()
        io.Copy(f, file)

        //compilationCode, runCode := process("examine/" + handler.Filename)
        compilationCode, runCode, testsPositive, testsTotal := processWithDocker("examine/" + handler.Filename, handler.Filename)

        result := Result{
            CompilationCode: compilationCode,
            RunCode: runCode,
            TestsPositive:testsPositive,
            TestsTotal:testsTotal,
        }
        resultMarshaled, _ := json.Marshal(result)
        w.Write(resultMarshaled)

        s := string(resultMarshaled)
        log.Println(s)
    } else {
        w.Write([]byte("GO server is active. Use POST to submit your solution."))
    }
}

// @filename contains "examine" directory
// there is assumption that docker is installed where server.go is running
// and the container is already pulled
// TODO: handle situation when container is not pulled
// TODO: somehow capture if compilation wasn't successful and
// TODO: distinguish it from possible execution / time limit / memory limit error
// http://stackoverflow.com/questions/18986943/in-golang-how-can-i-write-the-stdout-of-an-exec-cmd-to-a-file
func process(filename string) (int, int) {
    cmdRun := exec.Command("docker", getDockerExecutionCommanand(filename)...)

    // Temporary solution! Result file is being overwritten every time.
    outfile, errCreate := os.Create("./out.txt")
    if errCreate != nil {
        panic(errCreate)
    }
    defer outfile.Close()

    //var out bytes.Buffer
    //var stderr bytes.Buffer
    //cmdRun.Stdout = &out
    //cmdRun.Stderr = &stderr
    cmdRun.Stdout = outfile
    cmdRun.Stderr = outfile

    errStart := cmdRun.Start()
    if errStart != nil {
        panic(errStart)
    }
    errStart = cmdRun.Wait()
    //log.Printf("Command finished with error: %v", err)
    //log.Printf("Full error message: %s", stderr.String())
    //log.Printf("Command output: %s", out.String())

    fileContent, errRead := ioutil.ReadFile("./out.txt")
    if errRead != nil {
        panic(errRead)
    }
    log.Printf("Result file created with the following content:\n%s", string(fileContent))

    return 2, 8
}

func processWithDocker(filenameWithDir string, filenameWithoutDir string) (int, int, int, int) {
    ctx := context.Background()
    cli, err := client.NewEnvClient()
    if err != nil {
        panic(err)
    }


    ex, err := os.Executable()
    if err != nil {
        panic(err)
    }
    exPath := path.Dir(ex)

    log.Printf("Ex path:\n");
    log.Printf(exPath);
    log.Printf("With:\n");
    log.Printf(filenameWithDir);
    log.Printf("Without:\n");
    log.Printf(filenameWithoutDir);

    var hostConfig = &container.HostConfig{
        Binds: []string{exPath + filenameWithDir + ":/WORKING_FOLDER/" + filenameWithoutDir},
    }

    resp, err := cli.ContainerCreate(ctx, &container.Config{
        Image: "tusty53/ubuntu_c_runner:tag",
        //Image: "hello-world",
        Env: []string{"F00=" + filenameWithoutDir},
        Volumes: map[string]struct{}{
            exPath + filenameWithDir: struct{}{},
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

    /*if json.State.Running{
        _, errC := cli.ContainerWait(ctx, resp.ID, "")
        if err := <-errC; err != nil {
            panic(err)
        }
}*/

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

    var testsPositive=0
    var testsTotal=0

    if(sErr!=""){
        return 0,0,0,0
    }

    if(sOut!=""){
        fmt.Sscanf(sOut, "%d %d", &testsPositive, &testsTotal)
        return 1,1,testsPositive,testsTotal
    }
    return 1,0,0,0

}


func getDockerExecutionCommanand(filename string) []string {
    // get current directory
    dir, err := os.Getwd()
    if err != nil {
        fmt.Println(err)
        os.Exit(1)
    }

    var f00 = "F00='" + filename + "'"
    var working_directory = dir + "/" + filename
    var working_folder = ":/WORKING_FOLDER/" + filename
    var container_name = "tusty53/c_runner"

    var command = []string{"run", "-e", f00,
        "-v", working_directory + working_folder, container_name}

    return command
}

// Creates examine directory if it doesn't exist.
// If examine directory already exists, then comes an error.
func prepareDir() {
    cmdMkdir := exec.Command("mkdir", "examine")
    errMkdir := cmdMkdir.Run()
    if errMkdir != nil {
        log.Println(errMkdir)
    }
}

func main() {
    prepareDir()
    go http.HandleFunc("/submission", upload)
    http.ListenAndServe(":8123", nil)
}