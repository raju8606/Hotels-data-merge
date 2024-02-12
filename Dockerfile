FROM maven:3.6.3-openjdk-8
COPY target/hotel-data-merge.jar app.jar
COPY target/classes/template.json template.json
ENTRYPOINT ["java", "-jar", "/app.jar"]