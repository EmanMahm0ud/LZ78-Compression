
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Simple file:\n--------------------");
		LZ78 lz78 = new LZ78("data.txt", "compressed.txt");
		lz78.printDictionary();
		lz78.printTags();
		
		LZ78_Decompression dlz78 = new LZ78_Decompression("compressed.txt");
		dlz78.printData();
	}

}
