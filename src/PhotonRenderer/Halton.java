package PhotonRenderer;

public final class Halton implements QMCSequence {
    private final double[] invBase;
    private final double[] values;

    public Halton(int base, int dim) {
        invBase = new double[dim];
        values = new double[dim];
        double v = Math.random();
        for (int i = 0; i < dim; i++) {
            values[i] = v;
            invBase[i] = 1.0 / base;
            base = nextPrime(base);
        }
    }

    public final void reset() {
        double v = Math.random();
        for (int i = 0; i < values.length; i++)
            values[i] = v;
    }

    public final double[] getNext() {
        for (int i = 0; i < values.length; i++) {
            double r = 1.0 - values[i] - 1e-10;
            if (invBase[i] < r)
                values[i] += invBase[i];
            else {
                double hh;
                double h = invBase[i];
                do {
                    hh = h;
                    h *= invBase[i];
                } while (h >= r);
                values[i] += ((hh + h) - 1.0);
            }
        }
        return values;
    }

    private static final int nextPrime(int p) {
        p = p + (p & 1) + 1;
        while (true) {
            int div = 3;
            boolean isPrime = true;
            while (isPrime && ((div * div) <= p)) {
                isPrime = ((p % div) != 0);
                div += 2;
            }
            if (isPrime)
                return p;
            p += 2;
        }
    }
}