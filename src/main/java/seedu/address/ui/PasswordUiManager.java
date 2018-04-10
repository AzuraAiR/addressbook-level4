package seedu.address.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BirthdayNotificationEvent;
import seedu.address.commons.events.ui.PasswordCorrectEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.BirthdaysCommand;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

//@@author yeggasd
/**
 * The manager of the Password UI component.
 */
public class PasswordUiManager extends ComponentManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    public static final String FILE_OPS_ERROR_DIALOG_STAGE_TITLE = "File Op Error";
    public static final String FILE_OPS_ERROR_DIALOG_HEADER_MESSAGE = "Could not save data";
    public static final String FILE_OPS_ERROR_DIALOG_CONTENT_MESSAGE = "Could not save data to file";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private Storage storage;
    private Model model;
    private Ui ui;

    private MainWindow mainWindow;
    private Stage primaryStage;

    public PasswordUiManager(Storage storage, Model model, Ui ui) {
        super();
        this.storage = storage;
        this.model = model;
        this.ui = ui;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        this.primaryStage = primaryStage;
        try {
            PasswordWindow pw = new PasswordWindow(primaryStage, model, storage);
            pw.show();
            pw.fillInnerParts();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    @Override
    public void stop() {
        mainWindow.hide();
        mainWindow.releaseResources();
    }
    @Subscribe
    private void handlePasswordCorrectEvent(PasswordCorrectEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ui.start(primaryStage);
        autoOpenBirthdayNotification();
    }

    /**
     * Opens birthday notification
     * Called after UI is called
     */
    private void autoOpenBirthdayNotification() {
        LocalDate currentDate = LocalDate.now();

        if (model != null) {
            EventsCenter.getInstance().post(new BirthdayNotificationEvent(BirthdaysCommand
                    .parseBirthdaysForNotification(model.getAddressBook().getPersonList(), currentDate), currentDate));
        }
    }
}
