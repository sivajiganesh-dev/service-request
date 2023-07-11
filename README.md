# service-request

## Build and Run
Service has integrated with flyway for  database migration, on execution it keeps the database updated.

### Run Postgres Via Docker

`docker run --name postgres-latest -e POSTGRES_PASSWORD=password -e POSTGRES_USERNAME=postgres -e POSTGRES_DB=service_request -p 5432:5432 postgres`

### Build Boot Jar

`./gradlew clean bootJar`

### Run Service

`./gradlew bootRun`
