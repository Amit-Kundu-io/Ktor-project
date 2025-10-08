# =========================
# Stage 1: Build the fat JAR
# =========================
FROM eclipse-temurin:17-jdk-jammy AS build

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and configuration files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle.properties .

# Copy source code
COPY src ./src

# Make Gradle wrapper executable
RUN chmod +x ./gradlew

# Build the fat JAR using shadowJar (skip tests)
RUN ./gradlew shadowJar -x test --no-daemon

# =========================
# Stage 2: Runtime
# =========================
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the fat JAR from the build stage
COPY --from=build /app/build/libs/ktor-notes-backend-all.jar .

# Expose the port Ktor will run on
EXPOSE 8080

# Run the Ktor server
CMD ["java", "-jar", "ktor-notes-backend-all.jar"]
