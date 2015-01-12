import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Lina on 21.11.2014.
 */

public class Window extends JFrame {
    static Integer positionX;
    static Integer positionY;
    private BufferedImage bufferedNyaImage;
    private BufferedImage bufferedFullImage;
    private JTextField dataField;
    private JLabel nyaLabel = new JLabel();
    private JPanel buttonsPanel = new JPanel();
    private SimpleButton getNya;
    private Component northRigidArea = Box.createRigidArea(new Dimension(0, 0));
    private Component buttonsPanelFirstRigidArea = Box.createRigidArea(new Dimension(0, 0));
    private Component buttonsPaneSecondRigidArea = Box.createRigidArea(new Dimension(0, 0));
    static protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    BufferedImage getBufferedFullImage() {
        return bufferedFullImage;
    }

    protected Window() {
        super("getNya");

        try {
            setIconImage(new ImageIcon(ImageIO.read(getClass().getResource("resources/Nya.png"))).getImage());
        } catch (IOException e) {}

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ActionListener saveFullNya = new Listeners();
        ActionListener getNewNya = new getNewNya();
        ActionListener exitNya = new CloseNya();
        ActionListener maximizeNyaWindow = new MaximizeNya();
        ActionListener minimizeNyaWindow = new MinimizeNya();
        ActionListener nyaSettings = new Settings();

        MouseAdapter mouseMove = new MouseMoveListener();
        MouseAdapter mouseDrag = new MouseDragListener();

        getNya = new SimpleButton("resources/getNya.png", "resources/getNyaPressed.png");
        SimpleButton saveNya = new SimpleButton("resources/saveNya.png", "resources/saveNyaPressed.png");
        SimpleButton closeNya = new SimpleButton("resources/closeNya.png");
        SimpleButton maximizeNya = new SimpleButton("resources/maximizeNya.png");
        SimpleButton minimizeNya = new SimpleButton("resources/minimizeNya.png");
        SimpleButton nyaPrefs = new SimpleButton("resources/nyaPrefs.png", "resources/nyaPrefsPressed.png");

        JPanel centralPanel = new JPanel();
        JPanel smallButtonsPanel = new JPanel();
        JPanel panelForSmallButtonsPanel = new JPanel();
        JPanel panelForSettingsButton = new JPanel();
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

        panelForSettingsButton.setLayout(new BoxLayout(panelForSettingsButton, BoxLayout.Y_AXIS));
        panelForSettingsButton.setBackground(Color.WHITE);
        panelForSettingsButton.add(Box.createRigidArea(new Dimension(0, 22)));
        panelForSettingsButton.add(nyaPrefs);

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
        buttonsPanel.add(panelForSettingsButton);
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

    protected void drawNya() throws MalformedURLException {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Zerochan.generateNumberNya();
        Zerochan.generateURLs();

        try { //sometimes Zerochan.net blocks some pictures, so I need to handle this occasion and get another url with another picture
            Robot mouseMover = new Robot();
            bufferedNyaImage = ImageIO.read(Zerochan.nyaURL);
            bufferedFullImage = ImageIO.read(Zerochan.fullURL);
            Integer nyaImageHeight = bufferedNyaImage.getHeight();
            Integer nyaImageWidth = bufferedNyaImage.getWidth();
            Dimension maximumSizeForTheFistArea = new Dimension((nyaImageWidth - 204)/2 - 16, 48);
            Dimension maximumSizeForTheSecondArea = new Dimension((nyaImageWidth - 204)/2 - 48, 48);
            Dimension minimumWindowSize = new Dimension(261, 0); //just buttons size
            Integer maxContentPaneHeight = nyaImageHeight + 48 + dataField.getHeight() + Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom;

            //this on is for small screens, less then 720p
            if (screenSize.getHeight() < maxContentPaneHeight || screenSize.getWidth() < nyaImageWidth)
                drawNya();
            else
                nyaLabel.setIcon(new ImageIcon(bufferedNyaImage));

            dataField.setText("Width: " + bufferedFullImage.getWidth() + " Height: " + bufferedFullImage.getHeight());
            dataField.setMaximumSize(new Dimension(nyaImageWidth, dataField.getHeight()));
            northRigidArea.setMaximumSize(new Dimension(nyaImageWidth, ((int) screenSize.getHeight() - maxContentPaneHeight) / 2));
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

            if (getExtendedState() == Frame.NORMAL)
                mouseMover.mouseMove(getNya.getX() + getX() + 50, getY() + nyaImageHeight + 40); //102 is the width of buttons
        } catch (IOException e) {
            drawNya(); //get new image, if there is something wrong with this one
        } catch (AWTException e){} //never uses
    }

    protected void setWindowSizeNormal(Boolean normal) { //I need it to resize window every time when it changes state from maximized to normal
        if (normal)
            setSize(bufferedNyaImage.getWidth(), bufferedNyaImage.getHeight() + buttonsPanel.getHeight() + dataField.getHeight());
        else{
            Dimension maximumSizeForTheFistArea = new Dimension(((int) screenSize.getWidth() - 223)/2, 48);
            Dimension maximumSizeForTheSecondArea = new Dimension((int) maximumSizeForTheFistArea.getWidth() - 48, 48);

            buttonsPanelFirstRigidArea.setMaximumSize(maximumSizeForTheFistArea);
            buttonsPaneSecondRigidArea.setMaximumSize(maximumSizeForTheSecondArea);
        }
        //pack();
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
        } catch (IOException e) {}
    }

    public SimpleButton(String path) {
        super();
        try {
            setIcon(new ImageIcon(ImageIO.read(getClass().getResource(path))));
            setPressedIcon(new ImageIcon(ImageIO.read(getClass().getResource(path))));
            setMargin(new Insets(0, 0, 0, 0));
            setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, false));
            setBackground(Color.WHITE);
        } catch (IOException e) {}
    }
}