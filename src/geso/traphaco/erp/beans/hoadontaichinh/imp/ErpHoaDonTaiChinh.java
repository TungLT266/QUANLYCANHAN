package geso.traphaco.erp.beans.hoadontaichinh.imp;

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

/*import geso.TraphacoERP.center.beans.donmuahang.IERP_DonDatHang_SP;
import geso.TraphacoERP.center.beans.donmuahang.imp.ERP_DonDatHang_SP;*/
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDon_SP;
import geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDonTaiChinh;

public class ErpHoaDonTaiChinh extends Phan_Trang implements IErpHoaDonTaiChinh 
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
	String checkctu;
	String hinhthucxhd;
	String kh_dhId;
	String ttcpId;
	
	String ngayghino;
		
	ResultSet listHoaDon;
	ResultSet rsddhChuaXuatHD;
	ResultSet HinhthucxhdRs;
	ResultSet khoRs;
	ResultSet ttcpRs;
	
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
	String diachikh;
	
	String sotienavat;
	String diachigiaohangtk;
	
	String nguoimuahang;
	String donvi;
	String diachi;
	String masothue;
	
	String khxhd;
	
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
	
	String diachigiaohang;
	
	String khId;
	ResultSet khRs;
	ResultSet ddhRs;
 
	String dhId;
	String tenhanghoadichvu;
	String sohoadon2;
	
	String pxkId;
	
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
		
	private ResultSet nppList;
	
	boolean checkdonhang;
	

	public ResultSet getNppList() {
		return nppList;
	}
	
	
	public void setNppList(ResultSet nppList) {
		this.nppList = nppList;
	}
	
	public String getSochungtu() {
		return this.sochungtu;
	}
	
	public  ErpHoaDonTaiChinh(){
		/*
		 * khoi tao cac doi tuong
		 */
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
		this.NguoiTao = "";
		this.DonDatHang=  new String[0];
		this.POMT="";
		this.poInfo = "";
		this.noidungCK = "";
		this.ghichu1 = "";
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
		this.tenhanghoadichvu = "";
		this.khoId = "";
		this.diachigiaohang = "";
		this.TienAVAT = 0;
		this.sohoadon2 = "";
		this.diachikh = "";
		this.checkctu = "";
		this.hinhthucxhd = "100000";
		this.kh_dhId = "";
		this.sotienavat = "";
		this.diachigiaohangtk = "";
		this.TenKhXhd="";
		this.DiachiKhXhd="";
		this.MasothueXhd="";
		this.khxhd = "";
		this.nguoimuahang = "";
		this.donvi = "";
		this.diachi = "";
		this.masothue = "";
		this.pxkId = "";
		this.ttcpId = "";
		this.hienthiList = new ArrayList<IThongTinHienThi>();
	}
		
	
	public void init() {//ĐÃ CÓ ID - HÓA ĐƠN
		
		try
		{
			this.khRs = db.get("SELECT PK_SEQ, (MA+' - '+TenXuatHD) TEN from ERP_KHACHHANG WHERE TRANGTHAI = '1' ORDER BY TENXUATHD ");
			this.khoRs = db.get("select pk_seq, ten from erp_khott where trangthai=1 and pk_seq in (100003,100004,100012,100013,100014) ");
			this.HinhthucxhdRs = db.get("select PK_SEQ, TEN from ERP_HTXUATHD ORDER BY PK_SEQ");
			
			this.ttcpRs = db.get("select PK_SEQ, (MA+' - ' +TEN) TEN from ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI = 1");
			
			
			//B1.LẤY THÔNG TIN HÓA ĐƠN
			String query=
			
			"  SELECT PK_SEQ, NGAYXUATHD,isnull(NGAYGHINHAN,'') NGAYGHINHAN, KYHIEU,SOHOADON,VAT,isnull(TIENCKTHUONGMAI,0) TIENCKTHUONGMAI ,CHIETKHAU,isnull(GHICHU,'') GHICHU , \n" +
			"		  KHACHHANG_FK, ISNULL(TONGTIENAVAT,0) TONGTIENAVAT , HINHTHUCTT, GHICHU, isnull(KM_GHICHU,'') KM_GHICHU, ISNULL(HANGHOADICHVU,'') HANGHOADICHVU, KHO_FK, ISNULL(DIACHIGIAOHANG, '') DIACHIGIAOHANG, ISNULL(SOHOADONDK, '') SOHOADONDK, isnull(HTXUATHD,0) HTXUATHD, \n"+
			"		  isnull(DONVI,'') DONVI, isnull(DIACHI,'') DIACHI, isnull(MASOTHUE, '') MASOTHUE, isnull(NGUOIMUAHANG, '') NGUOIMUAHANG, isnull(HTXUATHD, 100000)  HTXUATHD, TTCP_FK   \n"+
			"  FROM   ERP_HOADON WHERE PK_SEQ = '"+this.Id+"' and LOAIHOADON = 0 ";
			
			System.out.println("INIT_THONGTINHOADON: "+query);
			
			ResultSet rs= db.get(query);
			if(rs!=null)
			{
				while(rs.next())
				{
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
					this.GhiChu = rs.getString("GHICHU");
					this.ghichu1 = rs.getString("KM_GHICHU");
					this.tenhanghoadichvu = rs.getString("HANGHOADICHVU");
					this.diachigiaohang = rs.getString("DIACHIGIAOHANG");
					this.sohoadon2 = rs.getString("SOHOADONDK");			
					this.khId = rs.getString("KHACHHANG_FK");
					this.khxhd = rs.getString("KHACHHANG_FK");
					this.nguoimuahang = rs.getString("NGUOIMUAHANG");
					this.donvi = rs.getString("DONVI");
					this.diachi = rs.getString("DIACHI");
					this.masothue = rs.getString("MASOTHUE");
					this.hinhthucxhd = 	rs.getString("HTXUATHD");	
					this.ttcpId = rs.getString("TTCP_FK");
				}
			}
			rs.close();
			
			String search = "";
			String ndxuatkho = "";
			String htxuathd = "";
			String loaidh = "";
			
			if(this.khId.length()>0){ 
				search+= " and c.PK_SEQ ='"+this.khId+"' ";
			}			
			
			if(this.hinhthucxhd.equals("100000")) //HÓA ĐƠN BÁN HÀNG
			{
					// XUẤT KHO: NỘI DUNG XUẤT: 100002 - xuất bán hàng
					// HÓA ĐƠN: LOAIHOADON =0, HTXUATHD = 100000: hóa đơn bán hàng
				ndxuatkho = "100002";
				loaidh = "1";
			}
			else
			{
				if(this.hinhthucxhd.equals("100001"))
				{
					// XUẤT KHO: NỘI DUNG XUẤT: 100027 - xuất đơn hàng nội bộ bán hàng
					// HÓA ĐƠN: LOAIHOADON = 0, HTXUATHD = 1: Xuất hóa đơn sử dụng nội bộ bán hàng
					ndxuatkho = "100027";
					loaidh = "2";
				}
				else if(this.hinhthucxhd.equals("100002"))
				{
					// XUẤT KHO: NỘI DUNG XUẤT: 100028 - xuất đơn hàng nội bộ bán hàng
					// HÓA ĐƠN: LOAIHOADON = 0, HTXUATHD = 2: Xuất hóa đơn sử dụng nội bộ khác bán hàng
					
					ndxuatkho = "100028";
					loaidh = "3";
				}
			}
			
			//LẤY PHIẾU XUẤT KHO		
			query =			
				" SELECT distinct A.PK_SEQ, A.NGAYXUAT NGAYDONHANG, C.Ten KHACHHANG, C.PK_SEQ KHACHHANG_FK \n"+ 
				" FROM	 ERP_XUATKHO A INNER JOIN ERP_DONDATHANG B ON A.DONDATHANG_FK = B.PK_SEQ \n"+ 
				"	 	 INNER JOIN ERP_KHACHHANG C ON A.NPP_FK = C.PK_SEQ \n"+	
				"	 	 INNER JOIN ERP_XUATKHO_SANPHAM D ON A.PK_SEQ = D.XUATKHO_FK \n"+
				" WHERE  A.TRANGTHAI IN ( 1, 3, 5 ) AND A.DONDATHANG_FK IS NOT NULL AND A.NOIDUNGXUAT = '"+ndxuatkho+"' \n"+ 	 
				"		 AND D.SANPHAM_FK NOT IN \n"+ 
				"		 (SELECT XK_SP.SANPHAM_FK \n"+ 
				"		  FROM  ERP_HOADON HD INNER JOIN ERP_HOADON_XUATKHO HD_XK ON HD.PK_SEQ = HD_XK.hoadon_fk \n"+ 
				"			    INNER JOIN ERP_XUATKHO_SANPHAM XK_SP ON HD_XK.xuatkho_fk = XK_SP.XUATKHO_FK \n"+
				"				INNER JOIN ERP_XUATKHO XK ON XK_SP.XUATKHO_FK = XK.PK_SEQ  \n"+
				"		  WHERE HD.TRANGTHAI IN ( 0,1 ) AND HD.LOAIHOADON = 0 AND HD_XK.xuatkho_fk = A.PK_SEQ \n"+
				"				AND HD.PK_SEQ NOT IN ("+(this.Id.length() <=0 ? "0": this.Id)+") \n"+
				"				AND XK.NOIDUNGXUAT = '"+ndxuatkho+"' \n"+
				"		  ) \n"+search+
				" GROUP BY A.PK_SEQ, A.NGAYXUAT,  C.Ten, C.PK_SEQ \n";
				
				System.out.println(query);
				this.ddhRs= db.get(query);
				
				//B3. INIT PHIẾU XUẤT KHO
				query = "SELECT XUATKHO_FK FROM ERP_HOADON_XUATKHO WHERE HOADON_FK = '" + this.Id+"'";
			
				ResultSet rsdh = this.db.get(query);
			
				System.out.println("query XUẤT KHO "+query);
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
				"		 isnull(TIENCK,0) TIENCK,  HD_SP.KHOTT_FK, HD_SP.DONGIACK, HD.VAT TIENVAT, HD.TIENCKTHUONGMAI, HD.TONGTIENAVAT, 1 as nr \n"+
				" FROM   ERP_HOADON_SP HD_SP INNER JOIN ERP_SANPHAM SP on HD_SP.SANPHAM_FK = SP.PK_SEQ \n "+
				"		 INNER JOIN ERP_HOADON HD ON HD.PK_SEQ = HD_SP.HOADON_FK \n"+	
				" WHERE  HOADON_FK ='"+this.Id+"' " +
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
					String spNR = "";
			    				    
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
							spNR += (rsLaySP.getString("nr"))+"__";
														
							soluong = rsLaySP.getDouble("SOLUONG");	
							dongia = rsLaySP.getDouble("DONGIA");
							
							sp_dongiagiamgia = rsLaySP.getDouble("DONGIACK");
							sp_thanhtien = rsLaySP.getDouble("TIENAVAT");
							
							thanhtien += sp_thanhtien;
							
							tienthue = rsLaySP.getDouble("TIENVAT");
							tienckthuongmai = rsLaySP.getDouble("TIENCKTHUONGMAI");
							
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
						
						spKHOTT = spKHOTT.substring(0, spKHOTT.length() - 2);
						this.spKhoTT = spKHOTT.split("__");						

						spNR = spNR.substring(0, spNR.length() - 2);
						this.spnr = spNR.split("__");
												
						}
						
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
	
	public ErpHoaDonTaiChinh(String id)
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
		this.tenhanghoadichvu = "";
		this.khId="";
		this.dhId="";
		this.diachigiaohang = "";
		this.TienAVAT = 0;
		this.sohoadon2 = "";
		this.diachigiaohangtk = "";
		this.TenKhXhd="";
		this.DiachiKhXhd="";
		this.MasothueXhd="";
		this.khxhd = "";	
		this.nguoimuahang = "";
		this.donvi = "";
		this.diachi = "";
		this.masothue = "";
		this.ttcpId = "";
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
			this.khRs = db.get("SELECT PK_SEQ, (MA+' - '+TenXuatHD) TEN from ERP_KHACHHANG WHERE TRANGTHAI = '1' ORDER BY TENXUATHD ");
			
			this.khoRs = db.get("select pk_seq, ten from erp_khott where trangthai=1 and pk_seq in (100003,100004,100012,100013,100014) ");
			
			this.HinhthucxhdRs = db.get("select PK_SEQ, TEN from ERP_HTXUATHD ORDER BY PK_SEQ");
			
			this.ttcpRs = db.get("select PK_SEQ, (MA+' - ' +TEN) TEN from ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI = 1");
			
			//B1.LẤY THÔNG TIN HÓA ĐƠN
			String query=
				"  SELECT PK_SEQ, NGAYXUATHD,isnull(NGAYGHINHAN,'') NGAYGHINHAN, KYHIEU,SOHOADON,VAT,isnull(TIENCKTHUONGMAI,0) TIENCKTHUONGMAI ,CHIETKHAU,isnull(GHICHU,'') GHICHU , \n" +
				"		  KHACHHANG_FK, ISNULL(TONGTIENAVAT,0) TONGTIENAVAT , HINHTHUCTT, GHICHU, isnull(KM_GHICHU,'') KM_GHICHU, ISNULL(HANGHOADICHVU,'') HANGHOADICHVU, KHO_FK, ISNULL(DIACHIGIAOHANG, '') DIACHIGIAOHANG, ISNULL(SOHOADONDK, '') SOHOADONDK, isnull(HTXUATHD,0) HTXUATHD, \n"+
				"		  		  isnull(DONVI,'') DONVI, isnull(DIACHI,'') DIACHI, isnull(MASOTHUE, '') MASOTHUE, isnull(NGUOIMUAHANG, '') NGUOIMUAHANG, isnull(HTXUATHD, 100000) HTXUATHD, TTCP_FK \n"+
				"  FROM   ERP_HOADON WHERE PK_SEQ = '"+this.Id+"' and LOAIHOADON = 0 ";
			
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
					this.hinhthucxhd = rs.getString("HTXUATHD");
					this.ttcpId = rs.getString("TTCP_FK");
				}
			}
			rs.close();
			
			String ndxuatkho = "";
			String loaidh = "";
			
			if(this.hinhthucxhd.equals("100000"))
			{
					// XUẤT KHO: NỘI DUNG XUẤT: 100002 - xuất bán hàng
					// HÓA ĐƠN: LOAIHOADON =0, HTXUATHD = 0: hóa đơn bán hàng
				ndxuatkho = "100002";
				loaidh = "1";
			}
			else
			{
				if(this.hinhthucxhd.equals("100001"))
				{
					// XUẤT KHO: NỘI DUNG XUẤT: 100027 - xuất đơn hàng nội bộ bán hàng
					// HÓA ĐƠN: LOAIHOADON = 0, HTXUATHD = 1: Xuất hóa đơn sử dụng nội bộ bán hàng
					ndxuatkho = "100027";
					loaidh = "2";
				}
				else if(this.hinhthucxhd.equals("100002"))
				{
					// XUẤT KHO: NỘI DUNG XUẤT: 100028 - xuất đơn hàng nội bộ bán hàng
					// HÓA ĐƠN: LOAIHOADON = 0, HTXUATHD = 2: Xuất hóa đơn sử dụng nội bộ khác bán hàng				
					ndxuatkho = "100028";
					loaidh = "3";
				}
			}
			
			//B3. LẤY ĐƠN HÀNG || PHIẾU XUẤT KHO CỦA KHÁCH HÀNG					
			if(this.khId.length()>0){
					
					query = " SELECT  distinct A.PK_SEQ, A.NGAYXUAT NGAYDONHANG, C.Ten KHACHHANG, C.PK_SEQ KHACHHANG_FK \n"+
							" FROM 	  ERP_XUATKHO A INNER JOIN ERP_DONDATHANG B ON A.DONDATHANG_FK = B.PK_SEQ \n"+
							"	 	  INNER JOIN ERP_KHACHHANG C ON B.KHACHHANG_FK = C.PK_SEQ \n"+
							" WHERE   B.PK_SEQ IS NOT NULL AND A.NOIDUNGXUAT = '"+ndxuatkho+"' " +
							" 		  AND A.DONDATHANG_FK IN (SELECT B.DONDATHANG_FK \n"+ 
							"								  FROM ERP_HOADON_XUATKHO A INNER JOIN ERP_XUATKHO B ON A.xuatkho_fk = B.PK_SEQ \n"+
							"								  WHERE A.HOADON_FK IN ("+ this.Id+")) \n";
					
					System.out.println(query);
					this.ddhRs= db.get(query);
					
					query = "	SELECT XUATKHO_FK FROM ERP_HOADON_XUATKHO  WHERE HOADON_FK = '" + this.Id+"'";
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
				}
			
				
			//LẤY SẢN PHẨM TRÊN HÓA ĐƠN 	
			query=
			" SELECT SANPHAM_FK SPID, SP.MA, SP.TEN,HD_SP.DONVITINH DONVI,HD_SP.DVDL_FK MADONVI,ISNULL(HD_SP.DONGIA,0) \n " +
			"		 as DONGIA,isnull(HD_SP.VAT,0) VAT, SOLUONG, HD_SP.TIENAVAT, isnull(HD_SP.CHIETKHAU,0) CHIETKHAU, \n " +
			"		 isnull(TIENCK,0) TIENCK,  HD_SP.KHOTT_FK, HD_SP.DONGIACK, HD.VAT TIENVAT, HD.TIENCKTHUONGMAI, HD.TONGTIENAVAT \n"+
			" FROM   ERP_HOADON_SP HD_SP INNER JOIN ERP_SANPHAM SP on HD_SP.SANPHAM_FK = SP.PK_SEQ \n "+
			"		 INNER JOIN ERP_HOADON HD ON HD.PK_SEQ = HD_SP.HOADON_FK \n"+	
			" WHERE  HOADON_FK ='"+this.Id+"' "+
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
	

	
	public void setListHoaDon(String sql) {
		
		String query="";
		if(!sql.equals("")){
			query=sql;
		}else{
			query=" SELECT 	HD.PK_SEQ,HD.NGAYXUATHD,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON , isnull(HD.SOHOADONDK,'') SOHOADONDK ,HD.HINHTHUCTT,KH.TENXUATHD TEN, \n"+
				  " 		NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA ,ISNULL(HD.TONGTIENAVAT,0) AS TONGTIENAVAT, ISNULL(HD.HTXUATHD,0)HTXUATHD  \n"+
				  " FROM 	ERP_HOADON HD LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=HD.KHACHHANG_FK \n"+ 
				  "			INNER JOIN NHANVIEN NT ON NT.PK_SEQ=HD.NGUOITAO \n"+
			      "     	INNER JOIN NHANVIEN NS ON NS.PK_SEQ=HD.NGUOISUA \n " +
			      "	WHERE   HD.LOAIHOADON = 0 \n" ;			
		}
 
		String query_init = createSplittingData_List(20, 10, "  PK_SEQ desc, NGAYXUATHD desc ", query);
		
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
								String sotien = rsKT.getString("SOTIEN") == null ?"0":rsKT.getString("SOTIEN");
									
								kt = new DinhKhoanKeToan(rsKT.getString("PK_SEQ"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),sotien,rsKT.getString("DOITUONG"),
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
						ht.setSohoadon2(rs.getString("SOHOADONDK"));
						ht.sethtxuathoadon(rs.getString("HTXUATHD"));
						
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
		
		this.khRs = db.get("SELECT PK_SEQ, (MA+' - '+TenXuatHD) TEN from ERP_KHACHHANG WHERE TRANGTHAI = '1' ORDER BY TENXUATHD ");
		this.khoRs = db.get("select pk_seq, ten from erp_khott where trangthai=1 and pk_seq in (100004,100012,100014, 100003, 100013) ");		
		//this.HinhthucxhdRs = db.get("select PK_SEQ, TEN from ERP_HTXUATHD ORDER BY PK_SEQ");
		//this.ttcpRs = db.get("select PK_SEQ, (MA+' - ' +TEN) TEN from ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI = 1");
		
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
						double dongiasauck=0;
						
						double vat=0;
						double ck=0;
						
						double sp_thanhtien=0; //SOLUONG*DONGIADATRUCHIETKHAU
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
		
		this.ngayghino = this.NgayXuaHd;
		
		// KIÊM TRA KHÁCH HÀNG TRONG ĐƠN HÀNG ĐÃ CHỌN, CHỈ CHO HIỆN NHỮNG ĐƠN CỦA KHÁCH HÀNG ĐÓ
		String [] kh_dh = this.kh_dhId.split(",") ;
		
		if(this.kh_dhId.trim().length()>0){
			this.khId = kh_dh[0].trim();
		}
		
		
		String query="";		

		//	 LẤY TẤT CẢ CÁC PHIẾU XUẤT KHO TRẠNG THÁI KHÁC CHƯA CHỐT VÀ ĐÃ XÓA  
		// TRẠNG THÁI PXK:	0. chưa chốt, 1. đã chốt, 2. đã xóa, 3.Đã xuất HDTC, 4. Hoàn tất
		
		String search_xk = "";
		
		String ndxuatkho = "";
		String loaidh = "";
		
		
		if(this.khId.length()>0){	
			search_xk+= " AND c.PK_SEQ ='"+this.khId+"' ";
		}
		
		if(this.tungay.trim().length()>0){
			search_xk+= " AND a.NGAYXUAT >= '"+this.tungay+"' ";
		}
		if(this.denngay.trim().length()>0){
			search_xk+= " AND a.NGAYXUAT <= '"+this.denngay+"' ";
		}		
		if(this.khoId.trim().length()>0){
			search_xk+= " AND a.KHO_FK ='"+this.khoId+"'";
		}
				
		if(this.diachigiaohangtk.trim().length()>0){
			search_xk+=" and b.diachigiaohang like N'%"+this.diachigiaohangtk+"%'";
		}
		
		if(this.hinhthucxhd.equals("100000"))
		{
				// XUẤT KHO: NỘI DUNG XUẤT: 100002 - xuất bán hàng
				// HÓA ĐƠN: LOAIHOADON =0, HTXUATHD = 0: hóa đơn bán hàng
			ndxuatkho = "100002";
			loaidh = "1";
		}
		else
		{
			if(this.hinhthucxhd.equals("100001"))
			{
				// XUẤT KHO: NỘI DUNG XUẤT: 100027 - xuất đơn hàng nội bộ bán hàng
				// HÓA ĐƠN: LOAIHOADON = 0, HTXUATHD = 1: Xuất hóa đơn sử dụng nội bộ bán hàng
				ndxuatkho = "100027";
				loaidh = "2";
			}
			else if(this.hinhthucxhd.equals("100002"))
			{
				// XUẤT KHO: NỘI DUNG XUẤT: 100028 - xuất đơn hàng nội bộ bán hàng
				// HÓA ĐƠN: LOAIHOADON = 0, HTXUATHD = 2: Xuất hóa đơn sử dụng nội bộ khác bán hàng				
				ndxuatkho = "100028";
				loaidh = "3";
			}
		}
			
		query =			
				" SELECT distinct A.PK_SEQ, A.NGAYXUAT NGAYDONHANG, C.Ten KHACHHANG, C.PK_SEQ KHACHHANG_FK \n"+ 
				" FROM	 ERP_XUATKHO A INNER JOIN ERP_DONDATHANG B ON A.DONDATHANG_FK = B.PK_SEQ \n"+ 
				"	 	 INNER JOIN ERP_KHACHHANG C ON A.NPP_FK = C.PK_SEQ \n"+	
				"	 	 INNER JOIN ERP_XUATKHO_SANPHAM D ON A.PK_SEQ = D.XUATKHO_FK \n"+
				" WHERE  A.TRANGTHAI IN ( 1, 3, 5 ) AND A.DONDATHANG_FK IS NOT NULL AND A.NOIDUNGXUAT = '"+ndxuatkho+"' \n"+ 	 
				"		 AND D.SANPHAM_FK NOT IN \n"+ 
				"		 (SELECT XK_SP.SANPHAM_FK \n"+ 
				"		  FROM  ERP_HOADON HD INNER JOIN ERP_HOADON_XUATKHO HD_XK ON HD.PK_SEQ = HD_XK.hoadon_fk \n"+ 
				"			    INNER JOIN ERP_XUATKHO_SANPHAM XK_SP ON HD_XK.xuatkho_fk = XK_SP.XUATKHO_FK \n"+
				"				INNER JOIN ERP_XUATKHO XK ON XK_SP.XUATKHO_FK = XK.PK_SEQ  \n"+
				"		  WHERE HD.TRANGTHAI IN ( 0,1 ) AND HD.LOAIHOADON = 0 AND HD_XK.xuatkho_fk = A.PK_SEQ \n"+
				"				AND HD.PK_SEQ NOT IN ("+(this.Id.length() <=0 ? "0": this.Id)+") \n"+
				"				AND XK.NOIDUNGXUAT = '"+ndxuatkho+"' \n"+
				"		  ) \n"+search_xk+
				" GROUP BY A.PK_SEQ, A.NGAYXUAT,  C.Ten, C.PK_SEQ \n";
		
				/*" SELECT distinct A.PK_SEQ, A.NGAYXUAT NGAYDONHANG, C.Ten KHACHHANG, C.PK_SEQ KHACHHANG_FK \n"+ 
				" FROM	 ERP_XUATKHO A INNER JOIN ERP_DONDATHANG B ON A.DONDATHANG_FK = B.PK_SEQ \n"+ 
				"	 	 INNER JOIN ERP_KHACHHANG C ON A.NPP_FK = C.PK_SEQ	\n"+ 
				"		 INNER JOIN \n"+
			 	"		 ( \n"+   
				"			SELECT 	XK.PK_SEQ XUATKHOID,XKSP.SANPHAM_FK AS SPID,XKSP.SOLUONG AS SOLUONG \n"+     
				"			FROM 	ERP_XUATKHO XK 	INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK \n"+    
				"			WHERE 	XK.TRANGTHAI IN ( 1, 3, 5 ) AND  XKSP.SOLUONG > 0   AND XK.NOIDUNGXUAT = '"+ndxuatkho+"' \n"+
				"		)  XK_SOLUONGGOC ON XK_SOLUONGGOC.XUATKHOID = A.PK_SEQ \n"+  
				" 		LEFT JOIN \n"+
			 	" 		( \n"+   
				"			SELECT 	XK.PK_SEQ XUATKHOID,HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,     \n"+
				"					CASE WHEN SP.DVDL_FK = HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG \n"+     
				"			FROM 	ERP_HOADON_SP HDSP      \n"+
				"					INNER JOIN ERP_HOADON HD ON HD.PK_SEQ = HDSP.HOADON_FK \n"+
				"					INNER JOIN ERP_HOADON_XUATKHO HD_XK ON HD.PK_SEQ = HD_XK.hoadon_fk \n"+ 
				"					INNER JOIN ERP_XUATKHO XK ON XK.PK_SEQ = HD_XK.xuatkho_fk \n"+
				"					INNER JOIN ERP_SANPHAM SP ON HDSP.SANPHAM_FK =  SP.PK_SEQ \n"+ 
				"					LEFT JOIN QUYCACH QC ON QC.DVDL2_FK = HDSP.DVDL_FK AND QC.SANPHAM_FK = HDSP.SANPHAM_FK      \n"+
				"			WHERE 	HD.TRANGTHAI IN ( 0,1 ) AND HD.LOAIHOADON = 0 AND HDSP.SOLUONG > 0 AND XK.NOIDUNGXUAT = '"+ndxuatkho+"' \n"+			
				" 					AND HD.PK_SEQ NOT IN ("+(this.Id.length() <=0 ? "0": this.Id)+") AND HD.HTXUATHD = '"+this.hinhthucxhd+"'  \n"+
				"			GROUP BY XK.PK_SEQ , HDSP.SANPHAM_FK  , SP.MA , SP.TEN ,HDSP.SOLUONG,   SP.DVDL_FK, HDSP.DVDL_FK			\n"+
				" 		)  XK_SOLUONGXUATHD ON XK_SOLUONGXUATHD.XUATKHOID = A.PK_SEQ  AND XK_SOLUONGGOC.SPID = XK_SOLUONGXUATHD.SPID \n"+
				" WHERE  A.TRANGTHAI IN ( 1, 3, 5 ) AND A.DONDATHANG_FK IS NOT NULL AND A.NOIDUNGXUAT = '"+ndxuatkho+"' \n"+search_xk+	
				"	GROUP BY 	A.PK_SEQ, A.NGAYXUAT,  C.Ten, C.PK_SEQ \n"+
				" 	HAVING (SUM(XK_SOLUONGGOC.SOLUONG) -  SUM(ISNULL(XK_SOLUONGXUATHD.SOLUONG,0))) > 0	\n"+
				" ORDER BY A.NGAYXUAT DESC ";*/
			
		System.out.println("LOADCONTENTS_INIT_DH: "+query);
		this.ddhRs = db.get(query);		
				
		ResultSet kt = db.get(query);
		
		String [] ddh_init = this.dhId.split(",") ;
		
		int count = 0;
		if(kt!=null){				
			try {
				while(kt.next())
				{
					for(int i =0;i<ddh_init.length;i++){
						if(kt.getString("PK_SEQ").equals(ddh_init[i])){
							count++;
							if(this.NgayXuaHd.trim().length()<=0){
								this.NgayXuaHd = kt.getString("NGAYDONHANG");
								this.ngayghino = kt.getString("NGAYDONHANG");
							}
						}	
					}
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		if(count==0){
			this.dhId = "0";
			this.NgayXuaHd = "";
			this.ngayghino = "";
		}		
		
		// 	LẤY THÔNG TIN ĐƠN HÀNG TỪ PHIẾU XUẤT KHO ĐÃ CHỌN
		
		double CKThuongMai = 0;
		  
		if(this.dhId.length() > 0){
			
			/*if(this.hinhthucxhd.equals("Đơn hàng")){			
			    query=  " SELECT isnull(CKTHUONGMAI,0) CKTHUONGMAI,HINHTHUCTT, GHICHU, isnull(diachigiaohang, '') diachigiaohang, khachhang_fk  FROM ERP_DONDATHANG where PK_SEQ IN ("+this.dhId+")";
			}
			else{*/
			query =  " SELECT isnull(CKTHUONGMAI,0) CKTHUONGMAI,HINHTHUCTT, GHICHU, isnull(diachigiaohang, '') diachigiaohang, khachhang_fk  " +
					 " FROM ERP_DONDATHANG where PK_SEQ IN ( SELECT DONDATHANG_FK FROM ERP_XUATKHO WHERE PK_SEQ IN ("+this.dhId+"))";
			/*}*/
						    
		    String hinhthuctt = "";
		    String ghichu = "";
		    String diachigiaohang = "";
			  
		    ResultSet RsLayCKThuongMai = db.get(query);
	    
		    if(RsLayCKThuongMai!=null)
		    {
		    	try {
					if(RsLayCKThuongMai.next())
					{
						CKThuongMai= RsLayCKThuongMai.getDouble("CKTHUONGMAI");
						hinhthuctt = RsLayCKThuongMai.getString("HINHTHUCTT");
						ghichu = RsLayCKThuongMai.getString("GHICHU");
						diachigiaohang = RsLayCKThuongMai.getString("diachigiaohang");
						this.khxhd =  RsLayCKThuongMai.getString("khachhang_fk");
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		    }			
		    
		    this.HinhThucTT = hinhthuctt;
		    this.GhiChu = ghichu;
		    this.diachigiaohang = diachigiaohang;
		    
		    
		  // ************** 3. HIỆN TẤT CẢ CÁC SẢN PHẨM ****************		    
		    query =
		    " ;WITH    numbered \n"+
    		" as \n"+ 
    		" ( \n"+ 
	    	"	SELECT	DHSP.SANPHAM_FK as SPID, SP.MA, SP.TEN, ISNULL(DH.VAT,0) VAT, isnull(DH.CHIETKHAU,0) CHIETKHAU, \n"+			 
			"			CASE WHEN SLXK_GOC.DVDL_FK = DHSP.DVDL_FK THEN (SUM(SLXK_GOC.SOLUONG) -  SUM(ISNULL(SLXK_DAXUATHD.SOLUONG,0))) \n"+
			"			ELSE (SUM(SLXK_GOC.SOLUONG) -  SUM(ISNULL(SLXK_DAXUATHD.SOLUONG,0)))*QC.SOLUONG2/QC.SOLUONG1  END AS SOLUONG , \n"+ 
			"			DHSP.DONGIA, DHSP.DONVI, DHSP.DVDL_FK MADONVI, DHSP.DONGIACK, ' ' KHOTT_FK,  \n"+ 
			"			ROW_NUMBER() OVER (PARTITION BY SP.MA, DHSP.SANPHAM_FK, DHSP.DONGIA, DHSP.DONVI, DHSP.DVDL_FK, DHSP.DONGIACK ORDER BY SP.MA DESC) as nr \n"+ 	 
			"	FROM \n"+ 
			"	(	SELECT	XK.PK_SEQ XUATKHOID,XK_SP.SANPHAM_FK,SUM(XK_SP.SOLUONG) SOLUONG, SP.DVDL_FK, XK.DONDATHANG_FK \n"+ 
			"		FROM	ERP_XUATKHO XK INNER JOIN ERP_XUATKHO_SANPHAM XK_SP ON XK.PK_SEQ = XK_SP.XUATKHO_FK \n"+ 
			"				INNER JOIN ERP_SANPHAM SP ON XK_SP.SANPHAM_FK =  SP.PK_SEQ \n"+ 	 
			"		WHERE   1 = 1 AND XK.NOIDUNGXUAT = '"+ndxuatkho+"' AND XK.DONDATHANG_FK IS NOT NULL AND XK.TRANGTHAI IN (1,3,5) AND XK.PK_SEQ IN ("+(this.dhId.length() <=0 ? "0": this.dhId)+") \n"+ 
			"		GROUP BY XK.PK_SEQ,XK_SP.SANPHAM_FK, SP.DVDL_FK, XK.DONDATHANG_FK \n"+
			" 	) SLXK_GOC LEFT JOIN \n"+
			"	( \n"+   
			"		SELECT 	distinct HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,SP.DVDL_FK, \n"+     
			"				CASE WHEN SP.DVDL_FK = HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG -- QUY ĐỔI VỀ THEO XUẤT KHO \n"+     
			"		FROM 	ERP_HOADON_SP HDSP  \n"+ 
			"				INNER JOIN ERP_HOADON HD ON HD.PK_SEQ = HDSP.HOADON_FK  \n"+ 
			"				INNER JOIN ERP_SANPHAM SP ON HDSP.SANPHAM_FK =  SP.PK_SEQ  \n"+
			"				LEFT JOIN QUYCACH QC ON QC.DVDL2_FK = HDSP.DVDL_FK AND QC.SANPHAM_FK = HDSP.SANPHAM_FK   \n"+ 
			"		WHERE 	HD.TRANGTHAI IN ( 0, 1) AND HD.LOAIHOADON = 0 AND HDSP.SOLUONG > 0 AND HD.HTXUATHD = '"+this.hinhthucxhd+"' \n"+   
			"				AND HD.PK_SEQ IN ( \n"+ 
			"									SELECT  HD_XK.hoadon_fk \n"+ 
			"		 							FROM	ERP_HOADON_XUATKHO HD_XK INNER JOIN ERP_XUATKHO XK ON HD_XK.XUATKHO_FK = XK.PK_SEQ \n"+ 
			"		 							WHERE   XK.NOIDUNGXUAT = '"+ndxuatkho+"' AND HD_XK.xuatkho_fk IN ("+(this.dhId.length() <=0 ? "0": this.dhId)+") AND HD_XK.hoadon_fk = HD.PK_SEQ \n"+			
			"	) \n"+
			"				AND HD.PK_SEQ NOT IN ("+(this.Id.length() <=0 ? "0": this.Id)+") \n"+			
			"		GROUP BY HDSP.SANPHAM_FK  , SP.MA , SP.TEN ,HDSP.SOLUONG, SP.DVDL_FK, HDSP.DVDL_FK \n"+    
			" 	)  SLXK_DAXUATHD ON SLXK_GOC.SANPHAM_FK = SLXK_DAXUATHD.SPID \n"+
			" 	   INNER JOIN ERP_DONDATHANG_SP DHSP ON SLXK_GOC.SANPHAM_FK = DHSP.SANPHAM_FK AND SLXK_GOC.DONDATHANG_FK = DHSP.DONDATHANG_FK \n"+ 
			" 	   INNER JOIN ERP_DONDATHANG DH ON SLXK_GOC.DONDATHANG_FK = DH.PK_SEQ \n"+ 
			" 	   INNER JOIN ERP_SANPHAM SP ON SLXK_GOC.SANPHAM_FK = SP.PK_SEQ \n"+ 
			" 	   LEFT JOIN  QUYCACH QC ON QC.DVDL2_FK = DHSP.DVDL_FK AND DHSP.SANPHAM_FK = QC.SANPHAM_FK \n"+ 						
			" WHERE DH.LOAIDONHANG = "+loaidh+" AND ISNULL(SLXK_DAXUATHD.SOLUONG,0) = 0  \n"+ 
			" GROUP BY DHSP.SANPHAM_FK, SP.MA, SP.TEN, DH.VAT, isnull(DH.CHIETKHAU,0), DHSP.DONGIA, DHSP.DONVI, DHSP.DVDL_FK, DHSP.DONGIACK, SLXK_GOC.DVDL_FK,QC.SOLUONG2,QC.SOLUONG1 \n"+
			"  HAVING (SUM(SLXK_GOC.SOLUONG) -  SUM(ISNULL(SLXK_DAXUATHD.SOLUONG,0))) >0 \n"+
			" ) \n"+
			"	select * \n"+
			"	FROM    numbered "; 
			
		      	System.out.println("LOADCONTENTS__INIT SP___: "+query);
				 
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
						String spVAT="";
						String spCHIETKHAU = "";
						
						String spKHOTT = "";
						
						String spMADONVI = "";
						String spTIENCHIETKHAU="";
						String spTIENTHUE="";
						String spTHANHTIEN ="";
						
						String spNR = "";
						
						double soluong=0;
						double dongia=0;
						
						double vat=0;
						double ck=0;
						
						double sp_thanhtien=0; 
						double sp_tienck_banhang=0;   
						double sp_tienthue=0; 
						double sp_thanhtienavat=0;
						
						double thanhtien=0;
						double tienck_banhang=0;
						double tienthue=0;
						
						double sp_dongiagiamgia = 0;
											
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
								spKHOTT += (rsLaySP.getString("KHOTT_FK"))+"__";
								spDONGIAGIAMGIA += (rsLaySP.getString("DONGIACK"))+"__";
								spNR += (rsLaySP.getString("nr"))+"__";
															
								soluong = rsLaySP.getDouble("SOLUONG");	
								dongia = Math.round(rsLaySP.getDouble("DONGIA"));
								sp_dongiagiamgia = rsLaySP.getDouble("DONGIACK");
								vat = rsLaySP.getDouble("VAT");
															
								sp_thanhtien = Math.round(soluong * sp_dongiagiamgia);					
								sp_tienthue = Math.round(sp_thanhtien*vat/100);		
								
								spTIENTHUE += sp_tienthue+"__";
								
								spTHANHTIEN += sp_thanhtien+"__";
								
								
								
								thanhtien += sp_thanhtien;
								tienthue += sp_tienthue;	
								
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
								
								spDONGIAGIAMGIA = spDONGIAGIAMGIA.substring(0, spDONGIAGIAMGIA.length() - 2);
								this.spDongiagiamgia =  spDONGIAGIAMGIA.split("__");
								
								spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
								this.spChietkhau = spCHIETKHAU.split("__");
								
								spVAT = spVAT.substring(0, spVAT.length() - 2);
								this.spVat = spVAT.split("__");
								
								spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
								this.spTienVat = spTIENTHUE.split("__");
															
								spTHANHTIEN = spTHANHTIEN.substring(0, spTHANHTIEN.length() - 2);
								this.spThanhtien = spTHANHTIEN.split("__");
								
								spMADONVI = spMADONVI.substring(0, spMADONVI.length() - 2);
								this.spMadonvi = spMADONVI.split("__");
								
								spSOLUONGGOC = spSOLUONG.substring(0, spSOLUONGGOC.length() - 2);
								this.spSoluonggoc = spSOLUONGGOC.split("__");
								
								spKHOTT = spKHOTT.substring(0, spKHOTT.length() - 2);
								this.spKhoTT = spKHOTT.split("__");
								
								spNR = spNR.substring(0, spNR.length() - 2);
								this.spnr = spNR.split("__");
							}
							else{
								setSpMa(null);
								setSpTen(null);
							}
														
							this.ThanhTien = thanhtien;
							this.TienCKThuongMai =  CKThuongMai; 
							this.TienVat=Math.round(tienthue) ;
							this.TienAVAT = Math.round(thanhtien + tienthue) ;	
							
					    }
					    
					}
				    catch (Exception e) 
					{
						e.printStackTrace();
					}
				    
				}
			
		this.khRs = db.get("select PK_SEQ, (MA+' - ' +TEN) TEN from ERP_KHACHHANG where TRANGTHAI = 1 ORDER BY TEN");
		
		this.khoRs = db.get("select pk_seq, ten from erp_khott where trangthai=1 and pk_seq in (100004,100012,100014, 100003, 100013) ");
		
		this.HinhthucxhdRs = db.get("select PK_SEQ, TEN from ERP_HTXUATHD ORDER BY PK_SEQ");
		
		this.ttcpRs = db.get("select PK_SEQ, (MA+' - ' +TEN) TEN from ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI = 1");
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
		
	public boolean Save() { 
		
		this.khRs = db.get("select PK_SEQ, (MA+' - ' +TENXUATHD) TEN from ERP_KHACHHANG WHERE TRANGTHAI = '1' ORDER BY TEN");
		
		String query="";
		
		if(this.NguoiTao==null || this.NguoiTao.equals("")){
			this.Msg ="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
			System.out.println(this.Msg);
			return false;
		}
		
		if(this.dhId == null||this.dhId.trim().length()<=0){
			this.Msg ="Vui lòng chọn phiếu xuất kho";
			System.out.println(this.Msg);
			return false;
		}
				
		
		if(checkSoHoaDon()){
			this.Msg = "Số hóa đơn : "+this.SoHoaDon+" đã có, vui lòng chọn lại.";
			System.out.println(this.Msg);
			return false;
		}
		
		if(this.sohoadon2.trim().length()>0){
			if(checkSoHoaDonDinhKem()){
				this.Msg = "Số hóa đơn đính kèm: "+this.sohoadon2+" đã có, vui lòng chọn lại.";
				System.out.println(this.Msg);
				return false;
			}
		}
			
		if(this.ngayghino == null || this.ngayghino.trim().length()<=0){
			this.ngayghino = this.NgayXuaHd;
		}
		
		
		if(this.spMa == null)
		{
			this.Msg ="Vui lòng kiểm tra lại danh sách sản phẩm xuất hoá đơn";
			System.out.println(this.Msg);
			return false;
		}
		
		if(this.hinhthucxhd == null)
		{
			this.Msg ="Vui lòng chọn nội dung xuất hóa đơn";
			return false;
		}
		
		if(this.hinhthucxhd.equals("100001")||this.hinhthucxhd.equals("100002"))
		{
			if(this.ttcpId.trim().length()<=0)
			{
				this.Msg = "Vui lòng chọn trung tâm chi phí";
				return false;
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			//********* 1: INSERT HÓA ĐƠN *********
			
			double tongtienbvat = this.TienAVAT - this.TienVat;
			
			/*if(this.hinhthucxhd.equals("Đơn hàng")){
					query=	" INSERT INTO ERP_HOADON (NGAYXUATHD, TRANGTHAI, NGAYTAO, NGAYSUA,NGUOITAO,NGUOISUA, KYHIEU, SOHOADON, HINHTHUCTT,KHACHHANG_FK, LOAIHOADON, KHO_FK, TIENCKTHUONGMAI,VAT,CHIETKHAU,TONGTIENAVAT, KBH_FK, NGAYGHINHAN, TIENTE_FK, TIGIA, GHICHU, KM_GHICHU, TONGTIENBVAT, HANGHOADICHVU, DIACHIGIAOHANG, SOHOADONDK, HTXUATHD, DONVI, DIACHI, MASOTHUE, NGUOIMUAHANG  ) \n"+
							" VALUES ('"+this.NgayXuaHd+"','0', '"+this.NgayTao+"','"+this.NgaySua+"','"+this.NguoiTao+"','"+this.NguoiSua+"',N'"+this.Kyhieu+"',N'"+this.SoHoaDon+"',N'"+this.HinhThucTT+"',(SELECT KHACHHANG_FK FROM ERP_DONDATHANG WHERE PK_SEQ = "+this.dhId+" ),'0', "+ 
							" (SELECT KHOTT_FK FROM ERP_DONDATHANG WHERE PK_SEQ='"+this.dhId+"')"+","+this.TienCKThuongMai+","+this.TienVat+","+this.ChietKhau+","+this.TienAVAT+","+" (SELECT KBH_FK FROM ERP_DONDATHANG WHERE PK_SEQ='"+this.dhId+"'),'"+this.ngayghino+"', " +
							" (SELECT TIENTE_FK FROM ERP_DONDATHANG WHERE PK_SEQ ='"+this.dhId+"'), '1', N'"+(this.GhiChu.length() <=0 ? "": this.GhiChu)+"', N'"+(this.ghichu1.length() <=0 ? "": this.ghichu1)+"',"+tongtienbvat+",N'"+this.tenhanghoadichvu+"', N'"+this.diachigiaohang+"', N'"+this.sohoadon2+"', 0, N'"+this.donvi+"', N'"+this.diachi+"', N'"+this.masothue+"', N'"+this.nguoimuahang+"')";
			}
			else{*/
			query=	" INSERT INTO ERP_HOADON (NGAYXUATHD, TRANGTHAI, NGAYTAO, NGAYSUA,NGUOITAO,NGUOISUA, KYHIEU, SOHOADON, HINHTHUCTT,KHACHHANG_FK, LOAIHOADON, KHO_FK, TIENCKTHUONGMAI,VAT,CHIETKHAU,TONGTIENAVAT, KBH_FK, NGAYGHINHAN, TIENTE_FK, TIGIA, GHICHU, KM_GHICHU, TONGTIENBVAT, HANGHOADICHVU, DIACHIGIAOHANG, SOHOADONDK, DONVI, DIACHI, MASOTHUE, NGUOIMUAHANG, HTXUATHD, TTCP_FK  ) \n"+
					" VALUES ('"+this.NgayXuaHd+"','0', '"+this.NgayTao+"','"+this.NgaySua+"','"+this.NguoiTao+"','"+this.NguoiSua+"',N'"+this.Kyhieu+"',N'"+this.SoHoaDon+"',N'"+this.HinhThucTT+"',(SELECT distinct NPP_FK FROM ERP_XUATKHO WHERE PK_SEQ IN ( "+this.dhId+" )),'0', "+ 
					" null "+","+this.TienCKThuongMai+","+this.TienVat+","+this.ChietKhau+","+this.TienAVAT+","+" (SELECT distinct KBH_FK FROM ERP_DONDATHANG WHERE PK_SEQ IN ( SELECT DONDATHANG_FK FROM ERP_XUATKHO WHERE PK_SEQ IN ("+this.dhId+"))),'"+this.ngayghino+"', " +
					" (SELECT distinct TIENTE_FK FROM ERP_DONDATHANG WHERE PK_SEQ IN ( SELECT DONDATHANG_FK FROM ERP_XUATKHO WHERE PK_SEQ IN ("+this.dhId+"))), '1', N'"+(this.GhiChu.length() <=0 ? "": this.GhiChu)+"', N'"+(this.ghichu1.length() <=0 ? "": this.ghichu1)+"',"+tongtienbvat+",N'"+this.tenhanghoadichvu+"', N'"+this.diachigiaohang+"', N'"+this.sohoadon2+"', N'"+this.donvi+"', N'"+this.diachi+"', N'"+this.masothue+"', " +
					"	N'"+this.nguoimuahang+"', "+this.hinhthucxhd+", "+(this.ttcpId.length() <=0 ? "NULL": this.ttcpId)+")";
			/*}*/
						
			System.out.println("SAVE_INSERT_ERP_HOADON: " + query);
			
			if(!db.update(query))
			{
				this.Msg = "Không thể tạo mới HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			// ******** 2: LẤY PK_SEQ HÓA ĐƠN VỪA MỚI TẠO **********
			String hdId = "";
			query = "select SCOPE_IDENTITY() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();
						
			
			// ********* 3. INSERT VÀO BẢNG ERP_HOADON_DDH **********		
			/*if(this.hinhthucxhd.equals("Đơn hàng")){
				query = "INSERT ERP_HOADON_DDH(hoadon_fk, ddh_fk) select " + hdId + " , pk_seq from ERP_DONDATHANG where pk_seq in ( " + this.dhId + " ) ";
			}
			else{*/
				query = "INSERT ERP_HOADON_XUATKHO(hoadon_fk, xuatkho_fk) select " + hdId + " , pk_seq from ERP_XUATKHO where pk_seq in ( " + this.dhId + " ) ";
			/*}*/
			
			if(!db.update(query))
			{
				this.Msg = "Không thể tạo mới: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			// ******** 4. INSERT VÀO BẢNG ERP_HOADON_SP ***********
			
			for(int i = 0; i < spId.length; i++)
			{
				query+= spDonvi[i];
				
				//CHỈ LƯU SẢN PHẨM NÀO CÓ SỐ LƯỢNG >0
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{	
					if(spnr[i].trim().length()>=2) 
					{
						this.Msg = "Không thể lưu hóa đơn đối với 1 sản phẩm có 2 đơn vị tính.  ";
						db.getConnection().rollback();
						return false;
					}
					
					if(spKhoTT[i].trim().length()<=0)
						spKhoTT[i] = "null";
					
					double sp_cktm = Double.parseDouble(spThanhtien[i].replaceAll(",", ""))/this.TienAVAT*this.TienCKThuongMai; //CHIA THEO % GIÁ TRỊ CỦA SẢN PHẨM TRÊN ĐƠN HÀNG --> NHẰM PHỤC VỤ BÁO CÁO --> K QUAN TRỌNG
					
										
					query = "INSERT ERP_HOADON_SP( HOADON_FK, SANPHAM_FK, SOLUONG, DONGIA, TIENAVAT,DONVITINH,CHIETKHAU, VAT,DVDL_FK, DONGIACK, KHOTT_FK, CKTHUONGMAI) " +
							" values ('"+ hdId +"' , '" + spId[i] + "',"+spSoluong[i].replaceAll(",", "")+","+spDongia[i].replaceAll(",", "") + "," + spThanhtien[i].replaceAll(",", "")+ 
									", N'"+spDonvi[i]+"',"+spChietkhau[i].replaceAll(",", "")+","+spVat[i]+","+spMadonvi[i]+", "+spDongiagiamgia[i].replaceAll(",", "")+","+spKhoTT[i]+ ", "+sp_cktm+")"; 

					System.out.println("SAVE_INSERT ERP_HOADON_SP: " + query);
					if(!db.update(query))
					{
						this.Msg = "Không thể tạo mới hóa đơn sản phẩm: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
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
	
	public String getInBangKe(){
		
		if(isInBangKe())
		{
			return inBangKe;
		}else return "0";
	}

	private boolean checkSoHoaDon() {
		
		try{
			String sql= 
			" SELECT COUNT (*) NUM" +
			" FROM ("+
			"		SELECT * FROM ERP_HOADON WHERE SOHOADON = '"+this.SoHoaDon+"' and TRANGTHAI NOT IN (2) ";
					if(this.Id.length() > 0){
						sql=sql+" and pk_seq NOT IN ("+this.Id+")";
					}
			sql+=
			"		UNION ALL \n"+
			"		SELECT * FROM ERP_HOADON WHERE SOHOADONDK = '"+this.SoHoaDon+"' and TRANGTHAI NOT IN (2)";
					if(this.Id.length() > 0){
						sql=sql+" and pk_seq NOT IN ("+this.Id+")";
					}
			sql+= ") a";
						
			ResultSet rs = db.get(sql);
			try {
					rs.next();
					if(rs.getInt("NUM") > 0) {
						return true;
					}
					rs.close();
			} catch(Exception e) { }
			
			rs.close();
		
		}catch(Exception er){
			return false;
		}
		return false;
	}
	
	private boolean checkSoHoaDonDinhKem() {
		
		try{
			
			String sql= 
				" SELECT COUNT (*) NUM" +
				" FROM ("+
				"		SELECT * FROM ERP_HOADON WHERE SOHOADON = '"+this.sohoadon2+"' and TRANGTHAI NOT IN (2) ";
						if(this.Id.length() > 0){
							sql=sql+" and pk_seq NOT IN ("+this.Id+")";
						}
				sql+=
				"		UNION ALL \n"+
				"		SELECT * FROM ERP_HOADON WHERE SOHOADONDK = '"+this.sohoadon2+"' and TRANGTHAI NOT IN (2)";
						if(this.Id.length() > 0){
							sql=sql+" and pk_seq NOT IN ("+this.Id+")";
						}
				sql+= ") a";
			
				System.out.println(sql);
			ResultSet rs = db.get(sql);
			try {
					rs.next();
					if(rs.getInt("NUM") > 0) {
						return true;
					}
					rs.close();
			} catch(Exception e) { }
			
			rs.close();
		
		}catch(Exception er){
			return false;
		}
		return false;
	}
	
	public boolean Edit() {
		
		String sql="";
		this.khRs = db.get("SELECT PK_SEQ, (MA+' - ' +TENXUATHD) TEN FROM ERP_KHACHHANG WHERE TRANGTHAI = '1' ORDER BY TEN");
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			if(this.ngayghino.length() <= 0 || this.ngayghino == null)
			{
				this.Msg="Vui lòng nhập ngày ghi nhận công nợ";
				System.out.println(this.Msg);
				return false;
			}
			
			if(this.dhId == null||this.dhId.trim().length()<=0){
				this.Msg ="Vui lòng chọn phiếu xuất kho";
				System.out.println(this.Msg);
				return false;
			}
			
			if(this.NguoiTao==null || this.NguoiTao.equals("")){
				this.Msg ="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				System.out.println(this.Msg);
				return false;
			}
			
			if(this.ngayghino == null|| this.ngayghino.trim().length() <=0){
				this.ngayghino = this.NgayXuaHd;
			}
			
			if(checkSoHoaDon()){
				this.Msg = "Số hóa đơn : "+this.SoHoaDon+" đã có, vui lòng chọn lại.";
				System.out.println(this.Msg);
				return false;
			}
			
			if(this.sohoadon2.trim().length()>0){
				if(checkSoHoaDonDinhKem()){
					this.Msg = "Số hóa đơn đính kèm : "+this.sohoadon2+" đã có, vui lòng chọn lại.";
					System.out.println(this.Msg);
					return false;
				}
			}
			
			if(this.TienAVAT<=0){
				this.Msg = "Tổng tiền thực thu không được nhỏ hơn 0, xin vui lòng kiểm tra lại.";
				System.out.println(this.Msg);
				return false;
			}
			
			if(this.spMa == null)
			{
				this.Msg ="Vui lòng kiểm tra lại danh sách sản phẩm xuất hoá đơn";
				System.out.println(this.Msg);
				return false;
			}
			
			if(this.hinhthucxhd == null)
			{
				this.Msg ="Vui lòng kiểm tra lại nội dung xuất hóa đơn";
				System.out.println(this.Msg);
				return false;
			}
			

			if(this.hinhthucxhd.equals("100001")||this.hinhthucxhd.equals("100002"))
			{
				if(this.ttcpId.trim().length()<=0)
				{
					this.Msg = "Vui lòng chọn trung tâm chi phí";
					return false;
				}
			}
			
			 // ********** 1.  
			 double tongtienbvat = this.TienAVAT - this.TienVat;
			 
			/* if(this.hinhthucxhd.equals("Đơn hàng")) 
				 {
					 sql ="	UPDATE ERP_HOADON SET NGAYXUATHD ='"+this.NgayXuaHd+"', NGAYSUA='"+this.NgaySua+"', NGUOISUA='"+this.NguoiSua+"', KYHIEU =N'"+this.Kyhieu+"',SOHOADON=N'"+this.SoHoaDon+"', HINHTHUCTT =N'"+this.HinhThucTT+"', KHACHHANG_FK= (SELECT KHACHHANG_FK FROM ERP_DONDATHANG WHERE PK_SEQ = '"+this.dhId+"') "+
		  		 	  "        , TIENCKTHUONGMAI="+this.TienCKThuongMai+", VAT="+this.TienVat+",CHIETKHAU="+this.ChietKhau+", TONGTIENAVAT = "+this.TienAVAT+", GHICHU = N'"+(this.GhiChu.length() <=0 ? "": this.GhiChu)+"', KM_GHICHU = N'"+(this.ghichu1.length() <=0 ? "": this.ghichu1)+"',"+
		  		 	  "			 TONGTIENBVAT = "+tongtienbvat+", KHO_FK = (SELECT KHOTT_FK FROM ERP_DONDATHANG WHERE PK_SEQ ='"+this.dhId+"'), KBH_FK = (SELECT KBH_FK FROM ERP_DONDATHANG WHERE PK_SEQ="+this.dhId+"), TIENTE_FK = (SELECT TIENTE_FK FROM ERP_DONDATHANG WHERE PK_SEQ ='"+this.dhId+"' ), "+
		  		 	  " 		 NGAYGHINHAN ='"+this.ngayghino+"', HANGHOADICHVU =N'"+this.tenhanghoadichvu+"', DIACHIGIAOHANG = N'"+this.diachigiaohang+"', SOHOADONDK = N'"+this.sohoadon2+"', HTXUATHD = 0, DONVI = N'"+this.donvi+"', DIACHI = N'"+this.diachi+"', MASOTHUE = N'"+this.masothue+"', NGUOIMUAHANG = N'"+this.nguoimuahang+"' "+
		  		 	  " WHERE PK_SEQ='"+this.Id+"'";
				 }
			 else 
				 {*/
			 sql =
			  "	UPDATE ERP_HOADON SET NGAYXUATHD ='"+this.NgayXuaHd+"', NGAYSUA='"+this.NgaySua+"', NGUOISUA='"+this.NguoiSua+"', KYHIEU =N'"+this.Kyhieu+"',SOHOADON=N'"+this.SoHoaDon+"', HINHTHUCTT =N'"+this.HinhThucTT+"', KHACHHANG_FK= (SELECT distinct NPP_FK FROM ERP_XUATKHO WHERE PK_SEQ IN ("+this.dhId+")) "+
		 	  "        				, TIENCKTHUONGMAI="+this.TienCKThuongMai+", VAT="+this.TienVat+",CHIETKHAU="+this.ChietKhau+", TONGTIENAVAT = "+this.TienAVAT+", GHICHU = N'"+(this.GhiChu.length() <=0 ? "": this.GhiChu)+"', KM_GHICHU = N'"+(this.ghichu1.length() <=0 ? "": this.ghichu1)+"',"+
		 	  "			 		  TONGTIENBVAT = "+tongtienbvat+",KBH_FK = (SELECT distinct KBH_FK FROM ERP_DONDATHANG WHERE PK_SEQ IN ( SELECT DONDATHANG_FK FROM ERP_XUATKHO WHERE PK_SEQ IN ("+this.dhId+" ))), TIENTE_FK = (SELECT distinct TIENTE_FK FROM ERP_DONDATHANG WHERE PK_SEQ IN ( SELECT DONDATHANG_FK FROM ERP_XUATKHO WHERE PK_SEQ IN ("+this.dhId+") )), "+
		 	  " 		 		  NGAYGHINHAN ='"+this.ngayghino+"', HANGHOADICHVU =N'"+this.tenhanghoadichvu+"', DIACHIGIAOHANG = N'"+this.diachigiaohang+"', SOHOADONDK = N'"+this.sohoadon2+"', DONVI = N'"+this.donvi+"', DIACHI = N'"+this.diachi+"', MASOTHUE = N'"+this.masothue+"', NGUOIMUAHANG = N'"+this.nguoimuahang+"', HTXUATHD = "+this.hinhthucxhd+", "+
		 	  "					  TTCP_FK = "+(this.ttcpId.length() <=0 ? "NULL": this.ttcpId) +
		 	  " WHERE PK_SEQ='"+this.Id+"'";
				/* }
				 */
	  		 
			 System.out.println("UPDATE ERP_HOADON: "+sql);
			 if(!db.update(sql)){
				db.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			 }
				 
			// ********** 2.  
			
			sql="DELETE ERP_HOADON_DDH where hoadon_fk = "+this.Id;
			
				if(!db.update(sql)){
					db.update("rollback");
					this.Msg="Khong The xoa ERP_HOADON_DDH ,Loi Tren Dong Lenh Sau :" + sql;
					return false;
				}	
			
			sql="DELETE ERP_HOADON_XUATKHO where hoadon_fk = "+this.Id;
			
				if(!db.update(sql)){
					db.update("rollback");
					this.Msg="Khong The xoa ERP_HOADON_XUATKHO ,Loi Tren Dong Lenh Sau :" + sql;
					return false;
				}	
					  
			sql="DELETE ERP_HOADON_SP where hoadon_fk="+this.Id;
			
				if(!db.update(sql)){
					db.update("rollback");
					this.Msg="Khong The xoa ERP_HOADON_SP ,Loi Tren Dong Lenh Sau :" + sql;
					return false;
				}				
								
			// ********** 4.  
			/*if(this.hinhthucxhd.equals("Đơn hàng")){
				sql = "INSERT ERP_HOADON_DDH(hoadon_fk, ddh_fk) select " + this.Id +" ,  pk_seq from ERP_DONDATHANG where pk_seq in ( " + this.dhId + " ) ";
			}
			else{*/
				sql = "INSERT ERP_HOADON_XUATKHO(hoadon_fk, xuatkho_fk) select " + this.Id  + " , pk_seq from ERP_XUATKHO where pk_seq in ( " + this.dhId + " ) ";
			/*}*/
						
			if(!db.update(sql))
			{
				this.Msg = "Không thể tạo mới: " + sql;
				db.getConnection().rollback();
				return false;
			}

			
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{					
					if(spnr[i].trim().length()>=2) 
					{
						this.Msg = "Không thể lưu hóa đơn đối với 1 sản phẩm có 2 đơn vị tính.  ";
						db.getConnection().rollback();
						return false;
					}
					
					if(spKhoTT[i].trim().length()<=0)
						spKhoTT[i] = "null";
					
					double sp_cktm = Double.parseDouble(spThanhtien[i].replaceAll(",", ""))/this.TienAVAT*this.TienCKThuongMai;
					
					sql = "INSERT ERP_HOADON_SP(HOADON_FK, SANPHAM_FK, SOLUONG, DONGIA, TIENAVAT,DONVITINH,CHIETKHAU, VAT,DVDL_FK, DONGIACK, KHOTT_FK, CKTHUONGMAI) " +
					" values ('"+ Id +"' , '" + spId[i] + "',"+spSoluong[i].replaceAll(",", "")+","+spDongia[i].replaceAll(",", "") + "," + spThanhtien[i].replaceAll(",", "")+ 
							", N'"+spDonvi[i]+"',"+spChietkhau[i].replaceAll(",", "")+","+spVat[i]+","+spMadonvi[i]+", "+spDongiagiamgia[i].replaceAll(",", "")+","+spKhoTT[i]+","+sp_cktm+")"; 
							
					System.out.println("Insert ERP_HOADON_SP: " + sql);
						
						if(!db.update(sql))
						{
							this.Msg = "Khong the tao moi ERP_HOADON_SP: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
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
	
	public boolean EditSoHoaDon() {
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			if(checkSoHoaDon()){
				this.Msg = "Số hóa đơn : "+this.SoHoaDon+" đã có, vui lòng chọn lại.";
				System.out.println(this.Msg);
				return false;
			}
			
			String  sql ="	UPDATE ERP_HOADON SET SOHOADON = '"+ this.SoHoaDon+"' where PK_SEQ = "+this.Id;
			
			if(!db.update(sql))
			{
				this.Msg = "Khong the cập nhật số hóa đơn " + sql;
				db.getConnection().rollback();
				return false;
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
	
	public void initdisplay(String Id) {
		
		//ĐÃ CÓ ID - HÓA ĐƠN
		try
		{
			this.khRs = db.get("select PK_SEQ, TENXUATHD TEN from ERP_KHACHHANG WHERE TRANGTHAI = '1' ORDER BY TEN");
			//B1.LẤY THÔNG TIN HÓA ĐƠN
			String query=
			"  SELECT PK_SEQ, NGAYXUATHD, isnull(NGAYGHINHAN, '') NGAYGHINHAN , KYHIEU,SOHOADON, VAT,isnull(TIENCKTHUONGMAI,0) TIENCKTHUONGMAI ,CHIETKHAU,isnull(GHICHU,'') GHICHU , KHACHHANG_FK, ISNULL(TONGTIENAVAT,0) TONGTIENAVAT , HINHTHUCTT, GHICHU \n"+
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
					this.GhiChu = rs.getString("GHICHU");
				}
			}
			rs.close();
			
			//B2. LẤY THÔNG TIN ĐƠN HÀNG
			if(this.khId.length()>0){
				query="SELECT a.PK_SEQ,a.NGAYDAT NGAYDONHANG, b.MA+' - '+ b.TEN KHACHHANG FROM ERP_DONDATHANG a inner join ERP_KHACHHANG b ON a.KHACHHANG_FK = b.PK_SEQ WHERE a.PK_SEQ in (SELECT DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK = '" + this.Id+"')";
				System.out.println("INIT_DH: "+query);
				this.ddhRs= db.get(query);
			}	
			
			//LẤY SẢN PHẨM TRÊN HÓA ĐƠN 			
			query=				
				/*" SELECT SANPHAM_FK SPID, SP.MA, SP.TEN,HD_SP.DONVITINH DONVI,HD_SP.DVDL_FK MADONVI,ISNULL(HD_SP.DONGIA,0) as DONGIA," +
				"		 isnull(HD_SP.VAT,0) VAT, SOLUONG, HD_SP.TIENAVAT, isnull(HD_SP.CHIETKHAU,0) CHIETKHAU, isnull(TIENCK,0) TIENCK, HD_SP.KHOTT_FK \n"+
				" FROM ERP_HOADON_SP HD_SP INNER JOIN ERP_SANPHAM SP on HD_SP.SANPHAM_FK = SP.PK_SEQ \n"+
				" WHERE HOADON_FK ='"+Id+"' \n";
			*/
			
			" SELECT SANPHAM_FK SPID, SP.MA, SP.TEN,HD_SP.DONVITINH DONVI,HD_SP.DVDL_FK MADONVI,ISNULL(HD_SP.DONGIA,0) \n " +
			"		 as DONGIA,isnull(HD_SP.VAT,0) VAT, SOLUONG, HD_SP.TIENAVAT, isnull(HD_SP.CHIETKHAU,0) CHIETKHAU, \n " +
			"		 isnull(TIENCK,0) TIENCK,  HD_SP.KHOTT_FK, HD_SP.DONGIACK, HD.VAT TIENVAT, HD.TIENCKTHUONGMAI, HD.TONGTIENAVAT \n"+
			" FROM   ERP_HOADON_SP HD_SP INNER JOIN ERP_SANPHAM SP on HD_SP.SANPHAM_FK = SP.PK_SEQ \n "+
			"		 INNER JOIN ERP_HOADON HD ON HD.PK_SEQ = HD_SP.HOADON_FK \n"+	
			" WHERE  HOADON_FK ='"+Id+"' \n";
			
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
						
						spKHOTT = spKHOTT.substring(0, spKHOTT.length() - 2);
						this.spKhoTT = spKHOTT.split("__");
						}
													
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
		//GHI NHẬN TÀI KHOẢN KẾ TOÁN
		 
		String query="";
		String laytk="";
		String ngayghinhan = "";
		String ngaychungtu= "";
		String loaidonhang = "";
		String khachhangId = "";
		String loaisp = "";
		String tenkh = "";
		String kenhbanhangId = "";
		String tienteId = "";
		String tigia = "";
		
		String taikhoanNO_SOHIEU = "";
		String taikhoanCO_SOHIEU = "";
		
		String htxuathd = "";			
		String ttcp = "";
		
		String nam = "";
		String thang = "";
		
		double tienhang = 0;
		double tienvat = 0;
		double chietkhau = 0;
		
		//HTXUATHD = 0: THEO ĐƠN HÀNG
		//HTXUATHD = 1: THEO XUẤT KHO
				
		/*if(htxuathd.equals("0")){
			query = 
				"SELECT HD.PK_SEQ, HD.NGAYXUATHD, HD.NGAYGHINHAN, DH.LOAIDONHANG, HD.KHACHHANG_FK, ISNULL(TIGIA,1) AS TIGIA, "
			  + "      HDSP.TIENHANG, ISNULL(HD.TIENCKTHUONGMAI,0) AS TIENCKTHUONGMAI , isnull(HD.VAT,0) VAT, ISNULL(HD.TIENTE_FK,100000) as TIENTEID, \n"+
				"      (SELECT TOP 1 b.LOAISANPHAM_FK FROM ERP_HOADON_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ WHERE HOADON_FK = HD.PK_SEQ) as LOAISP_FK , \n"+
				"      HD.KBH_FK, KH.MA + ' - '+ KH.TEN as TENKH, \n"+
				"      (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = KH.TAIKHOAN_FK ) AS TAIKHOAN_NOKH \n"+
				"FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK "
				+ "                 INNER JOIN "
				+ "                 ( SELECT HOADON_FK, SUM(SOLUONG*isnull(DONGIACK,0)) AS TIENHANG "
				+ "                   FROM ERP_HOADON_SP"
				+ "                   GROUP BY HOADON_FK) HDSP ON HD.Pk_SEQ = HDSP.HOADON_FK \n"+
				"				    INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
				"				    INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
				"WHERE HD.PK_SEQ = "+ id +" ";
		}
		else{*/
				query = 
					" SELECT HD.PK_SEQ, HD.NGAYXUATHD, HD.NGAYGHINHAN, DH.LOAIDONHANG, HD.KHACHHANG_FK, ISNULL(TIGIA,1) AS TIGIA, \n"+ 
				    "   	 HDSP.TIENHANG, ISNULL(HD.TIENCKTHUONGMAI,0) AS TIENCKTHUONGMAI , isnull(HD.VAT,0) VAT, ISNULL(HD.TIENTE_FK,100000) as TIENTEID, \n"+ 
				    "  		 (SELECT TOP 1 b.LOAISANPHAM_FK FROM ERP_HOADON_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ WHERE HOADON_FK = HD.PK_SEQ) as LOAISP_FK , \n"+ 
				    "  		  HD.KBH_FK, KH.MA + ' - '+ KH.TEN as TENKH, \n"+ 
				    "  		 (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = KH.TAIKHOAN_FK ) AS TAIKHOAN_NOKH,  TTCP_FK, ISNULL(HTXUATHD, 100000) HTXUATHD  \n"+
				    " FROM ERP_HOADON HD INNER JOIN ERP_HOADON_XUATKHO HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+ 
				    "              INNER JOIN \n"+ 
				    "              ( SELECT HOADON_FK, SUM(SOLUONG*isnull(DONGIACK,0)) AS TIENHANG \n"+ 
				    "                FROM ERP_HOADON_SP \n"+
				    "                GROUP BY HOADON_FK) HDSP ON HD.Pk_SEQ = HDSP.HOADON_FK \n"+ 
				    "                INNER JOIN ERP_XUATKHO XK ON XK.PK_SEQ = HDDH.xuatkho_fk \n"+ 
					"			    INNER JOIN ERP_DONDATHANG DH ON XK.DONDATHANG_FK = DH.PK_SEQ \n"+ 
					"			    INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+ 
					" WHERE HD.PK_SEQ =  "+id+" \n" ;
		/*}*/
		
		
		System.out.println("Câu lấy DL "+ query);
		
		ResultSet rs = db.get(query);
		
		if(rs!= null)
		{ 
			try{
				while(rs.next())
				{
					 ngayghinhan = rs.getString("NGAYGHINHAN");
					 ngaychungtu = rs.getString("NGAYXUATHD");
					 loaidonhang = rs.getString("LOAIDONHANG");
					 khachhangId = rs.getString("KHACHHANG_FK");
					 tenkh = rs.getString("TENKH");
					 loaisp =  rs.getString("LOAISP_FK");
					 kenhbanhangId =  rs.getString("KBH_FK");
					 tienteId =  rs.getString("tienteId");
					 tigia =  rs.getString("tigia");
						
										
					 tienhang = rs.getDouble("tienhang");
					 tienvat = rs.getDouble("VAT");
					 chietkhau = rs.getDouble("TIENCKTHUONGMAI") ;
					
					 htxuathd = rs.getString("HTXUATHD") ;					
					 ttcp = rs.getString("TTCP_FK") ;

					 
					 nam = ngayghinhan.substring(0, 4);
					 thang = ngayghinhan.substring(5, 7);
					
				}
				rs.close();
			}
			catch(Exception er)
			{
				db.update("rollback");
				er.printStackTrace();
				return "LOI KHONG THE CHOT :"+ er.toString();	
			}
		}
		
		String queryLayTK = "";
		
		String doituong = "";
		String madoituong = "";
		
		if(loaidonhang.equals("1")&&htxuathd.equals("100000"))  // DON HANG BAN
		{
			doituong = "Khách hàng";
			madoituong = khachhangId;
			
			if(loaisp.equals("100041")||loaisp.equals("100042")) // LOẠI SP LÀ THANHPHAM
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
			
		}else if(loaidonhang.equals("2")&&htxuathd.equals("100001"))  // 
		{
			 queryLayTK ="SELECT '64122000' as TAIKHOAN_NO,"
			         + "         '51124000' as TAIKHOAN_CO "
			         + "FROM  ERP_HOADON HD   "
			         + "WHERE HD.PK_SEQ = "+ id +" ";	
			 
		}
		else if(loaidonhang.equals("3")&&htxuathd.equals("100002"))  // BIEU TANG
		{
			 queryLayTK ="SELECT '64282000' as TAIKHOAN_NO,"
		         + "         '51124000' as TAIKHOAN_CO "
		         + "FROM  ERP_HOADON HD   "
		         + "WHERE HD.PK_SEQ = "+ id +" ";
			
		}
		else if(loaidonhang.equals("5") ) // DON HANG NOI BO
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
		
		int i = 1;
		if(queryLayTK.trim().length()>0)
		{		
			ResultSet tkRs = db.get(queryLayTK);
			try{
			if(tkRs != null)
			{
				while(tkRs.next())
					{
						taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
						taikhoanCO_SOHIEU = tkRs.getString("TAIKHOAN_CO");
					    			
					
						if(loaidonhang.equals("1")) {
							if(loaisp.equals("100041") || loaisp.equals("100042")){
								if(tienhang > 0)
								{
									if(laytk.trim().length()>0) laytk += " UNION ALL \n";
									 laytk  += 
									 	 	" SELECT N'NỢ' NO_CO, PK_SEQ,"+tienhang+" SOTIEN, "+taikhoanNO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
											" N'"+ tenkh +"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
											" FROM ERP_HOADON  \n"+
											" WHERE PK_SEQ = '"+id+"' \n" +				
											
											" UNION ALL \n"+
							
											" SELECT N'CÓ' NO_CO, PK_SEQ,"+tienhang+" SOTIEN, "+taikhoanCO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
											" '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
											" FROM ERP_HOADON  \n"+
											" WHERE PK_SEQ = '"+id+"' \n" ;
									 i++;
								}
								if(tienvat>0){
									taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
									taikhoanCO_SOHIEU = "33311000";
									
									if(laytk.trim().length()>0) laytk += " UNION ALL \n";
									
									 laytk  += 
									 	 	" SELECT N'NỢ' NO_CO, PK_SEQ,"+tienvat+" SOTIEN, "+taikhoanNO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
											" N'"+ tenkh +"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
											" FROM ERP_HOADON  \n"+
											" WHERE PK_SEQ = '"+id+"' \n" +				
											
											" UNION ALL \n"+
							
											" SELECT N'CÓ' NO_CO, PK_SEQ,"+tienvat+" SOTIEN, "+taikhoanCO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
											" '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
											" FROM ERP_HOADON  \n"+
											" WHERE PK_SEQ = '"+id+"' \n" ;		
									 i++;
								}
								
								if(chietkhau > 0){
									taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NOCK");
									taikhoanCO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
									
									if(laytk.trim().length()>0) laytk += " UNION ALL \n";
									
									 laytk  += 
									 	 	" SELECT N'NỢ' NO_CO, PK_SEQ,"+chietkhau+" SOTIEN, "+taikhoanNO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
											" N'' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
											" FROM ERP_HOADON  \n"+
											" WHERE PK_SEQ = '"+id+"' \n" +				
											
											" UNION ALL \n"+
							
											" SELECT N'CÓ' NO_CO, PK_SEQ,"+chietkhau+" SOTIEN, "+taikhoanCO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
											" N'"+ tenkh +"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
											" FROM ERP_HOADON  \n"+
											" WHERE PK_SEQ = '"+id+"' \n" ;	
									 i++;
								}
							}
						}
						
						if(loaidonhang.equals("2") || loaidonhang.equals("3") ){
							if(tienhang > 0)
							{
								if(laytk.trim().length()>0) laytk += " UNION ALL \n";
								
								 laytk  += 
								 	 	" SELECT N'NỢ' NO_CO, PK_SEQ,"+tienhang+" SOTIEN, "+taikhoanNO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
										" N'' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
										" FROM ERP_HOADON  \n"+
										" WHERE PK_SEQ = '"+id+"' \n" +				
										
										" UNION ALL \n"+
						
										" SELECT N'CÓ' NO_CO, PK_SEQ,"+tienhang+" SOTIEN, "+taikhoanCO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
										" '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
										" FROM ERP_HOADON  \n"+
										" WHERE PK_SEQ = '"+id+"' \n" ;	
								 i++;
							}
							if(tienvat>0){
								taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
								taikhoanCO_SOHIEU = "33311000";
								
								if(laytk.trim().length()>0) laytk += " UNION ALL \n";
								
								 laytk  += 
								 	 	" SELECT N'NỢ' NO_CO, PK_SEQ,"+tienvat+" SOTIEN, "+taikhoanNO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
										" N'' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
										" FROM ERP_HOADON  \n"+
										" WHERE PK_SEQ = '"+id+"' \n" +				
										
										" UNION ALL \n"+
						
										" SELECT N'CÓ' NO_CO, PK_SEQ,"+tienvat+" SOTIEN, "+taikhoanCO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
										" '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU,"+i+" STT, 2 SAPXEP \n"+
										" FROM ERP_HOADON  \n"+
										" WHERE PK_SEQ = '"+id+"' \n" ;		
								 i++;
							}
							
						}
						
						if(loaidonhang.equals("5")){
							
							if(tienhang > 0)
							{
								if(laytk.trim().length()>0) laytk += " UNION ALL \n";
								
								 laytk  += 
								 	 	" SELECT N'NỢ' NO_CO, PK_SEQ,"+tienhang+" SOTIEN, "+taikhoanNO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
										" N'"+ tenkh +"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
										" FROM ERP_HOADON  \n"+
										" WHERE PK_SEQ = '"+id+"' \n" +				
										
										" UNION ALL \n"+
						
										" SELECT N'CÓ' NO_CO, PK_SEQ,"+tienhang+" SOTIEN, "+taikhoanCO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
										" '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
										" FROM ERP_HOADON  \n"+
										" WHERE PK_SEQ = '"+id+"' \n" ;	
								 i++;
							}
							if(tienvat>0){
								taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
								taikhoanCO_SOHIEU = "33311000";
								
								if(laytk.trim().length()>0) laytk += " UNION ALL \n";
								
								 laytk  += 
								 	 	" SELECT N'NỢ' NO_CO, PK_SEQ,"+tienvat+" SOTIEN, "+taikhoanNO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
										" '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
										" FROM ERP_HOADON  \n"+
										" WHERE PK_SEQ = '"+id+"' \n" +				
										
										" UNION ALL \n"+
						
										" SELECT N'CÓ' NO_CO, PK_SEQ,"+tienvat+" SOTIEN, "+taikhoanCO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
										" N'"+ tenkh +"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
										" FROM ERP_HOADON  \n"+
										" WHERE PK_SEQ = '"+id+"' \n" ;	
								 i++;
							}
							
							if(chietkhau > 0){
								taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NOCK");
								taikhoanCO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
								
								if(laytk.trim().length()>0) laytk += " UNION ALL \n";
								
								 laytk  += 
								 	 	" SELECT N'NỢ' NO_CO, PK_SEQ,"+chietkhau+" SOTIEN, "+taikhoanNO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
										" '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
										" FROM ERP_HOADON  \n"+
										" WHERE PK_SEQ = '"+id+"' \n" +				
										
										" UNION ALL \n"+
						
										" SELECT N'CÓ' NO_CO, PK_SEQ,"+chietkhau+" SOTIEN, "+taikhoanCO_SOHIEU+" AS SOHIEUTAIKHOAN, \n"+ 
										" N'"+ tenkh +"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
										" FROM ERP_HOADON  \n"+
										" WHERE PK_SEQ = '"+id+"' \n" ;		
								 i++;
							}	
							 
						}	
			  }tkRs.close();
			}
		}
		catch(Exception er)
		{
			db.update("rollback");
			er.printStackTrace();
			return "LOI KHONG THE CHOT :"+ er.toString();	
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
						
			//B1: KIỂM TRA TRẠNG THÁI CHỐT HÓA ĐƠN (LOẠI TRƯỜNG HỢP NGƯỜI DÙNG SỬ DỤNG 2 TAB ĐỂ CHỐT)
						 //0: chưa chốt
						 //1: đã chốt
			
			query = " SELECT TRANGTHAI, isnull(HTXUATHD,0) HTXUATHD FROM ERP_HOADON WHERE PK_SEQ ='"+this.Id+"'";
			
			ResultSet kt_th = db.get(query);
			String trangthai = "";
			int HTXUATHD = 0;
			
			if(kt_th != null)
			{
				while(kt_th.next())
				{
					trangthai = kt_th.getString("TRANGTHAI");
					HTXUATHD = kt_th.getInt("HTXUATHD");
				}
				kt_th.close();
			}
			
			//NẾU HÓA ĐƠN Ở TRẠNG THÁI CHƯA CHỐT THÌ CHO CHỐT
			if(trangthai.trim().equals("0")){
				//B2: CẬP NHẬT TRẠNG THÁI CHỐT HÓA ĐƠN
				query = " UPDATE  ERP_HOADON SET TRANGTHAI = '1', nguoisua = '" + this.UserId+ "' WHERE PK_SEQ = '" + this.Id + "'";
				
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "KHONG THE CHOT HOA DON ,DONG LENH : " + query;
				}
						
				// B2: CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG VỀ TRẠNG THÁI ĐÃ XUẤT HÓA ĐƠN TÀI CHÍNH
				// 1 - chờ kinh doanh duyệt
				// 2 - chờ kế toán duyệt
				// 3 - chờ giám đốc duyệt
				// 4 - đã duyệt
				// 5 - đã xuất HD 
				// 6 - đã xuất kho
				// 7 - đã xóa
				// 8 - hoàn tất
				
				/*if(HTXUATHD == 0){ //XUẤT THEO ĐƠN ĐẶT HÀNG
					query=" UPDATE ERP_DONDATHANG SET TRANGTHAI = '5' "+
					       " WHERE PK_SEQ IN " +
					       " (SELECT DDH_FK FROM ERP_HOADON_DDH A INNER JOIN ERP_HOADON B ON A.HOADON_FK = B.PK_SEQ" +
					       " WHERE A.HOADON_FK = '"+this.Id+"' and B.LOAIHOADON = 0 )";
					
					if(!db.update(query))
					{
						db.getConnection().rollback();
						return "KHONG THE CAP NHAT TRANG THAI ERP_DONDATHANG ,DONG LENH : " + query;
					}	
				}
				else{//XUẤT THEO PHIẾU XUẤT KHO
*/					
					//LẤY PHIẾU XUẤT KHO
					query =" SELECT distinct DONDATHANG_FK FROM ERP_XUATKHO WHERE PK_SEQ IN (SELECT xuatkho_fk FROM ERP_HOADON_XUATKHO WHERE HOADON_FK = '"+this.Id+"')";
					
					ResultSet donhang = db.get(query);
					String donhang_fk = "";
					
					String [] donhangmang = null;
					
					if(donhang != null)
					{
						while(donhang.next())
						{
							donhang_fk = donhang.getString("DONDATHANG_FK")+",";
						}
						donhang.close();
					}
					
					donhang_fk = (donhang_fk.substring(0, donhang_fk.length() - 1));
					
					donhangmang = donhang_fk.split(",");
										  
					//KIỂM TRA XEM ĐÃ XUẤT HẾT SỐ LƯỢNG TRONG ĐƠN HÀNG CHƯA -> ĐỂ CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG THÀNH TOÀN TẤT
					
					for(int i =0; i<donhangmang.length; i++){						 
						  query=
							  " UPDATE ERP_DONDATHANG SET TRANGTHAI = '5' "+
						      " WHERE PK_SEQ =  " +donhangmang[i];
						  
						  if(!db.update(query))
							{
								db.getConnection().rollback();
								return "DONG LENH : " + query;
							}	
						 
						  
						  query = " UPDATE ERP_XUATKHO SET TRANGTHAI = 3 WHERE PK_SEQ IN (SELECT xuatkho_fk FROM ERP_HOADON_XUATKHO WHERE HOADON_FK = '"+this.Id+"') ";
						
						  if(!db.update(query))
							{
								db.getConnection().rollback();
								return "DONG LENH : " + query;
							}	
					}
				/*}*/
							
			}
			else{
					db.getConnection().rollback();
					this.Msg = "YÊU CẦU LOAD LẠI TRANG " ;
					return "YÊU CẦU LOAD LẠI TRANG" ;				
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
			String htxuathd = "";
			String ttcp = "";
			
			String taikhoanNO_SOHIEU = "";
			String taikhoanCO_SOHIEU = "";
			
			String nam = "";
			String thang = "";
			
			double tienhang = 0;
			double tienvat = 0;
			double chietkhau = 0;
			
			
			/*if(HTXUATHD ==0 ){
				query = 
					"SELECT HD.PK_SEQ, HD.NGAYXUATHD, HD.NGAYGHINHAN, DH.LOAIDONHANG, HD.KHACHHANG_FK, ISNULL(TIGIA,1) AS TIGIA, "
				  + "      HDSP.TIENHANG, ISNULL(HD.TIENCKTHUONGMAI,0) AS TIENCKTHUONGMAI , isnull(HD.VAT,0) VAT, ISNULL(HD.TIENTE_FK,100000) as TIENTEID, \n"+
					"      (SELECT TOP 1 b.LOAISANPHAM_FK FROM ERP_HOADON_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ WHERE HOADON_FK = HD.PK_SEQ) as LOAISP_FK , \n"+
					"      HD.KBH_FK, KH.MA + ' - '+ KH.TEN as TENKH, \n"+
					"      (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = KH.TAIKHOAN_FK ) AS TAIKHOAN_NOKH,  HD.KBH_FK  KENHBANHANG_FK  \n"+
					"FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK "
					+ "                 INNER JOIN "
					+ "                 ( SELECT HOADON_FK, SUM(SOLUONG*isnull(DONGIACK,0)) AS TIENHANG "
					+ "                   FROM ERP_HOADON_SP"
					+ "                   GROUP BY HOADON_FK) HDSP ON HD.Pk_SEQ = HDSP.HOADON_FK \n"+
					"				    INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
					"				    INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
					"WHERE HD.PK_SEQ = "+ this.Id  +" ";
			}
			else{*/
					query = 
						" SELECT HD.PK_SEQ, HD.NGAYXUATHD, HD.NGAYGHINHAN, DH.LOAIDONHANG, HD.KHACHHANG_FK, ISNULL(TIGIA,1) AS TIGIA, \n"+ 
					    "   	 HDSP.TIENHANG, ISNULL(HD.TIENCKTHUONGMAI,0) AS TIENCKTHUONGMAI , isnull(HD.VAT,0) VAT, ISNULL(HD.TIENTE_FK,100000) as TIENTEID, \n"+ 
					    "  		 (SELECT TOP 1 b.LOAISANPHAM_FK FROM ERP_HOADON_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ WHERE HOADON_FK = HD.PK_SEQ) as LOAISP_FK , \n"+ 
					    "  		  HD.KBH_FK, KH.MA + ' - '+ KH.TEN as TENKH, \n"+ 
					    "  		 (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = KH.TAIKHOAN_FK ) AS TAIKHOAN_NOKH, HD.KBH_FK  KENHBANHANG_FK, TTCP_FK, ISNULL(HTXUATHD, 100000) HTXUATHD \n"+ 
					    " FROM ERP_HOADON HD INNER JOIN ERP_HOADON_XUATKHO HDDH on HD.PK_SEQ = HDDH.HOADON_FK \n"+ 
					    "              INNER JOIN \n"+ 
					    "              ( SELECT HOADON_FK, SUM(SOLUONG*isnull(DONGIACK,0)) AS TIENHANG \n"+ 
					    "                FROM ERP_HOADON_SP \n"+
					    "                GROUP BY HOADON_FK) HDSP ON HD.Pk_SEQ = HDSP.HOADON_FK \n"+ 
					    "                INNER JOIN ERP_XUATKHO XK ON XK.PK_SEQ = HDDH.xuatkho_fk \n"+ 
						"			    INNER JOIN ERP_DONDATHANG DH ON XK.DONDATHANG_FK = DH.PK_SEQ \n"+ 
						"			    INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+ 
						" WHERE HD.PK_SEQ =  "+this.Id +" \n" ;
			/*}*/
			
	/*		query = 
				"SELECT HD.PK_SEQ, HD.NGAYXUATHD, HD.NGAYGHINHAN, DH.LOAIDONHANG, HD.KHACHHANG_FK, ISNULL(TIGIA,1) AS TIGIA, "
			  + "      HDSP.TIENHANG, ISNULL(HD.TIENCKTHUONGMAI,0) AS TIENCKTHUONGMAI , HD.VAT, ISNULL(HD.TIENTE_FK,100000) as TIENTEID, \n"+
				"      (SELECT TOP 1 b.LOAISANPHAM_FK FROM ERP_HOADON_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ WHERE HOADON_FK = HD.PK_SEQ) as LOAISP_FK , \n"+
				"      HD.KENHBANHANG_FK, \n"+
				"      (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = KH.TAIKHOAN_FK ) AS TAIKHOAN_NOKH \n"+
				"FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK "
				+ "                 INNER JOIN "
				+ "                 ( SELECT HOADON_FK, SUM(SOLUONG*DONGIACK) AS TIENHANG "
				+ "                   FROM ERP_HOADON_SP"
				+ "                   GROUP BY HOADON_FK) HDSP ON HD.Pk_SEQ = HDSP.HOADON_FK \n"+
				"				    INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
				"				    INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
				"WHERE HD.PK_SEQ = "+ this.Id +" ";*/
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
					 htxuathd = rs.getString("HTXUATHD") ;
					 ttcp = rs.getString("TTCP_FK") ;
							
					 nam = ngayghinhan.substring(0, 4);
					 thang = ngayghinhan.substring(5, 7);
					
				}
				rs.close();
			}
			
			String queryLayTK = "";
			String doituong = "";
			String madoituong = "";
			
			if(loaidonhang.equals("1")&&htxuathd.equals("100000"))  // DON HANG BAN
			{	
				doituong = "Khách hàng";
				madoituong = khachhangId;
				
				if(loaisp.equals("100041")||loaisp.equals("100042")) // LOẠI SP LÀ THANHPHAM
				{
					 queryLayTK =
						   "SELECT a.TAIKHOAN_FK as TAIKHOAN_NO,"
				         + "       (case when "+ kenhbanhangId +" = '100000' then (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51121100') "
				         + "       		 when "+ kenhbanhangId +" = '100001' then (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51121200') "
				         + "        	 when "+ kenhbanhangId +" = '100007' then (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51122000') "
				         + "       		 else (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51121300') "
				         + "       end) as TAIKHOAN_CO ,"
				         + "       (case when "+ kenhbanhangId +" = '100000' then (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52110000') "
				         + "       		 when "+ kenhbanhangId +" = '100001' then (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52120000') "
				         + "        	 when "+ kenhbanhangId +" = '100007' then (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52140000') "
				         + "        	 when "+ kenhbanhangId +" = '100008' then (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52130000') "
				         + "       end) as TAIKHOAN_NOCK, "
				         +	"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' ) TAIKHOAN_VAT "
				         + "FROM  ERP_KHACHHANG a  "
				         + "WHERE a.PK_SEQ = "+ khachhangId +" ";	
				}
				
			}else if(loaidonhang.equals("2")&&htxuathd.equals("100001")  )  // Hóa đơn xuất sử dụng nội bộ cho bán hàng	
			{
				doituong = "Trung tâm chi phí";
				madoituong = ttcp;
				
				 queryLayTK =
					   "SELECT (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '64122000') as TAIKHOAN_NO,"
			         + "       (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51124000') as TAIKHOAN_CO,"
			         + "  	   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' ) TAIKHOAN_VAT "
			         + "FROM  ERP_HOADON HD   "
			         + "WHERE HD.PK_SEQ = "+ this.Id  +" ";
				
			} else if(loaidonhang.equals("3")&&htxuathd.equals("100002")) // Hóa đơn xuất sử dụng nội bộ khác bán hàng
			{
				doituong = "Trung tâm chi phí";
				madoituong = ttcp;
				
				 queryLayTK =
					   "SELECT (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '64282000') as TAIKHOAN_NO,"
			         + "       (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51124000') as TAIKHOAN_CO,"
			         + "  	   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' ) TAIKHOAN_VAT "
			         + "FROM  ERP_HOADON HD   "
			         + "WHERE HD.PK_SEQ = "+ this.Id  +" ";
			}
			else if(loaidonhang.equals("5") ) // DON HANG NOI BO
			{
				queryLayTK ="SELECT a.TAIKHOAN_FK as TAIKHOAN_NO,"
			         + "       (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN =  '51121000') as TAIKHOAN_CO , "
			         + "       (case when "+ kenhbanhangId +" = '100000' then (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52110000') "
			         + "       		 when "+ kenhbanhangId +" = '100001' then (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52120000')"
			         + "        	 when "+ kenhbanhangId +" = '100007' then (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52140000') "
			         + "        	 when "+ kenhbanhangId +" = '100008' then (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52130000') "
			         + "       end) as TAIKHOAN_NOCK, "
			         + "  (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' ) TAIKHOAN_VAT "
			         + "FROM  ERP_KHACHHANG a   "
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
						
							this.Msg = util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn", this.Id, taikhoanNO_SOHIEU, taikhoanCO_SOHIEU, "",
									Double.toString(tienhang), Double.toString(tienhang), doituong, madoituong,"" ,"","0", "", "", tienteId, "", tigia, Double.toString(tienhang), Double.toString(tienhang), "Hóa đơn - Tiền hàng ");
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
							taikhoanCO_SOHIEU = tkRs.getString("TAIKHOAN_VAT");
							
							if(taikhoanNO_SOHIEU.trim().length() <= 0 || taikhoanCO_SOHIEU.trim().length() <= 0)
							{
								this.Msg = "Khách hàng hoặc nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								tkRs.close();
								db.getConnection().rollback();
		
							}
						
						
							this.Msg = util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn", this.Id, taikhoanNO_SOHIEU, taikhoanCO_SOHIEU, "",
									Double.toString(tienvat), Double.toString(tienvat), doituong,madoituong, "","", "0", "", "", tienteId, "", tigia, Double.toString(tienvat), Double.toString(tienvat), "Hóa đơn - VAT ");
							if(this.Msg.trim().length()>0)
							{
								tkRs.close();
								this.Msg = "Loi update: " + this.Msg;
								System.out.println("Loi khi chot: " + this.Msg);
								db.getConnection().rollback();
							}
						
						}
						
						// CHIẾT KHẤU
						if(chietkhau > 0&&loaidonhang.equals("1"))
						{	
							taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NOCK");
							taikhoanCO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
							
							if(taikhoanNO_SOHIEU.trim().length() <= 0 || taikhoanCO_SOHIEU.trim().length() <= 0)
							{
								this.Msg = "Khách hàng hoặc nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								tkRs.close();
								db.getConnection().rollback();
		
							}
						
						
							this.Msg = util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn", this.Id, taikhoanNO_SOHIEU, taikhoanCO_SOHIEU, "",
									Double.toString(chietkhau), Double.toString(chietkhau),"","", doituong, madoituong, "0", "", "", tienteId, "", tigia, Double.toString(chietkhau), Double.toString(chietkhau), "Hóa đơn -Chiết khấu ");
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

	public void SetTenHangHoaDichVu(String tenhanghoadichvu) {
		this.tenhanghoadichvu = tenhanghoadichvu;
		
	}
	
	public String getTenHangHoaDichVu() {
		
		return this.tenhanghoadichvu;
	}


	
	public String getKhoId() {
	
		return this.khoId;
	}


	
	public void setKhoId(String khoId) {
	
		this.khoId = khoId;
	}


	
	public ResultSet getKhoRs() {
	
		return this.khoRs;
	}


	
	public void setKhoRs(ResultSet khoRs) {
	
		this.khoRs = khoRs;
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


	
	public void SetDiachiKH(String diachikh) {
		
		this.diachikh = diachikh;
	}


	
	public String getDiachiKH() {
		
		return this.diachikh;
	}


	private boolean Ngaydonhang(String ngaydonhang){
		// NẾU NGÀY ĐƠN HÀNG  < 03/2015 -> CHẠY THEO QUY TRÌNH BÁN HÀNG -> HÓA ĐƠN -> XUẤT KHO.
		// NẾU NGÀY ĐƠN HÀNG > 01/2015 -> CHẠY THEO QUY TRÌNH BÁN HÀNG -> XUẤT KHO -> HÓA ĐƠN.
		
		boolean kt = false;
		int songay = 0;
		
		//NẾU NGÀY ĐƠN HÀNG > '2015-05-01' THÌ ĐƯA VỀ QUY TRÌNH MỚI
		
		dbutils db = new dbutils();
		String layngay = "select datediff(DD,'2015-05-01', '"+ngaydonhang+"') songay";
		
		System.out.println(layngay);
		ResultSet checkngay = db.get(layngay);
		
		try{
			if(checkngay.next())
			{
				songay = checkngay.getInt("songay");
				checkngay.close();
			}
			if(songay >= 0 ) kt = true;
		}
		catch (Exception e){
			e.printStackTrace();
			kt = false;
		}
		
		this.checkdonhang = kt;
		return kt;
		
	}
	
	public boolean checkdonhang() {		
		return this.checkdonhang;
	}

	public void Setcheckctu(String checkctu) {
		this.checkctu = checkctu;
		
	}

	public String getchecktu() {
		
		return this.checkctu;
	}


	
	public String gethinhthucxhd() {
		
		return this.hinhthucxhd;
	}


	
	public void sethinhthucxhd(String hinhthucxhd) {
		
		this.hinhthucxhd = hinhthucxhd;
	}


	
	public String getKh_dhId() {
		
		return this.kh_dhId;
	}


	
	public void setKh_dhId(String kh_dhId) {
		
		this.kh_dhId = kh_dhId;
	}


	
	public void SetSotienavat(String sotienavat) {
		
		this.sotienavat = sotienavat;
	}


	
	public String getSotienavat() {
		
		return this.sotienavat;
	}


	
	public String getDiachigiaohangtk() {
		
		return this.diachigiaohangtk;
	}


	
	public void setDiachigiaohangtk(String diachigiaohangtk) {
		
		this.diachigiaohangtk = diachigiaohangtk;
	}


	
	public String[] getSpnr() {
		
		return this.spnr;
	}


	
	public void setSpnr(String[] spnr) {
		
		this.spnr = spnr;
	}


public void setNguoiMuaHang(String nguoimuahang) {
		
		this.nguoimuahang = nguoimuahang;
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


	
	public String getNguoiMuaHang() {
		
		return this.nguoimuahang;
	}


	
	public String getkhxhd() {
	
		return this.khxhd;
	}


	
	public void setkhxhd(String khxhd) {
	
		this.khxhd = khxhd;
	}


	
	public String getpxkId() {
		
		return this.pxkId;
	}


	
	public void setpxkId(String pxkId) {
		
		this.pxkId = pxkId;
	}


	
	public ResultSet gethinhthucxhdRs() {
		
		return this.HinhthucxhdRs;
	}


	
	public void sethinhthucxhdRs(ResultSet hinhthucxhdRs) {
		
		this.HinhthucxhdRs = hinhthucxhdRs;
	}


	
	public String getttcpId() {
		
		return this.ttcpId;
	}


	
	public void settcpId(String ttcpId) {
		
		this.ttcpId = ttcpId;
	}


	
	public ResultSet getTTCPRs() {
		
		return this.ttcpRs;
	}


	
	public void setTTCPRs(ResultSet ttcpRs) {
		
		this.ttcpRs = ttcpRs;
	}

}
