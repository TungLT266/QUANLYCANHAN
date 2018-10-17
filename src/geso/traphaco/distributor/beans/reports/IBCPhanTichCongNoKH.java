package geso.traphaco.distributor.beans.reports;

import java.sql.ResultSet;

import com.aspose.cells.Color;
import com.aspose.cells.Workbook;

public interface IBCPhanTichCongNoKH {

	public void setUserId(String userId);
	public String getUserId();	
	public String getUserName();
	
	public void setTuNgay(String tuNgay);
	public String getTuNgay();
	
	public void setDenNgay(String denNgay);
	public String getDenNgay();
	
	public String getNPPID();	

	public ResultSet getKHList();	
	public void setKHList(ResultSet KHlist);
	
	public String getKHId();
	public void setKHId(String khid);
	
	public void createStaticHeader(Workbook workbook);
	public void init();
	public void init1();
	public void tieuDe(Workbook workbook,int rowIndex);
	public void createStaticData(Workbook workbook);
	public void getCellStyle(Workbook workbook, String cellName, Color color, Boolean bold, int size);
	public void createBorderSetting(Workbook workbook,String fileName);
	public String getDateTime();
	public void dbClose();
	public void setUserName(String userName);
	
}
