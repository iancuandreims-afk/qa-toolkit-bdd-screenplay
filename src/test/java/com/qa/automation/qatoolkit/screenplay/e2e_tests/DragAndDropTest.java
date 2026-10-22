package com.qa.automation.qatoolkit.screenplay.e2e_tests;

import com.qa.automation.qatoolkit.screenplay.interactions.DragAndDrop;
import com.qa.automation.qatoolkit.screenplay.interactions.DropFile;
import com.qa.automation.qatoolkit.screenplay.interactions.Navigate;
import com.qa.automation.qatoolkit.screenplay.questions.Text;
import org.junit.jupiter.api.Test;

import static com.qa.automation.qatoolkit.screenplay.GivenWhenThen.*;
import static org.openqa.selenium.By.id;

class DragAndDropTest extends AbstractBaseScreenplayTest {
    private static final String DRAG_DROP_PAGE = "http://localhost:8080/drag-drop.html";

    @Test
    void actorCanDragCardToDropZone() {
        given(theUser).wasAbleTo(
                Navigate.to(DRAG_DROP_PAGE)
        );

        then(theUser).shouldSee(
                Text.of(id("drop-status")), "not-dropped"
        );

        when(theUser).attemptsTo(
                DragAndDrop.from(id("drag-source")).to(id("drop-zone"))
        );

        then(theUser).shouldSee(
                Text.of(id("drop-status")), "dropped"
        );
    }

    @Test
    void actorCanDropFileOntoUploadZone() {
        given(theUser).wasAbleTo(
                Navigate.to(DRAG_DROP_PAGE)
        );

        then(theUser).shouldSee(
                Text.of(id("upload-status")), "no-file"
        );

        when(theUser).attemptsTo(
                DropFile.withName("sample.txt")
                        .andContent("hello from file upload")
                        .onto(id("upload-dropzone"))
        );

        then(theUser).shouldSee(
                Text.of(id("upload-status")), "uploaded: sample.txt"
        );
    }
}
