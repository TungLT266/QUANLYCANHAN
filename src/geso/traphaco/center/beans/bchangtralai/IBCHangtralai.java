package geso.traphaco.center.beans.bchangtralai;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IBCHangtralai extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);

	public String getTtId();
	public void setTtId(String ttid);
	public ResultSet getttRs();
	public String getNppId();
	public void setNppId(String nppid);
	public ResultSet getNppRs();
	/*public void setKhuvucRs(ResultSet khuvucRs);*/
	
	public void init();
	
	public void DBclose();

	public ResultSet getNhanvienRs();
	public String getNhanvienId();
	public void setNhanvienId(String nvId);
	public void setMsg(String string);
	public String getMsg();
	public ResultSet getBcRs();
	
	public ResultSet getVungRs();
	public ResultSet getKhuvucRs();
	public void setvungId(String vungid);
	public String getvungId();
	public void setkhuvucId(String kvid);
	public String getkhuvucId();
	public String getLoai();
	public void setLoai(String value);
	
}
