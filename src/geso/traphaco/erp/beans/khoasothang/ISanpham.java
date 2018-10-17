package geso.traphaco.erp.beans.khoasothang;

public interface ISanpham 
{
	public String getPnkId();
	public void setPnkId(String pnkId);
	public String getSonhapkho();
	public void setSonhapkho(String sonhapkho);
	public String getNgaynhap();
	public void setNgaynhap(String ngaynhap);
	public String getNgaychot();
	public void setNgaychot(String ngaychot);
	public String getSpId();
	public void setSpId(String spId);
	public String getSpMa();
	public void setSpMa(String spMa);
	public String getSpTen();
	public void setSpTen(String spTen);
	public String getSoluong();
	public void setSoluong(String soluong);
	
	public String getGiaOld();
	public void setGiaOld(String giaOld);
	
	//Voi nhung nhap kho khong lay gia Viet
	public String getGiaNgoaiTe();
	public void setGiaNgoaiTe(String giaNT);
	public String getTyGia();
	public void setTyGia(String tygia);
	
	public String getGiaNew();
	public void setGiaNew(String giaNew);
}
