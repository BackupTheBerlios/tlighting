package drawing_prog;

public final class MathUtils {
    private MathUtils() {}

    public static final int clamp(int x, int min, int max) {
        if (x < min)
            return min;
        if (x > max)
            return max;
        return x;
    }

    public static final float clamp(float x, float min, float max) {
        if (x < min)
            return min;
        if (x > max)
            return max;
        return x;
    }

    public static final double clamp(double x, double min, double max) {
        if (x < min)
            return min;
        if (x > max)
            return max;
        return x;
    }

    public static final int min(int a, int b, int c) {
        int min = a;
        if (min < b)
            min = b;
        if (min < c)
            min = c;
        return min;
    }

    public static final float min(float a, float b, float c) {
        float min = a;
        if (min < b)
            min = b;
        if (min < c)
            min = c;
        return min;
    }

    public static final double min(double a, double b, double c) {
        double min = a;
        if (min < b)
            min = b;
        if (min < c)
            min = c;
        return min;
    }

    public static final int max(int a, int b, int c) {
        int max = a;
        if (max < b)
            max = b;
        if (max < c)
            max = c;
        return max;
    }

    public static final float max(float a, float b, float c) {
        float max = a;
        if (max < b)
            max = b;
        if (max < c)
            max = c;
        return max;
    }

    public static final double max(double a, double b, double c) {
        double max = a;
        if (max < b)
            max = b;
        if (max < c)
            max = c;
        return max;
    }
}