package tech.siloxa.tap.util;

import tech.siloxa.tap.model.Language;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {

    public static Font load(final Language language) {
        try (InputStream inputStream = FontLoader.class.getClassLoader().getResourceAsStream(resolveFontFile(language))) {
            if (inputStream == null) {
                throw new IllegalArgumentException("file not found!");
            }
            return Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String resolveFontFile(Language language) {
        return switch (language) {
            case EN, DE -> "fonts/sf-pro-text-regular.ttf";
            case FA -> "fonts/Vazir-Regular.ttf";
        };
    }
}
