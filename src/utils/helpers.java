package utils;

import java.awt.*;
import java.awt.geom.Line2D;

import static utils.Constants.MINIMAP;

public class helpers {
    public static double getGradient(double startX, double startY, double endX, double endY) {
        return (startY-endY) / (endX-startX);
    }
    public static double getGradientFromHeading(double heading) {
//        if (0==0) return 0;
        if (heading < 0) {
            heading = 360 + heading;
        } else if (heading > 360) {
            heading -= 360;
        }
        if (heading == 180 || heading == 0 ) return Double.MAX_VALUE;
        return Math.tan(Math.toRadians(heading + 90));
    }
    public static double getLineAngle(double startX, double startY, double endX, double endY) {
        return (Math.toDegrees(Math.atan2(startY - endY, startX - endX)) + 270)%360;
    }

    public static void drawDebug(Graphics g, String text, int offset, Color c) {
        g.setColor(c);
        g.drawString(text, 5, 15 + offset);
    }public static void drawDebug(Graphics g, String text, int offset) {
        drawDebug(g, text, offset, Color.ORANGE);
    } public static void drawDebug(Graphics g, double text, int offset, Color c) {
        drawDebug(g, String.valueOf(text), offset, c);
    }
    public static void drawCast(Graphics g, Line2D.Double line, Color c) {
        g.setColor(c);
        g.drawLine((int) (line.x1*MINIMAP.SCALE), (int) (line.y1*MINIMAP.SCALE), (int) (line.x2*MINIMAP.SCALE), (int) (line.y2*MINIMAP.SCALE));
    }
    public static void drawCircle(Graphics g, int x, int y, Color c) {
        g.setColor(c);
        g.drawOval(x-5, y-5, 10, 10);
    }
    public static void drawFillCircle(Graphics g, int x, int y, Color c) {
        g.setColor(c);
        g.fillOval(x-5, y-5, 10, 10);
    }
}
