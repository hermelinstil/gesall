package GameEntity;

/**
 * Created by Stavling on 2014-10-24.
 */
public class Bullet extends GameObject {


    public Bullet(int size) {
        super(size);

        //don't render
        active = false;
    }


}
