package tech.siloxa.tap.util;

import tech.siloxa.tap.Tap;

import java.awt.*;

public class ResponsiveUtils {

    public static Dimension getCenterOfDisplay(final Dimension screenSize, final Dimension frameSize) {
        return new Dimension(
                (int) screenSize.getWidth() / 2 - (int) frameSize.getWidth() / 2,
                (int) screenSize.getHeight() / 2 - (int) frameSize.getHeight() / 2
        );
    }

    public static int resolveXPosition(final Dimension frameSize, int width) {
        return (int) (frameSize.getWidth() / 2) - (width / 2);
    }
}
