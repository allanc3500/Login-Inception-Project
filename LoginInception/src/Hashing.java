/*
	Hashing Class: Generates the hashing operations for 
	whatever input is in the argument of the function.
	ToHex converts the bytes to String to be displayed and be used.  
*/
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
	
	/*
		Converts input to bytes using SHA256 hashing. Returns the bytes.
	 */
	public static byte[] SHA256 (String input) throws NoSuchAlgorithmException{
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}
	
	/*
		Converts the bytes back to string to be used to verify usernames and passwords.
		If a byte conversion results in a string of only 1 character, a 0 is prepended.
		This is done to ensure there will always be 64 characters in the SHA256 hashing.
		Returns the 64 character length string that results from the conversion.
	 */
	public static String toHex(byte[] hash) {
		String hexString = "";
		for(int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) {
				hex = 0 + hex;
			}
			hexString = hexString + hex;
		}
		return hexString.toString();
	}

}
