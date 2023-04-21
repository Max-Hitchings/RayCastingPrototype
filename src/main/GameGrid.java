package main;

import java.awt.*;

import static main.Game.*;

public class GameGrid {
    private final String[] layout = {
            "#000############",
            "#00000000#######",
            "##000###########",
            "##0#0###########",
            "##000###000000##",
            "######000000#####",
            "###0##00########",
            "#####00#########",
            "################"
    };
    private boolean[][] grid;
    public GameGrid() {
        constructGrid();
    }

    public void render(Graphics g) {
        g.setColor(new Color(0));
        for (int y = 0; y < 9; y++) {
//            for (int x = 0; x < 16; x++) {
//                if (grid[x][y]) {
//                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE-5, TILE_SIZE-5);
//                }
//            }
            for (int x = 0; x < 16; x++) {
                if (grid[y][x]) {
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
                }
            }
        }
    }

    public boolean checkCollision(int x, int y) {
        if (y < 0 || x < 0 || y >= 9 || x >= 16) {
            return true;
        }
        return grid[y][x];
    }

    private void constructGrid() {
        grid = new boolean[9][16];

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                grid[y][x] = layout[y].charAt(x) == '#';
            }
        }
    }

    public boolean[][] getLayout() {
        return grid;
    }
}
