import javax.swing.*;
import java.net.MalformedURLException;

/**
 * Created by LabunskyA
 * Protected with GNU GPLv2 and your honesty
 */
class Solution {
    static final Window getNya = new Window();
    static final WelcomeLogo welcomeLogo = new WelcomeLogo();

    public static void main(String[] args) throws MalformedURLException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        Zerochan.getNumberOfTheNyas();
        getNya.addWindowStateListener(new WindowStateListener());
        getNya.drawNya();
        getNya.setVisible(true);
        welcomeLogo.setVisible(false);
        getNya.pack();
        getNya.setLocationRelativeTo(null);
    }
}