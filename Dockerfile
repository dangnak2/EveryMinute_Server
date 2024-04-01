FROM amazoncorretto:17
EXPOSE 8080
ARG JAR_FILE=/build/libs/everyminute-0.0.1-SNAPSHOT.jar
ENV TZ=Asia/Seoul
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "app.jar"]
