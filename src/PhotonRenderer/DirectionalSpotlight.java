package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;

public class DirectionalSpotlight implements LightSource {
    private Point3 src;
    private Vector3 dir;
    private OrthoNormalBasis basis;
    private double r;
    private double r2;
    private Color3 radiance;

    public DirectionalSpotlight(Point3 src, Point3 target, double r, Color3 radiance) {
        this.src = new Point3(src);
        dir = Point3.sub(target, src, new Vector3()).normalize();
        basis = OrthoNormalBasis.makeFromW(dir);
        this.r = r;
        this.r2 = r * r;
        this.radiance = new Color3(radiance);
    }

    public boolean isVisible(RenderState state) {
        if (Vector3.dot(dir, state.getVertex().n) < 0.0) {
            // project point onto source plane
            double x = state.getVertex().p.x - src.x;
            double y = state.getVertex().p.y - src.y;
            double z = state.getVertex().p.z - src.z;
            double t = ((x * dir.x) + (y * dir.y) + (z * dir.z));
            if (t >= 0.0) {
                x -= (t * dir.x);
                y -= (t * dir.y);
                z -= (t * dir.z);
                return (((x * x) + (y * y) + (z * z)) <= r2);
            }
        }
        return false;
    }

    /*public void getSample(double randX, double randY, RenderState state, LightSample dest) {
        // project point onto source plane
        double x = state.getVertex().p.x - src.x;
        double y = state.getVertex().p.y - src.y;
        double z = state.getVertex().p.z - src.z;
        double t = ((x * dir.x) + (y * dir.y) + (z * dir.z));
        x -= (t * dir.x);
        y -= (t * dir.y);
        z -= (t * dir.z);
        dir.negate(dest.getDirection());
        dest.getVertex().p.x = src.x + x;
        dest.getVertex().p.y = src.y + y;
        dest.getVertex().p.z = src.z + z;
        dest.getVertex().n.set(dir);
        dest.setShadowRay(new Ray(state.getVertex().p, dest.getVertex().p));
        dest.getRadiance().set(radiance);
        dest.setValid(true);
        // check to see if the geometric normal is pointing away from the light
        double cosNg = Vector3.dot(state.getGeoNormal(), dest.getDirection());
        if (cosNg < 0.0)
            // potential shadow problem
            // need to fix threshold on ray to avoid clipping
            dest.getShadowRay().setMin(0.3);
    }*/

    public void getPhoton(double randX1, double randY1, double randX2, double randY2, Point3 p, Vector3 dir, Color3 power) {
        double phi = 2 * Math.PI * randX1;
        double s = Math.sqrt(1.0 - randY1);
        dir.x = r * Math.cos(phi) * s;
        dir.y = r * Math.sin(phi) * s;
        dir.z = 0.0;
        basis.transform(dir);
        Point3.add(src, dir, p);
        dir.set(this.dir);
        //power.set(radiance).mul(Math.PI * r2);
        power.set(radiance);
    }

    public double getAveragePower() {
        return radiance.getAverage() * Math.PI * r2;
    }
}