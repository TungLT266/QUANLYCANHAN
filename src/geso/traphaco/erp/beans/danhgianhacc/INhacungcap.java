package geso.traphaco.erp.beans.danhgianhacc;

import java.io.Serializable;

public interface INhacungcap extends Serializable {
	
	public String getId();
	public void setId(String id);
	public String getMa();
	public void setMa(String ma);
	public String getTen();
	public void setTen(String ten);
	public String getDiachi();
	public void setDiachi(String diachi);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public String getMessage();
	public void setMessage(String msg);
	
	public String getNguoilienhe();
	public void setNguoilienhe(String nglienhe);
	
	public String getDienthoai();
	public void setDienthoai(String dienthoai);
	
	public String getDotincay();
	public void setDotincay(String dotincay);
	
	public String getChatluong();
	public void setChatluong(String chatluong);
	
	public String getKhanangcn();
	public void setKhanangcn(String khanangcn);
	
	public String getGiaca();
	public void setGiaca(String giaca);
	
	public String getPtthanhtoan();
	public void setPtthanhtoan(String ptthanhtoan);
	
	public String getTggiaohang();
	public void setTggiaohang(String tggiaohang);
	
	public String getHaumai();
	public void setHaumai(String haumai);
	
	public String getSopo();
	public void setSopo(String sopo);
	
	public String getTongluong();
	public void setTongluong(String tongluong);
	
	public String getChon();
	public void setChon(String chon);
}