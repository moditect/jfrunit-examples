# Gradle + Spring Boot + JOOQ Example

This example uses [Gradle JVM toolchains](https://docs.gradle.org/current/userguide/toolchains.html) and Java 17

To run this example:

```shell
cd examples/spring-jooq-gradle
docker-compose up -d # Start the Postgres container on port 5433

./gradlew downloadFile # Download jmc-agent to ./build/jmc-agent
./gradlew test # Compile and test
```
