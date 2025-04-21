FROM gradle:8.7-jdk21-alpine AS build

# Defina o diretório de trabalho dentro do container
WORKDIR /ticketsapi

# Copie os arquivos do seu projeto para dentro do container
COPY . .

# Execute o build da aplicação usando Gradle
RUN gradle assemble --no-daemon

# Etapa 2: Usar a imagem base para o OpenJDK
FROM gcr.io/distroless/java21-debian12

# Defina o diretório de trabalho no container
WORKDIR /ticketsapi/api/

# Copie o JAR gerado na etapa anterior para dentro do novo container
COPY --from=build /ticketsapi/build/libs/tickets-api.jar ./api.jar

# Defina o volume para uploads
VOLUME /ticketsapi/uploads

# Defina o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "api.jar", "-Dfile.encoding=UTF-8", "-Dsun.jnu.encoding=UTF-8"]