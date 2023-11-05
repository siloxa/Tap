package tech.siloxa.tap.panel;

import tech.siloxa.tap.Tap;
import tech.siloxa.tap.component.IconButton;
import tech.siloxa.tap.component.Box;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        initialize();
    }

    private void initialize() {
        setLayout(null);
        setBackground(Color.WHITE);
    }

    public void render() {
        renderSettingIcon();
        renderDarkModeIcon();
        renderTitle();
        renderWorkTimeBox();
        renderRestTimeBox();
    }

    private void renderSettingIcon() {
        final IconButton settingIcon = new IconButton("setting").bounds(24, 24);
        settingIcon.render();
        add(settingIcon);
    }

    private void renderDarkModeIcon() {
        final IconButton moonIcon = new IconButton("moon").bounds(resolveDarkModeButtonXPosition(), 24);
        moonIcon.render();
        add(moonIcon);
    }

    private void renderTitle() {
        final JLabel title = new JLabel("Hello!");
        title.setBounds(146, 112, 87, 29);
        title.setFont(Tap.FONT.deriveFont(24F).deriveFont(Font.BOLD));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);
    }

    private void renderWorkTimeBox() {
        final Box box = new Box().bounds(24, 417);
        add(box);
    }

    private void renderRestTimeBox() {
        final Box box = new Box().bounds(resolveRestTimeBoxXPosition(), 417);
        add(box);
    }

    private static int resolveRestTimeBoxXPosition() {
        return (int) (Tap.FRAME_SIZE.getWidth() - (24 + Box.SIZE));
    }

    private static int resolveDarkModeButtonXPosition() {
        return (int) (Tap.FRAME_SIZE.getWidth() - 48);
    }
}
