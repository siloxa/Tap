package tech.siloxa.tap.component;

import tech.siloxa.tap.model.Theme;
import tech.siloxa.tap.util.IconLoader;

import javax.swing.*;
import java.awt.*;

public class IconButton extends JLabel {

    private final ImageIcon icon;

    public IconButton(String iconName, Theme theme) {
        icon = initialize(iconName, theme);
    }

    private ImageIcon initialize(String iconName, Theme theme) {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return IconLoader.load(iconName, theme);
    }

    public IconButton bounds(int x, int y) {
        setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        return this;
    }

    public void render() {
        setIcon(icon);
    }
}
