FROM ubuntu:latest

LABEL authors="Amit"
# Use Java 17 runtime
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the fat JAR into container
COPY build/libs/ktor-app.jar app.jar

# Expose port your Ktor uses
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "app.jar"]
ENTRYPOINT ["top", "-b"]