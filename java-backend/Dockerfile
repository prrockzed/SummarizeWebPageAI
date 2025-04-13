FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradlew.bat .
COPY gradle ./gradle
COPY build.gradle .

# Copy Java source code
COPY src ./src

# Copy libs (where scala-lib.jar is located)
COPY libs ./libs

# Make Gradle wrapper executable
RUN chmod +x gradlew

# Expose the Spring Boot port
EXPOSE 8080

# Build and run the Spring Boot application
CMD ["./gradlew", "bootRun"]
