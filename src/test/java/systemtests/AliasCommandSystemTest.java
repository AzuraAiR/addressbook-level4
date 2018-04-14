package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_ADD;
import static seedu.address.testutil.TypicalAliases.ADD;

import org.junit.Test;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.testutil.AliasUtil;

public class AliasCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void alias() throws Exception {
        /* Case: add an alias to a non-empty address book, command with leading spaces and trailing spaces -> added */
        Alias toAdd = ADD;
        String command = "   " + AliasCommand.COMMAND_WORD + "  " + ALIAS_DESC_ADD;
        String[][] aliases = new String[][] {{VALID_ALIAS_ADD}};
        assertCommandSuccess(command, toAdd, aliases);
    }

    /**
     * Executes the {@code AliasCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AliasCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code AliasListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Alias toAdd, String[][] expectedTable) {
        assertCommandSuccess(AliasUtil.getAliasCommand(toAdd), toAdd, expectedTable);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Alias)}. Executes {@code command}
     * instead.
     * @see AliasCommandSystemTest#assertCommandSuccess(Alias, String[][])
     */
    private void assertCommandSuccess(String command, Alias toAdd, String[][] expectedTable) {
        Model expectedModel = getModel();
        try {
            expectedModel.addAlias(toAdd);
        } catch (DuplicateAliasException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AliasCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedTable);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Alias)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code AliasListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AliasCommandSystemTest#assertCommandSuccess(String, Alias, String[][])
     */
    private void assertCommandSuccess(String command, Model expectedModel,
                                      String expectedResultMessage, String[][] expectedTable) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
        executeCommand(ListCommand.COMMAND_WORD);
        assertTableDisplaysExpected("", expectedResultMessage, expectedTable);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code AliasListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
