package tech.siloxa.tap.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import tech.siloxa.tap.model.SystemConfiguration;
import tech.siloxa.tap.model.Theme;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class SystemConfigurationUtils {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public static SystemConfiguration load() {
        try {
            return OBJECT_MAPPER.readValue(new File("tap.conf"), SystemConfiguration.class);
        } catch (IOException e) {
            final SystemConfiguration systemConfiguration = new SystemConfiguration();
            systemConfiguration.setTheme(Theme.LIGHT);
            systemConfiguration.setWorkTime(Duration.ofHours(2));
            systemConfiguration.setRestTime(Duration.ofMinutes(15));
            return systemConfiguration;
        }
    }

    public static void save(SystemConfiguration systemConfiguration) {
        try {
            OBJECT_MAPPER.writeValue(new File("tap.conf"), systemConfiguration);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
