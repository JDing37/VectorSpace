package VectorSpace;

public class Vector extends Matrix {
	
	private float magnitude;
	
	public Vector(float[] vector) {
		super(vector);
	}
	
	public float get(int index) {
		return super.get(0, 0, 0, index);
	}
	
	public float magnitude() {
		for(int i = 0; i < getDim()[3]; i++) {
			magnitude += (this.get(i) * this.get(i));
		}
		magnitude = (float) Math.sqrt(magnitude);
		return magnitude;
	}
	
	public Vector unitVector() {
		float[] vectorValues = new float[getDim()[3]];
		for(int i = 0; i < getDim()[3]; i++) {
			vectorValues[i] = (1/this.magnitude()) * this.get(i);
		}
		return new Vector(vectorValues);
	}
	
	public float dotProduct(Vector v) {
		checkDimension(v);
		float dotProduct = 0;
		for(int i = 0; i < getDim()[3]; i++) {
			dotProduct += (this.get(i) * v.get(i));
		}
		return dotProduct;
	}
	
	public float scalarProjection(Vector v) {
		checkDimension(v);
		return dotProduct(v)/v.magnitude();
	}
	
	public Vector vectorProjection(Vector v) {
		checkDimension(v);
		float[] vectorValues = new float[getDim()[3]];
		float vectorProjection = dotProduct(v) / (v.magnitude() * v.magnitude());
		for(int i = 0; i < getDim()[3]; i++) {
			vectorValues[i] = vectorProjection * v.get(i);
		}
		return new Vector(vectorValues);
	}
	
	public float angleBetweenVectors(Vector v) {
		checkDimension(v);
		return (float) Math.acos((dotProduct(v))/(this.magnitude()*v.magnitude()));
	}
	
	public boolean equals(Object o) {
		if(o instanceof Vector v) {
			if(this.getDim()[3] != v.getDim()[3]) {
				return false;
			}
			for(int i = 0; i < v.getDim()[3]; i++) {
				if(this.get(i) != v.get(i)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public Vector clone() {
		float[] vectorValues = new float[getDim()[3]];
		for(int i = 0; i < vectorValues.length; i++) {
			vectorValues[i] = this.get(i);
		}
		return new Vector(vectorValues);
	}
	
	
}
