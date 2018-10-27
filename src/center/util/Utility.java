package center.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utility implements Serializable {
	private static final long serialVersionUID = 1L;

	public String getUserId(String querystring) {
		String userId;
		String tmp;
		if (querystring != null) {
			if (querystring.contains("&")) {
				tmp = querystring.split("&")[0];
			} else {
				tmp = querystring;
			}

			userId = tmp.split("=")[1];
			if (userId.contains(";"))
				userId = userId.split(";")[0];
		} else {
			userId = "";
		}
		return userId;
	}
	
	public String getAction(String querystring) {
		String action;
		String tmp;
		if (querystring != null) {
			if (querystring.contains("&")) {
				tmp = querystring.split("&")[1];
				action = tmp.split("=")[0];
			} else {
				action = "";
			}
		} else {
			action = "";
		}
		return action;
	}
	
	public String getId(String querystring) {
		String id;
		String tmp;
		if (querystring != null) {
			if (querystring.contains("&")) {
				tmp = querystring.split("&")[1];
				id = tmp.split("=")[1];
			} else {
				id = "";
			}
		} else {
			id = "";
		}
		return id;
	}
	
	public String encrypt(String plaintext) {
		MessageDigest msgDigest = null;
        String hashValue = null;
        try {
            msgDigest = MessageDigest.getInstance("SHA-1");
            msgDigest.update(plaintext.getBytes("UTF-8"));
            byte rawByte[] = msgDigest.digest();
            
            StringBuffer hexString = new StringBuffer();
        	for (int i=0;i<rawByte.length;i++) {
        	  hexString.append(Integer.toHexString(0xFF & rawByte[i]));
        	}

            hashValue = hexString.toString();
 
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm Exists");
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Encoding Is Not Supported");
        }
        return hashValue;
    }
}
