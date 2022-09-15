package nsu.Demchuck.lab1;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.UUID;

public class Receiver extends Thread {
    private MulticastSocket socket;
    private boolean running;
    private byte[] buf = new byte[1024];
    public final static int SERVICE_PORT = 8000;
    private HashMap<UUID, InetAddress> list;
    private Checker checker;

    public Receiver() throws IOException {
        socket = new MulticastSocket(SERVICE_PORT);
        socket.setSoTimeout(1000);
        socket.joinGroup(InetAddress.getByName("224.1.1.1"));
        checker = new Checker();
        CheckLive checkLive = new CheckLive(checker.getListClients(), checker.getLsitTime());
        checkLive.checkAliveCopies();
    }

    //@Override
    public void run() {
        running = true;
        //  while (running) {
        DatagramPacket packet =
                new DatagramPacket(buf, buf.length);
        try {
            while (running) {
                socket.receive(packet);
                UUID uid = UuidUtils.asUuid(packet.getData());
                checker.putInLiveHash(uid);
                if (!checker.checkClients(uid)) {
                    checker.putInListClients(uid, packet.getAddress());
                    list = checker.getListClients();
                    System.out.println("List of Clients " + list.size());
                    if (!list.isEmpty()) {
                        for (InetAddress ip : list.values()) {
                            System.out.println("ip : " + ip);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.leaveGroup(InetAddress.getByName("224.1.1.1"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket.close();
        }
        //}
    }

}
