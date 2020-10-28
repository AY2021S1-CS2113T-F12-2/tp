package anichan.parser;

import anichan.commands.WatchlistCommand;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WatchlistParser extends CommandParser {
    private static final String CREATE_OPTION = "n";
    private static final String LIST_OPTION = "l";
    private static final String SELECT_OPTION = "s";
    private static final String DELETE_OPTION = "d";
    private static final String BLANK = "";

    private static final String TOO_MUCH_ARGUMENTS = "Watchlist command" + TOO_MUCH_FIELDS;
    private static final String INVALID_OPTION = "Watchlist command only accepts the options: -n, -l, -s, and -d.";
    private static final String WATCHLIST_NAME_IS_EMPTY = "Watchlist name cannot be empty!";
    private static final String WATCHLIST_INDEX_IS_EMPTY = "Watchlist index cannot be empty!";
    private static final String WATCHLIST_INDEX_IS_ZERO = "Watchlist index cannot be zero!";
    private static final String WATCHLIST_INDEX_IS_NOT_POSITIVE_INTEGER = "Watchlist index is not a positive integer!";

    private static final int CREATION_REQUIRED_PARAMETER_COUNT = 2;
    private static final int LIST_REQUIRED_PARAMETER_COUNT = 1;
    private static final int MODIFICATION_REQUIRED_PARAMETER_COUNT = 2;

    private static final Logger LOGGER = AniLogger.getAniLogger(WatchlistParser.class.getName());

    public WatchlistCommand parse(String description) throws AniException {
        assert description != null : DESCRIPTION_CANNOT_BE_NULL;
        String[] paramGiven = parameterSplitter(description);
        paramIsSetCheck(paramGiven);
        if (paramGiven.length > 2) {
            throw new AniException(TOO_MUCH_ARGUMENTS);
        } else if (!paramGiven[0].isBlank()) {
            throw new AniException(paramGiven[0] + NOT_RECOGNISED);
        }

        String[] parsedParts = parameterParser(paramGiven[1]);
        int watchlistIndex = 0;
        if (!parsedParts[2].equals(BLANK)) {
            watchlistIndex = parseStringToInteger(parsedParts[2]);
            if (watchlistIndex == 0) {
                throw new AniException(WATCHLIST_INDEX_IS_ZERO);
            }
        }

        LOGGER.log(Level.INFO, "Returning WatchlistCommand object with option: "
                                    + parsedParts[0] + ", and information: " + parsedParts[1]);

        String option = parsedParts[0];
        String watchlistName = parsedParts[1];
        return new WatchlistCommand(option, watchlistName, watchlistIndex);
    }

    private String[] parameterParser(String parameter) throws AniException {
        String[] parsedParts = parameter.split(SPLIT_WHITESPACE, 2);
        String option = parsedParts[0];
        switch (option) {
        case CREATE_OPTION:
            checkCreationParameters(parsedParts);
            String watchlistName = parsedParts[1];
            return new String[]{option, watchlistName, BLANK};
        case LIST_OPTION:
            checkListParameters(parsedParts);
            return new String[]{option, BLANK, BLANK};
        case SELECT_OPTION:
            // Fallthrough because select option will call checkModificationParameters method too.
        case DELETE_OPTION:
            checkModificationParameters(parsedParts);
            String watchlistIndex = parsedParts[1];
            return new String[]{option, BLANK, watchlistIndex};
        default:
            throw new AniException(INVALID_OPTION);
        }
    }

    private void checkCreationParameters(String[] parsedParts) throws AniException {
        if (parsedParts.length != CREATION_REQUIRED_PARAMETER_COUNT) {
            throw new AniException(WATCHLIST_NAME_IS_EMPTY);
        }
    }

    private void checkListParameters(String[] parsedParts) throws AniException {
        if (parsedParts.length != LIST_REQUIRED_PARAMETER_COUNT) {
            throw new AniException(TOO_MUCH_ARGUMENTS);
        }
    }

    private void checkModificationParameters(String[] parsedParts) throws AniException {
        if (parsedParts.length != MODIFICATION_REQUIRED_PARAMETER_COUNT) {
            throw new AniException(WATCHLIST_INDEX_IS_EMPTY);
        }

        String watchlistIndex = parsedParts[1];
        if (!isInt(watchlistIndex)) {
            throw new AniException(WATCHLIST_INDEX_IS_NOT_POSITIVE_INTEGER);
        }
    }
}
