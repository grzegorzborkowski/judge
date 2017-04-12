To run, set up PostgreSQL locally.

* Install PostgresSQL - for Ubuntu:
```
sudo apt-get update
sudo apt-get install postgresql
```
* Alter initial user:
```
sudo -u postgres psql
postgres=# ALTER USER postgres PASSWORD 'init';
\q
```
* Create database:
```
createdb -h localhost -p 5432 -U postgres springbootdb
```

**External runner**

External runner is designed as a GO web server now.
To run the project with external runner, you have to:
* run external runner
    (golang 1.3 or higher is needed)

    `go run server.go`

    server works on
    `localhost:8123/submission`
* run Spring application