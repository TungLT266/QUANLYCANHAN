package geso.traphaco.distributor.beans.reports;

import java.sql.ResultSet;

public interface IBangKeTTThang {

	public void setUserId(String userId);
	public String getUserId();
	public void init();
	public void DBclose();
	

	public String getThang();
	public void setThang(String thang);

	public String getNam();
	public void setNam(String nam);

	public ResultSet getRsBangKeTTThang();
	public void setRsBangKeTTThang(ResultSet rsBangKeTTThang);

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
	
	public String getQuy();
	public void setQuy(String quy);

	public String getLoai();
	public void setLoai(String loai);

	public String getTuthang();
	public void setTuthang(String tuthang);

	public String getDenthang();
	public void setDenthang(String denthang);	
	
	public String getT1();
	public void setT1(String t1);
	public String getT2();
	public void setT2(String t2);
	public String getT3();
	public void setT3(String t3);
	
	public String getTt();
	public void setTt(String tt);
	
	public ResultSet getRsChiNhanh();

	public void setRsChiNhanh(ResultSet rsChiNhanh);

	public String getChinhanhId();

	public void setChinhanhId(String chinhanhId);

	
	
}
