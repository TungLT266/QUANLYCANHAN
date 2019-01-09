package qlcn.pages.chuyentien.beans;

import java.sql.ResultSet;

public interface IChuyenTien {
	public void init();
	public void createRs();
	public boolean create();
	public boolean update();
	public void DBClose();
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getID();
	public void setID(String iD);
	
	public String getMsg();
	public void setMsg(String msg);

	public String getNgay();
	public void setNgay(String ngay);

	public String getNoidung();
	public void setNoidung(String noidung);

	public String getTaikhoanchuyenId();
	public void setTaikhoanchuyenId(String taikhoanchuyenId);

	public String getSotienchuyen();
	public void setSotienchuyen(String sotienchuyen);

	public String getDonvichuyen();
	public void setDonvichuyen(String donvichuyen);

	public String getTaikhoannhanId();
	public void setTaikhoannhanId(String taikhoannhanId);

	public String getSotiennhan();
	public void setSotiennhan(String sotiennhan);

	public String getDonvinhan();
	public void setDonvinhan(String donvinhan);

	public String getTkphi();
	public void setTkphi(String tkphi);

	public String getPhi();
	public void setPhi(String phi);

	public ResultSet getTaikhoanRs();
	public void setTaikhoanRs(ResultSet taikhoanRs);
	
	public ResultSet getTaikhoannhanRs();
	public void setTaikhoannhanRs(ResultSet taikhoannhanRs);
	
	public String getAction();
	public void setAction(String action);
}
