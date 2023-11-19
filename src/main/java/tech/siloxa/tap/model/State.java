package tech.siloxa.tap.model;

public enum State implements TranslatedEnum<State> {

    IDLE,
    WORK_START,
    REST_START,
    WORK_PAUSE,
    REST_PAUSE,
    WORK_RESUME,
    REST_RESUME;
}
