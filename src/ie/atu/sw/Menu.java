package ie.atu.sw;

import static java.lang.System.*;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
	private Scanner s;
	private boolean keepRunning = true;
	private int matchesToReturn = 10;
	private String textToSearch = "";
	private String embeddingFile = "./word-embeddings.txt";
	private String outputFile = "result.txt";
	private int calculatorChoice = 1;
	private int embeddingsNumber = 0;

	
	
	public Menu() {
		s = new Scanner(System.in);
	}
	
	//Checks to see if user input is an integer, when navigating through menus and returns valid input
	private int InputChecker(String s) {
	        int validInt = 0;
	        boolean isValid = false;

	        // Keep asking for input until a valid integer is entered
	        if (!isValid) {
	            try {
	                validInt = Integer.parseInt(s);
	                isValid = true; // If no exception, input is valid
	            } catch (NumberFormatException e) {
	                // Catch the exception and prompt the user to input again
	                System.out.println("Invalid input. Please input a number to choose a menu option");  
	            }
	        }
	        return validInt; // Return the valid integer
	    }
	
	//User can set embedding file 
	private void specifyEmbeddingFile () throws Exception {
		
		out.println("Specify Embeddings file. Be sure to write full file-path correctly!>");
		
		String t = s.next();
		embeddingFile = t;
		
		out.println("You have set the embeddings file name as: " + embeddingFile);
		out.println("Is this correct?");
		out.println("(1) Yes! Return to the main menu!");
		out.println("(2) No! Change file name");
		
		int input = InputChecker(s.next());
		int choice = input;
		
		switch (choice) {
		case 1 -> backToMainMenu(); 
		case 2 -> specifyEmbeddingFile();
		default -> {out.println("[Error] Invalid Selection");
					specifyEmbeddingFile();
			}
		}
		
	}
	
	//Path to write output to
	private void specifyOutputFile() throws Exception {
		
		out.println("Specify Output file name>");
		String t = s.next();
		outputFile = t;
		
		out.println("You have set the output file name as: " + outputFile);
		out.println("Is this correct?");
		out.println("(1) Yes! Return to the main menu!");
		out.println("(2) No! Change file name");
		
		int input = InputChecker(s.next());
		int choice = input;
		switch (choice) {
		case 1 -> backToMainMenu(); 
		case 2 -> specifyOutputFile();
		default -> {out.println("[Error] Invalid Selection");
					specifyOutputFile();
			}
		}
		
	}
	
	private String hasMoreThanOneWord(String input) {
        // Trims the user String to remove leading/trailing spaces and split by spaces
		//Search will be performed on first word in given sentence
        String[] words = input.trim().split("\\s+");
        String s = words[0];
        // Generates new Strings that is just the first word in given String
        return s;
    }
	
	private void textAnalysis() throws Exception {
		
		out.println("Enter the word or text you want to perform the similarity search on>");
		
		String t = s.next();
		String u = hasMoreThanOneWord(t);
		textToSearch = u.toLowerCase();
		
		LineCounter lc = new LineCounter();
		SimilaritySearcher ss = new SimilaritySearcher();
		
		try {
			embeddingsNumber = lc.fileLineCounter(embeddingFile);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		ss.similaritySearch(textToSearch, matchesToReturn, calculatorChoice, embeddingFile, outputFile, embeddingsNumber);
		out.println("Would you like to perform another search?");
		out.println("(1) Return to Main Menu to search again");
		out.println("(2) Exit the programme");
		s.nextLine();
		
		int input = InputChecker(s.next());
		int choice = input;
		
		switch (choice) {
		case 1 -> backToMainMenu(); 
		case 2 -> keepRunning = false;
		default -> out.println("[Error] Invalid Selection");
		}
		
	}
	
	//Default matches shown is 10
	private void setMatchNumber() throws Exception {
		out.println("Set the number of top matches you want shown in the report (default is 10) > ");
		matchesToReturn = s.nextInt();
		out.println("The Report Will Show You The Top " + matchesToReturn + "Matches");
		configOptions();
		
	}
	
	
	private void setAnalysisMethod() throws Exception {
		out.println("**************************************");
		out.println("********Select Analysis Method********");
		out.println("**************************************");
		out.println("(1) Dot Product Search (Default)");
		out.println("(2) Cosine Distance Search");
		out.println("(3) Back to Options Menu");
		
		int input = InputChecker(s.next());
		int choice = input;
		switch (choice) {
		case 1 -> {calculatorChoice = 1; 
				out.println("You have set Analysis Method to Dot Product");
				configOptions();
				}
		case 2 -> {calculatorChoice = 2; 
				out.println("You have set Analysis Method to Cosine Distance");
				configOptions();
				}
		case 3 -> configOptions();
		default -> {out.println("[Error] Invalid Selection");
					setAnalysisMethod();
					}
			}		
		
	}

private void backToMainMenu() throws Exception {
	start();
	
}
	
	private void configOptions() throws Exception {
		out.println("**************************************");
		out.println("********Configure Options Menu********");
		out.println("**************************************");
		out.println("(1) Set Number of High Scoring Matches to Show in the Report");
		out.println("(2) Select Method to Use to Analyse Your Text");
		out.println("(3) Back to Main Menu");
		
		int input = InputChecker(s.next());
		int choice = input;  
		switch (choice) {
		case 1 -> setMatchNumber(); 
		case 2 -> setAnalysisMethod();
		case 3 -> backToMainMenu();
		default -> {out.println("[Error] Invalid Selection");
					configOptions();
						}
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
		System.out.println("(1) Specify Embedding File (default: ./word-embeddings.txt)");
		System.out.println("(2) Specify an Output File (default: ./out.txt)");
		System.out.println("(3) Enter a Word or Text and Run the Similarity Search");
		System.out.println("(4) Configure Options");
		System.out.println("(5) Quit");
	
	}
	
	
	public void start() throws Exception { //Main Menu selection handling
		
		while(keepRunning) {
			showOptions();
			int input = InputChecker(s.next());//checks if int
			int choice = input; 
		
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
