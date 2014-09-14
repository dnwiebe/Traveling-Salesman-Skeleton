package salesman.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CSVReaderTest {
	
	private InputStream inputStream;
	private Reader reader;
	private BufferedReader bufferedReader;
	private CSVReader subject;
	private List<List<String>> expected;
	
	@Before
	public void setup () {
		String contents = "Header Line\nWhat,we,have,here\nis,a,failure,to,communicate\n";
		// Spies rather than bare objects so that we can tell whether close ()
		// has been called.
		inputStream = spy (new ByteArrayInputStream (contents.getBytes ()));
		reader = spy (new StringReader (contents));
		bufferedReader = spy (new BufferedReader (new StringReader (contents)));
		subject = new CSVReader ();
		expected = Arrays.asList (
			Arrays.asList ("What", "we", "have", "here"),
			Arrays.asList ("is", "a", "failure", "to", "communicate")
		);
	}
	
	@Test
	public void readsFromBufferedReaderAndCloses () throws Exception {
		List<List<String>> result = subject.read (bufferedReader);
		
		assertEquals (expected, result);
		// Make sure close() was called on bufferedReader.
		verify(bufferedReader).close ();
	}
	
	@Test
	public void handlesExceptionFromBufferedReader () throws Exception {
		// Not a real BufferedReader, just something that looks like a
		// BufferedReader but throws an exception when directed to read.
		BufferedReader rdr = mock (BufferedReader.class);
		doThrow (new IOException ("Hope and change!")).when (rdr).readLine ();
		
		try {
			subject.read (rdr);
			fail ();
		}
		catch (IllegalStateException e) {
			assertEquals ("Hope and change!", e.getCause ().getMessage ());
		}
	}
	
	@Test
	public void readsFromReaderAndCloses () throws Exception {
		List<List<String>> result = subject.read (reader);
		
		assertEquals (expected, result);
		// Make sure close() was called on reader.
		verify(reader).close ();
	}
	
	@Test
	public void readsFromInputStreamAndCloses () throws Exception {
		List<List<String>> result = subject.read (inputStream);
		
		assertEquals (expected, result);
		// Make sure close() was called on inputStream.
		verify(inputStream).close ();
	}
	
	@Test
	public void handlesExceptionFromInputStream () throws Exception {
		// Not a real InputStream, just something that looks like a
		// InputStream but throws an exception when directed to read.
		InputStream istr = mock (InputStream.class);
		doThrow (new IOException ("Hope and change!")).when (istr).read (any (), anyInt (), anyInt ());
		
		try {
			subject.read (istr);
			fail ();
		}
		catch (IllegalStateException wrapper) {
			// Checked exception IOException wrapped in unchecked
			// IllegalStateException so callers don't have to mess
			// with "throws" clauses.
			IOException e = (IOException)wrapper.getCause ();
			assertEquals ("Hope and change!", e.getMessage ());
		}
	}
	
	@Test
	public void readsFromFile () throws Exception {
		List<List<String>> result = subject.read ("src/main/resources/map.csv");
		
		List<String> line;
		line = result.get (0);
		assertEquals (Arrays.asList (
			"Seattle", "Minneapolis", "1440", "1656", "1437", "1656"
		), line);
		line = result.get (result.size () - 1);
		assertEquals (Arrays.asList (
			"Seattle", "Albuquerque", "1334", "1435", "1331", "1434"
		), line);
	}
	
	@Test
	public void handlesExceptionFromProblemOpeningFile () throws Exception {
		try {
			subject.read ("Gloobety.csv");
			fail ();
		}
		catch (IllegalStateException wrapper) {
			// Checked exception FileNotFoundException wrapped in unchecked
			// IllegalStateException so callers don't have to mess
			// with "throws" clauses.
			FileNotFoundException e = (FileNotFoundException)wrapper.getCause ();
			assertEquals ("Gloobety.csv (No such file or directory)", e.getMessage ());
		}
	}
}
