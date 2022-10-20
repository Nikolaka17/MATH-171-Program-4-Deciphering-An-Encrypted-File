// Author: Nikolas Leslie
// Date created: 10/13/2022
// Last modified: 10/20/2022
// Decrypts a given file

//Util imports
import java.util.Scanner;

//Io imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Decipher{
	
	//Starting files
	static final String DEFAULT_ENCRYPTED_FILE = "ciphertext.txt";
	static final String DEFAULT_PLAINTEXT_FILE = "plaintext.txt";
	
	//Encryption formula
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
	
	//Decryption formula
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
	
	//Decrypts the given file and adds it to the output file
	static int decrypt(String encryptedFile, String plainTextFile){
		
		File encrypted = null;
		Scanner fileReader = null;
		PrintWriter fileWriter = null;
		StringBuilder message = new StringBuilder("Your decrypted "+
												"message is: \n\n");
		StringBuilder fileMessage = new StringBuilder();
		int value = 0;
		
		//Test if input file is valid
		try{
			encrypted = new File(encryptedFile);
			fileReader = new Scanner(encrypted);
		}catch(FileNotFoundException e){
			StringBuilder error = new StringBuilder("The file ");
			error = error.append(encryptedFile);
			error = error.append(" was not found. Please check to see if the "+
								 "file is correct.");
			System.out.println(error);
			return 0;
		}
		
		//Read and translate from given file
		while(fileReader.hasNextInt()){
			value = fileReader.nextInt();
			message = message.append((char) inverseFormula(value));
			fileMessage = fileMessage.append((char) inverseFormula(value));
		}
		
		//Test if output file is valid
		try {
			fileWriter = new PrintWriter(plainTextFile);
		}catch(FileNotFoundException e){
			StringBuilder error = new StringBuilder("The file ");
			error = error.append(plainTextFile);
			error = error.append(" was not found. Please check to see "+
								 "if the file is correct.");
			System.out.println(error);
			return 0;
		}
		
		message = message.append("\n\nYour message has also been saved in ");
		message = message.append(plainTextFile);
		
		fileWriter.println(fileMessage);
		System.out.println(message);
		
		fileWriter.close();
		return 1;
	}
	
	//Encrypt the given file and add it to the output file
	static int encrypt(String inputFile, String outputFile){
		
		File input = null;
		Scanner fileReader = null;
		PrintWriter fileWriter = null;
		StringBuilder message = new StringBuilder();
		StringBuilder encryptedMessage = new StringBuilder("Your encrypted"+
														   " message is:\n\n");
		StringBuilder encryptedFile = new StringBuilder();
		String line = "";
		char[] charsInMessage;
		
		//Check if input file is valid
		try{
			input = new File(inputFile);
			fileReader = new Scanner(input);
		}catch(FileNotFoundException e){
			StringBuilder error = new StringBuilder("The file ");
			error = error.append(inputFile);
			error = error.append(" was not found. Please check to see if "+
								 "the file is correct.");
			System.out.println(error);
			return 0;
		}
		
		//Read and store input
		while(fileReader.hasNextLine()){
			line = fileReader.nextLine();
			message = message.append(line);
		}
		
		//Translate input
		charsInMessage = message.toString().toCharArray();
		for(char letter : charsInMessage){
			encryptedMessage = encryptedMessage.append(formula((int) letter));
			encryptedMessage = encryptedMessage.append(" ");
			encryptedFile = encryptedFile.append(formula((int) letter));
			encryptedFile = encryptedFile.append(" ");
		}
		
		//Check if output file is valid
		try{
			fileWriter = new PrintWriter(outputFile);
		}catch(FileNotFoundException e){
			StringBuilder error = new StringBuilder("The file ");
			error = error.append(outputFile);
			error = error.append(" was not found. Please check to see"+
								 " if the file is correct.");
			System.out.println(error);
			return 0;
		}
		
		encryptedMessage = encryptedMessage.append("\n\nYour encrypted messag"+
												   "e has also been saved in ");
		encryptedMessage = encryptedMessage.append(outputFile);
		
		fileWriter.println(encryptedFile);
		System.out.println(encryptedMessage);
		
		fileWriter.close();
		return 1;
	}
	
	//Show the contents of the selected file to the user
	static int view(String file){
		
		File fileToView = null;
		Scanner fileReader = null;
		String line = "";
		StringBuilder content = new StringBuilder("The file ");
		content = content.append(file);
		content = content.append(" contains this message:\n\n");
		
		//Test if given file is valid
		try{
			fileToView = new File(file);
			fileReader = new Scanner(fileToView);
		}catch(FileNotFoundException e){
			StringBuilder error = new StringBuilder("The file ");
			error = error.append(file);
			error = error.append(" was not found. Please check to see "+
								 "if the file is correct.");
			System.out.println(error);
			return 0;
		}
		
		while(fileReader.hasNextLine()){
			line = fileReader.nextLine();
			content = content.append(line);
		}
		
		System.out.println(content);
		return 1;
	}
	
	public static void main(String[] args){
		
		Scanner stdin = new Scanner(System.in);
		
		while (true){
			
			String inputFile = "";
			String outputFile = "";
			
			//Get input

			System.out.println("\n\nWhat would you like to do?");
			System.out.println("\n");
			System.out.println("\tA) Decrypt a file");
			System.out.println("\tB) Encrypt a file");
			System.out.println("\tC) View a file");
			System.out.println("\tD) Switch the files");
			System.out.println("\tE) Quit");
			System.out.println("\n");
			
			String choice = stdin.nextLine();

			//Do something depending on input
			switch(choice.toUpperCase()){
				case "A": case "DECRYPT": case "DECRYPT A FILE":
					//Test if default values need to be used
					if (inputFile.equals("")){
						if (outputFile.equals("")){
							decrypt(DEFAULT_ENCRYPTED_FILE, 
									DEFAULT_PLAINTEXT_FILE);
						}else{
							decrypt(DEFAULT_ENCRYPTED_FILE, outputFile);
						}
					}else{
						if (outputFile.equals("")){
							decrypt(inputFile, DEFAULT_PLAINTEXT_FILE);
						}else{
							decrypt(inputFile, outputFile);
						}
					}
					break;
				case "B": case "ENCRYPT": case "ENCRYPT A FILE":
					//Test if default values need to be used
					if (inputFile.equals("")){
						if (outputFile.equals("")){
							encrypt(DEFAULT_ENCRYPTED_FILE, 
									DEFAULT_PLAINTEXT_FILE);
						}else{
							encrypt(DEFAULT_ENCRYPTED_FILE, outputFile);
						}
					}else{
						if (outputFile.equals("")){
							encrypt(inputFile, DEFAULT_PLAINTEXT_FILE);
						}else{
							encrypt(inputFile, outputFile);
						}
					}
					break;
				case "V": case "C": case "VIEW": case "VIEW A FILE":
					System.out.println("What file would you like to view?\n");
					String fileToView = stdin.nextLine();
					view(fileToView);
					break;
				case "S": case "D": case "SWITCH": case "SWITCH THE FILES":
					System.out.println("What is the new input file?\n");
					String newInput = stdin.nextLine();
					System.out.println("\nWhat is the new output file?\n");
					String newOutput = stdin.nextLine();
					//Change files to new files
					inputFile = newInput;
					outputFile = newOutput;
					StringBuilder message = new StringBuilder("The input f"+
											"ile has been changed to: ");
					message = message.append(inputFile);
					message = message.append("\nThe output fil"+
										"e has been changed to: ");
					message = message.append(outputFile);

					System.out.println(message);

					break;
				case "Q": case "E": case "QUIT":
					System.exit(0);
					break;
				default:
					System.out.println("Command could not be found. Please try again");
					break;
			}
		}
		
	}
}
