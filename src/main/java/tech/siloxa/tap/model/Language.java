package tech.siloxa.tap.model;

public enum Language {

    EN("English"),
    DE("Deutsch"),
    FA("Persian");

    final String name;

    Language(String name) {
        this.name = name;
    }

    public static Language resolveName(String name) {
        for (Language language : Language.values()) {
            if (language.getName().equalsIgnoreCase(String.valueOf(name))) {
                return language;
            }
        }
        throw new IllegalArgumentException();
    }

    public static int resolveEnumOrder(Language language) {
        for (int i = 0; i < Language.values().length; i++) {
            if (Language.values()[i] == language) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getName() {
        return name;
    }
}
