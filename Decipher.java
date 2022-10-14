// Author: Nikolas Leslie
// Date created: 10/13/2022
// Last modified: 10/14/2022
// Decrypts a given file

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

public class Decipher{
	
	static final String DEFAULT_ENCRYPTED_FILE = "ciphertext.txt";
	static final String DEFAULT_PLAINTEXT_FILE = "plaintext.txt";
	
	static int formula(int num){
		
		int result = num;
		result -= 8;
		result *= 2;
		result += 9;
		result *= 567891;
		result -= 171;
		result /= 3;
		return result;
		
	}
	
	static int inverseFormula (int num){
		
		int result = num;
		result *= 3;
		result += 171;
		result /= 567891;
		result -= 9;
		result /= 2;
		result += 8;
		return result;
	
	}
	
	static int decrypt(String encryptedFile, String plainTextFile){
		
		File encrypted = null;
		Scanner fileReader = null;
		PrintWriter fileWriter = null;
		char[] decryptedValues = new char[];
		StringBuilder message = new StringBuilder("Your decrypted message is: \n\n");
		StringBuilder fileMessage = new StringBuilder();
		int count = 0;
		int value = 0;
		
		try{
			encrypted = new File(encryptedFile);
			fileReader = new Scanner(encrypted);
		}catch(FileNotFoundException e){
			StringBuilder error = new StringBuilder("The file ");
			error = error.append(encryptedFile);
			error = error.append(" was not found. Please check to see if the file is correct.");
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		while(fileReader.hasNextInt()){
			value = fileReader.hasNextInt();
			decryptedValues[count] = (char) inverseFormula(value);
			count++;
		}
		
		try {
			fileWriter = new PrintWriter(plainTextFile);
		}catch(FileNotFoundException e){
			StringBuilder error = new StringBuilder("The file ");
			error = error.append(plainTextFile);
			error = error.append(" was not found. Please check to see if the file is correct.");
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		fileMessage = fileMessage.append(decryptedValues);
		message = message.append(decryptedValues);
		message = message.append("\n\nYour message has also been saved in ");
		message = message.append(plainTextFile);
		
		fileWriter.println(fileMessage);
		JOptionPane.showMessageDialog(null, message, "Decrypt", JOptionPane.INFORMATION_MESSAGE);
		return 1;
	
	}
	
	static String encrypt(String message){
		
		StringBuilder encryptedMessage = new StringBuilder("Your encrypted message is:\n\n");
		char[] charsInMessage = message.toCharArray();
		
		for(char letter : charsInMessage){
			encryptedMessage = encryptedMessage.append(formula((int) letter));
			//Add spaces between numbers
		}
		
	}
	
	public static void main(String[] args){
		
		
		
	}
}
