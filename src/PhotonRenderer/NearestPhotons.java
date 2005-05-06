package PhotonRenderer;

import drawing_prog.*;

public class NearestPhotons {
    public int found;
    public float px;
    public float py;
    public float pz;
    private int max;
    private boolean gotHeap;
    public  float[] dist2;
    public Photon[] index;

    public NearestPhotons(Point3 p, int n, double maxDist2) {
        max = n;
        found = 0;
        gotHeap = false;
        px = (float) p.x;
        py = (float) p.y;
        pz = (float) p.z;
        dist2 = new float[n + 1];
        index = new Photon[n + 1];
        dist2[0] = (float) maxDist2;
    }

    public void reset(Point3 p, double maxDist2) {
        found = 0;
        gotHeap = false;
        px = (float) p.x;
        py = (float) p.y;
        pz = (float) p.z;
        dist2[0] = (float) maxDist2;
    }

    public void checkAddNearest(Photon p) {
        float fdist2 = p.getDist2(px, py, pz);
        if (fdist2 < dist2[0]) {
            if (found < max) {
                found++;
                dist2[found] = fdist2;
                index[found] = p;
            } else {
                int j;
                int parent;
                if (!gotHeap) {
                    float dst2;
                    Photon phot;
                    int halfFound = found >> 1;
                    for (int k = halfFound; k >= 1; k--) {
                        parent = k;
                        phot = index[k];
                        dst2 = dist2[k];
                        while (parent <= halfFound) {
                            j = parent + parent;
                            if ((j < found) && (dist2[j] < dist2[j + 1]))
                                j++;
                            if (dst2 >= dist2[j])
                                break;
                            dist2[parent] = dist2[j];
                            index[parent] = index[j];
                            parent = j;
                        }
                        dist2[parent] = dst2;
                        index[parent] = phot;
                    }
                    gotHeap = true;
                }
                parent = 1;
                j = 2;
                while (j <= found) {
                    if ((j < found) && (dist2[j] < dist2[j + 1]))
                        j++;
                    if (fdist2 > dist2[j])
                        break;
                    dist2[parent] = dist2[j];
                    index[parent] = index[j];
                    parent = j;
                    j += j;
                }
                dist2[parent] = fdist2;
                index[parent] = p;
                dist2[0] = dist2[1];
            }
        }
    }
}