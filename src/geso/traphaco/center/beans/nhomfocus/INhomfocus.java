package geso.traphaco.center.beans.nhomfocus;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface INhomfocus
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
	
	// Cac Muc 1 thang
	public String[] getDiengiaiMuc();
	public void setDiengiaiMuc(String[] diengiai);
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
	

	
	// Cac Muc 3 thang
	public String[] getDiengiaiMuc3();
	public void setDiengiaiMuc3(String[] diengiai);
	public String[] getTumuc3();
	public void setTumuc3(String[] tumuc);
	public String[] getDenmuc3();
	public void setDenmuc3(String[] denmuc);
	public String[] getThuongSR3();
	public void setThuongSR3(String[] thuongSR);
	public String[] getThuongTDSR3();
	public void setThuongTDSR3(String[] thuongTDSR);
	public String[] getThuongSS3();
	public void setThuongSS3(String[] thuongSS);
	public String[] getThuongTDSS3();
	public void setThuongTDSS3(String[] thuongTDSS);
	public String[] getThuongASM3();
	public void setThuongASM3(String[] thuongASM);
	public String[] getThuongTDASM3();
	public void setThuongTDASM3(String[] thuongTDASM);
	
	public String getMucvuot();
	public void setMucvuot(String mucvuot);
	public String getChietkhauMucvuot();
	public void setChietkhauMucvuot(String ckMv);
	public String getDonviMucvuot();
	public void setDonviMucvuot(String dvMv);
	
	public void init();
	public void createRs();
	
	public boolean save();
	public boolean edit();
	
	// San pham tra
	public String getHinhthuctra();
	public void setHinhthuctra(String htTra);
	
	public String[] getMaspTra();
	public void setMaspTra(String[] maspTra);
	public String[] getTenspTra();
	public void setTenspTra(String[] tenspTra);
	public String[] getSoluongspTra();
	public void setSoluongspTra(String[] soluongspTra);
	public String[] getIdspTra();
	public void setIdspTra(String[] idspTra);
	
	// /////////////////////////
	
	public String getDoanhsotheoThung();
	public void setDoanhsotheoThung(String isThung);
	
	public String getDoituong();
	public void setDoituong(String doituong);
	
	public String getQuy();
	public void setQuy(String quy);
	
	public String getApdung();
	public void setApdung(String apdung);
	
	public String getLoaichitieu();
	public void setLoaichitieu(String loaichitieu);
	
	
}
