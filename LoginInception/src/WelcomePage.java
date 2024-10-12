/*
	WelcomePage Class: Implements the GUI for the Welcome page. Displays a
	basic page that displays the user. Includes two buttons for the user to use.
	One is the option to reset passwords and the other button is log out.
	The Reset Password button will take the user to the ResetPage so the user 
	can reset their password. The Logout button will direct the user back to the LoginPage
	so different user can log in.  
*/
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WelcomePage implements ActionListener{

	/*
 		Elements to be used in the WelcomePage such as the JFrames, JLabels,
 		and JButtons. JFrames to display the frame, JLabels to display text, 
 		and JButtons are buttons for the user to click and use. 
	 */
	JFrame frame = new JFrame();
	JLabel welcomeLabel = new JLabel();
	JButton resetButton = new JButton("Reset Password");
	JButton logoutButton = new JButton("Logout");
	JLabel attemptsLabel = new JLabel();
	String userIdentification = "";
	int attemptsCount;
	
	WelcomePage(String userID, int count){
		
		userIdentification = userID;
		attemptsCount = count;
		
		welcomeLabel.setBounds(90,0,200,35);
		welcomeLabel.setFont(new Font(null, Font.PLAIN, 25));
		welcomeLabel.setText("Welcome " + userID);
		welcomeLabel.setSize(welcomeLabel.getPreferredSize());

		if(count > 0) {
			String message = "There has been " + count + " failed attempts before this successful login.";			
			attemptsLabel.setText(message);
			attemptsLabel.setBounds(10,300,200,35);
			attemptsLabel.setFont(new Font(null, Font.PLAIN, 12));
			attemptsLabel.setSize(attemptsLabel.getPreferredSize());
			frame.add(attemptsLabel);
		}
		resetButton.setBounds(25, 180, 100, 25);
		resetButton.addActionListener(this);
		resetButton.setSize(resetButton.getPreferredSize());
		
		logoutButton.setBounds(300, 180, 100, 25);
		logoutButton.addActionListener(this);
		logoutButton.setSize(logoutButton.getPreferredSize());

		
		frame.add(welcomeLabel);
		frame.add(resetButton);
		frame.add(logoutButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	/* 
		ActionListeners for the Reset Password and Logout buttons. 
		the Reset Password button will direct the user to set a new password
		and the Logout button will return the user back to the Login Page. 
	*/
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == resetButton) {
			frame.dispose();
			ResetPage resetPage = new ResetPage(userIdentification, attemptsCount);

		}
		
		if(e.getSource() == logoutButton) {
			frame.dispose();
			LoginManager idandPasswords;
			try {
				idandPasswords = new LoginManager();
				LoginPage loginPage = new LoginPage(idandPasswords.getUsersandPass());
			} 
			catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
		
}
