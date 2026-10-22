# QA Toolkit BDD Screenplay

A Java 21 test automation demo using Selenium WebDriver, JUnit 5, and the Screenplay pattern.

## Project layout

- `src/main/java/com/qa/automation/qatoolkit/screenplay` – Screenplay abstractions (actors, tasks, questions, interactions).
- `src/test/java/com/qa/automation/qatoolkit/screenplay` – Tests, including `e2e_tests` and `support` (local static server helper).
- `src/test/resources/local-app` – Static demo site served by the local test server.

## Running the tests

Requires JDK 21 and Maven.

```
mvn test
```

To run a single test class:

```
mvn -Dtest=LocalStaticServerTest test
```

Test reports are written to `target/surefire-reports`.
