package geso.traphaco.erp.beans.canhbaothieuhang;

import java.sql.ResultSet;
import java.util.List;

public interface IErpCanhbaothieunguyenlieu 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public ResultSet getThangRs();
	public void setThangRs(ResultSet thangRs);
	public String getThangId();
	public void setThangId(String thangId);	
	
	public ResultSet getNamRs();
	public void setNamRs(ResultSet namRs);
	public String getNamId();
	public void setNamId(String namId);	
	
	public ResultSet getLoaispRs();
	public void setLoaispRs(ResultSet loaispRs);
	public String getLoaispId();
	public void setLoaiId(String loaispId);	
	
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	public String getspIds();
	public void setspIds(String spIds);	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	
	public ResultSet getCanhbaoRs();
	public void setCanhbaoRs(ResultSet canhbaoRs);
	
	public void setcanhbaoList(List<ICanhbao> cbList);
	public List<ICanhbao> getCanhbaoList();
	
	public String getViewMode();
	public void setViewMode(String viewMode);
	
	public void init();
	public void DbClose();
	
}
