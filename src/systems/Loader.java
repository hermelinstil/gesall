package systems;


import gameentity.Block;
import gameentity.GameObject;

import java.awt.*;
import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;

/**
 * Created by pter on 24-Oct-14.
 */
public abstract class Loader {



    //should return something iterable
    public static char[] loadLevel() {
        byte[] level;

        try {
            BufferedInputStream fileInputStream = (BufferedInputStream) Loader.class.getResourceAsStream("level.txt");

            level = new byte[fileInputStream.available()];
            fileInputStream.read(level);

            return new String(level, "UTF-8").toCharArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void translateToLevel(char[] level, ArrayList<GameObject> list) {
        int placeX = 0;
        int placeY = 0;

        for(char c : level) {
            switch(c) {
                case ' ':
                    break;
                case 'B': list.add(new Block(placeX, placeY, new Rectangle(50, 50)));
                    break;
                case 'H': list.add(new Block(placeX, placeY, new Rectangle(50, 50)));
                    break;
            }
            if(!(placeX > Game.WIDTH - 50)) {
                placeX += 50;
            } else {
                placeY += 50;
                placeX = 0;
            }
        }
    }
}
