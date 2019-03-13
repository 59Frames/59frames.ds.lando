package _59frames.ds.eggsy.util;

import _59frames.ds.eggsy.model.Arguments;

import java.util.HashMap;
import java.util.List;

public class ParameterParser {
    private final char parameterChar;

    public ParameterParser(char parameterChar) {
        this.parameterChar = parameterChar;
    }

    public Arguments parse(String[] args) {
        HashMap<String, String> arguments = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == parameterChar) {
                arguments.put(args[i].substring(1), args.length > i+1 ? args[i + 1] : "");
            }
        }
        return new Arguments(arguments);
    }

    public Arguments parse(String string) {
        return parse(string.split(" "));
    }

    public Arguments parse(List<String> args) {
        return parse(args.toArray(new String[0]));
    }
}
