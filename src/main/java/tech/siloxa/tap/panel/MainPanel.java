package tech.siloxa.tap.panel;

import tech.siloxa.tap.Tap;
import tech.siloxa.tap.component.IconButton;
import tech.siloxa.tap.component.Box;
import tech.siloxa.tap.model.Theme;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private final Theme theme;

    public MainPanel(Theme theme) {
        this.theme = theme;
        initialize();
    }

    public void render() {
        renderSettingIcon();
        renderThemeIcon();
        renderTitle();
        renderWorkTimeBox();
        renderRestTimeBox();
    }

    private void initialize() {
        setLayout(null);
        setBackground(resolvePanelBackground());
    }

    private Color resolvePanelBackground() {
        return theme == Theme.LIGHT ? Color.WHITE : new Color(31, 31, 31);
    }

    private void renderSettingIcon() {
        final IconButton settingIcon = new IconButton("setting", theme).bounds(24, 24);
        settingIcon.render();
        add(settingIcon);
    }

    private void renderThemeIcon() {
        final IconButton themeIcon = new IconButton("theme", theme).bounds(resolveDarkModeButtonXPosition(), 24);
        themeIcon.render();
        add(themeIcon);
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
