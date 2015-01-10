import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Lina on 21.11.2014.
 */
public class Solution implements ActionListener {
    static protected Window getNya = new Window();

    public static void main(String[] args) throws IOException {
        Zerochan.getNumberOfTheNyas();
        getNya.addWindowStateListener(new WindowStateListener());
        getNya.drawNya();
        getNya.setVisible(true);
        getNya.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            getNya.drawNya();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}