package ie.atu.sw;

import static java.lang.System.*;
import java.util.Scanner;


public class Menu {
	private Scanner s;
	private boolean keepRunning = true;
	private int matchesToReturn = 10;
	public String textToSearch = "";
	
	public Menu() {
		s = new Scanner(System.in);
	}
	
	
	
	//User can set embedding file 
	private void specifyEmbeddingFile () {
		
		out.println("Specify Embedding file>");
	}
	
	//Path to write output to
	private void outputFile() {
		
		out.println("Specify Output file name>");
		
	}
	
	
	private void textAnalysis() {
		
		out.println("Enter the word or text you want to perform the similarity search on>");
		textToSearch = s.next();
		SimilaritySearcher ss = new SimilaritySearcher();
		
		ss.similaritySearch(textToSearch, matchesToReturn);
	
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
	
	public void start() {
		while(keepRunning) {
			showOptions();
			
			int choice = Integer.parseInt(s.next()); //Allow user to select menu option by inputting number
			switch (choice) {
			case 1 -> specifyEmbeddingFile(); 
			case 2 -> outputFile();
			case 3 -> textAnalysis();
			case 4 -> configOptions();
			case 5 -> keepRunning = false;
			default -> out.println("[Error] Invalid Selection");
			}
		}
		out.println("[INFO] Exiting...Bye!");
		
		
	}


}
