package geso.traphaco.erp.beans.thanhtoanhoadon;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IDuyetthanhtoanhoadon extends Serializable, IPhan_Trang {
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCtyId();
	
	public void setCtyId(String ctyId);
	
	public String getNgayghinhan();
	
	public void setNgayghinhan(String ngayghinhan);
	
	public String getNvId();
	
	public void setNvId(String nvId);
	
	public ResultSet getNvRs();
	
	public void setNvRs(ResultSet nvRs);
	
	public ResultSet getPoList();
	
	public void setPoList(ResultSet polist);	
	
	public void setRequest(HttpServletRequest request);
	
/*	public String getDaduyet(String mhId);*/
	
	public String getMsg();
	
	public void setMsg(String msg);
	
	public void init();
	
	public boolean Duyetmuahang(String Id, String loaiId);

	public void DBclose();
	
	public String getMaTTHD();
	public void setMaTTHD(String maTTHD);
	
	public String getNccId();
	public void setNccId(String nccId);
	
	public ResultSet getNccList();
	public void setNccList(ResultSet nccList);
	
	public String getKhId();
	
	public void setKhId(String khId);
	
	public ResultSet getKhRs();
	
	public void setKhRs(ResultSet khRs);
	
	public String getLoaiCT();
	
	public void setLoaiCT(String loaiCT);
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
}
