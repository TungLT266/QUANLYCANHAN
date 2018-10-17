package geso.traphaco.erp.beans.lapkehoach;

import java.sql.ResultSet;

public interface IErpLenhmuahangdkList 
{
	public String getCtyId(); 
	public void setCtyId(String ctyId); 
	
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	
	public ResultSet getLenhmuahangdkRs();
	public void setLenhmuahangdkRs(ResultSet khlRs);
	
	public void init();
	
	public void delete(String id );
	
	public void DbClose();
	
	public String getNam(); 

	public void setNam(String nam); 
	
	public String getThang(); 

	public void setThang(String thang); 
	
	public String getLoai(); 

	public void setLoai(String loai); 

	public String getDateTime();	
	
	
	public String getSpId();

	public void setSpId(String spId) ;
	
	public ResultSet getSanphamRS();
	public ResultSet getNhamayRS();
	
	public String getNhamayId();
	
	public void setNhamayId(String nmId);
	
	public void setSanphamRS(ResultSet spRS);
	public ResultSet getNLThaythe(String spId);
	public ResultSet getMa_ket(String spId, String ngay);
}
