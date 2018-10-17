package geso.traphaco.erp.beans.kho;

import geso.traphaco.erp.beans.xuatdungccdc.Erp_Item;

import java.sql.ResultSet;
import java.util.List;

public interface IErp_KhoTT 
{
	public void setPK_SEQ(String pk_seq);
	public String getPK_SEQ();
	public void setCongty_fk(String congty_fk);
	public String getCongty_fk();
	public void setTen(String ten);
	public String getTen();
	public void setDiachi(String diachi);
	public String getDiachi();
	public void setQuanlybin(String quanlybin);
	public String getQuanlybin();
	
	public void setNguoitao(String nguoitao);
	public String getNguoitao();
	public void setNguoisua(String nguoisua);
	public String getNguoisua();
	public void setNgaytao(String ngaytao);
	public String getNgaytao();
	public void setNgaysua(String ngaysua);
	public String getNgaysua();
	
	public void setMa(String ma);
	public String getMa();
	public void setMsg(String msg);
	public String getMsg();
	public void setTrangthai(String trangthai);
	public String getTrangthai();
	
	public void setLoai(String loai);
	public String getLoai();

	public void setRSCongty(ResultSet rs);
	public ResultSet getRSCongty();
	
	public void setNhamayRs(ResultSet nhamayRs);
	public ResultSet getNhamayRs();
	public void setNhamayId(String nhamayId);
	public String getNhamayId();
	
	public void setKhoTTRs(ResultSet khottRs);
	public ResultSet getKhoTTRs();
	public void setKhoTTIds(String khoIds);
	public String getKhoTTIds();
	
	public void setLoaiSPRs(ResultSet loaispRs);
	public ResultSet getLoaiSpRs();
	public void setLoaispIds(String loaiSpIds);
	public String getLoaispIds();
	
	public void setSanphamRs(ResultSet sanphamRs);
	public ResultSet getSanphamRs();
	public void setSpIds(String spIds);
	public String getSpIds();
	
	public boolean init();
	public boolean Create();
	public boolean Update();
	
	public void CreateRs();
	public void DBClose();
	
	public List<Erp_Item> getLoaiKhoList();
	public void setLoaiKhoList(List<Erp_Item> loaiKhoList);
}