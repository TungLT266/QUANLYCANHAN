package geso.traphaco.erp.beans.duyetbom;

import java.sql.ResultSet;

public interface IErpDuyetbomTP
{
	public String getMsg();
	public void setMsg(String msg);

	public String getCtyId();
	public void setCtyId(String ctyId) ;

	public String getBomId() ;
	public void setBomId(String bomId);

	public String getUserId();
	public void setUserId(String userId) ;

	public String getMasp() ;
	public void setMasp(String masp);

	public String getDiengiai() ;
	public void setDiengiai(String diengiai);

	public String getTensanpham();
	public void setTensanpham(String tensanpham);
	
	public String getTenBom();
	public void setTenBom(String tenBom) ;
	
	public void Close();
	
	public void init();
	public String duyet();
	public ResultSet getRsBom() ;
	public void setRsBom(ResultSet rsBom);
}
