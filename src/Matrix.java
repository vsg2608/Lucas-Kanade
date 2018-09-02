
public class Matrix {
	
	public static float[][] getTransposeMatrix(float[][] Matrix, int size){
		float[][] MatrixT= new float[2][size];
		
		for(int i=0;i<size;i++) {
			MatrixT[0][i]=Matrix[i][0];
			MatrixT[1][i]=Matrix[i][1];
		}
		
		return MatrixT;
	}
	
	public static float[][] mulitplyMatrix(float[][]Matrix1, float[][] Matrix2){
		float[][] Result=null;
		
		return Result;
	}
	
	public static float[][] inverseMatrix2(float[][] Matrix){
		float[][] Result=new float[2][2];
		float determinant=Matrix[1][1]*Matrix[0][0]-Matrix[0][1]*Matrix[1][0];
		Result[0][0]=Matrix[1][1]/determinant;
		Result[1][1]=Matrix[0][0]/determinant;
		Result[1][0]=-Matrix[1][0]/determinant;
		Result[0][1]=-Matrix[0][1]/determinant;
		return Result;
	}

}
