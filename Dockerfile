FROM openjdk:21-slim-buster

WORKDIR /ticketsifsp/api/
ARG JAR_FILE=tickets-ifsp-api.jar

COPY ./build/libs/${JAR_FILE} ./api.jar

VOLUME /ticketsifsp/uploads

ENTRYPOINT ["java","-jar","api.jar", "-Dfile.encoding=UTF-8", "-Dsun.jnu.encoding=UTF-8"]