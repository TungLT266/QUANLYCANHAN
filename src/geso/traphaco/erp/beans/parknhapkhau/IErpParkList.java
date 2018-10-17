package geso.traphaco.erp.beans.parknhapkhau;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;
public interface IErpParkList extends Serializable, IPhan_Trang 
{
	public String getUserId();
	public void setUserId(String userId);	
	
	public String getId();
	public void setId(String id);
	
	public int getDuyet();
	public void setDuyet(int duyet);
	
	public String getNgayghinhan();
	public void setNgayghinhan(String ngayghinhan);
	
	public void setNcc(String ncc);	
	public String getNcc();
	
	public void setLoaihang(String loaihang);	
	public String  getLoaihang();
	
	public String getNccId();
	public void setNccId(String nccId);
	
	public ResultSet getLoaihangList();
	public String getMsg();
	public void setMsg(String msg);
	public void setLoaihangList(ResultSet loaihanglist);
	public List<IErpHoadon> getHdList();
	public void setHdList(List<IErpHoadon> hdList);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public void createRs();
	public ResultSet getParkList();
	
	public void setNhacungcapList(ResultSet nhacungcapList);
	public ResultSet getNhacungcapList();
	
	public void setParkList(ResultSet parklist);
	public void init(String query);
	public void close();
	
	public String getNGUOITAO();
	public void setNGUOITAO(String nGUOITAO);

	public String getSOHOADON();
	public void setSOHOADON(String sOHOADON);
	
	public String getSonhanhang();
	public void setSonhanhang(String sonhanhang);
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
	public String Unchot(String parkId, String userId);

	public String checkSoHoaDon(String parkId);
	public String getDonMuaHang();
	public void setDonMuaHang(String donMuaHang);
}
