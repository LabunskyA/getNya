package zerochan;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class LittleParser {
    static final char MAIN_PAGE = 0;
    static final char TAG = 1;

    String parse(String urlString, char type) throws MalformedURLException {
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
