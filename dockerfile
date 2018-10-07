FROM openjdk:8-jre-alpine

RUN apk update
RUN apk add --no-cache curl bash

EXPOSE 8080

COPY target/localstack-spring-s3-poc.jar /app.jar
RUN bash -c 'touch /app.jar'

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar | tee app.log