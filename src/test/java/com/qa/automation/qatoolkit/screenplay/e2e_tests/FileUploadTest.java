package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.Click;
import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.interactions.UploadFile;
import com.qa.automation.qatoolkit.screenplay.questions.Text;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.*;
import static org.openqa.selenium.By.id;

class FileUploadTest extends AbstractBaseScreenplayTest {
    private static final String FILE_UPLOAD_PAGE = "http://localhost:8080/file-upload.html";
    private static final String TEST_FILE = Path.of("src", "test", "resources", "local-app", "upload-sample.txt").toAbsolutePath().toString();

    @Test
    void actorCanUploadAFileUsingFileInput() {
        given(theUser).wasAbleTo(
                Navigate.to(FILE_UPLOAD_PAGE)
        );

        then(theUser).shouldSee(
                Text.of(id("upload-status")), "no file selected"
        );

        when(theUser).attemptsTo(
                UploadFile.path(TEST_FILE).in(id("upload-input")),
                Click.on(id("upload-button"))
        );

        then(theUser).shouldSee(
                Text.of(id("upload-status")), "uploaded: upload-sample.txt"
        );
    }
}
