import window.WelcomeLogo;
import window.Window;
import window.listeners.WindowStateListener;
import zerochan.Zerochan;

import javax.swing.*;
import java.net.MalformedURLException;

/**
 * Created by LabunskyA
 * Protected with GNU GPLv2 and your honesty
 */
class Solution {
    public static void main(String[] args) throws MalformedURLException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Window getNya = new Window();
        WelcomeLogo welcomeLogo = new WelcomeLogo();

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        Zerochan.getNumberOfTheNyas();

        getNya.addWindowStateListener(new WindowStateListener(getNya));
        getNya.drawNya();

        welcomeLogo.setVisible(false);
        getNya.setVisible(true);

        getNya.pack();
        getNya.setLocationRelativeTo(null);
    }
}