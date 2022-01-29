package lesson10.labs.prob2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class NewFileIO {
	private static final Logger LOG = Logger.getLogger(NewFileIO.class.getName());
	public final static String FILE_LOCATION = "src/lesson10/labs/prob2/newOutput.txt";
	public final static String TO_PRINT = "This is the string to print to file.";
	
	void writeText(String filename, String text) {

		try (PrintWriter writer=new PrintWriter(new FileWriter(filename))){
			writer.print(text);
		} catch(IOException e) {
			LOG.warning("Main exception: " + e.getMessage());
			List<Throwable> suppressed = Arrays.asList(e.getSuppressed());	
			suppressed.forEach(except -> LOG.warning("Suppressed message: " 
			                                         + except.getMessage()));
		} 
	}
	
	public static void main(String[] args) {
		NewFileIO fileIO = new NewFileIO();
	    fileIO.writeText(FILE_LOCATION, TO_PRINT);
		
	}

}
