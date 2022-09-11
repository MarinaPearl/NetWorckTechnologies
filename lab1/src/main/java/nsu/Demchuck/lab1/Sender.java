package nsu.Demchuck.lab1;

import java.io.IOException;
import java.net.*;

public class Sender extends Thread{
       private DatagramSocket socket;
       private DatagramPacket packet;
       private final static int SERVICE_PORT = 8080;
       public Sender() throws SocketException {
           socket = new DatagramSocket();
       }
       public void run() {
           try {
               System.out.println("212");
               InetAddress address = InetAddress.getByName("localhost");
               byte[] sendingDataBuffer = new byte[1024];
               byte[] receiveDataBuffer = new byte[1024];
               String sentence = "Hello from UDP client";
               sendingDataBuffer = sentence.getBytes();
               System.out.println("000");
               DatagramPacket packet = new DatagramPacket(sendingDataBuffer,
                       sendingDataBuffer.length, address, SERVICE_PORT);
               System.out.println("131");
               socket.send(packet);
               System.out.println("222");
             //  packet = new DatagramPacket(receiveDataBuffer, receiveDataBuffer.length);
              // socket.receive(packet);
               //String string = new String(packet.getData());
              // System.out.println(string);
               socket.close();

           } catch (IOException e) {
               e.printStackTrace();
           }
       }
}
