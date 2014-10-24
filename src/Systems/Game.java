package Systems;

import GameEntity.Avatar;
import GameEntity.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by Stavling on 2014-10-21.
 */
public class Game implements Runnable {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private boolean running = true;

    private Systems.Renderer renderer;

    private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

    public Game() {
        BufferStrategy bufferStrategy = initGUI();
        populateList();

        renderer = new Systems.Renderer(bufferStrategy);
    }

    private void populateList() {
        gameObjects.add(new Avatar(25));
    }

    @Override
    public void run() {

        long oldTime = System.nanoTime();
        long newTime = System.nanoTime();

        //Systems.Game loop
        while(running) {
            oldTime = newTime;
            newTime = System.nanoTime();

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


    private BufferStrategy initGUI() {
        JFrame frame = new JFrame();

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        Canvas canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        Input.registerInputListeners(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);

        canvas.requestFocus();
        return canvas.getBufferStrategy();
    }



    public static void main(String[] args) {
        Game game = new Game();
        new Thread(game).start();
    }
}
