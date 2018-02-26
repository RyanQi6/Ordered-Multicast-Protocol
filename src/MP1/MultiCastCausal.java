package MP1;
import java.io.IOException;
import java.util.*;
public class MultiCastCausal {
    class key_value{
        int[] timeStamp;
        int fromWho;
        String message;
        key_value(int[] timeStamp, int fromWho, String message){
            this.timeStamp = Arrays.copyOf(timeStamp,timeStamp.length);
            this.fromWho = fromWho;
            this.message = message;
        }
    }

    Unicast u;
    List<key_value> holdBackQueue;
    int[] timeStamp;
    Config config;
    int ID;

    public MultiCastCausal(Unicast u)throws IOException{
        this.u = u;
        this.holdBackQueue = new ArrayList<key_value>();
        this.config = Config.parseConfig("configFile");
        this.timeStamp = new int[config.idList.size()];
        this.ID = u.ID;
        startListen();
    }

    public void co_multiCast(String message) throws IOException, InterruptedException{
        String final_message = "";
        this.timeStamp[this.ID] += 1;
        for(int i=0; i<timeStamp.length; i++) {
            final_message += Integer.toString(this.timeStamp[i]);
            if(i<timeStamp.length-1)
                final_message +=",";
        }
        final_message += "||" + this.ID + "||" + message;
        for(int i:config.idList){
            if(i != this.ID) {
                u.unicast_send(i, final_message);
            }
        }
    }
    public void b_deliver()throws IOException,InterruptedException{
        String message;
        for(int index:u.hostInfo.idList){
            while((message = u.unicast_receive(u.hostInfo.idList.get(index))) != null){
                //System.out.println("initial String message:" + message);
                String[] time_ID_Message = message.split("\\u007C\\u007C");
                String[] time = time_ID_Message[0].split(",");
                int[] exTimeStamp = new int[this.timeStamp.length];
                System.out.println("timeStamp in String:" + time_ID_Message[0]);
                for(int i=0; i<this.timeStamp.length; i++){
                    exTimeStamp[i] = Integer.parseInt(time[i]);
                }
                this.holdBackQueue.add(new key_value(exTimeStamp, Integer.parseInt(time_ID_Message[1]), time_ID_Message[2]));
            }
        }
        if(this.holdBackQueue.size()!=0){
            for(int i=0; i<this.holdBackQueue.size(); i++){
                key_value current = this.holdBackQueue.get(i);
                if(check_co_condition(current)){
                    System.out.println("Sender ID: " + current.fromWho + " System Time: " + System.currentTimeMillis() + " Message: " + current.message);
                    //this.timeStamp = Arrays.copyOf(current.timeStamp,current.timeStamp.length);
                    this.holdBackQueue.remove(i);
                    i--;
                    this.timeStamp[current.fromWho] += 1;
                }
            }
        }
    }
    private boolean check_co_condition(key_value current){
        if(current.timeStamp[current.fromWho] != this.timeStamp[current.fromWho] + 1){
            return false;
        }
        else{
            for(int k=0; k<holdBackQueue.size(); k++){
                if(k != current.fromWho){
                    if(current.timeStamp[k] > this.timeStamp[k])
                        return false;
                }
            }
            return true;
        }
    }
    public void startListen() {
        Runnable listener = new Runnable() {
            @Override
            public void run() {
                try {
                    while(true)
                        b_deliver();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(listener).start();
    }

}
