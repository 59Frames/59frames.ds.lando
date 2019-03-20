package _59frames.ds.lando.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;

public class Arguments {
    private final LinkedHashMap<String, Argument> args;

    public Arguments() {
        this(new LinkedHashMap<>());
    }

    public Arguments(LinkedHashMap<String, Argument> args) {
        this.args = args;
    }

    public void add(Argument arg) {
        args.put(arg.getKey(), arg);
    }

    public Argument get(String key) {
        return args.get(key);
    }

    public boolean has(String key) {
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

    public int size() {
        return this.args.size();
    }

    public Iterator<Argument> iterator() {
        return this.args.values().iterator();
    }
}
