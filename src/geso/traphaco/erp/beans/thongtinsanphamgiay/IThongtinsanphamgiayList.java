package geso.traphaco.erp.beans.thongtinsanphamgiay;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IThongtinsanphamgiayList extends IPhan_Trang, Serializable 
{
public void init();
	
	public String getCtyId();
	public void setCtyId(String ctyId);
	
	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getMasp();
	
	public void setMasp(String masp);
	
	public String getTennoibo();
	
	public void setTennoibo(String tennoibo);
	
	public String getTensp();
	
	public void setTensp(String tensp);
	
	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);

	public String getNhId();
	
	public void setNhId(String nhId);

	public String getClId();
	
	public void setClId(String clId);
			
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);

	public String getQuery();
	
	public void setQuery(String query);

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getNh();
	
	public void setNh(ResultSet nh);

	public ResultSet getCl();
	
	public void setCl(ResultSet cl);

	public String getLspId();
	public void setLspId(String lspId);
	public ResultSet getLspRs();
	public void setLspRs(ResultSet lspRs);
	
	public void CreateRS();
	
	public ResultSet getThongtinsanphamList();
	
	public void setThongtinsanphamList(ResultSet splist);
	
	public void setMsg(String Msg);
	
	public String getMsg();
	
	public void DBClose();
	public void NewDbUtil();
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public int getLastPage();
	
	//
	
	public String getCtyTen();
	
	public void setCtyTen(String ctyTen);
	
	public String getloaisanphamid();
	
	public void setloaisanphamid(String loaisanphamid);
	
	public ResultSet getLoaisanpham();
	
	public void setLoaisanpham(ResultSet rsLoaisanpham);
	
	public void setMAsp(String MAsp);
	public String getMAsp();
	
	public String getMaketoan();
	public void setMaketoan(String maketoan);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public void setMachitietsp(String machitietsp);
	public String getMachitietsp();
}