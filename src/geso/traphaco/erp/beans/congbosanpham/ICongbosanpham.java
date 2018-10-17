package geso.traphaco.erp.beans.congbosanpham;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface ICongbosanpham extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);

	public String getMasp();
	public void setMasp(String masp);
	
	public String getTensp();
	public void setTensp(String tensp);
	
	public String getMancc();
	public void setMancc(String mancc);
	
	public String getTenncc();
	public void setTenncc(String tenncc);
	
	public String getHancongbo();
	public void setHancongbo(String hancongbo);

	public String getHinhcongbo();
	public void setHinhcongbo(String hinhcongbo);
	
	public String getFilepath();
	public void setFilepath(String filepath);

	public String getMsg();
	public void setMsg(String msg);

	public boolean createCongbosanpham();
	public boolean updateCongbosanpham();

	public String convertDate(String date);
	public void init();
	public void createRs();

	public void DbClose();
	
	
	public String getSodangki() ;

	public void setSodangki(String sodangki) ;

	public String getDangbaocheId();

	public void setDangbaocheId(String dangbaocheId) ;

	public ResultSet getDangbaocheRs();

	public void setDangbaocheRs(ResultSet dangbaocheRs);

	public String getNgaybatdauhieuluc() ;
	public void setNgaybatdauhieuluc(String ngaybatdauhieuluc) ;

	public String getNgayketthuchieuluc() ;

	public void setNgayketthuchieuluc(String ngayketthuchieuluc) ;
}
