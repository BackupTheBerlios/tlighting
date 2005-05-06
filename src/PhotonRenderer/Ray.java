package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;

/**
 * This class represents a ray as a oriented half line segment. The ray direction is always normalized. The valid region
 * is delimted by two distances along the ray, tMin and tMax.
 */
public class Ray {
    private Point3 origin;
    private Vector3 direction;
    private double tMin;
    private double tMax;
    private static final double EPSILON = 0.0001;

    /**
     * Creates a new ray that points from the given origin to the given direction. The ray has infinite length. Note
     * that the parameters are copied, so the ray has a new instance of both. The direction vector is normalized.
     *
     * @param o ray origin
     * @param d ray direction (need not be normalized)
     */
    public Ray(Point3 o, Vector3 d) {
        origin = new Point3(o);
        tMin = EPSILON;
        tMax = Double.POSITIVE_INFINITY;
        direction = new Vector3(d).normalize();
    }

    /**
     * Creates a new ray that points from point a to point b. The created ray will set tMin and tMax to limit the ray
     * to the segment (a,b) (non-inclusive of a and b). This is often used to create shadow rays.
     *
     * @param a start point
     * @param b end point
     */
    public Ray(Point3 a, Point3 b) {
        origin = new Point3(a);
        tMin = EPSILON;
        direction = Point3.sub(b, a, new Vector3());
        tMax = direction.length() - EPSILON;
        direction.normalize();
    }

    /**
     * Gets the minimum distance along the ray. Usually a small epsilon above 0.
     *
     * @return value of the smallest distance along the ray
     */
    public double getMin() {
        return tMin;
    }

    /**
     * Gets the maximum distance along the ray. May be infinite.
     *
     * @return value of the largest distance along the ray
     */
    public double getMax() {
        return tMax;
    }

    /**
     * Gets the origin of the ray. The returned reference is not cloned from the internal copy for efficiency. Care must
     * be taken not to change the point.
     *
     * @return ray origin
     */
    public Point3 getOrigin() {
        return origin;
    }

    /**
     * Gets the direction of the ray. The returned reference is not cloned from the internal copy for efficiency. Care
     * must be taken not to change the vector. This direction can be assumed to be normalized.
     *
     * @return ray direction (normalized)
     */
    public Vector3 getDirection() {
        return direction;
    }

    /**
     * Checks to see if the ray interval is empty. This happens if the minimum distance exceeds the maximum distance.
     *
     * @return <code>true</code> if the ray is empty, <code>false</code> otherwise
     */
    public boolean isEmpty() {
        return tMax <= tMin;
    }

    /**
     * Checks to see if the specified distance falls within the valid range on this ray. This should always be used
     * before an intersection with the ray is detected.
     *
     * @param t distance to be tested
     * @return <code>true</code> if t falls between the minimum and maximum distance of this ray, <code>false</code>
     * otherwise
     * @see Intersectable
     */
    public boolean isInside(double t) {
        return (tMin <= t) && (t <= tMax);
    }

    /**
     * Gets the point at the specified distance along the ray. No check is performed to see if the  distance is within
     * the valid range. The destination point is assumed to not be <code>null</code>. A reference to <code>dest</code>
     * is returned to support chaining.
     *
     * @param t distance along the ray
     * @param dest reference to the point to store
     * @return reference to <code>dest</code>
     */
    public Point3 getPoint(double t, Point3 dest) {
        dest.x = origin.x + (t * direction.x);
        dest.y = origin.y + (t * direction.y);
        dest.z = origin.z + (t * direction.z);
        return dest;
    }

    /**
     * Sets the absolute minimum and maximum without any error checking.
     *
     * @param min minimum distance along the ray.
     * @param max maximum distance along the ray.
     */
    public void setMinMax(double min, double max) {
        tMin = min;
        tMax = max;
    }

    /**
     * Updates the minimum to the specified distance if and only if the new distance is larger than the current one.
     * @param t new minimum distance
     */
    public void setMin(double t) {
        if (t > tMin)
            tMin = t;
    }

    /**
     * Updates the maximum to the specified distance if and only if the new distance is smaller than the current one.
     * @param t new maximum distance
     */
    public void setMax(double t) {
        if (t < tMax)
            tMax = t;
    }
}