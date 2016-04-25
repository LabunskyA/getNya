package window.listeners;

import window.Window;

import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class WindowStateListener extends NyaListener implements java.awt.event.WindowStateListener {
    public WindowStateListener(Window getNya) {
        super(getNya);
    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        if (e.getOldState() != e.getNewState() && e.getOldState() != Frame.NORMAL && e.getOldState() != Frame.ICONIFIED)
            getNya.setWindowSizeNormal(true);
        else if(e.getOldState() == Frame.NORMAL && e.getNewState() == Frame.MAXIMIZED_BOTH)
            getNya.setWindowSizeNormal(false);
    }
}
