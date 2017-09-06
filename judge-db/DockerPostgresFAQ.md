Execute under judge/ directory:
docker-compose build [only for the first execution and after changing Dockerfiles]
docker-compose up [every time]


Docker-Postgres FAQ

In case of:

ERROR: for db  Cannot start service db: driver failed programming external connectivity on endpoint judgepostgres_db_1 ([Id]): Error starting userland proxy: listen tcp 0.0.0.0:5432: bind: address already in use
ERROR: Encountered errors while bringing up the project.

Execute (to stop your local postgres running on port 5432):
sudo service postgresql stop

and run:
docker-compose up

In case of:

ERROR: for db  no such image: [ImageId]: No such image: [ImageId]
ERROR: Encountered errors while bringing up the project.

Execute (to clean cache):
docker-compose rm

and run:
docker-compose build
docker-compose up

