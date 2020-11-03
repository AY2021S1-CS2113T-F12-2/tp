package anichan.parser;

import anichan.commands.RemoveCommand;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

//@@author michaeldinata
/**
 * Handles parsing for remove command.
 */
public class RemoveCommandParser extends CommandParser {
    protected static final String TOO_MUCH_ARGUMENTS = "Remove command" + TOO_MUCH_FIELDS;
    protected static final String ANIME_ID_IN_WATCHLIST = "Anime ID in Watchlist!";
    protected static final String OUT_OF_BOUND_INDEX_ERROR = "Anime ID in watchlist is invalid!";
    private static final Logger LOGGER = AniLogger.getAniLogger(RemoveCommandParser.class.getName());

    private RemoveCommand removeCommand;

    /**
     * Creates a new instance of RemoveCommandParser.
     */
    public RemoveCommandParser() {
        removeCommand = new RemoveCommand();
    }

    /**
     * Parses the specified command description.
     *
     * @param description the specified command description
     * @return initialised {@code RemoveCommand} object
     * @throws AniException when an error occurred while parsing the command description
     */
    public RemoveCommand parse(String description) throws AniException {
        description = description.trim();

        if (description == null || description.isBlank()) {
            throw new AniException(DESCRIPTION_CANNOT_BE_NULL);
        }

        parameterParser(description);
        LOGGER.log(Level.INFO, "Parameter parsed properly");
        
        return removeCommand;
    }

    /**
     * Parses the parameter provided in the command description.
     *
     * @param fieldGiven a String Array containing the value given
     * @throws AniException when an error occurred while parsing the parameters
     */
    private void parameterParser(String fieldGiven) throws AniException {
        String fieldValue = fieldGiven.trim();
        String[] fieldParts = fieldValue.split(WHITESPACE);

        if (fieldParts.length > 1) {
            throw new AniException(TOO_MUCH_ARGUMENTS);
        }
        isIntegerCheck(fieldValue, ANIME_ID_IN_WATCHLIST);

        try {
            removeCommand.setWatchlistListIndex(Integer.parseInt(fieldValue));
        } catch (NumberFormatException e) {
            throw new AniException(OUT_OF_BOUND_INDEX_ERROR);
        }
    }
}
