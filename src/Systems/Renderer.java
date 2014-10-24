package Systems;

import GameEntity.Avatar;
import GameEntity.GameObject;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by Stavling on 2014-10-22.
 */
public class Renderer {

    private ArrayList<GameObject> renderList;
    private BufferStrategy bufferStrategy;

    public Renderer(BufferStrategy bufferStrategy) {
        this.bufferStrategy = bufferStrategy;
    }

    public void updateRenderList(ArrayList<GameObject> renderList) {
        this.renderList = renderList;
    }

    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g.setColor(Color.BLACK);

        for(GameObject object : renderList) {
            g.fillRect(object.getX(),
                    object.getY(),
                    object.getSize(),
                    object.getSize());

            g.drawLine(object.getX() + object.getSize()/2, object.getY() + object.getSize()/2, ((Avatar)object).getAim().x, ((Avatar)object).getAim().y);
        }



        g.dispose();
        bufferStrategy.show();
    }

}
