package tech.siloxa.tap.panel;

import tech.siloxa.tap.Tap;
import tech.siloxa.tap.component.ComboBoxArrowUI;
import tech.siloxa.tap.component.IconButton;
import tech.siloxa.tap.model.Language;
import tech.siloxa.tap.model.SystemConfiguration;
import tech.siloxa.tap.util.SystemConfigurationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingPanel extends AbstractPanel {

    private final ResourceBundle resourceBundle;

    public SettingPanel(SystemConfiguration systemConfiguration) {
        super(systemConfiguration);
        this.resourceBundle = ResourceBundle.getBundle("i18n.messages", new Locale(systemConfiguration.getLanguage().toString().toLowerCase()));
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

        final JLabel label = new JLabel(resourceBundle.getString("setting.language"));
        label.setBounds(24, 80, 100, 24);
        label.setForeground(foreground);
        label.setFont(Tap.FONT.deriveFont(20F).deriveFont(Font.PLAIN));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
        final String[] languagesAvailable = Arrays.stream(Language.values()).map(Language::getName).toArray(value -> new String[Language.values().length]);
        final JComboBox<String> languages = new JComboBox<>(languagesAvailable);
        languages.setSelectedIndex(Language.resolveEnumOrder(systemConfiguration.getLanguage()));
        languages.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                final Language selectedLanguage = Language.resolveName(itemEvent.getItem().toString());
                systemConfiguration.setLanguage(selectedLanguage);
                SystemConfigurationUtils.save(systemConfiguration);
                Tap.reRun();
            }
        });
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
