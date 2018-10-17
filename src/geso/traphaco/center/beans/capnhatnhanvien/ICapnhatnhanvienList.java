package geso.traphaco.center.beans.capnhatnhanvien;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface ICapnhatnhanvienList extends Serializable, IPhan_Trang
{
	public void setTen(String Ten);
	public String getTen();
	public void setNgaysinh(String Ngaysinh);
	public String getNgaysinh();
	public void setTungay(String Tungay);
	public String getTungay();
	public void setDenngay(String Denngay);
	public String getDenngay();
	public ResultSet getDSNV();
	public void init(String st);
	public void setuserId(String userId);
	public String getuserId();
	public void setTrangthai(String Trangthai);
	public String getTrangthai();
	public void setPhanloai(String Phanloai);
	public String getPhanloai();
	public boolean xoa(String Id);
	public void setmsg(String msg);
	public String getmsg();
	
	public void setTenquyen(String tenQuyen);
	public String getTenquyen();
	
	public void setNhomquyenId(String nhomquyenId);
    public String getNhomquyenId();
    public void setNhomquyenRs(ResultSet nhomRs);
    public ResultSet getNhomquyenRs();
  
    public String getPhongbanId() ;
	public void setPhongbanId(String phongbanId);
	public ResultSet getPhongbanRs();
	public void setPhongbanRs(ResultSet phongbanRs);
	public void DbClose();
	
}
