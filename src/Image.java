
public class Image {
	public int[][] color;
	private int width;
	private int height;
	
	public Image(int width, int height, int[][]color) {
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
}
