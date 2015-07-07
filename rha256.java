
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class rha256 {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	String salt;
	String hash;
	public rha256(String password){
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-256");
			SecureRandom random = new SecureRandom();
		    byte bytes[] = new byte[30];
		    random.nextBytes(bytes);
		    salt = bytesToHex(bytes);
		    String hpass = bytesToHex(password.getBytes())+bytesToHex(bytes);
		    hash = bytesToHex(sha.digest(hpass.getBytes()));
		    
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public rha256(String password,String salt){
		this.salt = salt;
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-256");
		    byte bytes[] = hexStringToByteArray(salt);
		    String hpass = bytesToHex(password.getBytes())+bytesToHex(bytes);
		    hash = bytesToHex(sha.digest(hpass.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	  public static byte[] hexStringToByteArray(String s) {
		    byte[] b = new byte[s.length() / 2];
		    for (int i = 0; i < b.length; i++) {
		      int index = i * 2;
		      int v = Integer.parseInt(s.substring(index, index + 2), 16);
		      b[i] = (byte) v;
		    }
		    return b;
		  }
}
