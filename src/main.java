import MP1.*;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length != 1) {
            System.out.println("Wrong command line!\n");
            System.exit(-1);
        }

        Unicast u = new Unicast( Integer.parseInt(args[0]), Config.parseConfig("configFile") );
        u.startListen();

    }
}
