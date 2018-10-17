package geso.traphaco.erp.beans.danhmucvattu;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
/*import java.util.Hashtable;*/
import java.util.List;


/*
 * Thangpt author */
public interface IDanhmucvattu  extends  Serializable, IPhan_Trang
{
	
	public String getId();
	public void setId(String id);	
	
	public String getHieuLucTu();
	public void setHieuLucTu(String hieuluctu);

	public String getHieuLucDen();
	public void setHieuLucDen(String hieulucden);	
	
	public String getMaSpSxId();
	public void setMaSpSxId(String maspsxid);
	
/*	public String getIdSP();
	public void setIdSP(String IdSP);*/
	
	public ResultSet getRsMaSpSx();
	public void setRsMaSpSx(ResultSet rsmaspsx);
	
	public String getTenSpSx();
	public void setTenSpSx(String tenspsx);	
	
	public String getDvt();
	public void setDvt(String dvt);
	
	public String getSoLuongSx();
	public void setSoLuongSx(String soluongsx);
	
	public String getTrangThai();
	public void setTrangThai(String trangthai);
	
	public String getMessage();
	public void setMessage(String msg);
	 
	public void setListDanhMuc(List<IDanhmucvattu_SP> list);
	
	public List<IDanhmucvattu_SP> getListDanhMuc();

	public void init();
	
	/*public Hashtable<String, Integer> getSpThieuList();
	public void setSpThieuList(Hashtable<String, Integer> spThieuList);*/

	
	public void setUserTen(String userten);
	public String getUserTen();
	
	public boolean Save();
	public boolean Update();
	public boolean Edit(String _ischot);
	public boolean Delete();
	public void DBClose();
	
	public void setUserid(String user);
	public String getUserid();
	
	public void CreateDmList(String sql);
	
	public ResultSet getDmList();
	public void setNgaytao(String ngaytao);
	public String getNgaytao();
	
	public void setNgaysua(String ngaysua);
	public String getNgaysua();
	
	public void setNguoitao(String nguoitao);
	public String getNguoitao();
	
	public void setNguoisua(String nguoisua);
	public String getNguoisua();
	
	
}


