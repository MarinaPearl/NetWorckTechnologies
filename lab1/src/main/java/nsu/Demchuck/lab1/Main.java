package nsu.Demchuck.lab1;

import java.io.IOException;

public class Main {
    public static void main(String args[]) {
           try {
               Sender sender = new Sender();
               System.out.println("cuyc");
               System.out.println("868");
               sender.run();
               Receiver receiver = new Receiver();
               receiver.run();

           }catch (IOException error) {
               error.printStackTrace();
           }
    }
}
