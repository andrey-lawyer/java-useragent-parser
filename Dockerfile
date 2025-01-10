FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /tmp

# Copy the Gradle build file
COPY build/libs/*.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]

