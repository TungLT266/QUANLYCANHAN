package geso.traphaco.erp.beans.huychungtu;

 

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpHuychungtubanhangList  extends Serializable, IPhan_Trang 
{
	public String getUserId();
	public void setUserId(String userId);
	
	
	public void setTennguoitao(String tennguoitao);
	public String getTennguoitao();
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getHctBhRs();
	public void setHctBhRs(ResultSet hctmhRs);
	
	public void init(String search);
	public void DBclose();
	
	public String getSochungtu();

	public void setSochungtu(String sochungtu);
}
