package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;

/**
 * A shader represents a particular light-surface interaction. Shaders may be used as sources of light, reflectors of
 * light, and even as surface modifiers for effects such as bump-mapping.
 */
public abstract class Shader {
    private LightServer lightServer;

    final void setLightServer(LightServer lightServer) {
        this.lightServer = lightServer;
    }

    /**
     * Initialize the use of light samples. Prepares a list of visible lights from the specified point and normal.
     *
     * @param state current state
     * @param getCaustics <code>true</code> to add light samples representing caustics, <code>false</code> if the
     * surface should not receive caustics.
     * @param getIndirectDiffuse <code>true</code> if this shader will include a diffuse indirect illumination term, <code>false</code> otherwise
     */
    protected final void initLightSamples(RenderState state, boolean getCaustics, boolean getIndirectDiffuse) {
        lightServer.initLightSamples(state, getCaustics, getIndirectDiffuse);
    }

    /**
     * Gets the next visible light sample. If no more samples are available, this will return <code>false</code>.
     *
     * @param state current render state
     * @param sample light sample to fill in
     * @return <code>true</code> if more samples are to be taken, <code>false</code> otherwise
     */
    /*protected final boolean getNextLightSample(RenderState state, LightSample sample) {
        LightSample ls = state.getNextSample();
        if (ls == null)
            return false;
        sample.set(ls);
        return true;
    }*/

    /**
     * Returns the color obtained by recursively tracing the specified ray. The reflection is assumed to be specular.
     *
     * @param state current render state
     * @param r ray to trace
     * @return color observed along specified ray.
     */
    protected final Color3 traceSpecular(RenderState state, Ray r) {
        return lightServer.traceSpecular(state, r);
    }

    /**
     * Check to see if the light comming from the specified sample is in shadow.
     *
     * @param sample light sample to check
     * @return <code>true</code> if the sample is in shadow, <code>false</code> otherwise
     */
    /*protected final boolean isShadowed(LightSample sample) {
        return lightServer.isShadowed(sample);
    }*/

    /**
     * Records a photon at the specified location.
     *
     * @param state current state
     * @param dir incoming direction of the photon
     * @param power photon power
     */
    protected final void storePhoton(RenderState state, Vector3 dir, Color3 power,int curLight) {
        
        
        
        lightServer.storePhoton(state.getVertex().p,state.getVertex().n, dir, power,true,curLight);
    }

    /**
     * Trace a new photon from the current location. This assumes that the photon was reflected by a specular surface.
     *
     * @param state current state
     * @param r ray to trace photon along
     * @param power power of the new photon
     */
    protected final void traceSpecularPhoton(RenderState state, Ray r, Color3 power) {
        lightServer.traceSpecularPhoton(state, r, power); 
    }

    /**
     * Trace a new photon from the current location. This assumes that the photon was reflected by a diffuse surface.
     *
     * @param state current state
     * @param r ray to trace photon along
     * @param power power of the new photon
     */
    protected final void traceDiffusePhoton(RenderState state, Ray r, Color3 power) {
        lightServer.traceDiffusePhoton(state, r, power);
    }

    /**
     * Gets the total irradiance reaching the current point from diffuse surfaces. For final gather rays, this returns
     * the total irradiance at the intersection point.
     *
     * @param state current state
     * @return indirect diffuse irradiance reaching the point
     */
    protected final Color3 getIrradiance(RenderState state) {
        return Color3.BLACK;//lightServer.getIrradiance(state);
    }

    /**
     * Gets the radiance for a specified rendering state. When this method is called, you can assume that a hit has been
     * registered in the state and that the hit vertex has been computed.
     * @param state current render state
     * @return color emitted or reflected by the shader
     * @see Intersectable#setSurfaceLocation(RenderState)
     */
    public abstract Color3 getRadiance(RenderState state);

    /**
     * Scatter a photon with the specied power. Incoming photon direction is specified by the ray attached
     * to the current render state.
     * @param state current state
     * @param power power of the incoming photon.
     */
    public abstract void scatterPhoton(RenderState state, Color3 power);
}