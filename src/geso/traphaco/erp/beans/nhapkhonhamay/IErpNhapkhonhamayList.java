package geso.traphaco.erp.beans.nhapkhonhamay;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpNhapkhonhamayList extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String id);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public String getNgaynhan();
	public void setNgaynhan(String ngaynhan);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getDvthId();
	public void setDvthId(String dvthid);
	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);

	public String getSoPO();
	public void setSoPO(String soPO);
	
	public ResultSet getNguoitaoRs();
	public void setNguoitaoRs(ResultSet nguoitaoRs);
	public void setNguoitaoIds(String nspIds);
	public String getNguoitaoIds();
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void setmsg(String msg);
	public String getmsg();
	
	public ResultSet getNhList();
	public void setNhList(ResultSet nhlist);
	
	public void init(String search);
	public void DBclose();

	public String getNppId();

	public void setSoItems(int soItems);
	public int getSoItems();
}
