import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by LabunskyA.
 * Protected with GNU GPLv2 and your honesty
 */
class Dialogs{
    //just name
}

class SettingsDialog {
    private Integer positionX = 0;
    private Integer positionY = 0;

    public void ShowSettingsDialog(){
        Window.settingsIsOpen = true;

        final JFrame settingsDialog = new JFrame("Settings");
        final JTextField width = new JTextField(" Width: ");
        final JTextField height = new JTextField(" Height: ");
        final JTextField tag = new JTextField(Window.tag);

        String width4Scan = "";
        String height4Scan = "";
        if (Window.lessThanX)
            width4Scan = "<";
        else if (Window.moreThanX)
            width4Scan = ">";
        if (Window.moreThanY)
            height4Scan = ">";
        else if (Window.lessThanY)
            height4Scan = "<";
        if (Window.customResolution.width != 0)
            width4Scan = width4Scan + Integer.toString(Window.customResolution.width);
        if (Window.customResolution.height != 0)
            height4Scan = height4Scan + Integer.toString(Window.customResolution.height);
        final JTextField widthScan = new JTextField(width4Scan);
        final JTextField heightScan = new JTextField(height4Scan);

        JTextField tagPointer = new JTextField(" Tag (could works slowly): ");

        JPanel mainPanel = new JPanel();
        JPanel hdCheckBoxPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        JPanel tagPanel = new JPanel();
        JPanel sizePanel = new JPanel();
        JPanel prevNyaPanel = new JPanel();
        JPanel closePanel = new JPanel();

        Checkbox hdCheckBox = new Checkbox("HD nya only");

        SimpleButton closeButton = new SimpleButton("resources/closeNya.png");
        SimpleButton prevNya = new SimpleButton("resources/prevNya.png", "resources/prevNyaPressed.png");

        ActionListener getPrevNya = new GetPrevNya();

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
                String value = widthScan.getText();
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

                value = heightScan.getText();
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

                if (tag.getText().length() == 0) {
                    Window.useTag = false;
                    Window.tag = "";
                } else {
                    Window.useTag = true;
                    Window.tag = tag.getText().toLowerCase();
                }

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

        tagPanel.setLayout(new FlowLayout());
        tagPanel.setBackground(Color.WHITE);
        tagPanel.add(tagPointer);
        tagPanel.add(tag);

        prevNyaPanel.setLayout(new FlowLayout());
        prevNyaPanel.setBackground(Color.WHITE);
        prevNyaPanel.add(prevNya);

        closePanel.setLayout(new BoxLayout(closePanel, BoxLayout.Y_AXIS));
        closePanel.setBackground(Color.WHITE);
        closePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        closePanel.add(closeButton);

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(Box.createRigidArea(new Dimension(16, 0)));
        buttonsPanel.add(prevNyaPanel);
        buttonsPanel.add(closePanel);
        buttonsPanel.setBackground(Color.WHITE);

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
        mainPanel.add(buttonsPanel);

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

class AboutDialog{
    void ShowAbout(){
        final JFrame about = new JFrame("About");

        JPanel southPanel = new JPanel();
        JTextField madeBy = new JTextField("Made by Labunsky Artem");
        JTextArea aboutNya = new JTextArea("Simple Zerochan.net image downloader.\nUse '+' to search for multiple word tag.\nUse ' ' (space) to search for more than one tag.\nUse || as logical or in text field.\nUse '>' and '<' before size in height and width fields to specify nya size.");
        SimpleButton closeAbout = new SimpleButton("resources/closeNya.png");
        ActionListener closeDialog = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                about.dispose();
            }
        };

        closeAbout.addActionListener(closeDialog);

        madeBy.setEnabled(false);
        madeBy.setFont(Font.getFont("Open Sans"));
        madeBy.setDisabledTextColor(Color.BLACK);
        madeBy.setBackground(Color.WHITE);
        madeBy.setBorder(BorderFactory.createEmptyBorder());

        southPanel.setBackground(Color.WHITE);
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.add(Box.createRigidArea(new Dimension(2, 0)));
        southPanel.add(madeBy);
        southPanel.add(closeAbout);

        aboutNya.setEnabled(false);
        aboutNya.setBackground(Color.WHITE);
        aboutNya.setFont(Font.getFont("Open Sans"));
        aboutNya.setDisabledTextColor(Color.BLACK);
        about.add(aboutNya, BorderLayout.CENTER);
        about.add(southPanel, BorderLayout.SOUTH);

        about.setUndecorated(true);
        about.pack();
        about.setLocation(Solution.getNya.getX() + Solution.getNya.getWidth() / 2 - about.getSize().width / 2, Solution.getNya.getY() + Solution.getNya.getHeight() / 2 - about.getSize().height);
        about.setVisible(true);
        System.out.println(madeBy.getWidth());
    }
}