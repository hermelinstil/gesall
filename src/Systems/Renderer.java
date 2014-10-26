package systems;

import gameentity.Avatar;
import gameentity.GameObject;

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



        g.dispose();
        bufferStrategy.show();
    }

}
