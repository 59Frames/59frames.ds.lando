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

    private CommandListener(@NotNull final InputStream inputStream, @NotNull final OutputStream outputStream, @NotNull final ParameterParser parser) {
        this.output = new PrintStream(outputStream);
        this.scanner = new Scanner(inputStream);
        this.parser = parser;
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
            if (s != null && !(s.isEmpty() || s.isBlank()))
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

            var args = parser.parse(argStrings, c);

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
        private boolean isNamed;
        private boolean hasHelpCommand;

        public Builder() {
            this.parameterChar = '=';
            this.outputStream = System.err;
            this.inputStream = System.in;
            this.isNamed = true;
            this.hasHelpCommand = true;
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

        public Builder hasNamedArguments(final boolean val) {
            this.isNamed = val;
            return this;
        }

        public Builder hasDefaultHelpCommand(final boolean val) {
            this.hasHelpCommand = val;
            return this;
        }

        public CommandListener build() {
            final var instance = new CommandListener(inputStream, outputStream, new ParameterParser(parameterChar, isNamed));

            if (hasHelpCommand) {
                instance.add(new Command("help", args -> {
                    final var leftAlignFormat = "| %-15s | %-26s | %-26s |%n";

                    instance.output.format("+-----------------+----------------------------+----------------------------+%n");
                    instance.output.format("| Command name    | Required Arguments         | Optional Arguments         |%n");
                    instance.output.format("+-----------------+----------------------------+----------------------------+%n");

                    instance.commands.forEach((key, command) -> instance.output.format(leftAlignFormat, key, command.getRequiredArgs(), command.getOptionalArgs()));

                    instance.output.format("+-----------------+----------------------------+----------------------------+%n");
                }));
            }

            return instance;
        }
    }
}
