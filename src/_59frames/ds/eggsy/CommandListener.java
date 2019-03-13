package _59frames.ds.eggsy;

import _59frames.ds.eggsy.model.Command;
import _59frames.ds.eggsy.util.ParameterParser;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
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

    public CommandListener(@NotNull final InputStream stream) {
        this(stream, System.err, ':');
    }

    public CommandListener(@NotNull final InputStream inputStream, @NotNull final PrintStream outputStream, final char parameterChar) {
        this.output = outputStream;
        this.scanner = new Scanner(inputStream);
        this.parser = new ParameterParser(parameterChar);
    }

    public void addCommand(String name, Command command) {
        commands.put(name.toLowerCase(), command);
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

            for (var name : c.getRequiredArgs()) {
                if (!args.hasArgument(name)) {
                    missingParameter(name);
                    return;
                }
            }

            for (var name : args.keys()) {
                if (!(c.getRequiredArgs().contains(name) || c.getOptionalArgs().contains(name))) {
                    unknownParameter(name);
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
        output.println(String.format("Unknown command %s", command));
    }

    private void missingParameter(String parameter) {
        output.println(String.format("Missing parameter %s", parameter));
    }

    private void unknownParameter(String parameter) {
        output.println(String.format("Unknown parameter %s", parameter));
    }
}
