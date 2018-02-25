import MP1.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length != 1) {
            System.out.println("Wrong command line!\n");
            System.exit(-1);
        }

        Unicast u = new Unicast( Integer.parseInt(args[0]), Config.parseConfig("configFile") );
        u.startListen();

        Multicast m = new Multicast(u);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String s = br.readLine();
            if (s.equals("m"))
                m.multicast("THIS IS A MULTICAST MESSAGE");
        }
    }
}
