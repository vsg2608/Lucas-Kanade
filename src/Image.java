import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Image {
	
	
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
	
	public static void displayImage(BufferedImage image) {
		//Displaying image
			JFrame frame = new JFrame("Image");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JLabel lbl= new JLabel();
			lbl.setIcon(new ImageIcon(image));
			frame.getContentPane().add(lbl, BorderLayout.CENTER);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
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
