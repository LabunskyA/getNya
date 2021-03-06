import window.WelcomeLogo;
import window.Window;
import window.listeners.WindowStateListener;
import zerochan.Zerochan;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by LabunskyA
 * Protected with GNU GPLv2 and your honesty
 */
class Executable {
    public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException, ClassNotFoundException {
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