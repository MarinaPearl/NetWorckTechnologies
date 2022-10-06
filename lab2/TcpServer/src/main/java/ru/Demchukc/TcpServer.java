package ru.Demchukc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer {
    private static int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            serverSocket = new ServerSocket(PORT);
            System.out.println("server started");
            while (!serverSocket.isClosed()) {
                socket = serverSocket.accept();
                Package receivePackege = new Package(socket);
                executorService.submit(receivePackege);
            }
        } catch (IOException error) {
            error.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }
}
