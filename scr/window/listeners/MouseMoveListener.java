package window.listeners;

import window.Window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class MouseMoveListener extends NyaListener implements MouseListener {
    public MouseMoveListener(Window getNya) {
        super(getNya);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        getNya.positionX = e.getX();
        getNya.positionY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
