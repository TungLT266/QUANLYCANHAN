package geso.traphaco.center.beans.thuongdauthung;

import java.sql.ResultSet;
import java.util.List;

public interface IThuongdauthung {


	public String getDisplay();
	public void setDisplay(String display);
	
	public void setID(double id);
	public double getID();

	public void setNguoiTao(String nguoitao);
	public String getNguoiTao();
	public void setNguoiSua(String nguoisua);
	public String getNguoiSua();
	public void setThang(int thang);
	public int getThang();
	public void setNam(int nam);
	public int getNam();
	public void setMessage(String strmessage);
	public String getMessage();


	public void setNgayTao(String ngaytao);
	public String getNgayTao();

	public void setNgaySua(String nguoisua);
	public String getNgaySua();

	public void setUserId(String userid);
	public String getUserId();

	public void setDienGiai(String diengiai);
	public String getDienGiai();


	public void setTrangThai(String trangthai);
	public String getTrangThai();


	public void closeDB();

	public void setLuongkhacRs(String uploadluongcobanRs) ;
	public ResultSet getLuongkhacRs() ;

	public boolean CreateUploadLuongCoBan();
	public boolean UpdateUploadLuongCoBan();


	public boolean chotUploadLuongCoBan();
	public boolean UnchotUploadLuongCoBan();
	public boolean DeleteUploadLuongCoBan();

	public double getSoluong();
	public void setSoluong(double soluong) ;

	public String getLoaict();
	public void setLoaict(String isThung) ;

	public String getIsSalesIn() ;
	public void setIsSalesIn(String isSalesIn);

	public String getTungay() ;
	public void setTungay(String tungay) ;
	public String getDenngay();
	public void setDenngay(String denngay) ;

	
	public void setNsp_fk(String nsp_fk) ;
	public String getNsp_fk() ;
	public ResultSet getNspRs() ;
	
	public String getLoaidk() ;
	public void setLoaidk(String loaidk) ;
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
	public String[] getSanpham();
	public void setSanpham(String[] sanpham);
	
	
	public void createSpList();
	
	public ResultSet getDataRs();
	public void setDataRs(ResultSet dataRs);
	
	public String getLoai();
	public void setLoai(String loai);
	
	public String[] getTumuc();
	public void setTumuc(String[] tumuc);
	
	public String[] getDenmuc();
	public void setDenmuc(String[] denmuc);
	
	public String[] getThuongSR();
	public void setThuongSR(String[] thuong);
	
	public String[] getThuongSS();
	public void setThuongSS(String[] thuong);
	
	public String[] getThuongASM();
	public void setThuongASM(String[] thuong);
	
	public String[] getThuongBM();
	public void setThuongBM(String[] thuong);
	
	public String[] getDonvi_thuong();
	public void setDonvi_thuong(String[] donvi_thuong);
	public String getTieuchilay();
	public void setTieuchilay(String tieuchilay);
	public String getThangdt();

	public void setThangdt(String thangdt);


	public String getNamdt();


	public void setNamdt(String namdt);

	
}
