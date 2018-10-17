package geso.traphaco.distributor.beans.doihang;

import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IErpKhachhangdoihang
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);
	public String getNgaydenghi();
	public void setNgaydenghi(String ngaydenghi);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getNppId();
	public void setNppId(String nppId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public ResultSet getDvtRs();
	public void setDvtRs(ResultSet dvtRs);
	
	public String getSchemeId();
	public void setSchemeId(String kbhId);
	public ResultSet getSchemeRs();
	public void setSchemeRs(ResultSet schemeRs);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getVat();
	public void setVat(String vat);
	
	
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	
	public ResultSet getSanphamDoiRs();
	public void setSanphamDoiRs(ResultSet spDoiRs);
	
	public void setSanpham_soluong(Hashtable<String, String> sp_soluong);
	public Hashtable<String, String> getSanpham_soluong();
	
	public void setSanpham_solo(Hashtable<String, String> sp_solo);
	public Hashtable<String, String> getSanpham_solo();
	
	public void setSanpham_NSX(Hashtable<String, String> sp_nsx);
	public Hashtable<String, String> getSanpham_NSX();
	
	public ResultSet getSoloRs( String spId, String khoId );
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createNK(HttpServletRequest request);
	public boolean updateNK(HttpServletRequest request);
	public boolean duyetNK(HttpServletRequest request);
	

	public void createRs();
	public void createRsDuyet();
	
	public void init();
	public void initDuyet();
	
	public void DBclose();
	
}
