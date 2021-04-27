package by.lukyanets.acmesun.entity;

public enum Subject {
    EDUCATION("education"),
    IT("it"),
    MEDICINE("medicine"),
    POLITICS("politics"),
    SPORT("sport"),
    HELP("help");
    String value;

    public String getValue() {
        return value;
    }

    Subject(String value) {
        this.value = value;
    }
}
