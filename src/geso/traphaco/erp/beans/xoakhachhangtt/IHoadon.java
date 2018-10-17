package geso.traphaco.erp.beans.xoakhachhangtt;
import java.util.List;

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
	public String getLoaihd();
	public void setLoaihd(String loaihd);
	public String getTongtiencoVAT();
	public void setTongtiencoVAT(String aVat);
	public String getTongtiencoVATVND();
	public void setTongtiencoVATVND(String aVatVND);
	public String getThanhtoan();
	public void setThanhtoan(String thanhtoan);
	public String getConlai();
	public void setConlai(String conlai);
	public String getTrahet();
	public void setTrahet(String trahet);
	
	public String getTienteId();
	public void setTienteId(String tienteId);
	
	public String getTigiaHD();
	public void setTigiaHD(String tigiaHD);
	
	public String getLoaict();
	public void setLoaict(String loaict);
	
	public String getTenkh();
	public void setTenkh(String tenkh);
	
	public String getKhId();
	public void setKhId(String KhId);
	
	public String getKenhTen();
	public void setKenhTen(String Kenhten);
	
	public String getTiengocHD();
	public void setTiengocHD(String TiengocHD);
	
	public List<IHoadonct> getHoadonList();
	public void setHoadonList(List<IHoadonct> spList);
	
	public String getNgaydenhanTT();
	public void setNgaydenhanTT(String ngaydenhantt);
	
	public String getKenhId();
	public void setKenhId(String Kenhid);
	
	public String getMaKH();
	public void setMaKH(String makh);
	
	public String getSotiengoc();
	public void setSotiengoc(String sotiengoc);
	
	public String getptck();
	public void setptck(String ptck);
	
	public String getthucthu();
	public void setthucthu(String thucthu);
	
	public String gettienck();
	public void settienck(String tienck);
	
	public String getghichu();
	public void setghichu(String ghichu);
	
	public String getisNPP();
	public void setisNPP(String isNPP);
	
}
