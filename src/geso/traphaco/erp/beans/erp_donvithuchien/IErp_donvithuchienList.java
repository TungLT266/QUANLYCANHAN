package geso.traphaco.erp.beans.erp_donvithuchien;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErp_donvithuchienList  extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getID();

	public String getMA();

	public String getTEN();

	public String getNGAYTAO();

	public String getNGAYSUA();

	public String getNGUOITAO();

	public String getNGUOISUA();

	public String getMsg();

	public ResultSet getRsdvth();

	public void setID(String ID);

	public void setMA(String MA);

	public void setTEN(String TEN);

	public void setNGAYTAO(String NGAYTAO);

	public void setNGAYSUA(String NGAYSUA);

	public void setNGUOITAO(String NGUOITAO);

	public void setNGUOISUA(String NGUOISUA);

	public void setMsg(String Msg);

	public void setRsdvth(ResultSet Rsdvth);

	void init();

	public boolean CheckReferences(String column, String table);
	
	public boolean DeleteDvth();

	void DBClose();

	public void setUserid(String user);

	public String getUserid();

	public void setUserTen(String userten);

	public String getUserTen();
	
	public void setCtyList(ResultSet ctylist);
	
	public ResultSet getCtyList();
	
	
}
