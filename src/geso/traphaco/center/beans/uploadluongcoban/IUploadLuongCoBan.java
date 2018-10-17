package geso.traphaco.center.beans.uploadluongcoban;

import geso.traphaco.center.beans.uploadluongcoban.imp.UploadLuongCoBan;

import java.sql.ResultSet;
import java.util.List;

public interface IUploadLuongCoBan {

	
	public String getDisplay() ;
	public void setDisplay(String loai);
	
	public void setID(String id);
	public String getID();

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

	public boolean CreateUploadLuongCoBan(String values);
	public boolean UpdateUploadLuongCoBan(String values);


	public ResultSet getLuongkhacChiTietRs() ;
	public void setLuongkhacChiTietRs(String uploadluongcobanChiTietRs) ;

	public boolean chotUploadLuongCoBan();
	public boolean UnchotUploadLuongCoBan();
	public boolean DeleteUploadLuongCoBan();
}
