FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /songapp

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /songapp
COPY --from=builder /songapp/target/*.jar songapp.jar

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "songapp.jar"]

