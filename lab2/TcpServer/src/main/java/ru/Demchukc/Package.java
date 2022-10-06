package ru.Demchukc;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Package implements Runnable {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String nameFile;
    private int sizeFile;
    private File file;
    private FileWriter fileWriter;
    private int generalSize;
    private Socket socket;
    private int CORE_POOl_SIZE = 1;
    private int INITIAL_DELAY = 0;
    private int PERIOD = 3;
    private int time = 0;

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException error) {
            error.printStackTrace();
        }
        receiveNameFile();
        receiveSizeFile();
        createDir();
        receiveFile();
    }

    public Package(Socket socket) {
        this.socket = socket;
    }

    public void receiveNameFile() {
        try {
            int sizeNameFile = dataInputStream.readInt();
            byte[] array = new byte[sizeNameFile];
            dataInputStream.read(array);
            this.nameFile = new String(array, StandardCharsets.UTF_8);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void receiveSizeFile() {
        try {
            sizeFile = dataInputStream.readInt();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    private void speedCalculation() {
        time += PERIOD;
        int byt = generalSize / time;
        System.out.println("IP: " + socket.getInetAddress() + ", " + "Time: " + time + ", byte: " + byt);
    }

    public void receiveFile() {
        try {
            this.fileWriter = new FileWriter(this.file, true);
        } catch (IOException error) {
            error.printStackTrace();
        }
        try {
            var scheduledThreadPool = Executors.newScheduledThreadPool(CORE_POOl_SIZE);
            scheduledThreadPool.scheduleAtFixedRate(this::speedCalculation, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
            while (true) {
                int sizeLine = dataInputStream.readInt();
                generalSize += sizeLine;
                byte[] array = new byte[sizeLine];
                dataInputStream.read(array);
                String str = new String(array, StandardCharsets.UTF_8);
                if (str.equals("end")) {
                    generalSize -= sizeLine;
                    break;
                }
                fileWriter.write(str);
                fileWriter.write("\n");
                fileWriter.flush();
                //System.out.println(str);
            }
            sendMessageClient();
            scheduledThreadPool.shutdown();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void createDir() {
        File filePath = new File("TcpServer\\src\\main\\resources\\uploads");
        filePath.mkdir();
        this.file = new File(filePath + "\\" + this.nameFile);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageClient() {
        try {
            if (sizeFile == generalSize) {
                dataOutputStream.writeInt(1);
            } else dataOutputStream.writeInt(0);
            releaseResources();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    private void releaseResources() {
        try {
            this.socket.close();
            this.dataOutputStream.close();
            this.dataInputStream.close();
            this.fileWriter.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

}
