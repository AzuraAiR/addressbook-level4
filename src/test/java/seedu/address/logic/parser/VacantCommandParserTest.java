package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.VacantCommand;
import seedu.address.model.building.Building;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUILDING_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUILDING_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class VacantCommandParserTest {
    private VacantCommandParser parser = new VacantCommandParser();

    @Test
    public void parse_retrieveBuilding_success() {
        assertParseSuccess(parser, VALID_BUILDING_1, new VacantCommand(new Building(VALID_BUILDING_1)));
    }

    @Test
    public void parse_invalidNumberOfArguments_failure() {
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, VacantCommand.MESSAGE_USAGE);

        //more than one building name arguement
        String tooManyArgumentsCommand = INVALID_BUILDING_DESC_2;
        assertParseFailure(parser, tooManyArgumentsCommand, message);

        //missing both arguments
        String noArgumentCommand = "";
        assertParseFailure(parser, noArgumentCommand, message);

        //missing both arguments, extra spaces
        String spacesArgumentCommand = "       ";
        assertParseFailure(parser, spacesArgumentCommand, message);
    }
}
