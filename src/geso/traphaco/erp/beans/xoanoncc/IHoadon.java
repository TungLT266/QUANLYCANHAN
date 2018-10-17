package geso.traphaco.erp.beans.xoanoncc;

public interface IHoadon extends Comparable<IHoadon>
{
	public String getId();
	public void setId(String id);
	public String getKyhieu();
	public void setKyhieu(String kyhieu);
	public String getSo();
	public void setSo(String so);
	public String getNgay();
	public void setNgay(String ngay);
	public String getTongtiencoVAT();
	public void setTongtiencoVAT(String aVat);
	public String getThanhtoan();
	public void setThanhtoan(String thanhtoan);
	public String getConlai();
	public void setConlai(String conlai);
	public String getTrahet();
	public void setTrahet(String trahet);
	public String getHopdong();
	public void setHopdong(String hopdong);
	public String getLoaict();
	public void setLoaict(String loaict);
}
