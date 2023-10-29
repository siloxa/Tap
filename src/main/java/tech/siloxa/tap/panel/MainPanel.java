package tech.siloxa.tap.panel;

import tech.siloxa.tap.Tap;
import tech.siloxa.tap.component.IconButton;

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
    }

    private void renderDarkModeIcon() {
        final IconButton moonIcon = new IconButton("moon").bounds(resolveDarkModeButtonXPosition(), 24);
        moonIcon.render();
        add(moonIcon);
    }

    private static int resolveDarkModeButtonXPosition() {
        return (int) (Tap.frameSize.getWidth() - 48);
    }

    private void renderSettingIcon() {
        final IconButton settingIcon = new IconButton("setting").bounds(24, 24);
        settingIcon.render();
        add(settingIcon);
    }
}
