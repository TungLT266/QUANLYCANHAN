package geso.traphaco.erp.beans.nhanhangtrongnuoc;

import geso.traphaco.erp.beans.nhanhangtrongnuoc.ISanpham;
import java.sql.ResultSet;
import java.util.List;

public interface IErpNhanhang 
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getNgaynhanhang();
	public void setNgaynhanhang(String ngaynhanhang);
	public String getNgaychot();
	public void setNgaychot(String ngaychot);
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public String getDvthId();
	public void setDvthId(String dvthid);
	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);

	public String getDonmuahangId();
	public void setDonmuahangId(String dmhid);
	public ResultSet getDmhList();
	public void setDmhList(ResultSet dmhlist);
	
	public String getMahangmuaId();
	public void setMahangmuaId(String mhmId);
	public ResultSet getMahangmuaList();
	public void setMahangmuaList(ResultSet mhmlist);
	
	public String getNdnId();
	public void setNdnId(String mhmId);
	public ResultSet getNdnList();
	public void setNdnList(ResultSet ndnlist);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTongSoluongPO();
	public void setTongSoluongPO(String tongslgPO);
	public String getTongSoluongDN();
	public void setTongSoluongDN(String tongslgDN);
	
	public int getNgayhethan();
	public void setNgayhethan(int ngayhethan);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaihanghoa();
	public void setLoaihanghoa(String loaihh);
	
	public void createRs();
	public void init();
	public void initPdf();
	
	public boolean createNhanHang();
	public boolean updateNhanHang();
	public void updateDonmuahang(String poId);
	
	
	
	
	public void close();
}
