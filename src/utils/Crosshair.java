package utils;

import java.awt.*;

import static main.Game.GAME_WIDTH;
import static main.Game.GAME_HEIGHT;

public class Crosshair {
    int width, height, thickness;
    public Crosshair(int height, int width, int thickness) {
        this.width = width;
        this.height = height;
        this.thickness = thickness;
    }

    public void draw(Graphics g) {
        int x1, y1, x2, y2;
        g.setColor(Color.RED);

        x1 = (GAME_WIDTH/2)-(width /2);
        y1 = (GAME_HEIGHT/2)-(thickness /2);
        g.fillRect(x1, y1, width, thickness);

        x2 = (GAME_WIDTH/2)-(thickness /2);
        y2 = (GAME_HEIGHT/2)-(height /2);
        g.fillRect(x2, y2, thickness, height);

    }
}
