package VectorSpace;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class TensorReader {
	
	private Map<String, Tensor> tensorMap = new TreeMap<>();
	private Scanner user;
	
	public TensorReader(String fileName) {
		try {
			user = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Tensor> readFile() {

		float[][] matrix;
		float[] vector;
		
		int totalVectors = 0;
		int totalMatrices = 0;
		
		int i = 0;
		int j = 0;
		
		while(user.hasNext()) {
			if(user.next().equalsIgnoreCase("*")) {
				if(user.hasNextInt()) {
					int a = user.nextInt();
					vector = new float[a];
					while(user.hasNextInt()) {
						vector[i] = user.nextFloat();
						i++;
					}
					totalVectors++;
					String vectorString = "Vector ID: " + totalVectors;
					tensorMap.put(vectorString, new Vector(vector));
					i = 0;
					vector = null;
				}
				
			}
		}
		return tensorMap;
	}
	
}
