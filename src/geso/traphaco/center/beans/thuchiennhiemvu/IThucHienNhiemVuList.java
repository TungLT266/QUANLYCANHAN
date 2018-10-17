package geso.traphaco.center.beans.thuchiennhiemvu;

import java.sql.ResultSet;

public interface IThucHienNhiemVuList
{
	public String getId();
	public void setId(String id);
	
	public String getThang();
	public void setThang(String thang);
	
	public String getNam();
	public void setNam(String nam);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getRsThucHienNhiemVu();
	public void setThucHienNhiemVu(ResultSet RsThucHienNhiemVu);
	
	public ResultSet getRsNhiemvu_NhanVien();
	public void setRsNhiemvu_NhanVien(ResultSet Nhiemvu_NhanVien);
	
	
	public void closeDB();
	
}
