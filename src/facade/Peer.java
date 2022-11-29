package facade;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Peer {
    private static final String localHostAddress = "127.0.0.1";
    private final int serverPort;
    private final ServerSocket serverSocket;
    private final Thread serverThread;
    private Socket receivingSocket;
    private DataInputStream receivingInput;

    private Socket sendingSocket;
    private DataOutputStream out;
    private BufferedReader sendingInput;
    private Thread sendingThread;

    // mapping port -> Socket
    // port will identify the userID
    // use ports between 10_000 - 15_000
    private final ConcurrentHashMap<Integer, Socket> portsToSocketsMapping = new ConcurrentHashMap<>();

    public Peer(int serverPort) throws IOException {
        this.serverPort = serverPort;
        serverSocket = new ServerSocket(this.serverPort);

        System.out.println("Server is listening for connections!");
        serverThread = new Thread(this::handleServerSocket);
        serverThread.start();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose your port number: ");
        int serverPort = Integer.parseInt(scanner.nextLine());

        Peer peer = new Peer(serverPort);

        System.out.print("Connect to user on port number: ");
        int sendingPort = Integer.parseInt(scanner.nextLine());

        peer.handleSendingSocket(sendingPort);

        peer.sendingThread.join();
        peer.serverThread.join();
    }

    private void handleServerSocket() {
        try {
            receivingSocket = serverSocket.accept();
            System.out.println("\nThe connection has been established!");

            receivingInput = new DataInputStream(new BufferedInputStream(receivingSocket.getInputStream()));

            String line = "";
            while (!line.equals("end")) {
                line = receivingInput.readUTF();
                System.out.println(line);
            }

            receivingSocket.close();
            receivingInput.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleSendingSocket(int sendingPort) throws IOException {
        sendingSocket = new Socket(localHostAddress, sendingPort);
        sendingThread = new Thread(() -> {
            try {
                this.sendingInput = new BufferedReader(new InputStreamReader(System.in));
                out = new DataOutputStream(sendingSocket.getOutputStream());

                String line = "";
                while (!line.equals("end")) {
                    line = sendingInput.readLine();
                    out.writeUTF("[" + serverPort + "] " + line);
                }

                sendingInput.close();
                out.close();
                sendingSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        sendingThread.start();
    }
}
