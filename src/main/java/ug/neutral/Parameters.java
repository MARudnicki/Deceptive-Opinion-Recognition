package ug.neutral;

public enum Parameters {

    ROWS(10),
    FEATURES(2),
    Y_INDEX(2),
    ANSWER(1);

    private final int value;

    Parameters(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
