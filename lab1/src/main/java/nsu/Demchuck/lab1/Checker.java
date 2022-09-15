package nsu.Demchuck.lab1;

import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Checker  {
    private HashMap<UUID, InetAddress> listClients;
    private HashMap<UUID, Long> lsitTime;
    private UUID id;

    public Checker() {
        listClients = new HashMap<>();
        lsitTime = new HashMap<>();
    }
    public void putInLiveHash(UUID id) {
        lsitTime.put(id, System.currentTimeMillis());
    }

    public HashMap<UUID, Long> getLsitTime() {
        return lsitTime;
    }

    public HashMap<UUID, InetAddress> getListClients() {
        return listClients;
    }

    public void putInListClients(UUID id, InetAddress address) {
        if (!listClients.containsKey(id)) {
            this.id = id;
            listClients.put(id, address);
        }
    }

    public boolean checkClients(UUID id) {
        if (listClients.containsKey(id)) {
            return true;
        } else {
            return false;
        }
    }

    public void removeClient(UUID id) {
        listClients.remove(id);
    }
}
