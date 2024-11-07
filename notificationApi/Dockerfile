# Use an official Maven image to build the app
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use an OpenJDK runtime image for the actual application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the packaged JAR file from the build stage
COPY --from=build /app/target/notification-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app will run on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]