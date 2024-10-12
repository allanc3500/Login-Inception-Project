/*
	LoginPage Class: Implements the GUI for the LoginInception. Shows
					the login page interface. Displays username and password
					textboxes for the user to enter. Once the user is done,
					user can click login and the LoginInception
					will go through a verification process. There is also a 
					Clear button that clears all characters for convenience. 
*/
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



public class LoginPage implements ActionListener {

	/*
 		Elements to be used in the Login Page interface such as
 		JFrames, JButtons, JTextFields etc.
	 */
	JFrame frame = new JFrame();
	JButton loginButton = new JButton("Login");
	JButton clearButton = new JButton("Clear");
	JTextField usernameField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	
	JLabel usernameLabel = new JLabel("username: ");
	JLabel passwordLabel = new JLabel("password: ");
	JLabel messageLabel = new JLabel();
	boolean passed = false;
	int totalFailedAttempts;
	
	/* failAttempts is an ArrayList used to log an occurrence of
		a failed attempt.
	*/
	ArrayList<String> failedAttempts = new ArrayList<>();
	
	
	/* A copy of usernames and passwords to be used in the LoginPage class.*/
	HashMap<String, String> userandPass = new HashMap <String,String>();
	
	
	/*
	 	Parameterized Constructor. Uses the username and passwords
	 	Hash Map as an argument for verification purposes.
	 	The page sets coordinates for interface components and displays them.
	 */
	LoginPage(HashMap<String,String> userandPassOld){
		userandPass = userandPassOld;
		
		usernameLabel.setBounds(50, 100, 75, 25);
		passwordLabel.setBounds(50, 150, 75, 25);
		
		messageLabel.setBounds(0, 250, 250, 35);
		messageLabel.setFont(new Font(null,Font.PLAIN, 14));

		
		usernameField.setBounds(125, 100, 200, 25);
		passwordField.setBounds(125, 150, 200, 25);
		passwordField.setEchoChar('*');

		loginButton.setBounds(125, 200, 100, 25);
		loginButton.addActionListener(this);
		
		clearButton.setBounds(225, 200, 100, 25);
		clearButton.addActionListener(this);
		
		frame.add(usernameLabel);
		frame.add(passwordLabel);
		frame.add(messageLabel);
		frame.add(usernameField);
		frame.add(passwordField);
		frame.add(loginButton);
		frame.add(clearButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	/*
	 	ActionListener to handle when the Login or Clear button has been clicked.
	 	When the Clear button has been clicked, the username and password fields is
	 	set to an empty string.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == clearButton) {
			usernameField.setText("");
			passwordField.setText("");
		}
		
		if(e.getSource() == loginButton) {
			String userID = usernameField.getText();
			String password = String.valueOf(passwordField.getPassword());
			
			
			//if to Hex
			try {
				if(userandPass.containsKey(Hashing.toHex(Hashing.SHA256(userID + 4078)))){
					if(userandPass.get(Hashing.toHex(Hashing.SHA256(userID + 4078))).equals(Hashing.toHex(Hashing.SHA256(password)))) {
						messageLabel.setForeground(Color.green);
						messageLabel.setText("Login successful");
						frame.dispose();
						
						FileWriter writer;
						try {
							writer = new FileWriter("loginAttempts.txt", true);
							String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
							String attempt = date;
							writer.write(attempt + " " + userID + " is successful");
							writer.write("\n");
							writer.close();
						} 
						catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

						
						int count = 0;
						
						if(userID.matches("scientist")) {
							try {
								Scanner scan = new Scanner(new File("scientistRecentFails.txt"));
								while(scan.hasNext()) {
									scan.nextLine();
									count++;
								}
								try {
									FileWriter write = new FileWriter("scientistRecentFails.txt");
									write.write("");
									write.close();
								} 
								catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}		
							} 
							catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else if (userID.matches("engineer")) {
							try {
								Scanner scan = new Scanner(new File("engineerRecentFails.txt"));
								while(scan.hasNext()) {
									scan.nextLine();
									count++;
								}
								try {
									FileWriter write = new FileWriter("engineerRecentFails.txt");
									write.write("");
									write.close();
								} 
								catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}		
							} 
							catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
						}
						else if(userID.matches("security")) {
							try {
								Scanner scan = new Scanner(new File("securityRecentFails.txt"));
								while(scan.hasNext()) {
									scan.nextLine();
									count++;
								}
								try {
									FileWriter write = new FileWriter("securityRecentFails.txt");
									write.write("");
									write.close();
								} 
								catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}		
							} 
							catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
						totalFailedAttempts = count;
						passed = true;
						WelcomePage welcomePage = new WelcomePage(userID, totalFailedAttempts);
					}
					else {
						messageLabel.setForeground(Color.red);
						messageLabel.setText("Wrong password: This attempt is monitored and logged.");
						messageLabel.setSize(messageLabel.getPreferredSize());

						try {
							FileWriter writer = new FileWriter("loginAttempts.txt", true);
							String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
							String attempt = date;
							writer.write(attempt + " " + userID);
							writer.write("\n");
							writer.close();
							
							if(userID.matches("scientist")) {
								FileWriter scientistWriter = new FileWriter("scientistRecentFails.txt", true);
								scientistWriter.write(attempt);
								scientistWriter.write("\n");
								scientistWriter.close();
							}
							else if(userID.matches("engineer")) {
								FileWriter engineerWriter = new FileWriter("engineerRecentFails.txt", true);
								engineerWriter.write(attempt);
								engineerWriter.write("\n");
								engineerWriter.close();
							}
							else if (userID.matches("security")) {
								FileWriter securityWriter = new FileWriter("securityRecentFails.txt", true);
								securityWriter.write(attempt);
								securityWriter.write("\n");
								securityWriter.close();
							}
							failedAttempts.add(attempt);

							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				else {
					messageLabel.setForeground(Color.red);
					messageLabel.setText("Username not found");
				}
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
	}
	
}


