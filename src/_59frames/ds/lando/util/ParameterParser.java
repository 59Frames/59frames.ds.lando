package _59frames.ds.lando.util;

import _59frames.ds.lando.model.Argument;
import _59frames.ds.lando.model.Arguments;

import java.util.List;

public class ParameterParser {
    private final char parameterChar;
    private final boolean isNamed;

    public ParameterParser(final char parameterChar, final boolean isNamed) {
        this.parameterChar = parameterChar;
        this.isNamed = isNamed;
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
}
