package geso.traphaco.erp.beans.dieuchuyentien;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpDieuchuyentienList   extends Serializable, IPhan_Trang
{
	public String getCongtyId();

	public void setCongtyId(String congtyId);

	public String getUserId(); 

	public void setUserId(String userId); 
	
	public String getSochungtu();
	
	public void setSochungtu(String sochungtu);
	
	public String getNgaychungtu();
	
	public void setNgaychungtu(String ngaychungtu);

	public String getTtId();
	
	public void setTtId(String ttId);

	public String getNhchuyenId();
	
	public void setNhchuyenId(String nhchuyenId);

	public String getNhnhanId();
	
	public void setNhnhanId(String nhnhanId);

	public String getSotien();
	
	public void setSotien(String sotien);

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getMsg();

	public void setMsg(String msg);

	public ResultSet getTienteRs();

	public void setTienteRs(ResultSet ttRs);

	public ResultSet getNHChuyenRs();

	public void setNHChuyenRs(ResultSet nhchuyenRs);

	public ResultSet getNHNhanRs();

	public void setNHNhanRs(ResultSet nhnhanRs);
	
	public ResultSet getDieutienRs();

	public void setDieutienRs(ResultSet dieutien);

	public void init(String str);
	
	public void createRs();
	
	public void DBclose(); 
	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
}
