package tech.siloxa.tap;

import tech.siloxa.tap.model.SystemConfiguration;
import tech.siloxa.tap.model.Theme;
import tech.siloxa.tap.panel.MainPanel;
import tech.siloxa.tap.util.FontLoader;
import tech.siloxa.tap.util.ResponsiveUtils;
import tech.siloxa.tap.util.SystemConfigurationUtils;

import javax.swing.*;
import java.awt.*;

public class Tap {

    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension FRAME_SIZE = new Dimension(380, 700);
    public static final Font FONT = FontLoader.load();
    public static final SystemConfiguration SYSTEM_CONFIGURATION;
    private static JFrame frame;

    static {
        SYSTEM_CONFIGURATION = SystemConfigurationUtils.load();
        initialize();
    }

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        EventQueue.invokeLater(() -> {
            try {
                frame.setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void reRun() {
        frame.dispose();
        frame = null;
        initialize();
        run();
    }

    private static void initialize() {
        final Dimension centerOfDisplay = ResponsiveUtils.getCenterOfDisplay(SCREEN_SIZE, FRAME_SIZE);

        frame = new JFrame();
        frame.setBounds(
                (int) centerOfDisplay.getWidth(), (int) centerOfDisplay.getHeight(),
                (int) FRAME_SIZE.getWidth(), (int) FRAME_SIZE.getHeight()
        );
        frame.setTitle("Tap");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final MainPanel mainPanel = new MainPanel(SYSTEM_CONFIGURATION);
        mainPanel.render();
        frame.setContentPane(mainPanel);
    }

    public static void changePanel(JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }
}