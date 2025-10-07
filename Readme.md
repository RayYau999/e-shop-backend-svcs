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

## Kafka commands
### Mainly base on document from https://kafka.apache.org/quickstart
1. ```KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)" ```
2. ```bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties```
3. ```bin/kafka-server-start.sh config/server.properties```
4. What I do is install docker image by ```docker pull apache/kafka:4.1.0```
5. Start the docker container by ```docker run -p 9092:9092 apache/kafka:4.1.0```

### Kafka - for my own project
1. I added a topic named payment-events by running the api builded in 

2. you can read the topic by running the following command
``bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \
--topic payment-events \
--from-beginning \
--property print.key=true \
--property print.value=true \
--property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
--property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer``
