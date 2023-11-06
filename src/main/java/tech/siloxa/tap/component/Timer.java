package tech.siloxa.tap.component;

import tech.siloxa.tap.model.Theme;

import javax.swing.*;
import java.awt.*;

public class Timer extends JProgressBar {

    public static final int SIZE = 220;
    private static final Color LIGHT_BAR = new Color(15, 108, 189);
    private static final Color DARK_BAR = new Color(71, 158, 245);
    private static final Color LIGHT_BACKGROUND = new Color(235, 235, 235);
    private static final Color DARK_BACKGROUND = new Color(66, 66, 66);

    public Timer(Theme theme) {
        initialize(theme);
    }

    public Timer bounds(int x, int y) {
        setBounds(x, y, SIZE, SIZE);
        return this;
    }

    private void initialize(Theme theme) {
        setOpaque(false);
        setBackground(resolveBackgroundColor(theme));
        setForeground(new Color(97, 97, 97));
        setBorderPainted(false);
        setStringPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setUI(new CircularProgressBarUI(this, resolveBarColor(theme)));
    }

    private Color resolveBackgroundColor(Theme theme) {
        return theme == Theme.LIGHT ? LIGHT_BACKGROUND : DARK_BACKGROUND;
    }

    private Color resolveBarColor(Theme theme) {
        return theme == Theme.LIGHT ? LIGHT_BAR : DARK_BAR;
    }
}
