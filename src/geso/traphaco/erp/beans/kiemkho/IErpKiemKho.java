package geso.traphaco.erp.beans.kiemkho;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IErpKiemKho
{
	public String getID();
	
	public void setID(String id);
	
	// Chua giao dien dieu chinh ton kho de cap nhap//Chot
	public void ViewToUpdate();
	
	public String getNgayDieuChinh();
	
	public void setNgayDieuChinh(String ngayDieuChinh);
	
	public String getLyDoDieuChinh();
	
	public void setLyDoDieuChinh(String lyDoDieuChinh);
	
	public String getKhoTT_FK();
	
	public void setKhoTT_FK(String khoTT_FK);
	
	public void SetKhu_FK(String Khu_FK);
	
	public ResultSet getKhuRs();
	
	public void setKhuRs(ResultSet KhuRs);
	
	public String getKhu_FK();
	
	public int getTrangThai();
	
	public void setTrangThai(int trangThai);
	
	public String getNguoiTao();
	
	public void setNguoiTao(String nguoiTao);
	
	public String getNguaSua();
	
	public void setNguoiSua(String nguoiSua);
	
	public String getMessage();
	
	public void setMessage(String msg);
	
	public String getNgayChot();
	
	public void setNgayChot(String ngaychot);
	
	public ResultSet getRsKhoTT();
	
	public void setRsKhoTT(ResultSet rsKhoTT);
	
	public ResultSet getRsSanPham();
	
	public void setRsSanPham(ResultSet rsSanPham);
	
	public void CreateRsKho();
	
	public void CreateRsSanPham();
	
	public ResultSet getViTriKhoRs();
	
	public void setViTriKhoRs(ResultSet vitrikho);
	
	public ResultSet getBinRs();
	
	public void setBinRs(ResultSet binRs);
	
	// Chot Dieu Chinh
	public boolean Approve();
	
	// Cap nhat so luong san pham cua dieu chinh ton kho
	public boolean Update();
	
	// Tao moi mot dieu chinh Ton Kho
	public boolean SaveNew();
	
	// Huy dieu chinh ton kho
	public boolean Cancel();// Huy--Set trang thai la huy
	
	public List<IErpKiemKho_SanPham> getSanPhamKhoList();
	
	public void setSanPhamKho(List<IErpKiemKho_SanPham> setSanPhamKhoList);
	
	public void display();
	
	public void PDF();
	
	public void close();
	
	public void beanDctktt();
	
	public String getnam();
	public void setNam(String nam);
	public String getthang();
	public void setthang(String thang);

	public void initThangNam();

	public Hashtable<String, String> getSanphamKho(String spid_str);
	
	public String getLoaisanpham();
	public void setLoaisanpham(String loaisanpham);

	public String getMalonsanpham();
	public void setMalonsanpham(String malonsanpham);

	public String getTicksoluong();
	public void setTicksoluong(String ticksoluong);
	
	public ResultSet getRsLoaisanpham();
	public void setRsLoaisanpham(ResultSet rsLoaisanpham);

	public ResultSet getRsMalonsanpham();
	public void setRsMalonsanpham(ResultSet rsMalonsanpham);
	
	public void CreateRsLoaiSanPham();
	public void CreateRsMaLonSanPham();
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
}
