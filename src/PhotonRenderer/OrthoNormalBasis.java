package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;

public final class OrthoNormalBasis {
    private final Vector3 u;
    private final Vector3 v;
    private final Vector3 w;

    private OrthoNormalBasis() {
        u = new Vector3();
        v = new Vector3();
        w = new Vector3();
    }

    public Vector3 transform(Vector3 a, Vector3 dest) {
        dest.x = (a.x * u.x) + (a.y * v.x) + (a.z * w.x);
        dest.y = (a.x * u.y) + (a.y * v.y) + (a.z * w.y);
        dest.z = (a.x * u.z) + (a.y * v.z) + (a.z * w.z);
        return dest;
    }

    public Vector3 transform(Vector3 a) {
        double x = (a.x * u.x) + (a.y * v.x) + (a.z * w.x);
        double y = (a.x * u.y) + (a.y * v.y) + (a.z * w.y);
        double z = (a.x * u.z) + (a.y * v.z) + (a.z * w.z);
        return a.set(x, y, z);
    }

    public Vector3 untransform(Vector3 a, Vector3 dest) {
        dest.x = Vector3.dot(a, u);
        dest.y = Vector3.dot(a, v);
        dest.z = Vector3.dot(a, w);
        return dest;
    }

    public static final OrthoNormalBasis makeFromW(Vector3 w) {
        OrthoNormalBasis onb = new OrthoNormalBasis();
        w.normalize(onb.w);
        if ((Math.abs(onb.w.x) < Math.abs(onb.w.y)) && (Math.abs(onb.w.x) < Math.abs(onb.w.z))) {
            onb.v.x = 0;
            onb.v.y = onb.w.z;
            onb.v.z = -onb.w.y;
        } else if (Math.abs(onb.w.y) < Math.abs(onb.w.z)) {
            onb.v.x = onb.w.z;
            onb.v.y = 0;
            onb.v.z = -onb.w.x;
        } else {
            onb.v.x = onb.w.y;
            onb.v.y = -onb.w.x;
            onb.v.z = 0;
        }
        Vector3.cross(onb.v.normalize(), onb.w, onb.u);
        return onb;
    }

    public static final OrthoNormalBasis makeFromWV(Vector3 w, Vector3 v) {
        OrthoNormalBasis onb = new OrthoNormalBasis();
        w.normalize(onb.w);
        Vector3.cross(v, onb.w, onb.u).normalize();
        Vector3.cross(onb.w, onb.u, onb.v);
        return onb;
    }
}