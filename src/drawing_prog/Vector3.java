package drawing_prog;

public final class Vector3 {
    public double x;
    public double y;
    public double z;

    public Vector3() {}

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public final double length() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public final double lengthSquared() {
        return (x * x) + (y * y) + (z * z);
    }

    public final Vector3 negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public final Vector3 negate(Vector3 dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        return dest;
    }

    public final Vector3 normalize() {
        double in = 1.0 / Math.sqrt((x * x) + (y * y) + (z * z));
        x *= in;
        y *= in;
        z *= in;
        return this;
    }

    public final Vector3 normalize(Vector3 dest) {
        double in = 1.0 / Math.sqrt((x * x) + (y * y) + (z * z));
        dest.x = x * in;
        dest.y = y * in;
        dest.z = z * in;
        return dest;
    }

    public final Vector3 set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public final Vector3 set(Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    public static final double dot(Vector3 v1, Vector3 v2) {
        return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
    }

    public static final Vector3 cross(Vector3 v1, Vector3 v2, Vector3 dest) {
        dest.x = (v1.y * v2.z) - (v1.z * v2.y);
        dest.y = (v1.z * v2.x) - (v1.x * v2.z);
        dest.z = (v1.x * v2.y) - (v1.y * v2.x);
        return dest;
    }
}