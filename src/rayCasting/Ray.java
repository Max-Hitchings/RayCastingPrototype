package rayCasting;

import main.GameGrid;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import static main.Game.TILE_SIZE;
import static utils.helpers.*;

public class Ray {
    GameGrid gameGrid;
    Point2D.Double origin;
    //        ty - Top Y
    //        by - Bottom Y
    //        rx - Right X
    //        lx - Left X
    double tyDelta, byDelta, rxDelta, lxDelta;
    public double gradient, heading;
    public double headingDelta;
    public double xNormalStep, yNormalStep;
    public Line xLine, yLine, finalCast;
    public int yX = 0;
    int xDir, yDir;
    public Ray(GameGrid gameGrid) {
        this(gameGrid, 0);
    }
    public Ray(GameGrid gameGrid, double headingDelta) {
        this.gameGrid = gameGrid;
        this.headingDelta = headingDelta;
//        this.headingDelta = 20;
        update(new Point2D.Double(), 0);
    }
    public void update(Point2D.Double origin, double heading) {
        heading += headingDelta;

//        clamp heading to 0 <= heading <= 360
        if (heading < 0) {
            heading = 360 + heading;
        } else if (heading > 360) {
            heading -= 360;
        }
        this.heading = heading;
        this.origin = origin;

        tyDelta =  -(origin.y % TILE_SIZE);
        byDelta = TILE_SIZE - (origin.y % TILE_SIZE);
        rxDelta = TILE_SIZE - (origin.x % TILE_SIZE);
        lxDelta = origin.x % TILE_SIZE;

        gradient = getGradient(heading);
        xLine = new Line(origin, origin);
        yLine = new Line(origin, origin);

        firstStep();
        completeRay();
    }
    public void draw(Graphics g) {
        drawCast(g, finalCast, new Color(137,207,240));
//        drawDebug(g, String.valueOf(finalCast.len), 20);
    }
    private void completeRay() {
        boolean endFlag = false;
        while (!endFlag) {
            if (xLine.len < (10*TILE_SIZE) || yLine.len < (10*TILE_SIZE)){
                if (yLine.len > xLine.len) {
                    if (!checkCollisionX(xLine.x2, xLine.y2)) {
                        stepX();
                    } else {
                        endFlag = true;
                    }
                } else {
                    if (!checkCollisionY(yLine.x2, yLine.y2)) {
                        stepY();
                    } else {
                        endFlag = true;
                    }
                }
            } else {
                endFlag = true;
            }
        }
        if (yLine.len > xLine.len) {
            finalCast = (Line) xLine.clone();
            yX = 1;
        } else {
            finalCast = (Line) yLine.clone();
            yX = 0;
        }
    }
    private void stepX() {
        xLine.x2 += TILE_SIZE * xDir;
        xLine.y2 += TILE_SIZE * gradient * xDir;
        xLine.len += xNormalStep;
    }

    private void stepY() {
        yLine.y2 += TILE_SIZE * yDir;
        yLine.x2 += TILE_SIZE / gradient * yDir;
        yLine.len += yNormalStep;
    }

    private void firstStep() {
        double xDelta, yDelta;
        if (xDir == 1) {
            xDelta = rxDelta;
        } else {
            xDelta = -lxDelta;
        }
        if (heading > 90 && heading < 270) {
            yDelta =  byDelta;
        } else {
            yDelta = tyDelta;
        }

        xNormalStep = Math.sqrt(((TILE_SIZE)*(TILE_SIZE)) + ((gradient*TILE_SIZE)*(gradient*TILE_SIZE)));
        yNormalStep = Math.sqrt(((TILE_SIZE)*(TILE_SIZE)) + ((TILE_SIZE / gradient)*(TILE_SIZE / gradient)));

        xLine.x2 += xDelta;
        xLine.y2 += xDelta * gradient;
        xLine.len += getLineLength(xLine);

        yLine.y2 += yDelta;
        yLine.x2 += yDelta / gradient;
        yLine.len += getLineLength(yLine);
    }

    private boolean checkCollisionX(double x, double y) {
        int xDelta = 0;
        if (xDir == -1) {
            xDelta = -1;
        }

//        if (gameGrid.checkCollision((int) Math.floor((x / TILE_SIZE) + xDelta), (int) Math.floor(y / TILE_SIZE))) {
////            drawCircle(g, (int)x, (int)y, Color.CYAN);
//        } else {
////            drawFillCircle(g, (int)x, (int)y, Color.CYAN);
//        }
        return this.gameGrid.checkCollision((int) Math.floor((x / TILE_SIZE) + xDelta), (int) Math.floor(y / TILE_SIZE));
    }
    private boolean checkCollisionY(double x, double y) {
        int yDelta = 0;
        if (yDir == -1) {
            yDelta = -1;
        }

//        if (gameGrid.checkCollision((int) Math.floor(x / TILE_SIZE) , (int) Math.floor((y / TILE_SIZE) + yDelta))) {
//            drawCircle(g, (int)x, (int)y, Color.GREEN);
//        } else {
//            drawFillCircle(g, (int)x, (int)y, Color.GREEN);
//        }
        return this.gameGrid.checkCollision((int) Math.floor(x / TILE_SIZE), (int) Math.floor((y / TILE_SIZE) + yDelta));
    }

    private double getLineLength(Line line) {
        return Math.sqrt(((line.x2-line.x1)*(line.x2-line.x1)) + ((line.y2-line.y1)*(line.y2-line.y1)));
    }

    private double getGradient(double heading) {
        if (heading < 180) {
            xDir = 1;
        } else {
            xDir = -1;
        }

        if (heading > 270 || heading < 90) {
            yDir = -1;
        } else {
            yDir = 1;
        }
        if (heading == 180 || heading == 0 ) return Double.MAX_VALUE;
        return Math.tan(Math.toRadians(heading + 90));
    }
}

class Line extends Line2D.Double {
    public double len;
    public Line(Point2D.Double start, Point2D.Double end) {
        super(start, end);
    }
}