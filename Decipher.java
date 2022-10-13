// Author: Nikolas Leslie
// Date created: 10/13/2022
// Last modified: 10/13/2022
// Decrypts a given file

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Decipher{
	
	static final String ENCRYPTED_FILE = "ciphertext.txt";
	static final String PLAINTEXT_FILE = "plaintext.txt";
	
	public static void main(String[] args){
		
		File encryped = null;
		Scanner fileReader = null;
		char[] decryptedValues = new char[]
		
		try{
			encrypted = new File(ENCRYPTED_FILE);
			fileReader = new Scanner(encrypted);
		}catch(FileNotFoundException e){
			System.out.println("File of encrypted message not found. Please check to make sure the file and path are there and correct.");
			System.exit(0);
		}
		
	}
}
