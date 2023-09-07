import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class Line {
    private Point[] points;
    private Point center;

    private double distance;

    private Color color;

    private double thickness;

    private int[] xPoints, yPoints;

    private boolean status = true;

    public Line() {
        points = new Point[2];
        for(int i = 0; i < 2; i++)
            setPoint(i, new Point());

        setThickness(Math.random() * 10);

        setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));

        xPoints = new int[2];
        yPoints = new int[2];

        double xSum = 0, ySum = 0, zSum = 0;
        for(int i = 0; i < getPoints().length; i++) {
            xSum += getPoint(i).getX();
            ySum += getPoint(i).getY();
            zSum += getPoint(i).getZ();
        }
        center = new Point(xSum / getPoints().length, ySum / getPoints().length, zSum / getPoints().length);
    }

    public Line(Point[] arr) {
        points = arr;

        setThickness(Math.random() * (10 - 1) + 1);

        setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));

        xPoints = new int[getPoints().length];
        yPoints = new int[getPoints().length];

        double xSum = 0, ySum = 0, zSum = 0;
        for(int i = 0; i < getPoints().length; i++) {
            xSum += getPoint(i).getX();
            ySum += getPoint(i).getY();
            zSum += getPoint(i).getZ();
        }
        center = new Point(xSum / getPoints().length, ySum / getPoints().length, zSum / getPoints().length);
    }

    public Line(Point[] arr, double t) {
        points = arr;
        
        setThickness(t);;

        setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));

        xPoints = new int[getPoints().length];
        yPoints = new int[getPoints().length];

        double xSum = 0, ySum = 0, zSum = 0;
        for(int i = 0; i < getPoints().length; i++) {
            xSum += getPoint(i).getX();
            ySum += getPoint(i).getY();
            zSum += getPoint(i).getZ();
        }
        center = new Point(xSum / getPoints().length, ySum / getPoints().length, zSum / getPoints().length);
    }

    public Line(Point[] arr, double t, Color c) {
        if(arr == null) {
            points = new Point[2];
            for(int i = 0; i < 2; i++)
                setPoint(i, new Point());
        }
        else
            points = arr;

        if(t == -1)
            setThickness(Math.random() * (10 - 1) + 1);
        else
            setThickness(t);;

        if(c == null)
            setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
        else
            setColor(c);;

        xPoints = new int[getPoints().length];
        yPoints = new int[getPoints().length];

        double xSum = 0, ySum = 0, zSum = 0;
        for(int i = 0; i < getPoints().length; i++) {
            xSum += getPoint(i).getX();
            ySum += getPoint(i).getY();
            zSum += getPoint(i).getZ();
        }
        center = new Point(xSum / getPoints().length, ySum / getPoints().length, zSum / getPoints().length);
    }

    public void draw(Graphics window, double camX, double camY, double camZ, double yaw, double pitch) {
        boolean test = true;
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).update(camX, camY, camZ, yaw, pitch);

            if(getPoint(i).getY2() < 0)
                test = false;
        }
        getCenter().update(camX, camY, camZ, yaw, pitch);

        distance = center.getDistance();

        if(status) {
            if(test) {
                for(int i = 0; i < getPoints().length; i++) {
                    xPoints[i] = (int)getPoint(i).getScreenX();
                    yPoints[i] = (int)getPoint(i).getScreenY();
                }

                ((Graphics2D) window).setStroke(new BasicStroke((int)(getThickness() / center.getY2() * 160), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                window.setColor(color);
                window.drawPolyline(xPoints, yPoints, xPoints.length);
                ((Graphics2D) window).setStroke(new BasicStroke(0, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            }
        }
    }

    public double getDistance() {
        return distance;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point p) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setPos(getPoint(i).getX() + (p.getX() - getCenter().getX()), getPoint(i).getY() + (p.getY() - getCenter().getY()), getPoint(i).getZ() + (p.getZ() - getCenter().getZ()));
        }
        center = p;
    }

    public void setCenter(double px, double py, double pz) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setPos(getPoint(i).getX() + (px - getCenter().getX()), getPoint(i).getY() + (py - getCenter().getY()), getPoint(i).getZ() + (pz - getCenter().getZ()));
        }
        getCenter().setPos(px, py, pz);
    }

    public void setCenterX(double px) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setX(getPoint(i).getX() + (px - getCenter().getX()));
        }
        getCenter().setX(px);
    }

    public void setCenterY(double py) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setY(getPoint(i).getY() + (py - getCenter().getY()));
        }
        getCenter().setY(py);
    }

    public void setCenterZ(double pz) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setZ(getPoint(i).getZ() + (pz - getCenter().getZ()));
        }
        getCenter().setY(pz);
    }

    public double getCenterX() {
        return getCenter().getX();
    }

    public double getCenterY() {
        return getCenter().getY();
    }

    public double getCenterZ() {
        return getCenter().getZ();
    }

    public void moveCenterX(double px) {
        setCenterX(getCenterX() + px);
    }

    public void moveCenterY(double py) {
        setCenterY(getCenterY() + py);
    }

    public void moveCenterZ(double pz) {
        setCenterZ(getCenterZ() + pz);
    }

    public void rotateYaw(double a) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setX(((getPoint(i).getX() - getCenterX()) * Math.cos(a) - (getPoint(i).getY() - getCenterY()) * Math.sin(a)) + getCenterX());
            getPoint(i).setY(((getPoint(i).getX() - getCenterX()) * Math.sin(a) + (getPoint(i).getY() - getCenterY()) * Math.cos(a)) + getCenterY());
        }
    }

    public void rotatePitch(double a) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setY(((getPoint(i).getY() - getCenterY()) * Math.cos(a) - (getPoint(i).getZ() - getCenterZ()) * Math.sin(a)) + getCenterY());
            getPoint(i).setZ(((getPoint(i).getY() - getCenterY()) * Math.sin(a) + (getPoint(i).getZ() - getCenterZ()) * Math.cos(a)) + getCenterZ());
        }
    }

    public void rotateYaw(double a, double px, double py, double pz) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setX(((getPoint(i).getX() - px) * Math.cos(a) - (getPoint(i).getY() - py) * Math.sin(a)) + px);
            getPoint(i).setY(((getPoint(i).getX() - px) * Math.sin(a) + (getPoint(i).getY() - py) * Math.cos(a)) + py);
        }
        getCenter().setX(((getCenterX() - px) * Math.cos(a) - (getCenterY() - py) * Math.sin(a)) + px);
        getCenter().setY(((getCenterX() - px) * Math.sin(a) + (getCenterY() - py) * Math.cos(a)) + py);
    }

    public void rotatePitch(double a, double px, double py, double pz) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setY(((getPoint(i).getY() - py) * Math.cos(a) - (getPoint(i).getZ() - pz) * Math.sin(a)) + py);
            getPoint(i).setZ(((getPoint(i).getY() - py) * Math.sin(a) + (getPoint(i).getZ() - pz) * Math.cos(a)) + pz);
        }
        getCenter().setY(((getCenterY() - py) * Math.cos(a) - (getCenterZ() - pz) * Math.sin(a)) + py);
        getCenter().setZ(((getCenterY() - py) * Math.sin(a) + (getCenterZ() - pz) * Math.cos(a)) + pz);
    }

    public void rotateYaw(double a, Point p) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setX(((getPoint(i).getX() - p.getX()) * Math.cos(a) - (getPoint(i).getY() - p.getY()) * Math.sin(a)) + p.getX());
            getPoint(i).setY(((getPoint(i).getX() - p.getX()) * Math.sin(a) + (getPoint(i).getY() - p.getY()) * Math.cos(a)) + p.getY());
        }
        getCenter().setX(((getCenterX() - p.getX()) * Math.cos(a) - (getCenterY() - p.getY()) * Math.sin(a)) + p.getX());
        getCenter().setY(((getCenterX() - p.getX()) * Math.sin(a) + (getCenterY() - p.getY()) * Math.cos(a)) + p.getY());
    }

    public void rotatePitch(double a, Point p) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setY(((getPoint(i).getY() - p.getY()) * Math.cos(a) - (getPoint(i).getZ() - p.getZ()) * Math.sin(a)) + p.getY());
            getPoint(i).setZ(((getPoint(i).getY() - p.getY()) * Math.sin(a) + (getPoint(i).getZ() - p.getZ()) * Math.cos(a)) + p.getZ());
        }
        getCenter().setY(((getCenterY() - p.getY()) * Math.cos(a) - (getCenterZ() - p.getZ()) * Math.sin(a)) + p.getY());
        getCenter().setZ(((getCenterY() - p.getY()) * Math.sin(a) + (getCenterZ() - p.getZ()) * Math.cos(a)) + p.getZ());
    }

    public void stretchX(double s) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).moveX(s * Math.cos(Math.atan(getPoint(i).getX() - getCenterX()) - Math.PI / 2));
        }
    }

    public void stretchY(double s) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).moveY(s * Math.sin(Math.atan(getPoint(i).getY() - getCenterY())));
        }
    }

    public void stretchZ(double s) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).moveZ(s * Math.sin(Math.atan(getPoint(i).getZ() - getCenterZ())));
        }
    }

    public void scale(double s) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).moveX(s * Math.cos(Math.atan(getPoint(i).getX() - getCenterX()) - Math.PI / 2));
            getPoint(i).moveY(s * Math.sin(Math.atan(getPoint(i).getY() - getCenterY())));
            getPoint(i).moveZ(s * Math.sin(Math.atan(getPoint(i).getZ() - getCenterZ())));
        }
    }

    public void scale(double s, Point p) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).moveX(s * Math.cos(Math.atan(getPoint(i).getX() - p.getX()) - Math.PI / 2));
            getPoint(i).moveY(s * Math.sin(Math.atan(getPoint(i).getY() - p.getY())));
            getPoint(i).moveZ(s * Math.sin(Math.atan(getPoint(i).getZ() - p.getZ())));
        }
        getCenter().moveX(s * Math.cos(Math.atan(getCenterX() - p.getX()) - Math.PI / 2));
        getCenter().moveY(s * Math.sin(Math.atan(getCenterY() - p.getY())));
        getCenter().moveZ(s * Math.sin(Math.atan(getCenterZ() - p.getZ())));
    }

    public void scale(double s, double px, double py, double pz) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).moveX(s * Math.cos(Math.atan(getPoint(i).getX() - px) - Math.PI / 2));
            getPoint(i).moveY(s * Math.sin(Math.atan(getPoint(i).getY() - py)));
            getPoint(i).moveZ(s * Math.sin(Math.atan(getPoint(i).getZ() - pz)));
        }
        getCenter().moveX(s * Math.cos(Math.atan(getCenterX() - px) - Math.PI / 2));
        getCenter().moveY(s * Math.sin(Math.atan(getCenterY() - py)));
        getCenter().moveZ(s * Math.sin(Math.atan(getCenterZ() - pz)));
    }

    public void reflectX() {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setX(getCenterX() - (getPoint(i).getX() - getCenterX()));
        }
    }

    public void reflectX(double px) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setX(px - (getPoint(i).getX() - px));
        }
        getCenter().setX(px - (getCenterX() - px));
    }

    public void reflectX(Point p) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setX(p.getX() - (getPoint(i).getX() - p.getX()));
        }
        getCenter().setX(p.getX() - (getCenterX() - p.getX()));
    }

    public void reflectY() {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setY(getCenterY() - (getPoint(i).getY() - getCenterY()));
        }
    }

    public void reflectY(double py) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setY(py - (getPoint(i).getY() - py));
        }
        getCenter().setY(py - (getCenterY() - py));
    }

    public void reflectY(Point p) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setY(p.getY() - (getPoint(i).getY() - p.getY()));
        }
        getCenter().setY(p.getY() - (getCenterY() - p.getY()));
    }

    public void reflectZ() {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setZ(getCenterZ() - (getPoint(i).getZ() - getCenterZ()));
        }
    }

    public void reflectZ(double pz) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setZ(pz - (getPoint(i).getZ() - pz));
        }
        getCenter().setZ(pz - (getCenterZ() - pz));
    }

    public void reflectZ(Point p) {
        for(int i = 0; i < getPoints().length; i++) {
            getPoint(i).setZ(p.getZ() - (getPoint(i).getZ() - p.getZ()));
        }
        getCenter().setZ(p.getZ() - (getCenterZ() - p.getZ()));
    }

    public Point[] getPoints() {
        return points;
    }

    public Point getPoint(int index) {
        return points[index];
    }

    public void setPoint(int index, Point p) {
        points[index] = p;
    }

    public double getThickness() {
        return thickness;
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

    public String toString() {
        String str = "Line\n";

        for(int i = 0; i < getPoints().length - 1; i++) {
            str += "(" + getPoint(i).getX() + ", " + getPoint(i).getY() + ", " + getPoint(i).getZ() + "), ";
        }
        str += "(" + getPoint(getPoints().length - 1).getX() + ", " + getPoint(getPoints().length - 1).getY() + ", " + getPoint(getPoints().length - 1).getZ() + ")";

        return str + "\n" + thickness + "\n" + getColor().getRed() + ", " + getColor().getGreen() + ", " + getColor().getBlue() + "\n" + status + "\n";
    }
}
