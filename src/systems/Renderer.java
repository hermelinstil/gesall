package systems;

import gameentity.Avatar;
import gameentity.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by Stavling on 2014-10-22.
 */
public class Renderer {

    private JFrame frame;

    private ArrayList<GameObject> renderList;
    private BufferStrategy bufferStrategy;

    public Renderer() {
        bufferStrategy = initGUI();
    }

    public void updateRenderList(ArrayList<GameObject> renderList) {
        this.renderList = renderList;
    }

    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        for(GameObject object : renderList) {
            g.setColor(object.getColor());
            g.fillRect(object.getX(),
                    object.getY(),
                    object.getWidth(),
                    object.getHeight());

            if(object instanceof Avatar) {
                Avatar a = (Avatar) object;
                if(a.isHidden()) {
                    g.setColor(Color.WHITE);
                    g.drawRect(a.getX() - 1, a.getY() - 1, a.getWidth() + 1, a.getHeight() + 1);
                }
            }

            //g.drawLine(object.getX() + object.getWidth()/2, object.getY() + object.getHeight()/2, ((Avatar)object).getAim().x, ((Avatar)object).getAim().y);
        }


        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        bufferStrategy.show();
    }

    private BufferStrategy initGUI() {
        frame = new JFrame();

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        panel.setLayout(null);

        Canvas canvas = new Canvas();
        canvas.setBounds(0, 0, Game.WIDTH, Game.HEIGHT);
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

    public boolean query(String string) {
        return JOptionPane.showConfirmDialog(frame, string) == JOptionPane.OK_OPTION;
    }

    public String queryText(String string) {
        return JOptionPane.showInputDialog(frame, string);
    }
}
