package geso.traphaco.distributor.beans.dieuchinhtonkho;

import java.io.Serializable;
import java.sql.ResultSet;


import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;



public interface IDieuchinhtonkho extends Serializable
{
	public String getId();
	
	public void setId(String id);

	public String getNppId();
	
	public void setNppId(String nppId);

	public String getNppTen();
	
	public void setNppTen(String nppTen);

	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getUserTen();
	
	public void setUserTen(String userTen);
	
	public String getNgaydc();
	
	public void setNgaydc(String ngaydc);
	
	
	public String getLydodc();
	
	public void setLydodc(String lydodc);
	
	
	public String getNccId();
	
	public void setNccId(String nccId);

	public ResultSet getNcc();
	
	public void setNcc(ResultSet ncc);

	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);
	
	public String getKbhId();
	
	public void setKbhId(String kbhId);

	public ResultSet getKbh();
	
	public void setKbh(ResultSet kbh);
	
	public String getKhoId();
	
	public void setKhoId(String khoId);

	public ResultSet getKho();
	
	public void setKho(ResultSet kho);

	public String getGtdc();
	
	public void setGtdc(String gtdc);
	
	public String[] getSpId();
	
	public void setSpId(String[] spId);
	
	public String[] getMasp();
	
	public void setMasp(String[] masp);
	
	public String[] getTensp();
	
	public void setTenSp(String[] tensp);
	
	public String[] getTonkho();
	
	public void setTonkho(String[] tonkho);

	public String[] getDonvi();
	
	public void setDonvi(String[] dv);
	
	public String[] getDc();
	
	public void setDc(String[] dc);
	
	public String[] getGiamua();
	
	public void setGiamua(String[] giamua);

	public String[] getTtien();
	
	public void setTtien(String[] ttien);
	
	public String getMessage();
	
	public void setMessage(String msg);

	public int getSize();
	
	public void setSize(int size);
	
	
	public String[] getSolo();
	public void setSolo(String [] solo);
	
	public String[] getTonthung();
	public void setTonthung(String[] tonthung);
	
	public String[] getTonle();
	public void setTonle(String[] tonle);
	
	
	public String[] getSoluongthung();
	public void setSoluongthung(String[] soluongthung);
	
	public String[] getSoluongle();
	public void setSoluongle(String[] soluongle);
	
	public String[] getQuycach();
	public void setQuycach(String[] quycach);
	
	public String[] getDongiathung();
	public void setDongiathung(String[] dongiathung);
	

	public String[] getSpNgayHetHan();
	public void setSpNgayHetHan(String[] ngayHetHan);
	
	
	
	public void init0();
	public void initNew();
	public void initUpdate();
	public void initDisplay();
	public boolean CreateDctk(HttpServletRequest request) throws SQLException;
	public boolean UpdateDctk(HttpServletRequest request) throws SQLException;
	public void DBclose();
}