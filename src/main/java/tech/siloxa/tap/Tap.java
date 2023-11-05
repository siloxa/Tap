package tech.siloxa.tap;

import tech.siloxa.tap.model.Theme;
import tech.siloxa.tap.panel.MainPanel;
import tech.siloxa.tap.util.FontLoader;
import tech.siloxa.tap.util.ResponsiveUtils;

import javax.swing.*;
import java.awt.*;

public class Tap {

    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension FRAME_SIZE = new Dimension(380, 700);
    public static final Font FONT = FontLoader.load();

    public static JFrame frame;

    static {
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                frame.setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
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

        final MainPanel mainPanel = new MainPanel(Theme.LIGHT);
        mainPanel.render();
        frame.setContentPane(mainPanel);
    }
}