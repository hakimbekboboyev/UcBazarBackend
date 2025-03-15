# Use the official Gradle image with JDK 17
FROM gradle:jdk17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy Gradle files and source code to the container
COPY build.gradle settings.gradle /app/
COPY src /app/src

# Run Gradle build to compile and package the application
RUN gradle build --no-daemon

# Use a smaller JDK image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built application from the previous stage
COPY --from=build /app/build/libs/*.jar /app/app.jar



# Expose the port that the app runs on
EXPOSE 8080

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
