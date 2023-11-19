package tech.siloxa.tap.panel;

import tech.siloxa.tap.Tap;
import tech.siloxa.tap.component.IconButton;
import tech.siloxa.tap.component.RoundButton;
import tech.siloxa.tap.model.SystemConfiguration;
import tech.siloxa.tap.model.Theme;
import tech.siloxa.tap.model.TimeDuration;
import tech.siloxa.tap.util.ResponsiveUtils;
import tech.siloxa.tap.util.SystemConfigurationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.time.Duration;
import java.util.Locale;
import java.util.ResourceBundle;

public class TimePickPanel extends AbstractPanel {

    private static final Color LIGHT_COLOR = new Color(235, 235, 235);
    private static final Color DARK_COLOR = new Color(66, 66, 66);

    private static JTextField HOUR_FIELD_UPPER_BOUND;
    private static JTextField HOUR_FIELD;
    private static JTextField HOUR_FIELD_LOWER_BOUND;
    private static JTextField MINUTE_FIELD_UPPER_BOUND;
    private static JTextField MINUTE_FIELD;
    private static JTextField MINUTE_FIELD_LOWER_BOUND;
    private static JTextField SECOND_FIELD_UPPER_BOUND;
    private static JTextField SECOND_FIELD;
    private static JTextField SECOND_FIELD_LOWER_BOUND;

    private final ResourceBundle resourceBundle;
    private final TimeDuration timePickerFor;

    public TimePickPanel(SystemConfiguration systemConfiguration, TimeDuration timePickerFor) {
        super(systemConfiguration);
        this.timePickerFor = timePickerFor;
        this.resourceBundle = ResourceBundle.getBundle("i18n.messages", new Locale(systemConfiguration.getLanguage().toString().toLowerCase()));
        initialize();
    }

    public void render() {
        renderBackIcon();
        renderHeader();
        renderTimePicker();
        renderSetButton();
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

    private void renderHeader() {
        final JLabel header = new JLabel(resolveHeader());
        final int width = 110;
        header.setBounds(ResponsiveUtils.resolveXPosition(Tap.FRAME_SIZE, width), 80, width, 24);
        header.setForeground(resolveFontColor());
        header.setFont(Tap.FONT.deriveFont(20F).deriveFont(Font.PLAIN));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        add(header);
    }

    private void renderTimePicker() {
        final JLabel band = renderBand();

        long totalSeconds = 0;
        switch (timePickerFor) {
            case WORK -> totalSeconds = systemConfiguration.getWorkTime().getSeconds();
            case REST -> totalSeconds = systemConfiguration.getRestTime().getSeconds();
        }
        final long hours = totalSeconds / 3600;
        final long minutes = (totalSeconds % 3600) / 60;
        final long seconds = totalSeconds % 60;

        HOUR_FIELD_UPPER_BOUND = renderTextField(hours == 0 ? "--" : String.format("%02d", hours - 1));
        HOUR_FIELD_UPPER_BOUND.setBounds(120, 152, 40, 40);
        HOUR_FIELD_UPPER_BOUND.setForeground(resolveColor());

        MINUTE_FIELD_UPPER_BOUND = renderTextField(minutes == 0 ? "--" : String.format("%02d", minutes - 1));
        MINUTE_FIELD_UPPER_BOUND.setBounds(170, 152, 40, 40);
        MINUTE_FIELD_UPPER_BOUND.setForeground(resolveColor());

        SECOND_FIELD_UPPER_BOUND = renderTextField(seconds == 0 ? "--" : String.format("%02d", seconds - 1));
        SECOND_FIELD_UPPER_BOUND.setBounds(220, 152, 40, 40);
        SECOND_FIELD_UPPER_BOUND.setForeground(resolveColor());

        add(HOUR_FIELD_UPPER_BOUND);
        add(MINUTE_FIELD_UPPER_BOUND);
        add(SECOND_FIELD_UPPER_BOUND);

        HOUR_FIELD = renderTextField(String.format("%02d", hours));
        HOUR_FIELD.setBounds(120, 1, 40, 40);
        HOUR_FIELD.addMouseWheelListener(
                new MouseAdapter() {
                    @Override
                    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                        if (mouseWheelEvent.getWheelRotation() < 0) {
                            final int currentValue = Integer.parseInt(HOUR_FIELD.getText());

                            if (currentValue < 99) {
                                final int newValue = currentValue + 1;

                                HOUR_FIELD_UPPER_BOUND.setText(String.format("%02d", currentValue));
                                HOUR_FIELD.setText(String.format("%02d", newValue));
                                HOUR_FIELD_LOWER_BOUND.setText(newValue == 99 ? "--" : String.format("%02d", newValue + 1));
                            }
                        } else {
                            final int currentValue = Integer.parseInt(HOUR_FIELD.getText());

                            if (currentValue > 0) {
                                final int newValue = currentValue - 1;

                                HOUR_FIELD_UPPER_BOUND.setText(newValue == 0 ? "--" : String.format("%02d", newValue - 1));
                                HOUR_FIELD.setText(String.format("%02d", newValue));
                                HOUR_FIELD_LOWER_BOUND.setText(String.format("%02d", currentValue));
                            }
                        }
                    }
                }
        );

        final JLabel firstColon = renderColon();
        firstColon.setBounds(160, 1, 10, 40);

        MINUTE_FIELD = renderTextField(String.format("%02d", minutes));
        MINUTE_FIELD.setBounds(170, 1, 40, 40);
        MINUTE_FIELD.addMouseWheelListener(
                new MouseAdapter() {
                    @Override
                    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                        if (mouseWheelEvent.getWheelRotation() < 0) {
                            final int currentValue = Integer.parseInt(MINUTE_FIELD.getText());

                            if (currentValue < 59) {
                                final int newValue = currentValue + 1;

                                MINUTE_FIELD_UPPER_BOUND.setText(String.format("%02d", currentValue));
                                MINUTE_FIELD.setText(String.format("%02d", newValue));
                                MINUTE_FIELD_LOWER_BOUND.setText(newValue == 59 ? "--" : String.format("%02d", newValue + 1));
                            }
                        } else {
                            final int currentValue = Integer.parseInt(MINUTE_FIELD.getText());

                            if (currentValue > 0) {
                                final int newValue = currentValue - 1;

                                MINUTE_FIELD_UPPER_BOUND.setText(newValue == 0 ? "--" : String.format("%02d", newValue - 1));
                                MINUTE_FIELD.setText(String.format("%02d", newValue));
                                MINUTE_FIELD_LOWER_BOUND.setText(String.format("%02d", currentValue));
                            }
                        }
                    }
                }
        );

        final JLabel secondColon = renderColon();
        secondColon.setBounds(210, 1, 10, 40);

        SECOND_FIELD = renderTextField(String.format("%02d", seconds));
        SECOND_FIELD.setBounds(220, 1, 40, 40);
        SECOND_FIELD.addMouseWheelListener(
                new MouseAdapter() {
                    @Override
                    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                        if (mouseWheelEvent.getWheelRotation() < 0) {
                            final int currentValue = Integer.parseInt(SECOND_FIELD.getText());

                            if (currentValue < 59) {
                                final int newValue = currentValue + 1;

                                SECOND_FIELD_UPPER_BOUND.setText(String.format("%02d", currentValue));
                                SECOND_FIELD.setText(String.format("%02d", newValue));
                                SECOND_FIELD_LOWER_BOUND.setText(newValue == 59 ? "--" : String.format("%02d", newValue + 1));
                            }
                        } else {
                            final int currentValue = Integer.parseInt(SECOND_FIELD.getText());

                            if (currentValue > 0) {
                                final int newValue = currentValue - 1;

                                SECOND_FIELD_UPPER_BOUND.setText(newValue == 0 ? "--" : String.format("%02d", newValue - 1));
                                SECOND_FIELD.setText(String.format("%02d", newValue));
                                SECOND_FIELD_LOWER_BOUND.setText(String.format("%02d", currentValue));
                            }
                        }
                    }
                }
        );

        band.add(HOUR_FIELD);
        band.add(firstColon);
        band.add(MINUTE_FIELD);
        band.add(secondColon);
        band.add(SECOND_FIELD);

        HOUR_FIELD_LOWER_BOUND = renderTextField(hours == 99 ? "--" : String.format("%02d", hours + 1));
        HOUR_FIELD_LOWER_BOUND.setBounds(120, 252, 40, 40);
        HOUR_FIELD_LOWER_BOUND.setForeground(resolveColor());

        MINUTE_FIELD_LOWER_BOUND = renderTextField(minutes == 59 ? "--" : String.format("%02d", minutes + 1));
        MINUTE_FIELD_LOWER_BOUND.setBounds(170, 252, 40, 40);
        MINUTE_FIELD_LOWER_BOUND.setForeground(resolveColor());

        SECOND_FIELD_LOWER_BOUND = renderTextField(seconds == 59 ? "--" : String.format("%02d", seconds + 1));
        SECOND_FIELD_LOWER_BOUND.setBounds(220, 252, 40, 40);
        SECOND_FIELD_LOWER_BOUND.setForeground(resolveColor());

        add(HOUR_FIELD_LOWER_BOUND);
        add(MINUTE_FIELD_LOWER_BOUND);
        add(SECOND_FIELD_LOWER_BOUND);
    }

    private void renderSetButton() {
        final RoundButton setButton = new RoundButton(systemConfiguration.getTheme(), 312, 52).bounds(34, 400);
        setButton.setText(resourceBundle.getString("timePicker.set"));
        setButton.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        final int hours = Integer.parseInt(HOUR_FIELD.getText());
                        final int minutes = Integer.parseInt(MINUTE_FIELD.getText());
                        final int seconds = Integer.parseInt(SECOND_FIELD.getText());

                        final int totalSeconds = (hours * 3600) + (minutes * 60) + seconds;

                        switch (timePickerFor) {
                            case WORK -> systemConfiguration.setWorkTime(Duration.ofSeconds(totalSeconds));
                            case REST -> systemConfiguration.setRestTime(Duration.ofSeconds(totalSeconds));
                        }
                        SystemConfigurationUtils.save(systemConfiguration);
                        final MainPanel mainPanel = new MainPanel(systemConfiguration);
                        mainPanel.render();
                        Tap.changePanel(mainPanel);
                    }
                }
        );
        add(setButton);
    }

    private JLabel renderBand() {
        final JLabel band = new JLabel();
        band.setBounds(0, 200, (int) Tap.FRAME_SIZE.getWidth(), 42);
        band.setOpaque(true);
        band.setBackground(resolveColor());
        add(band);
        return band;
    }

    private Color resolveColor() {
        return systemConfiguration.getTheme() == Theme.LIGHT ? LIGHT_COLOR : DARK_COLOR;
    }

    private JLabel renderColon() {
        final JLabel colon = new JLabel(":");
        colon.setForeground(resolveFontColor());
        colon.setFont(Tap.FONT.deriveFont(24F).deriveFont(Font.BOLD));
        colon.setHorizontalAlignment(SwingConstants.CENTER);
        return colon;
    }

    private JTextField renderTextField(String initialValue) {
        final JTextField textField = new JTextField(initialValue);
        textField.setForeground(resolveFontColor());
        textField.setEditable(false);
        textField.setOpaque(false);
        textField.setBorder(null);
        textField.setFont(Tap.FONT.deriveFont(24F).deriveFont(Font.BOLD));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        return textField;
    }

    private String resolveHeader() {
        return resourceBundle.getString("main." + timePickerFor.toString().toLowerCase() + "-time");
    }
}
