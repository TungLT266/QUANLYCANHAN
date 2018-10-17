package geso.traphaco.erp.beans.donbanhang;

import geso.traphaco.erp.beans.donbanhang.ISanpham;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IErp_Donbanhang 
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();
	public void setId(String id);	
	public String getNgaygiaodich();
	public void setNgaygiaodich(String ngaygiaodich);
	public String getNgaydukiengiao();
	public void setNgaydukiengiao(String ngaygiaodich);
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
	public String getISKM();
	public void setISKM(String iskm);	
	public void setloaichietkhau(String loaichietkhau);
	public String getloaichietkhau();
	
	public void setUngck(double ungck);
	public double getUngck();
	
	public void setCkthuongmai(double ckthuongmai);
	public double getCkthuongmai();
	
	public void setCKTrucTiep(double CKTrucTiep);
	public double getCKTrucTiep();
	
	public String getMessage();
	public void setMessage(String msg);
	 
	public void setrs_nhacc(ResultSet rsncc);
	
	public ResultSet GetRsnhacc();
	
	public String[] getSotien();
	public void setSotien(String[] sotien);
	
	public String[] getScheme();
	public void setScheme(String[] scheme);
	
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getNoidungchietkhau();
	public void setNoidungchietkhau(String noidungchietkhau);
	
	public String getIdNhaCungCap();
	public void  setIdNhaCungCap(String idnhacc);
	
	public String getTenNhaCungCap();
	public void  setTenNhaCungCap(String tennhacc);
	
	public void setListSanPham(List<IErp_Donbanhang_SP> list);
	public List<IErp_Donbanhang_SP> getListSanPham();
	
	public String getIDKenhBanHang();
	public void setIDKenhBanHang(String kenhbanhangid);
	
	public void setrs_kbh(ResultSet rskbh);
	public ResultSet GetRsKbh();
	
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public void setrs_nhapp(ResultSet rsnpp);
	
	public ResultSet GetRsnhapp();
	
	public String getKhottId();
	public void setKhottId(String khottid);
	

	public void setRskhott(ResultSet rskhott);
	public ResultSet GetRskhott();
	public String getKhottTen();
	public void setKhottTen(String KhottTen);
	
	public double getTongtientruocVAT();
	public void setTongtientruocVAT(double tttvat);
	public double getTongtiensauVAT();
	public void setTongtiensauVAT(double ttsvat);
	
	public double getTongtienKM();
	public void setTongtienKM(double ttKM);
	public double getTongtiensauKM();
	public void setTongtiensauKM(double ttSauKm);
	
	public void Init();
	public void InitPdf();
	
	public Hashtable<String, Integer> getSpThieuList();
	public void setSpThieuList(Hashtable<String, Integer> spThieuList);
	
	public ResultSet getrsdvkd();
	public void setrsdvkd(ResultSet rsdvkd);
	
	public void setdvkdid(String dvkdid);
	public String getdvkdid();
	
	public void setUserTen(String Userten);
	public String getUserten();
		
	public void setUserId(String UserId);
	public String getUserId();
	
	
	//tra khuyen mai
	public Hashtable<String, Float> getScheme_Tongtien();
	public void setScheme_Tongtien(Hashtable<String, Float> scheme_tongtien);
	public Hashtable<String, Float> getScheme_Chietkhau();
	public void setScheme_Chietkhau(Hashtable<String, Float> scheme_chietkhau);
	public List<ISanpham> getScheme_SpList();
	public void setScheme_SpList(List<ISanpham> splist);
	
	public boolean Save();
	public boolean Edit(String _ischot);
	public boolean Delete();
	public void DBClose();
	public void CreateRs();
	public String getdiachi();
	public String getdiachixhd();
	public String getmasothue();
	public  void setdiachi(String diachi);
	public  void setdiachixhd(String diachixhd);
	public  void setmasothue(String mst);
	
	public String getMakeToStock();
	public void setMakeToStock(String maketoStock);
	
	public ResultSet getHopDongRs();
	public void setHopDongRs(ResultSet hdRs);
	public String getHopdongId();
	public void setHopdongId(String hdId);
	public String getLoaihopdong();
	public void setLoaihopdong(String lhdId);
	
	public String getChophepsuagia();
	public void setChophepsuagia(String cpSuaGiaId);
	public String getChophepdoiKH();
	public void setChophepdoiKH(String cpDoiKh);
	
	public ResultSet getTienteRs();
	public void setTienteRs(ResultSet ttRs);
	public String getTienteId();
	public void setTienteId(String ttId);
	
	public String getTyGiaQuyDoi();
	public void setTyGiaQuyDoi(String tygia);
	
	public String getSoTienBaoHiem();
	public void setSoTienBaoHiem(String SoTienBaoHiem);

	public double getDungsai();
	public void setDungsai(String dungsai);
	public void setDungsai(double dungsai);
	
	public String getHinhthucTT();
	public void setHinhthucTT(String hinhthuctt);

	public String getPaymentTerms();
	public void setPaymentTerms(String paymentTerms);

	public String getDeliveryTerms();
	public void setDeliveryTerms(String deliveryTerms);

	public String getETD();
	public void setETD(String ETD);

	public String getRemarks();
	public void setRemarks(String remarks);
	
	public double getFreightCost();
	public void setFreightCost(String freightCost);
	public void setFreightCost(double freightCost);
	
	public String getCustomerspo();
	public void setCustomerspo(String customerspo);
	
	public String getSoPo();
	public void setSoPo(String SoPo);
 
	public ResultSet getDvdlRs();
	public void setDvdlRs(ResultSet dvdlRs);
	public void InitDisplay();
	public boolean SaveDungSai();
	
	public void Donbanhang_XK(String donhangId);
	public String getTienteTen();
	public void setTienteTen(String TTten);
	
	public String getHopdongTen();
	public void setHopdongTen(String hopdongten);
	
	public String formatTienVND( String tienVND);
	
	public String formatTienVND_2( String tienVND_2);
	
	
	public String getHanmucno();	
	
	public String getThoihanno();
	
	public String getTongsono();
	
	public String getNoquahan();
	
	public String getNotronghan();
	
	public String getNgayvuothanno();
	
	public String getSonovuothanmuc();
	
	public String getYCGDDuyet();
	public void setYCGDDuyet(String yeucauGDDuyet);
	
	public String getDathanhtoan();
	public void setDathanhtoan(String dathanhtoan);
	
	public String getduyetDH();
	public void setduyetDH(String duyetdh);
	
	public String[] getTichLuy_Scheme();
	public void setTichLuy_Scheme(String[] tichluy_scheme);
	public String[] getTichLuy_Tongtien();
	public void setTichLuy_Tongtien(String[] tichluy_tongtien);
	public void ApTichLuy();
	
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	
	public String getDiachigiaohang();
	public void setDiachigiaohang(String diachigiaohang);
	
	public boolean getCoKhuyenmai();
	public void setCoKhuyenmai(boolean value);
	
	public String getBgId();
	public void setBgId(String bgId);
		
	public String getSchemeIds();
	public void setSchemeIds(String schemeIds);
	public ResultSet getSchemeRs();
	public void setSchemeRs(ResultSet schemeRs);
	
	public String getTungaytk();
	public void setTungaytk(String tungaytk);
	
	public String getDenngaytk();
	public void setDenngaytk(String denngaytk);
	
	public String getKhachhangtk();
	public void setKhachhangtk(String khachhangtk);
	
	public String getLoaidhtk();
	public void setLoaidhtk(String loaidhtk);
	
	public String getTrangthaitk();
	public void setTrangthaitk(String Trangthaitk);
	
	public String getDonvikinhdoanhtk();
	public void setDonvikinhdoanhtk(String Donvikinhdoanhtk);
	
	public String getSochungtutk();
	public void setSochungtutk(String Sochungtutk);
	
	public String getNguoitaotk();
	public void setNguoitaotk(String Nguoitaotk);
	
	public String getKhodattk();
	public void setKhodattk(String Khodattk);
	
	public String getKbhtk();
	public void setKbhtk(String Kbhtk);
	
	public String getBantructiep();
	public void setBantructiep(String bantructiep);
	
	public ResultSet getChitietCongnoRs();
	public void setChitietCongnoRs(ResultSet ChitietCongnoRs);
}

