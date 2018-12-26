package qlcn.pages.vayno.beans;

import java.sql.ResultSet;

public interface IVayNo {
	public void init();
	public void createRS();
	public boolean create();
	public boolean update();
	public boolean nhantra();
	public void DBClose();
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getID();
	public void setID(String iD);
	
	public String getMsg();
	public void setMsg(String msg);

	public String getNgay();
	public void setNgay(String ngay);

	public String getSotien();
	public void setSotien(String sotien);

	public String getLoai();
	public void setLoai(String loai);

	public String getNguoivayno();
	public void setNguoivayno(String nguoivayno);

	public String getNoidung();
	public void setNoidung(String noidung);

	public String getGhichu();
	public void setGhichu(String ghichu);

	public String getNgaytra();
	public void setNgaytra(String ngaytra);

	public String getPhi();
	public void setPhi(String phi);

	public ResultSet getTaikhoanRs();
	public void setTaikhoanRs(ResultSet taikhoanRs);
	
	public String getDonvi();
	public void setDonvi(String donvi);
	
	public String getTaikhoanId();
	public void setTaikhoanId(String taikhoanId);

	public String getTaikhoannhantra();
	public void setTaikhoannhantra(String taikhoannhantra);
	
	public ResultSet getTaikhoanNhantraRs();
	public void setTaikhoanNhantraRs(ResultSet taikhoanNhantraRs);
	
	public String getAction();
	public void setAction(String action);
	
	public String getGhichu2();
	public void setGhichu2(String ghichu2);
}
