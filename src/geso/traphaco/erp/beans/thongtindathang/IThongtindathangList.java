package geso.traphaco.erp.beans.thongtindathang;
import geso.traphaco.center.util.IPhan_Trang;
import java.io.Serializable;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;

public interface IThongtindathangList extends IPhan_Trang, Serializable
{
	HttpServletRequest getRequestObj();
	public void setRequestObj(HttpServletRequest request);

	public String getCtyId();
	public void setCtyId(String ctyId);
	
	public String getCtyTen();
	public void setCtyTen(String ctyTen);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getThongtindathangRs();
	public void setThongtindathangRs(ResultSet thongtindathangRs);

	public String getTtdhId();
	public void setTtdhId(String ttdhId);

	public String convertDate(String date);
	public void init(String query);
	public ResultSet getSpRs();
	public ResultSet getLspRs();
	public ResultSet getNccRs();
	public String getNccId();
	public void setNccId(String nccId);
	public String getSpId();
	public void setSpId(String spId);
	
	public String getLspId();
	public void setLspId(String lspId);
	
	public String getIsMua();
	public void setIsMua(String isMua);
	
	public void DbClose();

}
