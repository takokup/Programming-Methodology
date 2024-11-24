
/*
 * File: Yahtzee.java
 * ------------------
 * This program plays the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}

		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		// Initialize the scores array with -1
		int[][] scores = new int[N_CATEGORIES][nPlayers];
		for (int i = 0; i < N_CATEGORIES; i++) {
			for (int j = 0; j < nPlayers; j++) {
				scores[i][j] = -1;
			}
		}

		for (int n = 0; n < N_SCORING_CATEGORIES; n++) {

			for (int player = 1; player <= nPlayers; player++) {
				display.printMessage(playerNames[player - 1] + "'s turn. Click Roll-Dice ");
				display.waitForPlayerToClickRoll(player);

				// Roll the dice
				for (int dice = 0; dice < N_DICE; dice++) {
					roll[dice] = rgen.nextInt(1, 6);
				}
				display.displayDice(roll);
				scndAndThrdRolls(); // Allow for second and third rolls
				checkAndCountScores(player, scores); // Check and count the
														// scores
			}
		}

		// Determine the winner
		int winner = 0;
		for (int player = 0; player < nPlayers; player++) {
			if (scores[TOTAL - 1][player] > scores[TOTAL - 1][winner]) {
				winner = player;
			}
		}

		// Display the winner
		display.printMessage(
				"CONGRATULATIONS!!! " + playerNames[winner] + " won with " + scores[TOTAL - 1][winner] + " scores");
	}

	// Method to calculate the upper and lower scores
	private void sum(int player, int[][] scores) {
		if (upperScore(scores, player)) {
			for (int i = 0; i < SIXES; i++) {
				if (scores[i][player - 1] >= 0) {
					sum += scores[i][player - 1];
				}
			}
			display.updateScorecard(UPPER_SCORE, player, sum);

			// Update upper bonus if applicable
			if (sum > 63) {
				display.updateScorecard(UPPER_BONUS, player, 35);
				scores[UPPER_BONUS - 1][player - 1] = 35;
			} else {
				display.updateScorecard(UPPER_BONUS, player, 0);
			}
			scores[UPPER_SCORE - 1][player - 1] = sum;
		}
		sum = 0;

		// Calculate and update the lower score
		if (lowerScore(scores, player)) {
			for (int i = THREE_OF_A_KIND - 1; i < CHANCE; i++) {
				if (scores[i][player - 1] >= 0) {
					suml += scores[i][player - 1];
				}
			}
			display.updateScorecard(LOWER_SCORE, player, suml);
			scores[LOWER_SCORE - 1][player - 1] = suml;
		}

		suml = 0;
	}

	// Check if all lower categories have been scored
	private boolean lowerScore(int[][] scores, int player) {
		for (int i = UPPER_BONUS; i < CHANCE; i++) {
			if (scores[i][player - 1] < 0) {
				return false;
			}
		}
		return true;
	}

	// Check the selected category, count the scores, and update the display
	private void checkAndCountScores(int player, int[][] scores) {
		while (true) {
			display.printMessage(" Please Select category");
			int category = display.waitForPlayerToSelectCategory();

			if (scores[category - 1][player - 1] == -1) {
				for (int dice = 0; dice < N_DICE; dice++) {
					if (category >= ONES && category <= SIXES) {
						if (roll[dice] == category) {
							result += category;
						}
					} else if (category == THREE_OF_A_KIND && combinationOfThree(roll)) {
						result += roll[dice];
					} else if (category == FOUR_OF_A_KIND && combinationOfFour(roll)) {
						result += roll[dice];
					} else if (category == FULL_HOUSE && combinationOfFullHouse(roll)) {
						result = 25;
					} else if (category == CHANCE) {
						result += roll[dice];
					} else if (category == YAHTZEE && combinationOfFullHouse(roll) && combinationOfFour(roll)) {
						result = 50;
					} else if (category == SMALL_STRAIGHT && combinationOfSmallStraight(roll)) {
						result = 30;
					} else if (category == LARGE_STRAIGHT && combinationOfLargeStraight(roll)) {
						result = 40;
					}
				}

				// Update scores and display
				scores[category - 1][player - 1] = result;
				display.updateScorecard(category, player, result);

				// Calculate and update total score
				for (int i = 0; i < TOTAL - 2; i++) {
					if (scores[i][player - 1] != -1 && i != UPPER_SCORE - 1 && i != LOWER_SCORE - 1) {
						total += scores[i][player - 1];
					}
				}
				scores[TOTAL - 1][player - 1] = total;
				display.updateScorecard(TOTAL, player, total);

				total = 0;
				result = 0;
				break;
			}

			if (scores[category - 1][player - 1] != -1) {
				display.printMessage("This category is already selected. Choose a different one.");
			}
		}

		// Calculate upper and lower scores
		sum(player, scores);
	}

	// Check if upper scores have been filled
	private boolean upperScore(int[][] scores, int player) {
		for (int i = 0; i < SIXES; i++) {
			if (scores[i][player - 1] < 0) {
				return false;
			}
		}
		return true;
	}

	// Check if there is a combination of a large straight
	private boolean combinationOfLargeStraight(int[] roll) {
		for (int i = 0; i < N_DICE; i++) {
			for (int j = 0; j < N_DICE; j++) {
				for (int dice = 0; dice < N_DICE; dice++) {
					for (int dice1 = 0; dice1 < N_DICE; dice1++) {
						for (int dice2 = 0; dice2 < N_DICE; dice2++) {
							if (roll[i] == roll[j] - 1 && roll[i] == roll[dice] - 2 && roll[i] == roll[dice1] - 3
									&& roll[i] == roll[dice2] - 1) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	// Check if there is a combination of a small straight
	private boolean combinationOfSmallStraight(int[] roll) {
		for (int i = 0; i < N_DICE; i++) {
			for (int j = 0; j < N_DICE; j++) {
				for (int dice = 0; dice < N_DICE; dice++) {
					for (int dice1 = 0; dice1 < N_DICE; dice1++) {
						if (roll[i] == roll[j] - 1 && roll[i] == roll[dice] - 2 && roll[i] == roll[dice1] - 3) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// Check if there is a combination of a full house
	private boolean combinationOfFullHouse(int[] roll) {
		for (int i = 0; i < N_DICE; i++) {
			for (int j = 1; j < N_DICE; j++) {
				for (int dice = 2; dice < N_DICE; dice++) {
					for (int dice1 = 0; dice1 < N_DICE; dice1++) {
						for (int dice2 = 0; dice2 < N_DICE; dice2++) {
							if (dice != j && j != i && dice != i && dice1 != j && dice1 != i && dice1 != dice
									&& dice2 != i && dice2 != dice && dice2 != j && dice2 != dice1
									&& roll[dice] == roll[j] && roll[j] == roll[i] && roll[dice1] == roll[dice2]) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	// Check if there is a combination of four of a kind
	private boolean combinationOfFour(int[] roll) {
		for (int i = 0; i < N_DICE; i++) {
			for (int j = 1; j < N_DICE; j++) {
				for (int dice = 2; dice < N_DICE; dice++) {
					for (int dice1 = 3; dice1 < N_DICE; dice1++) {
						if (dice != j && j != i && dice != i && dice != dice1 && dice1 != i && dice1 != j
								&& roll[dice] == roll[j] && roll[j] == roll[i] && roll[i] == roll[dice1]) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// Check if there is a combination of three of a kind
	private boolean combinationOfThree(int[] roll) {
		for (int i = 0; i < N_DICE; i++) {
			for (int j = 1; j < N_DICE; j++) {
				for (int dice = 2; dice < N_DICE; dice++) {
					if (dice != j && j != i && dice != i && roll[dice] == roll[j] && roll[j] == roll[i]) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Allow the player to re-roll selected dice for the second and third rolls
	private void scndAndThrdRolls() {
		for (int i = 0; i < 2; i++) {
			int cnt = 0;
			display.printMessage("Select the dice you wish to re-roll and click Roll Again.");
			display.waitForPlayerToSelectDice();
			for (int dice = 0; dice < N_DICE; dice++) {
				if (display.isDieSelected(dice)) {
					roll[dice] = rgen.nextInt(1, 6);
					cnt++;
				}
			}
			display.displayDice(roll);
			if (cnt == 0) {
				break;
			}
		}
	}

	int result = 0;
	int total = 0;
	private int[] roll = new int[N_DICE];
	int sum = 0;
	int suml = 0;
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
}
