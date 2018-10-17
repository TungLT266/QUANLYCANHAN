package geso.traphaco.erp.beans.canhbaothieuhang;

import java.sql.ResultSet;

public interface ICanhbao 
{
	public String getChungtu();
	public void setChungtu(String chungtuId);
	
	public String getKhachhang();
	public void setKhachhang(String khId);
	public String getSanphamId();
	public void setSanphamId(String spId);
	public String getSanpham();
	public void setSanpham(String sanpham);
	public String getNgaygiaoOld();
	public void setNgaygiaoOld(String ngaygiaoOld);
	public String getNgaygiao();
	public void setNgaygiao(String ngaygiao);
	public String getSoluonggiao();
	public void setSoluonggiao(String soluonggiao);
	public String getThieu();
	public void setThieu(String thieu);
	public String getMaKH();

	public void setMaKH(String maKH);
	
	public ResultSet getLsxRs();
	public void setLsxRs(ResultSet lsxRs);
	
	public String getSanPhamMa();
	public void setSanPhamMa(String string);
	
	public void DBClose();
}
