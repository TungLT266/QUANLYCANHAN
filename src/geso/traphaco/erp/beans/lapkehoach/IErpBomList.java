package geso.traphaco.erp.beans.lapkehoach;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpBomList  extends Serializable, IPhan_Trang
{
	public String getCtyId(); 

	public void setCtyId(String ctyId); 
	
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTenBOM();
	public void setTenBOM(String tenbom);
	
	public String getVanBanHuongDan();
	public void setVanBanHuongDan(String vanbanhuongdan);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getMsg();
	public void setMsg(String msg);
	
	public String getSanpham();
	public void setSanpham(String sanpham);

	public ResultSet getSpRs();
	public void setSpRs(ResultSet rs);
	
	
	public ResultSet getBomRs();
	public void setBomRs(ResultSet khlRs);

	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet rs);
	
	public String getLspId();
	public void setLspId(String lspid);
	
	public ResultSet getLspRs();
	public void setLspRs(ResultSet rs);
	
	public void init(String query);
	public void DbClose();
 
	public int getCurrentPage();
	public void setCurrentPage(int current);	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getManguyenlieu();
	public void setManguyenlieu(String manguyenlieu);
	public ResultSet getManguyenlieuRs();
	public void setManguyenlieuRs(ResultSet manguyenlieuRs);
}
