package littlelib;

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Tools {
	private static Scanner scanner = new Scanner(System.in);
	private static Random random = new Random();

	public static String saveStringInput(String prompt, int minLength, int maxLength) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine();
			if (input.length() < minLength || input.length() > maxLength) {
				System.out.printf("Your input must be %d to %d characters long.\n", minLength, maxLength);
			} else {
				return input;
			}
		}
	}
	
	public static int saveIntInput(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return Integer.valueOf(scanner.nextLine());

			} catch (NumberFormatException e) {
				System.out.println("Not a valid number.");
			}
		}
	}
	
	public static int saveIntInput(String prompt, int lowerRange, int upperRange) {
		while (true) {
			int input = saveIntInput(prompt);
			if (input > upperRange || input < lowerRange) {
				System.out.println("The input has to be between " + lowerRange + " and " + upperRange + ".");
			} else {
				return input;
			}
		}
	}
	
	public static boolean equalsOneOf(boolean ignoreCase, String string, String... symbols) {
		for (String symbol : symbols) {
			if (ignoreCase) {
				if (string.equalsIgnoreCase(symbol)) return true;
			} else {
				if (string.equals(symbol)) return false;
			}
		}
		return false;
	}
	
	public static int generateRandomInt(int lower, int upper) {
		return random.nextInt(upper - lower + 1) + lower;
	}
	
	public static String repeatSymbol(char symbol, int times) {
		if (times > 0) {			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < times; i++) {
				sb.append(symbol);
			}
			return sb.toString();
		}
		return null;
	}
	
	public static String repeatString(String value, int times) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < times; i++) {
			sb.append(value);
		}
		return sb.toString();
	}
	
	// only works for strings that do not contain line break
	public static String makeBorderString(String value) {
		StringBuilder sb = new StringBuilder();
		sb.append("+" + repeatString("-", value.length() + 2) + "+\n");
		sb.append("| " + repeatString(" ", value.length()) + " |\n");
		sb.append("| " + value + " |\n");
		sb.append("| " + repeatString(" ", value.length()) + " |\n");
		sb.append("+" + repeatString("-", value.length() + 2) + "+\n");
		return sb.toString();
	}
	
	// frames a string that contains newlines
	// does not work for strings that contain symbols that need more than one
	// space unit, for example tabs
	public static String makeBorder(String value) {
		String[] lines = value.split("\\r?\\n");
		return makeBorder(lines);
	}
	
	// does not work for strings that contain symbols that need more than one
	// space unit, for example tabs
	public static String makeBorder(String stringArray[]) {
		List<String> stringList = Arrays.asList(stringArray);
		return makeBorder(stringList);
	}
	
	// does not work for strings that contain symbols that need more than one
	// space unit, for example tabs
	public static String makeBorder(List<String> stringList) {
		// find most long line
		int mostLong = 0;
		for (int i = 0; i < stringList.size(); i++) {
			if (stringList.get(i).length() > mostLong) {
				mostLong = stringList.get(i).length();
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		// make upper Border border
		sb.append("+" + repeatSymbol('-', mostLong + 2) + "+\n");
		
		// make body
		for (int i = 0; i < stringList.size(); i++) {
			String current = stringList.get(i);
			int whiteSpaceAmount = mostLong - current.length();
			if (whiteSpaceAmount > 0) {
				String whitespace = repeatSymbol(' ', + whiteSpaceAmount);
				sb.append("| " + current + whitespace + " |\n");
			} else {
				sb.append("| " + current + " |\n");
			}
			
		}
		
		// make lower Border border
		sb.append("+" + repeatSymbol('-', mostLong + 2) + "+");
		
		return sb.toString();
	}
}
