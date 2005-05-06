package PhotonRenderer;

import drawing_prog.*;
//import org.sunflow.system.ProgressDisplay;

/**
 * The <code>IntersectionAccelerator</code> interface is used to implement fast ray tracing of large quantities of
 * objects.
 */
public interface IntersectionAccelerator {
    /**
     * Add a new object to the intersection accelerator.
     * @param object object to be added
     */
    void add(Intersectable object);

    /**
     * Build the data structures needed. This method will be called before rendering, once all objects have been added.
     *
     * @param output display build progress
     */
    void build();

    /**
     * Gets a bounding box enclosing all objects (excluding those with infinite extents).
     *
     * @return bounding box of all enclosed objects
     */
    BoundingBox getBounds();

    /**
     * Recursively calls {@link Intersectable#intersect(RenderState)} on all objects that are likely to interesect
     * the given ray. The accelerator may skip those objects which are trivially known to be off the path of the ray.
     *
     * @param state current state to record the intersection point
     */
    void intersect(RenderState state);

    /**
     * Recursively calls {@link Intersectable#intersects(Ray)} on all objects that are likely to intersect the given
     * ray. The accelerator may skip those objects which are trivially known to be off the path of the ray.
     * @param r ray to intersect
     * @return <code>true</code> if an intersection was found, <code>false</code> otherwise
     */
    boolean intersects(Ray r);
}