package window;

import window.listeners.*;
import zerochan.Checker;
import zerochan.LittleParser;
import zerochan.Zerochan;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by LabunskyA
 * Protected with GNU GPLv2 and your honesty
 */
public class Window extends JFrame {
    private Boolean currentResolution = false;

    private Boolean moreThanX = false;
    private Boolean lessThanX = false;

    private Boolean moreThanY = false;
    private Boolean lessThanY = false;

    private Boolean settingsIsOpen = false;
    private Boolean aboutIsOpen = false;

    private Boolean useTag = false;

    private Integer positionX;
    private Integer positionY;

    private String tag = "";
    private Boolean hdOnly = false;

    private final SettingsPanel settingsPanel = new SettingsPanel(this);
    private final AboutPanel aboutPanel = new AboutPanel();

    private final LittleParser littleParser = new LittleParser();

    public Dimension customResolution = new Dimension(0, 0);
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

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

    public BufferedImage getBufferedFullImage() {
        return bufferedFullImage;
    }

    public Window() { //Here i will create main window of the program
        super("getNya");

        try {
            setIconImage(new ImageIcon(ImageIO.read(new File("resources/Nya.png"))).getImage());
        } catch (IOException ignored) {}

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ActionListener saveFullNya = new Save2File(this);
        ActionListener getNewNya = new GetNewNya(this);
        ActionListener exitNya = new CloseNya();
        ActionListener maximizeNyaWindow = new MaximizeNya(this);
        ActionListener minimizeNyaWindow = new MinimizeNya(this);
        ActionListener nyaSettings = new ServicePanelListener(this, settingsPanel, ServicePanelListener.Panel.SETTINGS);
        ActionListener aboutDialog = new ServicePanelListener(this, aboutPanel, ServicePanelListener.Panel.ABOUT);

        MouseMoveListener mouseMove = new MouseMoveListener(this);
        MouseMotionListener mouseDrag = new MouseDragListener(this);

        getNya = new SimpleButton("resources/getNya.png", "resources/getNyaPressed.png");
        JToggleButton saveNya = new SimpleButton("resources/saveNya.png", "resources/saveNyaPressed.png");
        JToggleButton closeNya = new SimpleButton("resources/closeNya.png");
        JToggleButton maximizeNya = new SimpleButton("resources/maximizeNya.png");
        JToggleButton minimizeNya = new SimpleButton("resources/minimizeNya.png");
        JToggleButton nyaPrefs = new SimpleButton("resources/nyaPrefs.png");
        JToggleButton nyaAbout = new SimpleButton("resources/nyaAbout.png");

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
        settingsButtonsPanel.add(Box.createRigidArea(new Dimension(1, 0)));
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
        buttonsPanel.setPreferredSize(new Dimension(buttonsPanel.getWidth(), 41));

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

    public void drawNya() throws MalformedURLException {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Boolean check = true;
        Checker checker = new Checker(this);

        Zerochan.prevFull = Zerochan.fullURL;
        Zerochan.prevURL = Zerochan.nyaURL;

        while (check) {
            while (check) { //generate at least one time
                Zerochan.generateNumberNya();
                Zerochan.generateURLs();

                check = checker.CheckTag(littleParser);
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

    public void draw() {
        try {
            Robot mouseMover = new Robot();

            bufferedNyaImage = ImageIO.read(Zerochan.nyaURL);

            Integer nyaImageHeight = bufferedNyaImage.getHeight();
            Integer nyaImageWidth = bufferedNyaImage.getWidth();

            Dimension maximumSizeForTheFistArea;
            Dimension maximumSizeForTheSecondArea;
            Dimension minimumWindowSize = new Dimension(297, 0); //just buttons size

            Integer maxContentPaneHeight = nyaImageHeight + 41 + dataField.getHeight();

            //this on is for small screens, less then 720p
            if (screenSize.height < maxContentPaneHeight + Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom || screenSize.width < nyaImageWidth) {
                Image scaledImage = bufferedNyaImage.getScaledInstance(-1, screenSize.height - 41 - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom, Image.SCALE_FAST);

                nyaLabel.setIcon(new ImageIcon(scaledImage));

                nyaImageHeight = toBufferedImage(scaledImage).getHeight();
                nyaImageWidth = toBufferedImage(scaledImage).getWidth();
            } else if (getExtendedState() == Frame.NORMAL)
                nyaLabel.setIcon(new ImageIcon(bufferedNyaImage));
            else if (nyaFullHeight < screenSize.height - 41 - dataField.getHeight() && nyaFullWidth < screenSize.width - 10) {
                nyaLabel.setIcon(new ImageIcon(bufferedFullImage));
                nyaImageHeight = bufferedFullImage.getHeight();
                nyaImageWidth = bufferedFullImage.getWidth();
            } else { //mmm, FULLSCREEN
                BufferedImage fullscreenNya = bufferedFullImage;
                if (nyaFullHeight >= screenSize.height - dataField.getHeight() - 41)
                    fullscreenNya = toBufferedImage(fullscreenNya.getScaledInstance(-1, screenSize.height - 41 - dataField.getHeight(), Image.SCALE_SMOOTH));

                if (fullscreenNya.getWidth() >= screenSize.width)
                    fullscreenNya = toBufferedImage(fullscreenNya.getScaledInstance(screenSize.width, -1, Image.SCALE_SMOOTH));

                nyaLabel.setIcon(new ImageIcon(fullscreenNya));
                nyaImageHeight = fullscreenNya.getHeight();
                nyaImageWidth = fullscreenNya.getWidth();
            }

            if (getExtendedState() == Frame.NORMAL && (nyaImageWidth - 206) / 2 - 48 >= 0) {
                maximumSizeForTheFistArea = new Dimension((nyaImageWidth - 206) / 2 - 32, 41);
                maximumSizeForTheSecondArea = new Dimension((nyaImageWidth - 206) / 2 - 48, 41);
            } else if ((nyaImageWidth - 206) / 2 - 41 >= 0){
                maximumSizeForTheFistArea = new Dimension((screenSize.width - 206) / 2 - 32, 41);
                maximumSizeForTheSecondArea = new Dimension((screenSize.width - 206) / 2 - 32, 41);
            } else {
                maximumSizeForTheFistArea = new Dimension(0, 0);
                maximumSizeForTheSecondArea = new Dimension(0, 0);
            }

            dataField.setText("Width: " + nyaFullWidth + " Height: " + nyaFullHeight);
            dataField.setMaximumSize(new Dimension(nyaImageWidth, dataField.getHeight()));
            northRigidArea.setMaximumSize(new Dimension(nyaImageWidth,
                                                (screenSize.height - nyaImageHeight - dataField.getHeight() - 41) / 2));
            buttonsPanelFirstRigidArea.setMaximumSize(maximumSizeForTheFistArea);
            buttonsPaneSecondRigidArea.setMaximumSize(maximumSizeForTheSecondArea);
            setMinimumSize(minimumWindowSize);

            //this will not allow window to set it's width to settings panel width + image width
            if (settingsIsOpen)
                settingsPanel.setVisible(false);
            if (aboutIsOpen)
                aboutPanel.setVisible(false);

            if (getExtendedState() == Frame.MAXIMIZED_BOTH) {
                setMinimumSize(getSize());
                pack();
                setMinimumSize(null);
            } else pack();

            settingsPanel.setVisible(settingsIsOpen);
            aboutPanel.setVisible(aboutIsOpen);

            setCursor(Cursor.getDefaultCursor());

            if (getExtendedState() == Frame.NORMAL)
                mouseMover.mouseMove(getNya.getX() + getX() + 50, getY() + nyaImageHeight + 40); //102 is the width of buttons
        } catch (IOException | AWTException ignored) {}

        System.gc();
    }

    public void setWindowSizeNormal(Boolean normal) { //I need it to resize window every time when it changes state from maximized to normal
        if (normal) {
            setSize(bufferedNyaImage.getWidth(), bufferedNyaImage.getHeight() + buttonsPanel.getHeight() + dataField.getHeight());
            nyaLabel.setIcon(new ImageIcon(bufferedNyaImage));
        } else draw();
    }

    private BufferedImage toBufferedImage(Image img){
        if (img instanceof BufferedImage) {
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

    /**
     * Getters and setters section
     */
    public Boolean getCurrentResolution() {
        return currentResolution;
    }

    void setCurrentResolution(Boolean currentResolution) {
        this.currentResolution = currentResolution;
    }

    public Boolean getMoreThanX() {
        return moreThanX;
    }

    void setMoreThanX(Boolean moreThanX) {
        this.moreThanX = moreThanX;
    }

    public Boolean getLessThanX() {
        return lessThanX;
    }

    void setLessThanX(Boolean lessThanX) {
        this.lessThanX = lessThanX;
    }

    public Boolean getMoreThanY() {
        return moreThanY;
    }

    void setMoreThanY(Boolean moreThanY) {
        this.moreThanY = moreThanY;
    }

    public Boolean getLessThanY() {
        return lessThanY;
    }

    void setLessThanY(Boolean lessThanY) {
        this.lessThanY = lessThanY;
    }

    public Boolean getAboutIsOpen() {
        return aboutIsOpen;
    }

    public void setAboutIsOpen(Boolean aboutIsOpen) {
        this.aboutIsOpen = aboutIsOpen;
    }

    public Boolean getUseTag() {
        return useTag;
    }

    void setUseTag(Boolean useTag) {
        this.useTag = useTag;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public String getTag() {
        return tag;
    }

    void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean isHdOnly() {
        return hdOnly;
    }

    void setHdOnly(Boolean hdOnly) {
        this.hdOnly = hdOnly;
    }

    public Boolean getSettingsIsOpen() {
        return settingsIsOpen;
    }

    public void setSettingsIsOpen(boolean settingsIsOpen) {
        this.settingsIsOpen = settingsIsOpen;
    }
}

