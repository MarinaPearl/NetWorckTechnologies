package ru.Demchukc;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            serverSocket = new ServerSocket(8080);
            System.out.println("server started");
            Socket socket = serverSocket.accept();
            Package receivePackege = new Package(socket);
            executorService.submit(receivePackege);
        }catch (IOException error) {
            error.printStackTrace();
        }finally {
            serverSocket.close();
        }
    }
}
