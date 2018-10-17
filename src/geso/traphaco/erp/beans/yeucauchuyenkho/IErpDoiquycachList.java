package geso.traphaco.erp.beans.yeucauchuyenkho;
 
import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpDoiquycachList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungayTao();
	public void setTungayTao(String tungay);
	public String getDenngayTao();
	public void setDenngayTao(String denngay);
	
	public String getSophieu();
	public void setSophieu(String sophieu);
	
	public String getSolenh();
	public void setSolenh(String solenh);
	
	public String getNdxuat();
	public void setNdxuat(String ndxuat);
	public String getLydo();
	public void setLydo(String lydo);
	
	public ResultSet getNdxRs();
	public void setNdxRs(ResultSet ndxRs);
	
	public String getkhonhanId();
	public void setkhonhanId(String khonhanid);
	public ResultSet getKhonhanRs();
	public void setKhonhanRs(ResultSet khonhanRs);
	
	public String getKhoChuyenId();
	public void setKhoChuyenId(String khochuyenid);
	public ResultSet getKhoChuyenRs();
	public void setKhoChuyenRS(ResultSet khochuyenrs);
	
	public ResultSet getNhanvienRs();
	public void setNhanvienRs(ResultSet nhanvienRs);
	public ResultSet getNhanvien2Rs();
	public void setNhanvien2Rs(ResultSet nhanvien2Rs);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public String getMasp();
	public void setMasp(String masp);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getsochungtu();
	public void setsochungtu(String sochungtu);
	
	public String getsohoadon();
	public void setsohoadon(String sohoadon);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getIsnhanHang();
	public void setIsnhanHang(String isnhanHang);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public ResultSet getLsxRs();
	public void setLsxRs(ResultSet lsxRs);
	
	public String getlsxId();
	public void setlsxId(String lsxid);
	
	public void init(String search);
	public void DBclose();
	
	public String getsochungtubnId();
	public void setsochungtubnId(String sochungtubnid);
	
	public String getSolo() ;

	public void setSolo(String solo) ;
}
