# Examples for JfrUnit

Some examples for spotting potential performance regressions using [JfrUnit](https://github.com/moditect/jfrunit).

There are currently two examples:
| Example | Technology | Description |
| --- | --- | --- |
| [quarkus-hibernate-maven](./quarkus-hibernate-maven) | Maven, Quarkus, Hibernate, JMC Agent, JUnit | Service Testing GC, object allocation, socket I/O, and Hibernate HQL/SQL events |
| [spring-jooq-gradle](./examples/spring-jooq-gradle) | Gradle, Spring Boot, jOOQ, JMC Agent, JUnit | Service Demonstrating launching Gradle tests with the JMC Agent to test queries executed with the jOOQ DSL |

## License

This code base is available under the Apache License, version 2.
