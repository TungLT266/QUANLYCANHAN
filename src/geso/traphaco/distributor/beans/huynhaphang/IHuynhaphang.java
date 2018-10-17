package geso.traphaco.distributor.beans.huynhaphang;

import geso.traphaco.distributor.beans.huynhaphang.ISanpham;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IHuynhaphang extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
		
	public String getNgaygiaodich();
	public void setNgaygiaodich(String ngaygiaodich);
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	
	public String getNhaphangId();
	public void setNhaphangId(String NhaphangId);
	
	
	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);

	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public String getMessage();
	public void setMessage(String msg);
	public String getNppId();
	public void setNppId(String nppId);
	
	public String getKhoId();
	public void setkhoId(String KhoId);
	
	public String getKBHId();
	public void setKBHId(String KbhId);
	
	
	public String getNppTen();
	public void setNppTen(String nppTen);

	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> splist);
	

	public boolean CreateDhtv();
	public void init();
	public void createRS();
	public void DBclose();


}

