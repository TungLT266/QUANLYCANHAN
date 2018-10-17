package geso.traphaco.erp.beans.phieuthanhtoan;

import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.erp.beans.phieuthanhtoan.IPhieuchitamung;

import java.sql.ResultSet;
import java.util.List;

public interface IErpDonmuahang_Giay
{
	public ResultSet getRsKhoanChiPhi() ;
	public void createKhoanMucChi();
	public void setRsKhoanChiPhi(ResultSet rsKhoanChiPhi);
	public String getLhhId();
	public String getIsDaChot();
	public String getIsHoanTat();
	public String getDuyetdntt_backButton();
	public void setIsHoanTat(String isHoanTat);
	public void setIsDaChot(String isDaChot) ;

	public void setLhhId(String lhhId) ;
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);

	// Them 28-08-2012
	public void CreateListTrungTamChiPhi();

	public void setNguonGocHH(String nguongoc);
	public String getNguonGocHH();

	public void setMaLoaiHH(String maloaihh);
	public String getMaLoaiHH();

	public void setTienTe_FK(String tiente_fk);
	public String getTienTe_FK();

	public void setTyGiaQuyDoi(float tygiaquydoi);
	public Float GetTyGiaQuyDoi();

	public String getGhiChu();
	public void setGhiChu(String ghichu);

	public void setTrungTamChiPhi_FK(String trungtamchiphi_fk);
	public String getTrungTamChiPhi_FK();

	public List<ITrungTamChiPhi> getTrungTamCpList();
	public void setTrungTamCpList(List<ITrungTamChiPhi> trungtamchiphi);
	
	public void CreatePOfromPR(ResultSet rs, String mnlId);
	public String getKhoanChiPhiId() ;

	public void setKhoanChiPhiId(String khoanChiPhiId);
	
	// Them 28-08-2012

	public String getId();
	public void setId(String id);

	public String getSochungtu();
	public void setSochungtu(String sochungtu);

	public String getNgaymuahang();
	public void setNgaymuahang(String ngaymuahang);


	public String getNguoidenghithanhtoan();
	public void setNguoidenghithanhtoan(String nguoidenghithanhtoan);
	
	public String getETD();
	public void setETD(String ETD);
	public String getETA();
	public void setETA(String ETA);

	public String getDvthId();
	public void setDvthId(String dvthid);

	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);

	public String getNCC();
	public void setNCC(String ncc);
	public String getNccTen();
	public void setNccTen(String nccTen);
	public String getNccLoai();
	public void setNccLOai(String nccLoai);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getTongtienchuaVat();
	public void setTongtienchuaVat(String ttchuavat);

	public String getVat();
	public void setVat(String vat);

	public String getTongtiensauVat();
	public void setTongtiensauVat(String ttsauvat);
	
	public String getTongtienconlai();
	public void setTongtienconlai(String tongtienconlai);

	public String getLoaispId();
	public void setLoaispId(String loaispid);

	public ResultSet getLoaiList();
	public void setLoaiList(ResultSet loaihhlist);

	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);

	public List<IDonvi> getDvList();
	public void setDvList(List<IDonvi> dvList);

	public List<ITiente> getTienteList();
	public void setTienteList(List<ITiente> ttList);

	public List<IKho> getKhoList();
	public void setKhoList(List<IKho> khoList);

	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaihanghoa();
	public void setLoaihanghoa(String loaihh);
	
	public String getIsdontrahang();
	public void setIsdontrahang(String dontrahang);

	public void createRs();
	public void init();
	public boolean createDmh();
	public boolean updateDmh();

	public String getDungsai();
	public void setDungsai(String dungsai);
	
	public String getTrangthaiDuyet();
	public ResultSet getDuyet();
	
	public String getMakeToStock();
	public void setMakeToStock(String maketoStock);

	public String[] getDuyetIds();
	public void setDuyetIds(String[] duyetIds);

	public String getKhoId();
	public void setKhoId(String khoId);
	
	public String getCanDuyet();
	public void setCanDuyet(String canduyet);
	
	public void setQuanlycongno(String quanlyCN);
	public String getQuanlycongno();
	
	public String getSoThamChieu();
	public void setSoThamChieu(String sothamchieu);
	
	public String[] getGhiChuArr();
	public void setGhiChuArr(String[] ghichuArr);
	
	
	public String getHinhThucTT();
	public void setHinhThucTT(String httt);
	public String getDiaDiemGiaoHang();
	public void setDiaDiemGiaoHang(String ddgh);
	public void close();
	/*
	 * Loại đối tượng là nhân viên hay nhà cung cấp
	 */
	public String getLoaidoituong();
	public void setLoaidoituong(String loaihh);
	
	public String getNvId();
	public void setNvId(String NvId);
	public String getNvTen();
	public void setNvTen(String NvTen);
	
	public String getKhId();
	public void setKhId(String KhId);
	public String getKhTen();
	public void setKhTen(String KhTen);
		
	public String[] getCpkDienGiai();
	public void setCpkDiengiai(String[] cpkDD);
	public String[] getCpkSoTien();
	public void setCpkSoTien(String[] cpkST);
	
	public String getCheckedNoiBo();
	public void setCheckedNoiBo(String checkedNoiBo);
	
	public void initPdf(String id);
	
	public String getLydoTT();
	public void setLydoTT(String lydo);
	

	public List<INhacungcap> getNccList();
	public void setNccList(List<INhacungcap> nccList);
	
	public String getTongtienCantru();
	public void setTongtienCantru(String tongtienCantru);

	
	public List<IPhieuchitamung> getPhieuchiTURs();
	public void setPhieuchiTURs(List<IPhieuchitamung> phieuchiTURs);
	
	public int getSodongPC();
	public void setSodongPc(int sodong_pc);
	
	public String getChucnang();
	public void setChucnang(String chucnang);
	
	public ResultSet getNhanvienRs();
	public void setNhanvienRs(ResultSet nhanvienRs);
	
	public ResultSet getNhaCungCapRs();
	public void setNhaCungCapRs(ResultSet nhacungcapRs);
	
	public ResultSet getKhachHangRs();
	public void setKhachHangRs(ResultSet khachhangRs);
	
	// ĐỊA CHỈ
	
	public String getDiachiNCC();
	public void setDiachiNCC(String diachincc);
	
	public ResultSet getTtDuyetRs();
	public void setTtDuyetRs(ResultSet TtDuyetRs);
	public String getIsKTV() ;

	public void setIsKTV(String isKTV) ;

	public String getIsKTT() ;

	public void setIsKTT(String isKTT) ;

	public String getIsTP() ;

	public void setIsTP(String isTP);

	public String getDuyetdntt();
	public void setDuyetdntt(String duyetdntt);
	
	public ResultSet getHtttRs();
	public void setHtttRs(ResultSet HtttRs);
	
	public String getHtttId();
	public void setHtttId(String htttId);
	
    public void setkbhRs(ResultSet kbhRs);
    public ResultSet getkbhRs();
    public String getkbhId();
    public void setkbhId(String kbhId);
    
    public ResultSet getPBList();
	public void setPBList(ResultSet pblist);
	
	public ResultSet getDiabanList();
	public void setDiabanList(ResultSet diabanlist);
	
	public ResultSet getSPList();
	public void setSPList(ResultSet splist);
	
	public ResultSet getBenhVienList();
	public void setBenhVienList(ResultSet bvlist);

	// THÔNG BÁO 
	
	public String getMsgCanhBao();
	public void setMsgCanhBao(String msgcanhbao);
	
	public void setLoaiDoiTuongList(List<Erp_Item> loaiDoiTuongList);
	public List<Erp_Item> getLoaiDoiTuongList();
	
	public void setChiNhanhId(String chiNhanhId);
	public String getChiNhanhId();

	public void setChiNhanhList(List<Erp_Item> chiNhanhList);
	public List<Erp_Item> getChiNhanhList();
	
	public void setSanPhamKhoList(List<Erp_Item> sanPhamKhoList);
	public List<Erp_Item> getSanPhamKhoList();
	
	public void setLanhDaoKyDuyet(int lanhDaoKyDuyet);
	public int getLanhDaoKyDuyet() ;
	
	public void changeNCC();
	
	public String getLoaiKM();
	
	public void setLoaiKM(String loaiKM);
	
	public void setSoChungTu_Chu(String soChungTu_Chu);
	public String getSoChungTu_Chu();

	public void setSoChungTu_So(String soChungTu_So);
	public String getSoChungTu_So();
	
	public void setDoiTuongKhacList(List<Erp_Item> doiTuongKhacList);
	public List<Erp_Item> getDoiTuongKhacList();
	
	public void setTaiKhoanDTKList(List<Erp_Item> taiKhoanDTKList);
	public List<Erp_Item> getTaiKhoanDTKList();
	
	public void setDoiTuongKhacId(String doiTuongKhacId);
	public String getDoiTuongKhacId();
	
	public void setTaiKhoanDoiTuongKhacId(String taiKhoanDoiTuongKhacId);
	public String getTaiKhoanDoiTuongKhacId();
	
	public void setTenTaiKhoanDoiTuongKhac(String tenTaiKhoanDoiTuongKhac);
	public String getTenTaiKhoanDoiTuongKhac();
	

	public String getNccMst() ;

	public void setNccMst(String nccMst) ;
	public String getNgoaiKhoan() ;

	public void setNgoaiKhoan(String ngoaiKhoan) ;
	String getNguoidenghithanhtoanTen();
	void setNguoidenghithanhtoanTen(String nguoidenghithanhtoanTen);
	String getChiNhanhTen();
	void setChiNhanhTen(String chiNhanhTen);
	String getDoiTuongKhacTen();
	void setDoiTuongKhacTen(String doiTuongKhacTen);
	
	public int getSoDongThem();
	public void setSoDongThem(int soDongThem);
	
	public String getThem();

	public void setThem(String them) ;
}