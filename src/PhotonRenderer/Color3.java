package PhotonRenderer;

import drawing_prog.*;

public final class Color3 {
    private double r;
    private double g;
    private double b;
    public static final double GAMMA = 2.2;
    public static final Color3 BLACK = new Color3(0.0, 0.0, 0.0);
    public static final Color3 RED = new Color3(1.0, 0.0, 0.0);
    public static final Color3 GREEN = new Color3(0.0, 1.0, 0.0);
    public static final Color3 BLUE = new Color3(0.0, 0.0, 1.0);
    public static final Color3 WHITE = new Color3(1.0, 1.0, 1.0);
    public static final Color3 YELLOW = new Color3(0.7, 0.7, 0.0);

    public Color3() {}

    public Color3(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color3(Color3 c) {
        r = c.r;
        g = c.g;
        b = c.b;
    }

    public Color3(int rgb) {
        r = ((rgb >> 16) & 0xFF) / 255.0f;
        g = ((rgb >> 8) & 0xFF) / 255.0f;
        b = (rgb & 0xFF) / 255.0f;
    }

    public Color3 copy() {
        return new Color3(this);
    }

    public final Color3 set(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
        return this;
    }

    public final Color3 set(Color3 c) {
        r = c.r;
        g = c.g;
        b = c.b;
        return this;
    }

    public final Color3 setRGB(int rgb) {
        r = ((rgb >> 16) & 0xFF) / 255.0;
        g = ((rgb >> 8) & 0xFF) / 255.0;
        b = (rgb & 0xFF) / 255.0;
        return this;
    }

    public final Color3 setRGBE(int rgbe) {
        int e = rgbe & 0xFF;
        if (e != 0) {
            double f = 1.0f;
            e = e - (128 + 8);
            if (e > 0)
                for (int i = 0; i < e; i++)
                    f *= 2.0f;
            else
                for (int i = 0; i < -e; i++)
                    f *= 0.5f;
            int ir = (rgbe >> 24) & 0xFF;
            int ig = (rgbe >> 16) & 0xFF;
            int ib = (rgbe >> 8) & 0xFF;
            r = f * (ir + 0.5f);
            g = f * (ig + 0.5f);
            b = f * (ib + 0.5f);
        } else
            r = g = b = 0.0f;
        return this;
    }

    public final double getLuminance() {
        return (0.2989 * r) + (0.5866 * g) + (0.1145 * b);
    }

    public final double getMin() {
        return MathUtils.min(r, g, b);
    }

    public final double getMax() {
        return MathUtils.max(r, g, b);
    }

    public final double getAverage() {
        return (r + g + b) / 3.0;
    }

    public final int toRGB() {
        // gamma correct and pack into 32bits
        int ir = (int) (Math.pow(r, 1.0 / GAMMA) * 255.0);
        int ig = (int) (Math.pow(g, 1.0 / GAMMA) * 255.0);
        int ib = (int) (Math.pow(b, 1.0 / GAMMA) * 255.0);
        ir = MathUtils.clamp(ir, 0, 255);
        ig = MathUtils.clamp(ig, 0, 255);
        ib = MathUtils.clamp(ib, 0, 255);
        return (ir << 16) | (ig << 8) | ib;
    }

    public final int toRGB(boolean gammaCorrect) {
        if (gammaCorrect)
            return toRGB();
        int ir = (int) (r * 255.0);
        int ig = (int) (g * 255.0);
        int ib = (int) (b * 255.0);
        ir = MathUtils.clamp(ir, 0, 255);
        ig = MathUtils.clamp(ig, 0, 255);
        ib = MathUtils.clamp(ib, 0, 255);
        return (ir << 16) | (ig << 8) | ib;
    }

    public final int toRGBE() {
        // encode the color into 32bits while preserving HDR using Ward's RGBE technique
        double v = MathUtils.max(r, g, b);
        if (v < 1e-32)
            return 0;

        // get mantissa and exponent
        double m = v;
        int e = 0;
        if (v > 1.0f) {
            while (m > 1.0f) {
                m *= 0.5f;
                e++;
            }
        } else if (v <= 0.5f) {
            while (m <= 0.5f) {
                m *= 2.0f;
                e--;
            }
        }
        v = (m * 255.0f) / v;
        int c = (e + 128);
        c |= ((int) (r * v) << 24);
        c |= ((int) (g * v) << 16);
        c |= ((int) (b * v) << 8);
        return c;
    }

    public final Color3 add(Color3 c) {
        r += c.r;
        g += c.g;
        b += c.b;
        return this;
    }

    public static final Color3 add(Color3 c1, Color3 c2) {
        return Color3.add(c1, c2, new Color3());
    }

    public static final Color3 add(Color3 c1, Color3 c2, Color3 dest) {
        dest.r = c1.r + c2.r;
        dest.g = c1.g + c2.g;
        dest.b = c1.b + c2.b;
        return dest;
    }

    public final Color3 madd(double s, Color3 c) {
        r += (s * c.r);
        g += (s * c.g);
        b += (s * c.b);
        return this;
    }

    public final Color3 sub(Color3 c) {
        r -= c.r;
        g -= c.g;
        b -= c.b;
        return this;
    }

    public static final Color3 sub(Color3 c1, Color3 c2) {
        return Color3.sub(c1, c2, new Color3());
    }

    public static final Color3 sub(Color3 c1, Color3 c2, Color3 dest) {
        dest.r = c1.r - c2.r;
        dest.g = c1.g - c2.g;
        dest.b = c1.b - c2.b;
        return dest;
    }

    public final Color3 mul(Color3 c) {
        r *= c.r;
        g *= c.g;
        b *= c.b;
        return this;
    }

    public static final Color3 mul(Color3 c1, Color3 c2) {
        return Color3.mul(c1, c2, new Color3());
    }

    public static final Color3 mul(Color3 c1, Color3 c2, Color3 dest) {
        dest.r = c1.r * c2.r;
        dest.g = c1.g * c2.g;
        dest.b = c1.b * c2.b;
        return dest;
    }

    public final Color3 mul(double s) {
        r *= s;
        g *= s;
        b *= s;
        return this;
    }

    public static final Color3 mul(double s, Color3 c) {
        return Color3.mul(s, c, new Color3());
    }

    public static final Color3 mul(double s, Color3 c, Color3 dest) {
        dest.r = s * c.r;
        dest.g = s * c.g;
        dest.b = s * c.b;
        return dest;
    }

    public final Color3 div(Color3 c) {
        r /= c.r;
        g /= c.g;
        b /= c.b;
        return this;
    }

    public static final Color3 div(Color3 c1, Color3 c2) {
        return Color3.div(c1, c2, new Color3());
    }

    public static final Color3 div(Color3 c1, Color3 c2, Color3 dest) {
        dest.r = c1.r / c2.r;
        dest.g = c1.g / c2.g;
        dest.b = c1.b / c2.b;
        return dest;
    }
    
    public double getR(){
        return r;
    }
    public double getG(){
        return g;
    }
    public double getB(){
        return b;
    }
}