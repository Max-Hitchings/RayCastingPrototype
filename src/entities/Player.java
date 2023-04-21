package entities;


import main.Game;
import rayCasting.RayCaster;
import utils.Crosshair;

import java.awt.*;
import java.awt.geom.Point2D;

import static main.Game.TILE_SIZE;
import static utils.Constants.PLAYER;
import static utils.helpers.getLineAngle;

public class Player extends Entity{
    private RayCaster rayCaster;
    private Crosshair crosshair;
    private boolean up, down, left, right;
    private final float playerSpeed = PLAYER.SPEED;
    public Mouse mouse = new Mouse();
    private int fov;
    private double heading;

    public Player(Game game) {
        super(game, (TILE_SIZE/2)*2, (TILE_SIZE/2)*2, 10, 10);
        this.fov = PLAYER.FOV;
        initClasses();
    }

    private void initClasses() {
        rayCaster = new RayCaster(game, fov);
        mouse.pos = new Point2D.Double(0,0);
        crosshair = new Crosshair(15, 15, 2);
    }

    public void update() {
        updatePos();
        Point2D.Double pos = new Point2D.Double(x+ (width/2f), y+ (height/2f));
        rayCaster.update(pos, heading);
    }

    public void render(Graphics g) {
        rayCaster.render(g);
        crosshair.draw(g);
//        drawPlayer(g);
    }

    public void drawPlayer(Graphics g) {
        g.setColor(new Color(255, 0, 0));
        g.fillOval((int) x,(int) y, width, height);
    }

    public void updateHeading(double headingDelta) {
        heading += headingDelta;

//        clamp heading to 0 <= heading <= 360
        if (heading < 0) {
            heading = 360 + heading;
        } else if (heading > 360) {
            heading -= 360;
        }
//        game.mouseRobot.mouseMove(GAME_WIDTH/2, GAME_HEIGHT/2);
    }

    private void updatePos() {
        if (left && !right) {
            move(Direction.LEFT);
        } else if (right && !left) {
            move(Direction.RIGHT);
        }

        if (up && !down) {
            move(Direction.FORWARD);
        } else if (down && !up) {
            move(Direction.BACKWARD);
        }
    }

    private void move(Direction direction) {
        double relativeHeading;
        int dir;
        switch (direction) {
            case FORWARD -> {
                dir = 1;
                relativeHeading = heading;
            }
            case BACKWARD -> {
                dir = -1;
                relativeHeading = heading;
            }
            case LEFT -> {
                dir = -1;
                relativeHeading = heading + 90;
            }
            case RIGHT -> {
                dir = 1;
                relativeHeading = heading + 90;
            } case default -> {
                dir = 0;
                relativeHeading = 0;
            }

        }
        x += Math.sin(Math.toRadians(relativeHeading)) * playerSpeed * dir;
        y -= Math.cos(Math.toRadians(relativeHeading)) * playerSpeed * dir;
    }

    enum Direction {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    public Point2D.Float getPos() {
        return new Point2D.Float(x, y);
    }

    public void cancelMovement() {
        right = down = left = up = false;
    }
    public void setUp(boolean up) {
        this.up = up;
    }
    public void setDown(boolean down) {
        this.down = down;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public void setRight(boolean right) {
        this.right = right;
    }

    public void updateMouse(double mouseX, double mouseY) {
            mouse.pos.x = mouseX;
            mouse.pos.y = mouseY;
            mouse.angle = getLineAngle((x +(width/2f)), (y - (height/2f)), mouseX, mouseY);
        }
    }

    class Mouse {
        Point2D.Double pos;
        double angle;
    }