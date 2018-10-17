package geso.traphaco.erp.beans.thutien;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpThutienList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getCtyId();
	public void setCtyId(String ctyId); 

	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getsochungtu();
	public void setsochungtu(String sochungtu);
	
	public String getsobangke();
	public void setsobangke(String sobangke);
	
	public String getsohoadon();
	public void setsohoadon(String sohoadon);
	
	public String getNccId();
	public void setNccId(String nccid);
	public ResultSet getNccList();
	public void setNccList(ResultSet ncclist);

	public String getHtttId();
	public void setHtttId(String htttid);
	public ResultSet getHtttList();
	public void setHtttList(ResultSet htttlist);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void setmsg(String msg);
	public String getmsg();
	
	public ResultSet getTThoadonList();
	public void setTThoadonList(ResultSet tthdlist);
	
	public String getNguoisuaId();
	public void setNguoisuaId(String nguoisuaId);
	public ResultSet getNguoisuaRs();
	public void setNguoisuaRs(ResultSet nguoisuaRs);
	
	public void init(String search);
	public void DBclose();
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
	public String getKhId();
	public void setKhId(String khid);
	public ResultSet getKhList();
	public void setKhList(ResultSet khrs);
	
	public String getNvId();
	public void setNvId(String nvid);
	public ResultSet getNvList();
	public void setNvList(ResultSet nvrs);
	
	public String getSotien();
	public void setSotien(String sotien);
	
	public String getKbhId();
	public void setKbhId(String kbhid);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhrs);
	
	public String getNhomkhId();
	public void setNhomkhId(String nhomkhid);
	public ResultSet getNhomkhRs();
	public void setNhomkhRs(ResultSet Nhomkhrs);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
	// KHO HÀNG HÓA
	public String getKhohangId();
	public void setKhohangId(String khohangid);
	public ResultSet getKhohangRs();
	public void setKhohangRs(ResultSet khohangrs);
	
	// NHÂN VIÊN GIAO NHẬN
	public String getNvgnId();
	public void setNvgnId(String Nvgnid);
	public ResultSet getNvgnRs();
	public void setNvgnRs(ResultSet nvgnrs);
	
	// GHI CHÚ
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getCtyTen();
	
	public String getDiachi();

	public String getMasothue();
	
	public String getMaChungTu();

	public void setMaChungTu(String maChungTu);
	

	public String getTusotien() ;

	public void setTusotien(String tusotien) ;

	public String getDensotien();

	public void setDensotien(String densotien);
		
	public ResultSet getChinhanhRs() ;
	
	public String getChinhanh(String npp_fk);

	public void setChinhanhRs(ResultSet chinhanhRs);
	

	public ResultSet getNvtuRs();

	public void setNvtuRs(ResultSet nvtuRs);
	

	public ResultSet getDoituongRs( String madoituong, String doituongdinhkhoan) ;

	public void setDoituongRs(ResultSet doituongRs);
}