package com.qa.automation.qatoolkit.screenplay.unit_tests.core_tests;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Performable;
import com.qa.automation.qatoolkit.screenplay.Question;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GivenWhenThenTest {

    @Test
    void supportsGivenWhenThenScreenplayStyle() {
        Actor actor = new Actor("Tester");
        AtomicInteger counter = new AtomicInteger();

        Question<Boolean> isReady = currentActor -> true;
        Question<String> result = currentActor -> "result";
        Performable action = currentActor -> counter.incrementAndGet();

        given(actor).wasAbleTo(action);
        when(actor).attemptsTo(action);

        then(actor).shouldSee(isReady);
        then(actor).shouldSee(result, "result");
        then(actor).shouldSee(actual -> {
            actual.checkTrue(true, "truthy check");
            actual.checkEquals("result", actor.asksFor(result), "result check");
        });

        assertEquals(2, counter.get());
    }
}
