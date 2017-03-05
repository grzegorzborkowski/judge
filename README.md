To run, set up PostgreSQL locally.

* Install PostgresSQL - for Ubuntu:
```
sudo apt-get update
udo apt-get install postgresql
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