#### Spring Boot Test Cheatsheet

https://johndobie.com/blog/testing-spring-boot-microservices-cheat-sheet/

A complete application and comprehensive set of unit and integration tests that can be used as a cheat sheet for testing spring boot Microservices.

### Building The Code.
```shell
mvn clean install
```
   
####
To run the services locally

```shell
 mvn spring-boot:run -Dspring-boot.run.profiles=test
```
http://localhost:8080/actuator/info

http://localhost:8080/actuator/health

```shell
curl -X POST http://localhost:8080/message -H "Content-Type: application/json" -d '{"message":"your-message"}'
```

