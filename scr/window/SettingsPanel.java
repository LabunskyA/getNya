package window;

import window.listeners.GetPrevNya;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
@SuppressWarnings("WeakerAccess")
public class SettingsPanel extends JPanel {
    private final JTextField widthScan = new JTextField();
    private final JTextField heightScan = new JTextField();
    private final JTextField tag = new JTextField();

    SettingsPanel(final Window getNya) {
        super();

        setVisible(false);

        final JTextField width = new JTextField(" Width: ");
        final JTextField height = new JTextField(" Height: ");

        String width4Scan = "";
        String height4Scan = "";

        JTextField tagPointer = new JTextField(" Tag (could works slowly): ");

        JPanel hdCheckBoxPanel = new JPanel();
        JPanel tagPanel = new JPanel();
        JPanel sizePanel = new JPanel();
        JPanel prevNyaPanel = new JPanel();

        Checkbox hdCheckBox = new Checkbox("HD nya only");

        SimpleButton prevNya = new SimpleButton("resources/prevNya.png", "resources/prevNyaPressed.png");

        ActionListener getPrevNya = new GetPrevNya(getNya);

        hdCheckBox.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                getNya.setHdOnly(!getNya.isHdOnly());
            }
        });

        prevNya.addActionListener(getPrevNya);

        height.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        width.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        tag.setText(getNya.getTag());
        tag.setPreferredSize(new Dimension(100, 20));
        tag.setBackground(Color.WHITE);
        tag.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
                    getNya.setUseTag(false);
                    getNya.setTag("");
                } else {
                    getNya.setUseTag(true);
                    getNya.setTag(tag.getText().toLowerCase());
                }
            }
        });

        heightScan.setText(height4Scan);
        heightScan.setPreferredSize(new Dimension(50, 20));
        heightScan.setBackground(Color.WHITE);
        heightScan.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        heightScan.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {warn();}
            @Override
            public void removeUpdate(DocumentEvent e) {warn();}
            @Override
            public void changedUpdate(DocumentEvent e) {warn();}

            public void warn() {
                String value = heightScan.getText();
                if (value.length() == 0 || value.length() == 1 && (value.contains(">") || value.contains("<"))) {
                    if (widthScan.getText().length() == 0)
                        getNya.setCurrentResolution(false);

                    getNya.setMoreThanY(false);
                    getNya.setLessThanY(false);

                    getNya.customResolution = new Dimension(getNya.customResolution.width, 0);
                } else {
                    getNya.setCurrentResolution(true);

                    if (value.contains(">")) {
                        getNya.setMoreThanY(true);
                        value = value.substring(1);
                    } else getNya.setMoreThanY(false);

                    if (value.contains("<")) {
                        getNya.setLessThanY(true);
                        value = value.substring(1);
                    } else getNya.setLessThanY(false);

                    getNya.customResolution = new Dimension(getNya.customResolution.width, Integer.valueOf(value));
                }
            }
        });
        heightScan.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( (c < '0' || c > '9') && c != '>' && c != '<' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();  // ignore event
                }
            }
        });

        widthScan.setText(width4Scan);
        widthScan.setPreferredSize(new Dimension(50, 20));
        widthScan.setBackground(Color.WHITE);
        widthScan.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        widthScan.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {warn();}
            @Override
            public void removeUpdate(DocumentEvent e) {warn();}
            @Override
            public void changedUpdate(DocumentEvent e) {warn();}

            void warn() {
                String value = widthScan.getText();

                if (value.length() == 0 || value.length() == 1 && (value.contains(">") || value.contains("<"))) {
                    if (heightScan.getText().length() == 0)
                        getNya.setCurrentResolution(false);

                    getNya.setMoreThanX(false);
                    getNya.setLessThanX(false);

                    getNya.customResolution = new Dimension(0, getNya.customResolution.height);
                } else {
                    getNya.setCurrentResolution(true);

                    if (value.contains(">")) {
                        getNya.setMoreThanX(true);
                        value = value.substring(1);
                    } else getNya.setMoreThanX(false);

                    if (value.contains("<")) {
                        getNya.setLessThanX(true);
                        value = value.substring(1);
                    } else getNya.setLessThanX(false);

                    getNya.customResolution = new Dimension(Integer.valueOf(value), getNya.customResolution.height);
                }
            }
        });
        widthScan.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( (c < '0' || c > '9') && c != '>' && c != '<' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();  // ignore event
                }
            }
        });

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

        tagPointer.setBackground(Color.WHITE);
        tagPointer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tagPointer.setDisabledTextColor(Color.BLACK);
        tagPointer.setEnabled(false);

        height.setBackground(Color.WHITE);
        height.setDisabledTextColor(Color.BLACK);
        height.setEnabled(false);

        width.setBackground(Color.WHITE);
        width.setDisabledTextColor(Color.BLACK);
        width.setEnabled(false);

        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(hdCheckBoxPanel);
        add(tagPanel);
        add(sizePanel);
        add(prevNyaPanel);

        setBackground(Color.WHITE);

        if (getNya.isHdOnly())
            hdCheckBox.setState(true);
        else hdCheckBox.setState(false);
    }
}
