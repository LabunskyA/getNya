import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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
    static Boolean useTag = false;
    static String tag = "";
    static Integer positionX;
    static Integer positionY;
    static Dimension customResolution = new Dimension(0, 0);
    static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

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
        JPanel settingsPanel = new JPanel();
        JPanel panelForSettingsPanel = new JPanel();
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

        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.X_AXIS));
        settingsPanel.setBackground(Color.WHITE);
        settingsPanel.add(nyaAbout);
        settingsPanel.add(nyaPrefs);

        panelForSettingsPanel.setLayout(new BoxLayout(panelForSettingsPanel, BoxLayout.Y_AXIS));
        panelForSettingsPanel.setBackground(Color.WHITE);
        panelForSettingsPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        panelForSettingsPanel.add(settingsPanel);

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

        getContentPane().add(centralPanel, BorderLayout.CENTER);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        getContentPane().setBackground(Color.WHITE);

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
            Dimension maximumSizeForTheFistArea = new Dimension((nyaImageWidth - 206) / 2 - 32, 48);
            Dimension maximumSizeForTheSecondArea = new Dimension((nyaImageWidth - 206) / 2 - 48, 48);
            Dimension minimumWindowSize = new Dimension(297, 0); //just buttons size
            Integer maxContentPaneHeight = nyaImageHeight + 48 + dataField.getHeight();

            //this on is for small screens, less then 720p
            if (screenSize.height < maxContentPaneHeight || screenSize.width < nyaImageWidth) {
                Image scaledImage = bufferedNyaImage.getScaledInstance(screenSize.height - 48 - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom, -1, Image.SCALE_FAST);
                nyaLabel.setIcon(new ImageIcon(scaledImage));
                nyaImageHeight = ((BufferedImage) scaledImage).getHeight();
                nyaImageWidth = ((BufferedImage) scaledImage).getWidth();
            } else if (getExtendedState() == Frame.NORMAL)
                nyaLabel.setIcon(new ImageIcon(bufferedNyaImage));
            else if (nyaFullHeight < screenSize.height && nyaFullWidth < screenSize.getWidth())
                nyaLabel.setIcon(new ImageIcon(bufferedFullImage));
            else { //mmm, FULLSCREEN
                BufferedImage scaledImage = null;
                if (nyaFullHeight < screenSize.height)
                    scaledImage = toBufferedImage(bufferedFullImage.getScaledInstance(screenSize.width, -1, Image.SCALE_SMOOTH));
                else
                    scaledImage = toBufferedImage(bufferedFullImage.getScaledInstance(-1, (int) screenSize.height - 48 - dataField.getHeight(), Image.SCALE_SMOOTH));
                nyaLabel.setIcon(new ImageIcon(scaledImage));
                nyaImageHeight = scaledImage.getHeight();
                nyaImageWidth = scaledImage.getWidth();
            }

            dataField.setText("Width: " + nyaFullWidth + " Height: " + nyaFullHeight);
            dataField.setMaximumSize(new Dimension(nyaImageWidth, dataField.getHeight()));
            northRigidArea.setMaximumSize(new Dimension(nyaImageWidth, (screenSize.height - maxContentPaneHeight) / 2));
            buttonsPanelFirstRigidArea.setMaximumSize(maximumSizeForTheFistArea);
            buttonsPaneSecondRigidArea.setMaximumSize(maximumSizeForTheSecondArea);
            setMinimumSize(minimumWindowSize);

            if (getExtendedState() == Frame.MAXIMIZED_BOTH) {
                setMinimumSize(getSize());
                pack();
                setMinimumSize(null);
            } else
                pack();

            setCursor(Cursor.getDefaultCursor());

            if (getExtendedState() == Frame.NORMAL && !settingsIsOpen)
                mouseMover.mouseMove(getNya.getX() + getX() + 50, getY() + nyaImageHeight + 40); //102 is the width of buttons
        } catch (IOException | AWTException ignored) {}
    }

    void setWindowSizeNormal(Boolean normal) { //I need it to resize window every time when it changes state from maximized to normal
        if (normal)
            setSize(bufferedNyaImage.getWidth(), bufferedNyaImage.getHeight() + buttonsPanel.getHeight() + dataField.getHeight());
        else{//if maximized
            Dimension maximumSizeForTheFistArea = new Dimension((screenSize.width - 223)/2, 48);
            Dimension maximumSizeForTheSecondArea = new Dimension((int) maximumSizeForTheFistArea.getWidth() - 48, 48);

            buttonsPanelFirstRigidArea.setMaximumSize(maximumSizeForTheFistArea);
            buttonsPaneSecondRigidArea.setMaximumSize(maximumSizeForTheSecondArea);
        }
    }

    void resize(BufferedImage nyaFull, BufferedImage nya){
        if (getExtendedState() == Frame.MAXIMIZED_BOTH){

        }
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