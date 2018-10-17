package geso.traphaco.distributor.beans.nhomkhachhang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface INhomkhachhangNPP extends Serializable {
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();

	public void setId(String id);
		
	public String getDiengiai();
	
	public void setDiengiai(String diengiai);

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
		
	public ResultSet getKhList();

	public void setKhList(ResultSet khList);

	public ResultSet getKhSelected();

	public void setKhSelected(ResultSet khSelected);

	public ResultSet getVungList();

	public void setVungList(ResultSet vungList);
	
	public ResultSet getKvList();

	public void setKvList(ResultSet kvList);
	
	public ResultSet getNppList();

	public void setNppList(ResultSet nppList);
	
	public String getVungId();

	public void setVungId(String vungId);

	public String getKvId();

	public void setKvId(String kvId);

	public String getNppId();

	public void setNppId(String nppId);
	
	public void UpdateRS();

	public String[] getKhachhang();

	public void setKhachhang(String[] khachhang);
		
	public boolean saveNewNkh();
	
	public boolean updateNkh();   
	public void DBClose();
	

	public String getLoaiNpp();

	public void setLoaiNpp(String loaiNpp) ;
	
	public String getTen() ;

	public void setTen(String ten);
	

}
