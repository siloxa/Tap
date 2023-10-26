package tech.siloxa.tap.util;

import java.awt.*;

public class ResponsiveUtils {

    public static Dimension getCenterOfDisplay(final Dimension screenSize, final Dimension frameSize) {
        return new Dimension(
                (int) screenSize.getWidth() / 2 - (int) frameSize.getWidth() / 2,
                (int) screenSize.getHeight() / 2 - (int) frameSize.getHeight() / 2
        );
    }
}
