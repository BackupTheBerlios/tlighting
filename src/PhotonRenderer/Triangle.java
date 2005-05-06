package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;

public class Triangle implements Intersectable {
    protected Vertex v0;
    protected Vertex v1;
    protected Vertex v2;
    protected Vector3 ng;
    private Shader shader;
    private double d;
    private int dropAxis;
    private double edge1x;
    private double edge1y;
    private double edge2x;
    private double edge2y;

    public Triangle(Shader shader, Vertex v0, Vertex v1, Vertex v2) {
        this.shader = shader;
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        ng = Vector3.cross(Point3.sub(v1.p, v0.p, new Vector3()), Point3.sub(v2.p, v0.p, new Vector3()), new Vector3()).normalize();
        d = -((ng.x * v0.p.x) + (ng.y * v0.p.y) + (ng.z * v0.p.z));
        if (Math.abs(ng.y) > Math.abs(ng.x))
            dropAxis = (Math.abs(ng.z) > Math.abs(ng.y)) ? 2 : 1;
        else
            dropAxis = (Math.abs(ng.z) > Math.abs(ng.x)) ? 2 : 0;
        switch (dropAxis) {
            case 0:
                edge1x = v0.p.y - v1.p.y;
                edge2x = v0.p.y - v2.p.y;
                edge1y = v0.p.z - v1.p.z;
                edge2y = v0.p.z - v2.p.z;
                break;
            case 1:
                edge1x = v0.p.x - v1.p.x;
                edge2x = v0.p.x - v2.p.x;
                edge1y = v0.p.z - v1.p.z;
                edge2y = v0.p.z - v2.p.z;
                break;
            default:
                edge1x = v0.p.x - v1.p.x;
                edge2x = v0.p.x - v2.p.x;
                edge1y = v0.p.y - v1.p.y;
                edge2y = v0.p.y - v2.p.y;
        }
        double s = 1.0 / ((edge1x * edge2y) - (edge2x * edge1y));
        edge1x *= s;
        edge1y *= s;
        edge2x *= s;
        edge2y *= s;
    }

    public BoundingBox getBounds() {
        BoundingBox bounds = new BoundingBox();
        bounds.include(v0.p);
        bounds.include(v1.p);
        bounds.include(v2.p);
        return bounds;
    }

    public boolean intersects(BoundingBox box) {
        return box.intersects(getBounds());
    }

    public Shader getSurfaceShader() {
        return shader;
    }

    public void setSurfaceLocation(RenderState state) {
        Vertex dest = state.getVertex();
        double u = state.getU();
        double v = state.getV();
        state.getRay().getPoint(state.getT(), dest.p);
        if (v0.n != null) {
            dest.n.x = v0.n.x + (u * (v1.n.x - v0.n.x)) + (v * (v2.n.x - v0.n.x));
            dest.n.y = v0.n.y + (u * (v1.n.y - v0.n.y)) + (v * (v2.n.y - v0.n.y));
            dest.n.z = v0.n.z + (u * (v1.n.z - v0.n.z)) + (v * (v2.n.z - v0.n.z));
            dest.n.normalize();
        } else
            dest.n.set(ng);
        dest.tex.x = v0.tex.x + (u * (v1.tex.x - v0.tex.x)) + (v * (v2.tex.x - v0.tex.x));
        dest.tex.y = v0.tex.y + (u * (v1.tex.y - v0.tex.y)) + (v * (v2.tex.y - v0.tex.y));
        state.getGeoNormal().set(ng);
    }

    public void intersect(RenderState state) {
        Ray r = state.getRay();
        double vd;
        double vx;
        double vy;
        Point3 orig = r.getOrigin();
        Vector3 dir = r.getDirection();
        /* Check if the ray lies parallel to the plane */
        vd = Vector3.dot(ng, dir);
        if ((vd > -0.000001) && (vd < 0.000001))
            return;
        /* Check if ray intersects plane */
        double t = -((ng.x * orig.x) + (ng.y * orig.y) + (ng.z * orig.z) + d) / vd;
        if (!r.isInside(t))
            return;
        /* Check if intersection is inside the triangle */
        switch (dropAxis) {
            case 0:
                vx = (orig.y + (dir.y * t)) - v0.p.y;
                vy = (orig.z + (dir.z * t)) - v0.p.z;
                break;
            case 1:
                vx = (orig.x + (dir.x * t)) - v0.p.x;
                vy = (orig.z + (dir.z * t)) - v0.p.z;
                break;
            default:
                vx = (orig.x + (dir.x * t)) - v0.p.x;
                vy = (orig.y + (dir.y * t)) - v0.p.y;
        }
        double u = (edge2x * vy) - (edge2y * vx);
        if ((u < 0.0) || (u > 1.0))
            return;
        double v = (edge1y * vx) - (edge1x * vy);
        if ((v < 0.0) || ((u + v) > 1.0))
            return;
        r.setMax(t);
        state.setIntersection(this, t, u, v);
    }

    public boolean intersects(Ray r) {
        double vd;
        double vx;
        double vy;
        Point3 orig = r.getOrigin();
        Vector3 dir = r.getDirection();
        /* Check if the ray lies parallel to the plane */
        vd = Vector3.dot(ng, dir);
        if ((vd > -0.000001) && (vd < 0.000001))
            return false;
        /* Check if ray intersects plane */
        double t = -((ng.x * orig.x) + (ng.y * orig.y) + (ng.z * orig.z) + d) / vd;
        if (!r.isInside(t))
            return false;
        /* Check if intersection is inside the triangle */
        switch (dropAxis) {
            case 0:
                vx = (orig.y + (dir.y * t)) - v0.p.y;
                vy = (orig.z + (dir.z * t)) - v0.p.z;
                break;
            case 1:
                vx = (orig.x + (dir.x * t)) - v0.p.x;
                vy = (orig.z + (dir.z * t)) - v0.p.z;
                break;
            default:
                vx = (orig.x + (dir.x * t)) - v0.p.x;
                vy = (orig.y + (dir.y * t)) - v0.p.y;
        }
        double u = (edge2x * vy) - (edge2y * vx);
        if ((u < 0.0) || (u > 1.0))
            return false;
        double v = (edge1y * vx) - (edge1x * vy);
        if ((v < 0.0) || ((u + v) > 1.0))
            return false;
        return true;
    }
}