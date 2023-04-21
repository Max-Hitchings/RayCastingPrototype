package map;

import utils.LoadStuff;

import java.awt.image.BufferedImage;

public class MapLoader {
    public static LvlMap loadNew(String fileName) {
        BufferedImage mapImg = LoadStuff.Image(fileName);

        LvlMap newMap = new LvlMap(mapImg.getWidth(), mapImg.getWidth());

        for (int y = 0; y < mapImg.getHeight(); y++) {
            for (int x = 0; x < mapImg.getWidth(); x++) {
                int c = mapImg.getRGB(x, y);
                newMap.layout[y][x] = c == -16777216;
            }
        }

        return newMap;
    }
}