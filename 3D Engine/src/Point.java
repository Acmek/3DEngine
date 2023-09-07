import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class Point {

    private double x, y, z, distance;
    
    private double size;

    private Color color;

    private double screenX, screenY;

    private double y2;

    private boolean status = true, filled = true;

    private double thickness = 0;

    public Point() {
        setPos(Math.random() * 640 - 320, Math.random() * 480 - 240, Math.random() * 50);

        setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
    }

    public Point(double px, double py, double pz) {
        setPos(px, py, pz);

        setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
    }

    public Point(double px, double py, double pz, Color c) {
        setPos(px, py, pz);

        setColor(c);
    }

    public Point(Point p) {
        setPos(p.getX(), p.getY(), p.getZ());

        setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
    }

    public Point(Point p, Color c) {
        if(p == null)
            setPos(Math.random() * 640 - 320, Math.random() * 480 - 240, Math.random() * 50);
        else
            setPos(p.getX(), p.getY(), p.getZ());

        if(c == null)
            setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
        else
            setColor(c);
    }

    public Point(Point p, double s, Color c) {
        if(p == null)
            setPos(Math.random() * 640 - 320, Math.random() * 480 - 240, Math.random() * 50);
        else
            setPos(p.getX(), p.getY(), p.getZ());

        if(s == -1)
            setSize(8);
        else
            setSize(s);

        if(c == null)
            setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
        else
            setColor(c);
    }

    public void update(double camX, double camY, double camZ, double yaw, double pitch) {
        distance = Math.sqrt(Math.pow(x - camX, 2) + Math.pow(y - camY, 2) + Math.pow(z - camZ, 2));

        double x2 = x;
        y2 = y;
        double z2 = z;

        x2 -= camX;
        y2 -= camY;
        z2 -= camZ;

        double p = Math.atan2(x2, y2) + (yaw - Math.PI / 2);
        double d = Math.sqrt(x2 * x2 + y2 * y2);
        x2 = Math.sin(p) * d;
        y2 = Math.cos(p) * d;

        p = Math.atan2(z2, y2) - pitch;
        d = Math.sqrt(z2 * z2 + y2 * y2);
        z2 = Math.sin(p) * d;
        y2 = Math.cos(p) * d;

        //if(x2 < -30)
        //    x2 = -30;
        //if(y2 <= 0)
        //    y2 = 0.1;

        setScreenX((x2 / y2 * 320) + 320);
        setScreenY(-(z2 / y2 * 320) + 240);

        /*if(y2 <= 0) { //need x to increase too also y, create parabola if y2 <= 0? rather than circle?
            screenX = -(x2 / y2 * 320) + 320;
            screenY = (z2 / y2 * 320) + 240;
        }*/

        //System.out.println(/*screenX + " " + screenY + " " + */x2 + " " + y2 + " " + z2);
    }

    public void draw(Graphics window, double camX, double camY, double camZ, double yaw, double pitch) {
        update(camX, camY, camZ, yaw, pitch);

        if(status) {
            if(getY2() > 0) {
                double screenSize = getSize() / getY2() * 160;
                window.setColor(color);

                if(filled)
                    window.fillRect((int)(getScreenX() - screenSize / 2), (int)(getScreenY() - screenSize / 2), (int)screenSize, (int)screenSize);
                else {
                    ((Graphics2D) window).setStroke(new BasicStroke((int)(thickness / getY2() * 160), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    window.drawRect((int)(getScreenX() - screenSize / 2), (int)(getScreenY() - screenSize / 2), (int)screenSize, (int)screenSize);
                    ((Graphics2D) window).setStroke(new BasicStroke(0, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                }
            }
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double px) {
        x = px;
    }

    public void setY(double py) {
        y = py;
    }

    public void setZ(double pz) {
        z = pz;
    }

    public void moveX(double px) {
        setX(getX() + px);
    }

    public void moveY(double py) {
        setY(getY() + py);
    }

    public void moveZ(double pz) {
        setZ(getZ() + pz);
    }

    public void rotateYaw(double a, double px, double py, double pz) {
        setX(((getX() - px) * Math.cos(a) - (getY() - py) * Math.sin(a)) + px);
        setY(((getX() - px) * Math.sin(a) + (getY() - py) * Math.cos(a)) + py);
    }

    public void rotatePitch(double a, double px, double py, double pz) {
        setY(((getY() - py) * Math.cos(a) - (getZ() - pz) * Math.sin(a)) + py);
        setZ(((getY() - py) * Math.sin(a) + (getZ() - pz) * Math.cos(a)) + pz);
    }

    public void rotateYaw(double a, Point p) {
        setX(((getX() - p.getX()) * Math.cos(a) - (getY() - p.getY()) * Math.sin(a)) + p.getX());
        setY(((getX() - p.getX()) * Math.sin(a) + (getY() - p.getY()) * Math.cos(a)) + p.getY());
    }

    public void rotatePitch(double a, Point p) {
        setY(((getY() - p.getY()) * Math.cos(a) - (getZ() - p.getZ()) * Math.sin(a)) + p.getY());
        setZ(((getY() - p.getY()) * Math.sin(a) + (getZ() - p.getZ()) * Math.cos(a)) + p.getZ());
    }

    public void setPos(double px, double py, double pz) {
        setX(px);
        setY(py);
        setZ(pz);
    }

    public void setPos(Point p) {
        setX(p.getX());
        setY(p.getY());
        setZ(p.getZ());
    }

    public double getSize() {
        return size;
    }

    public void setSize(double s) {
        size = s;
    }

    public void reflectX(double px) {
        setX(px - (getX() - px));
    }

    public void reflectX(Point p) {
        setX(p.getX() - (getX() - p.getX()));
    }

    public void reflectY(double py) {
        setY(py - (getY() - py));
    }

    public void reflectY(Point p) {
        setY(p.getY() - (getY() - p.getY()));
    }

    public void reflectZ(double pz) {
        setZ(pz - (getZ() - pz));
    }

    public void reflectZ(Point p) {
        setZ(p.getZ() - (getZ() - p.getZ()));
    }

    public void setScreenX(double px) {
        screenX = px;
    }

    public void setScreenY(double py) {
        screenY = py;
    }

    public double getScreenX() {
        return screenX;
    }

    public double getScreenY() {
        return screenY;
    }

    public double getDistance() {
        return distance;
    }

    public double getY2() {
        return y2;
    }

    public void setStatus(boolean b) {
        status = b;
    }

    public void setFilled(boolean b) {
        filled = b;
    }

    public void setColor(Color c) {
        color = c;
    }

    public Color getColor() {
        return color;
    }

    public void setThickness(double t) {
        thickness = t;
    }

    public String toString() {
        return "Point" + "\n(" + getX() +", " + getY() + ", " + getZ() + ")\n" + getColor().getRed() + ", " + getColor().getGreen() + ", " + getColor().getBlue() + "\n" + status + "\n" + filled + "\n" + thickness + "\n";
    }
}
