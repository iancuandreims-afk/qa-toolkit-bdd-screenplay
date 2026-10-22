package com.qa.automation.qatoolkit.screenplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Soft-assertion helper: records every failed check instead of stopping at the first one,
 * then reports them all together via {@link #assertAll()}.
 */
public class AssertionCollector {
    private final Actor actor;
    private final List<String> failures = new ArrayList<>();

    public AssertionCollector(Actor actor) {
        this.actor = actor;
    }

    /** Starts a fluent assertion on a raw value. */
    public AssertionCondition assertThat(Object actual) {
        return new AssertionCondition(actual);
    }

    /** Starts a fluent assertion on the answer to a {@link Question}, asked by the actor. */
    public AssertionCondition assertThat(Question<?> question) {
        Object actual = actor.asksFor(question);
        return new AssertionCondition(actual);
    }

    /** Records {@code message} as a failure when {@code condition} is false. */
    public void checkTrue(boolean condition, String message) {
        if (!condition) {
            failures.add(message);
        }
    }

    /** Records a failure, including expected and actual values, when the two are not equal. */
    public void checkEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            failures.add(message + " Expected: " + expected + " but was: " + actual);
        }
    }

    /**
     * Throws a single {@link AssertionError} listing all recorded failures, if there are any.
     * Does nothing when every check passed.
     */
    public void assertAll() {
        if (!failures.isEmpty()) {
            throw new AssertionError(String.join(System.lineSeparator(), failures));
        }
    }

    public final class AssertionCondition {
        private final Object actual;

        private AssertionCondition(Object actual) {
            this.actual = actual;
        }

        public AssertionCondition isTrue() {
            if (!Boolean.TRUE.equals(actual)) {
                failures.add("Expected: true but was: " + actual);
            }
            return this;
        }

        public AssertionCondition isEqualTo(Object expected) {
            if (!Objects.equals(expected, actual)) {
                failures.add("Expected: " + expected + " but was: " + actual);
            }
            return this;
        }

        public AssertionCondition contains(String expected) {
            if (actual == null || !actual.toString().contains(expected)) {
                failures.add("Expected value to contain: " + expected + " but was: " + actual);
            }
            return this;
        }
    }
}
