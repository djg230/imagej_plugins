import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import ij.plugin.filter.*;
import java.util.Random;


//counts the number of pure red, blue, and green pixels in an image. See readme for examples
public class Color_Counter implements PlugInFilter {
	ImagePlus imp;
	public int setup(String arg, ImagePlus imp){
	this.imp = imp;
	return DOES_RGB+NO_UNDO+NO_CHANGES+DOES_STACKS;
	}
	public void run(ImageProcessor ip) {
	int red = 0;
	int blue = 0;
	int green = 0;
	int white = 0;
	int black = 0;
	int r;
	int g;
	int b;
	int[] RGB = new int[3];
	int h = ip.getHeight();
	int w = ip.getWidth();
	for (int i=0; i<h; i++){
		for(int j = 0; j<w; j++){
			ip.getPixel(j,i, RGB);
			r = RGB[0];
			g = RGB[1];
			b = RGB[2];
			if(r == 255 && g == 0 && b == 0){
				red++;
			} else if(r == 0 && g == 255 && b == 0){
				green++;
			} else if(r == 0 && g == 0 && b == 255){
				blue++;
			} else if(r == 0 && g == 0 && b == 0){
				black++;
			} else if(r == 255 && g == 255 && b == 255){
				white++;
			}
		}
	}
	IJ.log("red: " + red);
	IJ.log("green: " + green);
	IJ.log("blue: " + blue);
	IJ.log("black: " + black);
	IJ.log("white: " + white);
	}
}