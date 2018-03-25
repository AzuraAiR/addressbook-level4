package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BirthdayListEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.VenueTableEvent;

/**
 * Container for both browser panel and person information panel
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private BrowserPanel browserPanel;
    private BirthdayList birthdayList;
    private VenueTable venueTable;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane birthdayPlaceholder;

    @FXML
    private StackPane venuePlaceholder;

    public InfoPanel() {
        super(FXML);

        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        birthdayList = new BirthdayList(null);
        birthdayPlaceholder.getChildren().add(birthdayList.getRoot());

        venueTable = new VenueTable(null);
        venuePlaceholder.getChildren().add(venueTable.getRoot());

        browserPlaceholder.toFront();
        registerAsAnEventHandler(this);
    }

    public void freeResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPanel.loadPersonPage(event.getNewSelection().person);

        birthdayPlaceholder.getChildren().removeAll();
        browserPlaceholder.toFront();
    }

    @Subscribe
    private void handleBirthdayListEvent(BirthdayListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        birthdayPlaceholder.getChildren().removeAll();
        birthdayList = new BirthdayList(event.getBirthdayList());
        birthdayPlaceholder.getChildren().add(birthdayList.getRoot());
        birthdayPlaceholder.toFront();
    }

    @Subscribe
    private void handleVenueTableEvent(VenueTableEvent event) {
        venuePlaceholder.getChildren().removeAll();
        venueTable = new VenueTable(event.getSchedule());
        venuePlaceholder.getChildren().add(venueTable.getRoot());
        venuePlaceholder.toFront();
    }
}
