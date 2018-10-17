package geso.traphaco.erp.beans.nhapkhoNK;

import geso.traphaco.erp.beans.shiphang.ISanpham;

import java.sql.ResultSet;
import java.util.List;

public interface IErpNhapkhoNK 
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);
	public String getNgaychot();
	public void setNgaychot(String ngaychot);
	
	public String getDvthId();
	public void setDvthId(String dvthid);
	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);

	public String getDonmuahangId();
	public void setDonmuahangId(String dmhid);
	public ResultSet getDmhList();
	public void setDmhList(ResultSet dmhlist);
	
	public void setNccId(String ndnId);
	public String getNccId();
	public ResultSet getNccList();
	public void setNccList(ResultSet nccList);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTongSoluongPO();
	public void setTongSoluongPO(String tongslgPO);
	public String getTongSoluongDN();
	public void setTongSoluongDN(String tongslgDN);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void createRs();
	public void init();
	public void initPdf(String spId);
	public boolean createNhapKho();
	public boolean updateNhapKho();

	
	public void close();
	public String  getTigia();
	public void setTiia(String tigia);

	
	public String getGhichu();
	public void setGhichu(String ghichu);
	public String ChotNhapKho(String id, String nccId, String userId);
	
	public String CreateNK(String shId);
}
