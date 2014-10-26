package gameentity;

import java.awt.*;
import java.util.Random;

/**
 * Created by pter on 25-Oct-14.
 */
public class InterpolatingBackground extends GameObject {

    private Color targetColor;
    private float inter = 0.99f;

    public InterpolatingBackground(Shape geometry) {
        super(geometry);
        z = 1000;

        randomizeColor();
    }

    private void randomizeColor() {
        Random random = new Random();
        targetColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    @Override
    public void update(long delta) {
        super.update(delta);

        float[] currentColorArray = new float[3];
        float[] targetColorArray = new float[3];
        currentColorArray = color.getRGBColorComponents(currentColorArray);
        targetColorArray = targetColor.getRGBColorComponents(targetColorArray);

        color = new Color(
                currentColorArray[0] * inter + targetColorArray[0] * (1 - inter),
                currentColorArray[1] * inter + targetColorArray[1] * (1 - inter),
                currentColorArray[2] * inter + targetColorArray[2] * (1 - inter));

        if(color.equals(targetColor)) {
            randomizeColor();
        }
    }
}
