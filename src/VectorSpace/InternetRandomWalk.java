package VectorSpace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class InternetRandomWalk {
	
	private Map<Integer, Set<Integer>> internetMap = new TreeMap<>();
	private Random rand = new Random();
	private Matrix internetProbabilities;
	private int numberOfSites;
	private int iterationsOfSim;
	private List<Integer> moveList = new ArrayList<>();
	
	public InternetRandomWalk(int numberOfSites) {
		this.numberOfSites = numberOfSites;
		createInternet(numberOfSites);
		setInternetMatrix(numberOfSites);
	}

	private void createInternet(int numberOfSites) {
		Set<Integer> setOfSites = new HashSet<>();
		int randAmount;
		int randSite;
		for(int i = 0; i < numberOfSites; i++) {
			randAmount = rand.nextInt(numberOfSites-1)+1;
			for(int j = 0; j < randAmount; j++) {
				randSite = rand.nextInt(numberOfSites);
				if((randSite == i && randSite != 0) || (randSite == i && randSite == numberOfSites)) { // So it doesn't link to itself
					randSite--;
				} else if(randSite == i && randSite == 0) {
					randSite++;
				}
				setOfSites.add(randSite);
			}
			internetMap.put(i, setOfSites);
			setOfSites = new HashSet<>();
		}
	}
	
	private void setInternetMatrix(int numberOfSites) {
		float[][] internetValues = new float[numberOfSites][numberOfSites];
		float[][] randValues = new float[numberOfSites][numberOfSites];
		float probability = 0;
		for(int i = 0; i < numberOfSites; i++) {
			for(int j = 0; j < numberOfSites; j++) {
				if(internetMap.get(j).contains(i)) {
					probability++;
				}
				randValues[i][j] = (float) 0.15 / numberOfSites;
			}
			probability = 1/probability;
			for(int k = 0; k < numberOfSites; k++) {
				if(internetMap.get(k).contains(i)) {
					internetValues[k][i] = probability;
				} else {
					internetValues[k][i] = 0;
				}
			}
			probability = 0;
		}
		Matrix randJump = new Matrix(randValues);
		internetProbabilities = new Matrix(internetValues);
		internetProbabilities.scale(.85f);
		internetProbabilities.add(randJump);
	}
	
	public Map<Integer, Set<Integer>> getInternet() {
		return internetMap;
	}
	
	public Matrix getMatrix() {
		return internetProbabilities;
	}
	
	public List<Integer> getWebSim() {
		return moveList;
	}
	
	public Vector estimatedProbabilityVector() {
		Matrix eigenValues = internetProbabilities.getIndentity(numberOfSites);
		eigenValues.scale(-1f);
		internetProbabilities.add(eigenValues);
		Matrix rrefProb = internetProbabilities.rref();
		float[] eigenVector = new float[numberOfSites];
		for(int i = 0; i < numberOfSites; i++) {
			eigenVector[i] = -rrefProb.get(i, numberOfSites-1);
		}
		return new Vector(eigenVector); 
	}
	
 	public Vector simulatedProbabilityVector() {
		float[] pVector = new float[numberOfSites];
		for(int i = 0; i < iterationsOfSim; i++) {
			pVector[moveList.get(i)-1]++;
		}
		Vector probVector = new Vector(pVector);
		probVector.scale((float) 1/iterationsOfSim);
		return probVector;
	}
	
	public void simulateWebSurfing(int iterations) {
		resetSim();
		iterationsOfSim = iterations;
		int position = rand.nextInt(numberOfSites);
		int setIt = 0;
		int randNum;
		int randJump;
		moveList.add(position+1);
		Iterator it;
		for(int i = 0; i < iterations; i++) {
			randJump = rand.nextInt(99);
			if(randJump <= 84) {
				it = internetMap.get(position).iterator();
				randNum = rand.nextInt(internetMap.get(position).size());
				while(setIt <= randNum) {
					if(setIt == randNum) {
						position = (int) it.next();
					} else {
						it.next();
					}
					setIt++;
				}
				moveList.add(position+1);
				setIt = 0;
			} else {
				position = rand.nextInt(numberOfSites);
				moveList.add(position+1);
			}
		}
	}
	
	private void resetSim() {
		moveList.clear();
	}
	
	public String toString() {
		Vector estVector = estimatedProbabilityVector();
		Vector simVector = simulatedProbabilityVector();
		float[] estValues = setDummyVector(estVector);
		float[] simValues = setDummyVector(simVector);
		float[] estSortedValues = estValues.clone();
		float[] simSortedValues = simValues.clone();
		Arrays.sort(estSortedValues);
		Arrays.sort(simSortedValues);
		String randWalk = "Hello User, Welcome to the Random Walk Simulation!\nHere we randomly generate an internet to simulate Google's billion dollar idea for their page rank system.";
		randWalk += "\n\nThe Eigen Vector, or the estimated probability for page popularity, is: " + estVector;
		randWalk += "\nThe random walk over the internet, or the simulated probability for page popularity, is: " + simVector;
		randWalk += "\nThe most popular sites are: ";
		int i = 0;
		int estSite = 0;
		int simSite = 0;
		while(i < 5 && i < estValues.length) {
			for(int j = 0; j < estValues.length; j++) {
				if(estSortedValues[estSortedValues.length-(1+i)] == estValues[j]) {
					estSite = j;
				}
				if(simSortedValues[simSortedValues.length-(1+i)] == simValues[j]) {
					simSite = j;
				}
			}
			estSite++;
			simSite++;
			i++;
			randWalk += "\nEstimated Number " + i + ": Site " + estSite + "\nSimulated Number " + i + ": Site " + simSite;
		}
		return randWalk;
	}
	
	private float[] setDummyVector(Vector v){
		float[] dummyVector = new float[v.getDim()[3]];
		for(int i = 0; i < v.getDim()[3]; i++) {
			dummyVector[i] = v.get(i); 
		}
		return dummyVector;
	}

}
