package main;

import entities.Player;
import inputs.Mouse;
import utils.MiniMap;

import java.awt.*;

import static utils.Constants.GAME;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private Player player;
    private GameGrid gameGrid;
    public Mouse mouse;
    MiniMap miniMap;

    private final int FPS_SET = GAME.FPS;
    private final int TPS_SET = GAME.TPS;

    public final static int TILE_NORMAL_SIZE = GAME.TILE_SIZE;
    public final static float SCALE = GAME.SCALE;
    public final static int TILES_IN_WIDTH = 16;
    public final static int TILES_IN_HEIGHT = 9;
    public final static int TILE_SIZE = (int) (TILE_NORMAL_SIZE * SCALE);
    public final static int GAME_WIDTH = TILE_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILE_SIZE * TILES_IN_HEIGHT;

    public Game() throws AWTException {

        initClasses();
        gamePanel = new GamePanel(this, GAME_WIDTH, GAME_HEIGHT);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void initClasses() throws AWTException {
        gameGrid = new GameGrid();
        player = new Player(this);
        mouse = new Mouse(this);
        miniMap = new MiniMap(this, 0, 0);
    }

    public GameGrid getGrid() {
        return gameGrid;
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        mouse.update();
        player.update();
    }
    public void render(Graphics g) {
//        gameGrid.render(g);
        player.render(g);
        miniMap.draw(g);
    }
    @Override
    public void run() {

        double frameTime = 1000000000.0 / FPS_SET;
        double timePerTick = 1000000000.0 / TPS_SET;

        long previousTime = System.nanoTime();
        int updates = 0;

        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaLag = 0;
        double deltaFrames = 0;

        long currentTime;

        while (true) {
            currentTime = System.nanoTime();

            deltaLag += (currentTime - previousTime) / timePerTick;
            deltaFrames += (currentTime - previousTime) / frameTime;
            previousTime = currentTime;

            if (deltaLag >= 1) {
                update();
                updates++;
                deltaLag--;
            }

            if (deltaFrames >= 1) {
                gamePanel.repaint();
                frames++;
                deltaFrames--;
            }

//            if (now - lastFrame >= frameTime) {
//                gamePanel.repaint();
//                lastFrame = now;
//                frames++;
//            }


            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS " + frames + " | TPS: " + updates);
                frames = 0;
                updates = 0;
            }

        }

    }
    public Player getPlayer() {
        return player;
    }

    public Mouse getMouse() {
        return mouse;
    }
}