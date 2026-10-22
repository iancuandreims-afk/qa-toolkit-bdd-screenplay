package com.qa.automation.qatoolkit.screenplay;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * BDD-style entry points for writing scenarios as {@code given(actor).wasAbleTo(...)},
 * {@code when(actor).attemptsTo(...)}, and {@code then(actor).shouldSee(...)}.
 */
public final class GivenWhenThen {

    private GivenWhenThen() {
    }

    public static GivenClause given(Actor actor) {
        return new GivenClause(actor);
    }

    public static WhenClause when(Actor actor) {
        return new WhenClause(actor);
    }

    public static ThenClause then(Actor actor) {
        return new ThenClause(actor);
    }

    public static final class GivenClause {
        private final Actor actor;

        private GivenClause(Actor actor) {
            this.actor = actor;
        }

        public void wasAbleTo(Performable... tasks) {
            actor.attemptsTo(tasks);
        }
    }

    public static final class WhenClause {
        private final Actor actor;

        private WhenClause(Actor actor) {
            this.actor = actor;
        }

        public void attemptsTo(Performable... tasks) {
            actor.attemptsTo(tasks);
        }
    }

    public static final class ThenClause {
        private final Actor actor;

        private ThenClause(Actor actor) {
            this.actor = actor;
        }

        public void shouldSee(Question<Boolean> question) {
            boolean result = actor.asksFor(question);
            if (!result) {
                throw new AssertionError("Expected question to evaluate to true.");
            }
        }

        public <T> void shouldSee(Question<T> question, Consumer<T> assertion) {
            T result = actor.asksFor(question);
            assertion.accept(result);
        }

        public <T> void shouldSee(Question<T> question, T expected) {
            T actual = actor.asksFor(question);
            if (!Objects.equals(expected, actual)) {
                throw new AssertionError("Expected: " + expected + " but was: " + actual);
            }
        }

        public void shouldSee(Consumer<AssertionCollector> assertions) {
            AssertionCollector collector = new AssertionCollector(actor);
            assertions.accept(collector);
            collector.assertAll();
        }
    }
}
