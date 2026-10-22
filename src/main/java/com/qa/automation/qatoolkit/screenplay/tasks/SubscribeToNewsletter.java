package com.qa.automation.qatoolkit.screenplay.tasks;

import com.qa.automation.qatoolkit.screenplay.Actor;
import com.qa.automation.qatoolkit.screenplay.Task;
import com.qa.automation.qatoolkit.screenplay.interactions.Check;
import com.qa.automation.qatoolkit.screenplay.interactions.Click;
import com.qa.automation.qatoolkit.screenplay.interactions.Type;
import org.openqa.selenium.By;

import static org.openqa.selenium.By.id;

/**
 * Fills in and submits the newsletter form.
 * <p>
 * {@link #withData} expects the form to use the fixed element IDs {@code first-name},
 * {@code last-name}, {@code email}, {@code terms} and {@code subscribe-button}.
 */
public class SubscribeToNewsletter implements Task {
    private final String firstName;
    private final String lastName;
    private final String email;

    private SubscribeToNewsletter(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static SubscribeToNewsletter withData(String firstName, String lastName, String email) {
        return new SubscribeToNewsletter(firstName, lastName, email);
    }

    @Override
    public void performAs(Actor actor) {
        actor.attemptsTo(
                Type.text(firstName).into(id("first-name")),
                Type.text(lastName).into(id("last-name")),
                Type.text(email).into(id("email")),
                Check.box(id("terms")),
                Click.on(id("subscribe-button"))
        );
    }
}
