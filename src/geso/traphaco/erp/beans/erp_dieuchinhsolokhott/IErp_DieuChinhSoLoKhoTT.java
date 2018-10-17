package geso.traphaco.erp.beans.erp_dieuchinhsolokhott;

import java.sql.ResultSet;
import java.util.List;

public interface IErp_DieuChinhSoLoKhoTT
{
	public String getID();
	public void setID(String id);
	
	// Chua giao dien dieu chinh ton kho de cap nhap//Chot
	public void ViewToUpdate();
	
	public String getNgayDieuChinh();
	public void setNgayDieuChinh(String ngayDieuChinh);
	
	public String getLyDoDieuChinh();
	public void setLyDoDieuChinh(String lyDoDieuChinh);
	
	public String getKhu_FK();
	public void SetKhu_FK(String Khu_FK);
	
	public ResultSet getKhuRs();
	public void setKhuRs(ResultSet KhuRs);

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
	
	public String getKhoTT_FK();
	public void setKhoTT_FK(String khoTT_FK);
	public ResultSet getRsKhoTT();
	public void setRsKhoTT(ResultSet rsKhoTT);
	
	public ResultSet getRsSanPham();
	public void setRsSanPham(ResultSet rsSanPham);
	
	public void CreateRs();

	public void rsSanPhamByKhoForUpdate();
	
	public ResultSet getViTriKhoRs();
	public void setViTriKhoRs(ResultSet vitrikho);
	
	public ResultSet getBinRs();
	public void setBinRs(ResultSet binRs);
	
	public boolean Approve();
	public boolean Update();
	public boolean SaveNew();
	public boolean Cancel();
	
	public List<IErp_DieuChinhSoLoKhoTT_SanPham> getSanPhamKhoList();
	public void setSanPhamKho(List<IErp_DieuChinhSoLoKhoTT_SanPham> setSanPhamKhoList);
	
	public void display();
	
	public void PDF();
	
	public void close();
	
	public void beanDctktt();
	
	public String getSPID();
	public void setSPID(String spId);
	
	public ResultSet getRsLoaiSanPham();
	public void setRsLoaiSanPham(ResultSet rsLoaiSanPham);
	
	public String getLOAISPID();
	public void setLOAISPID(String loaispId);	
}
