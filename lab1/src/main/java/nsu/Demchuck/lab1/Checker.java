package nsu.Demchuck.lab1;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.UUID;

public class Checker {
    private HashMap<UUID, InetAddress> listClients;
    public Checker() {
        listClients = new HashMap<>();
    }

    public HashMap<UUID, InetAddress> getListClients() {
        return listClients;
    }

    public void putInListClients(UUID id, InetAddress address) {
        listClients.put(id, address);
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
