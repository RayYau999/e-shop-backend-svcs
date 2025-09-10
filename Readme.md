# Read this before jump into the project code
1. There is a data.sql file in the eshop-login-svc module which will be executed when the mysql container is started. 
2. run the following command to start a mysql container with the database and table created.
```
$ docker run --name mysql \
   -e MYSQL_ROOT_PASSWORD=root \
   -e MYSQL_DATABASE=demo-dev \
   -e MYSQL_USER=devuser \
   -e MYSQL_PASSWORD=devpassword \
   -p 3306:3306 \
   -d mysql:latest
```
2. It will create a database named demo-dev and a table named users with some sample data.


## Command you may need to run
### To access mysql db in docker container
1. please run ```mysql -h localhost -P 3306 -u devuser -p demo-dev``` in exec mode
2. And the password of the DB is "devpassword"


### To publish to maven local
```./gradlew publishToMavenLocal```