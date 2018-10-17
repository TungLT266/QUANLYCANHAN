package geso.traphaco.erp.beans.yeucauchuyenkho;
 
import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpNhapkhotraveList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungayTao();
	public void setTungayTao(String tungay);
	public String getDenngayTao();
	public void setDenngayTao(String denngay);
	
	public String getSophieu();
	public void setSophieu(String sophieu);
	
	public ResultSet getNhapkhoRs();
	public void setNhapkhoRs(ResultSet nkRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String search);
	public void DBclose();
	
	public String getMasanpham();
	public void setMasanpham(String masanpham);
	public String getSohoadon();
	public void setSohoadon(String sohoadon);

	public String getKhonhanId();
	public void setKhonhanId(String khonhanId);
	public ResultSet getKhonhanRs();
	public void setKhonhanRs(ResultSet khonhanRs);
}
