package ru.Demchuck;


import java.io.IOException;
import java.net.Socket;

public class TcpClient {
    private static int PORT = 8080;

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("insufficient number of arguments: enter two argument : ip address and name file");
            System.exit(1);
        }
        try {
            Socket socket = new Socket(args[0], PORT);
            Package aPackage = new Package(socket);
            aPackage.sendNameFiel(args[1]);
            aPackage.sendSizeFile();
            aPackage.sendFile();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
