package VectorSpace;

public class Tensor {
	
	private float[][][][] tensor;
	private int[] dimensions;
	private String tensorString = "";
	
	public Tensor(float[][][][] tensor) {
		this.tensor = tensor;
		setDimensions();
	}
	
	public Tensor(float[][] matrix) {
		tensor = new float[1][1][matrix.length][matrix[0].length]; 
		//for(int i = 0; i < matrix.length; i++) {
			//for(int j = 0; j < matrix[0].length; j++) {
				//tensor[0][0][i][j] = matrix[i][j];
			//}
		//}
		tensor[0][0] = matrix;
		setDimensions();
	}
	
	public Tensor(float[] vector) {
		tensor = new float[1][1][1][vector.length];
		for(int i = 0; i < vector.length; i++) {
			tensor[0][0][0][i] = vector[i];
		}
		setDimensions();
	}
	
	public float get(int tDim, int kDim, int row, int col) {
		return tensor[tDim][kDim][row][col];
	}
	
	public int[] getDim() {
		return dimensions;
	}
	
	public void add(Tensor t) {
		checkDimension(t);
		for(int i = 0; i < tensor.length; i++) {
			for(int j = 0; j < tensor[0].length; j++) {
				for(int k = 0; k < tensor[0][0].length; k++) {
					for(int l = 0; l < tensor[0][0][0].length; l++) {
						tensor[i][j][k][l] += t.get(i, j, k, l);
					}
				}
			}
		}
	}
	
	public void subtract(Tensor t) {
		checkDimension(t);
		for(int i = 0; i < tensor.length; i++) {
			for(int j = 0; j < tensor[0].length; j++) {
				for(int k = 0; k < tensor[0][0].length; k++) {
					for(int l = 0; l < tensor[0][0][0].length; l++) {
						tensor[i][j][k][l] -= t.get(i, j, k, l);
					}
				}
			}
		}
	}
	
	public void scale(float scalar) {
		for(int i = 0; i < tensor.length; i++) {
			for(int j = 0; j < tensor[0].length; j++) {
				for(int k = 0; k < tensor[0][0].length; k++) {
					for(int l = 0; l < tensor[0][0][0].length; l++) {
						tensor[i][j][k][l] *= scalar;
					}
				}
			}
		}
	}
	
	public void hadamardProduct(Tensor t) {
		checkDimension(t);
		for(int i = 0; i < tensor.length; i++) {
			for(int j = 0; j < tensor[0].length; j++) {
				for(int k = 0; k < tensor[0][0].length; k++) {
					for(int l = 0; l < tensor[0][0][0].length; l++) {
						tensor[i][j][k][l] *= t.get(i, j, k, l);
					}
				}
			}
		}
	}
	
	public void checkDimension(Tensor t) {
		if(dimensions[0] != t.dimensions[0] || dimensions[1] != t.dimensions[1] || dimensions[2] != t.dimensions[2] || dimensions[3] != t.dimensions[3]) {
			throw new IllegalArgumentException("Dimensions do not match");
		}
	}
	
	private void setDimensions() {
		dimensions = new int[4];
		dimensions[0] = tensor.length;
		dimensions[1] = tensor[0].length;
		dimensions[2] = tensor[0][0].length;
		dimensions[3] = tensor[0][0][0].length;
	}
	
	public String toString() {
		for(int i = 0; i < getDim()[0]; i++) {
			for(int j = 0; j < getDim()[1]; j++) {
				for(int k = 0; k < getDim()[2]; k++) {
					tensorString += "< ";
					for(int l = 0; l < getDim()[3]; l++) {
						tensorString += get(i, j, k, l) + " ";
					}
					tensorString += ">\n";
				}
				if(getDim()[1] > 1) {
					tensorString += "\n";
				}
			}
			if(getDim()[0] > 1) {
				tensorString += "-\n";
			}
		}
		return tensorString;
	}
	
	public int compareTo(Tensor t) {
		if(this.dimensions[0] > t.dimensions[0] || (this.dimensions[0] == t.dimensions[0] && this.dimensions[1] > t.dimensions[1]) || 
				(this.dimensions[0] == t.dimensions[0] && this.dimensions[1] == t.dimensions[1] && this.dimensions[2] > t.dimensions[2]) || 
				(this.dimensions[0] == t.dimensions[0] && this.dimensions[1] == t.dimensions[1] && this.dimensions[2] == t.dimensions[2] && this.dimensions[3] > t.dimensions[3])) {
			return 1;
		} else if(dimensions[0] == t.dimensions[0] && dimensions[1] == t.dimensions[1] && dimensions[2] == t.dimensions[2] && dimensions[3] == t.dimensions[3]) {
			return 0;
		} else {
			return -1;
		}
	}
	
	public Tensor clone() {
		return new Tensor(this.tensor);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Tensor t) {
			if(dimensions[0] == t.dimensions[0] && dimensions[1] == t.dimensions[1] && dimensions[2] == t.dimensions[2] && dimensions[3] == t.dimensions[3]) {
				for(int i = 0; i < tensor.length; i++) {
					for(int j = 0; j < tensor[0].length; j++) {
						for(int k = 0; k < tensor[0][0].length; k++) {
							for(int l = 0; l < tensor[0][0][0].length; l++) {
								if(tensor[i][j][k][l] != t.get(i, k, i, l)) {
									return false;
								}
							}
						}
					}
				}
				return true;
			}
			return false;
		}
		return false;
	}

}
