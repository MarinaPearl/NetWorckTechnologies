package nsu.Demchuck.lab1;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
           try {
//               Scanner scanner = new Scanner(System.in);
//               String adress = scanner.nextLine();
               Sender sender = new Sender();
               sender.start();
               Receiver receiver = new Receiver();
               receiver.start();


           }catch (IOException error) {
               error.printStackTrace();
           }
    }
}
