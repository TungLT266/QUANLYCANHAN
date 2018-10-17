package geso.traphaco.erp.beans.phieuxuatkho;

import java.sql.ResultSet;
import java.util.List;

public interface IErpPhieuxuatkho 
{

	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public float getSoLuongXuat();
	public void setSoLuongXuat(float soluongxuat);
	
	public String getNgayxuatkho();
	public void setNgayxuatkho(String ngayxuatkho);
	
	public String getNgaychotNV();
	public void setNgaychotNV(String ngaychotNV);
	
	public String getLydoxuat();
	public void setLydoxuat(String lydoxuat);
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getNppId();
	public void setNppId(String nppid);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public ResultSet getNppList();
	public void setNppList(ResultSet nppList);

	public String getNppIds(); 
	public void setNppIds(String nppids);
	public ResultSet getNppList2(); 
	public void setNppList2(ResultSet nppList2);
	
 
	
	public void setInddhId(String inddhId);
	public String getInddhId();
	public void setInddhIds(String inddhIds) ;
	public String getInddhIds();
	
	public String getNccId();
	public void setNccId(String nccid);
	public ResultSet getNccList();
	public void setNccList(ResultSet nccList);
	
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
	public boolean chotXuatKho(String userId);
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
	public String getNPP(String soId);
	
	public void DBClose();
	
	public String getMaphieu();
	
	public ResultSet getHDTCList();
	public String getHDTCId();
	public void setHDTCId(String hdtcId);
	public void setNppIdKhoId(String hdId);
	public boolean isDataoPXK();
	public String getIsKhoXuatQuanLyKV();
	 
	public String getLoaixuatkho();
	public void setLoaixuatkho(String loaixuatkho);
		
	public ResultSet getRsDonhang();
	public void setRsRsDonhang(ResultSet rs);
	
	public String getDDHId();
	public void setDDHId(String DDHId);
	public void InitXuatKho_From_DDH();
	
}
