package drawing_prog;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import PhotonRenderer.Color3;



public class Bitmap {
    private int[] pixels;
    private int width;
    private int height;
    private boolean isHDR;

    public Bitmap(String filename) {
        try {
            BufferedImage bi = ImageIO.read(new File(filename));
            width = bi.getWidth();
            height = bi.getHeight();
            isHDR = false;
            pixels = new int[width * height];
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++)
                    setPixel(x, height - 1 - y, bi.getRGB(x, y));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap(int w, int h, boolean isHDR) {
        width = w;
        height = h;
        this.isHDR = isHDR;
        pixels = new int[w * h];
    }

    public void setPixel(int x, int y, Color3 c) {
        if ((x >= 0) && (x < width) && (y >= 0) && (y < height))
            pixels[(y * width) + x] = isHDR ? c.toRGBE() : c.toRGB();
    }

    public void setPixel(int x, int y, int rgb) {
        if ((x >= 0) && (x < width) && (y >= 0) && (y < height))
            pixels[(y * width) + x] = isHDR ? new Color3(rgb).toRGBE() : rgb;
    }

    public Color3 getPixel(int x, int y) {
        if ((x >= 0) && (x < width) && (y >= 0) && (y < height))
            return isHDR ? new Color3().setRGBE(pixels[(y * width) + x]) : new Color3(pixels[(y * width) + x]);
        return Color3.BLACK.copy();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void save(String filename) {
        if (filename.endsWith(".hdr"))
            saveHDR(filename);
        else if (filename.endsWith(".png"))
            savePNG(filename);
        else
            saveHDR(filename + ".hdr");
    }

    private void savePNG(String filename) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                bi.setRGB(x, height - 1 - y, isHDR ? getPixel(x, y).toRGB() : pixels[(y * width) + x]);
        try {
            ImageIO.write(bi, "png", new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveHDR(String filename) {
        try {
            FileOutputStream f = new FileOutputStream(filename);
            f.write("#?RGBE\n".getBytes());
            f.write("FORMAT=32-bit_rle_rgbe\n\n".getBytes());
            f.write(("-Y " + height + " +X " + width + "\n").getBytes());
            for (int y = height - 1; y >= 0; y--) {
                for (int x = 0; x < width; x++) {
                    int rgbe = isHDR ? pixels[(y * width) + x] : new Color3(pixels[(y * width) + x]).toRGBE();
                    f.write(rgbe >> 24);
                    f.write(rgbe >> 16);
                    f.write(rgbe >> 8);
                    f.write(rgbe);
                }
            }
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}