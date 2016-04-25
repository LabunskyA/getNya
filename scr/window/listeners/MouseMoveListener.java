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
        getNya.setPositionX(e.getX());
        getNya.setPositionY(e.getY());
    }

    /**
     * Not used interface methods
     * */
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
