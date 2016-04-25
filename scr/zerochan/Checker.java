package zerochan;

import window.Window;

import java.net.MalformedURLException;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class Checker {
    private final Window getNya;

    public Checker(Window getNya) {
        this.getNya = getNya;
    }

    public boolean CheckTag(LittleParser littleParser) throws MalformedURLException {
        Boolean check = true;

        if (getNya.getUseTag()) {
            String[] or = getNya.getTag().split("or ");

            for (String tags : or) {
                Integer tagNumContains = 0;
                String[] splitedTags = tags.split(" "); //for more than one tag
                for (String tag : splitedTags) {
                    String parseResult = littleParser.parse("http://www.zerochan.net/" +
                                                                            Zerochan.numberNya, LittleParser.TAG);
                    if (tag.length() > 0 && (parseResult.contains(">" + tag) ||
                            parseResult.contains(tag + " ") || parseResult.contains(tag + "<"))) //fixed?
                        tagNumContains++;
                }

                if (tagNumContains == splitedTags.length)
                    check = false;
            }
        } else check = false;

        return check;
    }

    public boolean CheckSize(Integer nyaWidth, Integer nyaHeight, Boolean check) {
        if (getNya.isHdOnly() && (nyaWidth < 1920 || nyaHeight < 1080))
            check = true;

        if (getNya.getCurrentResolution()) {
            check = check ||
                    (getNya.getMoreThanY() && nyaHeight < getNya.customResolution.height) ||
                    (getNya.getLessThanY() && nyaHeight > getNya.customResolution.height) ||
                    (getNya.getMoreThanX() && nyaWidth < getNya.customResolution.width) ||
                    (getNya.getLessThanX() && nyaWidth > getNya.customResolution.width);

            if (!getNya.getMoreThanY() && !getNya.getMoreThanX() && !getNya.getLessThanY() && !getNya.getLessThanX() &&
                    ((nyaHeight != getNya.customResolution.height && nyaHeight != 0) ||
                                    (nyaWidth != getNya.customResolution.width && nyaWidth != 0))
                                                                                                    )
                check = true;
        }

        return check;
    }
}
