package nsu.Demchuck.lab1;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CheckLive extends Thread{
    private HashMap<UUID, InetAddress> listClients;
    private HashMap<UUID, Long> lsitTime;
    CheckLive(HashMap<UUID, InetAddress> list, HashMap<UUID, Long> listLive) {
            this.listClients = list;
            this.lsitTime = listLive;
    }
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);
    public void checkAliveCopies() {
        final Runnable checker = new Runnable() {
            public void run() {
                for (Map.Entry<UUID, Long> entry : lsitTime.entrySet()) {
                    if (System.currentTimeMillis() - entry.getValue() > 1000) {
                        if (listClients.containsKey(entry.getKey())) {
                            listClients.remove(entry.getKey());
                            System.out.println("The number of copies has changed");
                            for (InetAddress value : listClients.values()) {
                                System.out.println(value);
                            }
                        }
                        lsitTime.remove(entry.getKey());
                    }
                }
            }
        };
        final ScheduledFuture<?> checkerHandle = scheduler.scheduleAtFixedRate(checker, 0, 1, TimeUnit.SECONDS);
    }
}
