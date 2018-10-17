package geso.traphaco.erp.beans.huychungtu;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpHuylenhsanxuatList extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getHctMhRs();
	public void setHctMhRs(ResultSet hctmhRs);
	
	public void init(String search);
	public void DBclose();
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getsochungtu();
	public void setsochungtu(String sochungtu);
}
