package systems;

import gameentity.Avatar;
import gameentity.AvatarMP;
import gameentity.GameObject;
import gameentity.InterpolatingBackground;
import network.Client;
import network.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Stavling on 2014-10-21.
 */
public class Game implements Runnable {

    private static Game instance;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private boolean running = true;

    private systems.Renderer renderer;

    public static final ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    private ArrayList<GameObject> tempList = new ArrayList<GameObject>();
    private char[] currentLevel;

    private Client client;


    protected Game() {
        renderer = new Renderer();
        currentLevel = Loader.loadLevel();

        populateList();

        initNetwork();
    }

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }

        return instance;
    }

    private void populateList() {
        //gameObjects.add(new Avatar(new Rectangle(25, 25)));
        gameObjects.add(new InterpolatingBackground(new Rectangle(WIDTH, HEIGHT)));

        Loader.translateToLevel(currentLevel, gameObjects);

        Collections.sort(gameObjects);
    }

    public void addMPPlayer(String ID) {
        tempList.add(new AvatarMP(new Rectangle(25, 25), ID));
    }

    public void addPlayer() {
        tempList.add(new Avatar(new Rectangle(25, 25)));
    }

    public void handleNetworkEvents(String s) {
        client.sendPacket(s.getBytes());
    }

    @Override
    public void run() {

        long oldTime;
        long newTime = System.nanoTime();

        //systems.Game loop
        while(running) {
            oldTime = newTime;
            newTime = System.nanoTime();

            if(!tempList.isEmpty()) {
                gameObjects.addAll(tempList);
                tempList.clear();
            }

            updateGameObjects(newTime - oldTime);

            renderer.updateRenderList(gameObjects);
            renderer.render();

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        System.exit(0);
    }

    private void updateGameObjects(long delta) {
        for(GameObject obj : gameObjects) {
            obj.update(delta);
        }
    }

    private void initNetwork() {
        boolean isServer = false;
        if(renderer.query("Server?")) {
            new Server(this).start();
            isServer = true;
        }

        String address = isServer ? "localhost" : renderer.queryText("IP-Adress?");
        client = new Client(this, address);
        client.start();
    }

    public static void main(String[] args) {
        Game game = Game.getInstance();
        new Thread(game).start();
    }
}
