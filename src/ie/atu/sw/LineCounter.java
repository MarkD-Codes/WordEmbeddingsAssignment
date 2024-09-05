package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LineCounter {
	
	private int lines = 0;
	
	public int fileLineCounter (String gloveFile) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(gloveFile)));
		
		while (br.readLine() != null) lines++;
		
		br.close();
		
		return lines;
	}

}
