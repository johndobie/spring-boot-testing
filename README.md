### Spring Boot Test Cheatsheet

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

