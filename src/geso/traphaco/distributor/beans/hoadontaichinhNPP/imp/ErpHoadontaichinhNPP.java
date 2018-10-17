package geso.traphaco.distributor.beans.hoadontaichinhNPP.imp;

import geso.traphaco.distributor.beans.hoadontaichinhNPP.IErpHoadontaichinhNPP;
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

public class ErpHoadontaichinhNPP implements IErpHoadontaichinhNPP
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
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	String nppTen;
	String sitecode;
	
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
	String spChonin;
	
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
	
	String isdhMuon;
	
	ResultSet ttXhdRs;
	
	Hashtable<String, String> schemeTichluy;
	String tientichluy;

	dbutils db;
	
	public ErpHoadontaichinhNPP()
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
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.schemeTichluy = new Hashtable<String, String>();
		this.tientichluy = "0";
		this.spChonin = "";
		
		this.db = new dbutils();
	}
	
	public ErpHoadontaichinhNPP(String id)
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
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.schemeTichluy = new Hashtable<String, String>();
		this.tientichluy = "0";
		this.spChonin = "";
		
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
		this.nppDangnhap = nppDangnhap;
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
/*		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppDangnhap=util.getIdNhapp(this.userId);
		//this.nppId = util.getIdNhapp(this.userId);
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
		
		System.out.println("nppId_____:"+this.nppId);*/
		
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			//this.nppId = util.getIdNhapp(this.userId);
			this.nppDangnhap=util.getIdNhapp(this.userId);
			this.nppTen = util.getTenNhaPP();
			this.sitecode = util.getSitecode();
		}
		else
		{
			//this.nppId = this.npp_duocchon_id;
			this.nppDangnhap=this.npp_duocchon_id;
			this.nppTen = "";
			this.sitecode = "";
		}
		
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

		System.out.println("loaiXD:"+this.loaiXHD);

		this.ngayghinhanCN = this.ngayxuatHD;

		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + util.quyen_kho(this.userId));
		
		//LẤY KH - NPP - NV
		if(this.loaiXHD.equals("1") || this.loaiXHD.equals("2") )// KHÁCH HÀNG
		{
			query = "select PK_SEQ, MAFAST + '-' + TEN as TEN from KHACHHANG KH  where TRANGTHAI = '1' and npp_fk ='"+ this.nppDangnhap +"' " +
					" and pk_seq in (select KHACHHANG_FK from ERP_DONDATHANGNPP where trangthai in (2,4) )"+
					//PHAN QUYEN
					util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KH", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );

			System.out.println(query);

			this.khRs = db.getScrol(query);
		}
		else if(this.loaiXHD.equals("3")) // NHÂN VIÊN
		{
			query = "select PK_SEQ, MA + '-' + TEN as TEN from ERP_NHANVIEN where TRANGTHAI = '1' and congty_fk = ( select congty_fk from NHAPHANPHOI where pk_seq = '" + this.nppDangnhap + "' ) " +
					" and pk_seq in (select NHANVIEN_FK from ERP_DONDATHANGNPP where trangthai in (2,4) )";
			this.nhanvienRs = db.get(query);
		}
		else if(this.loaiXHD.equals("0"))
		{
			query = "select PK_SEQ, MAFAST + '-' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk ='"+ this.nppDangnhap +"' ";
			if( this.nppId.trim().length() > 0 )
			{
				query += "union " + 
						" select PK_SEQ, MAFAST + '-' + TEN as TEN from NHAPHANPHOI where pk_seq = '" + this.nppId + "' ";
			}

			this.nppRs = db.get(query);
		}

		//LẤY NHÓM KÊNH
		if(this.ddhId.length() > 0)
		{
			if(this.loaiXHD.equals("0"))
				query="select pk_seq, diengiai as ten from NHOMKENH where pk_Seq in (select nhomkenh_fk from Erp_DonDatHangNPP where pk_Seq in ("+this.ddhId+") )";
			else
				query="select pk_seq, diengiai as ten from NHOMKENH where trangthai = '1' ";

			this.kbhRs = this.db.get(query);
			System.out.println("NHom kenh : "+query);
		}


		if(this.khId.trim().length()>0)
		{
			query = "select PK_SEQ , case when LEN(isnull(TENXUATHD,'')) > 0 then isnull(TENXUATHD,'') else TEN end  TENXUATHD, " +
					"isnull(GHICHU2, '') GHICHU2 FROM KHACHHANG WHERE PK_SEQ = "+this.khId;

			System.out.println(query);
			ResultSet rs =  this.db.get(query);

			if(rs!=null)
			{
				try{
					while (rs.next()){
						this.tenXuatHD = rs.getString("TENXUATHD")==null?new String[0]: rs.getString("TENXUATHD").split(",");
						this.ghichu = rs.getString("GHICHU2");
					}
					rs.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
					this.msg="Loi Trong Qua Trinh Lay Du Lieu ."+ e.toString();
				}
			}	

			//Lấy thông tin xuất hóa đơn để chọn
			query = "select PK_SEQ, TENNGUOIMUA, DONVI, DIACHI, MASOTHUE from KHACHHANG_THONGTINHOADON " + 
					" where KHACHHANG_FK = '" + this.khId + "' order by PK_SEQ asc";
			
			System.out.println(query);
			this.ttXhdRs = db.get(query);

			//SAU KHI CHỌN LẠI, THÌ LẤY THÔNG TIN CHỌN
			if( this.tthdId.trim().length() > 0 )
			{
				query = "select PK_SEQ, TENNGUOIMUA, DONVI, DIACHI, MASOTHUE from KHACHHANG_THONGTINHOADON where pk_seq = '" + this.tthdId + "'";
				rs =  this.db.get(query);
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

		if(this.nppId != null)
		{
			if(this.nppId.trim().length()>0)
			{			
				//Lấy thông tin xuất hóa đơn để chọn
				query = "select PK_SEQ, TENNGUOIMUA, DONVI, DIACHI, MASOTHUE from NPP_THONGTINHOADON " + 
						" where NPP_FK = '" + this.nppId + "' order by PK_SEQ asc";
				System.out.println(query);
				this.ttXhdRs = db.get(query);

				//SAU KHI CHỌN LẠI, THÌ LẤY THÔNG TIN CHỌN
				if( this.tthdId.trim().length() > 0 )
				{
					query = "select PK_SEQ, TENNGUOIMUA, DONVI, DIACHI, MASOTHUE from NPP_THONGTINHOADON where pk_seq = '" + this.tthdId + "'";
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
		}

		if(this.khId.trim().length() > 0 )
		{	
			// LẤY ĐƠN HÀNG
			query = 
					" select 	DH.PK_SEQ, DH.NgayDonHang \n"+
							" from 	ERP_DONDATHANGNPP DH \n"+
							"	INNER JOIN \n"+ 
							"	  ( \n"+ 
							"		SELECT DONDATHANG_fk,  SUM(soluong) AS SOLUONG \n"+
							"		FROM ERP_DONDATHANGNPP_SANPHAM_CHITIET \n"+ 
							"		GROUP BY DONDATHANG_fk \n"+
							"	  ) AS DHSP ON DH.PK_SEQ = DHSP.dondathang_fk \n"+
							"	  LEFT JOIN \n"+
							"	  ( \n"+
							"		SELECT B.DDH_FK, SUM(ISNULL(C.SoLuong, 0 )) AS SOLUONGDAXUAT \n"+
							"		FROM ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_DDH B ON A.PK_SEQ = B.HOADONNPP_FK \n"+
							"			 INNER JOIN ERP_HOADONNPP_SP C ON B.HOADONNPP_FK = C.HOADON_FK \n"+
							"		WHERE A.TRANGTHAI NOT IN (3,5) AND A.PK_SEQ NOT IN (" + (this.id.length() >0?this.id :"0") + ") AND LEN(ISNULL(C.SCHEME,'')) <= 0 \n"+
							"		GROUP BY B.DDH_FK \n"+
							"	  ) HOADON ON DH.PK_SEQ = HOADON.DDH_FK \n"+
							" WHERE DH.TRANGTHAI in ( 2, 4 ) AND (DHSP.SOLUONG - ISNULL(HOADON.SOLUONGDAXUAT,0) > 0) and DH.NPP_FK="+ this.nppDangnhap +" " +
							"	AND  DH.KHACHHANG_FK = '" + this.khId + "' and DH.kho_fk in " + util.quyen_kho(this.userId) ;

			System.out.println(":::LAY DDH: " + query);		
			this.ddhRs = db.get(query);
		}
		else if(this.nppId.trim().length() > 0 )
		{			
			query = 
					" select 	DH.PK_SEQ, DH.NgayDonHang \n"+
							" from 	ERP_DONDATHANGNPP DH \n"+
							"	INNER JOIN \n"+ 
							"	  ( \n"+ 
							"		SELECT DONDATHANG_fk,  SUM(soluong) AS SOLUONG \n"+
							"		FROM ERP_DONDATHANGNPP_SANPHAM_CHITIET \n"+ 
							"		GROUP BY DONDATHANG_fk \n"+
							"	  ) AS DHSP ON DH.PK_SEQ = DHSP.dondathang_fk \n"+
							"	  LEFT JOIN \n"+
							"	  ( \n"+
							"		SELECT B.DDH_FK, SUM(ISNULL( C.SoLuong, 0 )) AS SOLUONGDAXUAT \n"+
							"		FROM ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_DDH B ON A.PK_SEQ = B.HOADONNPP_FK \n"+
							"			 INNER JOIN ERP_HOADONNPP_SP C ON B.HOADONNPP_FK = C.HOADON_FK \n"+
							"		WHERE A.TRANGTHAI NOT IN (3,5) AND A.PK_SEQ NOT IN (" + this.id + ") AND LEN(ISNULL(C.SCHEME,'')) <=0 \n"+
							"		GROUP BY B.DDH_FK \n"+
							"	  ) HOADON ON DH.PK_SEQ = HOADON.DDH_FK \n"+
							" WHERE DH.TRANGTHAI in ( 2, 4 ) AND (DHSP.SOLUONG - ISNULL(HOADON.SOLUONGDAXUAT,0) > 0) and DH.NPP_FK="+ this.nppDangnhap +"  " +
							"	AND  DH.NPP_DAT_FK = '" + this.nppId + "' and DH.kho_fk in " + util.quyen_kho(this.userId) ;

			System.out.println(":::LAY DDH: " + query);		
			this.ddhRs = db.get(query);
		}

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
							//this.msg = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến (" + sohoadonden + ")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
							chuoi = "NA";
						}
					}

					this.sohoadon = chuoi ;

					// KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
					query = " select (select count(*) from ERP_XUATHOADONKM where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '2' and npp_fk = '" + nppDangnhap + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraOTC, " +
							"        (select count(*) from ERP_HOADONNPP where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppDangnhap + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraETC " +
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
						this.msg = "Số hoá đơn tiếp theo "+sohoadon+" đã có trên hệ thống! \n Vui lòng kiểm tra lại ";
						System.out.println(this.msg);
						
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
					query = "select b.PK_SEQ as SPID, b.MA, b.TEN, DV.donvi, a.dongia , "+
							"  isnull(scheme,' ') as scheme , isnull(a.thuevat,0) as vat ,   " +
							"  SUM( a.soluong)  as soluong, SUM( isnull(a.chietkhau, 0)) as chietkhau, b.MA as CHONIN "+
							"from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ   "+  	 
							" INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK  " +
							" inner join  ERP_DONDATHANGNPP c on a.dondathang_fk=c.pk_seq    "+
							"where a.dondathang_fk in ( "+ chuoi +" ) and a.dondathang_fk in (select pk_seq from ERP_DONDATHANGNPP where NPP_FK="+ this.nppDangnhap +")  " +
							"group by b.PK_SEQ , b.MA, b.TEN, DV.donvi, a.dongia , isnull(scheme,' ') , isnull(a.thuevat,0) ";

					System.out.println("INIT SP "+query);
					ResultSet rsLaySP = db.get(query);

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
					String spCHONIN = "";

					if(rsLaySP!= null)
					{				    	
						while(rsLaySP.next())
						{
							spID += rsLaySP.getString("SPID") + "__";
							spMA += rsLaySP.getString("MA") + "__";
							spTEN += rsLaySP.getString("TEN") + "__";
							spDONVI += rsLaySP.getString("DONVI") + "__";
							spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
							spGIANHAP += (rsLaySP.getDouble("DONGIA")) + "__";
							spCHIETKHAU += (rsLaySP.getDouble("chietkhau")) + "__";
							spSCHEME += rsLaySP.getString("scheme") + "__";
							spVAT +=  (rsLaySP.getDouble("vat")) + "__";
							spTIENTHUE +=  Math.round( ( Math.round( rsLaySP.getDouble("SOLUONG") * rsLaySP.getDouble("DONGIA") ) - rsLaySP.getDouble("chietkhau") ) * rsLaySP.getDouble("vat") / 100 ) + "__";
							spCHONIN += (rsLaySP.getString("CHONIN")) + ";" + rsLaySP.getString("scheme") + "__";

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
							this.spChonin = spCHONIN;
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
			query = "select b.PK_SEQ as SPID, b.MA, isnull(a.sanphamTEN, b.TEN) as TEN, a.donvitinh, a.soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, a.scheme, a.vat," +
					"	isnull( TIENVAT, ( ( round( soluong * dongia, 0 ) - chietkhau ) * isnull(vat, 0) / 100 ) ) as tienVAT, a.CHONIN, a.thanhtien  " +
					"from ERP_HOADONNPP_SP a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					"where a.hoadon_fk = "+ this.id +"  " ;				   

			System.out.println("INIT_SP: "+query);
			ResultSet rsLaySP = db.get(query);
			try 
			{
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
						
						if( rsLaySP.getString("CHONIN").equals("1") )
							spCHONIN +=  rsLaySP.getString("MA") + ";" + rsLaySP.getString("scheme") + "__";
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

						if( spCHONIN.trim().length() > 0 )
						{
							spCHONIN = spCHONIN.substring(0, spCHONIN.length() - 2);
							this.spChonin = spCHONIN;
						}
					}
				}
				
				
				//INIT CHIET KHAU
				NumberFormat formatter = new DecimalFormat("##,###,###");
				query = "select scheme, SUM( giatri ) as giatri from ERP_HOADONNPP_CHIETKHAU where HOADON_FK = '" + this.id + "' group by scheme";
				ResultSet	rs = db.get(query);
				Hashtable<String, String> schemeTichluy = new Hashtable<String, String>();
				while( rs.next() )
				{
					schemeTichluy.put( rs.getString("scheme"), formatter.format( rs.getDouble("giatri") ) );
				}
				rs.close();
				this.schemeTichluy = schemeTichluy;
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

		}

	}
		

	public void init() 
	{
		NumberFormat formatter = new DecimalFormat("##,###,###");
		Utility util = new Utility();
		this.getNppInfo();

		String query=	" SELECT isnull(c.ChietKhau,0) as ptchietkhau,a.kho_fk,dondathangnpp_fk, a.nhomkenh_fk, a.npp_fk, ngayxuatHD, ISNULL(a.ghichu, '') as ghichu, "+
						"  a.khachhang_fk, a.npp_dat_fk, a.nhanvien_fk, a.trangthai, kyhieu, sohoadon, hinhthuctt ,  isnull(a.chietkhau,0) as chietkhau,"+
						"  a.nguoimua, a.TENXUATHD, a.diachi, a.masothue, " + 
						"  isnull(innguoimua,1) as innguoimua,  isnull(tongtienbvat,0) as tongtienbvat,  isnull(tongtienavat,0)" +
						"  as tongtienavat,  isnull(a.vat, 0) as vat, isnull(a.chietkhau, 0) as chietkhau, loaixuatHD,isnull(mavv,'') as mavv, a.KHGHINO, a.loaihoadon,  isnull(a.NGAYGHINHAN, GETDATE()) ngayghinhan, c.khachhangKG_FK, ISNULL( c.ghichu, '' ) as ghichuDH, "+
						"  isnull( ( select SUM( GIATRI ) from ERP_HOADONNPP_CHIETKHAU where HOADON_FK = a.pk_seq ), 0 ) as tientichluy, c.donhangMUON	" +
						" FROM ERP_HOADONNPP a"+
						" LEFT JOIN ERP_HOADONNPP_DDH b on b.HOADONNPP_FK=a.PK_SEQ"+
						" LEFT join ERP_DONDATHANGNPP c on c.PK_SEQ=b.DDH_FK"+
						" WHERE a.pk_seq = '"+ this.id +"'";
		
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

					this.loaiXHD = rs.getString("loaixuatHD");		
					this.innguoimua = rs.getString("innguoimua");
					this.trangthai = rs.getString("trangthai");
					this.kbhId=rs.getString("nhomkenh_fk");
					this.khoNhanId=rs.getString("kho_fk");
					this.mavuviec=rs.getString("mavv");
					this.KhGhiNo = rs.getString("KHGHINO");
					this.loaidonhang = rs.getString("loaihoadon");
					this.ngayghinhanCN = rs.getString("ngayghinhan");
					
					this.khKGId = rs.getString("khachhangKG_FK") == null ? "" : rs.getString("khachhangKG_FK");
					
					this.ptchietkhau=rs.getString("ptchietkhau");
	
					this.bvat = formatter.format(rs.getDouble("tongtienbvat"));
					this.totalCHIETKHAU = formatter.format(rs.getDouble("chietkhau"));
					this.thueVAT = formatter.format(rs.getDouble("vat"));
					this.avat = formatter.format(rs.getDouble("tongtienavat"));
					
					//THONG TIN HOA DON
					this.tenxuathd = rs.getString("tenxuathd");
					
					this.nguoimuahang = rs.getString("nguoimua");	
					this.donvimua = rs.getString("tenxuathd");
					this.diachi = rs.getString("diachi");	
					this.masothue = rs.getString("masothue");
					this.ghichuDONHANG = rs.getString("ghichuDH");
					this.tientichluy = formatter.format(rs.getDouble("tientichluy"));
					
					
					this.ddhId="";
					//INIT DDH
					query = "SELECT HOADONNPP_FK, DDH_FK FROM ERP_HOADONNPP_DDH WHERE HOADONNPP_FK = " + this.id;
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
					
					if(this.trangthai.equals("3") || this.trangthai.equals("5") )
					{
						query = " select B.PK_SEQ ,B.NgayDonHang   " +
								" from ERP_HOADONNPP_DDH A INNER JOIN ERP_DONDATHANGNPP B ON A.DDH_FK = B.PK_SEQ " +
								" where A.HOADONNPP_FK = '"+ this.id +"'  and B.kho_fk in " + util.quyen_kho(this.userId);
					}
					else
					{
						if( this.loaiXHD.equals("1") || this.loaiXHD.equals("2") ) // KHACHHANG
						{			
						  query = 
							  " select 	DH.PK_SEQ, DH.NgayDonHang \n"+
							  " from 	ERP_DONDATHANGNPP DH \n"+
							  "	INNER JOIN \n"+ 
							  "	  ( \n"+ 
							  "		SELECT DONDATHANG_fk,  SUM(soluong) AS SOLUONG \n"+
							  "		FROM ERP_DONDATHANGNPP_SANPHAM \n"+ 
							  "		GROUP BY DONDATHANG_fk \n"+
							  "	  ) AS DHSP ON DH.PK_SEQ = DHSP.dondathang_fk \n"+
							  "	  LEFT JOIN \n"+
							  "	  ( \n"+
							  "		SELECT B.DDH_FK, SUM(ISNULL(C.SoLuong_Chuan,0)) AS SOLUONGDAXUAT \n"+
							  "		FROM ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_DDH B ON A.PK_SEQ = B.HOADONNPP_FK \n"+
							  "			 INNER JOIN ERP_HOADONNPP_SP C ON B.HOADONNPP_FK = C.HOADON_FK \n"+
							  "		WHERE A.TRANGTHAI NOT IN (3,5) AND A.PK_SEQ NOT IN ("+this.id+") \n"+
							  "		GROUP BY B.DDH_FK \n"+
							  "	  ) HOADON ON DH.PK_SEQ = HOADON.DDH_FK \n"+
							  " WHERE DH.TRANGTHAI in ( 2, 4 ) AND (DHSP.SOLUONG - ISNULL(HOADON.SOLUONGDAXUAT,0) > 0) and DH.NPP_FK="+ this.nppDangnhap +" " +
							  "	AND  DH.KHACHHANG_FK = '" + this.khId + "' and DH.kho_fk in " + util.quyen_kho(this.userId) ;
							/*" select PK_SEQ , NgayDonHang  " +
							" from ERP_DONDATHANGNPP " +
							" where trangthai = '4' and NPP_FK="+ this.nppDangnhap +" AND  KHACHHANG_FK = '" + this.khId + "' and kho_fk in " + util.quyen_kho(this.userId) +						
							" and pk_seq not in  (select a.DDH_FK from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK = b.PK_SEQ where b.TRANGTHAI in (2,4) and b.pk_seq != " + this.id +" and a.ddh_fk not in (select a.DDH_FK from  ERP_HOADONNPP_DDH where hoadonnpp_fk = "+this.id+"))   " ;
							 */
						}
						else if( this.loaiXHD.equals("0") ) // DOITAC
						{			
						  query = 
							  " select 	DH.PK_SEQ, DH.NgayDonHang \n"+
							  " from 	ERP_DONDATHANGNPP DH \n"+
							  "	INNER JOIN \n"+ 
							  "	  ( \n"+ 
							  "		SELECT DONDATHANG_fk,  SUM(soluong) AS SOLUONG \n"+
							  "		FROM ERP_DONDATHANGNPP_SANPHAM \n"+ 
							  "		GROUP BY DONDATHANG_fk \n"+
							  "	  ) AS DHSP ON DH.PK_SEQ = DHSP.dondathang_fk \n"+
							  "	  LEFT JOIN \n"+
							  "	  ( \n"+
							  "		SELECT B.DDH_FK, SUM(ISNULL(C.SoLuong_Chuan,0)) AS SOLUONGDAXUAT \n"+
							  "		FROM ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_DDH B ON A.PK_SEQ = B.HOADONNPP_FK \n"+
							  "			 INNER JOIN ERP_HOADONNPP_SP C ON B.HOADONNPP_FK = C.HOADON_FK \n"+
							  "		WHERE A.TRANGTHAI NOT IN (3,5) AND A.PK_SEQ NOT IN ("+this.id+") \n"+
							  "		GROUP BY B.DDH_FK \n"+
							  "	  ) HOADON ON DH.PK_SEQ = HOADON.DDH_FK \n"+
							  " WHERE DH.TRANGTHAI in ( 2, 4 ) AND (DHSP.SOLUONG - ISNULL(HOADON.SOLUONGDAXUAT,0) > 0) and DH.NPP_FK="+ this.nppDangnhap +" " +
							  "	AND  DH.NPP_DAT_FK = '" + this.nppId + "' and DH.kho_fk in " + util.quyen_kho(this.userId) ;
						  
							/*" select PK_SEQ , NgayDonHang  " +
							" from ERP_DONDATHANGNPP " +
							" where  trangthai = '4' and NPP_FK="+ this.nppDangnhap +" AND  NPP_DAT_FK = '" + this.nppId + "' and kho_fk in" +util.quyen_kho(this.userId) +					
							" and pk_seq not in(select a.DDH_FK from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ where b.TRANGTHAI =2 and b.pk_seq != " + this.id +" and a.ddh_fk not in (select a.DDH_FK from  ERP_HOADONNPP_DDH where hoadonnpp_fk = "+this.id+"))   " ;*/
	
						}
						else if(this.loaiXHD.equals("3")) // NỘI BỘ // NHÂN VIÊN
						{
							query = 
								  " select 	DH.PK_SEQ, DH.NgayDonHang \n"+
								  " from 	ERP_DONDATHANGNPP DH \n"+
								  "	INNER JOIN \n"+ 
								  "	  ( \n"+ 
								  "		SELECT DONDATHANG_fk,  SUM(soluong) AS SOLUONG \n"+
								  "		FROM ERP_DONDATHANGNPP_SANPHAM \n"+ 
								  "		GROUP BY DONDATHANG_fk \n"+
								  "	  ) AS DHSP ON DH.PK_SEQ = DHSP.dondathang_fk \n"+
								  "	  LEFT JOIN \n"+
								  "	  ( \n"+
								  "		SELECT B.DDH_FK, SUM(ISNULL(C.SoLuong_Chuan,0)) AS SOLUONGDAXUAT \n"+
								  "		FROM ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_DDH B ON A.PK_SEQ = B.HOADONNPP_FK \n"+
								  "			 INNER JOIN ERP_HOADONNPP_SP C ON B.HOADONNPP_FK = C.HOADON_FK \n"+
								  "		WHERE A.TRANGTHAI NOT IN (3,5) AND A.PK_SEQ NOT IN ("+this.id+") \n"+
								  "		GROUP BY B.DDH_FK \n"+
								  "	  ) HOADON ON DH.PK_SEQ = HOADON.DDH_FK \n"+
								  " WHERE DH.TRANGTHAI in ( 2, 4 ) AND (DHSP.SOLUONG - ISNULL(HOADON.SOLUONGDAXUAT,0) > 0) and DH.NPP_FK="+ this.nppDangnhap +" " +
								  "	AND  DH.NHANVIEN_FK = '" + this.nhanvienId + "' and DH.kho_fk in " + util.quyen_kho(this.userId) ;
							
								/*" select PK_SEQ , NgayDonHang  " +
								" from ERP_DONDATHANGNPP " +
								" where  trangthai = '4' and NPP_FK="+ this.nppDangnhap +" AND  NHANVIEN_FK = '" + this.nhanvienId + "' and kho_fk in" +util.quyen_kho(this.userId) +							
								" and pk_seq not in(select a.DDH_FK from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ where b.TRANGTHAI =2 and b.pk_seq != " + this.id +" and a.ddh_fk not in (select a.DDH_FK from  ERP_HOADONNPP_DDH where hoadonnpp_fk = "+this.id+"))   " ;
								*/
						}
					}
					
					System.out.println("LAY DANH SACH DDH: " + query);		
					this.ddhRs = db.get(query);
				}
				rs.close();
				
				//INIT CHIET KHAU
				query = "select scheme, SUM( giatri ) as giatri from ERP_HOADONNPP_CHIETKHAU where HOADON_FK = '" + this.id + "' group by scheme";
				rs = db.get(query);
				Hashtable<String, String> schemeTichluy = new Hashtable<String, String>();
				while( rs.next() )
				{
					schemeTichluy.put( rs.getString("scheme"), formatter.format( rs.getDouble("giatri") ) );
				}
				rs.close();
				this.schemeTichluy = schemeTichluy;
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		
		/*if(this.loaiXHD.equals("1") || this.loaiXHD.equals("2") )
		{
			query = "select PK_SEQ , case when LEN(isnull(TENXUATHD,'')) > 0 then isnull(TENXUATHD,'') else TEN end  TENXUATHD " + 
					" FROM KHACHHANG WHERE PK_SEQ = "+this.khId;
			ResultSet rs_kh =  this.db.get(query);
			try{
				if(rs_kh!=null)
				{
					while(rs_kh.next())
					{
						this.tenXuatHD = rs_kh.getString("TENXUATHD")==null?new String[0]: rs_kh.getString("TENXUATHD").split(",");

					}
					rs_kh.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				this.msg="Loi Trong Qua Trinh Lay Du Lieu ."+ e.toString();
			}
		}*/
		  
		try
		{
			//INIT SOLO
			/*query = "select b.ma, ct.scheme, solo, ngayhethan, sum(soluong) as soluong  " +
					"from ERP_DONDATHANGNPP_SANPHAM_CHITIET  ct inner join SANPHAM b on ct.sanpham_fk = b.pk_seq "+ 
					"where dondathang_fk = '" + this.ddhId + "' "+
					"group by b.ma, ct.scheme, solo, ngayhethan order by ct.scheme asc ";*/
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

		if(this.ngayghinhanCN.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày ghi nợ";
			return false;
		}
		
		if(this.loaiXHD.equals("0")) // HÓA ĐƠN - NHÀ PHÂN PHỐI
		{
			if(this.nppId.trim().length()<=0)
			{
				this.msg = "Vui lòng chọn nhà phân phối";
				return false;
			}
		}
		else if(this.loaiXHD.equals("1")||this.loaiXHD.equals("2")) //HÓA ĐƠN - KHÁCH HÀNG THẦU || KHÔNG THẦU
		{
			if(this.khId.trim().length()<=0)
			{
				this.msg = "Vui lòng chọn khách hàng";
				return false;
			}
			
			if(this.KhGhiNo.trim().length()<=0)
			{
				this.msg = "Vui lòng chọn khách hàng ghi nợ";
				return false;
			}
		}
		else if(this.loaiXHD.equals("3")) // HÓA ĐƠN - NỘI BỘ
		{
			if(this.nhanvienId.trim().length()<=0)
			{
				this.msg = "Vui lòng chọn nhân viên";
				return false;
			}
		}
		
		if(this.ddhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn đặt hàng";
			return false;
		}
				
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn";
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
			String query = "select NgayDonHang, DATEDIFF(dd, NgayDonHang, '" + this.ngayxuatHD + "') as chenhLECH from ERP_DondathangNPP where PK_SEQ = '" + this.ddhId + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					if(rs.getInt("chenhLECH") < 0 )
					{
						this.msg = "Ngày xuất hóa đơn ( " + this.ngayxuatHD + " ) không được nhỏ hơn ngày đơn hàng ( " + rs.getString("NgayDonHang") + " ) ";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			db.getConnection().setAutoCommit(false);
			
			/*String query = " select loaiNPP from NHAPHANPHOI where pk_seq = '" + this.nppDangnhap + "' ";
			ResultSet rs = db.get(query);
			String loaiNPP = "";
			if(rs != null)
			{
				if(rs.next())
				{
					loaiNPP = rs.getString("loaiNPP");
				}
				rs.close();
			}*/
			
			// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
			/*String chuoi="";
			
			int kbDLN=0;
			
			// Lấy mẫu hóa đơn của Khách hàng dùng
			query = " select mauhoadon from KHACHHANG where PK_SEQ ='"+this.khId+"'";
			ResultSet mauHDrs = db.get(query);
			String mau = "";
			if(mauHDrs!=null)
			{
				while(mauHDrs.next())
				{
					mau = mauHDrs.getString("mauhoadon");
				}
				mauHDrs.close();
			}*/
			
			String loaiNPP = "1";
			long sohoadontu = 0;
			long sohoadonden = 0;
			String mau = "1";
			
			if(!loaiNPP.equals("4"))
			{
				String chuoi = "";
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
					   "         from ERP_XUATHOADONKM hd               "+
					   "         where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
					   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
					   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_OTC, \n" +
					   "        (select count(hd.pk_seq) as dem  "+
					   "         from ERP_HOADONNPP hd               "+
					   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
					   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
					   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_ETC \n" +
					   //" FROM NHANVIEN NV  \n" +
					   //" WHERE NV.pk_seq = '" + userId + "' \n";
					   " FROM NHANVIEN_SOHOADON NV  \n" +
				       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppId + "' \n";
				System.out.println("Câu check khai báo SHD "+query);
				ResultSet rsLayDL = db.get(query);
				
				int check_OTC = 0;
				int check_ETC = 0;
									
					while(rsLayDL.next())
					{
						kyhieuhoadon= rsLayDL.getString("kyhieu");
						sohoadontu = rsLayDL.getString("sohoadontu").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadontu") ;
						sohoadonden = rsLayDL.getString("sohoadonden").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadonden") ;
						check_OTC = rsLayDL.getInt("isSd_OTC");
						check_ETC = rsLayDL.getInt("isSd_ETC");
					}
					rsLayDL.close();
				
				if(kyhieuhoadon.equals("-1") || sohoadontu == -1 || sohoadonden == -1 )
				{
					msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
					return false;
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
						   "        from ERP_XUATHOADONKM hd               "+
						   "        where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ ) soinMAX_OTC, \n" +
						   "       (select max(cast(sohoadon as float)) as soin_max "+
						   "        from ERP_HOADONNPP hd               "+
						   "        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ) soinMAX_ETC  \n" +
						   //" FROM NHANVIEN NV  \n" +
						   //" WHERE NV.pk_seq = '" + userId + "' \n";
						   " FROM NHANVIEN_SOHOADON NV  \n" +
					       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppId + "' \n";
					
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
						   "       having max(cast(hd.sohoadon as float)) != ( select  MAX(cast(SOHOADON as float)) as SOIN_MAX  from ERP_HOADONNPP where trangthai!= 3 and  kyhieu = ISNULL("+ query_kyhieu +",'-1')  and nguoisua= NV.PK_SEQ) \n"+
						   "       ) soinMAX_OTC 										  \n"+								  
						   //" FROM NHANVIEN NV   \n" +
						   //" WHERE NV.pk_seq = '" + userId + "' \n";
						   " FROM NHANVIEN_SOHOADON NV  \n" +
					       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppId + "' \n";
						   
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
						//msg = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến (" + sohoadonden + ")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
						//return false;
						chuoi = "NA";
					}
				}
				
				 sohoadon = chuoi ;
				
			    // KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
				query = " select (select count(*) from ERP_XUATHOADONKM where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '2' and npp_fk = '" + nppDangnhap + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraOTC, " +
						"        (select count(*) from ERP_HOADONNPP where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppDangnhap + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraETC " +
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
					msg = "Số hoá đơn tiếp theo "+sohoadon+" đã có trên hệ thống! \n Vui lòng kiểm tra lại ";
					System.out.println(msg);
					return false;
				}
			}
			
			String tbThongTin = " NHAPHANPHOI ";
			String thongtinId = this.nppId;
			if(this.nppId.length() <= 0)
			{ 
				this.nppId = "NULL"; 
				tbThongTin = " KHACHHANG ";
				thongtinId = this.khId;
			}
			
			String ddkd_fk ="(select ddkd_fk From Erp_DonDatHangNpp where pk_Seq='"+this.ddhId+"') ";
			String khoId ="(select KHO_FK From Erp_DonDatHangNpp where pk_Seq='"+ddhId+"') ";
			String kbhId ="(select kbh_fk From Erp_DonDatHangNpp where pk_Seq='"+ddhId+"') ";
		 
			if(this.KhGhiNo.trim().length() <= 0)
				this.KhGhiNo = this.khId;
			
			query =    " insert ERP_HOADONNPP(KHO_FK,DDKD_FK, innguoimua,ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt ," +
				       " 	chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaihoadon, loaixuathd, npp_fk, khachhang_fk, npp_dat_fk, mauhoadon,TENKHACHHANG,DIACHI,MASOTHUE,mavv, TENXUATHD, congty_fk, KHGHINO, GHICHU2, KBH_FK, NGAYGHINHAN ) " +
					   " select "+khoId+","+ddkd_fk+", "+ this.innguoimua +", '" + this.ngayxuatHD + "', '1','" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', RTRIM(LTRIM('" + this.kyhieuhoadon + "')), " +
					   "       RTRIM(LTRIM('" + this.sohoadon + "')), N'"+ this.hinhthuctt +"' , '"+ this.totalCHIETKHAU.replaceAll(",", "")  +"', '"+ this.bvat.replaceAll(",", "") +"', '" + this.avat.replaceAll(",", "")  +"'," +
					   "       '"+ this.thueVAT.replaceAll(",", "") +"', N'"+ this.ghichu +"', '0', '"+ this.loaiXHD +"', '"+ this.nppDangnhap +"', "+ this.khId +", "+ this.nppId +", '"+ mau +"'" +
					   "		 , (select ten from "+tbThongTin+" where pk_Seq =" + thongtinId + " ) as nppMua " +
					   " 		, (select ISNULL(DIACHI,'') from "+tbThongTin+" where pk_Seq =" + thongtinId + " ) as diachinpp " +
					   " 		, (select ISNULL(MASOTHUE,'') from "+tbThongTin+" where pk_Seq =" + thongtinId + " ) as mst, '"+this.mavuviec+"', '"+this.tenxuathd+"', '" + congtyId + "', "+this.KhGhiNo+", N'"+this.Ghichu2+"', " + kbhId + ", '"+this.ngayghinhanCN+"' " ;
 			
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
			
			String tooltip = "NULL";
			String table = "";
			double tongtien = 0;
			double tongtienck = 0;
			double tongvat = 0;
			double tongsauck = 0;
			double tongsauvat = 0;
			
			query = "select ten from nhomkenh where pk_seq = " + this.kbhId;
			rs = db.get(query);
			String kbh = "";
			if(rs != null)
			{
				if(rs.next())
					kbh = rs.getString("ten");
				rs.close();
			}
			tooltip = "Mã số: " + hdId + "</br> Ngày hóa đơn: " + this.ngayxuatHD +
					"</br> Số hóa đơn: " + this.sohoadon + "</br> Kênh bán hàng: " + kbh;
			table = "</br><table><tr><th style=width: 10% >Mã SP</th><th style=width: 10% >Tên SP</th><th style=width: 10% >Số lượng</th><th style=width: 10% >Đơn giá</th></tr>";
		
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					String thanhtien = spSoluong[i].replaceAll(",", "")+ " * "+ spDongia[i].replaceAll(",", "");
					String chonin = "0";
					if( this.spChonin.contains( spMa[i] + ";" + spSCheme[i] ) )
						chonin = "1";
					
					query = "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme , vat, CHONIN) " +
							" values( "+ hdId +", '" + spId[i] + "', N'" + spTen[i] + "', N'"+ spDonvi[i] +"', '" + spSoluong[i].replaceAll(",", "") + "', '" + spDongia[i].replaceAll(",", "") + "'," +
									" "+ thanhtien +", '"+ spChietkhau[i].replaceAll(",", "") +"', N'"+ spSCheme[i].replaceAll(",", "") +"', '"+ spVat[i].replaceAll(",", "") +"', " + chonin + " ) ";
					
					System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					table += "<tr><td>"+spMa[i].trim()+"</td><td>"+spTen[i]+"</td><td>"+spSoluong[i]+"</td><td>"+spDongia[i]+"</td></tr>";
					tongtien += Double.parseDouble(spDongia[i].replaceAll(",", "")) * Double.parseDouble(spSoluong[i].replaceAll(",", ""));
					tongtienck += Double.parseDouble(spChietkhau[i].replaceAll(",", ""));
					tongvat += ((Double.parseDouble(spDongia[i].replaceAll(",", "")) * Double.parseDouble(spSoluong[i].replaceAll(",", "")) - Double.parseDouble(spChietkhau[i].replaceAll(",", ""))) * (Double.parseDouble(spVat[i].replaceAll(",", "")) / 100));
				}
			}
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			table += "</table>";
			tongsauck = tongtien - tongtienck;
			tongsauvat = tongsauck + tongvat;
			tooltip += "</br> Tổng thành tiền: " + formatter.format(tongtien);
			tooltip += "</br> Tổng tiền CK: " + formatter.format(tongtienck);
			tooltip += "</br> Tổng tiền sau CK: " + formatter.format(tongsauck);
			tooltip += "</br> Tổng tiền sau VAT: " + formatter.format(tongsauvat);
			tooltip += table;
			
			query = "update ERP_HOADONNPP set tooltip = N'"+ tooltip + "' where pk_seq = " + hdId ;
			System.out.println("-- update hoa don: " + query );
			if(!db.update(query))
			{
				msg = "Lỗi khi cập nhật đơn hàng: " + query;
				db.getConnection().rollback();
				return false;
			}
			
				query=
				"		update C set SoLuong_Chuan=  "+
				"				case     when c.donvitinh = e.donvi then c.soluong  "+     
				"								  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  ,  "+ 
				"		DonGia_Chuan=	case     when c.donvitinh = e.donvi then c.dongia      "+
				"							  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from		DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  , "+
				"			DonVi_Chuan=e.DONVI  "+
				"		from ERP_HOADONNPP a         "+
				"			left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ      "+
				"			inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  "+ 
				"			inner join SANPHAM d on c.sanpham_fk = d.pk_seq    "+
				"			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq  "+      
				"		where a.pk_Seq='"+hdId+"' ";
				System.out.println("1.1.UPDATE ERP_HOADONNPP: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_HOADONNPP_SP " + query;
					db.getConnection().rollback();
					return false;
				}
			
			
			query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk) select " + hdId + ", pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + this.ddhId + " )    ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
					
			query = "Update ERP_YCXUATKHONPP set NGAYYEUCAU = '" + this.ngayxuatHD + "' " +
					"where PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk in ( " + this.ddhId + " ) )";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//Trong trường hợp 1 khách hàng xuất hóa đơn với hình thức thanh toán là tiền mặt thì giới hạn không được xuất quá 20,000,000 VNĐ/ ngày
			if(this.hinhthuctt.equals("Tiền mặt") && this.loaiXHD.equals("1") )
			{
				query = "select isnull( SUM(tongtienavat), 0) as tongtien from ERP_HOADONNPP where NGAYXUATHD = '" + this.ngayxuatHD + "' and KHACHHANG_FK = '" + this.khId + "' and HINHTHUCTT = N'" + this.hinhthuctt + "' ";
				rs = db.get(query);
				double tongSOTIEN = 0;
				if(rs != null)
				{
					if(rs.next())
					{
						tongSOTIEN = rs.getDouble("tongtien");
					}
					rs.close();
				}
				
				if(tongSOTIEN > 20000000 )
				{
					this.msg = "Trong ngày " + this.ngayxuatHD + " với hình thức thanh toán tiền mặt, bạn chỉ có thể thanh toán tối đa thêm ( " + ( tongSOTIEN - 20000000 ) + " ) ";
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.loaiXHD.equals("1") || this.loaiXHD.equals("2")){
				query = "SELECT COUNT(*) as NUM FROM KHACHHANG_THONGTINHOADON " +
						"WHERE TENNGUOIMUA = N'" + this.nguoimuahang + "' AND DONVI = N'" + this.donvimua + "' " +
						"AND DIACHI = N'" + this.diachi + "' AND MASOTHUE = '" + this.masothue + "' " +
						"AND KHACHHANG_FK = " + this.khId + " AND PK_SEQ = " + this.tthdId + " ";
				rs = this.db.get(query);
				
				rs.next();
				
				if(rs.getInt("NUM") == 0){
					query = "INSERT INTO KHACHHANG_THONGTINHOADON(TENNGUOIMUA, DONVI, DIACHI, MASOTHUE, KHACHHANG_FK, ACTIVE) VALUES( " +
							" N'" + this.nguoimuahang + "', N'" + this.donvimua + "', N'" + this.diachi + "', N'" + this.masothue + "', " + this.khId + ", '1' )";
					
					this.db.update(query);
					
				}else{
					
					query = "UPDATE KHACHHANG_THONGTINHOADON SET ACTIVE = '0' WHERE KHACHHANG_FK = " + this.khId + " " ;
					this.db.update(query);
					
					query = "UPDATE KHACHHANG_THONGTINHOADON SET ACTIVE = '1' WHERE PK_SEQ = " + this.tthdId + " " ;
					this.db.update(query);
					
				}
				rs.close();
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			Utility util = new Utility();
			util.CapNhat_ThanhTien_HoaDon(db, this.id);
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

	String donhangMUON = "0";
	public boolean update() 
	{
		this.getNppInfo();
		
		//NEU HOA DON DA HUY THI CHI DUOC SUA KY HIEU VA SO HOA DON
		String sqlCHECK = "select a.trangthai, b.loaiNPP, a.nguoitao, a.npp_fk  from ERP_HOADONNPP a inner join NHAPHANPHOI b on a.npp_fk = b.pk_seq " +
						  "where a.pk_seq = '" + this.id + "'";
	
		ResultSet rsCHECK = db.get(sqlCHECK);
		String trangthai = "";
		String loaiNPP = "";
		String nguoitao = "";
		String npp_fk = "";
		try 
		{
			if(rsCHECK.next())
			{
				trangthai = rsCHECK.getString("trangthai");
				loaiNPP = rsCHECK.getString("loaiNPP");
				nguoitao = rsCHECK.getString("nguoitao");
				npp_fk = rsCHECK.getString("npp_fk");
			}
			rsCHECK.close();
		} 
		catch (Exception e2) 
		{
			e2.printStackTrace();
			this.msg = e2.getMessage();
			return false;
		}
		
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
					
		if(this.loaiXHD.equals("0")) // HÓA ĐƠN - NHÀ PHÂN PHỐI
		{
			if(this.nppId.trim().length()<=0)
			{
				this.msg = "Vui lòng chọn nhà phân phối";
				return false;
			}
		}
		else if(this.loaiXHD.equals("1")||this.loaiXHD.equals("2")) //HÓA ĐƠN - KHÁCH HÀNG THẦU || KHÔNG THẦU
		{
			if(this.khId.trim().length()<=0)
			{
				this.msg = "Vui lòng chọn khách hàng";
				return false;
			}
			
			if(this.KhGhiNo.trim().length()<=0)
			{
				this.msg = "Vui lòng chọn khách hàng ghi nợ";
				return false;
			}
		}
		else if(this.loaiXHD.equals("3")) // HÓA ĐƠN - NỘI BỘ
		{
			if(this.nhanvienId.trim().length()<=0)
			{
				this.msg = "Vui lòng chọn nhân viên";
				return false;
			}
		}
		
		if(trangthai.equals("1") || trangthai.equals("2") || trangthai.equals("4") )
		{
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
		}
		else if( trangthai.equals("3") || trangthai.equals("5") )
		{
			this.msg = "Trạng thái hóa đơn không hợp lệ. Vui lòng liên hệ Admin để được xử lý.";
			return false;
		}
		
		try
		{
			String query  = "";
			// TẠM THỜI BỎ RÀNG
			//CHECK NGÀY HÓA ĐƠN KHÔNG ĐƯỢC NHỎ HƠN NGÀY ĐƠN HÀNG
			query = "select NgayDonHang, DATEDIFF(dd, NgayDonHang, '" + this.ngayxuatHD + "') as chenhLECH, donhangMUON " + 
						   " from ERP_DondathangNPP where PK_SEQ = '" + this.ddhId + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					this.donhangMUON = rs.getString("donhangMUON");
					if(rs.getInt("chenhLECH") < 0 )
					{
						this.msg = "Ngày xuất hóa đơn ( " + this.ngayxuatHD + " ) không được nhỏ hơn ngày đơn hàng ( " + rs.getString("NgayDonHang") + " ) ";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			db.getConnection().setAutoCommit(false);
			
			// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
			long sohoadontu = 0;
			long sohoadonden = 0;
						
			String kyhieuhoadon="";
			String sohoadon="";
			String ngayhoadon = "";
			
			sohoadon="NA";
			kyhieuhoadon="NA";
			String mau = "1";
			
			String chuoi = "";			

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
				   "         from ERP_XUATHOADONKM hd               "+
				   "         where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
				   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
				   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(SOHOADON) =1 ) isSd_OTC, \n" +
				   "        (select count(hd.pk_seq) as dem  "+
				   "         from ERP_HOADONNPP hd               "+
				   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
				   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
				   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(SOHOADON) =1) isSd_ETC, \n" +
				   "        (select count(hd.pk_seq) as dem  "+
				   "         from ERP_HOADONNPP hd               "+
				   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1') and ISNUMERIC( hd.SOHOADON)= 1 "+
				   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
				   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND HD.PK_SEQ = "+this.id+" AND ISNUMERIC(SOHOADON) =1) isSHDkhaibao \n" +				   
				  // " FROM NHANVIEN NV  \n" +
				   //" WHERE NV.pk_seq = '" + userId + "' \n";
				   " FROM NHANVIEN_SOHOADON NV  \n" +
			       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppDangnhap + "' \n";
			System.out.println("Câu check khai báo SHD "+query);
			ResultSet rsLayDL = db.get(query);
			
			int check_OTC = 0;
			int check_ETC = 0;
			int check_SHDkhaibao = 0;
								
				while(rsLayDL.next())
				{
					kyhieuhoadon= rsLayDL.getString("kyhieu");
					sohoadontu = rsLayDL.getString("sohoadontu").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadontu") ;
					sohoadonden = rsLayDL.getString("sohoadonden").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadonden") ;;
					ngayhoadon = rsLayDL.getString("ngayhoadon");
					check_OTC = rsLayDL.getInt("isSd_OTC");
					check_ETC = rsLayDL.getInt("isSd_ETC");
					check_SHDkhaibao = rsLayDL.getInt("isSHDkhaibao");
				}
				rsLayDL.close();
			
			if(kyhieuhoadon.equals("-1") || sohoadontu == -1 || sohoadonden == -1 )
			{
				msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
				db.getConnection().rollback();
				return false;
			}
						
			if(check_OTC <= 0 && check_ETC <= 0)
			{
				chuoi = ("000000" + sohoadontu);
				chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
			}
			else
			{
				// KIỂM TRA NẾU SỐ HÓA ĐƠN THUỘC KHOẢNG ĐANG KHAI BÁO THÌ LẤY LUÔN SỐ ĐÓ => KHÔNG NHẢY SỐ NỮA
				if(check_SHDkhaibao == 1)
				{
					chuoi = this.sohoadon;
				}
				else
				{
					// LAY SOIN MAX TRONG OTC && ETC
					query= " SELECT  \n"+
						   "       (select max(cast(sohoadon as float)) as soin_max  "+
						   "        from ERP_XUATHOADONKM hd               "+
						   "        where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(SOHOADON) =1) soinMAX_OTC, \n" +
						   "       (select max(cast(sohoadon as float)) as soin_max "+
						   "        from ERP_HOADONNPP hd               "+
						   "        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ and hd.pk_seq != "+this.id+" AND ISNUMERIC(SOHOADON) =1 ) soinMAX_ETC  \n" +
						   //" FROM NHANVIEN NV  \n" +
						   //" WHERE NV.pk_seq = '" + userId + "' \n";
						   " FROM NHANVIEN_SOHOADON NV  \n" +
					       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppDangnhap + "' \n";
					
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
			}
						
			if(Integer.parseInt(chuoi) > sohoadonden )
			{ 
				msg = "Số hóa đơn tiếp theo đã vượt quá dãy Số hóa đơn được khai trong trong dữ liệu nền. Vui lòng vào dữ liệu nền số hóa đơn khai báo lại ! ";
				return false;
				
			}
			
			 sohoadon = chuoi ;
			
		    // KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
			query = " select ( select count(*) from ERP_XUATHOADONKM where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '2' and npp_fk = '" + this.nppDangnhap + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) =1 ) as KtraOTC, \n" +
					"        ( select count(*) from ERP_HOADONNPP where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + this.nppDangnhap + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" and pk_seq != "+this.id+" AND ISNUMERIC(SOHOADON) =1 ) as KtraETC, \n" +
					"        ( select count(*) from ERP_HOADONNPP where cast( sohoadon as float ) < cast( ISNULL( "+ sohoadon +", -1 ) as float) and NGAYXUATHD > '"+ngayhoadon+"' and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + this.nppDangnhap + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" and pk_seq != "+this.id+" AND ISNUMERIC(SOHOADON) =1 ) as is_KtraOTC_dk, \n"+
					"        ( select count(*) from ERP_XUATHOADONKM where cast( sohoadon as float ) < cast( ISNULL( "+ sohoadon +", -1 ) as float) and NGAYHOADON > '"+ngayhoadon+"' and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + this.nppDangnhap + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) =1 ) as is_KtraETC_dk \n"+
					" from NHANVIEN where pk_seq = '" + userId + "' ";
			
			System.out.println("Câu kiểm tra lại SHD: "+query);
			ResultSet RsRs = db.get(query);
			int KT_OTC = 0;
			int KT_ETC = 0;
			
			int is_KT_OTC = 0;
			int is_KT_ETC = 0;
			
			while(RsRs.next())
			{
				KT_OTC = RsRs.getInt("KtraOTC") ;
				KT_ETC = RsRs.getInt("KtraETC") ;
				
				is_KT_OTC = RsRs.getInt("is_KtraOTC_dk") ;
				is_KT_ETC = RsRs.getInt("is_KtraETC_dk") ;
			}
			
			if(is_KT_OTC >0 || is_KT_ETC > 0) // NẾU CÓ SỐ HÓA ĐƠN LỚN HƠN MÀ NGÀY XUẤT NHỎ HƠN CỦA HÓA ĐƠN TIẾP THEO THÌ K CHO
			{
				msg = "Có số hóa đơn lơn hơn mà ngày xuất nhỏ hơn nên không thể duyệt hóa đơn này. Yêu cầu check lại ngày thiết lập số hóa đơn hoặc liên hệ admin!";
				db.getConnection().rollback();
				return false;
			}
				
			if(KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI TẠO
			{
				//msg = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
				msg = "Số hoá đơn tiếp theo "+sohoadon+" đã có trên hệ thống! \n Vui lòng kiểm tra lại, hoặc liên hệ admin! ";
				db.getConnection().rollback();
				System.out.println(msg);
				return false;
			}
			
			
			if(this.nppId.length() <= 0) this.nppId = "NULL";
			if(this.khId.length() <= 0) this.khId = "NULL";
			if(this.nhanvienId.length() <= 0) this.nhanvienId = "NULL";
			
						
			query = "";
			if( !trangthai.equals("1") )  //NẾU ĐÃ DUYỆT THÌ CHỈ ĐƯỢC CHỈNH SỬA MỘT SỐ CÁC THÔNG TIN SAU
			{
				query = " update ERP_HOADONNPP set  ngaysua = '" + getDateTime() + "' , nguoisua ='" + this.userId + "'," +
					    " hinhthuctt= N'"+ this.hinhthuctt +"', ghichu= N'"+ this.ghichu +"' " +
					    " where pk_seq = '"+ this.id +"' and TrangThai!=1 " ;
				System.out.println("1.Update ERP_HOADONNPP1: " + query);
				if(db.updateReturnInt(query)!=1)
				{
					this.msg = "Hóa đơn đã chốt,vui lòng kiểm tra lại. ";
					db.getConnection().rollback();
					return false;
				}
			}
			else
			{
				String _KhGhiNo = this.KhGhiNo.length() <= 0 ? this.khId : this.KhGhiNo;
				
				String nhanvien_fk = "NULL";
				String khachhang_fk = "NULL";
				if(this.loaiXHD.equals("1") || this.loaiXHD.equals("2"))
					khachhang_fk = this.khId;
				else if(this.loaiXHD.equals("3"))
					nhanvien_fk = this.khId;
				
				double tongtien = 0;
				double tongtienck = 0;
				double tongvat = 0;
				double tongsauck = 0;
				double tongsauvat = 0;

				query = " update ERP_HOADONNPP set innguoimua= "+ this.innguoimua +", dondathangnpp_fk = "+ this.ddhId + ", ngayxuatHD = '" + this.ngayxuatHD + "' , ngaysua = '" + getDateTime() + "' , nguoisua ='" + this.userId + "'," +
					     " kyhieu = RTRIM(LTRIM('" + this.kyhieuhoadon + "')) , sohoadon= RTRIM(LTRIM('" + this.sohoadon + "')), hinhthuctt= N'"+ this.hinhthuctt +"' ," +
					     " chietkhau =  '"+ this.totalCHIETKHAU.replaceAll(",", "")  +"' , tongtienbvat= '"+ this.bvat.replaceAll(",", "") +"', tongtienavat='" + this.avat.replaceAll(",", "") + "', vat = '"+ this.thueVAT.replaceAll(",", "") + "', ghichu= N'"+ this.ghichu +"'," +
					     " loaixuathd= '"+ this.loaiXHD +"' , npp_fk = '"+ this.nppDangnhap +"', khachhang_fk= "+ khachhang_fk +", npp_dat_fk = "+ this.nppId +" , nhanvien_fk = " + nhanvien_fk + ", mavv='" +this.mavuviec+"', " + 
					     " TENXUATHD =  N'"+ this.donvimua +"', diachi = N'" + this.diachi + "', masothue = N'" + this.masothue + "', nguoimua = N'"+ this.nguoimuahang +"', " + 
					     " KHGHINO = " + _KhGhiNo + ", GHICHU2 = N'"+this.Ghichu2+"', "+
					     " ngayghinhan = '"+this.ngayxuatHD+"'"+
					     " where pk_seq = '"+ this.id +"'" ;
				 
				if(!db.update(query))
				{
					this.msg = "Không thể lưu dữ liệu. Vui lòng liên hệ với admin ";
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
				
				//KHO TỔNG KHÔNG THAY ĐỔI KHI XỬ LÝ HÓA ĐƠN
				query = "delete from ERP_HOADONNPP_SP where hoadon_fk = '"+ this.id +"' " ;	
				System.out.println(query);
				if(!db.update(query))
				{
					this.msg = "Không thể lưu dữ liệu. Vui lòng liên hệ với admin ";
					db.getConnection().rollback();
					return false;
				}
				
				boolean chungDVQL = false;
				if( !chungDVQL )
				{
					//TĂNG KHO CHI TIẾT CỦA HÓA ĐƠN CŨ LẠI TRƯỚC --> PHẢI TĂNG BẰNG TỔNG TRONG ĐƠN HÀNG CŨ MỚI ĐÚNG
					/*if(this.khKGId.trim().length() <= 0)
					{
						query =  "update khoCT  " + 
								 "	set khoCT.BOOKED = khoCT.BOOKED - DDH.SoLuong, " + 
								 "		khoCT.AVAILABLE = khoCT.AVAILABLE + DDH.SoLuong " + 
								 "from NHAPP_KHO_CHITIET khoCT inner join "+
								 "( "+
								 "	select hd_ct.kho_fk, hd.npp_fk, 100000 as nhomkenh_fk, "+
								 "			sp.PK_SEQ as sanpham_fk, SoLuong_Chuan as SoLuong, SOLO, NGAYHETHAN, sp.MA, sp.TEN "+
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
								 "	set khoCT.BOOKED = khoCT.BOOKED - DDH.SoLuong, " + 
								 "		khoCT.AVAILABLE = khoCT.AVAILABLE + DDH.SoLuong " + 
								 "from NHAPP_KHO_KYGUI_CHITIET khoCT inner join "+
								 "( "+
								 "	select hd_ct.kho_fk, hd.npp_fk, 100000 as nhomkenh_fk, "+
								 "			sp.PK_SEQ as sanpham_fk, SoLuong_Chuan as SoLuong, SOLO, NGAYHETHAN, sp.MA, sp.TEN "+
								 "	from ERP_HOADONNPP_SP_CHITIET hd_ct inner join ERP_HOADONNPP hd on hd_ct.hoadon_fk = hd.PK_SEQ "+
								 "			inner join SANPHAM sp on hd_ct.MA = sp.MA "+
								 "	where hoadon_fk = '" + this.id + "' "+
								 ") "+
								 "DDH on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
								 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan and khoCT.khachhang_fk = '" + this.khKGId + "' and khoCT.isNPP = '0' ";
					}*/
					
					
					if( this.donhangMUON.equals("0") )
					{
						if(this.khKGId.trim().length() <= 0)
						{
							query =  "update khoCT  " + 
									 "	set khoCT.BOOKED = khoCT.BOOKED - DDH.SoLuong, " + 
									 "		khoCT.AVAILABLE = khoCT.AVAILABLE + DDH.SoLuong " + 
									 "from NHAPP_KHO_CHITIET khoCT inner join  dbo.ufn_LayBookedConLai ( " + this.id + " ) DDH " + 
									 " 		on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
									 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan ";
						}
						else
						{
							query = "update khoCT  " + 
									 "	set khoCT.BOOKED = khoCT.BOOKED - DDH.SoLuong, " + 
									 "		khoCT.AVAILABLE = khoCT.AVAILABLE + DDH.SoLuong " + 
									 "from NHAPP_KHO_KYGUI_CHITIET khoCT inner join  dbo.ufn_LayBookedConLai ( " + this.id + " ) DDH " +
									 "		on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
									 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan and khoCT.khachhang_fk = '" + this.khKGId + "' and khoCT.isNPP = '0' ";
						}
					}
				}
				else //HÓA ĐƠN CỦA ĐƠN HÀNG MƯỢN KHÔNG ĐƯỢC CẬP NHẬT KHO
				{
					/*if( this.donhangMUON.equals("0") )
					{
						//TĂNG KHO CHI TIẾT CỦA HÓA ĐƠN CŨ LẠI TRƯỚC
						if(this.khKGId.trim().length() <= 0)
						{
							query =  "update khoCT  " + 
									 "	set khoCT.SOLUONG = khoCT.SOLUONG + DDH.SoLuong, " + 
									 "		khoCT.AVAILABLE = khoCT.AVAILABLE + DDH.SoLuong " + 
									 "from NHAPP_KHO_CHITIET khoCT inner join "+
									 "( "+
									 "	select hd_ct.kho_fk, hd.npp_fk, 100000 as nhomkenh_fk, "+
									 "			sp.PK_SEQ as sanpham_fk, SoLuong_Chuan as SoLuong, SOLO, NGAYHETHAN, sp.MA, sp.TEN "+
									 "	from ERP_HOADONNPP_SP_CHITIET hd_ct inner join ERP_HOADONNPP hd on hd_ct.hoadon_fk = hd.PK_SEQ "+
									 "			inner join SANPHAM sp on hd_ct.MA = sp.MA "+
									 "	where hoadon_fk = '" + this.id + "' "+
									 ") "+
									 "DDH on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
									 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan ";
									 //"where khoCT.AVAILABLE < DDH.SoLuong ";
						}
						else
						{
							query = "update khoCT  " + 
									 "	set khoCT.SOLUONG = khoCT.SOLUONG + DDH.SoLuong, " + 
									 "		khoCT.AVAILABLE = khoCT.AVAILABLE + DDH.SoLuong " + 
									 "from NHAPP_KHO_KYGUI_CHITIET khoCT inner join "+
									 "( "+
									 "	select hd_ct.kho_fk, hd.npp_fk, 100000 as nhomkenh_fk, "+
									 "			sp.PK_SEQ as sanpham_fk, SoLuong_Chuan as SoLuong, SOLO, NGAYHETHAN, sp.MA, sp.TEN "+
									 "	from ERP_HOADONNPP_SP_CHITIET hd_ct inner join ERP_HOADONNPP hd on hd_ct.hoadon_fk = hd.PK_SEQ "+
									 "			inner join SANPHAM sp on hd_ct.MA = sp.MA "+
									 "	where hoadon_fk = '" + this.id + "' "+
									 ") "+
									 "DDH on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
									 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan and khoCT.khachhang_fk = '" + this.khKGId + "' and khoCT.isNPP = '0' ";
									 //"where khoCT.AVAILABLE < DDH.SoLuong ";
						}
					}*/
				}
				
				if( this.donhangMUON.equals("0") )  //HÓA ĐƠN BT
				{
					System.out.println("::: CAP NHAT KHO TRUOC KHI XOA CHI TIET: " + query );
					if( db.updateReturnInt(query) < 1 )
					{
						this.msg = "Không thể lưu dữ liệu kho. Vui lòng liên hệ admin! ";
						db.getConnection().rollback();
						return false;
					}
					
									
					
					query = "delete from ERP_HOADONNPP_SP_ChiTiet where hoadon_fk = " + this.id;
					if(db.updateReturnInt(query)<=0)
					{
						msg = "Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
						db.getConnection().rollback();
						return false;
					}						
					
					query = "delete from ERP_HOADONNPP_DDH where hoadonnpp_fk = '"+ this.id +"' " ;
					if(!db.update(query))
					{
						this.msg = "Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
						db.getConnection().rollback();
						return false;
					}
					
					for(int i = 0; i < spId.length; i++)
					{
						if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
						{
							String thanhtien = spSoluong[i].replaceAll(",", "") + " * "+ spDongia[i].replaceAll(",", "");
							String chonin = "0";
							if( this.spChonin.contains( spMa[i] + ";" + spSCheme[i] ) )
								chonin = "1";
							
							query = "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme, vat, TIENVAT, CHONIN ) values " +
									" ('"+ this.id +"', '" + spId[i] + "', N'" + spTen[i] + "', N'" + spDonvi[i] + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spDongia[i].replaceAll(",", "") + "'," +
									" ("+ thanhtien +"), '"+ spChietkhau[i].replaceAll(",", "") +"', N'"+ spSCheme[i].replaceAll(",", "") +"','"+ spVat[i].replaceAll(",", "") +"', '" + spTienthue[i].replaceAll(",", "") + "', " + chonin + ")  ";
							
							System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
							if(!db.update(query))
							{
								this.msg = "Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
								db.getConnection().rollback();
								return false;
							}
							
							tongtien += Double.parseDouble(spDongia[i].replaceAll(",", "")) * Double.parseDouble(spSoluong[i].replaceAll(",", ""));
							tongtienck += Double.parseDouble(spChietkhau[i].replaceAll(",", ""));
							tongvat += ((Double.parseDouble(spDongia[i].replaceAll(",", "")) * Double.parseDouble(spSoluong[i].replaceAll(",", "")) - Double.parseDouble(spChietkhau[i].replaceAll(",", ""))) * (Double.parseDouble(spVat[i].replaceAll(",", "")) / 100));
						}
					}
					
					NumberFormat formatter = new DecimalFormat("#,###,###.##");
					tongsauck = tongtien - tongtienck;
					tongsauvat = tongsauck + tongvat;
					
					query = "		update C set SoLuong_Chuan=  "+
							"				case     when c.donvitinh = e.donvi then c.soluong  "+     
							"								  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  ,  "+ 
							"			DonGia_Chuan=	case     when c.donvitinh = e.donvi then c.dongia      "+
							"							  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from		DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  , "+
							"			DonVi_Chuan=e.DONVI  "+
							"		from ERP_HOADONNPP a         "+
							"			left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ      "+
							"			inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  "+ 
							"			inner join SANPHAM d on c.sanpham_fk = d.pk_seq    "+
							"			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq  "+      
							"		where a.pk_Seq = '" + this.id + "' ";
					
					System.out.println("1.1.UPDATE ERP_HOADONNPP_SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
						db.getConnection().rollback();
						return false;
					}		
					
					query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk) select '"+ this.id +"', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + this.ddhId + " )  ";
					if(!db.update(query))
					{
						this.msg ="Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
						db.getConnection().rollback();
						return false;
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
									
									if(key.startsWith( spMa[i] + "__" + spSCheme[i] ) )
									{
										String[] _sp = key.split("__");
										
										String _soluongCT = "0"; 
										if(this.sanpham_soluong.get(key) != null)
										{
											_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
										}
										
										if(Double.parseDouble(_soluongCT) > 0)
										{
											totalCT += Double.parseDouble(_soluongCT);
											System.out.println(":::: KEYS: " + key + " -- TOTAL CHI TIET: " + totalCT );
										
											// tên sản phẩm
											
											int inLot = 0;
											int inDate = 0;
											int allTensp = 0;
											String inTenSp = "";
											String inngayhethan = "";
																				
											String[] ngayhd = _sp[3].split("-");
											
											inTenSp = spTen[i];
											
											inTenSp = inTenSp.replace("....", " "+ ngayhd[1]+"/"+ngayhd[0] ); // DATE - HẠN SỬ DỤNG
											
											inTenSp = inTenSp.replace("...", " "+ _sp[2] ); // LOT - LÔ
											
											System.out.println("inTenSp:"+inTenSp);		
											
											String chonin = "0";
											if( this.spChonin.contains( spMa[i] + ";" + spSCheme[i] ) )
												chonin = "1";
											
											sqlHOADON_CHITIET  += " select '" + this.id + "' as hoadonId, '" + ddhId + "' as donhang_fk, a.pk_seq as sanphamId, '" + spMa[i] + "' as spMa, N'" + inTenSp + "' as spTen, N'" + spDonvi[i] + "' as donvi, " + 
																  " 	ISNULL( ( select dvdl_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' and sanpham_fk = a.pk_seq ), a.dvdl_fk ) as dvDATHANG, a.dvdl_fk as dvCHUAN, " + spDongia[i].replaceAll(",", "") + " as dongia, a.thuexuat, b.kho_fk, b.kbh_fk, N'" + _sp[2] + "' as solo, '" + _soluongCT.replaceAll(",", "") + "' as soluong, '" + _sp[3] + "' as NgayHetHan, N'" + spSCheme[i] + "' as scheme, " + chonin + " as chonIn " +
																  " from SANPHAM a, ERP_DONDATHANGNPP b " + 
																  " where a.MA = '" + spMa[i] + "' and b.pk_seq = '" + this.ddhId + "' ";
											
											sqlHOADON_CHITIET += " union ALL ";
										}
									}
								}
								
								System.out.println("sqlHOADON_CHITIET:"+sqlHOADON_CHITIET);
								//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
								if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
								{
									this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
									db.getConnection().rollback();
									return false;
								}
								else
								{
									/*if( sqlHOADON_CHITIET.trim().length() <= 0 )
									{
										this.msg = "Lỗi khi xác định số lô chèn vào hóa đơn. Vui lòng liên hệ kỹ thuật để được xử lý. ";
										db.getConnection().rollback();
										return false;
									}*/
									
									if(sqlHOADON_CHITIET.trim().length() > 0)
									{
										sqlHOADON_CHITIET = sqlHOADON_CHITIET.substring(0, sqlHOADON_CHITIET.length() - 10);
	
										query = " insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, sanpham_fk, ma, ten, donvi, dvDATHANG, dvCHUAN, dongia, thueVAT, Kho_FK, KBH_FK, SOLO, SOLUONG, NGAYHETHAN, SCHEME, CHONIN) " 
												+ sqlHOADON_CHITIET;
										
										System.out.println("::::CHEN HOA DON CHI TIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
											db.getConnection().rollback();
											return false;
										}
										
										query =  "update C set  "+
												 "	SoLuong_Chuan =  case     when c.DVCHUAN = c.DVDATHANG then c.soluong        "+
												 "						  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = d.PK_SEQ and DVDL2_FK = c.DVDATHANG and DVDL1_FK = d.DVDL_FK )   end  ,    "+
												 "	DonGia_Chuan =	case     when c.DVCHUAN = c.DVDATHANG then c.dongia       "+
												 "					  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = d.PK_SEQ and DVDL2_FK = c.DVDATHANG and DVDL1_FK = d.DVDL_FK )   end  ,  "+
												 "	SoLuong_DatHang = c.SOLUONG   "+
												 "from ERP_HOADONNPP a             "+
												 "	inner join ERP_HOADONNPP_SP_CHITIET c on a.pk_seq = c.hoadon_fk    "+
												 "	inner join SANPHAM d on c.MA = d.MA     "+
												 "	inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq         "+
												 "where a.pk_Seq = '" + this.id + "' ";
										
										if(!db.update(query))
										{
											this.msg = "Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
											db.getConnection().rollback();
											return false;
										}
									
									}
									
								}
							}	
						}
					}
					
					//CHECK TONG KHO CHI TIET PHAI BANG TONG TRONG KHO TONG
					/*query = "select count(*) as soDONG   " +
							"from ERP_DONDATHANGNPP_SANPHAM tong inner join   " +
							"	(  " +
							"		select sanpham_fk, sum(soluong) as soluong   " +
							"		from ERP_DONDATHANGNPP_SANPHAM_CHITIET  " +
							"		where  dondathang_fk = '" + this.ddhId + "' and  ltrim(rtrim(scheme)) = ''  and hoadon_fk = "+this.id+" " +
							"		group by sanpham_fk " +
							"	)  " +
							"	CT on tong.sanpham_fk = CT.sanpham_fk " +
							"where dondathang_fk = '" + this.ddhId + "' and tong.soluong != isnull(CT.soluong, 0) " ;
					
					System.out.println("[CheckTong_CT]: " + query);
					
					rsCHECK = db.get(query);
					int soDONG = 0;
					{
						if( rsCHECK.next() )
						{
							soDONG = rsCHECK.getInt("soDONG");
						}
						rsCHECK.close();
					}
					
					if(soDONG > 0)
					{
						db.getConnection().rollback();
						this.msg = "11.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý."+query;
						return false;
					}*/
					
					//CẬP NHẬT LẠI NHŨNG SẢN PHẨM CÓ THAY ĐỔI LÔ
					msg = this.Cap_Nhat_LenhXuatHang( ddhId, chungDVQL );
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return false;
					}
				}
				else
				{
					query = "delete from ERP_HOADONNPP_SP_ChiTiet where hoadon_fk = " + this.id;
					if(db.updateReturnInt(query)<=0)
					{
						msg = "Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
						db.getConnection().rollback();
						return false;
					}						
					
					query = "delete from ERP_HOADONNPP_DDH where hoadonnpp_fk = '"+ this.id +"' " ;
					if(!db.update(query))
					{
						this.msg = "Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
						db.getConnection().rollback();
						return false;
					}
					
					for(int i = 0; i < spId.length; i++)
					{
						if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
						{
							String thanhtien = spSoluong[i].replaceAll(",", "") + " * "+ spDongia[i].replaceAll(",", "");
							String chonin = "0";
							if( this.spChonin.contains( spMa[i] + ";" + spSCheme[i] ) )
								chonin = "1";
							
							query = "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme, vat, TIENVAT, CHONIN ) values " +
									" ('"+ this.id +"', '" + spId[i] + "', N'" + spTen[i] + "', N'" + spDonvi[i] + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spDongia[i].replaceAll(",", "") + "'," +
									" ("+ thanhtien +"), '"+ spChietkhau[i].replaceAll(",", "") +"', N'"+ spSCheme[i].replaceAll(",", "") +"','"+ spVat[i].replaceAll(",", "") +"', '" + spTienthue[i].replaceAll(",", "") + "', " + chonin + ")  ";
							
							System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
							if(!db.update(query))
							{
								this.msg = "Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
								db.getConnection().rollback();
								return false;
							}
							
							tongtien += Double.parseDouble(spDongia[i].replaceAll(",", "")) * Double.parseDouble(spSoluong[i].replaceAll(",", ""));
							tongtienck += Double.parseDouble(spChietkhau[i].replaceAll(",", ""));
							tongvat += ((Double.parseDouble(spDongia[i].replaceAll(",", "")) * Double.parseDouble(spSoluong[i].replaceAll(",", "")) - Double.parseDouble(spChietkhau[i].replaceAll(",", ""))) * (Double.parseDouble(spVat[i].replaceAll(",", "")) / 100));
						}
					}
					
					NumberFormat formatter = new DecimalFormat("#,###,###.##");
					tongsauck = tongtien - tongtienck;
					tongsauvat = tongsauck + tongvat;
					
					query = "		update C set SoLuong_Chuan=  "+
							"				case     when c.donvitinh = e.donvi then c.soluong  "+     
							"								  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  ,  "+ 
							"			DonGia_Chuan=	case     when c.donvitinh = e.donvi then c.dongia      "+
							"							  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from		DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  , "+
							"			DonVi_Chuan=e.DONVI  "+
							"		from ERP_HOADONNPP a         "+
							"			left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ      "+
							"			inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  "+ 
							"			inner join SANPHAM d on c.sanpham_fk = d.pk_seq    "+
							"			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq  "+      
							"		where a.pk_Seq = '" + this.id + "' ";
					
					System.out.println("1.1.UPDATE ERP_HOADONNPP_SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
						db.getConnection().rollback();
						return false;
					}		
					
					query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk) select '"+ this.id +"', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + this.ddhId + " )  ";
					if(!db.update(query))
					{
						this.msg ="Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
						db.getConnection().rollback();
						return false;
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
									
									if(key.startsWith( spMa[i] + "__" + spSCheme[i] ) )
									{
										String[] _sp = key.split("__");
										
										String _soluongCT = "0"; 
										if(this.sanpham_soluong.get(key) != null)
										{
											_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
										}
										
										if(Double.parseDouble(_soluongCT) > 0)
										{
											totalCT += Double.parseDouble(_soluongCT);
											System.out.println(":::: KEYS: " + key + " -- TOTAL CHI TIET: " + totalCT );
										
											// tên sản phẩm
											
											int inLot = 0;
											int inDate = 0;
											int allTensp = 0;
											String inTenSp = "";
											String inngayhethan = "";
																				
											String[] ngayhd = _sp[3].split("-");
											
											inTenSp = spTen[i];
											
											inTenSp = inTenSp.replace("....", " "+ ngayhd[1]+"/"+ngayhd[0] ); // DATE - HẠN SỬ DỤNG
											
											inTenSp = inTenSp.replace("...", " "+ _sp[2] ); // LOT - LÔ
											
											System.out.println("inTenSp:"+inTenSp);		
											
											String chonin = "0";
											if( this.spChonin.contains( spMa[i] + ";" + spSCheme[i] ) )
												chonin = "1";
											
											sqlHOADON_CHITIET  += " select '" + this.id + "' as hoadonId, '" + ddhId + "' as donhang_fk, a.pk_seq as sanphamId, '" + spMa[i] + "' as spMa, N'" + inTenSp + "' as spTen, N'" + spDonvi[i] + "' as donvi, " + 
																  " 	ISNULL( ( select dvdl_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' and sanpham_fk = a.pk_seq ), a.dvdl_fk ) as dvDATHANG, a.dvdl_fk as dvCHUAN, " + spDongia[i].replaceAll(",", "") + " as dongia, a.thuexuat, b.kho_fk, b.kbh_fk, N'" + _sp[2] + "' as solo, '" + _soluongCT.replaceAll(",", "") + "' as soluong, '" + _sp[3] + "' as NgayHetHan, N'" + spSCheme[i] + "' as scheme, " + chonin + " as chonIn " +
																  " from SANPHAM a, ERP_DONDATHANGNPP b " + 
																  " where a.MA = '" + spMa[i] + "' and b.pk_seq = '" + this.ddhId + "' ";
											
											sqlHOADON_CHITIET += " union ALL ";
										}
									}
								}
								
								System.out.println("sqlHOADON_CHITIET:"+sqlHOADON_CHITIET);
								//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
								if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
								{
									this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
									db.getConnection().rollback();
									return false;
								}
								else
								{
									/*if( sqlHOADON_CHITIET.trim().length() <= 0 )
									{
										this.msg = "Lỗi khi xác định số lô chèn vào hóa đơn. Vui lòng liên hệ kỹ thuật để được xử lý. ";
										db.getConnection().rollback();
										return false;
									}*/
									
									if(sqlHOADON_CHITIET.trim().length() > 0)
									{
										sqlHOADON_CHITIET = sqlHOADON_CHITIET.substring(0, sqlHOADON_CHITIET.length() - 10);
	
										query = " insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, sanpham_fk, ma, ten, donvi, dvDATHANG, dvCHUAN, dongia, thueVAT, Kho_FK, KBH_FK, SOLO, SOLUONG, NGAYHETHAN, SCHEME, CHONIN) " 
												+ sqlHOADON_CHITIET;
										
										System.out.println("::::CHEN HOA DON CHI TIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
											db.getConnection().rollback();
											return false;
										}
										
										query =  "update C set  "+
												 "	SoLuong_Chuan =  case     when c.DVCHUAN = c.DVDATHANG then c.soluong        "+
												 "						  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = d.PK_SEQ and DVDL2_FK = c.DVDATHANG and DVDL1_FK = d.DVDL_FK )   end  ,    "+
												 "	DonGia_Chuan =	case     when c.DVCHUAN = c.DVDATHANG then c.dongia       "+
												 "					  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = d.PK_SEQ and DVDL2_FK = c.DVDATHANG and DVDL1_FK = d.DVDL_FK )   end  ,  "+
												 "	SoLuong_DatHang = c.SOLUONG   "+
												 "from ERP_HOADONNPP a             "+
												 "	inner join ERP_HOADONNPP_SP_CHITIET c on a.pk_seq = c.hoadon_fk    "+
												 "	inner join SANPHAM d on c.MA = d.MA     "+
												 "	inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq         "+
												 "where a.pk_Seq = '" + this.id + "' ";
										
										if(!db.update(query))
										{
											this.msg = "Không thể lưu lại dữ liệu hóa đơn. Vui lòng liên hệ với admin!";
											db.getConnection().rollback();
											return false;
										}
									
									}
									
								}
							}	
						}
					}
					
					//CHECK TONG KHO CHI TIET PHAI BANG TONG TRONG KHO TONG
					/*query = "select count(*) as soDONG   " +
							"from ERP_DONDATHANGNPP_SANPHAM tong inner join   " +
							"	(  " +
							"		select sanpham_fk, sum(soluong) as soluong   " +
							"		from ERP_DONDATHANGNPP_SANPHAM_CHITIET  " +
							"		where  dondathang_fk = '" + this.ddhId + "' and  ltrim(rtrim(scheme)) = ''  and hoadon_fk = "+this.id+" " +
							"		group by sanpham_fk " +
							"	)  " +
							"	CT on tong.sanpham_fk = CT.sanpham_fk " +
							"where dondathang_fk = '" + this.ddhId + "' and tong.soluong != isnull(CT.soluong, 0) " ;
					
					System.out.println("[CheckTong_CT]: " + query);
					
					rsCHECK = db.get(query);
					int soDONG = 0;
					{
						if( rsCHECK.next() )
						{
							soDONG = rsCHECK.getInt("soDONG");
						}
						rsCHECK.close();
					}
					
					if(soDONG > 0)
					{
						db.getConnection().rollback();
						this.msg = "11.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý."+query;
						return false;
					}*/
					
					//CẬP NHẬT LẠI NHŨNG SẢN PHẨM CÓ THAY ĐỔI LÔ
					msg = this.Cap_Nhat_LenhXuatHang( ddhId, chungDVQL );
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return false;
					}
				}

				String msg1 = this.Cap_Nhat_KeToan_LenhXuatHang( ddhId,this.nppId );
				if( msg1.trim().length() > 0 )
				{
					this.msg=msg1;
					db.getConnection().rollback();
					return false;
				}
			}		
			
			//Trong trường hợp 1 khách hàng xuất hóa đơn với hình thức thanh toán là tiền mặt thì giới hạn không được xuất quá 20,000,000 VNĐ/ ngày
			if(this.hinhthuctt.equals("Tiền mặt")  )
			{
				query = "select isnull( SUM(tongtienavat), 0) as tongtien from ERP_HOADONNPP where NGAYXUATHD = '" + this.ngayxuatHD + "' and KHACHHANG_FK = '" + this.khId + "' and HINHTHUCTT = N'" + this.hinhthuctt + "' ";
				rs = db.get(query);
				double tongSOTIEN = 0;
				if(rs != null)
				{
					if(rs.next())
					{
						tongSOTIEN = rs.getDouble("tongtien");
					}
					rs.close();
				}
				
				if(tongSOTIEN > 20000000 )
				{
					this.msg = "Trong ngày " + this.ngayxuatHD + " với hình thức thanh toán tiền mặt, bạn chỉ có thể thanh toán tối đa thêm ( " + ( tongSOTIEN - 20000000 ) + " ) ";
					db.getConnection().rollback();
					return false;
				}
			}
			
			Utility util = new Utility();
			msg = util.Check_Kho_Tong_VS_KhoCT(npp_fk, db);
			if(msg.length() > 0)
			{
				db.getConnection().rollback();
				return false;
			}
			
			//Nếu không chọn thì tạo mới, nếu có chọn thì cập nhật
			if(this.khId != null)
			{
				if(this.khId.trim().length() > 0)
				{
					if( this.tthdId.trim().length() <= 0 )
					{
						query = "SELECT COUNT(*) as NUM FROM KHACHHANG_THONGTINHOADON " +
								"WHERE TENNGUOIMUA = N'" + this.nguoimuahang + "' AND DONVI = N'" + this.donvimua + "' " +
								"AND DIACHI = N'" + this.diachi + "' AND MASOTHUE = '" + this.masothue + "' " +
								"AND KHACHHANG_FK = " + this.khId;
						rs = this.db.get(query);
						rs.next();
						
						if(rs.getInt("NUM") == 0){
							
							query = "INSERT INTO KHACHHANG_THONGTINHOADON(TENNGUOIMUA, DONVI, DIACHI, MASOTHUE, KHACHHANG_FK, ACTIVE) VALUES( " +
									" N'" + this.nguoimuahang + "', N'" + this.donvimua + "', N'" + this.diachi + "', N'" + this.masothue + "', " + this.khId + ", '1' )";
							
							System.out.println(":::Không thể cập nhật thông tin xuất hóa đơn. Vui lòng liên hệ admin!");
							this.db.update(query);
						}
					}
					else
					{
						query = "UPDATE KHACHHANG_THONGTINHOADON SET ACTIVE = '0' WHERE KHACHHANG_FK = " + this.khId + " " ;
						this.db.update(query);
						
						query = "UPDATE KHACHHANG_THONGTINHOADON SET ACTIVE = '1', TENNGUOIMUA = N'" + this.nguoimuahang + "', DONVI = N'" + this.donvimua + "', DIACHI = N'" + this.diachi + "', MASOTHUE = N'" + this.masothue + "' WHERE PK_SEQ = " + this.tthdId + " " ;
						System.out.println(":::Không thể cập nhật thông tin xuất hóa đơn. Vui lòng liên hệ admin!");
						this.db.update(query);
					}
				}
			
			}
			
			if(this.nppId != null)
			{
				if(this.nppId.trim().length() > 0)
				{
					if( this.tthdId.trim().length() <= 0 )
					{
						query = "SELECT COUNT(*) as NUM FROM NPP_THONGTINHOADON " +
								"WHERE TENNGUOIMUA = N'" + this.nguoimuahang + "' AND DONVI = N'" + this.donvimua + "' " +
								"AND DIACHI = N'" + this.diachi + "' AND MASOTHUE = '" + this.masothue + "' " +
								"AND NPP_FK = " + this.nppId;
						rs = this.db.get(query);
						rs.next();
						
						if(rs.getInt("NUM") == 0){
							
							query = "INSERT INTO NPP_THONGTINHOADON(TENNGUOIMUA, DONVI, DIACHI, MASOTHUE, NPP_FK, ACTIVE) VALUES( " +
									" N'" + this.nguoimuahang + "', N'" + this.donvimua + "', N'" + this.diachi + "', N'" + this.masothue + "', " + this.nppId + ", '1' )";
							
							System.out.println(":::Không thể cập nhật thông tin xuất hóa đơn. Vui lòng liên hệ admin!");
							this.db.update(query);
						}
					}
					else
					{
						query = "UPDATE NPP_THONGTINHOADON SET ACTIVE = '0' WHERE NPP_FK = " + this.nppId + " " ;
						this.db.update(query);
						
						query = "UPDATE NPP_THONGTINHOADON SET ACTIVE = '1', TENNGUOIMUA = N'" + this.nguoimuahang + "', DONVI = N'" + this.donvimua + "', DIACHI = N'" + this.diachi + "', MASOTHUE = N'" + this.masothue + "' WHERE PK_SEQ = " + this.tthdId + " " ;
						System.out.println(":::Không thể cập nhật thông tin xuất hóa đơn. Vui lòng liên hệ admin!");
						this.db.update(query);
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			util.CapNhat_ThanhTien_HoaDon(db, this.id);
			
			//CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[] { this.id } );
			
			//Đơn hàng nếu có thay đổi số lô
			db.execProceduce2("CapNhatTooltip", new String[] { ddhId } );
			
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

	private boolean Revert_KeToan_Donhang(String loaichungtu, String sochungtu )
	{
		try 
		{
			
		//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
		String query =  " select NGAYGHINHAN,SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
					    " from ERP_PHATSINHKETOAN " +
					    " where LOAICHUNGTU in ('XK01','XK02') and SOCHUNGTU = '" + sochungtu + "' ";
		
		 
		ResultSet rsPSKT = db.get(query);
			while(rsPSKT.next())
			{
				String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
				String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
				double NO = rsPSKT.getDouble("NO");
				double CO = rsPSKT.getDouble("CO");
				double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
				String Ngayghinhan=  rsPSKT.getString("NGAYGHINHAN");
				String nam=Ngayghinhan.substring(0,4);
				String thang=Ngayghinhan.substring(5,7);
				
				//NEU LA CO THI BAY GIO GHI GIAM CO LAI
				if( NO > 0 )
				{
					query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
							" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
				}
				else
				{
					query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
							" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
				}
				
				//System.out.println("1.REVERT NO-CO: " + query);
				
				if(db.updateReturnInt(query)<0)
				{
					this.msg = "KHÔNG THỂ REVERT KẾ TOÁN. YÊU CẦU LIÊN HỆ LẬP TRÌNH ";
					return false;
				}
				
			}
			rsPSKT.close();
			 
			
			//HỦY KẾ TOÁN ĐÃ GHI NHẬN
			query = " DELETE ERP_PHATSINHKETOAN WHERE LOAICHUNGTU in ('XK01','XK02') and SOCHUNGTU = '"+sochungtu+"'";	
			//System.out.println("1.CHECK NO-CO: " + query);
			
			if(!db.update(query))
			{
				msg = "Không thể hủy ERP_PHATSINHKETOAN " + query;
				return false;
			}			
			return true;
			
		} 
		catch (Exception e) {
			this.msg= "103. Vui lòng báo với admin để trợ giúp :"+e.getMessage();
			
			e.printStackTrace();
			return false;
			
		}
	}
 
	private String Cap_Nhat_KeToan_LenhXuatHang(String iddonhang, String nppId2) {
		// TODO Auto-generated method stub
		try{
			
			String query="SELECT ISNULL(donhangMUON,'0') AS DONHANGMUON  FROM ERP_DONDATHANGNPP WHERE PK_SEQ="+iddonhang;
			ResultSet rs=db.get(query);
			
			String isdonhangmuon="0";
			
			if(rs.next()){
				isdonhangmuon=rs.getString("DONHANGMUON");
			}else{
				return "Bị lỗi trong quá trình cập nhật, vui lòng thử lại, không được vui lòng báo Admin để được xử lý .Lỗi: 09013";
			}
			rs.close();
			
			 if(!isdonhangmuon.equals("1")){
			// chua lam trường hợp revert của đơn hàng mượn 
				if(!Revert_KeToan_Donhang("",iddonhang)){
					return this.msg;
				}
				   query="select npp_fk from ERP_DONDATHANGNPP where PK_SEQ= "+iddonhang;
				   rs=db.get(query);
					if(rs.next()){
						nppId2=rs.getString("npp_fk");
					}
					rs.close();
			
				String msg1= CapNhatKeToan_Kho_DONHANG(db,iddonhang,nppId2);
				if(msg1.length() >0){
	
					return msg1;
				}
			 }
			return "";
		}catch(Exception er){
			return "Vui lòng báo với Admin để được trợ giúp: "+er.getMessage();
		}
		
	}

	private String CapNhatKeToan_Kho_DONHANG(dbutils db, String id,String nppId) {
	 
		try{
			
			
			Utility util=new Utility();
			Utility_Kho util_kho=new Utility_Kho();
			
			String query = 	 " SELECT KBH_FK,Kho_FK ,ISNULL(YC.DONHANGMUON,'0') AS DONHANGMUON , YC.NgayDonHang AS NGAYYEUCAU, YC_CT.SANPHAM_FK, "+ 
							 " SUM(isnull(YC_CT.soluongCHUAN,0)) AS TONGXUAT,YC_CT.SOLO , YC_CT.ngayhethan , "+ 
							 " CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN "+ 
							 " 'XK01' "+ 
							 " ELSE "+ 
							 " 'XK02' END AS MAXUATKHO, "+ 
							 " CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 AND YC.KHO_FK <> 100008  THEN "+ 
							 " ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63211000' "+ 
							 " AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = "+nppId+")) "+ 
							 " ELSE   "+ 
							 " (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63220000' "+ 
							 " AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = "+nppId+" )) "+ 
							 " END AS TAIKHOANNO_FK,   "+ 
							 " "+ 
							 " (  SELECT TK.PK_SEQ FROM ERP_LOAISANPHAM LSP "+ 
							 " INNER JOIN SANPHAM SP ON SP.LOAISANPHAM_FK=LSP.PK_SEQ "+ 
							 " INNER JOIN ERP_TAIKHOANKT TK ON TK.SOHIEUTAIKHOAN=LSP.TAIKHOANKT_FK "+ 
							 " AND TK.CONGTY_FK =((SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = "+nppId+" )) WHERE SP.PK_SEQ=SANPHAM_FK )  AS TAIKHOANCO_FK, "+ 
							 " "+ 
							 " CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN "+ 
							 " '100002' "+ 
							 " ELSE "+ 
							 " '100008' END AS NOIDUNGXUAT_FK, "+ 
							 " CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN "+ 
							 " N'Xuất bán hàng (theo đơn hàng bán) - Khác kho ký gửi' "+ 
							 " ELSE "+ 
							 " N'Xuất khuyến mại - Khác kho ký gửi' END AS KHOANMUC ,"+ 
							 " (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = "+nppId+" ) as congty_fk , SP.MA_FAST MASP, SP.TEN TENSP, DV.DIENGIAI DVT	"+ 
							 " FROM ERP_DONDATHANGNPP YC "+ 
							 " INNER JOIN ERP_DONDATHANGNPP_SANPHAM_CHITIET YC_CT ON YC.PK_SEQ = YC_CT.dondathang_fk "+ 
							 " INNER JOIN SANPHAM SP ON YC_CT.SANPHAM_FK = SP.PK_SEQ "+
							 " INNER JOIN DONVIDOLUONG DV ON YC_CT.DVDL_FK = DV.PK_SEQ "+
							 " WHERE YC.PK_SEQ ="+id +
							 " GROUP BY NgayDonHang, YC_CT.SANPHAM_FK, YC.Kho_FK, YC.NHOMKENH_FK, YC_CT.SCHEME , " +
							 " YC_CT.SOLO,YC.DONHANGMUON, SP.MA_FAST , SP.TEN , YC_CT.soluong , DV.DIENGIAI ,YC_CT.ngayhethan ,KBH_FK,Kho_FK ";

						System.out.println("Định khoản: " + query);
						ResultSet rs = db.get(query);
						int dem=0;
						while(rs.next()){
							dem++;
							String ngayhethan= rs.getString("ngayhethan");
							String ngaychungtu = rs.getString("NGAYYEUCAU");
							String nam = ngaychungtu.substring(0, 4);
							String thang = "" + (Integer.parseInt(ngaychungtu.substring(5, 7)));
							String ngayghinhan = ngaychungtu;
							String loaichungtu = rs.getString("MAXUATKHO");
							String sochungtu = id;
							String taikhoanNO_fk =  rs.getString("TAIKHOANNO_FK");
							String taikhoanCO_fk =  rs.getString("TAIKHOANCO_FK");
							String NOIDUNGXUAT_FK = rs.getString("NOIDUNGXUAT_FK");
							
							String masp = rs.getString("MASP");
							String tensp = rs.getString("TENSP");
							String dvt = rs.getString("DVT");
							
							
							String congty_fk= rs.getString("congty_fk");
							
							String  SANPHAM_FK= rs.getString("SANPHAM_FK");
							String solo=rs.getString("SOLO");
							String DONHANGMUON=rs.getString("DONHANGMUON");
							double DONGIA=0;
							
							String KBH_FK_ = rs.getString("KBH_FK");
							String KHO_FK_ = rs.getString("Kho_FK");
							
							
							String str[] = util_kho.getGiaChayKT(ngaychungtu, db, congty_fk , nppId, SANPHAM_FK, solo);
							if(str[1].length() >0){
					
								msg = "Không thể cập nhật lỗi :  " + str[1];
								//db.getConnection().rollback();
								return msg;
							}else{
								DONGIA=Double.parseDouble(str[0]);
							}
							
							query="UPDATE  ERP_DONDATHANGNPP_SANPHAM_CHITIET SET GIACHAYKT ="+DONGIA+",taikhoanktNo="+taikhoanNO_fk+",taikhoanktCO="+taikhoanCO_fk+"  WHERE SOLO='"+solo+"' AND SANPHAM_FK= "+SANPHAM_FK+" AND DONDATHANG_FK= "+id;
							if(!db.update(query)){
								return "Không thể cập nhật dòng lệnh ,vui lòng kiểm tra lại"+query;
							}
							NumberFormat formater = new DecimalFormat("########.00");
							String NO =   formater.format((rs.getDouble("TONGXUAT")* DONGIA));
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
							
							String TIENTEGOC_FK = "100000";
							String DONGIANT = "0";
							String TIGIA_FK = "1";
							String TONGGIATRI = NO;
							String TONGGIATRINT = TONGGIATRI;
							String khoanmuc = rs.getString("KHOANMUC");
							
							 
							
							// chạy kế toán cho đơn hàng mượn
							String msg1 = util.Update_TaiKhoan_DienGiai_SP_TheoLo(db, thang, nam, ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoanNO_fk, taikhoanCO_fk, 
									NOIDUNGXUAT_FK, NO, CO, 
									DOITUONG_NO, MADOITUONG_NO, DOITUONG_CO, MADOITUONG_CO, LOAIDOITUONG, 
									SOLUONG,  formater.format(DONGIA), 
									TIENTEGOC_FK,DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, "",khoanmuc, sochungtu, masp, tensp, dvt,solo,ngayhethan,KBH_FK_,KHO_FK_,"NULL");
							 
							if(msg1.length()>0){
								return msg1;
							}
							 
			}
			
			if(dem==0){
				return  "Không cập nhật được khoản mục kế toán , vui lòng báo admin để được xử lý .";
				//db.getConnection().rollback();
			 
			}
	
			
		}catch(Exception er){
			er.printStackTrace();
			return "1003: Vui lòng báo với admin để được trợ giúp : "+ er.getMessage();
		}
		return "";
	}

	private String Cap_Nhat_LenhXuatHang( String ddhId, boolean chungDVQL ) 
	{
		String msg = "";
		String query = "";
		
		//Cập nhật lại số lô cho lệnh xuất hàng khi thay dổi số lô trong lúc xuất hóa đơn, lượng chênh lệch sẽ tạo ra hóa đơn nằm chờ,
		//// lúc tạo mới hóa đơn, chỉ lấy lượng hóa đơn nằm chờ để xử lý tiếp
		
		////CHECK SỐ LÔ MỚI CÓ ĐỦ HÀNG KHÔNG TRƯỚC
		/*if(this.khKGId.trim().length() <= 0)
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
		}*/
		
		if(this.khKGId.trim().length() <= 0)
		{
			query =  "select DDH.MA, DDH.TEN, khoCT.AVAILABLE, DDH.SoLuong, DDH.solo  "+
					 "from NHAPP_KHO_CHITIET khoCT inner join dbo.ufn_LayBookedSuDung ( " + this.id + " ) DDH "+
					 "		on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
					 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan "+
					 "where khoCT.AVAILABLE < DDH.SoLuong ";
		}
		else
		{
			query = "select DDH.MA, DDH.TEN, khoCT.AVAILABLE, DDH.SoLuong, DDH.solo  "+
					 "from NHAPP_KHO_KYGUI_CHITIET khoCT inner join dbo.ufn_LayBookedSuDung ( " + this.id + " ) DDH "+
					 "		 on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
					 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan and khoCT.khachhang_fk = '" + this.khKGId + "' and khoCT.isNPP = '0' "+
					 "where khoCT.AVAILABLE < DDH.SoLuong ";
		}
		
		System.out.println("4.0 CHECK TON KHO: " + query);
		ResultSet rsTK = db.get(query);
		if( rsTK != null )
		{
			try 
			{
				while(rsTK.next())
				{
					msg += "Mã hàng: " + rsTK.getString("TEN") + " với số lô " + rsTK.getString("SOLO") + " chỉ còn tối đa " + rsTK.getString("SoLuong") + "\n ";
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
		if( !chungDVQL )
		{
			if(this.khKGId.trim().length() <= 0)
			{
				query =  "update khoCT  " + 
						 "	set khoCT.BOOKED = khoCT.BOOKED + DDH.SoLuong, " + 
						 "		khoCT.AVAILABLE = khoCT.AVAILABLE - DDH.SoLuong " + 
						 "from NHAPP_KHO_CHITIET khoCT inner join dbo.ufn_LayBookedSuDung ( " + this.id + " ) DDH "+
						 "	 on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
						 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan ";
			}
			else
			{
				query = "update khoCT  " + 
						 "	set khoCT.BOOKED = khoCT.BOOKED + DDH.SoLuong, " + 
						 "		khoCT.AVAILABLE = khoCT.AVAILABLE - DDH.SoLuong " + 
						 "from NHAPP_KHO_KYGUI_CHITIET khoCT inner join dbo.ufn_LayBookedSuDung ( " + this.id + " ) DDH " +
						 "	 on khoCT.NPP_FK = DDH.npp_fk and khoCT.KHO_FK = DDH.Kho_FK and khoCT.nhomkenh_fk = DDH.nhomkenh_fk and khoCT.SANPHAM_FK = DDH.SanPham_fk  "+
						 "					 and khoCT.SOLO = DDH.SoLo and khoCT.NGAYHETHAN = DDH.NgayHetHan and khoCT.khachhang_fk = '" + this.khKGId + "' and khoCT.isNPP = '0' ";
			}
		}
		else
		{
			/*if(this.khKGId.trim().length() <= 0)
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
			}*/
		}
		
		System.out.println("4.1. CAP NHAT TON KHO: " + query);
		if(!db.update(query))
		{
			msg = "Lỗi khi duyệt: " + query;
			return msg;
		}
		
		//CHỈ HÓA ĐƠN CỦA ĐƠN HÀNG BÌNH THƯỜNG MỚI VÀO ĐÂY
		if( chungDVQL )
		{
			msg = "Lỗi nghiệp vụ, hóa đơn của đơn hàng mượn không thể chỉnh sửa thông tin kho.";
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
		
		//TEST
		/*if( msg.trim().length() <= 0 )
			return "";*/
		
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
				 "			inner join SANPHAM sp on ddh.SPMA = sp.MA "+
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
					
					System.out.println("4.3.1 SAN PHAM: " + sanpham_fk + " -- SCHEME: " + scheme + " -- SOLUONG CHUA XUAT: " + soluongCHUAXUAT);
					
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
						msg = this.tudongDEXUAT_SOLO( nppId, khoId, nhomkenh_fk, ddhId, sanpham_fk, soluong, soluongCHUAXUAT , loai, scheme, chungDVQL );
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
				 "set a.soluongCHUAN = a.soluong * ISNULL( dbo.LayQuyCach( a.SANPHAM_FK, null, a.DVDL_FK ), 0 ), "+
				 "	  a.dvdl_fk = case when a.dvdl_fk is null then b.dvdl_fk else a.dvdl_fk end	" +
				 "from ERP_DONDATHANGNPP_SANPHAM_CHITIET a left join SANPHAM b on a.sanpham_fk = b.pk_seq "+
				 "where a.dondathang_fk = '" + ddhId + "' ";
		if( !db.update(query) )
		{
			msg = "Loi cap nhat ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query;
			return msg;
		}	

		msg= this.check_tonkhobi_am(ddhId);
		if(msg.length() > 0)
		{
			return msg;
		}
		
		return msg;
	}

	private String check_tonkhobi_am(String ddhId) {
		
	
		Utility_Kho util_kho=new Utility_Kho();
		String msg="";
		try
		{
		// TODO Auto-generated method stub
		String query=" SELECT  A.NGAYDONHANG,B.SANPHAM_FK, A.KHO_FK,A.NPP_FK, A.NHOMKENH_FK, solo ,   SUM(B.soluongCHUAN) AS TONGXUAT "+   
		"		 FROM ERP_DONDATHANGNPP A  "+
		" 		INNER JOIN ERP_DONDATHANGNPP_SANPHAM_CHITIET B ON A.PK_SEQ = B.DONDATHANG_FK "+  
		" 		WHERE   A.TRANGTHAI=4 and a.pk_seq= " +ddhId+
		" 		and NPP_DACHOT=1  "+
		" 		  "+
		" 		GROUP BY B.SANPHAM_FK, A.KHO_FK,A.NPP_FK, A.NHOMKENH_FK, solo, A.NGAYDONHANG  ";
		
		ResultSet rs=db.get(query);
		while(rs.next())
		{
			String sanpham_fk= rs.getString("SANPHAM_FK");
			//String ngaydh= rs.getString("NGAYDONHANG");
			String kho_fk= rs.getString("KHO_FK");
			String NPP_FK= rs.getString("NPP_FK");
			String NHOMKENH_FK= rs.getString("NHOMKENH_FK");
			String solo= rs.getString("solo");
			
			String ngaydh = this.getDateTime();
			msg=util_kho.Check_NPP_Kho_Sp_Chitiet_Nhohon_0(ngaydh, "Đơn đặt hàng", db, kho_fk, sanpham_fk, NPP_FK, NHOMKENH_FK, solo);
			if(msg.length()>0)
			{
				return msg;
			}
		}
		rs.close();
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Lỗi khi duyệt: " + e.getMessage();
		}
		return msg;
	}

	private String tudongDEXUAT_SOLO(String nppID, String khoId, String nhomkenh_fk, String ddhId, String spId, 
				double soluongDATHANG, double soluong, String loai, String scheme, boolean chungDVQL )
	{
		System.out.println("::::: SAN PHAM: " + spId + " -- SO LUONG CAN DE XUAT: " + soluong + " -- SO LUOGN DAT HANG: " + soluongDATHANG );
		String msg = "";
		String query = "";
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		try
		{
			//TU DE XUAT LO --> KHO CHI TIET THI VAN TRU SO LUONG + AVAI
			if(this.khKGId.trim().length() <= 0 )
			{
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
				query = "select distinct kho.AVAILABLE, kho.SOLO, kho.NGAYHETHAN, ISNULL( hdOLD.stt, 100 ) as stt  "+
						 "from NHAPP_KHO_KYGUI_CHITIET kho left join "+
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
					//Bước này phải xử lý trường hợp giảm lô đang có, khi đó chỉ thay đổi booked và số lượng nếu lượng đề xuất vượt quá lượng đang booked
					if(soluongXUAT >= 1)
					{
						//CAP NHAT KHO CHI TIET
						if( !chungDVQL )
						{
							if(this.khKGId.trim().length() <= 0 )
							{
								query = "UPDATE NHAPP_KHO_CHITIET  set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', BOOKED = BOOKED + '" + soluongXUAT + "' " +
										"where kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and SOLO = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
							}
							else
							{
								query = "UPDATE NHAPP_KHO_KYGUI_CHITIET  set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', BOOKED = BOOKED + '" + soluongXUAT + "' " +
										"where kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and khachhang_fk = '" + this.khKGId + "' and isNPP = '0' and SOLO = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
							}
						}
						else //hóa đơn hàng mượn không được vào đây
						{
							/*if(this.khKGId.trim().length() <= 0 )
							{
								query = "UPDATE NHAPP_KHO_CHITIET  set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
										"where kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and SOLO = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
							}
							else
							{
								query = "UPDATE NHAPP_KHO_KYGUI_CHITIET  set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
										"where kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and khachhang_fk = '" + this.khKGId + "' and isNPP = '0' and SOLO = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
							}*/
						}
						
						int kq = db.updateReturnInt(query);
						System.out.println("---CAP NHAT TON KHO TU DE XUAT: " + query + " -- KQ: " + kq);
						if(kq != 1 )
						{
							return "5.1.Lỗi khi chốt xuất kho: " + query;
						}
						
						//INSERT DONHANG - CHI TIET
						query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET_TEMP( dondathang_fk, SANPHAM_FK, dvdl_fk, solo, soluong, soluongCHUAN, ngayhethan, loai, scheme, bosungTHEM )  " +
								"select '" + ddhId + "', pk_seq, ( select dvdl_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' and sanpham_fk = a.pk_seq ),  N'" + solo + "' as solo, " + 
								" 		" + soluongXUAT + " * dbo.LayQuyCach_DVBan( a.pk_seq, null, ( select dvdl_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' and sanpham_fk = a.pk_seq ) ) as soluong,  '" + soluongXUAT + "', '" + ngayhethan + "' as NgayHetHan, '" + loai + "' as loai, N'" + scheme + "', 1 as bosungTHEM   " +
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
		this.getNppInfo();
		
		
		if( vitribam.equals("1") )
		{
			if( !this.update() )
			{
				return false;
			}
		}
		
		try
		{			
			// KIEM TRA THONG TIN
			
			String query =  " select isnull(TENXUATHD, '' ) TENXUATHD, isnull(diachi, '') diachi, isnull(masothue, '') masothue, nguoimua, nguoitao, sohoadon, kyhieu, trangthai " + 
							" from ERP_HOADONNPP WHERE PK_SEQ = "+this.id ;
			System.out.println(query);
			ResultSet ktrahd = db.get(query);
			String tenxuathd = "";
			String diachi = "";
			String masothue = "";
			String nguoimua = "";
			String nguoitao = "";
			String SHD = "";
			String trangthai = "";
			
			if(ktrahd!=null)
			{
				while(ktrahd.next())
				{
					tenxuathd = ktrahd.getString("TENXUATHD");
					diachi = ktrahd.getString("diachi");
					masothue = ktrahd.getString("masothue");
					nguoitao = ktrahd.getString("nguoitao");
					SHD =  ktrahd.getString("sohoadon");
					trangthai = ktrahd.getString("trangthai");
				}
			}
			
			if( !( trangthai.equals("1") || trangthai.equals("0") ) )
			{
				msg = "Trạng thái hóa đơn không hợp lệ. Vui lòng liên hệ Admin để được xử lý.";
				return false;
			}
			
			/*if(!nguoitao.equals(this.userId))
			{
				this.msg = "Bạn chỉ được chốt hóa đơn mà bạn tạo.";
				return false;
			}
			*/
			if(tenxuathd.trim().length() <=0 || diachi.trim().length() <=0 || masothue.trim().length() <=0 )
			{
				msg = "Hóa đơn này chưa đầy đủ thông tin xuất hóa đơn. Vui lòng điền đủ thông tin !!!";
				return false;
			}
			
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
			
			query = "";
			
			
			if( vitribam.equals("0") )
			{
				query ="select ddh.NgayDonHang, DATEDIFF(dd, ddh.NgayDonHang, hd.NGAYXUATHD) as chenhLECH " +
					   "from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_ddh on hd.PK_SEQ = hd_ddh.HOADONNPP_FK  " +
					   "inner join ERP_DONDATHANGNPP ddh on hd_ddh.DDH_FK = ddh.PK_SEQ " +
					   "where hd.PK_SEQ = '" + this.id + "' ";
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					if(rs.next())
					{
						if(rs.getInt("chenhLECH") < 0 )
						{
							this.msg = "Ngày xuất hóa đơn ( " + this.ngayxuatHD + " ) không được nhỏ hơn ngày đơn hàng ( " + rs.getString("NgayDonHang") + " ) ";
							rs.close();
							return false;
						}
					}
					rs.close();
				}
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
				   "         from ERP_XUATHOADONKM hd               "+
				   "         where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
				   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
				   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) isSd_OTC, \n" +
				   "        (select count(hd.pk_seq) as dem  "+
				   "         from ERP_HOADONNPP hd               "+
				   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
				   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
				   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) isSd_ETC, \n" +
				   "        (select count(hd.pk_seq) as dem  "+
				   "         from ERP_HOADONNPP hd               "+
				   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
				   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
				   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND HD.PK_SEQ = "+this.id+" AND ISNUMERIC(SOHOADON) = 1 ) isSHDkhaibao, \n" +
				   "        (select count(hd.pk_seq) as dem  "+
				   "         from ERP_HOADONNPP hd               "+
				   "         where hd.trangthai IN ( 1 ) and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1') and ISNUMERIC(SOHOADON)= 1   "+
				   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
				   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND HD.PK_SEQ != "+this.id+"" +
				   "			   and cast(hd.SOHOADON as int) <= cast((select SOHOADON from ERP_HOADONNPP WHERE PK_SEQ = "+this.id+") as int)  ) isSHDbeChuaChot, \n" +
				   " 		( select SOHOADON from ERP_HOADONNPP where pk_seq = "+this.id+") sohoadon \n"+
				   " FROM NHANVIEN_SOHOADON NV  \n" +
			       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppDangnhap + "' \n";
			System.out.println("Câu check khai báo SHD "+query);
			ResultSet rsLayDL = db.get(query);
			
			int check_OTC = 0;
			int check_ETC = 0;
			int isSHDkhaibao = 0;
			int isSHDbeChuaChot = 0;
			String shd = "";
								
			while(rsLayDL.next())
			{
				kyhieuhoadon= rsLayDL.getString("kyhieu");
				sohoadontu = rsLayDL.getString("sohoadontu").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadontu") ;
				sohoadonden = rsLayDL.getString("sohoadonden").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadonden") ;;
				ngayhoadon = rsLayDL.getString("ngayhoadon");
				check_OTC = rsLayDL.getInt("isSd_OTC");
				check_ETC = rsLayDL.getInt("isSd_ETC");
				isSHDkhaibao = rsLayDL.getInt("isSHDkhaibao");
				isSHDbeChuaChot = rsLayDL.getInt("isSHDbeChuaChot");
				shd = rsLayDL.getString("SOHOADON");
			}
			rsLayDL.close();
			
			if(kyhieuhoadon.equals("-1") || sohoadontu == -1 || sohoadonden == -1 )
			{
				msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
				return false;
			}
						
			if(check_OTC <= 0 && check_ETC <= 0)
			{
				chuoi = ("000000" + sohoadontu);
				chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
			}
			else
			{
				
				if(isSHDkhaibao == 1)
				{
					chuoi = shd;
				}
				else
				{				
					// LAY SOIN MAX TRONG OTC && ETC
					query= " SELECT  \n"+
						   "       (select max(cast(sohoadon as float)) as soin_max  "+
						   "        from ERP_XUATHOADONKM hd               "+
						   "        where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) soinMAX_OTC, \n" +
						   "       (select max(cast(sohoadon as float)) as soin_max "+
						   "        from ERP_HOADONNPP hd               "+
						   "        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ and hd.pk_seq != "+this.id+" AND ISNUMERIC(SOHOADON) = 1 ) soinMAX_ETC  \n" +
						   " FROM NHANVIEN_SOHOADON NV  \n" +
					       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppDangnhap + "' \n";
									
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
			}
			
			
			if(Integer.parseInt(chuoi) > sohoadonden )
			{ 
					//chuoi = "NA";
				msg = "Số hóa đơn tiếp theo đã vượt quá Số hóa đơn được khai báo trong dữ liệu nền. Vui lòng vào dữ liệu nền số hóa đơn khai báo lại ! ";
				return false;
			}
			
			 sohoadon = chuoi ;
			
		    // KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
			query = " select ( select count(*) from ERP_XUATHOADONKM where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '2' and npp_fk = '" + this.nppDangnhap + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) = 1  ) as KtraOTC, \n" +
					"        ( select count(*) from ERP_HOADONNPP where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + this.nppDangnhap + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" and pk_seq != "+this.id+" AND ISNUMERIC(SOHOADON) = 1  ) as KtraETC, \n" +
					"        ( select count(*) from ERP_HOADONNPP where cast( sohoadon as float ) < cast( ISNULL( '"+ sohoadon +"', -1 ) as float) and NGAYXUATHD > '"+ngayhoadon+"' and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" and pk_seq != "+this.id+" AND ISNUMERIC(SOHOADON) = 1 ) as is_KtraOTC_dk, \n"+
					"        ( select count(*) from ERP_XUATHOADONKM where cast( sohoadon as float ) < cast( ISNULL( '"+ sohoadon +"', -1 ) as float) and NGAYHOADON > '"+ngayhoadon+"' and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) = 1 ) as is_KtraETC_dk, \n"+
					"        ( select count(*) from ERP_HOADONNPP hd \n"+
					"          where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
					"              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
					"              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ and hd.pk_seq != "+this.id+" AND ISNUMERIC(SOHOADON) = 1 " +
					"			   and cast( sohoadon as float ) < cast( ISNULL( "+ sohoadon +", -1 ) as float) ) CHECK_SHD_BE \n" +					   		
					" from NHANVIEN NV where pk_seq = '" + userId + "' ";
			
			System.out.println("Câu kiểm tra lại SHD: "+query);
			ResultSet RsRs = db.get(query);
			int KT_OTC = 0;
			int KT_ETC = 0;
			
			int is_KT_OTC = 0;
			int is_KT_ETC = 0;
			
			int is_KT_SHDB = 0;
			
			while(RsRs.next())
			{
				KT_OTC = RsRs.getInt("KtraOTC") ;
				KT_ETC = RsRs.getInt("KtraETC") ;
				
				is_KT_OTC = RsRs.getInt("is_KtraOTC_dk") ;
				is_KT_ETC = RsRs.getInt("is_KtraETC_dk") ;
				
				is_KT_SHDB = RsRs.getInt("CHECK_SHD_BE") ;
			}
			
			if(is_KT_OTC >0 || is_KT_ETC > 0) // NẾU CÓ SỐ HÓA ĐƠN LỚN HƠN MÀ NGÀY XUẤT NHỎ HƠN CỦA HÓA ĐƠN TIẾP THEO THÌ K CHO
			{
				msg = "Không thể duyệt được hóa đơn. Trong hệ thống có số hóa đơn lớn hơn mà ngày xuất nhỏ hơn. Yêu cầu check lại ngày thiết lập số hóa đơn hoặc liên hệ với admin!";
				return false;
			}
				
			if(KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI TẠO
			{
				//msg = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
				msg = "Số hoá đơn tiếp theo "+sohoadon+" đã có trên hệ thống! \n Vui lòng kiểm tra lại ";
				System.out.println(msg);
				
				return false;
			}
					
			db.getConnection().setAutoCommit(false);
			
			// KIỂM TRA XEM CÓ SỐ HÓA ĐƠN NHỎ HƠN CỦA NGƯỜI SỬA NÀY K
			
			query = " update ERP_HOADONNPP set trangthai = '4', NgaySua='"+getDateTime()+"', created_Date=getdate() , sohoadon= RTRIM(LTRIM('" + sohoadon + "')), kyhieu = RTRIM(LTRIM('" + kyhieuhoadon + "')), nguoisua = " +this.userId +
					" where pk_seq = '" + this.id + "' and trangthai in (0,1)  ";
			System.out.println(query);
			
			if( db.updateReturnInt(query) !=1  )
			{
				msg = "Không thể chốt được hóa đơn. Vui lòng liên hệ admin! ";
				db.getConnection().rollback();
				return false;
			}
	
	
			//CHECK BANG TONG PHAI BANG BANG CHI TIET
			query = "select count(*) as sodong, \n" + 
					" 	(SELECT count(*) sodong FROM ERP_HOADONNPP_SP WHERE CHONIN = 0 AND HOADON_FK = " + this.id + ") soDONGtach, \n" +
					"	(SELECT count(*) sodong FROM ERP_HOADONNPP_SP WHERE CHONIN = 0 AND HOADON_FK = " + this.id + " AND dbo.trim(SCHEME) != '' ) soDONGtachKM \n"+
					"from \n" +
					"( \n" +
					"	select b.pk_seq as sanpham_fk, sum(soluong_chuan) as soluong  \n" +
					"	from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.pk_seq \n" +
					"	where a.hoadon_fk = '" + this.id + "' \n" +
					"	group by b.pk_seq \n" +
					") \n" +
					"dh left join \n" +
					"( \n" +
					"	select b.pk_seq as sanpham_fk, sum(soluong_chuan) as soluong  \n" +
					"	from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA \n" +
					"	where a.hoadon_fk = '" + this.id + "' \n" +
					"	group by b.pk_seq \n" +
					") \n" +
					"xk on dh.sanpham_fk = xk.sanpham_fk \n" +
					"where dh.soluong != isnull(xk.soluong, 0) \n";
			System.out.println("---CHECK HOA DON: " + query);
			int soDONG = 0;
			int soDONGtach = 0;
			int soDONGtachKM = 0;
			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("sodong");
					soDONGtach = rsCHECK.getInt("soDONGtach");
					soDONGtachKM = rsCHECK.getInt("soDONGtachKM");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				msg = "3.Số lượng trong đơn hàng không khớp với hóa đơn. Vui lòng liên hệ Admin để xử lý ";
				db.getConnection().rollback();
				return false;
			}
			
			msg = util.Check_Kho_Tong_VS_KhoCT(util.getIdNhapp(userId), db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}
			
			
			if(soDONGtach > 0) // TÁCH HÓA ĐƠN TÀI CHÍNH VÀO HÓA ĐƠN MỚI VỚI NHỮNG DÒNG CHONIN = 0
			{			
				if(soDONGtach == soDONGtachKM) // ALL DÒNG TÁCH HÓA ĐƠN TOÀN LÀ SẢN PHẨM KHUYẾN MÃI - SỐ HÓA ĐƠN ĐỂ TRỐNG, NGÀY HÓA ĐƠN ĐỂ TRỐNG 
				{
					msg = this.TaoHoaDonTaiChinhNPP_KM(db, this.id, userId);
					if(msg.trim().length() > 0)
					{
						msg = "Khong the tao hoa don tai chinh KM: " + msg;
						db.getConnection().rollback();
						return false;
					}
				}
				else // ALL DÒNG TÁCH HÓA ĐƠN CÓ SP HÀNG BÁN - TẠO HÓA ĐƠN NHƯ BÌNH THƯỜNG 
				{
					msg = this.TaoHoaDonTaiChinhNPP_Tach(db, id, userId, nppId, congtyId);
					if(msg.trim().length() > 0)
					{
						msg = "Khong the tao hoa don tai chinh: " + msg;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			//CHECK XEM CÓ SẢN PHẨM NÀO CHƯA XUẤT HẾT KHÔNG, NẾU CÓ THÌ TẠO HÓA ĐƠN NẰM CHỜ
			/*query = "select count(ddh.sanpham_fk) as soDong  "+
					 "from ERP_DONDATHANGNPP_SANPHAM ddh left join "+
					 "( "+
					 "	select SANPHAM_FK, SUM(soluong) as soluong "+
					 "	from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.HOADON_FK "+
					 "	where a.TRANGTHAI not in ( 3, 5 ) and a.PK_SEQ in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + id + "' ) ) "+
					 "	group by SANPHAM_FK "+
					 ") "+
					 "CT on CT.SANPHAM_FK = ddh.SANPHAM_FK  "+
					 "where ddh.dondathang_fk = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + id + "' ) and ddh.soluong - ISNULL( CT.soluong, 0 ) > 0 ";*/
			
			query = " select count(ddh.sanpham_fk) as soDong  "+
					 " from "+
					 " ( "+
					 " 	select dondathang_fk, sanpham_fk, sum(soluong) as soluong "+
					 " 	from ERP_DONDATHANGNPP_SANPHAM_CHITIET "+
					 " 	where dondathang_fk = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + id + "' )"+
					 " 	group by dondathang_fk, sanpham_fk"+
					 " ) "+
					 " ddh left join "+
					 " ( "+
					 " 	select SANPHAM_FK, SUM(soluong) as soluong "+
					 " 	from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.HOADON_FK "+
					 " 	where a.TRANGTHAI not in ( 3, 5 ) and a.PK_SEQ in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH "+
					 " 						where DDH_FK = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + id + "' ) ) "+
					 " 	group by SANPHAM_FK "+
					 " ) "+
					 " CT on CT.SANPHAM_FK = ddh.SANPHAM_FK  "+
					 " where ddh.soluong - ISNULL( CT.soluong, 0 ) > 0 ";
			System.out.println(":::: CHECK HOA DON TACH NAM CHO: " + query );
			boolean taohoadonCHO = false;
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				if( rs.next() )
				{
					if( rs.getInt("soDong") > 0 )
					{
						taohoadonCHO = true;
						System.out.println("SoDong:"+rs.getInt("soDong"));
					}
					rs.close();
				}
			}
			
			System.out.println("taohoadonCHO:"+taohoadonCHO);
			if( taohoadonCHO )
			{
				msg = this.TaoHoaDonTaiChinhNPP_NamCho(db, id, userId, nppId, congtyId);
				if(msg.trim().length() > 0)
				{
					msg = "Lỗi: " + msg;
					db.getConnection().rollback();
					return false;
				}
			}
			
			//CHECK XEM HÓA ĐƠN CÁC BỊ VƯỢT QUÁ 12 dòng trong đó bao gồm cả 1 dòng KM, 1 dòng tiền chiết khấu nếu có
			boolean vuotqua12DONG = false;
			int sodongTOIDA = 12;
			query = "select ( select count(*) from ERP_HOADONNPP_SP_CHITIET where HOADON_FK = a.pk_seq and isnull(scheme, '') != '' ) as coKM, " + 
					" 	0 as cochietKHAU " + 
					"from ERP_HOADONNPP a where pk_seq = '" + this.id + "' ";
			System.out.println(query);
			rs = db.get(query);
			if( rs != null )
			{
				if( rs.next() )
				{
					if( rs.getInt("coKM") > 0 )
						sodongTOIDA -= 1;
					if( rs.getInt("cochietKHAU") > 0 )
						sodongTOIDA -= 1;
				}
				rs.close();
			}
			
			query = "select isnull(TEN, '')  as TEN, LEN(TEN) as doidai, ROW_NUMBER( ) OVER( ORDER BY sotutang ASC ) AS Row, sotutang, isnull(scheme, '') as scheme  "+
					"from ERP_HOADONNPP_SP_CHITIET where HOADON_FK = '" + this.id + "' ";
			
			System.out.println(query);
			rs = db.get(query);
			if( rs != null )
			{
				//Mỗi dòng tên SP sẽ không được quá 40 ký tự
				//int maxDODAI = sodongTOIDA * 40;
				//int totalDODAI = 0;
				int totalSODONG = 0;
				int max_index = 0;
				
				while( rs.next() )
				{
					//totalDODAI += rs.getInt("doidai");
					totalSODONG += this.DemSoDong(rs.getString("TEN"), 40);
					System.out.println(":::: TOTAL SO DONG: " + totalSODONG + " -- TOI DA: " + sodongTOIDA );
					
					//if( totalDODAI > maxDODAI )
					if( totalSODONG > sodongTOIDA )
					{
						vuotqua12DONG = true;
						rs.close();
						break;
					}
					else
					{
						max_index = rs.getInt("sotutang");
					}
					System.out.println(":::: INDEX HIEN TAI: " + max_index );
				}
				rs.close();
				
				System.out.println(":::: VUOT QUA 12 DONG: " + vuotqua12DONG + " - TACH TU: " + max_index );
				if( vuotqua12DONG ) //TÁCH HÓA ĐƠN
				{
					msg = this.TaoHoaDonTaiChinhNPP_NamCho_TuViTri(db, id, userId, nppId, congtyId, max_index);
					if(msg.trim().length() > 0)
					{
						msg = "Lỗi khi tách hóa đơn: " + msg;
						db.getConnection().rollback();
						return false;
					}
				}
			}			
			
			//CAP NHAT LAI TIEN HOA DON CHO DUNG
			//util.CapNhat_ThanhTien_HoaDon(db, this.id);
			
			//TU DONG SINH RA 1 PHIEU GIAO HANG, KHI CHỐT PHIẾU GIAO HÀNG MỚI TRỪ KHO, ĐỒNG THỜI TẠO RA NHẬN HÀNG DƯỚI NPP
			msg = this.createPHIEUGIAOHANG(db, this.id, userId);
			System.out.println("::::: MSG TAO PGH: " + msg);
			String pghId = "";
			if(msg.length() >= 10)
			{
				db.getConnection().rollback();
				return false;
			}
			else
				pghId = msg;				
			
			//CHỐT PHIẾU GIAO HÀNG
			System.out.println("---PGH ID: " + pghId + " -- userId: " + userId + " -- nppId: " + nppId + " -- NPP DANG NHAP: " + this.nppDangnhap );
			msg = this.ChotPHIEUGIAOHANG(db, pghId, userId, util.getIdNhapp(userId));
			if(msg.length() > 0)
			{
				db.getConnection().rollback();
				return false;
			}
			
			//PHAN BO CHIET KHAU TICH LUY NEU CO
			query = "select SUM( GIATRI ) as giatri from ERP_HOADONNPP_CHIETKHAU where HOADON_FK = '" + this.id + "' ";
			rs = db.get(query);
			
			double tienCHIETKHAU = 0;
			if( rs != null )
			{
				if( rs.next() )
				{
					tienCHIETKHAU = rs.getDouble("giatri");
				}
				rs.close();
			}
			
			if( tienCHIETKHAU > 0 )
			{
				query = "select SANPHAM_FK, TIENAVAT  "+
						 "from ERP_HOADONNPP_SP  "+
						 "where HOADON_FK = '" + this.id + "' and dbo.Trim( SCHEME ) = '' order by VAT asc, STT asc ";
				rs = db.get(query);
				
				double tongtienSUDUNG = 0;
				boolean exits = false;
				while( rs.next() )
				{
					String spId = rs.getString("SANPHAM_FK");
					double tienAVAT = rs.getDouble("TIENAVAT");
					double tienPHANBO = 0;
					
					tongtienSUDUNG += tienAVAT;
					if( tongtienSUDUNG < tienCHIETKHAU )
					{
						tienPHANBO = tongtienSUDUNG;
					}
					else
					{
						tienPHANBO = tienAVAT - ( tongtienSUDUNG - tienCHIETKHAU ); 
								
						exits = true;
					}
					
					if( tienPHANBO > 0 )
					{
						query = "update ERP_HOADONNPP_SP set tientichluyCOVAT = '" + tienPHANBO + "' where hoadon_fk = '" + this.id + "' and sanpham_fk = '" + spId + "' and dbo.Trim( SCHEME ) = '' ";
						System.out.println("::: CAP NHAT TIEN PHAN BO: " + query);
						if( !db.update(query) )
						{
							msg = "Lỗi khi chốt hóa đơn: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
					
					if( exits )
						break;
					
				}
				rs.close();
			}
			
			//Cập nhật tooltip hóa đơn
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[] { this.id } );
					
			
			//CÀI KẾ TOÁN			
			
			String nam = "";
			String thang = "";
			
			query =		
				"\n SELECT	A.NGAYXUATHD,A.PK_SEQ, B.SANPHAM_FK, B.SOLUONG, ISNULL(B.DONGIA,0) DONGIA,ISNULL(B.CHIETKHAU,0) CHIETKHAU, ISNULL(A.NGAYGHINHAN, A.NGAYXUATHD) as NGAYGHINHAN, "+
				"\n ISNULL( ISNULL(A.KHGHINO, A.NPP_DAT_FK), A.nhanvien_fk ) KHACHHANG_FK, "+
				"\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13100000' AND CONGTY_FK = "+congtyId+" ) TAIKHOANNO, "+
				"\n isnull(B.THUEVAT,0) VAT, (SELECT LOAISP.TAIKHOANKT_FK FROM ERP_LOAISANPHAM LOAISP INNER JOIN ERP_TAIKHOANKT TAIKHOAN ON LOAISP.TAIKHOANKT_FK = TAIKHOAN.SOHIEUTAIKHOAN "+
				"\n WHERE C.LOAISANPHAM_FK =  LOAISP.PK_SEQ AND TAIKHOAN.SOHIEUTAIKHOAN = LOAISP.TAIKHOANKT_FK AND TAIKHOAN.CONGTY_FK =  "+ congtyId +" ) LOAISP, "+
				"\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51110000' AND CONGTY_FK =  "+ congtyId +" ) as a51110000, "+
				"\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51120000' AND CONGTY_FK =  "+ congtyId +" ) as a51120000, "+
				"\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52110000' AND CONGTY_FK =  "+ congtyId +" ) as a52110000, "+
				"\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK =  "+ congtyId +" ) as a33311000, "+
				"\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51180000' AND CONGTY_FK =  "+ congtyId +" ) as a51180000, ISNULL(A.TIENTE_FK, 100000) TIENTE_FK, ISNULL(A.TYGIA, 1) TYGIA, ISNULL(B.THANHTIEN,0) THANHTIEN, ROUND(ISNULL(B.TIENTHUE,0),0) TIENVAT, ISNULL(B.CHIETKHAU,0) CHIETKHAU, "+
				"\n ( SELECT TK.PK_SEQ FROM ERP_NHOMCHIPHI CP INNER JOIN ERP_TAIKHOANKT TK ON CP.TAIKHOAN_FK = TK.SOHIEUTAIKHOAN WHERE TK.CONGTY_FK = "+ congtyId +" AND B.KMCP_FK = CP.PK_SEQ ) KMCP, C.TEN TENSP, C.MA_FAST MASP, B.DONVI DONVITINH, "+
				"\n CASE WHEN A.KHACHHANG_FK IS NOT NULL THEN 0 WHEN A.NPP_DAT_FK IS NOT NULL THEN 1 ELSE 2 END AS ISNPP, A.KBH_FK, B.Kho_FK, B.SOLO, B.NGAYHETHAN  "+
				"\n	FROM ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_SP_CHITIET B ON A.PK_SEQ = B.HOADON_FK "+
				"\n	INNER JOIN SANPHAM C ON B.SANPHAM_FK = C.PK_SEQ	 "+
				"\n	LEFT JOIN KHACHHANG D ON A.KHACHHANG_FK = D.PK_SEQ "+
				"\n	LEFT JOIN NHAPHANPHOI E ON A.NPP_DAT_FK = E.PK_SEQ "+
				"\n LEFT JOIN KHACHHANG F ON A.KHGHINO = F.PK_SEQ LEFT JOIN ERP_NHANVIEN G ON A.nhanvien_fk = G.PK_SEQ "+
				"\n WHERE A.PK_SEQ = "+this.id+" AND LEN(ISNULL(SCHEME,'')) = 0  ";
				
				/*"\n	SELECT	A.NGAYXUATHD,A.PK_SEQ, B.SANPHAM_FK, B.SOLUONG, ISNULL(B.DONGIA,0) DONGIA,ISNULL(B.CHIETKHAU,0) CHIETKHAU, ISNULL(A.NGAYGHINHAN, A.NGAYXUATHD) as NGAYGHINHAN, "+
				"\n	ISNULL( ISNULL(A.KHGHINO, A.NPP_DAT_FK), A.nhanvien_fk ) KHACHHANG_FK, "+
				"\n	CASE WHEN A.KHGHINO IS NOT NULL THEN F.TAIKHOAN_FK  WHEN A.NPP_DAT_FK IS NOT NULL THEN E.TAIKHOAN_FK when A.nhanvien_fk is not null then G.TAIKHOAN_FK END TAIKHOANNO, "+
				"\n	isnull(B.VAT,0) VAT, (SELECT LOAISP.TAIKHOANKT_FK "+
				"\n					FROM ERP_LOAISANPHAM LOAISP INNER JOIN ERP_TAIKHOANKT TAIKHOAN ON LOAISP.TAIKHOANKT_FK = TAIKHOAN.SOHIEUTAIKHOAN "+
				"\n	 		WHERE C.LOAISANPHAM_FK =  LOAISP.PK_SEQ AND TAIKHOAN.SOHIEUTAIKHOAN = LOAISP.TAIKHOANKT_FK AND TAIKHOAN.CONGTY_FK = "+ congtyId +" ) LOAISP, "+
				"\n	( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51110000' AND CONGTY_FK = "+ congtyId +") as a51110000, "+
				"\n	( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51120000' AND CONGTY_FK = "+ congtyId +") as a51120000, "+
				"\n	( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52110000' AND CONGTY_FK = "+ congtyId +") as a52110000, "+
				"\n	( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK = "+ congtyId +") as a33311000, "+
				"\n	( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51180000' AND CONGTY_FK = "+ congtyId +") as a51180000, ISNULL(A.TIENTE_FK, 100000) TIENTE_FK, ISNULL(A.TYGIA, 1) TYGIA, ISNULL(B.THANHTIEN,0) THANHTIEN, ROUND(ISNULL(B.TIENVAT,0),0) TIENVAT, ISNULL(B.CHIETKHAU,0) CHIETKHAU, "+
		 		"\n	( SELECT TK.PK_SEQ FROM ERP_NHOMCHIPHI CP INNER JOIN ERP_TAIKHOANKT TK ON CP.TAIKHOAN_FK = TK.SOHIEUTAIKHOAN WHERE TK.CONGTY_FK = 100001 AND B.KMCP_FK = CP.PK_SEQ ) KMCP, C.TEN TENSP, C.MA_FAST MASP, B.DONVITINH DONVITINH, "+
		    	"\n	CASE WHEN A.KHACHHANG_FK IS NOT NULL THEN 0 WHEN A.NPP_DAT_FK IS NOT NULL THEN 1 ELSE 2 END AS ISNPP, A.KBH_FK, B.Kho_FK "+
				"\n FROM ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_SP B ON A.PK_SEQ = B.HOADON_FK "+
				"\n INNER JOIN SANPHAM C ON B.SANPHAM_FK = C.PK_SEQ	 "+
				"\n LEFT JOIN KHACHHANG D ON A.KHACHHANG_FK = D.PK_SEQ "+
				"\n LEFT JOIN NHAPHANPHOI E ON A.NPP_DAT_FK = E.PK_SEQ "+
				"\n	LEFT JOIN KHACHHANG F ON A.KHGHINO = F.PK_SEQ LEFT JOIN ERP_NHANVIEN G ON A.nhanvien_fk = G.PK_SEQ "+
				"\n	WHERE A.PK_SEQ = "+this.id+" AND LEN(ISNULL(SCHEME,'')) = 0  ";*/
			
			System.out.println(query);
			ResultSet kt  = db.get(query);
			
			if(kt!=null)
			{
				while(kt.next())
				{
					String khachhang_fk = kt.getString("KHACHHANG_FK");
					double soluong = kt.getDouble("SOLUONG");
					double dongia = kt.getDouble("DONGIA"); // ĐƠN GIÁ SAU KHI GIẢM
					double tienhang = kt.getDouble("THANHTIEN");
					String SOHIEUTAIKHOAN = kt.getString("LOAISP")== null ? "": kt.getString("LOAISP") ;
					double tienthue = kt.getDouble("TIENVAT");
					
					String sanpham_fk = kt.getString("SANPHAM_FK");
					
					double tienchietkhau = kt.getDouble("CHIETKHAU");
					
					String ngaychungtu = kt.getString("NGAYXUATHD");
					String ngayghinhan = kt.getString("NGAYGHINHAN");
					
					String solo = kt.getString("SOLO")== null ? "": kt.getString("SOLO") ;
					String khoxuat = kt.getString("Kho_FK")== null ? "": kt.getString("Kho_FK") ;
					String ngayhethan = kt.getString("NGAYHETHAN")== null ? "": kt.getString("NGAYHETHAN") ;
					
					String isNPP = kt.getString("ISNPP");					
					String kbh_fk = kt.getString("KBH_FK");
					
					nam = ngayghinhan.substring(0, 4);
					thang = ngayghinhan.substring(5, 7);
					
					String tiente_fk = kt.getString("TIENTE_FK");
					double tygia = kt.getDouble("TYGIA");
					
					String doituong_no = "";
					String madoituong_no =  "";
					
					String doituong_co = "";
					String madoituong_co = "";
					
					String TAIKHOANNO = "";
					String TAIKHOANCO ="";
					
					String masp = kt.getString("MASP");
					String tensp = kt.getString("TENSP");
					String donvitinh = kt.getString("DONVITINH");
					
					if(SOHIEUTAIKHOAN.trim().length()<=0 || SOHIEUTAIKHOAN.trim().length() <=0 || SOHIEUTAIKHOAN == null || SOHIEUTAIKHOAN == null)
					{
						kt.close();
						this.msg = "Loại sản phẩm / Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
						
						
					if(SOHIEUTAIKHOAN.equals("15610000")) // SẢN PHẨM LÀ LOẠI HÀNG HÓA
					{
						if(tienhang>0)
						{
							doituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;
							
							doituong_co = "Sản phẩm";
						    madoituong_co = sanpham_fk;
						    
						    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
						    TAIKHOANCO = kt.getString("a51110000") == null ? "": kt.getString("a51110000") ;
						    
						    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
						    {						    	
								this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								System.out.println(this.msg);
								kt.close();
								db.getConnection().rollback();
								return false;
						    }		
						    
					       this.msg = util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", this.id, TAIKHOANNO, TAIKHOANCO, "", 
								Double.toString(tienhang), Double.toString(tienhang), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienhang), 
								Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(tienthue) , "" , this.id ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan, Double.toString(tienhang));
																						   
							if(this.msg.trim().length()>0)
							{
								this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								kt.close();
								db.getConnection().rollback();
								return false;
							}
						}
							
					}
					else if(SOHIEUTAIKHOAN.equals("15510000")) // SẢN PHẨM LÀ THÀNH PHẨM
					{
						if(tienhang>0)
						{
							doituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;
							
							doituong_co = "Sản phẩm";
						    madoituong_co = sanpham_fk;
						    
						    
						    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
						    TAIKHOANCO = kt.getString("a51120000")== null ? "": kt.getString("a51120000") ;
						    
						    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
						    {
						    	this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
						    	System.out.println(this.msg);
						    	kt.close();								
								db.getConnection().rollback();
								return false;
						    }
						    
						  /*  this.msg = 	util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", this.id, TAIKHOANNO, TAIKHOANCO, "", 
										Double.toString(tienhang), Double.toString(tienhang), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienhang), 
										Double.toString(tienhang), "Hóa đơn - Tiền hàng", "0" , "" , this.id ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan);*/
											
						    
						    msg = 	util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", this.id, TAIKHOANNO, TAIKHOANCO, "", 
									Double.toString(tienhang), Double.toString(tienhang), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienhang), 
									Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(tienthue) , "" , this.id ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan, Double.toString(tienhang));
										
						   
							if(this.msg.trim().length()>0)
							{
								this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								System.out.println(this.msg);
								kt.close();
								db.getConnection().rollback();
								return false;
							}
						}
					}
					else
					{
						if(tienhang>0)
						{
							doituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;
							
							doituong_co = "Sản phẩm";
						    madoituong_co = sanpham_fk;
						    
						    
						    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
						    TAIKHOANCO = kt.getString("a51180000") == null ? "": kt.getString("a51180000") ;
						    
						    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
						    {						    	
								this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								System.out.println(this.msg);
								kt.close();
								db.getConnection().rollback();
								return false;
						    }
						    
						   /* this.msg = 	util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", this.id, TAIKHOANNO, TAIKHOANCO, "", 
									Double.toString(tienhang), Double.toString(tienhang), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienhang), 
									Double.toString(tienhang), "Hóa đơn - Tiền hàng", "0" , "" , this.id ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan);*/
								
						   
						    msg = 	util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", this.id, TAIKHOANNO, TAIKHOANCO, "", 
									Double.toString(tienhang), Double.toString(tienhang), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienhang), 
									Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(tienthue) , "" , this.id ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan, Double.toString(tienhang));
								
						    
							if(this.msg.trim().length()>0)
							{								
								this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								System.out.println(this.msg);
								kt.close();
								db.getConnection().rollback();
								return false;
							}
						}
					}
					
					if(tienthue>0)
					{
						doituong_no = "Khách hàng";
						madoituong_no = khachhang_fk;
						
						doituong_co = "Sản phẩm";
					    madoituong_co = sanpham_fk;
					    
					    
					    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
					    TAIKHOANCO = kt.getString("a33311000") == null ? "": kt.getString("a33311000") ;
					    
					    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
					    {
							this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(this.msg);
							kt.close();
							db.getConnection().rollback();
							return false;
					    }
					    
					   /* this.msg = 	util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", this.id, TAIKHOANNO, TAIKHOANCO, "", 
						Double.toString(tienthue), Double.toString(tienthue), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienthue), 
						Double.toString(tienthue), "Hóa đơn - Tiền thuế", "0" , "" , this.id ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan);*/
							
					    
					    this.msg = util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", this.id, TAIKHOANNO, TAIKHOANCO, "", 
									Double.toString(tienthue), Double.toString(tienthue), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienthue), 
									Double.toString(tienthue), "Hóa đơn - Tiền thuế", Double.toString(tienthue) , "" , this.id ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan, Double.toString(tienhang));
									
						if(this.msg.trim().length()>0)
						{							
							this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(this.msg);
							kt.close();
							db.getConnection().rollback();
							return false;
						}
					}
					
					if(tienchietkhau>0) // CHIẾT KHẤU THEO DÒNG HÀNG
					{
						doituong_no = "Sản phẩm";
						madoituong_no = sanpham_fk;
						
						doituong_co = "Khách hàng";
					    madoituong_co = khachhang_fk;
					    
					    TAIKHOANNO = kt.getString("KMCP")== null ? "": kt.getString("KMCP") ;
					    TAIKHOANCO = kt.getString("TAIKHOANNO")== null ? "": kt.getString("TAIKHOANNO") ;
					    
					    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
					    {
							this.msg = "Khách hàng tương ứng hoặc chiết khấu chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(this.msg);
							kt.close();
							db.getConnection().rollback();
							return false;
					    }
				    
					    this.msg = util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", this.id, TAIKHOANNO, TAIKHOANCO, "", 
								Double.toString(tienchietkhau), Double.toString(tienchietkhau), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienchietkhau), 
								Double.toString(tienchietkhau), "Hóa đơn - Tiền chiết khấu", Double.toString(tienthue) , "" , this.id ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan, Double.toString(tienhang));
										
						if(this.msg.trim().length()>0)
						{							
							this.msg = "Khách hàng tương ứng hoặc chiết khấu chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(this.msg);
							kt.close();							
							db.getConnection().rollback();
							return false;
						}
					}
				}
				kt.close();
			}
			
			// CHIẾT KHẤU TÍCH LŨY
			
			query = " SELECT A.HOADON_FK, round(round(isnull(A.tienchuaVAT, 0),0)*isnull(A.VAT,0)/100,0) tichluy_tienvat , \n" +
					" ISNULL(B.NGAYGHINHAN, B.NGAYXUATHD) as NGAYGHINHAN, \n"+
					" B.NGAYXUATHD, ISNULL(B.TIENTE_FK, 100000) TIENTE_FK, ISNULL(B.TYGIA, 1) TYGIA , \n"+
					" round(isnull(A.tienchuaVAT,0),0) tichluy_tienBVAT, \n"+
					" ISNULL( ISNULL(B.KHGHINO, B.NPP_DAT_FK), B.nhanvien_fk ) KHACHHANG_FK, \n"+
					" CASE WHEN B.KHGHINO IS NOT NULL THEN F.TAIKHOAN_FK  WHEN B.NPP_DAT_FK IS NOT NULL THEN E.TAIKHOAN_FK when B.nhanvien_fk is not null then G.TAIKHOAN_FK END TAIKHOANNO, \n"+
					" ( SELECT TK.PK_SEQ FROM ERP_NHOMCHIPHI CP INNER JOIN ERP_TAIKHOANKT TK ON CP.TAIKHOAN_FK = TK.SOHIEUTAIKHOAN WHERE TK.CONGTY_FK = "+ congtyId +" AND A.KMCP_FK = CP.PK_SEQ ) KMCP, \n"+
					" ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK = "+ congtyId +") as a33311000,  \n"+
					" CASE WHEN B.KHACHHANG_FK IS NOT NULL THEN 0 WHEN B.NPP_DAT_FK IS NOT NULL THEN 1 ELSE 2 END AS ISNPP, B.KBH_FK \n"+
				    " FROM  ERP_HOADONNPP_CHIETKHAU A INNER JOIN ERP_HOADONNPP B ON A.HOADON_FK = B.PK_SEQ \n"+
				    " LEFT JOIN KHACHHANG D ON B.KHACHHANG_FK = D.PK_SEQ \n"+
					" LEFT JOIN NHAPHANPHOI E ON B.NPP_DAT_FK = E.PK_SEQ \n"+
					" LEFT JOIN KHACHHANG F ON B.KHGHINO = F.PK_SEQ \n " +
					" LEFT JOIN ERP_NHANVIEN G ON B.nhanvien_fk = G.PK_SEQ  \n"+
				    " WHERE A.HOADON_FK = "+this.id ;
			
			System.out.println(query);
			kt = db.get(query);
			
			if(kt!=null)
			{
				double tichluy_tienvat = 0;
				double tichluy_tienBVAT = 0;
				
				String doituong_no = "";
				String doituong_co = "";
				
				String madoituong_no = "";
				String madoituong_co = "";
				
				String khachhang_fk = "";
				
				String TAIKHOANNO = "";
				String TAIKHOANCO = "";
				
				String isNPP = "";
				String kbh_fk = "";
				
				String tiente_fk = "";
				double tygia = 0;
				
				while(kt.next())
				{
					tichluy_tienvat = kt.getDouble("tichluy_tienvat");
					tichluy_tienBVAT = kt.getDouble("tichluy_tienBVAT");
											
					khachhang_fk = kt.getString("KHACHHANG_FK");
					kbh_fk = kt.getString("KBH_FK");
					tygia = kt.getDouble("TYGIA");
					tiente_fk = kt.getString("TIENTE_FK");
					
					String ngaychungtu = kt.getString("NGAYXUATHD");
					String ngayghinhan = kt.getString("NGAYGHINHAN");
					
					isNPP = kt.getString("isNPP");
					
					nam = ngayghinhan.substring(0, 4);
					thang = ngayghinhan.substring(5, 7);
					
					if(tichluy_tienBVAT > 0)
					{
						doituong_no = "";
						madoituong_no = "";
						
						doituong_co = "Khách hàng";
						madoituong_co = khachhang_fk;
						
						TAIKHOANNO = kt.getString("KMCP")== null ? "": kt.getString("KMCP") ;
					    TAIKHOANCO = kt.getString("TAIKHOANNO")== null ? "": kt.getString("TAIKHOANNO") ;
					    
					    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
					    {
							this.msg = "Khách hàng tương ứng hoặc chiết khấu chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(this.msg);
							kt.close();
							db.getConnection().rollback();
							return false;
					    }
					    
					   /* this.msg = 	util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", this.id, TAIKHOANNO, TAIKHOANCO, "", 
						Double.toString(tichluy_tienBVAT), Double.toString(tichluy_tienBVAT), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", "", "", tiente_fk, "", Double.toString(tygia), Double.toString(tichluy_tienBVAT), 
						Double.toString(tichluy_tienBVAT), "Hóa đơn - Tiền chiết khấu tích lũy", "0" , "" , this.id ,isNPP ,"" , "", "", kbh_fk, kho);*/
					    
					  /*  this.msg = util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan,  "Hóa đơn tài chính", this.id, TAIKHOANNO, TAIKHOANCO, 
				    			"", Double.toString(tichluy_tienBVAT), Double.toString(tichluy_tienBVAT), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", 
				    			"", "", tiente_fk, "", Double.toString(tygia), Double.toString(tichluy_tienBVAT), Double.toString(tichluy_tienBVAT), "Hóa đơn - Tiền chiết khấu tích lũy");
					    */
					    this.msg = 	util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", this.id, TAIKHOANNO, TAIKHOANCO, "", 
								Double.toString(tichluy_tienBVAT), Double.toString(tichluy_tienBVAT), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", "", "", tiente_fk, "", Double.toString(tygia), Double.toString(tichluy_tienBVAT), 
								Double.toString(tichluy_tienBVAT), "Hóa đơn - chiết khấu tích lũy", "0" , "" , this.id ,isNPP ,"" , "", "", kbh_fk);
							    
					    if(this.msg.trim().length()>0)
						{							
							this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(this.msg);
							kt.close();
							db.getConnection().rollback();
							return false;
						}
					    
					}
					
					if(tichluy_tienvat > 0)
					{						
						doituong_no = "";
					    madoituong_no = "";
					    
					    doituong_co = "Khách hàng";
						madoituong_co = khachhang_fk;
					    
					    
					    TAIKHOANCO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
					    TAIKHOANNO = kt.getString("a33311000") == null ? "": kt.getString("a33311000") ;
					    
					    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
					    {
							this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(this.msg);
							kt.close();
							db.getConnection().rollback();
							return false;
					    }
					    
					    this.msg = 	util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", this.id, TAIKHOANNO, TAIKHOANCO, "", 
								Double.toString(tichluy_tienvat), Double.toString(tichluy_tienvat), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", "", "", tiente_fk, "", Double.toString(tygia), Double.toString(tichluy_tienvat), 
								Double.toString(tichluy_tienvat), "Hóa đơn - Tiền thuế tích lũy", "0" , "" , this.id ,isNPP ,"" , "", "", kbh_fk);
							    
					  
						if(this.msg.trim().length()>0)
						{							
							this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(this.msg);
							kt.close();
							db.getConnection().rollback();
							return false;
						}
					}
				}
				kt.close();
			}
			
			//SAU KHI TÁCH CÁC LOẠI HÓA ĐƠN, TỔNG TRONG ĐƠN HÀNG KHÔNG ĐƯỢC KHÁC TỔNG TRONG HÓA ĐƠN
			query = " select count(ddh.sanpham_fk) as soDong  "+
					 " from "+
					 " ( "+
					 " 	select dondathang_fk, sanpham_fk, sum(soluong) as soluong "+
					 " 	from ERP_DONDATHANGNPP_SANPHAM_CHITIET "+
					 " 	where dondathang_fk = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + id + "' )"+
					 " 	group by dondathang_fk, sanpham_fk"+
					 " ) "+
					 " ddh left join "+
					 " ( "+
					 " 	select SANPHAM_FK, SUM(soluong) as soluong "+
					 " 	from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.HOADON_FK "+
					 " 	where a.TRANGTHAI not in ( 3, 5 ) and a.PK_SEQ in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH "+
					 " 						where DDH_FK = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + id + "' ) ) "+
					 " 	group by SANPHAM_FK "+
					 " ) "+
					 " CT on CT.SANPHAM_FK = ddh.SANPHAM_FK  "+
					 " where ddh.soluong != ISNULL( CT.soluong, 0 )  ";
			System.out.println(":::: CHECK TONG HOA DON VA DON HANG: " + query );
			boolean tachbiLOI = false;
			rs = db.get(query);
			if( rs != null )
			{
				if( rs.next() )
				{
					if( rs.getInt("soDong") > 0 )
						tachbiLOI = true;
					rs.close();
				}
			}
			
			if( tachbiLOI )
			{
				msg = "Tổng số lượng của các mặt hàng trong hóa đơn sau khi chọn in, tách tự động không bằng tổng số lượng ban đầu. Vui lòng kiên hệ Admin để xử lý.";
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
			//LƯU Ý, NHÓM KÊNH ĐỂ NPP NHẬN HÀNG, PHẢI LÀ NHÓM KÊNH TRONG LÚC ĐẶT HÀNG, LÚC CHỐT SẼ LẤY CHÍNH XÁC
			query = "select a.DDH_FK, b.SOHOADON, b.npp_fk, b.Kho_FK, " +
					" ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = ( select kbh_fk from ERP_DONDATHANGNPP where PK_SEQ = a.DDH_FK ) ) as nhomkenhId	" +
					"from ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK = b.PK_SEQ " +
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
			
			// nếu là giao hàng của NPP
			// nếu là giao hàng của đơn hàng mượn thì giao hàng đi từ kho ký gửi
			query="SELECT isnull(DONHANGMUON,'0')  as DONHANGMUON  ,khachhang_fk FROM ERP_DONDATHANGNPP WHERE PK_SEQ="+ddhId;
			
			ResultSet rsdh=db.get(query);
			 String donhangmuon="";
			 String Khahhang_kyguiId="";
			if(rsdh.next()){
				donhangmuon=rsdh.getString("DONHANGMUON");	
				Khahhang_kyguiId= rsdh.getString("khachhang_fk");	
			}
			rsdh.close();
			if(donhangmuon.equals("1")){
				khoId="100003";
			}
				
			//Tạo mặc định chốt luôn
			query = " insert ERP_YCXUATKHONPP(DONHANGMUON,NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, npp_dat_fk, khachhang_fk, nhomkenh_fk, ngaytao, nguoitao, ngaysua, nguoisua, hoadon_fk, sohoadon, ddh_fk, loaidonhang, khachhangKG_FK, NHANVIEN_FK) " +
					" select "+donhangmuon+", ( select NGAYXUATHD from ERP_HOADONNPP where pk_seq = '" + hdId + "' ), N'Phiếu giao hàng tự động từ duyệt hóa đơn " + hdId + "', '0', '" + nppId + "', " + khoId + ", " +
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
			
			// cập nhật kho ký gửi 
			if(donhangmuon.equals("1")){
				 
				
				query=	" SELECT B.nhomkenh_fk ,B.NgayYeuCau, B.NPP_FK,B.KHO_FK,A.SANPHAM_FK,A.SOLO,A.SOLUONG,A.NGAYHETHAN " +
						" FROM ERP_YCXUATKHONPP_SANPHAM_CHITIET A " +
						" INNER JOIN ERP_YCXUATKHONPP B  ON A.YCXK_FK=B.PK_SEQ WHERE B.PK_SEQ="+ycxkId;
				ResultSet rssp=db.get(query);
				Utility_Kho util_kho= new Utility_Kho();
				while(rssp.next()){
					double	soluong_cn=(-1)*rssp.getDouble("SOLUONG");
					double	booked_cn=0;
					double	available_cn=(-1)*rssp.getDouble("SOLUONG");
					String spid=rssp.getString("SANPHAM_FK");
					String Npp_fk=rssp.getString("NPP_FK");
					String ngaydonhang=rssp.getString("NgayYeuCau");
					String nhomkenh=rssp.getString("nhomkenh_fk");
					String solo=rssp.getString("solo");
					String NGAYHETHAN=rssp.getString("NGAYHETHAN");
					double giaton=0;
					String	msg1=util_kho.Update_NPP_Kho_Sp(ngaydonhang, "Chuyển kho", db, khoId, spid, Npp_fk, nhomkenh,soluong_cn, booked_cn , available_cn,giaton);
					if(msg1.length() >0){
						return msg1;
					}
					msg1=util_kho.Update_NPP_Kho_Sp_Kygui( ngaydonhang, "Chuyển kho", db,  khoId, spid,   Npp_fk, nhomkenh,soluong_cn, booked_cn , available_cn, giaton , Khahhang_kyguiId); 
					if(msg1.length() >0){
							return msg1;
					}
					msg1=util_kho.Update_NPP_Kho_Sp_Kygui_Chitiet( ngaydonhang, "Chuyển kho", db,  khoId,  spid, Npp_fk, nhomkenh,  Khahhang_kyguiId ,solo, "", NGAYHETHAN,ngaydonhang,  soluong_cn, booked_cn , available_cn, 0) ;
					if(msg1.length() >0){
							return msg1;
					}
				}
				rssp.close();
				// Chạy kế toán đối với xuất kho hàng ký gửi
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
		String msg = "";
		try
		{
			//db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			// BUT TOAN KE TOAN
			Utility_Kho util_kho=new Utility_Kho();
			
			Utility util =new Utility(); 
			
			query = " SELECT HD.KBH_FK,ISNULL(YC.DONHANGMUON,'0') AS DONHANGMUON , YC.NGAYYEUCAU, YC_CT.SANPHAM_FK, " +
					" SUM(YC_CT.SOLUONG) AS TONGXUAT,YC_CT.SOLO ,YC_CT.NGAYHETHAN, " +
			 		" CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN  " +
			 		" 'XK01' " +
			 		" ELSE " +
			 		" 'XK02' END AS MAXUATKHO, " +
			 		" CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
					" (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63211000' " +
					" AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')) " +
					" ELSE " +  // ĐƠN HÀNG KHUYẾN MẠI
					" (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63220000' " +
					"  AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')) " +
					" END AS TAIKHOANNO_FK,  " + 
					
					" ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '15700000' " +
					"  AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "'))  AS TAIKHOANCO_FK, " +
					
			 		" CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
			 		" '100002' " +
			 		" ELSE " +
			 		" '100008' END AS NOIDUNGXUAT_FK, " +
					
			 		" CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN " +
			 		" N'Xuất bán hàng (theo đơn hàng bán) - Kho ký gửi' " +
			 		" ELSE " +
			 		" N'Xuất khuyến mại - Kho ký gửi' END AS KHOANMUC ," +
			 		" (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "') as congty_fk ,  " +
			 		" SP.MA_FAST MASP, SP.TEN TENSP, DV.DIENGIAI DVT " +	
			 		" FROM ERP_YCXUATKHONPP YC " +
					" INNER JOIN ERP_YCXUATKHONPP_SANPHAM_CHITIET YC_CT ON YC.PK_SEQ = YC_CT.YCXK_FK  " +
					" INNER JOIN SANPHAM SP ON SP.PK_SEQ=YC_CT.SANPHAM_FK " +
					" LEFT JOIN DONVIDOLUONG DV ON DV.PK_sEQ=SP.DVDL_FK  " + 
					" INNER JOIN ERP_HOADONNPP HD ON HD.PK_SEQ = YC.HOADON_FK " +
					" WHERE YCXK_FK = '" + Id + "' " +
					" GROUP BY HD.KBH_FK, NGAYYEUCAU, YC_CT.SANPHAM_FK, YC_CT.KHO_FK, YC.NHOMKENH_FK, YC_CT.SCHEME,YC_CT.SOLO,YC.DONHANGMUON, YC_CT.NGAYHETHAN, SP.MA_FAST, SP.TEN, DV.DIENGIAI ";
			
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
				String DONHANGMUON=rs.getString("DONHANGMUON");
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
				 
				String TIENTEGOC_FK = "100000";
				String DONGIANT = "0";
				String TIGIA_FK = "1";
				String TONGGIATRI = NO;
				String TONGGIATRINT = TONGGIATRI;
				String khoanmuc = rs.getString("KHOANMUC");
				NumberFormat formater = new DecimalFormat("########.00");
				String masp=rs.getString("MASP");
					String tensp=rs.getString("TENSP");
					String  dvt=rs.getString("DVT");
						
						String ngayhethan =rs.getString("ngayhethan");
				String _KBH_FK=rs.getString("KBH_FK");
				// kho KÝ GỬI
				String _Kho_FK="100003";
				
				
				if(DONHANGMUON.equals("1")){
					
					String msg1 =util.Update_TaiKhoan_DienGiai_SP_TheoLo(db, thang, nam, ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoanNO_fk, taikhoanCO_fk, 
						 	NOIDUNGXUAT_FK, NO, CO, DOITUONG_NO, MADOITUONG_NO, DOITUONG_CO, MADOITUONG_CO, LOAIDOITUONG, 
						 	SOLUONG,formater.format(dongia_capnhat), TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, 
						 	TONGGIATRINT,"", khoanmuc, sochungtu, masp, tensp, dvt,solo,ngayhethan,_KBH_FK,_Kho_FK,"null");
					 
					if(msg1.length()>0){
						return msg1;
					}
					
					query=" UPDATE  ERP_YCXUATKHONPP_SANPHAM_CHITIET SET GIACHAYKT ="+dongia_capnhat+",taikhoanktNo="+taikhoanNO_fk+",taikhoanktCO="+taikhoanCO_fk+"  WHERE SOLO='"+solo+"' AND SANPHAM_FK= "+SANPHAM_FK+" AND YCXK_FK= "+Id;
					if(!db.update(query)){
						return "Không thể cập nhật dòng lệnh ,vui lòng kiểm tra lại"+query;
					}
					
					
				}
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
			
			//TU DONG HOAN TAT CAC DON DAT HANG TU CU TOI MOI --> TRANG THAI DON HANG KHI DA DUYET CS LUC NAO CUNG = 4
			/*query = "select ddh_fk, ( select xuatcho from ERP_YCXUATKHONPP where pk_seq = a.ycxk_fk ) as xuatcho " +
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
			}*/
			
			//TẠO NHẬP HÀNG TỤ ĐỘNG ĐỔI LẠI LÚC CHỐT SỔ XUẤT HÀNG
			/*System.out.println("---XUAT CHO: " + xuatCHO);
			if(xuatCHO.equals("0"))  //XUẤT CHO ĐỐI TÁC THÌ TẠO NHẬP HÀNG CHO ĐỐI TÁC = LUONG THUC GIAO, LUONG CON DU CHUYEN VAO KHO KY GUI
			{
				//NHÓM KÊNH PHẢI LÀ NHÓM ĐẶT HÀNG THEO KÊNH
				query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, SOCHUNGTU, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, NHOMKENH_FK, YCXKNPP_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
						" select distinct NgayYeuCau, NgayYeuCau, NPP_DAT_FK,  " +
						" 			( select sohoadon from ERP_YCXUATKHONPP where PK_SEQ = a.pk_seq ), " +
						" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_DAT_FK ) ), " +
						"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_DAT_FK ), " +
						"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK )), " +
						"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK ) ) ), " +
						" 	   '100001' as DVKD_FK, " + 
						" 		( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = ( select kbh_fk from ERP_DONDATHANGNPP where PK_SEQ = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + this.id + "' ) ) ) NHOMKENH_FK, " + 
						" 	   '" + Id + "', '1' as trangthai, '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "' " +
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
			*/
				
			//Lượng chênh lệch giữa lúc duyệt hóa đơn và lúc phiếu giao hàng sẽ được cho vào kho gửi của npp, khách hàng tại PHANAM
			query =  "select '100003' as kho_fk, c.xuatcho, a.sanpham_fk, c.nhomkenh_fk, c.NPP_DAT_FK, c.KHACHHANG_FK, a.solo, a.ngayhethan, 1 as DUNGCHUNGKENH, a.soluong - b.soluong as conLAI  " + 
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
					//if(rsKYGUI.getString("DUNGCHUNGKENH").equals("1"))
						nhomkenh_fk = "100000";
						
					String isNPP = "0";
					if(rsKYGUI.getString("xuatcho").equals("0")) //đơn hàng bán cho NPP
						isNPP = "1";
					
					String KHACHHANG_FK = rsKYGUI.getString("KHACHHANG_FK");
					if(isNPP.equals("1"))
						KHACHHANG_FK = rsKYGUI.getString("NPP_DAT_FK");
					
					String solo = rsKYGUI.getString("solo");
					String ngayhethan = rsKYGUI.getString("ngayhethan");
					double soluongCONLAI = rsKYGUI.getDouble("conLAI");
					
					query = " select count(*) as sodong from NHAPP_KHO_KYGUI " + 
							" where isNPP = '" + isNPP + "' and NPP_FK = '" + nppId + "' and khachhang_fk = '" + KHACHHANG_FK + "' and kho_fk = '" + khokyguiID + "' and sanpham_fk = '" + sanpham_fk + "' and nhomkenh_fk = '" + nhomkenh_fk + "' ";
					
					System.out.println("::: CHECK KHO KY GUI: " + query);
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
		Utility util = new Utility();
		query = "select PK_SEQ, TEN from KHACHHANG a where TRANGTHAI = '1' and npp_fk ='"+ this.nppDangnhap +"' " +
		//PHAN QUYEN
		util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		System.out.println(query);
		this.khRs = db.getScrol(query);

		query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and loaiNPP = '4' and tructhuoc_fk ='"+ this.nppDangnhap +"' ";
		this.nppRs = db.get(query);
		
		if(this.khId.trim().length() > 0 )
		{			
			query = " select PK_SEQ , NgayDonHang  " +
					" from ERP_DONDATHANGNPP " +
					" where NPP_FK = '"+ this.nppDangnhap +"'  AND  KHACHHANG_FK = '" + this.khId + "' and kho_fk in " + util.quyen_kho(this.userId)+
					"       and pk_seq in (select ddh_fk " +
					"                      from ERP_YCXUATKHONPP_DDH a inner join ERP_YCXUATKHONPP b on a.ycxk_fk= b.pk_seq " +
					"                      where b.trangthai=2 )  " +
					" and pk_seq not in (select a.DDH_FK " +
					"                    from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ" +
					"                    where b.TRANGTHAI = 2 and isKM != 0)" +
					" and pk_seq not in(select a.DDH_FK from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ where b.TRANGTHAI in ( 1, 2, 4 ) and b.pk_seq != " + ( this.id.trim().length() > 0 ? this.id : "-1" ) + " )  " +
					" order by NgayDonHang desc ";
			System.out.println("Câu query " + query);		
			this.ddhRs = db.get(query);	
			
			//Lây thông tin xuất hóa đon cũ cho người dùng chọn lại, trong popup lúc nào cũng là thông tin mới nhất
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
					catch (Exception e) { }
				}
			}
			
			System.out.println("query:query:"+query);
			/*query = "select PK_SEQ , case when LEN(isnull(TENXUATHD,'')) > 0 then isnull(TENXUATHD,'') else TEN end  TENXUATHD, ISNULL(GHICHU2, '') GHICHU2 FROM KHACHHANG WHERE PK_SEQ = "+this.khId;
			System.out.println(query);
			ResultSet rs =  this.db.get(query);

			if(rs!=null)
			{
				try{
					while (rs.next()){
						this.tenXuatHD = rs.getString("TENXUATHD")==null?new String[0]: rs.getString("TENXUATHD").split(",");
						this.ghichu = rs.getString("GHICHU2");
					}
					rs.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
					this.msg="Loi Trong Qua Trinh Lay Du Lieu ."+ e.toString();
				}
			}*/
		}
		
		if(this.nppId.trim().length() > 0 )
		{			
			query = " select PK_SEQ , NgayDonHang  " +
					" from ERP_DONDATHANGNPP " +
					" where NPP_FK = '"+ this.nppDangnhap +"' AND  NPP_DAT_FK = '" + this.nppId + "' and kho_fk in " + util.quyen_kho(this.userId) +
					"       and pk_seq in (select ddh_fk " +
					"                      from ERP_YCXUATKHONPP_DDH a inner join ERP_YCXUATKHONPP b on a.ycxk_fk = b.pk_seq " +
					"                      where b.trangthai=2 ) " +
					" and pk_seq not in (select a.DDH_FK " +
					"                    from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK = b.PK_SEQ" +
					"                    where b.TRANGTHAI =2 )" +
					" and pk_seq not in(select a.DDH_FK from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ where b.TRANGTHAI in ( 1, 2, 4 ) and b.pk_seq != " + ( this.id.trim().length() > 0 ? this.id : "-1" ) + " )  " +
					" order by NgayDonHang desc ";
		
			this.ddhRs = db.get(query);
		}
		
		String chuoi = this.ddhId;
		if(chuoi.length() > 0)
		{	
			// INIT TONG TIEN VAT
			try 
			{
				query = "select b.PK_SEQ as SPID, b.MA, b.TEN, DV.donvi, a.dongia , "+
						"  isnull(scheme,' ') as scheme , isnull(a.thuevat,0) as vat ,   " +
						"  SUM( a.soluong)  as soluong, SUM( isnull(a.chietkhau, 0)) as chietkhau "+
						"from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ   "+  	 
						" INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK  " +
						" inner join  ERP_DONDATHANGNPP c on a.dondathang_fk=c.pk_seq    "+
						"where a.dondathang_fk in ( "+ chuoi +" ) and a.dondathang_fk in (select pk_seq from ERP_DONDATHANGNPP where NPP_FK="+ this.nppDangnhap +")  " +
						"group by b.PK_SEQ , b.MA, b.TEN, DV.donvi, a.dongia , isnull(scheme,' ') , isnull(a.thuevat,0) ";

				System.out.println("Lấy sản phẩm: "+query);
				ResultSet rsLaySP = db.get(query);

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

				if(rsLaySP!= null)
				{				    	
					while(rsLaySP.next())
					{
						spID += rsLaySP.getString("SPID") + "__";
						spMA += rsLaySP.getString("MA") + "__";
						spTEN += rsLaySP.getString("TEN") + "__";
						spDONVI += rsLaySP.getString("DONVI") + "__";
						spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
						spGIANHAP += (rsLaySP.getDouble("DONGIA")) + "__";
						spCHIETKHAU += (rsLaySP.getDouble("chietkhau")) + "__";
						spSCHEME += rsLaySP.getString("scheme") + "__";
						spVAT +=  (rsLaySP.getDouble("vat")) + "__";
						spTIENTHUE +=  Math.round( ( Math.round( rsLaySP.getDouble("SOLUONG") * rsLaySP.getDouble("DONGIA") ) - rsLaySP.getDouble("chietkhau") ) * rsLaySP.getDouble("vat") / 100 ) + "__";

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

					}
				}

				query = "select khachhangKG_FK from ERP_DondathangNPP where pk_seq in ( " + chuoi + " ) ";
				ResultSet rs = db.get(query);
				if(rs.next())
				{
					this.khKGId = rs.getString("khachhangKG_FK") == null ? "" : rs.getString("khachhangKG_FK");
				}
				rs.close();

			} 
			catch (Exception e) 
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
	
	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String kbh_fk = this.kbhId;
		if(dungchungKENH.equals("1")) //LUU NHOM KENH
		{
			kbh_fk="100000";
		}
	
		String	query = "";
		
		//this.khoNhanId = "100000";
		if(this.khKGId.trim().length() <= 0)
		{
			query= "select case when sp.dvdl_fk !=(select PK_SEQ from DONVIDOLUONG where DIENGIAI like N'"+donvi+"') \n"+  
					"then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = (select PK_SEQ from DONVIDOLUONG where DIENGIAI like N'"+donvi+"') ) * AVAILABLE else AVAILABLE end as AVAILABLE, \n"+ 
					"NGAYHETHAN, SOLO \n"+
					"from NHAPP_KHO_CHITIET ct inner join SANPHAM sp on ct.sanpham_fk = sp.pk_seq \n"+
					"where KHO_FK = '"+this.khoNhanId+"' and SANPHAM_FK = '"+spMa+"' \n"+
					"and ( AVAILABLE>0 or( \n"+
					"	exists \n"+
					"	(( select hdsp.SOLUONG   \n"+
					"		 from erp_dondathangnpp_sanpham_chitiet hdsp  \n"+
					"		 where hdsp.dondathang_fk ='"+this.ddhId+"'  \n"+
					"		 and hdsp.SOLO=ct.SOLO and ct.sanpham_fk=hdsp.sanpham_fk and hdsp.NGAYHETHAN=ct.NGAYHETHAN  \n"+
					"	))) ) \n"+ 
					"and NPP_FK = '"+this.nppDangnhap+"' and NHOMKENH_FK ='"+kbh_fk+"' \n"+
					"order by NGAYHETHAN asc \n";
		}
		else
		{
			query= "select case when sp.dvdl_fk !=(select PK_SEQ from DONVIDOLUONG where DIENGIAI like N'"+donvi+"') \n"+  
					"then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = (select PK_SEQ from DONVIDOLUONG where DIENGIAI like N'"+donvi+"') ) * AVAILABLE else AVAILABLE end as AVAILABLE, \n"+ 
					"NGAYHETHAN, SOLO \n"+
					"from NHAPP_KHO_KYGUI_CHITIET ct inner join SANPHAM sp on ct.sanpham_fk = sp.pk_seq \n"+
					"where ct.khachhang_fk = '" + this.khKGId + "' and ct.isNPP = '0' and KHO_FK = '"+this.khoNhanId+"' and SANPHAM_FK = '"+spMa+"' \n"+
					"and ( AVAILABLE>0 or( \n"+
					"	exists \n"+
					"	(( select hdsp.SOLUONG   \n"+
					"		 from erp_dondathangnpp_sanpham_chitiet hdsp  \n"+
					"		 where hdsp.dondathang_fk ='"+this.ddhId+"'  \n"+
					"		 and hdsp.SOLO=ct.SOLO and ct.sanpham_fk=hdsp.sanpham_fk and hdsp.NGAYHETHAN=ct.NGAYHETHAN  \n"+
					"	))) ) \n"+ 
					"and NPP_FK = '"+this.nppDangnhap+"' and NHOMKENH_FK ='"+kbh_fk+"' \n"+
					"order by NGAYHETHAN asc \n";
		}
		
		System.out.println("----LAY SO LO ( " + spMa + " ): " + query );
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
			System.out.println("---TU DONG DE XUAT BIN - LO ( " + spMa + " ): " + query2 );
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

	
	public String getSpChonin() {
		
		return this.spChonin;
	}

	
	public void setSpChonin(String spChonin) {
		
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
					" 		 chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaixuathd, npp_fk, khachhang_fk, mauhoadon, TENKHACHHANG, DIACHI, MASOTHUE, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2," +
					"		 npp_dat_fk, nhanvien_fk) \n"	+
					
					" SELECT 1 as LOAIHOADON, isKM, DDKD_FK, KBH_FK, KHO_FK, nguoimua, '' , 1, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, '' , hinhthuctt,  "+
					"		 0, 0, 0, 0, N'Hóa đơn được tách từ hóa đơn: ' + SOHOADON, loaixuatHD, npp_fk, khachhang_fk, mauhoadon, TENKHACHHANG, DIACHI, MASOTHUE, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2, "+
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
				msg1 = "Không thể tạo tách được hóa đơn KM. Vui lòng liên hệ với admin!";
				db.getConnection().rollback();
				return msg1;
			}
			
			//XÓA CÁC DÒNG KHÔNG IN TRONG BẢNG HÓA ĐƠN CŨ CÓ CỘT CHONIN = 0
			
			query = " DELETE ERP_HOADONNPP_SP WHERE CHONIN = 0 AND HOADON_FK = "+id;
			
			if(!db.update(query))
			{
				msg1 = "Không thể tạo tách được hóa đơn KM. Vui lòng liên hệ với admin!";
				db.getConnection().rollback();
				return msg1;
			}
			
			query = " DELETE ERP_HOADONNPP_SP_CHITIET WHERE CHONIN = 0 AND HOADON_FK = "+this.id;
			
			if(!db.update(query))
			{
				msg1 = "Không thể tạo tách được hóa đơn KM. Vui lòng liên hệ với admin!";
				db.getConnection().rollback();
				return msg1;
			}
			
			query = " UPDATE ERP_DONDATHANGNPP_SANPHAM_CHITIET SET CHONIN = 1 WHERE CHONIN = 0 AND HOADON_FK = "+this.id;
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo tách được hóa đơn KM. Vui lòng liên hệ với admin!";
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
			if( donhangMUON.equals("0") )
			{
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
					   "         from ERP_XUATHOADONKM hd               "+
					   "         where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
					   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
					   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(hd.SOHOADON) = 1  ) isSd_OTC, \n" +
					   "        (select count(hd.pk_seq) as dem  "+
					   "         from ERP_HOADONNPP hd               "+
					   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
					   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
					   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(hd.SOHOADON) = 1  ) isSd_ETC \n" +
					   " FROM NHANVIEN_SOHOADON NV  \n" +
				       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + nppId + "' \n";
					  /* " FROM NHANVIEN NV  \n" +
					   " WHERE NV.pk_seq = '" + userId + "' \n";*/
				System.out.println("Câu check khai báo SHD "+query);
				ResultSet rsLayDL = db.get(query);
				
				int check_OTC = 0;
				int check_ETC = 0;
									
					while(rsLayDL.next())
					{
						kyhieuhoadon= rsLayDL.getString("kyhieu");
						sohoadontu = rsLayDL.getString("sohoadontu").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadontu") ;
						sohoadonden = rsLayDL.getString("sohoadonden").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadonden") ;
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
						   "        from ERP_XUATHOADONKM hd               "+
						   "        where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(hd.SOHOADON) = 1  ) soinMAX_OTC, \n" +
						   "       (select max(cast(sohoadon as float)) as soin_max "+
						   "        from ERP_HOADONNPP hd               "+
						   "        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(hd.SOHOADON) = 1 ) soinMAX_ETC  \n" +
						   " FROM NHANVIEN_SOHOADON NV  \n" +
					       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + nppId + "' \n";
						   /*" FROM NHANVIEN NV  \n" +
						   " WHERE NV.pk_seq = '" + userId + "' \n";*/
					
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
					msg = "Số hóa đơn tách tiếp theo đã vượt quá dãy Số hóa đơn đến được khai trong dữ liệu nền. Vui lòng vào dữ liệu nền số hóa đơn khai báo lại ! ";
					return msg;
				}
				
				 sohoadon = chuoi ;
				
			    // KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
				query = " select ( select count(*) from ERP_XUATHOADONKM where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '2' and npp_fk = '" + this.nppDangnhap + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) = 1  ) as KtraOTC, \n" +
						"        ( select count(*) from ERP_HOADONNPP where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + this.nppDangnhap + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) = 1  ) as KtraETC, \n" +
						"        ( select count(*) from ERP_HOADONNPP where cast( sohoadon as float ) < cast( ISNULL( "+ sohoadon +", -1 ) as float) and NGAYXUATHD > '"+ngayhoadon+"' and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) = 1 ) as is_KtraOTC_dk, \n"+
						"        ( select count(*) from ERP_XUATHOADONKM where cast( sohoadon as float ) < cast( ISNULL( "+ sohoadon +", -1 ) as float) and NGAYHOADON > '"+ngayhoadon+"' and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) = 1 ) as is_KtraETC_dk \n"+
						" from NHANVIEN where pk_seq = '" + userId + "' ";
				
				System.out.println("Câu kiểm tra lại SHD: "+query);
				ResultSet RsRs = db.get(query);
				int KT_OTC = 0;
				int KT_ETC = 0;
				
				int is_KT_OTC = 0;
				int is_KT_ETC = 0;
				
				while(RsRs.next())
				{
					KT_OTC = RsRs.getInt("KtraOTC") ;
					KT_ETC = RsRs.getInt("KtraETC") ;
					
					is_KT_OTC = RsRs.getInt("is_KtraOTC_dk") ;
					is_KT_ETC = RsRs.getInt("is_KtraETC_dk") ;
				}
				
				/*if(is_KT_OTC >0 || is_KT_ETC > 0) // NẾU CÓ SỐ HÓA ĐƠN LỚN HƠN MÀ NGÀY XUẤT NHỎ HƠN CỦA HÓA ĐƠN TIẾP THEO THÌ K CHO
				{
					msg = "Không thể duyệt đơn hàng. Yêu cầu check lại ngày thiết lập số hóa đơn!";
					return msg;
				}*/
					
				if(KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI TẠO
				{
					//msg = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
					msg = "Số hoá đơn tách tiếp theo đã trùng trên hệ thống hoặc đã vượt dãy quá số hóa đơn tiếp theo được khai trong dữ liệu nền ! Vui lòng kiểm tra lại ";
					System.out.println(msg);
					return msg;
				}
			}
			else // NẾU LÀ ĐƠN HÀNG MƯỢN THÌ SỐ HÓA ĐƠN LÀ 'NA' và ký hiệu hóa đơn là rỗng!
			{
				sohoadon = "NA";
				kyhieuhoadon = "";
			}
		}
		
		if(loaidonhang.equals("3")) mau = "";
			
			//TẠO MỚI HÓA ĐƠN
			
			query = " insert ERP_HOADONNPP (LOAIHOADON, isKM, DDKD_FK, KBH_FK, KHO_FK, NGUOIMUA, NGAYXUATHD, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, KYHIEU, SOHOADON, HINHTHUCTT, " +
					"		CHIETKHAU, TONGTIENAVAT, VAT, TONGTIENBVAT, GHICHU, LOAIXUATHD, NPP_FK, KHACHHANG_FK, NPP_DAT_FK, nhanvien_fk, MAUHOADON, " +
					"		MASOTHUE, TENKHACHHANG, DIACHI, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN) "+
					" select LoaiHoaDon, isKM, DDKD_FK, KBH_FK, Kho_FK, NGUOIMUA, NGAYXUATHD, 1 as TRANGTHAI, '"+getDateTime()+"' NGAYTAO, "+userId+" NGUOITAO, '"+getDateTime()+"' NGAYSUA, "+userId+" nguoisua, '"+kyhieuhoadon+"' kyhieu,'"+sohoadon+"' sohoadon, hinhthuctt , \n"+
			   		" 		 0 as chietkhau, 0 as tongtienavat, 0 as vat, 0 as tongtienbvat,N'Hóa đơn (chọn in) được tách từ hóa đơn có số chứng từ: "+this.id+"' GHICHU,  LOAIXUATHD, NPP_FK, KHACHHANG_FK, NPP_DAT_FK, nhanvien_fk,"+mau+" MAUHOADON, \n"+
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
				msg1 = "Không thể tạo tách được hóa đơn KM. Vui lòng liên hệ với admin!";
				return msg1;
			}
			
			query = " insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SoLuong_DatHang, SCHEME, CHONIN)    " +
					" select '"+hdId+"' as hoadon_fk, '"+donhangID+"' as donhang_fk, kbh_fk, kho_fk, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SOLUONG_CHUAN, DONGIA_CHUAN, SOLUONG_DATHANG, SCHEME, 1 as CHONIN "+
					" from ERP_HOADONNPP_SP_CHITIET " +
					" where CHONIN = 1 AND HOADON_FK = "+id;
			
			System.out.println("1.0.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo tách được hóa đơn KM. Vui lòng liên hệ với admin!";
				return msg1;
			}
			
			query =  " insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluong_chuan, dongia, thanhtien, chietkhau, scheme , vat, CHONIN)  " + 
					 " select "+hdId+" hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluong_chuan, dongia, thanhtien, chietkhau, scheme, vat, 1 as CHONIN " +
					 " from ERP_HOADONNPP_SP " +
					 " where CHONIN = 1 AND HOADON_FK = "+id;
			
			System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo tách được hóa đơn KM. Vui lòng liên hệ với admin!";
				return msg1;
			}
						
			//CHECK BANG TONG PHAI BANG BANG CHI TIET
			query = "select count(*) as sodong  \n" +
					"from \n" +
					"( \n" +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  \n" +
					"	from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.pk_seq \n" +
					"	where a.hoadon_fk = '" + hdId + "' \n" +
					"	group by b.pk_seq \n" +
					") \n" +
					"dh left join \n" +
					"( \n" +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  \n" +
					"	from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA \n" +
					"	where a.hoadon_fk = '" + hdId + "' \n" +
					"	group by b.pk_seq \n" +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk \n" +
					"where dh.soluong != isnull(xk.soluong, 0) \n";
			
			System.out.println("---CHECK HOA DON2: " + query);
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
						
			//XÓA CÁC DÒNG SẢN PHẨM TÁCH KHỎI HÓA ĐƠN CŨ
			query =  "delete ERP_HOADONNPP_SP WHERE CHONIN = 0 AND HOADON_FK = "+id;
			System.out.println(query);
			if(!db.update(query))
			{
				msg1 = "Không thể tạo tách được hóa đơn KM. Vui lòng liên hệ với admin!";
				return msg1;
			} 
	
			//XÓA CÁC DÒNG SẢN PHẨM TÁCH KHỎI HÓA ĐƠN CŨ
			query =  "delete ERP_HOADONNPP_SP_CHITIET WHERE CHONIN = 0 AND HOADON_FK = "+id;
			System.out.println(query);
			if(!db.update(query) )
			{
				msg1 = "Không thể tạo tách được hóa đơn KM. Vui lòng liên hệ với admin!";
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
							"               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) isSd_OTC, \n" +
							"        (select count(hd.pk_seq) as dem  "+
							"         from ERP_HOADONNPP hd               "+
							"         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							"               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							"               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) isSd_ETC \n" +
							" FROM NHANVIEN_SOHOADON NV  \n" +
						    " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + nppId + "' \n";
							/*" FROM NHANVIEN NV  \n" +
							" WHERE NV.pk_seq = '" + userId + "' \n";*/

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
								"              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) soinMAX_OTC, \n" +
								"       (select max(cast(sohoadon as float)) as soin_max "+
								"        from ERP_HOADONNPP hd               "+
								"        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
								"              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
								"              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) soinMAX_ETC  \n" +
								" FROM NHANVIEN_SOHOADON NV  \n" +
							    " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + nppId + "' \n";
								/*" FROM NHANVIEN NV  \n" +
								" WHERE NV.pk_seq = '" + userId + "' \n";*/

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
							//chuoi = "NA";
						msg1 = "Số hóa đơn tiếp tách tiếp theo đã vượt quá Số hóa đơn được khai báo trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
						return msg1;
						
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
						//msg1 = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
						msg1 = "Số hoá đơn tách tiếp theo đã có trên hệ thống! \n Vui lòng kiểm tra lại";
						System.out.println(msg1);
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
				msg1 = "Không thể tạo tách được hóa đơn . Vui lòng liên hệ với admin!";
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
				msg1 = "Không thể tạo tách được hóa đơn . Vui lòng liên hệ với admin!";
				return msg1;
			}

			query = "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, DonVi_Chuan, soluong, soluong_chuan, scheme, CHONIN, DONGIA, VAT)  \n"+
			
					 "select '" + hdId + "' as hoadon_fk, ddh_sp.sanpham_fk, ddh_sp.TEN, ddh_sp.DONVI, ddh_sp.dvCHUAN, \n"+
					 "		 ddh_sp.soluong - isnull(daxuat.soluong, 0) as soluong, ddh_sp.soluongCHUAN - isnull(daxuat.soluongCHUAN, 0) as soluongCHUAN,  \n"+
					 "		 ddh_sp.scheme, 1 as chonin, \n"+
					 "		 case when dbo.Trim( ddh_sp.scheme ) != '' then 0 else \n" +
					 "		 ( select dongia from ERP_DONDATHANGNPP_SANPHAM a where a.dondathang_fk = ddh.PK_SEQ and a.sanpham_fk = ddh_sp.SANPHAM_FK ) end as dongia,  \n" +
					 "		 ( select thuexuat from SANPHAM where PK_SEQ = ddh_sp.SANPHAM_FK ) as VAT	\n" +
					 "from ERP_DONDATHANGNPP ddh inner join \n"+
					 "( \n"+
					 "  SELECT A.dondathang_fk, A.SANPHAM_FK, A.MA, A.TEN, A.DONVI, A.dvCHUAN, A.dvDATHANG, A.scheme,  \n"+
					 " 		  SUM(soluong) as soluong, SUM( soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.dvDATHANG ) ) as soluongCHUAN \n"+
					 "   FROM ( \n"+
					 "          select dondathang_fk, SANPHAM_FK, b.MA, \n"+
					 "				N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.TENXUATHOADON,''), '__') WHERE part!= ' '  order by id desc), '') \n"+	
					 "				+', ' + N'Lot:... , Date:.... ' +	\n"+	
					 "				(case when isnull(kh.insoVISA,0) = 1 then ', '+ N'Số VISA: '+ isnull(b.VISA,'') else '' end)+ \n"+
					 "				(case when isnull(kh.innhaNK,0) = 1 then N', NNK: ' + isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.nguongocnhapkhau,''), '__') WHERE part!= ' ' order by id desc),'') + \n"+
					 "          	( case when isnull(kh.innhasx,0) = 1 then N', NSX: ' + isnull(b.NSX,'') else '' end) +' '  else '' end) as TEN  \n"+
					 "				, c.DONVI, d.DONVI as dvCHUAN, a.DVDL_FK as dvDATHANG, a.scheme,soluong  \n"+
					 "			from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ \n"+
					 "			inner join ERP_DONDATHANGNPP dh on a.dondathang_fk = dh.PK_SEQ \n"+
					 "			left join KHACHHANG kh on dh.KHACHHANG_FK = kh.PK_SEQ \n"+
					 "			inner join DONVIDOLUONG c on a.DVDL_FK = c.PK_SEQ \n"+
					 "			inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ \n"+
					 "			where dondathang_fk = '" + donhangID + "'  "+
					 "     ) a \n"+
					 "  group by A.dondathang_fk, A.SANPHAM_FK, A.MA, A.TEN, A.DONVI, A.dvCHUAN, A.dvDATHANG, A.scheme \n"+
					 /*"	select dondathang_fk, SANPHAM_FK, b.MA, b.TEN, c.DONVI, d.DONVI as dvCHUAN, a.DVDL_FK as dvDATHANG, a.scheme, "+
					 "		SUM(soluong) as soluong, SUM( soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluongCHUAN  "+
					 "	from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ "+
					 "			inner join DONVIDOLUONG c on a.DVDL_FK = c.PK_SEQ "+
					 "			inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ "+
					 "	where dondathang_fk = '" + donhangID + "'  "+
					 "	group by dondathang_fk, SANPHAM_FK, b.MA, b.TEN, c.DONVI, d.DONVI, a.DVDL_FK, a.scheme "+*/
					 ") \n"+
					 "ddh_sp on ddh.PK_SEQ = ddh_sp.dondathang_fk left join \n"+
					 "( \n"+
					 "	select c.PK_SEQ as sanpham_fk, b.scheme, sum(b.SOLUONG) as soluong, sum(b.SoLuong_Chuan) as soluongCHUAN \n"+
					 "	from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk \n"+
					 "			inner join SANPHAM c on b.MA = c.MA \n"+
					 "	where a.TRANGTHAI not in ( 3, 5 ) and a.PK_SEQ in ( select hoadonnpp_fk from ERP_HOADONNPP_DDH where DDH_FK = '" + donhangID + "' ) \n"+
					 "	group by c.PK_SEQ, b.scheme \n"+
					 ") \n"+
					 "daxuat on daxuat.SANPHAM_FK = ddh_sp.SANPHAM_FK and ddh_sp.scheme = daxuat.scheme  \n"+
					 "where ddh_sp.soluong - isnull(daxuat.soluong, 0) > 0 ";

			System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo tách được hóa đơn . Vui lòng liên hệ với admin!";
				return msg1;
			}
			
			query = "insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SCHEME, CHONIN)  \n"+
					 "select '" + hdId + "' as hoadon_fk, '" + donhangID + "' as donhang_fk, ddh.kbh_fk, ddh.kho_fk, ddh_sp.MA, ddh_sp.TEN, ddh_sp.DONVI, ddh_sp.dvCHUAN, ddh_sp.dvDATHANG, \n"+
					 "		 ddh_sp.soluong - isnull(daxuat.soluong, 0) as soluong, ddh_sp.solo, ddh_sp.ngayhethan, 0 as chietkhau, daxuat.THUEVAT as tienVAT, daxuat.DONGIA as dongia, \n"+
					 "		 ddh_sp.soluongCHUAN - isnull(daxuat.soluongCHUAN, 0) as soluongCHUAN, 0 as DonGia_Chuan, ddh_sp.scheme, 1 as scheme \n"+
					 "from ERP_DONDATHANGNPP ddh inner join \n"+
					 "( \n"+
					 "  SELECT a.dondathang_fk, a.SANPHAM_FK, a.MA, a.TEN, a.DONVI, a.dvCHUAN, a.dvDATHANG, a.LOAI, a.scheme, a.solo, a.ngayhethan, SUM(a.soluong) soluong,  \n"+
					 "		   SUM( soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.dvDATHANG ) ) as soluongCHUAN \n"+
					 "  FROM ( \n"+
					 "  		select dondathang_fk, SANPHAM_FK, b.MA, \n"+
					 "			N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.TENXUATHOADON,''), '__') WHERE part!= ' '  order by id desc), '') \n"+
					 "			+ ' , lot: ' + a.solo + ', Date: ' + \n"+
					 "          (	case LEN (CAST(DATEPART(MM, a.NGAYHETHAN ) AS NVARCHAR(50)))  \n"+
					 "			when 1 then '0'+ CAST(DATEPART(MM, a.NGAYHETHAN) AS NVARCHAR(50))  + '/' + CAST(DATEPART(YYYY, a.NGAYHETHAN) AS NVARCHAR(50))  \n"+
					 " 			when 2 then CAST(DATEPART(MM, a.NGAYHETHAN) AS NVARCHAR(50))  + '/' + CAST(DATEPART(YYYY, a.NGAYHETHAN) AS NVARCHAR(50))  \n"+
					 "			end ) + \n"+
					 "			(case when isnull(kh.insoVISA,0) = 1 then ', '+ N'Số VISA: '+ isnull(b.VISA,'') else '' end)+ \n"+
					 "			(case when isnull(kh.innhaNK,0) = 1 then N', NNK: ' + isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.nguongocnhapkhau,''), '__') WHERE part!= ' ' order by id desc),'') +  \n"+
					 "			( case when isnull(kh.innhasx,0) = 1 then N', NSX: ' + isnull(b.NSX,'') else '' end) +' '  else '' end) as TEN, c.DONVI, b.DVDL_FK as dvCHUAN, a.DVDL_FK as dvDATHANG, a.LOAI, a.scheme, \n"+
					 "			a.solo, a.ngayhethan, soluong "+
					 "			from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ "+
					 "			inner join ERP_DONDATHANGNPP dh on a.dondathang_fk = dh.PK_SEQ \n"+
					 "			left join KHACHHANG kh on dh.KHACHHANG_FK = kh.PK_SEQ \n"+
					 "			inner join DONVIDOLUONG c on a.DVDL_FK = c.PK_SEQ "+
					 "			where dondathang_fk = '" + donhangID + "'  "+
					 "  ) a \n"+
					 "  group by A.dondathang_fk, A.SANPHAM_FK, A.MA, A.TEN, A.DONVI, A.dvCHUAN, A.dvDATHANG, a.LOAI, A.scheme, a.solo, a.ngayhethan \n"+
					 
					/* "		SUM(soluong) as soluong, SUM( soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluongCHUAN  "+
					 "	from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ "+
					 "			inner join DONVIDOLUONG c on a.DVDL_FK = c.PK_SEQ "+
					 "	where dondathang_fk = '" + donhangID + "'  "+
					 "	group by dondathang_fk, SANPHAM_FK, b.MA, b.TEN, c.DONVI, b.DVDL_FK, a.DVDL_FK, a.LOAI, a.scheme, a.solo, a.ngayhethan "+*/
					 ") \n"+
					 "ddh_sp on ddh.PK_SEQ = ddh_sp.dondathang_fk left join \n"+
					 "( \n"+
					 "	select c.PK_SEQ as sanpham_fk, b.solo, b.ngayhethan, \n"+
					 "		case when dbo.trim(b.scheme) != '' then 1 else 0 end as loai, b.scheme, sum(b.SOLUONG) as soluong, sum(b.SoLuong_Chuan) as soluongCHUAN, b.DONGIA, b.THUEVAT \n"+
					 "	from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk \n"+
					 "			inner join SANPHAM c on b.MA = c.MA \n"+
					 "	where a.TRANGTHAI not in ( 3, 5 ) and a.PK_SEQ in ( select hoadonnpp_fk from ERP_HOADONNPP_DDH where DDH_FK = '" + donhangID + "' ) \n"+
					 "	group by c.PK_SEQ, b.solo, b.ngayhethan, b.scheme, b.DONGIA, b.THUEVAT	\n" +
					 ") \n"+
					 "daxuat on daxuat.SANPHAM_FK = ddh_sp.SANPHAM_FK and ddh_sp.loai = daxuat.LOAI and ddh_sp.scheme = daxuat.scheme  \n"+
					 "		and daxuat.SOLO = ddh_sp.solo and daxuat.NGAYHETHAN = ddh_sp.ngayhethan \n"+
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

			//KIỂM TRA XEM CÒN TIỀN TÍCH LŨY, CHIẾT KHẤU NẰM CHỜ HAY KHÔNG
			query = "  insert ERP_HOADONNPP_CHIETKHAU( HOADON_FK, DIENGIAI, GIATRI, LOAI, scheme, ddh_fk, VAT, tienchuaVAT, sohopdong, tungay, denngay, kmcp_fk )  " + 
					"  select '" + hdId + "' as HOADON_FK, DT2.DIENGIAI, DT2.GIATRI ,  " + 
					"  		DT2.LOAI, DT2.scheme, DT2.ddh_fk, DT2.VAT, DT2.GIATRI / ( 1 + DT2.VAT / 100.0 ) as tienchuaVAT, DT2.sohopdong, DT2.tungay, DT2.denngay, DT2.kmcp_fk " + 
					"  from " + 
					"  ( " + 
					"  	select DT.HOADON_FK, DT.DIENGIAI,  " + 
					"  		DT.GIATRI_GOC - isnull( (  " + 
					"  							select sum( giatri ) from ERP_HOADONNPP_CHIETKHAU where loai = 0 and ddh_fk = DT.ddh_fk  " + 
					"  								and HOADON_FK in ( select PK_SEQ from ERP_HOADONNPP where TRANGTHAI not in ( 3, 5 ) )	 ), 0) as GIATRI ,  " + 
					"  		DT.LOAI, DT.scheme, DT.ddh_fk, DT.VAT, DT.tienchuaVAT, DT.sohopdong, DT.tungay, DT.denngay, DT.kmcp_fk  " + 
					"  	from " + 
					"  	( " + 
					"  		select top(1) '' as HOADON_FK, DIENGIAI,  " + 
					"  			(	select sum(TONGGIATRI) from ERP_DONDATHANGNPP_TICHLUY_TRATL where loai = 0 and DONDATHANGID = a.ddh_fk ) as GIATRI_GOC, LOAI, scheme, ddh_fk,  " + 
					"  			a.VAT, tienchuaVAT, sohopdong, tungay, denngay, kmcp_fk " + 
					"  		from ERP_HOADONNPP_CHIETKHAU a inner join ERP_HOADONNPP b on a.HOADON_FK = b.PK_SEQ " + 
					"  		where a.loai = 0 and ddh_fk = '" + donhangID + "'  and b.TRANGTHAI not in ( 3, 5 ) " + 
					"  	) " + 
					"  	DT " + 
					"  ) " + 
					"  DT2 where DT2.GIATRI > 0 ";
			if(!db.update(query))
			{
				msg ="Không thể tạo tách được hóa đơn . Vui lòng liên hệ với admin!";
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
			/*geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
			msg1 = util.CapNhat_ThanhTien_HoaDon(db, hdId);
			if( msg1.trim().length() > 0 )
				return msg1;*/
			
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
					/*query =" select isnull( mauhoadon, 1 ) as mauhoadon from KHACHHANG where PK_SEQ ='"+khachhangID+"'";
					System.out.println("AAAAA:"+ query);
					ResultSet mauHDrs = db.get(query);

					if(mauHDrs != null)
					{
						while(mauHDrs.next())
						{
							mau = mauHDrs.getString("mauhoadon");
						}
						mauHDrs.close();
					}*/

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
							"               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) isSd_OTC, \n" +
							"        (select count(hd.pk_seq) as dem  "+
							"         from ERP_HOADONNPP hd               "+
							"         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							"               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							"               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) isSd_ETC \n" +
							" FROM NHANVIEN_SOHOADON NV  \n" +
						    " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + nppId + "' \n";
							/*" FROM NHANVIEN NV  \n" +
							" WHERE NV.pk_seq = '" + userId + "' \n";*/

					System.out.println("Câu check khai báo SHD tach "+query);
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
								"              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) soinMAX_OTC, \n" +
								"       (select max(cast(sohoadon as float)) as soin_max "+
								"        from ERP_HOADONNPP hd               "+
								"        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
								"              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
								"              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) soinMAX_ETC  \n" +
								" FROM NHANVIEN_SOHOADON NV  \n" +
							    " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + nppId + "' \n";
								/*" FROM NHANVIEN NV  \n" +
								" WHERE NV.pk_seq = '" + userId + "' \n";*/

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
							//chuoi = "NA";
						msg1 = "Số hóa đơn tách tiếp theo đã vượt quá dãy Số hóa đơn được khai trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại hoặc liên hệ với admin ! ";

						System.out.println("ABBCB:"+msg1);
						return msg1;
					}

					sohoadon = chuoi ;

					// KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
					query = " select (select count(*) from HOADON where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) = 1) as KtraOTC, " +
							"        (select count(*) from ERP_HOADONNPP where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) = 1) as KtraETC " +
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
						msg1 = "Số hoá đơn tiếp theo "+sohoadon+" đã có trên hệ thống! \n Vui lòng kiểm tra lại ";
						//msg1 = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
						System.out.println(msg1);
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
				msg1 = "Không thể tách hóa đơn. Vui lòng liên hệ admin ";
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
				msg1 = "Không thể tách hóa đơn. Vui lòng liên hệ admin ";
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
				msg1 = "Không thể tách hóa đơn. Vui lòng liên hệ admin ";
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
				msg1 = "Không thể tách hóa đơn. Vui lòng liên hệ admin ";
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
				msg1 = "Không thể tách hóa đơn. Vui lòng liên hệ admin ";
				return msg1;
			}
			
			//TINH LAI HOA DON CU TOTAL
			query = "delete ERP_HOADONNPP_SP where hoadon_fk = '" + id + "' " + 
					" and sanpham_fk not in ( select pk_seq from SANPHAM where ma in ( select MA from ERP_HOADONNPP_SP_CHITIET where hoadon_fk = '" + id + "' )  ) ";
			if( !db.update(query) )
			{
				msg1 = "Không thể tách hóa đơn. Vui lòng liên hệ admin ";
				return msg1;
			}
			
			//COT SO LUONG TOTAL CU CO THE BI THAY DOI, NEN PHAI CAP NHAT LAI
			query =  "update hd set hd.SOLUONG = ( select SUM( SOLUONG ) from ERP_HOADONNPP_SP_CHITIET where hoadon_fk = hd.HOADON_FK and MA = sp.MA and scheme = hd.SCHEME ),  "+
					 "			  hd.SoLuong_Chuan = ( select SUM( SoLuong_Chuan ) from ERP_HOADONNPP_SP_CHITIET where hoadon_fk = hd.HOADON_FK and MA = sp.MA and scheme = hd.SCHEME ) "+
					 "from ERP_HOADONNPP_SP hd inner join SANPHAM sp on hd.SANPHAM_FK = sp.PK_SEQ "+
					 "where HOADON_FK = '" + id + "' ";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tách hóa đơn. Vui lòng liên hệ admin ";
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
		
		//String query = "";
		/*
		if(this.khId.length() > 0)
		{
			query = "SELECT * \n " +
					"FROM KHACHHANG_THONGTINHOADON \n " +
					"WHERE KHACHHANG_FK = " + this.khId + " AND ACTIVE = '1' \n " ;
		}
		else if(this.nppId.trim().length() >0)
		{
			query = "SELECT * \n " +
					"FROM NPP_THONGTINHOADON \n " +
					"WHERE NPP_FK = " + sthis.nppId + " AND ACTIVE = '1' \n " ;
		}
		
		System.out.println(":: LAY THONG TIN KHACH HANG: " + query );*/
		//return this.db.get(query);
		
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
	
	Object loainhanvien;
	Object doituongId;
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}
	
	public static void main(String[] arg)
	{
		ErpHoadontaichinhNPP hd = new ErpHoadontaichinhNPP();
		
		String data = "Xisat Viêm Mũi 75ml, Việt Nam, (1chai\\hộp)  , lot: 0061215, Date: 12/2015";
		int totalSODONG = hd.DemSoDong(data, 40);
		System.out.println(":::: TOTAL SO DONG: " + totalSODONG  );
	}
	
	public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}

}
