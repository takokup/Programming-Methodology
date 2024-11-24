import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

import acm.util.ErrorException;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	private NameSurferEntry entryName;
	private HashMap<String,NameSurferEntry> findEntry= new HashMap<String,NameSurferEntry>();
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		try{
			BufferedReader rdr = new BufferedReader(new FileReader(filename));
			while(true){
			String dataBase = rdr.readLine();
			if(dataBase == null){
				break;
			}
			entryName = new NameSurferEntry(dataBase);
			findEntry.put(entryName.getName(),entryName);
			}
		}catch(IOException ex){
			throw new ErrorException(ex);
		}
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		String name1 = "";
		name1 += name.charAt(0);
		
		name1 = name1.toUpperCase();
		String bla = name.substring(1);
		String name2 = bla.toLowerCase();
		name = name1 + name2;
		return findEntry.get(name);
	}
}

