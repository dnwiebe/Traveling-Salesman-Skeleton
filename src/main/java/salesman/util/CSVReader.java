package salesman.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CSVReader {
	
	public List<List<String>> read (String filename) {
		try {
			return unfriendlyRead (filename);
		}
		catch (IOException e) {
			throw new IllegalStateException (e);
		}
	}
	
	public List<List<String>> read (InputStream istr) {
		return read (new InputStreamReader (istr));
	}
	
	public List<List<String>> read (Reader rdr) {
		return read (new BufferedReader (rdr));
	}
	
	public List<List<String>> read (BufferedReader rdr) {
		try {
			return unfriendlyRead (rdr);
		}
		catch (IOException e) {
			throw new IllegalStateException (e);
		}
	}
	
	private List<List<String>> unfriendlyRead (String filename) throws IOException {
		return read (new FileReader (filename));
	}
	
	private List<List<String>> unfriendlyRead (BufferedReader rdr) throws IOException {
		List<List<String>> lines = new LinkedList<List<String>> ();
		rdr.readLine (); // throw away header line
		String line;
		while ((line = rdr.readLine ()) != null) {
			String[] fields = line.split(",");
			lines.add (Arrays.asList (fields));
		}
		rdr.close ();
		return lines;
	}
}