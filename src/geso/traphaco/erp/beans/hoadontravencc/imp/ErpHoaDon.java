package geso.traphaco.erp.beans.hoadontravencc.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.hoadon.IErpHoaDonList;
import geso.traphaco.erp.beans.hoadontravencc.IErpHoaDon;
import geso.traphaco.erp.beans.hoadontravencc.IErpHoaDon_SP;
import geso.traphaco.erp.beans.nhapkhoNK.ISpDetail;
import geso.traphaco.erp.beans.nhapkhoNK.imp.SpDetail;
import geso.traphaco.erp.util.CapnhatKT;
import geso.traphaco.erp.util.DinhDang;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ErpHoaDon extends Phan_Trang implements IErpHoaDon 
{
	private static final long serialVersionUID = 1L;
	String Id;
	String NgayXuaHd;
	String TrangThai;
	String NgayTao;
	String NguoiTao;
	String NgaySua;
	String NguoiSua;
	String HoanTat;
	double ChietKhau;
	double VAT;
	double TienVat;
	String Msg;
	String Kyhieu;
	String SoHoaDon;
	String IdDonHangDat;
	String HinhThucTT;
	String NppId;
	String NppTen;
	String khoId;
	String KhoTen;
	String LoaiDonHang;
	String DeNghiDatHangId;
	String POMT;
	String Sodontrahang;
	ResultSet listHoaDon;
	ResultSet rsddhChuaXuatHD;
	

	List<IErpHoaDon_SP> listsanpham=new ArrayList<IErpHoaDon_SP>();
	

	List<IErpHoaDon_SP> listdonhang_sp =new ArrayList<IErpHoaDon_SP>();
	
	List<IErpHoaDonList> HoadonList;
	
	double TongTienChuaCK=0;
	double TienCK=0;
	double TienSauCK=0;
	String Loaihoadon="";
	double TongTienTruocVAT=0;
	double TongTienSauVAT=0;
	double TTTienTraKM=0;
	String IDNhaCungCap;
	String TenNhaCungCap;
	String KenhBanHangId;
	String DVKDID;
	String SoSO="";
	double NoCu=0;
	String Sku;
	String Scheme_chietkhau[];
	String Scheme_ck_thanhtien[];
	double SoTienTraTrungBay;
	String NguoiMuaHang;
	private int num;
	private int[] listPages;
	private int currentPages;
	dbutils db;
	String sochungtu;
	
	
	/*
	 * 
	 * cac bien luu lai gia tri tim kiem
	 */
	String tungay;
	String denngay;
	String UserId;
	String[] DonDatHang;
	String poInfo;
	String noidungCK;
	String GhiChu;
	String ghichu1;
	String ghichu2;
	String ghichu3;
	String TenKhXhd;
	String DiachiKhXhd;
	String MasothueXhd;
	
	String ctyId;
		
	List<IThongTinHienThi> hienthiList;
	
	
	

	public String getSochungtu() {
		return this.sochungtu;
	}
	
	public  ErpHoaDon(){
		
		this.TrangThai="";
		this.tungay="";
		this.denngay="";
		this.SoHoaDon="";
		this.Msg="";
		this.Sku="";
		this.NgayXuaHd="";
		this.HinhThucTT="";
		 this.createKyHieu();
		this.NppId="";
		this.TenKhXhd="";
		this.DiachiKhXhd="";
		this.MasothueXhd="";
		this.Kyhieu = "";
		this.Sodontrahang="";
		this.NppTen="";
		this.NguoiMuaHang="";
		this.DonDatHang=  new String[0];
		this.POMT="";
		this.poInfo = "";
		this.noidungCK = "";
		currentPages = 1;
		this.HinhThucTT="TM/CK";
		num = 1;
		this.Id="";
		this.GhiChu="";
		this.noidungCK="";
		this.Scheme_chietkhau=new String[]{"","",""};
		this.Scheme_ck_thanhtien=new String[]{"","",""};
		
		this.HoadonList = new ArrayList<IErpHoaDonList>();
		
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
		db=new dbutils();
		
		this.ctyId = "";
	}
	
	private void createKyHieu() {/*
		
		String query=" SELECT top 1 kyhieu FROM ERP_HOADON ORDER BY ngayxuathd desc";
		System.out.println(query);
		ResultSet rs=db.get(query);
		try{
		if(rs!=null && rs.next()){
				this.Kyhieu=rs.getString("kyhieu");
		}
		rs.close();
		}catch(Exception er){
			er.printStackTrace();
		}
	*/	this.Kyhieu ="";
	}
	
	public void init(){

		try
		{
			this.Scheme_chietkhau=new String[]{"","",""};
			this.Scheme_ck_thanhtien=new String[]{"","",""};
			
			// LẤY THÔNG TIN HÓA ĐƠN ĐÃ LƯU
			
			String query=   " SELECT isnull(TENKHXHD,'') as TENKHXHD ,isnull(DIACHIKHXHD,'') as DIACHIKHXHD  , \n " +
							"		 isnull(MASOTHUEKHXHD,'') as  MASOTHUEKHXHD , HD.PK_SEQ as sochungtu ,ISNULL(HD.LOAIHOADON,'0') AS LOAIHOADON ,HD.PK_SEQ,HD.NGAYXUATHD,HD.GHICHU,HD.NOIDUNGCHIETKHAU,HD.PO_MT,HD.NGUOIMUA,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT, \n " +
							" 		 CASE WHEN HD.LOAIHOADON = 2 THEN NV.TEN  ELSE  NPP.TEN  END AS NPP ,HD.NCC_FK, NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA,CHIETKHAU,VAT,TONGTIENAVAT,TONGTIENBVAT,HD.HOANTAT, '' as ghichu1, '' as ghichu2, '' as ghichu3  \n " +
							" FROM 	 ERP_HOADON HD \n " +
							" 		 LEFT JOIN ERP_NHACUNGCAP NPP ON NPP.PK_SEQ = HD.NCC_FK \n " +
							" 		 LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = HD.NCC_FK  \n " +
							" 		 INNER JOIN NHANVIEN NT ON NT.PK_SEQ = HD.NGUOITAO \n " +
							" 		 INNER JOIN NHANVIEN NS ON NS.PK_SEQ = HD.NGUOISUA \n " +
							" WHERE  HD.PK_SEQ = " + this.Id;
			System.out.println("INIT :"+query);
			db=new dbutils();
			ResultSet rs= db.get(query);
		 
			while (rs.next())
			{
				this.TenKhXhd = rs.getString("TENKHXHD");
				this.DiachiKhXhd = rs.getString("DIACHIKHXHD");
				this.MasothueXhd = rs.getString("MASOTHUEKHXHD");
				this.Loaihoadon = rs.getString("LOAIHOADON");
				this.NgaySua=rs.getString("ngaysua");
				this.NgayTao=rs.getString("ngaytao");
				this.NgayXuaHd=rs.getString("NGAYXUATHD");
				this.NppId=rs.getString("NCC_FK");
				this.NppTen=rs.getString("npp");
				this.SoHoaDon=rs.getString("sohoadon");
				this.TrangThai=rs.getString("trangthai");
				this.Kyhieu=rs.getString("kyhieu");
				this.HinhThucTT=rs.getString("HINHTHUCTT");
				this.NguoiMuaHang=rs.getString("NGUOIMUA");
				this.POMT=rs.getString("PO_MT");
				this.TongTienChuaCK=rs.getDouble("tongtienbvat");
				this.TienCK=rs.getDouble("chietkhau");
				this.TienVat=rs.getDouble("vat");
				this.TongTienSauVAT=rs.getDouble("tongtienavat");
				this.TienSauCK=this.TongTienChuaCK-this.TienCK;
				this.Msg="";
				this.noidungCK=rs.getString("noidungchietkhau");
				this.GhiChu=rs.getString("ghichu");
				this.HoanTat=rs.getString("HoanTat");
				this.ghichu1 = rs.getString("ghichu1");
				this.ghichu2 = rs.getString("ghichu2");
				this.ghichu3 = rs.getString("ghichu3");
				
				this.sochungtu = rs.getString("sochungtu");
			}
			rs.close();
				
			
			//LẤY ĐƠN MUA HÀNG TỪ NHÀ CUNG CẤP
			
			query = "	SELECT HOADON_FK, ddh_fk as DDH_FK " +
					"	FROM ERP_HOADON_DDH " +
					"	WHERE HOADON_FK = " + this.Id;
 
			System.out.println(query);
			
			rs = this.db.get(query);
			
			this.DonDatHang=new String[20];
			int i= 0;
			while(rs.next())
			{
				this.DonDatHang[i] =rs.getString("DDH_FK");
				i=i+1;
			}
			
			// HIỂN THỊ TẤT CẢ ĐƠN MUA HÀNG TỪ NHÀ CUNG CẤP
			query =
			"	SELECT 	PK_SEQ AS ID ,NGAYMUA  AS ngaydat " +
			"	FROM 	ERP_MUAHANG ddh \n"+        
			"	WHERE 	NHACUNGCAP_FK = "+this.NppId+" AND TRANGTHAI in (1,2,3) and ddh.TYPE = 2 AND \n " +
			" 			PK_SEQ NOT IN (SELECT 	DDH_FK \n " +
			"						   FROM 	ERP_HOADON_DDH a INNER JOIN ERP_HOADON b on a.HOADON_FK = b.PK_SEQ \n "+  
			"						   WHERE  	a.HOADON_FK NOT IN ("+this.Id+") and b.TRANGTHAI!=2 and b.LOAIHOADON = 6 ) \n"; 
		
			System.out.println(query);
		
			this.rsddhChuaXuatHD=db.get(query);
			
			// HIỂN THỊ THÔNG TIN SẢN PHẨM TỪ HÓA ĐƠN
			
			query=  " SELECT DDH_SP.SANPHAM_FK,SP.MA,SP.TEN as tensp,DONGIA,SOLUONG AS SOLUONG, \n "+ 
				  	" 		  DONVITINH AS DONVI \n " +
				  	" FROM 	 ERP_HOADON_SP DDH_SP \n "+ 
				  	" 		 INNER JOIN SANPHAM SP ON SP.PK_SEQ = DDH_SP.SANPHAM_FK \n "+ 
				  	" WHERE  DDH_SP.SOLUONG > 0 AND HOADON_FK = "+this.Id +" \n " +
				  	" ORDER BY (soluong* dongia) desc"; 
						rs=this.db.get(query);
						while(rs.next())
						{
							IErpHoaDon_SP sanpham= new ErpHoanDon_SP();
							sanpham.setId(this.Id);
							sanpham.setMaSanPham(rs.getString("ma"));
							sanpham.setTenSanPham(rs.getString("tensp"));
							sanpham.setSoLuong(rs.getInt("soluong"));
							sanpham.setDonGia(rs.getDouble("dongia"));
							sanpham.setDonViTinh(rs.getString("DONVI"));
							
							this.listsanpham.add(sanpham);
							
							this.TongTienChuaCK=this.TongTienChuaCK+ (rs.getInt("soluong") * rs.getDouble("dongia"));
						}
					 
		 
		}
		catch(Exception er)
		{
			er.printStackTrace();
		}
	}
	
	public ErpHoaDon(String id)
	{
		this.Id = id;
		this.TrangThai="";
		this.tungay="";
		this.denngay="";
		this.SoHoaDon="";
		this.Msg="";
		this.Sku="";
		this.NgayXuaHd="";
		this.HinhThucTT="";
		this.NppId="";
		this.TenKhXhd="";
		this.DiachiKhXhd="";
		this.MasothueXhd="";
		this.Kyhieu = "";
		this.Sodontrahang="";
		this.NppTen="";
		this.NguoiMuaHang="";
		this.DonDatHang=  new String[0];
		this.POMT="";
		this.poInfo = "";
		this.noidungCK = "";
		currentPages = 1;
		this.HinhThucTT="TM/CK";
		num = 1;
		this.GhiChu="";
		this.noidungCK="";
		this.Scheme_chietkhau=new String[]{"","",""};
		this.Scheme_ck_thanhtien=new String[]{"","",""};
		
		this.HoadonList = new ArrayList<IErpHoaDonList>();
		
		if(id.length()>0)
			init();
		
		db=new dbutils();
	
	}
	
	public String getId() {
		
		return this.Id;
	}

	
	public void setId(String id) {
		
		this.Id=id;
	}

	
	public String getNgayxuathd() {
		
		return this.NgayXuaHd;
	}

	
	public void setNgayxuathd(String ngayxuathd) {
		
		this.NgayXuaHd=ngayxuathd;
	}

	
	public String getNppTen() {
		
		return this.NppTen;
	}

	
	public void setNppTen(String nppTen) {
		
		this.NppTen=nppTen;
	}

	
	public String getTrangthai() {
		
		return (this.TrangThai==null?"":this.TrangThai);
	}

	
	public void setTrangthai(String trangthai) {
		
		this.TrangThai=trangthai;
	}

	
	public String getNgaytao() {
		
		return this.NgayTao;
	}

	
	public void setNgaytao(String ngaytao) {
		
		this.NgayTao=ngaytao;
	}

	
	public String getNguoitao() {
		
		return this.NguoiTao;
	}

	
	public void setNguoitao(String nguoitao) {
		
	this.NguoiTao=nguoitao;	
	}

	
	public String getNgaysua() {
		
		return this.NgaySua;
	}

	
	public void setNgaysua(String ngaysua) {
		
		this.NgaySua=ngaysua;
	}

	
	public String getNguoisua() {
		
		return this.NguoiSua;
	}

	
	public void setNguoisua(String nguoisua) {
		
		this.NguoiSua=nguoisua;
	}

	
	public double getChietkhau() {
		
		return this.ChietKhau;
	}

	
	public void setChietkhau(double chietkhau) {
		
		this.ChietKhau=chietkhau;
	}

	
	public double getVAT() {
		
		return this.VAT;
	}

	
	public void setVAT(double vat) {
		
		this.VAT=vat;
	}

	
	public String getMessage() {
		
		return this.Msg;
	}

	
	public void setMessage(String msg) {
		
		this.Msg=msg;
	}

	
	public void SetKyHieu(String kyhieu) {
		
		this.Kyhieu=kyhieu;
	}

	
	public String getKyHieu() {
		
		return this.Kyhieu;
	}

	
	public void SetSoHoaDon(String sohoadon) {
		
		this.SoHoaDon=sohoadon;
	}

	
	public String getSoHoaDon() {
		
		return this.SoHoaDon;
	}

	
	public String gethinhthuctt() {
		
		return this.HinhThucTT;
	}

	
	public void sethinhthuctt(String hinhthuctt) {
		
		this.HinhThucTT=hinhthuctt;
	}

	
	public double getTongtienchuaCK() {
		
		return this.TongTienChuaCK;
	}

	
	public void setTongtienchuaCK(double ttcck) {
		
		
	}

	
	public double getTienCK() {
		
		return this.TienCK;
	}

	
	public void setTienCK(double tienck) {
		
		this.ChietKhau=tienck;
	}

	
	public double getTongtientruocVAT() {
		
		return this.TongTienTruocVAT;
	}

	
	public void setTongtientruocVAT(double tttvat) {
		
		this.TongTienTruocVAT=tttvat;
	}

	
	public double getTongtiensauVAT() {
		
		return this.TongTienSauVAT;
	}

	
	public void setTongtiensauVAT(double ttsvat) {
		
		this.TongTienSauVAT=ttsvat;
	}

	
	public ResultSet getListHoaDon() {
		
		return this.listHoaDon;
	}

	private String LayDuLieu(String id) {
		String query = "";
		
		String laytk = "";
		//NTVN CHI GHI NHAN
		query ="SELECT  b.MA + '-' + b.TEN as TENNCC, \n " +
				   "	   (SELECT TAIKHOAN_FK FROM ERP_NHACUNGCAP  WHERE PK_SEQ = a.NCC_FK) as taiKhoanNCC, \n " +
				   "	   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51180000' AND CONGTY_FK = a.CONGTY_FK) as taiKhoanCO_TIENHANG, \n " +
				   "	   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52110000' AND CONGTY_FK = a.CONGTY_FK) as taiKhoanNO_TIENCK, \n " +
				   "	   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK = a.CONGTY_FK) as taiKhoanCO_TIENTHUE, \n " +
				   "	   (isnull(a.TONGTIENBVAT,0) - isnull(a.CHIETKHAU,0) )  as TienHang, isnull(a.VAT,0) as Tienthue, isnull(a.CHIETKHAU,0) as TienCK \n" +
				   "FROM   ERP_HOADON a INNER JOIN ERP_NHACUNGCAP b on a.NCC_FK = b.PK_SEQ \n " +
				   "WHERE  a.PK_SEQ = '"+id+"'";
			
		
		System.out.println("___KE TOAN DOANH SO: " + query);
		ResultSet rsDoanhSo = db.get(query);
		
		int i = 1;
		try{
			if(rsDoanhSo != null)
			{
				String taikhoanktCo = "";
				String taikhoanktNo = "";
				
				String TENNCC = "";
				
				while(rsDoanhSo.next())
				{	
					
					double tienhang = Math.round(rsDoanhSo.getDouble("TienHang"));
					
					double tienthue = Math.round(rsDoanhSo.getDouble("Tienthue"));	
					
					double tienck = Math.round(rsDoanhSo.getDouble("TienCK"));	
					
					taikhoanktNo = rsDoanhSo.getString("taiKhoanNCC");
					
					TENNCC = rsDoanhSo.getString("TENNCC");
					
					if(tienhang > 0)
					{
						taikhoanktCo = rsDoanhSo.getString("taiKhoanCO_TIENHANG");	
						
						if(laytk.trim().length() > 0) laytk += " UNION ALL \n";
						
						laytk += " SELECT N'NỢ' NO_CO, PK_SEQ , "+tienhang+" SOTIEN, ( SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanktNo+"') AS SOHIEUTAIKHOAN, \n"+ 
								" 		 N'"+TENNCC+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
								" FROM 	 ERP_HOADON \n"+
								" WHERE  PK_SEQ = '"+id+"' \n" +				
						
								" UNION ALL \n"+
				
								" SELECT  N'CÓ' NO_CO, PK_SEQ, "+tienhang+" SOTIEN, ( SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ ='"+taikhoanktCo+"') AS SOHIEUTAIKHOAN,  \n"+
								"		 N'' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
								" FROM 	  ERP_HOADON CQT \n"+
								" WHERE   PK_SEQ = '"+id+"' \n";
						i++;
					}
					
					if(tienthue > 0)
					{
						
						taikhoanktCo = rsDoanhSo.getString("taiKhoanCO_TIENTHUE");	
						
						if(laytk.trim().length() > 0) laytk += " UNION ALL \n";
						
						laytk += " SELECT N'NỢ' NO_CO, PK_SEQ , "+tienthue+" SOTIEN, ( SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanktNo+"') AS SOHIEUTAIKHOAN, \n"+ 
						" 		 N'"+TENNCC+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
						" FROM 	 ERP_HOADON \n"+
						" WHERE  PK_SEQ = '"+id+"' \n" +				
				
						" UNION ALL \n"+
		
						" SELECT  N'CÓ' NO_CO, PK_SEQ, "+tienthue+" SOTIEN, ( SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ ='"+taikhoanktCo+"') AS SOHIEUTAIKHOAN,  \n"+
						"		 N'' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
						" FROM 	  ERP_HOADON CQT \n"+
						" WHERE   PK_SEQ = '"+id+"' \n";
						
						i++;
					}
					
					if(tienck > 0)
					{
						
						taikhoanktNo = rsDoanhSo.getString("taiKhoanCO_TIENCK");	
						taikhoanktCo = rsDoanhSo.getString("taiKhoanNCC");	
						
						if(laytk.trim().length() > 0) laytk += " UNION ALL \n";
						
						laytk += " SELECT N'NỢ' NO_CO, PK_SEQ , "+tienck+" SOTIEN, ( SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanktNo+"') AS SOHIEUTAIKHOAN, \n"+ 
						" 		 N'' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
						" FROM 	 ERP_HOADON \n"+
						" WHERE  PK_SEQ = '"+id+"' \n" +				
				
						" UNION ALL \n"+
		
						" SELECT  N'CÓ' NO_CO, PK_SEQ, "+tienck+" SOTIEN, ( SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ ='"+taikhoanktCo+"') AS SOHIEUTAIKHOAN,  \n"+
						"		 N'"+ TENNCC +"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
						" FROM 	  ERP_HOADON CQT \n"+
						" WHERE   PK_SEQ = '"+id+"' \n";
						
						i++;
					}
					
				}
				rsDoanhSo.close();
			}
		}
		catch( Exception e){
			e.printStackTrace();
		}
		
		if(laytk.trim().length()>0) laytk += " ORDER BY PK_SEQ, STT, SAPXEP ";
		
		if(laytk.trim().length()<=0){
			laytk += 
				" SELECT '' NO_CO, '' PK_SEQ, '' SOHIEUTAIKHOAN, '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP " +
				" FROM ERP_HOADON HD "+
				" WHERE HD.PK_SEQ = '"+id+"'";
		}
		return laytk;
	}
	
	public void setListHoaDon(String sql) {
		
		String query="";
		if(!sql.equals("")){
			query=sql;
		}else{
			query=" SELECT HD.LOAIHOADON , HD.PK_SEQ, HD.NGAYXUATHD,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT,NV.TEN , HD.TONGTIENAVAT, "+
				"	 CASE WHEN HD.LOAIHOADON = 6 THEN NV.TEN ELSE  NPP.TEN END AS NPP, NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA ,isnull( HD.HOANTAT,'0') as HOANTAT "+
				"   FROM ERP_HOADON HD LEFT JOIN ERP_NHACUNGCAP NPP ON NPP.PK_SEQ=HD.NCC_FK " +
				"	LEFT JOIN ERP_NHACUNGCAP NV ON NV.PK_SEQ=HD.NCC_FK "+
				" INNER JOIN NHANVIEN NT ON NT.PK_SEQ=HD.NGUOITAO "+
				" INNER JOIN NHANVIEN NS ON NS.PK_SEQ=HD.NGUOISUA  " +
				"  WHERE  LOAIHOADON = 6 AND HD.CONGTY_FK = "+ this.ctyId +" ";
		}
 
		//this.listHoaDon=createSplittingData(50, 10, "NGAYXUATHD DESC, cast(SOHOADON as numeric(18,0)) desc", query);
		
		System.out.println(query);
		String sql1 = createSplittingData_List(25, 10, " NGAYXUATHD DESC, cast(SOHOADON as numeric(18,0)) desc", query) ;

		ResultSet rs = db.get(sql1);
		
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		try
		{
			if(rs!= null)
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{		
					ht = new ThongTinHienThi();
					//LAY DINH KHOAN KE TOAN
					String dk = LayDuLieu(rs.getString("PK_SEQ") );		
					
					ResultSet rsKT = db.get(dk);
					
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
					if(rsKT!= null)
					{
						IDinhKhoanKeToan kt = null;
						while(rsKT.next())
						{
							kt = new DinhKhoanKeToan(rsKT.getString("PK_SEQ"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),
										rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
										rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
							ktList.add(kt);
						}
						
						rsKT.close();
					}
					
					ht.setSOTIENVND(rs.getString("TONGTIENAVAT"));
					ht.setId(rs.getString("PK_SEQ"));
					ht.setNgayxuat(rs.getString("NGAYXUATHD"));
					ht.setTrangthai(rs.getString("TRANGTHAI"));
					ht.setHoantat(rs.getString("HOANTAT"));
					ht.setSohoadon(rs.getString("SOHOADON"));
					ht.setKhachhang(rs.getString("NPP"));
					ht.setNgaytao(rs.getString("NGAYTAO"));
					ht.setNgaysua(rs.getString("NGAYSUA"));
					ht.setNguoisua(rs.getString("NGUOISUA"));
					ht.setNguoitao(rs.getString("NGUOITAO"));
					
					ht.setLayDinhkhoanKT(ktList);
					htList.add(ht);	
					

					
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		this.hienthiList = htList;
		
		
		/*List<IErpHoaDonList> hdList = new ArrayList<IErpHoaDonList>();
		if(rs!= null)
		{
			try
			{
				IErpHoaDonList hd = null;
				while(rs.next())
				{
					String id = rs.getString("PK_SEQ") ;
					String ngayxuathd = rs.getString("NGAYXUATHD");
					String trangthai = rs.getString("TRANGTHAI");
					String hoantat = rs.getString("HOANTAT");
					String soxuatkho = "";
					String sohoadon = rs.getString("SOHOADON");
					String khachhang = rs.getString("NPP");
					String ngaytao = rs.getString("NGAYTAO");
					String ngaysua = rs.getString("NGAYSUA");
					String nguoitao = rs.getString("NGUOITAO");
					String nguoisua = rs.getString("NGUOISUA");
					String tongtienavat = "";
					
					// Lấy lịch sử in của từng HD 
					query = " SELECT T.PK_SEQ, T.NGAYIN, NV.TEN AS NGUOIIN, T.SOLANIN," +
							"        CASE T.TRANGTHAIHD WHEN '0' THEN N'Chưa chốt' " +
							"                           WHEN '1' THEN N'Đã chốt' " +
							"                           WHEN '2' THEN N'Đã xóa' " +
							"                           ELSE  N'Đã hủy' " +
							"        END AS TRANGTHAIHD " +
							" FROM LICHSUIN T INNER JOIN NHANVIEN NV ON T.NGUOIIN = NV.PK_SEQ " +
							" WHERE T.SOCHUNGTU = "+ rs.getString("PK_SEQ") +" AND T.LOAIHD = '1' ";
					
					ResultSet rsLayLS = db.get(query);
					List<IHdDetail> hdDetail = new ArrayList<IHdDetail>();
					 
						IHdDetail hdD = null;
						while(rsLayLS.next())
						{
							String pk_seq = rsLayLS.getString("PK_SEQ");
							String ngayinHd = rsLayLS.getString("NGAYIN");
							String nguoiin = rsLayLS.getString("NGUOIIN");
							String solanin = rsLayLS.getString("SOLANIN");
							String trangthaiHd = rsLayLS.getString("TRANGTHAIHD");
							
							hdD = new HdDetail(pk_seq, "", "",trangthaiHd, ngayinHd, solanin, nguoiin, "" );
							hdDetail.add(hdD);
							
						}
						rsLayLS.close();
				 
					
					hd = new ErpHoaDonList(id,soxuatkho, ngayxuathd, sohoadon, tongtienavat, "", khachhang, trangthai, hoantat,
							               ngaytao, ngaysua, nguoitao,nguoisua );
					hd.setHdDetailList(hdDetail);
					hdList.add(hd);
					
					
				}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		this.HoadonList = hdList;*/
		
	}	
		
	public void setTungay(String _tungay) {
		
		this.tungay=_tungay;
	}

	
	public String getTungay() {
		
		
		 return this.tungay;
	}

	
	public void setDenNgay(String _denngay) {
		
		this.denngay=_denngay;
	}

	
	public String getDenNgay() {
		
		return this.denngay;
	}

	
	public String getUserid() {
		
		return this.UserId;
	}

	
	public void setUserId(String user) {
		
		this.UserId=user;
	}
	
	public String[] getDonDatHang() {
		
		return this.DonDatHang;
	}
	
	public void setDonDatHang(String[] dondathang) {
		
		this.DonDatHang=dondathang;
	}
	
	public void CreateRs(boolean loadlaighichu) {
		
	 
		if(!this.NppId.equals("")){
			
			String	 query="";
			 
				 query= 
				    "	select PK_SEQ AS ID ,NGAYMUA  AS ngaydat \n"+
				    "   from ERP_MUAHANG ddh \n"+        
					"	where NHACUNGCAP_FK="+this.NppId+"    and trangthai in (1,2) and ddh.TYPE = 2 and PK_SEQ not in \n"+ 
					"	(SELECT DDH_FK FROM ERP_HOADON_DDH a INNER JOIN ERP_HOADON b on a.HOADON_FK = b.PK_SEQ \n"+  
					"	WHERE a.HOADON_FK NOT IN ("+(this.Id.trim().length()<=0 ? "0": this.Id)+") and b.TRANGTHAI!=2 and b.LOAIHOADON = 6 ) \n"; 
				
			System.out.println("__LAY CHUNG TU: " + query);
		    this.rsddhChuaXuatHD=db.get(query);
			
			String chuoi="";
			int i=0;
			if(this.DonDatHang!=null){
			while(i<this.DonDatHang.length){
				if(i==0){
					chuoi=DonDatHang[i];
				}
				else{
					chuoi=chuoi +"," +DonDatHang[i];
				}
				i=i+1;
			}
			 
			if(chuoi.length() >0) {
				if(this.Loaihoadon.equals("6")){
				 
					query=  " SELECT ISNULL ( CAST(DDH.TONGTIENBVAT AS FLOAT) ,0) AS SOTIENBVAT, "+
							"\n ISNULL(CAST (DDH.TONGTIENAVAT AS FLOAT),0)-  ISNULL ( CAST(DDH.TONGTIENBVAT AS FLOAT) ,0) AS TIENVAT,     "+
							"\n 0 AS CHIETKHAU,      "+
							"\n ISNULL(CAST (DDH.TONGTIENAVAT AS FLOAT),0)  AS TIENAVAT , "+ 
							"\n '' AS GHICHU,''  AS NOIDUNGCHIETKHAU      "+
							"\n FROM ERP_MUAHANG DDH  where ddh.pk_seq in ("+chuoi+") and DDH.NHACUNGCAP_FK="+this.NppId;
				}
				
				System.out.println("Du Lieu : "+query);
				ResultSet rs=db.get(query);
				double phantramthue=0;	
				double phantramck=0;
				double thanhtiendh=0;
				int dong=12;
				  
				try{
			 
					if(	rs.next()){
					
						this.TienCK=rs.getDouble("chietkhau");
						double tienvat =rs.getDouble("tienvat");
						double tiensauvat=rs.getDouble("tienavat");
						double tientruocvat=tiensauvat-tienvat;
						 
						// khuc nay chua dung toi
						if(tienvat!=0){ 
							phantramthue=  (tienvat *100)/tientruocvat ;
						} 
						 thanhtiendh=rs.getDouble("sotienbvat");
						if(this.TienCK>0){
							phantramck=this.TienCK /rs.getDouble("sotienbvat")*100;
						}
						this.VAT=phantramthue;
						
						//--------------
						
						this.TongTienTruocVAT= rs.getDouble("SOTIENBVAT");
						this.TienSauCK= rs.getDouble("SOTIENBVAT"); // = tien truoc vat do chua dung ck
						this.TongTienSauVAT= rs.getDouble("TIENAVAT");
						this.TienVat= rs.getDouble("TIENVAT");
						this.noidungCK=rs.getString("noidungchietkhau");
					}
 
					
					if(this.GhiChu.trim().length() >0){
						dong--;
						System.out.println("ghi chu nek dong : "+dong);
					}
 
				}catch(Exception er){
					er.printStackTrace();
					
				}
  
 
				 
				
				/*query= "  SELECT DATA.SANPHAM_FK,DATA.MA,DATA.TENSP , DATA.DONGIA ,     \n    " +  
					   "         DATA.DONVI  ,SUM(DATA.SOLUONG) AS SOLUONG ,  DATA.dvtId       FROM  \n     " +  
					   "         (    \n " +  
					   "         SELECT    DDH_SP.SANPHAM_FK,SP.MA,SP.TEN AS TENSP, DDH_SP.DONGIA AS DONGIA ,       \n " +  
					   "         '' AS SCHEME,  DDH_SP.SOLUONG - (       \n  " +  
					   "                                  SELECT ISNULL(SUM(SOLUONG),0) FROM ERP_HOADON_SP A           \n " +  
					   "                                  INNER JOIN ERP_HOADON B ON B.PK_SEQ=A.HOADON_FK \n " +  
					   "                                  INNER JOIN ERP_HOADON_DDH hd_dh on hd_dh.HOADON_FK= B.PK_SEQ        \n  " +  
					   "                                  WHERE hd_dh.DDH_FK =DDH.PK_SEQ AND A.SANPHAM_FK=DDH_SP.SANPHAM_FK AND B.TRANGTHAI < '2' AND B.LOAIHOADON =6   \n " +  
					   "                                  AND  B.PK_SEQ <>  "+(this.Id.length() >0? this.Id:"0" )+"      \n  " +  
					   "         ) AS SOLUONG     \n   " +  
					   "         ,ISNULL(DVDL.DONVI,'') AS DONVI , DVDL.PK_SEQ as dvtId      \n  " +  
					   "  FROM ERP_MUAHANG_SP DDH_SP        \n   " +  
					   "       INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK         \n  " +  
					   "       INNER JOIN ERP_MUAHANG DDH  ON DDH.PK_SEQ=DDH_SP.MUAHANG_FK        \n  " +   
					   "       LEFT JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK=DVDL.PK_SEQ        \n     " +  
					   "  WHERE  DDH.NHACUNGCAP_FK=   "+this.NppId+"  AND  DDH_SP.SOLUONG >0   AND MUAHANG_FK IN ("+chuoi+")     \n  " +  
					   "         AND  DDH_SP.SOLUONG - (       \n  " +  
					   "              SELECT ISNULL(SUM(SOLUONG),0) FROM ERP_HOADON_SP A          \n  " +  
					   "              INNER JOIN ERP_HOADON B ON B.PK_SEQ=A.HOADON_FK  \n " +  
					   "              inner join ERP_HOADON_DDH hd_dh on hd_dh.HOADON_FK= B.PK_SEQ         \n " +  
					   "              WHERE hd_dh.DDH_FK =DDH.PK_SEQ AND A.SANPHAM_FK=DDH_SP.SANPHAM_FK AND B.TRANGTHAI < '2' AND B.LOAIHOADON =6   \n " +  
					   "              AND  B.PK_SEQ <>  "+(this.Id.length() >0? this.Id:"0" )+"       \n " +  
					   "          )    > 0  \n   " +  
					   "  ) AS DATA   \n   " +  
					   "  GROUP BY   DATA.SANPHAM_FK,DATA.MA,DATA.TENSP , DATA.DONGIA, DATA.DONVI  ,  DATA.dvtId       \n " +  
					   "  ORDER BY  SUM( SOLUONG * DATA.DONGIA ) DESC \n ";*/
				
				
				
				
				query= "  SELECT   DATA.MUAHANG_FK,DATA.SANPHAM_FK,DATA.MA,DATA.TENSP , DATA.DONGIA ,    DATA.HANSUDUNG,     \n    " +  
						   "         DATA.DONVI  ,SUM(DATA.SOLUONG) AS SOLUONG ,  DATA.dvtId , CAST( SUM(DATA.THANHTIENVIET) AS DECIMAL) AS THANHTIEN  , DATA.VAT  , DATA.THUEXUAT   , DATA.TIENAVAT      FROM  \n     " +  
						   "         (    \n " +  
						   "         SELECT  DDH_SP.MUAHANG_FK,ISNULL(SP.HANSUDUNG,0) AS HANSUDUNG,    DDH_SP.SANPHAM_FK,SP.MA,SP.TEN AS TENSP,  DDH_SP.DONGIAVIET AS DONGIA ,  DDH_SP.THANHTIENVIET,   DDH_SP.VAT  , DDH_SP.THUEXUAT,  (DDH_SP.THANHTIENVIET+ DDH_SP.VAT ) AS TIENAVAT,        \n " +  
						   "         '' AS SCHEME,  DDH_SP.SOLUONG - (       \n  " +  
						   "                                  SELECT ISNULL(SUM(SOLUONG),0) FROM ERP_HOADON_SP A           \n " +  
						   "                                  INNER JOIN ERP_HOADON B ON B.PK_SEQ=A.HOADON_FK \n " +  
						   "                                  INNER JOIN ERP_HOADON_DDH hd_dh on hd_dh.HOADON_FK= B.PK_SEQ        \n  " +  
						   "                                  WHERE hd_dh.DDH_FK =DDH.PK_SEQ AND A.SANPHAM_FK=DDH_SP.SANPHAM_FK AND B.TRANGTHAI < '2' AND B.LOAIHOADON =6   \n " +  
						   "                                  AND  B.PK_SEQ <>  "+(this.Id.length() >0? this.Id:"0" )+"      \n  " +  
						   "         ) AS SOLUONG     \n   " +  
						   "         ,ISNULL(DVDL.DONVI,'') AS DONVI , DVDL.PK_SEQ as dvtId      \n  " +  
						   "  FROM ERP_MUAHANG_SP DDH_SP        \n   " +  
						   "       INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK         \n  " +  
						   "       INNER JOIN ERP_MUAHANG DDH  ON DDH.PK_SEQ=DDH_SP.MUAHANG_FK        \n  " +   
						   "       LEFT JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK=DVDL.PK_SEQ        \n     " +  
						   "  WHERE  DDH.NHACUNGCAP_FK=   "+this.NppId+"  AND  DDH_SP.SOLUONG >0   AND MUAHANG_FK IN ("+chuoi+")     \n  " +  
						   "         AND  DDH_SP.SOLUONG - (       \n  " +  
						   "              SELECT ISNULL(SUM(SOLUONG),0) FROM ERP_HOADON_SP A          \n  " +  
						   "              INNER JOIN ERP_HOADON B ON B.PK_SEQ=A.HOADON_FK  \n " +  
						   "              inner join ERP_HOADON_DDH hd_dh on hd_dh.HOADON_FK= B.PK_SEQ         \n " +  
						   "              WHERE hd_dh.DDH_FK =DDH.PK_SEQ AND A.SANPHAM_FK=DDH_SP.SANPHAM_FK AND B.TRANGTHAI < '2' AND B.LOAIHOADON =6   \n " +  
						   "              AND  B.PK_SEQ <>  "+(this.Id.length() >0? this.Id:"0" )+"       \n " +  
						   "          )    > 0  \n   " +  
						   "  ) AS DATA   \n   " +  
						   "  GROUP BY   DATA.MUAHANG_FK,DATA.SANPHAM_FK,DATA.MA,DATA.TENSP , DATA.DONGIA, DATA.DONVI  ,  DATA.dvtId  ,  DATA.THANHTIENVIET  , DATA.VAT  , DATA.THUEXUAT   , DATA.TIENAVAT , DATA.HANSUDUNG    \n " +  
						   "  ORDER BY  SUM( SOLUONG * DATA.DONGIA ) DESC \n ";
				
				
				
				
		 
			    System.out.println("load du lieu tao moi  : " +query);
				rs=this.db.get(query);
				this.listsanpham.clear();
				this.TongTienChuaCK=0;
				String chuoisp="0";
					try{
						while(rs.next()){
							IErpHoaDon_SP sanpham= new ErpHoanDon_SP();
							sanpham.setId(this.Id);
							chuoisp=chuoisp+","+rs.getString("SANPHAM_FK");
							
							sanpham.setDvtId(rs.getString("dvtId"));
							
							sanpham.setIdSanPham(rs.getString("SANPHAM_FK"));
							sanpham.setMaSanPham(rs.getString("ma"));
							sanpham.setTenSanPham(rs.getString("tensp"));
							sanpham.setSoLuong(rs.getDouble("soluong"));
							NumberFormat formatter = new DecimalFormat("#######.####");
							double giasauck=  Double.parseDouble(formatter.format(rs.getDouble("dongia")));
							sanpham.setDonGia(giasauck);							
							sanpham.setDonViTinh(rs.getString("DONVI"));
							
							/*sanpham.setThanhTien(Math.round(giasauck*rs.getDouble("soluong")));*/
							sanpham.setThanhTien(rs.getDouble("THANHTIEN"));
							sanpham.setTienvat(rs.getDouble("VAT"));
							sanpham.setVAT(rs.getDouble("THUEXUAT"));
							sanpham.setThanhtienavat(rs.getDouble("TIENAVAT"));
							sanpham.setHansudung(rs.getString("HANSUDUNG"));
							
							System.out.println("tien bvat 1:  "+ sanpham.getThanhTien());
							
							// lay danh sach lo
							
							List<ISpDetail> spSoloList= new ArrayList<ISpDetail>();
							//String qr= "select hoadon_fk, donhang_fk, MA,TEN, DONVI,  SOLO, NGAYHETHAN,NGAYSANXUAT,DONGIA, SOLUONG from ERP_HOADON_SP_CHITIET where hoadon_fk ="+(this.Id.length() >0? this.Id:"0" )+"     and MA='"+ sanpham.getMaSanPham()+"'";
							String qr= "select   SOLO, NGAYHETHAN,NGAYNHAPKHO,ISNULL(MAME,'') AS MAME,ISNULL(MATHUNG,'') AS MATHUNG,ISNULL(MAPHIEU,'') AS MAPHIEU,ISNULL(BIN_FK,0) AS BIN_FK, KHOTT_SP_CT_FK, SUM(SOLUONG) AS SOLUONG,isnull(hamluong,'100') as hamluong,isnull(hamam,'0') as hamam from ERP_MUAHANG_SP_CHITIET where MUAHANG_fk  IN ("+chuoi+")    and SANPHAM_FK="+ sanpham.getIdSanPham()+" \n"
									+ " GROUP BY  SOLO, NGAYHETHAN,NGAYNHAPKHO,ISNULL(MAME,'') ,ISNULL(MATHUNG,''),ISNULL(MAPHIEU,''),ISNULL(BIN_FK,0) , KHOTT_SP_CT_FK,isnull(hamluong,'100'),isnull(hamam,'0')";
							System.out.println(" Chi tiet Lo: "+ qr);
							ResultSet rsSolo= db.get(qr);
							if(rsSolo!= null){
								while(rsSolo.next()){
									ISpDetail spdt = new SpDetail();
									spdt.setSolo(rsSolo.getString("SOLO"));
									spdt.setSoluong(rsSolo.getString("SOLUONG"));
									spdt.setNgayHethan(rsSolo.getString("NGAYHETHAN"));
									spdt.setNgaySx("");
									spdt.setKhott_fk(rsSolo.getString("KHOTT_SP_CT_FK"));
									spdt.setMame(rsSolo.getString("MAME"));
									spdt.setMathung(rsSolo.getString("MATHUNG"));
									spdt.setMaphieu(rsSolo.getString("MAPHIEU"));
									spdt.setNgaynhapkho(rsSolo.getString("NGAYNHAPKHO"));
									spdt.setkhuid(rsSolo.getString("BIN_FK"));
									spdt.setHamam(rsSolo.getString("hamam"));
									spdt.setHamluong(rsSolo.getString("hamluong"));
									spSoloList.add(spdt);
								}
								rsSolo.close();
							}
							sanpham.setSpDetail(spSoloList);
							
							
							
							this.listsanpham.add(sanpham);
							//this.TongTienChuaCK=this.TongTienChuaCK+sanpham.getThanhTien();
							
							
							
							}
						}catch(Exception er){
							er.printStackTrace();
						}
					
					
					/*double phantramds=this.TongTienChuaCK*100/thanhtiendh;
					this.TienCK= Math.round(this.TongTienChuaCK*phantramck/100);
					this.TienSauCK= this.TongTienChuaCK-this.TienCK;
					this.TienVat=Math.round(this.TienSauCK * phantramthue /100);
					this.TongTienSauVAT=this.TienSauCK+this.TienVat;*/
		 
				}
		}
	}
	}
	
	public ResultSet getrsddhChuaXuathd() {
		
		return this.rsddhChuaXuatHD;
	}
	
	public void SetNppId(String nppid) {
		
		this.NppId=nppid;
	}
	
	public String getNppId() {
		
		return this.NppId;
	}
	
	public List<IErpHoaDon_SP> GetListSanPham() {
		
		return this.listsanpham;
	}

	public void setListsanpham(List<IErpHoaDon_SP> listsanpham) {
		this.listsanpham = listsanpham;
	}
	
	public void SetNguoiMuaHang(String nguoimuahang) {
		
		this.NguoiMuaHang=nguoimuahang;
	}
	
	public String getNguoiMuaHang() {
		
		return this.NguoiMuaHang;
	}
	
	public boolean Save() {
		
		String sql="";
		
		try
		{
			
			
			
			if(checkSoHoaDon()){
				this.setMessage("Số hóa đơn : "+this.SoHoaDon+" đã có, vui lòng chọn lại.");
				return false;
			}
			
			if(this.listsanpham==null){
				this.setMessage("Sản phẩm xuất hóa đơn chưa có ,vui lòng chọn lại");
				return false;
			}
			if(this.listsanpham.size()==0){
				this.setMessage("Sản phẩm xuất hóa đơn chưa có ,vui lòng chọn lại");
				return false;
			}
			
			try{
				Double.parseDouble(this.SoHoaDon);
			}catch(Exception er){
				this.Msg="Số hóa đơn không hợp lệ, chỉ được nhập số,và không được bỏ trống";
				return false;
			}
			
			if(this.NguoiTao==null || this.NguoiTao.equals("")){
				this.Msg="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			}
			
			if(this.NppId==null || this.NppId.equals("")){
				this.Msg="Nhà phân phối không được rỗng";
				return false;
			}
			
			if(this.ctyId==null || this.ctyId.equals("")){
				this.Msg="Thông tin công ty không được rỗng";
				return false;
			}
 		 
			db.getConnection().setAutoCommit(false);
			
			this.VAT=10; //SET CỨNG VAT = 10%
		 
			//CreateRs(false);	
			
			if(this.TienSauCK <0){
				this.Msg="Vui Long Kiem Tra Lai,Tong Gia Tri Don Hang Khong Duoc Nho Hon 0";
				return false;
			}
			 	
			if(this.listsanpham.size()<=0){
				this.Msg="Khong The Tao Hoa Don Nay,Khong Co San Pham Trong Hoa Don";
				return false;
			}
			
			sql=	" INSERT INTO ERP_HOADON (CONGTY_FK, TENKHXHD, DIACHIKHXHD, MASOTHUEKHXHD ,NGAYXUATHD,TRANGTHAI,NGAYTAO,NGAYSUA,KYHIEU,SOHOADON,HINHTHUCTT,NGUOITAO,NGUOISUA,NCC_FK,NGUOIMUA,PO_MT,chietkhau,tongtienbvat,vat,tongtienavat,ghichu,noidungchietkhau,loaihoadon) "+
					" VALUES ( "+ this.ctyId +",N'"+this.TenKhXhd+"',N'"+this.DiachiKhXhd+"',N'"+this.MasothueXhd+"','"+this.NgayXuaHd+"','0','"
					+this.NgayTao+"','"+this.NgaySua+"','"+this.Kyhieu+"','"+this.SoHoaDon+"',N'"+this.HinhThucTT+"','"
					+this.NguoiTao+"','"+this.NguoiSua+"','"+this.NppId+"',N'"+this.NguoiMuaHang+"','"+this.POMT+"','"
					+this.TienCK+"','"+this.TongTienTruocVAT+"','"+this.TienVat+"','"+this.TongTienSauVAT+"',N'"
					+this.GhiChu+"',N'"+this.noidungCK+"',"+this.Loaihoadon+")";
		 
			System.out.println(sql);
		 
			if(!db.update(sql)){
				db.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			String query = "select SCOPE_IDENTITY() as dhId";
			
			String hdId = "";
			query = "select SCOPE_IDENTITY() as hdId";
			
			try{
				ResultSet rs1 = db.get(query);
				rs1.next();
				hdId = rs1.getString("hdId");
				rs1.close();
			}
			catch(Exception er){
				db.update("rollback");
				er.printStackTrace();
				return false;
			}
			
			 if(Scheme_chietkhau!=null){
				for(int i=0;i<this.Scheme_chietkhau.length;i++){
					if(this.Scheme_chietkhau[i].length() >0 ){
						sql="INSERT INTO   ERP_HOADON_GHICHU(HOADON_FK,GHICHU,SOTIEN)  " +
						" values ('"+this.Id+"',N'"+Scheme_chietkhau[i]+"',"+Scheme_ck_thanhtien[i]+") ";
						if(!db.update(sql)){
							db.update("rollback");
							this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
							return false;
						}	
					}
				}
			 }
			
			int i=0;
			String ddhIds = "";
			if(this.DonDatHang!=null){
				while(i<this.DonDatHang.length){
						// check ngay hoa don
						String ngayhoadoni="";
						sql=" SELECT NGAYMUA FROM ERP_MUAHANG WHERE PK_SEQ='"+DonDatHang[i]+"' ";
						ResultSet rs= db.get(sql);
						if(rs.next()){
							ngayhoadoni= rs.getString("NGAYMUA");
						}
						rs.close();
						System.out.println(" ngay don hang");
						if(this.NgayXuaHd.compareTo(ngayhoadoni)<0){
							db.update("rollback");
							this.Msg="Vui lòng chọn ngày lớn hơn hoặc bằng ngày đơn hàng!";
							return false;
						}
					
			
						sql="insert into ERP_HOADON_DDH (HOADON_FK,DDH_FK )  " +" values('"+hdId+"','"+DonDatHang[i]+"') ";
						System.out.println( sql);
						if(!db.update(sql)){
							db.update("rollback");
							this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
							return false;
						}	
						i=i+1;
				}
			}
  
	      	int count = 0;
			while(count < this.listsanpham.size())
			{
				IErpHoaDon_SP sanpham = new ErpHoanDon_SP();
				sanpham =listsanpham.get(count);
				
				sanpham.setId(this.Id);
				/*double thanhtien=sanpham.getSoLuong()* sanpham.getDonGia();
				double thanhtiensauck=sanpham.getSoLuong()* sanpham.getDonGia()-sanpham.getChietKhau();
				
				sql="insert into erp_hoadon_sp (SANPHAM_FK,HOADON_FK,SOLUONG,DONGIA,CHIETKHAU,TIENBVAT,VAT,TIENAVAT,DONVITINH,SCHEME, DVDL_FK ) values (" 
					+sanpham.getIdSanPham()+","+hdId+","+sanpham.getSoLuong()+","+ sanpham.getDonGia() +",'"+sanpham.getChietKhau()+"','"+thanhtien+"','"
					+(thanhtiensauck*this.VAT/100 )+"','"+(thanhtiensauck+(thanhtiensauck*this.VAT/100 ) )+"',N'"+sanpham.getDonViTinh()+ "',N'"
					+(sanpham.getCTKMId()== null ? "":sanpham.getCTKMId()) +"', "+sanpham.getDvtId()+" )";*/
				
				
				double tienbvat=Math.round(sanpham.getThanhTien());
				System.out.println(" tienbvat "+sanpham.getThanhTien() );
				double vat=Math.round(sanpham.getVAT());
				double tienvat=Math.round(sanpham.getTienvat());
				double tienavat=Math.round(sanpham.getThanhtienavat());
				
				
				sql="insert into erp_hoadon_sp (SANPHAM_FK,HOADON_FK,SOLUONG,DONGIA,CHIETKHAU,TIENBVAT,THUESUAT, VAT,TIENAVAT,DONVITINH,SCHEME, DVDL_FK) values (" 
				    	+sanpham.getIdSanPham()+","+hdId+","+sanpham.getSoLuong()+","+ sanpham.getDonGia() +",'"
						+sanpham.getChietKhau()+"','"+tienbvat+"','"+vat+"','" +tienvat +"','"+
						+tienavat+"',N'"+sanpham.getDonViTinh()+ "',N'"
						+(sanpham.getCTKMId()== null ? "":sanpham.getCTKMId()) +"', "+sanpham.getDvtId()+" )";
	
				System.out.println(" insert into erp_hoadon_sp : " + sql);
				if(!db.update(sql)){
					 
					this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql;
					db.update("rollback");
					return false;
				}
				
				// insert bang chi tiet
				
				List<ISpDetail> spDetail= sanpham.getSpDetail();
				if(spDetail!= null){
					int size = spDetail.size();
					
					// kiem tra bang so luong tong
					int k=0;
					double sumsoluong=0;
					while(k<size){
						ISpDetail spSolo=spDetail.get(k);
						String solo=spSolo.getSolo();
						String NGAYHETHAN= spSolo.getNgayHethan();
						String NGAYSANXUAT= spSolo.getNgaySx();
						String SOLUONG= spSolo.getSoluong();
						if(solo.trim().length()>0 &&  SOLUONG.trim().length()>0 &&  NGAYHETHAN.trim().length()>0  )
						{
							sumsoluong+= Double.parseDouble(SOLUONG.replaceAll(",", "")) ;
						}
						
						k++;
					}
					if(DinhDang.dinhdangso( sumsoluong) != DinhDang.dinhdangso(sanpham.getSoLuong())){
						this.Msg="Tổng số lượng lô khác số lượng tổng, vui lòng kiểm tra lại : " + sanpham.getMaSanPham() +" - "+ sanpham.getTenSanPham() + "so luong "+DinhDang.dinhdangso( sumsoluong) +"Tong "+ DinhDang.dinhdangso(sanpham.getSoLuong());
						return false;
					}
					
					
					
					
					int m = 0;
					while(m<size){
						ISpDetail spSolo=spDetail.get(m);
						String hoadon_fk= hdId;
						String donhang_fk= "";
						String MA= sanpham.getMaSanPham();
						String TEN= sanpham.getTenSanPham();
						String solo=spSolo.getSolo();
						String NGAYHETHAN= spSolo.getNgayHethan();
						String NGAYSANXUAT= spSolo.getNgaySx();
						double DONGIA=sanpham.getDonGia();
						String SOLUONG= spSolo.getSoluong();
						String DONVI=sanpham.getDvtId();
						
						
						sql=   	  "	   INSERT INTO  ERP_HOADON_SP_CHITIET  \n" + 
								  "    (hoadon_fk,  MA,TEN, DONVI,  SOLO, NGAYHETHAN,NGAYNHAPKHO,DONGIA, SOLUONG, MAME, MATHUNG, MAPHIEU, BIN_FK,KHO_FK,HAMLUONG,HAMAM) \n" + 
								  "    VALUES( '"+ hoadon_fk +"','"+ MA +"',N'"+ TEN+"','"+ DONVI +"','"+ solo +"','"+ NGAYHETHAN+"','"+ spSolo.getNgaynhapkho()+"','"+ DONGIA+"','"+ SOLUONG+"','"+spSolo.getMame()+"','"+spSolo.getMathung()+"','"+spSolo.getMaphieu()+"',"+spSolo.getkhuid()+","+spSolo.getKhott_fk()+",'"+spSolo.getHamluong()+"','"+spSolo.getHamam()+"')";
						System.out.println(" INSERT SOLO :"+sql);
						if(!db.update(sql)){
							 
							this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql;
							db.update("rollback");
							return false;
						}
						m++;
					}
				}
				
				count++;
			
			}
	 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er){
			db.update("rollback");
			this.Msg=er.getMessage();
			er.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean checkSoHoaDon() {//LOAIHOADON = 0: HÓA ĐƠN HÀNG BÁN; LOAIHOADON = 1: HÓA ĐƠN HÀNG KHUYẾN MÃI; LOAIHOADON = 2: HÓA ĐƠN TRẢ VỀ NCC
		
		try{
			String sql=" select sohoadon  " +
					   " from erp_hoadon " +
					   " where sohoadon='"+this.SoHoaDon+"'  and  trangthai NOT IN ('2')  and  LOAIHOADON = 2";
			if(this.Id.length() > 0){
				sql=sql+" and pk_seq <>"+this.Id;
			}
			ResultSet rs=db.get(sql);
			if(rs.next()){
				return true;
			}
			rs.close();
		
		}catch(Exception er){
			return false;
		}
		return false;
	}
	
	public boolean Edit() {
		
		String sql="";
		
		try
		{
			if(checkSoHoaDon()){
				this.setMessage("Số hóa đơn : "+this.SoHoaDon+" đã có, vui lòng chọn lại.");
				return false;
			}
			
			if(this.listsanpham==null){
			
				this.setMessage("Sản phẩm xuất hóa đơn chưa có ,vui lòng chọn lại");
			
				return false;
			}
			if(this.listsanpham.size()==0){
				
				this.setMessage("Sản phẩm xuất hóa đơn chưa có ,vui lòng chọn lại");
			
				return false;
			}
	 
			if(this.NguoiTao==null || this.NguoiTao.equals("")){
				this.Msg="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			}
			
			if(this.NppId==null || this.NppId.equals("")){
				this.Msg="Nha Phan Phoi Khong Duoc Rong";
				return false;
			}
			
			if(this.ctyId==null || this.ctyId.equals("")){
				this.Msg="Thông tin công ty không được rỗng";
				return false;
			}
			
/*			if(this.Loaihoadon.trim().length() <= 0)
			{
				this.Msg = "Vui lòng chọn loại hóa đơn";
				return false;
			}*/
			
			try{
				Double.parseDouble(this.SoHoaDon);
			}catch(Exception er){
				this.Msg="So Hoa Don Khong Hop Le";
				return false;
			}
			
			  db.getConnection().setAutoCommit(false);
			  
			
			  //CreateRs(false);	
			  
			  
			 	if(this.listsanpham.size()<=0){
			 		this.Msg="Khong The Tao Hoa Don Nay,Khong Co San Pham Trong Hoa Don";
			 		return false;
			 	}		  			 
				 sql=" UPDATE ERP_HOADON  SET TENKHXHD=N'"+this.TenKhXhd+"',DIACHIKHXHD=N'"+this.DiachiKhXhd+"',MASOTHUEKHXHD=N'"+this.MasothueXhd+"', ghichu=N'"+this.GhiChu+"',noidungchietkhau=N'"+this.noidungCK+"' ,PO_MT='"+this.POMT+"',NGUOIMUA=N'"+this.NguoiMuaHang+"', NGAYXUATHD ='"+this.NgayXuaHd+"',NGAYSUA='"+this.NgaySua+"',KYHIEU ='"+this.Kyhieu+"'," +
				 		"SOHOADON='"+this.SoHoaDon+"',HINHTHUCTT=N'"+this.HinhThucTT+"',NGUOISUA='"+this.NguoiSua+"',NCC_FK="+this.NppId+",chietkhau='"+this.TienCK+"',tongtienbvat='"+this.TongTienTruocVAT+"',vat='"+this.TienVat+"',tongtienavat='"+this.TongTienSauVAT+"' where pk_seq="+this.Id;
				 if(!db.update(sql)){
					db.update("rollback");
					this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
					return false;
				 }	
		 
				sql="delete ERP_HOADON_DDH where hoadon_fk="+this.Id;
				
				if(!db.update(sql)){
					db.update("rollback");
					this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
					return false;
				}	
				
					
				
				 	sql="delete erp_hoadon_sp where hoadon_fk="+this.Id;
					
					if(!db.update(sql)){
						db.update("rollback");
						this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
						return false;
					}
					
					sql="delete ERP_HOADON_SP_CHITIET where hoadon_fk="+this.Id;
					
					if(!db.update(sql)){
						db.update("rollback");
						this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
						return false;
					}	
	 
				
				  
				
				int i=0;
				String ddhIds = "";
				if(this.DonDatHang!=null){
					while(i<this.DonDatHang.length){
						
						
						// check ngay hoa don
						String ngayhoadoni="";
						sql=" SELECT NGAYMUA FROM ERP_MUAHANG WHERE PK_SEQ='"+DonDatHang[i]+"' ";
						ResultSet rs= db.get(sql);
						if(rs.next()){
							ngayhoadoni= rs.getString("NGAYMUA");
						}
						rs.close();
						
						if(this.NgayXuaHd.compareTo(ngayhoadoni)<0){
							db.update("rollback");
							this.Msg="Vui lòng chọn ngày lớn hơn hoặc bằng ngày đơn hàng!";
							return false;
						}
						 
							sql="insert into ERP_HOADON_DDH (HOADON_FK,DDH_FK )  " +
									" values('"+this.Id+"','"+DonDatHang[i]+"') ";
						 
						if(!db.update(sql)){
							db.update("rollback");
							this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
							return false;
						}	
						i=i+1;
					}
				}
	 
			
 
				
		      	int count = 0;
				while(count < this.listsanpham.size())
				{
					IErpHoaDon_SP sanpham = new ErpHoanDon_SP();
					sanpham =listsanpham.get(count);
					
					sanpham.setId(this.Id);
					
					/*double thanhtien=sanpham.getSoLuong()* sanpham.getDonGia();
					double thanhtiensauck=sanpham.getSoLuong()* sanpham.getDonGia()-sanpham.getChietKhau();*/
					
					double tienbvat=Math.round(sanpham.getThanhTien());
					System.out.println(" tienbvat "+sanpham.getThanhTien() );
					double vat=Math.round(sanpham.getVAT());
					double tienvat=Math.round(sanpham.getTienvat());
					double tienavat=Math.round(sanpham.getThanhtienavat());
					
					
					sql="insert into erp_hoadon_sp (SANPHAM_FK,HOADON_FK,SOLUONG,DONGIA,CHIETKHAU,TIENBVAT, THUESUAT, VAT,TIENAVAT,DONVITINH,SCHEME, DVDL_FK) values (" 
					    	+sanpham.getIdSanPham()+","+this.Id+","+sanpham.getSoLuong()+","+ sanpham.getDonGia() +",'"
							+sanpham.getChietKhau()+"','"+tienbvat+"','"+vat+"','" +tienvat +"','"+
							+tienavat+"',N'"+sanpham.getDonViTinh()+ "',N'"
							+(sanpham.getCTKMId()== null ? "":sanpham.getCTKMId()) +"', "+sanpham.getDvtId()+" )";
					
					System.out.println(" insert into erp_hoadon_sp : " + sql);
					if(!db.update(sql)){
						 
						this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql;
						db.update("rollback");
						return false;
					}
					
					
					List<ISpDetail> spDetail= sanpham.getSpDetail();
					
					if(spDetail!= null){
						System.out.println("spDetail ko null: "+ spDetail.size());
						int size = spDetail.size();
						
						
						// kiem tra bang so luong tong
						int k=0;
						double sumsoluong=0;
						while(k<size){
							ISpDetail spSolo=spDetail.get(k);
							String solo=spSolo.getSolo();
							String NGAYHETHAN= spSolo.getNgayHethan();
							String NGAYSANXUAT= spSolo.getNgaySx();
							String SOLUONG= spSolo.getSoluong();
							if(solo.trim().length()>0 &&  SOLUONG.trim().length()>0 &&  NGAYHETHAN.trim().length()>0  )
							{
								sumsoluong+= Double.parseDouble(SOLUONG.replaceAll(",", "")) ;
							}
							
							k++;
						}
						if(DinhDang.dinhdangso(sumsoluong) != DinhDang.dinhdangso(sanpham.getSoLuong())){
							this.Msg="Tổng số lượng lô khác số lượng tổng, vui lòng kiểm tra lại : " + sanpham.getMaSanPham() +" - "+ sanpham.getTenSanPham();
							return false;
						}
						
						
						
						int m = 0;
						while(m<size){
							ISpDetail spSolo=spDetail.get(m);
							String hoadon_fk= this.Id;
							String donhang_fk= "";
							String MA= sanpham.getMaSanPham();
							String TEN= sanpham.getTenSanPham();
							String solo=spSolo.getSolo();
							String NGAYHETHAN= spSolo.getNgayHethan();
							String NGAYSANXUAT= spSolo.getNgaySx();
							double DONGIA=sanpham.getDonGia();
							String SOLUONG= spSolo.getSoluong();
							String DONVI=sanpham.getDvtId();
								
							
							sql=   	  "	   INSERT INTO  ERP_HOADON_SP_CHITIET  \n" + 
									  "    (hoadon_fk,  MA,TEN, DONVI,  SOLO, NGAYHETHAN,NGAYNHAPKHO,DONGIA, SOLUONG, MAME, MATHUNG, MAPHIEU, BIN_FK,KHO_FK,HAMLUONG,HAMAM) \n" + 
									  "    VALUES( '"+ hoadon_fk +"','"+ MA +"',N'"+ TEN+"','"+ DONVI +"','"+ solo +"','"+ NGAYHETHAN+"','"+ spSolo.getNgaynhapkho()+"','"+ DONGIA+"','"+ SOLUONG+"','"+spSolo.getMame()+"','"+spSolo.getMathung()+"','"+spSolo.getMaphieu()+"',"+spSolo.getkhuid()+","+spSolo.getKhott_fk()+",'"+spSolo.getHamluong()+"','"+spSolo.getHamam()+"')";
							System.out.println(" INSERT SOLO :"+sql);
							if(!db.update(sql)){
								 
								this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql;
								db.update("rollback");
								return false;
							}
							m++;
						}
					}
					
					
					 
					count++;
				
				}
				 
		
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}
		catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
			return false;
		}
		return true;
	}
	
	public double getTienSauCK() {
		
		return this.TienSauCK;
	}
	
	public void getTienSauCK(double tiensauCK) {
		
		this.TienSauCK=tiensauCK;
	}
	
	public double getTienVat() {
		
		return this.TienVat;
	}
	
	public void setTienVat(double tienvat) {
		
		this.TienVat=tienvat;
	}
	
	public void initdisplay(String Id) {
		try{
			String query=	" SELECT isnull(TENKHXHD,'') as TENKHXHD ,isnull(DIACHIKHXHD,'') as DIACHIKHXHD  , " +
							" isnull(MASOTHUEKHXHD,'') as  MASOTHUEKHXHD , HD.PK_SEQ as sochungtu, isnull(loaihoadon,'0') as loaihoadon , " +
							" GHICHU,NOIDUNGCHIETKHAU,HD.PK_SEQ,HD.NGAYXUATHD,HD.PO_MT,HD.NGUOIMUA,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT , " +
							" NPP.TEN AS NPP,HD.NCC_FK, NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA,CHIETKHAU,VAT,TONGTIENAVAT,TONGTIENBVAT,HD.HOANTAT "+
							" FROM ERP_HOADON HD INNER JOIN erp_nhacungcap NPP ON NPP.PK_SEQ=HD.NCC_FK "+
							" INNER JOIN NHANVIEN NT ON NT.PK_SEQ=HD.NGUOITAO "+
							" INNER JOIN NHANVIEN NS ON NS.PK_SEQ=HD.NGUOISUA WHERE HD.PK_SEQ="+ Id+ "";
			System.out.println("[ErpHoaDon.initdisplay] Get Noi Dung :"+query);
			db=new dbutils();
			ResultSet rs = db.get(query);
			if(rs!=null){
				
				while (rs.next()) {
					this.Id=Id;
					this.TenKhXhd = rs.getString("TENKHXHD");
					this.DiachiKhXhd = rs.getString("DIACHIKHXHD");
					this.MasothueXhd = rs.getString("MASOTHUEKHXHD");
					
					this.NgaySua=rs.getString("ngaysua");
					this.NgayTao=rs.getString("ngaytao");
					this.NgayXuaHd=rs.getString("NGAYXUATHD");
					this.NppId=rs.getString("NCC_FK");
					this.NppTen=rs.getString("npp");
					this.SoHoaDon=rs.getString("sohoadon");
					this.TrangThai=rs.getString("trangthai");
					this.Kyhieu=rs.getString("kyhieu");
					this.HinhThucTT=rs.getString("HINHTHUCTT");
					this.NguoiMuaHang=rs.getString("NGUOIMUA");
					this.POMT=rs.getString("PO_MT");
					this.TongTienChuaCK=rs.getDouble("tongtienbvat");
					this.TienCK=rs.getDouble("chietkhau");
					this.TienVat=rs.getDouble("vat");
					this.TongTienTruocVAT= rs.getDouble("tongtienbvat");
					this.TongTienSauVAT=rs.getDouble("tongtienavat");
					this.TienSauCK=this.TongTienChuaCK-this.TienCK;
					this.Msg="";	
					this.GhiChu=rs.getString("ghichu");
					this.noidungCK=rs.getString("noidungchietkhau");
					this.HoanTat=rs.getString("HoanTat");
					this.Loaihoadon=rs.getString("loaihoadon");
					this.sochungtu =  rs.getString("sochungtu");
				}
				rs.close();
				 
					query="SELECT HOADON_FK,DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK="+this.Id;
			 
				rs=this.db.get(query);
				if(rs!=null){
					this.DonDatHang=new String[20];
					int i= 0;
					while(rs.next()){
					
						this.DonDatHang[i] =rs.getString("DDH_FK");
							i=i+1;
					}
					
					
				}else{
					this.setMessage("Khong Lay Duoc Gia tri Don hang");
					return;
				}
					
					 
					  query="	select ddh.pk_seq as id,ddh.ngaymua as ngaydat from erp_muahang " +
					  		" ddh where ddh.pk_seq in (select ddh_fk from erp_hoadon_ddh where hoadon_fk='"+Id+"' )";
					 
						//System.out.println(sql);
					this.rsddhChuaXuatHD=db.get(query);
				
				query=" SELECT DDH_SP.SANPHAM_FK,SP.MA,SP.TEN as tensp,DONGIA,SOLUONG AS SOLUONG,  isnull (SP.HANSUDUNG ,0) as HANSUDUNG ,"+ 
				  	" ISNULL(QUYCACH.SOLUONG1,'1') AS QUYDOI, dvdl.DONVI AS DONVI FROM ERP_HOADON_SP DDH_SP  "+ 
				  	" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK  " +
				  	" left join donvidoluong dvdl on dvdl.pk_seq=sp.dvdl_fk "+ 
				  	" LEFT JOIN QUYCACH ON SP.PK_SEQ=QUYCACH.SANPHAM_FK  "+ 
				  	" WHERE DDH_SP.SOLUONG >0 AND HOADON_FK="+this.Id +"order by (soluong* dongia) desc"; 
				
			System.out.println("Get San Pham :"+query);
				 rs=this.db.get(query);
				if(rs!=null){
				try{
				while(rs.next()){
					IErpHoaDon_SP sanpham= new ErpHoanDon_SP();
					sanpham.setId(this.Id);
					sanpham.setMaSanPham(rs.getString("ma"));
					sanpham.setTenSanPham(rs.getString("tensp"));
					sanpham.setSoLuong(rs.getDouble("soluong"));
					sanpham.setDonGia(rs.getDouble("dongia"));
					sanpham.setDonViTinh(rs.getString("DONVI")); 
					sanpham.setQuyDoi(rs.getInt("QUYDOI"));
					sanpham.setHansudung(rs.getString("HANSUDUNG"));
					
					
					// lay danh sach lo
					
					List<ISpDetail> spSoloList= new ArrayList<ISpDetail>();
					//String qr= "select hoadon_fk, donhang_fk, MA,TEN, DONVI,  SOLO, NGAYHETHAN,NGAYSANXUAT,DONGIA, SOLUONG from ERP_HOADON_SP_CHITIET where hoadon_fk ="+(this.Id.length() >0? this.Id:"0" )+"     and MA='"+ sanpham.getMaSanPham()+"'";
					String qr= "select   SOLO,isnull(NGAYSANXUAT,'') as NGAYSANXUAT, NGAYHETHAN,NGAYNHAPKHO,ISNULL(MAME,'') AS MAME,ISNULL(MATHUNG,'') AS MATHUNG,ISNULL(MAPHIEU,'') AS MAPHIEU,ISNULL(BIN_FK,0) AS BIN_FK,KHO_FK AS KHOTT_SP_CT_FK, SOLUONG,isnull(hamluong,'100') as hamluong,isnull(hamam,'0') as hamam from ERP_HOADON_SP_CHITIET where hoadon_fk IN ("+(this.Id.length() >0? this.Id:"0" )+" )    and MA='"+ sanpham.getMaSanPham()+"'";
					System.out.println(" Chi tiet Lo: "+ qr);
					ResultSet rsSolo= db.get(qr);
					if(rsSolo!= null){
						while(rsSolo.next()){
							ISpDetail spdt = new SpDetail();
							spdt.setSolo(rsSolo.getString("SOLO"));
							spdt.setSoluong(rsSolo.getString("SOLUONG"));
							spdt.setNgayHethan(rsSolo.getString("NGAYHETHAN"));
							spdt.setNgaySx(rsSolo.getString("NGAYSANXUAT"));
							spdt.setKhott_fk(rsSolo.getString("KHOTT_SP_CT_FK"));
							spdt.setMame(rsSolo.getString("MAME"));
							spdt.setMathung(rsSolo.getString("MATHUNG"));
							spdt.setMaphieu(rsSolo.getString("MAPHIEU"));
							spdt.setNgaynhapkho(rsSolo.getString("NGAYNHAPKHO"));
							spdt.setkhuid(rsSolo.getString("BIN_FK"));
							spdt.setHamam(rsSolo.getString("hamam"));
							spdt.setHamluong(rsSolo.getString("hamluong"));
							spSoloList.add(spdt);
						}
						rsSolo.close();
					}
					sanpham.setSpDetail(spSoloList);
					
					
					
					this.listsanpham.add(sanpham);
					//this.TongTienChuaCK=this.TongTienChuaCK+ (rs.getInt("soluong") * rs.getDouble("dongia"));
					
				}
				}catch(Exception er){
					er.printStackTrace();
					System.out.println("Error ErpHoaDon.java 492 : "+er);
				}
				}
		 
			}
			
			}catch(Exception er){
				er.printStackTrace();
				System.out.println("Error : " + er.toString());
			}
	}
	
	public void SetPOMT(String PoMt) {
		
		this.POMT=PoMt;
	}
	
	public String GetPOMT() {
		
		return this.POMT;
	}
	
	public String getPOInfo() 
	{
		return this.poInfo;
	}
	
	public void setPOInfor(String poInfo) 
	{
		this.poInfo = poInfo;
	}
	
	public String getNoidungCK()
	{	
		return this.noidungCK;
	}
	
	public void setNoidungCK(String noidungCK)
	{
		this.noidungCK = noidungCK;
	}
	
	public void SetGhiChu(String ghichu) {
		
		this.GhiChu=ghichu;
	}
	
	public String getGhiChu() {
		
		return this.GhiChu;
	}
	
	public String getHoanTat() {
		return this.HoanTat;
	}
	
	public void setHoanTat(String HoanTat) {
	this.HoanTat=HoanTat;
		
	}
	
	public boolean EditHT() {
		String query="Update Erp_HoaDon Set SoHoaDon=N'"+this.SoHoaDon+"',KyHieu='"+this.Kyhieu+"' Where PK_SEQ='"+this.Id+"'" +
		" And HoanTat=0";
	if(!this.db.update(query))
	{
		this.setMessage("Không thể cập nhật hóa đơn");
		return false;
	}
		return true;
	}
	
	public String getLoaihoadon() {
		
		return this.Loaihoadon;
	}
	
	public void setLoaihoadon(String loaihoadon) {
		
		this.Loaihoadon=loaihoadon;
		
	}
	
	public String getGhiChu1() {
		return this.ghichu1;
	}
	
	public void setGhiChu1(String ghichu1) {
		this.ghichu1 = ghichu1;
	}
	
	public String getGhiChu2() {
		return this.ghichu2;
	}
	
	public void setGhiChu2(String ghichu2) {
		this.ghichu2 = ghichu2;
	}
	
	public String getGhiChu3() {
		return this.ghichu3;
	}
	
	public void setGhiChu3(String ghichu3) {
		this.ghichu3 = ghichu3;
	}
	
	public String ChotHoaDon() 
	{
		String ngayhoadon = "";
		String ngayghinhan = "";
		try
		{
			 db.getConnection().setAutoCommit(false);
			 String query =  " select  hd.loaihoadon, hd.ngayxuatHD, isnull(hd.ngayxuatHD,'') as NGAYGHINHAN  " +
			 				 " from  erp_hoadon hd    where  hd.pk_seq  ="+this.Id;
			 ResultSet checkNpp = db.get(query);
			 
			 if(checkNpp !=null){
				 if(checkNpp.next()){
					 this.Loaihoadon = checkNpp.getString("loaihoadon");
					 ngayhoadon = checkNpp.getString("ngayxuatHD");
					 ngayghinhan = checkNpp.getString("ngayghinhan");
				 }
				 checkNpp.close();
			 }
			 
			 query = "update  Erp_HoaDon set trangthai='1',nguoisua='"+this.UserId+"' where pk_Seq='" + this.Id + "' and trangthai=0";
			 if(db.updateReturnInt(query)!=1){
				db.getConnection().rollback();
				return "Khong The Chot Hoa Don ,Loi Dong Lenh : "+ query;
			 }
			 
			 
			 	if(this.Loaihoadon.equals("6") ){
 
			 		query=     "  SELECT    DDH_SP.SANPHAM_FK,SP.MA,SP.TEN AS TENSP, DDH_SP.DONGIA AS DONGIA           " +  
							   "  FROM ERP_MUAHANG_SP DDH_SP               " +  
							   "  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK               " +  
							   "  INNER JOIN ERP_MUAHANG DDH  ON DDH.PK_SEQ=DDH_SP.MUAHANG_FK              " +  
							   "  LEFT JOIN QUYCACH ON SP.PK_SEQ=QUYCACH.SANPHAM_FK               " +  
							   "  LEFT JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK=DVDL.PK_SEQ                 " +  
							   "  WHERE  DDH_SP.SOLUONG >0   AND MUAHANG_FK IN   " +  
							   "  (select ddh_fk from erp_hoadon_ddh where hoadon_fk='"+ this.Id +"')          " +  
							   "  AND  DDH_SP.SOLUONG - (             " +  
							   "  SELECT ISNULL(SUM(SOLUONG),0) FROM ERP_HOADON_SP A                " +  
							   "  INNER JOIN ERP_HOADON B ON B.PK_SEQ=A.HOADON_FK      " +  
							   "  inner join ERP_HOADON_DDH hd_dh on hd_dh.HOADON_FK= B.PK_SEQ              " +  
							   "  WHERE hd_dh.DDH_FK =DDH.PK_SEQ AND A.SANPHAM_FK=DDH_SP.SANPHAM_FK AND B.TRANGTHAI = '1' AND B.LOAIHOADON =6 )    > 0  " ;
							 
					 
					ResultSet rs=db.get(query);
				 
						if(!rs.next()){
							 
							//HOAN TAT DON TRA HANG
							query=" update ERP_MUAHANG set trangthai='3' where pk_seq in (select ddh_fk from erp_hoadon_ddh where hoadon_fk='"+ this.Id +"')";
							if(!db.update(query)){
								db.getConnection().rollback();
								System.out.println("___LOI CHOT HOA DON: " + query);
								return "Khong The Chot Hoa Don ,Loi Dong Lenh : "+ query;
							}
						}
					 
					
				}else{
					db.getConnection().rollback();
					return "Không xác định được loại hóa đơn";
				}
				//GHI NHAN BUT TOAN
				Utility util = new Utility();
				String nam = ngayghinhan.substring(0, 4);
				String thang = ngayghinhan.substring(5, 7);
				CapnhatKT	KT;	
				
				query=" SELECT  MH.TIENTE_FK  ,TT.MA "+
					" FROM ERP_HOADON_DDH A INNER JOIN ERP_MUAHANG MH  "+
					" INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ= MH.TIENTE_FK "+
					" ON MH.PK_SEQ =A.DDH_FK "+ 
					" WHERE A.HOADON_FK="+this.Id;
				
				ResultSet rstien=db.get(query);
				String matiente="";
				String tiente_fk = "";
				  
				if(rstien.next()){
					matiente=rstien.getString("MA");
					tiente_fk=rstien.getString("TIENTE_FK");
					 
				}
				rstien.close();
				if(!matiente.equals("VND")){
					db.getConnection().rollback();
					return "Chưa định khoản kế toán trong trường hợp tiền tệ khác VND,vui lòng báo Admin để được trợ giúp";				
				}
				
				
				String loaichungtu="Hóa đơn trả về NCC";
				
				
				query="delete erp_phatsinhketoan where loaichungtu=N'"+loaichungtu+"' and SOCHUNGTU="+this.Id+" ";
				if(!db.update(query)){
					db.getConnection().rollback();
					System.out.println("___LOI CHOT HOA DON: " + query);
					return "Khong The Chot Hoa Don ,Loi Dong Lenh : "+ query;
				}
				query="delete erp_phatsinhketoan where loaichungtu=N'Hóa đơn trả hàng NCC' and SOCHUNGTU="+this.Id+" ";
				if(!db.update(query)){
					db.getConnection().rollback();
					System.out.println("___LOI CHOT HOA DON: " + query);
					return "Khong The Chot Hoa Don ,Loi Dong Lenh : "+ query;
				}
			
				
				
				
				ResultSet rsDoanhSo;
				/*// GHI NHAN: TIEN HANG, 
				query ="  SELECT N'Nhà cung cấp' as doituongNCC, a.NCC_FK as madoituongNCC, \n " +
					   " (SELECT TAIKHOAN_FK FROM ERP_NHACUNGCAP  WHERE PK_SEQ = a.NCC_FK) as taiKhoanNCC, \n " +
					   " (SELECT pk_seq FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = lsp.taikhoankt_fk and congty_fk=a.congty_fk ) as taiKhoanCO_TIENHANG, \n " +
					   " hd_SP.TIENBVAT  as TienHang " +
					   " FROM   ERP_HOADON a INNER JOIN ERP_NHACUNGCAP b on a.NCC_FK = b.PK_SEQ \n " +
					   " INNER JOIN ERP_HOADON_SP hd_SP ON hd_SP.HOADON_FK = A.PK_SEQ \n" +
					   " inner join erp_sanpham sp on sp.pk_seq = hd_SP.sanpham_fk \n" +
					   " inner join erp_loaisanpham lsp on lsp.pk_seq = sp.loaisanpham_fk \n" +
					   " WHERE  a.PK_SEQ = '"+this.Id+"'";
				ResultSet rsDoanhSo = db.get(query);
				while(rsDoanhSo.next())
				{	
					String taikhoanktCo = "";
					String taikhoanktNo = "";
					
					String doituong_no = "";
					String madoituong_no = "";
					String doituong_co = "";
					String madoituong_co = ""; 
					 
					taikhoanktNo = rsDoanhSo.getString("taiKhoanNCC");
					double tienhang =rsDoanhSo.getDouble("TIENHANG");
					if(tienhang > 0)
					{
						doituong_no = rsDoanhSo.getString("doituongNCC");
						madoituong_no = rsDoanhSo.getString("madoituongNCC");
						taikhoanktCo = rsDoanhSo.getString("taiKhoanCO_TIENHANG");
					
						if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
						{
							rsDoanhSo.close();
							db.getConnection().rollback();
							return "Nhà cung cấp và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
						}
						
						 	KT=new CapnhatKT();
						KT.setSochungtu(this.Id);
					 	KT.setNOIDUNGNHAPXUAT_FK(""); 
						String spid = "";
						KT.setSpId(spid);
						 
						double soluong = 0;
						// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
						 
						KT.setSOLUONG("0");;
						
						double dongia=0;
						
						KT.setDONGIA("0");
						
						String thanhtien =""+ Math.round(tienhang);
						
						
					 
						KT.setNam(nam);
						KT.setThang(thang);
					 
						 
						KT.setTaikhoanCO_fk(taikhoanktCo);
						KT.setTaikhoanNO_fk(taikhoanktNo);
				 
					 
						KT.setDOITUONG_CO(doituong_co);
						KT.setDOITUONG_NO(doituong_no);
						KT.setMADOITUONG_CO(madoituong_co);
						KT.setMADOITUONG_NO(madoituong_no);
						KT.setTIGIA_FKl("1");
						KT.setDONGIANT("0");
					  
						KT.setTIENTEGOC_FK(tiente_fk);
						
						double  dongiaViet = dongia;
						
						KT.setDONGIA(dongiaViet+"");
						  
						KT.setNO(thanhtien+"");
						KT.setCO(thanhtien+"");
						KT.setTONGGIATRI(thanhtien+"");
						
						KT.setChiPhiId("NULL");
						KT.setKhoNhanID("NULL");
						KT.setMasp("");
						KT.setTensp("");
						KT.setDonvi("");
						KT.setLoaichungtu(loaichungtu);
						
						KT.setNgaychotnv(ngayghinhan);
						KT.setNgaychungtu(ngayghinhan);
						KT.setNgayghinhan(ngayghinhan);
					 
						KT.setKhoanmuc("Hóa đơn trả về NCC-Tiền hàng");
						//System.out.println("THANHTIEN : "+thanhtien);
						String msg1=KT.CapNhatKeToan_Kho(util, db);
						//System.out.println("thông bao loi : "+msg1);
						if(msg1.length()> 0){
							db.getConnection().rollback();
							return msg1;
							
							
						}
						 
					}
					
				 
					
				}
				rsDoanhSo.close();*/
				
				
				
				// GHI NHAN:   TIEN CHIET KHAU, TIEN THUE
				query ="  SELECT N'Nhà cung cấp' as doituongNCC, a.NCC_FK as madoituongNCC, \n " +
					   " (SELECT TAIKHOAN_FK FROM ERP_NHACUNGCAP  WHERE PK_SEQ = a.NCC_FK) as taiKhoanNCC, \n " + 
					   " (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52110000'  and congty_fk=a.congty_fk  ) as taiKhoanNO_TIENCK, \n " +
					   " (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13314000'  and congty_fk=a.congty_fk ) as taiKhoanCO_TIENTHUE, \n " +
					   " isnull(a.VAT,0) as Tienthue, isnull(a.CHIETKHAU,0) as TienCK \n" +
					   " FROM   ERP_HOADON a INNER JOIN ERP_NHACUNGCAP b on a.NCC_FK = b.PK_SEQ \n " +
					   " WHERE  a.PK_SEQ = '"+this.Id+"'";
				
				System.out.println("___KE TOAN DOANH SO: " + query);
				
					
				 rsDoanhSo = db.get(query);
					
					
					while(rsDoanhSo.next())
					{	
						String taikhoanktCo = "";
						String taikhoanktNo = "";
						
						String doituong_no = "";
						String madoituong_no = "";
						String doituong_co = "";
						String madoituong_co = "";
						double tienthue = Math.round(rsDoanhSo.getDouble("Tienthue"));
						
						double tienck = Math.round(rsDoanhSo.getDouble("TienCK"));
						
						taikhoanktNo = rsDoanhSo.getString("taiKhoanNCC");
				  
						if(tienthue > 0)
						{
							doituong_no = rsDoanhSo.getString("doituongNCC");
							madoituong_no = rsDoanhSo.getString("madoituongNCC");
							
							taikhoanktCo = rsDoanhSo.getString("taiKhoanCO_TIENTHUE");	
						
							if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
							{
								rsDoanhSo.close();
								db.getConnection().rollback();
								return "Nhà cung cấp và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							}
							
							KT=new CapnhatKT();
							KT.setSochungtu(this.Id);
						 	KT.setNOIDUNGNHAPXUAT_FK(""); 
							String spid = "";
							KT.setSpId(spid);
							 
							double soluong = 0;
							// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
							 
							KT.setSOLUONG("0");;
							
							double dongia=0;
							
							KT.setDONGIA("0");
							
							String thanhtien =""+ Math.round(tienthue);
							
							
						 
							KT.setNam(nam);
							KT.setThang(thang);
						 
							 
							KT.setTaikhoanCO_fk(taikhoanktCo);
							KT.setTaikhoanNO_fk(taikhoanktNo);
					 
						 
							KT.setDOITUONG_CO(doituong_co);
							KT.setDOITUONG_NO(doituong_no);
							KT.setMADOITUONG_CO(madoituong_co);
							KT.setMADOITUONG_NO(madoituong_no);
							KT.setTIGIA_FKl("1");
							KT.setDONGIANT("0");
						  
							KT.setTIENTEGOC_FK(tiente_fk);
							
							double  dongiaViet = dongia;
							
							KT.setDONGIA(dongiaViet+"");
							  
							KT.setNO(thanhtien+"");
							KT.setCO(thanhtien+"");
							KT.setTONGGIATRI(thanhtien+"");
							
							KT.setChiPhiId("NULL");
							KT.setKhoNhanID("NULL");
							KT.setMasp("");
							KT.setTensp("");
							KT.setDonvi("");
							KT.setLoaichungtu(loaichungtu);
							
							KT.setNgaychotnv(ngayghinhan);
							KT.setNgaychungtu(ngayghinhan);
							KT.setNgayghinhan(ngayghinhan);
						 
							KT.setKhoanmuc("Hóa đơn trả về NCC-Tiền thuế");
							//System.out.println("THANHTIEN : "+thanhtien);
							String msg1=KT.CapNhatKeToan_Kho(util, db);
							//System.out.println("thông bao loi : "+msg1);
							if(msg1.length()> 0){
								db.getConnection().rollback();
								return msg1;
								
								
							}
							 
						}
						
						if(tienck > 0)
						{ 
							return "Chưa định khoản kế toán trong trường hợp có chiết khấu ,vui lòng báo Admin để được trợ giúp";	
						}
						
					}
					rsDoanhSo.close();
			 
					
			
			 
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		}
		catch(Exception er)
		{
			db.update("rollback");
			er.printStackTrace();
			return "Loi Khong The Cap nhat :"+ er.toString();	
		}
	}
	

 
	 

	
	public void SetSKU(String SKU) {
		
		this.Sku= SKU;
	}
	
	public String GetSKU() {
		
		return this.Sku;
	}
	
	public String[] getScheme_chietkhau() {
		
		return this.Scheme_chietkhau;
	}
	
	public String[] getScheme_ck_thanhtien() {
		
		return  this.Scheme_ck_thanhtien;
	}
	
	public void setScheme_chietkhau(String[] Scheme_chietkhau) {
		this.Scheme_chietkhau=Scheme_chietkhau;
		
	}
	
	public void setScheme_ck_thanhtien(String[] Scheme_ck_thanhtien) {
		
		this.Scheme_ck_thanhtien=Scheme_ck_thanhtien;
	}
	
	
	public String getTenKhXhd() {
		
		return this.TenKhXhd;
	}

	
	public void setTenKhXhd(String TenKhXhd) {
		
		 this.TenKhXhd=TenKhXhd ;
	}

	
	public String getMasothueXhd() {
		
		return this.MasothueXhd;
	}

	
	public void setMasothueXhd(String MasothueXhd) {
		
		this.MasothueXhd=MasothueXhd;
	}

	
	public String getDiachiXhd() {
		
		return this.DiachiKhXhd;
	}

	
	public void setDiachiXhd(String DiachiXhd) {
		
		this.DiachiKhXhd= DiachiXhd;
	}
	
	public List<IErpHoaDonList> getHoadonList()
	{
		return this.HoadonList;
	}

	public void setHoadonList(List<IErpHoaDonList> hoadonList) 
	{
		this.HoadonList = hoadonList ;
		
//		ArrayList<IErpHoaDonList> result = Arrays.
//		ArrayList<String> result = Arrays.stream(input)
//        .filter(a -> a.length() > 10)
//        .distinct()
//        .collect(Collectors.toCollection(ArrayList::new));

		
	}

	
	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}

	
	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}


	public void setCongtyId(String ctyId) 
	{
		this.ctyId = ctyId;
		
	}

	public String getCongtyId() 
	{
		return this.ctyId;
	}

	@Override
	public void DBClose() {
		// TODO Auto-generated method stub
		this.db.shutDown();
	}

	public String getSodontrahang() {
		return Sodontrahang;
	}

	public void setSodontrahang(String sodontrahang) {
		Sodontrahang = sodontrahang;
	}
	
}
