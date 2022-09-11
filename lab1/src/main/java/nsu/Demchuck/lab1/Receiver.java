package nsu.Demchuck.lab1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Receiver extends Thread {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    public final static int SERVICE_PORT=8080;
    public Receiver() throws SocketException {
        socket = new DatagramSocket(SERVICE_PORT);
    }

    public void run() {
        running = true;
       // while (running) {
        System.out.println("hello");
            DatagramPacket packet =
                    new DatagramPacket(buf, buf.length);
        System.out.println("7789");
            try {
                socket.receive(packet);
                System.out.println("123456");
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                System.out.println("111");
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());
//                if (received.equals("end")) {
//                    running = false;
//                    continue;
//                }
                System.out.println(received);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        //}
        socket.close();
    }

}
