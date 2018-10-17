package geso.traphaco.erp.beans.giamgiahangmua;

import java.sql.ResultSet;

public interface IErpGiamgiahangmua
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();
	public void setId(String Id);
	
	public String getNgayghinhan();
	public void setNgayghinhan(String ngayghinhan);
	public String getNgayhoadon();
	public void setNgayhoadon(String ngayhoadon);
	public String getKyhieuhoadon();
	public void setKyhieuhoadon(String kyhieuhd);
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	public String getVat();
	public void setVat(String vat);
	public String getBvat();
	public void setBvat(String bvat);
	public String getAvat();
	public void setAvat(String avat);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNccId();
	public void setNccId(String nccId);
	public String getNccTen();
	public void setNccTen(String nccTen);

	public ResultSet getPORs();
	public void setPORs(ResultSet poRs);
	public String getPOId();
	public void setPOId(String poId);
	
	public String[] getHoadonTen();
	public void setHoadonTen(String[] hoadonTen);
	public String[] getIdsansham();
	public void setIdsanpham(String[] idsanpham);
	public String[] getMasansham();
	public void setMasanpham(String[] masanpham);
	public String[] getTensansham();
	public void setTensanpham(String[] tensanpham);
	public String[] getSoluong();
	public void setSoluong(String[] soluong);
	public String[] getDongia();
	public void setDongia(String[] dongia);
	public String[] getTongtien();
	public void setTongtien(String[] tongtien);
	public String[] getSotien();
	public void setSotien(String[] sotien);
	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createGiamgia();
	public boolean updateGiamgia();
	
	public void createRS();
	public void init();
	
	public void DbClose();
	public ResultSet getTienteRs();
	public String getTienteId();
	public void setTienteId(String ttId);
	public String getDVTiente();
	
	public String[] getLoai();
	
	public void setLoai(String[] loai);
	
	public String[] getHoadon();
	
	public void setHoadon(String[] hd);
	

}
