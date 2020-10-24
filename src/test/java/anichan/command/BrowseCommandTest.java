package anichan.command;

import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.parser.BrowseParser;
import anichan.storage.StorageManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BrowseCommandTest {

    User user;
    private static AnimeData animeData;
    private static StorageManager storageManager;
    BrowseParser testParse = new BrowseParser();

    private static final String NAME_SORT_ASC_2 = "-s name -o asc -p 2";
    private static final String NAME_SORT_DSC_3 = "-s name -o dsc -p 3";
    private static final String RATING_SORT_ASC_3 = "-s rating -o asc -p 3";
    private static final String RATING_SORT_DSC_3 = "-s rating -o dsc -p 3";
    private static final String LARGE_PAGE_NUM = "-p 9999";
    private static final String NEGATIVE_PAGE_NUM = "-p -1";
    private static final String ZERO_PAGE_NUM = "-p 0";
    private static final String LAST_PAGE = "-p 26";
    private static final String ID_SORT_ASC = "-o asc";

    private static final int LARGE_NUM = 9999;
    private static final int NEGATIVE_NUM = -1;
    private static final int ZERO_NUM = 0;

    private static final String EMPTY_STRING = "";
    private static final String OUTPUT_FIRST_ANIME = "1. Cowboy Bebop [Id: 1]";
    private static final String OUTPUT_PAGE_1 = "Browsing Page: 1";
    private static final String OUTPUT_PAGE_2 = "Browsing Page: 2";
    private static final String OUTPUT_PAGE_3 = "Browsing Page: 3";
    private static final String OUTPUT_LAST_ANIME = "1. Major S2 [Id: 511]";
    private static final String OUTPUT_ASC_ANIME = "21. Akane Maniax [Id: 250]";
    private static final String OUTPUT_DSC_ANIME = "41. Trinity Blood [Id: 18]";
    private static final String OUTPUT_ASC_RATING = "41. Psychic Academy [Id: 304]";
    private static final String OUTPUT_DSC_RATING = "41. Beck: Mongolian Chop Squad [Id: 39]";

    @BeforeAll
    static void setUp() throws AniException {
        animeData = new AnimeData();
        storageManager = new StorageManager(EMPTY_STRING);
    }

    @Test
    void execute_printLastSeries_correctOutput() throws AniException {
        BrowseCommand testBrowse = testParse.parse(LAST_PAGE);
        testBrowse.execute(animeData, storageManager, user);
    }

    @Test
    void execute_assertTest_assertionThrow() {
        BrowseCommand testBrowse = new BrowseCommand();
        testBrowse.setSortType(LARGE_NUM);
        assertThrows(AssertionError.class, () -> {
            testBrowse.execute(animeData, storageManager, user);
        });

        testBrowse.setSortType(ZERO_NUM);
        testBrowse.setOrder(LARGE_NUM);
        testBrowse.setSortType(NEGATIVE_NUM);
        assertThrows(AssertionError.class, () -> {
            testBrowse.execute(animeData, storageManager, user);
        });
    }

    @Test
    void execute_browseWithNoParam_correctOutput() throws AniException {
        BrowseCommand testBrowse = testParse.parse(EMPTY_STRING);
        testBrowse.setAnimePerPage(1);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_FIRST_ANIME + System.lineSeparator() + OUTPUT_PAGE_1, result);
    }

    @Test
    void execute_browseByAscOnly_correctOutput() throws AniException {
        BrowseCommand testBrowse = testParse.parse(ID_SORT_ASC);
        testBrowse.setAnimePerPage(1);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_LAST_ANIME + System.lineSeparator() + OUTPUT_PAGE_1, result);
    }

    @Test
    void execute_browseByNameAsc_correctOutput() throws AniException {
        BrowseCommand testBrowse = testParse.parse(NAME_SORT_ASC_2);
        testBrowse.setAnimePerPage(1);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_ASC_ANIME + System.lineSeparator() + OUTPUT_PAGE_2, result);
    }

    @Test
    void execute_browseByNameDsc_correctOutput() throws AniException {
        BrowseCommand testBrowse = testParse.parse(NAME_SORT_DSC_3);
        testBrowse.setAnimePerPage(1);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_DSC_ANIME + System.lineSeparator() + OUTPUT_PAGE_3, result);
    }
    @Test
    void execute_browseByRatingAsc_correctOutput() throws AniException {
        BrowseCommand testBrowse = testParse.parse(RATING_SORT_ASC_3);
        testBrowse.setAnimePerPage(1);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_ASC_RATING + System.lineSeparator() + OUTPUT_PAGE_3, result);
    }
    @Test
    void execute_browseByRatingDsc_correctOutput() throws AniException {
        BrowseCommand testBrowse = testParse.parse(RATING_SORT_DSC_3);
        testBrowse.setAnimePerPage(1);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_DSC_RATING + System.lineSeparator() + OUTPUT_PAGE_3, result);
    }

    @Test
    void execute_resetSort_correctOutput() throws AniException {
        BrowseCommand testBrowse = new BrowseCommand();
        testBrowse.setAnimePerPage(1);
        testBrowse.setSortType(3);
        String result = testBrowse.execute(animeData, storageManager, user);
        System.out.println(result);
        assertEquals(OUTPUT_FIRST_ANIME + System.lineSeparator() + OUTPUT_PAGE_1, result);
    }


    @Test
    void execute_invalidPageNum_throwsAniException() throws AniException {
        BrowseCommand testBrowse = testParse.parse(LARGE_PAGE_NUM);
        assertThrows(AniException.class, () -> {
            testBrowse.execute(animeData, storageManager, user);
        });

        assertThrows(AniException.class, () -> {
            testParse.parse(NEGATIVE_PAGE_NUM);
        });

        BrowseCommand testBrowse2 = testParse.parse(ZERO_PAGE_NUM);
        assertEquals(testBrowse2.getPage(), 1);
    }
}
