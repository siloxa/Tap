package tech.siloxa.tap.component;

import tech.siloxa.tap.Tap;
import tech.siloxa.tap.model.Theme;

import javax.swing.*;
import java.awt.*;

public class RoundButton extends JButton {

    private static final Color LIGHT_BUTTON = new Color(15, 108, 189);
    private static final Color DARK_BUTTON = new Color(71, 158, 245);

    private final int WIDTH;
    private final int HEIGHT;

    public RoundButton(Theme theme, int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        initialize(theme);
    }

    public RoundButton bounds(int x, int y) {
        setBounds(x, y, WIDTH, HEIGHT);
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Dimension arcs = new Dimension(16, 16);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, WIDTH - 1, HEIGHT - 1, arcs.width, arcs.height);
        super.paintComponent(g);
    }
    private void initialize(Theme theme) {
        setBackground(resolveBackgroundColor(theme));
        setForeground(Color.WHITE);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(Tap.FONT.deriveFont(24F).deriveFont(Font.BOLD));
        setBorderPainted(false);
        setContentAreaFilled(false);
    }

    private static Color resolveBackgroundColor(Theme theme) {
        return theme == Theme.LIGHT ? LIGHT_BUTTON : DARK_BUTTON;
    }

}
