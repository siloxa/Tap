package tech.siloxa.tap.component;

import tech.siloxa.tap.util.IconLoader;

import javax.swing.*;
import java.awt.*;

public class IconButton extends JLabel {

    private final ImageIcon icon;

    public IconButton(String iconName) {
        icon = initialize(iconName);
    }

    private ImageIcon initialize(String iconName) {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return IconLoader.load(iconName);
    }

    public IconButton bounds(int x, int y) {
        setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        return this;
    }

    public void render() {
        setIcon(icon);
    }
}
