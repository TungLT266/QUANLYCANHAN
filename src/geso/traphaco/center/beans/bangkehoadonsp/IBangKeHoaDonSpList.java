package geso.traphaco.center.beans.bangkehoadonsp;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;


public interface IBangKeHoaDonSpList extends IPhan_Trang, Serializable
{
	public String getTuNgay();
	public void setTuNgay(String denngay);
	
	public String getDenNgay();
	public void setDenNgay(String denngay);
	
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getSpId();
	public void setSpId(String spId);
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getKhId();
	public void setKhId(String khId);
	
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	
	
	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	
	public ResultSet getHoadonRs();
	public void setHoadonRs(ResultSet hdRs);
	
	
	public String getView();
	public void setView(String view);
	
	public void closeDB();

	public void createRs();
	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);	

	public void init(String search);
	
	public String getQueryHd();
	public void setQueryHd(String query);
	
	public void setTotal_Query(String searchquery);
	public ResultSet getTotalRs();
	public void setTotalRs(ResultSet totalRs);
	
	public String getLoaiBaoCao();
	public void setLoaiBaoCao(String loaiBaoCao);
}
