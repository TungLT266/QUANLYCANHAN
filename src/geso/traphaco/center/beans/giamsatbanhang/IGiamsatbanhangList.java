package geso.traphaco.center.beans.giamsatbanhang;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IGiamsatbanhangList extends Serializable, IPhan_Trang
{	
	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public String getUserId();
	public void setUserId(String userId);
	public String getTen();
	public void setTen(String ten);
	public String getSodienthoai();
	public void setSodienthoai(String sodienthoai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<IGiamsatbanhang> getGsbhList();
	public void setGsbhList(List<IGiamsatbanhang> gsbhlist);
	
	public ResultSet getKenhbanhang();
	public void setKenhbanhang(ResultSet kenhbanhang);
	public String getKbhId();
	public void setKbhId(String kbhId);
	
	public void init(String search);
	public void setMsg(String Msg);
	public String getMsg();
	void initSplitting(); 
	public void DBClose();
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getMaFAST();
	public void setMaFAST(String maFAST);
	
}
