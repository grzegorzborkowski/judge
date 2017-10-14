echo "Pulling external dependencies for go"
echo "Pulling github.com/docker/docker/client"
go get github.com/docker/docker/client
echo "Downloaded github.com/docker/docker/client. Pulling github.com/docker/docker/api/types"
go get github.com/docker/docker/api/types
echo "Downloaded github.com/docker/docker/api/types. Pulling github.com/docker/docker/api/types/container"
go get github.com/docker/docker/api/types/container
echo "Downloaded github.com/docker/docker/api/types/container. Pulling golang.org/x/net/context"
go get golang.org/x/net/context
echo "Downloaded golang.org/x/net/context"