FROM golang:1.8

WORKDIR /go/src/app
COPY external_dependencies.sh /go/src/app

RUN bash external_dependencies.sh

COPY . /go/src/app

CMD ["go", "run", "server.go", "/tmp/examine"]
