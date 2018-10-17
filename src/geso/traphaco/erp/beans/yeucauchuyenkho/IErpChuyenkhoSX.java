package geso.traphaco.erp.beans.yeucauchuyenkho;

import geso.traphaco.erp.beans.nhapkho.IKhu_Vitri;

import java.sql.ResultSet;
import java.util.List;

public interface IErpChuyenkhoSX
{
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
	
	public String getCDSXId();

	public void setCDSXId(String CDSXId);
	
	public ResultSet getCDSXRs();

	public void setCDSXRs(ResultSet CDSXRs);
	
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
	public ResultSet getTsddRs();
	public void setTsddRS(ResultSet tsddRs); 
	public String getTsddId();
	public void setTsddId(String tsddId);
	
	public String getCoDoituong();
	public void setCoDoituong(String codoituong);
	public String getLoaidoituongId();
	public void setLoaidoituongId(String loaidoituong);
	public String getDoituongId();
	public void setDoituongId(String doituongId);
	public ResultSet getDoituongRs();
	public void setDoituongRs(ResultSet doituongRs);
	
	public String getCoKhonhan();
	public void setCoKhonhan(String cokhoNHAN);
	public String getCoDoituongNhan();
	public void setCoDoituongNhan(String codoituongNhan);
	public String getLoaidoituongNhanId();
	public void setLoaidoituongNhanId(String loaidoituongNhan);
	public String getDoituongNhanId();
	public void setDoituongNhanId(String doituongNhanId);
	public ResultSet getDoituongNhanRs();
	public void setDoituongNhanRs(ResultSet doituongNhanRs);
	
	public String getMaNDX(String ndxId);
	
	public String getCochiphi();
	public void setCochiphi(String coChiphi);
	public String getChiphiId();
	public void setChiphiId(String chiphiId);
	public ResultSet getChiphiRs();
	public void setChiphiRs(ResultSet chiphiRs);
	
	public String getMuahang_fk();
	public void setMuahang_fk(String muahang_fk) ;
	public ResultSet getMuahangList() ;
	public void setMuahangList(ResultSet muahangList) ;
	
	public ResultSet getTonKhoChiTiet(String khoId, String spMa,String lenhsxId);
	
}
