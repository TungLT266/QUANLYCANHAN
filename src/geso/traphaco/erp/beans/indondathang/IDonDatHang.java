package geso.traphaco.erp.beans.indondathang;

import java.sql.ResultSet;

public interface IDonDatHang {
	public String getCongTyId();
	public void setCongTyId(String congTyId);
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getCongTyDatHang();
	public void setCongTyDatHang(String congTyDatHang);
	public String getDonViDatHang();
	public void setDonViDatHang(String donViDatHang);
	public String getDiaChi();
	public void setDiaChi(String diaChi);
	public String getDienThoai();
	public void setDienThoai(String dienThoai);
	public String getFax();
	public void setFax(String fax);
	public String getMaSoThue();
	public void setMaSoThue(String maSoThue);
	public long getTongCong();
	public void setTongCong(long tongCong);
	public String getHinhThucThanhToan();
	public void setHinhThucThanhToan(String hinhThucThanhToan);
	public String getTongCongBangChu();
	public void setTongCongBangChu(String tongCongBangChu);
	public String getTienTe();
	public void setTienTe(String tienTe);
	public String getLoai();
	public void setLoai(String loai);
	public ResultSet getThongTinDatHang();
	public void setThongTinDatHang(ResultSet thongTinDatHang);
	public void createThongTinDatHang();
	public void init();
	
	
	
	public String getDiachincc();

	public void setDiachincc(String diachincc);

	public String getDienthoaincc() ;

	public void setDienthoaincc(String dienthoaincc) ;

	public String getMasothuencc();

	public void setMasothuencc(String masothuencc) ;

	public String getFaxncc() ;

	public void setFaxncc(String faxncc);
	
	public String getSotaikhoanncc() ;

	public void setSotaikhoanncc(String sotaikhoanncc) ;
	public String getNganhangncc() ;

	public void setNganhangncc(String nganhangncc) ;

	public String getSotaikhoan() ;

	public void setSotaikhoan(String sotaikhoan);

	public String getNganhang() ;

	public void setNganhang(String nganhangn) ;

	
	
}
