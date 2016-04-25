package window.listeners;

import window.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class About extends NyaListener implements ActionListener {
    public About(Window getNya) {
        super(getNya);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!getNya.aboutIsOpen) {
            getNya.aboutPanel.setVisible(true);
            getNya.aboutIsOpen = !getNya.aboutIsOpen;
        } else {
            getNya.aboutPanel.setVisible(false);
            getNya.aboutIsOpen = !getNya.aboutIsOpen;
        }
    }
}
