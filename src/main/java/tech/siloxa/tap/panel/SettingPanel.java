package tech.siloxa.tap.panel;

import tech.siloxa.tap.Tap;
import tech.siloxa.tap.component.IconButton;
import tech.siloxa.tap.model.SystemConfiguration;
import tech.siloxa.tap.component.ComboBoxArrowUI;
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
        renderSelectLanguage();
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

    private void renderSelectLanguage() {
        final Color foreground = resolveFontColor();
        final Color background = resolvePanelBackground();

        final JLabel label = new JLabel("Language");
        label.setBounds(24, 80, 100, 24);
        label.setForeground(foreground);
        label.setFont(Tap.FONT.deriveFont(20F).deriveFont(Font.PLAIN));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
        final JComboBox<String> languages = new JComboBox<>(
                new String[]{"English", "Deutsch", "Persian"}
        );
        final int width = 150;
        languages.setBounds(resolveSelectLanguageXPosition(width), 80, width, 24);
        languages.setBackground(background);
        languages.setForeground(foreground);
        languages.setFont(Tap.FONT.deriveFont(20F).deriveFont(Font.PLAIN));
        languages.setFocusable(false);
        languages.setUI(new ComboBoxArrowUI(background, foreground));
        languages.setBorder(null);
        add(languages);
    }

    private static int resolveSelectLanguageXPosition(int width) {
        return (int) (Tap.FRAME_SIZE.getWidth() - width - 24);
    }
}
