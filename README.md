# Summary
A simple project following the microservice architecture with the objective of learn more about asynchronous and non-blocking programming with Java using the Reactor library along with how to implement microservices in a reactive way using Spring WebFlux.

## Projects

- ### In this repository there is four folders, each one being Spring WebFlux API
  -  `webflux-demo`: A simple demo application built with Spring Webflux, the application is unit-tested with the reactor-test library
  -   `product-service`: Microservice responsible for the management of products, connected to a MongoDB database through spring-boot-starter-data-mongodb-reactive library.
  -   `user-service`: Microservice responsible for the management of users, connected to a PostgresSQL database through spring-boot-starter-data-r2dbc library.
  -   `order-service`: Microservice responsible for the placement and management of orders, integrated to both `user-service` and `product-service` by HTTP calls and connected to a SQL Server database through Spring Data JPA. 

## Run the projects

### Demo application (http://localhost:8079)

```bash
./gradlew bootRun
```

### Product service (http://localhost:8081)

```bash
./mvnw spring-boot:run
```

### User service (http://localhost:8082)

```bash
./mvnw spring-boot:run
```

### Order service (http://localhost:8080)

```bash
./mvnw spring-boot:run
```

