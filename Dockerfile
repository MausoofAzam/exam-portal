# Use OpenJDK 11 with Alpine for a lightweight image
FROM openjdk:11-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/EXAM_MANAGEMENT-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (8585) so it can be accessed
EXPOSE 8585

# Run the application
CMD ["java", "-jar", "app.jar"]
