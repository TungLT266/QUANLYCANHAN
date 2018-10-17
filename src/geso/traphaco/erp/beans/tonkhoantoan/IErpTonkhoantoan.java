package geso.traphaco.erp.beans.tonkhoantoan;

import java.sql.ResultSet;
import java.util.List;

public interface IErpTonkhoantoan
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getClId();
	public void setClId(String clId);
	public ResultSet getClRs();
	public void setClRs(ResultSet clRs);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean save();
	
	public void init();
	
	public void createRs();
	
	public void setTkatRs_1(ResultSet tkanRs_1);

	public ResultSet getTkatRs_1();

	public void setTkatRs_2(ResultSet tkanRs_2);
	
	public ResultSet getTkatRs_2();
	
	public void setTkatRs_3(ResultSet tkanRs_3);
	
	public ResultSet getTkatRs_3();
	
	public void setTkatRs_4(ResultSet tkanRs_4);
	
	public ResultSet getTkatRs_4();

	public String[] getSp_1();
	public void setSp_1(String[] sp_1);
	public String[] getSp_2();
	public void setSp_2(String[] sp_2);
	public String[] getSp_3();
	public void setSp_3(String[] sp_3);
	public String[] getSp_4();
	public void setSp_4(String[] sp_4);

	public String[] getTkat_1();
	
	public void setTkat_1(String[] tkat_1); 
	
	public String[] getTkat_2();
	
	public void setTkat_2(String[] tkat_2); 
	
	public String[] getTkat_3();
	
	public void setTkat_3(String[] tkat_3); 
	
	public String[] getTkat_4(); 
	
	public void setTkat_4(String[] tkat_4); 

	public void DbClose();
	
}
