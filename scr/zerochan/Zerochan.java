package zerochan;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Created by LabunskyA
 * Protected with GNU GPLv2 and your honesty
 */
public class Zerochan {
    private static Integer numberOfTheNyas;
    public static Integer numberNya;
    public static URL prevURL;
    public static URL prevFull;
    public static URL nyaURL;
    public static URL fullURL;

    public static void getNumberOfTheNyas() {
        try {
            LittleParser littleParser = new LittleParser();
            String result = littleParser.parse("https://zerochan.net", LittleParser.MAIN_PAGE);

            int index = result.indexOf("240.");
            String stringNumber = result.substring(index + 4, index + 11);
            //4 is length of "240." and there is only ~1.8 million of images on zerochan, so we need only 6 digits
            numberOfTheNyas =  Integer.parseInt(stringNumber);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void generateNumberNya() {
        Random randomNumberNya = new Random();
        numberNya = randomNumberNya.nextInt(numberOfTheNyas + 1);
    }

    public static void generateURLs() {
        try {
            nyaURL = new URL("https://s1.zerochan.net/.600." + numberNya + ".jpg");
            fullURL = new URL("https://static.zerochan.net/.full." + numberNya + ".jpg");
        } catch (MalformedURLException ignored) {}
    }
}

