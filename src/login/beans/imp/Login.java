package login.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import login.beans.ILogin;
import db.Dbutils;

public class Login implements ILogin {
	private String userId;
	private String username;
	private String password;
	private String msg;
	
	private Dbutils db;
	
	public Login() {
		this.username = "";
		this.password = "";
		this.msg = "";
		
		this.db = new Dbutils();
	}
	
	public boolean login() {
		String query = "select ID from NGUOIDUNG where USERNAME = '" + this.username + "' and PASSWORD = '" + this.password + "'";
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try {
				rs.next();
				this.userId = rs.getString("ID");
				rs.close();
				
				if (this.userId.trim().length() > 0) {
					return true;
				} else {
					this.msg = "Tài khoản không đúng";
					return false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				this.msg = "Hệ thống xảy ra lỗi, vui lòng đăng nhập lại sau.";
				e.printStackTrace();
				return false;
			}
		} else {
			this.msg = "Tài khoản không đúng";
			return false;
		}
	}
	
	public void DBClose() {
		try {
			this.db.shutDown();
		} catch (Exception e) {}
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
