FROM openjdk:17-jdk-alpine
LABEL name="monkeyapi_springboot"
COPY target/monkeyapi-0.0.1-SNAPSHOT.jar monkeyapi-0.0.1-SNAPSHOT.jar 
ENTRYPOINT ["java","-jar", "/monkeyapi-0.0.1-SNAPSHOT.jar"]
