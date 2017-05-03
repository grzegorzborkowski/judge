package main

import (
    "fmt"
    "net/http"
    "io"
    "os"
    "os/exec"
    "log"
    "bytes"
)

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

           w.Header().Set("compilationCode", compilationCode)
           w.Header().Set("runCode", runCode)
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
func process(filename string) (string, string) {
    cmdRun := exec.Command(getDockerExecutinoCommanand(filename))
        var out bytes.Buffer
        cmdRun.Stdout = &out

        if errRun := cmdRun.Start(); errRun != nil {
            log.Fatal(errRun)
        }

    //cmdClean := exec.Command("rm", filename, execFilename)
    //errClean := cmdClean.Start()
    //if errClean != nil {
    //    log.Println(errClean)
    //}
    return "2", "8"
}

// Creates examine directory if it doesn't exist.
// If examine directory already exists, then comes an error.

func getDockerExecutinoCommanand(filename string) string{
    dir, err := os.Getwd()
    if err != nil {
        fmt.Println(err)
        os.Exit(1)
    }
    // -----------------------------------------------
    var prefix = "docker run -e "
    var f00 = "F00='" + filename + "' "
    var working_directory = "-v " + dir + "/" + filename
    var working_folder = ":/WORKING_FOLDER/" + filename + " "
    var container_name = "tusty53/c_runner"
    result := prefix + f00 + working_directory + working_folder + container_name
    return result
}

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
