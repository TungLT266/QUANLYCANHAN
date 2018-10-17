package geso.traphaco.distributor.beans.dondathang;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpDuyetddhNppList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getKhTen();
	public void setKhTen(String khTen);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	
	public String getKvId();
	public void setKvId(String KvId);
	
	public ResultSet getKvRs();
	
	public ResultSet getHtbhRs();
	
	public String getHtbhId();
	public void setHtbhId(String HtbhId);
	
	public String getSpId();
	public void setSpId(String SpId);
	public ResultSet getSpRs();
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getSodh();
	public void setSodh(String sodh);
	
	public String getDoiTuong();
	public void setDoiTuong(String DoiTuong);
	
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
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String search);
	public void initLENHXUATHANG(String search);
	public void initTHONGKENHANHANG(String search);
	
	public void DBclose();
	
	public String getIsKm();
	public void setIsKm(String iskm);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public String getPhanloai();
	public void setPhanloai(String phanloai);
	
	public String getCapduyet();
	public void setCapduyet(String capduyet);
	public ResultSet getRsnvbanhang();

	public void setRsnvbanhang(ResultSet rsnvbanhang);
	public String getNvbanhang();

	public void setNvbanhang(String nvbanhang);
	public String getNgaygiao() ;

	public void setNgaygiao(String ngaygiao);
	public ResultSet getRskhoid() ;

	public void setRskhoid(ResultSet rskhoid);
	public String getKhohh();

	public void setKhohh(String khohh);
	
	public String getNguoigiao();
	public void setNguoigiao(String nguoigiao);

	public String getTensp();
	public void setTensp(String tensp);

	public String getSolo();
	public void setSolo(String solo);

	public String getChietkhau();
	public void setChietkhau(String chietkhau);

	public String getThuegtgt();
	public void setThuegtgt(String thuegtgt);
;
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public ResultSet getRstien();
	public void setRstien(ResultSet rstien) ;
	
	public void laytien(String sql);
	
	// 22/12/2015
	public String getNgayno() ;

	public void setNgayno(String ngayno);
	
	//NEU DANG NHAP LA TDV, THI CHI LAY NPP VA TDV NAY
	public String getNpp_duocchon_id();
	public void setNpp_duocchon_id(String npp_duocchon_id);
	public String getTdv_dangnhap_id();
	public void setTdv_dangnhap_id(String tdv_dangnhap_id);
	
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
}
