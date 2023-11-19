package tech.siloxa.tap.model;

import java.util.Locale;
import java.util.ResourceBundle;

public interface TranslatedEnum<E extends Enum<E>> {

    default String translate(Language language) {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", new Locale(language.toString().toLowerCase()));

        final String messageKey = this.getClass().getSimpleName() + '.' + ((E) this).name().toLowerCase();

        return resourceBundle.getString(messageKey);
    }
}
