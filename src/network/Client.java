package network;

import systems.Game;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

/**
 * Created by molotov on 10/26/14.
 */
public class Client extends NetworkEntity {

    private InetAddress ipAdress;

    public Client(Game game, String ipAdress) {
        super(game);

        try {
            socket = new DatagramSocket();
            this.ipAdress = InetAddress.getByName(ipAdress);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAdress, 2000);
        sendData(packet);
    }

    @Override
    protected void handleData(DatagramPacket packet) {
        //DEBUG
        System.out.println("CLIENT > Packet received from " + packet.getAddress() + ": " + new String(packet.getData()));


        String[] message = new String(packet.getData()).split("-");

        char code = message[0].charAt(0);

        switch(code) {
            case 'N': game.addMPPlayer(message[1]);
                break;
            case 'Y':   game.addPlayer();
                break;
            case 'M':
                break;
        }
    }

    @Override
    public void start() {
        super.start();
        sendPacket("L".getBytes());
    }


}
