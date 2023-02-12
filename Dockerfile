FROM openjdk:17-jdk-alpine

MAINTAINER Emin Yilmaz <eminyilmzz@gmail.com>
EXPOSE 8080
ADD target/loan-broker-app-0.1.0.jar loan-broker-app.jar

ENTRYPOINT ["java","-jar","loan-broker-app.jar"]