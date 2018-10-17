package geso.traphaco.erp.beans.erp_capquanly;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErp_capquanly extends Serializable, IPhan_Trang
{
	public String getID();

	public String getMA();

	public String getTEN();

	public String getNGAYTAO();

	public String getNGAYSUA();

	public String getNGUOITAO();

	public String getNGUOISUA();

	public String getTRANGTHAI();

	public String getMsg();

	public ResultSet getRscn();

	public void setID(String ID);

	public void setMA(String MA);

	public void setTEN(String TEN);

	public void setNGAYTAO(String NGAYTAO);

	public void setNGAYSUA(String NGAYSUA);

	public void setNGUOITAO(String NGUOITAO);

	public void setNGUOISUA(String NGUOISUA);

	public void setTRANGTHAI(String TRANGTHAI);

	public void setMsg(String Msg);

	public void setUserTen(String userten);

	public String getUserTen();

	public void setUserid(String user);

	public String getUserid();

	public void init(String sql);

	public boolean CheckReferences(String column, String table);

	public boolean CheckUnique();
	
	public boolean Delete();

	void DBClose();

	boolean ThemMoiMa();

	boolean UpdateMa();
	
	
	// MỚI

	public String getLoaicapId();
	
	public void setLoaicapId(String loaicapId);
	
	public ResultSet getLoaicapRs();
	
	public void setLoaicapRs(ResultSet loaicapRs);
	
	public void init();
	
	public void createRs();
	
	public String getMacap();
	
	public void setMacap(String macap);
	
	public String getTencap();
	
	public void setTencap(String tencap);
	
	public String getQuanlycapId();
	
	public void setQuanlycapId(String quanlycapId);
	
	public ResultSet getQuanlycapRs();
	
	public void setQuanlycapRs(ResultSet quanlycapRs);
	
	public String getEmail();
	
	public void setEmail(String email);
	
	public ResultSet getNhanvienRs();
	
	public void setNhanvienRs(ResultSet nhanvienRs);
	
	public ResultSet getNhanvienChonRs();
	
	public void setNhanvienChonRs(ResultSet nhanvienchonRs);
	
    public void CreateNhanVien(String[] chuoi);
    
    public void setRsCty(ResultSet rsCty);
    
	public ResultSet getRsCty(); 
	
	public String getCtyId();

	public void setCtyId(String ctyId);
	
}
