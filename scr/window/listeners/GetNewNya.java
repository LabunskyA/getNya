package window.listeners;

import window.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class GetNewNya extends NyaListener implements ActionListener {
    public GetNewNya(Window getNya) {
        super(getNya);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            getNya.drawNya();
        } catch (IOException ignored) {}
    }
}
