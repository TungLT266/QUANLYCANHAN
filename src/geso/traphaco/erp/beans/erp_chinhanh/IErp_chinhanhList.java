package geso.traphaco.erp.beans.erp_chinhanh;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErp_chinhanhList  extends Serializable, IPhan_Trang
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
	
	public boolean Delete();
	
	public void setChixem(String chixem);
	public String getChixem();

	void DBClose();
		
	public String getnppId();
	
	public void setnppId(String nppId);
	
	public String getcongtyId();
	
	public void setcongtyId(String congtyId);

}
