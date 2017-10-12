To run, set up PostgresSQL locally.

**First usage only:**
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

**Every usage:**

***Database:***
* run PostgresSQL on your machine

`sudo -u postgres psql`

***External runner***

External runner is designed as a GO web server now.
To run the project with external runner, you have to:
* run external runner
    (golang 1.3 or higher is needed)

    `go run server.go`

    server works on
    `localhost:8123/submission`

***Spring application***
* IntelliJ users: if IntelliJ didn't do it by itself, mark Beans.xml as a valid xml source for judge module
* run Spring application



****FLOW SUMMARY****

Backend supports the following requests:

**GET**

* /student/getAll
* /submission/getAll
* /student/getById

param: id = [student id]
* /submission/getAllForUser

param: id = [student id]


**POST**

* /judge/submit

{   "code":"[code as string]",
	"problemID":"[problem id]"
}

* /student/add

{   "role":"[role]",
    "email":"[email]",
    "password":"[password]",
    "id":"[id]",
    "username":"[username]"
}

If Student A with id=X exists in the database and we add Student B with id=X, then Student B overwrites Student A!
It'll be changed.