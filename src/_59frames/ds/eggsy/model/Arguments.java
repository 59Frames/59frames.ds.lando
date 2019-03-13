package _59frames.ds.eggsy.model;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class Arguments {
    private HashMap<String, String> args;

    public Arguments(HashMap<String, String> args) {
        this.args = args;
    }

    public void addArgument(String name, String value) {
        args.put(name, value);
    }

    public String getArgument(String name) {
        return args.get(name);
    }

    public boolean hasArgument(String name) {
        return args.containsKey(name);
    }

    public String[] keys() {
        return args.keySet().toArray(new String[0]);
    }

    public String[] values() {
        return args.values().toArray(new String[0]);
    }

    public void forEach(BiConsumer<String, String> action) {
        this.args.forEach(action);
    }
}
