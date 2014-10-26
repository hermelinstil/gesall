package network;

import systems.Game;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by molotov on 10/26/14.
 */
public class Server extends NetworkEntity {

    private HashMap<Integer, InetAddress> players;

    public Server(Game game) {
        super(game);
        try {
            socket = new DatagramSocket(2000);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        players = new HashMap<Integer, InetAddress>();
    }

    protected void sendPacket(byte[] data, InetAddress address, int port) {
        //måstte veta vart den ska skicka!
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        sendData(packet);
    }

    @Override
    protected void handleData(DatagramPacket packet) {
        //DEBUG
        System.out.println("SERVER > Packet received from " + packet.getAddress() + ": " + new String(packet.getData()));


        String[] message = new String(packet.getData()).split("-");
        char code = message[0].charAt(0);
        switch(code) {
            case 'L':
                players.put(packet.getPort(), packet.getAddress());
                ///MÅSTE SKICKA MED DE SPELARE SOM REDAN FINNS!


                sendPacket("Y".getBytes(), packet.getAddress(), packet.getPort());

                for(Map.Entry<Integer, InetAddress> i : players.entrySet()) {
                    if(i.getKey() != packet.getPort()) {
                        sendPacket(("N-" + i.getKey()).getBytes(), packet.getAddress(), packet.getPort());
                    }
                }

                broadCast(("N-" + packet.getPort()).getBytes(), packet.getPort());
                System.out.println(players);
                break;
        }
    }

    protected void broadCast(byte[] data, int ignorePort) {
        for(Map.Entry<Integer, InetAddress> i : players.entrySet()) {
            if(i.getKey() != ignorePort) {
                sendPacket(data, i.getValue(), i.getKey());
            }
        }
    }
}
