package geso.traphaco.distributor.beans.duyetbandunggia;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface IDuyetbandunggiaNpp 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	
	public String getLoaict();
	public void setLoaict(String loaict);
	
	public String getThang();
	public void setThang(String thang);
	public String getQuy();
	public void setQuy(String quy);
	public String getNam();
	public void setNam(String nam);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getMsg();
	public void setMsg(String msg);

	public void setKhRs(ResultSet khRs);
    public ResultSet getKhRs();
    public String getKhIds();
	public void setKhIds(String khIds);
	public String getKh_QuyIds();
	public void setKh_QuyIds(String kh_QuyIds);
	
	public void setNppRs(ResultSet nppRs);
    public ResultSet getNppRs();
    public String getNppIds();
	public void setNppIds(String nppIds);
	
	public void setVungRs(ResultSet vungRs);
    public ResultSet getVungRs();
    public String getVungIds();
	public void setVungIds(String vungIds);
	
	public void setKhuvucRs(ResultSet kvRs);
    public ResultSet getKhuvucRs();
    public String getKhuvucIds();
	public void setKhuvucIds(String kvIds);

	public void init();
	public void createRs();
	
	public void init_display();
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public boolean createTctSKU();
	public boolean updateTctSKU();
	public boolean duyetTctSKU();
	
	public String getDuyetdunggia();
	public void setDuyetdunggia(String duyetdunggia);
	public String getKhongtinhtienthuve();
	public void setKhongtinhtienthuve(String khongtinhthuve);
	public String getKhongtinhdoanhso();
	public void setKhongtinhdoanhso(String khongtinhdoanhso);
	public ResultSet getExportRs();
	public ArrayList<String> getCkquy() ;

	public void setCkquy(ArrayList<String> ckquy) ;
	public String getKh_ckuh() ;

	public void setKh_ckuh(String kh_ckuh);

    
}
