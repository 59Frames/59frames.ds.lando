package _59frames.ds.eggsy.model;

public class Argument {
    private final String key;
    private final String value;

    public Argument(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public boolean getBool() {
        return Boolean.parseBoolean(this.value);
    }

    public double getDouble() {
        return Double.parseDouble(this.value);
    }

    public int getInteger() {
        return Integer.parseInt(this.value);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
