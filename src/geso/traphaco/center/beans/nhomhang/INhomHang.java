package geso.traphaco.center.beans.nhomhang;

import java.sql.ResultSet;

public interface INhomHang
{
	public String getId();
	public void setId(String id);

	public String getMa();
	public void setMa(String ma);

	public String getTen();
	public void setTen(String ten);

	public String getMsg();
	public void setMsg(String msg);

	public String getUserId();
	public void setUserId(String userId);
	
	public String getNhanHangId();
	public void setNhanHangId(String nhanhangId);
	
	public ResultSet getNhanhangRs();
	public void setNhanhangRs(ResultSet nhanhangRs);

	public String getNganhHangId();
	public void setNganhHangId(String nganhangId);
	
	public void setNganhHangRs(ResultSet nganhhangRs);
	public ResultSet getNganhHangRs();
	
	public String getDvdlId();
	public void setDvdlId(String dvdlId);
		
	public boolean save();
	
	public boolean edit();

	public void closeDB();

	public void init();

	public void createRs();

	public String getSpId();
	public void setSpId(String spId);

	public ResultSet getRsnhomhang();

	public void setRsnhomhang(ResultSet rsnhomhang) ;
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	
}
