import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class HangmanLexicon {
	private static final String FILE = "HangmanLexicon.txt";

	private ArrayList<String> WordList = new ArrayList<String>();

	public HangmanLexicon() {
/*
 * it will read the words from HangmanLexicon.
 */
		try {
			BufferedReader bufferReader = new BufferedReader(new FileReader(FILE));
			while (true) {
				String line = bufferReader.readLine();
				if (line == null) {
					break;
				}
				WordList.add(line);
			}
			bufferReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return WordList.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {

		return WordList.get(index);
	}

}
