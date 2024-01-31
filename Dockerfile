FROM openjdk:17-jdk-slim AS build
COPY . .
RUN mvn clean package

FROM openjdk:17-jdk-slim
COPY --from=build /target/cookbook-backend-0.0.1-SNAPSHOT.jar cookbook.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]