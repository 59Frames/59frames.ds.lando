import _59frames.ds.lando.CommandListener;
import _59frames.ds.lando.model.Command;

public class Test {
    public static void main(String[] args) {

        var listener = CommandListener.builder()
                .paramChar('=')
                .hasDefaultHelpCommand(true)
                .build();

        listener.start();
    }
}
