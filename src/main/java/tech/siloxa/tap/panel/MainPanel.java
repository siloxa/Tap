package tech.siloxa.tap.panel;

import tech.siloxa.tap.Tap;
import tech.siloxa.tap.component.Alert;
import tech.siloxa.tap.component.Box;
import tech.siloxa.tap.component.IconButton;
import tech.siloxa.tap.component.Timer;
import tech.siloxa.tap.model.State;
import tech.siloxa.tap.model.SystemConfiguration;
import tech.siloxa.tap.model.Theme;
import tech.siloxa.tap.model.TimeDuration;
import tech.siloxa.tap.util.ResponsiveUtils;
import tech.siloxa.tap.util.SystemConfigurationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class MainPanel extends AbstractPanel {

    private static JLabel HEADER;
    private static JLabel TIMER;
    private static State STATE = State.IDLE;
    private static Integer PROGRESS_BAR = 0;
    private static Timer TIMER_BAR;
    private static javax.swing.Timer TIMER_COUNTER;
    private static Duration TIMER_DURATION = Duration.ZERO;

    private final ResourceBundle resourceBundle;

    public MainPanel(SystemConfiguration systemConfiguration) {
        super(systemConfiguration);
        this.resourceBundle = ResourceBundle.getBundle("i18n.messages", new Locale(systemConfiguration.getLanguage().toString().toLowerCase()));
        initialize();
    }

    public void render() {
        renderSettingIcon();
        renderThemeIcon();
        renderHeader();
        renderTimer();
        renderWorkTimeBox();
        renderRestTimeBox();
    }

    private void initialize() {
        setLayout(null);
        setBackground(resolvePanelBackground());
    }

    private void renderSettingIcon() {
        final IconButton settingIcon = new IconButton("setting", systemConfiguration.getTheme()).bounds(24, 24);
        settingIcon.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        final SettingPanel settingPanel = new SettingPanel(systemConfiguration);
                        settingPanel.render();
                        Tap.changePanel(settingPanel);
                    }
                }
        );
        settingIcon.render();
        add(settingIcon);
    }

    private void renderThemeIcon() {
        final IconButton themeIcon = new IconButton("theme", systemConfiguration.getTheme()).bounds(resolveThemeButtonXPosition(), 24);
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

    private void renderHeader() {
        HEADER = new JLabel(STATE.translate(systemConfiguration.getLanguage()));
        HEADER.setBounds(106, 112, 170, 29);
        HEADER.setForeground(resolveFontColor());
        HEADER.setFont(Tap.FONT.deriveFont(24F).deriveFont(Font.BOLD));
        HEADER.setHorizontalAlignment(SwingConstants.CENTER);
        add(HEADER);
    }

    private void renderTimer() {
        TIMER_BAR = new Timer(systemConfiguration.getTheme()).bounds(
                ResponsiveUtils.resolveXPosition(Tap.FRAME_SIZE, Timer.SIZE), 180
        );
        TIMER_BAR.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        switch (STATE) {
                            case IDLE -> startTimer(State.WORK_START);
                            case WORK_START, WORK_RESUME -> stopTimer(State.WORK_START);
                            case WORK_PAUSE -> startTimer(State.WORK_RESUME);
                            case REST_START, REST_RESUME -> stopTimer(State.REST_START);
                            case REST_PAUSE -> startTimer(State.REST_RESUME);
                        }
                    }
                }
        );
        TIMER_BAR.setValue(PROGRESS_BAR);
        add(TIMER_BAR);

        TIMER = new JLabel(renderDurationAsString(TIMER_DURATION));
        TIMER.setBounds(32, 97, 156, 30);
        TIMER.setForeground(resolveFontColor());
        TIMER.setFont(Tap.FONT.deriveFont(30F).deriveFont(Font.BOLD));
        TIMER.setHorizontalAlignment(SwingConstants.CENTER);
        TIMER_BAR.add(TIMER);
    }

    private void stopTimer(State state) {
        STATE = state == State.WORK_START ? State.WORK_PAUSE : State.REST_PAUSE;
        HEADER.setText(STATE.translate(systemConfiguration.getLanguage()));

        if (TIMER_COUNTER != null) {
            TIMER_COUNTER.stop();
        }
    }

    private void startTimer(State state) {
        STATE = state;

        if (state == State.WORK_START) {
            TIMER_DURATION = Tap.SYSTEM_CONFIGURATION.getWorkTime();
        } else if (state == State.REST_START) {
            TIMER_DURATION = Tap.SYSTEM_CONFIGURATION.getRestTime();
        }

        HEADER.setText(STATE.translate(systemConfiguration.getLanguage()));

        final float frequency = TIMER_DURATION.getSeconds() / 100F;
        final float period = frequency * 10;

        final AtomicInteger progressBarCounter = new AtomicInteger(0);
        final AtomicInteger clockCounter = new AtomicInteger(0);
        TIMER_COUNTER = new javax.swing.Timer(100,
                actionEvent -> {
                    if (progressBarCounter.incrementAndGet() == period) {
                        TIMER_BAR.setValue(++PROGRESS_BAR);
                        progressBarCounter.set(0);
                    }
                    if (clockCounter.incrementAndGet() == 10) {
                        TIMER_DURATION = TIMER_DURATION.minus(Duration.ofSeconds(1));
                        TIMER.setText(renderDurationAsString(TIMER_DURATION));
                        clockCounter.set(0);
                        if(TIMER_DURATION.isZero()) {
                            TIMER_COUNTER.stop();
                            alert();
                        }
                    }
                }
        );
        TIMER_COUNTER.start();
    }

    private void alert() {
        final Alert alert = new Alert(systemConfiguration);
        alert.setMessage(resolveAlertMessage());
        alert.setOkButtonActionListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        switch (STATE) {
                            case WORK_START, WORK_RESUME -> startTimer(State.REST_START);
                            case REST_START, REST_RESUME -> startTimer(State.WORK_START);
                        }
                        alert.dispose();
                    }
                }
        );
    }

    private String resolveAlertMessage() {
        return switch (STATE) {
            case WORK_START, WORK_RESUME -> resourceBundle.getString("alert.rest");
            case REST_START, REST_RESUME -> resourceBundle.getString("alert.work");
            default -> "";
        };
    }

    private void renderWorkTimeBox() {
        final Box box = new Box(systemConfiguration.getTheme()).bounds(24, 437);
        box.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        final TimePickPanel timePickPanel = new TimePickPanel(systemConfiguration, TimeDuration.WORK);
                        timePickPanel.render();
                        Tap.changePanel(timePickPanel);
                    }
                }
        );
        add(box);
        final JLabel boxHeader = renderBoxHeader(resourceBundle.getString("main.work-time"));
        box.add(boxHeader);
        final JLabel boxTime = renderBoxTime(renderDurationAsString(systemConfiguration.getWorkTime()));
        box.add(boxTime);
    }

    private void renderRestTimeBox() {
        final Box box = new Box(systemConfiguration.getTheme()).bounds(resolveRestTimeBoxXPosition(), 437);
        box.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        final TimePickPanel timePickPanel = new TimePickPanel(systemConfiguration, TimeDuration.REST);
                        timePickPanel.render();
                        Tap.changePanel(timePickPanel);
                    }
                }
        );
        add(box);
        final JLabel boxHeader = renderBoxHeader(resourceBundle.getString("main.rest-time"));
        box.add(boxHeader);
        final JLabel boxTime = renderBoxTime(renderDurationAsString(systemConfiguration.getRestTime()));
        box.add(boxTime);
    }

    private String renderDurationAsString(Duration duration) {
        final long totalSeconds = duration.getSeconds();
        final long hours = totalSeconds / 3600;
        final long minutes = (totalSeconds % 3600) / 60;
        final long seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private JLabel renderBoxHeader(String header) {
        final JLabel boxHeader = new JLabel(header);
        boxHeader.setBounds(29, 50, 90, 19);
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

    private static int resolveRestTimeBoxXPosition() {
        return (int) (Tap.FRAME_SIZE.getWidth() - (24 + Box.SIZE));
    }

    private static int resolveThemeButtonXPosition() {
        return (int) (Tap.FRAME_SIZE.getWidth() - 48);
    }
}
