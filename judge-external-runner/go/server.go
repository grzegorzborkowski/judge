package main

import (
    "fmt"
    "net/http"
    "time"
    "io"
    "os"
    "os/exec"
    "log"
    "bytes"
)

func upload(w http.ResponseWriter, r *http.Request) {
       log.Println("method:", r.Method)
       if r.Method == "GET" {
           w.Write([]byte("GO server is active. Use POST to submit your solution."))
       } else {
           log.Println("Processing new SUBMISSION.")
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

           compilationCode, runCode := process(w, r, "examine/" + handler.Filename)

           w.Header().Set("compilationCode", compilationCode)
           w.Header().Set("runCode", runCode)
       }
}

// @filename contains "examine" directory
func process(w http.ResponseWriter, r *http.Request, filename string) (string, string) {
    compilationCode := "-1"
    runCode := "-1"

    execFilename := filename  + ".out"
    cmdCompile := exec.Command("gcc", "-o" + execFilename, filename)
    errCompile := cmdCompile.Run()

    if errCompile != nil {
        log.Println("COMPILATION FAILED")
        log.Println(errCompile)
    } else {
        compilationCode = "0"
        cmdRun := exec.Command("./" + execFilename)
        var out bytes.Buffer
        cmdRun.Stdout = &out

        if errRun := cmdRun.Start(); errRun != nil {
            log.Fatal(errRun)
        }
        timer := time.AfterFunc(10 * time.Second, func() {
            cmdRun.Process.Kill()
        })
        errTimeout := cmdRun.Wait()
        timer.Stop()
        if errTimeout == nil {
            log.Println(execFilename + " - SUCCEED")
            log.Printf(out.String())
            runCode = "0"
        } else {
            log.Println(execFilename + " - TIMEOUT")
            runCode = "2"
        }
    }
    cmdClean := exec.Command("rm", filename, execFilename)
    errClean := cmdClean.Start()
    if errClean != nil {
        log.Println(errClean)
    }
    return compilationCode, runCode
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
