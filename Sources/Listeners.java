import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
        if (!Window.settingsIsOpen) {
            Window.settingsIsOpen = true;
            final JFrame settingsDialog = new JFrame("Settings");
            JTextField tagPointer = new JTextField("Tag (only one, VERY SLOW): ");
            final JTextField tag = new JTextField(Window.tag);
            JPanel mainPanel = new JPanel();
            JPanel smallButtonsPanel = new JPanel();
            JPanel tagPanel = new JPanel();
            Checkbox hdCheckBox = new Checkbox("HD nya only");
            SimpleButton closeButton = new SimpleButton("resources/closeNya.png");
            ActionListener closeSettings = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    settingsDialog.dispose();
                    Window.settingsIsOpen = false;
                }
            };

            if (Window.hdOnly)
                hdCheckBox.setState(true);
            else
                hdCheckBox.setState(false);

            hdCheckBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (Window.hdOnly == true)
                        Window.hdOnly = false;
                    else
                        Window.hdOnly = true;
                }
            });
            closeButton.addActionListener(closeSettings);

            hdCheckBox.setBackground(Color.WHITE);

            tag.setPreferredSize(new Dimension(100, 20));
            tag.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    if (tag.getText().length() == 0) {
                        Window.useTag = false;
                        System.out.println("it works");
                    }
                    else{
                        Window.useTag = true;
                        Window.tag = tag.getText();
                        System.out.println(Window.tag);
                    }
                }
            });

            smallButtonsPanel.setLayout(new BoxLayout(smallButtonsPanel, BoxLayout.X_AXIS));
            smallButtonsPanel.add(closeButton);
            smallButtonsPanel.setBackground(Color.WHITE);

            tagPanel.setLayout(new FlowLayout());
            tagPanel.setBackground(Color.WHITE);
            tagPanel.add(tagPointer);
            tagPanel.add(tag);

            tagPointer.setBackground(Color.WHITE);
            tagPointer.setDisabledTextColor(Color.BLACK);
            tagPointer.setEnabled(false);

            mainPanel.setBackground(Color.WHITE);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(hdCheckBox);
            mainPanel.add(tagPanel);
            mainPanel.add(smallButtonsPanel);

            settingsDialog.add(mainPanel);
            settingsDialog.setUndecorated(true);
            settingsDialog.pack();
            settingsDialog.setLocation(Window.screenSize.width / 2 - settingsDialog.getSize().width / 2, Window.screenSize.height / 2 - settingsDialog.getSize().height);
            settingsDialog.setVisible(true);
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