package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class PrintLargeAlphabets {

	String userDirPath;
	String resourcesDirPath;
	public PrintLargeAlphabets() {
		userDirPath = System.getProperty("user.dir");
		resourcesDirPath = userDirPath+"/src/test/java/common/Alphabets";
		System.out.println(resourcesDirPath);
	}
	
	InputStream reader;
	BufferedReader bufferedReader;
	String line;
	
	public String getLargeAlphabetsFilePath(String character) {
		return resourcesDirPath+"/"+character.toUpperCase()+".txt";
	}
	
	public void printFinalText(String text) throws IOException {
		String line;
		String[] splitString = text.split("");
		String fullLineForLength = lineBuilder(splitString, 1);
		String border = getBorder(fullLineForLength);
		System.out.println(border);
		System.out.println(border);
		System.out.println("");
		for (int i = 1; i <= 7; i++) {
			line = lineBuilder(splitString, i);
			System.out.println(line);
		}
		System.out.println("");
		System.out.println(border);
		System.out.println(border);
	}
	
	public String getBorder(String fullLineForLength) {
		int lineLength = fullLineForLength.length();
		String border = "";
		for (int i = 0; i < lineLength; i++) {
			border = border+"*";
		}
		return border;
	}
	
	public String lineBuilder(String[] splitString, int lineNumber) throws IOException {
		String line = "";
		String finalLine = "";
		for (String character: splitString) {
			bufferedReader = readAlphabets(character);
			line = readLine(bufferedReader, lineNumber);
			finalLine = finalLine+line;
		}
		return finalLine;
	}
	
	public BufferedReader readAlphabets(String character) throws FileNotFoundException {
		String charactersFilePath = getLargeAlphabetsFilePath(character);
		reader = new FileInputStream(charactersFilePath);
		bufferedReader = new BufferedReader(new InputStreamReader(reader));
		return bufferedReader;
	}
	
	public String readLine(BufferedReader bufferedReader, int lineNumber) throws IOException {
		String line = null;
		for (int i = 0; i < lineNumber; i ++) {
			line = bufferedReader.readLine();
		}
		return line;
	}

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter any text using only alphabets (upper or lower case) and press ENTER!!");
		String text = scanner.nextLine();
		scanner.close();
		PrintLargeAlphabets print = new PrintLargeAlphabets();
		print.printFinalText(text);
	}
}
