# Examples for JfrUnit

Some examples for spotting potential performance regressions using [JfrUnit](https://github.com/moditect/jfrunit).
JfrUnit must be installed into the local Maven repository.
Alternatively, adjust the JfrUnit dependency in _example-service/pom.xml_ to the JitPack coordinates specified in the README in the JfrUnit repository.

## Build

This project requires OpenJDK 15 or later for its build.
Apache Maven is used for the build.

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

## License

This code base is available under the Apache License, version 2.
