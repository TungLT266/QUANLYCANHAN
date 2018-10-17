package geso.traphaco.distributor.beans.reports;

import geso.traphaco.center.util.Erp_Item;

import java.sql.ResultSet;
import java.util.List;

import com.aspose.cells.Color;
import com.aspose.cells.Workbook;

public interface IBCChiTietCongNoHD {
	public void setUserId(String userId);
	public String getUserId();
	public String getUserName();
	public void setUserName(String userName);
	public void setTuNgay(String tuNgay);
	public String getTuNgay();
	public void setDenNgay(String denNgay);
	public String getDenNgay();
	public String getNPPID();
	public String getNgayKS();
	public ResultSet getRS();
	
	public void setnppId(String nppId);
	public String getnppId();

	public void setMsg(String msg);
	public String getMsg();

	
	public void setNvbhIds(String nvbhIds);
	public String getNvbhIds();
	public ResultSet getNvbhRs();
	public ResultSet setNvbhRs(ResultSet nvbhRs);
	
	public void setNvgnIds(String nvgnIds);
	public String getNvgnIds();
	public ResultSet getNvgnRs();
	public ResultSet setNvgnRs(ResultSet nvgnRs);
	
	public void setKhIds(String khIds);
	public String getKhIds();
	public ResultSet getKhRs();
	public ResultSet setKhRs(ResultSet khRs);
	
	public void setDtIds(String dtIds);
	public String getDtIds();
	public ResultSet getDtRs();
	public ResultSet setDtRs(ResultSet dtRs);
	
	public void createStaticHeader(Workbook workbook, IBCChiTietCongNoHD obj);
	public void init();
	public void tieuDe(Workbook workbook,int rowIndex);
	public void createStaticData(Workbook workbook);
	public void getCellStyle(Workbook workbook, String cellName, Color color, Boolean bold, int size);
	public void createBorderSetting(Workbook workbook,String fileName);
	public String getDateTime();
	public void dbClose();
	public void createRs();
	public void initExcel();
	public ResultSet getRsErpCongty();
	
	public String getCtyId();
	public void setCtyId(String ctyId);
	

	public String getDvkdId();


	public void setDvkdId(String dvkdId) ;


	public ResultSet getDvkdRs() ;


	public void setDvkdRs(ResultSet dvkdRs) ;
	
	
	public String getkBh();


	public void setkBh(String kBh);


	public String getnKh();


	public void setnKh(String nKh);
	
	
	public ResultSet getKbhRs();


	public void setKbhRs(ResultSet kbhRs);


	public ResultSet getNkhRs();


	public void setNkhRs(ResultSet nkhRs);
	
	public String getLoaiDoiTuong() ;
	public void setLoaiDoiTuong(String loaiDoiTuong);
	public String getDoiTuong() ;

	public void setDoiTuong(String doiTuong) ;
	public List<Erp_Item> getLoaiDoiTuongList() ;

	public void setLoaiDoiTuongList(List<Erp_Item> loaiDoiTuongList) ;
	public List<Erp_Item> getDoiTuongList() ;
	public void setDoiTuongList(List<Erp_Item> doiTuongList) ;
}
