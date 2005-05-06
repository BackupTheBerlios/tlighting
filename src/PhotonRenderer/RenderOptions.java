package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;

/**
 * Holds options that determine how the image is rendered.
 */
public class RenderOptions {
    // direct illumination
    private boolean traceShadows;
    private int numLightSamples;
    private int maxDepth;

    // indirect illumination
    private boolean computeGI;
    private boolean computeCaustics;
    private int numPhotons;
    private int numGather;
    private double photonReductionRatio;

    // irradiance caching
    private boolean irradianceCaching;
    private double irradianceCacheTolerance;
    private double irradianceCacheSpacing;
    private int irradianceSamples;
    private boolean displayIrradianceSamples;

    // anti-aliasing
    private int minAASamples;
    private int maxAASamples;
    private boolean displayAASamples;
    private double threshold;

    // image
    private String outputFilename;

    /**
     * Create a set of default options.
     */
    public RenderOptions() {
        traceShadows = true;
        numLightSamples = 8;
        maxDepth = 9;

        computeGI = false;
        computeCaustics = false;
        numPhotons = 200000;
        numGather = 100;
        photonReductionRatio = 0.9;

        irradianceCaching = true;
        irradianceCacheTolerance = 0.05;
        irradianceCacheSpacing = 2.0;
        displayIrradianceSamples = false;

        minAASamples = 4;
        maxAASamples = 16;
        threshold = 0.001;
        displayAASamples = false;
        outputFilename = "output.hdr";
    }

    void validate() {
        numLightSamples = Math.max(0, numLightSamples);
        maxDepth = Math.max(0, maxDepth);

        numPhotons = Math.max(0, numPhotons);
        numGather = Math.max(10, numGather);
        photonReductionRatio = MathUtils.clamp(photonReductionRatio, 0.0, 0.995);
        irradianceCacheTolerance = Math.max(0.001, irradianceCacheTolerance);
        irradianceCacheSpacing = Math.max(0.001, irradianceCacheSpacing);

        if (!irradianceCaching)
            computeCaustics = false;

        if (minAASamples < 4)
            minAASamples = 4;
        if (maxAASamples <= 1)
            minAASamples = maxAASamples = 1;
        if (minAASamples > maxAASamples)
            minAASamples = maxAASamples;
        if (minAASamples == maxAASamples)
            displayAASamples = false;
        threshold = Math.max(0, threshold);
    }

    /**
     * Checks to see if shadows should be traced.
     *
     * @return <code>true</code>if shadows are to be traced, <code>false</code> otherwise
     */
    public boolean traceShadows() {
        return traceShadows;
    }

    /**
     * Set this option to turn on or off the rendering of shadows.
     *
     * @param traceShadows <code>true</code>if shadows are to be traced, <code>false</code> otherwise
     */
    public void setTraceShadows(boolean traceShadows) {
        this.traceShadows = traceShadows;
    }

    /**
     * Gets the number of light samples to be used in computing direct illumination.
     *
     * @return number of light samples for direct illumination
     */
    public int getNumLightSamples() {
        return numLightSamples;
    }

    /**
     * Sets the number of light samples to be used in computing direct illumination.
     *
     * @param numLightSamples number of light samples for direct illumination
     */
    public void setNumLightSamples(int numLightSamples) {
        this.numLightSamples = numLightSamples;
    }

    /**
     * Gets the maximum ray tracing depth.
     *
     * @return maximum ray tracing depth
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * Sets the maximum ray tracing depth.
     *
     * @param maxDepth maximum ray tracing depth
     */
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     * Checks to see if global illumination (diffuse inter-reflections) should be computed.
     *
     * @return <code>true</code> if diffuse inter-reflections are to be computed, <code>false</code> otherwise
     */
    public boolean computeGI() {
        return computeGI;
    }

    /**
     * Sets the option to compute diffuse inter-reflections. This turns on the creation of a global photon map and the
     * use of irradiance caching.
     *
     * @param computeGI <code>true</code> if diffuse inter-reflections are to be computed, <code>false</code> otherwise
     */
    public void setComputeGI(boolean computeGI) {
        this.computeGI = computeGI;
    }

    /**
     * Checks to see if caustics should be computed.
     *
     * @return <code>true</code> if caustics are to be computed, <code>false</code> otherwise
     */
    public boolean computeCaustics() {
        return computeCaustics;
    }

    /**
     * Sets the option to compute caustics. This turns on the creation of a caustic photon map.
     *
     * @param computeCaustics <code>true</code> if caustics are to be computed, <code>false</code> otherwise
     */
    public void setComputeCaustics(boolean computeCaustics) {
        this.computeCaustics = computeCaustics;
    }

    /**
     * Gets the maximum number of photons to be used in the global photon map and caustic photon map.
     *
     * @return maximum number of photons to be used in the global photon map and caustic photon map
     */
    public int getNumPhotons() {
        return numPhotons;
    }

    /**
     * Sets the maximum number of photons to be used in the global photon map and caustic photon map.
     * @param numPhotons maximum number of photons to be used in the global photon map and caustic photon map
     */
    public void setNumPhotons(int numPhotons) {
        this.numPhotons = numPhotons;
    }

    /**
     * Gets the number of photons to gather for caustics and for precomputing irradiance in the global photon map.
     *
     * @return number of photons to gather
     */
    public int getNumGather() {
        return numGather;
    }

    /**
     * Sets the number of photons to gather for caustics and for precomputing irradiance in the global photon map.
     *
     * @param numGather number of photons to gather
     */
    public void setNumGather(int numGather) {
        this.numGather = numGather;
    }

    /**
     * Gets the photon reduction ratio. This is a number between 0 and 1 that specifies the percentage of global photon
     * hits to throw away. This is used only when both {@link #computeGI()} and {@link #computeCaustics()} are true.
     *
     * @return photon reduction ratio
     */
    public double getPhotonReductionRatio() {
        return photonReductionRatio;
    }

    /**
     * Sets the photon reductio ratio.
     *
     * @param photonReductionRatio photon reduction ratio
     * @see #getPhotonReductionRatio
     */
    public void setPhotonReductionRatio(double photonReductionRatio) {
        this.photonReductionRatio = photonReductionRatio;
    }

    /**
     * Checks if irradiance caching should be used. If not, the global photon map
     * is looked up instead, and caustics are disabled.
     *
     * @return <code>true</code> if irradiance caching is to be used, <code>false</code> otherwise
     */
    public boolean irradianceCaching() {
        return irradianceCaching;
    }

    /**
     * Sets if irradiance caching should be used or not.
     *
     * @param irradianceCaching <code>true</code> if irradiance caching is to be used, <code>false</code> otherwise
     * @see #irradianceCaching()
     */
    public void setIrradianceCaching(boolean irradianceCaching) {
        this.irradianceCaching = irradianceCaching;
    }

    /**
     * Gets the irradiance cache tolerance.
     *
     * @return tolerance value for the irradiance cache
     */
    public double getIrradianceCacheTolerance() {
        return irradianceCacheTolerance;
    }

    /**
     * Sets the irradiance cache tolerance.
     *
     * @param irradianceCacheTolerance tolerance value for the irradiance cache
     */
    public void setIrradianceCacheTolerance(double irradianceCacheTolerance) {
        this.irradianceCacheTolerance = irradianceCacheTolerance;
    }

    /**
     * Gets the minimum spacing for the irradiance cache. This is usualy to be set to the approximate size of the
     * smallest feature in the scene.
     *
     * @return minimum spacing for the irradiance cache
     */
    public double getIrradianceCacheSpacing() {
        return irradianceCacheSpacing;
    }

    /**
     * Sets the minimum spacing for the irradiance cache.
     *
     * @param irradianceCacheSpacing minimum spacing for the irradiance cache
     * @see #getIrradianceCacheSpacing
     */
    public void setIrradianceCacheSpacing(double irradianceCacheSpacing) {
        this.irradianceCacheSpacing = irradianceCacheSpacing;
    }

    /**
     * Gets the number of samples to take when computing irradiance for the irradiance cache. The actual number of
     * samples might vary slighty because the renderer finds two integers <code>M</code> and <code>N</code> such that
     * <code>MxN = numSamples</code> and <code>N=MxPi</code>.
     *
     * @return number of samples for the irradiance cache
     */
    public int getIrradianceSamples() {
        return irradianceSamples;
    }

    /**
     * Sets the number of samples to take when computing irradiance for the irradiance cache.
     *
     * @param irradianceSamples number of samples for the irradiance cache
     * @see #getIrradianceSamples
     */
    public void setIrradianceSamples(int irradianceSamples) {
        this.irradianceSamples = irradianceSamples;
    }

    /**
     * Checks if new irradiance cache samples are to be overlaid onto the image.
     *
     * @return <code>true</code> if new irradiance cache samples should be displayed, <code>false</code> otherwise
     */
    public boolean displayIrradianceSamples() {
        return displayIrradianceSamples;
    }

    /**
     * Sets the option to display new irradiance cache samples on the image.
     *
     * @param displayIrradianceSamples <code>true</code> if new irradiance cache samples should be displayed, <code>false</code> otherwise
     */
    public void setDisplayIrradianceSamples(boolean displayIrradianceSamples) {
        this.displayIrradianceSamples = displayIrradianceSamples;
    }

    /**
     * Gets the minimum number of image samples for anti-aliasing.
     *
     * @return minimum number of image samples
     */
    public int getMinAASamples() {
        return minAASamples;
    }

    /**
     * Sets the minimum number of image samples for anti-aliasing.
     *
     * @param minAASamples minimum number of image samples
     */
    public void setMinAASamples(int minAASamples) {
        this.minAASamples = minAASamples;
    }

    /**
     * Gets the maximum number of image samples for anti-aliasing. If this is 1, then no anti-aliasing is performed.
     *
     * @return maximum number of image samples
     */
    public int getMaxAASamples() {
        return maxAASamples;
    }

    /**
     * Sets the maximum number of image samples for anti-aliasing. If this is 1, then no anti-aliasing is performed.
     *
     * @param maxAASamples maximum number of image samples
     */
    public void setMaxAASamples(int maxAASamples) {
        this.maxAASamples = maxAASamples;
    }

    /**
     * Gets the anti-aliasing threshold used to determine if more samples should be taken for a given pixel. If the
     * variance of a pixel exceeds the threshold, more samples are taken, up to a maximum of {@link #getMaxAASamples}.
     *
     * @return anti-aliasing threshold
     */
    public double getAAThreshold() {
        return threshold;
    }

    /**
     * Sets the anti-aliasing threshold.
     *
     * @param threshold anti-aliasing threshold
     * @see #getAAThreshold
     */
    public void setAAThreshold(double threshold) {
        this.threshold = threshold;
    }

    /**
     * Checks to see if an additional image should be generated indicating how many image samples were taken at each
     * pixel.
     *
     * @return <code>true</code> anti-aliasing sample image will be generated, <code>false</code> otherwise
     */
    public boolean displayAASamples() {
        return displayAASamples;
    }

    /**
     * Sets if an additional image should be generated indicating how many image samples were taken at each
     * pixel.
     *
     * @param displayAASamples <code>true</code> anti-aliasing sample image will be generated, <code>false</code> otherwise
     */
    public void setDisplayAASamples(boolean displayAASamples) {
        this.displayAASamples = displayAASamples;
    }

    /**
     * Gets the filename to save the final rendered image.
     *
     * @return rendered output filename
     */
    public String getOutputFilename() {
        return outputFilename;
    }

    /**
     * Sets the filename to save the final rendered image.
     *
     * @param outputFilename rendered output filename
     */
    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }
}