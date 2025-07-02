# Use a base image with JDK
FROM openjdk:22-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Gradle build output (JAR file) to the container
COPY build/libs/AuthService-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the service runs on
EXPOSE 9001

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]