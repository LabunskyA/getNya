import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Created by Lina on 21.11.2014.
 */
public class Solution implements ActionListener {
    static protected Window getNya = new Window();

    public static void main(String[] args) throws IOException {
        getNya.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (e.getOldState() != e.getNewState() && e.getOldState() != Frame.NORMAL)
                    getNya.setWindowSize();
            }
        });

        Zerochan.getNumberOfTheNyas();
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