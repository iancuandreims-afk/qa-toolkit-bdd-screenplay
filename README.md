# QA Toolkit BDD Screenplay

A Java 21 test automation demo using Selenium WebDriver, JUnit 5, and the [Screenplay pattern](https://serenity-js.org/handbook/design/screenplay-pattern/). Tests read like scenarios: an *actor* performs *tasks* and *interactions*, and checks the outcome with *questions*.

## Screenplay concepts

| Concept | Package / type | Purpose |
|---------|----------------|---------|
| Actor | `Actor` | The user under test. Holds abilities and performs work. |
| Ability | `Ability`, `abilities/` | A capability granted to an actor, e.g. `BrowseTheWeb`, `ManageAlerts`, `ManageCookies`, `TakeScreenshots`. Granting a second ability of the same class replaces the first. |
| Interaction | `interactions/` | A single low-level step, e.g. `Navigate`, `Click`, `Type`, `Check`, `DragAndDrop`, `UploadFile`, `SwitchToFrame`, `Wait`. |
| Task | `Task`, `tasks/` | A business-level goal built from interactions, e.g. `SubscribeToNewsletter`. |
| Question | `Question`, `questions/` | Reads state from the application, e.g. `Text`, `Visible`, `Count`, `Value`, `Url`, `Title`, `CookieValue`. |
| Given/When/Then | `GivenWhenThen` | Static helpers (`given`, `when`, `then`) that make tests read as scenarios. |

The actor API is `can(ability)`, `attemptsTo(...)`, and `asksFor(question)`.

## Prerequisites

- JDK 21 (`maven.compiler.release` is 21)
- Maven 3.x
- Google Chrome. The ChromeDriver binary is resolved automatically by WebDriverManager, so no manual driver install is needed. Firefox and Edge helpers exist, but the demo tests use Chrome.

## Tech stack

| Library | Version | Scope |
|---------|---------|-------|
| Selenium Java | 4.25.0 | main |
| WebDriverManager | 5.9.2 | main |
| JUnit Jupiter | 5.10.2 | test |
| Mockito | 5.12.0 | test |
| REST Assured | 5.4.0 | test |
| slf4j-simple | 2.0.13 | test |

Surefire is 3.2.5.

## Project layout

- `src/main/java/com/qa/automation/qatoolkit/screenplay` – Screenplay abstractions: `Actor`, `Ability`, `Task`, `Question`, `GivenWhenThen`, `AssertionCollector`, `TimedAction`, plus the `abilities`, `interactions`, `questions`, and `tasks` packages.
- `src/test/java/com/qa/automation/qatoolkit/screenplay/e2e_tests` – End-to-end scenarios against the local demo site. `AbstractBaseScreenplayTest` starts the server, creates the actor, and quits the browser after each test.
- `src/test/java/com/qa/automation/qatoolkit/screenplay/unit_tests` – Unit tests for actors, abilities, interactions, questions, and tasks.
- `src/test/java/com/qa/automation/qatoolkit/screenplay/support` – `LocalStaticServer`, a small HTTP server on port 8080.
- `src/test/resources/local-app` – 19 static HTML pages used as the demo application.

## Local demo site

`LocalStaticServer` serves `src/test/resources/local-app` at `http://localhost:8080/`. The e2e tests load pages from fixed URLs such as `http://localhost:8080/newsletter.html`. The server starts in `@BeforeAll` of the base test class, so port 8080 must be free while tests run.

## Running the tests

Run all tests:

```
mvn test
```

Run a single test class:

```
mvn -Dtest=LocalStaticServerTest test
```

Test reports are written to `target/surefire-reports`.

## Writing a new test

Extend `AbstractBaseScreenplayTest` so the actor (`theUser`), the browser, and the local server are set up for you. Then compose the scenario with Given/When/Then:

```java
class NewsletterTaskTest extends AbstractBaseScreenplayTest {
    private static final String NEWSLETTER_PAGE = "http://localhost:8080/newsletter.html";

    @Test
    void actorCanSubscribeToNewsletterUsingTask() {
        given(theUser).wasAbleTo(
                Navigate.to(NEWSLETTER_PAGE)
        );

        when(theUser).attemptsTo(
                SubscribeToNewsletter.withData("Jane", "Doe", "jane@example.com")
        );

        then(theUser).shouldSee(
                Text.of(id("success-message")), "Thanks, Jane! You are subscribed."
        );
    }
}
```

To add a reusable business step, implement `Task` in `tasks/` and build it from existing interactions. To add a new way to read the UI, implement `Question` in `questions/`.

## License

See [LICENSE](LICENSE).
