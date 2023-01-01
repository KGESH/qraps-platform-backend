FROM openjdk:11-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the compiled jar file and dependencies
COPY build/libs/*.jar /app.jar

# Copy the HTML, CSS, and image files
COPY src/main/resources/templates/ /app/templates/
COPY src/main/resources/static/web/ /app/static/web/

# Run the application
ENTRYPOINT ["java","-jar","/app.jar"]
