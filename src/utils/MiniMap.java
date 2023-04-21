package utils;

import entities.Player;
import main.Game;

import java.awt.*;

import static main.Game.*;
import static utils.Constants.MINIMAP;

public class MiniMap {
    boolean[][] mapLayout;
    Player player;
    int height, width, x, y;
    float scale = MINIMAP.SCALE, miniTileSize;

    public MiniMap(Game game, int x, int y) {
        mapLayout = game.getGrid().getLayout();
        player = game.getPlayer();
        height = (int)(GAME_HEIGHT * scale);
        width = (int)(GAME_WIDTH * scale);
        miniTileSize = (float)width/TILES_IN_WIDTH;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        drawMap(g);
        drawMiniPlayer(g);
    }

    private void drawMap(Graphics g) {
        g.setColor(Color.PINK);
        for (int i=0; i < TILES_IN_HEIGHT; i++) {
            for (int j=0; j < TILES_IN_WIDTH; j++) {
                if(mapLayout[i][j]) {
                    g.fillRect((int) (j * miniTileSize), (int) (i * miniTileSize), (int) miniTileSize, (int) miniTileSize);
                }
            }
        }
    }
    private void drawMiniPlayer(Graphics g) {
            g.setColor(Color.RED);
            g.fillOval((int)((player.getPos().x*scale)-7/2), (int)((player.getPos().y*scale)-7/2), 7, 7);
    }
}
