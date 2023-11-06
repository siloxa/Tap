package tech.siloxa.tap.panel;

import tech.siloxa.tap.Tap;
import tech.siloxa.tap.component.Box;
import tech.siloxa.tap.component.IconButton;
import tech.siloxa.tap.model.SystemConfiguration;
import tech.siloxa.tap.model.Theme;
import tech.siloxa.tap.util.SystemConfigurationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel {

    private static final Color LIGHT_THEME_FONT_COLOR = Color.BLACK;
    private static final Color DARK_THEME_FONT_COLOR = new Color(255, 253, 250);
    private static final Color LIGHT_BACKGROUND = Color.WHITE;
    private static final Color DARK_BACKGROUND = new Color(31, 31, 31);
    private final SystemConfiguration systemConfiguration;

    public MainPanel(SystemConfiguration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
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

    private void renderSettingIcon() {
        final IconButton settingIcon = new IconButton("setting", systemConfiguration.getTheme()).bounds(24, 24);
        settingIcon.render();
        add(settingIcon);
    }

    private void renderThemeIcon() {
        final IconButton themeIcon = new IconButton("theme", systemConfiguration.getTheme()).bounds(resolveDarkModeButtonXPosition(), 24);
        themeIcon.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        systemConfiguration.setTheme(resolveNextThemeMode());
                        SystemConfigurationUtils.save(systemConfiguration);
                        final MainPanel mainPanel = new MainPanel(systemConfiguration);
                        mainPanel.render();
                        Tap.changePanel(mainPanel);
                    }
                }
        );
        themeIcon.render();
        add(themeIcon);
    }

    private Theme resolveNextThemeMode() {
        return systemConfiguration.getTheme() == Theme.LIGHT ? Theme.DARK : Theme.LIGHT;
    }

    private void renderTitle() {
        final JLabel title = new JLabel("Hello!");
        title.setBounds(146, 112, 87, 29);
        title.setForeground(resolveFontColor());
        title.setFont(Tap.FONT.deriveFont(24F).deriveFont(Font.BOLD));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);
    }

    private void renderWorkTimeBox() {
        final Box box = new Box(systemConfiguration.getTheme()).bounds(24, 417);
        add(box);
        final JLabel boxHeader = renderBoxHeader("Work Time");
        box.add(boxHeader);
        final JLabel boxTime = renderBoxTime("01:00");
        box.add(boxTime);
    }

    private void renderRestTimeBox() {
        final Box box = new Box(systemConfiguration.getTheme()).bounds(resolveRestTimeBoxXPosition(), 417);
        add(box);
        final JLabel boxHeader = renderBoxHeader("Rest Time");
        box.add(boxHeader);
        final JLabel boxTime = renderBoxTime("01:00");
        box.add(boxTime);
    }

    private JLabel renderBoxHeader(String header) {
        final JLabel boxHeader = new JLabel(header);
        boxHeader.setBounds(33, 50, 82, 19);
        boxHeader.setForeground(resolveFontColor());
        boxHeader.setFont(Tap.FONT.deriveFont(16F).deriveFont(Font.PLAIN));
        boxHeader.setHorizontalAlignment(SwingConstants.CENTER);
        return boxHeader;
    }

    private JLabel renderBoxTime(String time) {
        final JLabel boxTime = new JLabel(time);
        boxTime.setBounds(33, 77, 82, 19);
        boxTime.setForeground(resolveFontColor());
        boxTime.setFont(Tap.FONT.deriveFont(16F).deriveFont(Font.PLAIN));
        boxTime.setHorizontalAlignment(SwingConstants.CENTER);
        return boxTime;
    }

    private Color resolvePanelBackground() {
        return systemConfiguration.getTheme() == Theme.LIGHT ? LIGHT_BACKGROUND : DARK_BACKGROUND;
    }

    private Color resolveFontColor() {
        return systemConfiguration.getTheme() == Theme.LIGHT ? LIGHT_THEME_FONT_COLOR : DARK_THEME_FONT_COLOR;
    }

    private static int resolveRestTimeBoxXPosition() {
        return (int) (Tap.FRAME_SIZE.getWidth() - (24 + Box.SIZE));
    }

    private static int resolveDarkModeButtonXPosition() {
        return (int) (Tap.FRAME_SIZE.getWidth() - 48);
    }
}
