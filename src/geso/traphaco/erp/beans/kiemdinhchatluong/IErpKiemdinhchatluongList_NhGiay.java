package geso.traphaco.erp.beans.kiemdinhchatluong;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpKiemdinhchatluongList_NhGiay  extends IPhan_Trang, Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getMa();
	public void setMa(String ma); 
	public String getSanpham();
	public void setSanpham(String sanpham);
	public String getSonhanhang();
	public void setSonhanhang(String sonhanhang);
	
	public String getsochungtu();
	public void setsochungtu(String sochungtu);
	
	// them loai mua hang(trong nuoc hoac nhap khau)
	public String getloaimuahang();
	public void setloaimuahang(String loaimh);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getTungayNH();
	public void setTungayNH(String tungayNH);
	public String getDenngayNH();
	public void setDenngayNH(String denngayNH);

	public String getNgayNhan();
	public void setNgayNhan(String ngaynhan);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getMsg();
	public void setMsg(String msg);
	
	
	public ResultSet getKdclRs();
	public void setKdclRs(ResultSet kdclRs);
	
	public void init(String query);
	public void DbClose();
	
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	
	public String getSohoadon() ;

	public void setSohoadon(String sohoadon) ;
	
	
	public ResultSet getSpRs() ;


	public void setSpRs(ResultSet spRs);
	
}
