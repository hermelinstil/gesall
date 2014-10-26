package gameentity;

import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Random;

public class GameObject implements Comparable<GameObject> {

    protected int x, y, z;

    protected float acceleration,
                    speedX, speedY,
                    maxVelocity = 0;

    //for rendering
    protected boolean active = true; //? onödig??
    protected Color color;

    //should also have direction to prevent high diagonal speed
    protected GameObject parent;
    protected Shape geometry;
    protected ArrayList<GameObject> children;

    private int ID;

    private static Random random = new Random();


    public GameObject(Shape geometry) {
        this.geometry = geometry;
        x = 0;
        y = 0;
        color = Color.black;

        ID = random.nextInt();
    }

    public int getWidth() {
        return (int) geometry.getBounds().getWidth();
    }

    public int getHeight() {
        return (int) geometry.getBounds().getHeight();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setActive(boolean active) {
        this.active = active;
    }



    public void update(long delta) {
        x += (delta * speedX) / 10000000;
        y += (delta * speedY) / 10000000;
    }

    public int getID() {
        return ID;
    }

    //fullösning, sämsta jag sett
    public Shape getGeometry() {
        return new Rectangle(x, y, (int) geometry.getBounds().getWidth(), (int) geometry.getBounds().getHeight());
    }

    @Override
    public String toString() {
        return getClass() + " " + ID;
    }

    @Override
    public int compareTo(GameObject o) {
        return o.z  - this.z;
    }
}