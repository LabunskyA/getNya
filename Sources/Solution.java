import javax.swing.*;
import java.net.MalformedURLException;

/**
 * Created by Lina on 21.11.2014.
 */
public class Solution {
    static protected Window getNya = new Window();

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        Zerochan.getNumberOfTheNyas();
        getNya.addWindowStateListener(new WindowStateListener());
        getNya.drawNya();
        getNya.setVisible(true);
        getNya.pack();
    }
}