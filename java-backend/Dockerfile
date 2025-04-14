FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY gradlew .
COPY gradlew.bat .
COPY gradle ./gradle
COPY build.gradle .
COPY src ./src
COPY libs ./libs
COPY src/main/resources/application.properties ./src/main/resources/
RUN chmod +x gradlew
EXPOSE 8080
CMD ["./gradlew", "bootRun"]
