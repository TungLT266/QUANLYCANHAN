package geso.traphaco.erp.beans.erp_tiente;

import java.sql.ResultSet;

public interface IErp_tienteList
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

	void init();

	public boolean CheckReferences(String column, String table, String trangthai, boolean check_trangthai);

	public boolean DeleteTienTe();

	void DBClose();
	
	public void setChixem(String chixem);
	public String getChixem();

}
