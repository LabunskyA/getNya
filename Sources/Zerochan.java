import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Created by LabunskyA
 * Protected with GNU GPLv2 and your honesty
 */
class Zerochan {
    private static Integer numberOfTheNyas;
    static Integer numberNya;
    static URL prevURL;
    static URL nyaURL;
    static URL fullURL;

    public static void getNumberOfTheNyas() {
        try {
            LittleParser littleParser = new LittleParser();
            String result = littleParser.parse("http://Zerochan.net", LittleParser.MAIN_PAGE);

            int index = result.indexOf("240.");
            String stringNumber = result.substring(index + 4, index + 11);
            //4 is length of "240." and there is only ~1.8 million of images on zerochan, so we need only 6 digits
            numberOfTheNyas =  Integer.parseInt(stringNumber);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    static void generateNumberNya() {
        Random randomNumberNya = new Random();
        numberNya = randomNumberNya.nextInt(numberOfTheNyas + 1);
    }

    static void generateURLs() {
        try {
            prevURL = nyaURL;
            nyaURL = new URL("http://s1.zerochan.net/.600." + Integer.toString(numberNya) + ".jpg");
            fullURL = new URL("http://static.zerochan.net/.full." + Integer.toString(numberNya) + ".jpg");
        } catch (MalformedURLException ignored) {}
    }
}

class LittleParser {
    static final char MAIN_PAGE = 0;
    static final char TAG = 1;

    public String parse(String urlString, char type) throws MalformedURLException {
        URL url = new URL(urlString);
        StringBuilder stringBuffer = new StringBuilder();

        try {
            InputStream inputStream = url.openConnection().getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int numCharsRead;
            char[] charArray = new char[1024];

            while ((numCharsRead = inputStreamReader.read(charArray)) > 0) {
                stringBuffer.append(charArray, 0, numCharsRead);
            }
            inputStream.close();
            inputStreamReader.close();
        } catch (IOException e) {
            return "";
        }

        if (type == TAG ) {
            String result = "";
            if (stringBuffer.toString().contains("<ul id=\"tags\">")) {
                result = stringBuffer.toString().substring(stringBuffer.toString().indexOf("<ul id=\"tags\">"));
                result = result.substring(0, result.indexOf("/ul")).toLowerCase();
            }

            return result;
        }

        return stringBuffer.toString().toLowerCase();
    }
}