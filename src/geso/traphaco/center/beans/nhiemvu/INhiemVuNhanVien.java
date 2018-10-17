package geso.traphaco.center.beans.nhiemvu;

import java.sql.ResultSet;
import java.util.List;

public interface INhiemVuNhanVien {
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
	
	public void setDvkdList(ResultSet dvkdList);
	public ResultSet getDvkdList();  
	public void setKbhList(ResultSet kbhList);
	public ResultSet getKbhList();
	
	public void setNvList(List<INhiemVuNhanVienChiTiet> value);
	public List<INhiemVuNhanVienChiTiet> getNvList();
	
	public void setNguoiTao(String nguoitao);
	public String getNguoiTao();
	public void setNguoiSua(String nguoisua);
	public String getNguoiSua();
	
	public void setNgayTao(String ngaytao);
	public String getNgayTao();
	public void setNgaySua(String nguoisua);
	public String getNgaySua();

	public String getDvkdTen();
	public void setDvkdTen(String value);
	public String getKbhTen();
	public void setKbhTen(String value);

	public String getMessage();
	public void setMessage(String msg);
	
	public void setUserId(String userid);
	public String getUserId();

	public void init();
	public boolean create();
	public boolean update();
	
	public List<INhiemVuNhanVienChiTiet> createNhiemVuNhanVienChiTietList();
	public List<INhiemVuNhanVienChiTiet> loadNhiemVuNhanVienChiTietList(String nvnvId);
	
	public void closeDB();
}
