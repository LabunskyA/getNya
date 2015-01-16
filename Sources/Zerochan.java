import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Created by Lina on 21.11.2014.
 */

public class Zerochan {
    protected static Integer numberOfTheNyas;
    protected static Integer numberNya;
    protected static URL prevURL;
    protected static URL nyaURL;
    protected static URL fullURL;

    public static void getNumberOfTheNyas() {
        try {
            LittleParser littleParser = new LittleParser();
            String result = littleParser.parse("http://Zerochan.net");

            int index = result.indexOf("240.");
            String stringNumber = result.substring(index + 4, index + 10);
            //4 is length of "240." and there is only ~1.8 million of images on zerochan, so we need only 6 digits
            numberOfTheNyas =  Integer.parseInt(stringNumber);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected static void generateNumberNya() {
        Random randomNumberNya = new Random();
        numberNya = randomNumberNya.nextInt(numberOfTheNyas + 1);
    }

    protected static void generateURLs() {
        try {
            prevURL = nyaURL;
            nyaURL = new URL("http://s1.zerochan.net/.600." + Integer.toString(numberNya) + ".jpg");
            fullURL = new URL("http://static.zerochan.net/.full." + Integer.toString(numberNya) + ".jpg");
        } catch (MalformedURLException ignored) {}
    }
}

class LittleParser {
    public String parse(String urlString) throws MalformedURLException {
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

        return stringBuffer.toString();
    }
}