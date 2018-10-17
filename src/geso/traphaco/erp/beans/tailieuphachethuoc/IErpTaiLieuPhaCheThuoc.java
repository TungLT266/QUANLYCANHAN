package geso.traphaco.erp.beans.tailieuphachethuoc;

import geso.traphaco.erp.beans.tailieuphachethuoc.imp.ErpTaiLieuPhaCheThuoc_ThongTin;

import java.sql.ResultSet;
import java.util.List;

public interface IErpTaiLieuPhaCheThuoc {
	public void init();
	public void createRs();
	public boolean create();
	public boolean update();
	public void DBClose();
	public String getUserId();
	public void setUserId(String userId);
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getId();
	public void setId(String id);
	public String getMa();
	public void setMa(String ma);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	public String getNoidung();
	public void setNoidung(String noidung);
	public String getThuocthu();
	public void setThuocthu(String thuocthu);
	public String getCongthuc();
	public void setCongthuc(String congthuc);
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet sanphamRs);
	public List<ErpTaiLieuPhaCheThuoc_ThongTin> getThongtinList();
	public void setThongtinList(List<ErpTaiLieuPhaCheThuoc_ThongTin> thongtinList);
}
