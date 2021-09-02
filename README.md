# Examples for JfrUnit

Some examples for spotting potential performance regressions using [JfrUnit](https://github.com/moditect/jfrunit).

## Prerequisites

This project requires OpenJDK 16 for its build.
Apache Maven is used for the build.
[JMC Agent](https://developers.redhat.com/blog/2020/10/29/collect-jdk-flight-recorder-events-at-runtime-with-jmc-agent) must be installed into the local Maven repository.
As JMC Agent doesn't publish binaries currently, it must be built from source:

```shell
git clone https://github.com/openjdk/jmc
cd jmc/agent
mvn clean install
```

## Build

Run the following to build the project:

```shell
cd user-service
mvn clean verify
java -Dquarkus.http.port=8082 -jar target/jfrunit-demo-user-1.0.0-SNAPSHOT-runner.jar
```

This starts the "user" service, which is accessed by the example service within one of the regression scenarios.
Then build the example service itself:

```shell
cd example-service
mvn clean verify
```

In each test class, there's a method `...Regression()` with an assertion that's commented out.
When commenting it in, this test should fail due to a performance "regresssion",
e.g. due to higher memory allocation than expected, more IO, or more SQL statements.

## Running in the IDE

When running the `TodoResourceSqlStatementsTest` in your IDE, make sure to specify the correct JMC Agent configuration,
as seen in the _example-service/pom.xml_ file.

## Running the Application

For manual testing, build the application, start a separate Postgres instance via Docker Compose and launch the app like so:

```shell
docker-compose up
cd example-service
clean verify -DskipTests=true
java -jar ./example-service/target/quarkus-app/quarkus-run.jar

# Testing, e.g. via httpie
http POST localhost:8080/todo title=Test priority=2 completed=true
```

## License

This code base is available under the Apache License, version 2.
