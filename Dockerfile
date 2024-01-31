FROM eclipse-temurin:17-jre-jammy
COPY target/cookbook-backend-0.0.1-SNAPSHOT.jar cookbook.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]
