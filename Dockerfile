# Stage 1: Build and run inside Docker
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy project files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle.properties .
COPY src ./src

# Make Gradle wrapper executable
RUN chmod +x ./gradlew

# Pre-download dependencies and build
RUN ./gradlew build -x test --no-daemon

# Expose the port
EXPOSE 8080

# Run the app using Gradle
CMD ["./gradlew", "run"]
