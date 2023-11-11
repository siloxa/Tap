package tech.siloxa.tap.panel;

import tech.siloxa.tap.Tap;
import tech.siloxa.tap.component.IconButton;
import tech.siloxa.tap.model.SystemConfiguration;
import tech.siloxa.tap.model.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingPanel extends AbstractPanel {

    public SettingPanel(SystemConfiguration systemConfiguration) {
        super(systemConfiguration);
        initialize();
    }

    public void render() {
        renderBackIcon();
    }

    private void initialize() {
        setLayout(null);
        setBackground(resolvePanelBackground());
    }

    private void renderBackIcon() {
        final IconButton backIcon = new IconButton("back", systemConfiguration.getTheme()).bounds(24, 24);
        backIcon.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        final MainPanel mainPanel = new MainPanel(systemConfiguration);
                        mainPanel.render();
                        Tap.changePanel(mainPanel);
                    }
                }
        );
        backIcon.render();
        add(backIcon);
    }
}
