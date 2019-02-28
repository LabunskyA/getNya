package window.listeners;

import window.Window;
import zerochan.Zerochan;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class Save2File extends NyaListener implements ActionListener {
    private File lastSave = null;
    private Boolean firstTime = true;

    public Save2File(Window getNya) {
        super(getNya);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if (firstTime) {
                ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("nyaPath.path"));
                lastSave = (File) objectIn.readObject();

                objectIn.close();

                firstTime = false;
            }
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e2){
            e2.printStackTrace();
        }

        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG file", "png", "PNG");
        JFileChooser fileChooser = new JFileChooser();


        if(lastSave != null)
            fileChooser = new JFileChooser(lastSave);
        fileChooser.setSelectedFile(new File("Zerochan.net - " + Zerochan.numberNya + ".png"));
        fileChooser.setFileFilter(filter);

        if (fileChooser.showSaveDialog(getNya) == JFileChooser.APPROVE_OPTION) {
            try {
                if (lastSave != fileChooser.getSelectedFile()) {
                    lastSave = fileChooser.getSelectedFile();
                    FileOutputStream fileOut = new FileOutputStream("nyaPath.path");
                    ObjectOutputStream prefOut = new ObjectOutputStream(fileOut);
                    prefOut.writeObject(lastSave);
                    prefOut.close();
                }

                ImageIO.write(getNya.getFullImage(), "png", lastSave);
            } catch (IOException e1) {
                System.out.println("Something went wrong =(");
            }
        }
    }
}
