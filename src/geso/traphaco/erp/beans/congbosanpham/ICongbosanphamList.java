package geso.traphaco.erp.beans.congbosanpham;
import geso.traphaco.center.util.IPhan_Trang;
import java.io.Serializable;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;

public interface ICongbosanphamList extends IPhan_Trang, Serializable
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
	
	public String getThoihan();
	public void setThoihan(String thoihan);
	
	public String getSanPham();
	public void setSanPham(String sanpham);

	public ResultSet getCongbosanphamRs();
	public void setCongbosanphamRs(ResultSet congbosanphamRs);

	public String getCongbosanphamId();
	public void setCongbosanphamId(String congbosanphamId);

	public String convertDate(String date);
	public void init(String query);
	public void DbClose();

}
