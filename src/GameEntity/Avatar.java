package GameEntity;

import Systems.Input;

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

    public Avatar(int size) {
        super(size);

        maxVelocity = 3;
        bullet = new Bullet(25);
    }


    @Override
    public void update(long delta) {
        super.update(delta);

        handleInput();
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

        if(Input.getKeyState().contains(KeyEvent.VK_SPACE)) {
            if(shootCooldown < (System.currentTimeMillis() - shootIntervalInSeconds*1000)) {
                shoot();
            }
        }
    }

    private void shoot() {
        shootCooldown = System.currentTimeMillis();
        System.out.println("BAAAM");
    }
}
