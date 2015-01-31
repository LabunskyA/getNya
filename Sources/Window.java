import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by LabunskyA
 * Protected with GNU GPLv2 and your honesty
 */
class Window extends JFrame {
    static Boolean hdOnly = false;
    static Boolean currentResolution = false;
    static Boolean moreThanX = false;
    static Boolean lessThanX = false;
    static Boolean moreThanY = false;
    static Boolean lessThanY = false;
    static Boolean settingsIsOpen = false;
    static Boolean aboutIsOpen = false;
    static Boolean useTag = false;
    static String tag = "";
    static Integer positionX;
    static Integer positionY;
    static Dimension customResolution = new Dimension(0, 0);
    static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static JPanel settingsPanel = new SettingsPanel();
    static JPanel aboutPanel = new AboutPanel();

    private BufferedImage bufferedNyaImage;
    private BufferedImage bufferedFullImage;
    private Integer nyaFullHeight;
    private Integer nyaFullWidth;
    private JLabel nyaLabel = new JLabel();
    private final JTextField dataField;
    private final JPanel buttonsPanel = new JPanel();
    private final SimpleButton getNya;
    private final Component northRigidArea = Box.createRigidArea(new Dimension(0, 0));
    private final Component buttonsPanelFirstRigidArea = Box.createRigidArea(new Dimension(0, 0));
    private final Component buttonsPaneSecondRigidArea = Box.createRigidArea(new Dimension(0, 0));

    BufferedImage getBufferedFullImage() {
        return bufferedFullImage;
    }

    Window() {
        super("getNya");

        try {
            setIconImage(new ImageIcon(ImageIO.read(getClass().getResource("resources/Nya.png"))).getImage());
        } catch (IOException ignored) {}

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ActionListener saveFullNya = new Save2File();
        ActionListener getNewNya = new GetNewNya();
        ActionListener exitNya = new CloseNya();
        ActionListener maximizeNyaWindow = new MaximizeNya();
        ActionListener minimizeNyaWindow = new MinimizeNya();
        ActionListener nyaSettings = new Settings();
        ActionListener aboutDialog = new About();

        MouseAdapter mouseMove = new MouseMoveListener();
        MouseAdapter mouseDrag = new MouseDragListener();

        getNya = new SimpleButton("resources/getNya.png", "resources/getNyaPressed.png");
        SimpleButton saveNya = new SimpleButton("resources/saveNya.png", "resources/saveNyaPressed.png");
        SimpleButton closeNya = new SimpleButton("resources/closeNya.png");
        SimpleButton maximizeNya = new SimpleButton("resources/maximizeNya.png");
        SimpleButton minimizeNya = new SimpleButton("resources/minimizeNya.png");
        SimpleButton nyaPrefs = new SimpleButton("resources/nyaPrefs.png");
        SimpleButton nyaAbout = new SimpleButton("resources/nyaAbout.png");

        JPanel centralPanel = new JPanel();
        JPanel smallButtonsPanel = new JPanel();
        JPanel panelForSmallButtonsPanel = new JPanel();
        JPanel settingsButtonsPanel = new JPanel();
        JPanel panelForSettingsPanel = new JPanel();
        JPanel settingsAndAboutPanel = new JPanel();
        nyaLabel = new JLabel();
        dataField = new JTextField();

        nyaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        addMouseListener(mouseMove);
        addMouseMotionListener(mouseDrag);

        getNya.addActionListener(getNewNya);
        saveNya.addActionListener(saveFullNya);
        closeNya.addActionListener(exitNya);
        maximizeNya.addActionListener(maximizeNyaWindow);
        minimizeNya.addActionListener(minimizeNyaWindow);
        nyaPrefs.addActionListener(nyaSettings);
        nyaAbout.addActionListener(aboutDialog);

        settingsButtonsPanel.setLayout(new BoxLayout(settingsButtonsPanel, BoxLayout.X_AXIS));
        settingsButtonsPanel.setBackground(Color.WHITE);
        settingsButtonsPanel.add(nyaAbout);
        settingsButtonsPanel.add(nyaPrefs);

        panelForSettingsPanel.setLayout(new BoxLayout(panelForSettingsPanel, BoxLayout.Y_AXIS));
        panelForSettingsPanel.setBackground(Color.WHITE);
        panelForSettingsPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        panelForSettingsPanel.add(settingsButtonsPanel);

        smallButtonsPanel.setLayout(new BoxLayout(smallButtonsPanel, BoxLayout.X_AXIS));
        smallButtonsPanel.add(minimizeNya);
        smallButtonsPanel.add(maximizeNya);
        smallButtonsPanel.add(closeNya);
        smallButtonsPanel.setBackground(Color.WHITE);

        panelForSmallButtonsPanel.setLayout(new BoxLayout(panelForSmallButtonsPanel, BoxLayout.Y_AXIS));
        panelForSmallButtonsPanel.setBackground(Color.WHITE);
        panelForSmallButtonsPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        panelForSmallButtonsPanel.add(smallButtonsPanel);

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(panelForSettingsPanel);
        buttonsPanel.add(buttonsPanelFirstRigidArea);
        buttonsPanel.add(getNya);
        buttonsPanel.add(Box.createRigidArea(new Dimension(3, 0)));
        buttonsPanel.add(saveNya);
        buttonsPanel.add(buttonsPaneSecondRigidArea);
        buttonsPanel.add(panelForSmallButtonsPanel);
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setPreferredSize(new Dimension(buttonsPanel.getWidth(), 48));

        dataField.setBackground(Color.BLACK);
        dataField.setHorizontalAlignment(SwingConstants.CENTER);
        dataField.setDisabledTextColor(Color.WHITE);
        dataField.setEnabled(false);
        dataField.setBorder(null);

        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.add(northRigidArea);
        centralPanel.add(nyaLabel);
        centralPanel.add(dataField);
        centralPanel.setBackground(Color.WHITE);

        settingsAndAboutPanel.setLayout(new BorderLayout());
        settingsAndAboutPanel.add(settingsPanel, BorderLayout.NORTH);
        settingsAndAboutPanel.add(aboutPanel, BorderLayout.SOUTH);
        settingsAndAboutPanel.setBackground(Color.WHITE);

        add(settingsAndAboutPanel, BorderLayout.WEST);
        add(centralPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setBackground(Color.WHITE);

        setResizable(false);
        setUndecorated(true);

        pack();
    }

    void drawNya() throws MalformedURLException {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Boolean check = true;
        Checker checker = new Checker();
        LittleParser littleParser = new LittleParser();

        while (check) {
            while (check) { //generate at least one time
                Zerochan.generateNumberNya();
                Zerochan.generateURLs();

                check = checker.CheckTag(littleParser, true);
            }
            try {
                bufferedFullImage = ImageIO.read(Zerochan.fullURL);
                nyaFullHeight = bufferedFullImage.getHeight();
                nyaFullWidth = bufferedFullImage.getWidth();

                check = checker.CheckSize(nyaFullWidth, nyaFullHeight, check);
            } catch (IOException e) { check = true; } //sometimes Zerochan.net blocks some pictures, so I need to handle this occasion and get another url with another picture

        }
        draw();
    }

    void draw() {
        try {
            Robot mouseMover = new Robot();
            bufferedNyaImage = ImageIO.read(Zerochan.nyaURL);
            Integer nyaImageHeight = bufferedNyaImage.getHeight();
            Integer nyaImageWidth = bufferedNyaImage.getWidth();
            Dimension maximumSizeForTheFistArea;
            Dimension maximumSizeForTheSecondArea;
            Dimension minimumWindowSize = new Dimension(297, 0); //just buttons size
            Integer maxContentPaneHeight = nyaImageHeight + 48 + dataField.getHeight();

            //this on is for small screens, less then 720p
            if (screenSize.height < maxContentPaneHeight + Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom || screenSize.width < nyaImageWidth) {
                Image scaledImage = bufferedNyaImage.getScaledInstance(screenSize.height - 48 - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom, -1, Image.SCALE_FAST);

                nyaLabel.setIcon(new ImageIcon(scaledImage));

                nyaImageHeight = toBufferedImage(scaledImage).getHeight();
                nyaImageWidth = toBufferedImage(scaledImage).getWidth();
            } else if (getExtendedState() == Frame.NORMAL)
                nyaLabel.setIcon(new ImageIcon(bufferedNyaImage));
            else if (nyaFullHeight < screenSize.height - 48 - dataField.getHeight() && nyaFullWidth < screenSize.width - 10) {
                nyaLabel.setIcon(new ImageIcon(bufferedFullImage));
                nyaImageHeight = bufferedFullImage.getHeight();
                nyaImageWidth = bufferedFullImage.getWidth();
            }
            else { //mmm, FULLSCREEN
                if (nyaFullHeight >= screenSize.height - dataField.getHeight() - 48)
                    bufferedFullImage = toBufferedImage(bufferedFullImage.getScaledInstance(-1, screenSize.height - 48 - dataField.getHeight(), Image.SCALE_SMOOTH));

                if (bufferedFullImage.getWidth() >= screenSize.width)
                    bufferedFullImage = toBufferedImage(bufferedFullImage.getScaledInstance(screenSize.width, -1, Image.SCALE_SMOOTH));

                nyaLabel.setIcon(new ImageIcon(bufferedFullImage));
                nyaImageHeight = bufferedFullImage.getHeight();
                nyaImageWidth = bufferedFullImage.getWidth();
            }

            if (getExtendedState() == Frame.NORMAL && (nyaImageWidth - 206) / 2 - 48 >= 0) {
                maximumSizeForTheFistArea = new Dimension((nyaImageWidth - 206) / 2 - 32, 48);
                maximumSizeForTheSecondArea = new Dimension((nyaImageWidth - 206) / 2 - 48, 48);
            } else if ((nyaImageWidth - 206) / 2 - 48 >= 0){
                maximumSizeForTheFistArea = new Dimension((screenSize.width - 206) / 2 - 32, 48);
                maximumSizeForTheSecondArea = new Dimension((screenSize.width - 206) / 2 - 32, 48);
            } else {
                maximumSizeForTheFistArea = new Dimension(0, 0);
                maximumSizeForTheSecondArea = new Dimension(0, 0);
            }

            dataField.setText("Width: " + nyaFullWidth + " Height: " + nyaFullHeight);
            dataField.setMaximumSize(new Dimension(nyaImageWidth, dataField.getHeight()));
            northRigidArea.setMaximumSize(new Dimension(nyaImageWidth, (screenSize.height - nyaImageHeight - dataField.getHeight() - 48) / 2));
            buttonsPanelFirstRigidArea.setMaximumSize(maximumSizeForTheFistArea);
            buttonsPaneSecondRigidArea.setMaximumSize(maximumSizeForTheSecondArea);
            setMinimumSize(minimumWindowSize);

            //this will not allow window to set it's width to settings panel width + image width
            if (settingsIsOpen)
                settingsPanel.setVisible(!settingsIsOpen);
            if (aboutIsOpen)
                aboutPanel.setVisible(!aboutIsOpen);

            if (getExtendedState() == Frame.MAXIMIZED_BOTH) {
                setMinimumSize(getSize());
                pack();
                setMinimumSize(null);
            } else
                pack();

            settingsPanel.setVisible(settingsIsOpen);
            aboutPanel.setVisible(aboutIsOpen);

            setCursor(Cursor.getDefaultCursor());

            if (getExtendedState() == Frame.NORMAL)
                mouseMover.mouseMove(getNya.getX() + getX() + 50, getY() + nyaImageHeight + 40); //102 is the width of buttons
        } catch (IOException | AWTException ignored) {}
    }

    void setWindowSizeNormal(Boolean normal) { //I need it to resize window every time when it changes state from maximized to normal
        if (normal) {
            setSize(bufferedNyaImage.getWidth(), bufferedNyaImage.getHeight() + buttonsPanel.getHeight() + dataField.getHeight());
            nyaLabel.setIcon(new ImageIcon(bufferedNyaImage));
        }
        else//if maximized
            draw();
    }

    private BufferedImage toBufferedImage(Image img){
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}

class SimpleButton extends JToggleButton {
    public SimpleButton(String path, String pressedPath) {
        super();
        try {
            setIcon(new ImageIcon(ImageIO.read(getClass().getResource(path))));
            setPressedIcon(new ImageIcon(ImageIO.read(getClass().getResource(pressedPath))));
            setMargin(new Insets(0, 0, 0, 0));
            setBorder(BorderFactory.createEmptyBorder());
            setBackground(Color.WHITE);
        } catch (IOException ignored) {}
    }

    public SimpleButton(String path) {
        super();
        try {
            setIcon(new ImageIcon(ImageIO.read(getClass().getResource(path))));
            setPressedIcon(new ImageIcon(ImageIO.read(getClass().getResource(path.substring(0, path.indexOf(".")) + "Pressed.png"))));
            setMargin(new Insets(0, 0, 0, 0));
            setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, false));
            setBackground(Color.WHITE);
        } catch (IOException ignored) {}
    }
}

class SettingsPanel extends JPanel {
    static final JTextField widthScan = new JTextField();
    static final JTextField heightScan = new JTextField();
    static final JTextField tag = new JTextField();

    SettingsPanel() {
        super();

        setVisible(false);

        final JTextField width = new JTextField(" Width: ");
        final JTextField height = new JTextField(" Height: ");

        String width4Scan = "";
        String height4Scan = "";

        JTextField tagPointer = new JTextField(" Tag (could works slowly): ");
        JTextField closeToSave = new JTextField("Close settings to save");

        JPanel hdCheckBoxPanel = new JPanel();
        JPanel tagPanel = new JPanel();
        JPanel sizePanel = new JPanel();
        JPanel prevNyaPanel = new JPanel();
        JPanel closeToSavePanel = new JPanel();

        Checkbox hdCheckBox = new Checkbox("HD nya only");

        SimpleButton prevNya = new SimpleButton("resources/prevNya.png", "resources/prevNyaPressed.png");

        ActionListener getPrevNya = new GetPrevNya();

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

        hdCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Window.hdOnly = !Window.hdOnly;
            }
        });
        prevNya.addActionListener(getPrevNya);

        height.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        width.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        tag.setText(Window.tag);
        tag.setPreferredSize(new Dimension(100, 20));
        tag.setBackground(Color.WHITE);
        tag.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        closeToSave.setBackground(Color.WHITE);
        closeToSave.setBorder(BorderFactory.createEmptyBorder());

        heightScan.setText(height4Scan);
        heightScan.setPreferredSize(new Dimension(50, 20));
        heightScan.setBackground(Color.WHITE);
        heightScan.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        widthScan.setText(width4Scan);
        widthScan.setPreferredSize(new Dimension(50, 20));
        widthScan.setBackground(Color.WHITE);
        widthScan.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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

        closeToSavePanel.setLayout(new FlowLayout());
        closeToSavePanel.setBackground(Color.WHITE);
        closeToSavePanel.add(closeToSave);

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
        add(closeToSavePanel);

        setBackground(Color.WHITE);

        if (Window.hdOnly)
            hdCheckBox.setState(true);
        else
            hdCheckBox.setState(false);
    }
}

class AboutPanel extends JPanel{
    AboutPanel(){
        super();
        setVisible(false);

        JTextArea aboutNya = new JTextArea("Simple Zerochan.net image downloader.\nClose settings to save fields state\nUse '+' to search for multiple words tag.\nUse ' ' (space) as logical and in the text field.\nUse 'or' as logical or in text field.\nUse '>' and '<' before size in height and width\n fields to specify nya size.\n\nMade by Labunsky Artem");
        SimpleButton closeAbout = new SimpleButton("resources/closeNya.png");

        aboutNya.setEnabled(false);
        aboutNya.setBackground(Color.WHITE);
        aboutNya.setFont(Font.getFont("Open Sans"));
        aboutNya.setDisabledTextColor(Color.BLACK);

        setLayout(new BorderLayout());
        add(aboutNya, BorderLayout.CENTER);
    }
}