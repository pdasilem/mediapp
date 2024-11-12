FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /disapp

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /disapp
COPY --from=builder /disapp/target/*.jar disapp.jar

EXPOSE 8080

CMD ["java", "-jar", "disapp.jar"]

