package geso.traphaco.distributor.beans.kiemkhotdv;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IKiemkhotdvList extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getNppId();	
	public void setNppId(String nppId);

	public String getNppTen();
	public void setNppTen(String nppTen);

	public ResultSet getDctkList();	
	public void setDctkList(ResultSet dctkList);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);

	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);

	public ResultSet getKbh();
	public void setKbh(ResultSet kbh);
	
	public String getKhoId();
	public void setKhoId(String khoId);
	public ResultSet getKho();
	public void setKho(ResultSet kho);
	
	public void init0();
	public void createDctklist(String querystr);
	public void DBclose();
}