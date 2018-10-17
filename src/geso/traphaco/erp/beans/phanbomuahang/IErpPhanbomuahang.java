package geso.traphaco.erp.beans.phanbomuahang;

import geso.traphaco.erp.beans.timnhacc.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpPhanbomuahang extends Serializable{

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
	public ResultSet getCtRs();
	public void setCtRs(ResultSet ctRs);
	
	public String getDnmhId();
	public void setDnmhId(String dnmhId);
	
	public ResultSet getDnmhRs();
	public void setDnmhRs(ResultSet dnmhRs);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public void createRs();
	public void init();
	
	public boolean createTimNcc();
	public boolean updateTimNcc();
	
	public void close();
	
	public String getMessage();
	public void setMessage(String msg);
	
	public void setActive(String active);
	public String getActive();
	
	public void setVat(String vat);
	public String getVat();
	
	public void setDungsai(String dungsai);
	public String getDungsai();
	
	public void setNgaymua(String ngaymua);
	public String getNgaymua();
	
	public void setDvth(String dvth);
	public String getDvth();
	
	public void setNcc(String ncc);
	public String getNcc();
}
