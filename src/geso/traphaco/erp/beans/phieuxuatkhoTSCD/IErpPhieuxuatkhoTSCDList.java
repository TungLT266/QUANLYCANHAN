package geso.traphaco.erp.beans.phieuxuatkhoTSCD;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpPhieuxuatkhoTSCDList extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getSoPhieu();
	public void setSoPhieu(String sophieu);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getPxkList();
	public void setPxkList(ResultSet pxkList);
	
	public void init(String query);
	public void DBclose();
	
	public String getKhachhang();
	public void setKhachhang(String khachhang);
	
	public String getSanPhamId();
	public void setSanPhamId(String spId);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdList();
	public void setDvkdList(ResultSet dvkdList);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
}
