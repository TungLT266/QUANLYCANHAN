package qlcn.pages.taikhoanthanhtoan.beans;

import java.sql.ResultSet;

public interface ITaiKhoanThanhToan {
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

	public String getLoai();
	public void setLoai(String loai);

	public String getTaikhoan();
	public void setTaikhoan(String taikhoan);

	public ResultSet getTaikhoanRs();
	public void setTaikhoanRs(ResultSet taikhoanRs);
	
	public String getLoaithe();
	public void setLoaithe(String loaithe);

	public String getSothe();
	public void setSothe(String sothe);

	public String getMapin();
	public void setMapin(String mapin);

	public String getTenchuthe();
	public void setTenchuthe(String tenchuthe);

	public String getChuky();
	public void setChuky(String chuky);
	
	public String getThanghieuluc();
	public void setThanghieuluc(String thanghieuluc);

	public String getNamhieuluc();
	public void setNamhieuluc(String namhieuluc);

	public String getThanghethan();
	public void setThanghethan(String thanghethan);

	public String getNamhethan();
	public void setNamhethan(String namhethan);
}
