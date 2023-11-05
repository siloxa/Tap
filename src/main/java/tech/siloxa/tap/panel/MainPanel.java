package tech.siloxa.tap.panel;

import tech.siloxa.tap.Tap;
import tech.siloxa.tap.component.Box;
import tech.siloxa.tap.component.IconButton;
import tech.siloxa.tap.model.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel {

    public static final Color DARK_THEME_FONT_COLOR = new Color(255, 253, 250);
    public static final Color LIGHT_THEME_FONT_COLOR = Color.BLACK;
    public static final Color LIGHT_BACKGROUND = Color.WHITE;
    public static final Color DARK_BACKGROUND = new Color(31, 31, 31);
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
        return theme == Theme.LIGHT ? LIGHT_BACKGROUND : DARK_BACKGROUND;
    }

    private void renderSettingIcon() {
        final IconButton settingIcon = new IconButton("setting", theme).bounds(24, 24);
        settingIcon.render();
        add(settingIcon);
    }

    private void renderThemeIcon() {
        final IconButton themeIcon = new IconButton("theme", theme).bounds(resolveDarkModeButtonXPosition(), 24);
        themeIcon.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        final MainPanel mainPanel = new MainPanel(resolveNextThemeMode());
                        mainPanel.render();
                        Tap.frame.setContentPane(mainPanel);
                        Tap.frame.revalidate();
                        Tap.frame.repaint();
                    }
                }
        );
        themeIcon.render();
        add(themeIcon);
    }

    private Theme resolveNextThemeMode() {
        return theme == Theme.LIGHT ? Theme.DARK : Theme.LIGHT;
    }

    private void renderTitle() {
        final JLabel title = new JLabel("Hello!");
        title.setBounds(146, 112, 87, 29);
        title.setForeground(resolveTitleColor());
        title.setFont(Tap.FONT.deriveFont(24F).deriveFont(Font.BOLD));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);
    }

    private Color resolveTitleColor() {
        return theme == Theme.LIGHT ? LIGHT_THEME_FONT_COLOR : DARK_THEME_FONT_COLOR;
    }

    private void renderWorkTimeBox() {
        final Box box = new Box(theme).bounds(24, 417);
        add(box);
    }

    private void renderRestTimeBox() {
        final Box box = new Box(theme).bounds(resolveRestTimeBoxXPosition(), 417);
        add(box);
    }

    private static int resolveRestTimeBoxXPosition() {
        return (int) (Tap.FRAME_SIZE.getWidth() - (24 + Box.SIZE));
    }

    private static int resolveDarkModeButtonXPosition() {
        return (int) (Tap.FRAME_SIZE.getWidth() - 48);
    }
}
