package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;
/**
 * This class is used to store all the necessary parameters for a single step in rendering. This involves tracing a ray,
 * finding an intersection and shading the result if a hit is found.
 */
public final class RenderState {
    private Vertex v;
    private Vector3 ng;
    private Ray r;
    private double hitT;
    private double hitU;
    private double hitV;
    private Intersectable object;
    private int diffuseDepth;
    private int specularDepth;
    private boolean includeLights;
    private boolean includeSpecular;
    //private LightSample[] samples;
    private int curSamples;
    private int numSamples;

    private int currentLight;
    
    static RenderState createPhotonState(Ray r) {
        return new RenderState(r);
    }

    static RenderState createState(Ray r) {
        return new RenderState(r);
    }

    static RenderState createDiffuseBounceState(RenderState previous, Ray r) {
        return new RenderState(previous, r, true, false);
    }

    static RenderState createSpecularBounceState(RenderState previous, Ray r) {
        return new RenderState(previous, r, false, true);
    }

    static RenderState createFinalGatherState(RenderState state, Ray r) {
        RenderState finalGatherState = new RenderState(state, r, true, false);
        finalGatherState.includeLights = false;
        finalGatherState.includeSpecular = false;
        return finalGatherState;
    }

    private RenderState(Ray r) {
        this(null, r, false, false);
    }

    private RenderState(RenderState previous, Ray r, boolean diffuse, boolean specular) {
        v = new Vertex();
        ng = new Vector3();
        this.r = r;
        hitT = Double.POSITIVE_INFINITY;
        hitU = hitV = 0.0;
        object = null;
        diffuseDepth = (previous == null) ? 0 : previous.diffuseDepth;
        if (diffuse)
            diffuseDepth++;
        specularDepth = (previous == null) ? 0 : previous.specularDepth;
        if (specular)
            specularDepth++;
        includeLights = includeSpecular = true;
        //samples = null;
        curSamples = numSamples = 0;
        currentLight=-1;
    }

    void initSamples(int maxSamples) {
    //    samples = new LightSample[maxSamples];
    }

    //public void addSample(LightSample sample) {
    //    samples[numSamples++] = sample;
    //}

    //LightSample getNextSample() {
    //    return (curSamples >= numSamples) ? null : samples[curSamples++];
    //}

    /**
     * Checks to see if the shader should include emitted light.
     *
     * @return <code>true</code> if emitted light should be included, <code>false</code> otherwise
     */
    public boolean includeLights() {
        return includeLights;
    }

    /**
     * Checks to see if the shader should include specular terms.
     *
     * @return <code>true</code> if specular terms should be included, <code>false</code> otherwise
     */
    public boolean includeSpecular() {
        return includeSpecular;
    }

    /**
     * Sets if the shader should include emitted light.
     * @param includeLights <code>true</code> if emitted light should be included, <code>false</code> otherwise
     * @return
     */
    public void setIncludeLights(boolean includeLights) {
        this.includeLights = includeLights;
    }

    public void setIncludeSpecular(boolean includeSpecular) {
        this.includeSpecular = includeSpecular;
    }

    /**
     * Checks to see if a hit has been recorded.
     *
     * @return <code>true</code> if a hit has been recorded, <code>false</code> otherwise
     * @see #setIntersection(Intersectable, double, double, double)
     */
    public boolean hit() {
        return object != null;
    }

    /**
     * Record an intersection with the specified object at distance t along the current ray. The u and v parameters are
     * used to pinpoint the location on the surface if needed.
     *
     * @param object reference to the object beeing intersected
     * @param hitT distance along the ray
     * @param hitU u surface parameter of the intersection point
     * @param hitV v surface parameter of the intersection point
     * @see Intersectable#intersect(RenderState)
     */
    public void setIntersection(Intersectable object, double hitT, double hitU, double hitV) {
        this.object = object;
        this.hitT = hitT;
        this.hitU = hitU;
        this.hitV = hitV;
    }

    /**
     * Get the current total tracing depth. First generation rays have a depth of 0.
     *
     * @return current tracing depth
     */
    public int getDepth() {
        return diffuseDepth + specularDepth;
    }

    /**
     * Get the current diffuse tracing depth. This is the number of diffuse surfaces reflected from.
     *
     * @return current diffuse tracing depth
     */
    public int getDiffuseDepth() {
        return diffuseDepth;
    }

    /**
     * Get the current specular tracing depth. This is the number of specular surfaces reflected from.
     *
     * @return current specular tracing depth
     */
    public int getSpecularDepth() {
        return specularDepth;
    }

    /**
     * Gets the value of the saved u surface parameter. Only valid when {@link #hit()} is <code>true</code>
     *
     * @return value of the u surface parameter
     */
    public double getU() {
        return hitU;
    }

    /**
     * Gets the value of the saved v surface parameter. Only valid when {@link #hit()} is <code>true</code>
     *
     * @return value of the v surface parameter
     */
    public double getV() {
        return hitV;
    }

    /**
     * Gets the value of the saved intersection distance. Only valid when {@link #hit()} is <code>true</code>
     *
     * @return value of the intersection distance
     */
    public double getT() {
        return hitT;
    }

    /**
     * Gets the vertex representing the current hit point.
     *
     * @return vertex of the current hit point
     */
    public Vertex getVertex() {
        return v;
    }

    /**
     * Gets the geometric normal of the current hit point.
     *
     * @return geometric normal of the current hit point
     */
    public Vector3 getGeoNormal() {
        return ng;
    }

    /**
     * Gets a reference to the stored intersected object. This will be <code>null</code> if {@link #hit()} is
     * <code>false</code>.
     * @return reference to the intersected object
     */
    public Intersectable getObject() {
        return object;
    }

    /**
     * Gets the ray that is associated with this state.
     *
     * @return ray associated with this state.
     */
    public Ray getRay() {
        return r;
    }
    
    public void setCurrentLight(int i){
        currentLight=i;
    }
    public int getCurrentLight(){
        return currentLight;
    }
}