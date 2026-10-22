package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.questions.Text;
import com.qa.automation.qatoolkit.screenplay.tasks.SubscribeToNewsletter;
import org.junit.jupiter.api.Test;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.*;
import static org.openqa.selenium.By.id;

class NewsletterTaskTest extends AbstractBaseScreenplayTest {
    private static final String NEWSLETTER_PAGE = "http://localhost:8080/newsletter.html";

    @Test
    void actorCanSubscribeToNewsletterUsingTask() {
        given(theUser).wasAbleTo(
                Navigate.to(NEWSLETTER_PAGE)
        );

        when(theUser).attemptsTo(
                SubscribeToNewsletter.withData(
                        "Jane",
                        "Doe",
                        "jane@example.com"
                )
        );

        then(theUser).shouldSee(
                Text.of(id("success-message")), "Thanks, Jane! You are subscribed."
        );
    }
}
