package tech.siloxa.tap.util;


import tech.siloxa.tap.model.Theme;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IconLoader {

    public static ImageIcon load(String icon, Theme theme) {
        final URL resource = IconLoader.class.getClassLoader().getResource(
                icon + "-" + theme.toString().toLowerCase() + ".png"
        );
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        }

        return new ImageIcon(resource);
    }
}
