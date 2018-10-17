package geso.traphaco.erp.beans.kichbansanxuatgiay.imp;

public class ErpChiTietBom 
{

	private String stt;
	private String sanpham;
	private double soluong;
	private Boolean tinh;
	
	public ErpChiTietBom(String sp)
	{
		this.stt = "";
		this.sanpham = sp;
		this.soluong = 0;
		this.tinh = false;
	}
	
	public ErpChiTietBom(String stt, String sp, double soluong)
	{
		this.stt = stt;
		this.sanpham = sp;
		this.soluong = soluong;
		this.tinh = false;
	}

	public String getStt()
	{
		return this.stt;
	}

	public void setStt(String stt)
	{
		this.stt = stt;
	}

	public String getSanpham()
	{
		return this.sanpham;
	}

	public void setSanpham(String sanpham)
	{
		this.sanpham = sanpham;
	}

	public double getSoluong()
	{
		return this.soluong;
	}

	public void setSoluong(double soluong)
	{
		this.soluong = soluong;
	}
	
	public Boolean getTinh()
	{
		return this.tinh;
	}

	public void setTinh(Boolean tinh)
	{
		this.tinh = tinh;
	}
	
	@Override
    public boolean equals(Object o) {
        return sanpham.equals(((ErpChiTietBom)o).getSanpham());
    }
}
