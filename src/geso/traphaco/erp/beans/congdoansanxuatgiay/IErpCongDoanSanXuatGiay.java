package geso.traphaco.erp.beans.congdoansanxuatgiay;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface IErpCongDoanSanXuatGiay
{
	public String getCtyId();

	public void setCtyId(String ctyId);

	public String getUserId();

	public void setUserId(String userId);

	public String getId();

	public void setId(String Id);

	public String getSpSelected();

	public void setSpSelected(String SpSelected);

	public String getDiengiai();

	public void setDiengiai(String diengiai);

	public ResultSet getNhamayRs();

	public void setNhamayRs(ResultSet nmRs);

	public String getNhamayIds();

	public void setNhamayIds(String nmIds);

	public ResultSet getThietbiRs();

	public void setThietbiRs(ResultSet tbRs);

	public String getTbIds();

	public void setTbIds(String tbIds);

	public String getSonhancong();

	public void setSonhancong(String sonhancong);

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getKiemdinhchatluong();

	public void setKiemdinhchatluong(String kiemdinhchatluong);

	// Checkbox de biet cong doan san xuat ra thanh pham hay ban thanh pham

	public String getSanXuat();

	public void setSanXuat(String SanXuat);

	/************ Kiem dinh chat luong *************/
	public ResultSet getRsSp();

	public void settRsSp(ResultSet rsSp);

	public ResultSet getRsDmvt();

	public void setRsDmvt(ResultSet rsDmvt);

	public String getSanPhamId();

	public void setSanPhamId(String sanPhamId);

	public String getSanPhamMa();

	public void setSanPhamMa(String sanphamMa);

	public String getDmvtId();

	public void setDmvtId(String dmvtId);

	public void setDinhluong(String dinhluong);

	public String getDinhluong();

	public void setDinhtinh(String dinhluong);

	public String getDinhtinh();

	public void setTieuchikiemdinhList(List<ITieuchikiemdinh> list);

	public List<ITieuchikiemdinh> getTieuchikiemdinhList();

	public String[] getToanTu();

	public String[] getTieuchi_Dinhtinh();

	public void setTieuchi_Dinhtinh(String[] tieuchi_dinhtinh);

	/*********** End Kiem dinh chat luong *************/

	boolean valid();

	public String getMsg();

	public void setMsg(String msg);

	public boolean createCumsanxuat();

	public boolean updateCumsanxuat();

	public void createRs();

	public void init();
	public void setDaytruyenSXId(String str);

	public void DbClose();
	public ArrayList<ITaisan> gettaisan(String mats);
	public List<ITaisan> getLts();

	public void setLts(List<ITaisan> lts);
	
	public String[] getSttpb();
	public void setSttpb(String[] sttpb);
	
	public ResultSet getPhongbanRs();
	public void getPhongbanRs(ResultSet phongbanRs);
	
	public String[] getPhongbanId();
	public void setPhongbanId(String[] phongbanId);
	
	public ResultSet getGiaidoanRs();
	public void getGiaidoanRs(ResultSet giaidoanRs);
	
	public String[] getGiaidoanId();
	public void setGiaidoanId(String[] giaidoanId);
	
	/******* mau in sc ****************/
	public ResultSet getLoaimauinSxRs() ;

	public void setLoaimauinSxRs(ResultSet loaimauinSxRs);

	public String getLoaimauinSxId() ;

	public void setLoaimauinSxId(String loaimauinSxId);
	
	public String getYckn();
	public void setYckn(String yckn);

	public ResultSet getLhsknRs();
	public void setLhsknRs(ResultSet lhsknRs);

	public String getLhsknList();
	public void setLhsknList(String lhsknList);
}
