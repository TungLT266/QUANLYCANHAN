package geso.traphaco.erp.beans.khoasothang;

 

import java.util.List;

public interface IErptinhgiadongluc
{
	public String getId();
	public void setId(String IdDongHoDien);
	
	public String getTrangthai();
	public void setTrangthai(String Trangthai);
	
	
	public String getUserId();
	public void setUserId(String UserId);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public int  getThang();
	public void setThang(int thang);
	
	public int  getNam();
	public void setNam(int nam);
	
	
 
	public double getTongTienDien();
	public void setTongTienDien(double tongtiendien);
	
	public List<IErptiendien> GetListiendien();
	public void setListiendien(List<IErptiendien> listtiendien);
	
	
	public double getTongTienNuoc();
	public void setTongTienNuoc(double tongtiennuoc);
	
	public List<IErptiennuoc> GetListiennuoc();
	public void setListiennuoc(List<IErptiennuoc> listtiennuoc);

	public List<IErpNuocda> GetLisnuocda();
	public void setListnuocda(List<IErpNuocda> listnuocda);
	
	
	public void DbClose();
	public void Init();
	public boolean Save();
	public boolean Edit();
	
}
