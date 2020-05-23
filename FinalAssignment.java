import java.util.*;

public class FinalAssignment {
	
	/*	Name			Bram Vrielink
	 * 	Date			07/05/2020
	 * 	Description		Mastermind Code
	 */	
	
	public static void main(String[] args) {
		
		Boolean gameOver = false;
		Integer codeLength = 4;
		Integer rounds = 10;
		String[] colors = {"R", "G", "B", "P", "O", "Y"};
		Scanner in = new Scanner(System.in);
			
		String code = "";
		String guess = "";
		Boolean valid = false;
		String feedback = "";
		
		String layoutLeft = "|";
		String layoutRight = "| ";
			
			
		// Generate Random Code //2.1
		code = getCode(codeLength, colors);
			
		// Welcome Player //3.1
		showWelcome(codeLength);
			
		//Play game while gameover is false
		while(!gameOver) {
				
			// Reset Valid Boolean
			valid = false;
				
			// Ask Player's Guess //3.2
			guess = getGuess(in);
				
			// Check Guess Validation //4.1
			valid = checkValid(valid, codeLength, guess, colors);
				
			if(!valid) {
				// If Invalid //4.2
				showInvalid(valid);
			}
				
			else {
					
				// Subtract a round
				rounds--;
					
				// Check if Code has been Guessed //5.1/
				gameOver = checkGameOver(gameOver, code, guess, rounds);
					
				// Give Feedback //5.2/
				feedback = feedback + "\r\n" + getFeedback(feedback, code, guess, colors, codeLength, layoutLeft, layoutRight);
					
				// Show Layout //6.1
				showLayout(gameOver, codeLength, rounds, code, guess, feedback, layoutLeft, layoutRight);
			}
		}
	}
  
  
  
  
	// Master Mind //2.0/
  
	
	// Generate Random Code //2.1/
	private static String getCode(Integer codeLength, String[] colors) {
		
		Random rng = new Random();
		String sCode = "";
		int index, cLength = codeLength;
		
		for(int i=0; i<cLength; i++) {
			index = rng.nextInt(colors.length);
			sCode+=colors[index];
		}
		return sCode;
	}	

	
	
	
	// Master Mind //3.0/
	
	
	// Welcome Player //3.1/
	private static void showWelcome(Integer codeLength) {
		String welcomeInstructions = "Welcome to Mastermind" + "\r\n" +
	                                     "\r\n" +
	                                     "Create combinations with colors and crack the code." + "\r\n" +
	                                     "The following colors are included" + "\r\n" +
	                                     "Red, Green, Blue, Purple, Orange and Yellow" + "\r\n" +
	                                     "\r\n" +
	                                     "To create a combination, fill in the first letter of each color you want to use." + "\r\n" +
	                                     "For example: if your guess is \"Yellow, Blue, Blue, Red\"" + "\r\n" +
	                                     "you would fil in \"YBBR\"." + "\r\n" +
	                                     "\r\n" +
	                                     "Depending on your guess, you may recieve small hints." + "\r\n" +
	                                     "These hints consists of blacks and whites." + "\r\n" +
	                                     "A black means you got a color correct and is in the right position." + "\r\n" +
	                                     "A white meand you got a color correct but is not in the right position." + "\r\n" +
	                                     "\r\n" +
	                                     "The length of the code is " + codeLength + "\r\n" +
	                                     "Now make your guess.";
			
		System.out.println(welcomeInstructions);
	}
	
	
	// Ask Player's Guess //3.2/
	private static String getGuess(Scanner in) {
		
		String guess = in.nextLine().toUpperCase();
		return guess;
	}
	
	
	
	
	// Master Mind //4.0/

	
	// Check Guess Validation //4.1/
	private static Boolean checkValid(Boolean valid, Integer codeLength, String guess, String[] colors) {
		
		// Turn String Array to Regular String //4.1.1/
		if(guess.length() == codeLength && guess.matches("["+getColors(colors)+"]+")) {
			return true;
		}
		else {return false;}
	}
	
	// Turn String Array to Regular String //4.1.1/
	private static String getColors(String[] colors) {
		
		String sColors="";
		
		for(int i=0; i<colors.length; i++) {
			sColors += colors[i];
		}
		return sColors;
	}


	// If Invalid //4.2/
	private static void showInvalid(Boolean valid) {
		
		String invalid = "Invalid answer, please try again.";
		
		if(!valid) {
			System.out.println(invalid);
		}
	}
	
	
	
	
	// Master Mind //5.0/
	
	
  	// Check if Code has been Guessed // 5.1
	private static Boolean checkGameOver(Boolean gameOver, String code, String guess, Integer rounds) {
		
		if(code.equals(guess) || rounds == 0) {
			gameOver = true;
		}
	return gameOver;
	}
	
	
	// Give Feedback //5.2/
	private static String getFeedback(String feedback, String code, String guess, String[] colors, Integer codeLength, String layoutLeft, String layoutRight) {
		
		Integer black = 0;
		Integer white = 0;
		
		// Change String Array to Char Array //5.2.1/
		char[] chColors = changeStringArrayToCharArray(colors);
		
		// Determine Matrix Length
		Integer length = colors.length;
		
		// Change Guess and Code to Matrix //5.2.2/
		int[] countGuess = getMatrixColors(guess, chColors, length);
		int[] countCode = getMatrixColors(code, chColors, length);
				
		// Check Blacks //5.2.3/
		black = checkBlacks(black, code, guess);
		
		// Check Whites //5.2.4/
		white = checkWhites(black, white, countGuess, countCode, codeLength);
		
		// Create Feedback //5.2.7/
		feedback = createFeedback(black, white, guess, layoutLeft, layoutRight);
		
	return feedback;
	}

	
	// Change String Array to Char Array //5.2.1/
	private static char[] changeStringArrayToCharArray(String[] colors) {
		
		String toChar = "";
		
		for(int i=0; i<colors.length; i++) {
			toChar += colors[i];
		}
		
		char[] ch = toChar.toCharArray();
		
		return ch;
	}


	// Change Guess and Code to Matrix //5.2.2/
	private static int[] getMatrixColors(String code, char[] chColors, Integer length) {
		
		int[] num = {0,0,0,0,0,0};
		
		for(int i=0; i<code.length(); i++) {
			for (int j=0; j<chColors.length; j++) {
				if(code.charAt(i) == chColors[j]) {
					num[j]++;
				}
			}
		}
		return num;
	}
	

	// Check Blacks //5.2.3/
	private static Integer checkBlacks(Integer black, String code, String guess) {
		
		char[] chCode = code.toCharArray();
		char[] chGuess = guess.toCharArray();
		
		for(int i=0; i<code.length(); i++) {
			if(chGuess[i] == chCode[i]) {
				black++;
			}
		}
		return black;
	}
	
	
	// Check Whites //5.2.4/
	private static Integer checkWhites(Integer black, Integer white, int[] countGuess, int[] countCode, Integer codeLength) {
		
		// Set Max Amount of Whites //5.2.5/
		countGuess = setMax(countGuess, countCode);
		
		// Set Whites //5.2.6/
		white = setWhites(white, countGuess);
			
		// Subtract Blacks from Whites
		white = white-black;
		
		// Make Sure Whites Can't Go Below 0
		if(white < 0) {
			white = 0;
		}
		return white;
	}

	
	// Set Max Amount of Whites //5.2.5/
	private static int[] setMax(int[] countGuess, int[] countCode) {
		
		for(int i=0; i<countCode.length; i++) {
			if(countGuess[i] > countCode[i]) {
				countGuess[i] = countCode[i];
			}
		}
		return countGuess;
	}
	
	
	// Set Whites //5.2.6/
	private static Integer setWhites(Integer white, int[] countGuess) {
		
		for(int i = 0; i<countGuess.length; i++) {
			if(countGuess[i] > 0) {
				white += countGuess[i];
			}
		}
		return white;
	}
	
	
	// Create Feedback //5.2.7/
	private static String createFeedback(Integer black, Integer white, String guess, String layoutLeft, String layoutRight) {
		
		String blackIn = black + " black";
		String whiteIn = white + " white";
		String hint = "You got ";
		
		// Are black and white bigger then 1?
		if(black > 1) {
			blackIn = blackIn + "s";
		}
		
		if(white > 1) {
			whiteIn = whiteIn + "s";
		}
		
		// Order to establish hint
		if(black > 0 && white > 0) {
			hint = hint + blackIn + " & " + whiteIn;
		}
		
		if(black > 0 && white == 0) {
			hint = hint + blackIn;
		}
		
		if(black == 0 && white > 0) {
			hint = hint + whiteIn;
		}
		
		hint = hint + ".";
		
		// When there are no blacks or whites
		if(black == 0 && white == 0) {
			hint = "";
		}
		
		hint = layoutLeft + guess + layoutRight + hint;
		return hint;
	}
	
	
	
	
	// Master Mind //6.0/
	
	
	//Show Layout //6.1
	private static void showLayout(Boolean gameOver, Integer codeLength, Integer rounds, String code, String guess, String feedback, String layoutLeft, String layoutRight) {
			
		String line = "__";
		String empty = "";
		String hidden = "";
						
		// Fill In Strings to Match Code Length
		for(int j=0; j<codeLength; j++) {
			line = line + "_";
			empty = empty + "o";
			hidden = hidden + "*";
		}
			
		// Strings for Board Use
		String emptyRow = layoutLeft + empty + layoutRight;
		String masterCode = layoutLeft + code + layoutRight;
		String masterCodeHidden = layoutLeft + hidden + layoutRight;
			
		// Show Board			
		System.out.println(feedback);
			
		for(int k=0; k<rounds; k++) {
			System.out.println(emptyRow);
		}
		System.out.println(line);
			
		// To Show or Not To Show Master Code
		if(gameOver) {
			System.out.println(masterCode);
				
			// Show what ending happened
			if(code.equals(guess)) {
				System.out.println("\r\n" +
								"**************************************" + "\r\n" +
								"* You won, you are a real mastermind *" + "\r\n" +
								"**************************************");
			}
				
			else {
				System.out.println("___________________________" + "\r\n" +
								"The rounds are up, too bad.");
			}
		}
			
		else {
			System.out.println(masterCodeHidden);
		}
	}
}
