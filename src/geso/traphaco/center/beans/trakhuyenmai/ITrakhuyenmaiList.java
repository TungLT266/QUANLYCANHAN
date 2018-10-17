package geso.traphaco.center.beans.trakhuyenmai;

import geso.traphaco.center.beans.trakhuyenmai.ITrakhuyenmai;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ITrakhuyenmaiList extends Serializable, IPhan_Trang
{
	

	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getMatrakm();
	public void setMatrakm(String matrakm);
	
	public String getTensp();
	public void setTensp(String tensp);
	
	public String getMasp();
	public void setMasp(String masp);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	//public List<ITrakhuyenmai> getTrakmList();
	//public void setTrakmList(List<ITrakhuyenmai> trakmlist);
	public ResultSet getTrakmList();
	public void setTrakmList(ResultSet trakmlist);
	public void init(String search);
	public void setmsg(String msg);
	public String getmsg();
	public void DBclose();
}
