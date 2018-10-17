package geso.traphaco.erp.beans.lenhsanxuat;

import geso.traphaco.erp.beans.nhapkho.IKhu_Vitri;
import geso.traphaco.erp.beans.phieuxuatkho.ISanpham;

import java.sql.ResultSet;
import java.util.List;

public interface IErpChuyenkhoSX
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);
	
	public String getLydoyeucau();
	public void setLydoyeucau(String lydoyeucau);
	
	public String getNguoinhan();
	public void setNguoinhan(String Nguoinhan);
 
	public String getGhichu();
	public void setGhichu(String ghichu);
	
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
	
	
	public String getNdxId();
	public void setNdxId(String ndxId);
	
	public String getnoiDungXuat();
	public void setnoiDungXuat(String noidungxuat);
	
	public ResultSet getNdxList();
	public void setNdxList(ResultSet ndxList);
	
	public String getKhoXuatId();
	public void setKhoXuatId(String khoxuattt);
	
	public String getIsQuanlykhuvuc_khoxuat();
	
	public String getIsQuanlykhuvuc_khonhap();
	 
	
	
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
	
	public List<ISanpham> getSPList();
	public void setSPList(List<ISanpham> SPList);
	
	public List<IYeucau> getSpChoNhapList();
	public void setSpChoNhapList(List<IYeucau> spchoNhapList);
	
	public String getMsg();
	public void setMsg(String msg);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
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
	
	public String getNccChuyenIds();
	public void setNccChuyenIds(String nccChuyenIds);
	public ResultSet getNccChuyenRs();
	public void setNccChuyenRs(ResultSet nccChuyenRs);
	
	public String getNccNhanIds();
	public void setNccNhanIds(String nccNhanIds);
	public ResultSet getNccNhanRs();
	public void setNccNhanRs(ResultSet nccNhanRs);
	
	public ResultSet getRsKhukhonhan();
	public void setRsKhukhonhan(ResultSet Rskhu);
	
	public boolean chuyenNL();
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
	public boolean createChuyenKho(String idyeucau);
	
	
	public String getIsChuyenHangSX();
	public void setIsChuyenHangSX(String IsChuyenHangSX);
	
	public String getDiachi();
	public void setDiachi(String diachi);
	
	
	public String getYeucauchuyenkhoId();
	public void setYeucauchuyenkhoId(String YeucauchuyenkhoId);
	
	public String getTongSLYC();
	public void setTongSLYC(String tongSLYC);
	public String getTongSLnhan();
	public void setTongSLnhan(String tongSLnhan);
	public void inPdf(); 
	public String getDvkd();
	public String getNguoinhanhang();
	public String getXuattaikho();
	public void initXuatkhoPdf();
	public boolean getChoPhepChuyenhangdat();
	public void settask(String task);
	public String gettask();
	public ResultSet getTsddRs();
	public void setTsddRS(ResultSet tsddRs); 
	public String getTsddId();
	public void setTsddId(String tsddId);
}
