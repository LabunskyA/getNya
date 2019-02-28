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

            int read;
            char[] chars = new char[2048];

            while ((read = inputStreamReader.read(chars)) > 0)
                stringBuffer.append(chars, 0, read);

            inputStream.close();
            inputStreamReader.close();
        } catch (IOException e) {
            return "";
        }

        if (type == TAG ) {
            String result = stringBuffer.toString();
            if (result.contains("<ul id=\"tags\">")) {
                result = result.substring(stringBuffer.toString().indexOf("<ul id=\"tags\">"));
                result = result.substring(0, result.indexOf("/ul")).toLowerCase();
            }

            return result;
        }

        return stringBuffer.toString().toLowerCase();
    }
}
