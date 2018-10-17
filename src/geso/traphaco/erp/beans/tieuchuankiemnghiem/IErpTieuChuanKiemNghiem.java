package geso.traphaco.erp.beans.tieuchuankiemnghiem;

import java.sql.ResultSet;
import java.util.List;

public interface IErpTieuChuanKiemNghiem {

	public void init();
	public void createRs();
	public String getId();
	public void setId(String id);
	public void DBclose();
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getUserId();
	public void setUserId(String userId);
	public String getMa();
	public void setMa(String ma);
	public String getTen();
	public void setTen(String ten);
	public String getNgaycap();
	public void setNgaycap(String ngaycap);
	public String getSanphamId();
	public void setSanphamId(String sanphamId);
	public String getHosomau();
	public void setHosomau(String hosomau);
	public String getHosokiemnghiem();
	public void setHosokiemnghiem(String hosokiemnghiem);
	public String getPhieukiemnghiem();
	public void setPhieukiemnghiem(String phieukiemnghiem);
	public String getIshoatdong();
	public void setIshoatdong(String ishoatdong);
	public String getMsg();
	public void setMsg(String msg);
	public String getUserTen();
	public void setUserTen(String userTen);
	public String getNppId();
	public void setNppId(String nppId);
	public String getLoaimauknId();
	public void setLoaimauknId(String loaimauknId);
	public ResultSet getLoaimauknRs();
	public void setLoaimauknRs(ResultSet loaimauknRs);
	public String getBieumauhsId();
	public void setBieumauhsId(String bieumauhsId);
	public ResultSet getBieumauhsRs();
	public void setBieumauhsRs(ResultSet bieumauhsRs);
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet sanphamRs);
	public boolean createTCKN();
	public boolean UpdateTCKN();
	public List<IItemLoader> getYeuCauKNList();
	public void setYeuCauKNList(List<IItemLoader> yeuCauKNList);
	public List<IItemLoader> getThietbiList();
	public void setThietbiList(List<IItemLoader> thietbiList);
	public String getDienGiai();
	public void setDienGiai(String dienGiai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getPPID();
	public void setPPID(String pPID);
	public void loadPP(String index);
	public String getItem();
	public void setItem(String item);
	public String getYeucauIDSS();
	public void setYeucauIDSS(String yeucauIDSS);
	public String getThietbiIDSS();
	public void setThietbiIDSS(String thietbiIDSS);
}
