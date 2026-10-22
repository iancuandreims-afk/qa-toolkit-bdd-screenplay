package com.qa.automation.qatoolkit.screenplay.unit_tests.core_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Performable;
import com.qa.automation.qatoolkit.screenplay.Question;
import com.qa.automation.qatoolkit.screenplay.Task;
import com.qa.automation.qatoolkit.screenplay.abilities.BrowseTheWeb;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ActorTest {

    @Test
    void actorCanStoreAndRetrieveAbility() {
        WebDriver driver = mock(WebDriver.class);
        Actor actor = new Actor("Tester");

        actor.can(new BrowseTheWeb(driver));

        assertSame(driver, actor.abilityTo(BrowseTheWeb.class).getDriver());
    }

    @Test
    void actorThrowsWhenAbilityNotPresent() {
        Actor actor = new Actor("Tester");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> actor.abilityTo(BrowseTheWeb.class));

        assertEquals("Actor 'Tester' does not have ability BrowseTheWeb", exception.getMessage());
    }

    @Test
    void canReplacesPreviousAbilityOfSameType() {
        WebDriver first = mock(WebDriver.class);
        WebDriver second = mock(WebDriver.class);
        Actor actor = new Actor("Tester");

        actor.can(new BrowseTheWeb(first));
        actor.can(new BrowseTheWeb(second));

        assertSame(second, actor.abilityTo(BrowseTheWeb.class).getDriver());
    }

    @Test
    void getNameReturnsConstructorName() {
        assertEquals("Tester", new Actor("Tester").getName());
    }

    @Test
    void attemptsToRunsPerformablesInOrder() {
        Actor actor = new Actor("Tester");
        List<String> order = new ArrayList<>();

        actor.attemptsTo(
                a -> order.add("first"),
                a -> order.add("second"),
                a -> order.add("third"));

        assertEquals(List.of("first", "second", "third"), order);
    }

    @Test
    void attemptsToPassesTheActorToEachPerformable() {
        Actor actor = new Actor("Tester");
        List<Actor> received = new ArrayList<>();
        Performable performable = received::add;

        actor.attemptsTo(performable);

        assertEquals(1, received.size());
        assertSame(actor, received.get(0));
    }

    @Test
    void attemptsToStopsAtFirstFailure() {
        Actor actor = new Actor("Tester");
        List<String> order = new ArrayList<>();

        assertThrows(IllegalStateException.class, () -> actor.attemptsTo(
                a -> order.add("before"),
                a -> {
                    throw new IllegalStateException("boom");
                },
                a -> order.add("after")));

        assertEquals(List.of("before"), order);
    }

    @Test
    void taskIsRunnableThroughAttemptsTo() {
        Actor actor = new Actor("Tester");
        List<String> order = new ArrayList<>();
        Task task = a -> order.add("ran as " + a.getName());

        actor.attemptsTo(task);

        assertEquals(List.of("ran as Tester"), order);
    }

    @Test
    void asksForReturnsTheQuestionAnswer() {
        Actor actor = new Actor("Tester");
        Question<String> question = a -> "answer for " + a.getName();

        assertEquals("answer for Tester", actor.asksFor(question));
    }
}
