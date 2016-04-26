package window.listeners;

import window.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
public class ServicePanelListener extends NyaListener implements ActionListener {
    public enum Panel {SETTINGS, ABOUT}

    private final JPanel workingPanel;
    private final Panel type;

    public ServicePanelListener(Window getNya, JPanel workingPanel, Panel type) {
        super(getNya);
        this.workingPanel = workingPanel;
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (type) {
            case ABOUT:
                if (!getNya.getAboutIsOpen()) {
                    workingPanel.setVisible(true);
                    getNya.setAboutIsOpen(true);
                } else {
                    workingPanel.setVisible(false);
                    getNya.setAboutIsOpen(false);
                }

                break;
            case SETTINGS:
                if (!getNya.getSettingsIsOpen()) {
                    workingPanel.setVisible(true);
                    getNya.setSettingsIsOpen(true);
                } else {
                    workingPanel.setVisible(false);
                    getNya.setSettingsIsOpen(false);
                }

                break;
        }
    }
}
