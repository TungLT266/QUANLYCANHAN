package geso.traphaco.erp.beans.phieuthanhtoan;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IDuyetdonmuahang 	extends Serializable, IPhan_Trang{
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCtyId();
	
	public void setCtyId(String ctyId);
	
	public String getDvthId();
	
	public void setDvthId(String dvthId);
	
	public String getLspId();
	
	public void setLspId(String lspId);
	
	public String getNgaymua();
	
	public void setNgaymua(String ngaymua);
	
	public String getDenNgay();
	
	public void setDenNgay(String denNgay);
	
	public String getTrangThai();
	
	public void setTrangThai(String trangThai);
	
	public ResultSet getLspList();
	
	public void setLspList(ResultSet lsp);

	public ResultSet getDvthList();
	
	public void setDvthList(ResultSet dvth);
	
	public ResultSet getPoList();
	
	public void setPoList(ResultSet polist);	
	
	public void setRequest(HttpServletRequest request);
	
	public String getDaduyet(String mhId);
	
	public String getMsg();
	
	public void setMsg(String msg);
	
	public void init();
	
	public boolean Duyetmuahang(String Id, String action);
	
	public boolean BoDuyetmuahang(String Id);
	
	public boolean Xoamuahang(String Id);
	
	public boolean Suamuahang(String Id);

	public void DBclose();
	
	public String getMaDMH();
	public void setMaDMH(String maDMH);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);

	public String getNccId();
	public void setNccId(String nccId);
	
	public ResultSet getNccList();
	public void setNccList(ResultSet nccList);
	
	public String getLydomoduyet();	
	
	public void setLydomoduyet(String lydomoduyet);
	
	public String getLydoxoa();	
	
	public void setLydoxoa(String lydoxoa);
	
	public String getLydosua();	
	
	public void setLydosua(String lydosua);
	
	public void setTongTien(String tongTien);
	public String getTongTien();
	
	public boolean boChot(String id,String action);
	
	public ResultSet getNguoitaoRs();
	public void setNguoitaoRs(ResultSet nguoitaoRs);
	public void setNguoitaoIds(String nspIds);
	public String getNguoitaoIds();
	public void NewDbUtil();
	
	public String getHtttid() ;

	public void setHtttid(String htttid) ;
	
	public ResultSet getHtttList() ;

	public void setHtttList(ResultSet htttList) ;
	
}