import acm.program.*;
import acm.util.*;

public class Hangman extends ConsoleProgram {

	private HangmanCanvas canvas;

	private RandomGenerator rgen = RandomGenerator.getInstance();
	private int numOfGuesses = 8;
	private int leftGuesses = 8;
	private HangmanLexicon hangmanWords = new HangmanLexicon();

	String dash;
	int indexInt = rgen.nextInt(0, hangmanWords.getWordCount() - 1);
	String pickWord = hangmanWords.getWord(indexInt);
	/*
	 * it adds Hangman canvas,where you can graphically see the result
	 */

	public void init() {
		canvas = new HangmanCanvas();

		add(canvas);

		canvas.reset();

	}

	public void run() {
		buildGame();

	}

	/*
	 * This code contains the details of the game, if the player enters the
	 * wrong letter, the body parts will appear on the canvas in sequence, if he
	 * enters a symbol, the player the number of attempts will remain the same
	 * and the computer will ask him to enter the letter, if he enters more than
	 * one symbol c computer will ask him to enter only one letter,if he guesses
	 * the word or if he loses it will notify him.He will know the word at the
	 * end of the game.
	 */
	private void buildGame() {
		println("Welcome to Hangman!");
		String dash = "";

		for (int i = 0; i < pickWord.length(); i++) {
			dash = dash += "-";
		}
		canvas.displayWord(dash);
		println("The Word now looks like this :" + dash);
		println("you have " + leftGuesses + " guesses left!");

		for (int i = 0; i < numOfGuesses; i++) {
			String guessLetter = readLine("your guess: ");

			guessLetter = guessLetter.toUpperCase();
			if (!Character.isLetter(guessLetter.charAt(0))) {
				println("please enter letter.");
				numOfGuesses++;
				println("you have " + leftGuesses + " guesses left!");

			}

			else if (guessLetter.length() != 1) {
				println("please enter only one letter !");
				numOfGuesses++;
				println("you have " + leftGuesses + " guesses left!");

			}
			if (!pickWord.contains(guessLetter) && guessLetter.length() == 1
					&& Character.isLetter(guessLetter.charAt(0)) && leftGuesses >= 1) {
				println("There is no " + guessLetter + " 's in the word");
				canvas.noteIncorrectGuess(guessLetter.charAt(0));
				println("The Word now looks like this: " + dash);
				leftGuesses--;

				println("you have " + leftGuesses + " guesses left!");

			}
			if (pickWord.contains(guessLetter) && guessLetter.length() == 1) {
				println("your guess is correct <3");
				numOfGuesses++;
				println("you have " + leftGuesses + " guesses left!");
				for (int j = 0; j < pickWord.length(); j++) {
					if (guessLetter.charAt(0) == pickWord.charAt(j) && j != 0) {
						dash = dash.substring(0, j) + guessLetter.charAt(0) + dash.substring(j + 1);

					} else if (guessLetter.charAt(0) == pickWord.charAt(j) && j == 0) {
						dash = guessLetter.charAt(0) + dash.substring(j + 1);
					}

					if (dash.equals(pickWord)) {
						println("you guessed the word: " + pickWord);
						println("YOU WiN ! ❤");
						numOfGuesses = 0;
						canvas.displayWord(dash);
						break;
					}

					canvas.displayWord(dash);

				}
				if (!dash.equals(pickWord)) {
					println("The word now looks like this:" + dash);

				}
			}
			if (leftGuesses < 1) {
				println("there is no " + guessLetter + "'s in the word");
				println("You're completely hung :(");
				println("The word was: " + pickWord);
				println("YOU LOSE :( ❤");

			}
		}
	}
}
