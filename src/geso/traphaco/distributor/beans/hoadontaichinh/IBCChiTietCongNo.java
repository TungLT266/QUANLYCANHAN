package geso.traphaco.distributor.beans.hoadontaichinh;

import java.io.Serializable;
import java.sql.ResultSet;

import com.aspose.cells.Color;
import com.aspose.cells.Workbook;


public interface IBCChiTietCongNo extends Serializable
{
	public void setUserId(String userId);
	public String getUserId();	
	public String getUserName();
	
	public void setTuNgay(String tuNgay);
	public String getTuNgay();
	
	public void setDenNgay(String denNgay);
	public String getDenNgay();
	
	public ResultSet getKHList();	
	public void setKHList(ResultSet KHlist);
	
	public String getKHId();
	public void setKHId(String khid);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public void setvungId(String vungId);
	public String getvungId();

	public void setvung(ResultSet vung);
	public ResultSet getvung();
	
	public void setkhuvucId(String khuvucId);
	public String getkhuvucId();

	public void setkhuvuc(ResultSet khuvuc);
	public ResultSet getkhuvuc();
	
	public ResultSet getTtRs();
	public void setTtRs(ResultSet ttRs);
	
	public String getTtId();
	public void setTtId(String ttId);
	
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
	
	public void settype(String type);
	public String gettype();
	
	public String getBCTheoKH();
	
	public String getDuNoDauKyKH();	
	
	public String getDuNoDauKyDoiTac();	
	
	public String getBCTheoDoiTac();
	
	public ResultSet getDoiTacRs();
	public void setDoiTacRs(ResultSet DoiTacRs);
	
	public String getDoiTacId();
	public void setDoiTacId(String doitacId);
	
	
}
