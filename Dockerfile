FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /target/cookbook-backend-0.0.1-SNAPSHOT.jar cookbook.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]
