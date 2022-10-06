package ru.Demchuck;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Package {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String nameFile;
    private long sizeFile;
    private File file;
    private Socket socket;

    public Package(Socket socket) {
        try {
            this.socket = socket;
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void sendNameFiel(String path) {
        try {
            ClassLoader classLoader = Package.class.getClassLoader();
            file = new File(classLoader.getResource(path).getFile());
            this.nameFile = file.getName();
            int sizeFile = this.nameFile.length();
            dataOutputStream.writeInt(sizeFile);
            dataOutputStream.write(this.nameFile.getBytes(StandardCharsets.UTF_8), 0, sizeFile);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void sendSizeFile() {
        try {
            this.sizeFile = file.length();
            dataOutputStream.writeInt((int) this.sizeFile);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void sendFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int sizeLine = line.length();
                dataOutputStream.writeInt(sizeLine);
                dataOutputStream.write(line.getBytes(StandardCharsets.UTF_8), 0, sizeLine);
            }
            bufferedReader.close();
            String end = "end";
            dataOutputStream.writeInt(end.length());
            dataOutputStream.write(end.getBytes(StandardCharsets.UTF_8), 0, end.length());
            receiveAnswer();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    private void receiveAnswer() {
        try {
            int answer = dataInputStream.readInt();
            if (answer == 1) {
                System.out.println("The package has fully arrived");
            } else if (answer == 0) {
                System.out.println("The package has reached partially");
            }
            releaseResources();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    private void releaseResources() {
        try {
            socket.close();
            dataInputStream.close();
            dataOutputStream.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

}
