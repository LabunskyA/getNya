package window.listeners;

import window.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class Settings extends NyaListener implements ActionListener {
    public Settings(Window getNya) {
        super(getNya);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!getNya.settingsIsOpen) {
            getNya.settingsPanel.setVisible(true);
            getNya.settingsIsOpen = true;
        } else {
            getNya.settingsPanel.setVisible(false);
            getNya.settingsIsOpen = false;
        }
    }
}
