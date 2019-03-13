import _59frames.ds.lando.CommandListener;
import _59frames.ds.lando.model.Command;

public class Test {
    public static void main(String[] args) {

        var listener = CommandListener.builder()
                .paramChar(':')
                .build();

        listener.add(new Command("print", args1 -> System.out.println(String.format("My name is %s", args1.get("name"))), "name"));

        listener.start();
    }
}
