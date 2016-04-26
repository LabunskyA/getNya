package window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class WelcomeLogo extends JFrame {
    public WelcomeLogo() {
        BufferedImage logo = null;
        try {
            logo = ImageIO.read(new File("resources/getNya.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel logoImage = new JLabel();

        assert logo != null;
        logoImage.setIcon(new ImageIcon(logo.getScaledInstance(300, -1, Image.SCALE_FAST)));

        add(logoImage);
        setUndecorated(true);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }
}
