package facade;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose your port number [10000 - 15000]: ");
        int serverPort = Integer.parseInt(scanner.nextLine());

        Peer peer = new PeerImpl(serverPort);
        /**
         * List of working commands:
         *  - 'connect' (connects to all available users)
         *  - 'register portNumber' (peer declares that wants to receive messages from some user)
         *  - 'send-one portNumber "message"' (send one message, other peer need to first register sending peer to get the message)
         *  - 'send-many pN1 pN2 ... pN3 "message"' (send many messages, other peers need to register sending peer to get the message)
         *  - 'show-active' (show list of active users)
         */
        while (true) {
            String[] mainArguments = scanner.nextLine().split("\"");
            String[] arguments = mainArguments[0].split(" ");

            switch (arguments[0]) {
                case "connect" -> peer.broadcastConnection();
                case "register" -> peer.registerIncomingMessages(Integer.parseInt(arguments[1]));
                case "unregister" -> peer.unregisterIncomingMessages(Integer.parseInt(arguments[1]));
                case "send-one" -> {
                    if (arguments.length != 2 || !arguments[1].matches("-?\\d+"))
                        System.out.println("Invalid format of send-one command!");
                    else
                        peer.sendMessageToOne(Integer.parseInt(arguments[1]), mainArguments[1]);
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
}
