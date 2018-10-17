package geso.traphaco.erp.beans.huyhoadontaichinh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;


 
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
 
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDon_SP;
 
import geso.traphaco.erp.beans.huyhoadontaichinh.IHuyhoadontaichinh;

public class  Huyhoadontaichinh extends Phan_Trang implements IHuyhoadontaichinh 
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
	String NPPghinhan;
	String ddhId;
	
	String ngayghino;
	
	ResultSet rsNPPghinhan;
	ResultSet listHoaDon;
	ResultSet rsddhChuaXuatHD;
	List<IErpHoaDon_SP> listsanpham=new ArrayList<IErpHoaDon_SP>();
	
	double TongTienChuaCK=0;
	double TienCK=0;
	double TienThucThu=0;
	String Loaihoadon="";
	
	double ThanhTien=0;
	
	double TienAVAT=0;
	double TTTienTraKM=0;
	String IDNhaCungCap;
	String TenNhaCungCap;
	String KenhBanHangId;
	String DVKDID;
	String SoSO="";
	double NoCu=0;
	String Sku;
	String inBangKe = "";
	
	String Scheme_chietkhau[];
	String Scheme_ck_thanhtien[];
	
	double SoTienTraTrungBay;
	double TienCKThuongMai=0;
	String NguoiMuaHang;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	dbutils db;
	
	dbutils ab;
	String sochungtu;
	String ngaydonhang;
	
	String sohoadon2;
	
	List<IThongTinHienThi> hienthiList;
	
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
	
	String khId;
	ResultSet khRs;
	ResultSet ddhRs;
 
	String dhId;
	
	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spMadonvi;
	String[] spSoluong;
	String[] spSoluonggoc;
	String[] spDongia;
	String[] spDongiagiamgia;
	
	String[] spChietkhau;	
	String[] spVat;
	
	String[] spTienCK;
	String[] spTienVat;
	String[] spThanhtien;
	
	String[] spKhoTT;
	String[] spnr;
	
	String Ngayhuy;
	String SoCT;
	
	String nguoimuahang;
	String donvi;
	String diachi;
	String masothue;	
	
	String khxhd;	
	String diachigiaohang;
	String tenhanghoadichvu;
	
	
	private ResultSet nppList;
	

	public ResultSet getNppList() {
		return nppList;
	}
	
	
	public void setNppList(ResultSet nppList) {
		this.nppList = nppList;
	}
	
	public String getSochungtu() {
		return this.sochungtu;
	}
	
	public  Huyhoadontaichinh(){
		/*
		 * khoi tao cac doi tuong
		 */
		this.Ngayhuy="";
		this.SoCT="";
		db=new dbutils();
		this.TrangThai="";
		this.tungay="";
		this.denngay="";
		this.SoHoaDon="";
		this.Msg="";
		this.Sku="";
		this.NgayXuaHd="";
		this.HinhThucTT="";
		this.Kyhieu="";
		this.NppId="";
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
		this.NPPghinhan="";
		this.TenKhXhd="";
		this.DiachiKhXhd="";
		this.MasothueXhd="";
		this.khId= "";
		this.dhId= "";
		this.ngayghino="";
		this.ngaydonhang = "";
		this.ddhId = "";
		this.nguoimuahang = "";
		this.donvi = "";
		this.diachi = "";
		this.masothue = "";
		this.khxhd = "";
		this.diachigiaohang = "";
		this.tenhanghoadichvu = "";
		this.sohoadon2 = "";
		this.hienthiList = new ArrayList<IThongTinHienThi>();
	}
		
	public void init() {//ĐÃ CÓ ID - HÓA ĐƠN
		
	 
		try
		{
			String query="";
			if(this.Id.length()>0){
			
				query="SELECT HOADON_FK,NGAYHUY FROM ERP_HUYHOADONTAICHINH WHERE PK_SEQ="+this.Id;
				ResultSet rshd=db.get(query);
				if(rshd.next()){
					this.SoCT=rshd.getString("HOADON_FK");
					this.Ngayhuy=rshd.getString("NGAYHUY");
				}
				rshd.close();
			}
			
			this.khRs = db.get("select PK_SEQ, TEN from ERP_KHACHHANG WHERE TRANGTHAI = '1' ORDER BY TEN");
			
			//B1.LẤY THÔNG TIN HÓA ĐƠN
						  
			  query=
					"  SELECT PK_SEQ, NGAYXUATHD,isnull(NGAYGHINHAN,'') NGAYGHINHAN, KYHIEU,SOHOADON,VAT,isnull(TIENCKTHUONGMAI,0) TIENCKTHUONGMAI ,CHIETKHAU,isnull(GHICHU,'') GHICHU , \n" +
					"		  KHACHHANG_FK, ISNULL(TONGTIENAVAT,0) TONGTIENAVAT , HINHTHUCTT, GHICHU, isnull(KM_GHICHU,'') KM_GHICHU, ISNULL(HANGHOADICHVU,'') HANGHOADICHVU, KHO_FK, ISNULL(DIACHIGIAOHANG, '') DIACHIGIAOHANG, ISNULL(SOHOADONDK, '') SOHOADONDK, \n"+
					"		  		  isnull(DONVI,'') DONVI, isnull(DIACHI,'') DIACHI, isnull(MASOTHUE, '') MASOTHUE, isnull(NGUOIMUAHANG, '') NGUOIMUAHANG   \n"+
					"  FROM   ERP_HOADON WHERE PK_SEQ = '"+this.SoCT+"' ";
			  			
			  System.out.println("INIT_THONGTINHOADON: "+query);
			  
			  ResultSet rs= db.get(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					this.khId = rs.getString("KHACHHANG_FK");
					this.NgayXuaHd = rs.getString("NGAYXUATHD");
					this.ngayghino = rs.getString("NGAYGHINHAN");
					this.HinhThucTT = rs.getString("HINHTHUCTT");
					this.Kyhieu = rs.getString("KYHIEU");
					this.GhiChu = rs.getString("GHICHU");
					this.SoHoaDon = rs.getString("SOHOADON");
					this.TienCK = rs.getDouble("CHIETKHAU");
					this.TienCKThuongMai = rs.getDouble("TIENCKTHUONGMAI");
					this.TienVat = rs.getDouble("VAT");
					this.TienAVAT = rs.getDouble("TONGTIENAVAT");
					this.ghichu1 = rs.getString("KM_GHICHU");
					this.tenhanghoadichvu = rs.getString("HANGHOADICHVU");
					this.diachigiaohang = rs.getString("DIACHIGIAOHANG");
					this.sohoadon2 = rs.getString("SOHOADONDK");
					this.khxhd = rs.getString("khachhang_fk");
					this.donvi = rs.getString("DONVI");
					this.diachi = rs.getString("DIACHI");
					this.masothue = rs.getString("MASOTHUE");
					this.nguoimuahang = rs.getString("NGUOIMUAHANG");
				}
			}
			rs.close();
			 
			query = " SELECT  distinct A.PK_SEQ, A.NGAYXUAT NGAYDONHANG, C.Ten KHACHHANG, C.PK_SEQ KHACHHANG_FK \n"+
			" FROM 	  ERP_XUATKHO A INNER JOIN ERP_DONDATHANG B ON A.DONDATHANG_FK = B.PK_SEQ \n"+
			"	 	  INNER JOIN ERP_KHACHHANG C ON B.KHACHHANG_FK = C.PK_SEQ \n"+
			" WHERE   B.PK_SEQ IS NOT NULL AND A.NOIDUNGXUAT = '100002' " +
			" 		  AND A.DONDATHANG_FK IN (SELECT B.DONDATHANG_FK \n"+ 
			"								  FROM ERP_HOADON_XUATKHO A INNER JOIN ERP_XUATKHO B ON A.xuatkho_fk = B.PK_SEQ \n"+
			"								  WHERE A.HOADON_FK IN ("+ this.SoCT+")) \n";
			
			System.out.println(query);
			this.ddhRs= db.get(query);
			
			query = "	SELECT XUATKHO_FK FROM ERP_HOADON_XUATKHO  WHERE HOADON_FK = '" + this.SoCT+"'";
			ResultSet rsdh = this.db.get(query);
			
			System.out.println("query DDH "+query);
			String ddh = "";
			
			if(rsdh!=null)
			{
				while(rsdh.next())
				{
					ddh += rsdh.getString("XUATKHO_FK") + ",";
				}
				rsdh.close();
				
				if(ddh.trim().length() > 0)
					this.dhId = ddh.substring(0, ddh.length() - 1);
			}
			
			//LẤY SẢN PHẨM TRÊN HÓA ĐƠN 	
			query=
			" SELECT SANPHAM_FK SPID, SP.MA, SP.TEN,HD_SP.DONVITINH DONVI,HD_SP.DVDL_FK MADONVI,ISNULL(HD_SP.DONGIA,0) \n " +
			"		 as DONGIA,isnull(HD_SP.VAT,0) VAT, SOLUONG, HD_SP.TIENAVAT, isnull(HD_SP.CHIETKHAU,0) CHIETKHAU, \n " +
			"		 isnull(TIENCK,0) TIENCK,  HD_SP.KHOTT_FK, HD_SP.DONGIACK, HD.VAT TIENVAT, HD.TIENCKTHUONGMAI, HD.TONGTIENAVAT \n"+
			" FROM   ERP_HOADON_SP HD_SP INNER JOIN ERP_SANPHAM SP on HD_SP.SANPHAM_FK = SP.PK_SEQ \n "+
			"		 INNER JOIN ERP_HOADON HD ON HD.PK_SEQ = HD_SP.HOADON_FK \n"+	
			" WHERE  HOADON_FK ='"+this.SoCT+"' "+
			" ORDER BY HD_SP.KHOTT_FK DESC ";
			
			System.out.println("INIT ERP_HOADON_SP___: "+query);
			ResultSet rsLaySP = db.get(query);
			
			
			
			
			 try 
			    {
			    	String spID = "";
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONG = "";
					String spSOLUONGGOC = "";
					String spDONGIA = "";
					String spDONGIAGIAMGIA = "";
					String spCHIETKHAU = "";
					String spTIENCHIETKHAU= "";
					String spVAT = "";
					String spTIENTHUE = "";
					String spTHANHTIEN = "";
					String spMADONVI = "";
					String spKHOTT = "";
			    				    	
					double soluong=0;
					double dongia=0;
					
					double sp_dongiagiamgia = 0;
					
					double vat=0;
					double ck=0;
					
					double sp_tienck_banhang=0;    //TIENCK BÁN HÀNG = SOLUONG*DONGIA*CK/100;
					double tienthue=0; //TIENTHUE = (THANHTIEN - TIENCK)*VAT/100					
					double thanhtien=0;
					
					double tienckthuongmai = 0;
					
					double sp_tienthue = 0;
					
					double sp_thanhtien =0;
				    if(rsLaySP!= null)
				    {				    	
						while(rsLaySP.next())
						{
							spID += rsLaySP.getString("SPID") + "__";
							spMA += rsLaySP.getString("MA") + "__";
							spTEN += rsLaySP.getString("TEN") + "__";
							spDONVI += rsLaySP.getString("DONVI") + "__";
							spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
							spSOLUONGGOC += (rsLaySP.getDouble("SOLUONG")) + "__";
							spDONGIA += (rsLaySP.getDouble("DONGIA")) + "__";
							spVAT+=(rsLaySP.getDouble("VAT"))+"__";							
							spCHIETKHAU += (rsLaySP.getDouble("CHIETKHAU")) + "__";
							spTIENCHIETKHAU+=(rsLaySP.getDouble("TIENCK")) +"__";							
							spTHANHTIEN+=(rsLaySP.getDouble("TIENAVAT"))+"__";
							spMADONVI+=(rsLaySP.getString("MADONVI"))+"__";
							spKHOTT += (rsLaySP.getString("KHOTT_FK"))+"__";
							spDONGIAGIAMGIA += (rsLaySP.getDouble("DONGIACK"))+"__";
														
							soluong = rsLaySP.getDouble("SOLUONG");	
							dongia = rsLaySP.getDouble("DONGIA");
							
							sp_dongiagiamgia = rsLaySP.getDouble("DONGIACK");
							sp_thanhtien = rsLaySP.getDouble("TIENAVAT");
							
							thanhtien += sp_thanhtien;
							
							tienthue = rsLaySP.getDouble("TIENVAT");
							tienckthuongmai = rsLaySP.getDouble("TIENCKTHUONGMAI");
							
							//vat = rsLaySP.getDouble("VAT");
							//ck = rsLaySP.getDouble("CHIETKHAU");
							
							//sp_tienck_banhang = rsLaySP.getDouble("TIENCK");
							
							//sp_dongiagiamgia = dongia - Math.round(dongia * ck/100);
							
							
							
							//System.out.println("ck:"+ ck +"dongiagiamgia:"+ sp_dongiagiamgia);
							
							/*sp_thanhtien = Math.round(soluong * sp_dongiagiamgia);//tiền sau khi đã trừ chiết khấu
*/							
						/*	vat = rsLaySP.getDouble("VAT");*/
							
							sp_tienthue = Math.round(tienthue);
							
							spTIENTHUE+=sp_tienthue+"__";	
						}
						rsLaySP.close();
						
						if(spMA.trim().length() > 0)
						{spID = spID.substring(0, spID.length() - 2);
						this.spId = spID.split("__");
						
						System.out.println("IDIDID: "+spID);
						spMA = spMA.substring(0, spMA.length() - 2);
						this.spMa = spMA.split("__");
						
						spTEN = spTEN.substring(0, spTEN.length() - 2);
						this.spTen = spTEN.split("__");
						
						spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
						this.spDonvi = spDONVI.split("__");
						
						spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
						this.spSoluong = spSOLUONG.split("__");							
						
						spDONGIA = spDONGIA.substring(0, spDONGIA.length() - 2);
						this.spDongia = spDONGIA.split("__");
						
						spDONGIAGIAMGIA = spDONGIAGIAMGIA.substring(0, spDONGIAGIAMGIA.length() - 2);
						this.spDongiagiamgia =  spDONGIAGIAMGIA.split("__");
						
						spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
						this.spChietkhau = spCHIETKHAU.split("__");
						
						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spVat = spVAT.split("__");
						
						spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
						this.spTienVat = spTIENTHUE.split("__");
						
						/*spTIENCHIETKHAU =spTIENCHIETKHAU.substring(0, spTIENCHIETKHAU.length() - 2);
						this.spTienCK=spTIENCHIETKHAU.split("__");*/
						
						spTHANHTIEN = spTHANHTIEN.substring(0, spTHANHTIEN.length() - 2);
						this.spThanhtien = spTHANHTIEN.split("__");
						
						spMADONVI = spMADONVI.substring(0, spMADONVI.length() - 2);
						this.spMadonvi = spMADONVI.split("__");
						
						spSOLUONGGOC = spSOLUONG.substring(0, spSOLUONGGOC.length() - 2);
						this.spSoluonggoc = spSOLUONGGOC.split("__");
						
						/*spKHOTT = spKHOTT.substring(0, spKHOTT.length() - 2);
						this.spKhoTT = spKHOTT.split("__");*/}
													
						this.ThanhTien = thanhtien;
						this.TienCKThuongMai = tienckthuongmai; // LÚC LOAD RA MẶC ĐỊNH TIỀN CHIẾT KHẤU THƯƠNG MẠI = 0 VÌ ĐÂY LÀ TRƯỜNG NGƯỜI NHẬP TỰ NHẬP VÀO
						this.TienVat=Math.round(tienthue) ;
						this.TienAVAT = Math.round(thanhtien + tienthue - tienckthuongmai) ; // HOẶC LẤY THẲNG TỪ HÓA ĐƠN RA
						
				    }
				}
			    catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		 catch (Exception e) 
			{
				e.printStackTrace();
			}
	}
	
	public Huyhoadontaichinh(String id)
	{
		this.db = new dbutils();
		this.TrangThai="";
		this.tungay="";
		this.denngay="";
		this.SoHoaDon="";
		this.Msg="";
		this.Sku="";
		this.NgayXuaHd="";
		this.HinhThucTT="";
		this.Kyhieu="";
		this.NppId="";
		this.NppTen="";
		this.NguoiMuaHang="";
		this.DonDatHang=  new String[0];
		this.POMT="";
		this.poInfo = "";
		this.noidungCK = "";
		currentPages = 1;
		this.HinhThucTT="TM/CK";
		num = 1;
		this.Id=id;
		this.GhiChu="";
		this.noidungCK="";
		this.Scheme_chietkhau=new String[]{"","",""};
		this.Scheme_ck_thanhtien=new String[]{"","",""};
		this.NPPghinhan="";
		this.TenKhXhd="";
		this.DiachiKhXhd="";
		this.MasothueXhd="";
		this.khId="";
		this.dhId="";
		this.nguoimuahang = "";
		this.donvi = "";
		this.diachi = "";
		this.masothue = "";
		this.khxhd = "";
		this.diachigiaohang = "";
		this.tenhanghoadichvu = "";
		this.sohoadon2 = "";
/*		if(id.length() > 0)
			this.init();*/
		
		
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
		
		return this.TrangThai;
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

	
	public double getTienAVAT() {
		
		return this.TienAVAT;
	}

	
	public void setTienAVAT(double tienavat) {
		
		this.TienAVAT=tienavat;
	}

	
	public ResultSet getListHoaDon() {
		
		return this.listHoaDon;
	}

	
	public void setDisplayHD(){
		//ĐÃ CÓ ID - HÓA ĐƠN
		try
		{
			this.khRs = db.get("select PK_SEQ, TEN from ERP_KHACHHANG WHERE TRANGTHAI = '1' ORDER BY TEN");
			//B1.LẤY THÔNG TIN HÓA ĐƠN
			String query=
			"  SELECT PK_SEQ, NGAYXUATHD,NGAYGHINHAN, KYHIEU,SOHOADON, VAT,isnull(TIENCKTHUONGMAI,0) TIENCKTHUONGMAI ,CHIETKHAU,isnull(GHICHU,'') GHICHU , KHACHHANG_FK, ISNULL(TONGTIENAVAT,0) TONGTIENAVAT , HINHTHUCTT \n"+
			"  FROM ERP_HOADON WHERE PK_SEQ = '"+Id+"' ";
			
			System.out.println("INIT_THONGTINHOADON: "+query);
			ResultSet rs= db.get(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					this.khId = rs.getString("KHACHHANG_FK");
					this.NgayXuaHd = rs.getString("NGAYXUATHD");
					this.ngayghino = rs.getString("NGAYGHINHAN");
					this.HinhThucTT = rs.getString("HINHTHUCTT");
					this.Kyhieu = rs.getString("KYHIEU");
					this.GhiChu = rs.getString("GHICHU");
					this.SoHoaDon = rs.getString("SOHOADON");
					this.TienCK = rs.getDouble("CHIETKHAU");
					this.TienCKThuongMai = rs.getDouble("TIENCKTHUONGMAI");
					this.TienVat = rs.getDouble("VAT");
					this.TienAVAT = rs.getDouble("TONGTIENAVAT");
				}
			}
			rs.close();
			//B2. LẤY TẤT CẢ ĐƠN HÀNG CHƯA XUẤT HẾT CỦA KHÁCH HÀNG
			if(this.khId.length()>0){
				query="SELECT a.PK_SEQ,a.NGAYDAT NGAYDONHANG, b.MA+' - '+ b.TEN KHACHHANG FROM ERP_DONDATHANG a inner join ERP_KHACHHANG b ON a.KHACHHANG_FK = b.PK_SEQ WHERE a.PK_SEQ in (SELECT DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK = '" + this.Id+"')";
				System.out.println("INIT_DH: "+query);
				this.ddhRs= db.get(query);
				
			}	
			//LẤY SẢN PHẨM TRÊN HÓA ĐƠN 			
			query=				
				" SELECT SANPHAM_FK SPID, SP.MA, SP.TEN,HD_SP.DONVITINH DONVI,HD_SP.DVDL_FK MADONVI,ISNULL(HD_SP.DONGIA,0) as DONGIA,isnull(HD_SP.VAT,0) VAT, SOLUONG, HD_SP.TIENAVAT, isnull(HD_SP.CHIETKHAU,0) CHIETKHAU, isnull(TIENCK,0) TIENCK \n"+
				" FROM ERP_HOADON_SP HD_SP INNER JOIN ERP_SANPHAM SP on HD_SP.SANPHAM_FK = SP.PK_SEQ \n"+
				" WHERE HOADON_FK ='"+Id+"' \n";
			
			System.out.println("INIT ERP_HOADON_SP___: "+query);
			 ResultSet rsLaySP = db.get(query);
			
			 try 
			    {
			    	String spID = "";
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONG = "";
					String spDONGIA = "";
					String spCHIETKHAU = "";
					String spTIENCHIETKHAU="";
					String spVAT="";
					String spTIENTHUE="";
					String spTHANHTIEN ="";
					String spMADONVI ="";
			    				    	
					double soluong=0;
					double dongia=0;
					
					double vat=0;
					double ck=0;
					
					double sp_tienck_banhang=0;    //TIENCK BÁN HÀNG = SOLUONG*DONGIA*CK/100;
					double sp_tienthue=0; //TIENTHUE = (THANHTIEN - TIENCK)*VAT/100					
					double thanhtien=0;

				    if(rsLaySP!= null)
				    {				    	
						while(rsLaySP.next())
						{
							spID += rsLaySP.getString("SPID") + "__";
							spMA += rsLaySP.getString("MA") + "__";
							spTEN += rsLaySP.getString("TEN") + "__";
							spDONVI += rsLaySP.getString("DONVI") + "__";
							spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
							spDONGIA += (rsLaySP.getDouble("DONGIA")) + "__";
							spVAT+=(rsLaySP.getDouble("VAT"))+"__";							
							spCHIETKHAU += (rsLaySP.getDouble("CHIETKHAU")) + "__";
							spTIENCHIETKHAU+=(rsLaySP.getDouble("TIENCK")) +"__";							
							spTHANHTIEN+=(rsLaySP.getDouble("TIENAVAT"))+"__";
							spMADONVI+=(rsLaySP.getDouble("MADONVI"))+"__";
														
							soluong = rsLaySP.getDouble("SOLUONG");	
							dongia= rsLaySP.getDouble("DONGIA");
							sp_tienck_banhang = rsLaySP.getDouble("TIENCK");
							vat=rsLaySP.getDouble("VAT");
							
							thanhtien+=soluong*dongia;								
							sp_tienthue= (soluong*dongia - sp_tienck_banhang)*vat/100;
							
							spTIENTHUE+=sp_tienthue+"__";
							
						}
						rsLaySP.close();
						
						if(spMA.trim().length() > 0)
						{
							spID = spID.substring(0, spID.length() - 2);
							this.spId = spID.split("__");
							
							spMA = spMA.substring(0, spMA.length() - 2);
							this.spMa = spMA.split("__");
							
							spTEN = spTEN.substring(0, spTEN.length() - 2);
							this.spTen = spTEN.split("__");
							
							spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
							this.spDonvi = spDONVI.split("__");
							
							spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
							this.spSoluong = spSOLUONG.split("__");							
							
							spDONGIA = spDONGIA.substring(0, spDONGIA.length() - 2);
							this.spDongia = spDONGIA.split("__");
							
							spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
							this.spChietkhau = spCHIETKHAU.split("__");
							
							spTIENCHIETKHAU = spTIENCHIETKHAU.substring(0, spTIENCHIETKHAU.length() - 2);
							this.spTienCK = spTIENCHIETKHAU.split("__");
							
							spVAT = spVAT.substring(0, spVAT.length() - 2);
							this.spVat = spVAT.split("__");
							
							spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
							this.spTienVat = spTIENTHUE.split("__");
							
							spTHANHTIEN = spTHANHTIEN.substring(0, spTHANHTIEN.length() - 2);
							this.spThanhtien = spTHANHTIEN.split("__");
							
							spMADONVI = spMADONVI.substring(0, spMADONVI.length() - 2);
							this.spMadonvi = spMADONVI.split("__");
						}
																		
						this.ThanhTien = thanhtien;
						
				    }
				}
			    catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		 catch (Exception e) 
			{
				e.printStackTrace();
			}
	
	}
	

	
	public void setListHoaDon(String sql) {
		
		String query="";
		if(!sql.equals("")){
			query=sql;
		}else{
			query=" SELECT 	HD.PK_SEQ,HD.NGAYXUATHD,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON ,HD.HINHTHUCTT,KH.TEN, \n"+
				  " 		NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA ,ISNULL(HD.TONGTIENAVAT,0) AS TONGTIENAVAT \n"+
				  " FROM 	ERP_HOADON HD LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=HD.KHACHHANG_FK \n"+ 
				  "			INNER JOIN NHANVIEN NT ON NT.PK_SEQ=HD.NGUOITAO \n"+
			      "     	INNER JOIN NHANVIEN NS ON NS.PK_SEQ=HD.NGUOISUA \n " +
			      "	WHERE   HD.LOAIHOADON = 0 \n" ;			
		}
 
		System.out.println("INIT_HD: "+query);
		String query_init = createSplittingData_List(25, 10, "  PK_SEQ desc, NGAYXUATHD desc ", query);
		
		ResultSet rs = db.get(query_init);
		
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		try
		{
			if(rs!= null)
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{					
					//LAY DINH KHOAN KE TOAN
					String dk = LayDuLieu(rs.getString("PK_SEQ"));
					System.out.println("Dinh khoan "+dk);
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{
								kt = new DinhKhoanKeToan(rsKT.getString("PK_SEQ"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
										 rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"),"");
								ktList.add(kt);
							}
							rsKT.close();
						}
												
					// INIT
					
						ht = new ThongTinHienThi();		
						
						ht.setId(rs.getString("PK_SEQ"));
						ht.setTrangthai(rs.getString("TRANGTHAI"));
						ht.setNgaychungtu(rs.getString("NGAYXUATHD"));						
						ht.setSohoadon(rs.getString("SOHOADON"));
						ht.setTongtien(rs.getString("TONGTIENAVAT"));
						ht.setTenKH(rs.getString("TEN"));
						ht.setNgaytao(rs.getString("NGAYTAO"));
						ht.setNgaysua(rs.getString("NGAYSUA"));
						ht.setNguoitao(rs.getString("NGUOITAO"));
						ht.setNgaysua(rs.getString("NGAYSUA"));
						ht.setNguoisua(rs.getString("NGUOISUA"));						
						
						ht.setLayDinhkhoanKT(ktList);
					
					htList.add(ht);																	
				}
				rs.close();
			}
			
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		this.hienthiList = htList;
		System.out.println("Size:"+this.hienthiList.size());
		
		//this.listHoaDon=createSplittingData(50, 10, " NGAYXUATHD desc, sohoadon desc ", query);
		
		query = " select pk_seq, MA, TEN from ERP_KHACHHANG " ;
		this.nppList = this.db.get(query);
		
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
	
	
	public String checkGiaSP(String listDH) {
		String result = null;	
		String queryCheck = "SELECT DH.SANPHAM_FK,DH.DONHANG_FK as DONHANG1,DH1.DONHANG_FK AS DONHANG2 FROM\n" +
		 "(\n" +
		 "SELECT D.DONHANG_FK, D.SANPHAM_FK, D.GIAMUA FROM DONHANG_SANPHAM D\n" +
		 "WHERE DONHANG_FK IN("+listDH+")\n" +
		 ")DH\n" +
		 "INNER JOIN \n" +
		 "(\n" +
		 "SELECT D.DONHANG_FK, D.SANPHAM_FK, D.GIAMUA FROM DONHANG_SANPHAM D\n" +
		 "WHERE DONHANG_FK IN("+listDH+")\n" +
		 ")DH1 ON DH.DONHANG_FK = DH1.DONHANG_FK AND DH.GIAMUA = DH1.GIAMUA AND DH.SANPHAM_FK = DH1.SANPHAM_FK\n" +
		 "WHERE DH1.SANPHAM_FK = DH.SANPHAM_FK AND DH1.GIAMUA <> DH.GIAMUA";
		ResultSet rs = db.get(queryCheck);
		try {
			if(rs != null && rs.next()) {				
				result = "Mặt hàng [" +rs.getString(1)+
						"] có giá khác nhau trong đơn hàng [" +rs.getString(2)+
						"] và [" +rs.getString(3)+
						"], vui lòng thay đổi đơn hàng xuất hóa đơn.";
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return result;
	}
	public void setDonDatHang(String[] dondathang) {
		
		this.DonDatHang=dondathang;
	}

	public void createRs()
	{
		this.khRs = db.get("select PK_SEQ, TEN from NHAPHANPHOI where TRANGTHAI = 1");
		
		if(this.Id.length()<=0)
		{	
			
			String query="";
			//NẾU TICK CHỌN ĐƠN ĐẶT HÀNG TRONG RADIO
			if(this.dhId.length()>0){
				
				//B3: LẤY TIỀN CHIẾT KHẤU THƯƠNG MẠI CÒN LẠI SAU KHI TRỪ TỪ HÓA ĐƠN
			    
			    query= " SELECT (ISNULL(CKTHUONGMAI,0) - (SELECT ISNULL(SUM(ISNULL(TIENCKTHUONGMAI,0)),0) \n"+
					   " 										FROM ERP_HOADON \n"+ 
					   " 										WHERE TRANGTHAI !=2 and  PK_SEQ IN \n"+ 
					   "										( SELECT HOADON_FK FROM ERP_HOADON_DDH WHERE DDH_FK = DH.PK_SEQ))) AS TIENCHIETKHAUTHUONGMAI \n"+										
					   " FROM ERP_DONDATHANG DH where PK_SEQ ='"+this.dhId+"'";
					   
			    System.out.println(query);
			    ResultSet RsLayCKThuongMai = db.get(query);
			    double TienCKThuongMai =0;
			    if(RsLayCKThuongMai!=null)
			    {
			    	try {
						if(RsLayCKThuongMai.next())
						{
							TienCKThuongMai= RsLayCKThuongMai.getDouble("TIENCHIETKHAUTHUONGMAI");
						}
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
			    }			    
			    System.out.println("TIEN CHIET KHAU THUONG MAI:"+TienCKThuongMai);
			    this.TienCKThuongMai = TienCKThuongMai;
			    
				//B2: HIỆN TẤT CẢ CÁC SẢN PHẨM VỚI SỐ LƯỢNG CÒN LẠI CỦA ĐƠN HÀNG ĐÃ CHỌN	//HIỆN SỐ LƯỢNG CÒN LẠI	
				query=
				" 	 SELECT DDH_SP.SANPHAM_FK SPID,SP.MA,SP.TEN,DVDL.DONVI,DVDL.PK_SEQ MADONVI, ISNULL(DDH_SP.DONGIA,0) as DONGIA,ISNULL(DDH_SP.VAT,0) VAT,ISNULL(DDH_SP.chietkhau,0) CHIETKHAU, \n"+
			 	"			SOLUONG - (SELECT isnull(sum(soluong),0)  \n"+
			 	"  				   	   FROM   ERP_HOADON_SP a   \n"+
			 	"						  	  inner join ERP_HOADON b on b.PK_SEQ=a.HOADON_FK \n"+   
			 	"						      inner join ERP_HOADON_DDH c on c.HOADON_FK=b.PK_SEQ \n"+ 
			 	"					   WHERE  c.DDH_FK=DDH_SP.DONDATHANG_FK and a.SANPHAM_FK=DDH_SP.SANPHAM_FK and b.TRANGTHAI in ('0', '1')) AS SOLUONG \n"+
			 	"  			,ISNULL(QUYCACH.SOLUONG1,'1') AS QUYDOI  \n"+
			 	"	   		,ISNULL(DVDL.DONVI,'') AS DONVI  \n"+
			    "	FROM    ERP_DONDATHANG_SP DDH_SP  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK  INNER JOIN ERP_DONDATHANG DDH \n"+   
			 	"		    ON DDH.PK_SEQ=DDH_SP.DONDATHANG_FK  LEFT JOIN QUYCACH ON SP.PK_SEQ=QUYCACH.SANPHAM_FK  LEFT JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK=DVDL.PK_SEQ \n"+     
				"	WHERE  	DDH_SP.SOLUONG > 0 AND DONDATHANG_FK IN ('"+this.dhId+"') \n"+
			 	"		 	AND SOLUONG - (SELECT ISNULL(SUM(SOLUONG),0)  \n"+
			 	"						 FROM	ERP_HOADON_SP a    \n"+
			 	"								INNER JOIN ERP_HOADON b on b.pk_Seq=a.hoadon_fk \n"+    
			 	"								INNER JOIN ERP_HOADON_DDH c on c.hoadon_fk=b.pk_Seq \n"+   
			 	"						 WHERE c.ddh_fk=DDH_SP.DONDATHANG_FK and a.sanpham_fk=ddh_sp.sanpham_fk and b.trangthai in ('0','1')) !=0 \n"; 
				
				 System.out.println("INIT SP___: "+query);
				 ResultSet rsLaySP = db.get(query);
				 
				 try 
				    {
				    	String spID = "";
						String spMA = "";
						String spTEN = "";
						String spDONVI = "";
						String spSOLUONG = "";
						String spDONGIA = "";
						String spVAT="";
						String spCHIETKHAU = "";
						String spMADONVI ="";
						
						String spTIENCHIETKHAU="";
						String spTIENTHUE="";
						String spTHANHTIEN ="";
						
						double soluong=0;
						double dongia=0;
						
						double vat=0;
						double ck=0;
						
						double sp_thanhtien=0; //SOLUONG*DONGIA
						double sp_tienck_banhang=0;    //TIENCK BÁN HÀNG = SOLUONG*DONGIA*CK/100;
						double sp_tienthue=0; //TIENTHUE = (THANHTIEN - TIENCK)*VAT/100
						double sp_thanhtienavat=0;
						
						double thanhtien=0;
						double tienck_banhang=0;
						double tienthue=0;
											
					    if(rsLaySP!= null)
					    {				    	
							while(rsLaySP.next())
							{
								spID += rsLaySP.getString("SPID") + "__";
								spMA += rsLaySP.getString("MA") + "__";
								spTEN += rsLaySP.getString("TEN") + "__";
								spDONVI += rsLaySP.getString("DONVI") + "__";
								spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
								spDONGIA += (rsLaySP.getDouble("DONGIA")) + "__";
								spVAT+=(rsLaySP.getDouble("VAT"))+"__";
								spCHIETKHAU += (rsLaySP.getDouble("CHIETKHAU")) + "__";
								spMADONVI += (rsLaySP.getString("MADONVI"))+"__";
								
								soluong = rsLaySP.getDouble("SOLUONG");	
								dongia= rsLaySP.getDouble("DONGIA");
								vat = rsLaySP.getDouble("VAT");
								ck = rsLaySP.getDouble("CHIETKHAU");
								
								sp_thanhtien=soluong*dongia;
								System.out.println("SL"+ soluong+"__ don gia: "+dongia+"= thanhtien: "+sp_thanhtien);
								sp_tienck_banhang=sp_thanhtien*ck/100;
								sp_tienthue = (sp_thanhtien-sp_tienck_banhang)*vat/100;
								sp_thanhtienavat=sp_thanhtien-sp_tienck_banhang+sp_tienthue;
																						
								spTIENCHIETKHAU +=sp_tienck_banhang+"__";
								spTIENTHUE+=sp_tienthue+"__";
								spTHANHTIEN+= sp_thanhtienavat + "__";
								
								thanhtien+=sp_thanhtien;
								System.out.println("thanhtien: "+ thanhtien);
								tienck_banhang+=sp_tienck_banhang;
								tienthue+=sp_tienthue;							
							}
							rsLaySP.close();
							
							if(spMA.trim().length() > 0)
							{
								spID = spID.substring(0, spID.length() - 2);
								this.spId = spID.split("__");
								
								spMA = spMA.substring(0, spMA.length() - 2);
								this.spMa = spMA.split("__");
								
								spTEN = spTEN.substring(0, spTEN.length() - 2);
								this.spTen = spTEN.split("__");
								
								spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
								this.spDonvi = spDONVI.split("__");
								
								spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
								this.spSoluong = spSOLUONG.split("__");							
								
								spDONGIA = spDONGIA.substring(0, spDONGIA.length() - 2);
								this.spDongia = spDONGIA.split("__");
								
								spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
								this.spChietkhau = spCHIETKHAU.split("__");
								
								spVAT = spVAT.substring(0, spVAT.length() - 2);
								this.spVat = spVAT.split("__");
								
								spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
								this.spTienVat = spTIENTHUE.split("__");
								
								spTIENCHIETKHAU =spTIENCHIETKHAU.substring(0, spTIENCHIETKHAU.length() - 2);
								this.spTienCK=spTIENCHIETKHAU.split("__");
								
								spTHANHTIEN = spTHANHTIEN.substring(0, spTHANHTIEN.length() - 2);
								this.spThanhtien = spTHANHTIEN.split("__");
								
								spMADONVI = spMADONVI.substring(0, spTHANHTIEN.length() - 2);
								this.spMadonvi = spMADONVI.split("__");
							}
							
							this.ThanhTien = thanhtien;
							this.TienCK=tienck_banhang;
							this.TienVat=tienthue;
							this.TienAVAT = thanhtien - tienck_banhang - TienCKThuongMai + tienthue ;
					    }
					}
				    catch (Exception e) 
					{
						e.printStackTrace();
					}
			}
		}
		
		else
		{
			this.init();
		}
	}
	
	public void loadContents() {
		
		String query="";
		

		//B1: LẤY TẤT CẢ CÁC ĐƠN HÀNG CHƯA XUẤT HẾT 
		//query="SELECT PK_SEQ,NGAYDAT NGAYDONHANG FROM ERP_DONDATHANG WHERE ISNULL(TINHTRANG,0)=0 AND TRANGTHAI in (4,5,6)  AND KHACHHANG_FK='"+this.khId+"'" ;
/*					" AND PK_SEQ NOT IN ( select ddh_fk from  erp_hoadon_ddh a inner join erp_hoadon hd on a.hoadon_fk= hd.pk_seq where hd.loaihoadon ='0' and hd.trangthai != '2' and hd.pk_seq = "+(this.Id.length() >0 ?this.Id:"0")+" )";*/
		
		//TRẠNG THÁI ĐƠN ĐẶT HÀNG: 
			//1: CHỜ KINH DOANH DUYỆT; 2: CHỜ KẾ TOÁN DUYỆT; 3: CHỜ GIÁM ĐỐC DUYỆT; 4: ĐÃ DUYỆT; 5: ĐÃ XUẤT KHO
			//6: ĐÃ XUẤT HD GTGT; 7: ĐÃ XÓA; 8: HOÀN TẤT
		query =
		"	SELECT distinct a.PK_SEQ, a.NGAYDAT NGAYDONHANG, (SELECT TEN FROM ERP_KHACHHANG WHERE PK_SEQ = a.KHACHHANG_FK) KHACHHANG \n"+
		"	FROM ERP_DONDATHANG a INNER JOIN \n"+ 
		"	  ( select DONDATHANG_FK, SANPHAM_FK, SUM(SOLUONG) as SLGOC \n"+
		"	   from ERP_DONDATHANG_SP \n"+
		"	   group by DONDATHANG_FK, SANPHAM_FK \n"+
		"	   ) b on a.PK_SEQ = b.DONDATHANG_FK \n"+
		"	   	LEFT JOIN \n"+ 
		"	 ( select c.DDH_FK, SANPHAM_FK, SUM(hdsp.SOLUONG) as SLDAXUAT \n"+
		"	   from ERP_HOADON_SP hdsp  inner JOIN ERP_HOADON_DDH c on hdsp.HOADON_FK = c.HOADON_FK \n"+
		"	                            inner join erp_hoadon d on c.HOADON_FK = d.PK_SEQ and d.TRANGTHAI != 2 \n"+
		"	   where c.DDH_FK NOT IN (SELECT DDH_FK FROM ERP_HOADON_DDH c WHERE HOADON_FK = "+(this.Id.length() <=0 ? "0": this.Id) +" ) \n"+
		"	   group by c.DDH_FK, SANPHAM_FK \n"+
		"	   ) e on a.PK_SEQ = e.DDH_FK and b.SANPHAM_FK = e.SANPHAM_FK \n"+					
		"	WHERE ISNULL(a.TINHTRANG,0)= 0  and a.TRANGTHAI IN (4,5,6) AND cast(b.SLGOC as numeric(18,0)) - cast(ISNULL(e.SLDAXUAT,0) as numeric(18,0)) > 0 \n";
		
		if(this.khId.length()>0){
			query+= " and a.KHACHHANG_FK ='"+this.khId+"' ";
		}
		if(this.tungay.trim().length()>0){
			query+= " and a.NGAYTAO >= '"+this.tungay+"'";
		}
		if(this.denngay.trim().length()>0){
			query+= " and a.NGAYTAO <= '"+this.tungay+"'";
		}
		
		System.out.println("INIT_DH: "+query);
		
		this.ddhRs= db.get(query);
		int count =0;
		if(ddhRs!=null){
			
			try {
				if(ddhRs.next())
				{
					count++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("count"+count);
		
		if(count==1){
			this.dhId = "0";
		}
		
		System.out.println("DDH:"+this.dhId);
		
		System.out.println("Don Dat Hang:"+dhId);
		if(this.dhId.length()>0){
			
			//B3: LẤY TIỀN CHIẾT KHẤU THƯƠNG MẠI CÒN LẠI SAU KHI TRỪ TỪ HÓA ĐƠN
		    
		    query= " SELECT (ISNULL(CKTHUONGMAI,0) - (SELECT ISNULL(SUM(ISNULL(TIENCKTHUONGMAI,0)),0) \n"+
				   " 										FROM ERP_HOADON \n"+ 
				   " 										WHERE TRANGTHAI != 2 AND PK_SEQ IN \n"+ 
				   "										( SELECT HOADON_FK FROM ERP_HOADON_DDH WHERE DDH_FK = DH.PK_SEQ))) AS TIENCHIETKHAUTHUONGMAI \n"+										
				   " FROM ERP_DONDATHANG DH where PK_SEQ ='"+this.dhId+"'";
				   
		    System.out.println(query);
		    ResultSet RsLayCKThuongMai = db.get(query);
		    double TienCKThuongMai =0;
		    if(RsLayCKThuongMai!=null)
		    {
		    	try {
					if(RsLayCKThuongMai.next())
					{
						TienCKThuongMai= RsLayCKThuongMai.getDouble("TIENCHIETKHAUTHUONGMAI");
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		    }			    
		    System.out.println("TIEN CHIET KHAU THUONG MAI:"+TienCKThuongMai);
		    this.TienCKThuongMai = TienCKThuongMai;
		    System.out.println("TienCKThuongMai: "+TienCKThuongMai);
			//B2: HIỆN TẤT CẢ CÁC SẢN PHẨM VỚI SỐ LƯỢNG CÒN LẠI CỦA ĐƠN HÀNG ĐÃ CHỌN	//HIỆN SỐ LƯỢNG CÒN LẠI	
			query=
			" 	 SELECT DDH_SP.SANPHAM_FK SPID,SP.MA,SP.TEN,DVDL.DONVI,DVDL.PK_SEQ MADONVI, ISNULL(DDH_SP.DONGIA,0) as DONGIA,ISNULL(DDH_SP.VAT,0) VAT,ISNULL((SELECT CHIETKHAU FROM ERP_DONDATHANG a WHERE a.PK_SEQ = '"+this.dhId+"'),0) CHIETKHAU, \n"+
		 	"			SOLUONG - (SELECT isnull(sum(soluong),0)  \n"+
		 	"  				   	   FROM   ERP_HOADON_SP a   \n"+
		 	"						  	  inner join ERP_HOADON b on b.PK_SEQ=a.HOADON_FK \n"+   
		 	"						      inner join ERP_HOADON_DDH c on c.HOADON_FK=b.PK_SEQ \n"+ 
		 	"					   WHERE  c.DDH_FK=DDH_SP.DONDATHANG_FK and a.SANPHAM_FK=DDH_SP.SANPHAM_FK and b.TRANGTHAI !=2 "+
		 	"                             and a.HOADON_FK NOT IN ("+(this.Id.trim().length()<=0 ? "0" : this.Id ) +")" +
		 	"					  ) AS SOLUONG" +
		 	"  			,ISNULL(QUYCACH.SOLUONG1,'1') AS QUYDOI  \n"+
		 	"	   		,ISNULL(DVDL.DONVI,'') AS DONVI  \n"+
		    "	FROM    ERP_DONDATHANG_SP DDH_SP  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK  INNER JOIN ERP_DONDATHANG DDH \n"+   
		 	"		    ON DDH.PK_SEQ=DDH_SP.DONDATHANG_FK  LEFT JOIN QUYCACH ON SP.PK_SEQ=QUYCACH.SANPHAM_FK  LEFT JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK=DVDL.PK_SEQ \n"+     
			"	WHERE  	DDH_SP.SOLUONG >0 AND DONDATHANG_FK IN ('"+this.dhId+"')  \n"+
		 	"		 	AND  SOLUONG - (SELECT isnull(sum(soluong),0)  \n"+
		 	"						 FROM	erp_hoadon_sp a    \n"+
		 	"								inner join ERP_HOADON b on b.pk_Seq=a.hoadon_fk \n"+    
		 	"								inner join ERP_HOADON_DDH c on c.hoadon_fk=b.pk_Seq \n"+   
		 	"						 WHERE c.ddh_fk=DDH_SP.DONDATHANG_FK and a.sanpham_fk=ddh_sp.sanpham_fk and b.trangthai !=2 "+
		 	"							   and a.HOADON_FK NOT IN ("+(this.Id.trim().length()<=0 ? "0" : this.Id )+")  ) !=0 "; 
			 System.out.println("INIT SP___: "+query);
			 ResultSet rsLaySP = db.get(query);
			 
			 try 
			    {
			    	String spID = "";
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONG = "";
					String spSOLUONGGOC = "";
					String spDONGIA = "";
					String spVAT="";
					String spCHIETKHAU = "";
					
					String spMADONVI = "";
					String spTIENCHIETKHAU="";
					String spTIENTHUE="";
					String spTHANHTIEN ="";
					
					double soluong=0;
					double dongia=0;
					
					double vat=0;
					double ck=0;
					
					double sp_thanhtien=0; //SOLUONG*DONGIA
					double sp_tienck_banhang=0;    //TIENCK BÁN HÀNG = SOLUONG*DONGIA*CK/100;
					double sp_tienthue=0; //TIENTHUE = (THANHTIEN - TIENCK)*VAT/100
					double sp_thanhtienavat=0;
					
					double thanhtien=0;
					double tienck_banhang=0;
					double tienthue=0;
										
				    if(rsLaySP!= null)
				    {				    	
						while(rsLaySP.next())
						{
							spID += rsLaySP.getString("SPID") + "__";
							spMA += rsLaySP.getString("MA") + "__";
							spTEN += rsLaySP.getString("TEN") + "__";
							spDONVI += rsLaySP.getString("DONVI") + "__";
							spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
							spSOLUONGGOC += (rsLaySP.getDouble("SOLUONG")) + "__";
							spDONGIA += (rsLaySP.getDouble("DONGIA")) + "__";
							spVAT+=(rsLaySP.getDouble("VAT"))+"__";
							spCHIETKHAU += (rsLaySP.getDouble("CHIETKHAU")) + "__";
							spMADONVI += (rsLaySP.getString("MADONVI"))+ "__";
							
							soluong = rsLaySP.getDouble("SOLUONG");	
							dongia= rsLaySP.getDouble("DONGIA");
							vat = rsLaySP.getDouble("VAT");
							ck = rsLaySP.getDouble("CHIETKHAU");
							
							sp_thanhtien=soluong*dongia;
							System.out.println("SL"+ soluong+"__ don gia: "+dongia+"= thanhtien: "+sp_thanhtien);
							sp_tienck_banhang=sp_thanhtien*ck/100;
							sp_tienthue = (sp_thanhtien-sp_tienck_banhang)*vat/100;
							sp_thanhtienavat=sp_thanhtien-sp_tienck_banhang+sp_tienthue;
																					
							spTIENCHIETKHAU +=sp_tienck_banhang+"__";
							spTIENTHUE+=sp_tienthue+"__";
							spTHANHTIEN+= sp_thanhtienavat + "__";
							
							thanhtien+=sp_thanhtien;
							System.out.println("thanhtien: "+ thanhtien);
							tienck_banhang+=sp_tienck_banhang;
							tienthue+=sp_tienthue;							
						}
						rsLaySP.close();
						
						if(spMA.trim().length() > 0)
						{
							spID = spID.substring(0, spID.length() - 2);
							this.spId = spID.split("__");
							
							System.out.println("IDIDID: "+spID);
							spMA = spMA.substring(0, spMA.length() - 2);
							this.spMa = spMA.split("__");
							
							spTEN = spTEN.substring(0, spTEN.length() - 2);
							this.spTen = spTEN.split("__");
							
							spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
							this.spDonvi = spDONVI.split("__");
							
							spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
							this.spSoluong = spSOLUONG.split("__");							
							
							spDONGIA = spDONGIA.substring(0, spDONGIA.length() - 2);
							this.spDongia = spDONGIA.split("__");
							
							spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
							this.spChietkhau = spCHIETKHAU.split("__");
							
							spVAT = spVAT.substring(0, spVAT.length() - 2);
							this.spVat = spVAT.split("__");
							
							spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
							this.spTienVat = spTIENTHUE.split("__");
							
							spTIENCHIETKHAU =spTIENCHIETKHAU.substring(0, spTIENCHIETKHAU.length() - 2);
							this.spTienCK=spTIENCHIETKHAU.split("__");
							
							spTHANHTIEN = spTHANHTIEN.substring(0, spTHANHTIEN.length() - 2);
							this.spThanhtien = spTHANHTIEN.split("__");
							
							spMADONVI = spMADONVI.substring(0, spMADONVI.length() - 2);
							this.spMadonvi = spMADONVI.split("__");
							
							spSOLUONGGOC = spSOLUONG.substring(0, spSOLUONGGOC.length() - 2);
							this.spSoluonggoc = spSOLUONGGOC.split("__");	
						}
						else{
							setSpMa(null);
							setSpTen(null);
						}
						
						this.ThanhTien = thanhtien;
						this.TienCK=tienck_banhang;
						this.TienVat=(thanhtien - tienck_banhang - TienCKThuongMai)*10/100 ;
						this.TienAVAT = thanhtien - tienck_banhang - TienCKThuongMai + tienthue ;
						 
				    }
				    
				}
			    catch (Exception e) 
				{
					e.printStackTrace();
				}
			    
			}
		this.khRs = db.get("select PK_SEQ, TEN from ERP_KHACHHANG where TRANGTHAI = 1 ORDER BY TEN");
			    
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
	
	public void SetNguoiMuaHang(String nguoimuahang) {
		
		this.NguoiMuaHang=nguoimuahang;
	}
	
	public String getNguoiMuaHang() {
		
		return this.NguoiMuaHang;
	}
	
	public boolean Save() { 
		
		this.khRs = db.get("select PK_SEQ, TEN from ERP_KHACHHANG WHERE TRANGTHAI = '1' ORDER BY TEN");
		
		String query="";
		
		if(this.NguoiTao==null || this.NguoiTao.equals("")){
			this.Msg ="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
			System.out.println(this.Msg);
			return false;
		}
		
		if(this.dhId == null||this.dhId.trim().length()<=0){
			this.Msg ="Vui lòng chọn đơn hàng";
			System.out.println(this.Msg);
			return false;
		}
		
		if(checkSoHoaDon()){
			this.Msg = "Số hóa đơn : "+this.SoHoaDon+" đã có, vui lòng chọn lại.";
			System.out.println(this.Msg);
			return false;
		}
			
		
		if(this.spMa == null)
		{
			this.Msg ="Vui lòng kiểm tra lại danh sách sản phẩm xuất hoá đơn";
			System.out.println(this.Msg);
			return false;
		}
		else
		{
			boolean coSP = false;
			System.out.println("CoSP" + spId.length);
			for(int i = 0; i < spId.length; i++)
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					if(Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0)
						coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.Msg = "Vui lòng kiểm tra lại danh sách sản phẩm xuất hoá đơn";
				System.out.println(this.Msg);
				return false;
			}	
		}
		
		
		try
		{
			db.getConnection().setAutoCommit(false);
			//B1: INSERT HÓA ĐƠN
			query=" insert into ERP_HOADON (NGAYXUATHD, TRANGTHAI, NGAYTAO, NGAYSUA,NGUOITAO,NGUOISUA, KYHIEU, SOHOADON, HINHTHUCTT,KHACHHANG_FK, LOAIHOADON, KHO_FK, TIENCKTHUONGMAI,VAT,CHIETKHAU,TONGTIENAVAT, KBH_FK, NGAYGHINHAN, TIENTE_FK, TIGIA) "+
				  " values ('"+this.NgayXuaHd+"','0', '"+this.NgayTao+"','"+this.NgaySua+"','"+this.NguoiTao+"','"+this.NguoiSua+"',N'"+this.Kyhieu+"',N'"+this.SoHoaDon+"',N'"+this.HinhThucTT+"',(SELECT KHACHHANG_FK FROM ERP_DONDATHANG WHERE PK_SEQ='"+this.dhId+"'),'0',"+ 
				  		" (SELECT KHOTT_FK FROM ERP_DONDATHANG WHERE PK_SEQ='"+this.dhId+"')"+","+this.TienCKThuongMai+","+this.TienVat+","+this.ChietKhau+","+this.TienAVAT+","+" (SELECT KBH_FK FROM ERP_DONDATHANG WHERE PK_SEQ='"+this.dhId+"'),'"+this.ngayghino+"', " +
				  		"(SELECT TIENTE_FK FROM ERP_DONDATHANG WHERE PK_SEQ ='"+this.dhId+"'), '1')";
			
			System.out.println("Insert HOADON: " + query);
			if(!db.update(query))
			{
				this.Msg = "Không thể tạo mới HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String hdId = "";
			query = "select SCOPE_IDENTITY() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();
			
			//B2: INSERT VÀO BẢNG ERP_HOADON_DDH
			query = "INSERT ERP_HOADON_DDH(hoadon_fk, ddh_fk) select " + hdId + " , pk_seq from ERP_DONDATHANG where pk_seq in ( " + this.dhId + " ) ";
			System.out.println(query);
			if(!db.update(query))
			{
				this.Msg = "Không thể tạo mới ERP_HOADON_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//B3: INSERT VÀO BẢNG ERP_HOADON_SP
			
			for(int i = 0; i < spId.length; i++)
			{
				query+= spDonvi[i];
				
				
				//CHỈ LƯU SẢN PHẨM NÀO CÓ SỐ LƯỢNG >0
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{											
					query = "INSERT ERP_HOADON_SP( HOADON_FK, SANPHAM_FK, SOLUONG, DONGIA, TIENAVAT,DONVITINH,CHIETKHAU, VAT,TIENCK, DVDL_FK) " +
							" values ('"+ hdId +"' , '" + spId[i] + "',"+spSoluong[i].replaceAll(",", "")+","+spDongia[i].replaceAll(",", "") + "," + spThanhtien[i].replaceAll(",", "")+ 
									", N'"+spDonvi[i]+"',"+spChietkhau[i].replaceAll(",", "")+","+spVat[i].replaceAll(",", "")+","+spTienCK[i].replaceAll(",", "")+",'"+spMadonvi[i]+"')"; 

					System.out.println("Insert ERP_HOADON_SP: " + query);
					if(!db.update(query))
					{
						this.Msg = "Khong the tao moi ERP_HOADON_SP: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
						

			//B4: KIỂM TRA SỐ LƯỢNG SẢN PHẨM ĐÃ XUẤT HẾT HAY CHƯA
			 query=
				 "	 SELECT DDH_SP.SANPHAM_FK,SP.MA,SP.TEN as tensp,SOLUONG - (SELECT isnull(sum(soluong),0) \n"+ 
				 "	  															FROM  ERP_HOADON_SP a   \n"+
				 "																			 inner join ERP_HOADON b on b.PK_SEQ=a.HOADON_FK   \n"+
				 "																		     inner join ERP_HOADON_DDH c on c.HOADON_FK=b.PK_SEQ  \n"+
				 "	  														    WHERE c.DDH_FK=DDH_SP.DONHANG_FK and a.SANPHAM_FK=DDH_SP.SANPHAM_FK and b.TRANGTHAI in ('0', '1')) AS SOLUONG, ISNULL(QUYCACH.SOLUONG1,'1') AS QUYDOI  \n"+
				 "		   	,ISNULL(DVDL.DONVI,'') AS DONVI \n"+ 
				 "   FROM   ERP_DONDATHANG_SP DDH_SP  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK  INNER JOIN ERP_DONDATHANG DDH \n"+  
				 "			ON DDH.PK_SEQ=DDH_SP.DONHANG_FK  LEFT JOIN QUYCACH ON SP.PK_SEQ=QUYCACH.SANPHAM_FK  LEFT JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK=DVDL.PK_SEQ \n"+    
				 "	 WHERE  DDH_SP.SOLUONG >0 AND DONHANG_FK IN ('"+this.dhId+"') \n"+   
				 "			 and  SOLUONG - (SELECT isnull(sum(soluong),0) \n"+ 
				 "							 FROM	erp_hoadon_sp a \n"+   
				 "									inner join ERP_HOADON b on b.pk_Seq=a.hoadon_fk \n"+   
				 "									inner join ERP_HOADON_DDH c on c.hoadon_fk=b.pk_Seq \n"+  
				 "							 WHERE c.ddh_fk=DDH_SP.DONHANG_FK and a.sanpham_fk=ddh_sp.sanpham_fk and b.trangthai in ('0','1')) > 0 \n";			 
			
			 
/*			System.out.println("KIEM TRA: "+query);
			ResultSet kt = db.get(query);
			if(kt!=null)
			{
				if(!kt.next()){
					query="UPDATE ERP_DONDATHANG SET TINHTRANG = 1 WHERE PK_SEQ = '"+this.dhId+"'";
					if(!db.update(query))
					{
						this.Msg = "Khong the cap nhat tinh trang ERP_DONDATHANG: " + query;
						db.getConnection().rollback();
						return false;
					}
				}	
			}*/
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er){
			db.update("rollback");
			this.Msg=er.getMessage();
			er.printStackTrace();
			return false;
		}
		//loadContents();
		return true;
	}
	
	public String getInBangKe(){
		
		if(isInBangKe())
		{
			return inBangKe;
		}else return "0";
	}


	private boolean checkSoHoaDon() {
		
		try{
			String sql=" SELECT SOHOADON FROM ERP_HOADON WHERE SOHOADON='"+this.SoHoaDon+"'  and  TRANGTHAI NOT IN ('2') ";
			System.out.println(sql);
			if(this.Id.length() > 0){
				sql=sql+" and pk_seq NOT IN ("+this.Id+")";
			}
			ResultSet rs=db.get(sql);
			if(rs.next()){
				System.out.println(rs.getString("SOHOADON"));
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
		this.khRs = db.get("select PK_SEQ, TEN from ERP_KHACHHANG WHERE TRANGTHAI = '1' ORDER BY TEN");
		try
		{

			if(this.dhId == null||this.dhId.trim().length()<=0){
				this.Msg ="Vui lòng chọn đơn hàng";
				System.out.println(this.Msg);
				return false;
			}
			
			if(this.NguoiTao==null || this.NguoiTao.equals("")){
				this.Msg ="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				System.out.println(this.Msg);
				return false;
			}
			
			if(checkSoHoaDon()){
				this.Msg = "Số hóa đơn : "+this.SoHoaDon+" đã có, vui lòng chọn lại.";
				System.out.println(this.Msg);
				return false;
			}
			
			
			
			
			
			if(this.spMa == null)
			{
				this.Msg ="Vui lòng kiểm tra lại danh sách sản phẩm xuất hoá đơn";
				System.out.println(this.Msg);
				return false;
			}
			else
			{
				boolean coSP = false;
				System.out.println("CoSP" + spId.length);
				for(int i = 0; i < spId.length; i++)
				{
					if( spSoluong[i].trim().length() > 0 )
					{
						if(Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0)
							coSP = true;
					}
				}
				
				if(!coSP)
				{
					this.Msg = "Vui lòng kiểm tra lại danh sách sản phẩm xuất hoá đơn";
					System.out.println(this.Msg);
					return false;
				}	
			}
						
			 db.getConnection().setAutoCommit(false);
			 
			 //B1. 
	  		 sql ="	UPDATE ERP_HOADON SET NGAYXUATHD ='"+this.NgayXuaHd+"', NGAYSUA='"+this.NgaySua+"', NGUOISUA='"+this.NguoiSua+"', KYHIEU =N'"+this.Kyhieu+"',SOHOADON=N'"+this.SoHoaDon+"', HINHTHUCTT =N'"+this.HinhThucTT+"', KHACHHANG_FK='"+this.khId+"', GHICHU=N'"+this.GhiChu+"'"+
	  		 	  "        , TIENCKTHUONGMAI="+this.TienCKThuongMai+", VAT="+this.TienVat+",CHIETKHAU="+this.ChietKhau+", TONGTIENAVAT = "+this.TienAVAT+
	  		 	  " WHERE PK_SEQ='"+this.Id+"'";
	  		 
				 System.out.println("UPDATE ERP_HOADON: "+sql);
				 if(!db.update(sql)){
					db.update("rollback");
					this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
					return false;
				 }
				 
			//B2.
				 
			sql="delete ERP_HOADON_DDH where hoadon_fk="+this.Id;
			 System.out.println("DELETE ERP_HOADON_DDH: "+sql);
				if(!db.update(sql)){
					db.update("rollback");
					this.Msg="Khong The xoa ERP_HOADON_DDH ,Loi Tren Dong Lenh Sau :" + sql;
					return false;
				}	
			
			//B3.
			sql="delete ERP_HOADON_SP where hoadon_fk="+this.Id;
			 System.out.println("DELETE ERP_HOADON_SP: "+sql);
				if(!db.update(sql)){
					db.update("rollback");
					this.Msg="Khong The xoa ERP_HOADON_SP ,Loi Tren Dong Lenh Sau :" + sql;
					return false;
				}	
			
			//B4.
			sql = "INSERT ERP_HOADON_DDH(hoadon_fk, ddh_fk) select " + this.Id +" ,  pk_seq from ERP_DONDATHANG where pk_seq in ( " + this.dhId + " ) ";
			 System.out.println("INSERT ERP_HOADON_DDH: "+sql);
			if(!db.update(sql))
			{
				this.Msg = "Không thể tạo mới HOADON_DDH " + sql;
				db.getConnection().rollback();
				return false;
			}

			//B5.CHÈN SẢN PHẨM
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{						
					sql = "INSERT ERP_HOADON_SP( HOADON_FK, SANPHAM_FK, SOLUONG, DONGIA, TIENAVAT,DONVITINH,CHIETKHAU, VAT,TIENCK, DVDL_FK) " +
					" values ('"+ Id +"' , '" + spId[i] + "',"+spSoluong[i].replaceAll(",", "")+","+spDongia[i].replaceAll(",", "") + "," + spThanhtien[i].replaceAll(",", "")+ 
							", N'"+spDonvi[i]+"',"+spChietkhau[i].replaceAll(",", "")+","+spVat[i].replaceAll(",", "")+","+spTienCK[i].replaceAll(",", "")+",'"+spMadonvi[i]+"')"; 

					System.out.println("Insert ERP_HOADON_SP: " + sql);
					
					if(!db.update(sql))
					{
						this.Msg = "Khong the tao moi ERP_HOADON_SP: " + sql;
						db.getConnection().rollback();
						return false;
					}
				}
			}
		
			//B6: KIỂM TRA SỐ LƯỢNG SẢN PHẨM ĐÃ XUẤT HẾT HAY CHƯA
			 sql=
				 "	 SELECT DDH_SP.SANPHAM_FK,SP.MA,SP.TEN as tensp,SOLUONG - (SELECT isnull(sum(soluong),0) \n"+ 
				 "	  															FROM  ERP_HOADON_SP a   \n"+
				 "																			 inner join ERP_HOADON b on b.PK_SEQ=a.HOADON_FK   \n"+
				 "																		     inner join ERP_HOADON_DDH c on c.HOADON_FK=b.PK_SEQ  \n"+
				 "	  														    WHERE c.DDH_FK=DDH_SP.DONDATHANG_FK and a.SANPHAM_FK=DDH_SP.SANPHAM_FK and b.TRANGTHAI in ('0', '1')) AS SOLUONG, ISNULL(QUYCACH.SOLUONG1,'1') AS QUYDOI \n"+
				 "		   	,ISNULL(DVDL.DONVI,'') AS DONVI \n"+ 
				 "   FROM   ERP_DONDATHANG_SP DDH_SP  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK  INNER JOIN ERP_DONDATHANG DDH \n"+  
				 "			ON DDH.PK_SEQ=DDH_SP.DONDATHANG_FK  LEFT JOIN QUYCACH ON SP.PK_SEQ=QUYCACH.SANPHAM_FK  LEFT JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK=DVDL.PK_SEQ \n"+    
				 "	 WHERE  DDH_SP.SOLUONG >0 AND DONDATHANG_FK IN (select ddh_fk from ERP_HOADON_DDH where hoadon_fk='"+ this.Id +"') " +			
				 "			 AND  SOLUONG - (SELECT isnull(sum(soluong),0) \n"+ 
				 "							 FROM	erp_hoadon_sp a \n"+   
				 "									inner join ERP_HOADON b on b.pk_Seq=a.hoadon_fk \n"+   
				 "									inner join ERP_HOADON_DDH c on c.hoadon_fk=b.pk_Seq \n"+  
				 "							 WHERE c.ddh_fk=DDH_SP.DONDATHANG_FK and a.sanpham_fk=ddh_sp.sanpham_fk and b.trangthai in ('0','1')) > 0 \n";

			 
			 System.out.println("KTRA_SOLUONG: "+ sql);
		
			
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
	
	public double getTienThucThu() {
		
		return this.TienThucThu;
	}
	
	public void getTienThucThu(double tienthucthu) {
		
		this.TienThucThu=tienthucthu;
	}
	
	public double getTienVat() {
		
		return this.TienVat;
	}
	
	public void setTienVat(double tienvat) {
		
		this.TienVat=tienvat;
	}
	
	public void initdisplay(String Id) {/*
		try{
			String query=
			" SELECT HD.SOHOADON, HD.KYHIEU, HD.NGAYXUATHD, HD.HINHTHUCTT, NPP.MASOTHUE, NPP.DIACHI, HD.NGUOIMUA, NPP.TEN \n"+
			" FROM ERP_HOADON HD LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=HD.NPP_FK \n"+ 
			" WHERE HD.PK_SEQ ='"+Id+"' \n";
			
			ResultSet rs = db.get(query);
			
			if(rs!=null){
				while(rs.next()){
					this.Id=Id;
				}
			}
			
			query=	" SELECT HD.npp_ghinhancongno_fk as ghinhan, isnull(TENKHXHD,'') as TENKHXHD ,isnull(DIACHIKHXHD,'') as DIACHIKHXHD  , " +
							" isnull(MASOTHUEKHXHD,'') as  MASOTHUEKHXHD ,NCC.TEN AS TENNCC,HD.PK_SEQ as sochungtu, isnull(loaihoadon,'0') as loaihoadon , HD.INBANGKE, " +
							"  GHICHU,NOIDUNGCHIETKHAU,HD.PK_SEQ,HD.NGAYXUATHD,HD.PO_MT,HD.NGUOIMUA,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT, isnull(HD.ghichushop1,'') as ghichu1, isnull(HD.ghichushop2,'') as ghichu2, isnull(HD.ghichushop3,'') as ghichu3, "+
							" NPP.TEN AS NPP,HD.NPP_FK, NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA,CHIETKHAU,VAT,TONGTIENAVAT,TONGTIENBVAT,HD.HOANTAT "+
							" FROM ERP_HOADON HD LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=HD.NPP_FK " +
							" LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ=HD.NPP_FK  "+
							" INNER JOIN NHANVIEN NT ON NT.PK_SEQ=HD.NGUOITAO "+
							" INNER JOIN NHANVIEN NS ON NS.PK_SEQ=HD.NGUOISUA WHERE HD.PK_SEQ="+ Id+ "";
			System.out.println("[ErpHoaDon.initdisplay] Get Noi Dung :"+query);
			db=new dbutils();
			rs = db.get(query);
			if(rs!=null){
				
				while (rs.next()) {
					this.Id=Id;
					this.NgaySua=rs.getString("ngaysua");
					this.NgayTao=rs.getString("ngaytao");
					this.NgayXuaHd=rs.getString("NGAYXUATHD");
					this.NppId=rs.getString("npp_fk");
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
					this.ThanhTien= rs.getDouble("tongtienbvat");
					//this.TongTienSauVAT=rs.getDouble("tongtienavat");
					//this.TienSauCK=this.TongTienChuaCK-this.TienCK;
					this.Msg="";	
					this.GhiChu=rs.getString("ghichu");
					this.noidungCK=rs.getString("noidungchietkhau");
					this.HoanTat=rs.getString("HoanTat");
					this.Loaihoadon=rs.getString("loaihoadon");

					this.inBangKe = rs.getString("INBANGKE");
					this.TenKhXhd = rs.getString("TENKHXHD");
					this.DiachiKhXhd = rs.getString("DIACHIKHXHD");
					this.MasothueXhd = rs.getString("MASOTHUEKHXHD");
					this.NPPghinhan=rs.getString("ghinhan");
					
					this.ghichu1 = rs.getString("ghichu1");
					this.ghichu2 = rs.getString("ghichu2");
					this.ghichu3 = rs.getString("ghichu3");
					
					this.sochungtu =  rs.getString("sochungtu");
					if(this.Loaihoadon.equals("6")){
						this.NppTen=rs.getString("TENNCC");
					}
				}
				rs.close();
				
				if(this.Loaihoadon.equals("0")|| this.Loaihoadon.equals("6"))
				{
					//Lấy ghi chú từ đơn đặt hàng
					query = "  select HOADON_FK,GHICHU,SOTIEN FROM ERP_HOADON_GHICHU  " +
					"  WHERE  HOADON_FK = " + this.Id;
						 rs= db.get(query);
					 		
						 int j=0;
						 while(rs.next()){
							 this.Scheme_chietkhau[j]=rs.getString("GHICHU");
							 this.Scheme_ck_thanhtien[j]=rs.getString("SOTIEN");
							 
							 j++;
						 }
						 rs.close();
					
					
					query="SELECT HOADON_FK,DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK="+this.Id;
				}
				else
					
				{
					query="SELECT HOADON_FK,DONHANG_FK as DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK="+this.Id;
				}
				 
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
					
					if(this.Loaihoadon.equals("0")){
					  query="	select ddh.pk_seq as id,ddh.ngaydat from dondathang ddh where ddh.pk_seq in (select ddh_fk from erp_hoadon_ddh where hoadon_fk='"+Id+"')";
					}else{
						query="	select ddh.pk_seq as id, ddh.ngaynhap as ngaydat from donhang ddh where ddh.pk_seq in (select donhang_fk from erp_hoadon_ddh where hoadon_fk='"+Id+"')";
					}
						//System.out.println(sql);
					this.rsddhChuaXuatHD=db.get(query);
				
				query=" SELECT DDH_SP.SANPHAM_FK,SP.MA,SP.TEN as tensp,DONGIA,SOLUONG AS SOLUONG, "+ 
				  	" ISNULL(QUYCACH.SOLUONG1,'1') AS QUYDOI, dvdl.diengiai AS DONVI FROM ERP_HOADON_SP DDH_SP  "+ 
				  	" INNER JOIN SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK  " +
				  	" left join donvidoluong dvdl on dvdl.pk_seq=sp.dvdl_fk "+ 
				  	" LEFT JOIN QUYCACH ON SP.PK_SEQ=QUYCACH.SANPHAM_FK  "+ 
				  	" WHERE DDH_SP.SOLUONG >0 AND HOADON_FK="+this.Id +"order by (soluong* dongia) desc"; 
					
					//tại sao lại lấy từ đơn đặt hàng ?
					
				query=" SELECT DDH_SP.SANPHAM_FK,SP.MA,SP.TEN as tensp,DONGIA,SOLUONG AS SOLUONG,  ISNULL(QUYCACH.SOLUONG1,'1') AS QUYDOI, dvdl.diengiai AS DONVI "+
					 " FROM DONDATHANG_SP DDH_SP   "+
					 "		INNER JOIN SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK   "+
					 "		left join donvidoluong dvdl on dvdl.pk_seq=sp.dvdl_fk  LEFT JOIN QUYCACH ON SP.PK_SEQ=QUYCACH.SANPHAM_FK  "+
					 " WHERE DDH_SP.SOLUONG >0 AND DDH_SP.DONDATHANG_FK in ( SELECT DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK ="+this.Id +") "+
					 " order by (DDH_SP.SOLUONG * DDH_SP.dongia) desc ";
					
			System.out.println("Get San Pham :"+query);
				 rs=this.db.get(query);
				if(rs!=null){
				try{
				while(rs.next()){
					IErpHoaDon_SP sanpham= new ErpHoanDon_SP();
					sanpham.setId(this.Id);
					sanpham.setMaSanPham(rs.getString("ma"));
					sanpham.setTenSanPham(rs.getString("tensp"));
					sanpham.setSoLuong(rs.getInt("soluong"));
					sanpham.setDonGia(rs.getDouble("dongia"));
					
					sanpham.setDonViTinh(rs.getString("DONVI"));
					 
					sanpham.setQuyDoi(rs.getInt("QUYDOI"));
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
	*/}
	
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
	
	
	private String LayDuLieu(String id) {
/*		String query = " SELECT '' as id,'' as noco, '' as sohieutk, '' as sotien, '' as doituong,'' as trungtamcp,'' as trungtamdt ,6 as loai " +
				       " FROM erp_nhanhang tt " +
				       " WHERE tt.PK_SEQ = "+ id +" ";	*/	
		String laytk="";
		String ngayghinhan = "";
		String ngaychungtu= "";
		String loaidonhang = "";
		String khachhangId = "";
		String loaisp = "";
		String kenhbanhangId = "";
		String tienteId = "";
		String tigia = "";
		
		String taikhoanNO_SOHIEU = "";
		String taikhoanCO_SOHIEU = "";
		
		String nam = "";
		String thang = "";
		
		double tienhang = 0;
		double tienvat = 0;
		double chietkhau = 0;
		
		String query = 
			"SELECT HD.PK_SEQ, HD.NGAYXUATHD, HD.NGAYGHINHAN, DH.LOAIDONHANG, HD.KHACHHANG_FK, (HD.TONGTIENAVAT - HD.VAT) + (HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0) )  AS TONGTIENBVAT ,"
		  + " ISNULL(TIGIA,1) AS TIGIA, "
		  + "   ISNULL(HD.TIENCKTHUONGMAI,0) AS TIENCKTHUONGMAI , HD.CHIETKHAU, HD.VAT, ISNULL(HD.TIENTE_FK,100000) as TIENTEID, \n"+
			"      (SELECT TOP 1 b.LOAISANPHAM_FK FROM ERP_HOADON_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ WHERE HOADON_FK = HD.PK_SEQ) as LOAISP_FK , \n"+
			"      KH.KENHBANHANG_FK, \n"+
			"      (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = KH.TAIKHOAN_FK ) AS TAIKHOAN_NOKH \n"+
			"FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
			"				    INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
			"				    INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
			"WHERE HD.PK_SEQ = "+ id +" ";
		System.out.println("Câu lấy DL "+ query);
		
		ResultSet rs = db.get(query);
		
		if(rs!= null)
		{
			try {
					while(rs.next())
					{
						 ngayghinhan = rs.getString("NGAYGHINHAN");
						 ngaychungtu = rs.getString("NGAYXUATHD");
						 loaidonhang = rs.getString("LOAIDONHANG");
						 khachhangId = rs.getString("KHACHHANG_FK");
						 loaisp =  rs.getString("LOAISP_FK");
						 kenhbanhangId =  rs.getString("KENHBANHANG_FK");
						 tienteId =  rs.getString("tienteId");
						 tigia =  rs.getString("tigia");							
											
						 tienhang = rs.getDouble("TONGTIENBVAT");
						 tienvat = rs.getDouble("VAT");
						 chietkhau = rs.getDouble("CHIETKHAU") + rs.getDouble("TIENCKTHUONGMAI") ;
								
						 nam = ngayghinhan.substring(0, 4);
						 thang = ngayghinhan.substring(5, 7);
						
					}
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		
		if(loaidonhang.equals("1"))  // DON HANG BAN
		{
			if(loaisp.equals("100006")) // LOẠI SP LÀ THANHPHAM
			{
				if(tienhang > 0){
					laytk = 
						"	 SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ WHERE a.PK_SEQ = HD.KHACHHANG_FK) AS SOHIEUTAIKHOAN, \n"+ 
						"		(HD.TONGTIENAVAT - HD.VAT) + (HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0) )  AS SOTIEN, (KH.MA+ ' - ' + KH.TEN) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
						" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
						"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
						"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
						" WHERE HD.PK_SEQ = '"+id+"' \n"+
						 
						" UNION ALL \n"+
						 
						"  SELECT N'CÓ' NO_CO, HD.PK_SEQ, \n"+ 
						"		(case when "+ kenhbanhangId +" = '100000' then '51121100' \n"+ 
						"			when "+ kenhbanhangId +" = '100001' then '51121200'  \n"+
						"			when "+ kenhbanhangId +" = '100007' then '51122000'  \n"+
						"			 else '51121300' end) AS SOHIEUTAIKHOAN, \n"+ 
						"		(HD.TONGTIENAVAT - HD.VAT) + (HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0) )  AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+ 
						" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
						"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
						"       INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
						"  WHERE HD.PK_SEQ = '"+id+"' \n";
				}
				
				if(tienvat > 0){
					if(laytk.trim().length()>0 ) laytk += " UNION ALL \n";
					
					laytk += 
					"	 SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ WHERE a.PK_SEQ = HD.KHACHHANG_FK) AS SOHIEUTAIKHOAN, \n"+ 
					"		HD.VAT AS SOTIEN, ( KH.MA+' - '+KH.TEN ) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
					" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
					"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					" WHERE HD.PK_SEQ = '"+id+"' \n"+
					 
					" UNION ALL \n"+
					 
					"  SELECT N'CÓ' NO_CO, HD.PK_SEQ, '33311000' AS SOHIEUTAIKHOAN, \n"+ 
					"		HD.VAT  AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+ 
					" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
					"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"       INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					"  WHERE HD.PK_SEQ = '"+id+"' \n";
				}
				
				if(chietkhau > 0){
					if(laytk.trim().length()>0 ) laytk += " UNION ALL \n";
					
					laytk += 
					"  SELECT N'NỢ' NO_CO, HD.PK_SEQ, \n" +
					 "       (case when "+ kenhbanhangId +" = '100000' then '52110000' " +
			         "       		 when "+ kenhbanhangId +" = '100001' then '52120000' " +
			         "        	 when "+ kenhbanhangId +" = '100007' then '52140000' " +
			         "        	 when "+ kenhbanhangId +" = '100008' then '52130000' " +
					"			end) AS SOHIEUTAIKHOAN, \n"+ 
					"		(HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0))  AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 3 STT, 1 SAPXEP \n"+ 
					" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
					"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"       INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					"  WHERE HD.PK_SEQ = '"+id+"' \n" +
					
					" UNION ALL \n"+
					
					"	 SELECT N'CÓ' NO_CO, HD.PK_SEQ, (SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ WHERE a.PK_SEQ = HD.KHACHHANG_FK) AS SOHIEUTAIKHOAN, \n"+ 
					"		(HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0))  AS SOTIEN, (KH.MA+' -  '+ KH.TEN) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 3 STT, 2 SAPXEP \n"+ 
					" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
					"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					" WHERE HD.PK_SEQ = '"+id+"' \n";
				}
				
			}
		}
		else if(loaidonhang.equals("2") || loaidonhang.equals("3") || loaidonhang.equals("4")) {
			
			if(tienhang > 0){
					
			laytk =
				" SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ WHERE a.PK_SEQ = HD.KHACHHANG_FK) AS SOHIEUTAIKHOAN, \n"+ 
				" (HD.TONGTIENAVAT - HD.VAT) + (HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0) )  AS SOTIEN, " +
				" (KH.MA + ' - ' + KH.TEN) DOITUONG,'' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
				" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
				"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
				"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
				" WHERE HD.PK_SEQ = '"+id+"' \n"+
			 
			 
				" UNION ALL \n"+
			 
			    " SELECT N'CÓ' NO_CO, HD.PK_SEQ,'51122000' AS SOHIEUTAIKHOAN, \n"+ 
				"	(HD.TONGTIENAVAT - HD.VAT) + (HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0) )  AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+ 
				" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
				"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
				"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
				" WHERE HD.PK_SEQ = '"+id+"' \n";
			}
			
			if(chietkhau > 0){
				if(laytk.trim().length() >0) laytk+= " UNION ALL \n";
				laytk += 
					" SELECT N'NỢ' NO_CO, HD.PK_SEQ, , \n"+ 
					 "       (case when "+ kenhbanhangId +" = '100000' then '52110000' " +
			         "       		 when "+ kenhbanhangId +" = '100001' then '52120000' " +
			        "        	 when "+ kenhbanhangId +" = '100007' then '52140000' " +
			         "        	 when "+ kenhbanhangId +" = '100008' then '52130000' " +
			         "       end) as SOHIEUTAIKHOAN, " +
					" (HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0))  AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
					" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
					"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					" WHERE HD.PK_SEQ = '"+id+"' \n" +
					
					" UNION ALL \n"+
					
					" SELECT N'CÓ' NO_CO, HD.PK_SEQ, (SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ WHERE a.PK_SEQ = HD.KHACHHANG_FK) AS SOHIEUTAIKHOAN, \n"+ 
					" (HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0))  AS SOTIEN, (KH.MA+' - '+KH.TEN) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+ 
					" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
					"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					" WHERE HD.PK_SEQ = '"+id+"' \n";
					
					
			}
			
			if(tienvat>0){
				if(laytk.trim().length() >0) laytk+= " UNION ALL \n";
				laytk += 
					"	 SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ WHERE a.PK_SEQ = HD.KHACHHANG_FK) AS SOHIEUTAIKHOAN, \n"+ 
					"		HD.VAT AS SOTIEN, (KH.MA+' - '+KH.TEN) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 3 STT, 1 SAPXEP \n"+ 
					" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
					"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					" WHERE HD.PK_SEQ = '"+id+"' \n"+
					" UNION ALL \n"+
					
					"  SELECT N'CÓ' NO_CO, HD.PK_SEQ, '33311000' AS SOHIEUTAIKHOAN, \n"+ 
					"		HD.VAT  AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 3 STT, 2 SAPXEP \n"+ 
					" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
					"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"       INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					"  WHERE HD.PK_SEQ = '"+id+"' \n";
			}
			
		}
		
		else { //DON HANG NOI BO
			
			if(tienhang > 0){
			laytk =
				" SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ WHERE a.PK_SEQ = HD.KHACHHANG_FK) AS SOHIEUTAIKHOAN, \n"+ 
				" (HD.TONGTIENAVAT - HD.VAT) + (HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0) )  AS SOTIEN, ( KH.MA +' - '+KH.TEN ) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
				" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
				"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
				"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
				" WHERE HD.PK_SEQ = '"+id+"' \n"+
				
				" UNION ALL  \n"+
				
			    " SELECT N'CÓ' NO_CO, HD.PK_SEQ,'51122000' AS SOHIEUTAIKHOAN, \n"+ 
				"	(HD.TONGTIENAVAT - HD.VAT) + (HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0) )  AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+ 
				" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
				"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
				"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
				" WHERE HD.PK_SEQ = '"+id+"' \n";
			}
			
			if(chietkhau > 0){
				if(laytk.trim().length() >0) laytk+= " UNION ALL \n";
				
				laytk +=				 
					" SELECT N'NỢ' NO_CO, HD.PK_SEQ,  \n"+
					"       (case when "+ kenhbanhangId +" = '100000' then '52110000' " +
			        "       		 when "+ kenhbanhangId +" = '100001' then '52120000' " +
			        "        	 when "+ kenhbanhangId +" = '100007' then '52140000' "+
			        "        	 when "+ kenhbanhangId +" = '100008' then '52130000' "+
			        "       end) as SOHIEUTAIKHOAN, " +
					" (HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0))  AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
					" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
					"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					" WHERE HD.PK_SEQ = '"+id+"' \n"+
					" UINON ALL "+
					" SELECT N'CÓ' NO_CO, HD.PK_SEQ, (SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ WHERE a.PK_SEQ = HD.KHACHHANG_FK) AS SOHIEUTAIKHOAN, \n"+ 
					" (HD.CHIETKHAU + ISNULL(HD.TIENCKTHUONGMAI,0))  AS SOTIEN, ( KH.MA+ ' - ' +KH.TEN) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
					" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
					"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					" WHERE HD.PK_SEQ = '"+id+"' \n";
			}
			
			if(tienvat > 0){
				if(laytk.trim().length() >0) laytk+= " UNION ALL \n";
				laytk += 
					" SELECT N'NỢ' NO_CO, HD.PK_SEQ, (SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ WHERE a.PK_SEQ = HD.KHACHHANG_FK) AS SOHIEUTAIKHOAN, \n"+ 
					" HD.VAT  AS SOTIEN, 'KHÁCH HÀNG' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 3 STT, 1 SAPXEP \n"+ 
					" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
					"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"      INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					" WHERE HD.PK_SEQ = '"+id+"' \n"+
					
					"UNION ALL \n"+
					
					"  SELECT N'CÓ' NO_CO, HD.PK_SEQ, '33311000' AS SOHIEUTAIKHOAN, \n"+ 
					"		HD.VAT  AS SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 3 STT, 2 SAPXEP \n"+ 
					" FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+
					"      INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"       INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					"  WHERE HD.PK_SEQ = '"+id+"' \n";
				
			}
		}
		
		if(laytk.trim().length()>0) laytk += " ORDER BY PK_SEQ, STT, SAPXEP ";
		
		if(laytk.trim().length()<=0){
			laytk += 
				" SELECT '' NO_CO, '' PK_SEQ, '' SOHIEUTAIKHOAN, '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP " +
				" FROM ERP_HOADON HD "+
				" WHERE HD.PK_SEQ = '"+id+"'";
		}
		System.out.println(laytk);
		return laytk;
	}
	
	
	public String ChotHoaDon() 
	{	
		String query="";
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			Utility util = new Utility();
			
			//B1: CẬP NHẬT TRẠNG THÁI CHỐT HÓA ĐƠN
			query = "UPDATE  ERP_HOADON SET TRANGTHAI = '1', nguoisua = '" + this.UserId+ "' WHERE PK_SEQ = '" + this.Id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "KHONG THE CHOT HOA DON ,DONG LENH : " + query;
			}
			
			//B2: KIỂM TRA XEM CÓ XUẤT HÓA ĐƠN CHO ĐƠN HÀNG TRƯỚC ĐÓ HAY CHƯA
			query =" SELECT count(HD_DH.DDH_FK) row " +
				   " FROM ERP_HOADON_DDH HD_DH INNER JOIN ERP_HOADON HD ON HD.PK_SEQ= HD_DH.hoadon_fk \n " +
				   " WHERE HD_DH.hoadon_fk='"+this.Id+"' and HD.TRANGTHAI = '1'";
			 System.out.println("KIEMTRA SOLUONG HOADON DAXUAT:"+query);
			 ResultSet rskt = db.get(query);
			 
			 if(rskt!=null)
			 {
				 if(!rskt.next()){
					 //CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG VỀ ĐÃ XUẤT NẾU ĐƠN HÀNG CHƯA XUẤT HÓA ĐƠN LẦN NÀO
					 query=" UPDATE ERP_DONDATHANG SET TRANGTHAI = '5' "+
					       " WHERE PK_SEQ IN (SELECT DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK = '"+this.Id+"')";
					 
					 if(!db.update(query))
						{
							db.getConnection().rollback();
							return "KHONG THE CAP NHAT TRANG THAI ERP_DONDATHANG ,DONG LENH : " + query;
						}
				 }
					 
			 }
			
				//B4: KIỂM TRA SỐ LƯỢNG SẢN PHẨM ĐÃ XUẤT HẾT HAY CHƯA, KHÔNG LẤY SỐ LƯỢNG KHUYẾN MÃI
			 query=
			 "	 SELECT DDH_SP.SANPHAM_FK,SP.MA,SP.TEN as tensp,SOLUONG - (SELECT isnull(sum(soluong),0) \n"+ 
			 "	  															FROM  ERP_HOADON_SP a   \n"+
			 "																			 inner join ERP_HOADON b on b.PK_SEQ=a.HOADON_FK   \n"+
			 "																		     inner join ERP_HOADON_DDH c on c.HOADON_FK=b.PK_SEQ  \n"+
			 "	  														    WHERE c.DDH_FK=DDH_SP.DONDATHANG_FK and a.SANPHAM_FK=DDH_SP.SANPHAM_FK and b.TRANGTHAI in ('0', '1')) AS SOLUONG, ISNULL(QUYCACH.SOLUONG1,'1') AS QUYDOI \n"+
			 "		   	,ISNULL(DVDL.DONVI,'') AS DONVI \n"+ 
			 "   FROM   ERP_DONDATHANG_SP DDH_SP  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK  INNER JOIN ERP_DONDATHANG DDH \n"+  
			 "			ON DDH.PK_SEQ=DDH_SP.DONDATHANG_FK  LEFT JOIN QUYCACH ON SP.PK_SEQ=QUYCACH.SANPHAM_FK  LEFT JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK=DVDL.PK_SEQ \n"+    
			 "	 WHERE  DDH_SP.SOLUONG >0 AND DONDATHANG_FK IN (select ddh_fk from ERP_HOADON_DDH where hoadon_fk='"+ this.Id +"') " +			
			 "			 AND  SOLUONG - (SELECT isnull(sum(soluong),0) \n"+ 
			 "							 FROM	erp_hoadon_sp a \n"+   
			 "									inner join ERP_HOADON b on b.pk_Seq=a.hoadon_fk \n"+   
			 "									inner join ERP_HOADON_DDH c on c.hoadon_fk=b.pk_Seq \n"+  
			 "							 WHERE c.ddh_fk=DDH_SP.DONDATHANG_FK and a.sanpham_fk=ddh_sp.sanpham_fk and b.trangthai in ('0','1')) > 0 \n";
			 
			System.out.println("KIEM TRA: "+query);
			ResultSet kt = db.get(query);
			if(kt!=null)
			{
				if(!kt.next()){
					query="UPDATE ERP_DONDATHANG SET TINHTRANG = '1' WHERE PK_SEQ IN (SELECT DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK ='"+this.Id+"')";
					if(!db.update(query))
					{
						this.Msg = "Khong the cap nhat tinh trang ERP_DONDATHANG: " + query;
						db.getConnection().rollback();
						return "";
					}
				}	
			}
			
			//GHI NHẬN TÀI KHOẢN KẾ TOÁN
			 
			String ngayghinhan = "";
			String ngaychungtu= "";
			String loaidonhang = "";
			String khachhangId = "";
			String loaisp = "";
			String kenhbanhangId = "";
			String tienteId = "";
			String tigia = "";
			
			String taikhoanNO_SOHIEU = "";
			String taikhoanCO_SOHIEU = "";
			
			String nam = "";
			String thang = "";
			
			double tienhang = 0;
			double tienvat = 0;
			double chietkhau = 0;
			
			query = 
				"SELECT HD.PK_SEQ, HD.NGAYXUATHD, HD.NGAYGHINHAN, DH.LOAIDONHANG, HD.KHACHHANG_FK, ISNULL(TIGIA,1) AS TIGIA, "
			  + "      HDSP.TIENHANG, ISNULL(HD.TIENCKTHUONGMAI,0) AS TIENCKTHUONGMAI , HD.VAT, ISNULL(HD.TIENTE_FK,100000) as TIENTEID, \n"+
				"      (SELECT TOP 1 b.LOAISANPHAM_FK FROM ERP_HOADON_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ WHERE HOADON_FK = HD.PK_SEQ) as LOAISP_FK , \n"+
				"      KH.KENHBANHANG_FK, \n"+
				"      (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = KH.TAIKHOAN_FK ) AS TAIKHOAN_NOKH \n"+
				"FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK "
				+ "                 INNER JOIN "
				+ "                 ( SELECT HOADON_FK, SUM(SOLUONG*DONGIA-TIENCK) AS TIENHANG "
				+ "                   FROM ERP_HOADON_SP"
				+ "                   GROUP BY HOADON_FK) HDSP ON HD.Pk_SEQ = HDSP.HOADON_FK \n"+
				"				    INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
				"				    INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
				"WHERE HD.PK_SEQ = "+ this.Id +" ";
			System.out.println("Câu lấy DL "+ query);
			ResultSet rs = db.get(query);
			if(rs!= null)
			{
				while(rs.next())
				{
					 ngayghinhan = rs.getString("NGAYGHINHAN");
					 ngaychungtu = rs.getString("NGAYXUATHD");
					 loaidonhang = rs.getString("LOAIDONHANG");
					 khachhangId = rs.getString("KHACHHANG_FK");
					 loaisp =  rs.getString("LOAISP_FK");
					 kenhbanhangId =  rs.getString("KENHBANHANG_FK");
					 tienteId =  rs.getString("tienteId");
					 tigia =  rs.getString("tigia");
						
										
					 tienhang = rs.getDouble("tienhang");
					 tienvat = rs.getDouble("VAT");
					 chietkhau = rs.getDouble("TIENCKTHUONGMAI") ;
							
					 nam = ngayghinhan.substring(0, 4);
					 thang = ngayghinhan.substring(5, 7);
					
				}
				rs.close();
			}
			
			String queryLayTK = "";
			
			if(loaidonhang.equals("1"))  // DON HANG BAN
			{
				if(loaisp.equals("100006")) // LOẠI SP LÀ THANHPHAM
				{
				 queryLayTK ="SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO,"
						         + "       (case when "+ kenhbanhangId +" = '100000' then '51121100' "
						         + "       		 when "+ kenhbanhangId +" = '100001' then '51121200' "
						         + "        	 when "+ kenhbanhangId +" = '100007' then '51122000' "
						         + "       		 else '51121300' "
						         + "       end) as TAIKHOAN_CO ,"
						         + "       (case when "+ kenhbanhangId +" = '100000' then '52110000' "
						         + "       		 when "+ kenhbanhangId +" = '100001' then '52120000' "
						         + "        	 when "+ kenhbanhangId +" = '100007' then '52140000' "
						         + "        	 when "+ kenhbanhangId +" = '100008' then '52130000' "
						         + "       end) as TAIKHOAN_NOCK "
						         + "FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ  "
						         + "WHERE a.PK_SEQ = "+ khachhangId +" ";
				
				}
				
			}else if(loaidonhang.equals("2") || loaidonhang.equals("3") || loaidonhang.equals("4"))  // BIEU TANG
			{
				 queryLayTK ="SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO,"
				         + "         '51122000' as TAIKHOAN_CO,"
				         + "       (case when "+ kenhbanhangId +" = '100000' then '52110000' "
				         + "       		 when "+ kenhbanhangId +" = '100001' then '52120000' "
				         + "        	 when "+ kenhbanhangId +" = '100007' then '52140000' "
				         + "        	 when "+ kenhbanhangId +" = '100008' then '52130000' "
				         + "       end) as TAIKHOAN_NOCK "
				         + "FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ  "
				         + "WHERE a.PK_SEQ = "+ khachhangId +" ";
				
			}else if(loaidonhang.equals("5") ) // DON HANG NOI BO
			{
				queryLayTK ="SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO,"
				         + "         '51121000' as TAIKHOAN_CO , "
				         + "       (case when "+ kenhbanhangId +" = '100000' then '52110000' "
				         + "       		 when "+ kenhbanhangId +" = '100001' then '52120000' "
				         + "        	 when "+ kenhbanhangId +" = '100007' then '52140000' "
				         + "        	 when "+ kenhbanhangId +" = '100008' then '52130000' "
				         + "       end) as TAIKHOAN_NOCK "
				         + "FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ  "
				         + "WHERE a.PK_SEQ = "+ khachhangId +" ";
			}
			
			System.out.println("5.Query lay tai khoan: " + queryLayTK);
			if(queryLayTK.trim().length()>0)
			{		
				ResultSet tkRs = db.get(queryLayTK);
				if(tkRs != null)
				{
					while(tkRs.next())
						{
							taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
							taikhoanCO_SOHIEU = tkRs.getString("TAIKHOAN_CO");
						    
					
						if(tienhang > 0)
						{
							if(taikhoanNO_SOHIEU.trim().length() <= 0 || taikhoanCO_SOHIEU.trim().length() <= 0)
							{
								tkRs.close();
								this.Msg = "Khách hàng hoặc nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								db.getConnection().rollback();
		
							}
						
						//UPDATE NO-CO NEW
						
							this.Msg = util.Update_TaiKhoan_TheoSoHieu(db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn", this.Id, taikhoanNO_SOHIEU, taikhoanCO_SOHIEU, "",
									Double.toString(tienhang), Double.toString(tienhang), "Khách hàng", khachhangId, "0", "", "", tienteId, "", tigia, Double.toString(tienhang), Double.toString(tienhang), "Hóa đơn - Tiền hàng ");
							if(this.Msg.trim().length()>0)
							{
								tkRs.close();
								this.Msg = "Loi update: " + this.Msg;
								System.out.println("Loi khi chot: " + this.Msg);
								db.getConnection().rollback();
							}
						}
						
						// VAT
						if(tienvat > 0)
						{
	
							taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
							taikhoanCO_SOHIEU = "33311000";
							
							if(taikhoanNO_SOHIEU.trim().length() <= 0 || taikhoanCO_SOHIEU.trim().length() <= 0)
							{
								this.Msg = "Khách hàng hoặc nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								tkRs.close();
								db.getConnection().rollback();
		
							}
						
						
							this.Msg = util.Update_TaiKhoan_TheoSoHieu(db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn", this.Id, taikhoanNO_SOHIEU, taikhoanCO_SOHIEU, "",
									Double.toString(tienvat), Double.toString(tienvat), "Khách hàng", khachhangId, "0", "", "", tienteId, "", tigia, Double.toString(tienvat), Double.toString(tienvat), "Hóa đơn - VAT ");
							if(this.Msg.trim().length()>0)
							{
								tkRs.close();
								this.Msg = "Loi update: " + this.Msg;
								System.out.println("Loi khi chot: " + this.Msg);
								db.getConnection().rollback();
							}
						
						}
						
						// CHIẾT KHẤU
						if(chietkhau > 0)
						{
	
							taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NOCK");
							taikhoanCO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
							
							if(taikhoanNO_SOHIEU.trim().length() <= 0 || taikhoanCO_SOHIEU.trim().length() <= 0)
							{
								this.Msg = "Khách hàng hoặc nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								tkRs.close();
								db.getConnection().rollback();
		
							}
						
						
							this.Msg = util.Update_TaiKhoan_TheoSoHieu(db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn", this.Id, taikhoanNO_SOHIEU, taikhoanCO_SOHIEU, "",
									Double.toString(chietkhau), Double.toString(chietkhau), "Khách hàng", khachhangId, "0", "", "", tienteId, "", tigia, Double.toString(chietkhau), Double.toString(chietkhau), "Hóa đơn -Chiết khấu ");
							if(this.Msg.trim().length()>0)
							{
								tkRs.close();
								this.Msg = "Loi update: " + this.Msg;
								System.out.println("Loi khi chot: " + this.Msg);
								db.getConnection().rollback();
							}
						
						}
						
				  }tkRs.close();
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		}
		catch(Exception er)
		{
			db.update("rollback");
			er.printStackTrace();
			return "LOI KHONG THE CHOT :"+ er.toString();	
		}
	}
		 
	private String CreateTaoPhieuXuatKho(dbutils db, String hdid, String user) {
		try{/*
			
			String msg="";
			
			
			String sql="select ddh.nhanvien_fk ,ddh.loai ,ddh.ncc_fk,hd.NGAYXUATHD,ddh.NPP_FK,ddh.KHOTT_FK ,ddh.pk_seq as ddhid from ERP_HOADON hd inner join ERP_HOADON_DDH hd_ddh on hd.PK_SEQ=hd_ddh.HOADON_FK "+
						" inner join DONDATHANG ddh on ddh.PK_SEQ=hd_ddh.DDH_FK  "+
						" where ddh.loai in (3,4) and  hd.PK_SEQ= "+hdid;
			
			ResultSet rs=db.get(sql);
			String ngayxk="";
			String nppId ="";
			String KHOTT_FK="";
			String lydoxuat="Xuất kho tự động cho hóa đơn :"+hdid;
			// xuất kho ký gửi 100022
			String ddhId="";
			String noidungxuat="100022";
			String ncc_fk ="";
			String loaidh="";
			String nhanvien_fk=null;
			if(rs.next()){
				loaidh=rs.getString("loai");
				ngayxk=rs.getString("NGAYXUATHD");
				nppId=rs.getString("NPP_FK");
				KHOTT_FK=rs.getString("KHOTT_FK");
				ddhId=rs.getString("ddhid");
				ncc_fk=rs.getString("ncc_fk");
				nhanvien_fk=rs.getString("nhanvien_fk");
			}
	    	
			if(loaidh.equals("")){
				return "";
			}
 
	    	String query=" select SANPHAM_FK as spId,SOLUONG  from ERP_HOADON_SP  where HOADON_FK="+hdid;
	    	
	    	List<ISanpham> spList =  new ArrayList<ISanpham>();
 
			System.out.println("query : " + query);
			ResultSet spRs = db.get(query);
					ISanpham sp = null;
					while(spRs.next())
					{
						String spId = spRs.getString("spId");
						String spMa = "";
						String spTen ="";
						String soluong = spRs.getString("soluong");
						sp = new Sanpham(spId, spMa, spTen, "", soluong);
	  
							List<ISpDetail> spDetail = new ArrayList<ISpDetail>();	
							
							if(loaidh.equals("3")){
								//khách hàng ký gửi
								query= "  select SANPHAM_FK, isnull(AVAILABLE, 0) as AVAILABLE, SOLO  , 0 as soluong      " +  
									   "  from ERP_KHOTT_SP_CHITIET_KYGUINPP  a        " +  
									   "  where  a.npp_fk="+nppId+" and a.sanpham_fk ="+spId+"   and a.AVAILABLE > 0 and a.khoTT_fk = " + KHOTT_FK +  
									   "  order by a.ngayhethan asc, a.AVAILABLE asc ";
								
								
							}else if(loaidh.equals("4")){
								//nhanvien ky gui
								query= "  select SANPHAM_FK, isnull(AVAILABLE, 0) as AVAILABLE, SOLO  ,0 as soluong      " +  
								   "  from ERP_KHOTT_SP_CHITIET_KYGUINHANVIEN  a        " +  
								   "  where  a.NHANVIEN_FK="+nhanvien_fk+" and a.sanpham_fk ="+spId+"   and a.AVAILABLE > 0 and a.khoTT_fk = " +KHOTT_FK+  
								   "  order by a.ngayhethan asc, a.AVAILABLE asc ";
								
							}
							double soluongtong=0;
							
							try{
							  soluongtong= Double.parseDouble(soluong);
							}catch(Exception err){
								
							}
							
							System.out.println("Check soluongtong : " + soluongtong);
							System.out.println("Check soluong san pham: " + query);
							
							ResultSet rsSpDetail = db.get(query);
							
								 
								while(rsSpDetail.next())
								{
									ISpDetail  splo =new SpDetail();
									splo.setId(spId);
									splo.setSolo(rsSpDetail.getString("solo"));
									
									double avai= rsSpDetail.getDouble("SOLUONG")+ rsSpDetail.getDouble("AVAILABLE");
							 		
								 		if( soluongtong >0) {
										if(soluongtong < avai){
											splo.setSoluong(""+soluongtong);
											soluongtong=0;
										}else{
											soluongtong=soluongtong-avai;
											splo.setSoluong(""+avai);
										}
										}else{
											splo.setSoluong("0");
										}
							 		 
							 		
							 		splo.setSoluongton(""+avai);
							 		spDetail.add(splo);
								}
								
								rsSpDetail.close();
							
							
							sp.setSpDetailList(spDetail);
					 					
						spList.add(sp);
					}
					spRs.close();
				

					if(spList.size() > 0)
					{
						for(int i = 0; i <  spList.size(); i++)
						{
							  sp = spList.get(i);
							List<ISpDetail> spDetail = sp.getSpDetailList();
							
							double sum = 0;
							for(int j = 0; j < spDetail.size(); j++)
							{
								sum += Double.parseDouble(spDetail.get(j).getSoluong());
							}
							
							if(sum < Double.parseDouble(sp.getSoluong()))
							{
							msg= "+ Sản phẩm " + sp.getId() + " - " + sp.getDiengiai() + ", không đủ số lượng để xuất kho, vui lòng kiểm tra lại \n";
							}
						}
					}
					
			
			//*********************************8----------------*************
			
					if(msg.length() >0){
						return msg;
					}
					 query = "insert ERP_XUATKHO(NGAYXUAT, NGAYCHOT, DONDATHANG_FK, TRAHANGNCC_FK, NOIDUNGXUAT, KHO_FK, LYDOXUAT, GHICHU, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI,LOAIDH,NPP_FK,NHANVIEN_FK ) " +
					" values('" +ngayxk + "', '" + ngayxk + "', " + ddhId + ", null, '" + noidungxuat + "', '" + KHOTT_FK + "', N'" + lydoxuat + "', N'Xuất kho tự động cho hóa đơn :"+hdid+"', " +
							"'" + user + "', '" + user + "', '" + this.getdatetime() + "', '" +this.getdatetime() + "', '1','"+loaidh+"',"+ nppId +","+  nhanvien_fk +")";
		
		if(!db.update(query))
		{
			return "Khong the tao moi phieu xuat kho: " + query;
		}
		
		String xkCurrent = "";
		query = "select IDENT_CURRENT('Erp_XUATKHO') as xkId";
		
		ResultSet rsPxk = db.get(query);						
		if(rsPxk.next())
		{
			xkCurrent = rsPxk.getString("xkId");
			rsPxk.close();
		}
		
		if(spList.size() > 0)
		{
			for(int i = 0; i < spList.size(); i++)
			{
				 sp = spList.get(i);
				query = "insert ERP_XUATKHO_SANPHAM(XUATKHO_FK, SANPHAM_FK, SOLUONG) " +
						"values('" + xkCurrent + "', '" + sp.getId() + "', '" + sp.getSoluong() + "')";
				
				if(!db.update(query))
				{
					return "Khong the tao moi ERP_XUATKHO_SANPHAM: " + query;
					 
				}
 
					List<ISpDetail> spCon = sp.getSpDetailList();
					for(int j = 0; j < spCon.size(); j++)
					{
						ISpDetail detail = spCon.get(j);
						
						query = " insert ERP_XUATKHO_SP_CHITIET(XUATKHO_FK, SANPHAM_FK, SOLO, SOLUONG, BEAN) " +
								" values('" + xkCurrent + "', '" + detail.getId() + "', '" + detail.getSolo() + "', '" + detail.getSoluong() + "', 100000)";
						
						
						
						
						if(!db.update(query))
						{
							return "Khong the tao moi ERP_XUATKHO_SP_CHITIET: " + query;
							 
						}
					 
						
						query=  " update  ERP_KHOTT_SP_CHITIET set soluong = soluong - "+detail.getSoluong()+",AVAILABLE=AVAILABLE-"+detail.getSoluong()+"  " +
						" where SANPHAM_FK="+detail.getId()+" and KHOTT_FK="+KHOTT_FK+" and rtrim(LTRIM(solo))='"+detail.getSolo()+"'";

						if(db.updateReturnInt(query) != 1 ){
							return  "Lỗi dòng lệnh1 : "+query;
							 
						}
						
						query  = "update ERP_KHOTT_SANPHAM set BOOKED=BOOKED- " + detail.getSoluong() + ", soluong = soluong - " + detail.getSoluong() + " " +
						"where khott_fk = '" + KHOTT_FK+ "' and sanpham_fk = '" + detail.getId() + "'";
						
						if(db.updateReturnInt(query) != 1 ){
							return  "Lỗi dòng lệnh2 : "+query;
							 
						}
						
						
						
						
						if(loaidh.equals("3")){
							query=  " update  ERP_KHOTT_SP_CHITIET_KYGUINPP set soluong = soluong - "+detail.getSoluong() +",AVAILABLE=AVAILABLE-"+detail.getSoluong() +"  " +
							" where npp_fk= "+nppId+"  and  SANPHAM_FK="+detail.getId()+" and KHOTT_FK="+KHOTT_FK+" and  rtrim(LTRIM(solo)) ='"+detail.getSolo()+"'";

							if(db.updateReturnInt(query) != 1 ){
								return  "Lỗi dòng lệnh3 : "+query;
								 
							}
							query=  " update  ERP_KHOTT_SANPHAM_KYGUINPP set soluong = soluong - "+detail.getSoluong() +",AVAILABLE=AVAILABLE-"+detail.getSoluong() +"  " +
							" where npp_fk= "+nppId+"  and  SANPHAM_FK="+detail.getId()+" and KHOTT_FK="+KHOTT_FK+" ";

							if(db.updateReturnInt(query) != 1 ){
								return  "Lỗi dòng lệnh4 : "+query;
								 
							}
							
							
						}else if(loaidh.equals("4")){
							query=  " update  ERP_KHOTT_SP_CHITIET_KYGUINHANVIEN set soluong = soluong -"+detail.getSoluong()  +",AVAILABLE=AVAILABLE-"+detail.getSoluong() +"  " +
							" where nhanvien_fk="+nhanvien_fk+"  and SANPHAM_FK="+detail.getId()+" and KHOTT_FK="+KHOTT_FK+" and rtrim(LTRIM(solo))='"+detail.getSolo()+"'";
					  
							if(db.updateReturnInt(query) != 1 ){
								return  "Lỗi dòng lệnh5 : "+query;
								 
							}
							query=  " update  ERP_KHOTT_Sanpham_KYGUINHANVIEN set soluong = soluong -"+detail.getSoluong()  +",AVAILABLE=AVAILABLE-"+detail.getSoluong() +"  " +
							" where nhanvien_fk="+nhanvien_fk+"  and SANPHAM_FK="+detail.getId()+" and KHOTT_FK="+KHOTT_FK+"";
					  
							if(db.updateReturnInt(query) != 1 ){
								return  "Lỗi dòng lệnh 6: "+query;
								 
							}
							
						} 
						 
						

					}
				 
			}
		}
					
					
					
					
		*/}catch(Exception er){
			er.printStackTrace();
			return er.getMessage();
		}
		return "";
		//return"đã test thanh cong";
		
	}
	
	
	private String getdatetime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
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


	
	public void setInBangKe(String inBangKe) {
		
		this.inBangKe = inBangKe;
		
	}


	
	public boolean isInBangKe() {
		
		if(this.inBangKe==null||this.inBangKe.length()==0||this.inBangKe.equals("0"))
		{
			return false;
		}
		return true;
	}


	
	public String getNPPghinhan() {
		return this.NPPghinhan;
	}



	public void setNPPghinhan(String id) {
		this.NPPghinhan=id;
		
	}


	
	public ResultSet getRsNPPghinhan() {
		return rsNPPghinhan;
	}


	public String getDonhangId() {
		
		return this.dhId;
	}


	
	public void setDonhangId(String dhId) {
		
		this.dhId=dhId;
	}


	
	public ResultSet getDonhangRs() {
		
		return this.ddhRs;
	}


	
	public void setDonhangRs(ResultSet ddhRs) {
		
		this.ddhRs=ddhRs;
	}


	
	public String[] getSpId() {
		
		return this.spId;
	}


	
	public void setSpId(String[] spId) {
		
		this.spId=spId;
	}


	
	public String[] getSpMa() {
		
		return this.spMa;
	}


	
	public void setSpMa(String[] spMa) {
		this.spMa= spMa;
		
	}


	
	public String[] getSpTen() {
		
		return this.spTen;
	}


	
	public void setSpTen(String[] spTen) {
		
		this.spTen=spTen;
	}


	
	public String[] getSpDonvi() {
		
		return this.spDonvi;
	}


	
	public void setSpDonvi(String[] spDonvi) {
		
		this.spDonvi= spDonvi;
	}


	
	public String[] getSpDongia() {
		
		return this.spDongia;
	}


	
	public void setSpDongia(String[] spDongia) {
		
		this.spDongia=spDongia;
	}


	
	public String[] getSpChietKhau() {
		
		return this.spChietkhau;
	}


	
	public void setSpChietKhau(String[] spChietkhau) {
		
		this.spChietkhau=spChietkhau;
	}


	
	public String[] getSpSoluong() {
		
		return this.spSoluong;
	}


	
	public void setSpSoluong(String[] spSoluong) {
		
		this.spSoluong=spSoluong;
	}


	
	public String[] getSpThanhTien() {
		
		return this.spThanhtien;
	}


	
	public void setSpThanhTien(String[] spThanhtien) {
		
		this.spThanhtien= spThanhtien;
	}

	
	public String[] getSpVat() {
		
		return this.spVat;
	}


	
	public void setSpVat(String[] spVat) {
		
		this.spVat=spVat;
	}


	
	public String[] getSpTienVat() {
		
		return this.spTienVat;
	}


	
	public void setSpTienVat(String[] spTienVat) {
		
		this.spTienVat=spTienVat;
	}


	
	public void DBclose(){
			
			try {
				if(this.rsNPPghinhan != null)
					this.rsNPPghinhan.close();
				if(this.listHoaDon!=null)
					this.listHoaDon.close();
				if(this.rsddhChuaXuatHD!=null)
					this.rsddhChuaXuatHD.close();
				if(this.nppList!=null)
					this.nppList.close();
				if(!(this.db == null)){
					this.db.shutDown();
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}


	
	public double getTienCKThuongMai() {
		
		return this.TienCKThuongMai;
	}


	
	public void setTienCKThuongMai(double tienckthuongmai) {
		
		this.TienCKThuongMai=tienckthuongmai;
	}


	
	public String[] getSpTienCK() {
		
		return this.spTienCK;
	}


	
	public void setSpTienCK(String[] spTienCK) {
		
		this.spTienCK=spTienCK;
	}


	
	public double getThanhTien() {
		
		return this.ThanhTien;
	}


	
	public void seThanhTien(double thanhtien) {
		
		this.ThanhTien=thanhtien;
	}



	public void CreateRsNPPghinhan() {
		
		
	}



	public String getkhId() {
		
		return this.khId;
	}

	public void setkhId(String khId) {
		
		this.khId=khId;
	}

	public ResultSet getkhRs() {
		
		return this.khRs;
	}

	public void setkhRs(ResultSet khRs) {
		
		this.khRs= khRs;
	}
	
	public String getNgayghino() {
		
		return this.ngayghino;
	}
	
	public void setNgayghino(String ngayghino) {
		
		this.ngayghino = ngayghino;
	}


	
	public String[] getSpMaDonvi() {
		
		return this.spMadonvi;
	}


	
	public void setSpMaDonvi(String[] spMaDonvi) {
		
		this.spMadonvi = spMaDonvi;
	}


	
	public List<IThongTinHienThi> getHienthiList() 
	{
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) 
	{
		this.hienthiList = hienthiList;
	}


	
	public String[] getSpSoluonggoc() {
		 
		return this.spSoluonggoc;
	}


	
	public void setSpSoluonggoc(String[] spSoluonggoc) {
		 
		this.spSoluonggoc = spSoluonggoc;
	}


	
	public String getddhId() {
		
		return this.ddhId;
	}


	
	public void setddhId(String ddhId) {
		
		this.ddhId = ddhId;
	}
	public boolean checkhd(){
		String query ="";
		query = " select HOADON_FK from ERP_HUYHOADONTAICHINH where HOADON_FK='"+this.SoCT+"' and TRANGTHAI != 2 ";
		ResultSet kt = this.db.get(query);
		try{
			if(kt.next()){
				this.Msg=" Hiện đã có hủy hóa đơn số "+this.SoCT+" trong hệ thống, vui lòng kiểm tra danh sách lại !!! ";
				return false;
			}
		}catch(Exception e){ 
			e.printStackTrace();
		}
	
		return true;	
	}
	
	public boolean checkhdthutien(){
		String query ="";
		query = " select HOADON_FK from ERP_THUTIEN_HOADON where HOADON_FK='"+this.SoCT+"'  ";
		ResultSet kt = this.db.get(query);
		try{
			if(kt.next()){
				this.Msg=" Hiện hóa đơn số "+this.SoCT+" đã được thu tiền, không thể xóa hóa đơn này !!! ";
				return false;
			}
		}catch(Exception e){ 
			e.printStackTrace();
		}
	
		return true;	
	}
	public boolean createHuyhoadontaichinh()
	{	
		
		//************* KIỂM TRA ĐÃ TẠO CHƯA & ĐÃ THU TIỀN CHƯA  *************//
		if(checkhd()==false){
			return false;
		}
		if(checkhdthutien()==false){
			return false;
		}
		//*******************************************************************//
		
		
		if(this.Ngayhuy.length()==0){
			this.Msg= " Vui lòng nhập ngày hủy HĐTC";
			return false;
		}
		String query="";
		
		
		try
		{
			//************** THÊM THÔNG TIN  HUY HOA DON ********************************//
			
			db.getConnection().setAutoCommit(false);
		 
			query = " insert into ERP_HUYHOADONTAICHINH(NGAYHUY, HOADON_FK,	NGAYXUATHD	,TRANGTHAI,	NGAYTAO	,NGUOITAO,	KYHIEU,	SOHOADON	,HINHTHUCTT	, "+
					" NGAYSUA	,NGUOISUA,	NPP_FK,KHO_FK	,KBH_FK	,NGUOIMUA	,PO_MT,	CHIETKHAU	,TONGTIENAVAT, "+
					" TONGTIENBVAT,	VAT,	GHICHU	,NOIDUNGCHIETKHAU,	REVERT_FK	,FROM_REVERT	,KHACHHANG_FK,	LOAIHD, "+
					" HOANTAT,	CONGTY_FK,	TIENKHUYENMAI	,TIENTE_FK	,XUATTHEO,	NGAYGHINHANCN, "+
					" DIENGIAI	,HOPDONG_FK,	LOAIHOADON	,TENKHXHD	,DIACHIKHXHD	, "+
					" MASOTHUEKHXHD,	NCC_FK	,TIENCHIETKHAU	,TONGTIENBVAT_NGOAITE	,NGAYGHINHAN	,TIGIA	,TYGIA	, "+
					" TIENBAOHIEM	,TIENVANCHUYEN,	KM_GHICHU,	SOPO	,INVOICE,	GOIDAU	,TIENCKTHUONGMAI )  " +
					" SELECT '"+this.Ngayhuy+"', PK_SEQ,	NGAYXUATHD	,0,	'"+this.getdatetime()+"'	,"+this.UserId+",	KYHIEU,	SOHOADON	,HINHTHUCTT	, "+
					" '"+this.getdatetime()+"'	,"+this.UserId+",	NPP_FK,KHO_FK	,KBH_FK	,NGUOIMUA	,PO_MT,	CHIETKHAU	,TONGTIENAVAT, "+
					" TONGTIENBVAT,	VAT,	GHICHU	,NOIDUNGCHIETKHAU,	REVERT_FK	,FROM_REVERT	,KHACHHANG_FK,	LOAIHD, "+
					" HOANTAT,	CONGTY_FK,	TIENKHUYENMAI	,TIENTE_FK	,XUATTHEO,	NGAYGHINHANCN, "+
					" DIENGIAI	,HOPDONG_FK,	LOAIHOADON	,TENKHXHD	,DIACHIKHXHD	, "+
					" MASOTHUEKHXHD,	NCC_FK	,TIENCHIETKHAU	,TONGTIENBVAT_NGOAITE	,NGAYGHINHAN	,TIGIA	,TYGIA	, "+
					" TIENBAOHIEM	,TIENVANCHUYEN,	KM_GHICHU,	SOPO	,INVOICE,	GOIDAU	,TIENCKTHUONGMAI "+
					" FROM ERP_HOADON where PK_SEQ=  " +this.SoCT;
			
			//System.out.println(" 1. Insert Huy HD : " + query);
			if(!db.update(query))
			{
				this.Msg = "Không thể tạo mới :  " + query;
				db.getConnection().rollback();
				return false;
			}
		 
			  query = "select SCOPE_IDENTITY() as dhId";
			ResultSet rsDh = db.get(query);	
			
			rsDh.next();
			String newId = rsDh.getString("dhId");
		    rsDh.close();
		    
		    
			//************** THÊM THÔNG TIN SP CỦA HOA DON ĐÓ ********************************//
			
			query = " INSERT INTO ERP_HUYHOADONTAICHINH_SP ( HUYHOADONTAICHINH_FK,HOADON_FK,	SANPHAM_FK	,SOLUONG	,DONGIA,	TIENBVAT,	VAT	,TIENAVAT	,DONVITINH,	SCHEME	, "+
					" CHIETKHAU,	DONGIAVIET,	GHICHU,	INRAHD,	DVDL_FK	,TIENCK ) "+
				    " SELECT "+newId+",HOADON_FK,	SANPHAM_FK	,SOLUONG	,DONGIA,	TIENBVAT,	VAT	,TIENAVAT	,DONVITINH,	SCHEME	, "+
					" CHIETKHAU,	DONGIAVIET,	GHICHU,	INRAHD,	DVDL_FK	,TIENCK FROM ERP_HOADON_SP WHERE HOADON_FK="+this.SoCT;
			//System.out.println("2. Insert SP Huy HD :" + query);
			if(!db.update(query))
			{
				this.Msg = "Không thể tạo mới :  " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query=" INSERT INTO ERP_HUYHOADONTAICHINH_SPKM ( HUYHOADONTAICHINH_FK,HOADON_FK,SANPHAM_FK,SOLUONG,DONGIA,TIENBVAT,VAT,TIENAVAT,DONVITINH,SCHEME,CHIETKHAU) "+
				  " SELECT "+newId+",A.HOADON_FK,A.SANPHAM_FK,A.SOLUONG,A.DONGIA,A.TIENBVAT,A.VAT,A.TIENAVAT,A.DONVITINH,A.SCHEME,A.CHIETKHAU "+ 
				  " FROM ERP_HOADON_SPKM A WHERE HOADON_FK="+this.SoCT;
					if(!db.update(query))
					{
						this.Msg = "Không thể tạo mới :  " + query;
						db.getConnection().rollback();
						return false;
					}
					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
					
		}
		catch (Exception e)
		{
			db.update("rollback");
			this.Msg = "Lỗi: " + e.getMessage();
			return false;
		}

		return true;
	}

	public boolean updateHuyhoadontaichinh()
	{
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "Update huyhoadontaichinh set ngaysua = '" + getdatetime() + "', nguoisua = '" + this.UserId + "' where pk_seq = '" + this.Id + "' ";

			if(!db.update(query))
			{
				this.Msg= "Không thể tạo mới Huyhoadontaichinh: " + query;
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}

		catch (Exception e)
		{
			this.Msg = "Lỗi: " + e.getMessage();
			try
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}

		return true;
	}


	
	public String getSoCT() {
		
		return this.SoCT;
	}


	
	public void setSoCT(String SoCT) {
		
		this.SoCT=SoCT;
	}


	
	public String getNgayHuy() {
		
		return this.Ngayhuy;
	}


	
	public void setNgayHuy(String NgayHuy) {
		
		this.Ngayhuy=NgayHuy;
	}


	
	public void SetTenHangHoaDichVu(String tenhanghoadichvu) {
		
		this.tenhanghoadichvu = tenhanghoadichvu;
	}


	
	public String getTenHangHoaDichVu() {
		
		return this.tenhanghoadichvu;
	}


	
	public String getDiachigiaohang() {
		
		return this.diachigiaohang;
	}


	
	public void setDiachigiaohang(String diachigiaohang) {
		
		this.diachigiaohang = diachigiaohang;
	}


	
	public void SetSoHoaDon2(String sohoadon2) {
		
		this.sohoadon2 = sohoadon2;
	}


	
	public String getSoHoaDon2() {
		
		return this.sohoadon2;
	}


	
	public String getkhxhd() {
		
		return this.khxhd;
	}


	
	public void setkhxhd(String khxhd) {
		
		this.khxhd = khxhd;
	}


	
	public String getDonVi() {
		
		return this.donvi;
	}


	
	public void setDonVi(String donvi) {
		
		this.donvi = donvi;
	}


	
	public String getDiaChi() {
		
		return this.diachi;
	}


	
	public void setDiaChi(String diachi) {
		this.diachi = diachi;
		
	}


	
	public String getMST() {
		
		return this.masothue;
	}


	
	public void setMST(String masothue) {
		
		this.masothue = masothue;
	}


	
	public void setNguoiMuaHang(String nguoimuahang) {
		
		this.nguoimuahang = nguoimuahang;
	}


	
	public String[] getSpDongiagiamgia() {
	
		return this.spDongiagiamgia;
	}


	
	public void setSpDongiagiamgia(String[] spDongiagiamgia) {
	
		this.spDongiagiamgia = spDongiagiamgia;
	}


	
	public String[] getSpKhoTT() {
	
		return this.spKhoTT;
	}


	
	public void setSpKhoTT(String[] spKhoTT) {
	
		this.spKhoTT = spKhoTT;
	}


	
	public String[] getSpnr() {
	
		return this.spnr;
	}


	
	public void setSpnr(String[] spnr) {
	
		this.spnr = spnr;
	}



}
