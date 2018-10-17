package geso.traphaco.erp.beans.erp_yeucausx;

import geso.traphaco.center.util.IPhan_Trang;
import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface IYeuCauSXList extends IPhan_Trang,Serializable{
	
	public String getCtyId();
	
	public void setCtyId(String ctyId);

	public String getKhoId();
	
	public void setKhoId(String Id);
	
	public String getNam();
	
	public void setNam(String nam);
	
	public String getThang();
	
	public void setThang(String thang);
	
	public String getView();
	
	public void setView(String view);

	public String getClId();
	
	public void setClId(String clId);
	
	public int getSohang();
	
	public void setSohang(int sohang);

	public int getSocot();
	
	public void setSocot(int socot);
	
	public String getTheothang();
	
	public void setTheothang(String theothang);	

	public ResultSet getChungloaiRS();
	
	public void setChungloaiRS(ResultSet clRS);
	
	public void createRs();

	public ResultSet getData();
	
	public void setData(ResultSet data);

	public String getDateTime();
	
	public String getLoai();
	
	public void setLoai(String loai);
	
	public void DBClose();
}
