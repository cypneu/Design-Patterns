package facade;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;


public class Peer {
    private static final String localHostAddress = "127.0.0.1";
    public final Thread serverThread;
    private final int serverPort;
    private final ServerSocket serverSocket;

    // use ports between 10_000 - 15_000
    private final ConcurrentHashMap<Integer, Socket> portsToSockets = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, DataOutputStream> portsToOut = new ConcurrentHashMap<>();

    public Peer(int serverPort) throws IOException {
        this.serverPort = serverPort;
        serverSocket = new ServerSocket(this.serverPort);

        serverThread = new Thread(() -> {
            try {
                handleServerSocket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose your port number [10000 - 15000]: ");
        int serverPort = Integer.parseInt(scanner.nextLine());

        Peer peer = new Peer(serverPort);

        // listening to commands from console
        while (true) {
            String[] mainArguments = scanner.nextLine().split("\"");
            String[] arguments = mainArguments[0].split(" ");

            switch (arguments[0]) {
                case "connect" -> {
                    peer.broadcastConnection();
                    System.out.println("Successfully connected to all users!");
                }
                case "send-one" -> {
                    if (arguments.length != 2 || !arguments[1].matches("-?\\d+"))
                        System.out.println("Invalid format of send-one command!");
                    else {
                        int recipientPortNumber = Integer.parseInt(arguments[1]);
                        if (recipientPortNumber < 1000 || recipientPortNumber > 15000)
                            System.out.println("Provided recipient ID is incorrect!");
                        else if (!peer.portsToSockets.containsKey(recipientPortNumber))
                            System.out.println("Provided recipient ID is not assigned to any user!");

                        peer.sendMessageToOne(recipientPortNumber, mainArguments[1]);
                    }
                }
                case "send-many" -> {
                    ArrayList<Integer> portNumbers = new ArrayList<>();
                    for (int i = 1; i < arguments.length; i++) {
                        try {
                            portNumbers.add(Integer.parseInt(arguments[i]));
                        } catch (NumberFormatException ignored) {
                        }
                    }

                    peer.sendMessageToMany(portNumbers, mainArguments[1]);
                }
                case "show-active" -> peer.printActiveNodes();
                case "disconnect" -> peer.disconnect();
            }
        }
    }

    public void handleServerSocket() throws IOException {
        while (true) {
            Socket acceptedSocket = serverSocket.accept();
            new Thread(new Runnable() {
                final Socket receivingSocket = acceptedSocket;
                final DataInputStream receivingInput = new DataInputStream(new BufferedInputStream(receivingSocket.getInputStream()));
                String line = "";
                @Override
                public void run() {
                    try {
                        while (true) {
                            line = receivingInput.readUTF();
                            String[] parsed = line.split(" ");
                            if (parsed.length == 3){
                                if (parsed[1].equals("disconnect")) {
                                    int disconnectedPort = Integer.parseInt(parsed[2]);

                                    if (disconnectedPort != serverPort) {
                                        sendMessageToOne(disconnectedPort, "disconnect " + disconnectedPort);
                                        System.out.println("User " + disconnectedPort + " disconnected!");
                                        portsToSockets.remove(disconnectedPort);
                                        portsToOut.remove(disconnectedPort);
                                    }

                                    receivingInput.close();
                                    receivingSocket.close();
                                    return;
                                }
                                else if (parsed[1].equals("connect")){
                                    int connectedPort = Integer.parseInt(parsed[2]);
                                    if (!portsToSockets.containsKey(connectedPort)){
                                        portsToSockets.put(connectedPort, new Socket(localHostAddress, connectedPort));
                                        portsToOut.put(connectedPort, new DataOutputStream(portsToSockets.get(connectedPort).getOutputStream()));
                                    }

                                    continue;
                                }
                            }

                            System.out.println(line);
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }

    public void sendMessageToOne(int sendingPort, String message) throws IOException {
        portsToOut.get(sendingPort).writeUTF( "[" + serverPort + "]: " + message);
    }

    public void sendMessageToMany(ArrayList<Integer> sendingPorts, String message) throws IOException {
        for (Integer portNumber : sendingPorts)
            portsToOut.get(portNumber).writeUTF("[" + serverPort + "]: " + message);
    }

    public void broadcastConnection() {
        for (int port = 10000; port <= 15000; port++) {
            if (port != serverPort) {
                try {
                    portsToSockets.put(port, new Socket(localHostAddress, port));
                    portsToOut.put(port, new DataOutputStream(portsToSockets.get(port).getOutputStream()));
                    sendMessageToOne(port, "connect " + serverPort);
                } catch (IOException ignored) {
                }
            }
        }
    }

    public void disconnect() throws IOException {
        sendMessageToMany(new ArrayList<>(portsToSockets.keySet()), "disconnect " + serverPort);
        portsToSockets.clear();
        portsToOut.clear();
    }

    public void printActiveNodes() {
        System.out.println("\nList of active users:");
        for (Integer portNumber : portsToSockets.keySet())
            System.out.println(portNumber);
        System.out.println();
    }
}
