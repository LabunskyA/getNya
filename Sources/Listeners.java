import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

/**
 * Created by LabunskyA
 * Protected with GNU GPLv2 and your honesty
 */

class Listeners {
    //I just want to use this name for .java
}

class Save2File implements ActionListener{
    private File lastSave = null;
    private Boolean firstTime = true;

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if (firstTime) {
                FileInputStream fileIn = new FileInputStream("nyaPath.path");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                lastSave = (File) objectIn.readObject();
                fileIn.close();
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

        if (fileChooser.showSaveDialog(Solution.getNya) == JFileChooser.APPROVE_OPTION) {
            try{
                if (lastSave != fileChooser.getSelectedFile()) {
                    lastSave = fileChooser.getSelectedFile();
                    FileOutputStream fileOut = new FileOutputStream("nyaPath.path");
                    ObjectOutputStream prefOut = new ObjectOutputStream(fileOut);
                    prefOut.writeObject(lastSave);
                    prefOut.close();
                }

                ImageIO.write(Solution.getNya.getBufferedFullImage(), "png", lastSave);
            }catch (IOException e1) {
                System.out.println("Something went wrong =(");
            }
        }
    }
}

class CloseNya implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}

class GetNewNya implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Solution.getNya.drawNya();
        } catch (IOException ignored) {}
    }
}

class GetPrevNya implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        URL temp[] = {Zerochan.nyaURL, Zerochan.fullURL};
        Zerochan.nyaURL = Zerochan.prevURL;
        Zerochan.prevURL = temp[0];
        Zerochan.fullURL = Zerochan.prevFull;
        Zerochan.prevFull = temp[1];

        Solution.getNya.draw();
    }
}

class MaximizeNya implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Solution.getNya.getExtendedState() == Frame.NORMAL)
            Solution.getNya.setExtendedState(Frame.MAXIMIZED_BOTH);
        else
            Solution.getNya.setExtendedState(Frame.NORMAL);
    }
}

class MinimizeNya implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        Solution.getNya.setExtendedState(Frame.ICONIFIED);
    }
}

class Settings implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Window.settingsIsOpen) {
            Window.settingsPanel.setVisible(true);
            Window.settingsIsOpen = true;
        } else {
            String value = SettingsPanel.widthScan.getText();
            if (value.length() == 0) {
                Window.currentResolution = false;
                Window.moreThanX = false;
                Window.lessThanX = false;
                Window.customResolution = new Dimension(0, Window.customResolution.height);
            } else {
                Window.currentResolution = true;
                if (value.contains(">")) {
                    Window.moreThanX = true;
                    value = value.substring(1);
                }
                else Window.moreThanX = false;
                if (value.contains("<")) {
                    Window.lessThanX = true;
                    value = value.substring(1);
                }
                else Window.lessThanX = false;
                Window.customResolution = new Dimension(Integer.valueOf(value), Window.customResolution.height);
            }

            value = SettingsPanel.heightScan.getText();
            if (value.length() == 0) {
                Window.currentResolution = false;
                Window.moreThanY = false;
                Window.lessThanY = false;
                Window.customResolution = new Dimension(Window.customResolution.width, 0);
            } else {
                Window.currentResolution = true;
                if (value.contains(">")) {
                    Window.moreThanY = true;
                    value = value.substring(1);
                }
                else Window.moreThanY = false;
                if (value.contains("<")) {
                    Window.lessThanY = true;
                    value = value.substring(1);
                }
                else Window.lessThanY = false;
                Window.customResolution = new Dimension(Window.customResolution.width, Integer.valueOf(value));
            }

            if (SettingsPanel.tag.getText().length() == 0) {
                Window.useTag = false;
                Window.tag = "";
            } else {
                Window.useTag = true;
                Window.tag = SettingsPanel.tag.getText().toLowerCase();
            }

            Window.settingsPanel.setVisible(false);
            Window.settingsIsOpen = false;
        }
    }
}

class About implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Window.aboutIsOpen) {
            Window.aboutPanel.setVisible(true);
            Window.aboutIsOpen = !Window.aboutIsOpen;
        }
        else {
            Window.aboutPanel.setVisible(false);
            Window.aboutIsOpen = !Window.aboutIsOpen;
        }
    }
}

class MouseMoveListener extends MouseAdapter {
    public void mousePressed(MouseEvent e){
        Window.positionX = e.getX();
        Window.positionY = e.getY();
    }
}

class MouseDragListener extends MouseAdapter {
    public void mouseDragged(MouseEvent e) {
        if (Solution.getNya.getExtendedState() == Frame.NORMAL)
            Solution.getNya.setLocation(e.getXOnScreen() - Window.positionX, e.getYOnScreen() - Window.positionY);
    }
}

class WindowStateListener implements java.awt.event.WindowStateListener{
    @Override
    public void windowStateChanged(WindowEvent e) {
        if (e.getOldState() != e.getNewState() && e.getOldState() != Frame.NORMAL && e.getOldState() != Frame.ICONIFIED)
            Solution.getNya.setWindowSizeNormal(true);
        else if(e.getOldState() == Frame.NORMAL && e.getNewState() == Frame.MAXIMIZED_BOTH)
            Solution.getNya.setWindowSizeNormal(false);
    }
}
