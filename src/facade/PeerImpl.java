package facade;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public class PeerImpl implements Peer {
    private static final String localHostAddress = "127.0.0.1";
    private final int serverPort;

    // use ports between 10_000 - 15_000
    private final ConcurrentHashMap<Integer, Socket> portsToSendSockets = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, DataInputStream> portsToIn = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, DataOutputStream> portsToOut = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, Thread> portsToListeningThread = new ConcurrentHashMap<>();

    private ServerSocket serverSocket;
    private Thread serverThread;
    private volatile boolean connected;

    public PeerImpl(int serverPort) {
        this.serverPort = serverPort;
    }

    public void handleServerSocket() throws IOException {
        while (connected) {
            Socket acceptedSocket = serverSocket.accept();
            new Thread(new Runnable() {
                final Socket receivingSocket = acceptedSocket;
                final DataInputStream connectInput = new DataInputStream(receivingSocket.getInputStream());

                @Override
                public void run() {
                    try {
                        String line = connectInput.readUTF();
                        String[] parsedMessage = line.split(" ");
                        int connectedPort = Integer.parseInt(parsedMessage[2]);
                        System.out.println("Accepted new connection on " + connectedPort + "!");

                        portsToIn.put(connectedPort, connectInput);
                        if (!portsToSendSockets.containsKey(connectedPort) || portsToSendSockets.get(connectedPort).isClosed()) {
                            portsToSendSockets.put(connectedPort, new Socket(localHostAddress, connectedPort));
                            portsToOut.put(connectedPort, new DataOutputStream(portsToSendSockets.get(connectedPort).getOutputStream()));
                            sendMessageToOne(connectedPort, "connect " + serverPort);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }

    public void sendMessageToOne(int sendingPort, String message) {
        if (sendingPort < 10000 || sendingPort > 15000)
            System.out.println("Provided recipient ID is incorrect!");
        else if (!portsToSendSockets.containsKey(sendingPort))
            System.out.println("Provided recipient ID is not assigned to any user!");
        else {
            try {
                portsToOut.get(sendingPort).writeUTF("[" + serverPort + "]: " + message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessageToMany(ArrayList<Integer> sendingPorts, String message) {
        for (Integer portNumber : sendingPorts) {
            try {
                portsToOut.get(portNumber).writeUTF("[" + serverPort + "]: " + message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void registerIncomingMessages(int portNumber) {
        portsToListeningThread.put(portNumber, new Thread(() -> {
            try {
                final DataInputStream receivingInput = portsToIn.get(portNumber);
                while (true) {
                    String line = receivingInput.readUTF();
                    String[] parsed = line.split(" ");
                    if (parsed.length == 3) {
                        if (parsed[1].equals("disconnect")) {
                            int disconnectedPort = Integer.parseInt(parsed[2]);

                            if (disconnectedPort != serverPort) {
                                sendMessageToOne(disconnectedPort, "disconnect " + disconnectedPort);
                                System.out.println("User " + disconnectedPort + " disconnected!");
                                portsToSendSockets.remove(disconnectedPort);
                                portsToOut.remove(disconnectedPort);
                            }

                            receivingInput.close();
                            return;
                        }
                    }
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Unregistered " + portNumber + "!");
            }
        }));
        portsToListeningThread.get(portNumber).start();
    }

    public void unregisterIncomingMessages(int portNumber) {
        try {
            portsToIn.get(portNumber).close();
            portsToListeningThread.get(portNumber).join();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void broadcastConnection() {
        if (connected) {
            System.out.println("You are already connected!");
            return;
        }

        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        connected = true;
        serverThread = new Thread(() -> {
            try {
                handleServerSocket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();

        for (int port = 10000; port <= 15000; port++) {
            if (port != serverPort) {
                try {
                    portsToSendSockets.put(port, new Socket(localHostAddress, port));
                    portsToOut.put(port, new DataOutputStream(portsToSendSockets.get(port).getOutputStream()));
                    sendMessageToOne(port, "connect " + serverPort);
                } catch (IOException ignored) {
                }
            }
        }
    }

    public void disconnect() {
        connected = false;
        sendMessageToMany(new ArrayList<>(portsToSendSockets.keySet()), "disconnect " + serverPort);
        portsToSendSockets.clear();
        portsToOut.clear();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void printActiveNodes() {
        System.out.println("\nList of active users:");
        for (Integer portNumber : portsToSendSockets.keySet())
            System.out.println(portNumber);
        System.out.println();
    }

    public void restartPeer() {
    }
}
