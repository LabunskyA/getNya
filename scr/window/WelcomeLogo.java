package window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class WelcomeLogo extends JFrame {
    public WelcomeLogo() {
        try {
            setIconImage(new ImageIcon(ImageIO.read(getClass().getResource("resources/Nya.png"))).getImage());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        BufferedImage logo = null;
        try {
            logo = ImageIO.read(getClass().getResource("resources/getNya.png"));
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
