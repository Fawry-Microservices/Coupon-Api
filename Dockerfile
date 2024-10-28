
FROM openjdk:17-oracle
WORKDIR /app
COPY target/final_project.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "final_project.jar"]