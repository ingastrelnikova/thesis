# Stage 1: Build the application using Maven
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app

# Copy your Maven project files into the image
COPY src /app/src
COPY pom.xml /app/
COPY lib /app/lib

# Run Maven build, skipping unit tests to speed up the build
RUN mvn install:install-file -Dfile=lib/libarx-3.9.1.jar -DgroupId=org.deidentifier.arx -DartifactId=arx -Dversion=3.9.1 -Dpackaging=jar
RUN mvn clean install -DskipTests

# Stage 2: Setup the runtime container with OpenJDK
FROM openjdk:17-slim
WORKDIR /app

# Copy only the built JAR from the build stage to the runtime container
COPY --from=build /app/target/anonymization-service-1.0-SNAPSHOT.jar /app/anonymization-service.jar

# Expose the port the app runs on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/anonymization-service.jar"]
