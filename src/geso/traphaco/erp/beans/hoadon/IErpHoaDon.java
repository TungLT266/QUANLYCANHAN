package geso.traphaco.erp.beans.hoadon;

import geso.traphaco.center.util.IPhan_Trang;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IErpHoaDon extends Serializable, IPhan_Trang
{
	public void init(String id, boolean in);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);

	public String getId();
	public void setId(String id);

	public String getNgayxuathd();
	public void setNgayxuathd(String ngayxuathd);
	public String getChoPhepSuaGiaHD();
	public void setChoPhepSuaGiaHD(String ChoPhepSuaGiaHD);

	public String getNppTen();
	public void setNppTen(String nppTen);

	public String getNppDiachi();
	public void setNppDiachi(String nppDiachi);
	
	
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
	
	public String getSoXuatKho();
	public void setSoXuatKho(String SoXuatKho);
	
	/**THEM TIEN TE**/
	public ResultSet getTienteRs();
	public void setTienteRs(ResultSet ttRs);
	public String getTienteId();
	public void setTienteId(String ttId);
	public String getTienteTen();
	public void setTienteTen(String ttTen);
	
	public String getTyGiaQuyDoi();
	public void setTyGiaQuyDoi(String tygia);
	
	public String getHinhthucxuat();
	public void setHinhthucxuat(String htxuat);
	
	
	public double getTongtienchuaVat();
	public void setTongtienchuaVat(double ttchuaVAT);

	public double getChietkhau();
	public void setChietkhau(double chietkhau);

	public double getTienkhuyenmai();
	public void setTienkhuyenmai(double tienKM);

	public String getKM_ghichu();
	public void setKM_ghichu(String KM_ghichu);
	
	public double getTienSauCK_KM();
	public void setTienSauCK_KM(double tiensauCK);

	public double getVAT();
	public void setVAT(double vat);
	
	public double getTienVAT();
	public void setTienVAT(double vat);

	public double getTongtiensauVAT();
	public void setTongtiensauVAT(double ttsvat);

	public String getMessage();
	public void setMessage(String msg);

	public void SetKyHieu(String kyhieu);
	public String getKyHieu();

	public void SetSoHoaDon(String sohoadon);
	public String getSoHoaDon();

	public String gethinhthuctt();
	public void sethinhthuctt(String hinhthuctt);

	public String getPOInfo();
	public void setPOInfor(String poInfo);

	public String getNoidungCK();
	public void setNoidungCK(String noidungCK);

	public ResultSet getListHoaDon();
	public void setListHoaDon(String sql);

	public void setTungay(String tungay);
	public String getTungay();

	public void setDenNgay(String denngay);
	public String getDenNgay();

	public String getUserid();
	public void setUserId(String user);

	public String getDonDatHang();
	public void setDonDatHang(String dondathang);

	public List<String> getDonDatHangIdList();
	public void setDonDatHangIdList(List<String> list);

	public void CreateRs();

	public ResultSet getrsddhChuaXuathd();

	public void SetNppId(String nppid);
	public String getNppId();

	public List<IErpHoaDon_SP> GetListSanPham();
	public List<IErpHoaDon_SP> GetListSanPhamCone();
	
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

	public void SetGhiChu(String ghichu);

	public String getGhiChu();

	public String getHoanTat();
	public void setHoanTat(String HoanTat);

	public boolean EditHT();

	public String getKenhbanhangId();
	public void setKenhbanhangId(String kenhbanhangId);
	
	
	/* THEM 1 SO THONG TIN */
	public ResultSet getHopdongRs();
	public void setHopdongRs(ResultSet hdRs);
	public String getHopdongId();
	public void setHopdongId(String hdId);
	
	public void setNgayghinhanCN(String ngayghinhanCN);
	public String getNgayghinhanCN();
	public void setGhichu(String ghichu);
	public String getGhichu();
	
	public void setSanphamGhiChu( Hashtable<String, String> sanpham_ghichu );
	public Hashtable<String, String> getSanphamGhiChu();
	
	
	public void setSanphamGia( Hashtable<String, Double> SanphamGia );
	public Hashtable<String, Double> getSanphamGia();
	
	
	public void sethtb_SoluongMoi( Hashtable<String, Double> htb_SoluongMoi );
	public Hashtable<String, Double> gethtb_SoluongMoi();
	
	
	
	/**************************/

	//Dùng cho in hóa đơn
	public int getSoDongSanPham();
	public int getSoKyTu1DongSanPham();

	public void DbClose();


	public void setTienChietKhau(double tienck);
	public double getTienChietKhau();

	public String getPaymentTerms();
	public void setPaymentTerms(String paymentTerms);

	public String getDeliveryTerms();
	public void setDeliveryTerms(String deliveryTerms);

	public String getETD();
	public void setETD(String ETD);

	public String getRemarks();
	public void setRemarks(String remarks);
	
	public double getTienVanChuyen();
	public void setTienVanChuyen(String freightCost);
	public void setTienVanChuyen(double freightCost);
	
	public double getTienBaoHiem();
	public void setTienBaoHiem(String SoTienBaoHiem);
	public void setTienBaoHiem(double SoTienBaoHiem);


	public String getCustomersPO();
	public void setCustomersPO(String cpo);

	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	public void createDvkdRs();
	
	public String getSoPO();
	public void setSoPO(String SoPO);
	
	public String getinvoice();
	public void setinvoice(String invoice);
	
	//IN LỊCH SỬ IN H
	public String CreateLSIN(String ddhId, String loaihd);
	
	public ResultSet getLichSuInRs();
	public void setLichSuInRs(ResultSet lichSuInRs);
	public String getLichSuInId();
	public void setLichSuInId(String lichSuInId);

	public void initLSIN(String ddhId);
	
	public List<IErpHoaDonList> getHoadonList();
	public void setHoadonList(List<IErpHoaDonList> hoadonList);
	
	public String getNccId();
	public void setNccId(String nccId);
	
	public void hoadon_pdf_XK(String sohoadon);
	public String getSohopdong();
	public void setSohopdong(String sohopdong);
	public String getDienthoai();
	public void setDienthoai(String dienthoai);
	public String getFax();
	public void setFax(String fax);
	
	
	
}
