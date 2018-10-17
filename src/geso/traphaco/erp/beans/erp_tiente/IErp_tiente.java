package geso.traphaco.erp.beans.erp_tiente;

import java.sql.ResultSet;

public interface IErp_tiente
{
	public String getID();

	public String getMA();

	public String getTEN();

	public String getMsg();

	ResultSet getRstt();

	public void setID(String ID);

	public void setMA(String MA);

	public void setTEN(String TEN);

	public void setRstt(ResultSet Rstt);

	public void setMsg(String Msg);

	public void setUserid(String userId);

	public String getUserid();

	public void setUserTen(String userTen);

	public String getUserTen();

	public boolean CheckUnique();

	boolean ThemMoiMa();

	boolean UpdateMa();

	void init();

	void DBClose();
	public String getDoctiensonguyen();
	public void setDoctiensonguyen(String doctiensonguyen);
	
	public String getDoctiensole() ;
	public void setDoctiensole(String doctiensole);
}
