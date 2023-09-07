public class Camera {

    private double x;
    private double y;
    private double z;

    private double yaw;
    private double pitch;

    public Camera() {
        setPos(0, 0, 0);

        setAngle(0, 0);
    }

    public Camera(double px, double py, double pz) {
        setPos(px, py, pz);

        setAngle(0, 0);
    }

    public Camera(double ya, double pi) {
        setPos(0, 0, 0);

        setAngle(ya, pi);
    }

    public Camera(double px, double py, double pz, double ya, double pi) {
        setPos(px, py, pz);

        setAngle(ya, pi);
    }

    public void lookAt(Point p) {
        setAngle(Math.atan2(p.getY() - getY(), p.getX() - getX()), Math.atan2(p.getZ() - getZ(), Math.sqrt((p.getX() - getX()) * (p.getX() - getX()) + (p.getY() - getY()) * (p.getY() - getY()))));
    }

    public void lookAt(double px, double py, double pz) {
        setAngle(Math.atan2(py - getY(), px - getX()), Math.atan2(pz - getZ(), Math.sqrt((px - getX()) * (px - getX()) + (py - getY()) * (py - getY()))));
    }

    public void leftAndRight(double num) {
        moveX(num * Math.cos(getYaw() - Math.PI / 2));
        moveY(num * Math.sin(getYaw() - Math.PI / 2));
    }

    public void forwardAndBack(double num) {
        moveX(num * Math.cos(getYaw()));
        moveY(num * Math.sin(getYaw()));
        //moveZ(num * Math.sin(getPitch()));
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

    public void moveX(double px) {
        setX(getX() + px);
    }

    public void moveY(double py) {
        setY(getY() + py);
    }

    public void moveZ(double pz) {
        setZ(getZ() + pz);
    }

    public double getYaw() {
        return yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public void moveYaw(double ya) {
        setYaw(getYaw() + ya);
    }

    public void movePitch(double pi) {
        setPitch(getPitch() + pi);
    }

    public void setCamera(double px, double py, double pz, double ya, double pi) {
        setPos(px, py, pz);
        
        setAngle(ya, pi);
    }
    
    public void setPos(double px, double py, double pz) {
        setX(px);
        setY(py);
        setZ(pz);
    }

    public void setAngle(double ya, double pi) {
        setYaw(ya);
        setPitch(pi);
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

    public void setYaw(double ya) {
        yaw = ya;

        while(yaw < 0) { yaw += Math.PI * 2; }
        while(yaw > Math.PI * 2) { yaw -= Math.PI * 2; }
    }

    public void setPitch(double pi) {
        pitch = pi;

        while(pitch < 0) { pitch += Math.PI * 2; }
        while(pitch > Math.PI * 2) { pitch -= Math.PI * 2; }
    }
}
