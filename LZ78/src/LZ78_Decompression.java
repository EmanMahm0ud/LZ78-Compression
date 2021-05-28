import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class LZ78_Decompression {

	String sentence = "";                      // stores original file content before compressing in one string
	Vector<String> tags = new Vector();        // tag is <new char, index in dictionary>
	Vector<String> dictionary = new Vector();  // contains added new character(s)
	
	/**
	 * in this constructor, it calls functions to run decompression algorithm
	 * @param fileName is the file name that contains tags
	 */
	LZ78_Decompression(String fileName){		
		readFile(fileName);
		decompressFile();
		addToFile();
	}
	
	/**
	 * read line by line from file and add each line to "tags"
	 * @param fileName is the file name that contains tags
	 */
	private void readFile(String fileName) {
		File file = new File(fileName);
		Scanner fileReader;
		try {
			fileReader = new Scanner(file);
			while (fileReader.hasNextLine()) {
				String data = fileReader.nextLine();
				tags.add(data); 
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void decompressFile() {
		dictionary.add("");      // the first index is empty character
		char newChar;
		String word = "";
		int index;
		
		for (int i = 0 ; i < tags.size() ; i++) {
			
			// if empty line is found it means new char is new line
			if (tags.get(i).length() == 0) {
				i++;
				newChar = '\n';
				index = Integer.parseInt(tags.get(i));
			} else {
				newChar = tags.get(i).charAt(0);
				index = Integer.parseInt(tags.get(i).substring(1, tags.get(i).length()));
			}
			
			if (i == tags.size()-1 && index != 0){  // last tag with no new character
				sentence += dictionary.get(index);
			} else {
				word = dictionary.get(index) + newChar;   // add string in dictionary & new character to "word"
				dictionary.add(word);
				sentence += word;
			}
		}
	}
	
	/**
	 * write the original file content after decompression in data2 file
	 */
	private void addToFile() {
		File file = new File("data2.txt");
		try {
			FileWriter myWriter = new FileWriter(file);
			myWriter.write(sentence);
		    myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * print the original file content after decompression
	 */
	public void printData() {
		System.out.println("-----------------------------\nData after decompression:-\n-----------------------------");
		System.out.println(sentence);
	}

}
