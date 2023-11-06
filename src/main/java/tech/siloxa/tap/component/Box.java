package tech.siloxa.tap.component;

import tech.siloxa.tap.model.Theme;

import javax.swing.*;
import java.awt.*;

public class Box extends JLabel {

    public static final int SIZE = 148;
    private static final Color LIGHT_BACKGROUND = new Color(235, 235, 235);
    private static final Color DARK_BACKGROUND = new Color(66, 66, 66);

    public Box(Theme theme) {
        initialize(theme);
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Dimension arcs = new Dimension(16, 16);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, SIZE - 1, SIZE - 1, arcs.width, arcs.height);
        super.paintComponent(g);
    }

    private void initialize(Theme theme) {
        setBackground(resolveBackgroundColor(theme));
    }

    private static Color resolveBackgroundColor(Theme theme) {
        return theme == Theme.LIGHT ? LIGHT_BACKGROUND : DARK_BACKGROUND;
    }

    public Box bounds(int x, int y) {
        setBounds(x, y, SIZE, SIZE);
        return this;
    }
}
