package geso.traphaco.erp.beans.yeucauchuyenkho;

import java.sql.ResultSet;

public interface IErpCannguyenlieu
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCtyId();
	public void setCtyId(String ctyId);
	
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);
	
	public String getLydoyeucau();
	public void setLydoyeucau(String lydoyeucau);
	
	public String getKyHieu();
	public void setKyHieu(String kyhieu);
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getTongSLYC();
	public void setTongSLYC(String tongSLYC);
	
	public String getKhoXuatId();
	public void setKhoXuatId(String khoxuattt);
	public String getKhoXuatTen();
	public void setKhoXuatTen(String khoxuattt);
	public ResultSet getKhoXuatRs();
	public void setKhoXuatRs(ResultSet khoxuatRs);
	
	public String getMsg();
	public void setMsg(String msg);
	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public boolean createCK();
	public boolean updateCK();
	
	public void createRs();
	public void init( boolean display );

	public void DBclose();

	public String getCoDoituong();
	public void setCoDoituong(String codoituong);
	public String getLoaidoituongId();
	public void setLoaidoituongId(String loaidoituong);
	public String getDoituongId();
	public void setDoituongId(String doituongId);
	public ResultSet getDoituongRs();
	public void setDoituongRs(ResultSet doituongRs);

	public String getVitrichuyenId();
	public void setVitrichuyenId(String vitriChuyenId);
	public ResultSet getVitriChuyenRs();
	public void setVitriChuyenRs(ResultSet vitriChuyenRs);
	
	public String getCochiphi();
	public void setCochiphi(String coChiphi);
	public String getChiphiId();
	public void setChiphiId(String chiphiId);
	public ResultSet getChiphiRs();
	public void setChiphiRs(ResultSet chiphiRs);
	
	public String getSanphamId();
	public void setSanphamId(String spId);
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	
	public String getSoloId();
	public void setSoloId(String soloId);
	public ResultSet getSoloRs();
	public void setSoloRs(ResultSet soloRs);
	
	public String[] getSpId();
	public void setSpId(String[] id);
	public String[] getSpVitri();
	public void setSpVitri(String[] vitri);
	public String[] getSpMame();
	public void setSpMame(String[] mame);
	public String[] getSpMathung();
	public void setSpMathung(String[] mathung);
	public String[] getSpMaphieu();
	public void setSpMaphieu(String[] maphieu);
	public String[] getSpPhieuDT();
	public void setSpPhieuDT(String[] phieudt);
	public String[] getSpPhieuEO();
	public void setSpPhieuEO(String[] phieuEO);
	public String[] getSpMARQ();
	public void setSpMARQ(String[] marq);
	public String[] getSpHamluong();
	public void setSpHamluong(String[] hamluong);
	public String[] getSpHamam();
	public void setSpHamam(String[] hamam);
	
	public String[] getSpTonkho();
	public void setSpTonkho(String[] spTonkho);
	public String[] getSpSoluong();
	public void setSpSoluong(String[] spSoluong);
	
	
	
	
}
