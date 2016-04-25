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

    public boolean CheckTag(LittleParser littleParser, Boolean check) throws MalformedURLException {
        if (getNya.useTag) {
            String[] or = getNya.tag.split("or ");

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
        if (getNya.hdOnly && (nyaWidth < 1920 || nyaHeight < 1080))
            check = true;

        if (getNya.currentResolution) {
            if (getNya.moreThanY && nyaHeight < getNya.customResolution.height)
                check = true;
            if (getNya.lessThanY && nyaHeight > getNya.customResolution.height)
                check = true;
            if (getNya.moreThanX && nyaWidth < getNya.customResolution.width)
                check = true;
            if (getNya.lessThanX && nyaWidth > getNya.customResolution.width)
                check = true;

            if (!getNya.moreThanY && !getNya.moreThanX && !getNya.lessThanY && !getNya.lessThanX && (
                            (nyaHeight != getNya.customResolution.height && nyaHeight != 0) ||
                                    (nyaWidth != getNya.customResolution.width && nyaWidth != 0)
            ))
                check = true;
        }

        return check;
    }
}
