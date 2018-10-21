package qlcn.pages.taikhoan.beans;

import java.sql.ResultSet;

import qlcn.center.util.IPhan_Trang;

public interface ITaiKhoanList extends IPhan_Trang {
	public void init();
	public void delete(String id);
	public void deleteDB();
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

	public String getSoItems();
	public void setSoItems(String soItems);

//	public String getLoai();
//	public void setLoai(String loai);

	public ResultSet getTaikhoanRs();
	public void setTaikhoanRs(ResultSet taikhoanRs);
}
