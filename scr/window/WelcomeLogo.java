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
    public WelcomeLogo() throws IOException {
        setIconImage(new ImageIcon(ImageIO.read(getClass().getResource("resources/Nya.png"))).getImage());

        try {
            BufferedImage logo = ImageIO.read(getClass().getResource("resources/getNya.png"));

            JLabel logoImage = new JLabel();
            logoImage.setIcon(new ImageIcon(logo.getScaledInstance(300, -1, Image.SCALE_FAST)));

            add(logoImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setUndecorated(true);
        setVisible(true);

        pack();
        setLocationRelativeTo(null);
    }
}
