package geso.traphaco.erp.beans.parktrongnuoc;
import java.util.List;
public interface IErpHoadon 
{
	public String getId();
	
	public void setId(String id);
	
	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getMauhoadon();
	
	public void setMauhoadon(String mauhoadon);
	
	public String getMausohoadon();
	
	public void setMausohoadon(String mausohoadon);
	
	public String getKyhieu();
	
	public void setKyhieu(String kyhieu);
	
	public String getSohoadon();
	
	public void setSohoadon(String sohoadon);
	
	public String getPark();
	
	public void setPark(String park);
	
	public String getNgayhoadon();
	
	public void setNgayhoadon(String ngayhoadon);
	
	public String getSotienavat();
	
	public void setSotienavat(String sotienavat);
	
	public String getSotienavatVND();
	
	public void setSotienavatVND(String sotienavatVND);
	
	public String getSotienbvat();
	
	public void setSotienbvat(String sotienBvat);
	
	public String getVAT();
	
	public void setVAT(String vat);
	
	public String getThuesuat();
	
	public void setThuesuat(String thuesuat);
	
	public String getChieckhau();
	
	public void setChieckhau(String chieckhau);
	
	public List<IErpHoadonSp> getPnkList();
	
	public void setPnkList(List<IErpHoadonSp> pnkList);
	
	public String getTrangthai();
	
	public String getTiente(); 
	
	public void setTiente(String tiente); 
	
	public void setTrangthai(String trangthai);

	public String getSoBL();
	
	public void setSoBL(String soBL);

	public String getNgayBL();
	
	public void setNgayBL(String ngayBL);

	public String[] getPoId();
	
	public void setPoId(String[] PoId);
	
	public String[] getLoai();
	
	public void setLoai(String[] loai);

	public String[] getMahang();
	
	public void setMahang(String[] mahang);
	
	public String[] getDonvi();
	
	public void setDonvi(String[] donvi);

	public String[] getSoluong();
	
	public void setSoluong(String[] soluong);

	public String[] getDongia();
	
	public void setDongia(String[] dongia);

	public String[] getThanhtien();
	
	public void setThanhtien(String[] thanhtien);
		
	public String getNgaydenhanTT();
	
	public void setNgaydenhanTT(String ngaydenhanTT);
	
}
