package geso.traphaco.erp.beans.phieuphache;

import java.sql.ResultSet;

public interface IErpPhieuPhaCheList {
	public void init();
	
	public void createRS();
	
	public void delete(String id);
	
	public void DBClose();

	public String getUserId();
	public void setUserId(String userId);

	public String getCongtyId();
	public void setCongtyId(String congtyId);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getMsg();
	public void setMsg(String msg);

	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);

	public String getSanpham();
	public void setSanpham(String sanpham);

	public String getNguoiphache();
	public void setNguoiphache(String nguoiphache);

	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet sanphamRs);

	public ResultSet getPhieuphacheRs();
	public void setPhieuphacheRs(ResultSet phieuphacheRs);
	
	public void chot(String id);
	
	public void bochot(String id);
}
