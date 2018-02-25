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

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            String s = br.readLine();
            if(s.equals("s0"))
                u.unicast_send(0, "msgTo0");
            if(s.equals("s1"))
                u.unicast_send(1, "msgTo1");
            if(s.equals("r0"))
                System.out.println(u.unicast_receive(0));
            if(s.equals("r1"))
                System.out.println(u.unicast_receive(1));
        }
    }
}
