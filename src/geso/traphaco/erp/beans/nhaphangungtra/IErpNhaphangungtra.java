package geso.traphaco.erp.beans.nhaphangungtra;

import geso.traphaco.erp.beans.donmuahang.IKho;
import geso.traphaco.erp.beans.nhapkho.IKhu_Vitri;

import java.sql.ResultSet;
import java.util.List;

public interface IErpNhaphangungtra
{
	public String getLspId();
	public void setLspId(String lspId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCtyId();
	public void setCtyId(String ctyId);
 
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);
	
	public String getLydoyeucau();
	public void setLydoyeucau(String lydoyeucau);
	
	public String gettask();
	public void settask(String task);
	
	public String getKyHieu();
	public void setKyHieu(String kyhieu);
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	
	public String getLenhdieudong();
	public void setLenhdieudong(String lenhdieudong);
	
	public String getNgaydieudong();
	public void setNgaydieudong(String ngaydieudong);
	
	public String getNguoidieudong();
	public void setNguoidieudong(String nguoidieudong);
	
	public String getVeviec();
	public void setVeviec(String veviec);
	
	public String getNguoivanchuyen();
	public void setNguoivanchuyen(String nguoivanchuyen);
	
	public String getPhuongtien();
	public void setPhuongtien(String phuongtien);
	
	public String getHopdong();
	public void setHopdong(String hopdong);
	
	
	public String getNguoinhan();
	public void setNguoinhan(String Nguoinhan);
	 
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getTongSLYC();
	public void setTongSLYC(String tongSLYC);
	
	public String getNdxId();
	public void setNdxId(String ndxId);
	public ResultSet getNdxList();
	public void setNdxList(ResultSet ndxList);
	public ResultSet getLspRs();

	public void setLspRs(ResultSet lspRs);
	public String getKhoXuatId();
	public void setKhoXuatId(String khoxuattt);
	public String getKhoXuatTen();
	public void setKhoXuatTen(String khoxuattt);
	public ResultSet getKhoXuatRs();
	public void setKhoXuatRs(ResultSet khoxuatRs);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public String getKhoNhapTen();
	public void setKhoNhapTen(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getLsxIds();
	public void setLsxIds(String lsxIds);
	public ResultSet getLsxRs();
	public void setLsxRs(ResultSet lsxRs);
	
	public List<IYeucau> getSpList();
	public void setSpList(List<IYeucau> spList);
	
	public List<IYeucau> getSpChoNhapList();
	public void setSpChoNhapList(List<IYeucau> spchoNhapList);
	
	public String getMsg();
	public void setMsg(String msg);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getIsChuyenHangSX();
	public void setIsChuyenHangSX(String IsChuyenHangSX);
	
	
	public String getIsnhanHang();
	public void setIsnhanHang(String ischuyenKho);
	
	public List<IKhu_Vitri> getKhuList();
	public void setKhuList(List<IKhu_Vitri> khuList);

	public List<IKhu_Vitri> getVitriList();
	public void setVitriList(List<IKhu_Vitri> vitriList);
	
	public String getTrangthaiSP();
	public void setTrangthaiSP(String trangthaisp);
	
	public boolean createCK();
	public boolean updateCK();
	public boolean nhanHang();
	public boolean xuatHang();
	
	public boolean updateSoHoaDon();
	
	public String getNccChuyenIds();
	public void setNccChuyenIds(String nccChuyenIds);
	public ResultSet getNccChuyenRs();
	public void setNccChuyenRs(ResultSet nccChuyenRs);
	
	public String getNccNhanIds();
	public void setNccNhanIds(String nccNhanIds);
	public ResultSet getNccNhanRs();
	public void setNccNhanRs(ResultSet nccNhanRs);
	
	public String getLenhSXId();
	public void setLenhSXId(String LenhSXId);
	public List<ILenhsanxuat> getLenhSXList();
	public void setLenhSXList(List<ILenhsanxuat> lenhSXList);
	
	public boolean chuyenNL();
	
	public String getIsChuyenhangkhongdat();
	public void setIsChuyenhangkhongdat(String chuyenhangkhongdat);
	public boolean getChoPhepChuyenhangdat();
	
	public void createRs();

	public void init();
	public void initNhanhang();
	public void initXuathang();
	public void initView();
	public void initPdf();
	
	public void initYeucauNLPdf();
	public void initChuyenNLPdf();
	
	public void DBclose();
	public boolean createChuyenKho_LSX();
	public boolean createChuyenKho_LSX_DANH();
	public boolean createChuyenKhoThem(String khoyeucauthem);
 
	public String getIsKhoTrinhDuyetVien(String khoid);
	public ResultSet getNvRs_nhan();
	public ResultSet getNvRs_xuat();
	
	public void setNvId_nhan(String nvid_nhan);
	public void setNvid_xuat(String nvid_xuat);
	
	public String getNvId_nhan();
	public String getNvId_xuat();
	
	
	
	public String getIsKhoDuTruKhachHang_Kygui(String khoid);
	public ResultSet getKhachHangRs_nhan();
	public ResultSet getKhachHangRs_xuat();
	public void setKhachHangId_nhan(String KhachHangid_nhan);
	public void setKhachHangid_xuat(String KhachHangid_xuat);
	
	public String getKhachHangId_nhan();
	public String getKhachHangId_xuat();
	
	public String getIsKhoCuaNCC_KyGui(String khoid);
	 
	public boolean getIsCoKhoNhan();
	
	public String getLoaiKhoXuat();
	public String getNhomKenhId();
	

	public ResultSet getDonvithuchienRs();
	public ResultSet getNhomChiPhiRs();
	
	public void setDonvithuchienId(String dvthId);
	public void setNhomChiPhiId(String ncphiid);
	
	public String getDonvithuchienId();
	public String getNhomChiPhiId();
	
	public String getNguoidenghi();
	public void setNguoidenghi(String nguoidenghi);
	
	public boolean getCoKhoanMucChiPhi();
	public String getCongytyId();
	public void setCongtyId(String congtyId);
	
	
	public String getDoiTuongUngHangId();
	public void setDoiTuongUngHangId(String DoiTuongUngHangId);
	
	public ResultSet getRsDoiTuongUngHang();
	public void setRsDoiTuongUngHang(ResultSet Rs);
	

	public String getKenhId();
	public void setKenhId(String kenhId);
	
	public ResultSet getRsKenh();
	public void setRsKenh(ResultSet Rs);
	
	public String getNdxMa();
	public void setNdxMa(String ndxMa);
	 
}
