package rayCasting;

import main.Game;

import java.awt.*;
import java.awt.geom.Point2D;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static utils.helpers.drawDebug;

public class RayCaster {
    Ray[] rays;
    Game game;
    Point2D.Double origin;
    double heading;
    int fov;
    public RayCaster(Game game, int fov) {
        this.game = game;
        this.fov = fov;
        rays = new Ray[GAME_WIDTH];
        double angleDiff = (double)fov / (double)GAME_WIDTH;
        float halfFov = fov / 2;

        for (int i = 0; i < rays.length; i++) {
            rays[i] = new Ray(game.getGrid(), ((i*angleDiff)-halfFov));
        }

//        rays = new Ray[(int)(fov*frequency)];
//
//        float halfFov = rays.length / 2;
//        double test = 1/frequency;
//        for (int i = 0; i < rays.length; i ++) {
//            System.out.println((i * test));
//            rays[i] = new Ray(game.getGrid(), (int)(((i*test)-halfFov)));
//        }
    }

    public void update(Point2D.Double newOrigin, double newHeading) {
        origin = newOrigin;
        heading = newHeading;

        for (Ray ray : rays) {
            ray.update(origin, heading);
        }
    }

    public void draw3D(Graphics g) {
        for (int i=0; i < rays.length; i++) {
            drawRayVertical(g, rays[i], i);
        }
    }

    private void drawRayVertical(Graphics g, Ray ray, int i) {
        if (ray.yX == 1) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.YELLOW);
        }
        double perpLength = ray.finalCast.len * Math.sin(Math.toRadians(90-Math.abs(ray.headingDelta)));
//        drawDebug(g, String.valueOf(GAME_WIDTH), 100);
        double wallHeight = GAME_HEIGHT*30/perpLength;
        float midPoint = GAME_HEIGHT/2;
        int width = GAME_WIDTH / rays.length;
        int height = (int)(GAME_HEIGHT*30/perpLength);
        int x = width*i;
        int y = (int)(midPoint-(wallHeight/2));
        g.fillRect(x, y, width, height);
        drawDebug(g, width, 40, Color.RED);

    }

    public void render(Graphics g) {
        draw3D(g);
        for (Ray ray : rays) {
            ray.draw(g);
        }
    }

    private void updateCasts() {

    }
    public void addCast(double startX, double startY, double heading) {}
}
