package tech.siloxa.tap;

import tech.siloxa.tap.panel.MainPanel;
import tech.siloxa.tap.util.ResponsiveUtils;

import javax.swing.*;
import java.awt.*;

public class Tap {

    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static final Dimension frameSize = new Dimension(380, 600);

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    final Tap tap = new Tap();
                    tap.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Tap() {
        initialize();
    }

    private void initialize() {
        final Dimension centerOfDisplay = ResponsiveUtils.getCenterOfDisplay(screenSize, frameSize);

        frame = new JFrame();
        frame.setBounds(
                (int) centerOfDisplay.getWidth(), (int) centerOfDisplay.getHeight(),
                (int) frameSize.getWidth(), (int) frameSize.getHeight()
        );
        frame.setTitle("Tap");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final MainPanel mainPanel = new MainPanel();
        mainPanel.render();
        frame.setContentPane(mainPanel);
    }
}