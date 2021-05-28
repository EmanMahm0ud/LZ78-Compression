import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class LZ78 {
	String sentence = "";                          // stores file content in one string
	Vector<String> tags = new Vector();            // tag is <new char, index in dictionary>
	Vector<String> dictionary = new Vector();      // contains added new character(s)
	
	/**
	 * in this constructor, it calls functions to run compression algorithm
	 * @param fileName is the file name that will be compressed
	 * @param saveFileName is the file name that will be contained the tags
	 */
	LZ78(String fileName, String saveFileName){		
		readFile(fileName);
		compressFile();
		addToFile(saveFileName);
	}
	
	/**
	 * read file and store it in "sentence" variable
	 * @param fileName is the file name that will be compressed
	 */
	private void readFile(String fileName) {
		File file = new File(fileName);
		Scanner fileReader;
		try {
			fileReader = new Scanner(file);
			while (fileReader.hasNextLine()) {
				String data = fileReader.nextLine();
				sentence += (data + "\n"); 
			}
			fileReader.close();
			
			// to remove the last character "\n" (new line)
			sentence = sentence.substring(0, sentence.length()-1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * loop on sentence, add to dictionary new character(s), prepare tags
	 */
	private void compressFile() {
		dictionary.add("");   // the first index is empty character
		
		String word = "";     // takes maximum characters from "sentence" that have been appeared in "dictionary"
		Integer index;        // index of "word" in "dictionary"
		Integer preIndex = 0; // stores the previous value of "index", default 0 in case there is no index
		
		for (int i = 0 ; i < sentence.length() ; i++) {
			word += sentence.charAt(i);
			index = searchInDictionary(word);
			
			// new string is not in the "dictionary"
			if (index == 0) {
				tags.add(sentence.charAt(i) + preIndex.toString());        // add index and new character to "tags"
				dictionary.add(word);                                      // add new "word" to "dictionary"
				word = "";                                                 // reset "word"
				
			} else if (index != 0 && i == sentence.length()-1) {           // string is in "dictionary" and there is no characters in "sentence"
				tags.add(" " + index.toString());                          // add index and single space (no new character to be added)
			}
			preIndex = index;
		}
	}
	
	/**
	 * @param word is the string we search in "dictionary"
	 * @return index of the existed word or 0 if it is not found
	 */
	private int searchInDictionary(String word) {
		for (int i = 1 ; i < dictionary.size() ; i++) {
			if (dictionary.get(i).contentEquals(word)) {
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * write tags in file
	 * @param fileName is the file name that will store tags
	 */
	private void addToFile(String fileName) {
		File file = new File(fileName);
		try {
			FileWriter myWriter = new FileWriter(file);
			for (String tag : tags) {
				myWriter.write(tag + "\n");
			}
		    myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printDictionary() {
		System.out.println("--------------------\nDictionary :-\n--------------------");
		for (int i = 0 ; i < dictionary.size() ; i++)
			System.out.println(dictionary.get(i));
	}
	
	public void printTags() {
		System.out.println("--------------------\nTags :-\n--------------------");
		for (String tag : tags) {
			System.out.println(tag);
		}
	}
}
