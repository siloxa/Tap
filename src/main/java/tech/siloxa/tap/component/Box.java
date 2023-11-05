package tech.siloxa.tap.component;

import javax.swing.*;
import java.awt.*;

public class Box extends JLabel {

    public static final int SIZE = 148;
    private static final Color BG = new Color(235, 235, 235);

    public Box() {
        initialize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Dimension arcs = new Dimension(16, 16);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, SIZE - 1, SIZE - 1, arcs.width, arcs.height);
    }

    private void initialize() {
        setBackground(BG);
    }

    public Box bounds(int x, int y) {
        setBounds(x, y, SIZE, SIZE);
        return this;
    }
}
