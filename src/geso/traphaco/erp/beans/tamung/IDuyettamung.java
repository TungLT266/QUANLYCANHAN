package geso.traphaco.erp.beans.tamung;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IDuyettamung {
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	
	public void setId(String Id);

	public String getCtyId();
	
	public void setCtyId(String ctyId);
	
	public String getDvthId();
	
	public void setDvthId(String dvthId);
	
	public String getLspId();
	
	public void setLspId(String lspId);
	
	public String getNgaytao();
	
	public void setNgaytao(String ngaytao);
	
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
	
	public boolean Duyettamung(String Id, String capduyet);
	
	public boolean BoDuyetmuahang(String Id);
	
	public boolean Xoamuahang(String Id);
	
	public boolean Suamuahang(String Id);

	public void DBclose();
	
	public String getMaDNTU();
	public void setMaDNTU(String maDNTU);
	
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
	
	public boolean boChot(String id);
	public boolean boChot(String id,String action);
	
	public ResultSet getNguoitaoRs();
	public void setNguoitaoRs(ResultSet nguoitaoRs);
	public void setNguoitaoIds(String nspIds);
	public String getNguoitaoIds();
	String getTuNgay();
	void setTuNgay(String tuNgay);
	String getDenNgay();
	void setDenNgay(String denNgay);
	String getTrangThai();
	void setTrangThai(String trangThai);

}