//package ij.process;
import ij.*;
import ij.process.*;
import ij.gui.*;
import java.util.*;
import java.awt.*;
import ij.plugin.filter.*;
import ij.process.*;
import ij.gui.Plot;
import java.lang.Math.*;
import java.util.ArrayList;

//program to convert an image into CIE xy chromatic color space, then print an chromatic diagram. 
public class CIExy_Plot implements PlugInFilter {


public int setup(String arg, ImagePlus im) { 
        //title = im.getTitle(); 
        return DOES_ALL; 
    } 
 public double[][] M = {{0.4124, 0.3576,  0.1805},
                             {0.2126, 0.7152,  0.0722},
                             {0.0193, 0.1192,  0.9505}};

public void run(ImageProcessor ip){
	int w = ip.getWidth();
    int h = ip.getHeight();
    int r, g, b;
    double[] xy = new double[2];
    ArrayList<Double> x_array = new ArrayList<Double>();
    ArrayList<Double> y_array = new ArrayList<Double>();
    double x,y;
    int[] RGB = new int[3];
    ImageProcessor X = new FloatProcessor(w,h);
    ImageProcessor yy = new FloatProcessor(w,h); 
    ImageProcessor Y = new FloatProcessor(w,h);     
    ImageStack stack = new ImageStack(w,h);


    for (int i=1; i < w; i++) {
        for (int j=1; j < h; j++) {
			      ip.getPixel(i,j, RGB);
            r = RGB[0];
            g = RGB[1];
            b = RGB[2];
            xy = RGBtoxyY(r,g,b);
            X.putPixelValue(i,j, xy[0]);
            yy.putPixelValue(i,j, xy[1]);
            Y.putPixelValue(i,j, xy[2]);
            x = xy[0];
            y = xy[1];
            //X.putPixelValue(i,j,(int[])xy);
            x_array.add(x);
            y_array.add(y);

        }

	}	
  stack.addSlice(X);
  stack.addSlice(yy);
  stack.addSlice(Y);
  ImagePlus fin = new ImagePlus("xyY", stack);
  fin.show();
	//new ImagePlus("xy", X).show();

	Plot plot = new Plot("CIE xy", "x", "y");
 //IJ.log(String.valueOf(plot.toShape("BOX")));
  plot.addPoints(x_array, y_array, 0);
  plot.show();

}
public double[] RGBtoxyY(int R, int G, int B) {
 double[] result = new double[3];
      double[] xy = new double[3];
      // convert 0..255 into 0..1
      double r = R / 255.0;
      double g = G / 255.0;
      double b = B / 255.0;

      // assume sRGB
      if (r <= 0.04045) {
        r = r / 12.92;
      }
      else {
        r = Math.pow(((r + 0.055) / 1.055), 2.4);
      }
      if (g <= 0.04045) {
        g = g / 12.92;
      }
      else {
        g = Math.pow(((g + 0.055) / 1.055), 2.4);
      }
      if (b <= 0.04045) {
        b = b / 12.92;
      }
      else {
        b = Math.pow(((b + 0.055) / 1.055), 2.4);
      }

      r *= 100.0;
      g *= 100.0;
      b *= 100.0;

      // [X Y Z] = [r g b][M]
      result[0] = (r * M[0][0]) + (g * M[0][1]) + (b * M[0][2]);
      result[1] = (r * M[1][0]) + (g * M[1][1]) + (b * M[1][2]);
      result[2] = (r * M[2][0]) + (g * M[2][1]) + (b * M[2][2]);
      xy[0] = result[0]/(result[0]+result[1]+result[2]);
      xy[1] = result[1]/(result[0]+result[1]+result[2]);
      xy[2] = result[1];
      //xy[3] = result[1];
      return xy;
    }

}