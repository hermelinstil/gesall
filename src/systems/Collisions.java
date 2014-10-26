package systems;

import gameentity.GameObject;
import gameentity.InterpolatingBackground;

import java.awt.geom.Rectangle2D;

/**
 * Created by pter on 25-Oct-14.
 */
public abstract class Collisions {


    public static boolean check(GameObject target) {

        for(GameObject gameObject : Game.gameObjects) {
            if(target.getID() != gameObject.getID() && !(gameObject instanceof InterpolatingBackground)) {
                if(target.getGeometry().intersects((Rectangle2D) gameObject.getGeometry())) {
                    return true;
                }
            }
        }
        return false;
    }
}
