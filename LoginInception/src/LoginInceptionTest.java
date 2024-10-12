import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;


public class LoginInceptionTest {
	
	/*Test 1: Hashing the password manually and checking if this password was contained 
	 * in the Database HashMap in LoginManager, confirming that the password was
	 * hashed and stored correctly in the database.
	 */
	@Test
    public void HashingPasswordTest() throws NoSuchAlgorithmException {
		LoginManager manager = new LoginManager();
		LoginPage loginPage = new LoginPage(manager.getUsersandPass());
		
		String scientistPassword = "4078scientist4078"; 
		String hashedPassword = "";
		
		
        try {
            hashedPassword = Hashing.toHex(Hashing.SHA256(scientistPassword));
        } 
        catch (NoSuchAlgorithmException e) {
            fail("NoSuchAlgorithmException occurred");
        }
        
        assertTrue(manager.usersandPass.containsValue(hashedPassword));
    }
	
	/*Test 2: Salting and then Hashing the username manually.
	 * Then checking if this username was contained 
	 * in the Database HashMap in LoginManager, which will confirm that the password was
	 * salted, hashed, and stored correctly in the database.
	 */
	@Test
    public void SaltandHashTest() throws NoSuchAlgorithmException {
		LoginManager manager = new LoginManager();
		LoginPage loginPage = new LoginPage(manager.getUsersandPass());
		
		String scientistUsername = "scientist";
		String saltedUsername = scientistUsername + 4078;
		String hashedUsername = "";
		
		
        try {
            hashedUsername = Hashing.toHex(Hashing.SHA256(saltedUsername));
        } 
        catch (NoSuchAlgorithmException e) {
            fail("NoSuchAlgorithmException occurred");
        }
        
        assertTrue(manager.usersandPass.containsKey(hashedUsername));
    }
	
	/*Test 3: Hashing the username and password manually to get the username and password combination
	 * and checking if this password was contained in the Database HashMap in LoginManager.
	 */
	@Test
    public void ContainsUsernameAndPasswordSetTest() throws NoSuchAlgorithmException {
		LoginManager manager = new LoginManager();
		LoginPage loginPage = new LoginPage(manager.getUsersandPass());
		
		String engineerUser = "963c32a6fd64b76179335532f61dd83cb303de9ea87f1764e0ef7bbb28cc409f";
		String engineerPass = "dc0d662780a20cef4e5d188bb4d023f1eda24bff1378edc25e4b97ef82d89fbd";
		String securityUser = "d12bb9ae526d90ae6989ef15dcd1234f759459b0cc5b189b1904f114f110c09a";
		String securityPass = "be000b1af46510984d3028256ca64d5972cb81b407cbd583d3955cbd401975e3";
		String scientistUser = "81ef569219fbebc1395f07c1c27ca23c586671302ffd3d586ccd5874317a4c2f";
		String scientistPass = "66dc517e4f12e5aabc7aeede4a3147265c3e813e4c5b0c38e88ccabaddea2064";
		
    	HashMap<String, String> testHashMap = new HashMap<>();
        testHashMap.put(engineerUser, engineerPass);
		testHashMap.put(securityUser, securityPass);
		testHashMap.put(scientistUser, scientistPass);
        
        
        assertTrue(manager.usersandPass.equals(testHashMap));
    }
	
	/*Test 4: Testing Secure Login. Secure Strategy: Logging a Failed Attempt. When a failed
	 * login attempt is made, the exact date and time is logged into the database, to ensure that
	 * there is monitoring of the login activity. We fortified the security of the system, 
	 * as we can now look out for suspicious behavior. Furthermore, because the accuracy of the date is confirmed,
	 *  we were able to protect the integrity of the login system.
	 */
	@Test
    public void SecureLoginTest() throws NoSuchAlgorithmException, IOException {
		LoginManager manager = new LoginManager();
		LoginPage loginPage = new LoginPage(manager.getUsersandPass());
		
		loginPage.usernameField.setText("scientist");
		loginPage.passwordField.setText("incorrectPassword");
		loginPage.loginButton.doClick();
        
		List<String> logLines = Files.readAllLines(Path.of("scientistRecentFails.txt"));
		StringBuilder stringBuilder = new StringBuilder();
		for(String line: logLines) {
			stringBuilder.append(line); 
		}
		
		String stringBuilderToString = stringBuilder.toString();
		
		String todaysDateAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        
        assertTrue(stringBuilderToString.contains(todaysDateAndTime));
    }
	
	/*Test 5: Resetting Password. Update changes the password in the database and in the HashMap.
	 * If it passed, it means the password is updated and the user is able to log in successfully.
	 */
	@Test
    public void ResetPasswordTest() throws NoSuchAlgorithmException, IOException {
		LoginManager manager = new LoginManager();
		LoginPage loginPage = new LoginPage(manager.getUsersandPass());
		
		String username = "scientist";
		String saltedUsername = username + 4078;
		String newPassword = "1";
		manager.update(saltedUsername, newPassword);
		
		loginPage.usernameField.setText("scientist");
		loginPage.passwordField.setText(newPassword);
		loginPage.loginButton.doClick();
				
        assertEquals(loginPage.passed, true);

		
    }

	/*
	 * Reset to defualt settings.
	 */
	@After
	public void resetToDefault() throws NoSuchAlgorithmException {
		LoginManager manager = new LoginManager();
		String username = "scientist";
		String saltedUsername = username + 4078;
		String defaultPassword = "4078scientist4078";
		manager.update(saltedUsername, defaultPassword);
	}
}
