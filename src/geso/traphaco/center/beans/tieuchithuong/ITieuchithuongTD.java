package geso.traphaco.center.beans.tieuchithuong;

import java.sql.ResultSet;

public interface ITieuchithuongTD 
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
	
	public void setNhomkhachhangRs(ResultSet nhomRs);
    public ResultSet getNhomkhachhangRs();
    public String getNhomkhachhangIds();
	public void setNhomkhachhangIds(String nhomIds);
	
	public void setSanphamRs(ResultSet spRs);
    public ResultSet getSanphamRs();
    public String getSpIds();
	public void setSpIds(String spIds);
	
	public void setNppRs(ResultSet nppRs);
    public ResultSet getNppRs();
    public String getNppIds();
	public void setNppIds(String nppIds);
	
	public void setLoaikhRs(ResultSet loaiKh);
    public ResultSet getLoaikhRs();
    public void setLoaikhIds(String lkhIds);
    public String getLoaikhIds();
    
    public void setKbhRs(ResultSet kbh);
    public ResultSet getKbhRs();
    public void setKbhIds(String kbhIds);
    public String getKbhIds();
	
    public void setTichluyRs(ResultSet tichluyRs);
    public ResultSet getTichluyRs();
    public String getTichluyIds();
	public void setTichluyIds(String tichluyIds);
    
    //Cac Muc 1 thang
    public String[] getDiengiaiMuc();
    public void setDiengiaiMuc(String[] diengiai);
    public String[] getTumuc();
    public void setTumuc(String[] tumuc);
    public String[] getDenmuc();
    public void setDenmuc(String[] denmuc);
    public String[] getThuongSR();
    public void setThuongSR(String[] thuongSR);
    public String[] getThuongTDSR();
    public void setThuongTDSR(String[] thuongTDSR);
    public String[] getThuongSS();
    public void setThuongSS(String[] thuongSS);
    public String[] getThuongTDSS();
    public void setThuongTDSS(String[] thuongTDSS);
    public String[] getThuongASM();
    public void setThuongASM(String[] thuongASM);
    public String[] getThuongTDASM();
    public void setThuongTDASM(String[] thuongTDASM);
    
    public String getMucvuot();
    public void setMucvuot(String mucvuot);
    public String getChietkhauMucvuot();
    public void setChietkhauMucvuot(String ckMv);
    public String getDonviMucvuot();
    public void setDonviMucvuot(String dvMv);
    
	public void init();
	public void createRs();
	
	public boolean createTctSKU();
	public boolean updateTctSKU();
    
    public String getApdungchodaily();
    public void setApdungchodaily(String isThung);
    
    public String getApdungchohopdong();
    public void setApdungchohopdong(String isThung);
    
    public String getChiavaodongia();
    public void setChiavaodongia(String chiavaodongia);
    
    public void setKhoanmuccpRs(ResultSet khoanmucRs);
    public ResultSet getKhoanmuccpRs();
    public void setKhoanmuccpId(String khoanmuccpId);
    public String getKhoanmuccpId();
}
