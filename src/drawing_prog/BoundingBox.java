/*
 * BoundingBox.java
 *
 * Created on May 5, 2005, 1:36 AM
 */

package drawing_prog;

//import org.sunflow.math.Point3;
//import org.sunflow.math.Vector3;

/**
 * 3D axis-aligned bounding box. Stores only the minimum and maximum corner points.
 */
public class BoundingBox {
    private Point3 minimum;
    private Point3 maximum;
    private Point3 center;
    private Vector3 extents;

    /**
     * Creates an empty box. The minimum point will have all components set to positive infinity, and the maximum will
     * have all components set to negative infinity.
     */
    public BoundingBox() {
        minimum = new Point3(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        maximum = new Point3(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        center = new Point3();
        extents = new Vector3();
    }

    /**
     * Gets the minimum corner of the box. That is the corner of smallest coordinates on each axis. Note that the
     * returned reference is not cloned for efficiency purposes so care must be taken not to change the coordinates of
     * the point.
     *
     * @return a reference to the minimum corner
     */
    public Point3 getMinimum() {
        return minimum;
    }

    /**
     * Gets the maximum corner of the box. That is the corner of largest coordinates on each axis. Note that the
     * returned reference is not cloned for efficiency purposes so care must be taken not to change the coordinates of
     * the point.
     *
     * @return a reference to the maximum corner
     */
    public Point3 getMaximum() {
        return maximum;
    }

    /**
     * Gets the center of the box, computed as (min + max) / 2.
     *
     * @return a reference to the center of the box
     */
    public Point3 getCenter() {
        return Point3.mid(minimum, maximum, center);
    }

    /**
     * Gets the extents vector for the box. This vector is computed as (max - min). Its coordinates are always positive
     * and represent the dimensions of the box along the three axes.
     *
     * @return a refreence to the extent vector
     * @see org.sunflow.math.Vector3#length()
     */
    public Vector3 getExtents() {
        return Point3.sub(maximum, minimum, extents);
    }

    /**
     * Scales the box up or down in all directions by a the given percentage. The routine only uses the magnitude of
     * the scale factor to avoid inversion of the minimum and maximum.
     *
     * @param s scale factor
     */
    public void scale(double s) {
        Point3.mid(minimum, maximum, center);
        Point3.sub(maximum, minimum, extents);
        s *= ((s > 0.0) ? 0.5 : (-0.5));
        minimum.x = center.x - (extents.x * s);
        minimum.y = center.y - (extents.y * s);
        minimum.z = center.z - (extents.z * s);
        maximum.x = center.x + (extents.x * s);
        maximum.y = center.y + (extents.y * s);
        maximum.z = center.z + (extents.z * s);
    }

    /**
     * Returns <code>true</code> when the box has just been initialized, and is still empty. This method might also
     * return true if the state of the box becomes inconsistent and some component of the minimum corner is larger than
     * the corresponding coordinate of the maximum corner.
     *
     * @return <code>true</code> if the box is empty, <code>false</code> otherwise
     */
    public boolean isEmpty() {
        return (maximum.x < minimum.x) || (maximum.y < minimum.y) || (maximum.z < minimum.z);
    }

    /**
     * Returns <code>true</code> if the specified bounding box intersects this one. The boxes are treated as volumes,
     * so a box inside another will return true. Returns <code>false</code> if the parameter is <code>null</code>.
     *
     * @param b box to be tested for intersection
     * @return <code>true</code> if the boxes overlap, <code>false</code> otherwise
     */
    public boolean intersects(BoundingBox b) {
        return ((b != null) && (minimum.x <= b.maximum.x) && (maximum.x >= b.minimum.x) && (minimum.y <= b.maximum.y) && (maximum.y >= b.minimum.y) && (minimum.z <= b.maximum.z) && (maximum.z >= b.minimum.z));
    }

    /**
     * Checks to see if the specified {@link org.sunflow.math.Point3 point} is inside the volume defined by this box. Returns <code>false</code> if the
     * parameter is <code>null</code>.
     *
     * @param p point to be tested for containment
     * @return <code>true</code> if the point is inside the box, <code>false</code> otherwise
     */
    public boolean contains(Point3 p) {
        return ((p != null) && (p.x >= minimum.x) && (p.x <= maximum.x) && (p.y >= minimum.y) && (p.y <= maximum.y) && (p.z >= minimum.z) && (p.z <= maximum.z));
    }

    /**
     * Changes the extents of the box as needed to include the given {@link org.sunflow.math.Point3 point} into this box.
     * Does nothing if the parameter is <code>null</code>.
     *
     * @param p point to be included
     */
    public void include(Point3 p) {
        if (p != null) {
            if (p.x < minimum.x)
                minimum.x = p.x;
            if (p.x > maximum.x)
                maximum.x = p.x;
            if (p.y < minimum.y)
                minimum.y = p.y;
            if (p.y > maximum.y)
                maximum.y = p.y;
            if (p.z < minimum.z)
                minimum.z = p.z;
            if (p.z > maximum.z)
                maximum.z = p.z;
        }
    }

    /**
     * Changes the extents of the box as needed to include the given box into this box.
     * Does nothing if the parameter is <code>null</code>.
     *
     * @param b box to be included
     */
    public void include(BoundingBox b) {
        if (b != null) {
            if (b.minimum.x < minimum.x)
                minimum.x = b.minimum.x;
            if (b.maximum.x > maximum.x)
                maximum.x = b.maximum.x;
            if (b.minimum.y < minimum.y)
                minimum.y = b.minimum.y;
            if (b.maximum.y > maximum.y)
                maximum.y = b.maximum.y;
            if (b.minimum.z < minimum.z)
                minimum.z = b.minimum.z;
            if (b.maximum.z > maximum.z)
                maximum.z = b.maximum.z;
        }
    }
}
