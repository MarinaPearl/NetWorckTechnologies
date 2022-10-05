package ru.Demchuck;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            Package aPackage = new Package(socket);
            aPackage.sendNameFiel("file.txt");
            aPackage.sendSizeFile();
            aPackage.sendFile();
        }catch (IOException error){
            error.printStackTrace();
        }
    }
}
