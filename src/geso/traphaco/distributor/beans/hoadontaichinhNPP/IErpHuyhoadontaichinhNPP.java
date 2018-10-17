package geso.traphaco.distributor.beans.hoadontaichinhNPP;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import geso.traphaco.center.util.IThongTinHienThi;
import java.util.List;

public interface IErpHuyhoadontaichinhNPP extends Serializable, IPhan_Trang
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
	
		
	public String getNgayghino();
	public void setNgayghino(String ngayghino);
	
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
	

	public ResultSet getListHoaDon();
	public void setListHoaDon(String sql);
	
	public void setTungay(String tungay);
	public String getTungay();
	public void setDenNgay(String denngay);
	public String getDenNgay();
	
	public String getUserid();
	public void setUserId(String user);
	
	public String[] getDonDatHang();
		
	public void setDonDatHang(String[] dondathang);
	public void loadContents();
	public ResultSet getrsddhChuaXuathd();
	
	public void SetNppId(String nppid);
	public String getNppId();

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
	
	public ResultSet getNppList();

	public void setNppList(ResultSet nppList);
	
	public String getTenKhXhd();
	public void setTenKhXhd(String TenKhXhd);
	public String getMasothueXhd();
	public void setMasothueXhd(String MasothueXhd);
	public String getDiachiXhd();
	public void setDiachiXhd(String DiachiXhd);
	public void setInBangKe(String inBangKe);
	public String getInBangKe();
	public boolean isInBangKe();
	public void CreateRsNPPghinhan();
	
	public String getkhId();
	public void setkhId(String khId);
	public ResultSet getkhRs();
	public void setkhRs(ResultSet khRs);
	
	public String getDonhangId();
	public void setDonhangId(String dhId);
	public ResultSet getDonhangRs();
	public void setDonhangRs(ResultSet ddhRs);
	
	
	public String getddhId();
	public void setddhId(String ddhId);
	
	
	//TIEN TREN DON HANG
	
	public double getChietkhau();
	public void setChietkhau(double chietkhau);
	
	public double getTongtienchuaCK();
	public void setTongtienchuaCK(double ttcck);
	
	public double getTienCK();//TIỀN CHIẾT KHẤU
	public void setTienCK(double tienck);
	
	public double getThanhTien();//TIỀN BVAT
	public void seThanhTien(double thanhtien);
	
	public double getTienAVAT();//TIỀN AVAT
	public void setTienAVAT(double tienavat);
	
	public double getTienCKThuongMai();
	public void setTienCKThuongMai(double tienckthuongmai);
	
	//SP TREN DON HANG
	
	public String[] getSpId();
	public void setSpId(String[] spId);
	
	public String[] getSpMa();
	public void setSpMa(String[] spMa);
	
	public String[] getSpTen();
	public void setSpTen(String[] spTen);
	
	public String[] getSpDonvi();
	public void setSpDonvi(String[] spDonvi);
	
	public String[] getSpMaDonvi();
	public void setSpMaDonvi(String[] spMaDonvi);
	
	public String[] getSpSoluong();
	public void setSpSoluong(String[] spSoluong);
	
	public String[] getSpSoluonggoc();
	public void setSpSoluonggoc(String[] spSoluonggoc);
	
	public String[] getSpDongia();
	public void setSpDongia(String[] spDongia);
		
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	
	public String[] getSpChietKhau();
	public void setSpChietKhau(String[] spChietKhau);
	
	public String[] getSpTienVat();
	public void setSpTienVat(String[] spTienVat);
	
	public String[] getSpTienCK();
	public void setSpTienCK(String[] spTienCK);
		
	public String[] getSpThanhTien();
	public void setSpThanhTien(String[] spThanhtien);
	
	public void createRs();
	
	public void init_new();
	
	public void init();
	//
	public void DBclose();
	
	public void setDisplayHD();
	
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	public boolean createHuyhoadontaichinh();
	public boolean updateHuyhoadontaichinh();
	
	public String getSoCT();
	public void setSoCT(String SoCT);
	
	public String getNgayHuy();
	public void setNgayHuy(String NgayHuy);
	public String getLoaiHD();
	public void setLoaiHD(String loaiHD);
	
	public String getCtyId();
	public void setCtyId(String ctyId);
}
