package geso.traphaco.erp.beans.erp_yeucausx;

import geso.traphaco.erp.beans.dubaokinhdoanh.IDubao;

import java.sql.ResultSet;
import java.util.List;

public interface IYeuCauSX {
	public void setPK_SEQ(String pk_seq);
	public String getPK_SEQ();
	public void setSANPHAM_FK(String sanpham_fk);
	public String getSANPHAM_FK();
	public List<IDubao>[] getThang();
	public void setThang(List<IDubao>[] kholist);
	public void setDiengiai(String diengiai);
	public String getDiengiai();
	public void setNGAYTAO(String ngaytao);
	public String getNGAYTAO();
	public void setNGUOITAO(String nguoitao);
	public String getNGUOITAO();
	public void setNGAYSUA(String ngaysua);
	public String getNGAYSUA();
	public void setNGUOISUA(String nguoisua);
	public String getNGUOISUA();
	public void setSANXUAT(String sanxuat);
	public String getSANXUAT();
	public void setDUBAO_FK(String dubao_fk);
	public String getDUBAO_FK();
	public void setTUAN1(String tuan);
	public String getTUAN1();
	public void setTUAN2(String tuan);
	public String getTUAN2();
	public void setTUAN3(String tuan);
	public String getTUAN3();
	public void setTUAN4(String tuan);
	public String getTUAN4();
	public void setMsg(String msg);
	public void setNAM(String nam);
	public String getNAM();
	public String getMsg();
	public ResultSet getRSDuBaoKinhDoanh();
	public void setRSDuBaoKinhDoanh(ResultSet rs);
	public void setRSTHANGDB(ResultSet rs);
	public ResultSet getRSTHANGDB();
	public void setNGAYDUBAO(String ngaydubao);
	public String getNGAYDUBAO();
	public void setTHANG(String thang);
	public String getTHANG();
	public int getsothang();
	public void setsothang(int sothang);
	public int getphantuj();
	public void setphantuj(int phantuj);
	public void setMangThang(String[] mangthang);
	public String[] getMangThang();
	public ResultSet getRsSanPhamSX();
	public void setRsSanphamSX(ResultSet rs);
	public void setSOTHANGDUBAO(String sothangdubao);
	public String getSOTHANGDUBAO();
	public boolean Create();
	public void CreateRS();
	public void init();
	public int getCacThangCoDuBao();
	public String[] getMANGCACTHANGDERATAB();
	
	public void DBClose();
}
