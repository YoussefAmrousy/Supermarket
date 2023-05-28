
FROM openjdk:8
COPY target/supermarket.jar maven-wrapper.jar
ENTRYPOINT ["java","-jar","app.jar"]

# FROM openjdk:8-jdk-alpine
# VOLUME /tmp
# ARG JAR_FILE
# COPY ${JAR_FILE} maven-wrapper.jar
# ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/maven-wrapper.jar"]