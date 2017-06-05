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

        compilationCode, runCode := process("examine/" + handler.Filename)

        result := Result{
            CompilationCode: compilationCode,
            RunCode: runCode,
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