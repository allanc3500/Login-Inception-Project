/*
	ResetPage Class: Implements the GUI for the ResetPage. Shows
	"Resetting password for this user" followed by the userID, whether it
	is the scientist, security, or engineer.
	Once the user enters to a password that they prefer, the database
	will update accordingly.  
*/
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ResetPage implements ActionListener {

	/*
		Elements to be used in the ResetPage Interface.
	 */
	JFrame frame = new JFrame();
	JLabel resetLabel = new JLabel("Reset your password here.");
	JPasswordField changePasswordField = new JPasswordField();
	JButton resetButton = new JButton("Reset Password");
	String userIdentification = "";
	int attemptsCountReset;
	
	
	/*
		Parameterized Constructor. Takes in 2 arguments, the userID to 
		be displayed and an attemptsCount. This carries the failed attempt 
		count so it can still be displayed on the Welcome page if there has
		been previous failed attempts.
		The parameterized constructor sets the boundaries and sizes of the frames, 
		text fields, reset button etc. The reset button triggers an ActionListener
		so an action can be performed.
	 */
	ResetPage(String userID, int attemptsCount) {
		
		userIdentification = userID;
		attemptsCountReset = attemptsCount;
		
		resetLabel.setBounds(0,0,200,35);
		resetLabel.setFont(new Font(null, Font.PLAIN, 20));
		resetLabel.setText("Resetting password for this user: " + userID);
		resetLabel.setSize(resetLabel.getPreferredSize());
	
		resetButton.setBounds(125, 200, 100, 25);
		resetButton.addActionListener(this);
		
		changePasswordField.setBounds(125, 150, 200, 25);
		changePasswordField.setEchoChar('*');
		
		frame.add(resetLabel);
		frame.add(resetButton);
		frame.add(changePasswordField);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	
	/*
	 	ActionListener to handle updating the password. The user will type
	 	a desired password in the text field, and this function will call
	 	the update function in the LoginManager to update it. This will update
	 	the password in the database. After it updates the database via the 
	 	LoginManager, the frame closes and returns back to the Welcome page 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == resetButton) {
			String userID = userIdentification;
			userIdentification = userIdentification+4078;
			String newPass = new String(changePasswordField.getPassword());


			try {
				LoginManager lm = new LoginManager();
				lm.update(userIdentification, newPass);
			} 
			catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.dispose();
			JOptionPane.showMessageDialog(null, "Password has been resetted", "Successful Reset", JOptionPane.INFORMATION_MESSAGE); 
			WelcomePage welcomePage = new WelcomePage(userID, attemptsCountReset);
		}
		
	}
	
	
	
	
}
