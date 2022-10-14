// Author: Nikolas Leslie
// Date created: 10/13/2022
// Last modified: 10/14/2022
// Decrypts a given file
// Error count: 36

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

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
		StringBuilder message = new StringBuilder("Your decrypted message is: \n\n");
		StringBuilder fileMessage = new StringBuilder();
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
			value = fileReader.nextInt();
			message = message.append((char) inverseFormula(value));
			fileMessage = fileMessage.append((char) inverseFormula(value));
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
		
		message = message.append("\n\nYour message has also been saved in ");
		message = message.append(plainTextFile);
		
		fileWriter.println(fileMessage);
		JOptionPane.showMessageDialog(null, message, "Decrypt", JOptionPane.INFORMATION_MESSAGE);
		
		fileWriter.close();
		return 1;
	
	}
	
	static int encrypt(String inputFile, String outputFile){
		
		File input = null;
		Scanner fileReader = null;
		PrintWriter fileWriter = null;
		StringBuilder message = new StringBuilder();
		StringBuilder encryptedMessage = new StringBuilder("Your encrypted message is:\n\n");
		StringBuilder encryptedFile = new StringBuilder();
		String line = "";
		char[] charsInMessage;
		
		try{
			input = new File(inputFile);
			fileReader = new Scanner(input);
		}catch(FileNotFoundException e){
			StringBuilder error = new StringBuilder("The file ");
			error = error.append(inputFile);
			error = error.append(" was not found. Please check to see if the file is correct.");
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		while(fileReader.hasNextLine()){
			line = fileReader.nextLine();
			message = message.append(line);
		}
		
		charsInMessage = message.toString().toCharArray();
		
		for(char letter : charsInMessage){
			encryptedMessage = encryptedMessage.append(formula((int) letter));
			encryptedMessage = encryptedMessage.append(" ");
			encryptedFile = encryptedFile.append(formula((int) letter));
			encryptedFile = encryptedFile.append(" ");
		}
		
		try{
			fileWriter = new PrintWriter(outputFile);
		}catch(FileNotFoundException e){
			StringBuilder error = new StringBuilder("The file ");
			error = error.append(outputFile);
			error = error.append(" was not found. Please check to see if the file is correct.");
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		encryptedMessage = encryptedMessage.append("\n\nYour encrypted message has also been saved in ");
		encryptedMessage = encryptedMessage.append(outputFile);
		
		fileWriter.println(encryptedFile);
		JOptionPane.showMessageDialog(null, encryptedMessage, "Encrypt", JOptionPane.INFORMATION_MESSAGE);
		
		fileWriter.close();
		return 1;
		
	}
	
	static int view(String file){
		
		File fileToView = null;
		Scanner fileReader = null;
		String line = "";
		StringBuilder content = new StringBuilder("The file ");
		content = content.append(file);
		content = content.append(" contains this message:\n\n");
		
		try{
			fileToView = new File(file);
			fileReader = new Scanner(fileToView);
		}catch(FileNotFoundException e){
			StringBuilder error = new StringBuilder("The file ");
			error = error.append(file);
			error = error.append(" was not found. Please check to see if the file is correct.");
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		while(fileReader.hasNextLine()){
			line = fileReader.nextLine();
			content = content.append(line);
		}
		
		JOptionPane.showMessageDialog(null, content, "View", JOptionPane.INFORMATION_MESSAGE);
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
		decryptButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){choiceLabel.setText("d");}});
		JButton encryptButton = new JButton("Encrypt a file");
		encryptButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){choiceLabel.setText("e");}});
		JButton viewButton = new JButton("View a file");
		viewButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){choiceLabel.setText("v");}});
		JButton changeButton = new JButton("Change files");
		changeButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){choiceLabel.setText("c");}});
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){choiceLabel.setText("q");}});
		
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
		homePanel.add(quitButton);
		
		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.PAGE_AXIS));
		viewPanel.add(new JLabel("What file would you like to view?"));
		viewPanel.add(Box.createVerticalStrut(15));
		viewPanel.add(viewFileField);
		
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
			JOptionPane.showMessageDialog(null, homePanel, "Decipher", JOptionPane.QUESTION_MESSAGE);
			choice = choiceLabel.getText();
			switch(choice){
				case "d":
					if (inputFile.equals("")){
						if (outputFile.equals("")){
							decrypt(DEFAULT_ENCRYPTED_FILE, DEFAULT_PLAINTEXT_FILE);
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
					if (inputFile.equals("")){
						if (outputFile.equals("")){
							encrypt(DEFAULT_ENCRYPTED_FILE, DEFAULT_PLAINTEXT_FILE);
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
						JOptionPane.showMessageDialog(null, viewPanel, "View", JOptionPane.QUESTION_MESSAGE);
					}catch(Exception e){
						System.exit(0);
					}
					view(viewFileField.getText());
					break;
				case "c":
					try{
						JOptionPane.showMessageDialog(null, changePanel, "Change files", JOptionPane.QUESTION_MESSAGE);
					}catch(Exception e){
						System.exit(0);
					}
					inputFile = inputFileField.getText();
					outputFile = outputFileField.getText();
					StringBuilder message = new StringBuilder("The input file has been changed to: ");
					message = message.append(inputFile);
					message = message.append("\nThe output file has been changed to: ");
					message = message.append(outputFile);
					try{
						JOptionPane.showMessageDialog(null, message, "Change files", JOptionPane.INFORMATION_MESSAGE);
					}catch(Exception e){
						System.exit(0);
					}
					break;
				case "q":
					System.exit(0);
					break;
				default:
					try{
						JOptionPane.showMessageDialog(null, "Something went wrong", "Error", JOptionPane.WARNING_MESSAGE);
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
