FROM openjdk:11-jdk

# Set the working directory
WORKDIR /app

COPY target-images /pdrpic

# Copy the compiled jar file and dependencies
COPY build/libs/*.jar /app.jar

# Copy the HTML, CSS, and image files
COPY src/main/resources/templates/ /app/templates/
COPY src/main/resources/static/ /app/static/

# Run the application
ENTRYPOINT ["java","-jar","/app.jar"]
