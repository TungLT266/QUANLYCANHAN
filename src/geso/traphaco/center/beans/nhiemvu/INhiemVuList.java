package geso.traphaco.center.beans.nhiemvu;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface INhiemVuList extends Serializable {

	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public void setId(double id);
	public double getId();
	
	public void setTieuChi(String value);
	public String getTieuChi();
	public void setDienGiai(String value);
	public String getDienGiai();
	public void setIsTuDong(boolean value);
	public boolean getIsTuDong();
	public void setLoaiTieuChi(String value);
	public String getLoaiTieuChi();
	public void setDoiTuong(String value);
	public String getDoiTuong();
	
	public void setNguoiTao(String nguoitao);
	public String getNguoiTao();
	public void setNguoiSua(String nguoisua);
	public String getNguoiSua();
	
	public void setNgayTao(String ngaytao);
	public String getNgayTao();
	public void setNgaySua(String nguoisua);
	public String getNgaySua();
	
	public void setTrangThai(String trangthai);
	public String getTrangThai();

	public String getMessage();
	public void setMessage(String msg);
	
	public List<INhiemVu> getList();
	public void setList(List<INhiemVu> value);
	
	public void setUserId(String userid);
	public String getUserId();

	public void init(String search);
	 
	public void closeDB();
}
