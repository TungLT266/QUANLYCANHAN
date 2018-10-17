package geso.traphaco.erp.beans.lenhsanxuatgiay;

import java.sql.ResultSet;
import java.util.List;

public interface ISanPhamSanXuat {
	public void setLSXId(String LsxId );
	public String getLSXId();	
	public void setIdSp(String IdSp );
	public String getIdSp();

	public void setMaSp(String MaSp );
	public String getMaSp();

	public void setSoluong(String soluong );
	public String getSoluong();

	public void setTenSp(String TenSp );
	public String getTenSp();
	
	public void setKhorong(String Khorong);
	public String getKhorong();
	
	public void setBom(String bomid);
	public String getBomId();
	
	public void SetRsBom(ResultSet rsbom);
	public ResultSet getRsBom();
	
	public void setquydoi(float quydoi);
	public float getquydoi();
	
	public void setdonvi(String donvi);
	public String getDonvi();
	public void setChongbui(String chongbui);
	public String getChongbui();
	
	public void setIsCore(String iscore);
	public String getIsCore();

	public void setIdKhachhang(String idkh);
	public String getIdkhachhang();
	
	public void setTenkhachhang(String tenkh);
	public String getTenkhachhang();
	public void setcodemau(String codemau);
	public String getcodemau();
	
	public void SetListChiTiet(List<ISpSanxuatChitiet> listCt);
	public List<ISpSanxuatChitiet> getListChiTiet();
	
	public void SetListSpThayThe(List<ISpSanxuatChitiet> listThayThe);
	public List<ISpSanxuatChitiet> getListSpThayThe();
	
}
