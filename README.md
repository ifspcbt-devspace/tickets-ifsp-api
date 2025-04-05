
# 🎟️ Sistema de Ingressos - Backend API

Este projeto é o backend de um sistema de ingressos, desenvolvido em **Java** com **Spring Boot**, seguindo os princípios de **SOLID**, **Clean Architecture** e **Domain-Driven Design (DDD)**.  
Entre os módulos utilizados estão o **Spring Security** e o **Spring Data JPA**.

## 📦 Pré-requisitos

- Docker
- Docker Compose

## 🌐 Criando a rede Docker

Antes de subir os serviços, crie uma rede Docker chamada `ifspcbt` para permitir a comunicação entre os containers:

```bash
docker network create --driver bridge ifspcbt
```

## 🚀 Subindo a API

Com a rede criada, você pode subir a instância da API com o seguinte comando:
```bash
docker compose -f docker-compose.yml up -d
```

Esse comando irá:

- Subir o banco de dados PostgreSQL (`psql-db`)
- Subir a API (`ifspcbt-api`)
- Subir o serviço de atualização automática de containers (`watchtower`)

A API será exposta localmente na porta **8091**.

> Você pode acessar a API em: [http://localhost:8091](http://localhost:8091)

## 🔐 Configurações

As variáveis de ambiente já estão definidas no `docker-compose.yml` com valores padrões, como:

- `DATASOURCE_URL=jdbc:postgresql://localhost:5432/ifspcbt`
- `JWT_SECRET_KEY=secret`
- `MAIL_HOST=smtp.gmail.com`

Modifique conforme necessário antes de subir os containers.

## 📁 Estrutura de Diretórios

A estrutura do projeto segue os princípios da Clean Architecture e Domain-Driven Design, organizada da seguinte forma:

```
tickets-ifsp-api/
│
├── application/               # Casos de uso e orquestrações da aplicação
├── database/                  # Scripts SQL de inicialização (schema e inserts)
│   └── init.sql               # Exemplo de script inicial sugerido
├── domain/                    # Entidades e lógica de negócio
├── infrastructure/           # Adaptadores de entrada/saída (JPA, APIs externas, etc)
├── observability/            # Configurações para logs, métricas e tracing
│   ├── collector/
│   ├── grafana/
│   ├── prometheus/
│   └── tempo/
├── .github/                  # Ações do GitHub (CI/CD)
├── .gitlab-ci.yml            # Pipeline CI para GitLab
├── Dockerfile                # Dockerfile da aplicação
├── docker-compose.yml        # Compose com API, banco e serviços auxiliares
├── gradle/                   # Configurações do Gradle
├── gradlew                   # Wrapper Unix
├── gradlew.bat               # Wrapper Windows
├── LICENSE
├── README.md
└── settings.gradle
```

> 🧠 **Observação:**  
> O projeto é modular e segue uma separação clara entre as camadas, facilitando testes, manutenções e futuras extensões.

## 🛠️ Observabilidade

Se desejar integrar observabilidade (Grafana, Tempo, Prometheus etc.), utilize o `docker-compose.yml` localizado na pasta `observability`.

Antes, certifique-se de que a rede `ifspcbt` está marcada como `external: true` nesse `docker-compose`.

---

📫 Em caso de dúvidas ou sugestões, sinta-se à vontade para abrir uma issue ou contribuir com o projeto!