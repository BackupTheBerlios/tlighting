/*
 * Photon.java
 *
 * Created on May 5, 2005, 1:31 AM
 */

package drawing_prog;
import java.awt.*;
/**
 *
 * @author root
 */
public class Photon {
        public float x;
    public float y;
    public float z;
    public byte dirPhi;
    public byte dirTheta;
    public int power;
    public float R;
    public float G;
    public float B;
    public int flags;
    public int lightSource;
    static final int SPLIT_X = 0;
    static final int SPLIT_Y = 1;
    static final int SPLIT_Z = 2;
    static final int SPLIT_MASK = 3;
    public double nx;
    public double ny;
    public double nz;
    public double dx;
    public double dy;
    public double dz;
    public int depth;
    
    
    public Photon() {
        x = 0;
        y = 0;
        z = 0;
        dirPhi = 0;
        dirTheta = 0;
        this.power = 0;
        flags = SPLIT_X;
    }
    
    public Photon(Point3 p, Vector3 dir, Color power) {
        x = (float) p.x;
        y = (float) p.y;
        z = (float) p.z;
        dirPhi = getVectorPhi(dir);
        dirTheta = getVectorTheta(dir);
        this.power = power.getRGB();
        R=((float)power.getRed()/255);
        G=((float)power.getGreen()/255);
        B=((float)power.getBlue()/255);
        
        flags = SPLIT_X;
    }
    
    public void setN(double ax,double ay, double az){
        nx=ax;
        ny=ay;
        nz=az;
    }
    public void setD(double ax, double ay, double az){
        dx=ax;
        dy=ay;
        dz=az;
    }
    
    
    
    public void setSplitAxis(int axis) {
        flags &= ~SPLIT_MASK;
        flags |= axis;
    }
    
    public float getCoord(int axis) {
        switch (axis) {
            case SPLIT_X:
                return x;
            case SPLIT_Y:
                return y;
            default:
                return z;
        }
    }
    
    public float getDist1(float px, float py, float pz) {
        switch (flags & SPLIT_MASK) {
            case SPLIT_X:
                return px - x;
            case SPLIT_Y:
                return py - y;
            default:
                return pz - z;
        }
    }
    
    public float getDist2(float px, float py, float pz) {
        float dx = x - px;
        float dy = y - py;
        float dz = z - pz;
        return (dx * dx) + (dy * dy) + (dz * dz);
    }
    public static final byte getVectorTheta(Vector3 v) {
        return (byte) (Math.acos(v.z) * (256.0 / Math.PI));
    }
    
    public static final byte getVectorPhi(Vector3 v) {
        int phi = (int) (Math.atan2(v.y, v.x) * (128.0 / Math.PI));
        return (byte) ((phi < 0) ? (phi + 256) : phi);
    }
    
    public void setLightSource(int i){
        lightSource=i;
    }
    
    public int getLightSource(){
        return lightSource;
    }
    
}
