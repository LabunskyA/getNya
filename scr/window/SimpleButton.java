package window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
class SimpleButton extends JToggleButton {
    SimpleButton(String path, String pressedPath) {
        super();

        try {
            setIcon(new ImageIcon(ImageIO.read(new File(path))));
            setPressedIcon(new ImageIcon(ImageIO.read(new File(pressedPath))));
            setMargin(new Insets(0, 0, 0, 0));
            setBorder(BorderFactory.createEmptyBorder());
            setBackground(Color.WHITE);
        } catch (IOException ignored) {}
    }

    SimpleButton(String path) {
        super();
        try {
            setIcon(new ImageIcon(ImageIO.read(new File(path))));
            setPressedIcon(new ImageIcon(ImageIO.read(new File(path.substring(0, path.indexOf(".")) + "Pressed.png"))));
            setMargin(new Insets(0, 0, 0, 0));
            setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, false));
            setBackground(Color.WHITE);
        } catch (IOException ignored) {}
    }
}
