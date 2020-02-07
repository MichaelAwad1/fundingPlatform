package dbadapter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<String> supporters = new ArrayList<String>();
		supporters.add("First");
		supporters.add("Second");
		supporters.add("Third");
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("Test.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    for (String line : supporters) {
	        writer.println(line);
	    }
	    writer.close();
	}

}
