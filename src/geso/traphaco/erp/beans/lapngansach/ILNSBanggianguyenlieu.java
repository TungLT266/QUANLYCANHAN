package geso.traphaco.erp.beans.lapngansach;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface ILNSBanggianguyenlieu extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getUserTen(); 
	public void setUserTen(String userTen);	
	public String getCtyId();
	
	public void setCtyId(String ctyId);
	
	public String getCtyTen();
	
	public void setCtyTen(String ctyTen);
	
	public String getId();
	public void setId(String id);	
	public String getTen();
	public void setTen(String ten);
	public void setTrangthai(String trangthai);	
	public String getTrangthai();
	
	public String getNgaytao();	
	public void setNgaytao(String ngaytao);
	public String getNgaysua();	
	public void setNgaysua(String ngaysua);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNguoisua();	
	public void setNguoisua(String nguoisua);
	public String getMessage();
	public void setMessage(String msg);
	
	public String[] getSpIds(); 
	public void setSpIds(String[] spIds); 
		
	public String[] getGiamua();
	public void setGiamua(String[] giamua);
	
	public boolean CreateBgnguyenlieu(HttpServletRequest request); 
	public boolean UpdateBgnguyenlieu(HttpServletRequest request);
	public void init();
	public void closeDB();
	public String[][] getData();
	public void setData(String[][] data);
	
	public int getCount();
	public String getDateTime() ;
	public String getNam();
	public void setNam(String nam);
}
