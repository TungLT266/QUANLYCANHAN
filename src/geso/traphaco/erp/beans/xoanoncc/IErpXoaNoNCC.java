package geso.traphaco.erp.beans.xoanoncc;

import java.sql.ResultSet;
import java.util.List;

public interface IErpXoaNoNCC 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);
	public String getNgayghiso();
	public void setNgayghiso(String ngayghiso);
	
	public String getNppId();
	public void setNppId(String nccId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nccRs);
	
	public String getHtttId();
	public void setHtttId(String htttId);
	public ResultSet getHtttRs();
	public void setHtttRs(ResultSet htttRs);
	
	public String getNoidungId();
	public void setNoidungId(String ndId);
	public ResultSet getNoidungRs();
	public void setNoidungRs(ResultSet ndRs);
	
	public String getNganhangId();
	public void setNganhangId(String nganhangId);
	public ResultSet getNganhangRs();
	public void setNganhangRs(ResultSet nganhangRs);
	
	public String getChinhanhId();
	public void setChinhanhId(String cnId);
	public ResultSet getChinhanhRs();
	public void setChinhanhRs(ResultSet chinhanhRs);
	
	public String getSotaikhoan();
	public void setSotaikhoan(String sotk);
	
	public String getCttratruocId();
	public void setCttratruocId(String ctttId);
	public ResultSet getCttratruocRs();
	public void setCttratruocRs(ResultSet ctttRs);
	
	public String getNoidungtt();
	public void setNoidungtt(String ndtt);
	public String getSotientt();
	public void setSotientt(String sotientt);
	
	public String getHoadonIds();
	public void setHoadonIds(String hdIds);
	public List<IHoadon> getHoadonRs();
	public void setHoadonRs(List<IHoadon> hoadonRs);
	
	public String getCtttIds();
	public void setCtttIds(String ctttIds);
	public List<IHoadon> getCtttList();
	public void setCtttList(List<IHoadon> hoadonRs);
	
	public boolean createTTHD();
	public boolean updateTTHD();
	public boolean chotTTHD(String userId);
	
	public String getMsg();
	public void setMsg(String msg);
	

	public String getLoaixnId();
	public void setLoaixnId(String loaixnId);
	public String getDoiTuongTamUng();
	public void setDoiTuongTamUng(String DoiTuongTamUng);
	
	public String getNvId();
	public void setNvId(String nvId);
	public ResultSet getNvRs();
	public void setNvRs(ResultSet nvRs);
	
	public String getNccId();
	public void setNccId(String nccId);
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);

	public ResultSet getTienteRs();
	
	public void setTienteRs(ResultSet tienteRs);

	public String getTienteId();
	
	public void setTienteId(String ttId);
	
	public void init();
	public void initDisplay();
	public void createRs();
	public void DBclose();
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
}
