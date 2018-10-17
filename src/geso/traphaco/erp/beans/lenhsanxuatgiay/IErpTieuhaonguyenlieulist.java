package geso.traphaco.erp.beans.lenhsanxuatgiay;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpTieuhaonguyenlieulist extends Serializable, IPhan_Trang{
	
	
	public void setSochungtu(String sochungtu) ;
	public String getSochungtu() ;
	public String getUserId();
	public void setUserId(String userId);
	
	public String getLsxId();
	public void setLsxId(String LsxId);

	public String getCongDoanId();
	public void SetCongDoanId(String CongdoanId);
	
	
	public String getCtyId();
	public void SetCtyId(String ctyid);
	

	public String getSanPhamMa();
	public void SetSanPhamMa(String SanPhamMa);
	
	public String getSanPhamTen();
	public void SetSanPhamTen(String SanPhamTen);
	
	public String getNgayBanDau();
	public void SetNgayBanDau(String NgayBanDau);
	
	public String getNgayKetThuc();
	public void SetNgayKetThuc(String NgayKetThuc);
	
	public String getNhamay();
	public void SetNhamay(String Nhamay);
	
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void Init(String sql);
	public ResultSet GetRsListTieuHao();
	
	public void DBclose();
	public String DeleteTieuHao();
	
	public void setIdTieuHao(String idtieuhao);
	public String getIdTieuhao();
	
	public String getXuongId();
	public void setXuongId(String xuongId);
	public ResultSet getXuongRs();
	public void setXuongRs(ResultSet xuongRs);
	public String getNppId() ;

	public void setNppId(String nppId);
	public String Chottieuhao(String chungtuid);
	
	
}
