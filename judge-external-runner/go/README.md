***External runner***

To run the external runner (GO web server) required for compiling and running code in safe environment:

* Get required dependencies:

```
    bash external_dependencies.sh
```

* run external runner
    (golang 1.6 or higher is required)

    `go run server.go "/tmp/any_dir/`
    
    The path to provided dir is used for submission processing.

    server works on port :8123/submission
   