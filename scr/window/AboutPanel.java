package window;

import javax.swing.*;
import java.awt.*;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
@SuppressWarnings("WeakerAccess")
public class AboutPanel extends JPanel {
    AboutPanel() {
        super();
        setVisible(false);

        JTextArea aboutNya = new JTextArea("Simple Zerochan.net image downloader.\n" +
                "Use ' ' (space) as logical and in the text field.\n" +
                "Use 'or' as logical or in text field.\n" +
                "Use '>' and '<' before size in height and width\n " +
                "fields to specify nya size.\n\nMade by Labunsky Artem");

        aboutNya.setEnabled(false);
        aboutNya.setBackground(Color.WHITE);
        aboutNya.setFont(Font.getFont("Open Sans"));
        aboutNya.setDisabledTextColor(Color.BLACK);

        setLayout(new BorderLayout());
        add(aboutNya, BorderLayout.CENTER);
    }
}
