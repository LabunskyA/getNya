import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Created by Lina on 31.12.2014.
 */
public class Listeners implements ActionListener {
    private File lastSave = null;
    private Boolean firstTime = true;

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if (firstTime) {
                FileInputStream fileIn = new FileInputStream("nyaPrefs.bin");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                lastSave = (File) objectIn.readObject();
                fileIn.close();
            }
        } catch (IOException e1) {
        } catch (ClassNotFoundException e2){
            e2.printStackTrace();
        }

        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG file", "png", "PNG");
        JFileChooser fileChooser = new JFileChooser();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {}

        if(lastSave != null)
            fileChooser = new JFileChooser(lastSave);
        fileChooser.setSelectedFile(new File("Zerochan.net - " + Zerochan.numberNya + ".png"));
        fileChooser.setFileFilter(filter);

        if (fileChooser.showSaveDialog(Solution.getNya) == JFileChooser.APPROVE_OPTION) {
            try{
                if (lastSave != fileChooser.getSelectedFile()) {
                    lastSave = fileChooser.getSelectedFile();
                    FileOutputStream fileOut = new FileOutputStream("nyaPrefs.bin");
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

class getNewNya implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Solution.getNya.drawNya();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
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
        final JFrame settingsDialog = new JFrame("Settings");
        JTextField inDevelopment = new JTextField("Coming soon =)");
        JPanel smallButtonsPanel = new JPanel();
        SimpleButton closeButton = new SimpleButton("resources/closeNya.png");
        ActionListener closeSettings = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsDialog.dispose();
            }
        };

        closeButton.addActionListener(closeSettings);

        smallButtonsPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        smallButtonsPanel.add(closeButton);
        smallButtonsPanel.setBackground(Color.WHITE);

        inDevelopment.setBackground(Color.WHITE);
        inDevelopment.setDisabledTextColor(Color.BLACK);
        inDevelopment.setEnabled(false);

        settingsDialog.setBackground(Color.WHITE);
        settingsDialog.add(smallButtonsPanel, BorderLayout.SOUTH);
        settingsDialog.add(inDevelopment, BorderLayout.CENTER);
        settingsDialog.setUndecorated(true);
        settingsDialog.pack();
        settingsDialog.setLocation(Window.screenSize.width / 2 - settingsDialog.getSize().width / 2, Window.screenSize.height / 2 - settingsDialog.getSize().height);
        settingsDialog.setVisible(true);
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