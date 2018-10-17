package geso.traphaco.erp.beans.hoadontrahang;

import java.sql.ResultSet;
import java.util.List;

public interface IErpHdTraHang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();

	public void setId(String id);

	public String getNgayXuatHD();

	public void setNgayXuatHD(String NgayXuatHD);

	public String getKhTen();

	public void setKhTen(String KhTen);

	public double getTienVAT();

	public void setTienVAT(double TienVAT);
	
	public double getTienAVAT();

	public void setTienAVAT(double TienAVAT);	
	
	public double getTienBVAT();

	public void setTienBVAT(double TienBVAT);	

	public String getUserId();

	public void setUserId(String UserId);

	public String getMsg();

	public void setMsg(String msg);

	public void SetKyHieu(String kyhieu);

	public String getKyHieu();

	public void SetSoHoaDon(String sohoadon);

	public String getSoHoaDon();

	public String getHinhThucTT();

	public void setHinhThucTT(String HinhThucTT);

	public double getTienCK();

	public void setTienCK(double tienck);

	public double getTienSauCK();

	public void getTienSauCK(double tiensauCK);

	public double getVAT();

	public void setVAT(double VAT);

	public double getTienSauVAT();

	public void getTienSauVAT(double tienSauVAT);

	public String[] getDonTraHang();

	public void setDonTraHang(String[] DonTraHang);

	public void CreateRs();

	public ResultSet getRsDthChuaXuatHD();

	public void setRsDthChuaXuatHD(ResultSet RsDthChuaXuatHD);

	public void SetKhId(String Khid);

	public String getKhId();

	public void SetNguoiMuaHang(String nguoimuahang);

	public String getNguoiMuaHang();

	/*
	 * Số PO kênh siêu thị
	 */
	public void SetPOMT(String PoMt);

	public String GetPOMT();

	public List<IErpHdTraHang_SanPham> GetListSanPham();

	public boolean Save();

	public boolean Edit();

	public String ChotHd();

	public String XoaHd();

	public void Init();

	public void initdisplay();

	public void SetGhiChu(String ghichu);

	public String getGhiChu();
	public void InitDisplay();

	public ResultSet getkhRs();
	public void setkhRs(ResultSet khRs);

	public void SetKhongHD(String KhongHD);

	public String getKhongHD();
	
	public String getNppdangnhap();
	public void setNppdangnhap(String nppdangnhap);
	
	public ResultSet getHoadonCanTruRs();
	public void setHoadonCanTruRs(ResultSet HoadonCanTruRs);
	
	public void SetHoadonCanTruId(String HoadonCanTruId);
	public String getHoadonCanTruId();
	
	public String getKhachhangId();
	public void setKhachhangId(String khachhangId);
	
	public String getisNPP();
	public void setisNPP(String isNPP);
	
	public String getkbhId();
	public void setkbhId(String kbhId);
}
