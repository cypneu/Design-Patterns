package facade;

import java.util.ArrayList;

public interface Peer {
    void broadcastConnection();
    void sendMessageToOne(int sendingPort, String message);
    void sendMessageToMany(ArrayList<Integer> sendingPorts, String message);
    void printActiveNodes();
    void disconnect();
    void registerIncomingMessages(int portNumber);
    void unregisterIncomingMessages(int portNumber);
    void restartPeer();
}
