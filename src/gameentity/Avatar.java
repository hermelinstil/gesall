package gameentity;

import systems.Collisions;
import systems.Game;
import systems.Input;

import java.awt.*;
import java.awt.event.KeyEvent;



/**
 * Created by Stavling on 2014-10-21.
 */
public class Avatar extends GameObject {

    private Point aim = new Point();

    private long shootCooldown = 0;
    private final int shootIntervalInSeconds = 5;

    private Bullet bullet;
    private boolean hidden = false;

    public Avatar(Shape geometry) {
        super(geometry);

        acceleration = 0.2f;
        maxVelocity = 3;
        z = 1;
    }


    @Override
    public void update(long delta) {
        super.update(delta);

        handleInput();
        hidden = Collisions.check(this);
        checkBorders();
    }

    private void checkBorders() {
        if(x < 0) {
            x = 0;
        } else if (x > Game.WIDTH) {
            x = Game.WIDTH - getWidth();
        }
        if(y < 0) {
            y = 0;
        } else if(y - getHeight() > Game.HEIGHT) {
            y = Game.HEIGHT - getHeight();
        }
    }

    public Point getAim() {
        return aim;
    }

    //no deceleration!
    private void handleInput() {
        aim.setLocation(Input.getMousePosition().getX(), Input.getMousePosition().getY());

        if(Input.getKeyState().contains(KeyEvent.VK_UP) && !Input.getKeyState().contains(KeyEvent.VK_DOWN)) {
            if(speedY > -maxVelocity) speedY -= acceleration;
        } else if(Input.getKeyState().contains(KeyEvent.VK_DOWN)) {
            if(speedY < maxVelocity) speedY += acceleration;
        } else {
            speedY = 0;
        }

        if(Input.getKeyState().contains(KeyEvent.VK_LEFT) && !Input.getKeyState().contains(KeyEvent.VK_RIGHT)) {
            if(speedX > -maxVelocity) speedX -= acceleration;
        } else if(Input.getKeyState().contains(KeyEvent.VK_RIGHT)) {
            if(speedX < maxVelocity) speedX += acceleration;
        } else {
            speedX = 0;
        }

        if(Input.isMouseDown()) {
            if(shootCooldown < (System.currentTimeMillis() - shootIntervalInSeconds*1000)) {
                shoot();
            }
        }

        Game.getInstance().handleNetworkEvents("M-" + x + "-" + y);
    }

    private void shoot() {
        shootCooldown = System.currentTimeMillis();
        color = Color.red;
        //System.out.println("BAAAM");
        //Game.getInstance().handleNetworkEvents("hej");
    }

    public boolean isHidden() {
        return hidden;
    }
}
