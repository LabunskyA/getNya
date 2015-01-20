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

class Checker {
    public boolean CheckTag(LittleParser littleParser, Boolean check) throws MalformedURLException {
        if (Window.useTag) {
            String[] tags = Window.tag.split(" "); //for more than one tag
            Integer tagNumContains = 0;

            for (String tag : tags)
                if (littleParser.parse("http://www.zerochan.net/" + Zerochan.numberNya, LittleParser.TAG).contains(tag))
                    tagNumContains++;

            if (tagNumContains == tags.length)
                check = false;
        } else check = false;

        return check;
    }

    public boolean CheckSize(Integer nyaWidth, Integer nyaHeight, Boolean check) {
        if (Window.hdOnly && (nyaWidth < 1920 || nyaHeight < 1080))
            check = true;

        if (Window.currentResolution) {
            if (Window.moreThanY && nyaHeight < Window.customResolution.height)
                check = true;
            if (Window.lessThanY && nyaHeight > Window.customResolution.height)
                check = true;
            if (Window.moreThanX && nyaWidth < Window.customResolution.width)
                check = true;
            if (Window.lessThanX && nyaWidth > Window.customResolution.width)
                check = true;

            if (!Window.moreThanY && !Window.moreThanX && !Window.lessThanY && !Window.lessThanX && (nyaHeight != Window.customResolution.height || nyaWidth != Window.customResolution.width))
                check = true;
        }

        return check;
    }
}