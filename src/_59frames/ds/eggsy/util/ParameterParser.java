package _59frames.ds.eggsy.util;

import _59frames.ds.eggsy.model.Argument;
import _59frames.ds.eggsy.model.Arguments;

import java.util.List;

public class ParameterParser {
    private final char parameterChar;

    public ParameterParser(final char parameterChar) {
        this.parameterChar = parameterChar;
    }

    public Arguments parse(String[] args) {
        var arguments = new Arguments();
        String[] argPair;
        for (var argString : args) {
            if (!argString.contains(String.format("%c", this.parameterChar))) continue;

            argPair = argString.split(String.format("%c", this.parameterChar));

            if (argPair.length != 2) continue;

            arguments.add(new Argument(argPair[0], argPair[1]));
        }
        return arguments;
    }

    public Arguments parse(String string) {
        return parse(string.split(" "));
    }

    public Arguments parse(List<String> args) {
        return parse(args.toArray(new String[0]));
    }
}
