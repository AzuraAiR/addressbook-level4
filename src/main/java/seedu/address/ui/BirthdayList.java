package seedu.address.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class BirthdayList extends UiPart<Region> {

    private static final String FXML = "BirthdayList.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea birthdayList;

    public BirthdayList() {
        super(FXML);
        birthdayList.textProperty().bind(displayed);
    }

    public void loadList(String list){
        Platform.runLater(() -> displayed.setValue(list));
    }


}
