package geso.traphaco.erp.beans.congtytrahang;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpCongtytrahangList extends Serializable, IPhan_Trang 
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCtyId();

	public void setCtyId(String ctyId);
	
	public String getCty();
	
	public void setCty(String cty);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getDvthId();
	public void setDvthId(String dvthid);	
	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);

	public String getNCC();
	public void setNCC(String ncc);
	public String getTongtiensauVat();
	public void setTongtiensauVat(String ttsauvat);
	public void setmsg(String msg);
	public String getmsg();
	public ResultSet getDmhList();
	public void setDmhList(ResultSet dmhlist);
	
	public String getTask();
	public void setTask(String task);
	
	public String getSodonmuahang();
	public void setSodonmuahang(String sodonmuahang);
	
	public String getIsdontrahang();
	public void setIsdontrahang(String dontrahang);
	
	//Bao cao don mua hang
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	public void setNccIds(String nccIds);
	public String getNccIds();
	
	public ResultSet getNspRs();
	public void setNspRs(ResultSet nspRs);
	public void setNspIds(String nspIds);
	public String getNspIds();
	
	public ResultSet getNguoitaoRs();
	public void setNguoitaoRs(ResultSet nguoitaoRs);
	public void setNguoitaoIds(String nspIds);
	public String getNguoitaoIds();
	
	public ResultSet getLoaisanpham();
	public void setLoaisanpham(ResultSet loaisanpham);
	
	public String getLoaisanphamid();
	public void setLoaisanphamid(String loaisanpham);
	
	public String getMaCtSp();
	public void setMaCtSp(String mact);

	
	public String getLoaihanghoa();
	public void setLoaihanghoa(String loaihh);
	
	public void initBaoCao();
	
	public void init(String search);
	public void DBclose();
	
	public String getsothamchieu();
	public void setsothamchieu(String sothamchieu);
	
	public String getLoai();
	public void setLoai(String loai);
	
	public void setSoItems(int soItems);
	public int getSoItems();
	
	public String getLoaiDonMuaHang();
	public void setLoaiDonMuaHang(String loaiDonMuaHang);
	

}
