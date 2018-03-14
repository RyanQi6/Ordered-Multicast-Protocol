import MP1.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {
    public static String[] global_args;
//    public static Config config;
    public static void main(String[] args) throws IOException, InterruptedException {
        global_args = args;
        if(args.length != 1) {
            System.out.println("Wrong command line!\n");
            System.exit(-1);
        }
        Config config = Config.parseConfig("configFile");
        Unicast u = new Unicast( Integer.parseInt(args[0]), config);
        u.startListen();
        MultiCastCausal co_m = new MultiCastCausal(u);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            String s = br.readLine();
            String[] strings = s.split(" ");
            if(strings.length == 3){

                u.unicast_send(Integer.parseInt(strings[1]), strings[2]);
            }
            else if(strings.length == 2){
                // for multi cast
                co_m.co_multiCast(strings[1]);
            }
        }
    }
}
