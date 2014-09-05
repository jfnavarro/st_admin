
package com.spatialtranscriptomics.util;

import com.spatialtranscriptomics.model.Chip;
import com.spatialtranscriptomics.model.Feature;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * Experimental class for computing an image from the accumulated feature
 * expressions.
 */
public class ComputeFeatureImage {
    
    
    public static BufferedImage computeImage(Chip chip, List<Feature> features) throws IOException {
        int xdim = chip.getX2_total() /* - chip.getX1_total()*/  + 1;
        int ydim = chip.getY2_total() /* - chip.getY1_total()*/ + 1;
        int[][] intensity = new int[xdim][ydim];
        //int xoff = chip.getX1_total();
        //int yoff = chip.getY1_total();
        int max = -1;
        for (Feature f : features) {
            int x = f.getX() /* - xoff*/;
            int y = f.getY() /* - yoff*/;
            intensity[x][y] += f.getHits();
            if (intensity[y][y] > max) {
                max = intensity[x][y];
            }
        }
        
//        BufferedImage test = ImageIO.read(new File("/Users/joelsjostrand/donkey1.jpg"));
//        int[] pix = new int[100];
//        PixelGrabber pg = new PixelGrabber(test, 588, 402, 10, 10, pix, 0, 10);
//        try {
//            pg.grabPixels();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ComputeFeatureImage.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        BufferedImage img2 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
//        for (int i = 0; i < 100; ++i) {
//            int r   = (pix[i] >> 16) & 0xff;
//            int g = (pix[i] >>  8) & 0xff;
//            int b  = (pix[i]) & 0xff;
//            int rgb = (r << 16) | (g << 8) | b;
//            System.out.println("(" + r + "," + g + "," + b + ")");
//            img2.setRGB(i / 10, i % 10, rgb);
//        }
//        
//        File outputfile2 = new File("/Users/joelsjostrand/imagealignment2.png");
//        ImageIO.write(img2, "png", outputfile2);
        
        // Normalize logged.
        double logmax = Math.log(max);
        BufferedImage img = new BufferedImage(xdim, ydim, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < xdim; x++) {
            for (int y = 0; y < ydim; y++) {
                double val = Math.log(intensity[x][y]) / logmax * 255;
                int v = (int) Math.round(val);
                int rgb = (v << 16) | (v << 8) | v;
                img.setRGB(x, y, rgb);
            }
        }
        //File outputfile = new File("/Users/joelsjostrand/imagealignment.png");
        //ImageIO.write(img, "png", outputfile);
        return img;
    }
}
