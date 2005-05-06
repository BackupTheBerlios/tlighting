package drawing_prog;

public final class Point3 {
    public double x;
    public double y;
    public double z;

    public Point3() {}

    public Point3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3(Point3 p) {
        x = p.x;
        y = p.y;
        z = p.z;
    }

    public final double distanceTo(Point3 p) {
        double dx = x - p.x;
        double dy = y - p.y;
        double dz = z - p.z;
        return Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
    }

    public final double distanceToSquared(Point3 p) {
        double dx = x - p.x;
        double dy = y - p.y;
        double dz = z - p.z;
        return (dx * dx) + (dy * dy) + (dz * dz);
    }

    public final Point3 set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public final Point3 set(Point3 p) {
        x = p.x;
        y = p.y;
        z = p.z;
        return this;
    }

    public static final Point3 add(Point3 p, Vector3 v, Point3 dest) {
        dest.x = p.x + v.x;
        dest.y = p.y + v.y;
        dest.z = p.z + v.z;
        return dest;
    }

    public static final Vector3 sub(Point3 p1, Point3 p2, Vector3 dest) {
        dest.x = p1.x - p2.x;
        dest.y = p1.y - p2.y;
        dest.z = p1.z - p2.z;
        return dest;
    }

    public static final Point3 mid(Point3 p1, Point3 p2, Point3 dest) {
        dest.x = 0.5 * (p1.x + p2.x);
        dest.y = 0.5 * (p1.y + p2.y);
        dest.z = 0.5 * (p1.z + p2.z);
        return dest;
    }
}