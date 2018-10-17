package geso.traphaco.distributor.beans.hopdong;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpDonhangNppETCList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getMaHopDong();
	public void setMaHopDong(String maHopdong);
	
	public String getSoHopDong();
	public void setSoHopDong(String soHopDong);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getKhTen();
	public void setKhTen(String khTen);
	
	public String getMafast();
	public void setMafast(String mafast);
	
	public String getSodh();
	public void setSodh(String sodh);
	
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getKbhId();
	public void setKbhId(String KbhId);
	
	
	public String getKvId();
	public void setKvId(String KvId);
	
	public ResultSet getKbhRs();
	
	public ResultSet getHtbhRs();
	
	public String getHtbhId();
	public void setHtbhId(String HtbhId);
	
	public ResultSet getKvRs();
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public List<ISanpham> getListSP();
	public void setListSP(List<ISanpham> lstSP);
	public List<IDonhang> getListDH();
	public void setListDH(List<IDonhang> lstDH);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String search);
	public void initTHEODOIDONHANG(String search);
	public void initDCDH(String search);
	public void DBclose();

	
	public String getNgaygiao() ;

	public void setNgaygiao(String ngaygiao);

	public String getNgayno() ;

	public void setNgayno(String ngayno);

	public String getNguoigiao();

	public void setNguoigiao(String nguoigiao);

	public String getTensp();

	public void setTensp(String tensp);

	public String getChietkhau();

	public void setChietkhau(String chietkhau) ;

	public String getThuegtgt();

	public void setThuegtgt(String thuegtgt) ;

	public String getTongtien();

	public void setTongtien(String tongtien) ;

	public String getKhohangid();

	public void setKhohangid(String khohangid);
	public String getNvbanhang();

	public void setNvbanhang(String nvbanhang) ;
	public ResultSet getRsnvbanhang();

	public void setRsnvbanhang(ResultSet rsnvbanhang) ;
	public ResultSet getRskhoid();

	public void setRskhoid(ResultSet rskhoid);
	public ResultSet getNvgnid() ;

	public void setNvgnid(ResultSet nvgnid);
	public String getGhichu();

	public void setGhichu(String ghichu);
	public ResultSet getRscongty();

	public void setRscongty(ResultSet rscongty);
	public String getCongty() ;
	public void setCongty(String congty);
	public String getNguoitao();

	public void setNguoitao(String nguoitao);
	public ResultSet getRsTien();

	public void setRsTien(ResultSet rsTien) ;
	public void laytien(String sql);
	
	//NEU DANG NHAP LA TDV, THI CHI LAY NPP VA TDV NAY
	public String getNpp_duocchon_id();
	public void setNpp_duocchon_id(String npp_duocchon_id);
	public String getTdv_dangnhap_id();
	public void setTdv_dangnhap_id(String tdv_dangnhap_id);
	
	public String getHientimkiem();
	public void setHientiemkiem(String hientimkiem);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
	public String ChangeDonHang(String[] iddonhangNEW, String[] loaidonhangNEW, String[] ngaydonhangNEW, String[] giodonhangNEW, String[] ngaydonhangOLD, String[] giodonhangOLD);
	
	
}
