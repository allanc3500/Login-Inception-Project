/*
Allan Chung 
CEN 4078 Secure Software Development 
April 03, 2024

Main class: Runs the LoginManager and the LoginPage. 
*/
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


/*
 	Runs LoginManager and the LoginPage. The LoginPage uses the usernames and passwords stored 
	in the Hash Map from the LoginManager.
*/
public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

		LoginManager manager = new LoginManager();
		
		LoginPage loginPage = new LoginPage(manager.getUsersandPass());
		
	

	}

}
