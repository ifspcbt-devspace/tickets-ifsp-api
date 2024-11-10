FROM openjdk:21-slim-buster

WORKDIR /ticketsapi/api/
ARG JAR_FILE=tickets-api.jar

COPY ./build/libs/${JAR_FILE} ./api.jar

VOLUME /ticketsapi/uploads

ENTRYPOINT ["java","-jar","api.jar", "-Dfile.encoding=UTF-8", "-Dsun.jnu.encoding=UTF-8"]