package qlcn.pages.thuchi.beans;

import java.sql.ResultSet;

import qlcn.center.util.IPhan_Trang;

public interface IThuChiList extends IPhan_Trang {
	public void init();
	public void createRs();
	public void chot(String id);
	public void unchot(String id);
	public void delete(String id);
	public void deleteDB(String pinUser);
	public void DBClose();
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getID();
	public void setID(String iD);
	
	public String getMsg();
	public void setMsg(String msg);

	public String getSoItems();
	public void setSoItems(String soItems);

	public String getLoai();
	public void setLoai(String loai);

	public String getNoidungthuchiId();
	public void setNoidungthuchiId(String noidungthuchiId);

	public ResultSet getThuchiRs();
	public void setThuchiRs(ResultSet thuchiRs);

	public ResultSet getNoidungthuchiRs();
	public void setNoidungthuchiRs(ResultSet noidungthuchiRs);
	
	public String getSotientu();
	public void setSotientu(String sotientu);

	public String getSotienden();
	public void setSotienden(String sotienden);
	
	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNoidung();
	public void setNoidung(String noidung);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getTaikhoanRs();
	public void setTaikhoanRs(ResultSet taikhoanRs);

	public String getTaikhoanId();
	public void setTaikhoanId(String taikhoanId);
}
