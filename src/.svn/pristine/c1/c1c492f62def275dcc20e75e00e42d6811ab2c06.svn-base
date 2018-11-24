package qlcn.center.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;

public class Utility implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int CREATE = 0;
	public static final int DELETE = 1;
	public static final int EDIT = 2;
	public static final int XEM = 3;
	public static final int CHOT = 4;
	public static final int HUYCHOT = 5;
	public static final int HIENTHIALL = 6;
	public static final int EXPORTEXCEL = 7;
	public static final int DBDELETE = 8;
	
//	public static final int CHUYEN = 6;
//	public static final int SMS = 7;
//	public static final int FAX = 8;
	
	/*public String getUserId(String querystring) {
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
	}*/
	
	/*public String getAction(String querystring) {
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
	}*/
	
	/*public String getId(String querystring) {
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
	}*/
	
	public boolean check(String userId, String userIdSS) {
		if(userId != null && userIdSS != null) {
			if(userId.trim().length() > 0 && userIdSS.trim().length() > 0) {
				if(userId.equals(userIdSS)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String antiSQLInspection(String param) {
		String tmp = param;
		String[] keywords = {"'"};
		String[] keywordsReplace = {"''"};
		
		if(tmp != null){
//			tmp = tmp.toLowerCase();
			for (int i = 0; i < keywords.length; i++){
				if(tmp.contains(keywords[i])){
					tmp = tmp.replaceAll(keywords[i], keywordsReplace[i]);
				}
			}
		}
		
		return tmp;
	}
	
	private static char[] SPECIAL_CHARACTERS = { ' ', '!', '"', '#', '$', '%',
			'*', '+', ',', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^',
			'`', '|', '~', 'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò',
			'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê',
			'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ', 'đ',
			'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ',
			'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ',
			'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế',
			'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị',
			'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ', 'Ỗ', 'ỗ', 'Ộ',
			'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ',
			'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự', 'Ỹ',
			'Ý', 'Ỳ', 'Ỵ', 'ỳ', 'ỵ', 'ý', 'ỹ' };
	
	private static char[] REPLACEMENTS = { '-', '\0', '\0', '\0', '\0', '\0',
			'\0', '_', '\0', '_', '\0', '\0', '\0', '\0', '\0', '\0', '_',
			'\0', '\0', '\0', '\0', '\0', 'A', 'A', 'A', 'A', 'E', 'E', 'E',
			'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a', 'a', 'a',
			'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A',
			'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a',
			'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
			'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e',
			'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'I',
			'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
			'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
			'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
			'U', 'u', 'Ỹ', 'Y', 'Y', 'Y', 'y', 'y', 'y', 'y' };
	
	public String replaceAEIOU(String s) {
		int maxLength = Math.min(s.length(), 236);
		char[] buffer = new char[maxLength];
		int n = 0;
		for (int i = 0; i < maxLength; i++) {
			char ch = s.charAt(i);
			int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
			if (index >= 0) {
				buffer[n] = REPLACEMENTS[index];
			} else {
				buffer[n] = ch;
			}
			// skip not printable characters
			if (buffer[n] > 31) {
				n++;
			}
		}

		// skip trailing slashes
		while (n > 0 && buffer[n - 1] == '/') {
			n--;
		}

		String kq = String.valueOf(buffer, 0, n);
		kq = kq.replaceAll("---", "-");
		kq = kq.replaceAll("--", "-");
		kq = kq.replaceAll("--", "-");

		return kq;
	}
	
	public String encrypt(String plaintext) {
		MessageDigest msgDigest = null;
		String hashValue = null;
		try {
			msgDigest = MessageDigest.getInstance("SHA-1");
			msgDigest.update(plaintext.getBytes("UTF-8"));
			byte rawByte[] = msgDigest.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < rawByte.length; i++) {
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
	
	public String EncodeString64(String str) {
		byte[] message;
		String encoded = "";
		try {
			message = str.getBytes("UTF-8");
			encoded = DatatypeConverter.printBase64Binary(message);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encoded;
	}

	public String DecodeString64(String str) {
		byte[] message;
		String decoded = "";
		try {
			message = DatatypeConverter.parseBase64Binary(str);
			decoded = new String(message, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decoded;
	}
}
