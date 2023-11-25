package tech.siloxa.tap.component;

import tech.siloxa.tap.Tap;
import tech.siloxa.tap.model.SystemConfiguration;
import tech.siloxa.tap.model.Theme;
import tech.siloxa.tap.panel.AbstractPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Alert extends JDialog {

    private final static int WIDTH = 600;
    private final static int HEIGHT = 300;
    private final static int X_POSITION = (int) (Tap.SCREEN_SIZE.getWidth() / 2 - WIDTH / 2);
    private final static int Y_POSITION = (int) (Tap.SCREEN_SIZE.getHeight() / 2 - HEIGHT / 2);
    private final JLabel messageLabel;
    private final RoundButton okButton;
    private final ResourceBundle resourceBundle;

    public Alert(SystemConfiguration systemConfiguration) {
        super(Tap.getFrame(), "Tap Alarm");

        resourceBundle = ResourceBundle.getBundle("i18n.messages", new Locale(systemConfiguration.getLanguage().toString().toLowerCase()));
        messageLabel = new JLabel();
        okButton = new RoundButton(systemConfiguration.getTheme(), 100, 40).bounds(WIDTH / 2 - 50, HEIGHT / 2 + 40);
        initialize(systemConfiguration.getTheme());
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void setOkButtonActionListener(MouseAdapter mouseAdapter) {
        okButton.addMouseListener(mouseAdapter);
    }

    private void initialize(Theme theme) {
        setBounds(X_POSITION, Y_POSITION, WIDTH, HEIGHT);
        setResizable(false);
        getRootPane().setOpaque(false);
        getContentPane().setLayout(null);
        getContentPane().setBackground(resolvePanelBackground(theme));

        messageLabel.setBounds(0, HEIGHT / 2 - 50, WIDTH, 50);
        messageLabel.setForeground(resolveFontColor(theme));
        messageLabel.setFont(Tap.FONT.deriveFont(20F).deriveFont(Font.PLAIN));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(messageLabel);

        okButton.setText(resourceBundle.getString("alert.ok"));
        add(okButton);

        setVisible(true);
    }

    private Color resolvePanelBackground(Theme theme) {
        return theme == Theme.LIGHT ? AbstractPanel.LIGHT_BACKGROUND : AbstractPanel.DARK_BACKGROUND;
    }

    private Color resolveFontColor(Theme theme) {
        return theme == Theme.LIGHT ? AbstractPanel.LIGHT_THEME_FONT_COLOR : AbstractPanel.DARK_THEME_FONT_COLOR;
    }
}
