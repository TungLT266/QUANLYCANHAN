package geso.traphaco.distributor.beans.hoadonchuyenkhoNPP.imp;

import geso.traphaco.distributor.beans.hoadonchuyenkhoNPP.IErpHoadonchuyenkhoNPP;
import geso.traphaco.distributor.beans.nhaphang.imp.Nhaphang;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpHoadonchuyenkhoNPP implements IErpHoadonchuyenkhoNPP
{
	String userId;
	String id;
	
	String ngayxuatHD;
	String ngayghinhanCN;
	String ghichu;
	String ptchietkhau;
	String KhGhiNo;
	String Ghichu2;
	String[] tenXuatHD;
	
	String congtyId;
	String nhanvienId;
	ResultSet nhanvienRs;
	String khKGId;
	
	String kyhieuhoadon;
	String sohoadon;
	String hinhthuctt;
	String innguoimua;

	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn hàng khác
	
	String loaiXHD;
	
	String msg;
	String trangthai;
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String nppId;
	ResultSet nppRs;
	
	String khId;
	ResultSet khRs;
	
	String ddhId;
	ResultSet congnoRs;
	
	String nppDangnhap;
	String tenxuathd;
	
	String[] ckId;
	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spDongia;
	String[] spChietkhau;
	String[] spSoluong;
	String[] spLoai;
	String[] spSCheme;
	String[] spVat;
	String[] spTienthue;
	String[] spThanhtien;
	String[] spChonin;
	
	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	
	String bvat;
	String totalCHIETKHAU;
	String thueVAT;
	String avat;
	String mavuviec="";
	
	ResultSet ddhRs;
	
	String tenxuathdNew;
	String tthdId = "";
	String nguoimuahang = "";
	String donvimua = "";
	String diachi = "";
	String masothue = "";
	String ghichuDONHANG;
	String doituong;
	
	ResultSet ttXhdRs;
	
	Hashtable<String, String> schemeTichluy;
	String tientichluy;

	dbutils db;
	
	public ErpHoadonchuyenkhoNPP()
	{
		this.id = "";
		this.ngayxuatHD = getDateTime();
		this.ngayghinhanCN = getDateTime();
		this.ghichu = "";
		this.kyhieuhoadon = "";
		this.hinhthuctt = "";
		this.sohoadon= "";
		this.khoNhanId = "";
		this.nppId = "";
		this.khId ="";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		
		this.bvat = "0";
		this.totalCHIETKHAU = "0";
		this.thueVAT = "0";
		this.avat = "0";
		
		this.nppDangnhap="";
		
		this.loaidonhang = "0";
		this.innguoimua = "";
		this.loaiXHD ="0";
		this.ptchietkhau="0";
		this.tenxuathd = "";
		this.KhGhiNo = "";
		this.Ghichu2 = "";
		this.congtyId ="";
		this.nhanvienId = "";
		this.tenXuatHD = new String[0];
		this.khKGId = "";
		this.tenxuathdNew = "";
		
		this.tthdId = "";
		this.nguoimuahang = "";
		this.donvimua = "";
		this.diachi = "";
		this.masothue = "";
		this.ghichuDONHANG = "";
		
		this.doituong = "0";
		
		this.schemeTichluy = new Hashtable<String, String>();
		this.tientichluy = "0";
		
		this.db = new dbutils();
	}
	
	public ErpHoadonchuyenkhoNPP(String id)
	{
		this.id = id;
		this.ngayxuatHD = getDateTime();
		this.ngayghinhanCN = getDateTime();
		this.ghichu = "";
		this.kyhieuhoadon = "";
		this.sohoadon = "";
		this.khoNhanId = "";
		this.hinhthuctt = "";
		this.nppId = "";
		this.msg = "";
		this.trangthai = "0";
		this.khId ="";
		this.ddhId = "";
		
		this.bvat = "0";
		this.totalCHIETKHAU = "0";
		this.thueVAT = "0";
		this.avat = "0";
		
		this.nppDangnhap="";

		this.loaidonhang = "0";
		this.innguoimua = "";
		
		this.loaiXHD ="0";
		this.ptchietkhau="0";
		this.tenxuathd = "";
		this.KhGhiNo = "";
		this.Ghichu2 = "";
		this.congtyId = "";
		this.nhanvienId = "";
		this.tenXuatHD = new String[0];
		this.khKGId = "";
		this.tenxuathdNew = "";
		
		this.tthdId = "";
		this.nguoimuahang = "";
		this.donvimua = "";
		this.diachi = "";
		this.masothue = "";
		this.ghichuDONHANG = "";
		
		this.schemeTichluy = new Hashtable<String, String>();
		this.tientichluy = "0";
		this.doituong = "0";
		this.db = new dbutils();
	}

	public String getInNguoimua() 
	{
		return this.innguoimua;
	}

	public void setInNguoimua(String innguoimua) 
	{
		this.innguoimua = innguoimua;
	}
	
	public String getnppDangnhap() 
	{
		return this.nppDangnhap;
	}

	public void setnppDangnhap(String nppDangnhap) 
	{
		this.nppDangnhap = userId;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getNgayxuatHD() 
	{
		return this.ngayxuatHD;
	}

	public void setNgayxuatHD(String ngayxuatHD) 
	{
		this.ngayxuatHD = ngayxuatHD;
	}

	public String getKhoNhapId()
	{
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) 
	{
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{
		this.khoNhanRs = khonhapRs;
	}
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppDangnhap=util.getIdNhapp(this.userId);
		String	query = "select dungchungkenh from NHAPHANPHOI where PK_SEQ = '" + this.nppDangnhap + "' ";
		ResultSet rs = db.get(query);
		try {
			if(rs.next())
			{
				this.dungchungKENH=rs.getString("dungchungkenh");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	public void createRs() 
	{
		this.getNppInfo();
		Utility util = new Utility();
		String query = "";
		
		if(this.doituong.equals("1"))
		{
			query = "select PK_SEQ, MAFAST + '-' + TEN as TEN from KHACHHANG where TRANGTHAI = '1' and npp_fk ='"+ this.nppDangnhap +"' ";
					//" and pk_seq in (select KHACHHANG_FK from ERP_DONDATHANGNPP where trangthai in (2,4) )";	
			this.khRs = db.getScrol(query);			

			//Lấy thông tin xuất hóa đơn để chọn
			query = "select PK_SEQ, TENNGUOIMUA, DONVI, DIACHI, MASOTHUE from KHACHHANG_THONGTINHOADON " + 
					" where KHACHHANG_FK = '" + this.khId + "' order by PK_SEQ asc";
			this.ttXhdRs = db.get(query);
			
			//SAU KHI CHỌN LẠI, THÌ LẤY THÔNG TIN CHỌN
			if( this.tthdId.trim().length() > 0 )
			{
				query = "select PK_SEQ, TENNGUOIMUA, DONVI, DIACHI, MASOTHUE from KHACHHANG_THONGTINHOADON where pk_seq = '" + this.tthdId + "'";
				ResultSet rs =  this.db.get(query);
				if( rs != null )
				{
					try 
					{
						if( rs.next() )
						{
							this.nguoimuahang = rs.getString("TENNGUOIMUA");
							this.donvimua = rs.getString("DONVI");
							this.diachi = rs.getString("DIACHI");
							this.masothue = rs.getString("MASOTHUE");
						}
						rs.close();
					} 
					catch (Exception e) { }
				}
			}
			
		
		}
		else if(this.doituong.equals("0"))
		{
			query = "select PK_SEQ, MAFAST + '-' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk ='"+ this.nppDangnhap +"' ";
			//" and pk_seq in (select NPP_DAT_FK from ERP_DONDATHANGNPP where trangthai in (2,4)) ";
			this.nppRs = db.get(query);
		}
		else if(this.doituong.equals("2"))
		{
			query = "select PK_SEQ, MA + '-' + TEN as TEN from ERP_NHANVIEN where TRANGTHAI = '1' and congty_fk = ( select congty_fk from NHAPHANPHOI where pk_seq = '" + this.nppDangnhap + "' ) ";
			//" and pk_seq in (select NHANVIEN_FK from ERP_DONDATHANGNPP where trangthai in (2,4) )";
			this.nhanvienRs = db.get(query);
		}
		
		

		// LẤY PHIẾU CHUYỂN KHO
		query = 
			 " SELECT  a.PK_SEQ, isnull(Yeucauchuyenkho_fk,0) as PHIEUYEUCAU, a.lydo, a.NgayChuyen \n"+
			 " FROM ERP_CHUYENKHO a   \n"+
			 " LEFT JOIN KHO KHOTT on a.khonhan_fk = KHOTT.PK_SEQ  \n"+
			 " WHERE a.pk_seq > 0  and a.nhanhang_fk is null and a.npp_fk = "+this.nppDangnhap+" and a.lenhsanxuat_fk  is null "+
			 " AND a.TRANGTHAI IN (2,3) \n" +
			 " AND a.PK_SEQ NOT IN (SELECT CHUYENKHO_FK FROM ERP_HOADONNPP_CHUYENKHO A INNER JOIN ERP_HOADONNPP B ON A.HOADONNPP_FK = B.PK_SEQ \n" +
			 "						WHERE B.TRANGTHAI NOT IN (3,5) AND B.PK_SEQ != "+( this.id.trim().length() > 0 ? this.id : "-1" )+" ) \n"+
			 " ORDER BY a.NgayChuyen desc ";
			
			this.ddhRs = db.get(query);
		
		 
		if(this.id.length() <= 0 ) 
		{
			// TỰ TẠO SỐ HÓA ĐƠN CỦA USER

			String kyhieuhoadon="";
			String sohoadon="";
			String ngayhoadon = "";
			
			sohoadon="NA";
			kyhieuhoadon="NA";
			String mau = "1";
			
			String chuoi = "";
			long sohoadontu = 0;
			long sohoadonden = 0;

			try
			{				
				{
					String query_kyhieu = " NV.KYHIEU ";				
					String query_sohdTU = " NV.SOHOADONTU ";	
					String query_sohdDEN = " NV.SOHOADONDEN ";	
					String query_mauhd = "1";
					String query_ngayhd = " NV.NGAYHOADON  ";
					
					// LAY TT KHAI BAO SO HD TRONG DLN
					query= " SELECT ISNULL("+ query_ngayhd +", '') as NGAYHOADON, (CASE WHEN ISNULL("+ query_kyhieu +",'-1') = '' THEN '-1' ELSE ISNULL("+ query_kyhieu +",'-1') END)  as KYHIEU, \n"+
						   "        ISNULL("+ query_sohdTU +", -1) AS SOHOADONTU, ISNULL("+ query_sohdDEN +", -1) AS SOHOADONDEN,  \n"+
						   "        (select count(hd.pk_seq) as dem  "+
						   "         from ERP_XUATHOADONKM hd               "+
						   "         where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_OTC, \n" +
						   "        (select count(hd.pk_seq) as dem  "+
						   "         from ERP_HOADONNPP hd               "+
						   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_ETC \n" +
						   " FROM NHANVIEN NV  \n" +
						   " WHERE NV.pk_seq = '" + userId + "' \n";
					System.out.println("Câu check khai báo SHD "+query);
					ResultSet rsLayDL = db.get(query);
					
					int check_OTC = 0;
					int check_ETC = 0;
										
						while(rsLayDL.next())
						{
							kyhieuhoadon= rsLayDL.getString("kyhieu");
							sohoadontu = rsLayDL.getString("sohoadontu").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadontu") ;
							sohoadonden = rsLayDL.getString("sohoadonden").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadonden") ;;
							ngayhoadon = rsLayDL.getString("ngayhoadon");
							
							check_OTC = rsLayDL.getInt("isSd_OTC");
							check_ETC = rsLayDL.getInt("isSd_ETC");
						}
						rsLayDL.close();
					
					if(kyhieuhoadon.equals("-1") || sohoadontu == -1 || sohoadonden == -1 )
					{
						this.msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
					}
								
					if(check_OTC <= 0 && check_ETC <= 0)
					{
						chuoi = ("000000" + sohoadontu);
						chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
					}
					else
					{
						// LAY SOIN MAX TRONG OTC && ETC
						query= " SELECT  \n"+
							   "       isnull((select max(cast(sohoadon as float)) as soin_max  "+
							   "        from ERP_XUATHOADONKM hd               "+
							   "        where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ ),0) soinMAX_OTC, \n" +
							   "       isnull((select max(cast(sohoadon as float)) as soin_max "+
							   "        from ERP_HOADONNPP hd               "+
							   "        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ),0) soinMAX_ETC  \n" +
							   " FROM NHANVIEN NV  \n" +
							   " WHERE NV.pk_seq = '" + userId + "' \n";
						
						System.out.println("Câu lấy SHD Max: "+query);
						long soinMAX_OTC = 0;
					    long soinMAX_ETC = 0;
					    
						ResultSet laySOIN = db.get(query);						     
						while(laySOIN.next())
						{
							soinMAX_OTC = laySOIN.getLong("soinMAX_OTC");
							soinMAX_ETC = laySOIN.getLong("soinMAX_ETC");
						}
						laySOIN.close();
	
						if( soinMAX_OTC > soinMAX_ETC ) 
						{
							chuoi = ("000000" + (soinMAX_OTC > 0 ? (soinMAX_OTC + 1) : "1" ));
						}
						else
						{
							chuoi = ("000000" + (soinMAX_ETC > 0 ? (soinMAX_ETC + 1) : "1"));
						}
						
						 chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length()).trim();
					}
					
					
					if(Integer.parseInt(chuoi) > sohoadonden )
					{ 
						//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
						query= " SELECT  \n"+
							   "      (select  max(cast(hd.sohoadon as float)) as soin_max   \n"+
							   "       from ERP_HOADONNPP hd                                     \n"+
							   "       where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  \n"+
							   "             and cast(hd.sohoadon as float) >= cast(ISNULL("+ query_sohdTU +", -1) as float)                                 \n"+
							   "             and cast(hd.sohoadon as float) <= cast(ISNULL("+ query_sohdDEN +", -1) as float)  and hd.nguoisua = NV.PK_SEQ                               \n"+
							   "       having max(cast(hd.sohoadon as float)) != ( select  MAX(cast(SOHOADON as float)) as SOIN_MAX  from ERP_HOADONNPP where trangthai!= 3 and  kyhieu = ISNULL("+ query_kyhieu +",'-1')  and nguoisua = NV.PK_SEQ) \n"+
							   "       ) soinMAX_OTC 										  \n"+								  
							   " FROM NHANVIEN NV   \n" +
							   " WHERE NV.pk_seq = '" + userId + "' \n";
						
						System.out.println("Câu lấy HD không dùng "+query);
						ResultSet SoMAX_HD = db.get(query);
						String soinmax = "";
						while(SoMAX_HD.next())
						{
							soinmax = SoMAX_HD.getString("soinMAX_OTC")== null ? "" : SoMAX_HD.getString("soinMAX_OTC") ;
							chuoi = ("000000" + (SoMAX_HD.getLong("soinMAX_OTC")));
								
						} SoMAX_HD.close();
						
						chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
				  
						if(soinmax.trim().length() <= 0 )
						{
							this.msg = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến (" + sohoadonden + ")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
							
						}
					}
					
					 this.sohoadon = chuoi ;
					
				    // KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
					query = " select (select count(*) from ERP_XUATHOADONKM where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '2' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraOTC, " +
							"        (select count(*) from ERP_HOADONNPP where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraETC " +
							" from NHANVIEN where pk_seq = '" + userId + "' ";
					
					System.out.println("Câu kiểm tra lại SHD: "+query);
					ResultSet RsRs = db.get(query);
					int KT_OTC = 0;
					int KT_ETC = 0;
						while(RsRs.next())
						{
							KT_OTC = RsRs.getInt("KtraOTC") ;
							KT_ETC = RsRs.getInt("KtraETC") ;
						}
					
					if(KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI TẠO
					{
						//msg = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
						this.msg = "Không thể duyệt đơn hàng: Số hoá đơn tiếp theo '"+sohoadon+"' đã có trên hệ thống! ";
					}
				}
			}
			catch(Exception e)
			{
				this.msg = "Lỗi xảy ra trong quá trình lấy Số hóa đơn";
				e.printStackTrace();
			}

			
			chuoi = this.ddhId;
			if(chuoi.length() > 0)
			{	
				// INIT TONG TIEN VAT
				try 
				{
					query = " SELECT   CKSP.CHUYENKHO_FK CKID,SP.PK_SEQ AS SPID, SP.MA AS MA, N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(SP.TENXUATHOADON,''), '__') WHERE part!= ' '  order by id desc), '') AS TEN , \n"+
					 		"		   DVDL.DIENGIAI AS donvi, isnull(CKSP.SOLUONGXUAT,0) SOLUONG, \n"+
					 		"		   0 chietkhau, 1 CHONIN, ' ' scheme, 0 vat, 0 dongia, 0 thanhtien  \n"+
							" FROM ERP_CHUYENKHO_SANPHAM CKSP \n"+
							" INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CKSP.CHUYENKHO_FK \n"+
							" INNER JOIN SANPHAM SP ON SP.PK_SEQ=CKSP.SANPHAM_FK \n"+
							" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK \n"+
							" WHERE CK.PK_SEQ IN ("+ chuoi +") ";

					System.out.println("INIT SP "+query);
					ResultSet rsLaySP = db.get(query);

					String spCKID = "";
					String spID = "";
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONG = "";
					String spGIANHAP = "";
					String spCHIETKHAU = "";
					String spSCHEME = "";
					String spVAT = "";
					String spTIENTHUE = "";
					String spTHANHTIEN = "";
					String spCHONIN = "";

					if(rsLaySP!= null)
					{				    	
						while(rsLaySP.next())
						{
							spCKID += rsLaySP.getString("CKID") + "__";
							spID += rsLaySP.getString("SPID") + "__";
							spMA += rsLaySP.getString("MA") + "__";
							spTEN += rsLaySP.getString("TEN") + "__";
							spDONVI += rsLaySP.getString("DONVI") + "__";
							spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
							spGIANHAP += (rsLaySP.getDouble("DONGIA")) + "__";
							spCHIETKHAU += (rsLaySP.getDouble("chietkhau")) + "__";
							spTHANHTIEN += (rsLaySP.getDouble("THANHTIEN")) + "__";
							spSCHEME += rsLaySP.getString("scheme") + "__";
							spVAT +=  (rsLaySP.getDouble("vat")) + "__";
							spTIENTHUE +=  Math.round( ( Math.round( rsLaySP.getDouble("SOLUONG") * rsLaySP.getDouble("DONGIA") ) - rsLaySP.getDouble("chietkhau") ) * rsLaySP.getDouble("vat") / 100 ) + "__";
							spCHONIN += (rsLaySP.getString("CHONIN")) + "__";

						}
						rsLaySP.close();

						if(spMA.trim().length() > 0)
						{
							spCKID = spCKID.substring(0, spCKID.length() - 2);
							this.ckId = spCKID.split("__");
							
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

							spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
							this.spDongia = spGIANHAP.split("__");

							spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
							this.spChietkhau = spCHIETKHAU.split("__");

							spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
							this.spSCheme = spSCHEME.split("__");

							spVAT = spVAT.substring(0, spVAT.length() - 2);
							this.spVat = spVAT.split("__");

							spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
							this.spTienthue = spTIENTHUE.split("__");

							spCHONIN = spCHONIN.substring(0, spCHONIN.length() - 2);
							this.spChonin = spCHONIN.split("__");
							
							spTHANHTIEN = spTHANHTIEN.substring(0, spTHANHTIEN.length() - 2);
							this.spThanhtien = spTHANHTIEN.split("__");
						}
					}
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
	  }
	  else // ID > 0
	  {
		// LAY SP TRONG HOADON
		 query = 	"select b.PK_SEQ as SPID, b.MA, isnull(a.sanphamTEN, isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.TENXUATHOADON,''), '__') WHERE part!= ' '  order by id desc), '')) as TEN, a.donvitinh, a.soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, a.scheme, a.vat," +
					"	isnull( TIENVAT, ( ( round( soluong * dongia, 0 ) - chietkhau ) * isnull(vat, 0) / 100 ) ) as tienVAT, a.CHONIN, a.thanhtien, a.CHUYENKHO_FK  " +
					"from ERP_HOADONNPP_SP a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					"where a.hoadon_fk = "+ this.id +"  " ;				   
		  
		  System.out.println("INIT_SP: "+query);
		  ResultSet rsLaySP = db.get(query);
		  try 
		  {
			  	String spCKID = "";
		  		String spID = "";
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spTHANHTIEN = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spSCHEME = "";
				String spVAT = "";
				String spTIENTHUE = "";
				String spCHONIN = "";
				
		  	
			    if(rsLaySP!= null)
			    {				    	
					while(rsLaySP.next())
					{
						spCKID += rsLaySP.getString("CHUYENKHO_FK") + "__";
						spID += rsLaySP.getString("SPID") + "__";
						spMA += rsLaySP.getString("MA") + "__";
						spTEN += rsLaySP.getString("TEN") + "__";
						spDONVI += rsLaySP.getString("DONVITINH") + "__";
						spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
						spGIANHAP += (rsLaySP.getDouble("DONGIA")) + "__";
						spTHANHTIEN += (rsLaySP.getDouble("THANHTIEN")) + "__";
						spCHIETKHAU += (rsLaySP.getDouble("chietkhau")) + "__";
						spSCHEME += ( rsLaySP.getString("scheme").trim().length() <= 0 ? " " : rsLaySP.getString("scheme").trim() ) + "__";
						spVAT +=  (rsLaySP.getDouble("vat")) + "__";
						spTIENTHUE +=  (rsLaySP.getDouble("tienVAT")) + "__";
						spCHONIN +=  (rsLaySP.getString("CHONIN")) + "__";
					}
					rsLaySP.close();
					
					if(spMA.trim().length() > 0)
					{
						spCKID = spCKID.substring(0, spCKID.length() - 2);
						this.ckId = spCKID.split("__");
						
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
						
						spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
						this.spDongia = spGIANHAP.split("__");
						
						spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
						this.spChietkhau = spCHIETKHAU.split("__");
						
						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");
						
						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spVat = spVAT.split("__");
		
						spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
						this.spTienthue = spTIENTHUE.split("__");
						
						spTHANHTIEN = spTHANHTIEN.substring(0, spTHANHTIEN.length() - 2);
						this.spThanhtien = spTHANHTIEN.split("__");
						
						spCHONIN = spCHONIN.substring(0, spCHONIN.length() - 2);
						this.spChonin = spCHONIN.split("__");
					}
			    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
	  }
	  
	}
		

	public void init() 
	{
		NumberFormat formatter = new DecimalFormat("##,###,###");
		Utility util = new Utility();
		this.getNppInfo();

		String query=	"  SELECT 0 as ptchietkhau,a.kho_fk,b.chuyenkho_fk dondathangnpp_fk, a.nhomkenh_fk, a.npp_fk, ngayxuatHD, ISNULL(a.ghichu, '') as ghichu, \n"+
						"  		  a.khachhang_fk, a.npp_dat_fk, a.nhanvien_fk, a.trangthai, kyhieu, sohoadon, hinhthuctt ,  isnull(a.chietkhau,0) as chietkhau, \n"+
						"   	  a.nguoimua, a.TENXUATHD, a.diachi, a.masothue, \n" + 
						" 		  isnull(innguoimua,1) as innguoimua,  isnull(tongtienbvat,0) as tongtienbvat,  isnull(tongtienavat,0) \n" +
						"   	  as tongtienavat,  isnull(a.vat, 0) as vat, isnull(a.chietkhau, 0) as chietkhau, loaixuatHD,isnull(mavv,'') as mavv, a.KHGHINO, a.loaihoadon,  isnull(a.NGAYGHINHAN, GETDATE()) ngayghinhan, \n"+
						"		  0 as tientichluy, isnull(a.ghichu2, '') ghichu2, isnull( a.nguoimua, '' ) nguoimua , isnull(a.TENXUATHD,'') tenxuathd , isnull(a.diachi,'') diachi, isnull(a.masothue, '' ) masothue \n" +
						"  FROM ERP_HOADONNPP a \n"+
						"  INNER JOIN ERP_HOADONNPP_CHUYENKHO b on b.HOADONNPP_FK = a.PK_SEQ \n"+
						"  WHERE a.pk_seq = '"+ this.id +"'";
		
		System.out.println("____INIT HOADON: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayxuatHD = rs.getString("ngayxuatHD");
					this.ghichu = rs.getString("ghichu");
					this.hinhthuctt = rs.getString("hinhthuctt");
					this.kyhieuhoadon = rs.getString("kyhieu");
					this.sohoadon = rs.getString("sohoadon");
					this.khId = rs.getString("khachhang_fk") == null ? "" : rs.getString("khachhang_fk");
					this.nhanvienId = rs.getString("nhanvien_fk") == null ? "" : rs.getString("nhanvien_fk");
					this.nppId = rs.getString("npp_dat_fk");

					this.doituong = rs.getString("loaixuatHD");		
					this.innguoimua = rs.getString("innguoimua");
					this.trangthai = rs.getString("trangthai");	
					this.loaidonhang = rs.getString("loaihoadon");
					this.ngayghinhanCN = rs.getString("ngayghinhan");
					
					this.ptchietkhau = rs.getString("ptchietkhau");
	
					this.bvat = formatter.format(rs.getDouble("tongtienbvat"));
					this.totalCHIETKHAU = formatter.format(rs.getDouble("chietkhau"));
					this.thueVAT = formatter.format(rs.getDouble("vat"));
					this.avat = formatter.format(rs.getDouble("tongtienavat"));
					
					this.Ghichu2 = rs.getString("ghichu2");
					
					this.tenxuathd = rs.getString("tenxuathd");					
					this.nguoimuahang = rs.getString("nguoimua");	
					this.donvimua = rs.getString("tenxuathd");
					this.diachi = rs.getString("diachi");	
					this.masothue = rs.getString("masothue");
					
					this.ddhId="";
					//INIT DDH
					query = "SELECT HOADONNPP_FK, CHUYENKHO_FK DDH_FK FROM ERP_HOADONNPP_CHUYENKHO WHERE HOADONNPP_FK = " + this.id;
					System.out.println("---LAY DDH: " + query );
					rs = this.db.get(query);
					if(rs!=null)
					{
						
						String _ddhId = "";
						while(rs.next())
						{
							_ddhId = _ddhId + rs.getString("DDH_FK") + ",";
						}
						
						if(_ddhId.trim().length() > 0)
						{
							_ddhId = _ddhId.substring(0, _ddhId.length() - 1);
							this.ddhId = _ddhId;
						}
					}
					
					query =
					 " SELECT  a.PK_SEQ, isnull(Yeucauchuyenkho_fk,0) as PHIEUYEUCAU, a.lydo, a.NgayChuyen \n"+
					 " FROM ERP_CHUYENKHO a   \n"+
					 " LEFT JOIN KHO KHOTT on a.khonhan_fk = KHOTT.PK_SEQ  \n"+
					 " WHERE a.pk_seq > 0  and a.nhanhang_fk is null and a.npp_fk = "+this.nppDangnhap+" and a.lenhsanxuat_fk  is null "+
					 " AND a.TRANGTHAI IN (2,3) \n" +
					 " AND a.PK_SEQ NOT IN (SELECT CHUYENKHO_FK FROM ERP_HOADONNPP_CHUYENKHO A INNER JOIN ERP_HOADONNPP B ON A.HOADONNPP_FK = B.PK_SEQ \n" +
					 "						WHERE B.TRANGTHAI NOT IN (3,5) AND B.PK_SEQ != "+( this.id.trim().length() > 0 ? this.id : "-1" )+" ) \n"+
					 " ORDER BY a.NgayChuyen desc ";
					
					System.out.println(":::LAY DDH: " + query);		
					this.ddhRs = db.get(query);
				}
				rs.close();
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}		
		  
		try
		{
			//INIT SOLO
			query = "select b.ma, ct.scheme, solo, ngayhethan, sum(soluong) as soluong  " +
					"from ERP_HOADONNPP_SP_CHITIET  ct inner join SANPHAM b on ct.MA = b.MA "+ 
					"where hoadon_fk = '" + this.id + "' "+
					"group by b.ma, ct.scheme, solo, ngayhethan order by ct.scheme asc ";
			System.out.println("---LO DA XUAT: " + query);
			
			ResultSet rsSOLO = db.get(query);
			this.sanpham_soluong = new Hashtable<String, String>();
			if(rsSOLO != null)
			{
				while(rsSOLO.next())
				{
					String scheme = " ";
					if(rsSOLO.getString("scheme").trim().length() > 0)
						scheme = rsSOLO.getString("scheme").trim();
					
					String key = rsSOLO.getString("ma") + "__" + scheme + "__" + rsSOLO.getString("solo") + "__" + rsSOLO.getString("ngayhethan");
					this.sanpham_soluong.put(key, rsSOLO.getString("soluong"));
				}
				rsSOLO.close();
			}
		}
		catch (Exception e) 
		{	
			e.printStackTrace();
		}

		this.createRs();
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	public String getHinhthucTT() {
		
		return this.hinhthuctt;
	}

	public void setHinhthucTT(String hinhthucTT) {
		
		this.hinhthuctt = hinhthucTT;
	}
	
	public String[] getSpId() {
		
		return this.spId;
	}

	
	public void setSpId(String[] spId) {
		
		this.spId = spId;
	}

	
	public String[] getSpMa() {
		
		return this.spMa;
	}

	
	public void setSpMa(String[] spMa) {
		
		this.spMa = spMa;
	}

	
	public String[] getSpTen() {
		
		return this.spTen;
	}

	
	public void setSpTen(String[] spTen) {
		
		this.spTen = spTen;
	}

	
	public String[] getSpDonvi() {
		
		return this.spDonvi;
	}

	
	public void setSpDonvi(String[] spDonvi) {
		
		this.spDonvi = spDonvi;
	}

	
	public String[] getSpSoluong() {
		
		return this.spSoluong;
	}

	
	public void setSpSoluong(String[] spSoluong) {
		
		this.spSoluong = spSoluong;
	}


	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public String getDondathangId() {
		
		return this.ddhId;
	}

	
	public void setDondathangId(String kbhId) {
		
		this.ddhId = kbhId;
	}

	
	public ResultSet getDondathangRs() {
		
		return this.ddhRs;
	}

	
	public void setDondathangRs(ResultSet ddhRs) {
		
		this.ddhRs = ddhRs;
	}

	
	public boolean create( String congtyId ) 
	{
		this.getNppInfo();
		if(this.ngayxuatHD.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày xuất hóa đơn";
			return false;
		}
		
		if(this.ddhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn đặt hàng";
			return false;
		}
				
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm xuất hoá đơn";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++)
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					if(Double.parseDouble(spSoluong[i].trim().replace(",", "")) > 0)
						coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn";
				return false;
			}	
		}
		
		try
		{
			//CHECK NGÀY HÓA ĐƠN KHÔNG ĐƯỢC NHỎ HƠN NGÀY ĐƠN HÀNG
			String query = "";
			
			db.getConnection().setAutoCommit(false);
			
			String loaiNPP = "1";
			
			long sohoadontu = 0;
			
			String sohoadonden = "";
			
			String mau = "1";
			
			query= " select  sohoadontu, sohoadonden " +
				   " from NHANVIEN" +
				   " where pk_seq= '"+ this.userId +"' and  isnull(kyhieu,'')= '"+ this.kyhieuhoadon +"' ";
			
			ResultSet rsLayDL =  db.get(query);
			if(rsLayDL != null)
			{
				while(rsLayDL.next())
				{
					sohoadontu = rsLayDL.getLong("sohoadontu");
					sohoadonden = rsLayDL.getString("sohoadonden");
				}
				rsLayDL.close();
			}
			if (sohoadontu == 0 || sohoadonden.trim().length() <= 0 )
			{
				this.msg = "Ký hiệu "+ this.kyhieuhoadon +" khác với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn)";
				db.getConnection().rollback();
				return false;
			}
			
			// Check So hoa don sua da co dung chua
			   // OTC
			query=  " select count(pk_seq) as kiemtra \n" +
		       		" from ERP_XUATHOADONKM \n" +
		       		" where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu)) ='"+ this.kyhieuhoadon +"' \n" +
		       		" and trangthai != '2' and npp_fk = "+this.nppDangnhap+"  and mauhoadon = 1 and pk_seq != "+ (this.id == "" ? "0": this.id) +" \n";
			
			System.out.println("Cau kiem tra so hoa don "+query);
			ResultSet KtraSHD =  db.get(query);
			int ktra= 0;
			
			if(KtraSHD != null)
			{
				while(KtraSHD.next())
				{
					ktra = KtraSHD.getInt("kiemtra");
				}
				KtraSHD.close();
			}
			
			// ETC
			query= 	" select  count(pk_seq) as kiemtra \n" +
		       		" from ERP_HOADONNPP \n" +
		       		" where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu))='"+ this.kyhieuhoadon +"' \n" +
		       		" and trangthai != '3' and pk_seq != "+ (this.id == "" ? "0": this.id) +"  and npp_fk = "+this.nppDangnhap+" and mauhoadon = 1  \n";
			
			System.out.println("Cau kiem tra so hoa don "+query);
			ResultSet KtraSHD_ETC =  db.get(query);
			int ktra_ETC= 0;
			
			if(KtraSHD_ETC != null)
			{
				while(KtraSHD_ETC.next())
				{
					ktra_ETC = KtraSHD_ETC.getInt("kiemtra");
				}
				KtraSHD_ETC.close();
			}
				
				
			if (ktra > 0 || ktra_ETC > 0 )
			{
				this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
				db.getConnection().rollback();
				return false;
			}
			else if(this.sohoadon.trim().length() != 7)
			{
				this.msg = "Số hóa đơn phải đủ 7 ký tự .Vui lòng kiểm tra lại! ";
				db.getConnection().rollback();
				return false;
			}
			
			
			// LOẠI HÓA ĐƠN = 6: XUẤT HÓA ĐƠN KHÁC
			query =    " INSERT ERP_HOADONNPP ( innguoimua,ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt , \n" +
				       " 		chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaihoadon, loaixuathd, npp_fk, mauhoadon, \n" +
				       "		congty_fk, GHICHU2, NGAYGHINHAN , khachhang_fk, npp_dat_fk , TENXUATHD, diachi, masothue, nguoimua ) \n" +
					   " 		SELECT "+ this.innguoimua +", '" + this.ngayxuatHD + "', '1','" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', RTRIM(LTRIM('" + this.kyhieuhoadon + "')), " +
					   "        RTRIM(LTRIM('" + this.sohoadon + "')), N'"+ this.hinhthuctt +"' , '"+ this.totalCHIETKHAU.replaceAll(",", "")  +"', '"+ this.bvat.replaceAll(",", "") +"', '" + this.avat.replaceAll(",", "")  +"'," +
					   "        '"+ this.thueVAT.replaceAll(",", "") +"', N'"+ this.ghichu +"', '6', "+this.doituong+", '"+ this.nppDangnhap +"', '1', " +
					   "	    '" + congtyId + "', N'"+this.Ghichu2+"', '"+this.ngayghinhanCN+"', " +(this.khId == "" ? null:this.khId)+", "+(this.nppId == "" ? null:this.nppId)+
					   " 		, N'"+ this.donvimua +"', N'" + this.diachi + "', N'" + this.masothue + "', N'"+ this.nguoimuahang +"' ";
 			
			System.out.println("1.Insert ERP_HOADONNPP: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_HOADONNPP " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String hdId = "";
			query = "select SCOPE_IDENTITY() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();
			
			if(this.doituong.equals("1"))
			{
				// CẬP NHẬT LẠI THÔNG TIN KHÁCH HÀNG VÀO DỮ LIỆU NỀN
				query = " SELECT COUNT(*) as NUM FROM KHACHHANG_THONGTINHOADON " +
						" WHERE TENNGUOIMUA = N'" + this.nguoimuahang + "' AND DONVI = N'" + this.donvimua + "' " +
						" AND DIACHI = N'" + this.diachi + "' AND MASOTHUE = '" + this.masothue + "' " +
						" AND KHACHHANG_FK = " + this.khId;
				
				ResultSet rs = this.db.get(query);
				rs.next();
				
				if(rs.getInt("NUM") == 0){
					
					query = "INSERT INTO KHACHHANG_THONGTINHOADON(TENNGUOIMUA, DONVI, DIACHI, MASOTHUE, KHACHHANG_FK, ACTIVE) VALUES( " +
							" N'" + this.nguoimuahang + "', N'" + this.donvimua + "', N'" + this.diachi + "', N'" + this.masothue + "', " + this.khId + ", '1' )";
					
					System.out.println("::: CAP NHAT THONG TIN XUAT HOA DON: " + query );
					this.db.update(query);
				}
			
			}
			
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					String thanhtien = spSoluong[i].replaceAll(",", "")+ " * "+ spDongia[i].replaceAll(",", "");
					
					query = " insert ERP_HOADONNPP_SP ( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme , vat , chuyenkho_fk, chonin) " +
							" values ( "+ hdId +", '" + spId[i] + "', N'" + spTen[i] + "', N'"+ spDonvi[i] +"', '" + spSoluong[i].replaceAll(",", "") + "', '" + spDongia[i].replaceAll(",", "") + "'," +
							" "+ thanhtien +", '"+ spChietkhau[i].replaceAll(",", "") +"', N'"+ spSCheme[i].replaceAll(",", "") +"', '"+ spVat[i].replaceAll(",", "") +"',"+ckId[i]+", 1 ) ";
					
					System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
					
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
						db.getConnection().rollback();
						return false;
					}
					
				}
			}
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
									
			query = "Insert ERP_HOADONNPP_CHUYENKHO(hoadonnpp_fk, chuyenkho_fk) select " + hdId + ", pk_seq FROM ERP_CHUYENKHO WHERE PK_SEQ IN ("+this.ddhId + ")";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
				db.getConnection().rollback();
				return false;
			}					
		
			System.out.println("1.2.Insert ERP_HOADONNPP_CHUYENKHO: " + query);
			
			//CHÈN VÀO BẢNG HÓA ĐƠN - SẢN PHẨM CHI TIẾT
			//Lưu vào bảng hóa đơn chi tiết
			
			System.out.println("spMa:"+spMa.length);
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					if(this.sanpham_soluong != null)
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						double totalCT = 0;
						
						String sqlHOADON_CHITIET = "";
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							if(key.startsWith( spMa[i] + "__" + spSCheme[i] + "__" +ckId[i] ))
							{
								System.out.println(":::: KEYS: " + key);
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}								
								
								totalCT += Double.parseDouble(_soluongCT);
																
								sqlHOADON_CHITIET  += " select '" + hdId + "' as hoadonId, '" + _sp[2] + "' as chuyenkho_fk, "+spId[i]+" as sanphamId, '" + spMa[i] + "' as spMa, N'" + spTen[i] + "' as spTen, N'" + spDonvi[i] + "' as donvi, " + 
													  " '"+spDonvi[i]+"' as dvCHUAN, " + spDongia[i].replaceAll(",", "") + " as dongia, a.thuexuat, N'" + _sp[3] + "' as solo, '" + _soluongCT.replaceAll(",", "") + "' as soluong, '" + _sp[4] + "' as NgayHetHan, N'" + spSCheme[i] + "' as scheme, 1 as chonIn " +
													  " from SANPHAM a " + 
													  " where a.MA = '" + spMa[i] + "' ";
								
								sqlHOADON_CHITIET += " union ALL ";
							}
						}
													
						System.out.println("::::CHEN HOA DON CHI TIET1111: " + sqlHOADON_CHITIET);
						
						sqlHOADON_CHITIET = sqlHOADON_CHITIET.substring(0, sqlHOADON_CHITIET.length() - 10);
	
						query = " insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, CHUYENKHO_FK, sanpham_fk, ma, ten, donvi, dvCHUAN, dongia, thueVAT, SOLO, SOLUONG, NGAYHETHAN, SCHEME, CHONIN) " 
								+ sqlHOADON_CHITIET;
						
						
						if(!db.update(query))
						{
							this.msg = "Lỗi khi thực thi câu lệnh: " + query;
							db.getConnection().rollback();
							return false;
						}
					}	
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	
	public boolean update() 
	{
		this.getNppInfo();		
		
		if(this.ngayxuatHD.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày xuất hóa đơn";
			return false;
		}

		if(this.ngayghinhanCN.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày ghi nhận";
			return false;
		}
		
		if(this.ddhId.length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn đặt hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn tài chính";
			return false;
		}
		else
		{
			boolean coSP = false;
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
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn tài chính";
				return false;
			}	
		}
		
		try
		{
			
			db.getConnection().setAutoCommit(false);
			
			String 	query= "";
			// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
			String loaiNPP = "1";
			
			long sohoadontu = 0;
			
			String sohoadonden = "";
			
			String mau = "1";
			
			query= " select  sohoadontu, sohoadonden " +
				   " from NHANVIEN" +
				   " where pk_seq= '"+ this.userId +"' and  isnull(kyhieu,'')= '"+ this.kyhieuhoadon +"' ";
			
			ResultSet rsLayDL =  db.get(query);
			if(rsLayDL != null)
			{
				while(rsLayDL.next())
				{
					sohoadontu = rsLayDL.getLong("sohoadontu");
					sohoadonden = rsLayDL.getString("sohoadonden");
				}
				rsLayDL.close();
			}
			if (sohoadontu == 0 || sohoadonden.trim().length() <= 0 )
			{
				this.msg = "Ký hiệu "+ this.kyhieuhoadon +" khác với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn)";
				db.getConnection().rollback();
				return false;
			}
			
			// Check So hoa don sua da co dung chua
			   // OTC
			query= 	" select count(pk_seq) as kiemtra \n" +
		       		" from ERP_XUATHOADONKM \n" +
		       		" where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu)) ='"+ this.kyhieuhoadon +"' \n" +
		       		" and trangthai != '2' and npp_fk = "+this.nppDangnhap+"  and mauhoadon = 1 and pk_seq != "+ (this.id == "" ? "0": this.id) +" \n";
			
			System.out.println("Cau kiem tra so hoa don "+query);
			ResultSet KtraSHD =  db.get(query);
			int ktra= 0;
			
			if(KtraSHD != null)
			{
				while(KtraSHD.next())
				{
					ktra = KtraSHD.getInt("kiemtra");
				}
				KtraSHD.close();
			}
			
			// ETC
			query= 	" select  count(pk_seq) as kiemtra \n" +
		       		" from ERP_HOADONNPP \n" +
		       		" where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu))='"+ this.kyhieuhoadon +"' \n" +
		       		" and trangthai != '3' and pk_seq != "+ (this.id == "" ? "0": this.id) +"  and npp_fk = "+this.nppDangnhap+" and mauhoadon = 1  \n";
			
			System.out.println("Cau kiem tra so hoa don "+query);
			ResultSet KtraSHD_ETC =  db.get(query);
			int ktra_ETC= 0;
			
			if(KtraSHD_ETC != null)
			{
				while(KtraSHD_ETC.next())
				{
					ktra_ETC = KtraSHD_ETC.getInt("kiemtra");
				}
				KtraSHD_ETC.close();
			}
								
			if (ktra > 0 || ktra_ETC > 0 )
			{
				this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
				db.getConnection().rollback();
				return false;
			}
			else if(this.sohoadon.trim().length() != 7)
			{
				this.msg = "Số hóa đơn phải đủ 7 ký tự .Vui lòng kiểm tra lại! ";
				db.getConnection().rollback();
				return false;
			}

			
			if(this.nppId.length() <= 0) this.nppId = "NULL";
			if(this.khId.length() <= 0) this.khId = "NULL";
						
			query = "";
			
			double tongtien = 0;
			double tongtienck = 0;
			double tongvat = 0;
			double tongsauck = 0;
			double tongsauvat = 0;

			String tooltip = "NULL";
			String table = "";
			
			// LOẠI HÓA ĐƠN = 6: XUẤT HÓA ĐƠN KHÁC			
				
			 query = " update ERP_HOADONNPP set innguoimua= "+ this.innguoimua +", ngayxuatHD = '" + this.ngayxuatHD + "' , ngaysua = '" + getDateTime() + "' , nguoisua ='" + this.userId + "'," +
				     " kyhieu = RTRIM(LTRIM('" + this.kyhieuhoadon + "')) , sohoadon= RTRIM(LTRIM('" + this.sohoadon + "')), hinhthuctt= N'"+ this.hinhthuctt +"' ," +
				     " chietkhau =  '"+ this.totalCHIETKHAU.replaceAll(",", "")  +"' , tongtienbvat= '"+ this.bvat.replaceAll(",", "") +"', tongtienavat='" + this.avat.replaceAll(",", "") + "', vat = '"+ this.thueVAT.replaceAll(",", "") + "', ghichu= N'"+ this.ghichu +"'," +
				     " loaixuathd = "+this.doituong+" , npp_fk = '"+ this.nppDangnhap +"', " + 
				     " GHICHU2 = N'"+this.Ghichu2+"', "+
				     " ngayghinhan = '"+this.ngayghinhanCN+"', khachhang_fk = "+(this.khId == "" ? null:this.khId) +", npp_dat_fk = "+(this.nppId == "" ? null:this.nppId)+
				     " , TENXUATHD =  N'"+ this.donvimua +"', diachi = N'" + this.diachi + "', masothue = N'" + this.masothue + "', nguoimua = N'"+ this.nguoimuahang +"' " + 
				     " where pk_seq = '"+ this.id +"'" ;
				 
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_HOADONNPP " + query;
					db.getConnection().rollback();
					return false;
				}
				
				Utility util = new Utility();
				
				msg = util.Check_Huy_NghiepVu_KhoaSo("ERP_HOADONNPP", id, "ngayxuatHD", db);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					return false;
				}
				
				query = "delete from ERP_HOADONNPP_SP where hoadon_fk = '"+ this.id +"' " ;	
				System.out.println(query);
				if(!db.update(query))
				{
					this.msg = "Không thể xóa HOADON_SP " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = "delete from ERP_HOADONNPP_SP_ChiTiet where hoadon_fk = " + this.id;
				if(db.updateReturnInt(query)<=0)
				{
					msg = "Khong the cap nhat ERP_HOADONNPP_SP_ChiTiet: " + query;
					db.getConnection().rollback();
					return false;
				}						
				
				query = "delete from ERP_HOADONNPP_CHUYENKHO where hoadonnpp_fk = '"+ this.id +"' " ;
				if(!db.update(query))
				{
					this.msg = "Không thể xóa ERP_HOADONNPP_CHUYENKHO " + query;
					db.getConnection().rollback();
					return false;
				}
							
				for(int i = 0; i < spId.length; i++)
				{
					if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
					{
						String thanhtien = spSoluong[i].replaceAll(",", "")+ " * "+ spDongia[i].replaceAll(",", "");
						
						query = " insert ERP_HOADONNPP_SP ( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme , vat , chuyenkho_fk, chonin) " +
								" values ( "+ this.id +", '" + spId[i] + "', N'" + spTen[i] + "', N'"+ spDonvi[i] +"', '" + spSoluong[i].replaceAll(",", "") + "', '" + spDongia[i].replaceAll(",", "") + "'," +
								" "+ thanhtien +", '"+ spChietkhau[i].replaceAll(",", "") +"', N'"+ spSCheme[i].replaceAll(",", "") +"', '"+ spVat[i].replaceAll(",", "") +"',"+ckId[i]+", 1 ) ";
						
						System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
							db.getConnection().rollback();
							return false;
						}
						
					}
				}
				NumberFormat formatter = new DecimalFormat("#,###,###.##");
											
				query = "Insert ERP_HOADONNPP_CHUYENKHO ( hoadonnpp_fk, chuyenkho_fk ) select " + this.id + ", pk_seq FROM ERP_CHUYENKHO WHERE PK_SEQ IN ("+this.ddhId + ")";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
					db.getConnection().rollback();
					return false;
				}					
				if(this.doituong.equals("1"))
				{
					// CẬP NHẬT LẠI THÔNG TIN KHÁCH HÀNG VÀO DỮ LIỆU NỀN
					query = " SELECT COUNT(*) as NUM FROM KHACHHANG_THONGTINHOADON " +
							" WHERE TENNGUOIMUA = N'" + this.nguoimuahang + "' AND DONVI = N'" + this.donvimua + "' " +
							" AND DIACHI = N'" + this.diachi + "' AND MASOTHUE = '" + this.masothue + "' " +
							" AND KHACHHANG_FK = " + this.khId;
					
					ResultSet rs = this.db.get(query);
					rs.next();
					
					if(rs.getInt("NUM") == 0){
						
						query = "INSERT INTO KHACHHANG_THONGTINHOADON(TENNGUOIMUA, DONVI, DIACHI, MASOTHUE, KHACHHANG_FK, ACTIVE) VALUES( " +
								" N'" + this.nguoimuahang + "', N'" + this.donvimua + "', N'" + this.diachi + "', N'" + this.masothue + "', " + this.khId + ", '1' )";
						
						System.out.println("::: CAP NHAT THONG TIN XUAT HOA DON: " + query );
						this.db.update(query);
					}
				
				}
							
				
				//CHÈN VÀO BẢNG HÓA ĐƠN - SẢN PHẨM CHI TIẾT
				//Lưu vào bảng hóa đơn chi tiết
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
					{
						if(this.sanpham_soluong != null)
						{
							Enumeration<String> keys = this.sanpham_soluong.keys();
							double totalCT = 0;
							
							String sqlHOADON_CHITIET = "";
							while(keys.hasMoreElements())
							{
								String key = keys.nextElement();
								
								if(key.startsWith( spMa[i] + "__" + spSCheme[i] + "__" +ckId[i] ))
								{
									System.out.println(":::: KEYS: " + key);
									String[] _sp = key.split("__");
									
									String _soluongCT = "0"; 
									if(this.sanpham_soluong.get(key) != null)
									{
										_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
									}								
									
									totalCT += Double.parseDouble(_soluongCT);
																	
									sqlHOADON_CHITIET  += " select '" + this.id + "' as hoadonId, '" + _sp[2] + "' as chuyenkho_fk, "+spId[i]+" as sanphamId, '" + spMa[i] + "' as spMa, N'" + spTen[i] + "' as spTen, N'" + spDonvi[i] + "' as donvi, " + 
														  " '"+spDonvi[i]+"' as dvCHUAN, " + spDongia[i].replaceAll(",", "") + " as dongia, a.thuexuat, N'" + _sp[3] + "' as solo, '" + _soluongCT.replaceAll(",", "") + "' as soluong, '" + _sp[4] + "' as NgayHetHan, N'" + spSCheme[i] + "' as scheme, 1 as chonIn " +
														  " from SANPHAM a " + 
														  " where a.MA = '" + spMa[i] + "' ";
									
									sqlHOADON_CHITIET += " union ALL ";
								}
							}
														
							System.out.println("::::CHEN HOA DON CHI TIET1111: " + sqlHOADON_CHITIET);
							
							sqlHOADON_CHITIET = sqlHOADON_CHITIET.substring(0, sqlHOADON_CHITIET.length() - 10);
		
							query = " insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, CHUYENKHO_FK, sanpham_fk, ma, ten, donvi, dvCHUAN, dongia, thueVAT, SOLO, SOLUONG, NGAYHETHAN, SCHEME, CHONIN) " 
									+ sqlHOADON_CHITIET;
							
							
							if(!db.update(query))
							{
								this.msg = "Lỗi khi thực thi câu lệnh: " + query;
								db.getConnection().rollback();
								return false;
							}
						}	
					}
				}
				
				
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
									
		}
		
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	private String Cap_Nhat_LenhXuatHang( String ddhId ) 
	{
		String msg = "";
		String query = "";
		
		//Cập nhật lại số lô cho lệnh xuất hàng khi thay dổi số lô trong lúc xuất hóa đơn, lượng chênh lệch sẽ tạo ra hóa đơn nằm chờ,
		//// lúc tạo mới hóa đơn, chỉ lấy lượng hóa đơn nằm chờ để xử lý tiếp
		
		/*if(this.khKGId.trim().length() <= 0)
		{
			query =  "update khoCT  " + 
					 "	set khoCT.SOLUONG = khoCT.SOLUONG + DDH.SoLuong, " + 
					 "		khoCT.AVAILABLE = khoCT.AVAILABLE + DDH.SoLuong " + 
					 "from NHAPP_KHO_CHITIET khoCT inner join " + 
					 "( " + 
					 "	select b.NPP_FK as nppId,  " + 
					 "		  case when " + this.dungchungKENH + " = 1 then 100000 else b.nhomkenh_fk end as nhomkenh_fk,  " + 
					 "		  b.Kho_FK, a.sanpham_fk, a.solo, a.ngayhethan, a.soluong * dbo.LayQuyCach ( a.SANPHAM_FK, c.DVDL_FK, ISNULL( a.DVDL_FK, c.DVDL_FK ) ) as soluong  " + 
					 "	from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join ERP_DONDATHANGNPP b on b.PK_SEQ = a.DonDatHang_FK inner join SANPHAM c on a.sanpham_fk = c.pk_seq   " + 
					 "	where a.DonDatHang_FK = '" + this.ddhId + "' and  hoadon_fk =  " +this.id+ 
					 ") " + 
					 "DDH on khoCT.NPP_FK = DDH.nppId and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk " + 
					 "		and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan ";
		}
		else
		{
			query =  "update khoCT  " + 
					 "	set khoCT.SOLUONG = khoCT.SOLUONG + DDH.SoLuong, " + 
					 "		khoCT.AVAILABLE = khoCT.AVAILABLE + DDH.SoLuong " + 
					 "from NHAPP_KHO_KYGUI_CHITIET khoCT inner join " + 
					 "( " + 
					 "	select b.NPP_FK as nppId,  " + 
					 "		  case when " + this.dungchungKENH + " = 1 then 100000 else b.nhomkenh_fk end as nhomkenh_fk,  " + 
					 "		  b.Kho_FK, a.sanpham_fk, a.solo, a.ngayhethan, a.soluong * dbo.LayQuyCach ( a.SANPHAM_FK, c.DVDL_FK, ISNULL( a.DVDL_FK, c.DVDL_FK ) ) as soluong  " + 
					 "	from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join ERP_DONDATHANGNPP b on b.PK_SEQ = a.DonDatHang_FK inner join SANPHAM c on a.sanpham_fk = c.pk_seq   " + 
					 "	where a.DonDatHang_FK = '" + this.ddhId + "' and  hoadon_fk =  " +this.id+ 
					 ") " + 
					 "DDH on khoCT.NPP_FK = DDH.nppId and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk " + 
					 "		and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan and khoCT.khachhang_fk = '" + this.khKGId + "' and khoCT.isNPP = '0' ";
		}
		
		System.out.println("---TANG KHO NGUOC LAI:: " + query );
		if(!db.update(query))
		{
			msg = "Lỗi khi duyệt: " + query;
			return msg;
		}
		
		query = "delete ERP_DONDATHANGNPP_SANPHAM_CHITIET " + 
				" where dondathang_fk in  (" + this.ddhId + ") and hoadon_fk = " + this.id + " ";
		
		if(!db.update(query))
		{
			msg = "Lỗi khi duyệt: " + query;
			return msg;
		}*/
		

		////CHECK SỐ LÔ MỚI CÓ ĐỦ HÀNG KHÔNG TRƯỚC
		if(this.khKGId.trim().length() <= 0)
		{
			query =  "select DDH.MA, DDH.TEN, khoCT.AVAILABLE, DDH.SoLuong_Chuan, DDH.solo  "+
					 "from NHAPP_KHO_CHITIET khoCT inner join "+
					 "( "+
					 "	select hd_ct.kho_fk, hd.npp_fk, ( select nhomkenh_fk from ERP_DONDATHANGNPP where PK_SEQ = ( select DDH_FK from ERP_HOADONNPP_DDH where hoadonnpp_fk = hd.PK_SEQ ) ) as nhomkenh_fk, "+
					 "			sp.PK_SEQ as sanpham_fk, SoLuong_Chuan, SOLO, NGAYHETHAN, sp.MA, sp.TEN "+
					 "	from ERP_HOADONNPP_SP_CHITIET hd_ct inner join ERP_HOADONNPP hd on hd_ct.hoadon_fk = hd.PK_SEQ "+
					 "			inner join SANPHAM sp on hd_ct.MA = sp.MA "+
					 "	where hoadon_fk = '" + this.id + "' "+
					 ") "+
					 "DDH on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
					 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan "+
					 "where khoCT.AVAILABLE < DDH.SoLuong_Chuan ";
		}
		else
		{
			query = "select DDH.MA, DDH.TEN, khoCT.AVAILABLE, DDH.SoLuong_Chuan, DDH.solo  "+
					 "from NHAPP_KHO_KYGUI_CHITIET khoCT inner join "+
					 "( "+
					 "	select hd_ct.kho_fk, hd.npp_fk, ( select nhomkenh_fk from ERP_DONDATHANGNPP where PK_SEQ = ( select DDH_FK from ERP_HOADONNPP_DDH where hoadonnpp_fk = hd.PK_SEQ ) ) as nhomkenh_fk, "+
					 "			sp.PK_SEQ as sanpham_fk, SoLuong_Chuan, SOLO, NGAYHETHAN, sp.MA, sp.TEN "+
					 "	from ERP_HOADONNPP_SP_CHITIET hd_ct inner join ERP_HOADONNPP hd on hd_ct.hoadon_fk = hd.PK_SEQ "+
					 "			inner join SANPHAM sp on hd_ct.MA = sp.MA "+
					 "	where hoadon_fk = '" + this.id + "' "+
					 ") "+
					 "DDH on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
					 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan and khoCT.khachhang_fk = '" + this.khKGId + "' and khoCT.isNPP = '0' "+
					 "where khoCT.AVAILABLE < DDH.SoLuong_Chuan ";
		}
		
		System.out.println("4.0 CHECK TON KHO: " + query);
		ResultSet rsTK = db.get(query);
		if( rsTK != null )
		{
			try 
			{
				while(rsTK.next())
				{
					msg += "Mã hàng: " + rsTK.getString("TEN") + " với số lô " + rsTK.getString("SOLO") + " chỉ còn tối đa " + rsTK.getString("SoLuong_Chuan") + "\n ";
				}
				rsTK.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return "Lỗi: " + e.getMessage();
			}
		}
		
		if( msg.trim().length() > 0 )
		{
			return msg;
		}
		
		//CẬP NHẬT KHO CHI TIẾT VỚI THÔNG TIN MỚI
		if(this.khKGId.trim().length() <= 0)
		{
			query =  "update khoCT  " + 
					 "	set khoCT.SOLUONG = khoCT.SOLUONG - DDH.SoLuong, " + 
					 "		khoCT.AVAILABLE = khoCT.AVAILABLE - DDH.SoLuong " + 
					 "from NHAPP_KHO_CHITIET khoCT inner join "+
					 "( "+
					 "	select hd_ct.kho_fk, hd.npp_fk, ( select nhomkenh_fk from ERP_DONDATHANGNPP where PK_SEQ = ( select DDH_FK from ERP_HOADONNPP_DDH where hoadonnpp_fk = hd.PK_SEQ ) ) as nhomkenh_fk, "+
					 "			sp.PK_SEQ as sanpham_fk, SoLuong_Chuan as soluong, SOLO, NGAYHETHAN, sp.MA, sp.TEN "+
					 "	from ERP_HOADONNPP_SP_CHITIET hd_ct inner join ERP_HOADONNPP hd on hd_ct.hoadon_fk = hd.PK_SEQ "+
					 "			inner join SANPHAM sp on hd_ct.MA = sp.MA "+
					 "	where hoadon_fk = '" + this.id + "' "+
					 ") "+
					 "DDH on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
					 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan ";
		}
		else
		{
			query = "update khoCT  " + 
					 "	set khoCT.SOLUONG = khoCT.SOLUONG - DDH.SoLuong, " + 
					 "		khoCT.AVAILABLE = khoCT.AVAILABLE - DDH.SoLuong " + 
					 "from NHAPP_KHO_KYGUI_CHITIET khoCT inner join "+
					 "( "+
					 "	select hd_ct.kho_fk, hd.npp_fk, ( select nhomkenh_fk from ERP_DONDATHANGNPP where PK_SEQ = ( select DDH_FK from ERP_HOADONNPP_DDH where hoadonnpp_fk = hd.PK_SEQ ) ) as nhomkenh_fk, "+
					 "			sp.PK_SEQ as sanpham_fk, SoLuong_Chuan as soluong, SOLO, NGAYHETHAN, sp.MA, sp.TEN "+
					 "	from ERP_HOADONNPP_SP_CHITIET hd_ct inner join ERP_HOADONNPP hd on hd_ct.hoadon_fk = hd.PK_SEQ "+
					 "			inner join SANPHAM sp on hd_ct.MA = sp.MA "+
					 "	where hoadon_fk = '" + this.id + "' "+
					 ") "+
					 "DDH on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
					 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan and khoCT.khachhang_fk = '" + this.khKGId + "' and khoCT.isNPP = '0' ";
		}
		
		System.out.println("4.1. CAP NHAT TON KHO: " + query);
		if(!db.update(query))
		{
			msg = "Lỗi khi duyệt: " + query;
			return msg;
		}
		
		//THAY ĐỔI LỆNH XUẤT HÀNG THEO THÔNG TIN MỚI
		query = "delete ERP_DONDATHANGNPP_SANPHAM_CHITIET_TEMP where dondathang_fk = '" + ddhId + "' ";
		if(!db.update(query))
		{
			msg = "Lỗi khi duyệt: " + query;
			return msg;
		}
		
		query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET_TEMP(dondathang_fk, SANPHAM_FK, soluong, DVDL_FK, solo, ngayhethan, LOAI, scheme)  "+
				 "select '" + ddhId + "' as dondathang_fk,  "+
				 "		sp.PK_SEQ as sanpham_fk, sum(hd_ct.SOLUONG) as soluong, hd_ct.DVDATHANG, SOLO, NGAYHETHAN,  "+
				 "		case when hd_ct.scheme != '' then 1 else 0 end as loai, hd_ct.scheme "+
				 "from ERP_HOADONNPP_SP_CHITIET hd_ct inner join ERP_HOADONNPP hd on hd_ct.hoadon_fk = hd.PK_SEQ "+
				 "		inner join SANPHAM sp on hd_ct.MA = sp.MA "+
				 "where hoadon_fk in ( select hoadonnpp_fk from ERP_HOADONNPP_DDH where DDH_FK = '" + ddhId + "' ) and hd.trangthai not in ( 3, 5 ) "+ 
				 "group by sp.PK_SEQ, hd_ct.DVDATHANG, SOLO, NGAYHETHAN, hd_ct.scheme ";
		System.out.println("4.2. CHEN BANG TEMP: " + query);
		if(!db.update(query))
		{
			msg = "Lỗi khi duyệt: " + query;
			return msg;
		}
		
		//TÌM NHỮNG SẢN PHẨM KHÔNG XUẤT HẾT TRONG 1 HÓA ĐƠN, ĐỀ XUẤT LẠI SỐ LÔ CHO NHỮNG SP NÀY CHO ĐÚNG
		query =  "select ddh.npp_fk, ddh.kho_fk, ddh.nhomkenh_fk, ddh.sanpham_fk, ddh.ma, ddh.scheme, ddh.loai, " +
				 " 		CT.soluong - isnull(CT_TEMP.soluong, 0) as soluong,  "+
				 " 		CT.soluongCHUAN - isnull(CT_TEMP.soluongCHUAN, 0) as soluongCHUAXUAT  "+
				 "from  "+
				 "( "+
				 "	select sp.PK_SEQ as sanpham_fk, sp.ma, ddh.dvdl_fk, soluong, soluong * dbo.LayQuyCach ( sp.PK_SEQ, NULL, ddh.DVDL_FK ) as soluongCHUAN, " + 
				 " 			dh.nhomkenh_fk, dh.Kho_FK, dh.NPP_FK, 0 as loai, '' as scheme "+
				 "	from ERP_DONDATHANGNPP_SANPHAM ddh inner join ERP_DONDATHANGNPP dh on ddh.dondathang_fk = dh.pk_seq "+
				 "			inner join SANPHAM sp on ddh.sanpham_fk = sp.PK_SEQ "+
				 "	where ddh.dondathang_fk = '" + ddhId + "'  "+
				 "union ALL "+
				 "	select sp.PK_SEQ as sanpham_fk, sp.ma, dvdl_fk, soluong, soluong as soluongCHUAN, dh.nhomkenh_fk, dh.Kho_FK, dh.NPP_FK, 1 as loai, km.SCHEME "+
				 "	from ERP_DONDATHANGNPP_CTKM_TRAKM ddh inner join ERP_DONDATHANGNPP dh on ddh.DONDATHANGID = dh.pk_seq "+
				 "			inner join SANPHAM sp on ddh.SPMA = sp.PK_SEQ "+
				 "			inner join CTKHUYENMAI km on ddh.CTKMID = km.PK_SEQ "+
				 "	where ddh.DONDATHANGID = '" + ddhId + "' and ddh.SPMA is not null "+
				 ") "+
				 "ddh inner join "+
				 "( "+
				 "	select SANPHAM_FK, a.LOAI, dbo.trim(a.scheme) as scheme, "+
				 "		SUM(soluong) as soluong, SUM( soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluongCHUAN  "+
				 "	from ERP_DONDATHANGNPP_SANPHAM_CHITIET a  "+
				 "	where dondathang_fk = '" + ddhId + "'  "+
				 "	group by SANPHAM_FK, a.LOAI, a.scheme "+
				 ") "+
				 "CT on CT.SANPHAM_FK = ddh.SANPHAM_FK and ddh.loai = CT.LOAI and ddh.scheme = CT.scheme left join "+
				 "( "+
				 "	select SANPHAM_FK, a.LOAI, dbo.trim(a.scheme) as scheme, "+
				 "		 SUM(soluong) as soluong, SUM( soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluongCHUAN   "+
				 "	from ERP_DONDATHANGNPP_SANPHAM_CHITIET_TEMP a "+
				 "	where dondathang_fk = '" + ddhId + "'   "+
				 "	group by SANPHAM_FK, a.LOAI, a.scheme "+
				 ") "+
				 "CT_TEMP on CT_TEMP.SANPHAM_FK = ddh.SANPHAM_FK and CT.scheme = CT_TEMP.scheme and CT.LOAI = CT_TEMP.LOAI "+
				 "where CT.soluongCHUAN != isnull(CT_TEMP.soluongCHUAN, 0) ";
		
		System.out.println("4.3. NHUNG SP CHUA XUAT HET: " + query);
		ResultSet rs = db.get(query);
		if( rs != null )
		{
			try 
			{
				while(rs.next())
				{
					String sanpham_fk = rs.getString("sanpham_fk");
					double soluong = rs.getDouble("soluong");
					double soluongCHUAXUAT = rs.getDouble("soluongCHUAXUAT");
					
					String nppId = rs.getString("npp_fk");
					String khoId = rs.getString("kho_fk");
					String nhomkenh_fk = rs.getString("nhomkenh_fk");
					
					String loai = rs.getString("loai");
					String scheme = rs.getString("scheme");
					
					//Số lượng quy đổi ra đơn vị chuẩn khi tách hóa đơn không được lẻ
					double soluongCHUAXUAT_NGUYEN = Math.round(soluongCHUAXUAT);
					if( soluongCHUAXUAT_NGUYEN - soluongCHUAXUAT != 0 )
					{
						msg = "Số lượng muốn tách của mã sản phẩm: " + rs.getString("ma") + " đang bị lẻ khi quy ra đơn vị chuẩn. Vui lòng kiểm tra lại.";
						rs.close();
						return msg;
					}
					
					if( soluongCHUAXUAT > 0 ) 
					{
						//ĐỀ XUẤT LÔ VỚI SỐ LƯỢNG CÒN LẠI, ƯU TIÊN LẤY NHỮNG LÔ ĐÃ ĐƯỢC CHỌN, LƯỢNG HÀNG NÀY SẼ ĐƯỢC CẬP NHẬT TỒN KHO LUÔN
						msg = this.tudongDEXUAT_SOLO( nppId, khoId, nhomkenh_fk, ddhId, sanpham_fk, soluong, soluongCHUAXUAT , loai, scheme );
						if( msg.trim().length() > 0 )
						{
							return msg;
						}
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return "Lỗi khi duyệt: " + e.getMessage();
			}
		}
		
		//Lượng hàng này sẽ được CỘNG gộp vào hóa đơn cũ để thành LỆNH XUẤT HÀNG với thông tin số lô mới
		query = "delete ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + ddhId + "' ";
		if(!db.update(query))
		{
			msg = "Lỗi khi duyệt: " + query;
			return msg;
		}
		
		query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, soluong, DVDL_FK, solo, ngayhethan, LOAI, scheme )  "+
				"select dondathang_fk, SANPHAM_FK, sum(soluong) as soluong, DVDL_FK, solo, ngayhethan, LOAI, scheme "+
				"from ERP_DONDATHANGNPP_SANPHAM_CHITIET_TEMP where dondathang_fk = '" + ddhId + "' "+
				"group by dondathang_fk, SANPHAM_FK, DVDL_FK, solo, ngayhethan, LOAI, scheme ";
		System.out.println("4.5. CAP NHAT LAI LENH XUAT HANG: " + query);
		if(!db.update(query))
		{
			msg = "Lỗi khi duyệt: " + query;
			return msg;
		}
		
		//CAP NHAT LAI COT SO LUONG CHUAN
		query =  "update a  "+
				 "set a.soluongCHUAN = a.soluong * ISNULL( dbo.LayQuyCach( a.SANPHAM_FK, null, a.DVDL_FK ), 0 ) "+
				 "from ERP_DONDATHANGNPP_SANPHAM_CHITIET a "+
				 "where a.dondathang_fk = '" + ddhId + "' ";
		if( !db.update(query) )
		{
			msg = "Loi cap nhat ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query;
			return msg;
		}	
		
		return msg;
	}

	private String tudongDEXUAT_SOLO(String nppID, String khoId, String nhomkenh_fk, String ddhId, String spId, double soluongDATHANG, double soluong, String loai, String scheme )
	{
		System.out.println("::::: SAN PHAM: " + spId + " -- SO LUONG CAN DE XUAT: " + soluong + " -- SO LUOGN DAT HANG: " + soluongDATHANG );
		String msg = "";
		String query = "";
		
		try
		{
			//TU DE XUAT LO --> KHO CHI TIET THI VAN TRU SO LUONG + AVAI
			if(this.khKGId.trim().length() <= 0 )
			{
				/*query = "select AVAILABLE, SOLO, NGAYHETHAN from NHAPP_KHO_CHITIET " +
						"where AVAILABLE > 0 and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' "+ 
						"order by NGAYHETHAN asc ";*/
				
				query = "select distinct kho.AVAILABLE, kho.SOLO, kho.NGAYHETHAN, ISNULL( hdOLD.stt, 100 ) as stt  "+
						 "from NHAPP_KHO_CHITIET kho left join "+
						 "	( "+
						 "		select solo, ngayhethan, 1 as stt  "+
						 "		from ERP_DONDATHANGNPP_SANPHAM_CHITIET_TEMP  "+
						 "		where dondathang_fk = '" + ddhId + "' and sanpham_fk = '" + spId + "' "+
						 "	) "+
						 "	hdOLD on kho.SOLO = hdOLD.solo and kho.NGAYHETHAN = hdOLD.ngayhethan "+
						 "where AVAILABLE > 0 and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "'  "+
						 "order by ISNULL( hdOLD.stt, 100 ) asc, kho.NGAYHETHAN asc ";
			}
			else
			{
				/*query = "select AVAILABLE, SOLO, NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET " +
						"where AVAILABLE > 0 and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and khachhang_fk = '" + this.khKGId + "' and isNPP = '0' "+ 
						"order by NGAYHETHAN asc  ";*/
				
				query = "select distinct kho.AVAILABLE, kho.SOLO, kho.NGAYHETHAN, ISNULL( hdOLD.stt, 100 ) as stt  "+
						 "from NHAPP_KHO_CHITIET kho left join "+
						 "	( "+
						 "		select solo, ngayhethan, 1 as stt  "+
						 "		from ERP_DONDATHANGNPP_SANPHAM_CHITIET_TEMP  "+
						 "		where dondathang_fk = '" + ddhId + "' and sanpham_fk = '" + spId + "' "+
						 "	) "+
						 "	hdOLD on kho.SOLO = hdOLD.solo and kho.NGAYHETHAN = hdOLD.ngayhethan "+
						 "where AVAILABLE > 0 and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and khachhang_fk = '" + this.khKGId + "' and isNPP = '0'  "+
						 "order by ISNULL( hdOLD.stt, 100 ) asc, kho.NGAYHETHAN asc ";
			}
			
			//System.out.println("---5.0.DE XUAT SO LO: " + query);
			ResultSet rs = db.get(query);
			double tongluongxuatCT = 0;  //PHAI BAT BUOC TONG LUONG XUAT O KHO CHI TIET PHAI BANG TONG LUONG XUAT O KHO TONG
			if(rs != null)
			{
				double totalLUONG = 0;
				boolean exit = false;
				while(rs.next() && !exit)
				{
					String ngayhethan = rs.getString("NGAYHETHAN");
					String solo = rs.getString("SOLO");
					
					totalLUONG += rs.getDouble("AVAILABLE");
					double soluongXUAT = 0;

					if(totalLUONG <= soluong)
					{
						soluongXUAT = rs.getDouble("AVAILABLE");
					}
					else
					{
						soluongXUAT = soluong - ( totalLUONG - rs.getDouble("AVAILABLE") );
						exit = true;
					}

					//System.out.println(":::: TONG LUONG XUAT: " + totalLUONG + " -- SO LUONG XUAT: " + soluongXUAT + " -- SO LUONG DX: " + soluong );
					if(soluongXUAT >= 1)
					{
						//CAP NHAT KHO CHI TIET
						if(this.khKGId.trim().length() <= 0 )
						{
							query = "UPDATE NHAPP_KHO_CHITIET  set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
									"where kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and SOLO = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
						}
						else
						{
							query = "UPDATE NHAPP_KHO_KYGUI_CHITIET  set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
									"where kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and khachhang_fk = '" + this.khKGId + "' and isNPP = '0' and SOLO = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
						}
						
						int kq = db.updateReturnInt(query);
						//	System.out.println("---UPDATE KHO CHI TIET TOI DAY: " + query + " -- KQ: " + kq);
						if(kq != 1 )
						{
							return "5.1.Lỗi khi chốt xuất kho: " + query;
						}

						//INSERT DONHANG - CHI TIET
						query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET_TEMP( dondathang_fk, SANPHAM_FK, dvdl_fk, solo, soluong, ngayhethan, loai, scheme, bosungTHEM )  " +
								"select '" + ddhId + "', pk_seq, ( select dvdl_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' and sanpham_fk = a.pk_seq ),  N'" + solo + "' as solo, '" + soluongDATHANG + "' as soluong, '" + ngayhethan + "' as NgayHetHan, '" + loai + "' as loai, N'" + scheme + "', 1 as bosungTHEM   " +
								"from SANPHAM a where PK_SEQ = '" + spId + "'  ";

						System.out.println("---INSERT ERP_DONDATHANGNPP_SANPHAM_CHITIET_TEMP HANG BAN: " + query);
						if(db.updateReturnInt(query)!=1 )
						{
							return "5.2.Lỗi khi chốt xuất kho: " + query;
						}

						tongluongxuatCT += soluongXUAT;
						if(exit)  //DA XUAT DU
						{
							break;
						}
					}
				}
				rs.close();
			}
			
			//System.out.println(tongluongxuatCT + "____________" + soluong);
			if(tongluongxuatCT != soluong)
			{
				return "5.3.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "5.4.Lỗi tự đề xuất: " + e.getMessage();
		}
		
		return msg;
	}

	
	public String[] getSpLoai() {

		return this.spLoai;
	}


	public void setSpLoai(String[] spLoai) {

		this.spLoai = spLoai;
	}

	
	public String[] getSpScheme() {
		
		return this.spSCheme;
	}

	
	public void setSpScheme(String[] spScheme) {
		
		this.spSCheme = spScheme;
	}

	public boolean chot(String vitribam) 
	{
		if( vitribam.equals("1") )
		{
			if( !this.update() )
			{
				return false;
			}
		}
		
		try
		{
			Utility util = new Utility();
			msg = util.Check_Huy_NghiepVu_KhoaSo("ERP_HOADONNPP", id, "NgayXuatHD", db);
			if(msg.length()>0)
				return false;
			
			String nppId = this.nppDangnhap;
			int checkKS_KINHDOANH = util.CheckKhoaSoKinhDoanh(db, nppId, "", "ERP_HOADONNPP", "ngayxuatHD", this.id);
			if(checkKS_KINHDOANH != -1 )
			{
				if(checkKS_KINHDOANH == 0)
				{
					this.msg = "Ngày hóa đơn nằm trong tháng đã khóa sổ kinh doanh. Vui lòng kiểm tra lại.";
					return false;
				}
			}
			
			String query = "";
						
			db.getConnection().setAutoCommit(false);
						
			query = "update ERP_HOADONNPP set trangthai = '2', NgaySua='"+getDateTime()+"', created_Date=getdate()  where pk_seq = '" + this.id + "' and trangthai in (0,1)  ";
			System.out.println(query);
			
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Hóa đơn đã chuyển trạng thái ,vui lòng xem lại ";
				db.getConnection().rollback();
				return false;
			}
			
			//LUU LAI THONG TIN
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//CAP NHAT TOOLTIP
			//db.execProceduce2("CapNhatTooltip_HoaDon", new String[] { this.id } );
		}
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	private int DemSoDong( String chuoi, int sokytutoida )
	{
		int sodong = 1;
		
		if( !chuoi.contains(" ") )
			return sodong;
		
		String arr[] = chuoi.split(" ");
        int i = 0;
        int dodai = 0;
        
        while(i < arr.length)
        {
           if(arr[i].length() >= sokytutoida) i++;
           else
           {
	             dodai += arr[i].length();
	             i++;
	             if(dodai > sokytutoida)
	             {      
	                 sodong++;
	                 dodai = 0;
	                 i--;
	             }
	             else if(dodai == sokytutoida)
	             {
	                 sodong++;
	                 dodai = 0;
	             }
	             else
	                 dodai++;
           }
        }
        
        System.out.println("::::: CHUOI: " + chuoi + " -- SO DONG: " + sodong );
        return sodong;
	}
	
	private String createPHIEUGIAOHANG(dbutils db, String hdId, String userId) 
	{
		String msg = "";
		String query = "";
		String pghId = "";
		
		try
		{
			query = "select a.DDH_FK, b.SOHOADON, b.npp_fk, b.Kho_FK from ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK = b.PK_SEQ " +
					"where a.HOADONNPP_FK = '" + hdId + "'";
			ResultSet rs = db.get(query);
			String ddhId = "";
			String sohoadon = "";
			String nppId = "";
			String khoId = "";
			if(rs.next())
			{
				ddhId = rs.getString("DDH_FK");
				sohoadon = rs.getString("SOHOADON");
				nppId = rs.getString("npp_fk");
				khoId = rs.getString("Kho_FK");
			}
			rs.close();
			
			//Tạo mặc định chốt luôn
			query = " insert ERP_YCXUATKHONPP(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, npp_dat_fk, khachhang_fk, nhomkenh_fk, ngaytao, nguoitao, ngaysua, nguoisua, hoadon_fk, sohoadon, ddh_fk, loaidonhang, khachhangKG_FK, NHANVIEN_FK) " +
							" select ( select NGAYXUATHD from ERP_HOADONNPP where pk_seq = '" + hdId + "' ), N'Phiếu giao hàng tự động từ duyệt hóa đơn " + hdId + "', '0', '" + nppId + "', " + khoId + ", " +
							" loaidonhang as xuatcho, npp_dat_fk, khachhang_fk, nhomkenh_fk, '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + hdId + "', '" + sohoadon + "', '" + ddhId + "', loaidonhang, khachhangKG_FK, NHANVIEN_FK " +
					" from ERP_DONDATHANGNPP where pk_seq = '" + ddhId + "' ";
			
			System.out.println("1.Insert YCXUATKHO: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			String ycxkId = "";
			ResultSet rsYCXK = db.get("select IDENT_CURRENT('ERP_YCXUATKHONPP') as ycxkId");
			if(rsYCXK.next())
			{
				ycxkId = rsYCXK.getString("ycxkId");
			}
			rsYCXK.close();
			
			pghId = ycxkId;
			
			query = "Insert ERP_YCXUATKHONPP_DDH(ycxk_fk, ddh_fk) " +
					"select '" + ycxkId + "', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + ddhId + " )  ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP_DDH " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME, DVDL_FK, soluongCHUAN ) " +
					"select '" + ycxkId + "', b.SANPHAM_FK, b.SOLUONG, 0, 0, b.SOLUONG, case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme,   " +
					"	( select pk_seq from DONVIDOLUONG where donvi = b.donvitinh ), b.soluong_chuan	" +
					"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.hoadon_fk   " +
					"where a.PK_SEQ = '" + hdId + "' " ;
			
			System.out.println("1.1.Insert HD - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM_THUCGIAO( ycxk_fk, sanpham_fk, soluong, LOAI, SCHEME, DVDL_FK, soluongCHUAN ) " +
					"select '" + ycxkId + "', b.SANPHAM_FK, b.SOLUONG, case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme,   " +
					"	( select pk_seq from DONVIDOLUONG where donvi = b.donvitinh ), b.soluong_chuan	" +
					"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.hoadon_fk   " +
					"where a.PK_SEQ = '" + hdId + "' " ;
			
			System.out.println("1.2.Insert HD - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, kho_fk, sanpham_fk, solo, ngayhethan, soluong, LOAI, SCHEME, tonkho, dongia ) " +
					"select '" + ycxkId + "', a.Kho_FK, c.PK_SEQ, b.SOLO, b.NGAYHETHAN, b.SOLUONG_CHUAN, " + 
					" 	case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme, '0' as tonkho, '0' as dongia " +
					"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk  " +
					"		inner join SANPHAM c on b.MA = c.MA  " +
					"where a.PK_SEQ = '" + hdId + "' ";
			System.out.println("1.3.Insert HD - SP CT: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET( ycxk_fk, kho_fk, sanpham_fk, solo, ngayhethan, soluong, LOAI, SCHEME, tonkho, dongia ) " +
					"select '" + ycxkId + "', a.Kho_FK, c.PK_SEQ, b.SOLO, b.NGAYHETHAN, b.SOLUONG_CHUAN, " + 
					" case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme, '0' as tonkho, '0' as dongia " +
					"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk  " +
					"		inner join SANPHAM c on b.MA = c.MA  " +
					"where a.PK_SEQ = '" + hdId + "' ";
			System.out.println("1.4.Insert HD - SP CT: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			Utility util = new Utility();
			msg = util.CapNhat_MaChungTu(db, ycxkId, "ERP_YCXUATKHONPP", "ngayyeucau");
			if( msg.trim().length() > 0 )
			{
				return msg;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "Lỗi khi tạo phiếu giao hàng: " + ex.getMessage();
		}
		
		return pghId;
	}
	
	private String ChotPHIEUGIAOHANG(dbutils db, String Id, String userId, String nppId ) 
	{
		Utility util = new Utility();
		String msg = "";
		try
		{
			//db.getConnection().setAutoCommit(false);
			
			String query = "";
			
// BUT TOAN KE TOAN
			
			Utility_Kho util_kho=new Utility_Kho();
			query = "SELECT YC.NGAYYEUCAU, YC_CT.SANPHAM_FK, SUM(YC_CT.SOLUONG) AS TONGXUAT,YC_CT.SOLO , " +

					"CASE WHEN CONVERT(INT, SUBSTRING(NGAYYEUCAU, 6, 2)) > 1 THEN " + // TRƯỜNG HỢP KHÔNG LÀ THÁNG 1, THÌ LẤY THÁNG - 1
					"	ISNULL( ( SELECT AVG(GIATON) FROM ERP_TONKHOTHANG " +
					"	          WHERE THANG = (CONVERT(INT, SUBSTRING(NGAYYEUCAU, 6, 2)) - 1) AND NAM = SUBSTRING(NGAYYEUCAU, 1, 4) " +
					"	          AND SANPHAM_FK = YC_CT.SANPHAM_FK AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')), 0 ) " +
					"ELSE " +
					"	ISNULL( ( SELECT AVG(GIATON) FROM ERP_TONKHOTHANG " +
					"			  WHERE THANG = 12 and NAM = (SUBSTRING(NGAYYEUCAU, 1, 4) - 1) " +
					"			  AND SANPHAM_FK = YC_CT.SANPHAM_FK AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')), 0 ) " +
					"END AS DONGIA, " + 
			
			 		"CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
			 		"'XK01' " +
			 		"ELSE " +
			 		"'XK02' END AS MAXUATKHO, " +
			 		
			 		"CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
					"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63211000' " +
					"	AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')) " +
					
					"ELSE " +  // ĐƠN HÀNG KHUYẾN MẠI
					"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63220000' " +
					"	AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')) " +

					"END AS TAIKHOANNO_FK,  " + 
					
					" (	SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN IN (" +
					"		SELECT TOP 1 LSP.TAIKHOANKT_FK  " +
			 		"		FROM SANPHAM SP " +
					"		INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
					"		WHERE SP.PK_SEQ = YC_CT.SANPHAM_FK " +
					"   ) AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "') " +
					" ) AS TAIKHOANCO_FK, " +
					
			 		" CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
			 		" '100002' " +
			 		" ELSE " +
			 		" '100008' END AS NOIDUNGXUAT_FK, " +
					
			 		" CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
			 		" N'Xuất bán hàng (theo đơn hàng bán) - Kho không ký gửi' " +
			 		" ELSE " +
			 		" N'Xuất khuyến mại - Kho không ký gửi' END AS KHOANMUC ," +
			 		" (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "') as congty_fk " +	
			 		" FROM ERP_YCXUATKHONPP YC " +
					" INNER JOIN ERP_YCXUATKHONPP_SANPHAM_CHITIET YC_CT ON YC.PK_SEQ = YC_CT.YCXK_FK " + 
					" INNER JOIN ERP_HOADONNPP HD ON HD.PK_SEQ = YC.HOADON_FK " +
					" WHERE YCXK_FK = '" + Id + "' " +
					" GROUP BY NGAYYEUCAU, YC_CT.SANPHAM_FK, YC_CT.KHO_FK, YC.NHOMKENH_FK, YC_CT.SCHEME,YC_CT.SOLO ";
			
			System.out.println("Định khoản: " + query);
			ResultSet rs = db.get(query);
			int dem=0;
			while(rs.next()){
				dem++;
				String ngaychungtu = rs.getString("NGAYYEUCAU");
				String nam = ngaychungtu.substring(0, 4);
				String thang = "" + (Integer.parseInt(ngaychungtu.substring(5, 7)));
				String ngayghinhan = ngaychungtu;
				String loaichungtu = rs.getString("MAXUATKHO");
				String sochungtu = Id;
				String taikhoanNO_fk =  rs.getString("TAIKHOANNO_FK");
				String taikhoanCO_fk =  rs.getString("TAIKHOANCO_FK");
				String NOIDUNGXUAT_FK = rs.getString("NOIDUNGXUAT_FK");
				
				String congty_fk= rs.getString("congty_fk");
				
				String  SANPHAM_FK= rs.getString("SANPHAM_FK");
				String solo=rs.getString("SOLO");
				double dongia_capnhat=0; 
				
				String str[] = util_kho.getGiaChayKT(ngaychungtu, db, congty_fk , nppId, SANPHAM_FK, solo);
				if(str[1].length() >0){

					msg = "Không thể cập nhật lỗi :  " + str[1];
					//db.getConnection().rollback();
					return msg;
				}else{
					dongia_capnhat=Double.parseDouble(str[0]);
				}
				 
				String NO = "" + (rs.getDouble("TONGXUAT")* dongia_capnhat);
				String CO = NO;
				String DOITUONG_NO="";
				if(NOIDUNGXUAT_FK.equals("100002")){
						DOITUONG_NO = "Giá vốn hàng xuất bán";
				}else{
					   DOITUONG_NO = "Giá vốn hàng xuất khuyến mãi";
				}
				String MADOITUONG_NO = "";
				String DOITUONG_CO = "Sản phẩm";
				String MADOITUONG_CO = rs.getString("SANPHAM_FK");
				String LOAIDOITUONG = "";
				String SOLUONG = "" + rs.getDouble("TONGXUAT");
				String DONGIA = "" + rs.getDouble("DONGIA");
				String TIENTEGOC_FK = "100000";
				String DONGIANT = "0";
				String TIGIA_FK = "1";
				String TONGGIATRI = NO;
				String TONGGIATRINT = TONGGIATRI;
				String khoanmuc = rs.getString("KHOANMUC");
				/*util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoanNO_fk, taikhoanCO_fk, 
								 	NOIDUNGXUAT_FK, NO, CO, DOITUONG_NO, MADOITUONG_NO, DOITUONG_CO, MADOITUONG_CO, LOAIDOITUONG, 
								 	SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, khoanmuc);*/
			}
			
			if(dem==0){
				msg = "Không cập nhật được khoản mục kế toán , vui lòng báo admin để được xử lý .";
				//db.getConnection().rollback();
				return msg;
			}
			
			//GOP CHUNG BUOC YC VA XUAT THANH 1
			query = "update ERP_YCXUATKHONPP set trangthai = '1'  where pk_seq = '" + Id + "' ";
			//System.out.println("---CAP NHAT TRANG THAI: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHONPP " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			//TU DONG HOAN TAT CAC DON DAT HANG TU CU TOI MOI
			query = "select ddh_fk, ( select xuatcho from ERP_YCXUATKHONPP where pk_seq = a.ycxk_fk ) as xuatcho " +
					"from ERP_YCXUATKHONPP_DDH a where ycxk_fk = '" + Id + "' order by ddh_fk asc";
			ResultSet rsDDH = db.get(query);
			String ddhID = "";
			String xuatCHO = "";
			if(rsDDH != null)
			{
				while(rsDDH.next())
				{
					ddhID += rsDDH.getString("ddh_fk") + ",";
					xuatCHO = rsDDH.getString("xuatcho");
					
					query = "  select COUNT(*) as soDONG,   " +
							" 		(   select count(distinct sanpham_fk) as soSP      " +
							"   			from     " +
							"   			(     " +
							"   					select a.sanpham_fk " +
							"   					from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ     " +
							"   					where a.dondathang_fk in (    " + ( ddhID.substring(0, ddhID.length() - 1) ) + "   )    " +
							"   			)     " +
							"   			dathang  )	 as soSP  " +
							"  from  " +
							"  (  " +
							"  	select sanpham_fk, sum(soluongXUAT) as soluongXUAT  " +
							"  	from ERP_YCXUATKHONPP_SANPHAM  " +
							" 	where ycxk_fk in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk in ( " + ( ddhID.substring(0, ddhID.length() - 1) ) + " ) ) " +
							"  	group by sanpham_fk  " +
							"  )   " +
							"  XUAT inner join   " +
							"  (  " +
							"   	select dathang.sanpham_fk, SUM(dathang.soluong) as soluongDAT      " +
							"   	from     " +
							"   	(     " +
							"   			select a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
							"   					case when a.dvdl_fk IS null then a.soluong      " +
							"   						 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
							"   						 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )      " +
							"   										 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme    " +
							"   			from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ     " +
							"   			where a.dondathang_fk in (    " + ( ddhID.substring(0, ddhID.length() - 1) ) + "   )    " +
							"   	)     " +
							"   	dathang   " +
							"   	group by dathang.sanpham_fk  " +
							"  )  " +
							"  DDH on XUAT.sanpham_fk = DDH.sanpham_fk  " +
							"  where XUAT.soluongXUAT >= DDH.soluongDAT ";
					
					System.out.println("CHECK HOAN TAT: " + query);
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK.next())
					{
						String trangthai = "";
						if(rsCHECK.getInt("soDONG") == rsCHECK.getInt("soSP"))
							trangthai = "4";  //HOAN TAT
						else
							trangthai = "2";  //KE TOAN DUYET
						
						query = " UPDATE ERP_DONDATHANGNPP set trangthai = '" + trangthai + "' " +
								" where pk_seq in ( " + ( rsDDH.getString("ddh_fk") ) + " ) ";
						if(!db.update(query))
						{
							msg = "Không thể chốt ERP_YCXUATKHO " + query;
							//db.getConnection().rollback();
							return msg;
						}
					}
				}
				rsDDH.close();
			}
			
			System.out.println("---XUAT CHO: " + xuatCHO);
			if(xuatCHO.equals("0"))  //XUẤT CHO ĐỐI TÁC THÌ TẠO NHẬP HÀNG CHO ĐỐI TÁC = LUONG THUC GIAO, LUONG CON DU CHUYEN VAO KHO KY GUI
			{
				query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, SOCHUNGTU, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, NHOMKENH_FK, YCXKNPP_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
						" select distinct NgayYeuCau, NgayYeuCau, NPP_DAT_FK,  " +
						" 			( select sohoadon from ERP_YCXUATKHONPP where PK_SEQ = a.pk_seq ), " +
						" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_DAT_FK ) ), " +
						"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_DAT_FK ), " +
						"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK )), " +
						"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK ) ) ), " +
						" 	   '100001' as DVKD_FK, a.NHOMKENH_FK, '" + Id + "', '1' as trangthai, '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "' " +
						" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
						" where a.PK_SEQ = '" + Id + "' ";
				
				System.out.println("---INSERT NHAN HANG: " + query );
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG " + query;
					//db.getConnection().rollback();
					return msg;
				}
				
				query = "select IDENT_CURRENT('NHAPHANG') as nhId";
				rs = db.get(query);
				String nhaphangId = "";
				rs.next();
				nhaphangId = rs.getString("nhId");
				rs.close();
				
				query = " insert NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
						" select IDENT_CURRENT('NHAPHANG'),  " +
						" 		b.sanpham_fk, b.soluong, NULL, b.dongia, 0 as chietkhau, c.DVDL_FK, b.LOAI, b.SCHEME, b.solo, b.ngayhethan " +
						" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET b on a.PK_SEQ = b.ycxk_fk " +
						" 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ   " +
						" where a.PK_SEQ = '" + Id + "' and b.soluong > 0 ";
				System.out.println("---INSERT NHAN HANG - SP: " + query );
				if(db.updateReturnInt(query) < 1 )
				{
					msg = "Không tạo mới NHAPHANG_SP " + query;
					//db.getConnection().rollback();
					return msg;
				}
				
				query = "insert NHAPHANG_DDH(nhaphang_fk, ddh_fk)  " +
						"select IDENT_CURRENT('NHAPHANG') as nhID, ddh_fk  " +
						"from ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + Id + "'";
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG_DDH " + query;
					//db.getConnection().rollback();
					return msg;
				}
				
				//TANG KHO CỦA ĐỐI TÁC LUÔN
				Nhaphang nhaphang = new Nhaphang();
				if( !nhaphang.MTVChot(db, nhaphangId) )
				{
					msg = "Lỗi khi chốt nhập hàng " + nhaphang.getMsg();
					//db.getConnection().rollback();
					return msg;
				}
			}
				
			//Lượng chênh lệch giữa lúc duyệt hóa đơn và lúc phiếu giao hàng sẽ được cho vào kho gửi của npp, khách hàng tại PHANAM
			query =  "select '100003' as kho_fk, a.sanpham_fk, c.nhomkenh_fk, c.NPP_DAT_FK, c.KHACHHANG_FK, a.solo, a.ngayhethan, 1 as DUNGCHUNGKENH, a.soluong - b.soluong as conLAI  " + 
					 "from ERP_YCXUATKHONPP_SANPHAM_CHITIET a inner join ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET b  " + 
					 "		on a.sanpham_fk = b.sanpham_fk and a.solo = b.solo and a.ngayhethan = b.ngayhethan and a.LOAI = b.LOAI and a.ycxk_fk = b.ycxk_fk " + 
					 "	 inner join ERP_YCXUATKHONPP c on a.ycxk_fk = c.PK_SEQ " +
					 "where a.ycxk_fk = '" + Id + "' and a.soluong - b.soluong > 0 ";
			System.out.println("CHECK SO LUONG GIAO CON LAI: " + query);
			ResultSet rsKYGUI = db.get(query);
			if(rsKYGUI != null)
			{
				while(rsKYGUI.next())
				{
					String khokyguiID = rsKYGUI.getString("kho_fk");
					String sanpham_fk = rsKYGUI.getString("sanpham_fk");
					String nhomkenh_fk = rsKYGUI.getString("nhomkenh_fk");
					if(rsKYGUI.getString("DUNGCHUNGKENH").equals("1"))
						nhomkenh_fk = "100000";
						
					String isNPP = "0";
					if(xuatCHO.equals("0")) //đơn hàng bán cho NPP
						isNPP = "1";
					
					String KHACHHANG_FK = rsKYGUI.getString("KHACHHANG_FK");
					if(isNPP.equals("1"))
						KHACHHANG_FK = rsKYGUI.getString("NPP_DAT_FK");
					
					String solo = rsKYGUI.getString("solo");
					String ngayhethan = rsKYGUI.getString("ngayhethan");
					double soluongCONLAI = rsKYGUI.getDouble("conLAI");
					
					query = " select count(*) as sodong from NHAPP_KHO_KYGUI " + 
							" where isNPP = '" + isNPP + "' and NPP_FK = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' ";
					
					boolean exitKHO = false;
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK != null)
					{
						if(rsCHECK.next())
						{
							if( rsCHECK.getInt("sodong") > 0 )
								exitKHO = true;
							rsCHECK.close();
						}
					}
					
					if(exitKHO)
					{
						query = " update NHAPP_KHO_KYGUI set soluong = soluong + '" + soluongCONLAI + "', available = available + '" + soluongCONLAI + "' " + 
								" where isNPP = '" + isNPP + "' and npp_fk = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' ";
					}
					else
					{
						query = "insert NHAPP_KHO_KYGUI( isNPP, KHO_FK, NPP_FK, KHACHHANG_FK, SANPHAM_FK, nhomkenh_fk, SOLUONG, BOOKED, AVAILABLE ) " + 
								" values( '" + isNPP + "', '" + khokyguiID + "', '" + nppId +  "', '" + KHACHHANG_FK + "', '" + sanpham_fk + "', '" + nhomkenh_fk + "', '" + soluongCONLAI + "', '0', '" + soluongCONLAI + "' )";
					}
					
					System.out.println(":::: CHEN VAO KHO KY GUI: " + query);
					if(db.updateReturnInt(query) <= 0 )
					{
						msg = "Lỗi khi chốt phiếu giao hàng " + query;
						//db.getConnection().rollback();
						return msg;
					}
					
					query = " select count(*) as sodong from NHAPP_KHO_KYGUI_CHITIET " + 
							" where isNPP = '" + isNPP + "' and NPP_FK = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
					
					exitKHO = false;
					rsCHECK = db.get(query);
					if(rsCHECK != null)
					{
						if(rsCHECK.next())
						{
							if( rsCHECK.getInt("sodong") > 0 )
								exitKHO = true;
							rsCHECK.close();
						}
					}
					
					if(exitKHO)
					{
						query = " update NHAPP_KHO_KYGUI_CHITIET set soluong = soluong + '" + soluongCONLAI + "', available = available + '" + soluongCONLAI + "' " + 
								" where isNPP = '" + isNPP + "' and NPP_FK = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
					}
					else
					{
						query = "insert NHAPP_KHO_KYGUI_CHITIET( isNPP, KHO_FK, NPP_FK, KHACHHANG_FK, SANPHAM_FK, nhomkenh_fk, solo, ngayhethan, SOLUONG, BOOKED, AVAILABLE ) " + 
								" values( '" + isNPP + "', '" + khokyguiID + "', '" + nppId + "', '" + KHACHHANG_FK + "', '" + sanpham_fk + "', '" + nhomkenh_fk + "', '" + solo + "', '" + ngayhethan + "', '" + soluongCONLAI + "', '0', '" + soluongCONLAI + "' )";
					}
					
									
					System.out.println(":::: CHEN VAO KHO KY GUI CHI TIET: " + query);
					if(db.updateReturnInt(query) <= 0 )
					{
						msg = "Lỗi khi chốt phiếu giao hàng " + query;
						//db.getConnection().rollback();
						return msg;
					}
				}
				rsKYGUI.close();
			}
			
			//db.getConnection().commit();
			//db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			//db.update("rollback");
			//db.shutDown();
			e.printStackTrace();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}
	
	
	
	public String getNgayghinhanCN() 
	{
		return this.ngayghinhanCN;
	}

	public void setNgayghinhanCN(String ngayghinhanCN) 
	{
		this.ngayghinhanCN = ngayghinhanCN;
	}

	public String getKyhieuhoadon() 
	{
		return this.kyhieuhoadon;
	}

	public void setKyhieuhoadon(String kyhieuhoadon) 
	{
		this.kyhieuhoadon = kyhieuhoadon;
		
	}

	public String getSohoadon() 
	{		
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) 
	{
		this.sohoadon = sohoadon;
	}

	public String[] getSpDongia() 
	{
		return this.spDongia;
	}

	public void setSpDongia(String[] spDongia) 
	{
		this.spDongia = spDongia;
		
	}

	public String[] getSpChietkhau()
	{
		return this.spChietkhau;
	}

	public void setSpChietkhau(String[] spChietkhau) 
	{
		this.spChietkhau = spChietkhau;
		
	}

   public String[] getDhck_diengiai() {
		
		return this.dhCk_diengiai;
	}

	
	public void setDhck_Diengiai(String[] obj) {
		
		this.dhCk_diengiai = obj;
	}

	
	public String[] getDhck_giatri() {
		
		return this.dhCk_giatri;
	}

	
	public void setDhck_giatri(String[] obj) {
		
		this.dhCk_giatri = obj;
	}

	
	public String[] getDhck_loai() {
		
		return this.dhCk_loai;
	}

	
	public void setDhck_loai(String[] obj) {
		
		this.dhCk_loai = obj;
	}

	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}
	
	public String getKhId() {
		return this.khId;
	}

	
	public void setKhId(String khId) {
		this.khId =khId;
		
	}

	
	public ResultSet getKhRs() {
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		this.khRs = khRs;
		
	}

	public String getLoaiXHD() {
		
		return this.loaiXHD;
	}

	public void setLoaiXHD(String loaiXHD) {
		this.loaiXHD= loaiXHD;
		
	}

	
	public String[] getSpVat() {
		
		return this.spVat;
	}

	
	public void setSpVat(String[] spVat) {
		 this.spVat = spVat;
		
	}

	
	public void loadContents()
	{
		this.getNppInfo();
		String query ="";
		
		if(this.doituong.equals("1"))
		{
			query = "select PK_SEQ, MAFAST + '-' + TEN as TEN from KHACHHANG where TRANGTHAI = '1' and npp_fk ='"+ this.nppDangnhap +"' ";
			//" and pk_seq in (select KHACHHANG_FK from ERP_DONDATHANGNPP where trangthai in (2,4) )";
			System.out.println(query);
			this.khRs = db.getScrol(query);
		
		}
		else if(this.doituong.equals("0"))
		{
			query = "select PK_SEQ, MAFAST + '-' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk ='"+ this.nppDangnhap +"' ";
			//" and pk_seq in (select NPP_DAT_FK from ERP_DONDATHANGNPP where trangthai in (2,4)) ";
			this.nppRs = db.get(query);
		}
		else if(this.doituong.equals("2"))
		{
			query = "select PK_SEQ, MA + '-' + TEN as TEN from ERP_NHANVIEN where TRANGTHAI = '1' and congty_fk = ( select congty_fk from NHAPHANPHOI where pk_seq = '" + this.nppDangnhap + "' ) ";
			//" and pk_seq in (select NHANVIEN_FK from ERP_DONDATHANGNPP where trangthai in (2,4) )";
			this.nhanvienRs = db.get(query);
		}		
		
		//Lấy thông tin xuất hóa đơn để chọn
		if( this.loaiXHD.equals("0") )
		{
			query = "select PK_SEQ, TENNGUOIMUA, DONVI, DIACHI, MASOTHUE from NPP_THONGTINHOADON " + 
					" where NPP_FK = '" + this.nppId + "' order by PK_SEQ asc";
		}
		else
		{
			query = "select PK_SEQ, TENNGUOIMUA, DONVI, DIACHI, MASOTHUE from KHACHHANG_THONGTINHOADON " + 
					" where KHACHHANG_FK = '" + this.khId + "' order by PK_SEQ asc";
		}
		this.ttXhdRs = db.get(query);
		
		//SAU KHI CHỌN LẠI, THÌ LẤY THÔNG TIN CHỌN			
		
		if( this.tthdId.trim().length() > 0 )
		{
			if( this.loaiXHD.equals("0") )
				query = "select PK_SEQ, TENNGUOIMUA, DONVI, DIACHI, MASOTHUE from NPP_THONGTINHOADON where pk_seq = '" + this.tthdId + "'";
			else
				query = "select PK_SEQ, TENNGUOIMUA, DONVI, DIACHI, MASOTHUE from KHACHHANG_THONGTINHOADON where pk_seq = '" + this.tthdId + "'";
			
			ResultSet rs =  this.db.get(query);
			if( rs != null )
			{
				try 
				{
					if( rs.next() )
					{
						this.nguoimuahang = rs.getString("TENNGUOIMUA");
						this.donvimua = rs.getString("DONVI");
						this.diachi = rs.getString("DIACHI");
						this.masothue = rs.getString("MASOTHUE");
					}
					rs.close();
				} 
				catch (Exception e) { e.printStackTrace(); }
			}
		}
		
		
		// LẤY PHIẾU CHUYỂN KHO
		query = 
			 " SELECT  a.PK_SEQ, isnull(Yeucauchuyenkho_fk,0) as PHIEUYEUCAU, a.lydo, a.NgayChuyen \n"+
			 " FROM ERP_CHUYENKHO a   \n"+
			 " LEFT JOIN KHO KHOTT on a.khonhan_fk = KHOTT.PK_SEQ  \n"+
			 " WHERE a.pk_seq > 0  and a.nhanhang_fk is null and a.npp_fk = "+this.nppDangnhap+" and a.lenhsanxuat_fk  is null "+
			 " AND a.TRANGTHAI IN (2,3) \n" +
			 " AND a.PK_SEQ NOT IN (SELECT CHUYENKHO_FK FROM ERP_HOADONNPP_CHUYENKHO A INNER JOIN ERP_HOADONNPP B ON A.HOADONNPP_FK = B.PK_SEQ \n" +
			 "						WHERE B.TRANGTHAI NOT IN (3,5) AND B.PK_SEQ != "+( this.id.trim().length() > 0 ? this.id : "-1" )+" ) \n"+
			 " ORDER BY a.NgayChuyen desc ";
			
			System.out.println(":::LAY DDH: " + query);		
			this.ddhRs = db.get(query);
			
			
		if(this.id.length() <= 0 ) 
		{
			// TỰ TẠO SỐ HÓA ĐƠN CỦA USER

			int kbDLN =0;
			String chuoiHD= "";
			long sohoadontu= 0;
			String sohoadonden= "";

			try
			{				
				{
					//KIEM TRA USER ĐÃ KHAI BAO SO HOA DON TRONG DLN CHUA
					query=  " select count(pk_seq) as dem" +
							" from NHANVIEN" +
							" where pk_seq= '"+ this.userId+"' and  isnull(sohoadontu,'') != '' and isnull(sohoadonden,'')  != ''" +
							"       and isnull(kyhieu, '') != ''  ";

					ResultSet KTDLN = db.get(query);
					if(KTDLN!= null)
					{
						while(KTDLN.next())
						{
							kbDLN = KTDLN.getInt("dem");
						}
						KTDLN.close();
					}

					if(kbDLN <= 0 )
					{
						this.msg = "Vui lòng khai báo Số hóa đơn trong menu Cập nhật nhân viên cho user này ";

					}
					else
					{
						// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
						query= "  select kyhieu as kyhieuhoadon, sohoadontu, sohoadonden " +
								" from NHANVIEN " +
								" where pk_seq= '"+ this.userId +"'";
						ResultSet rsLayDL =  db.get(query);
						if(rsLayDL != null)
						{
							while(rsLayDL.next())
							{
								this.kyhieuhoadon= rsLayDL.getString("kyhieuhoadon");
								sohoadontu = rsLayDL.getLong("sohoadontu");
								sohoadonden = rsLayDL.getString("sohoadonden");
							}
							rsLayDL.close();
						}


						// KIEM TRA SOHOADON DA DUOC DUNG CHUA
						// ETC
						query = " select count(pk_seq) as dem" +
								" from ERP_HOADONNPP" +
								" where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as int) >=  "+ sohoadontu +" " +
								"       and trangthai != '3' and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 1  ";
						System.out.println("Câu kiểm tra SHD "+query);
						ResultSet KiemTra = db.get(query);
						int check = 0;
						if(KiemTra != null)
						{
							while(KiemTra.next())
							{
								check = KiemTra.getInt("dem");
							}
							KiemTra.close();
						}

						//OTC
						query = " select count(pk_seq) as dem" +
								" from HOADON" +
								" where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
								"        and trangthai != '3'  and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 1 ";
						System.out.println("Câu kiểm tra SHD "+query);
						ResultSet KiemTra_OTC = db.get(query);
						int check_OTC = 0;
						if(KiemTra_OTC != null)
						{
							while(KiemTra_OTC.next())
							{
								check_OTC = KiemTra_OTC.getInt("dem");
							}
							KiemTra_OTC.close();
						}

						// LAY SOIN MAX	
						if(check <= 0 && check_OTC <= 0)
						{
							chuoiHD = ("000000"+ sohoadontu);
							chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
						}
						else
						{
							//ETC
							query = " select  MAX(cast((case when  sohoadon!='NA' then sohoadon else '0' end) as numeric)) as SOIN_MAX" +
									" from ERP_HOADONNPP " +
									" where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' " +
									" and cast((case when  sohoadon!='NA' then sohoadon else '0' end) as numeric(18,0)) >= "+ sohoadontu +" " +
									" and cast((case when  sohoadon!='NA' then sohoadon else '0' end) as numeric(18,0)) <= " + Integer.parseInt(sohoadonden) + " " +
									" and trangthai != '3'  and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 1 ";
							System.out.println("Câu lấy shd max "+query);
							ResultSet laySOIN_ETC = db.get(query);
							long soinMAX_ETC= 0;
							if(laySOIN_ETC!= null)
							{
								while(laySOIN_ETC.next())
								{
									soinMAX_ETC = laySOIN_ETC.getLong("SOIN_MAX");

								}laySOIN_ETC.close();
							}

							
							chuoiHD = ("000000"+ (soinMAX_ETC >0 ? (soinMAX_ETC +1) :"1"));
							chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
						}
						this.sohoadon =  chuoiHD;

						if(Integer.parseInt(this.sohoadon) > Integer.parseInt(sohoadonden))
						{ 
							this.msg = "Số hóa đơn đã vượt quá Số hóa đơn đến trong dữ liệu nền. Vui lòng khai báo ký hiệu hóa đơn mới ! ";
						}
					}

				}
			}
			catch(Exception e)
			{
				this.msg = "Lỗi xảy ra trong quá trình lấy Số hóa đơn";
				e.printStackTrace();
			}
		}
		
		String chuoi = this.ddhId;
		if(chuoi.length() > 0)
		{	
			// INIT TONG TIEN VAT
			try 
			{
				query = " SELECT CKSP.CHUYENKHO_FK CKID,  SP.PK_SEQ AS SPID, SP.MA AS MA, N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(SP.TENXUATHOADON,''), '__') WHERE part!= ' '  order by id desc), '') AS TEN, \n"+
				 		"		   DVDL.DIENGIAI AS donvi, isnull(CKSP.SOLUONGXUAT,0) SOLUONG, \n"+
				 		"		   0 chietkhau, 1 CHONIN, ' ' scheme, 0 vat, 0 dongia, 0 thanhtien  \n"+
						" FROM ERP_CHUYENKHO_SANPHAM CKSP \n"+
						" INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CKSP.CHUYENKHO_FK \n"+
						" INNER JOIN SANPHAM SP ON SP.PK_SEQ=CKSP.SANPHAM_FK \n"+
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK \n"+
						" WHERE CK.PK_SEQ IN ("+ chuoi +") ";

				System.out.println("INIT SP "+query);
				ResultSet rsLaySP = db.get(query);

				String spCKID = "";
				String spID = "";
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spSCHEME = "";
				String spVAT = "";
				String spTIENTHUE = "";
				String spTHANHTIEN = "";
				String spCHONIN = "";

				if(rsLaySP!= null)
				{				    	
					while(rsLaySP.next())
					{
						spCKID += rsLaySP.getString("CKID") + "__";
						spID += rsLaySP.getString("SPID") + "__";
						spMA += rsLaySP.getString("MA") + "__";
						spTEN += rsLaySP.getString("TEN") + "__";
						spDONVI += rsLaySP.getString("DONVI") + "__";
						spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
						spGIANHAP += (rsLaySP.getDouble("DONGIA")) + "__";
						spCHIETKHAU += (rsLaySP.getDouble("chietkhau")) + "__";
						spTHANHTIEN += (rsLaySP.getDouble("THANHTIEN")) + "__";
						spSCHEME += rsLaySP.getString("scheme") + "__";
						spVAT +=  (rsLaySP.getDouble("vat")) + "__";
						spTIENTHUE +=  Math.round( ( Math.round( rsLaySP.getDouble("SOLUONG") * rsLaySP.getDouble("DONGIA") ) - rsLaySP.getDouble("chietkhau") ) * rsLaySP.getDouble("vat") / 100 ) + "__";
						spCHONIN += (rsLaySP.getString("CHONIN")) + "__";

					}
					rsLaySP.close();

					if(spMA.trim().length() > 0)
					{
						spCKID = spCKID.substring(0, spCKID.length() - 2);
						this.ckId = spCKID.split("__");
						
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

						spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
						this.spDongia = spGIANHAP.split("__");

						spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
						this.spChietkhau = spCHIETKHAU.split("__");

						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");

						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spVat = spVAT.split("__");

						spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
						this.spTienthue = spTIENTHUE.split("__");

						spCHONIN = spCHONIN.substring(0, spCHONIN.length() - 2);
						this.spChonin = spCHONIN.split("__");
						
						spTHANHTIEN = spTHANHTIEN.substring(0, spTHANHTIEN.length() - 2);
						this.spThanhtien = spTHANHTIEN.split("__");
					}
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public String getTongtienBVAT() {
		
		return this.bvat;
	}

	
	public void setTongtienBVAT(String bvat) {
		
		this.bvat = bvat;
	}

	
	public String getTongCK() {
		
		return this.totalCHIETKHAU;
	}

	
	public void setTongCK(String tongCK) {
		
		this.totalCHIETKHAU = tongCK;
	}

	
	public String getTongVAT() {
		
		return this.thueVAT;
	}

	
	public void setTongVAT(String vat) {
		
		this.thueVAT = vat;
	}

	
	public String getTongtienAVAT() {
		
		return this.avat;
	}

	
	public void setTongtienAVAT(String avat) {
		
		this.avat = avat;
	}

	
	public String[] getSpTienthue() {

		return this.spTienthue;
	}


	public void setSpTienthua(String[] spTienthue) {
		
		this.spTienthue = spTienthue;
	}

	Hashtable<String, String> sanpham_soluong= new Hashtable<String, String>();
	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong, String chuyenkhoId)
	{
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String	query = "";
		
		query=  "SELECT * FROM ERP_CHUYENKHO_SP_NHANHANG WHERE SANPHAM_FK="+spMa+" AND  CHUYENKHO_FK = "+chuyenkhoId;
		
		ResultSet rscheck=db.get(query);
		try {
				if(rscheck.next()){
					query = " SELECT   CK_SP_XH_FK  ,ISNULL(CK_XH.KHU ,0) AS KHU,CK_XH.CHUYENKHO_FK,CK_XH.SOLO,CK_XH.SOLUONG AVAILABLE ,CK_XH.NGAYBATDAU , CK_XH.NGAYHETHAN  \n"+ 
							" FROM ERP_CHUYENKHO_SP_NHANHANG CK_XH  \n"+
						    " INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CK_XH.CHUYENKHO_FK \n"+
							" WHERE CK_XH.SANPHAM_FK="+spMa+" AND CK.PK_SEQ="+chuyenkhoId + " AND CK_SP_XH_FK IS NOT NULL ";	
				}			
				else
				{
					query = " SELECT CK_XH.PK_SEQ AS CK_SP_XH_FK,0 AS KHU, CK_XH.CHUYENKHO_FK,CK_XH.SOLO,CK_XH.SOLUONG AVAILABLE ,CK_XH.NGAYBATDAU ,CK_XH.NGAYHETHAN \n"+ 
							" FROM ERP_CHUYENKHO_SP_XUATHANG CK_XH  \n"+
						    " INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CK_XH.CHUYENKHO_FK \n"+
							" WHERE CK_XH.SANPHAM_FK="+spMa+" AND CK.PK_SEQ="+chuyenkhoId+ " AND CK_XH.PK_SEQ IS NOT NULL ";
				}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		System.out.println("----LAY SO LO: " + query );
		String query2 = "";
		ResultSet rs = db.get(query);
		try 
		{
			double total = 0;
			
			while(rs.next())
			{
				double slg = 0;
				double avai = Math.round(rs.getDouble("AVAILABLE") * 100.0 ) / 100.0;
				
				//System.out.println("===================== AVAI: " + avai);
				total += avai;
				
				if(total < Double.parseDouble(tongluong))
				{
					slg = avai;
				}
				else
				{
					slg =  Double.parseDouble(tongluong) - ( total - avai );
				}
					
				if(slg >= 0)
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '" + slg + "' as tuDEXUAT union ALL ";
				}
				else
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '' as tuDEXUAT union ALL ";
				}
				
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
			e.printStackTrace();
		}
		
		if(query2.trim().length() > 0)
		{
			query2 = query2.substring(0, query2.length() - 10);
			//System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
			return db.get(query2);
		}
		
		return null;
	}
	
	String kbhId;
	ResultSet kbhRs;

	public String getKbhId() {
		return kbhId;
	}

	public void setKbhId(String kbhId) {
		this.kbhId = kbhId;
	}

	public ResultSet getKbhRs() {
		return kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs) {
		this.kbhRs = kbhRs;
	}
	
	String dungchungKENH;

	public String getDungchungKENH() {
		return dungchungKENH;
	}

	public void setDungchungKENH(String dungchungKENH) {
		this.dungchungKENH = dungchungKENH;
	}

	
	public String[] getTenXuatHD() {
		
		return this.tenXuatHD;
	}

	
	public void setTenXuanHD(String[] value) {
		this.tenXuatHD = value;
		
	}

	
	public String getTenxuathd() {
		
		return this.tenxuathd;
	}

	
	public void setTenxuathd(String Tenxuathd) {
		this.tenxuathd = Tenxuathd;
		
	}

	
	public String getKhGhiNo() {
		
		return this.KhGhiNo;
	}

	
	public void setKhGhiNo(String KhGhiNo) {
		
		this.KhGhiNo = KhGhiNo;
	}

	
	public String getGhichu2() {
		
		return this.Ghichu2;
	}

	
	public void setGhichu2(String Ghichu2) {
		
		this.Ghichu2 = Ghichu2;
	}

	
	public String getcongtyId() {
		
		return this.congtyId;
	}

	
	public void setcongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}

	
	public ResultSet getCongnoRs() {
	
		return this.congnoRs;
	}

	
	public void setCongnoRs(ResultSet congnoRs) {
	
		this.congnoRs = congnoRs;
	}

	
	public String[] getSpChonin() {
		
		return this.spChonin;
	}

	
	public void setSpChonin(String[] spChonin) {
		
		this.spChonin = spChonin;
	}

	
	public String getnhanvienId() {
		
		return this.nhanvienId;
	}

	
	public void setnhanvienId(String nhanvienId) {
		
		this.nhanvienId = nhanvienId;
	}

	
	public ResultSet getNhanvienRs() {
		
		return nhanvienRs;
	}

	
	public void setNhanvienRs(ResultSet nhanvienRs) {
		
		this.nhanvienRs = nhanvienRs;
	}

	private String TaoHoaDonTaiChinhNPP_KM(dbutils db, String id, String userId) 
	{
		String msg1 = "";
		
		try
		{
			System.out.println("Taohdkm");
			String query = "";
		 	query = " update NHANVIEN SET Active = '1' where pk_seq='"+userId+"'";
		 	if(!db.update(query))
		 	{
			   msg1 = "Không thể cập nhật thông tin NHANVIEN " + query;
			   return msg1;
		 	}
		 	
		 	//TẠO 1 HÓA ĐƠN MỚI THÔNG TIN NHƯ HÓA ĐƠN CŨ TRỪ SỐ HÓA ĐƠN, NGÀY XUẤT HÓA ĐƠN ĐỂ TRỐNG - LOAIHOADON: 0 (TÀI CHÍNH); LOAIHOADON: 1 (KHUYẾN MÃI)
		 	
			query = " insert ERP_HOADONNPP (LOAIHOADON, isKM, DDKD_FK, KBH_FK, KHO_FK, nguoimua, ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt, " +
					" 		 chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaixuathd, npp_fk, khachhang_fk, mauhoadon, TENKHACHHANG, DIACHI, MASOTHUE, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU," +
					"		 npp_dat_fk, nhanvien_fk) \n"	+
					
					" SELECT 1 as LOAIHOADON, isKM, DDKD_FK, KBH_FK, KHO_FK, nguoimua, '' , 1, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, '' , hinhthuctt,  "+
					"		 0, 0, 0, 0, N'Hóa đơn được tách từ hóa đơn: ' + SOHOADON, loaixuatHD, npp_fk, khachhang_fk, mauhoadon, TENKHACHHANG, DIACHI, MASOTHUE, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU, "+
					"		 npp_dat_fk, nhanvien_fk "+
					" FROM 	 ERP_HOADONNPP " +
					" WHERE  PK_SEQ = "+id;
			System.out.println(query);
			
			if(!db.update(query))
			{
				msg = " Lỗi khi tách hóa đơn. Query lỗi:"+ query;
				db.getConnection().rollback();
				return msg1;
			}
		
			String hdId = "";
			query = "select scope_identity() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();
			
			//MẶC ĐỊNH CỘT CHONIN = 1 
			query =" insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme, vat, " +
				   "		TIENVAT, CHONIN ) \n" +
				   " select "+hdId+", sanpham_fk, sanphamTEN, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme, vat, TIENVAT, 1 "+
				   " FROM ERP_HOADONNPP_SP WHERE CHONIN = 0 AND  HOADON_FK = "+id;
			
			System.out.println(query);
			
			if(!db.update(query))
			{
				msg = " Lỗi khi tách hóa đơn khuyến mãi. Query lỗi:"+ query;
				db.getConnection().rollback();
				return msg1;
			}		
							
			query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk) select '"+ hdId +"', ddh_fk from ERP_HOADONNPP_DDH where hoadonnpp_fk in ( " + this.id + " )  ";
			System.out.println(query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_HOADONNPP_DDH. Query lỗi: " + query;
				db.getConnection().rollback();
				return msg1;
			}
			
			query = 
			" insert ERP_HOADONNPP_SP_CHITIET (hoadon_fk, donhang_fk, KBH_FK, KHO_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SOLUONG_CHUAN, DONGIA_CHUAN, SOLUONG_DATHANG, SCHEME, CHONIN) "+
			" select "+hdId+", donhang_fk, kbh_fk, kho_fk, ma, ten, donvi, dvchuan, dvdathang, soluong, solo, ngayhethan, chietkhau, thuevat, dongia, soluong_chuan, dongia_chuan, soluong_dathang, scheme, chonin "+
			" FROM ERP_HOADONNPP_SP_CHITIET WHERE CHONIN = 0 AND HOADON_FK = "+id;
			
			System.out.println(query);
			
			if(!db.update(query))
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_SP_CHITIET. Query lỗi: " + query;
				db.getConnection().rollback();
				return msg1;
			}
			
			//XÓA CÁC DÒNG KHÔNG IN TRONG BẢNG HÓA ĐƠN CŨ CÓ CỘT CHONIN = 0
			
			query = " DELETE ERP_HOADONNPP_SP WHERE CHONIN = 0 AND HOADON_FK = "+id;
			
			if(!db.update(query))
			{
				msg1 = "Không thể xóa ERP_HOADONNPP_SP. Query lỗi: " + query;
				db.getConnection().rollback();
				return msg1;
			}
			
			query = " DELETE ERP_HOADONNPP_SP_CHITIET WHERE CHONIN = 0 AND HOADON_FK = "+this.id;
			
			if(!db.update(query))
			{
				msg1 = "Không thể xóa ERP_HOADONNPP_SP_CHITIET. Query lỗi: " + query;
				db.getConnection().rollback();
				return msg1;
			}
			
			query = " UPDATE ERP_DONDATHANGNPP_SANPHAM_CHITIET SET CHONIN = 1 WHERE CHONIN = 0 AND HOADON_FK = "+this.id;
			
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANGNPP_SANPHAM_CHITIET. Query lỗi: " + query;
				db.getConnection().rollback();
				return msg1;
			}
			
			//CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[] { hdId } );
						
		}
		catch (Exception e) 
		{
			db.update("rollback");
			msg1 = "Exception: " + e.getMessage();
			e.printStackTrace();
			return msg1;
		}
		
		return msg1;
	}
		
	private String TaoHoaDonTaiChinhNPP_Tach(dbutils db, String id, String userId, String nppId, String congtyId) 
	{
		String msg1 = "";
		try
		{
		 	String query = "";
		 	query = " update NHANVIEN SET Active = '1' where pk_seq='"+userId+"'";
		 	if(!db.update(query))
		 	{
			   msg1 = "Không thể cập nhật thông tin NHANVIEN " + query;
			   return msg1;
		 	}
		 	
			String kyhieuhoadon="";
			String sohoadon="";
			String ngayhoadon = "";
			
			sohoadon="NA";
			kyhieuhoadon="NA";
			String mau = "1";
			
			String chuoi = "";
			long sohoadontu = 0;
			long sohoadonden = 0;
		
			query = " select c.khachhang_fk, c.loaidonhang, c.donhangMUON, c.ngaydonhang, c.npp_fk, c.pk_seq donhangID, c.khachhangKG_FK \n" +
					" from ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_DDH B ON A.PK_SEQ = B.HOADONNPP_FK  \n" +
					"	   INNER JOIN ERP_DONDATHANGNPP C ON B.DDH_FK = C.PK_SEQ \n" +
					" where A.PK_SEQ = "+id;
			
			String khachhangID = "";
			String donhangMUON = "0";
			String loaidonhang = "";
			String ngaydonhang = "";
			String donhangID = "";
			String khachhangkgId = "";
						
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					ngaydonhang = rs.getString("ngaydonhang");
					if(rs.getString("khachhang_fk") != null)
						khachhangID = rs.getString("khachhang_fk");
					
					nppId = rs.getString("npp_fk");
					donhangMUON = rs.getString("donhangMUON");
					loaidonhang = rs.getString("loaidonhang");
					donhangID = rs.getString("donhangID");
					khachhangkgId = rs.getString("khachhangKG_FK") == null ? "" : rs.getString("khachhangKG_FK");
				}
				rs.close();
			}
			
			
		if( loaidonhang.equals("1") || loaidonhang.equals("2") || loaidonhang.equals("0") || loaidonhang.equals("3"))
		{
			if( donhangMUON.equals("0") ) // ĐƠN HÀNG MƯỢN KHÔNG TỰ NHẢY SỐ HÓA ĐƠN
			{
				if( !loaidonhang.equals("0") )
				{
					query =" select isnull( mauhoadon, 1 ) as mauhoadon from KHACHHANG where PK_SEQ ='"+khachhangID+"'";
					System.out.println("AAAAA:"+ query);
					ResultSet mauHDrs = db.get(query);
					
					if(mauHDrs != null)
					{
						while(mauHDrs.next())
						{
							mau = mauHDrs.getString("mauhoadon");
						}
						mauHDrs.close();
					}
				}
				
				// CN HÀ NỘI && (CN HCM CÓ KH KHAI BÁO MẪU 2) DÙNG MẪU 2 (HO), CÒN LẠI DÙNG MẪU 1
				String query_kyhieu = " NV.KYHIEU ";				
				String query_sohdTU = " NV.SOHOADONTU ";	
				String query_sohdDEN = " NV.SOHOADONDEN ";	
				String query_mauhd = "1";
				String query_ngayhd = " NV.NGAYHOADON  ";
				
				/*if(nppId.equals("100002") || (nppId.equals("106210")&& mau.equals("2")) )
				{
					query_kyhieu = " NV.KYHIEU2 ";				
					query_sohdTU = " NV.SOHOADONTU2 ";	
					query_sohdDEN = " NV.SOHOADONDEN2 ";				
					query_mauhd = "2";
					query_ngayhd = " NV.NGAYHOADON2 ";
				}*/
				
				// LAY TT KHAI BAO SO HD TRONG DLN
				query= " SELECT ISNULL("+ query_ngayhd +", '') as NGAYHOADON, (CASE WHEN ISNULL("+ query_kyhieu +",'-1') = '' THEN '-1' ELSE ISNULL("+ query_kyhieu +",'-1') END)  as KYHIEU, \n"+
					   "        ISNULL("+ query_sohdTU +", -1) AS SOHOADONTU, ISNULL("+ query_sohdDEN +", -1) AS SOHOADONDEN,  \n"+
					   "        (select count(hd.pk_seq) as dem  "+
					   "         from HOADON hd               "+
					   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
					   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
					   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_OTC, \n" +
					   "        (select count(hd.pk_seq) as dem  "+
					   "         from ERP_HOADONNPP hd               "+
					   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
					   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
					   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_ETC \n" +
					   " FROM NHANVIEN NV  \n" +
					   " WHERE NV.pk_seq = '" + userId + "' \n";
				
				
				System.out.println("Câu check khai báo SHD "+query);
				ResultSet rsLayDL = db.get(query);
				
				int check_OTC = 0;
				int check_ETC = 0;
									
					while(rsLayDL.next())
					{
						kyhieuhoadon= rsLayDL.getString("kyhieu");
						sohoadontu = rsLayDL.getString("sohoadontu").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadontu") ;
						sohoadonden = rsLayDL.getString("sohoadonden").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadonden") ;;
						ngayhoadon = rsLayDL.getString("ngayhoadon");
						if(ngayhoadon.trim().length() <= 0)  ngayhoadon = ngaydonhang;
						check_OTC = rsLayDL.getInt("isSd_OTC");
						check_ETC = rsLayDL.getInt("isSd_ETC");
					}
					rsLayDL.close();
				
				if(kyhieuhoadon.equals("-1") || sohoadontu == -1 || sohoadonden == -1 )
				{
					msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
					return msg;
				}
							
				if(check_OTC <= 0 && check_ETC <= 0)
				{
					chuoi = ("000000" + sohoadontu);
					chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
				}
				else
				{
					// LAY SOIN MAX TRONG OTC && ETC
					query= " SELECT  \n"+
						   "       (select max(cast(sohoadon as float)) as soin_max  "+
						   "        from HOADON hd               "+
						   "        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ ) soinMAX_OTC, \n" +
						   "       (select max(cast(sohoadon as float)) as soin_max "+
						   "        from ERP_HOADONNPP hd               "+
						   "        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ) soinMAX_ETC  \n" +
						   " FROM NHANVIEN NV  \n" +
						   " WHERE NV.pk_seq = '" + userId + "' \n";
					
					System.out.println("Câu lấy SHD Max: "+query);
					long soinMAX_OTC = 0;
				    long soinMAX_ETC = 0;
				    
					ResultSet laySOIN = db.get(query);						     
					while(laySOIN.next())
					{
						soinMAX_OTC = laySOIN.getLong("soinMAX_OTC");
						soinMAX_ETC = laySOIN.getLong("soinMAX_ETC");
					}
					laySOIN.close();

					if( soinMAX_OTC > soinMAX_ETC ) 
					{
						chuoi = ("000000" + (soinMAX_OTC > 0 ? (soinMAX_OTC + 1) : "1" ));
					}
					else
					{
						chuoi = ("000000" + (soinMAX_ETC > 0 ? (soinMAX_ETC + 1) : "1"));
					}
					
					 chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length()).trim();
				}
				
				
				if(Integer.parseInt(chuoi) > sohoadonden )
				{ 
					//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
					query= " SELECT  \n"+
						   "      (select  max(cast(hd.sohoadon as float)) as soin_max   \n"+
						   "       from HOADON hd                                     \n"+
						   "       where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  \n"+
						   "             and cast(hd.sohoadon as float) >= cast(ISNULL("+ query_sohdTU +", -1) as float)                                 \n"+
						   "             and cast(hd.sohoadon as float) <= cast(ISNULL("+ query_sohdDEN +", -1) as float)  and hd.nguoisua = NV.PK_SEQ                               \n"+
						   "       having max(cast(hd.sohoadon as float)) != ( select  MAX(cast(SOHOADON as float)) as SOIN_MAX  from HOADON where trangthai!= 3 and  kyhieu = ISNULL("+ query_kyhieu +",'-1')  and nguoisua= NV.PK_SEQ) \n"+
						   "       ) soinMAX_OTC 										  \n"+								  
						   " FROM NHANVIEN NV   \n" +
						   " WHERE NV.pk_seq = '" + userId + "' \n";
					
					System.out.println("Câu lấy HD không dùng "+query);
					ResultSet SoMAX_HD = db.get(query);
					String soinmax = "";
					while(SoMAX_HD.next())
					{
						soinmax = SoMAX_HD.getString("soinMAX_OTC")== null ? "" : SoMAX_HD.getString("soinMAX_OTC") ;
						chuoi = ("000000" + (SoMAX_HD.getLong("soinMAX_OTC")));
							
					} SoMAX_HD.close();
					
					chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
			  
					if(soinmax.trim().length() <= 0 )
					{
						msg = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến (" + sohoadonden + ")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
						return msg;
					}
				}
				
				 sohoadon = chuoi ;
				
			    // KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
				query = " select (select count(*) from HOADON where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraOTC, " +
						"        (select count(*) from ERP_HOADONNPP where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraETC " +
						" from NHANVIEN where pk_seq = '" + userId + "' ";
				
				System.out.println("Câu kiểm tra lại SHD: "+query);
				ResultSet RsRs = db.get(query);
				int KT_OTC = 0;
				int KT_ETC = 0;
					while(RsRs.next())
					{
						KT_OTC = RsRs.getInt("KtraOTC") ;
						KT_ETC = RsRs.getInt("KtraETC") ;
					}
				
				if(KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI TẠO
				{
					msg = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
					return msg;
				}
			}
		}
		
		if(loaidonhang.equals("3")) mau = "";
			
			//TẠO MỚI HÓA ĐƠN
			
			query = " insert ERP_HOADONNPP (LOAIHOADON, isKM, DDKD_FK, KBH_FK, KHO_FK, NGUOIMUA, NGAYXUATHD, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, KYHIEU, SOHOADON, HINHTHUCTT, " +
					"		CHIETKHAU, TONGTIENAVAT, VAT, TONGTIENBVAT, GHICHU, LOAIXUATHD, NPP_FK, KHACHHANG_FK, NPP_DAT_FK, nhanvien_fk, MAUHOADON, " +
					"		MASOTHUE, TENKHACHHANG, DIACHI, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN) "+
					" select LoaiHoaDon, isKM, DDKD_FK, KBH_FK, Kho_FK, NGUOIMUA, NGAYXUATHD, 1 as TRANGTHAI, '"+getDateTime()+"' NGAYTAO, "+userId+" NGUOITAO, '"+getDateTime()+"' NGAYSUA, "+userId+" nguoisua, '"+kyhieuhoadon+"' kyhieu,'"+sohoadon+"' sohoadon, hinhthuctt , \n"+
			   		" 		 0 as chietkhau, 0 as tongtienavat, 0 as vat, 0 as tongtienbvat,N'Hóa đơn được tách từ hóa đơn có số chứng từ: "+this.id+"' GHICHU,  LOAIXUATHD, NPP_FK, KHACHHANG_FK, NPP_DAT_FK, nhanvien_fk,"+mau+" MAUHOADON, \n"+
			   		" 		 MASOTHUE, TENKHACHHANG, DIACHI, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN \n"+
			   		" from ERP_HOADONNPP " +
			   		" where PK_SEQ = "+id;		
								 
			System.out.println("1.Insert ERP_HOADONNPP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP " + query;
				return msg1;
			}		
			
			String hdId = "";
			query = "select scope_identity() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();
			
			query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk)  values( "+ hdId +",  " + donhangID + "  )";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
				return msg1;
			}
			
			query = " insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SoLuong_DatHang, SCHEME, CHONIN)    " +
					" select '"+hdId+"' as hoadon_fk, '"+donhangID+"' as donhang_fk, kbh_fk, kho_fk, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SOLUONG_CHUAN, DONGIA_CHUAN, SOLUONG_DATHANG, SCHEME, 1 as CHONIN "+
					" from ERP_HOADONNPP_SP_CHITIET " +
					" where CHONIN = 0 AND HOADON_FK = "+id;
			
			System.out.println("1.0.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_SP_CHITIET " + query;
				return msg1;
			}
			
			query =  " insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluong_chuan, dongia, thanhtien, chietkhau, scheme , vat, CHONIN)  " + 
					 " select "+hdId+" hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluong_chuan, dongia, thanhtien, chietkhau, scheme, vat, 1 as CHONIN " +
					 " from ERP_HOADONNPP_SP " +
					 " where CHONIN = 0 AND HOADON_FK = "+id;
			
			System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
				return msg1;
			}
						
			//CHECK BANG TONG PHAI BANG BANG CHI TIET
			query = "select count(*) as sodong  " +
					"from " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"dh left join " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk " +
					"where dh.soluong != isnull(xk.soluong, 0) ";
			
			System.out.println("---CHECK HOA DON: " + query);
			int soDONG = 0;
			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("sodong");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				msg = "3.Số lượng trong đơn hàng tách không khớp với hóa đơn tách. Vui lòng liên hệ Admin để xử lý ";
				return msg;
			}
			
			//TỰ ĐỘNG CẬP NHẬT LẠI BẢNG ERP_DONDATHANGNPP_SANPHAM_CHITIET
			/*query = " UPDATE ERP_DONDATHANGNPP_SANPHAM_CHITIET SET HOADON_FK = "+hdId+", CHONIN = 1 " +
					" WHERE CHONIN = 0 AND DONDAThang_fk = "+donhangID;
			
			System.out.println(query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the cập nhật ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query;
				return msg1;
			}*/
			
			//XÓA CÁC DÒNG SẢN PHẨM TÁCH KHỎI HÓA ĐƠN CŨ
			query =  "delete ERP_HOADONNPP_SP WHERE CHONIN = 0 AND HOADON_FK = "+id;
			System.out.println(query);
			if(!db.update(query))
			{
				msg1 = "Khong the cập nhật ERP_HOADONNPP_SP: " + query;
				return msg1;
			} 
	
			//XÓA CÁC DÒNG SẢN PHẨM TÁCH KHỎI HÓA ĐƠN CŨ
			query =  "delete ERP_HOADONNPP_SP_CHITIET WHERE CHONIN = 0 AND HOADON_FK = "+id;
			System.out.println(query);
			if(!db.update(query) )
			{
				msg1 = "Khong the cập nhật ERP_HOADONNPP_SP_CHITIET: " + query;
				return msg1;
			} 
						
			
			//CẬP NHẬT LẠI TIỀN - CHO HÓA ĐƠN CŨ	
			geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
			
			util.CapNhat_ThanhTien_HoaDon(db, id);
			
			util.CapNhat_ThanhTien_HoaDon(db, hdId);
			
			//CAP NHAT TOOLTIP CHO 2 HOA DON

			db.execProceduce2("CapNhatTooltip_HoaDon", new String[] { id } );
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[] { hdId } );
					
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			msg1 = "Exception: " + e.getMessage();
			e.printStackTrace();
			return msg1;
		}
		
		return msg1;
	}


	private String TaoHoaDonTaiChinhNPP_NamCho(dbutils db, String id, String userId, String nppId, String congtyId) 
	{
		String msg1 = "";
		try
		{
			String query = "";

			String kyhieuhoadon = "";
			String sohoadon = "";
			String ngayhoadon = "";

			sohoadon = "NA";
			kyhieuhoadon = "NA";
			String mau = "1";

			String chuoi = "";
			long sohoadontu = 0;
			long sohoadonden = 0;

			query = " select c.khachhang_fk, c.loaidonhang, c.donhangMUON, c.ngaydonhang, c.npp_fk, c.pk_seq donhangID, c.khachhangKG_FK \n" +
					" from ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_DDH B ON A.PK_SEQ = B.HOADONNPP_FK  \n" +
					"	   INNER JOIN ERP_DONDATHANGNPP C ON B.DDH_FK = C.PK_SEQ \n" +
					" where A.PK_SEQ = "+id;

			String khachhangID = "";
			String donhangMUON = "0";
			String loaidonhang = "";
			String ngaydonhang = "";
			String donhangID = "";
			String khachhangkgId = "";

			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					ngaydonhang = rs.getString("ngaydonhang");
					if(rs.getString("khachhang_fk") != null)
						khachhangID = rs.getString("khachhang_fk");

					nppId = rs.getString("npp_fk");
					donhangMUON = rs.getString("donhangMUON");
					loaidonhang = rs.getString("loaidonhang");
					donhangID = rs.getString("donhangID");
					khachhangkgId = rs.getString("khachhangKG_FK") == null ? "" : rs.getString("khachhangKG_FK");
				}
				rs.close();
			}

			if( loaidonhang.equals("1") || loaidonhang.equals("2") || loaidonhang.equals("0") || loaidonhang.equals("3"))
			{
				if( donhangMUON.equals("0") ) // ĐƠN HÀNG MƯỢN KHÔNG TỰ NHẢY SỐ HÓA ĐƠN
				{
					query =" select isnull( mauhoadon, 1 ) as mauhoadon from KHACHHANG where PK_SEQ ='"+khachhangID+"'";
					System.out.println("AAAAA:"+ query);
					ResultSet mauHDrs = db.get(query);

					if(mauHDrs != null)
					{
						while(mauHDrs.next())
						{
							mau = mauHDrs.getString("mauhoadon");
						}
						mauHDrs.close();
					}

					// CN HÀ NỘI && (CN HCM CÓ KH KHAI BÁO MẪU 2) DÙNG MẪU 2 (HO), CÒN LẠI DÙNG MẪU 1
					String query_kyhieu = " NV.KYHIEU ";				
					String query_sohdTU = " NV.SOHOADONTU ";	
					String query_sohdDEN = " NV.SOHOADONDEN ";	
					String query_mauhd = "1";
					String query_ngayhd = " NV.NGAYHOADON  ";

					// LAY TT KHAI BAO SO HD TRONG DLN
					query= " SELECT ISNULL("+ query_ngayhd +", '') as NGAYHOADON, (CASE WHEN ISNULL("+ query_kyhieu +",'-1') = '' THEN '-1' ELSE ISNULL("+ query_kyhieu +",'-1') END)  as KYHIEU, \n"+
							"        ISNULL("+ query_sohdTU +", -1) AS SOHOADONTU, ISNULL("+ query_sohdDEN +", -1) AS SOHOADONDEN,  \n"+
							"        (select count(hd.pk_seq) as dem  "+
							"         from HOADON hd               "+
							"         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							"               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							"               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_OTC, \n" +
							"        (select count(hd.pk_seq) as dem  "+
							"         from ERP_HOADONNPP hd               "+
							"         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							"               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							"               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_ETC \n" +
							" FROM NHANVIEN NV  \n" +
							" WHERE NV.pk_seq = '" + userId + "' \n";

					System.out.println("Câu check khai báo SHD "+query);
					ResultSet rsLayDL = db.get(query);

					int check_OTC = 0;
					int check_ETC = 0;

					while(rsLayDL.next())
					{
						kyhieuhoadon= rsLayDL.getString("kyhieu");
						sohoadontu = rsLayDL.getString("sohoadontu").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadontu") ;
						sohoadonden = rsLayDL.getString("sohoadonden").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadonden") ;;
						ngayhoadon = rsLayDL.getString("ngayhoadon");
						if(ngayhoadon.trim().length() <= 0)  ngayhoadon = ngaydonhang;
						check_OTC = rsLayDL.getInt("isSd_OTC");
						check_ETC = rsLayDL.getInt("isSd_ETC");
					}
					rsLayDL.close();

					if(kyhieuhoadon.equals("-1") || sohoadontu == -1 || sohoadonden == -1 )
					{
						msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
						return msg;
					}

					if(check_OTC <= 0 && check_ETC <= 0)
					{
						chuoi = ("000000" + sohoadontu);
						chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
					}
					else
					{
						// LAY SOIN MAX TRONG OTC && ETC
						query= " SELECT  \n"+
								"       (select max(cast(sohoadon as float)) as soin_max  "+
								"        from HOADON hd               "+
								"        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
								"              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
								"              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ ) soinMAX_OTC, \n" +
								"       (select max(cast(sohoadon as float)) as soin_max "+
								"        from ERP_HOADONNPP hd               "+
								"        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
								"              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
								"              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ) soinMAX_ETC  \n" +
								" FROM NHANVIEN NV  \n" +
								" WHERE NV.pk_seq = '" + userId + "' \n";

						System.out.println("Câu lấy SHD Max: "+query);
						long soinMAX_OTC = 0;
						long soinMAX_ETC = 0;

						ResultSet laySOIN = db.get(query);						     
						while(laySOIN.next())
						{
							soinMAX_OTC = laySOIN.getLong("soinMAX_OTC");
							soinMAX_ETC = laySOIN.getLong("soinMAX_ETC");
						}
						laySOIN.close();

						if( soinMAX_OTC > soinMAX_ETC ) 
						{
							chuoi = ("000000" + (soinMAX_OTC > 0 ? (soinMAX_OTC + 1) : "1" ));
						}
						else
						{
							chuoi = ("000000" + (soinMAX_ETC > 0 ? (soinMAX_ETC + 1) : "1"));
						}

						chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length()).trim();
					}

					if(Integer.parseInt(chuoi) > sohoadonden )
					{ 
						//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
						query= " SELECT  \n"+
								"      (select  max(cast(hd.sohoadon as float)) as soin_max   \n"+
								"       from HOADON hd                                     \n"+
								"       where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  \n"+
								"             and cast(hd.sohoadon as float) >= cast(ISNULL("+ query_sohdTU +", -1) as float)                                 \n"+
								"             and cast(hd.sohoadon as float) <= cast(ISNULL("+ query_sohdDEN +", -1) as float)  and hd.nguoisua = NV.PK_SEQ                               \n"+
								"       having max(cast(hd.sohoadon as float)) != ( select  MAX(cast(SOHOADON as float)) as SOIN_MAX  from HOADON where trangthai!= 3 and  kyhieu = ISNULL("+ query_kyhieu +",'-1')  and nguoisua= NV.PK_SEQ) \n"+
								"       ) soinMAX_OTC 										  \n"+								  
								" FROM NHANVIEN NV   \n" +
								" WHERE NV.pk_seq = '" + userId + "' \n";

						System.out.println("Câu lấy HD không dùng "+query);
						ResultSet SoMAX_HD = db.get(query);
						String soinmax = "";
						while(SoMAX_HD.next())
						{
							soinmax = SoMAX_HD.getString("soinMAX_OTC")== null ? "" : SoMAX_HD.getString("soinMAX_OTC") ;
							chuoi = ("000000" + (SoMAX_HD.getLong("soinMAX_OTC")));

						} SoMAX_HD.close();

						chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());

						if(soinmax.trim().length() <= 0 )
						{
							msg1 = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến (" + sohoadonden + ")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
							return msg1;
						}
					}

					sohoadon = chuoi ;

					// KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
					query = " select (select count(*) from HOADON where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraOTC, " +
							"        (select count(*) from ERP_HOADONNPP where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraETC " +
							" from NHANVIEN where pk_seq = '" + userId + "' ";

					System.out.println("Câu kiểm tra lại SHD: "+query);
					ResultSet RsRs = db.get(query);
					int KT_OTC = 0;
					int KT_ETC = 0;
					while(RsRs.next())
					{
						KT_OTC = RsRs.getInt("KtraOTC") ;
						KT_ETC = RsRs.getInt("KtraETC") ;
					}

					if(KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI TẠO
					{
						msg1 = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
						return msg1;
					}
				}
			}

			if(loaidonhang.equals("3")) mau = "";

			//TẠO MỚI HÓA ĐƠN
			query = " insert ERP_HOADONNPP (LOAIHOADON, isKM, DDKD_FK, KBH_FK, KHO_FK, NGUOIMUA, NGAYXUATHD, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, KYHIEU, SOHOADON, HINHTHUCTT, " +
					"		CHIETKHAU, TONGTIENAVAT, VAT, TONGTIENBVAT, GHICHU, LOAIXUATHD, NPP_FK, KHACHHANG_FK, NPP_DAT_FK, nhanvien_fk, MAUHOADON, " +
					"		MASOTHUE, TENKHACHHANG, DIACHI, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN) "+
					" select LoaiHoaDon, isKM, DDKD_FK, KBH_FK, Kho_FK, NGUOIMUA, NGAYXUATHD, 1 as TRANGTHAI, '"+getDateTime()+"' NGAYTAO, "+userId+" NGUOITAO, '"+getDateTime()+"' NGAYSUA, "+userId+" nguoisua, '"+kyhieuhoadon+"' kyhieu,'"+sohoadon+"' sohoadon, hinhthuctt , \n"+
					" 		 0 as chietkhau, 0 as tongtienavat, 0 as vat, 0 as tongtienbvat,N'Hóa đơn được tách từ hóa đơn có số chứng từ: "+this.id+"' GHICHU,  LOAIXUATHD, NPP_FK, KHACHHANG_FK, NPP_DAT_FK, nhanvien_fk,"+mau+" MAUHOADON, \n"+
					" 		 MASOTHUE, TENKHACHHANG, DIACHI, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN \n"+
					" from ERP_HOADONNPP " +
					" where PK_SEQ = "+id;		

			System.out.println("1.Insert ERP_HOADONNPP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP " + query;
				return msg1;
			}		

			String hdId = "";
			query = "select scope_identity() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();

			query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk)  values( " + hdId + ",  " + donhangID + "  )";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
				return msg1;
			}

			query = "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, DonVi_Chuan, soluong, soluong_chuan, scheme, CHONIN, DONGIA, VAT)  "+
					 "select '" + hdId + "' as hoadon_fk, ddh_sp.sanpham_fk, ddh_sp.TEN, ddh_sp.DONVI, ddh_sp.dvCHUAN, "+
					 "		 ddh_sp.soluong - isnull(daxuat.soluong, 0) as soluong, ddh_sp.soluongCHUAN - isnull(daxuat.soluongCHUAN, 0) as soluongCHUAN,  "+
					 "		 ddh_sp.scheme, 1 as chonin, "+
					 "		 case when dbo.Trim( ddh_sp.scheme ) != '' then 0 else " +
					 "		 ( select dongia from ERP_DONDATHANGNPP_SANPHAM a where a.dondathang_fk = ddh.PK_SEQ and a.sanpham_fk = ddh_sp.SANPHAM_FK ) end as dongia,  " +
					 "		 ( select thuexuat from SANPHAM where PK_SEQ = ddh_sp.SANPHAM_FK ) as VAT	" +
					 "from ERP_DONDATHANGNPP ddh inner join "+
					 "( "+
					 "	select dondathang_fk, SANPHAM_FK, b.MA, b.TEN, c.DONVI, d.DONVI as dvCHUAN, a.DVDL_FK as dvDATHANG, a.scheme, "+
					 "		SUM(soluong) as soluong, SUM( soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluongCHUAN  "+
					 "	from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ "+
					 "			inner join DONVIDOLUONG c on a.DVDL_FK = c.PK_SEQ "+
					 "			inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ "+
					 "	where dondathang_fk = '" + donhangID + "'  "+
					 "	group by dondathang_fk, SANPHAM_FK, b.MA, b.TEN, c.DONVI, d.DONVI, a.DVDL_FK, a.scheme "+
					 ") "+
					 "ddh_sp on ddh.PK_SEQ = ddh_sp.dondathang_fk left join "+
					 "( "+
					 "	select c.PK_SEQ as sanpham_fk, b.scheme, sum(b.SOLUONG) as soluong, sum(b.SoLuong_Chuan) as soluongCHUAN "+
					 "	from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk "+
					 "			inner join SANPHAM c on b.MA = c.MA "+
					 "	where a.TRANGTHAI not in ( 3, 5 ) and a.PK_SEQ in ( select hoadonnpp_fk from ERP_HOADONNPP_DDH where DDH_FK = '" + donhangID + "' ) "+
					 "	group by c.PK_SEQ, b.scheme "+
					 ") "+
					 "daxuat on daxuat.SANPHAM_FK = ddh_sp.SANPHAM_FK and ddh_sp.scheme = daxuat.scheme  "+
					 "where ddh_sp.soluong - isnull(daxuat.soluong, 0) > 0 ";

			System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
				return msg1;
			}
			
			query = "insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SCHEME, CHONIN)  "+
					 "select '" + hdId + "' as hoadon_fk, '" + donhangID + "' as donhang_fk, ddh.kbh_fk, ddh.kho_fk, ddh_sp.MA, ddh_sp.TEN, ddh_sp.DONVI, ddh_sp.dvCHUAN, ddh_sp.dvDATHANG, "+
					 "		 ddh_sp.soluong - isnull(daxuat.soluong, 0) as soluong, ddh_sp.solo, ddh_sp.ngayhethan, 0 as chietkhau, daxuat.THUEVAT as tienVAT, daxuat.DONGIA as dongia, "+
					 "		 ddh_sp.soluongCHUAN - isnull(daxuat.soluongCHUAN, 0) as soluongCHUAN, 0 as DonGia_Chuan, ddh_sp.scheme, 1 as scheme "+
					 "from ERP_DONDATHANGNPP ddh inner join "+
					 "( "+
					 "	select dondathang_fk, SANPHAM_FK, b.MA, b.TEN, c.DONVI, b.DVDL_FK as dvCHUAN, a.DVDL_FK as dvDATHANG, a.LOAI, a.scheme, "+
					 "		a.solo, a.ngayhethan, "+
					 "		SUM(soluong) as soluong, SUM( soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluongCHUAN  "+
					 "	from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ "+
					 "			inner join DONVIDOLUONG c on a.DVDL_FK = c.PK_SEQ "+
					 "	where dondathang_fk = '" + donhangID + "'  "+
					 "	group by dondathang_fk, SANPHAM_FK, b.MA, b.TEN, c.DONVI, b.DVDL_FK, a.DVDL_FK, a.LOAI, a.scheme, a.solo, a.ngayhethan "+
					 ") "+
					 "ddh_sp on ddh.PK_SEQ = ddh_sp.dondathang_fk left join "+
					 "( "+
					 "	select c.PK_SEQ as sanpham_fk, b.solo, b.ngayhethan, "+
					 "		case when dbo.trim(b.scheme) != '' then 1 else 0 end as loai, b.scheme, sum(b.SOLUONG) as soluong, sum(b.SoLuong_Chuan) as soluongCHUAN, b.DONGIA, b.THUEVAT "+
					 "	from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk "+
					 "			inner join SANPHAM c on b.MA = c.MA "+
					 "	where a.TRANGTHAI not in ( 3, 5 ) and a.PK_SEQ in ( select hoadonnpp_fk from ERP_HOADONNPP_DDH where DDH_FK = '" + donhangID + "' ) "+
					 "	group by c.PK_SEQ, b.solo, b.ngayhethan, b.scheme, b.DONGIA, b.THUEVAT	" +
					 ") "+
					 "daxuat on daxuat.SANPHAM_FK = ddh_sp.SANPHAM_FK and ddh_sp.loai = daxuat.LOAI and ddh_sp.scheme = daxuat.scheme  "+
					 "		and daxuat.SOLO = ddh_sp.solo and daxuat.NGAYHETHAN = ddh_sp.ngayhethan "+
					 "where ddh_sp.soluong - isnull(daxuat.soluong, 0) > 0 ";

			System.out.println("1.0.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_SP_CHITIET " + query;
				return msg1;
			}

			//CHECK BANG TONG PHAI BANG BANG CHI TIET
			query = "select count(*) as sodong  " +
					"from " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"dh left join " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk " +
					"where dh.soluong != isnull(xk.soluong, 0) ";

			System.out.println("---CHECK HOA DON: " + query);
			int soDONG = 0;
			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("sodong");
				}
				rsCHECK.close();
			}

			if(soDONG > 0)
			{
				msg = "3.Số lượng trong đơn hàng tách không khớp với hóa đơn tách. Vui lòng liên hệ Admin để xử lý ";
				return msg;
			}

			//TỰ ĐỘNG CẬP NHẬT LẠI BẢNG ERP_DONDATHANGNPP_SANPHAM_CHITIET
			/*query = " UPDATE ERP_DONDATHANGNPP_SANPHAM_CHITIET SET HOADON_FK = " + hdId + ", CHONIN = 1 " +
					" WHERE CHONIN = 0 AND DONDAThang_fk = "+donhangID;

			System.out.println(query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the cập nhật ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query;
				return msg1;
			}*/

			//Cập nhật lại giá cho hóa đơn
			geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
			msg1 = util.CapNhat_ThanhTien_HoaDon(db, hdId);
			if( msg1.trim().length() > 0 )
				return msg1;
			
			//Cập nhật tooltip
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[] { hdId } );
		
		} 
		catch (Exception e) 
		{
			msg1 = "Exception: " + e.getMessage();
			e.printStackTrace();
			return msg1;
		}

		return msg1;
	}

	
	private String TaoHoaDonTaiChinhNPP_NamCho_TuViTri(dbutils db, String id, String userId, String nppId, String congtyId, int index) 
	{
		String msg1 = "";
		try
		{
			String query = "";

			String kyhieuhoadon = "";
			String sohoadon = "";
			String ngayhoadon = "";

			sohoadon = "NA";
			kyhieuhoadon = "NA";
			String mau = "1";

			String chuoi = "";
			long sohoadontu = 0;
			long sohoadonden = 0;

			query = " select c.khachhang_fk, c.loaidonhang, c.donhangMUON, c.ngaydonhang, c.npp_fk, c.pk_seq donhangID, c.khachhangKG_FK \n" +
					" from ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_DDH B ON A.PK_SEQ = B.HOADONNPP_FK  \n" +
					"	   INNER JOIN ERP_DONDATHANGNPP C ON B.DDH_FK = C.PK_SEQ \n" +
					" where A.PK_SEQ = "+id;

			String khachhangID = "";
			String donhangMUON = "0";
			String loaidonhang = "";
			String ngaydonhang = "";
			String donhangID = "";
			String khachhangkgId = "";

			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					ngaydonhang = rs.getString("ngaydonhang");
					if(rs.getString("khachhang_fk") != null)
						khachhangID = rs.getString("khachhang_fk");

					nppId = rs.getString("npp_fk");
					donhangMUON = rs.getString("donhangMUON");
					loaidonhang = rs.getString("loaidonhang");
					donhangID = rs.getString("donhangID");
					khachhangkgId = rs.getString("khachhangKG_FK") == null ? "" : rs.getString("khachhangKG_FK");
				}
				rs.close();
			}

			if( loaidonhang.equals("1") || loaidonhang.equals("2") || loaidonhang.equals("0") || loaidonhang.equals("3"))
			{
				if( donhangMUON.equals("0") ) // ĐƠN HÀNG MƯỢN KHÔNG TỰ NHẢY SỐ HÓA ĐƠN
				{
					query =" select isnull( mauhoadon, 1 ) as mauhoadon from KHACHHANG where PK_SEQ ='"+khachhangID+"'";
					System.out.println("AAAAA:"+ query);
					ResultSet mauHDrs = db.get(query);

					if(mauHDrs != null)
					{
						while(mauHDrs.next())
						{
							mau = mauHDrs.getString("mauhoadon");
						}
						mauHDrs.close();
					}

					// CN HÀ NỘI && (CN HCM CÓ KH KHAI BÁO MẪU 2) DÙNG MẪU 2 (HO), CÒN LẠI DÙNG MẪU 1
					String query_kyhieu = " NV.KYHIEU ";				
					String query_sohdTU = " NV.SOHOADONTU ";	
					String query_sohdDEN = " NV.SOHOADONDEN ";	
					String query_mauhd = "1";
					String query_ngayhd = " NV.NGAYHOADON  ";

					// LAY TT KHAI BAO SO HD TRONG DLN
					query= " SELECT ISNULL("+ query_ngayhd +", '') as NGAYHOADON, (CASE WHEN ISNULL("+ query_kyhieu +",'-1') = '' THEN '-1' ELSE ISNULL("+ query_kyhieu +",'-1') END)  as KYHIEU, \n"+
							"        ISNULL("+ query_sohdTU +", -1) AS SOHOADONTU, ISNULL("+ query_sohdDEN +", -1) AS SOHOADONDEN,  \n"+
							"        (select count(hd.pk_seq) as dem  "+
							"         from HOADON hd               "+
							"         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							"               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							"               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_OTC, \n" +
							"        (select count(hd.pk_seq) as dem  "+
							"         from ERP_HOADONNPP hd               "+
							"         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							"               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							"               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_ETC \n" +
							" FROM NHANVIEN NV  \n" +
							" WHERE NV.pk_seq = '" + userId + "' \n";

					System.out.println("Câu check khai báo SHD "+query);
					ResultSet rsLayDL = db.get(query);

					int check_OTC = 0;
					int check_ETC = 0;

					while(rsLayDL.next())
					{
						kyhieuhoadon= rsLayDL.getString("kyhieu");
						sohoadontu = rsLayDL.getString("sohoadontu").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadontu") ;
						sohoadonden = rsLayDL.getString("sohoadonden").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadonden") ;;
						ngayhoadon = rsLayDL.getString("ngayhoadon");
						if(ngayhoadon.trim().length() <= 0)  ngayhoadon = ngaydonhang;
						check_OTC = rsLayDL.getInt("isSd_OTC");
						check_ETC = rsLayDL.getInt("isSd_ETC");
					}
					rsLayDL.close();

					if(kyhieuhoadon.equals("-1") || sohoadontu == -1 || sohoadonden == -1 )
					{
						msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
						return msg;
					}

					if(check_OTC <= 0 && check_ETC <= 0)
					{
						chuoi = ("000000" + sohoadontu);
						chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
					}
					else
					{
						// LAY SOIN MAX TRONG OTC && ETC
						query= " SELECT  \n"+
								"       (select max(cast(sohoadon as float)) as soin_max  "+
								"        from HOADON hd               "+
								"        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
								"              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
								"              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ ) soinMAX_OTC, \n" +
								"       (select max(cast(sohoadon as float)) as soin_max "+
								"        from ERP_HOADONNPP hd               "+
								"        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
								"              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
								"              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ) soinMAX_ETC  \n" +
								" FROM NHANVIEN NV  \n" +
								" WHERE NV.pk_seq = '" + userId + "' \n";

						System.out.println("Câu lấy SHD Max: "+query);
						long soinMAX_OTC = 0;
						long soinMAX_ETC = 0;

						ResultSet laySOIN = db.get(query);						     
						while(laySOIN.next())
						{
							soinMAX_OTC = laySOIN.getLong("soinMAX_OTC");
							soinMAX_ETC = laySOIN.getLong("soinMAX_ETC");
						}
						laySOIN.close();

						if( soinMAX_OTC > soinMAX_ETC ) 
						{
							chuoi = ("000000" + (soinMAX_OTC > 0 ? (soinMAX_OTC + 1) : "1" ));
						}
						else
						{
							chuoi = ("000000" + (soinMAX_ETC > 0 ? (soinMAX_ETC + 1) : "1"));
						}

						chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length()).trim();
					}

					if(Integer.parseInt(chuoi) > sohoadonden )
					{ 
						//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
						query= " SELECT  \n"+
								"      (select  max(cast(hd.sohoadon as float)) as soin_max   \n"+
								"       from HOADON hd                                     \n"+
								"       where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  \n"+
								"             and cast(hd.sohoadon as float) >= cast(ISNULL("+ query_sohdTU +", -1) as float)                                 \n"+
								"             and cast(hd.sohoadon as float) <= cast(ISNULL("+ query_sohdDEN +", -1) as float)  and hd.nguoisua = NV.PK_SEQ                               \n"+
								"       having max(cast(hd.sohoadon as float)) != ( select  MAX(cast(SOHOADON as float)) as SOIN_MAX  from HOADON where trangthai!= 3 and  kyhieu = ISNULL("+ query_kyhieu +",'-1')  and nguoisua= NV.PK_SEQ) \n"+
								"       ) soinMAX_OTC 										  \n"+								  
								" FROM NHANVIEN NV   \n" +
								" WHERE NV.pk_seq = '" + userId + "' \n";

						System.out.println("Câu lấy HD không dùng "+query);
						ResultSet SoMAX_HD = db.get(query);
						String soinmax = "";
						while(SoMAX_HD.next())
						{
							soinmax = SoMAX_HD.getString("soinMAX_OTC")== null ? "" : SoMAX_HD.getString("soinMAX_OTC") ;
							chuoi = ("000000" + (SoMAX_HD.getLong("soinMAX_OTC")));

						} SoMAX_HD.close();

						chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());

						if(soinmax.trim().length() <= 0 )
						{
							msg1 = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến (" + sohoadonden + ")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
							return msg1;
						}
					}

					sohoadon = chuoi ;

					// KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
					query = " select (select count(*) from HOADON where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraOTC, " +
							"        (select count(*) from ERP_HOADONNPP where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraETC " +
							" from NHANVIEN where pk_seq = '" + userId + "' ";

					System.out.println("Câu kiểm tra lại SHD: "+query);
					ResultSet RsRs = db.get(query);
					int KT_OTC = 0;
					int KT_ETC = 0;
					while(RsRs.next())
					{
						KT_OTC = RsRs.getInt("KtraOTC") ;
						KT_ETC = RsRs.getInt("KtraETC") ;
					}

					if(KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI TẠO
					{
						msg1 = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
						return msg1;
					}
				}
			}

			if(loaidonhang.equals("3")) mau = "";

			//TẠO MỚI HÓA ĐƠN
			query = " insert ERP_HOADONNPP (LOAIHOADON, isKM, DDKD_FK, KBH_FK, KHO_FK, NGUOIMUA, NGAYXUATHD, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, KYHIEU, SOHOADON, HINHTHUCTT, " +
					"		CHIETKHAU, TONGTIENAVAT, VAT, TONGTIENBVAT, GHICHU, LOAIXUATHD, NPP_FK, KHACHHANG_FK, NPP_DAT_FK, nhanvien_fk, MAUHOADON, " +
					"		MASOTHUE, TENKHACHHANG, DIACHI, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN) "+
					" select LoaiHoaDon, isKM, DDKD_FK, KBH_FK, Kho_FK, NGUOIMUA, NGAYXUATHD, 1 as TRANGTHAI, '"+getDateTime()+"' NGAYTAO, "+userId+" NGUOITAO, '"+getDateTime()+"' NGAYSUA, "+userId+" nguoisua, '"+kyhieuhoadon+"' kyhieu,'"+sohoadon+"' sohoadon, hinhthuctt , \n"+
					" 		 0 as chietkhau, 0 as tongtienavat, 0 as vat, 0 as tongtienbvat,N'Hóa đơn được tách từ hóa đơn có số chứng từ: "+this.id+"' GHICHU,  LOAIXUATHD, NPP_FK, KHACHHANG_FK, NPP_DAT_FK, nhanvien_fk,"+mau+" MAUHOADON, \n"+
					" 		 MASOTHUE, TENKHACHHANG, DIACHI, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN \n"+
					" from ERP_HOADONNPP " +
					" where PK_SEQ = "+id;		

			System.out.println("1.Insert ERP_HOADONNPP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP " + query;
				return msg1;
			}		

			String hdId = "";
			query = "select scope_identity() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();

			query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk)  values( " + hdId + ",  " + donhangID + "  )";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
				return msg1;
			}

			//TRƯỜNG HỢP TÁCH VƯỢT DÒNG, THÌ CHÈN CHI TIẾT TRƯỚC, SAU ĐÓ SUM TỪ CHI TIẾT LÊN TỔNG ĐỂ LẤY SỐ LƯỢNG
			query = "insert ERP_HOADONNPP_SP_CHITIET( hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SCHEME, CHONIN )  "+
					 "select '" + hdId + "' as hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SCHEME, CHONIN "+
					 "from ERP_HOADONNPP_SP_CHITIET " + 
					 "where hoadon_fk = '" + id + "' and sotutang > '" + index + "' ";

			System.out.println("1.0.Tu dong chen hoa don tach: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_SP_CHITIET " + query;
				return msg1;
			}
			
			query = "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, DonVi_Chuan, soluong, soluong_chuan, scheme, CHONIN, DONGIA, VAT)  "+
					 "select a.hoadon_fk, b.PK_SEQ as sanpham_fk, c.sanphamTEN, c.donvitinh, c.DonVi_Chuan, sum(a.soluong) as soluong, sum(a.soluong_chuan) as soluongCHUAN, a.scheme, c.CHONIN, c.DONGIA, c.VAT "+
					 "from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA "+
					 "	 inner join ERP_HOADONNPP_SP c on b.PK_SEQ = c.SANPHAM_FK and c.HOADON_FK = '" + id + "' and isnull(a.scheme, '') = isnull(c.SCHEME, '') "+
					 "where a.hoadon_fk = '" + hdId + "' "+
					 "group by a.hoadon_fk, b.PK_SEQ, c.sanphamTEN, c.donvitinh, c.DonVi_Chuan, a.scheme, c.CHONIN, c.DONGIA, c.VAT ";

			System.out.println("1.1.Tu dong chen hoa don tach: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
				return msg1;
			}

			//CHECK BANG TONG PHAI BANG BANG CHI TIET
			query = "select count(*) as sodong  " +
					"from " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"dh left join " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk " +
					"where dh.soluong != isnull(xk.soluong, 0) ";

			System.out.println("---CHECK HOA DON: " + query);
			int soDONG = 0;
			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("sodong");
				}
				rsCHECK.close();
			}

			if(soDONG > 0)
			{
				msg = "3.Số lượng trong đơn hàng tách không khớp với hóa đơn tách. Vui lòng liên hệ Admin để xử lý ";
				return msg;
			}

			//XOA HOA DON CŨ CHI TIET
			query = "delete ERP_HOADONNPP_SP_CHITIET where hoadon_fk = '" + id + "' and sotutang > '" + index + "' ";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the cap nhat ERP_HOADONNPP_SP_CHITIET: " + query;
				return msg1;
			}
			
			//TINH LAI HOA DON CU TOTAL
			query = "delete ERP_HOADONNPP_SP where hoadon_fk = '" + id + "' " + 
					" and sanpham_fk not in ( select pk_seq from SANPHAM where ma in ( select MA from ERP_HOADONNPP_SP_CHITIET where hoadon_fk = '" + id + "' )  ) ";
			if( !db.update(query) )
			{
				msg1 = "Khong the cap nhat ERP_HOADONNPP_SP_CHITIET: " + query;
				return msg1;
			}
			
			//COT SO LUONG TOTAL CU CO THE BI THAY DOI, NEN PHAI CAP NHAT LAI
			query =  "update hd set hd.SOLUONG = ( select SUM( SOLUONG ) from ERP_HOADONNPP_SP_CHITIET where hoadon_fk = hd.HOADON_FK and MA = sp.MA and scheme = hd.SCHEME ),  "+
					 "			  hd.SoLuong_Chuan = ( select SUM( SoLuong_Chuan ) from ERP_HOADONNPP_SP_CHITIET where hoadon_fk = hd.HOADON_FK and MA = sp.MA and scheme = hd.SCHEME ) "+
					 "from ERP_HOADONNPP_SP hd inner join SANPHAM sp on hd.SANPHAM_FK = sp.PK_SEQ "+
					 "where HOADON_FK = '" + id + "' ";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the cap nhat ERP_HOADONNPP_SP_CHITIET: " + query;
				return msg1;
			}
			
			
			//Cập nhật lại giá cho hóa đơn
			geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
			msg1 = util.CapNhat_ThanhTien_HoaDon(db, hdId);
			if( msg1.trim().length() > 0 )
				return msg1;
			
			msg1 = util.CapNhat_ThanhTien_HoaDon(db, id);
			if( msg1.trim().length() > 0 )
				return msg1;
			
			//Cập nhật tooltip
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[] { hdId } );
			
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[] { id } );
		
		} 
		catch (Exception e) 
		{
			msg1 = "Exception: " + e.getMessage();
			e.printStackTrace();
			return msg1;
		}

		return msg1;
	}

	
	
	
	public String getKhachhangKGId() {

		return this.khKGId;
	}


	public void setKhachhangKGId(String khkgId) {
		
		this.khKGId = khkgId;
	}

	
	public ResultSet getInvoiceInfo(String khId){
				
		return this.ttXhdRs;
	}
	
	public void setTthdId(String tthdId){
		this.tthdId = tthdId;
	}
	
	public String getTthdId(){
		return this.tthdId;
	}
	
	public void setNguoimuahang(String nguoimuahang){
		this.nguoimuahang = nguoimuahang;
	}
	
	public String getNguoimuahang(){
		return this.nguoimuahang;
	}

	public void setDonvimua(String donvimua){
		this.donvimua = donvimua;
	}
	
	public String getDonvimua(){
		return this.donvimua;
	}

	public void setDiachi(String diachi){
		this.diachi = diachi;
	}
	
	public String getDiachi(){
		return this.diachi;
	}
	
	public void setMasothue(String masothue){
		this.masothue = masothue;
	}
	
	public String getMasothue(){
		return this.masothue;
	}

	
	public String[] getSpThanhtien() {
	
		return this.spThanhtien;
	}

	
	public void setSpThanhtien(String[] spThanhtien) {
	
		this.spThanhtien = spThanhtien;
	}


	public String getTenxuathdNew() {

		return this.tenxuathdNew;
	}

	public void setTenxuathdNew(String tenxhdNew) {
		
		this.tenxuathdNew = tenxhdNew;
	}
	
	public String getMavuviec() {
		return mavuviec;
	}

	public void setMavuviec(String mavuviec) {
		this.mavuviec = mavuviec;
	}
	
	public String getPtchietkhau() {
		return ptchietkhau;
	}

	public void setPtchietkhau(String ptchietkhau) {
		this.ptchietkhau = ptchietkhau;
	}

	
	public String getGhichudonhang() {

		return this.ghichuDONHANG;
	}


	public void setGhuchidonhang(String ghichudonhang) {
		
		this.ghichuDONHANG = ghichudonhang;
	}

	
	public Hashtable<String, String> getSchemeTichluy() {
		
		return this.schemeTichluy;
	}

	
	public void setSchemeTichluy(Hashtable<String, String> schemeTichluy) {
		
		this.schemeTichluy = schemeTichluy;
	}

	
	public String getTientichluy() {
		
		if( this.tientichluy.trim().length() <= 0 )
			return "0";
		return this.tientichluy;
	}

	
	public void setTientichluy(String tientichluy) {
		
		this.tientichluy = tientichluy;
	}

	
	public String getDoituong() {
		
		return this.doituong;
	}

	
	public void setDoituong(String doituong) {
		
		this.doituong = doituong;
	}

	
	public String[] getCkId() {
		
		return this.ckId;
	}

	
	public void setCkId(String[] ckId) {
		
		this.ckId = ckId;
	}

}
