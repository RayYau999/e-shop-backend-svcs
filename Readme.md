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
#### Kafka - start a kafka server in local (need java 17+)
1. ```KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)" ```
2. ```bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties```
3. ```bin/kafka-server-start.sh config/server.properties```, 
after you run step 1 and 2 once, you don't need to run them again. or else you might need to delete the kafka log folder. ```rm -rf /tmp/kraft-combined-logs/*```

#### Kafka - start a kafka server in docker (need java 17+)
1. What I do is install docker image by ```docker pull apache/kafka:4.1.0```
2. Start the docker container by ```docker run -p 9092:9092 apache/kafka:4.1.0```
#### Remark:
1. You need to run the kafka cli command in the kafka directory if you run in local.
2. This is for myself, where I put my kafka in 
```cd Developer/kafka_2.13-4.1.0```

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


### Ngrok to expose pymt service to paypal webhook
1. Download ngrok from https://ngrok.com/download
2. run ```./ngrok http 8081``` to expose the local pymt-service port 8081 to internet

### Build the image
1. cd to the root directory (e.g. e-shop-backend-svcs) and run 
```DOCKER_BUILDKIT=1 docker build --progress=plain -f eshop-api-gateway/Dockerfile -t eshop-api-gateway:latest .```
2. It will use the eshop-api-gateway/Dockerfile to build the image from the root directory.

### Build the container with docker compose
1. docker compose (or docker-compose) reads a docker-compose.yml file that defines multiple services, 
networks and volumes, then builds (if needed) and runs the containers together.

### Note for dockerfile and docker compose
1. Dockerfile is used to build a single image for a service.
2. Docker Compose is used to define and run multi-container Docker applications.
3. In this project, each microservice has its own Dockerfile to build its image.