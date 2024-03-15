FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the Spring Boot application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/tarefas-0.0.1-SNAPSHOT.jar /app/tarefas.jar
EXPOSE 8080
CMD ["java", "-jar", "tarefas.jar"]
