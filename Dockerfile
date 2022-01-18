FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/customer-service-1.0.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
