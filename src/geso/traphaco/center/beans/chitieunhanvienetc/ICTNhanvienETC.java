package geso.traphaco.center.beans.chitieunhanvienetc;

import geso.traphaco.center.beans.chitieunhanvienetc.imp.CTNhanvienETC;

import java.sql.ResultSet;
import java.util.List;

public interface ICTNhanvienETC  {

	public String getId();
	public void setId(String id) ;
	public void setTen(String ten) ;
	public String getTen();
	
	public String getNppId() ;
	public void setNppId(String manv);
	
	//this.DoanhSoBanRa = Double.parseDouble(param[3]);
	public double getDoanhSoBanRa();
	//this.SoLuongBanRa = Double.parseDouble(param[4]);
	public double getSoLuongBanRa();
	//this.DoanhSoMuaVao = Double.parseDouble(param[5]);
	public double getDoanhSoMuaVao();
	//this.SoLuongMuaVao = Double.parseDouble(param[6]);
	public double getSoLuongMuaVao();
	
	
	public double getGiaTriDonHang() ;
	public double getGiaTriSanPham() ;
	public double getKhachHangMoi() ;
	public String getLoainv() ;
	public double getSoDhTrongNgay() ;
	public double getSoKhMuaHang() ;
	public double getSoVTcoDonHang() ;
	public double getThanhToanNpp() ;
	public double getTyleKhMuahang();	
	
	
	public List<ICTNhanvienETC_NSP> getCtNspList() ;
	public void setCtNspList(List<ICTNhanvienETC_NSP> ctNspList);

}
