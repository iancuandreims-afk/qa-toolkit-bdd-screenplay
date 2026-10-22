package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Click;
import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.questions.Count;
import com.qa.automation.qatoolkit.screenplay.questions.Selected;
import com.qa.automation.qatoolkit.screenplay.questions.Text;
import com.qa.automation.qatoolkit.screenplay.questions.Value;
import org.junit.jupiter.api.Test;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.*;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

class TableGridTest extends AbstractBaseScreenplayTest {
    private static final String TABLE_PAGE = "http://localhost:8080/table-grid.html";

    @Test
    void actorCanInspectTableRowsAndSelectedState() {
        given(theUser).wasAbleTo(
                Navigate.to(TABLE_PAGE)
        );

        then(theUser).shouldSee(softly -> {
                    softly.assertThat(Count.of(cssSelector("#orders-table tbody tr"))).isEqualTo(3);
                    softly.assertThat(Text.of(cssSelector("#orders-table tbody tr:nth-child(1) td:nth-child(2)"))).isEqualTo("Alice");
                    softly.assertThat(Selected.of(cssSelector("#orders-table tbody tr:nth-child(2) input[type='checkbox']"))).isTrue();
                    softly.assertThat(Value.of(cssSelector("#orders-table tbody tr:nth-child(2) input[type='checkbox']"))).isEqualTo("row-2");
                    softly.assertThat(Text.of(id("selected-row"))).isEqualTo("Selected row: row-2");
                }
        );

        when(theUser).attemptsTo(
                Click.on(cssSelector("#orders-table tbody tr:nth-child(1) input[type='checkbox']"))
        );

        then(theUser).shouldSee(
                Selected.of(cssSelector("#orders-table tbody tr:nth-child(1) input[type='checkbox']"))
        );

        then(theUser).shouldSee(
                Text.of(id("selected-row")), "Selected row: row-1"
        );
    }
}
