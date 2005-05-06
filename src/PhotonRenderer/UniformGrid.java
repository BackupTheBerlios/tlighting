package PhotonRenderer;

import Data_Storage.*;
import drawing_prog.*;
import java.util.ArrayList;

public class UniformGrid implements IntersectionAccelerator {
    private int nx;
    private int ny;
    private int nz;
    private BoundingBox bounds;
    private ArrayList objectList;
    private GridCell[] cells;
    private GridCell infiniteCell;

    public UniformGrid() {
        nx = ny = nz = 0;
        bounds = new BoundingBox();
        objectList = new ArrayList();
        cells = null;
        infiniteCell = null;
    }

    public void add(Intersectable object) {
        bounds.include(object.getBounds());
        objectList.add(object);
    }

    public void build() {
        if (cells != null)
            return;
        long startTime = System.currentTimeMillis();

        // create grid from number of objects
        bounds.scale(1.0001);
        Vector3 w = bounds.getExtents();
        int numPrimitives = objectList.size();
        double s = Math.pow((w.x * w.y * w.z) / numPrimitives, 1.0 / 3.0);
        nx = MathUtils.clamp((int) ((w.x / s) + 0.5), 1, 100);
        ny = MathUtils.clamp((int) ((w.y / s) + 0.5), 1, 100);
        nz = MathUtils.clamp((int) ((w.z / s) + 0.5), 1, 100);
        //output.println("[ACC] Creating grid: " + nx + "x" + ny + "x" + nz + "...");
        infiniteCell = new GridCell();
        cells = new GridCell[nx * ny * nz];
        for (int i = 0; i < cells.length; i++)
            cells[i] = new GridCell();

        // add all objects into the grid cells they overlap
        int[] i0 = new int[3];
        int[] i1 = new int[3];
        //output.setTask("Creating Uniform Grid", 0, numPrimitives - 1);
        for (int i = 0; i < numPrimitives; i++) {
            //output.update(i);
            Intersectable object = (Intersectable) objectList.get(i);
            BoundingBox objectBounds = object.getBounds();
            if (objectBounds == null) {
                infiniteCell.add(object);
                continue;
            }
            getGridIndex(objectBounds.getMinimum(), i0);
            getGridIndex(objectBounds.getMaximum(), i1);
            for (int ix = i0[0]; ix <= i1[0]; ix++)
                for (int iy = i0[1]; iy <= i1[1]; iy++)
                    for (int iz = i0[2]; iz <= i1[2]; iz++)
                        if (object.intersects(getGridBox(ix, iy, iz)))
                            cells[ix + (nx * iy) + (nx * ny * iz)].add(object);
        }
        //output.println("[ACC] Building cells ...");
        int numEmpty = 0;
        int numInFull = 0;
        objectList.clear();
        objectList = null;
        infiniteCell.build();
        if (infiniteCell.size() == 0)
            numEmpty++;
        else
            numInFull += infiniteCell.size();
        for (int i = 0; i < cells.length; i++) {
            cells[i].build();
            if (cells[i].size() == 0)
                numEmpty++;
            else
                numInFull += cells[i].size();
        }
        long endTime = System.currentTimeMillis();
        double time = (endTime - startTime) / 1000.0;
        /*output.println("[ACC] Uniform grid statistics:");
        output.println("[ACC]   * Primitives:         " + numPrimitives);
        output.println("[ACC]   * Grid cells:         " + (cells.length + 1));
        output.println("[ACC]   * Used cells:         " + ((cells.length + 1) - numEmpty));
        output.println("[ACC]   * Empty cells:        " + numEmpty);
        output.println("[ACC]   * Occupancy:          " + ((100 * ((1 + cells.length) - numEmpty)) / (1 + cells.length)) + "%");
        output.println("[ACC]   * Objects/Cell:       " + ((double) numInFull / (double) (1 + cells.length)));
        output.println("[ACC]   * Objects/Used Cell:  " + ((double) numInFull / (double) ((1 + cells.length) - numEmpty)));
        output.println("[ACC]   * Creation time:      " + time + " secs.");
        output.println("[ACC] Done.");
         */
    }

    public BoundingBox getBounds() {
        return bounds;
    }

    private static final double EPSILON = 1e-10;

    public void intersect(RenderState state) {
        Ray r = state.getRay();
        double txmin;
        double txmax;
        double tymin;
        double tymax;
        double tzmin;
        double tzmax;
        Point3 o = r.getOrigin();
        Vector3 d = r.getDirection();
        Point3 minimum = bounds.getMinimum();
        Point3 maximum = bounds.getMaximum();

        boolean smallDx = (d.x > -EPSILON) && (d.x < EPSILON);
        boolean smallDy = (d.y > -EPSILON) && (d.y < EPSILON);
        boolean smallDz = (d.z > -EPSILON) && (d.z < EPSILON);

        if (smallDx) {
            txmin = Double.NEGATIVE_INFINITY;
            txmax = Double.POSITIVE_INFINITY;
        } else if (d.x > 0) {
            txmin = (minimum.x - o.x) / d.x;
            txmax = (maximum.x - o.x) / d.x;
        } else {
            txmin = (maximum.x - o.x) / d.x;
            txmax = (minimum.x - o.x) / d.x;
        }
        if (smallDy) {
            tymin = Double.NEGATIVE_INFINITY;
            tymax = Double.POSITIVE_INFINITY;
        } else if (d.y > 0) {
            tymin = (minimum.y - o.y) / d.y;
            tymax = (maximum.y - o.y) / d.y;
        } else {
            tymin = (maximum.y - o.y) / d.y;
            tymax = (minimum.y - o.y) / d.y;
        }
        if (smallDz) {
            tzmin = Double.NEGATIVE_INFINITY;
            tzmax = Double.POSITIVE_INFINITY;
        } else if (d.z > 0) {
            tzmin = (minimum.z - o.z) / d.z;
            tzmax = (maximum.z - o.z) / d.z;
        } else {
            tzmin = (maximum.z - o.z) / d.z;
            tzmax = (minimum.z - o.z) / d.z;
        }

        // find the biggest of txmin, tymin, tzmin
        double t0 = (txmin > tymin) ? txmin : tymin;
        if (tzmin > t0)
            t0 = tzmin;

        // find the smallest of txmax, tymax, tzmax
        double t1 = (txmax < tymax) ? txmax : tymax;
        if (tzmax < t1)
            t1 = tzmax;
        if (t0 >= t1) {
            infiniteCell.intersect(state);
            return;
        }
        double dtx = (txmax - txmin) / nx;
        double dty = (tymax - tymin) / ny;
        double dtz = (tzmax - tzmin) / nz;
        int[] i = new int[3];
        int ix;
        int iy;
        int iz;
        double t00 = r.getMin();
        double t10 = r.getMax();
        r.setMin(t0);
        getGridIndex(r.getPoint(r.getMin(), new Point3()), i);
        ix = i[0];
        iy = i[1];
        iz = i[2];
        double txnext;
        double tynext;
        double tznext;
        int ixstep;
        int iystep;
        int izstep;
        int ixstop;
        int iystop;
        int izstop;
        int indexStepX;
        int indexStepY;
        int indexStepZ;

        if (smallDx) {
            txnext = Double.POSITIVE_INFINITY;
            ixstep = 0;
            ixstop = ix;
            indexStepX = 0;
        } else if (d.x > 0) {
            txnext = txmin + ((ix + 1) * dtx);
            ixstep = 1;
            ixstop = nx;
            indexStepX = 1;
        } else {
            txnext = txmin + ((nx - ix) * dtx);
            ixstep = -1;
            ixstop = -1;
            indexStepX = -1;
        }
        if (smallDy) {
            tynext = Double.POSITIVE_INFINITY;
            iystep = 0;
            iystop = iy;
            indexStepY = 0;
        } else if (d.y > 0) {
            tynext = tymin + ((iy + 1) * dty);
            iystep = 1;
            iystop = ny;
            indexStepY = nx;
        } else {
            tynext = tymin + ((ny - iy) * dty);
            iystep = -1;
            iystop = -1;
            indexStepY = -nx;
        }
        if (smallDz) {
            tznext = Double.POSITIVE_INFINITY;
            izstep = 0;
            izstop = iz;
            indexStepZ = 0;
        } else if (d.z > 0) {
            tznext = tzmin + ((iz + 1) * dtz);
            izstep = 1;
            izstop = nz;
            indexStepZ = nx * ny;
        } else {
            tznext = tzmin + ((nz - iz) * dtz);
            izstep = -1;
            izstop = -1;
            indexStepZ = -nx * ny;
        }
        t0 = r.getMin();
        t1 = r.getMax();
        int gridIndex = ix + (nx * iy) + (nx * ny * iz);
        while (!state.hit() && !r.isEmpty()) {
            r.setMinMax(t0, t1);
            if ((txnext < tynext) && (txnext < tznext)) {
                r.setMax(txnext);
                cells[gridIndex].intersect(state);
                t0 = txnext;
                txnext += dtx;
                ix += ixstep;
                gridIndex += indexStepX;
                if (ix == ixstop)
                    break;
            } else if (tynext < tznext) {
                r.setMax(tynext);
                cells[gridIndex].intersect(state);
                t0 = tynext;
                tynext += dty;
                iy += iystep;
                gridIndex += indexStepY;
                if (iy == iystop)
                    break;
            } else {
                r.setMax(tznext);
                cells[gridIndex].intersect(state);
                t0 = tznext;
                tznext += dtz;
                iz += izstep;
                gridIndex += indexStepZ;
                if (iz == izstop)
                    break;
            }
        }
        if (state.hit())
            t10 = r.getMax();
        r.setMinMax(t00, t10);
        infiniteCell.intersect(state);
    }

    public boolean intersects(Ray r) {
        double txmin;
        double txmax;
        double tymin;
        double tymax;
        double tzmin;
        double tzmax;
        Point3 o = r.getOrigin();
        Vector3 d = r.getDirection();
        Point3 minimum = bounds.getMinimum();
        Point3 maximum = bounds.getMaximum();

        boolean smallDx = (d.x > -EPSILON) && (d.x < EPSILON);
        boolean smallDy = (d.y > -EPSILON) && (d.y < EPSILON);
        boolean smallDz = (d.z > -EPSILON) && (d.z < EPSILON);

        if (smallDx) {
            txmin = Double.NEGATIVE_INFINITY;
            txmax = Double.POSITIVE_INFINITY;
        } else if (d.x > 0) {
            txmin = (minimum.x - o.x) / d.x;
            txmax = (maximum.x - o.x) / d.x;
        } else {
            txmin = (maximum.x - o.x) / d.x;
            txmax = (minimum.x - o.x) / d.x;
        }
        if (smallDy) {
            tymin = Double.NEGATIVE_INFINITY;
            tymax = Double.POSITIVE_INFINITY;
        } else if (d.y > 0) {
            tymin = (minimum.y - o.y) / d.y;
            tymax = (maximum.y - o.y) / d.y;
        } else {
            tymin = (maximum.y - o.y) / d.y;
            tymax = (minimum.y - o.y) / d.y;
        }
        if (smallDz) {
            tzmin = Double.NEGATIVE_INFINITY;
            tzmax = Double.POSITIVE_INFINITY;
        } else if (d.z > 0) {
            tzmin = (minimum.z - o.z) / d.z;
            tzmax = (maximum.z - o.z) / d.z;
        } else {
            tzmin = (maximum.z - o.z) / d.z;
            tzmax = (minimum.z - o.z) / d.z;
        }

        // find the biggest of txmin, tymin, tzmin
        double t0 = (txmin > tymin) ? txmin : tymin;
        if (tzmin > t0)
            t0 = tzmin;

        // find the smallest of txmax, tymax, tzmax
        double t1 = (txmax < tymax) ? txmax : tymax;
        if (tzmax < t1)
            t1 = tzmax;
        if (t0 >= t1)
            return infiniteCell.intersects(r);
        double dtx = (txmax - txmin) / nx;
        double dty = (tymax - tymin) / ny;
        double dtz = (tzmax - tzmin) / nz;
        int[] i = new int[3];
        int ix;
        int iy;
        int iz;
        r.setMin(t0);
        if (infiniteCell.intersects(r))
            return true;
        getGridIndex(r.getPoint(r.getMin(), new Point3()), i);
        ix = i[0];
        iy = i[1];
        iz = i[2];
        double txnext;
        double tynext;
        double tznext;
        int ixstep;
        int iystep;
        int izstep;
        int ixstop;
        int iystop;
        int izstop;
        int indexStepX;
        int indexStepY;
        int indexStepZ;
        if (smallDx) {
            txnext = Double.POSITIVE_INFINITY;
            ixstep = 0;
            ixstop = ix;
            indexStepX = 0;
        } else if (d.x > 0) {
            txnext = txmin + ((ix + 1) * dtx);
            ixstep = 1;
            ixstop = nx;
            indexStepX = 1;
        } else {
            txnext = txmin + ((nx - ix) * dtx);
            ixstep = -1;
            ixstop = -1;
            indexStepX = -1;
        }
        if (smallDy) {
            tynext = Double.POSITIVE_INFINITY;
            iystep = 0;
            iystop = iy;
            indexStepY = 0;
        } else if (d.y > 0) {
            tynext = tymin + ((iy + 1) * dty);
            iystep = 1;
            iystop = ny;
            indexStepY = nx;
        } else {
            tynext = tymin + ((ny - iy) * dty);
            iystep = -1;
            iystop = -1;
            indexStepY = -nx;
        }
        if (smallDz) {
            tznext = Double.POSITIVE_INFINITY;
            izstep = 0;
            izstop = iz;
            indexStepZ = 0;
        } else if (d.z > 0) {
            tznext = tzmin + ((iz + 1) * dtz);
            izstep = 1;
            izstop = nz;
            indexStepZ = nx * ny;
        } else {
            tznext = tzmin + ((nz - iz) * dtz);
            izstep = -1;
            izstop = -1;
            indexStepZ = -nx * ny;
        }
        t0 = r.getMin();
        t1 = r.getMax();
        int gridIndex = ix + (nx * iy) + (nx * ny * iz);
        while (!r.isEmpty()) {
            r.setMinMax(t0, t1);
            if ((txnext < tynext) && (txnext < tznext)) {
                r.setMax(txnext);
                if (cells[gridIndex].intersects(r))
                    return true;
                t0 = txnext;
                txnext += dtx;
                ix += ixstep;
                gridIndex += indexStepX;
                if (ix == ixstop)
                    break;
            } else if (tynext < tznext) {
                r.setMax(tynext);
                if (cells[gridIndex].intersects(r))
                    return true;
                t0 = tynext;
                tynext += dty;
                iy += iystep;
                gridIndex += indexStepY;
                if (iy == iystop)
                    break;
            } else {
                r.setMax(tznext);
                if (cells[gridIndex].intersects(r))
                    return true;
                t0 = tznext;
                tznext += dtz;
                iz += izstep;
                gridIndex += indexStepZ;
                if (iz == izstop)
                    break;
            }
        }
        return false;
    }

    private void getGridIndex(Point3 p, int[] i) {
        Vector3 ext = bounds.getExtents();
        i[0] = (int) (((p.x - bounds.getMinimum().x) * nx) / ext.x);
        i[1] = (int) (((p.y - bounds.getMinimum().y) * ny) / ext.y);
        i[2] = (int) (((p.z - bounds.getMinimum().z) * nz) / ext.z);
        i[0] = MathUtils.clamp(i[0], 0, nx - 1);
        i[1] = MathUtils.clamp(i[1], 0, ny - 1);
        i[2] = MathUtils.clamp(i[2], 0, nz - 1);
    }

    private BoundingBox getGridBox(int x, int y, int z) {
        BoundingBox box = new BoundingBox();
        Vector3 ext = bounds.getExtents();
        box.getMinimum().x = bounds.getMinimum().x + ((ext.x * x) / nx);
        box.getMinimum().y = bounds.getMinimum().y + ((ext.y * y) / ny);
        box.getMinimum().z = bounds.getMinimum().z + ((ext.z * z) / nz);
        box.getMaximum().x = bounds.getMinimum().x + ((ext.x * (x + 1)) / nx);
        box.getMaximum().y = bounds.getMinimum().y + ((ext.y * (y + 1)) / ny);
        box.getMaximum().z = bounds.getMinimum().z + ((ext.z * (z + 1)) / nz);
        return box;
    }

    private final class GridCell implements IntersectionAccelerator {
        private ArrayList list;
        private Intersectable[] objects;

        private GridCell() {
            list = new ArrayList();
            objects = null;
        }

        int size() {
            return objects.length;
        }

        public void add(Intersectable tri) {
            list.add(tri);
        }

        public void build() {
            objects = (Intersectable[]) list.toArray(new Intersectable[list.size()]);
            list = null;
        }

        public void intersect(RenderState state) {
            for (int i = 0; i < objects.length; i++)
                objects[i].intersect(state);
        }

        public boolean intersects(Ray r) {
            for (int i = 0; i < objects.length; i++)
                if (objects[i].intersects(r))
                    return true;
            return false;
        }

        public BoundingBox getBounds() {
            return null;
        }
    }
}