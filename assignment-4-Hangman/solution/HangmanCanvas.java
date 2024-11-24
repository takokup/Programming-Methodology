import java.awt.Font;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 200;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

	String IncorrectLetter = "";
	int countIncrLett = 0;
	private GLabel dislpayIncorrectLetters = null;
	private GLabel displayText = null;
	double x = WIDTH / 2 - BEAM_LENGTH;
	double y = HEIGHT / 2 + SCAFFOLD_HEIGHT / 2;
	private static final int WIDTH = 400;
	private static final int HEIGHT = 450;
	

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		addScaffold();

	}

	private void addScaffold() {

		GLine scaffold = new GLine(x, y, x, y - SCAFFOLD_HEIGHT - 30);
		add(scaffold);

		GLine beam = new GLine(x, y - SCAFFOLD_HEIGHT - 30, x + BEAM_LENGTH, y - SCAFFOLD_HEIGHT - 30);
		add(beam);

		GLine rope = new GLine(x + BEAM_LENGTH, y - SCAFFOLD_HEIGHT - 30, x + BEAM_LENGTH,
				y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH);
		add(rope);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		if (displayText != null) {
			remove(displayText);
		}

		displayText = new GLabel(word, x - 30, y + 50);
		displayText.setFont(new Font("Verdana", Font.PLAIN, 30));

		add(displayText);

	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.
	 * Calling this method causes the next body part to appear on the scaffold
	 * and adds the letter to the list of incorrect guesses that appears at the
	 * bottom of the window.
	 */
	public void noteIncorrectGuess(char letter) {

		if (dislpayIncorrectLetters != null) {
			remove(dislpayIncorrectLetters);
		}
		IncorrectLetter = IncorrectLetter + letter;
		countIncrLett++;
		addHangMan();

		dislpayIncorrectLetters = new GLabel(IncorrectLetter, x - 25, y + 65);
		dislpayIncorrectLetters.setFont(new Font("Verdana", Font.PLAIN, 18));

		add(dislpayIncorrectLetters);
	}
/*
 * This code adds body parts based on the number of incorrect letters.
 */
	private void addHangMan() {
		if (countIncrLett == 1) {
			GOval head = new GOval(HEAD_RADIUS * 2, HEAD_RADIUS * 2);
			add(head, WIDTH / 2 - HEAD_RADIUS, y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH);
		}  if (countIncrLett == 2) {
			GLine body = new GLine(WIDTH / 2, y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + HEAD_RADIUS * 2, WIDTH / 2,
					y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH);
			add(body);
		} if (countIncrLett == 3) {
			GLine leftArm = new GLine(WIDTH/2,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + HEAD_RADIUS * 2 +ARM_OFFSET_FROM_HEAD,
					WIDTH/2 -UPPER_ARM_LENGTH ,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + HEAD_RADIUS * 2 +ARM_OFFSET_FROM_HEAD);
            GLine lowerLeftArm = new GLine(WIDTH/2 -UPPER_ARM_LENGTH ,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + HEAD_RADIUS * 2 +ARM_OFFSET_FROM_HEAD,
					WIDTH/2 -UPPER_ARM_LENGTH ,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + HEAD_RADIUS * 2 +ARM_OFFSET_FROM_HEAD +LOWER_ARM_LENGTH );
			add(lowerLeftArm);
			add(leftArm);
		} if(countIncrLett == 4){
			GLine rightArm=new GLine(WIDTH/2,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + HEAD_RADIUS * 2 +ARM_OFFSET_FROM_HEAD,
					WIDTH/2 +UPPER_ARM_LENGTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + HEAD_RADIUS * 2 +ARM_OFFSET_FROM_HEAD);
			
			GLine rightLowerArm = new GLine(WIDTH/2 +UPPER_ARM_LENGTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + HEAD_RADIUS * 2 +ARM_OFFSET_FROM_HEAD,
					WIDTH/2+UPPER_ARM_LENGTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + HEAD_RADIUS * 2 +ARM_OFFSET_FROM_HEAD +LOWER_ARM_LENGTH );
			add(rightLowerArm);
			add(rightArm);
		} if(countIncrLett == 5){
			GLine leftHip= new GLine (WIDTH/2,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH,
					WIDTH/2 -HIP_WIDTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH);
			GLine leftLeg = new GLine(WIDTH/2 -HIP_WIDTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH,
					WIDTH/2 -HIP_WIDTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH +LEG_LENGTH);
			add(leftHip);
			add(leftLeg);
			
		} if(countIncrLett == 6){
			GLine rightHip= new GLine (WIDTH/2,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH,
					WIDTH/2 +HIP_WIDTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH);
			GLine rightLeg = new GLine(WIDTH/2 +HIP_WIDTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH,
					WIDTH/2 +HIP_WIDTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH +LEG_LENGTH);
			add(rightHip);
			add(rightLeg);
			
		} if(countIncrLett == 7){
			GLine leftFoot= new GLine(WIDTH/2 -HIP_WIDTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH +LEG_LENGTH,
					WIDTH/2 -HIP_WIDTH -FOOT_LENGTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH +LEG_LENGTH);
			add(leftFoot);
		} if(countIncrLett == 8){
			GLine rightFoot = new GLine(WIDTH/2 +HIP_WIDTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH +LEG_LENGTH,
					WIDTH/2 +HIP_WIDTH +FOOT_LENGTH,y - SCAFFOLD_HEIGHT - 30 + ROPE_LENGTH + BODY_LENGTH +LEG_LENGTH);
			add(rightFoot);
			
			
		} 

	}

	
	

}
