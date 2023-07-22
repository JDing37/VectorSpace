package VectorSpace;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class VectorTester {
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		/*
		 * Demonstration of Tensor Reader class:
		 * Only use if with the CORRECT path name of the .txt file 
		 * This class reads data from a .txt file and stores it into a corresponding tensor
		 * It was created so the user does not need to enter all data into multidimensional arrays by hand
		 */
		
		TensorReader tr = new TensorReader("/Users/josephdingillo/eclipse-workspace/Comp271/src/VectorSpace/Tensors.txt");
		Map<String, Tensor> tensors = tr.readFile();
		Set<String> nameOftensors = tensors.keySet();
		Iterator<String> it = nameOftensors.iterator();
		System.out.println("Some example Vectors are: ");
		while(it.hasNext()) {
			System.out.println(tensors.get(it.next()));
		}
		
		/*
		 * Demonstration of Tensor hierarchy:
		 * This hierarchy is a compilation of numerical methods
		 * It is used to solve different problems by using methods of linear algebra
		 */
		
		float[][] matrixValues = {{7, 9}, {9, 7}};
		Matrix randomMatrix = new Matrix(matrixValues);
		randomMatrix.powerIteration();
		
		System.out.println("This is the power iteration technique to approximate dominant Eigen Values/Vectors");
		System.out.println("For the matrix\n" + randomMatrix + "The value is 16 vector is < 1, 1>");
		System.out.println("The approximation technique gives");
		System.out.println(randomMatrix.getEigenValue());
		System.out.println(randomMatrix.getEigenVector());
		
		/*
		 * Demonstration of Random Walk:
		 * This class uses Google's original implementation of the page rank system
		 * I created this to show applications of linear algebra
		 */
		
		InternetRandomWalk rw = new InternetRandomWalk(1000);
		System.out.println(rw.getMatrix());
		rw.simulateWebSurfing(1000000);
		
		System.out.println(rw);
	}
	

}
