package geso.traphaco.center.beans.nhiemvu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface INhiemVuNhanVienList {

	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public void setId(String id);
	public String getId();

	public void setDoiTuong(String value);
	public String getDoiTuong();
	public void setThang(int value);
	public int getThang();
	public void setNam(int value);
	public int getNam();
	public String getDvkdId();
	public void setDvkdId(String value);
	public String getKbhId();
	public void setKbhId(String value);
	public void setDienGiai(String value);
	public String getDienGiai();
	
	public void setNguoiTao(String nguoitao);
	public String getNguoiTao();
	public void setNguoiSua(String nguoisua);
	public String getNguoiSua();
	
	public void setNgayTao(String ngaytao);
	public String getNgayTao();
	public void setNgaySua(String nguoisua);
	public String getNgaySua();

	public String getMessage();
	public void setMessage(String msg);
	
	public List<INhiemVuNhanVien> getList();
	public void setList(List<INhiemVuNhanVien> value);
	
	public void setUserId(String userid);
	public String getUserId();

	public void init(String search);
	
	public void closeDB();
}
