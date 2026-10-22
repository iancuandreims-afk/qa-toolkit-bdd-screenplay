package com.qa.automation.qatoolkit.screenplay.unit_tests.core_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.AssertionCollector;
import com.qa.automation.qatoolkit.screenplay.Question;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssertionCollectorTest {

    private final Actor actor = new Actor("Tester");
    private final AssertionCollector collector = new AssertionCollector(actor);

    @Test
    void assertAllPassesWhenNothingFailed() {
        collector.checkTrue(true, "unused");
        collector.checkEquals("a", "a", "unused");

        assertDoesNotThrow(collector::assertAll);
    }

    @Test
    void checkTrueRecordsFailureWithMessage() {
        collector.checkTrue(false, "Banner missing.");

        AssertionError error = assertThrows(AssertionError.class, collector::assertAll);

        assertEquals("Banner missing.", error.getMessage());
    }

    @Test
    void checkEqualsTreatsTwoNullsAsEqual() {
        collector.checkEquals(null, null, "unused");

        assertDoesNotThrow(collector::assertAll);
    }

    @Test
    void checkEqualsRecordsExpectedAndActualValues() {
        collector.checkEquals("a", "b", "Title.");

        AssertionError error = assertThrows(AssertionError.class, collector::assertAll);

        assertEquals("Title. Expected: a but was: b", error.getMessage());
    }

    @Test
    void assertAllReportsEveryFailureOnSeparateLines() {
        collector.checkTrue(false, "First.");
        collector.checkEquals(1, 2, "Second.");

        AssertionError error = assertThrows(AssertionError.class, collector::assertAll);

        assertEquals("First." + System.lineSeparator() + "Second. Expected: 1 but was: 2",
                error.getMessage());
    }

    @Test
    void isTrueAcceptsBooleanTrue() {
        collector.assertThat(Boolean.TRUE).isTrue();

        assertDoesNotThrow(collector::assertAll);
    }

    @Test
    void isTrueRejectsNonBooleanValueEvenWhenItLooksTrue() {
        collector.assertThat("true").isTrue();

        AssertionError error = assertThrows(AssertionError.class, collector::assertAll);

        assertEquals("Expected: true but was: true", error.getMessage());
    }

    @Test
    void isEqualToPassesForEqualValues() {
        collector.assertThat(2).isEqualTo(2);

        assertDoesNotThrow(collector::assertAll);
    }

    @Test
    void isEqualToRecordsMismatch() {
        collector.assertThat(1).isEqualTo(2);

        AssertionError error = assertThrows(AssertionError.class, collector::assertAll);

        assertEquals("Expected: 2 but was: 1", error.getMessage());
    }

    @Test
    void containsPassesWhenSubstringIsPresent() {
        collector.assertThat("hello world").contains("world");

        assertDoesNotThrow(collector::assertAll);
    }

    @Test
    void containsRecordsFailureWhenSubstringIsMissing() {
        collector.assertThat("hello").contains("xyz");

        AssertionError error = assertThrows(AssertionError.class, collector::assertAll);

        assertEquals("Expected value to contain: xyz but was: hello", error.getMessage());
    }

    @Test
    void containsFailsForNullActualValue() {
        collector.assertThat((Object) null).contains("x");

        AssertionError error = assertThrows(AssertionError.class, collector::assertAll);

        assertEquals("Expected value to contain: x but was: null", error.getMessage());
    }

    @Test
    void chainedAssertionsAccumulateAllFailures() {
        collector.assertThat(1).isEqualTo(2).contains("x");

        AssertionError error = assertThrows(AssertionError.class, collector::assertAll);

        assertEquals("Expected: 2 but was: 1"
                + System.lineSeparator()
                + "Expected value to contain: x but was: 1", error.getMessage());
    }

    @Test
    void assertThatQuestionIsAnsweredByTheActor() {
        Question<String> question = a -> {
            assertSame(actor, a);
            return "Tester";
        };

        collector.assertThat(question).isEqualTo("Tester");

        assertDoesNotThrow(collector::assertAll);
    }
}
