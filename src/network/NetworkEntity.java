package network;

import systems.Game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by molotov on 10/26/14.
 */
public abstract class NetworkEntity extends Thread {

    protected Game game;
    protected DatagramSocket socket;


    public NetworkEntity(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        byte[] data = new byte[1024];
        DatagramPacket packet;
        while (true) {
            packet = new DatagramPacket(data, data.length);

            try {
                socket.receive(packet);
            } catch (IOException i) {
                i.printStackTrace();
            }
            handleData(packet);
        }
    }

//    protected abstract void sendPacket(byte[] data);
    protected abstract void handleData(DatagramPacket packet);

    protected void sendData(DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}