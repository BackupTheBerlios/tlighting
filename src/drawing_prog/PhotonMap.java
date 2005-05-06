/*
 * PhotonMap.java
 *
 * Created on May 5, 2005, 1:31 AM
 */

package drawing_prog;

/**
 *
 * @author root
 */
import java.awt.*;
import java.awt.geom.*;
import PhotonRenderer.*;

public class PhotonMap {
    
    private static final double[] COS_THETA = new double[256];
    private static final double[] SIN_THETA = new double[256];
    private static final double[] COS_PHI = new double[256];
    private static final double[] SIN_PHI = new double[256];
    Photon[] photons;
    int storedPhotons;
    int halfStoredPhotons;
    float[] dist1d2;
    int[] chosen;
    int gatherNum;
    double gatherRadius;
    private BoundingBox bounds;
    private boolean hasIrradiance;
    private double maxPower;
    private double maxRadius;
    
    static {
        // precompute tables to compress unit vectors
        for (int i = 0; i < 256; i++) {
            double angle = (i * Math.PI) / 256.0;
            COS_THETA[i] = Math.cos(angle);
            SIN_THETA[i] = Math.sin(angle);
            COS_PHI[i] = Math.cos(2.0 * angle);
            SIN_PHI[i] = Math.sin(2.0 * angle);
        }
    }
    
    public PhotonMap(int maxPhotons, int gatherNum, double gatherRadius) {
        photons = new Photon[maxPhotons + 1];
        storedPhotons = halfStoredPhotons = 0;
        dist1d2 = null;
        chosen = null;
        this.gatherNum = gatherNum;
        this.gatherRadius = gatherRadius;
        bounds = new BoundingBox();
        hasIrradiance = false;
        maxPower = 0;
        maxRadius = 0;
    }
    
    public final void storePhoton(Photon p) {
        if (storedPhotons >= (photons.length - 1))
            return;
        storedPhotons++;
        photons[storedPhotons] = p;
        bounds.include(new Point3(p.x, p.y, p.z));
        System.out.println("photon added at "+p.x+","+p.y+","+p.z+" with power "+p.power);
    }
    
    public final int size() {
        return storedPhotons;
    }
    
    public final int maxSize() {
        return photons.length - 1;
    }
    
    public final boolean isFull() {
        return storedPhotons == (photons.length - 1);
    }
    
    final void scalePhotonPower(double scale) {
    Color3 c = new Color3();
    for (int i = 1; i <= storedPhotons; i++)
    photons[i].power = c.setRGBE(photons[i].power).mul(scale).toRGBE();
   }
    
    /*public final void display(Camera cam, String filename) {
        Bitmap img = new Bitmap(cam.getImageWidth(), cam.getImageHeight(), true);
        for (int i = 1; i <= storedPhotons; i++) {
            Point2 p = cam.getPoint(new Point3(photons[i].x, photons[i].y, photons[i].z));
            if (p != null){
                if(photons[i].getLightSource()==0){
                    img.setPixel((int) p.x, (int) p.y, Color.RED);//new Color().setRGBE(photons[i].power));
                }else if(photons[i].getLightSource()==1){
                    img.setPixel((int) p.x, (int) p.y, Color.BLUE);//new Color().setRGBE(photons[i].power));
                }else if(photons[i].getLightSource()==2){
                    img.setPixel((int) p.x, (int) p.y, Color.GREEN);//new Color().setRGBE(photons[i].power));
                } else{
                    img.setPixel((int) p.x, (int) p.y,new Color().setRGBE(photons[i].power));
                }
            }
        }
        img.save(filename);
    }
    */ 
    final void locatePhotons(NearestPhotons np) {
        int i = 1;
        int level = 0;
        int cameFrom;
        while (true) {
            while (i < halfStoredPhotons) {
                float dist1d = photons[i].getDist1(np.px, np.py, np.pz);
                dist1d2[level] = dist1d * dist1d;
                i += i;
                if (dist1d > 0.0f)
                    i++;
                chosen[level++] = i;
            }
            np.checkAddNearest(photons[i]);
            do {
                cameFrom = i;
                i >>= 1;
                level--;
                if (i == 0)
                    return;
            } while ((dist1d2[level] >= np.dist2[0]) || (cameFrom != chosen[level]));
            np.checkAddNearest(photons[i]);
            i = chosen[level++] ^ 1;
        }
    }
    
    final void balance() {
        Photon[] temp = new Photon[storedPhotons + 1];
        balanceSegment(temp, 1, 1, storedPhotons);
        photons = temp;
        halfStoredPhotons = storedPhotons / 2;
        int log2n = (int) Math.ceil(Math.log(storedPhotons) / Math.log(2.0));
        dist1d2 = new float[log2n];        chosen = new int[log2n];
    }
    
    private void balanceSegment(Photon[] temp, int index, int start, int end) {
        int median = 1;
        while ((4 * median) <= (end - start + 1))
            median += median;
        if ((3 * median) <= (end - start + 1)) {
            median += median;
            median += (start - 1);
        } else
            median = end - median + 1;
        int axis = Photon.SPLIT_Z;
        Vector3 extents = bounds.getExtents();
        if ((extents.x > extents.y) && (extents.x > extents.z))
            axis = Photon.SPLIT_X;
        else if (extents.y > extents.z)
            axis = Photon.SPLIT_Y;
        int left = start;
        int right = end;
        while (right > left) {
            double v = photons[right].getCoord(axis);
            int i = left - 1;
            int j = right;
            while (true) {
                while (photons[++i].getCoord(axis) < v) {}
                while ((photons[--j].getCoord(axis) > v) && (j > left)) {}
                if (i >= j)
                    break;
                swap(i, j);
            }
            swap(i, right);
            if (i >= median)
                right = i - 1;
            if (i <= median)
                left = i + 1;
        }
        temp[index] = photons[median];
        temp[index].setSplitAxis(axis);
        if (median > start) {
            if (start < (median - 1)) {
                double tmp;
                switch (axis) {
                    case Photon.SPLIT_X:
                        tmp = bounds.getMaximum().x;
                        bounds.getMaximum().x = temp[index].x;
                        balanceSegment(temp, 2 * index, start, median - 1);
                        bounds.getMaximum().x = tmp;
                        break;
                    case Photon.SPLIT_Y:
                        tmp = bounds.getMaximum().y;
                        bounds.getMaximum().y = temp[index].y;
                        balanceSegment(temp, 2 * index, start, median - 1);
                        bounds.getMaximum().y = tmp;
                        break;
                    default:
                        tmp = bounds.getMaximum().z;
                        bounds.getMaximum().z = temp[index].z;
                        balanceSegment(temp, 2 * index, start, median - 1);
                        bounds.getMaximum().z = tmp;
                }
            } else
                temp[2 * index] = photons[start];
        }
        if (median < end) {
            if ((median + 1) < end) {
                double tmp;
                switch (axis) {
                    case Photon.SPLIT_X:
                        tmp = bounds.getMinimum().x;
                        bounds.getMinimum().x = temp[index].x;
                        balanceSegment(temp, (2 * index) + 1, median + 1, end);
                        bounds.getMinimum().x = tmp;
                        break;
                    case Photon.SPLIT_Y:
                        tmp = bounds.getMinimum().y;
                        bounds.getMinimum().y = temp[index].y;
                        balanceSegment(temp, (2 * index) + 1, median + 1, end);
                        bounds.getMinimum().y = tmp;
                        break;
                    default:
                        tmp = bounds.getMinimum().z;
                        bounds.getMinimum().z = temp[index].z;
                        balanceSegment(temp, (2 * index) + 1, median + 1, end);
                        bounds.getMinimum().z = tmp;
                }
            } else
                temp[(2 * index) + 1] = photons[end];
        }
    }
    
    private void swap(int i, int j) {
        Photon tmp = photons[i];
        photons[i] = photons[j];
        photons[j] = tmp;
    }
    
    static final void getUnitVector(byte theta, byte phi, Vector3 dest) {
        int t = theta & 0xFF;
        int p = phi & 0xFF;
        dest.x = SIN_THETA[t] * COS_PHI[p];
        dest.y = SIN_THETA[t] * SIN_PHI[p];
        dest.z = COS_THETA[t];
    }
    
    static final byte getVectorTheta(Vector3 v) {
        return (byte) (Math.acos(v.z) * (256.0 / Math.PI));
    }
    
    static final byte getVectorPhi(Vector3 v) {
        int phi = (int) (Math.atan2(v.y, v.x) * (128.0 / Math.PI));
        return (byte) ((phi < 0) ? (phi + 256) : phi);
    }
    
    public int getStoredPhotons(){
        return storedPhotons;
    }
    
    public Photon getPhoton(int index){
        return photons[index];
    }
    
    public void draw(Graphics2D screen,double scale, int offsetX, int offsetY){
        int i;
        
        for(i=0;i<storedPhotons;i++){
            Photon pho=photons[i];
            if(pho!=null){
                Color oc=screen.getColor();
                screen.setColor(new Color((float)pho.R,(float)pho.B,(float)pho.G));
                Ellipse2D.Double node_circ= new Ellipse2D.Double((pho.x+offsetX)*scale,(pho.y+offsetY)*scale,1*scale,1*scale);
                screen.fill(node_circ);
                screen.setColor(oc);
            }
        }
    }
    
    
     public void initialize(double scale) {
        balance();
        scalePhotonPower(scale);
        maxPower *= scale;
        maxRadius = 1.4 * Math.sqrt(maxPower * gatherNum);
        if (gatherRadius > maxRadius)
            gatherRadius = maxRadius;
    }

   /* public void precomputeIrradiance(boolean includeDirect, boolean includeCaustics) {
        if (size() == 0)
            return;

        // precompute the indirect irradiance for all photons that are neither
        // leaves nor parents of leaves in the tree.
        int quadStoredPhotons = halfStoredPhotons / 2;
        Point3 p = new Point3();
        Vector3 n = new Vector3();
        Point3 ppos = new Point3();
        Vector3 pdir = new Vector3();
        Vector3 pvec = new Vector3();
        Color3 irr = new Color3();
        Color3 pow = new Color3();
        double maxDist2 = gatherRadius * gatherRadius;
        NearestPhotons np = new NearestPhotons(p, gatherNum, maxDist2);
        Photon[] temp = new Photon[quadStoredPhotons + 1];
        for (int i = 1; i <= quadStoredPhotons; i++) {
            Photon curr = (Photon) photons[i];
            p.set(curr.x, curr.y, curr.z);
            getUnitVector(curr.normalTheta, curr.normalPhi, n);
            irr.set(Color.BLACK);
            np.reset(p, maxDist2);
            locatePhotons(np);
            double invArea = 1.0 / (Math.PI * np.dist2[0]);
            double maxNDist = np.dist2[0] * 0.05;
            for (int j = 1; j <= np.found; j++) {
                IrradiancePhoton phot = (IrradiancePhoton) np.index[j];
                if (!includeDirect && phot.isDirect())
                    continue;
                if (!includeCaustics && phot.isCaustic())
                    continue;
                getUnitVector(phot.dirTheta, phot.dirPhi, pdir);
                double cos = -Vector3.dot(pdir, n);
                if (cos > 0.01) {
                    ppos.set(phot.x, phot.y, phot.z);
                    Point3.sub(ppos, p, pvec);
                    double pcos = Vector3.dot(pvec, n);
                    if ((pcos < maxNDist) && (pcos > -maxNDist))
                        irr.add(pow.setRGBE(phot.power));
                }
            }
            irr.mul(invArea);
            curr.irradiance = irr.toRGBE();
            temp[i] = curr;
        }

        // resize photon map to only include irradiance photons
        gatherNum /= 4;
        maxRadius = 1.4 * Math.sqrt(maxPower * gatherNum);
        if (gatherRadius > maxRadius)
            gatherRadius = maxRadius;
        storedPhotons = quadStoredPhotons;
        halfStoredPhotons = storedPhotons / 2;
        int log2n = (int) Math.ceil(Math.log(storedPhotons) / Math.log(2.0));
        dist1d2 = new float[log2n];
        chosen = new int[log2n];
        photons = temp;
        hasIrradiance = true;
    }*/
    
}
