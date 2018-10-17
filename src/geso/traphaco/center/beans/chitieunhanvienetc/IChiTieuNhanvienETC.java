package geso.traphaco.center.beans.chitieunhanvienetc;

import java.sql.ResultSet;

public interface IChiTieuNhanvienETC 
{
	public void setUserId(String userid);
	public String getUserId();
	
	public void setID(String id);
	public String getID();
	
	public String getThang() ;
	public void setThang(String thang);
	public void setQuy(String thang);
	public String getQuy();
	public void setNam(String nam);
	public String getNam();
	
	public void setDienGiai(String diengiai);
	public String getDienGiai();

	public String getDisplay();
	public void setDisplay(String loai);

	public String getLoai();
	public void setLoai(String loai);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public void setMessage(String strmessage);
	public String getMessage();
	
	public boolean Create();
	public boolean Update();
	public boolean Delete();
	public boolean Chot();
	public boolean UnChot();
	
	public void init();
	public void initCtLict(String search);
	public void createRs();
	
	public void closeDB();

	public ResultSet getChitieuRs();
	public ResultSet getChitieuListRs();
	
	public String[] getCodeTDV();
	public void setCodeTDV(String[] codeTDV );
	public String[] getTenTDV();
	public void setTenTDV(String[] tenTDV);
	public String[] getHangchienluoc();
	public void setHangchienluoc(String[] hangchienluoc);
	public String[] getHangdactri();
	public void setHangdactri(String[] hangdactri);
	public String[] getTongdskhoan();
	public void setTongdskhoan(String[] tongdskhoan);
	public String[] getKPIChienluoc();
	public void setKPIChienluoc(String[] kpiChienluoc);
	public String[] getKPIDactri();
	public void setKPIDactri(String[] kpiDactri);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	
	//REPORT
	public String getLoainhanvien();
	public void setLoainhanvien(String loainhanvien);
	
	public String getNhanvienId();
	public void setNhanvienId(String nhanvienId);
	public ResultSet getNhanvienRs();
	public void setNhanvienRs(ResultSet nhanvienRs);
	
	
}
