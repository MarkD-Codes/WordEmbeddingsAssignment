package ie.atu.sw;

import static java.lang.System.*;
import java.util.Scanner;
import java.io.*;

public class Menu {
	private Scanner s;
	private boolean keepRunning = true;
	private int matchesToReturn = 10;
	private String textToSearch = "";
	private String embeddingFile = "./word-embeddings.txt";
	private String outputFile = "result.txt";
	
	
	public Menu() {
		s = new Scanner(System.in);
	}
	
	
	
	//User can set embedding file 
	private void specifyEmbeddingFile () {
		
		out.println("Specify Embeddings file>");
	}
	
	//Path to write output to
	private void specifyOutputFile() {
		
		out.println("Specify Output file name>");
		
		
	}
	
	
	private void textAnalysis() {
		
		out.println("Enter the word or text you want to perform the similarity search on>");
		textToSearch = s.next();
		
		SimilaritySearcher ss = new SimilaritySearcher();
		try {
			FileWriter output = new FileWriter(outputFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(embeddingFile)));
		    //modified code from ElegyProcessor to take in file
			
			String line = null;	
		    for (int i=0; i<= matchesToReturn; i++) {
		    	line = br.readLine();
		    	output.write(line + "\n");
		    	
		    	/*
		    	 * now can read the embedding file, line by line...and outputs a result to a .txt file
		    	 * just need to write loop that will go line by line and parse each line into two arrays
		    	 * BUT I need to put all these methods into SimilaritySearcher class
		    	 */
		    	
		    	
		    }
		    
		    /*
		     * TODO Write method to parse file into two arrays
		     * TODO Write method(s) to perform operations on embedding 
		     * 
		     */
			
		    output.write(ss.toString());
		    
			br.close();
			output.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ss.similaritySearch(textToSearch, matchesToReturn, embeddingFile);
		
		
	
	}
	
	//Default matches shown is 10
	private void setMatchNumber() {
		out.println("Set the number of top matches you want shown in the report (default is 10) > ");
		matchesToReturn = s.nextInt();
		out.println("The Report Will Show You The Top " + matchesToReturn + "Matches");
		configOptions();
		
	}
	
	//Default TBD
private void setAnalysisMethod() {
		
		out.println("You Have Selected to Analyse the Text Using the Method " + "Method Name");
		configOptions();
		
	}

private void backToMainMenu() {
	showOptions();
	
}
	
	private void configOptions() {
		out.println("**************************************");
		out.println("********Configure Options Menu********");
		out.println("**************************************");
		out.println("(1) Set Number of High Scoring Matches to Show in the Report");
		out.println("(2) Select Method to Use to Analyse Your Text");
		out.println("(3) Back to Main Menu");
		
		int choice = Integer.parseInt(s.next());
		switch (choice) {
		case 1 -> setMatchNumber(); 
		case 2 -> setAnalysisMethod();
		case 3 -> backToMainMenu();
		default -> out.println("[Error] Invalid Selection");
		}
	}
	
	
	private void showOptions() {
		System.out.println(ConsoleColour.GREEN);
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*          Similarity Search with Word Embeddings          *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Embedding File");
		System.out.println("(2) Specify an Output File (default: ./out.txt)");
		System.out.println("(3) Enter a Word or Text and Run the Similarity Search");
		System.out.println("(4) Configure Options");
		System.out.println("(5) Quit");
	
	}
	
	//TODO : Write method so file is parsed and loaded into two arrays on start up- or better to do after option 3 is selected? 
	//New class called "BuildArrays" needed?
	
	public void start() {
		
		while(keepRunning) {
			showOptions();
			
			int choice = Integer.parseInt(s.next()); //Allow user to select menu option by inputting number
			switch (choice) {
			case 1 -> specifyEmbeddingFile(); 
			case 2 -> specifyOutputFile();
			case 3 -> textAnalysis();
			case 4 -> configOptions();
			case 5 -> keepRunning = false;
			default -> out.println("[Error] Invalid Selection");
			}
		}
		out.println("[INFO] Exiting...Bye!");
		
		
	}


}
