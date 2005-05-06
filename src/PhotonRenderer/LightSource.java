package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;

/**
 * The <code>LightSource</code> interface is used to represent any light emitting primitive. It permits efficient
 * sampling of direct illumination.
 */
public interface LightSource {
    /**
     * Checks to see if the light is trivally visible from the current render state.
     *
     * @param state currente render state
     * @return <code>true</code>if the light source is visible, <code>false</code> otherwise
     */
    boolean isVisible(RenderState state);

    /**
     * Creates a light sample on the light source that points towards the vertex in the current state. The sampling
     * parameter <code>rand</code> is a 2D point on the unit square that can be used for sampling area lights. This
     * method will determine if it is necessary to trace shadows for this sample. This method also calls the light
     * shader to determine the emitted radiance.
     *
     * @param randX sampling parameter for area lights
     * @param randY sampling parameter for area lights
     * @param state current state, including point to be
     * @param dest light sample to be filled in
     * @see LightSample
     */
    //void getSample(double randX, double randY, RenderState state, LightSample dest);

    /**
     * Gets a photon to emit from this light source by setting each of the arguments. The two sampling parameters are
     * points on the unit square that can be used to sample a position and/or direction for the emitted photon.
     *
     * @param randX1 sampling parameter
     * @param randY1 sampling parameter
     * @param randX2 sampling parameter
     * @param randY2 sampling parameter
     * @param p position to shoot the photon from
     * @param dir direction to shoot the photon in
     * @param power power of the photon
     */
    void getPhoton(double randX1, double randY1, double randX2, double randY2, Point3 p, Vector3 dir, Color3 power);

    /**
     * Gets the average power emitted by the light source (over all color channels).
     *
     * @return average power of the light source
     */
    double getAveragePower();
}