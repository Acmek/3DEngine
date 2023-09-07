import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class OvalPlane {

    private Point center;
    private Point top = new Point(0, 30, 30);
    private Point bottom = new Point(0, 30, -10);
    private Point left = new Point(-10, 30, 10);
    private Point right = new Point(10, 30, 10);

    private Color color;

    private boolean status = true, filled = true;

    private double thickness = 0;

    public OvalPlane() {
        center = new Point((top.getX() + bottom.getX() + left.getX() + right.getX()) / 4, (top.getY() + bottom.getY() + left.getY() + right.getY()) / 4, (top.getZ() + bottom.getZ() + left.getZ() + right.getZ()) / 4);
    }

    public void draw(Graphics window, double camX, double camY, double camZ, double yaw, double pitch) {
        center.update(camX, camY, camZ, yaw, pitch);
        top.update(camX, camY, camZ, yaw, pitch);
        bottom.update(camX, camY, camZ, yaw, pitch);
        left.update(camX, camY, camZ, yaw, pitch);
        right.update(camX, camY, camZ, yaw, pitch);

        window.setColor(Color.RED);

        if(center.getY2() > 0 && top.getY2() > 0 && bottom.getY2() > 0 && left.getY2() > 0 && right.getY2() > 0) {
            int[] xPoints = {(int)top.getScreenX(), (int)left.getScreenX(), (int)bottom.getScreenX(), (int)right.getScreenX()};
            int[] yPoints = {(int)top.getScreenY(), (int)left.getScreenY(), (int)bottom.getScreenY(), (int)right.getScreenY()};
            window.fillPolygon(xPoints, yPoints, xPoints.length);
            window.setColor(Color.BLACK);
            window.fillRect((int)center.getScreenX() - 2, (int)center.getScreenY() - 2, 4, 4);
            //*/
            window.setColor(Color.BLUE);
            //window.fillArc(320, 240, 100, 100, 90, 180);
            window.drawArc((int)left.getScreenX(), (int)top.getScreenY(), (int)Math.abs((top.getScreenX() - left.getScreenX()) * 2), (int)Math.abs((left.getScreenY() - top.getScreenY()) * 2), 90, 90);
            window.fillRect((int)left.getScreenX(), (int)top.getScreenY(), 8, 8);
            System.out.println((int)Math.toDegrees(-Math.atan2(top.getScreenY() - center.getScreenY(), top.getScreenX() - center.getScreenX())));
            //window.fillArc((int)left.getScreenX(), (int)center.getScreenY(), (int)Math.abs((bottom.getScreenX() - left.getScreenX())), (int)Math.abs((left.getScreenY() - bottom.getScreenY())), 180, 90);
        }
    }

    public double getDistance() {
        return center.getDistance();
    }
}