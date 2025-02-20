#### Spring Boot Test Cheatsheet

A complete application and comprehensive set of unit and integration tests that can be used as a cheat sheet for testing spring boot Microservices.

For the full blog see this link
https://johndobie.com/blog/testing-spring-boot-microservices-cheat-sheet/

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

http://localhost:8080/cheatsheet/api/posts

```shell
curl -X POST http://localhost:8080/cheatsheet/api/echo -H "Content-Type: application/json" -d '{"content":"your-message"}'

curl -X GET http://localhost:8080/cheatsheet/api/posts -H "Content-Type: application/json"
```

