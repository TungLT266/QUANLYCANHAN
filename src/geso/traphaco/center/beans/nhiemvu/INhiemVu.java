package geso.traphaco.center.beans.nhiemvu;

public interface INhiemVu {
	public void setId(String id);
	public String getId();
	
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
	
	public void setTenLoaiTieuChi(String value);
	public String getTenLoaiTieuChi(); 
	
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
	
	public void setUserId(String userid);
	public String getUserId();

	public void init();
	public boolean create();
	public boolean update();
	
	public void closeDB();
}
