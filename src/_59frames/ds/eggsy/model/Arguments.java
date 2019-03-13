package _59frames.ds.eggsy.model;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class Arguments {
    private final HashMap<String, Argument> args;

    public Arguments() {
        this(new HashMap<>());
    }

    public Arguments(HashMap<String, Argument> args) {
        this.args = args;
    }

    public void add(Argument arg) {
        args.put(arg.getKey(), arg);
    }

    public Argument get(String key) {
        return args.get(key);
    }

    public boolean hasArgument(String key) {
        return args.containsKey(key);
    }

    public String[] keys() {
        return args.keySet().toArray(new String[0]);
    }

    public Argument[] values() {
        return args.values().toArray(new Argument[0]);
    }

    public void forEach(BiConsumer<String, Argument> action) {
        this.args.forEach(action);
    }
}
