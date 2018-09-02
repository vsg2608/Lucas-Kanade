
public class Image {
	public float[][] color;
	private int width;
	private int height;
	public int totalColors=255;
	
	public Image(int width, int height, float[][]color) {
		this.color=color;
		this.height=height;
		this.width=width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public  float[][] spaceDerivative(){
		float[][] derivative= new float[this.getWidth()*this.getHeight()][2];
		int n=0;
		for(int x=0;x<this.getWidth();x++) {
			for(int y=0;y<this.getHeight();y++) {
				if(x==0)
					derivative[n][0]=(this.color[x+1][y]-this.color[x][y])/totalColors;
				else if(x==this.getWidth()-1)
					derivative[n][0]=(this.color[x][y]-this.color[x-1][y])/totalColors;
				else 
					derivative[n][0]=(this.color[x+1][y]-this.color[x-1][y])/2/totalColors;
				if(y==0)
					derivative[n][1]=(this.color[x][y+1]-this.color[x][y])/totalColors;
				else if(y==this.getHeight()-1)
					derivative[n][1]=(this.color[x][y]-this.color[x][y-1])/totalColors;
				else 
					derivative[n][1]=(this.color[x][y+1]-this.color[x][y-1])/2/totalColors;
				
				n++;
			}
		}
		return derivative;
	}
	
	public float[] timeDerivative(Image image){
		float[] derivative= new float[this.getWidth()*this.getHeight()];
		int n=0;
		for(int x=0;x<this.getWidth();x++) {
			for(int y=0;y<this.getHeight();y++) {
				derivative[n]=-(image.color[x][y]-this.color[x][y])/totalColors;
				n++;
			}
		}
		
		return derivative;
	}
	
}
