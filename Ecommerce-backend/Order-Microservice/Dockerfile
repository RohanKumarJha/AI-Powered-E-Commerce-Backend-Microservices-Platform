FROM eclipse-temurin
WORKDIR /app
EXPOSE 8085
COPY target/Order-Microservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]