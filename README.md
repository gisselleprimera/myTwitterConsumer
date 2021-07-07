# myTwitterConsumer

Microservice that consumes tweets and persists them in a database for management. The application contains a listener that consumes tweets from the Twitter API in real time and stores them in the database if they meet certain conditions.

## Build with

- [Java](https://go.java/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [H2](https://www.h2database.com/html/main.html) as memory database

## External libraries

- [twitter4j](http://twitter4j.org/en/)
- [Swagger](https://swagger.io/)

## Features

The microservice allows users:

1. find tweets that are stored in the database
2. validate a tweet using its identifier
3. find valid tweets from an account using his username
4. consult trending topics in real time

## Testing

The microservice provides a user interface built with swagger where services are documented and can be tested, it can be accessed from:

http://localhost:{port}/swagger-ui.html

where **port** is the port where the application is running, for example:

http://localhost:8081/swagger-ui.html

The application can also be tested from a platform such as postman or using the browser directly.

## Project Configuration

The tweets that meet the following conditions will be stored in a database:

- tweets written in the following languages : **Spanish, French or Italian**
- tweets whose users exceed **1500** followers

These parameters can be easily configurable in the **application.properties** file.

Currently values:

languages=es,fr,it

user.followers=1500

## IMPORTANT

Due to the JavaScript size limitation for numeric variables, a bug was detected when consulting the findTweets service using the swagger interface, the id of the tweets may not be accurate, for this reason it's recommended to consult the service from another platform such as postman or the browser, to obtain the exact identifiers (not rounded) if necessary.
