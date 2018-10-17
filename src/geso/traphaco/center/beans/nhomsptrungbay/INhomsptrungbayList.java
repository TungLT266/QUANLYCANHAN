package geso.traphaco.center.beans.nhomsptrungbay;

import geso.traphaco.center.beans.nhomsptrungbay.INhomsptrungbay;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface INhomsptrungbayList extends Serializable, IPhan_Trang
{
	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public List<INhomsptrungbay> getNsptbList();
	public void setNsptbList(List<INhomsptrungbay> nsptblist);

	public void init(String search);
	public void DBclose();
	public void setMgs(String mgs);
	public String getMgs();
}

