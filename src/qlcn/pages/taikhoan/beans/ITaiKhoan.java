package qlcn.pages.taikhoan.beans;

import java.sql.ResultSet;

public interface ITaiKhoan {
	public void init();
	public void createRS();
	public boolean create();
	public boolean update();
	public void DBClose();
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getID();
	public void setID(String iD);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getMsg();
	public void setMsg(String msg);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getSotien();
	public void setSotien(String sotien);

	public String getDonvi();
	public void setDonvi(String donvi);
	
	public ResultSet getDonviRs();
	public void setDonviRs(ResultSet donviRs);

//	public String getLoai();
//	public void setLoai(String loai);
}
