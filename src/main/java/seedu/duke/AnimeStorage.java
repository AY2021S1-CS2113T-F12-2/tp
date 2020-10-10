package seedu.duke;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class AnimeStorage {

    /* Files */
    private static final String RELATIVE_DIR = System.getProperty("user.dir");
    private static final String FILE_SEPARATOR = File.separator;

    private File dataFile;
    private String[] pathnames;

    //public static void main(String[] args) throws IOException {
    //    AnimeStorage  animeStorage = new AnimeStorage("/data/AniListData");
    //    AnimeData animeList = new AnimeData(animeStorage.readAnimeDatabase());
    //    animeList.printAll();
    //}

    public AnimeStorage(String fileFolder) {
        this.dataFile = new File(prepareFile(fileFolder));
        pathnames = dataFile.list();
    }

    private String prepareFile(String fileFolder) {
        return RELATIVE_DIR + fileFolder.replace("\\",FILE_SEPARATOR).replace("/",FILE_SEPARATOR);
    }

    public ArrayList<Anime> readAnimeDatabase() throws IOException {
        ArrayList<Anime> animeDataList = new ArrayList<>();
        for (String pathname : pathnames) {
            FileReader fileData = new FileReader(dataFile.getPath() + FILE_SEPARATOR
                    + pathname);
            //System.out.println(fileData);
            parseJson(animeDataList, fileData);
        }
        return animeDataList;
    }

    private void parseJson(ArrayList<Anime> animeDataList,FileReader  fileData) {
        JSONParser parser = new JSONParser();
        JSONArray jsonList = new JSONArray();
        try {
            jsonList = (JSONArray) parser.parse(fileData);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        Iterator iterator = jsonList.iterator();
        JSONObject jsonObject = new JSONObject();
        while (iterator.hasNext()) {
            String animeName;
            int animeEpisode = 0;
            int animeRating = 0;
            int animeDuration = 0;

            //Advance the Iterator
            jsonObject = (JSONObject) iterator.next();
            jsonObject = (JSONObject) jsonObject.get("data");
            jsonObject = (JSONObject) jsonObject.get("Media");

            //Getting anime name
            JSONObject jsonTitle = (JSONObject) jsonObject.get("title");
            if (jsonTitle.get("english") == null) {
                animeName = (String) jsonTitle.get("romaji");
            } else {
                animeName = (String) jsonTitle.get("english");
            }

            //getting anime episode

            if (jsonObject.get("episodes") != null) {
                animeEpisode =  (int) (long) jsonObject.get("episodes");
            }

            //getting start date
            JSONObject jsonDate = (JSONObject) jsonObject.get("startDate");
            String[] animeReleaseDate;
            animeReleaseDate = new String[] { String.valueOf((long) jsonDate.get("year")),
                    String.valueOf((long) jsonDate.get("month")), String.valueOf((long) jsonDate.get("day"))};

            //getting rating
            if (jsonObject.get("averageScore") != null) {
                animeRating =  (int) (long) jsonObject.get("averageScore");
            }

            //getting genre
            ArrayList<String> animeGenre;
            animeGenre = new ArrayList<>();
            JSONArray jsonGenre = (JSONArray) jsonObject.get("genres");
            for (Object genre : jsonGenre) {
                animeGenre.add((String) genre);
            }
            String[] animeGenreArray;
            animeGenreArray = new String[animeGenre.size()];
            animeGenreArray = animeGenre.toArray(animeGenreArray);

            //getting duration
            if (jsonObject.get("duration") != null) {
                animeDuration =  (int) (long) jsonObject.get("duration");
            }

            Anime anime = new Anime(animeName, animeReleaseDate, animeRating, animeGenreArray, animeDuration,
                    animeEpisode);
            animeDataList.add(anime);
        }
    }
}