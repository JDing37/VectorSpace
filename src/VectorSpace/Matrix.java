package VectorSpace;

public class Matrix extends Tensor {
	
	private float eigenValue;
	private Vector eigenVector;
	
  	public Matrix(float[][] matrix) {
		super(matrix);
	}
	
	
	public Matrix(float[] vector) {
		super(vector);
	}
	
	
	public float get(int row, int column) {
		return super.get(0, 0, row, column);
	}
	
		
	public boolean canMult(Matrix m) {
		if(getDim()[3] == m.getDim()[2]) {
			return true;
		}
		return false;
	}
	
	
	public Matrix multiply(Matrix m) {
		if(!canMult(m)) {
			throw new IllegalArgumentException("Matrices can't multiply");
		}
		 float number = 0;
		 float[][] newMatrix = new float[getDim()[2]][m.getDim()[3]];
		 Matrix clone = this.clone();
		   for(int i = 0; i < getDim()[2]; i++) {
			   for(int j = 0; j < m.getDim()[3]; j++) {
				   for(int k = 0; k < getDim()[3]; k++) {
					   number += clone.get(i, k) * m.get(k, j);
				   }
				   newMatrix[i][j] = number;
				   number = 0;
			   }
		 }
		   return new Matrix(newMatrix);
	}
	
	
	public Matrix transpose() {
		float[][] newMatrix = new float[getDim()[2]][getDim()[3]];
		for(int i = 0; i < getDim()[2]; i++) {
			for(int j = 0; j < getDim()[3]; j++) {
				newMatrix[j][i] = this.get(i, j);
			}
		}
		return new Matrix(newMatrix);
	}
	
	
	public Matrix getIndentity(int dimension) {
		float[][] newMatrix = new float[dimension][dimension];
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				if(i == j) {
					newMatrix[i][j] = 1;
				} else {
					newMatrix[i][j] = 0;
				}
			}
		}
		return new Matrix(newMatrix);
	}
	
	public Matrix rref() {
		float[][] newMatrix = setDummyMatrix();
		boolean[] pivotTracker = new boolean[this.getDim()[3]];
		for(int i = 0; i < this.getDim()[3]; i++) {
			for(int j = 0; j < this.getDim()[2]; j++) {
					if( (!(pivotTracker[j]) && newMatrix[j][i] != 0)) { 
						pivotTracker = addPivot(pivotTracker, j);
						newMatrix = scaleRow(newMatrix, j, i);
						for(int k = 0; k < this.getDim()[2]; k++) {
							if(newMatrix[k][i] != 0 && k != j) { 
								newMatrix = subtractRows(newMatrix, j, k, i);
							}
						}
					}
			}
		}
		return new Matrix(newMatrix);
	}
		
		private float[][] subtractRows(float[][] matrix, int row, int rowTo, int col) {
			float placeHolder = matrix[rowTo][col];
			for(int i = 0; i < matrix[0].length; i++) {
				if(matrix[row][i] != 0) {
					matrix[rowTo][i] -= placeHolder*matrix[row][i]; 
				}
			}
			return matrix;
		}
		
		private float[][] scaleRow(float[][] matrix, int row, int col) {
			float placeHolder = matrix[row][col];
			float floatCheck = Math.abs(placeHolder - (int) placeHolder);
			if(floatCheck != 0 && floatCheck < .0001) {
				matrix[row][col] = 0;
				return matrix;
			}
			for(int i = 0; i < matrix[0].length; i++) {
				matrix[row][i] /= placeHolder;
			}
			return matrix;
		}
		
		private boolean[] addPivot(boolean[] pivots, int index) {
			pivots[index] = true;
			return pivots;
		}
	
	private float[][] setDummyMatrix(){
		float[][] dummyMatrix = new float[this.getDim()[2]][this.getDim()[3]];
		for(int i = 0; i < this.getDim()[2]; i++) {
			for(int j = 0; j < this.getDim()[3]; j++) {
				dummyMatrix[i][j] = this.get(i, j);
			}
		}
		return dummyMatrix;
	}
		
	public void powerIteration() {
		float[][] vectorValues = new float[this.getDim()[3]][1];
		vectorValues[0][0] = 1;
		
		for(int i = 1; i < this.getDim()[3]; i++) {
			vectorValues[i][0] = 0;
		}
		
		Matrix colVector = new Matrix(vectorValues);
		int prevValue = 0;
		float[] eigenVectorValues = new float[this.getDim()[3]];
		
		do {
			prevValue = (int) eigenValue;
			colVector = multiply(colVector);
			for(int i = 0; i < this.getDim()[3]; i++) {
				if(colVector.get(i, 0) > eigenValue) {
					eigenValue = colVector.get(i, 0);
				}
			}
			colVector.scale(1/eigenValue);
		} while((int) eigenValue != prevValue);
		
		for(int i = 0; i < this.getDim()[3]; i++) {
			eigenVectorValues[i] = colVector.get(i, 0);
		}
		
		eigenVector = new Vector(eigenVectorValues);
	}
	
	public float getEigenValue() {
		return eigenValue;
	}
	
	public Vector getEigenVector() {
		return eigenVector;
	}
		
	public Matrix clone() {
		float[][] matrixValues = new float[getDim()[2]][getDim()[3]];
		for(int i = 0; i < getDim()[2]; i++) {
			for(int j = 0; j < getDim()[3]; j++) {
				matrixValues[i][j] = this.get(i, j);
			}
		}
		return new Matrix(matrixValues);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Matrix m) {
			if(getDim()[2] != m.getDim()[2] || getDim()[3] != m.getDim()[3]) {
				return false;
			}
			for(int i = 0; i < m.getDim()[2]; i++) {
				for(int j = 0; j < m.getDim()[3]; j++) {
					if(this.get(i, j) != m.get(i, j)) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
		
}
