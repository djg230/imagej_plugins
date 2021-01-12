import ij.*;
import ij.process.*;
import ij.gui.*;
import java.util.*;
import java.awt.*;
import ij.plugin.filter.*;
import ij.process.*;
import java.lang.Math.*;

//Program to implement a y-pixel horizontal blur on a grayscale photo. See readme for examples
public class Horizontal_Blur implements PlugInFilter {
	String title = null;
	double blur[][] = {{0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0},
	{0.14,0.14,0.14,0.14,0.14,0.14,0.14},
	{0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0}};
	double pixel;
	int val;
	
	public int setup(String arg, ImagePlus im) {
		return DOES_8G;
	}
		
	public void run(ImageProcessor ip) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		double[][] matrix = new double[7][7];
		ImageProcessor copy = ip.duplicate();
		for (int x=4; x < w-5; x++) {
			for (int y=4; y < h-5; y++) {
				pixel = 0;
				for(int i = 0; i < 7; i++){
					for(int p = 0; p < 7; p++){
						matrix[i][p] = copy.getPixel(x-3+i,y-3+p);
					}
				}
		
				for(int l = 0; l < 7; l++){
					for(int j = 0; j < 7; j++){
						pixel += matrix[l][j] * blur[l][j];
					}
				}
				val = (int)pixel;
				ip.putPixel(x,y,val);
			}
		}
	}
}