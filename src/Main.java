import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedImage image=null;
		image=readImage(image,"res/Screenshot (8).png");
		
		int width=image.getWidth();
		int height=image.getHeight();
		int[][] color= new int[width][height];
		//Converts colored image into black and white Image
		for(int x=0;x< width;x++) {
			for(int y=0;y<height;y++) {
				int p=image.getRGB(x, y);
				int a = (p>>24) & 0xff;
			    int r = (p>>16) & 0xff;
			    int g = (p>>8) & 0xff;
			    int b = p & 0xff;
			    int BW=(Max3(r,g,b)+Min3(r,g,b))/2;
			    p = (a<<24) | (BW<<16) | (BW<<8) | BW;
			    image.setRGB(x, y, p);
			    color[x][y]=BW;
			    //System.out.print(BW+" ");
			}
			//System.out.println();
		}
		
		Image img1=new Image(width,height,color);
		
		
		
		writeImage(image,"res/Output.jpg");
	}
	 
	public static BufferedImage readImage(BufferedImage image, String Input_Path) {
		try {
			File input_file= new File(Input_Path);
			image = ImageIO.read(input_file);
			System.out.println("Reading complete.");
			return image;
		}
		catch(IOException e) {
			System.out.println("Error:"+ e);
		}
		return image;
	}
	
	public static void writeImage(BufferedImage image, String Output_FilePath) {
		//WRITE IMAGE
		try {
			File Output_File= new File(Output_FilePath);
			ImageIO.write(image, "jpg", Output_File);
			
			System.out.println("Writing Complete");
			
		}
		catch(IOException e) {
			System.out.println("Error wiriting image: "+e);
		}
	}
	
	public static int Max3(int a, int b, int c) {
		if(a>=b && a>=c) 
			return a;
		else if(b>=c && b>=a)
			return b;
		else 
			return c;
	}
	
	public static int Min3(int a, int b, int c) {
		if(a<=b && a<=c) 
			return a;
		else if(b<=c && b<=a)
			return b;
		else 
			return c;
	}
}
