package nsu.Demchuck.lab1;

import java.io.IOException;
import java.net.*;
import java.util.UUID;

public class Sender extends Thread {
    private MulticastSocket socket;
    private final static int SERVICE_PORT = 8000;

    public Sender() throws IOException {
       // socket.setSoTimeout(1000);
        socket = new MulticastSocket(SERVICE_PORT);
    }

    // @Override
    public void run() {
        try {
            byte[] sendingDataBuffer;
            UUID uid = UUID.randomUUID();
            sendingDataBuffer = UuidUtils.asBytes(uid);
            DatagramPacket packet = new DatagramPacket(sendingDataBuffer,
                    sendingDataBuffer.length, InetAddress.getByName("224.1.1.1"), SERVICE_PORT);
            while (true) {
                socket.send(packet);
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
