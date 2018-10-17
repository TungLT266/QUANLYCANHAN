package geso.traphaco.distributor.beans.xuatkho;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpXuathoadonKMList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getKhTen();
	public void setKhTen(String khTen);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public String getPhanloai();
	public void setPhanloai(String phanloai);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String search);
	public void DBclose();
	public ResultSet getRskhoid();
	public void setRskhoid(ResultSet rskhoid);
	public String getNguoitao();

	public void setNguoitao(String nguoitao);

	public String getKhohh();

	public void setKhohh(String khohh);
	public String getNguoigiao();

	public void setNguoigiao(String nguoigiao);
	public ResultSet getRsnvbanhang();

	public void setRsnvbanhang(ResultSet rsnvbanhang);
	public String getNvbanhang();

	public void setNvbanhang(String nvbanhang);
	
	public void setNvgnId(String nvgnId);
	public String getNvgnId();
	public void setNvgnRs(ResultSet nvgnRs);
	public ResultSet getNvgnRs();
	
	public void initLICHGIAOHANG(String search);
	
	//NEU DANG NHAP LA TDV, THI CHI LAY NPP VA TDV NAY
	public String getNpp_duocchon_id();
	public void setNpp_duocchon_id(String npp_duocchon_id);
	public String getTdv_dangnhap_id();
	public void setTdv_dangnhap_id(String tdv_dangnhap_id);
	
}
