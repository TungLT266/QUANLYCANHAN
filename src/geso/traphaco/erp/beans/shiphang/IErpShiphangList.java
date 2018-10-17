package geso.traphaco.erp.beans.shiphang;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpShiphangList extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getDvthId();
	public void setDvthId(String dvthid);
	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);

	public String getNCC();
	public void setNCC(String ncc);
	public String getSoPO();
	public void setSoPO(String soPO);
	
	public String getSoShiphang();
	public void setSoShiphang(String soShiphang);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void setmsg(String msg);
	public String getmsg();
	
	public ResultSet getNhList();
	public void setNhList(ResultSet nhlist);
	
	public void init(String search);
	public void DBclose();
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	public String getNppId();
	
	// Loại PO
	public void setLoaidh(String loaidh);
	public String getLoaidh();

	public void setSoItems(int soItems);
	public int getSoItems();

}
