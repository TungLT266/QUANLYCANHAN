package geso.traphaco.center.beans.nhomfocus;

import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface INhomfocusKPI 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getDiengiai();
	public void setDiengiai(String dg);
	
	public String getThang();
	public void setThang(String thang);
		
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getNam();
	public void setNam(String nam);
	
	public String getDvkd();
	public void setDvkd(String donvikinhdoanh);
	
	public String getKenhbanhang();
	public void setKenhbanhang(String kenhbanhang);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getDoituong();
	public void setDoituong(String doituong);
	///
	public List<ISanpham> getSanphamList();
	public void setSanphamList(List<ISanpham> SanphamList);
	public ResultSet getDvkdList();
	public void setDvkdList(ResultSet Dvkdlist);
	public ResultSet getKenhbanhangList();
	public void setKenhbanhangList(ResultSet Kenhbanhanglist);
	///
	public void init();
	public void createRs();
	public void createRsUpdate();
	public boolean createNhomfocus(HttpServletRequest request);
	public boolean updateNhomfocus(HttpServletRequest request);
	public void close();
	
	public ResultSet getkhuvuc();
	public ResultSet getVung();
	
	public String getKhuvucStr();
	public void setKhuvucStr(String khuvucstr);
	
	public String getVungStr();
	public void setVungStr(String Vungstr);
	
}
