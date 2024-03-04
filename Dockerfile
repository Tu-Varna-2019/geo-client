# Use the official OpenJDK image as the base image
FROM bellsoft/liberica-openjdk-alpine:18

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper files (gradlew and gradle) to the container
COPY gradlew .
COPY gradle gradle

# Copy the build.gradle.kts and settings.gradle.kts files
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy the entire project
COPY . .

# Run the Gradle build to download dependencies and build the project
RUN ./gradlew build -x test

# Expose the port your app runs on
EXPOSE 8080

# Launch your app
CMD ["./gradlew", "bootRun"]
