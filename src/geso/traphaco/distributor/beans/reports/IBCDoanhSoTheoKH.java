package geso.traphaco.distributor.beans.reports;

import java.sql.ResultSet;

public interface IBCDoanhSoTheoKH {

	public void setUserId(String userId);
	public String getUserId();
	public void init();
	public void DBclose();
	

	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);

	public ResultSet getRsBCDoanhSoTheoKH();
	public void setRsBCDoanhSoTheoKH(ResultSet rsBCDoanhSoTheoKH);

	public String getNppID();
	public void setNppID(String nppID);

	public ResultSet getRsTDV();
	public void setRsTDV(ResultSet rsTDV);
	
	public String getTdvId();
	public void setTdvId(String tdvId);
	
	public String getNppTen();
	public void setNppTen(String nppTen);

	public String getTdvTen();
	public void setTdvTen(String tdvTen);
	
	public String getLoai();
	public void setLoai(String loai);
	
	public String getTt();
	public void setTt(String tt);
	
	public ResultSet getRsChiNhanh();

	public void setRsChiNhanh(ResultSet rsChiNhanh);

	public String getChinhanhId();

	public void setChinhanhId(String chinhanhId);

	public ResultSet getVung();
	public String getvungId();
	public void setVungId(String vungId);
	public ResultSet getKhuvuc();
	public String getkhuvucId();
	public void setKhuvucId(String khuvucId);
	public ResultSet getTinhthanh();
	public String getTinhthanhId();
	public void setTinhthanhId(String tinhthanhId);
	
	public ResultSet rsBCDoanhSoTheoKHGroup();
	
	public String getMucCN_DT();
	public void setMucCN_DT(String cndt);
	
	
	public String getMuc_KhachHang();
	public void setMuc_KhachHang(String kh);
	
	
}
