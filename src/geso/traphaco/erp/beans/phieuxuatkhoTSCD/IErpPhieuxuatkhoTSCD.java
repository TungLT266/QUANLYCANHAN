package geso.traphaco.erp.beans.phieuxuatkhoTSCD;

import java.sql.ResultSet;
import java.util.List;

public interface IErpPhieuxuatkhoTSCD 
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getNgayxuatkho();
	public void setNgayxuatkho(String ngayxuatkho);
	
	public String getNgaychotNV();
	public void setNgaychotNV(String ngaychotNV);
	
	public String getLydoxuat();
	public void setLydoxuat(String lydoxuat);
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getKhId();
	public void setKhId(String khid);
	public String getKhachhangTen();
	public void setKhachhangTen(String khTen);

	public String getDondathangId();
	public void setDondathangId(String ddhid);
	public ResultSet getDdhList();
	public void setDdhList(ResultSet ddhList);
	
	public String getNccId();
	public void setNccId(String nccid);
	public String getNccTen();
	public void setNccTen(String nccTen);
	
	public String getTrahangNccId();
	public void setTrahangNccId(String trahangid);
	public ResultSet getTrahangList();
	public void setTrahangList(ResultSet trahangList);
	
	public String getKhoId();
	public void setKhoId(String khoId);
	public ResultSet getKhoList();
	public void setKhoList(ResultSet kholist);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getNdxId();
	public void setNdxId(String ndxId);
	public ResultSet getNdxList();
	public void setNdxList(ResultSet ndxList);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
	public void createRs();
	public void changeDdh();
	public String chotXuatKho(String userId);
	public void init();
	public void initPdf();
	public void initLayHang();
	
	public boolean createPxk();
	public boolean updatePxk();
	
	//pdf
	public String getNguoinhanhang();
	public void setNguoinhanhang(String nguoinhanhang);
	public String getDiachi();
	public void setDiachi(String diachi);
	public String getXuattaikho();
	public void setXuattaikho(String xuattaikho);
	
	public boolean isQuanlylo();
	public void setQuanlylo(boolean quanlylo);
	public boolean isQuanlybean();
	public void setQuanlybean(boolean quanlybean);

	public String getSoChungTu();
	public void setSoChungTu(String sochungtu);
	
	public String getDungsai();
	public void setDungsai(String dungsai);
	
	public void DbClose();
}
