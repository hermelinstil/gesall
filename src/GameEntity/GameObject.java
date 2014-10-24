package GameEntity;

import java.awt.*;
import java.util.ArrayList;

public class GameObject {

    protected int size,
                  x, y;

    protected float acceleration,
                    speedX, speedY,
                    maxVelocity = 0;

    protected boolean active = true;

    protected GameObject parent;
    protected Shape geometry;
    protected ArrayList<GameObject> children;


    public GameObject(int size) {
        this.size = size;
        x = 100;
        y = 100;
        acceleration = 0.1f;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setActive(boolean active) {
        this.active = active;
    }



    public void update(long delta) {
        x += (delta * speedX) / 10000000;
        y += (delta * speedY) / 10000000;
    }
}