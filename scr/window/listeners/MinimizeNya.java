package window.listeners;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class MinimizeNya extends NyaListener implements ActionListener {
    public MinimizeNya(window.Window getNya) {
        super(getNya);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getNya.setExtendedState(Frame.ICONIFIED);
    }
}
