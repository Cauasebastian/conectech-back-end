# Use the official Maven image to create a build environment.
FROM maven:3.8.4-openjdk-17 AS builder

# Copy the POM file to download dependencies.
COPY pom.xml /usr/src/app/
WORKDIR /usr/src/app

# Download dependencies.
RUN mvn dependency:go-offline -B

# Copy the rest of the source code and build the application.
COPY . /usr/src/app/
RUN mvn package -DskipTests

# Use OpenJDK as a runtime image.
FROM openjdk:17-alpine

# Set the working directory inside the container.
WORKDIR /usr/app

# Copy the JAR file built in the previous stage.
COPY --from=builder /usr/src/app/target/conectech-0.0.1-SNAPSHOT.jar /usr/app/conectech.jar

# Expose the port the application runs on.
EXPOSE 8080

# Define environment variables for MongoDB connection.
ENV SPRING_DATA_MONGODB_URI=mongodb://mongo:27017/conectech_db

# Define the command to run the application.
CMD ["java", "-jar", "conectech.jar"]
