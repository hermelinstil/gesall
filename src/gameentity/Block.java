package gameentity;


import java.awt.*;

/**
 * Created by pter on 25-Oct-14.
 */
public class Block extends GameObject {


    public Block(int x, int y, Shape geometry) {
        super(geometry);
        this.x = x;
        this.y = y;
        z = 500;
    }
}
