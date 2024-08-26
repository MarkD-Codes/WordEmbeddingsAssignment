package ie.atu.sw;

import static java.lang.System.out;

import java.io.*;
import java.lang.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.math.*;

public class SimilaritySearcher {

	private static final int EMBEDDINGS_NUMBER = 59_602;
	private static final int FEATURES_NUMBER = 50;
	
	String[] wordsArray = new String[EMBEDDINGS_NUMBER];
	double[][] embeddingsArray = new double[EMBEDDINGS_NUMBER][FEATURES_NUMBER];
	
	
	//separates word from the embeddings, line by line, returning the word as a string
	
	private String getWord(String s) {
		
		String[] tempArray = s.split(",", 2);
		return tempArray[0];
	}
	
	//returns fifty embeddings from the word in a line as one string
	private String getEmbeddingString (String s) {
		String[] tempArray = s.split(", ", 2);
		return tempArray[1];
	}
	
	//separates string into substrings and loads each into an array
	private String[] divideEmbeddingString(String s) {
		String[] tempArray = s.split(", " , FEATURES_NUMBER); //gets an array of 50 String elements
		return tempArray;
	}
	
	//reads an array of x elements and returns single String from the i'th index
	private String embeddingParser(String[] dividedEmbeddings, int i) { 
			String s = dividedEmbeddings[i];
			return s;
	}
	
	//converts string into a double
	private double getDouble (String s) {
		
		double d = Double.parseDouble(s);
		return d;
	}
	
	private void getEmbedding (String s, int counter) {
		
		String t = getEmbeddingString(s);
		String[] tempArray = divideEmbeddingString(t);
		String temp = null;
		double embedding = 0.00;
		
		for(int i = 0; i <= FEATURES_NUMBER-1; i++) {
			temp = embeddingParser(tempArray, i);
			embedding = getDouble(temp);
			
			embeddingsArray[counter][i] = embedding;
		}
	}
	
	//Builds the two arrays, wordsArray[] and embeddingsArray[][]
	
	private void arrayBuilder(String gloveFile) {
		
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(gloveFile)));
	    //modified code from ElegyProcessor to take in file
		String line = null;	
	    String word = null;
	    
		for (int counter = 0; counter <= EMBEDDINGS_NUMBER-1; counter++) {
	    	line = br.readLine();
	    	word = getWord(line);
	    	wordsArray[counter] = word;
	    	getEmbedding(line, counter);
	    
	    	}
		
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*test block, to test that arrays are being filled correctly
		for(int i = 1; i <= 5; i++) {
			
		System.out.println(wordsArray[i*2]);
		System.out.println(embeddingsArray[i][0]);
		
		}
		 */
	}
	
	//Search words array and return index of user input word, if present. 
	private int findWordIndex(String[] sa, String s) {
		
		int index = 0;	
		while (index < sa.length) {
			if (sa[index].equalsIgnoreCase(s)) {
				return index;
			}else {
				index++;
			}
		}
		return -1;
	}
	
	/*computes dot product of two double arrays 
	 * Source: https://stackoverflow.com/questions/18775744/dot-product-with-arrays
	*/
	private double dotProductCalculator(double[] da1, double[] da2) {
		
		double sum = 0;
		for (int i = 0; i <= da1.length-1; i++) {
			sum = sum + (da1[i]*da2[i]);
		}
		
		return sum;
	}
	
	//calculates similarity using cosine distance score 
	private double cosineDistanceCalculator(double[] da1, double[] da2) {
		double distance = 0;
		double sumOfSquaresDa1 = 0;
		double sumOfSquaresDa2 = 0;
		double dotProduct = dotProductCalculator(da1, da2);
		for(int i = 0; i <= da1.length-1; i++) {
			double j = (da1[i]*da1[i]);
			sumOfSquaresDa1 = sumOfSquaresDa1+ j; 
			
			double k = (da2[i]*da2[i]);
			sumOfSquaresDa2 = sumOfSquaresDa2+ k; 
		}
		
		distance = dotProduct/(Math.sqrt((sumOfSquaresDa1)*(sumOfSquaresDa2)));
		return distance;
		
	}
	
	private double similarityScoreGenerator(double[] da1, double[] da2, int calculatorChoice) {
		
		return switch(calculatorChoice) {
		case 1 -> dotProductCalculator(da1, da2);
		case 2 -> cosineDistanceCalculator(da1, da2);
		default -> dotProductCalculator(da1, da2);
		};
		
	}
	
	//create 1D array from a given row of a 2D array
	private double[] arrayDivider(double[][] da, int rowIndex, int rowLength) {
		double[] tempArray = new double[rowLength];
		
		for(int i = 0; i <= rowLength-1; i++) {
			tempArray[i] = da[rowIndex][i];
		}
		return tempArray;
		
	}
	
	//generate an array of similarity scores
	private double[] similarityScoreArrayGenerator(double[][] da, int rowLength, int wordIndex, int calculatorChoice) {
		
		double[] da2 = new double[EMBEDDINGS_NUMBER];
		double[] v1 = arrayDivider(da, wordIndex, rowLength); 
		for (int i = 0; i <= EMBEDDINGS_NUMBER-1; i++) {
			double[] v2 = arrayDivider(da, i, rowLength);
			da2[i] = similarityScoreGenerator(v1, v2, calculatorChoice);
		}
		return da2;
	}
	
	//Array iterative search for max element found at https://www.geeksforgeeks.org/program-to-find-largest-element-in-an-array/
	private int findIndexOfTopMatch(double[] arr) {
	        if (arr == null || arr.length == 0) {
	            return -1; // Return -1 if the array is empty
	        }
	        int indexOfTopMatch = 0;
	        double maxValue = arr[0];

	        for (int i = 1; i < arr.length; i++) {
	            if (arr[i] > maxValue) {
	                maxValue = arr[i];
	                indexOfTopMatch = i;
	            }
	        }
	        return indexOfTopMatch;
	}
	
	private int[] topResultsFinder(double[] arr, int matchesToReturn) {
		double[] tempArray = arr.clone();
		int indexOfTopMatch = 0;
		int[] topResultsIndices = new int[matchesToReturn + 1];
		
		for (int i = 0; i <= matchesToReturn-1; i++) {
			indexOfTopMatch = findIndexOfTopMatch(tempArray);
			topResultsIndices[i] = indexOfTopMatch; 
			tempArray[indexOfTopMatch] = 0.00;
		}
		
		return topResultsIndices;
	}
	
	
	
	public void similaritySearch (String textToSearch, int matchesToReturn, int calculatorChoice, String gloveFile, String outputFile) {		
		
		double[] similarityScoresArray = new double[EMBEDDINGS_NUMBER];
		
		try {
			FileWriter output = new FileWriter(outputFile);
			arrayBuilder(gloveFile);
			int wordIndex = findWordIndex(wordsArray, textToSearch);
			if(wordIndex != -1) {
				out.println("Your file " + outputFile + " has been generated. It contains " + matchesToReturn + " ranked results of words "
						+ "similar to the word: " + textToSearch);
				similarityScoresArray = similarityScoreArrayGenerator(embeddingsArray, FEATURES_NUMBER, wordIndex, calculatorChoice);
				int[] topMatchesIndexArray = topResultsFinder(similarityScoresArray, matchesToReturn);
				output.write("Text Entered: " + textToSearch + "\n" + "\n" + "Similarity Search Results " + "\n" + "\n");
				
				
				for(int i=1; i <= matchesToReturn; i++) {
					double d = (similarityScoresArray[topMatchesIndexArray[i]]);
					String s = Double.toString(d);
					output.write("Match number " + (i) + "        " + wordsArray[topMatchesIndexArray[i]] + "        " + s  + "\n");
					}
				//System.out.println(wordsArray[indexOfMax]);
				//System.out.println(similarityScoresArray[indexOfMax]);
			
			}else if (wordIndex == -1) out.println("Word not found. Please try another word!");
						
			output.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			}
		}
	
	
}
