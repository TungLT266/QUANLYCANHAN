package geso.traphaco.erp.beans.lapkehoach;

import java.sql.ResultSet;

public interface IErpLenhsanxuatdkList 
{
	public String getCtyId(); 
	public void setCtyId(String ctyId); 

	public String getUserId();
	public void setUserId(String userId);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getMsg();
	public void setMsg(String msg);
	
	
	public ResultSet getLenhsanxuatdkRs();
	public void setLenhsanxuatdkRs(ResultSet dnsxRs);
	
	public void init();
	
	public void delete(String id );
	
	public void DbClose();

	public String getNam(); 

	public void setNam(String nam); 
	
	public String getThang(); 

	public void setThang(String thang); 
	public String getDateTime();
	
	public ResultSet getNhamayRS();
	
	public void setNhamayRS(ResultSet nmRS);
	
	public String getNhamayId();
	
	public void setNhamayId(String nmId);
	
	public String getSpId();

	public void setSpId(String spId) ;
	
	public ResultSet getSanphamRS();
	
	public void setSanphamRS(ResultSet spRS);

	public String[] getSX_Tuan(String nam_thang, String spId);
	
	public String getLoaihh();
	public void setLoaihh(String loaihh) ;
	
}
