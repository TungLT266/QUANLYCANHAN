package geso.traphaco.center.beans.mucchietkhau;

public interface IChietkhau 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getLoai();
	public void setLoai(String loai);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getPhantramDS();
	public void setPhantramDS(String phantramDS);
	
	public String getGioihan();
	public void setGioihan(String gioihan);
	
	public String getTongsotien();
	public void setTongsotien(String tongsotien);
	
	public String getHanmucno();
	public void setHanmucno(String hanmucno);
	
	public String getSongayno();
	public void setSongayno(String songayno);
	
	public String getSotien();
	public void setSotien(String sotien);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean Create();
	public boolean Update();
	
	public void init();
	public void createRs();
	
	public void DBClose();
}
