package geso.traphaco.erp.beans.tieuhao;
 
import java.util.List;

public interface ISanpham
{
	public String getStt();
	public void setStt(String stt);
	
	public String getTieuHaoId();
	public void setTieuHaoId(String tieuhaoId);
	
	public String getId();
	public void setId(String id);
	
	public String getMa();
	public void setMa(String ma);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getDonViTinh();
	public void setDonViTinh(String dvt);
	
	
	public double getSoLuongDaTieuHao();
	public void setSoLuongDaTieuHao(double SoLuongDaTieuHao);
	
	public double getSoLuongChuan();
	public void setSoLuongChuan(double soluongchuan);
	public double getSoLuongThucTe();
	public void setSoLuongThucTe(double soluongthucte);
	public double getsoluongDMTieuhao();
	public void setsoluongDMTieuhao(double soluongdmtieuhao);
	
	public double getsoluongXuat();
	public void setsoluongXuat(double soluongxuat);
	
	public List<ISanphamLo> getSpList();
	public void setSpList(List<ISanphamLo> spList);
	
	
}
