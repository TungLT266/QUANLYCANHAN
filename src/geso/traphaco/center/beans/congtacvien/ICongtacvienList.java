package geso.traphaco.center.beans.congtacvien;

import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ICongtacvienList extends Serializable, IPhan_Trang
{	
	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public String getUserId();
	public void setUserId(String userId);
	public String getMa();
	public void setMa(String ma);
	public String getTen();
	public void setTen(String ten);
	public String getSodienthoai();
	public void setSodienthoai(String sodienthoai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getDdkdList();
	public void setDdkdList(ResultSet ddkdlist);
	
	public void setMsg(String msg);
	public String getMsg();
 
	
	public void init(String search);
	public void DbClose();
	
}
