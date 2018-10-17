package geso.traphaco.erp.beans.hoadonkhac;
import geso.dms.center.util.IPhan_Trang;
import geso.dms.center.util.IThongTinHienThi;

import java.sql.ResultSet;
import java.util.List;
public interface IErpHoadonkhacList extends IPhan_Trang{

	
	public String getTennguoitao();
	public void setTennguoitao(String tennguoitao);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public ResultSet getNhanVienRs() ;
	public void setNhanVienRs(ResultSet nhanVienRs) ;
	
	
	public String getNhanVienId() ;
	public void setNhanVienId(String nhanVienId) ;

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getMa();
	public void setMa(String ma);	
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public String getKhachhang();
	public void setKhachhang(String Khachhang);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getGiamgiaRs();
	public void setGiamgiaRs(ResultSet giamgiaRs);
	
	public ResultSet getKhoanmucDTRs();
	public void setKhoanmucDTRs(ResultSet khoanmucDTRs);
	public String getKhoanmucDTId();
	public void setKhoanmucDTId(String khoanmucDTId);
	
	public void init(String query);
	
	/*public List<IErpHoaDonList> getHoadonList();
	public void setHoadonList(List<IErpHoaDonList> hoadonList);*/
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
	public void DbClose();
	
	public String getTungay() ;

	public void setTungay(String tungay) ;

	public String getDenngay() ;

	public void setDenngay(String denngay) ;

}
