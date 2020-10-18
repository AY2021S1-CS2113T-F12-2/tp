package seedu.duke.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.storage.StorageManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AddToWatchlistParserTest {
    protected static final String INVALID_PARAMETERS_TEST1 = "";
    protected static final String INVALID_PARAMETERS_TEST2 = "-a";
    protected static final String INVALID_PARAMETERS_TEST3 = "-n 1";
    protected static final String INVALID_FIELD_TEST1 = "-a Gundam";

    @Test
    void parse_emptyDescription_throwsAniException() {
        AddToWatchlistParser testParse = new AddToWatchlistParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_PARAMETERS_TEST1);
        });
    }

    @Test
    void parse_emptyField_throwsAniException() {
        AddToWatchlistParser testParse = new AddToWatchlistParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_PARAMETERS_TEST2);
        });
    }

    @Test
    void parse_invalidOption_throwsAniException() {
        AddToWatchlistParser testParse = new AddToWatchlistParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_PARAMETERS_TEST3);
        });
    }

    @Test
    void parse_nonIntegerField_throwsAniException() {
        AddToWatchlistParser testParse = new AddToWatchlistParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_FIELD_TEST1);
        });
    }
}