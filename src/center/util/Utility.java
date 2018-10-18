package center.util;

import java.io.Serializable;

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
}
