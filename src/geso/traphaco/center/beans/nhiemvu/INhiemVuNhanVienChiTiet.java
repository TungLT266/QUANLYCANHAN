package geso.traphaco.center.beans.nhiemvu;

public interface INhiemVuNhanVienChiTiet
{
	public void setNvId(String value);

	public String getNvId();

	public void setNvTen(String value);

	public String getNvTen();

	public void setDoiTuong(String value);

	public String getDoiTuong();

	public void setThuong(float value);

	public float getThuong();

	public void setSoluong(float value);

	public float getSoluong();

	public void init(String nvnvId);

	public void closeDB();
}