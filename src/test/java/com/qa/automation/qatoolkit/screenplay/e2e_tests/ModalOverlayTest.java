package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Click;
import com.qa.automation.qatoolkit.screenplay.interactions.Hover;
import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.questions.Text;
import com.qa.automation.qatoolkit.screenplay.questions.Visible;
import org.junit.jupiter.api.Test;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.*;
import static org.openqa.selenium.By.id;

class ModalOverlayTest extends AbstractBaseScreenplayTest {
    private static final String MODAL_PAGE = "http://localhost:8080/modal-overlay.html";

    @Test
    void actorCanOpenAndCloseModalOverlay() {
        given(theUser).wasAbleTo(
                Navigate.to(MODAL_PAGE)
        );

        then(theUser).shouldSee(
                Visible.of(id("modal")), false
        );

        when(theUser).attemptsTo(
                Click.on(id("open-modal"))
        );

        then(theUser).shouldSee(
                Visible.of(id("modal"))
        );

        then(theUser).shouldSee(
                Visible.of(id("backdrop"))
        );

        when(theUser).attemptsTo(
                Click.on(id("close-modal"))
        );

        then(theUser).shouldSee(
                Visible.of(id("modal")), false
        );
    }

    @Test
    void actorCanRevealMenuOnHover() {
        given(theUser).wasAbleTo(
                Navigate.to(MODAL_PAGE)
        );

        then(theUser).shouldSee(
                Visible.of(id("menu-panel")), false
        );

        when(theUser).attemptsTo(
                Hover.on(id("menu-trigger"))
        );

        then(theUser).shouldSee(
                Visible.of(id("menu-panel"))
        );

        then(theUser).shouldSee(
                Text.of(id("menu-panel")),
                "Profile\n" +
                        "Settings\n" +
                        "Logout");
    }
}
