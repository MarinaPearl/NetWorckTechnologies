package nsu.Demchuck.lab1;

import java.io.IOException;

public class Main {
    public static void main(String args[]) {
           try {
               Sender sender = new Sender();
               Receiver receiver = new Receiver();
               sender.start();
               receiver.start();


           }catch (IOException error) {
               error.printStackTrace();
           }
    }
}
