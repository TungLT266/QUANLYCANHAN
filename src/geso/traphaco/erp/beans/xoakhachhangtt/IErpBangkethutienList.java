package geso.traphaco.erp.beans.xoakhachhangtt;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpBangkethutienList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getMaPhieu();
	public void setMaPhieu(String maphieu);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void setmsg(String msg);
	public String getmsg();
	
	public void init(String search);
	public void DBclose();
	
	public String getSotien();
	public void setSotien(String Sotien);
	
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	public ResultSet getNvgnRs();
	public void setNvgnRs(ResultSet nvgnRs);
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	
	public String getKhuvucId();
	public void setKhuvucId(String kvId);
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet kvRs);
	
	public String getMakhachhang();
	public void setMakhachhang(String makhachhang);
	
	public ResultSet getBangkeRs();
	public void setBangkeRs(ResultSet bangkeRs);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public String getNppId();
	public void setNppId(String nppId);
	public ResultSet getRskhoid();

	public void setRskhoid(ResultSet rskhoid) ;

	public String getKhohh();

	public void setKhohh(String khohh);
	public String getNguoitao();

	public void setNguoitao(String nguoitao);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
	public void setSohoadon(String sohoadon);
	public String getSohoadon();

}
