package entities;

import main.Game;

public class Entity {
    Game game;
    float x, y;
    int width, height;
    public Entity(Game game, float x, float y, int width, int height) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}