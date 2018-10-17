package geso.traphaco.distributor.beans.chitieunpp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IChiTieuNhaPP extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getScheme();
	public void setScheme(String scheme);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void setSanphamRs(ResultSet spRs);
	public ResultSet getSanphamRs();
	public String getSpIds();
	public void setSpIds(String spIds);
	
	public void setNppRs(ResultSet nppRs);
	public ResultSet getNppRs();
	public String getNppIds();
	public void setNppIds(String nppIds);
	
	public Hashtable<String, String> getNpp_chitieu();
	public void setNpp_chitieu(Hashtable<String, String> npp_chitieu);
	
	public Hashtable<String, String> getNpp_donvi_chitieu();
	public void setNpp_donvi_chitieu(Hashtable<String, String> npp_donvi_chitieu);
	
	public void setVungRs(ResultSet vungRs);
	public ResultSet getVungRs();
	public String getVungIds();
	public void setVungIds(String vungIds);
	
	public void setKhuvucRs(ResultSet kvRs);
	public ResultSet getKhuvucRs();
	public String getKhuvucIds();
	public void setKhuvucIds(String kvIds);
	
	public String[] getTumuc();
	public void setTumuc(String[] tumuc);
	public String[] getDenmuc();
	public void setDenmuc(String[] denmuc);
	public String[] getThuong();
	public void setThuong(String[] thuong);
	public String[] getDonvi_tinh_ds();
	public void setDonvi_tinh_ds(String[] donvi_tinh_ds);
	public String[] getDonvi_tinh_thuong();
	public void setDonvi_tinh_thuong(String[] donvi_tinh_thuong);
	
	public void init();
	public void createRs();
	
	public boolean save();
	public boolean edit();
	
	public String getDoituong();
	public void setDoituong(String doituong);
	
	public String getQuy();
	public void setQuy(String quy);
	
	public String getApdung();
	public void setApdung(String apdung);
	
	public String getLoaichitieu();
	public void setLoaichitieu(String loaichitieu);
	
	public String getView();
	public void setView(String view);
	
}
