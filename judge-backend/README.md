To run without docker, set up PostgresSQL locally.

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

***Spring application***
* run Spring application using IDE or
* Type in :
```
mvn spring-boot:run
```

