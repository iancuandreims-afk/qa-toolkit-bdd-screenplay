package com.qa.automation.qatoolkit.screenplay.unit_tests.interactions_tests;

import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestActors.actorWithBrowseTheWebAbility;
import static com.qa.automation.qatoolkit.screenplay.unit_tests.UnitTestMocks.mockDriver;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qa.automation.qatoolkit.screenplay.interactions.RefreshPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;

class RefreshPageTest {

    @Test
    void refreshesTheCurrentPage() {
        WebDriver driver = mockDriver();
        Navigation navigation = mock(Navigation.class);
        when(driver.navigate()).thenReturn(navigation);

        RefreshPage.now().performAs(actorWithBrowseTheWebAbility(driver));

        verify(navigation).refresh();
    }
}
