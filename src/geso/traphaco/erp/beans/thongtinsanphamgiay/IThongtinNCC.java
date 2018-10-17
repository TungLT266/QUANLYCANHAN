package geso.traphaco.erp.beans.thongtinsanphamgiay;

import java.util.List;

public interface IThongtinNCC
{
	
	public void setnhacungcap(String nhacungcap);
	public String getnhacungcap();
	
	public void sethinhcongbo(String hinhcongbo);
	public String gethinhcongbo();
	 
	public void setchuthich(String chuthich);
	public String getchuthich();
	
	public void setfilenamecb(String hinhcongbo);
	public String getfilenamecb();
	
	public void sethancongbo(String hancongbo);
	public String gethancongbo();
	
	public void setluongdattoithieu(String luongdattoithieu);
	public String getluongdattoithieu();
	
	public void setthoihangiaohang(String thoihangiaohang);
	public String getthoihangiaohang();
	
	public void setfhinh(String fhinh);
	public String getfhinh();
	public void setTieuchikiemdinhDinhluongList(List<ITieuchikiemdinh> list) ;
	 
	public List<ITieuchikiemdinh> getTieuchikiemdinhDinhluongList() ;
	 

	public void setTieuchikiemdinhDinhtinhList(List<ITieuchikiemdinh> list) 
	;
	public List<ITieuchikiemdinh> getTieuchikiemdinhDinhtinhList() 
	;
	public List<IHoaChat_SanPham> getHoaChatKiemDinhList();
	public void setHoaChatKiemDinhList(List<IHoaChat_SanPham> hoaChatKiemDinhList);
	public List<IMayMoc_SanPham> getMayMocKiemDinhList();
	public void setMayMocKiemDinhList(List<IMayMoc_SanPham> mayMocKiemDinhList);
	public void addHoaChatKiemDinh(IHoaChat_SanPham elementHoaChat);
	void loadHoaChatKiemDinhListCuaNCC(IThongtinsanphamgiay sanPham);
	void loadMayMocKiemDinhListCuaNCC(IThongtinsanphamgiay sanPham);
	public void addMayMocKiemDinh(IMayMoc_SanPham elementMayMoc);
	void init();
	String getTenNhaCungCap();
	void setTenNhaCungCap(String tenNhaCungCap);
}
