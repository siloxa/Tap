package tech.siloxa.tap.model;

import java.time.Duration;

public class SystemConfiguration {

    private Theme theme;

    private Language language;

    private Duration workTime;

    private Duration restTime;

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Duration getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Duration workTime) {
        this.workTime = workTime;
    }

    public Duration getRestTime() {
        return restTime;
    }

    public void setRestTime(Duration restTime) {
        this.restTime = restTime;
    }
}
