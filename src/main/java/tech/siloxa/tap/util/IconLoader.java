package tech.siloxa.tap.util;


import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IconLoader {

    public static ImageIcon load(String icon) {
        final URL resource = IconLoader.class.getClassLoader().getResource(icon + ".png");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        }

        return new ImageIcon(resource);
    }

    public static ImageIcon loadWithResize(String icon, int width, int height) {
        return new ImageIcon(
                load(icon).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
    }
}
