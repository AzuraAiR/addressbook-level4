package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Password;

//@@author Caijun7
/**
 * Uploads an address book to the existing address book.
 */
public class UploadCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "upload";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Uploads the current view of address book "
            + "to the specified filepath in Google Drive. "
            + "Parameters: FILEPATH PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + "data/addressbookbackup.xml "
            + "testpassword";

    public static final String MESSAGE_SUCCESS = "Current list of Persons, tags, or aliases from "
            + "Addressbook are successfully uploaded.";
    public static final String MESSAGE_FILE_UNABLE_TO_SAVE = "Unable to save or overwrite to given filepath. "
            + "Please give another filepath.";
    public static final String MESSAGE_INVALID_PASSWORD = "Password is in invalid format for Addressbook file.";

    private final String filepath;
    private final Password password;

    /**
     * Creates an UploadCommand to upload the current view of {@code AddressBook} to the filepath without a password
     */
    public UploadCommand(String filepath) {
        requireNonNull(filepath);

        this.filepath = filepath;
        password = null;
    }

    /**
     * Creates an UploadCommand to upload the current view of {@code AddressBook} to the filepath with a password
     */
    public UploadCommand(String filepath, String password) {
        requireNonNull(filepath);
        requireNonNull(password);

        this.filepath = filepath;
        this.password = new Password(password);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.uploadAddressBook(filepath, password);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FILE_UNABLE_TO_SAVE);
        } catch (WrongPasswordException e) {
            throw new CommandException(MESSAGE_INVALID_PASSWORD);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UploadCommand // instanceof handles nulls
                && filepath.equals(((UploadCommand) other).filepath));
    }
}
