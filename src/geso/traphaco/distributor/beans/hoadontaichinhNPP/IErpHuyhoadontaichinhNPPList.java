package geso.traphaco.distributor.beans.hoadontaichinhNPP;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpHuyhoadontaichinhNPPList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);


	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getHuyhoadontaichinhRs();
	public void setHuyhoadontaichinhRs(ResultSet huyhoadontaichinhRs);

	public String getHuyhoadontaichinhId();
	public void setHuyhoadontaichinhId(String huyhoadontaichinhId);

	public void init(String query);
	public void DbClose();
	
	// ***************** TÌM KIẾM TRÊN GIAO DIỆN *******************//
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String gettungay();
	public void settungay(String tungay);
	
	public String getdenngay();
	public void setdenngay(String denngay);
	
	
	public String getsochungtu();
	public void setsochungtu(String sochungtu);
	
	public ResultSet getkhachhang();
	public void setkhachhang(ResultSet khachhang);
	
	public String getkhId();
	public void setkhId(String khId);

	public String getsohoadon();
	public void setsohoadon(String sohoadon);
	
	
	
	
	//*********** TÁC VU ********************//
	public boolean chot(String Id, String loaiHD);
	public void delete(String Id);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public boolean kiemtraphatsinh(String Id, String trangthai);
	
	public String getCtyId();
	public void setCtyId(String ctyId);
}
