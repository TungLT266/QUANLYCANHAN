package geso.traphaco.erp.beans.hoadontravencc;
import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.erp.beans.hoadon.IErpHoaDonList;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpHoaDon extends Serializable, IPhan_Trang 
{
	public String getId();
	public void setId(String id);	
	public String getNgayxuathd();
	public void setNgayxuathd(String ngayxuathd);
	public String getNppTen();
	public void setNppTen(String nppTen);		
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public double getChietkhau();
	public void setChietkhau(double chietkhau);
	public double getVAT();
	public void setVAT(double vat);	
	
	public double getTienVat();
	public void setTienVat(double tienvat);	
	
	public String getMessage();
	public void setMessage(String msg);
	public  void SetKyHieu(String kyhieu);
	public String getKyHieu();
	public void SetSoHoaDon(String sohoadon);
	public String getSoHoaDon();
	public String gethinhthuctt();
	public void sethinhthuctt(String hinhthuctt);
	public double getTongtienchuaCK();
	public void setTongtienchuaCK(double ttcck);
	public double getTienCK();
	public void setTienCK(double tienck);
	
	public String getPOInfo();
	public void setPOInfor(String poInfo);
	public String getNoidungCK();
	public void setNoidungCK(String noidungCK);
	
	public double getTienSauCK();
	public void getTienSauCK(double tiensauCK);
	
	public double getTongtientruocVAT();
	public void setTongtientruocVAT(double tttvat);
	
	public double getTongtiensauVAT();
	public void setTongtiensauVAT(double ttsvat);
	
	public ResultSet getListHoaDon();
	public void setListHoaDon(String sql);
	
	public void setTungay(String tungay);
	public String getTungay();
	public void setDenNgay(String denngay);
	public String getDenNgay();
	
	public String getUserid();
	public void setUserId(String user);
	
	public String[] getDonDatHang();
	
	public String[] getScheme_chietkhau();
	public String[] getScheme_ck_thanhtien();
	
	
	public void setScheme_chietkhau(String[] Scheme_chietkhau);
	
	public void setScheme_ck_thanhtien(String[] Scheme_ck_thanhtien);
	
	public void setDonDatHang(String[] dondathang);
	public void CreateRs(boolean loadlaighichu);
	public ResultSet getrsddhChuaXuathd();
	
	public void SetNppId(String nppid);
	public String getNppId();
	
	public List<IErpHoaDon_SP> GetListSanPham();
	public void setListsanpham(List<IErpHoaDon_SP> listsanpham) ;
	
	public void SetNguoiMuaHang(String nguoimuahang);
	public String getNguoiMuaHang();

	public boolean Save();
	public boolean Edit();
	public void initdisplay(String ddhId);
	/*
	 * Số PO kênh siêu thị
	 */
	public void SetPOMT(String PoMt);
	public String GetPOMT();
	
	public void SetSKU(String SKU);
	public String GetSKU();
	
	public void SetGhiChu(String ghichu);
	public String getGhiChu();
	
	public String getHoanTat();
	public void setHoanTat(String HoanTat);
	
	public String getLoaihoadon();
	public void setLoaihoadon(String loaihoadon);

	public String getGhiChu1();
	public void setGhiChu1(String ghichu1);
	public String getGhiChu2();
	public void setGhiChu2(String ghichu2);
	public String getGhiChu3();
	public void setGhiChu3(String ghichu3);
	
	public boolean EditHT();
	public String ChotHoaDon();
	
	public String getSochungtu();
	public String getTenKhXhd();
	public void setTenKhXhd(String TenKhXhd);
	public String getMasothueXhd();
	public void setMasothueXhd(String MasothueXhd);
	public String getDiachiXhd();
	public void setDiachiXhd(String DiachiXhd);
	
	public List<IErpHoaDonList> getHoadonList();
	public void setHoadonList(List<IErpHoaDonList> hoadonList);
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
	public void setCongtyId(String ctyId);
	public String getCongtyId();
	public void DBClose();
	
	public String getSodontrahang();

	public void setSodontrahang(String sodontrahang);
	


}
