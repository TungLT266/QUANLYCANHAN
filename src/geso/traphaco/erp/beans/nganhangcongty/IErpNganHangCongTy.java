package geso.traphaco.erp.beans.nganhangcongty;
import geso.traphaco.center.util.Erp_Item;

import java.sql.ResultSet;
import java.util.List;
public interface IErpNganHangCongTy
{
	public String getId();
	public void setId(String id);
	public void setUserId(String userId);
	public String getUserId();
	public String getMsg();
	public void setMsg(String msg);
	public void setTrangThai(String trangthai);
	public String getTrangThai();
	public String getCongTy();
	public void setCongTy(String congty);
	public String getCtyId();
	public void setCTyId(String ctyId);
	
	public String getMasothue();
	public void setMasothue(String Masothue);
	
	public String getChuTaiKhoan();
	public void setChuTaiKhoan(String chutaikhoan);
	public String getLoaiTien();
	public void setLoaiTien(String loaitien);
	public String getNganHang();
	public void setNganHang(String nganhang);
	public String getSoTaiKhoan();
	public void setSoTaiKhoan(String sotaikhoan);
	public String getTaiKhoanKeToan();
	public void setTaiKhoanKeToan(String taikhoanketoan);
	public String getChiNhanh();
	public void setChiNhanh(String chinhanh);
	public void init();
	public void createaRs();
	public boolean Update();
	public boolean Create();
	public boolean Enable();
	public boolean Delete();
	public ResultSet getCongTyRs();
	public void setCongTyRs(ResultSet congty);
	public ResultSet getLoaiTienRs();
	public void setLoaiTien(ResultSet loaitien);
	public ResultSet getChiNhanhRs();
	public void setNganHang(ResultSet nganhang);
	public ResultSet getNganHangRs();
	public void setChiNhanhRs(ResultSet chinhanh);
	public ResultSet getTaiKhoanRs();
	public void setTaiKhoanRs(ResultSet taikhoan);
	public void closeDB();
	
	public void setNppId(String nppId);
	public String getNppId();

	public void setNppList(List<Erp_Item> nppList);
	public List<Erp_Item> getNppList();
}