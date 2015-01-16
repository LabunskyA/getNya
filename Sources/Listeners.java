import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

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

class GetPrevNya implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        URL temp[] = {Zerochan.nyaURL, Zerochan.fullURL};
        Zerochan.nyaURL = Zerochan.prevURL;
        Zerochan.prevURL = temp[0];
        Zerochan.fullURL = temp[1];

        try {
            Solution.getNya.draw();
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
    Integer positionX = 0;
    Integer positionY = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Window.settingsIsOpen) {
            Window.settingsIsOpen = true;

            final JFrame settingsDialog = new JFrame("Settings");
            final JTextField tag = new JTextField(Window.tag);
            final JTextField widthScan = new JTextField(Integer.toString((int) Window.customResolution.getWidth()));
            final JTextField heightScan = new JTextField(Integer.toString((int) Window.customResolution.getHeight()));

            JTextField tagPointer = new JTextField(" Tag (only one, could works slowly): ");
            JTextField width = new JTextField(" Width: ");
            JTextField height = new JTextField(" Height: ");

            JPanel mainPanel = new JPanel();
            JPanel hdCheckBoxPanel = new JPanel();
            JPanel smallButtonsPanel = new JPanel();
            JPanel tagPanel = new JPanel();
            JPanel sizePanel = new JPanel();
            JPanel buttonPanel = new JPanel();

            Checkbox hdCheckBox = new Checkbox("HD nya only");

            SimpleButton closeButton = new SimpleButton("resources/closeNya.png");
            SimpleButton prevNya = new SimpleButton("resources/prevNya.png", "resources/prevNyaPressed.png");

            ActionListener getPrevNya = new GetPrevNya();
            DocumentListener documentListener = new DocumentListener() {
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
                    } else {
                        Window.useTag = true;
                        Window.tag = tag.getText();
                    }
                }
            };
            DocumentListener customWidthListener = new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {warn();}
                @Override
                public void removeUpdate(DocumentEvent e) {warn();}
                @Override
                public void changedUpdate(DocumentEvent e) {warn();}

                public void warn() {
                    if (widthScan.getText().length() == 0) {
                        Window.currentResolution = false;
                        Window.customResolution = new Dimension(0, (int) Window.customResolution.getHeight());
                    } else {
                        Window.currentResolution = true;
                        Window.customResolution = new Dimension(Integer.valueOf(widthScan.getText()), (int) Window.customResolution.getHeight());
                    }
                }
            };
            DocumentListener customHeightListener = new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {warn();}
                @Override
                public void removeUpdate(DocumentEvent e) {warn();}
                @Override
                public void changedUpdate(DocumentEvent e) {warn();}

                public void warn() {
                    if (heightScan.getText().length() == 0) {
                        Window.currentResolution = false;
                        Window.customResolution = new Dimension((int) Window.customResolution.getWidth(), 0);
                    } else {
                        Window.currentResolution = true;
                        Window.customResolution = new Dimension((int) Window.customResolution.getWidth(), Integer.valueOf(heightScan.getText()));
                    }
                }
            };
            MouseAdapter dragDialogListener = new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    super.mouseDragged(e);
                    settingsDialog.setLocation(e.getXOnScreen() - positionX, e.getYOnScreen() - positionY);
                }
            };
            MouseAdapter mouseMoveListener = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    positionX = e.getX();
                    positionY = e.getY();
                }
            };
            ActionListener closeSettings = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    settingsDialog.dispose();
                    Window.settingsIsOpen = false;
                }
            };

            hdCheckBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    Window.hdOnly = !Window.hdOnly;
                }
            });
            mainPanel.addMouseListener(mouseMoveListener);
            mainPanel.addMouseMotionListener(dragDialogListener);
            closeButton.addActionListener(closeSettings);
            prevNya.addActionListener(getPrevNya);
            tag.getDocument().addDocumentListener(documentListener);
            widthScan.getDocument().addDocumentListener(customWidthListener);
            heightScan.getDocument().addDocumentListener(customHeightListener);

            tag.setPreferredSize(new Dimension(100, 20));
            heightScan.setPreferredSize(new Dimension(50, 20));
            widthScan.setPreferredSize(new Dimension(50, 20));

            hdCheckBox.setBackground(Color.WHITE);
            hdCheckBoxPanel.setLayout(new FlowLayout());
            hdCheckBoxPanel.setBackground(Color.WHITE);
            hdCheckBoxPanel.add(hdCheckBox);

            sizePanel.setBackground(Color.WHITE);
            sizePanel.setLayout(new FlowLayout());
            sizePanel.add(width);
            sizePanel.add(widthScan);
            sizePanel.add(height);
            sizePanel.add(heightScan);

            smallButtonsPanel.setLayout(new BoxLayout(smallButtonsPanel, BoxLayout.X_AXIS));
            smallButtonsPanel.add(Box.createRigidArea(new Dimension(277, 0)));
            smallButtonsPanel.add(closeButton);
            smallButtonsPanel.setBackground(Color.WHITE);

            tagPanel.setLayout(new FlowLayout());
            tagPanel.setBackground(Color.WHITE);
            tagPanel.add(tagPointer);
            tagPanel.add(tag);

            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(prevNya);

            tagPointer.setBackground(Color.WHITE);
            tagPointer.setDisabledTextColor(Color.BLACK);
            tagPointer.setEnabled(false);

            height.setBackground(Color.WHITE);
            height.setDisabledTextColor(Color.BLACK);
            height.setEnabled(false);

            width.setBackground(Color.WHITE);
            width.setDisabledTextColor(Color.BLACK);
            width.setEnabled(false);

            mainPanel.setBackground(Color.WHITE);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(hdCheckBoxPanel);
            mainPanel.add(tagPanel);
            mainPanel.add(sizePanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(smallButtonsPanel);

            settingsDialog.add(mainPanel);
            settingsDialog.setUndecorated(true);
            settingsDialog.setAlwaysOnTop(true);
            settingsDialog.pack();
            settingsDialog.setLocation(Solution.getNya.getX() + Solution.getNya.getWidth() / 2 - settingsDialog.getSize().width / 2, Solution.getNya.getY() + Solution.getNya.getHeight() / 2 - settingsDialog.getSize().height);
            settingsDialog.setVisible(true);

            if (Window.hdOnly)
                hdCheckBox.setState(true);
            else
                hdCheckBox.setState(false);
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
