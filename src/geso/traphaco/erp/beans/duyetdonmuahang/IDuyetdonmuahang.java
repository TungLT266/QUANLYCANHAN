package geso.traphaco.erp.beans.duyetdonmuahang;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IDuyetdonmuahang {
	
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
	
	public boolean Duyetmuahang(String Id);

	public void DBclose();
	
	public String getMaDMH();
	public void setMaDMH(String maDMH);
	
	public String getNccId();
	public void setNccId(String nccId);
	
	public ResultSet getNccList();
	public void setNccList(ResultSet nccList);
	
	public String getLoaidh();
	public void setLoaidh(String loaidh);
	
	public String[] getGhiChuArr();
	public void setGhiChuArr(String[] ghichuArr);
	
	public String[] getMhIdArr();
	public void setMhIdArr(String[] mhidArr);
	
	public String[] getSpIdArr();
	public void setSpIdArr(String[] spidArr);
	
	public String[] getSoluongduyetArr();
	public void setSoluongduyetArr(String[] soluongduyetArr);
	
	public String[] getDongiaArr();
	public void setDongiaArr(String[] dongiaArr);
	
	public boolean Capnhatmuahang(String id);
	
	
	public String[] getDonviArr() ;


	public void setDonviArr(String[] donviArr);


	public String[] getNgaynhanArr() ;


	public void setNgaynhanArr(String[] ngaynhanArr) ;
	
	public String[] getThuexuatArr() ;

	public void setThuexuatArr(String[] thuexuatArr) ;
}
