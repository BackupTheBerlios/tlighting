package PhotonRenderer;

import drawing_prog.*;
/**
 * The <code>Intersectable</code> interface is implemented by all geometric primitives that are to be rendered.
 */
public interface Intersectable {
    /**
     * Gets a bounding box that encloses the surface as best as possible. If the object has infinite extents, such as a
     * plane, this method should return <code>null</code>.
     *
     * @return a bounding box for the surface
     */
    BoundingBox getBounds();

    /**
     * Checks to see if the box intersects the surface. The test must treat the box as a solid. May return a
     * conservative result by using {@link BoundingBox#intersects(BoundingBox)} on the {@link #getBounds() bounding
     * box} of this object.
     *
     * @param box box to be intersected with the surface
     * @return <code>true</code> if the surface intersects the box, <code>false</code> otherwise
     */
    boolean intersects(BoundingBox box);

    /**
     * Gets the shader associated with the surface.
     *
     * @return shader attached to the surface
     */
    Shader getSurfaceShader();

    /**
     * Sets the vertex field and geometric normal field of the specified {@link RenderState render state}. This method
     * is called only if the current ray intersects this object. Only the intersection parameters (t, u, v) (set by
     * {@link #intersect(RenderState)}) should be used to compute the intersection vertex and geometric normal.
     *
     * @param state current state
     * @see RenderState
     */
    void setSurfaceLocation(RenderState state);

    /**
     * Compute the intersection (if any) with the ray and record the intersection in the {@link RenderState render
     * state}. The recorded (t, u, v) parameters are then used by {@link #setSurfaceLocation(RenderState)} to compute
     * the exact intersection point. Classes implementing this method should also ensure that the t value falls within
     * the valid range of the ray, and update the max t value to reflect the intersection when it occurs.
     *
     * @param state current state, used to store the intersection coordinates
     * @see Ray#isInside
     * @see RenderState#setIntersection(Intersectable, double, double, double)
     */
    void intersect(RenderState state);

    /**
     * Check to see if the ray intersects the surface. The t value of the intersection must fall within the valid range
     * on the ray.
     *
     * @param r ray to be intersected
     * @return <code>true</code> if the surface intersects the ray, <code>false</code> otherwise
     * @see Ray#isInside
     */
    boolean intersects(Ray r);
}