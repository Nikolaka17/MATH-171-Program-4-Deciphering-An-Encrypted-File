// Author: Nikolas Leslie
// Date created: 10/13/2022
// Last modified: 10/14/2022
// Decrypts a given file

/*
To select on option on the initial screen, click the option's button then click ok
The frame formatting is bad so sometimes it might be better to look at the file to find the result
*/

//Util imports
import java.util.Scanner;

//Io imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//Swing imports
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;

//Awt imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			JOptionPane.showMessageDialog(null, error, "Error", 
										  JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, error, "Error", 
										  JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		message = message.append("\n\nYour message has also been saved in ");
		message = message.append(plainTextFile);
		
		fileWriter.println(fileMessage);
		JOptionPane.showMessageDialog(null, message, "Decrypt", 
									  JOptionPane.INFORMATION_MESSAGE);
		
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
			JOptionPane.showMessageDialog(null, error, "Error", 
										  JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, error, "Error", 
										  JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		encryptedMessage = encryptedMessage.append("\n\nYour encrypted messag"+
												   "e has also been saved in ");
		encryptedMessage = encryptedMessage.append(outputFile);
		
		fileWriter.println(encryptedFile);
		JOptionPane.showMessageDialog(null, encryptedMessage, "Encrypt", 
									  JOptionPane.INFORMATION_MESSAGE);
		
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
			JOptionPane.showMessageDialog(null, error, "Error", 
										  JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		while(fileReader.hasNextLine()){
			line = fileReader.nextLine();
			content = content.append(line);
		}
		
		JOptionPane.showMessageDialog(null, content, "View", 
									  JOptionPane.INFORMATION_MESSAGE);
		return 1;
	}
	
	public static void main(String[] args){
		
		String choice = null;
		JLabel choiceLabel = new JLabel("h");
		String inputFile = "";
		String outputFile = "";
		JTextField outputFileField = new JTextField(DEFAULT_ENCRYPTED_FILE);
		JTextField inputFileField = new JTextField(DEFAULT_PLAINTEXT_FILE);
		JTextField viewFileField = new JTextField();
		JButton decryptButton = new JButton("Decrypt a file");
		decryptButton.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent e){choiceLabel.setText("d");}});
		JButton encryptButton = new JButton("Encrypt a file");
		encryptButton.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent e){choiceLabel.setText("e");}});
		JButton viewButton = new JButton("View a file");
		viewButton.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent e){choiceLabel.setText("v");}});
		JButton changeButton = new JButton("Change files");
		changeButton.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent e){choiceLabel.setText("c");}});
		
		//Set up main panel
		JPanel homePanel = new JPanel();
		homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.PAGE_AXIS));
		homePanel.add(new JLabel("What would you like to do?"));
		homePanel.add(Box.createVerticalStrut(15));
		homePanel.add(decryptButton);
		homePanel.add(Box.createVerticalStrut(15));
		homePanel.add(encryptButton);
		homePanel.add(Box.createVerticalStrut(15));
		homePanel.add(viewButton);
		homePanel.add(Box.createVerticalStrut(15));
		homePanel.add(changeButton);
		homePanel.add(Box.createVerticalStrut(15));
		
		//Set up panel for view function
		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.PAGE_AXIS));
		viewPanel.add(new JLabel("What file would you like to view?"));
		viewPanel.add(Box.createVerticalStrut(15));
		viewPanel.add(viewFileField);
		
		//Set up panel for change file function
		JPanel changePanel = new JPanel();
		changePanel.setLayout(new BoxLayout(changePanel, BoxLayout.PAGE_AXIS));
		changePanel.add(new JLabel("Enter the new files:"));
		changePanel.add(Box.createVerticalStrut(15));
		changePanel.add(new JLabel("Input file:"));
		changePanel.add(inputFileField);
		changePanel.add(Box.createVerticalStrut(15));
		changePanel.add(new JLabel("Output file:"));
		changePanel.add(outputFileField);
		
		while (true){
			//Get input
			try{
				int test1 = JOptionPane.showConfirmDialog(null, homePanel, 
								"Decipher", JOptionPane.OK_CANCEL_OPTION,
										  JOptionPane.QUESTION_MESSAGE);
				if (test1 != JOptionPane.OK_OPTION){
					System.exit(0);
				}
			}catch(Exception e){
				System.exit(0);
			}
			choice = choiceLabel.getText();
			
			//Do something depending on input
			switch(choice){
				case "d":
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
				case "e":
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
				case "v":
					try{
						int test3 = JOptionPane.showConfirmDialog(null, viewPanel, 
												"View", 
												JOptionPane.OK_CANCEL_OPTION,
												JOptionPane.QUESTION_MESSAGE);
						if (test3 != JOptionPane.OK_OPTION){
							System.exit(0);
						}
					}catch(Exception e){
						System.exit(0);
					}
					view(viewFileField.getText());
					break;
				case "c":
					try{
						int test2 = JOptionPane.showConfirmDialog(null, 
									changePanel, "Change files", 
									JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (test2 != JOptionPane.OK_OPTION){
							System.exit(0);
						}
					}catch(Exception e){
						System.exit(0);
					}
					//Change files to new files
					inputFile = inputFileField.getText();
					outputFile = outputFileField.getText();
					StringBuilder message = new StringBuilder("The input f"+
											"ile has been changed to: ");
					message = message.append(inputFile);
					message = message.append("\nThe output fil"+
										"e has been changed to: ");
					message = message.append(outputFile);
					try{
						JOptionPane.showMessageDialog(null, message, 
							"Change files", JOptionPane.INFORMATION_MESSAGE);
					}catch(Exception e){
						System.exit(0);
					}
					break;
				default:
					try{
						JOptionPane.showMessageDialog(null, 
						"Something went wrong", "Error", 
						JOptionPane.WARNING_MESSAGE);
					}catch(Exception e){
						System.exit(0);
					}
					break;
			}
			choice = "h";
			choiceLabel.setText("h");
		}
		
	}
}
