import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import java.util.Random;

//program to create a random static image. See readme for examples
public class Random_Image implements PlugIn {
	public void run(String arg) {
	int w = 500, h = 500;
	Random r = new Random();
	ImageProcessor ip = new ByteProcessor(w,h);
	byte[] pixels = (byte[])ip.getPixels();
	for(int i = 0; i < pixels.length; i++){
		pixels[i] = (byte)r.nextInt(256);
	}
	new ImagePlus("Random Image", ip).show();
	}
}
