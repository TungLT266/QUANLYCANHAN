package geso.traphaco.erp.beans.taikhoankt;

import java.sql.ResultSet;

public interface ITaikhoankt
{
	public String getUserId();

	public void setUserId(String userId);

	public String getId();

	public void setId(String Id);

	public String getLoaiTaiKhoanId();

	public void setLoaiTaiKhoanId(String LoaiTaiKhoanId);

	public String getSoHieuTaiKhoan();

	public void setSoHieuTaiKhoan(String SoHieuTaiKhoan);

	public String getTenTaiKhoan();

	public void setTenTaiKhoan(String TenTaiKhoan);

	public String getTaiKhoanCoChiTiet();

	public void setTaiKhoanCoChiTiet(String TaiKhoanCoChiTiet);

	public String getCongTyId();

	public void setCongTyId(String CongTyId);

	public String getTrangThai();

	public void setTrangThai(String TrangThai);

	public String getMsg();

	public void setMsg(String msg);

	public String getTaiKhoanCoChiPhi();

	public void setTaiKhoanCoChiPhi(String TaiKhoanCoChiPhi);

	public String getDungchokhachhang();

	public void setDungchokhachhang(String dungchoKhachhang);

	public String getDungchonhacungcap();

	public void setDungchonhacungcap(String dungchoNCC);

	public String getDungchonganhang();

	public void setDungchonganhang(String dungchoNganhang);

	public String getDungchokho();

	public void setDungchokho(String dungchoKho);

	public String getDungchotaisan();

	public void setDungchotaisan(String dungchoTaisan);	

	public ResultSet getCongTyRs();

	public void setCongTyRs(ResultSet CongTyRs);

	public ResultSet getLoaiTaiKhoanRs();

	public void setLoaiTaiKhoanRs(ResultSet LoaiTaiKhoanRs);
	
	public String getDungchonhanvien();

	public void setDungchonhanvien(String dungchonhanvien);

	public void init();

	public boolean CreateTaikhoankt();

	public boolean UpdateTaikhoankt();

	public boolean AllowtoChangeCty();
	
	public void CreateRs();

	public void closeDB();
	
	public void setDungChoDanhMucDuAn(String dungChoDanhMucDuAn);
	public String getDungChoDanhMucDuAn();
	
	public String getDungChoDoiTuongKhac();
	public void setDungChoDoiTuongKhac(String dungChoDoiTuongKhac);
	
	public ResultSet getLoaiTKBaoCaoRs() ;

	public void setLoaiTKBaoCaoRs(ResultSet loaiTKBaoCaoRs);
	
	
	public String getLoaiTKBaoCao() ;

	public void setLoaiTKBaoCao(String loaiTKBaoCao) ;
	public String getDungchoMSCL() ;
	public void setDungchoMSCL(String dungchoMSCL);

	public String getDungchoCPTT();

	public void setDungchoCPTT(String dungchoCPTT) ;
}