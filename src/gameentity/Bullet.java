package gameentity;

import java.awt.*;

/**
 * Created by Stavling on 2014-10-24.
 */
public class Bullet extends GameObject {


    public Bullet(Shape geometry) {
        super(geometry);

        //don't render
        active = false;
    }
}
