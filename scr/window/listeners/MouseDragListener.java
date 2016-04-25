package window.listeners;

import window.Window;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class MouseDragListener extends NyaListener implements MouseMotionListener {
    public MouseDragListener(Window getNya) {
        super(getNya);
    }

    public void mouseDragged(MouseEvent e) {
        if (getNya.getExtendedState() == Frame.NORMAL)
            getNya.setLocation(e.getXOnScreen() - getNya.positionX, e.getYOnScreen() - getNya.positionY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
