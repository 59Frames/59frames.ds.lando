package _59frames.ds.lando;

import _59frames.ds.lando.model.Command;
import _59frames.ds.lando.util.ParameterParser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class CommandListener {
    private final PrintStream output;
    private final Scanner scanner;
    private final ParameterParser parser;

    private HashMap<String, Command> commands = new HashMap<>();

    private boolean running = false;

    public CommandListener() {
        this(System.in);
    }

    public CommandListener(final char parameterChar) {
        this(System.in, System.out, parameterChar);
    }

    public CommandListener(@NotNull final InputStream stream) {
        this(stream, System.err, '=');
    }

    public CommandListener(@NotNull final InputStream inputStream, @NotNull final OutputStream outputStream, final char parameterChar) {
        this.output = new PrintStream(outputStream);
        this.scanner = new Scanner(inputStream);
        this.parser = new ParameterParser(parameterChar);
    }

    @NotNull
    @Contract(" -> new")
    public static Builder builder() {
        return new Builder();
    }

    public void add(Command command) {
        this.commands.put(command.getKey(), command);
    }

    public void start() {
        if (isRunning()) return;

        running = true;
        new Thread(() -> {
            while (running) {
                listen();
            }
        }).start();
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    private void listen() {
        try {
            String s = scanner.nextLine();
            if (s != null)
                interpret(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void interpret(@NotNull String s) {
        final var input = clean(s).split("\\s");

        if (input.length < 1)
            return;

        final var key = input[0].toLowerCase();
        if (commands.containsKey(key)) {
            var c = commands.get(key);

            var argStrings = new String[input.length - 1];
            System.arraycopy(input, 1, argStrings, 0, argStrings.length);

            var args = parser.parse(argStrings);

            if (c.hasRequiredArgs()) {
                for (var name : c.getRequiredArgs()) {
                    if (!args.hasArgument(name)) {
                        missingParameter(name);
                        return;
                    }
                }
            }

            if (c.hasOptionalArgs()) {
                for (var name : args.keys()) {
                    if (!(c.getRequiredArgs().contains(name) || c.getOptionalArgs().contains(name))) {
                        unknownParameter(name);
                    }
                }
            }

            c.execute(args);
        } else {
            unknownCommand(key);
        }
    }

    @NotNull
    private String clean(@NotNull String string) {
        string = string.trim().strip();
        string = string.replaceAll("\\s{2,}", " ");

        return string;
    }

    private void unknownCommand(String command) {
        output.println(String.format("Unknown command { %s }", command));
    }

    private void missingParameter(String parameter) {
        output.println(String.format("Missing parameter { %s }", parameter));
    }

    private void unknownParameter(String parameter) {
        output.println(String.format("Unknown parameter { %s }", parameter));
    }

    public static class Builder {
        private char parameterChar;
        private InputStream inputStream;
        private OutputStream outputStream;

        public Builder() {
            this.parameterChar = '=';
            this.outputStream = System.err;
            this.inputStream = System.in;
        }

        public Builder paramChar(final char pChar) {
            this.parameterChar = pChar;
            return this;
        }

        public Builder input(@NotNull InputStream input) {
            this.inputStream = input;
            return this;
        }

        public Builder output(@NotNull OutputStream output) {
            this.outputStream = output;
            return this;
        }

        public CommandListener build() {
            return new CommandListener(inputStream, outputStream, parameterChar);
        }
    }
}
