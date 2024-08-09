package ie.atu.sw;

import static java.lang.System.*;
import java.util.Scanner;


public class Menu {
	private Scanner s;
	private boolean keepRunning = true;
	
	public Menu() {
		s = new Scanner(System.in);
	}
	
	public void start() {
		while(keepRunning) {
			showOptions();
			
			int choice = Integer.parseInt(s.next()); //Allow user to select menu option by inputting number
			switch (choice) {
			case 1 -> specifyEmbeddingFile(); 
			case 2 -> outputFile();
			case 3 -> textToAnalyse();
			case 4 -> configOptions();
			case 5 -> keepRunning = false;
			default -> out.println("[Error] Invalid Selection");
			}
		}
		out.println("[INFO] Exiting...Bye!");
		
		
	}
	
	private void specifyEmbeddingFile () {
		
		out.println("Specify Embedding file>");
	}
	
	
	private void outputFile() {
		
		out.println("Specify Output file name>");
		
	}
	
	
	private void textToAnalyse() {
		
		out.println("Enter the word or text you wish to analyse>");
		
	}
	
	private void setMatchNumber() {
		
		out.println("The Report Will Show You The Top" + "X " + "Matches");
		configOptions();
		
	}
	
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
		System.out.println("(3) Enter a Word or Text");
		System.out.println("(4) Configure Options");
		System.out.println("(5) Quit");
	
	}

}
