package geso.traphaco.center.beans.thuchiennhiemvu;

import java.sql.ResultSet;

public interface IThucHienNhiemVu
{
	public String getId();

	public void setId(String id);

	public String getThang();

	public void setThang(String thang);

	public String getNam();

	public void setNam(String nam);

	public String getDoituong();

	public void setDoituong(String doituong);

	public String getDvkdId();

	public void setDvkdId(String dvkdId);

	public String getKbhId();

	public void setKbhId(String kbhId);

	public String getMsg();

	public void setMsg(String msg);

	public ResultSet getRsKbh();

	public void setRsKbh(ResultSet rsKbh);

	public ResultSet getRsDvkd();

	public void setRsDvkd(ResultSet rsDvkd);

	public ResultSet getRsNv();

	public void setRsNv(ResultSet rsNv);

	public String getUserId();

	public void setUserId(String userId);
	
	public String getNvId();
	public void setNvId(String nvId);
	

	public void createRs();

	public void DBClose();
	
	public void init();
	
}
