package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;

/**
 * Represents a surface point, defined by a point, normal and texture mapping coordinates.
 */
public class Vertex {
    /**
     * Surface point.
     */
    public Point3 p;

    /**
     * Surface normal.
     */
    public Vector3 n;

    /**
     * Texture mapping coordinates.
     */
    public Point2 tex;

    /**
     * Creates a new vertex, allocates storage for all fields.
     */
    public Vertex() {
        p = new Point3();
        n = new Vector3();
        tex = new Point2();
    }

    /**
     * Copy the specified vertex into this one.
     *
     * @param v vertex to be copied.
     */
    public void set(Vertex v) {
        p.set(v.p);
        n.set(v.n);
        tex.set(v.tex);
    }
}