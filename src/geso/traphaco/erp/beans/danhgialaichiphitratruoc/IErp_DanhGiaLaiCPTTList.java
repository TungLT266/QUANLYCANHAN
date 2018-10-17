package geso.traphaco.erp.beans.danhgialaichiphitratruoc;
import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public interface IErp_DanhGiaLaiCPTTList extends Serializable, IPhan_Trang
{
	
	public String getSochungtu() ;

	public void setSochungtu(String sochungtu) ;

	public String getPbId();
	

	public void setPbId(String pbId) ;

	public ResultSet getPbRs() ;

	public void setPbRs(ResultSet pbRs) ;

	public ResultSet getRsDc() ;

	public void setRsDc(ResultSet rsDc) ;

	public String getSodieuchuyen() ;
	public void setSodieuchuyen(String sodieuchuyen);

	public String getTungay() ;
	public void setTungay(String tungay) ;

	public String getDenngay();

	public void setDenngay(String denngay) ;

	public String getPhongban() ;

	public void setPhongban(String phongban) ;
	public void DBClose();
	public String getCtyId() ;
	public void setCtyId(String ctyId) ;
	public void init(String sql);
	public void createRs();
	public String getTrangthai() ;
	public void setTrangthai(String trangthai) ;
	
	public String getMsg() ;
	public void setMsg(String msg) ;
}
