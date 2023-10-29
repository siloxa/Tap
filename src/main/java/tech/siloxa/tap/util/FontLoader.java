package tech.siloxa.tap.util;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {

    public static Font load() {
        try (InputStream inputStream = FontLoader.class.getClassLoader().getResourceAsStream("sf-pro-text-regular.ttf")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("file not found!");
            }
            return Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
