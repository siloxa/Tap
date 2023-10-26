package tech.siloxa.tap;

import tech.siloxa.tap.util.ResponsiveUtils;

import javax.swing.*;
import java.awt.*;

public class Tap {

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private final Dimension frameSize = new Dimension(380, 600);

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
        frame.setTitle("Tap");
        frame.setBounds(
                (int) centerOfDisplay.getWidth(), (int) centerOfDisplay.getHeight(),
                (int) frameSize.getWidth(), (int) frameSize.getHeight()
        );
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}