About
===

---

This is Spring Boot project is developed for DefineX Java Spring Boot Practicum by Patika.dev.  

This is a backend service for loan application.
It is designed to expose RESTful API sticking to the standards as much as possible.

**Tech Stack:**
1. Java 17
2. Spring Boot 2.7.8
3. Spring Data JPA
4. Postgres
5. Maven
6. RabbitMQ
7. Swagger

**Tech preferences**

**RabbitMQ:** is a commonly used AMQP message broker. I preferred it
due to its simplicity and compatibility on both small and bigger scales. It is used to simulate an SMS Service that is supposedly
called on loan approval.

**Postgres:** Postgres is a powerful, open source object-relational database system.
Being object-relational served better for my purposes and dependencies over other databases.

**Maven:** Maven is a build automation, project management and comprehension tool. Maven is preferred
over other technologies for its enhanced dependency and plugin management.

**Spring Data JPA:** This Spring technology is used with hibernate ORM to build a simple persistence layer
quickly.

**Swagger:** Swagger is used for API documentation following OpenAPI 3 specifications.

Architecture of the service is also done in n-layered design. This helped to achieve the separation of components
such as service, repository, controller, model.

How To Run and Requirements
===

---

**Required drivers and services:**
1. Postgres - Developed in v15, is not tested for other versions.
2. RabbitMQ 3.0.0 or above.
3. Apache Maven 3.6.0 or above.

**Run:**

1. Configure data resources in application.properties
2. Configure RabbitMQ in application.properties unless default settings won't be used.
3. Navigate to the project files, and run `mvn clean install`
4. Then run `mvn spring-boot:run`

&nbsp;&nbsp;&nbsp;&nbsp;The server will run on localhost:8080 by default.  
&nbsp;&nbsp;&nbsp;&nbsp;If you don't want to run with maven cmd commands, open the project as a maven project with your IDE.  
&nbsp;&nbsp;&nbsp;&nbsp;Don't forget to run it with `maven clean install`.

**Run in a container with Docker:**

1. Build container via docker-compose with `docker-compose build`
2. Run the container with `docker-compose up`

&nbsp;&nbsp;&nbsp;&nbsp;Container exposes 8080 by default.

Diagrams
===

---
**UML Diagram of components of the program.**


![application_uml](/images/loan-broker-app.png)

Database Schema
---

---

![database_schema](/images/database_schema.png)

Other
===

---

If you want to reach out to me for any reason, you can contact me on [Github](https://github.com/eminyilmazz) as well as [Linked.in](https://www.linkedin.com/in/eminyilmz/).
