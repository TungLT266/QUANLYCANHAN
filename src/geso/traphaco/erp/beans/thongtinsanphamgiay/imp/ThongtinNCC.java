package geso.traphaco.erp.beans.thongtinsanphamgiay.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IErp_TaiSanCoDinh;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IHoaChat_SanPham;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IMayMoc_SanPham;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinNCC;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinsanphamgiay;
import geso.traphaco.erp.beans.thongtinsanphamgiay.ITieuchikiemdinh;

public class  ThongtinNCC implements IThongtinNCC 
{
	
	private String Nhacungcap;
	private String hinhcongbo;
	private String chuthich;
	private String filenamecb;
	
	private String hancongbo;
	private String luongdattoithieu;
	private String thoihangiaohang;
	private String fhinh;
	
	private String tenNhaCungCap;
	
	List<ITieuchikiemdinh> tckdDinhtinhList;
	 
	List<ITieuchikiemdinh> tckdDinhluongList;
	
	List<IHoaChat_SanPham> hoaChatKiemDinhList;
	
	List<IMayMoc_SanPham> mayMocKiemDinhList;
	
	dbutils db;
	
	public ThongtinNCC(){
		
		 tckdDinhluongList = new ArrayList<ITieuchikiemdinh>();
		 tckdDinhtinhList = new ArrayList<ITieuchikiemdinh>();
		 hoaChatKiemDinhList = new ArrayList<IHoaChat_SanPham>();
		 mayMocKiemDinhList = new ArrayList<IMayMoc_SanPham>();
		 db = new dbutils();
		 
	}
	
	@Override
	public void setnhacungcap(String nhacungcap) {
		// TODO Auto-generated method stub
		this.Nhacungcap=nhacungcap;
	}

	@Override
	public String getnhacungcap() {
		// TODO Auto-generated method stub
		return this.Nhacungcap;
	}

	@Override
	public void sethinhcongbo(String hinhcongbo) {
		// TODO Auto-generated method stub
		this.hinhcongbo=hinhcongbo;
		
	}

	@Override
	public String gethinhcongbo() {
		// TODO Auto-generated method stub
		return this.hinhcongbo;
	}

	@Override
	public void setchuthich(String chuthich) {
		// TODO Auto-generated method stub
		this.chuthich=chuthich;
	}

	@Override
	public String getchuthich() {
		// TODO Auto-generated method stub
		return this.chuthich;
	}

	@Override
	public void setfilenamecb(String filenamecb) {
		// TODO Auto-generated method stub
		this.filenamecb=filenamecb;
	}

	@Override
	public String getfilenamecb() {
		// TODO Auto-generated method stub
		return this.filenamecb;
	}

	@Override
	public void sethancongbo(String hancongbo) {
		// TODO Auto-generated method stub
		this.hancongbo=hancongbo;
	}

	@Override
	public String gethancongbo() {
		// TODO Auto-generated method stub
		return this.hancongbo;
	}

	@Override
	public void setluongdattoithieu(String luongdattoithieu) {
		// TODO Auto-generated method stub
		this.luongdattoithieu=luongdattoithieu;
	}

	@Override
	public String getluongdattoithieu() {
		// TODO Auto-generated method stub
		return this.luongdattoithieu;
	}

	@Override
	public void setthoihangiaohang(String thoihangiaohang) {
		// TODO Auto-generated method stub
		this.thoihangiaohang= thoihangiaohang;
	}

	@Override
	public String getthoihangiaohang() {
		// TODO Auto-generated method stub
		return this.thoihangiaohang;
	}

	@Override
	public void setfhinh(String fhinh) {
		// TODO Auto-generated method stub
		this.fhinh=fhinh;
	}

	@Override
	public String getfhinh() {
		// TODO Auto-generated method stub
		return this.fhinh;
	}

	@Override
	public void setTieuchikiemdinhDinhluongList(List<ITieuchikiemdinh> list) {
		// TODO Auto-generated method stub
		this.tckdDinhluongList= list;
	}

	@Override
	public List<ITieuchikiemdinh> getTieuchikiemdinhDinhluongList() {
		// TODO Auto-generated method stub
		return this.tckdDinhluongList;
	}

	@Override
	public void setTieuchikiemdinhDinhtinhList(List<ITieuchikiemdinh> list) {
		// TODO Auto-generated method stub
			this.tckdDinhtinhList=list;
	}

	@Override
	public List<ITieuchikiemdinh> getTieuchikiemdinhDinhtinhList() {
		// TODO Auto-generated method stub
		return this.tckdDinhtinhList;
	}

	@Override
	public List<IHoaChat_SanPham> getHoaChatKiemDinhList() {
		return hoaChatKiemDinhList;
	}

	@Override
	public void setHoaChatKiemDinhList(List<IHoaChat_SanPham> hoaChatKiemDinhList) {
		this.hoaChatKiemDinhList = hoaChatKiemDinhList;
	}

	@Override
	public List<IMayMoc_SanPham> getMayMocKiemDinhList() {
		return mayMocKiemDinhList;
	}

	@Override
	public void setMayMocKiemDinhList(List<IMayMoc_SanPham> mayMocKiemDinhList) {
		this.mayMocKiemDinhList = mayMocKiemDinhList;
	}

	@Override
	public void addHoaChatKiemDinh(IHoaChat_SanPham elementHoaChat) {
		elementHoaChat.setNhaCungCap(this);
		this.hoaChatKiemDinhList.add(elementHoaChat);
	}

	@Override
	public void addMayMocKiemDinh(IMayMoc_SanPham elementMayMoc) {
		elementMayMoc.setNhaCungCap(this);
		this.mayMocKiemDinhList.add(elementMayMoc);
	}

	@Override
	public String getTenNhaCungCap() {
		return tenNhaCungCap;
	}

	@Override
	public void setTenNhaCungCap(String tenNhaCungCap) {
		this.tenNhaCungCap = tenNhaCungCap;
	}

	@Override
	public void init() {
		String query = "";
		try{
			query = "SELECT * FROM ERP_NHACUNGCAP WHERE PK_SEQ = "+this.Nhacungcap;
			ResultSet rs = db.get(query);
			if (rs != null ) {
				while(rs.next()){
					this.tenNhaCungCap = rs.getString("TEN");
				}
			}
			
			rs.close();
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	
	@Override
	//Ham load thong tin hoa chat cua nha cung cap
	public void loadHoaChatKiemDinhListCuaNCC(IThongtinsanphamgiay sanPham) {
		String query = "";
		try{
			query = "SELECT [SANPHAM_FK]\r\n" + 
					"      ,[HOACHAT]\r\n" + 
					"      ,[SOCHATDUOCKIEMDINH]\r\n" + 
					"      ,[SOCHATKIEMDINH]\r\n" + 
					"      ,[NCC_FK]\r\n" + 
					"	  ,[MA]\r\n" + 
					"	  ,[TEN]\r\n" + 
					"	  ,[DIENGIAI]\r\n" + 
					"  FROM [dbo].[HOACHAT_SANPHAM] HC\r\n" + 
					"  INNER JOIN [dbo].[ERP_SANPHAM] SP ON HC.[HOACHAT] = SP.PK_SEQ\r\n" + 
					"  INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = SP.DVDL_FK" + 
					"  WHERE [SANPHAM_FK] = "+sanPham.getId()+ " AND [NCC_FK] = "+this.Nhacungcap;
			System.out.println(query);
			ResultSet resultSetHoaChat = db.get(query);
			this.hoaChatKiemDinhList.clear();
			if (resultSetHoaChat != null ) {
				while(resultSetHoaChat.next()){
					this.addHoaChatKiemDinh(new HoaChat_SanPham(sanPham.getId(), sanPham.getDvdlTen(), 
							resultSetHoaChat.getString("HOACHAT"), resultSetHoaChat.getString("MA"), 
							resultSetHoaChat.getString("TEN"), resultSetHoaChat.getString("DIENGIAI"),
							resultSetHoaChat.getInt("SOCHATDUOCKIEMDINH"), resultSetHoaChat.getInt("SOCHATKIEMDINH")));
				}
			}
			
			resultSetHoaChat.close();
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	@Override
	//HAM LOAD THONG TIN MAY MOC CUA NHA CUNG CAP
	public void loadMayMocKiemDinhListCuaNCC(IThongtinsanphamgiay sanPham) {
		String query = "";
		try{
			ResultSet resultSetMayMoc = db.get(query);
			query = "SELECT PK_SEQ, MA, TEN \r\n" + 
					"FROM MAYMOC_SANPHAM MM\r\n" + 
					"INNER JOIN ERP_TAISANCODINH TSCD ON MM.TAISANCODINH_FK = TSCD.pk_seq\r\n" + 
					"  WHERE [SANPHAM_FK] = "+sanPham.getId()+ " AND [NCC_FK] = "+this.Nhacungcap;
			resultSetMayMoc = db.get(query);
			if (resultSetMayMoc != null) {
				while(resultSetMayMoc.next()){
					IErp_TaiSanCoDinh mayMoc = new Erp_TaiSanCoDinh();
					mayMoc.setId(resultSetMayMoc.getString("PK_SEQ"));
					mayMoc.setMa(resultSetMayMoc.getString("MA"));
					mayMoc.setDiengiai(resultSetMayMoc.getString("TEN"));
					IMayMoc_SanPham mayMocKiemDinh = new MayMoc_SanPham(sanPham.getId(), mayMoc);
					this.addMayMocKiemDinh(mayMocKiemDinh);
				}
				
			}
			
			resultSetMayMoc.close();
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	
}
