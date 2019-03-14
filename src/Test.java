import _59frames.ds.lando.CommandListener;
import _59frames.ds.lando.model.Command;

public class Test {
    public static void main(String[] args) {

        var listener = CommandListener.builder()
                .paramChar(':')
                .hasDefaultHelpCommand(true)
                .build();

        listener.add(
                new Command("print",
                        args1 -> {
                            if (args1.hasArgument("age")) {
                                System.out.println(String.format("My name is %s and I am %d years old", args1.get("name"), args1.get("age").getInteger()));
                            } else {
                                System.out.println(String.format("My name is %s", args1.get("name")));
                            }
                        },
                        new String[]{"name"},
                        "age")
        );

        listener.start();
    }
}
