package window.listeners;

import window.Window;
import zerochan.Zerochan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class GetPrevNya extends NyaListener implements ActionListener {
    public GetPrevNya(Window getNya) {
        super(getNya);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        URL temp[] = {Zerochan.nyaURL, Zerochan.fullURL};

        Zerochan.nyaURL = Zerochan.prevURL;
        Zerochan.prevURL = temp[0];
        Zerochan.fullURL = Zerochan.prevFull;
        Zerochan.prevFull = temp[1];

        getNya.draw();
    }
}
