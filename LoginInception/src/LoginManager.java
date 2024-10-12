/*
	LoginManager Class: A hash map is created to obtain the usernames and passwords.
						This is then stored on the database. There is an update function
						that updates the database accordingly for situations such as password
						reset.			
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

public class LoginManager {
	
	HashMap<String, String> usersandPass = new HashMap<String, String>();
	
	/*
		Default Constructor. Creates the database file and stores the usernames and passwords
		obtained by the usersandPass Hash Map.
		Try Catch is used to handle appropriate error control.
	 */
	LoginManager() throws NoSuchAlgorithmException {
		
		String file = "database.txt";	
		Scanner scanner;
		try {
			scanner = new Scanner(new File(file));
			while(scanner.hasNext()) {
				String user = scanner.next();
				String pass = scanner.next();
				usersandPass.put(user, pass); 
			}
		} 
		catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}						
	}

	/*
		Returns the usersandPass Hash Map.
	 */
	protected HashMap getUsersandPass(){
		return usersandPass;
	}

	
	/*
		Updates password. Removes the username and old password from the usersandPass Hash Map
		Adds the new username and password pair.
		Update the database by writing the usernames and passwords to the database with the HashMap 
		that includes the new password. Try Catch is implemented in case an error occurs.
	 */
	protected void update(String userIdentification, String newPass) throws NoSuchAlgorithmException {	
		usersandPass.remove(userIdentification);
		usersandPass.put(Hashing.toHex(Hashing.SHA256(userIdentification)), Hashing.toHex(Hashing.SHA256(newPass)));

		FileWriter writer;
		try {
			writer = new FileWriter("database.txt");
						
			for(HashMap.Entry<String, String> map : usersandPass.entrySet()) {
				String key = map.getKey();
				String value = map.getValue();
				writer.write(key);
				writer.write("\n");
				writer.write(value);
				writer.write("\n");

			}
			writer.close();
					
		} 
		catch (IOException e) {	
			e.printStackTrace();
		}				
	}
}
