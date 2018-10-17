package geso.traphaco.erp.beans.lenhsanxuatgiay;

import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface ILenhSXCongDoan {
	public void setLenhSXId(String LSXid);
	public String getLenhSXId();
	
	public void setCongDoanId(String congdoanid);
	public String getCongDoanId();
	

	public void setPhanXuong(String phanxuongid);
	public String getPhanXuong();

	public void setNGayYcThem(String ngayycthem);
	public String getNGayYcThem();
	
	
	public void setDiengiai(String Diengiai);
	public String getDiengiai();
	//ten  sản phẩm
	public void setSanPham(String MaSanPham);
	public String getSanPham();
	
	//pk_seq sản phẩm
	public void setSpId(String spid);
	public String getSpid();
	//ma  sản phẩm
	public void setMaSp(String MaSp);
	public String getMaSp();
	
	public void setTrangthai(String Trangthai);
	public String gettrangthai();
	
	public void setActive(String Active);
	public String getActive();
	
	public void Setkichbansanxuat(String kichbansx);
	public String getKichbansanxuat();
	
	public void SetKiemDinhCL(String KiemDinhCL);
	public String getKiemDinhCL();
	// set xem đã tiêu hao nguyên liệu chưa?
	public void setDaTieuHaoNL(String DaTieuHaoNL);
	public String GetDaTieuHaoNL();
	
	public void setSoLuong(String soluong);
	public String getSoLuong();
	
	// setSoLuongChuan la so luong ma tinh ra duoc, nguoi dung co the nhap vao 1 so luong khac  luu vao(setSoLuong)
	//,nhung phai lon hon so nay neu muon tao them nhieu nguyen lieu.
	
	public void setSoLuongChuan(String soluong);
	public String getSoLuongChuan();
	
	
	public void setNhaMayId(String NhaMayId);
	public String getNhaMayId();
	
	public void setBomId(String bomId);
	public String getBomId();
	public void setBomTen(String bomten);
	public String getBomTen();
	
	
	public void setMaySuDung(String maysudung);
	public String getMaySuDung();
	
	public void setThuTu(float tt);
	public float getThutu();
	
	public void SetRsHangDaNhan(ResultSet rs);
	public ResultSet getRsHangDaNhan();
	
	public void SetRsBanThanhPham(ResultSet rs);
	public ResultSet getRsBanThanhPham();
	
	
	public void SetRsHangDaKD(ResultSet rs);
	public ResultSet getRsHangDaKD();
	
	public void SetRsDaTieuHao(ResultSet rs);
	public ResultSet getRsDaTieuHao();
	
 
	public ArrayList<String> getSoluongNhap() ;

	public void setSoluongNhap(ArrayList<String> soluongNhap);

	public ArrayList<String> getSoluongXuat();

	public void setSoluongXuat(ArrayList<String> soluongXuat);

	public ArrayList<String> getSoluongDuPham();

	public void setSoluongDuPham(ArrayList<String> soluongDuPham);

	public ArrayList<String> getSoluongPhePham();

	public void setSoluongPhePham(ArrayList<String> soluongPhePham) ;
	
	public ResultSet getCongDoanNhapXuat();

	public void setCongDoanNhapXuat(ResultSet congDoanNhapXuat) ;
	
	//by PhatNguyen
	public void initCongDoanNhapXuat() ;
	public void initArrayNhapXuat();
	
	public ResultSet getKhosxRs();
	public void setKhosxRs(ResultSet KhosxRs) ;
	
	
	public void setListDanhMuc(List<IDanhmucvattu_SP> list);
	public List<IDanhmucvattu_SP> getListDanhMuc();
	
	public void setKhoSXId(String KhoSXId);
	public String getKhoSXId();
	  
}
