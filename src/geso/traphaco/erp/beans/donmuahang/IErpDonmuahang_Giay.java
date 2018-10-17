package geso.traphaco.erp.beans.donmuahang;

import java.sql.ResultSet;
import java.util.List;

public interface IErpDonmuahang_Giay {
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

	public String getmaDMH();

	public void setmaDMH(String maDMH);

	// Them 28-08-2012

	public String getId();

	public void setId(String id);

	public String getSochungtu();

	public void setSochungtu(String sochungtu);

	public String getNgaymuahang();

	public void setNgaymuahang(String ngaymuahang);

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

	// Thời Hạn Thanh Toán
	public String[] getSoTienThanhToanArr();

	public void setSoTienThanhToanArr(String[] soTienThanhToanArr);

	public String[] getNgayThanhToanArr();

	public void setNgayThanhToanArr(String[] ngayThanhToanArr);
	
	public String[] getPhanTramThanhToanArr();
	
	public void setPhanTramThanhToanArr(String[] phanTramThanhToanArr);
	//Thời Hạn Thanh Toán 

	public String getHinhThucTT();

	public void setHinhThucTT(String httt);

	public String getDiaDiemGiaoHang();

	public void setDiaDiemGiaoHang(String ddgh);

	public void close();

	public String[] getCpkDienGiai();

	public void setCpkDiengiai(String[] cpkDD);

	public String[] getCpkSoTien();

	public void setCpkSoTien(String[] cpkST);

	public String getCheckedNoiBo();

	public void setCheckedNoiBo(String checkedNoiBo);

	public String getDutoanId();

	public void setDutoanId(String dutoanId);

	public String getKenhId();

	public void setKenhId(String kenhId);

	public void setKenhRs(ResultSet kenhRs);

	public ResultSet getKenhRs();

	// Phân biệt các loại mua hàng : 0 Mua hàng NK, 1 Trong nước, 2 Mua
	// CP/TS/CCDC
	public String getLoai();

	public void setLoai(String Loai);

	// Dùng trong PO trong nước : 0 PO tổng, 1 PO con
	public String getIsDuocPhanBo();

	public void setIsDuocPhanBo(String isDuocPhanBo);

	// Dùng cho mua hàng nhập khẩu: 0 : Hợp đồng, 1 Đơn mua hang (Dùng cho quản
	// lý CN)
	public String getLoaiDMH_NK();

	public void setLoaiDMH_NK(String loaiDMH_NK);

	public String getThoihanno();

	public void setThoihanno(String thoihanno);

	public String getSohopdong();

	public void setSohopdong(String sohopdong);

	public String getSoluong();

	public void setSoluong(String soluong);

	public String getTennhanhapkhau();

	public void setTennhanhapkhau(String tennhank);

	public String getTennhasanxuat();

	public void setTennhasanxuat(String tennhasx);

	public String getNgayship();

	public void setNgayship(String ngayship);

	public String getNgaynhapkho();

	public void setNgaynhapkho(String ngaynhapkho);

	public String getDvChiuTrachNhiem();

	public void setDvChiuTrachNhiem(String dvchiutrachnhiem);

	public Long getTiGiaNguyenTe();

	public void SetTiGiaNguyenTe(Long tiGiaNguyenTe);

	public ResultSet getThttRs();

	public void setThttRs(ResultSet thttRs);
	
	public ResultSet getNccRs();

	public void setNccRs(ResultSet nccRs);
	
	public String getNhacungcapNK();
	public void setNhacungcapNK(String nhacungcapNK);
	
	public ResultSet getNguoiDuyetRs();
	public void setNguoiDuyetRs(ResultSet nguoiDuyetRs) ;

}
