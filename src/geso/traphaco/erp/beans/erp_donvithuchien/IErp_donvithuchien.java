package geso.traphaco.erp.beans.erp_donvithuchien;

import java.sql.ResultSet;

public interface IErp_donvithuchien
{
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

	public void setTrangThai(String TrangThai);

	public String getTrangThai();

	public boolean CheckUnique();

	boolean UpdateMa();

	boolean themMoiMa();

	void init();

	void DBClose();

	public void setUserid(String user);

	public String getUserid();

	public void setUserTen(String userten);

	public String getUserTen();
	
	public void setCtyList(ResultSet ctylist);
	
	public ResultSet getCtyList();
	
	public String getCtyId();

	public void setCtyId(String CTYID);
	
	public String getPrefix();
	
	public void setPrefix(String PREFIX);	
	
/*	public String getDvkdId();

	public void setDvkdId(String dvkdId);

	public ResultSet getDvkdRs();

	public void setDvkdRs(ResultSet dvkd);*/

}
