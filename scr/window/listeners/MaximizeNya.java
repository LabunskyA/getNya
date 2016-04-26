package window.listeners;

import window.Window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class MaximizeNya extends NyaListener implements ActionListener {
    public MaximizeNya(Window getNya) {
        super(getNya);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (getNya.getExtendedState() == Frame.NORMAL)
            getNya.setExtendedState(Frame.MAXIMIZED_BOTH);
        else getNya.setExtendedState(Frame.NORMAL);
    }
}
