package geso.traphaco.distributor.beans.khachhang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IErpKhachHang_SPCK extends Serializable
{
	public String getId();
	public void setId(String id);
	
	public String getIdSanPham();
	public void setIdSanPham(String idsanpham);
	
	public String getTenSanPham();
	public void setTenSanPham(String tensanpham);	
	
	public String getMaSanPham();
	public void setMaSanPham(String masanpham);	
	
	public String getPTChietKhau();
	public void setPTChietKhau(String ptchietkhau);	
	
	public void setDonViTinh(String donvitinh);
	public String getDonViTinh();

}
