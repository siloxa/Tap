package tech.siloxa.tap.model;

public enum State {

    IDLE("Hello!"),
    WORK_START("Working"),
    REST_START("Resting"),
    WORK_PAUSE("Pause!"),
    REST_PAUSE("Pause!"),
    WORK_RESUME("Working"),
    REST_RESUME("Working");

    private final String header;

    State(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
