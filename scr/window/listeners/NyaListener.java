package window.listeners;

import window.Window;

import java.util.EventListener;

/**
 * Created by LabunskyA
 * GitHub: github.com/LabunskyA
 * VK: vk.com/labunsky
 */
abstract class NyaListener implements EventListener {
    final Window getNya;

    NyaListener(Window getNya) {
        this.getNya = getNya;
    }
}
