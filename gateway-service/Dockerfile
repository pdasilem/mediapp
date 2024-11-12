FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /gateapp

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /gateapp
COPY --from=builder /gateapp/target/*.jar gateapp.jar

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "gateapp.jar"]

