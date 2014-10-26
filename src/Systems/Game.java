package systems;

import gameentity.Avatar;
import gameentity.GameObject;
import gameentity.InterpolatingBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Stavling on 2014-10-21.
 */
public class Game implements Runnable {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private boolean running = true;

    private systems.Renderer renderer;

    public static final ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    private char[] currentLevel;


    public Game() {
        BufferStrategy bufferStrategy = initGUI();

        currentLevel = Loader.loadLevel();

        populateList();

        renderer = new systems.Renderer(bufferStrategy);
    }

    private void populateList() {
        gameObjects.add(new Avatar(new Rectangle(25, 25)));
        gameObjects.add(new InterpolatingBackground(new Rectangle(WIDTH, HEIGHT)));

        Loader.translateToLevel(currentLevel, gameObjects);

        Collections.sort(gameObjects);
    }

    @Override
    public void run() {

        long oldTime;
        long newTime = System.nanoTime();

        //systems.Game loop
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
