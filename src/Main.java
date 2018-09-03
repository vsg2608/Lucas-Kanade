import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	////////////////////////////MAIN/////////////////////////////////////////
	public static void main(String[] args) throws IOException {
		BufferedImage image1=null;
		BufferedImage image2=null;
		image1=readImage(image1,"res/Screenshot (8).png");
		image2=readImage(image2,"res/Screenshot (9).png");
		
		int width=image1.getWidth();
		int height=image1.getHeight();	
		
//		image1= resizeImage(image1, width, height);
//		image2= resizeImage(image2, width, height);
		
		width=image1.getWidth();
		height=image1.getHeight();	
		
		image1=convertToBW(image1);
		float[][] color= getColorMatrix(image1);
		Image img1=new Image(width,height,color);
		image2=convertToBW(image2);
		color= getColorMatrix(image2);
		Image img2=new Image(width,height,color);
		
		writeImage(image1,"res/Output1.jpg");
		writeImage(image2,"res/Output2.jpg");
		int noOfPixels=width*height;
		float[][] Ixy= img1.spaceDerivative();
		float[] v=new float[2];
		float[] It= img1.timeDerivative(img2);
		
		float[][] AtA = {{0,0},{0,0}};
		float[] Atb= {0,0};	
		
		for(int i=0;i<noOfPixels;i++) {
			AtA[0][0]+=Ixy[i][0]*Ixy[i][0];
			AtA[0][1]+=Ixy[i][0]*Ixy[i][1];
			AtA[1][1]+=Ixy[i][1]*Ixy[i][1];
			Atb[0]+=It[i]*Ixy[i][0];
			Atb[1]+=It[i]*Ixy[i][1];
		}
		AtA[1][0]=AtA[0][1];
		float[][] AtAi=Matrix.inverseMatrix2(AtA);
		v[0]=AtAi[0][0]*Atb[0]+AtAi[0][1]*Atb[1];
		v[1]=AtAi[1][0]*Atb[0]+AtAi[1][1]*Atb[1];
		
		System.out.print(v[0]+" "+v[1]);

			
	}
	////////////////////////////////////////////////////////////////////////////
	
	public static BufferedImage convertToBW(BufferedImage image) {
		int width = image.getWidth();
		int height= image.getHeight();
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
			}
		}
		return image;
	}
	
	public static float[][] getColorMatrix(BufferedImage image){
		int width = image.getWidth();
		int height= image.getHeight();
		float[][] color= new float[width][height];
		for(int x=0;x< width;x++) {
			for(int y=0;y<height;y++) {
				int p=image.getRGB(x, y);
				int a = (p>>24) & 0xff;
			    int BW = (p>>16) & 0xff;
			    color[x][y]=BW;
			}
		}
		return color;
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
	
	public static BufferedImage resizeImage(final java.awt.Image image, int width, int height) {
	    int targetw = 0;
	    int targeth = 75;

	    if (width > height)targetw = 112;
	    else targetw = 50;

	    do {
	        if (width > targetw) {
	            width /= 2;
	        }

	        if (height > targeth) {
	            height /= 2;
	        }
	    } while (width > targetw || height > targeth);

	    final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    final Graphics2D graphics2D = bufferedImage.createGraphics();
	    graphics2D.setComposite(AlphaComposite.Src);
	    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	    graphics2D.drawImage(image, 0, 0, width, height, null);
	    graphics2D.dispose();

	    return bufferedImage;
	}
	
	
}
