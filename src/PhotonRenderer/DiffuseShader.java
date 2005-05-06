package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;

        
public class DiffuseShader extends Shader { 
    private Color3 kd;
    //private Texture tex;

    public DiffuseShader(Color3 d) {
        kd = Color3.mul(1.0 / Math.PI, d);
        //tex = null;
    }

    public DiffuseShader(String filename) {
        kd = new Color3();
        //tex = TextureCache.getTexture(filename);
    }

    public Color3 getRadiance(RenderState state) {
        // make sure we are on the right side of the material
        if (Vector3.dot(state.getVertex().n, state.getRay().getDirection()) > 0.0) {
            state.getVertex().n.negate();
            state.getGeoNormal().negate();
        }

        // texturing
        //if (tex != null)
         //   Color.mul(1.0 / Math.PI, tex.getPixel(state.getVertex().tex.x, state.getVertex().tex.y), kd);

        // direct lighting
        initLightSamples(state, true, true);
        Color3 lr = new Color3(Color3.BLACK);
        /*LightSample sample = new LightSample();
        while (getNextLightSample(state, sample))
            if (!isShadowed(sample))
                lr.madd(Vector3.dot(sample.getDirection(), state.getVertex().n), sample.getRadiance());
        lr.add(getIrradiance(state));*/
        return lr.mul(kd);
    }

    public void scatterPhoton(RenderState state, Color3 power) {
        Color3 diffuse;

        // make sure we are on the right side of the material
        if (Vector3.dot(state.getVertex().n, state.getRay().getDirection()) > 0.0) {
            state.getVertex().n.negate();
            state.getGeoNormal().negate();
        }

        // texturing
        //if (tex != null)
           // diffuse = tex.getPixel(state.getVertex().tex.x, state.getVertex().tex.y);
       // else
            diffuse = Color3.mul(Math.PI, kd);
        storePhoton(state, state.getRay().getDirection(), power,state.getCurrentLight());
        double avg = diffuse.getAverage();
        double rnd = Math.random();
        if (rnd < avg) {
            // photon is scattered
            power.mul(diffuse).mul(1.0 / avg);
            OrthoNormalBasis onb = OrthoNormalBasis.makeFromW(state.getVertex().n);
            double u = 2 * Math.PI * Math.random();
            double v = Math.random();
            double s = Math.sqrt(v);
            double s1 = Math.sqrt(1.0 - v);
            Vector3 w = new Vector3(Math.cos(u) * s, Math.sin(u) * s, s1);

            w = onb.transform(w, new Vector3());
            traceDiffusePhoton(state, new Ray(state.getVertex().p, w), power);
        }
    }
}