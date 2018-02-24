package MP1;

import java.io.IOException;
import java.util.*;

public class Unicast {
    int ID;
    Config hostInfo;

    Server s;
    Client c;

    //buffer to store the messages received but not delivered yet.
    //it is a fifo queue for each ID
    Map<Integer, Queue<String>> messageBuffer;

    public Unicast(int ID, Config hostInfo) throws IOException{
        this.ID = ID;
        this.hostInfo = hostInfo;
        s = new Server(hostInfo.addrMap.get(ID).getHostName(), hostInfo.addrMap.get(ID).getPort());
        c = new Client();

        messageBuffer = new HashMap<>();
        for(Integer i : hostInfo.idList)
            messageBuffer.put(i, new LinkedList<>());
    }

    public void startListen(){
        Runnable server = new Runnable() {
            @Override
            public void run() {
                try {
                    s.startServer(messageBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(server).start();
    }

    public void unicast_send(int destination, String message) throws IOException, InterruptedException{
        c.startClient(hostInfo.addrMap.get(destination).getHostName(), hostInfo.addrMap.get(destination).getPort());
        c.sendMessage(ID + "||" + message);
        c.closeClient();
    }

    public String unicast_receive(int source){
        return messageBuffer.get(source).poll();
    }
}
