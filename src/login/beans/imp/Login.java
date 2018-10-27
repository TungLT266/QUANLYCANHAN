package login.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import qlcn.center.util.Utility;
import login.beans.ILogin;
import db.Dbutils;

public class Login implements ILogin {
	private String userTen;
	private String userId;
	private String username;
	private String password;
	private String msg;
	
	private Dbutils db;
	private Utility util;
	
	public Login() {
		this.username = "";
		this.password = "";
		this.msg = "";
		
		this.db = new Dbutils();
		this.util = new Utility();
	}
	
	public boolean login() {
		String query = "select ID, TEN from NGUOIDUNG where USERNAME = '" + this.username + "' and PASSWORD = '" + this.util.encrypt(this.password) + "' and trangthai = 1";
		ResultSet rs = this.db.get(query);
		try {
			if(rs.next()){
				this.userTen = rs.getString("TEN");
				this.userId = rs.getString("ID");
				rs.close();
				return true;
			} else {
				this.msg = "Tài khoản không đúng";
				rs.close();
				return false;
			}
		} catch (SQLException e) {
			this.msg = "Lỗi hệ thống.";
			e.printStackTrace();
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

	public String getUserTen() {
		return userTen;
	}

	public void setUserTen(String userTen) {
		this.userTen = userTen;
	}
	
}
