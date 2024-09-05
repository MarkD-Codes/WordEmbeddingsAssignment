package ie.atu.sw;

import static java.lang.System.out;

import java.io.*;

public class SimilaritySearcher {


	private static final int FEATURES_NUMBER = 50;
	
	//takes words out of GLOVE file, line by line, returning each word as a string
	private String getWord(String s) {
		
		String[] tempArray = s.split(",", 2);
		return tempArray[0];
	}
	
	//returns second part of string, split at instance of a comma
	private String getEmbeddingString (String s) {
		String[] tempArray = s.split(", ", 2);
		return tempArray[1];
	}
	
	//separates string into substrings and loads each into an array
	private String[] divideEmbeddingString(String s) {
		String[] tempArray = s.split(", " , FEATURES_NUMBER); //gets an array of 50 String elements
		return tempArray;
	}
	
	
	private String embeddingParser(String[] dividedEmbeddings, int i) { 
		//method takes a string and loads it into a given index of an array
			String s = dividedEmbeddings[i];
			return s;
	}
	
	//converts string into a double
	private double getDouble (String s) {
		double d = Double.parseDouble(s);
		return d;
	}
	
	
	
	private void getEmbedding (String s, int counter, double[][] embeddingsArray) {
		/*creates a double[][] containing all the embeddings at a row index
		 * corresponding to the same index of the word embeddings
		 */
		String t = getEmbeddingString(s);//String of all 50 embeddings
		String[] tempArray = divideEmbeddingString(t);//divided into 50 sub strings
		String temp = null;
		double embedding = 0.00;
		
		for(int i = 0; i <= FEATURES_NUMBER-1; i++) {
			temp = embeddingParser(tempArray, i);
			embedding = getDouble(temp);//take each embedding String and converts to double
			
			embeddingsArray[counter][i] = embedding;
		}
	}	
	
	
	
	private void arrayBuilder(String gloveFile, int embeddingsNumber, String[]wordsArray, double[][]embeddingsArray) {
		//Builds the two arrays, wordsArray[] and embeddingsArray[][]
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(gloveFile)));
	    //modified code from ElegyProcessor to take in file
		String line = null;	
	    String word = null;	
	    
	    
		for (int counter = 0; counter <= embeddingsNumber-1; counter++) {
	    	line = br.readLine();
	    	word = getWord(line);
	    	wordsArray[counter] = word;
	    	getEmbedding(line, counter, embeddingsArray);
	    
	    	}
		
			br.close();
		} catch (IOException e) {
			out.println("The specified embedding file cannot be found. Please check the file path and try again.");
			//e.printStackTrace();
		}
		
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
	
	
	
	private double dotProductCalculator(double[] da1, double[] da2) {
		/*computes dot product of two double arrays 
		 * Source: https://stackoverflow.com/questions/18775744/dot-product-with-arrays
		*/
		double sum = 0;
		for (int i = 0; i <= da1.length-1; i++) {
			sum = sum + (da1[i]*da2[i]);
		}
		
		return sum;
	}
	
	
	private double cosineDistanceCalculator(double[] da1, double[] da2) {
		//calculates similarity using cosine distance score 
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
			//selects method of calculation depending on user-set parameter
		return switch(calculatorChoice) {
		case 1 -> dotProductCalculator(da1, da2);
		case 2 -> cosineDistanceCalculator(da1, da2);
		default -> dotProductCalculator(da1, da2);
		};
		
	}
	
	//create 1D array from a given row of a 2D array - to calculate scores
	private double[] arrayDivider(double[][] da, int rowIndex, int rowLength) {
		double[] tempArray = new double[rowLength];
		
		for(int i = 0; i <= rowLength-1; i++) {
			tempArray[i] = da[rowIndex][i];
		}
		return tempArray;
		
	}
	
	
	private double[] similarityScoreArrayGenerator(double[][] da, int rowLength, int wordIndex, int calculatorChoice, int embeddingsNumber) {
		//generate an array of similarity scores, scores put at same index as original word/embeddings index in their own arrays
		double[] da2 = new double[embeddingsNumber];
		double[] v1 = arrayDivider(da, wordIndex, rowLength); 
		for (int i = 0; i <= embeddingsNumber-1; i++) {
			double[] v2 = arrayDivider(da, i, rowLength);
			da2[i] = similarityScoreGenerator(v1, v2, calculatorChoice);
		}
		return da2;
	}
	
	
	private int findIndexOfTopMatch(double[] arr) {
		/*Method returns index of highest value similarity score
		 * Array iterative search for max element found at https://www.geeksforgeeks.org/program-to-find-largest-element-in-an-array/
		 */
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
	
	
	
	public void similaritySearch (String textToSearch, int matchesToReturn, int calculatorChoice, String gloveFile, String outputFile, int embeddingsNumber) {		
		
		double[] similarityScoresArray = new double[embeddingsNumber];
		String[] wordsArray = new String[embeddingsNumber];
		double[][] embeddingsArray = new double[embeddingsNumber][FEATURES_NUMBER];

		
		try {
			FileWriter output = new FileWriter(outputFile);
			arrayBuilder(gloveFile, embeddingsNumber, wordsArray, embeddingsArray);
			int wordIndex = findWordIndex(wordsArray, textToSearch);
			//generate the text file containing the n top matches
			if(wordIndex != -1) {
				out.println("Your file " + outputFile + " has been generated. It contains " + matchesToReturn + " ranked results of words "
						+ "similar to the word: " + textToSearch);
				similarityScoresArray = similarityScoreArrayGenerator(embeddingsArray, FEATURES_NUMBER, wordIndex, calculatorChoice, embeddingsNumber);
				int[] topMatchesIndexArray = topResultsFinder(similarityScoresArray, matchesToReturn);
				output.write("Text Entered: " + textToSearch + "\n" + "\n" + "Similarity Search Results " + "\n" + "\n");
				
				for(int i=0; i <= matchesToReturn-1; i++) {
					double d = (similarityScoresArray[topMatchesIndexArray[i]]);
					String s = Double.toString(d);
					output.write("Match number " + (i+1) + "        " + wordsArray[topMatchesIndexArray[i]] + "        " + s  + "\n");
					}
			
			}else if (wordIndex == -1) out.println("Word not found. Please try another word!");
						
			output.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			}
		}
	
	
}
