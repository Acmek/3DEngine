import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class Sphere {

    private Point center;
    private double size;
    private Color color;

    private boolean status = true, filled = true;

    private double thickness = 0;

    public Sphere() {
        setCenter(new Point());

        setSize(Math.random() * (50 - 20) + 20);

        setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
    }

    public Sphere(Point p) {
        setCenter(p);

        setSize(Math.random() * (50 - 20) + 20);

        setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
    }

    public Sphere(double px, double py, double pz) {
        setCenter(new Point(px, py, pz));

        setSize(Math.random() * (50 - 20) + 20);

        setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
    }

    public Sphere(Point p, double s) {
        setCenter(p);

        setSize(s);

        setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
    }
    
    public Sphere(double px, double py, double pz, double s) {
        setCenter(new Point(px, py, pz));

        setSize(s);

        setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
    }

    public Sphere(double px, double py, double pz, double s, Color c) {
        setCenter(new Point(px, py, pz));

        setSize(s);

        setColor(c);
    }

    public Sphere(Point p, double s, Color c) {
        if(p == null)
            setCenter(new Point());
        else
            setCenter(p);

        if(s == -1)
            setSize(Math.random() * (50 - 20) + 20);
        else
            setSize(s);

        if(c == null)
            setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
        else
            setColor(c);
    }

    public void draw(Graphics window, double camX, double camY, double camZ, double yaw, double pitch) {
        getCenter().update(camX, camY, camZ, yaw, pitch);

        if(status) {
            if(getCenter().getY2() > 0) {
                double screenSize = getSize() / getCenter().getY2() * 160;
                window.setColor(getColor());

                if(filled)
                    window.fillOval((int)(getCenter().getScreenX() - screenSize / 2), (int)(getCenter().getScreenY() - screenSize / 2), (int)screenSize, (int)screenSize);
                else {
                    ((Graphics2D) window).setStroke(new BasicStroke((int)(thickness / center.getY2() * 160), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    window.drawOval((int)(getCenter().getScreenX() - screenSize / 2), (int)(getCenter().getScreenY() - screenSize / 2), (int)screenSize, (int)screenSize);
                    ((Graphics2D) window).setStroke(new BasicStroke(0, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                }
            }
        }
    }

    public double getDistance() {
        return getCenter().getDistance();
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point p) {
        center = p;
    }

    public double getX() {
        return getCenter().getX();
    }

    public double getY() {
        return getCenter().getY();
    }

    public double getZ() {
        return getCenter().getZ();
    }

    public void setX(double px) {
        getCenter().setX(px);
    }

    public void setY(double py) {
        getCenter().setY(py);
    }

    public void setZ(double pz) {
        getCenter().setZ(pz);
    }

    public void moveX(double px) {
        getCenter().moveX(px);
    }

    public void moveY(double py) {
        getCenter().moveY(py);
    }

    public void moveZ(double pz) {
        getCenter().moveZ(pz);
    }

    public void rotateYaw(double a, double px, double py, double pz) {
        getCenter().rotateYaw(a, px, py, pz);
    }

    public void rotatePitch(double a, double px, double py, double pz) {
        getCenter().rotatePitch(a, px, py, pz);
    }

    public void rotateYaw(double a, Point p) {
        getCenter().rotateYaw(a, p);
    }

    public void rotatePitch(double a, Point p) {
        getCenter().rotatePitch(a, p);
    }

    public void setPos(double px, double py, double pz) {
        getCenter().setPos(px, py, pz);
    }

    public void setPos(Point p) {
        getCenter().setPos(p);
    }

    public double getSize() {
        return size;
    }

    public void setSize(double s) {
        size = s;
    }

    public void scale(double s) {
        setSize(getSize() + s);
    }

    public void scale(double s, Point p) {
        setSize(getSize() + s);

        moveX(s * Math.cos(Math.atan(getX() - p.getX()) - Math.PI / 2));
        moveY(s * Math.sin(Math.atan(getY() - p.getY())));
        moveZ(s * Math.sin(Math.atan(getZ() - p.getZ())));
    }

    public void scale(double s, double px, double py, double pz) {
        setSize(getSize() + s);

        moveX(s * Math.cos(Math.atan(getX() - px) - Math.PI / 2));
        moveY(s * Math.sin(Math.atan(getY() - py)));
        moveZ(s * Math.sin(Math.atan(getZ() - pz)));
    }

    public void reflectX(double px) {
        getCenter().reflectX(px);
    }

    public void reflectX(Point p) {
        getCenter().reflectX(p);
    }

    public void reflectY(double py) {
        getCenter().reflectY(py);
    }

    public void reflectY(Point p) {
        getCenter().reflectY(p);
    }

    public void reflectZ(double pz) {
        getCenter().reflectZ(pz);
    }

    public void reflectZ(Point p) {
        getCenter().reflectZ(p);
    }

    public void setThickness(double t) {
        thickness = t;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color c) {
        color = c;
    }

    public void setStatus(boolean b) {
        status = b;
    }

    public void setFilled(boolean b) {
        filled = b;
    }

    public String toString() {
        return "Sphere" + "\n(" + getX() +", " + getY() + ", " + getZ() + ")\n" + getSize() + "\n" + getColor().getRed() + ", " + getColor().getGreen() + ", " + getColor().getBlue() + "\n" + status + "\n" + filled + "\n" + thickness + "\n";
    }
}
