package anichan.parser;

import anichan.commands.ViewWatchlistCommand;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

//@@author michaeldinata
/**
 * Handles parsing for view watchlist command.
 */
public class ViewWatchlistParser extends CommandParser {
    protected static final String VIEW_PARAM = "v";
    protected static final String SPLIT_DASH = "-";
    protected static final String TOO_MUCH_ARGUMENTS = "View command " + TOO_MUCH_FIELDS;
    protected static final String NON_INTEGER_PROVIDED = "Please specify an Int value for watchlist ID!";
    protected static final int MAX_INDEX_DIGITS = 4;
    protected static final String OUT_OF_BOUND_INDEX_ERROR = "Watchlist ID is invalid!";
    private static final Logger LOGGER = AniLogger.getAniLogger(AddToWatchlistParser.class.getName());

    private ViewWatchlistCommand viewWatchlistCommand;

    /**
     * Creates a new instance of ViewWatchlistParser.
     */
    public ViewWatchlistParser() {
        viewWatchlistCommand = new ViewWatchlistCommand();
    }

    /**
     * Parses the specified command description.
     *
     * @param description the specified command description
     * @return initialised {@code RemoveCommand} object
     * @throws AniException when an error occurred while parsing the command description
     */
    public ViewWatchlistCommand parse(String description) throws AniException {
        String[] paramGiven = description.split(SPLIT_DASH, 2);

        paramIsSetCheck(paramGiven);
        if (paramGiven[1] == null || paramGiven[1].trim().isBlank()) {
            throw new AniException(NO_PARAMETER_PROVIDED);
        }
        
        parameterParser(paramGiven[1]);
        LOGGER.log(Level.INFO, "Parameter parsed properly");

        return viewWatchlistCommand;
    }

    /**
     * Parses the parameter provided in the command description.
     *
     * @param paramGiven a String Array containing the parameters and the value
     * @throws AniException when an error occurred while parsing the parameters
     */
    private void parameterParser(String paramGiven) throws AniException {
        String[] paramParts = paramGiven.split(" ");

        switch (paramParts[0].trim()) {
        case VIEW_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            if (!isInteger(paramParts[1].trim())) {
                throw new AniException(NON_INTEGER_PROVIDED);
            }
            if (paramParts[1].length() >= 4) {
                throw new AniException(OUT_OF_BOUND_INDEX_ERROR);
            }
            viewWatchlistCommand.setWatchlistIndex(Integer.parseInt(paramParts[1].trim()));
            break;
        default:
            String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED;
            throw new AniException(invalidParameter);
        }
    }
}
