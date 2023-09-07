public class Light {

    private double x, y, z;

    public Light() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Light(double px, double py, double pz) {
        x = px;
        y = py;
        z = pz;
    }

    public Light(Point p) {
        x = p.getX();
        y = p.getY();
        z = p.getZ();
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
}
