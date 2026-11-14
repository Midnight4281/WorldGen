package main.noise;

import java.util.Random;

public class PerlinNoise {
    private final int[] permutation = new int[512];

    public PerlinNoise(long seed) {
        int[] p = new int[256];
        for (int i = 0; i < 256; i++) {
            p[i] = i;
        }

        // Shuffle the array using the provided seed
        Random random = new Random(seed);
        for (int i = 255; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = p[index];
            p[index] = p[i];
            p[i] = temp;
        }

        // Duplicate the permutation array to avoid buffer overflows
        for (int i = 0; i < 512; i++) {
            permutation[i] = p[i & 255];
        }
    }

    public double noise(double x, double y) {
        // Find unit grid cell containing point
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;

        // Get relative xy coordinates of point within that cell
        x -= Math.floor(x);
        y -= Math.floor(y);

        // Compute fade curves for each coordinate
        double u = fade(x);
        double v = fade(y);

        // Hash coordinates of the 4 square corners
        int aa = permutation[permutation[X] + Y];
        int ab = permutation[permutation[X] + Y + 1];
        int ba = permutation[permutation[X + 1] + Y];
        int bb = permutation[permutation[X + 1] + Y + 1];

        // Blend results from the 4 corners
        double x1 = lerp(u, grad(aa, x, y), grad(ba, x - 1, y));
        double x2 = lerp(u, grad(ab, x, y - 1), grad(bb, x - 1, y - 1));

        return lerp(v, x1, x2);
    }

    // Smoothing function: 6t^5 - 15t^4 + 10t^3
    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    // Linear interpolation
    private double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    // Calculate dot product between distance and gradient vectors
    private double grad(int hash, double x, double y) {
        int h = hash & 7; // Convert low 3 bits of hash code into 8 gradient directions
        double u = h < 4 ? x : y;
        double v = h < 4 ? y : x;
        return ((h & 1) != 0 ? -u : u) + ((h & 2) != 0 ? -2.0 * v : 2.0 * v);
    }
}

