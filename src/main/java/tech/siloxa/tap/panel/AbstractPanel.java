package tech.siloxa.tap.panel;

import tech.siloxa.tap.model.SystemConfiguration;
import tech.siloxa.tap.model.Theme;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractPanel extends JPanel {

    public static final Color LIGHT_THEME_FONT_COLOR = Color.BLACK;
    public static final Color DARK_THEME_FONT_COLOR = new Color(255, 253, 250);
    public static final Color LIGHT_BACKGROUND = Color.WHITE;
    public static final Color DARK_BACKGROUND = new Color(31, 31, 31);
    protected final SystemConfiguration systemConfiguration;

    public AbstractPanel(SystemConfiguration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
    }

    protected Color resolvePanelBackground() {
        return systemConfiguration.getTheme() == Theme.LIGHT ? LIGHT_BACKGROUND : DARK_BACKGROUND;
    }

    protected Color resolveFontColor() {
        return systemConfiguration.getTheme() == Theme.LIGHT ? LIGHT_THEME_FONT_COLOR : DARK_THEME_FONT_COLOR;
    }
}
