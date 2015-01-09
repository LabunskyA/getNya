import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Lina on 21.11.2014.
 */

public class Window extends JFrame{
    private JToggleButton getNya;
    private JToggleButton saveNya;
    private BufferedImage bufferedNyaImage;
    private BufferedImage bufferedFullImage;
    private JTextField dataField;
    private JLabel nyaLabel = new JLabel();
    private JPanel buttonsPanel = new JPanel(new FlowLayout());
    private ActionListener saveFullNya = new Save2File();
    private ActionListener getNewNya = new Solution();
    private Component northRigidArea = Box.createRigidArea(new Dimension(0, 0));

    protected BufferedImage getBufferedFullImage() {
        return bufferedFullImage;
    }

    protected Window() {
        super("getNya");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getNya = new JToggleButton();
        saveNya = new JToggleButton();

        getNya.addActionListener(getNewNya);
        saveNya.addActionListener(saveFullNya);

        try {
            setIconImage(new ImageIcon(ImageIO.read(getClass().getResource("resources/Nya.png"))).getImage());

            getNya.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("resources/getNya.png"))));
            getNya.setPressedIcon(new ImageIcon(ImageIO.read(getClass().getResource("resources/getNyaPressed.png"))));
            getNya.setMargin(new Insets(0, 0, 0, 0));
            getNya.setBorder(BorderFactory.createEmptyBorder());
            getNya.setBackground(Color.WHITE);

            saveNya.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("resources/saveNya.png"))));
            saveNya.setPressedIcon(new ImageIcon(ImageIO.read(getClass().getResource("resources/saveNyaPressed.png"))));
            saveNya.setMargin(new Insets(0, 0, 0, 0));
            saveNya.setBorder(BorderFactory.createEmptyBorder());
            saveNya.setBackground(Color.WHITE);
        } catch (IOException e){
            e.printStackTrace();
        }

        JPanel centralPanel = new JPanel();

        nyaLabel = new JLabel();
        dataField = new JTextField();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        nyaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonsPanel.add(getNya);
        buttonsPanel.add(saveNya);
        buttonsPanel.setBackground(Color.WHITE);

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
        //setUndecorated(true);

        System.out.println(buttonsPanel.getHeight());
    }

    protected void drawNya() throws MalformedURLException {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Zerochan.generateNumberNya();
        Zerochan.generateURLs();

        try { //sometimes Zerochan.net blocks some pictures, so I need to handle this occasion and get another url with another picture
            bufferedNyaImage = ImageIO.read(Zerochan.nyaURL);
            bufferedFullImage = ImageIO.read(Zerochan.fullURL);
            Integer nyaImageHeight = bufferedNyaImage.getHeight();
            Integer nyaImageWidth = bufferedNyaImage.getWidth();
            Integer maxContentPaneHeight = nyaImageHeight + buttonsPanel.getHeight() + dataField.getHeight() + Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            //this on is for small screens, less then 720p
            if (screenSize.getHeight() < maxContentPaneHeight || screenSize.getWidth() < nyaImageWidth)
                drawNya();
            else
                nyaLabel.setIcon(new ImageIcon(bufferedNyaImage));

            dataField.setText("Width: " + bufferedFullImage.getWidth() + " Height: " + bufferedFullImage.getHeight());
            dataField.setMaximumSize(new Dimension(nyaImageWidth, dataField.getHeight()));
            northRigidArea.setMaximumSize(new Dimension(nyaImageWidth, ((int) screenSize.getHeight() - maxContentPaneHeight) / 2));

            if (getExtendedState() != Frame.NORMAL) {
                setMinimumSize(getSize());
                pack();
                setMinimumSize(null);
            } else
                pack();

            setCursor(Cursor.getDefaultCursor());
        } catch (IOException e) {
            drawNya();
        }
    }

    protected void setWindowSize() { //I need it to resize window every time when it changes state from maximized to normal
        try {
            bufferedNyaImage = ImageIO.read(Zerochan.nyaURL);
            Integer nyaImageHeight = bufferedNyaImage.getHeight();
            Integer nyaImageWidth = bufferedNyaImage.getWidth();

            getContentPane().setSize(new Dimension(nyaImageWidth, nyaImageHeight + buttonsPanel.getHeight() + dataField.getHeight()));

            pack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}