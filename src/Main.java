import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	
	public static float Sigma=2;
	
	
	////////////////////////////MAIN/////////////////////////////////////////
	public static void main(String[] args) throws IOException {
		BufferedImage image1=null;
		BufferedImage image2=null;
		image1=Image.readImage(image1,"res/Screenshot (8).png");
		image2=Image.readImage(image2,"res/Screenshot (9).png");
		
		int width=image1.getWidth();		//Image width
		int height=image1.getHeight();		//Image height
		
		//Code to resize image if required
//		image1= Image.resizeImage(image1, width, height);
//		image2= Image.resizeImage(image2, width, height);
		
		width=image1.getWidth();					//Recalculation of width after resizing
		height=image1.getHeight();					//Recalculation of height after resizing
		
		image1=Image.convertToBW(image1);					//Convert to Black and White
		float[][] color= getColorMatrix(image1);	//Get a single color matrix
		imageMatrix img1=new imageMatrix(width,height,color);	//Initializing new frame image
		image2=Image.convertToBW(image2);					//Convert to Black and white image for second frame
		color= getColorMatrix(image2);				//Get a single color matrix
		imageMatrix img2=new imageMatrix(width,height,color);	//Initialization of second frame
		
		
		int noOfPixels=width*height;				//Total no of pixels
		float[][] Ixy= img1.spaceDerivative();		//space derivative matrix
		float[] v=new float[2];						//velocity matrix
		float[] It= img1.timeDerivative(img2);		//Time derivative matrix
		
		//Initialization of matrix
		float[][] AtA = {{0,0},{0,0}};				
		float[] Atb= {0,0};	
		
		int noOfW=20;
		int noOfH=20;
		for(int X=width/noOfW; X<width-width/noOfW; X=X+width/noOfW)
			for(int Y=height/noOfH; Y<height-height/noOfH; Y=Y+height/noOfH){
				//Velocity calculation
				for(int i=0;i<noOfPixels;i++) {
					int y=i%height;
					int x=i/height;
					float weight= gaussianFunction(Math.sqrt((X-x)*(X-x)+(Y-y)*(Y-y)));
					//System.out.println(weight);
					AtA[0][0]+=weight*Ixy[i][0]*Ixy[i][0];
					AtA[0][1]+=weight*Ixy[i][0]*Ixy[i][1];
					AtA[1][1]+=weight*Ixy[i][1]*Ixy[i][1];
					Atb[0]+=weight*It[i]*Ixy[i][0];
					Atb[1]+=weight*It[i]*Ixy[i][1];
				}
				AtA[1][0]=AtA[0][1];
				float[][] AtAi=Matrix.inverseMatrix2(AtA);
				v[0]=AtAi[0][0]*Atb[0]+AtAi[0][1]*Atb[1];
				v[1]=AtAi[1][0]*Atb[0]+AtAi[1][1]*Atb[1];
//				
//				System.out.println(width+" "+height);
//				System.out.println(v[0]+" "+v[1]);
				
				
			    int white = (254<<24) | (255<<16) | (255<<8) | 255;
			    int red = (254<<24) | (255<<16) | (100<<8) | 100;
			    int green = (254<<24) | (100<<16) | (255<<8) | 100;
			   
			    int multiplierForArrows=100;
			    if(v[0]>v[1])
				    for(int i=0;i<v[0]*multiplierForArrows;i++){
				    	try{
				    		image1.setRGB(X+i, (int) (Y+i*v[1]/v[0]), red);
				    	}catch (Exception e) {
				            System.out.println("Out of bound");
				        }
				    }
			    else
			    	for(int i=0;i<v[1]*multiplierForArrows;i++){
				    	try{
				    		image1.setRGB((int) (X+i*v[0]/v[1]), Y+i, red);
				    	}catch (Exception e) {
				            System.out.println("Out of bound");
				        }
				    }
			    image1.setRGB(X, (int) (Y), green);
			}
		
		Image.displayImage(image1);				//Display image on window
		Image.displayImage(image2);
		Image.writeImage(image1,"res/Output0.jpg");		//Writing black and white image
		Image.writeImage(image2,"res/Output1.jpg");		//Writing black and white image
			
	}
	
////////////////////////////////////////////////////////////////////////////
		
	public static float[][] getColorMatrix(BufferedImage image){
		int width = image.getWidth();
		int height= image.getHeight();
		float[][] color= new float[width][height];
		for(int x=0;x< width;x++) {
			for(int y=0;y<height;y++) {
				int p=image.getRGB(x, y);
			    int BW = (p>>16) & 0xff;
			    color[x][y]=BW;
			}
		}
		return color;
	}
	
	public static float gaussianFunction(double d) {
		return (float) ((float) Math.exp(-d/(2*Sigma*Sigma))/Math.sqrt(2*Math.PI*Sigma*Sigma));
	}
	
	
}
