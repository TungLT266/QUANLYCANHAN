package geso.traphaco.center.beans.dondathang.imp;

import geso.traphaco.center.beans.dondathang.IErpDuyetddh;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.WebService;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ErpDuyetddh implements IErpDuyetddh
{
	String userId;
	String id;
	
	String ma;
	String ngayyeucau;
	String ngaydenghi;
	String ghichu;

	String msg;
	String trangthai;
	
	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn hàng khác
	String chietkhau;
	String vat;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String nppId;
	ResultSet nppRs;
	
	String dvkdId;
	ResultSet dvkdRs;
	
	String gsbhId;
	ResultSet gsbhRs;
	
	String ddkdId;
	ResultSet ddkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	ResultSet dvtRs;
	
	String schemeId;
	ResultSet schemeRs;
	
	Hashtable<String, String> scheme_soluong;
	Hashtable<String, String> sanpham_soluong;
	Hashtable<String, String> sanpham_soluongDAXUAT;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spChietkhau;
	String[] spVAT;
	String[] spSCheme;
	String[] spTrongluong;
	String[] spThetich;
	
	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	String[] spIskhongthue;
	ResultSet congnoRs;
	
	String[] spTungay;
	String[] spDenngay;
	
	String tungay;
	String denngay;
	String ETC;
	
	dbutils db;
	
	public ErpDuyetddh()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.schemeId = "";
		this.ma = "";
		this.gsbhId = "";
		this.ddkdId = "";
		
		this.scheme_soluong = new Hashtable<String, String>();
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongDAXUAT = new Hashtable<String, String>();
		this.ETC = "";
		
		this.iskm="0";
		this.tungay = "";
		this.denngay = "";
		this.db = new dbutils();
	}
	
	public ErpDuyetddh(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.schemeId = "";
		this.ma = "";
		this.gsbhId = "";
		this.ddkdId = "";

		this.scheme_soluong = new Hashtable<String, String>();
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongDAXUAT = new Hashtable<String, String>();
		this.ETC = "";
		
		this.iskm="0";
		this.tungay = "";
		this.denngay = "";
		this.db = new dbutils();
	}
	public String[] getSpIskhongthue() {
		return spIskhongthue;
	}

	public void setSpIskhongthue(String[] spIskhongthue) {
		this.spIskhongthue = spIskhongthue;
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

	public String getNgayyeucau() 
	{
		return this.ngayyeucau;
	}

	public void setNgayyeucau(String ngayyeucau) 
	{
		this.ngayyeucau = ngayyeucau;
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

	public void createRs() 
	{
		if( this.dvkdId == null )
			this.dvkdId = "";
		if( this.kbhId == null )
			this.kbhId = "";
		
		this.khoNhanRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where trangthai = '1' ");
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1'");
		if( this.ETC.equals("0") )
			this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1'");
		else
			this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and pk_seq = '100052' ");
		
		this.gsbhRs = db.get("select PK_SEQ, TEN from GIAMSATBANHANG where trangthai = '1' ");
		String query = "select pk_seq, TEN from DAIDIENKINHDOANH where 1=1 ";
		if(this.gsbhId.trim().length() > 0)
			query += " and pk_seq in ( select ddkd_fk from DDKD_GSBH where GSBH_FK = '" + this.gsbhId + "' ) ";
		this.ddkdRs = db.get(query);
		
		/*if( this.ETC.equals("0") )
		{
			query = "select PK_SEQ, MAFAST + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk = '1' ";
			if(this.loaidonhang.equals("0"))
				query += " and loaiNPP = '4' "; //BÁN CHO ĐỐI TÁC
			if(this.dvkdId.trim().length() > 0)
				query += " and pk_seq in ( select NPP_FK from NHAPP_NHACC_DONVIKD where NCC_DVKD_FK in ( select PK_SEQ from NHACUNGCAP_DVKD where DVKD_FK = '" + this.dvkdId + "' ) ) ";
			if(this.kbhId.trim().length() > 0)
				query += " and pk_seq in ( select NPP_FK from NHAPP_KBH where KBH_FK = '" + this.kbhId + "' ) ";
		}
		else
		{
			query = "select PK_SEQ, MAFAST + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and isKHACHHANG = '1' ";
		}*/
		
		query = "select PK_SEQ, MAFAST + ' - ' + TEN as TEN from NHAPHANPHOI where pk_seq = '" + this.nppId + "' ";
		this.nppRs = db.get(query);
		
		if(this.nppId.trim().length() > 0)
		{
			if( this.ETC.equals("0") )
			{
				query = "select a.PK_SEQ as nppId, d.DVKD_FK, b.KBH_FK  " +
					    "From NhaPhanPhoi a left join nhapp_kbh b on b.NPP_FK = a.PK_SEQ left join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK  " +
					    "	left join NHACUNGCAP_DVKD d on d.PK_SEQ = c.NCC_DVKD_FK   " +
					    "where a.pk_Seq = '" + this.nppId + "' ";
				ResultSet rsInfo = db.get(query);
				if(rsInfo != null)
				{
					try 
					{
						if(rsInfo.next())
						{
							if(this.dvkdId.trim().length() <= 0)
								this.dvkdId = rsInfo.getString("DVKD_FK");
							if(this.kbhId.trim().length() <= 0 )
								this.kbhId = rsInfo.getString("KBH_FK");
						}
						rsInfo.close();
					} 
					catch (Exception e) {}
				}
			}
			else //Tự load GSBH + DDKD
			{
				this.kbhId = "100052";
				this.dvkdId = "100001";
				
				query = "select DDKD_FK, ( select top(1) gsbh_fk from DDKD_GSBH where ddkd_fk = a.ddkd_fk ) as gsbh_fk " +
					    "From NhaPhanPhoi a  " +
					    "where pk_Seq = '" + this.nppId + "' ";
				ResultSet rsInfo = db.get(query);
				if(rsInfo != null)
				{
					try 
					{
						if(rsInfo.next())
						{
							this.ddkdId = rsInfo.getString("DDKD_FK");
							this.gsbhId = rsInfo.getString("gsbh_fk");
						}
						rsInfo.close();
					} 
					catch (Exception e) {}
				}
			}
			
			query = "  SELECT sum( HD.TONGTIENAVAT - ISNULL( TT.DATHANHTOAN, 0 ) ) as tongno, 0 as notronghan, 0 as noquahan, 0 as noxau,  " + 
					"  		 DATEDIFF(DD, MIN( HD.NGAYXUATHD ), GETDATE() ) as songaynoxanhat, MIN( HD.NGAYXUATHD ) as ngaynoxanhat	  " + 
					"  FROM ERP_HOADON HD 	left join  " + 
					"  	( " + 
					"  		SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN    	  " + 
					"  		FROM   	  " + 
					"  		( 	 					  " + 
					"  			SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN     		  " + 
					"  			FROM ERP_THUTIEN_HOADON TTHD    " + 
					"  				INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ    		  " + 
					"  			WHERE  TT.TRANGTHAI NOT IN (2)	   " + 
					"  	  " + 
					"  			GROUP BY HOADON_FK    	  " + 
					"  		)   " + 
					"  		HOADONDATT  GROUP BY HOADON_FK    " + 
					"  	 )	 " + 
					"  	 TT on HD.PK_SEQ = TT.HOADON_FK  " + 
					"  WHERE  HD.TRANGTHAI in ( 2, 4 )  AND HD.NPP_FK = '" + this.nppId + "' and  HD.TONGTIENAVAT - ISNULL( TT.DATHANHTOAN, 0 ) > 0 ";
			
			System.out.println("CONG NO: " + query);
			this.congnoRs = db.get(query);
			
			
		}
		
		if(this.loaidonhang.equals("2"))
		{
			query = "select b.PK_SEQ, b.SCHEME + ', ' + b.DIENGIAI as SCHEME  " +
					"from DUYETTRAKHUYENMAI a inner join TIEUCHITHUONGTL b on a.CTKM_FK = b.PK_SEQ order by b.pk_seq desc ";
			this.schemeRs = db.get(query);
			
			if(this.schemeId.trim().length() > 0 && this.nppId.trim().length() > 0 )
			{
				query = "select a.PK_SEQ, a.SCHEME, a.DIENGIAI, SUM(b.thuong) as tongLUONG  " +
						"from TIEUCHITHUONGTL a inner join DUYETTRAKHUYENMAI_KHACHHANG b on a.PK_SEQ = b.duyetkm_fk " +
						"where a.PK_SEQ in ( " + this.schemeId + " ) and b.nppId = '" + this.nppId + "' and donvi = '2' " +
						"group by a.PK_SEQ, a.SCHEME, a.DIENGIAI";
				
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String _schemeID = "";
						String _scheme = "";
						String _diengiai = "";
						String _tongluong = "";
						
						NumberFormat format = new DecimalFormat("#,###,###");
						while(rs.next())
						{
							_schemeID += rs.getString("PK_SEQ") + "__";
							_scheme += rs.getString("SCHEME") + "__";
							_diengiai += rs.getString("DIENGIAI") + "__";
							_tongluong += format.format(rs.getDouble("tongLUONG")) + "__";
						}
						rs.close();
						
						if(_scheme.trim().length() > 0)
						{
							_schemeID = _schemeID.substring(0, _schemeID.length() -2 );
							this.spId = _schemeID.split("__");
							
							_scheme = _scheme.substring(0, _scheme.length() -2 );
							this.spMa = _scheme.split("__");
							
							_diengiai = _diengiai.substring(0, _diengiai.length() -2 );
							this.spTen = _diengiai.split("__");
							
							_tongluong = _tongluong.substring(0, _tongluong.length() -2 );
							this.spSoluong = _tongluong.split("__");
							
						}
					} 
					catch (Exception e) {}
				}
			}
		}
		else if(loaidonhang.equals("1"))
		{
			query = "select PK_SEQ, SCHEME from CTKHUYENMAI where  KHO_FK = '100001' order by DENNGAY desc ";
			this.schemeRs = db.get(query);
			
			if(this.schemeId.trim().length() > 0 && this.nppId.trim().length() > 0 )
			{
				/*query = "select c.PK_SEQ, c.SCHEME, c.DIENGIAI, SUM(a.SOLUONG) as tongLUONG  " +
						"from DONHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " +
						"	inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
						"where a.CTKMID in (" + this.schemeId + ") and SPMA is not null " +
								"and a.DONHANGID in ( select pk_seq from DONHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' ) " +
						"group by c.PK_SEQ, c.SCHEME, c.DIENGIAI";*/
				
				query = " select UNGHANG.PK_SEQ, UNGHANG.SCHEME, UNGHANG.DIENGIAI,  " +
						" 	UNGHANG.tongLUONG - ISNULL(DUYET.SOLUONG, 0) as tongLUONG " +
						" from " +
						" ( " +
						" 	select c.PK_SEQ, c.SCHEME, c.DIENGIAI, SUM(a.SOLUONG) as tongLUONG  " +
						" 	from DONHANG_CTKM_TRAKM a inner join ERP_SANPHAM b on a.SPMA = b.MA " +
						" 			inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
						" 	where a.CTKMID in ( " + this.schemeId + " ) and SPMA is not null " +
						"					and a.DONHANGID in ( select pk_seq from DONHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' ) " +
						" 	group by c.PK_SEQ, c.SCHEME, c.DIENGIAI " +
						" ) " +
						" UNGHANG left join " +
						" ( " +
						" 	select b.ctkm_fk, SUM(b.soluong) as soluong  " +
						" 	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk " +
						" 	where a.LoaiDonHang = '1' and a.TRANGTHAI != 3 and b.ctkm_fk in ( " + this.schemeId + " ) and a.NPP_FK = '" + this.nppId + "'  " + ( ( this.id.trim().length() > 0 ) ? " AND a.pk_seq != '" + this.id + "' " : "" ) +
						" 	group by b.ctkm_fk " +
						" ) " +
						" DUYET on UNGHANG.PK_SEQ = DUYET.ctkm_fk " +
						" where UNGHANG.tongLUONG - ISNULL(DUYET.SOLUONG, 0) > 0 ";
				
				System.out.println("INIT SP: " + query);
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String _schemeID = "";
						String _scheme = "";
						String _diengiai = "";
						String _tongluong = "";
						
						NumberFormat format = new DecimalFormat("#,###,###");
						while(rs.next())
						{
							System.out.println("--TOI DAY: " + rs.getString("PK_SEQ") );
							_schemeID += rs.getString("PK_SEQ") + "__";
							_scheme += rs.getString("SCHEME") + "__";
							_diengiai += rs.getString("DIENGIAI") + "__";
							_tongluong += format.format(rs.getDouble("tongLUONG")) + "__";
						}
						rs.close();
						
						if(_scheme.trim().length() > 0)
						{
							_schemeID = _schemeID.substring(0, _schemeID.length() -2 );
							this.spId = _schemeID.split("__");
							
							_scheme = _scheme.substring(0, _scheme.length() -2 );
							this.spMa = _scheme.split("__");
							
							_diengiai = _diengiai.substring(0, _diengiai.length() -2 );
							this.spTen = _diengiai.split("__");
							
							_tongluong = _tongluong.substring(0, _tongluong.length() -2 );
							this.spSoluong = _tongluong.split("__");
							
						}
						
						System.out.println("---SCHEME: " + _scheme);
					} 
					catch (Exception e) { e.printStackTrace(); }
					
					
				}
			}
		}
		else if(this.loaidonhang.equals("3"))
		{
			query = "select PK_SEQ, SCHEME from CTTRUNGBAY order by pk_seq desc ";
			this.schemeRs = db.get(query);
			
			if(this.schemeId.trim().length() > 0 && this.nppId.trim().length() > 0 )
			{
				/*query = " select d.PK_SEQ, d.SCHEME, d.DIENGIAI,  " +
						" 	b.xuatduyet * ( select SUM(isnull(TONGLUONG, 0)) from TRATRUNGBAY where PK_SEQ in ( select TRATRUNGBAY_FK from CTTB_TRATB where CTTRUNGBAY_FK = d.PK_SEQ ) ) as tongluong " +
						" from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK " +
						" 		inner join DANGKYTRUNGBAY c on a.CTTRUNGBAY_FK  = c.CTTRUNGBAY_FK " +
						" 		inner join CTTRUNGBAY d on c.CTTRUNGBAY_FK = d.PK_SEQ " +
						" where c.TRANGTHAI = '1' and a.NPP_FK = '" + this.nppId + "' and b.XUATDUYET != 0 and d.PK_SEQ in ( 2, 3 ) ";*/
				
				query = " select UNGHANG.PK_SEQ, UNGHANG.SCHEME, UNGHANG.DIENGIAI,  UNGHANG.tongLUONG - ISNULL(DUYET.SOLUONG, 0) as tongLUONG " +
						" from " +
						" ( " +
						"  select d.PK_SEQ, d.SCHEME, d.DIENGIAI,   " +
						"  	b.xuatduyet * ( select SUM(isnull(TONGLUONG, 0)) from TRATRUNGBAY where PK_SEQ in ( select TRATRUNGBAY_FK from CTTB_TRATB where CTTRUNGBAY_FK = d.PK_SEQ ) ) as tongluong  " +
						"  from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK  " +
						"  		inner join DANGKYTRUNGBAY c on a.CTTRUNGBAY_FK  = c.CTTRUNGBAY_FK  " +
						"  		inner join CTTRUNGBAY d on c.CTTRUNGBAY_FK = d.PK_SEQ  " +
						"  where c.TRANGTHAI = '1' and a.NPP_FK = '" + this.nppId + "' and b.XUATDUYET != 0 and d.PK_SEQ in ( " + this.schemeId + " )  " +
						" ) " +
						" UNGHANG left join  " +
						" ( " +
						" 	select b.cttb_fk, SUM(b.soluong) as soluong   " +
						" 	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk " +
						" 	where a.LoaiDonHang = '3' and a.TRANGTHAI != 3 and b.cttb_fk in ( " + this.schemeId + " ) and a.NPP_FK = '" + this.nppId + "' " + ( ( this.id.trim().length() > 0 ) ? " AND a.pk_seq != '" + this.id + "' " : "" ) +
						" 	group by b.cttb_fk  " +
						" ) " +
						" DUYET on UNGHANG.PK_SEQ = DUYET.cttb_fk " +
						" where UNGHANG.tongLUONG - ISNULL(DUYET.SOLUONG, 0) > 0 ";
				
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String _schemeID = "";
						String _scheme = "";
						String _diengiai = "";
						String _tongluong = "";
						
						NumberFormat format = new DecimalFormat("#,###,###");
						while(rs.next())
						{
							_schemeID += rs.getString("PK_SEQ") + "__";
							_scheme += rs.getString("SCHEME") + "__";
							_diengiai += rs.getString("DIENGIAI") + "__";
							_tongluong += format.format(rs.getDouble("tongLUONG")) + "__";
						}
						rs.close();
						
						if(_scheme.trim().length() > 0)
						{
							_schemeID = _schemeID.substring(0, _schemeID.length() -2 );
							this.spId = _schemeID.split("__");
							
							_scheme = _scheme.substring(0, _scheme.length() -2 );
							this.spMa = _scheme.split("__");
							
							_diengiai = _diengiai.substring(0, _diengiai.length() -2 );
							this.spTen = _diengiai.split("__");
							
							_tongluong = _tongluong.substring(0, _tongluong.length() -2 );
							this.spSoluong = _tongluong.split("__");
							
						}
					} 
					catch (Exception e) {}
				}
			}
		}
		
	}

	private void initSANPHAM() 
	{
		String query =  
					" select b.MA, b.TEN, DV.donvi, a.soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich    " +	
					"	, ISNULL ( (select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018), 0 )   as spQuyDoi,case when  dbo.trim(isnull(a.scheme, ' '))='' then ' ' else isnull(a.scheme, ' ') end as scheme, isnull(a.thueVAT, 0) as thueVAT, isnull(a.tungay, '') as tungay, isnull(a.denngay, '') as denngay,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE "+
					" from ERP_Dondathang_SANPHAM a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
					"where a.Dondathang_FK = '" + this.id + "' ";
		
		System.out.println("---INIT SP1 : " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###.####");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spTHUEVAT = "";
				String spSCHEME = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spTUNGAY = "";
				String spDENNGAY = "";
				String iskht ="";
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spTHUEVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					spSCHEME += spRs.getString("scheme") + "__";
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					
					if(spRs.getString("tungay").trim().length() > 0)
						spTUNGAY += spRs.getString("tungay") + "__";
					else
						spTUNGAY += " __";
					
					if(spRs.getString("denngay").trim().length() > 0)
						spDENNGAY += spRs.getString("denngay") + "__";
					else
						spDENNGAY += " __";
					
					System.out.println("scheme la --"+spSCHEME +"--\n");
					iskht += spRs.getString("IS_KHONGTHUE") + "__";
				}
				spRs.close();
				
				//INIT SP KHUYEN MAI
				query = "select isnull(b.MA, ' ') as MA, isnull(b.TEN, ' ') as TEN, isnull(c.DONVI, ' ') as donvi, d.SCHEME, isnull(a.soluong, 0) as soluong, a.tonggiatri, " +
						"		ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich " +
						"	, ISNULL( (select soluong1 / soluong2 from QUYCACH where sanpham_fk=b.pk_Seq and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018), 0)   as spQuyDoi "+
						"from ERP_DONDATHANG_CTKM_TRAKM a left join ERP_SANPHAM b on a.SPMA = b.MA  " +
						"	left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  " +
						"	inner join CTKHUYENMAI d on a.ctkmID = d.PK_SEQ " +
						"where a.dondathangID = '" + this.id + "' ";
				
				System.out.println("___INIT KM: " + query);
				spRs = db.get(query);
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("tonggiatri")) + "__";
					spCHIETKHAU += "0__";
					spTHUEVAT += "0__";
					spSCHEME += spRs.getString("SCHEME") + "__";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					
					spTUNGAY += " __";
					spDENNGAY += " __";
					System.out.println("scheme la --"+spSCHEME +"--\n");
					iskht += "0__";
				}
				spRs.close();
				
				
				//INIT CK NỘI BỘ
				/*if( this.loaidonhang.equals("5") )
				{
					query =  " select ' ' as MA, ' ' as TEN, ' ' as donvi, DIENGIAI as SCHEME, 1 as soluong, THANHTIEN as tonggiatri,  "+
							 " 		1 as trongluong, 1 as thetich, 1 as spQuyDoi  "+
							 " from ERP_DONDATHANG_CHIETKHAU "+
							 " where DONDATHANG_FK = '" + this.id + "' ";
					
					System.out.println("___INIT CK: " + query);
					spRs = db.get(query);
					while(spRs.next())
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spGIANHAP += formater.format(spRs.getDouble("tonggiatri")) + "__";
						spCHIETKHAU += "0__";
						spTHUEVAT += "0__";
						spSCHEME += spRs.getString("SCHEME") + "__";
						
						spTRONGLUONG += spRs.getString("trongluong") + "__";
						spTHETICH += spRs.getString("thetich") + "__";
						
						spTUNGAY += " __";
						spDENNGAY += " __";
					}
					spRs.close();
				}*/
				
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spTHUEVAT = spTHUEVAT.substring(0, spTHUEVAT.length() - 2);
					this.spVAT = spTHUEVAT.split("__");
					
					spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
					this.spSCheme = spSCHEME.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					this.spTungay = spTUNGAY.split("__");
					
					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					this.spDenngay = spDENNGAY.split("__");
					iskht = iskht.substring(0, iskht.length() - 2);
					this.spIskhongthue = iskht.split("__");
					
				}
				
				//INIT CHIET KHAU
				if(this.loaidonhang.equals("0") || this.loaidonhang.equals("5") )
				{
					query = "select DIENGIAI, GIATRI, LOAI from ERP_DONDATHANG_CHIETKHAU where DONDATHANG_FK = '" + this.id + "' order by pk_seq asc";
					System.out.println("[INIT_CK]"+query);
					ResultSet rsCK = db.get(query);
					if(rsCK != null)
					{
						String dkCK_diengiai = "";
						String dkCK_giatri = "";
						String dkCK_loai = "";
						
						while(rsCK.next())
						{
							dkCK_diengiai += rsCK.getString("DIENGIAI") + "__";
							dkCK_giatri += formater.format(rsCK.getDouble("GIATRI")) + "__";
							dkCK_loai += rsCK.getString("LOAI") + "__";
						}
						rsCK.close();
						
						if(dkCK_diengiai.trim().length() > 0)
						{
							dkCK_diengiai = dkCK_diengiai.substring(0, dkCK_diengiai.length() - 2);
							this.dhCk_diengiai = dkCK_diengiai.split("__");
							
							dkCK_giatri = dkCK_giatri.substring(0, dkCK_giatri.length() - 2);
							this.dhCk_giatri = dkCK_giatri.split("__");
							
							dkCK_loai = dkCK_loai.substring(0, dkCK_loai.length() - 2);
							this.dhCk_loai = dkCK_loai.split("__");
						}
						
					}
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
	}

	public void init() 
	{
		String query = "select isnull(isdhkhac,0) isdhkhac, isnull(isingia,0) isingia,ngaydonhang, ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, kho_fk, isnull(chietkhau, 0) as chietkhau, vat, loaidonhang,iskm, " +
						" isnull( tungay, '' ) as tungay, isnull( denngay, '' ) as denngay, isnull(isETC, 0) as isETC, gsbh_fk, ddkd_fk, "+
						" 	( select MaHopDong from ERP_HOPDONG where PK_SEQ = a.hopdong_fk ) as mahopdong	" +
						"from ERP_Dondathang a where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngaydonhang");
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.nppId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.iskm = rs.getString("iskm") == null ? "0" : rs.getString("iskm");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.ETC = rs.getString("isETC");
					
					this.ma = rs.getString("mahopdong") == null ? "" : rs.getString("mahopdong");
					this.gsbhId = rs.getString("gsbh_fk") == null ? "" : rs.getString("gsbh_fk");
					this.ddkdId = rs.getString("ddkd_fk") == null ? "" : rs.getString("ddkd_fk");
				}
				rs.close();
				
				//INIT SO LUONG CHI TIET
				/*query = "select (select MA from SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, ngayhethan, ngaynhapkho, isnull( mame, '' ) as mame, isnull( mathung, '' ) as mathung, soluong, isnull(scheme, ' ') as scheme " +
						"from ERP_DONDATHANG_SANPHAM_CHITIET a where dondathang_fk = '" + this.id + "'";
				System.out.println("---INIT SP: " + query);
				rs = db.get(query);
				if(rs != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						System.out.println("---KEY BEAN: " + ( rs.getString("spMA") + "__" + rs.getString("scheme") + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("ngaynhapkho") ) );
						sp_soluong.put( rs.getString("spMA") + "__" + rs.getString("scheme") + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("ngaynhapkho")+ "__" + rs.getString("mame") + "__" + rs.getString("mathung"), rs.getString("soluong") );
					}
					rs.close();
					
					this.sanpham_soluong = sp_soluong;
				}*/
				
				query = "select (select MA from ERP_SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, ngayhethan, mathung, mame, maphieu, marq, isnull(nsx.ma,'') nsx, ngaynhapkho, isnull( bin.ma, '' ) as vitri, isnull(scheme, ' ') as scheme, sum(soluong)  as soluong  " +
						"from ERP_DONDATHANG_SANPHAM_CHITIET a left join ERP_BIN bin on a.bin_fk = bin.pk_seq " +
						"left join erp_nhasanxuat nsx on a.nsx_fk = nsx.pk_seq " +
						"where dondathang_fk = '" + this.id + "' " + 
						"group by a.sanpham_fk, solo, ngayhethan, mathung, mame, maphieu, bin.ma, marq, nsx.ma, ngaynhapkho, scheme  ";
				System.out.println("---INIT SP CHI TIET: " + query);
				rs = db.get(query);
				if(rs != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						/*System.out.println("---KEY BEAN: " + ( rs.getString("spMA") + "__" + rs.getString("scheme") + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("vitri") + "__" + rs.getString("mathung") + "__" + rs.getString("mame") ) );
						sp_soluong.put( rs.getString("spMA") + "__" + rs.getString("scheme") + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("vitri") + "__" + rs.getString("mathung") + "__" + rs.getString("mame"), rs.getString("soluong") );*/
						String scheme = ( rs.getString("scheme").trim().length() <= 0 ? " " : rs.getString("scheme") );
						
						String key = rs.getString("spMA") + "__" + scheme + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("vitri") + "__" + rs.getString("mathung") + "__" + rs.getString("mame") + "__" + rs.getString("maphieu") + "__" + rs.getString("marq") + "__" + rs.getString("nsx") + "__" + rs.getString("ngaynhapkho"); 
						
						System.out.println( "::: KEY BEAN:  " + key + " -- SLG: " + rs.getString("soluong") );
						sp_soluong.put( key , rs.getString("soluong") );
					}
					rs.close();
					
					this.sanpham_soluong = sp_soluong;
					//this.sanpham_soluongDAXUAT = sp_soluong;
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		
		this.createRs();
		
		this.initSANPHAM();
		
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			
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

	
	public String[] getSpGianhap() {
		
		return this.spGianhap;
	}

	
	public void setSpGianhap(String[] spGianhap) {
		
		this.spGianhap = spGianhap;
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

	
	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getChietkhau() {
		
		return this.chietkhau;
	}

	
	public void setChietkhau(String chietkhau) {
		
		this.chietkhau = chietkhau;
	}

	
	public String getVat() {
		
		return this.vat;
	}

	
	public void setVat(String vat) {
		
		this.vat = vat;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	public String getNgaydenghi() {
		
		return this.ngaydenghi;
	}

	public void setNgaydenghi(String ngaydenghi) {
		
		this.ngaydenghi = ngaydenghi;
	}

	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {
		
		this.dvtRs = dvtRs;
	}

	
	public String getSchemeId() {
		
		return this.schemeId;
	}

	
	public void setSchemeId(String kbhId) {
		
		this.schemeId = kbhId;
	}

	
	public ResultSet getSchemeRs() {
		
		return this.schemeRs;
	}

	
	public void setSchemeRs(ResultSet schemeRs) {
		
		this.schemeRs = schemeRs;
	}

	public ResultSet getSpTheoScheme(String scheme, String tongluong) 
	{
		String query =  "select c.MA, c.TEN, d.donvi, '' as soluong " +
						"from TIEUCHITHUONGTL_SPTRA a inner join TIEUCHITHUONGTL b on a.thuongtl_fk = b.PK_SEQ " +
						"	inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ " +
						"	inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ " +
						"where b.PK_SEQ = N'" + scheme+ "'";
		return db.get(query);
	}

	
	public Hashtable<String, String> getScheme_Soluong() {
	
		return this.scheme_soluong;
	}


	public void setScheme_Soluong(Hashtable<String, String> scheme_soluong) {
		
		this.scheme_soluong = scheme_soluong;
	}

	public ResultSet getSpTheoScheme_UngHang(String scheme, String tongluong)
	{
		/*String query = " select b.MA, b.TEN, d.donvi, sum(a.SOLUONG) as soluong   " +
					   "from DONHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA 	inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
					   "	inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ " +
					   "where a.CTKMID in (" + scheme + ") and SPMA is not null and a.DONHANGID in ( select pk_seq from DONHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' )   " +
					   "group by b.MA, b.TEN, d.donvi ";*/
		
		String query =  " select UNGHANG.PK_SEQ, UNGHANG.MA, UNGHANG.TEN, UNGHANG.DONVI, " +
						" 	UNGHANG.soluong - ISNULL(DUYET.SOLUONG, 0) as soluong " +
						" from " +
						" ( " +
						" 	select b.PK_SEQ, b.MA, b.TEN, d.donvi, sum(a.SOLUONG) as soluong   " +
						" 	from DONHANG_CTKM_TRAKM a inner join ERP_SANPHAM b on a.SPMA = b.MA 	inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
						" 			inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ  " +
						" 	where a.CTKMID = '" + scheme + "' and SPMA is not null and a.DONHANGID in ( select pk_seq from DONHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' )  " +
						" 	group by b.PK_SEQ, b.MA, b.TEN, d.donvi " +
						" ) " +
						" UNGHANG left join " +
						" ( " +
						" 	select b.sanpham_fk, SUM(b.soluong) as soluong  " +
						" 	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk " +
						" 	where a.LoaiDonHang = '1' and a.TRANGTHAI in (0, 1) and b.ctkm_fk = '" + scheme + "' and a.NPP_FK = '" + this.nppId + "'  " + ( this.id.trim().length() > 0 ? " and a.PK_SEQ != '" + this.id + "' " : "" ) +
						" 	group by b.sanpham_fk " +
						" ) " +
						" DUYET on UNGHANG.PK_SEQ = DUYET.sanpham_fk ";
		
		return db.get(query);
	}

	public ResultSet getSpTheoScheme_TrungBay(String scheme, String tongluong) 
	{
		String query =  "select distinct c.MA, c.TEN, d.donvi, '' as soluong  " +
						"from TRATRUNGBAY_SANPHAM a inner join TRATRUNGBAY b on a.TRATRUNGBAY_FK = b.PK_SEQ  " +
						"	inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ  " +
						"	inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ   " +
						"where b.PK_SEQ in ( select TRATRUNGBAY_FK from CTTB_TRATB where CTTRUNGBAY_FK = '" + scheme + "' )";
		
		return db.get(query);

	}

	public String[] getSpScheme() {

		return this.spSCheme;
	}


	public void setSpScheme(String[] spScheme) {
		
		this.spSCheme = spScheme;
	}

	public ResultSet getCongnoRs() {

		return this.congnoRs;
	}


	public void setCongnoRs(ResultSet congnoRs) {
		
		this.congnoRs = congnoRs;
	}

	public String[] getSpChietkhau() {
		
		return this.spChietkhau;
	}

	
	public void setSpChietkhau(String[] spChietkhau) {
		
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

	
	public String[] getSpTrongluong() {
		
		return this.spTrongluong;
	}

	
	public void setSpTrongluong(String[] spTrongluong) {
		
		this.spTrongluong = spTrongluong;
	}

	
	public String[] getSpThetich() {
		
		return this.spThetich;
	}

	
	public void setSpThetich(String[] spThetich) {
		
		this.spThetich = spThetich;
	}

	public boolean saveDDH(HttpServletRequest request)
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String sotienthu = "0";
			if(request.getParameter("sotienthu") != null)
				sotienthu = request.getParameter("sotienthu").replaceAll(",", "");
			this.chietkhau = sotienthu;
			
			String query = "UPDATE ERP_DONDATHANG set SOTIENTHU = '" + sotienthu + "' where pk_seq = '" + this.id + "' ";
			System.out.println("----DUYET: " + query);
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhật ERP_DONDATHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//THAY DOI SAN PHAM, SOLO KHI DUYET
			Utility util = new Utility();
			
			String loaichungtu = "Duyệt đơn đặt hàng"; 
			String chungtuId = this.id; 
			String transactionId = util.createTransactionId();
			
			//Chi co don noi bo la khong co kho chi tiet thoi
			if( !this.loaidonhang.equals("5") )
			{
				//Tăng kho ngược lại trước khi xóa
				query = "  select b.NgayDonHang, b.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
						" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, isnull(a.nsx_fk, 0) as nsx_fk, a.phieudt, a.phieueo, SUM( a.SoLuongCHUAN ) as soluong  " + 
						"  from ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_DONDATHANG b on a.DonDatHang_FK = b.PK_SEQ " + 
						"  where b.PK_SEQ = '" + this.id + "' " + 
						"  group by b.NgayDonHang, b.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
				
				System.out.println("::: CAP NHAT KHO: " + query);
				ResultSet rs = db.get(query);
				if( rs != null )
				{
					while( rs.next() )
					{
						String khoId = rs.getString("Kho_FK");
						String spId = rs.getString("SanPham_fk");
						String solo = rs.getString("SoLo");
						String ngayhethan = rs.getString("NgayHetHan");
						String ngaynhapkho = rs.getString("ngaynhapkho");
						
						String loaidoituong = "";
						String doituongId = "";
						
						String mame = rs.getString("mame");
						String mathung = rs.getString("mathung");
						String bin_fk = rs.getString("bin_fk");
						
						String maphieu = rs.getString("maphieu");
						String phieudt = rs.getString("phieudt");
						String phieueo = rs.getString("phieueo");
						
						String marq = rs.getString("marq");
						String hamluong = rs.getString("hamluong");
						String hamam = rs.getString("hamam");
						String nsx = rs.getString("nsx_fk");

						double soluong = rs.getDouble("soluong");
						
						msg = util.Update_KhoTT_NEW(rs.getString("NgayDonHang"), "Cập nhật DH - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
								mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong, 
								0, "", nsx	);
						
						if( msg.trim().length() > 0 )
						{
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				
				query = "delete ERP_DONDATHANG_SANPHAM_CHITIET where Dondathang_fk = '" + this.id + "'";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_DONDATHANG_SANPHAM_CHITIET " + query;
					db.getConnection().rollback();
					return false;
				}
				
				for(int i = 0; i < spMa.length; i++)
				{
					if( spMa[i].trim().length() > 0 )
					{
						/*if( spSCheme[i].trim().length() <= 0 ) //CHỈ ĐƯỢC ĐỔI SỐ LÔ THÔI
						{
							query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk ) " +
									"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ) " +
									"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
							
							System.out.println("1.Insert NK - SP: " + query);
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
								db.getConnection().rollback();
								return false;
							}
						}*/
						String thueVAT = "0";
						if(this.spVAT !=null && this.spVAT[i]!=null && this.spVAT[i].trim().trim().length() > 0)
							thueVAT = this.spVAT[i].trim().replaceAll(",", "");
						//INSERT CHI TIET
						if(this.sanpham_soluong != null)
						{
							Enumeration<String> keys = this.sanpham_soluong.keys();
							double totalCT = 0;
							
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
									
									if(!_soluongCT.equals("0"))
									{
										//QUY VỀ SỐ LƯỢNG CHUẨN LƯU KHO
										query = " select '" + _soluongCT.replaceAll(",", "") + "' * dbo.LayQuyCach( a.pk_seq, null, ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) ) as soluongCHUAN, " + 
												"	dbo.LayQuyCach_DVBan( a.pk_seq, null, ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) ) as quyveDVBAN	" +
												" from ERP_SANPHAM a where ma = '" + spMa[i] + "' ";
										
										double soluongCHUAN = Double.parseDouble(_soluongCT.replaceAll(",", ""));
										double quyveDVBAN = 0;
										rs = db.get(query);
										if(rs != null )
										{
											if( rs.next() )
											{
												soluongCHUAN = rs.getDouble("soluongCHUAN");
												quyveDVBAN = rs.getDouble("quyveDVBAN");
											}
											rs.close();
										}
										
										//Bên đầu bán, lúc nhập chỉ có số lô, ngày hết hạn, vị trí, các thông số khác sẽ đề xuất chỗ này
										List<ErpThongtinkho> chitiet = util.DeXuatKho(db, this.ngayyeucau, this.nppId, this.khoNhanId, spMa[i], _sp[2],  _sp[3], _sp[4], _sp[5], _sp[6],_sp[7], _sp[8],_sp[9], soluongCHUAN );
										if( chitiet == null )
										{
											this.msg = "Lỗi đề xuất sản phẩm, vui lòng liên hệ admin để xử lý.";
											db.getConnection().rollback();
											return false;
										}
										
										totalCT += Double.parseDouble(_soluongCT);
										
										for( int j = 0; j < chitiet.size(); j++  )
										{
											ErpThongtinkho tt = chitiet.get(j);
											
											query = "insert ERP_DONDATHANG_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, nsx_fk, phieudt, phieueo, soluong, soluongCHUAN,IS_KHONGTHUE,TIENHANG,TIENVAT,TIENHANGSAUVAT ) " +
													"select '" + this.id + "', '" + tt.spId + "', N'', N'" + tt.solo + "', '" + tt.ngayhethan + "', '" + tt.ngaynhapkho + "', '" + tt.mame + "', '" + tt.mathung + "', N'" + tt.maphieu + "', '" + tt.MARQ + "', '" + tt.hamluong + "', '" + tt.hamam + "', '" + tt.vitriId + "', " + tt.nsxId + ", '" + tt.phieudt + "', '" + tt.phieueo + "', ( " + quyveDVBAN + " * " + tt.soluong + " ), '" + tt.soluong + "','"+this.spIskhongthue[i]+"' "+
													" ,ROUND(("+ tt.soluong+" * "+spGianhap[i].replaceAll(",", "")+"),0) AS TIENHANG, ROUND(((ROUND(("+ tt.soluong+" * "+spGianhap[i].replaceAll(",", "")+"),0))*("+thueVAT+")/100),0) AS TIENVAT"+
													", (ROUND(("+ tt.soluong+" * "+spGianhap[i].replaceAll(",", "")+"),0) +ROUND(((ROUND(("+ tt.soluong+" * "+spGianhap[i].replaceAll(",", "")+"),0))*("+thueVAT+")/100),0) )AS TIENHANGSAUVAT  ";
							
											System.out.println("1.2.Insert ERP_DONDATHANG_SANPHAM_CHITIET: " + query);
											if(!db.update(query))
											{
												this.msg = "Khong the tao moi ERP_DONDATHANG_SANPHAM_CHITIET: " + query;
												db.getConnection().rollback();
												return false;
											}
											
											//CẬP NHẬT KHO
											//this.msg = util.Update_KhoTT(this.ngaydenghi, "HO / Bán đối tác", db, this.khoNhanId, "( select pk_seq from SANPHAM where ma = '" + spMa[i] + "' )", _sp[2], _sp[3], _sp[4], _sp[5], _sp[6], 0, Double.parseDouble(_soluongCT.replaceAll(",", "")), -1 * Double.parseDouble(_soluongCT.replaceAll(",", "")));
											this.msg = util.Update_KhoTT_NEW(this.ngaydenghi, "HO / Bán đối tác", db, tt.khoId, tt.spId, tt.solo, tt.ngayhethan, tt.ngaynhapkho, 
															tt.mame, tt.mathung, tt.vitriId, tt.maphieu, tt.phieudt, tt.phieueo, tt.MARQ, tt.hamluong, tt.hamam, "", "", 0, tt.soluong, -1 * tt.soluong, 
															Double.parseDouble(spGianhap[i].replaceAll(",", "")), "", tt.nsxId	);
											if( this.msg.trim().length() > 0 )
											{
												db.getConnection().rollback();
												return false;
											}
										}
									}
								}
							}
							
							//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
							if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
							{
								this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
				
				//CHECK BẢNG ĐƠN HÀNG TỔNG VS CHI TIẾT
			}

			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			System.out.println("--LOI DUYET: " + e.getMessage());
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
		}
		
		return true;
	}
	
	public boolean duyetDDH(HttpServletRequest request)
	{
		try 
		{
			//THANG CHOT PHIEU XUAT KHO BUOC PHAI SAU THANG KS + 1
			/*String query = "select top(1) NAM as namMax, THANGKS as thangMax, " +
							"	( select ngaydonhang from ERP_DONDATHANG where pk_seq = '" + this.id + "' ) as ngaylapphieu " +
							"from ERP_KHOASOTHANG  " +
							"order by NAM desc, THANGKS desc ";
			System.out.println("1.Khoi tao thang: " + query);
			ResultSet rs = db.get(query);

			try
			{
				if(rs != null)
				{
					while(rs.next())
					{
						String thangHL = "";
						String namHL = "";

						String thangKs = rs.getString("thangMax");
						String namKs = rs.getString("namMax"); 

						String nam = rs.getString("ngaylapphieu").substring(0, 4);
						String thang = rs.getString("ngaylapphieu").substring(5, 7);

						if(thangKs.equals("12"))
						{
							thangHL = "1";
							namHL = Integer.toString(Integer.parseInt(namKs) + 1);
						}
						else
						{
							thangHL = Integer.toString(Integer.parseInt(thangKs) + 1);
							namHL = namKs;
						}

						if(thangHL.trim().length() < 2)
							thangHL = "0" + thangHL;

						System.out.println("---THANG HOP LE: " + thangHL + " -- NAM HOP LE: " + namHL + " -- NAM: " + nam + "  -- THANG:  " + thang );
						if(	!thangHL.equals(thang) || !namHL.equals(nam) )
						{
							msg = "Bạn chỉ được phép duyệt đơn hàng sau tháng khóa sổ ( " + thangKs + " ) 1 tháng";
							rs.close();
							return false;
						}

					}
					rs.close();
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				this.msg = "Lỗi khi chốt duyệt đơn hàng " + e.getMessage();
				return false;
			}*/

			db.getConnection().setAutoCommit(false);

			//Check phải có kho chi tiết
			String query = " SELECT COUNT(*) as sodong " + 
						   " FROM dbo.ERP_DONDATHANG_SANPHAM_CHITIET " + 
						   " WHERE DonDatHang_FK = '" + this.id + "' ";
			ResultSet rs = db.get(query);
			int soDong = 0;
			if( rs != null )
			{
				if( rs.next() )
				{
					soDong = rs.getInt("sodong");
				}
				rs.close();
			}
			
			if(soDong <= 0 && !this.loaidonhang.equals("5") )
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm chi tiết bán";
				db.getConnection().rollback();
				return false;
			}
			
			String sotienthu = "0";
			if(request.getParameter("sotienthu") != null)
				sotienthu = request.getParameter("sotienthu").replaceAll(",", "");
			this.chietkhau = sotienthu;

			//SAU KHI DUYET THI TRANG THAI LA DA XUAT KHO (= 3), 4 hoan tat
			query = "UPDATE ERP_DONDATHANG set SOTIENTHU = '" + sotienthu + "', trangthai = '4' where pk_seq = '" + this.id + "' ";
			System.out.println("----DUYET: " + query);
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhật ERP_DONDATHANG " + query;
				db.getConnection().rollback();
				return false;
			}

			int noibo = 0;
			if( this.loaidonhang.equals("5") ) //ĐƠN NỘI BỘ KHÔNG TRỪ TỒN KHO
			{
				noibo = 1;
				this.msg = DuyetDonHang_NoiBo();
				if( this.msg.trim().length() > 0 )
				{
					db.getConnection().rollback();
					return false;
				}
				
				//Nếu đơn nội bộ thì chèn vào bảng KM để ra hóa đơn như đơn bình thường
				query = "  insert ERP_DONDATHANG_CTKM_TRAKM( DONDATHANGID, CTKMID, TRAKMID, SOXUAT, TONGGIATRI, SPMA, SOLUONG )  " + 
						"  select a.dondathang_fk, b.PK_SEQ, 1, 1, 0, c.MA, a.soluong  " + 
						"  from ERP_DONDATHANG_SANPHAM a inner join CTKHUYENMAI b on a.SCHEME = b.SCHEME " + 
						"  	inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ  " + 
						"  where dondathang_fk = '" + this.id + "' ";
				if( !db.update(query))
				{
					this.msg = "Lỗi khi duyệt: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			//else
			//{
				//Không tự động xuất kho chỗ này
				boolean coKM = false;
				query = " select count(*) as sodong from ERP_DONDATHANG_CTKM_TRAKM where DONDATHANGID = '" + this.id + "' ";
				rs = db.get(query);
				if( rs != null )
				{
					if( rs.next() )
					{
						if( rs.getInt("sodong") > 0 )
							coKM = true;
					}
					rs.close();
				}
				
				//Hóa đơn hiện tại thấy tạo xong là tự xác nhận luôn, để check lại chỗ này
				
				if(coKM && noibo==1)
				{
					this.msg = this.TaoHoaDonTaiChinhKM( this.id );
					if( this.msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return false;
					}
				}
				else
				{
					this.msg = this.TaoHoaDonTaiChinh( this.id, noibo );
					if( this.msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return false;
					}
					/*if( coKM)
					{
						this.msg = this.TaoHoaDonTaiChinhKM( this.id );
						if( this.msg.trim().length() > 0 )
						{
							db.getConnection().rollback();
							return false;
						}
					}*/
				}	
			//}

			db.getConnection().commit();
		} 
		catch (Exception e) 
		{
			this.msg = "::::LOI DUYET: " + e.getMessage();
			e.printStackTrace();
			db.update("rollback");
			return false;
		}

		return true;
	}
	
	private String TaoHoaDonTaiChinh( String id, int noibo ) 
	{
		String msg = "";
		try
		{
			String kyhieuhoadon="";
			String sohoadon="";
			String ngayhoadon = "";
	
			sohoadon="NA";
			kyhieuhoadon="NA";
			String chuoi="";
			long sohoadontu=0;
			long sohoadonden= 0;
	
			String query_kyhieu = " NV.KYHIEU ";				
			String query_sohdTU = " NV.SOHOADONTU ";	
			String query_sohdDEN = " NV.SOHOADONDEN ";	
			String query_mauhd = "1";
			String query_ngayhd = " NV.NGAYHOADON  ";
			
			String query =  " SELECT ISNULL("+ query_ngayhd +", '') as NGAYHOADON, (CASE WHEN ISNULL("+ query_kyhieu +",'-1') = '' THEN '-1' ELSE ISNULL("+ query_kyhieu +",'-1') END)  as KYHIEU, \n"+
							"        ISNULL("+ query_sohdTU +", -1) AS SOHOADONTU, ISNULL("+ query_sohdDEN +", -1) AS SOHOADONDEN,  \n"+
							"        (select count(hd.pk_seq) as dem  "+
							"         from ERP_HOADON hd               "+
							"         where hd.trangthai != 3 and hd.sohoadon != 'NA'  and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							"               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							"               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_ETC \n" +
							" FROM NHANVIEN NV  \n" +
							" WHERE NV.pk_seq = '" + this.userId + "' \n";
			
			System.out.println("Câu check khai báo SHD " + query);
			ResultSet rsLayDL = db.get(query);
			int check_ETC = 0;
			while(rsLayDL.next())
			{
				kyhieuhoadon= rsLayDL.getString("kyhieu");
				sohoadontu = rsLayDL.getInt("sohoadontu");
				sohoadonden = rsLayDL.getInt("sohoadonden");
				ngayhoadon = rsLayDL.getString("ngayhoadon");
				check_ETC = rsLayDL.getInt("isSd_ETC");
			}
			rsLayDL.close();
			if(kyhieuhoadon.equals("-1") || sohoadontu == -1 || sohoadonden == -1 )
			{
				msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
				return msg;
			}
			if(check_ETC <= 0)
			{
				chuoi = ("000000" + sohoadontu);
				chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
			}
			else
			{
				query= " SELECT  \n"+
						"       (select max(cast(sohoadon as float)) as soin_max "+
						"        from ERP_HOADON hd               "+
						"        where hd.trangthai != 3 and hd.sohoadon != 'NA'  and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						"              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						"              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ) soinMAX_ETC  \n" +
						" FROM NHANVIEN NV  \n" +
						" WHERE NV.pk_seq = '" + this.userId + "' \n";
	
				System.out.println("Câu lấy SHD Max: "+query);
				long soinMAX_ETC = 0;
				ResultSet laySOIN = db.get(query);						     
				while(laySOIN.next())
				{
					soinMAX_ETC = laySOIN.getLong("soinMAX_ETC");
				}
				laySOIN.close();
				chuoi = ("000000" + (soinMAX_ETC > 0 ? (soinMAX_ETC + 1) : "1"));
				chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length()).trim();
			}
			
			if(Integer.parseInt(chuoi) > sohoadonden )
			{ 
				//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
				query= " SELECT  \n"+
						"      (select  max(cast(hd.sohoadon as float)) as soin_max   \n"+
						"       from ERP_HOADON hd                                     \n"+
						"       where hd.trangthai != 3 and hd.sohoadon != 'NA'  and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  \n"+
						"             and cast(hd.sohoadon as float) >= cast(ISNULL("+ query_sohdTU +", -1) as float)                                 \n"+
						"             and cast(hd.sohoadon as float) <= cast(ISNULL("+ query_sohdDEN +", -1) as float)  and hd.nguoisua = NV.PK_SEQ                               \n"+
						"       having max(cast(hd.sohoadon as float)) != ( select  MAX(cast(SOHOADON as float)) as SOIN_MAX  from ERP_HOADON where trangthai!= 3 and  kyhieu = ISNULL("+ query_kyhieu +",'-1')  and nguoisua= NV.PK_SEQ) \n"+
						"       ) soinMAX_OTC 										  \n"+								  
						" FROM NHANVIEN NV   \n" +
						" WHERE NV.pk_seq = '" + this.userId + "' \n";
	
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
			query = " select  " +
					"        (select count(*) from ERP_HOADON where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraETC " +
					" from NHANVIEN where pk_seq = '" + userId + "' ";
	
			System.out.println("Câu kiểm tra lại SHD: "+query);
			ResultSet RsRs = db.get(query);
			int KT_OTC = 0;
			int KT_ETC = 0;
			while(RsRs.next())
			{
				KT_ETC = RsRs.getInt("KtraETC") ;
			}
	
			if( KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI TẠO
			{
				msg = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
				db.getConnection().rollback();
				return msg;
			}	
			
			//Check xem đơn hàng KM có tích in giá hay không
			query = "select isnull( isingia, 0 ) as isingia, loaidonhang from ERP_DONDATHANG where pk_seq = '" + this.id + "' ";
			String isingia = "0";
			String loaidonhang = "0";
			ResultSet rs = db.get(query);
			if( rs.next() )
			{
				isingia = rs.getString("isingia");
				loaidonhang = rs.getString("loaidonhang");
			}
			rs.close();
			
			query = "	insert ERP_HOADON(ngayxuatHD, kho_fk, kbh_fk, trangthai,ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt, npp_fk ,chietkhau, tongtienbvat, tongtienavat, vat, ghichu, nguoimua,DonDatHang_FK, LoaiHoaDon, noibo) "+ 
					"	select NgayDonHang, a.kho_fk, kbh_fk, case a.loaidonhang when 5 then 2 else 1 end as TrangThai, '" + this.getDateTime() + "' NGAYTAO, '" + this.userId + "' NGUOITAO, '" + this.getDateTime() + "' NGAYSUA, '" + this.userId + "' NGUOISUA, "+
					"	'" + kyhieuhoadon + "', '" + sohoadon + "', case when ( a.iskm = 1 or a.loaidonhang = 4 ) then N'Không thu tiền' else 'TM/CK' END as hinhthuctt, NPP_FK as nppId, 0 as CK, 0 as Bvat, 0 as avat, 0 as vat, a.GHICHU, b.TEN, A.PK_SEQ, case when ( a.iskm = 1 or a.loaidonhang = 4 ) then 1 when a.isdhkhac = 1 then 2 else 0 END  as LoaiHD, case a.loaidonhang when 5 then 1 else 0 end as noibo "+
					"	 from ERP_DONDATHANG a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK  "+
					"	 where a.NPP_DACHOT = 1 and a.pk_Seq = '" + this.id + "' ";
	
			System.out.println("---INSERT ERP_HOADON: " + query );
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_HOADON " + query;
				return msg;
			}
	
			String hdId = "";
			rs = db.get("select Scope_Identity() as ycxkId");
			if(rs.next())
			{
				hdId = rs.getString("ycxkId");
			}
			rs.close();
	
			if( loaidonhang.equals("4") && isingia.equals("0") )
			{
				query = "  insert ERP_HOADON_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluongCHUAN, dongia, thanhtien, chietkhau, scheme, vat,thuesuat,isnhapkhau,IS_KHONGTHUE)  " + 
						"  SELECT '" + hdId + "',  " + 
						"         b.PK_SEQ, b.TEN, DV.donvi, SUM( a.soluong), SUM( a.soluongCHUAN), 0 dongia, 0 thanhtien, 0 chietkhau,  " + 
						"         isnull(scheme, ' '), 0 as vat, 0 as thuevat,0 AS isnhapkhau,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE    " + 
						"  FROM ERP_DONDATHANG_SANPHAM a INNER JOIN ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ     	  " + 
						"                                    INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK   " + 
						"                                    INNER JOIN  ERP_DONDATHANG c on a.dondathang_fk=c.pk_seq     " + 
						"  WHERE a.dondathang_fk in ( " + this.id + " )  and a.soluong > 0  " + 
						"  GROUP BY b.PK_SEQ, b.TEN, DV.donvi, isnull(scheme,' '),isnull(a.IS_KHONGTHUE,0) ";
			} else if(loaidonhang.equals("5"))
			{
				query = "insert ERP_HOADON_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluongCHUAN, dongia, thanhtien, chietkhau, scheme, vat,thuesuat,tienvat,isnhapkhau,IS_KHONGTHUE)  " + 
						"SELECT '" + hdId + "',  " + 
						"   b.PK_SEQ, b.TEN, DV.donvi, SUM( a.soluong), SUM( a.soluongCHUAN), avg( a.dongia ), SUM( a.soluong * a.dongia), SUM( isnull(a.chietkhau, 0)),  " + 
						"   isnull(scheme, ' ') , isnull( avg( a.thuevat ), 0 ) as vat,a.thuevat,a.tienvat, 0 AS isnhapkhau,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE    " + 
						"FROM ERP_DONDATHANG_SANPHAM a INNER JOIN ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ     	  " + 
						"   INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK   " + 
						"   INNER JOIN  ERP_DONDATHANG c on a.dondathang_fk=c.pk_seq     " + 
						"WHERE a.dondathang_fk in ( " + this.id + " )  and a.soluong > 0  and dbo.trim( isnull(scheme, '') ) = '' " + 
						"GROUP BY b.PK_SEQ, b.TEN, DV.donvi, isnull(scheme,' ') ,a.thueVAT,a.tienvat,isnull(a.IS_KHONGTHUE,0) ";
			}
			else
			{
				query = "insert ERP_HOADON_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluongCHUAN, dongia, thanhtien, chietkhau, scheme, vat,thuesuat,isnhapkhau,IS_KHONGTHUE)  " + 
						"SELECT '" + hdId + "',  " + 
						"	b.PK_SEQ, b.TEN, DV.donvi, SUM( a.soluong), SUM( a.soluongCHUAN), avg( a.dongia ), SUM( a.soluong * a.dongia), SUM( isnull(a.chietkhau, 0)),  " + 
						"   isnull(scheme, ' ') , isnull( avg( a.thuevat ), 0 ) as vat,a.thuevat,0 AS isnhapkhau ,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE   " + 
						"FROM ERP_DONDATHANG_SANPHAM a INNER JOIN ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ     	  " + 
						"   INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK   " + 
						"   INNER JOIN  ERP_DONDATHANG c on a.dondathang_fk=c.pk_seq     " + 
						"WHERE a.dondathang_fk in ( " + this.id + " )  and a.soluong > 0  and dbo.trim( isnull(scheme, '') ) = '' " + 
						"GROUP BY b.PK_SEQ, b.TEN, DV.donvi, isnull(scheme,' ') ,a.thueVAT,isnull(a.IS_KHONGTHUE,0) " +
						"union all " + 
						"SELECT '" + hdId + "', " +  
						"	b.PK_SEQ, b.TEN, DV.donvi, SUM( a.soluong ), SUM( a.soluong ), 0, 0, 0, " +   
						"	isnull(scheme, ' ') , 0 as vat, 0, 0 AS isnhapkhau, 0 as IS_KHONGTHUE " +   
						"FROM ERP_DONDATHANG_CTKM_TRAKM a INNER JOIN ERP_SANPHAM b on a.spma = b.ma " +    	   
						"	INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK " +    
						"	INNER JOIN ERP_DONDATHANG c on a.dondathangid=c.pk_seq " +   
						"	inner join ctkhuyenmai d on a.ctkmid = d.pk_seq " + 
						"WHERE a.dondathangid in ( " + this.id + " ) and a.spma <> '' and a.inchung = 1 " +
						"GROUP BY b.PK_SEQ, b.TEN, DV.donvi, isnull(scheme,' ')";
			}
			
			System.out.println("---INSERT ERP_HOADON_SP: " + query );
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_HOADON_SP " + query;
				return msg;
			}
			
			if( loaidonhang.equals("4") && isingia.equals("0") )
			{
				query = "  insert ERP_HOADON_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SoLuong_Chuan, SoLuong_DatHang, CHIETKHAU, THUEVAT, DONGIA, DonGia_Chuan, SOLO, NGAYHETHAN, NGAYNHAPKHO, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, nsx_fk, phieudt, phieueo,IS_KHONGTHUE  ,TIENHANG,TIENVAT,TIENHANGSAUVAT, scheme) " + 
						"  select '" + hdId + "', a.DonDatHang_FK, c.KBH_FK, c.Kho_FK, b.MA, b.TEN, d.DONVI, b.DVDL_FK as dvChuan, a.Dvdl_Fk as dvDathang,  " + 
						"  			a.SoLuong, a.SoLuong * dbo.LayQuyCach(a.sanpham_fk, null, e.dvdl_fk) as soluongChuan, a.SoLuong as SoLuong_DatHang,  " + 
						"  			0 chietkhau, 0 as THUEVAT, 0 as DONGIA, 0 as DonGia_Chuan, " + 
						"			a.SoLo, a.NgayHetHan, a.ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, nsx_fk, phieudt, phieueo,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE,ISNULL(a.TIENHANG,0) AS TIENHANG,ISNULL(a.TIENVAT,0) AS TIENVAT,ISNULL(a.TIENHANGSAUVAT,0) AS TIENHANGSAUVAT, ' ' scheme " +
						"  from ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_SANPHAM b on a.SanPham_fk = b.PK_SEQ " + 
						"  		inner join ERP_DONDATHANG c on a.DonDatHang_FK = c.PK_SEQ " + 
						"  		left join DONVIDOLUONG d on a.Dvdl_Fk = d.PK_SEQ " + 
						"  		inner join ERP_DONDATHANG_SANPHAM e on a.dondathang_fk = e.dondathang_fk and a.sanpham_fk = e.sanpham_fk " + 
						"  where a.DonDatHang_FK = '" + this.id + "' ";
			}	
			else
			{
				query = "insert ERP_HOADON_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SoLuong_Chuan, SoLuong_DatHang, CHIETKHAU, THUEVAT, DONGIA, DonGia_Chuan, SOLO, NGAYHETHAN, NGAYNHAPKHO, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, nsx_fk, phieudt, phieueo,IS_KHONGTHUE ,TIENHANG,TIENVAT,TIENHANGSAUVAT, scheme ) " + 
						"select '" + hdId + "', a.DonDatHang_FK, c.KBH_FK, c.Kho_FK, b.MA, b.TEN, d.DONVI, b.DVDL_FK as dvChuan, a.Dvdl_Fk as dvDathang,  " + 
						"  	a.SoLuong, a.SoLuong * dbo.LayQuyCach(a.sanpham_fk, null, e.dvdl_fk) as soluongChuan, a.SoLuong as SoLuong_DatHang,  " + 
						"  	e.chietkhau as chietkhau, e.thueVAT as THUEVAT, e.dongia as DONGIA, e.dongia * dbo.LayQuyCach_DVBan( a.SanPham_fk, null, e.Dvdl_Fk ) as DonGia_Chuan, " + 
						"	a.SoLo, a.NgayHetHan, a.ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, nsx_fk, phieudt, phieueo,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE,ISNULL(a.TIENHANG,0) AS TIENHANG,ISNULL(a.TIENVAT,0) AS TIENVAT,ISNULL(a.TIENHANGSAUVAT,0) AS TIENHANGSAUVAT, ' ' scheme " +
						"from ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_SANPHAM b on a.SanPham_fk = b.PK_SEQ " + 
						"	inner join ERP_DONDATHANG c on a.DonDatHang_FK = c.PK_SEQ " + 
						"	left join DONVIDOLUONG d on a.Dvdl_Fk = d.PK_SEQ " + 
						"	left join ERP_DONDATHANG_SANPHAM e on a.dondathang_fk = e.dondathang_fk and a.sanpham_fk = e.sanpham_fk " + 
						"where a.DonDatHang_FK = '" + this.id + "' and a.Scheme = '' " + 
						"union all " +
						"select '" + hdId + "', a.DonDatHang_FK, c.KBH_FK, a.Kho_FK, b.MA, b.TEN, d.DONVI, b.DVDL_FK as dvChuan, a.Dvdl_Fk as dvDathang, " +   
						"		a.SoLuong, a.SoLuong as soluongChuan, a.SoLuong as SoLuong_DatHang, 0 as chietkhau, 0 as THUEVAT, 0 as DONGIA, 0 as DonGia_Chuan, " + 
						"		a.SoLo, a.NgayHetHan, a.ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, nsx_fk, phieudt, phieueo,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE, " +
						"		0 AS TIENHANG, 0 AS TIENVAT, 0 AS TIENHANGSAUVAT, a.scheme " +		
						"from ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_SANPHAM b on a.SanPham_fk = b.PK_SEQ " +  
						"	inner join ERP_DONDATHANG c on a.DonDatHang_FK = c.PK_SEQ " + 
						"	left join DONVIDOLUONG d on a.Dvdl_Fk = d.PK_SEQ " + 
						"where a.DonDatHang_FK = '" + this.id + "' and a.Scheme != '' and a.inchung = 1 ";
			}
			
			System.out.println("---INSERT ERP_HOADON_SP_CHITIET: " + query );
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_HOADON_SP_CHITIET " + query;
				return msg;
			}
	
			query = "select npp_fk from  ERP_HOADON  where pk_seq = '" + hdId + "' ";
			ResultSet rs1 = db.get(query);
			String npp="";
			if(rs1.next())
			{
				npp = rs1.getString("npp_fk");
			}
			rs1.close();
			
			/*query = "update a set a.isnhapkhau=0 from "+
					"\n	ERP_HOADON_SP a inner join ufn_sanpham("+npp+",(select NGAYXUATHD from erp_hoadon where pk_seq="+hdId+" )) b  "+
					"\n	on a.sanpham_fk=b.sanpham_fk "+
					"\n	where b.isnhapkhau=1 and a.hoadon_fk="+hdId ;
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_HOADON_SP " + query;
				return msg;
			}*/
	
			query = "INSERT INTO ERP_HOADON_DDH(HOADON_FK,DDH_FK) "+
					" SELECT '" + hdId + "', '" + this.id + "'  ";
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_HOADON_DDH " + query;
				return msg;
			}
			
			//CHÈN CHIẾT KHẤU NẾU CÓ
			query = "insert ERP_HOADON_CHIETKHAU( hoadon_fk, donhang_fk, diengiai, chietkhau,tienvat,tienavat, thueVAT, stt, tichluyQUY, HIENTHI, inchietkhauKIEUMOI ) " +
					"select '" + hdId + "' hoadon_fk, DONDATHANG_FK, diengiai, tienbvat,tienvat,thanhtien, 5 as thueVAT, 1 stt, 1 tichluyQUY, 1 HIENTHI, 1 inchietkhauKIEUMOI "+
					"from ERP_DONDATHANG_CHIETKHAU where dondathang_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_HOADON_CHIETKHAU " + query;
				return msg;
			}
			
			//CẬP NHẬT LẠI TIỀN
			if(noibo==1)
			{
				db.execProceduce2("CapNhatTooltip_HoaDon_noibo", new String[]{ hdId });
			}
			else{
				db.execProceduce2("CapNhatTooltip_HoaDon", new String[]{ hdId });
			}
				
				
			
			//HOA DON NOI BO THI DUYET HOA DON LUON, GHI NHAN KE TOAN CHO NAY
			if(noibo == 1 ) // HÓA ĐƠN NỘI BỘ
			{
				Utility util = new Utility();
				// CÀI KẾ TOÁN
				query = " SELECT HD.LoaiHoaDon,LOAI_SP.PK_SEQ LOAIHH, HD.NPP_FK KHACHHANG_FK ,  \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = KH.TAIKHOANKHNB_FK AND npp_fk = 1 ) TAIKHOANHO_KH, \n"+
//						" (SELECT PK_SEQ FROM TraphacoERP.dbo.ERP_NHACUNGCAP WHERE NPP_FK = 1) NCC_FK, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '51200000' AND npp_fk = 1 ) TAIKHOANHO_51200000, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33311000' AND npp_fk = 1 ) TAIKHOANHO_33311000, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = 63213000 AND npp_fk = HD.NPP_FK ) TAIKHOANCN_GIAVON, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '13311000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_13311000, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33610000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_33610000, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TaikhoanChietkhau_sh_fk AND npp_fk = 1 ) TAIKHOANHO_CHIETKHAU, \n"+
						" HD_SP.SOLUONG SOLUONG,  HD_SP.DONGIA  DONGIA, HD.VAT THUEVAT,(SELECT SUM(CHIETKHAU) FROM ERP_HOADON_CHIETKHAU WHERE HOADON_FK=HD.PK_SEQ) AS TIENCKTM, \n"+
						" HD_SP.CHIETKHAU CHIETKHAU, SP.PK_SEQ SANPHAM_FK, HD.NGAYXUATHD,'100000' AS TIENTE_FK, 1  AS TIGIA, \n"+
						" SP.MA MASP, SP.TEN TENSP, HD_SP.DONVITINH DONVI, HD.SOHOADON, HD.KBH_FK, HD.KHO_FK,  \n"+
						" CASE WHEN ISNULL(HD.KHACHHANG_FK,0) > 0 THEN 0 ELSE 1 END AS ISNPP,KH.MAFAST+'-'+HD.GHICHU AS GHICHU \n"+
						" FROM ERP_HOADON HD \n " +
						" INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK \n"+
						" INNER JOIN ERP_SANPHAM SP ON HD_SP.SANPHAM_FK = SP.PK_SEQ \n"+
						" LEFT JOIN ERP_LOAISANPHAM LOAI_SP ON SP.LOAISANPHAM_FK = LOAI_SP.PK_SEQ \n"+
						" LEFT JOIN NHAPHANPHOI KH ON HD.NPP_FK = KH.PK_SEQ \n"+
						" WHERE HD.PK_SEQ  = '"+hdId+"' \n";
				System.out.println(query);
				ResultSet rskt = db.get(query);
				
				if(rskt!=null)
				{
					while(rskt.next())
					{
						String loaiHD = "HDHO_NB";
//						String loaihh_fk = rskt.getString("LOAIHH");
						String masp = rskt.getString("MASP");
						String tensp = rskt.getString("TENSP");
						String donvi = rskt.getString("DONVI"); 
						sohoadon = rskt.getString("SOHOADON"); 
						String kbh_fk = rskt.getString("KBH_FK")== null ? "": rskt.getString("KBH_FK"); 
						String kho_fk = rskt.getString("KHO_FK"); 
						String isNPP = rskt.getString("ISNPP"); 
	//					String ncc_fk = rskt.getString("NCC_FK"); 
						String dienGiai =rskt.getString("GHICHU");
						String tiente_fk = rskt.getString("tiente_fk");
						int tygia = rskt.getInt("tigia");

						double soluong = rskt.getDouble("SOLUONG");
						double chietkhau = Math.round(rskt.getDouble("CHIETKHAU"));
						double dongia = rskt.getDouble("DONGIA");
						double thuevat = rskt.getDouble("THUEVAT");

						double tienhang = Math.round(soluong * dongia)	;
						double thueGTGT = Math.round(tienhang*thuevat/100);

						String khachhang_fk = rskt.getString("KHACHHANG_FK");
						String sanpham_fk = rskt.getString("SANPHAM_FK");

						String TAIKHOAN_NO = "";
						String TAIKHOAN_CO = "";

						String loaidoituong_no = "";
						String loaidoituong_co = "";

						String madoituong_no = "";
						String madoituong_co = "";		

						String ngayghinhan = rskt.getString("NGAYXUATHD");
						String nam = ngayghinhan.substring(0, 4);
						String thang = ngayghinhan.substring(5, 7);

						String TAIKHOANHO_KH = rskt.getString("TAIKHOANHO_KH") == null ? "": rskt.getString("TAIKHOANHO_KH");
						String TAIKHOANHO_51200000 = rskt.getString("TAIKHOANHO_51200000") == null ? "": rskt.getString("TAIKHOANHO_51200000");
						String TAIKHOANHO_33311000 = rskt.getString("TAIKHOANHO_33311000") == null ? "": rskt.getString("TAIKHOANHO_33311000");
						String TAIKHOANCN_GIAVON = rskt.getString("TAIKHOANCN_GIAVON") == null ? "": rskt.getString("TAIKHOANCN_GIAVON");
						String TAIKHOANCN_13311000 = rskt.getString("TAIKHOANCN_13311000") == null ? "": rskt.getString("TAIKHOANCN_13311000");
						String TAIKHOANCN_33610000 = rskt.getString("TAIKHOANCN_33610000") == null ? "": rskt.getString("TAIKHOANCN_33610000");
						String TAIKHOANHO_CHIETKHAU = rskt.getString("TAIKHOANHO_CHIETKHAU") == null ? "": rskt.getString("TAIKHOANHO_CHIETKHAU");
						{ // HO
							if(tienhang > 0)
							{
								TAIKHOAN_NO = TAIKHOANHO_KH;
								TAIKHOAN_CO = TAIKHOANHO_51200000;

								loaidoituong_no = "Khách hàng";
								madoituong_no = khachhang_fk;

								loaidoituong_co = "Sản phẩm";
								madoituong_co = sanpham_fk;

								if (TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0) 
								{
									msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									rskt.close();
									return msg;
								}
					

				

								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(tienhang), Double.toString(tienhang), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(soluong), Double.toString(dongia), tiente_fk, Double.toString(dongia), Double.toString(tygia), Double.toString(tienhang), 
										Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(thueGTGT) , dienGiai , sohoadon ,isNPP ,masp , tensp, donvi, kbh_fk, 
										kho_fk, "", "", Double.toString(tienhang),loaiHD);

								if(msg.trim().length()>0)
								{
									msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									rskt.close();
									return msg;
								}
								
								
								TAIKHOAN_NO = TAIKHOANCN_GIAVON;
								TAIKHOAN_CO = TAIKHOANCN_33610000;

								loaidoituong_no = "";
								madoituong_no = "";

								loaidoituong_co = "Chi nhánh/Đối tác";
								madoituong_co = "1";
								
								
								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOANCN_GIAVON, TAIKHOANCN_33610000, "", 
										Double.toString(tienhang), Double.toString(tienhang), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(soluong), Double.toString(dongia), tiente_fk, Double.toString(dongia), Double.toString(tygia), Double.toString(tienhang), 
										Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(thueGTGT) , dienGiai , sohoadon ,"1" ,masp , tensp, donvi, kbh_fk, 
										kho_fk, "", "", Double.toString(tienhang),loaiHD);

								if(msg.trim().length()>0)
								{
									msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									rskt.close();
									return msg;
								}

							}

						}
						

					}
					rskt.close();
				}
				
				query ="  SELECT HD.LoaiHoaDon, HD.NPP_FK KHACHHANG_FK , \n"+
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = KH.TAIKHOANKHNB_FK AND npp_fk = 1 ) TAIKHOANHO_KH,  \n"+
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '51200000' AND npp_fk = 1 ) TAIKHOANHO_51200000,   \n"+
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33311000' AND npp_fk = 1 ) TAIKHOANHO_33311000,  \n"+
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33610000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_33610000, \n"+ 
						"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '13311000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_13311000,   \n"+
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '63213000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_63213000,  \n"+
						"   HD.GHICHU ,HD.VAT,HD_CHIETKHAU.CHIETKHAU AS CHIETKHAU,HD_CHIETKHAU.VATCK AS VATCK, HD.NGAYXUATHD,HD.SOHOADON,'100000' AS TIENTE_FK, 1  AS TIGIA, \n"+
						"   CASE WHEN ISNULL(HD.KHACHHANG_FK,0) > 0 THEN 0 ELSE 1 END AS ISNPP,KH.MAFAST+'-'+HD.GHICHU AS GHICHU  \n"+
						"   FROM ERP_HOADON HD  \n"+
						"   LEFT JOIN (  \n"+
						"   SELECT HOADON_FK,SUM(CHIETKHAU) AS CHIETKHAU,SUM(TIENVAT) AS VATCK FROM ERP_HOADON_CHIETKHAU \n"+
					    "   WHERE HOADON_FK =" + hdId+" \n"+
					    "   GROUP BY HOADON_FK \n"+
						"   ) HD_CHIETKHAU ON HD_CHIETKHAU.HOADON_FK = HD.PK_SEQ \n"+
						"   LEFT JOIN NHAPHANPHOI KH ON HD.NPP_FK = KH.PK_SEQ  \n"+
						" WHERE HD.PK_SEQ  = '"+hdId+"' \n";
						double TIENCKTM = 0;
						double VAT =0;
						System.out.println(query);
						rskt= db.get(query);
				
					if(rskt!=null)
					{
					while(rskt.next())
					{
						String loaiHD = "HDHO_NB";
	//					String loaihh_fk = rskt2.getString("LOAIHH");
						sohoadon = rskt.getString("SOHOADON"); 
						String isNPP = rskt.getString("ISNPP"); 
	//					String ncc_fk = rskt.getString("NCC_FK"); 
						String dienGiai =rskt.getString("GHICHU");
						String tiente_fk = rskt.getString("tiente_fk");
						int tygia = rskt.getInt("tigia");
	
						double chietkhau = Math.round(rskt.getDouble("CHIETKHAU"));
						double vat_chietkhau = Math.round(rskt.getDouble("VATCK"));
						double thuevat = rskt.getDouble("VAT");
	
						String khachhang_fk = rskt.getString("KHACHHANG_FK");
	
						String TAIKHOAN_NO = "";
						String TAIKHOAN_CO = "";
	
						String loaidoituong_no = "";
						String loaidoituong_co = "";
	
						String madoituong_no = "";
						String madoituong_co = "";		
	
						String ngayghinhan = rskt.getString("NGAYXUATHD");
						String nam = ngayghinhan.substring(0, 4);
						String thang = ngayghinhan.substring(5, 7);
	
						String TAIKHOANHO_KH = rskt.getString("TAIKHOANHO_KH") == null ? "": rskt.getString("TAIKHOANHO_KH");
						String TAIKHOANHO_51200000 = rskt.getString("TAIKHOANHO_51200000") == null ? "": rskt.getString("TAIKHOANHO_51200000");
						String TAIKHOANHO_33311000 = rskt.getString("TAIKHOANHO_33311000") == null ? "": rskt.getString("TAIKHOANHO_33311000");
						String TAIKHOANCN_GIAVON = rskt.getString("TAIKHOANCN_63213000") == null ? "": rskt.getString("TAIKHOANCN_63213000");
						String TAIKHOANCN_13311000 = rskt.getString("TAIKHOANCN_13311000") == null ? "": rskt.getString("TAIKHOANCN_13311000");
						String TAIKHOANCN_33610000 = rskt.getString("TAIKHOANCN_33610000") == null ? "": rskt.getString("TAIKHOANCN_33610000");
						
						
						{ // HO
							if(thuevat > 0)
							{
								TAIKHOAN_NO = TAIKHOANHO_KH;
								TAIKHOAN_CO = TAIKHOANHO_33311000;
	
								loaidoituong_no = "Chi nhánh/Đối tác";
								madoituong_no = khachhang_fk;
	
								loaidoituong_co = "";
								madoituong_co = "";
	
								if (TAIKHOANHO_KH.trim().length() <= 0 || TAIKHOANHO_51200000.trim().length() <= 0) 
								{
									msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									rskt.close();
									return msg;
								}
								
								
				
	
								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(thuevat), Double.toString(thuevat), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(0), Double.toString(0), tiente_fk, Double.toString(0), Double.toString(tygia), Double.toString(thuevat), 
										Double.toString(thuevat), "Hóa đơn - Tiền thuế", Double.toString(thuevat) , dienGiai , sohoadon ,isNPP ,"" , "", "", "", 
										"", "", "", Double.toString(thuevat),loaiHD);
	
								if(msg.trim().length()>0)
								{
									msg = "6.Lỗi khi chạy kế toán: " + msg;
									rskt.close();
									return msg;
								}
								
								TAIKHOAN_NO = TAIKHOANCN_13311000;
								TAIKHOAN_CO = TAIKHOANCN_33610000;
	
								loaidoituong_no = "";
								madoituong_no = "";
	
								loaidoituong_co = "Chi nhánh/Đối tác";
								madoituong_co = "1";
								
								
								
								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(thuevat), Double.toString(thuevat), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(0), Double.toString(0), tiente_fk, Double.toString(0), Double.toString(tygia), Double.toString(thuevat), 
										Double.toString(thuevat), "Hóa đơn - Tiền thuế", Double.toString(thuevat) , dienGiai , sohoadon ,"1" ,"" , "", "", "", 
										"", "", "", Double.toString(thuevat),loaiHD);
	
								if(msg.trim().length()>0)
								{
									msg = "6.Lỗi khi chạy kế toán: " + msg;
									rskt.close();
									return msg;
								}
	
							}
							
							if(chietkhau > 0)
							{
								String diengiai_ck = dienGiai+ " chiết khấu ";
								TAIKHOAN_NO = TAIKHOANHO_51200000;
								TAIKHOAN_CO = TAIKHOANHO_KH;
	
								loaidoituong_no = "";
								madoituong_no = "";
	
								loaidoituong_co = "Chi nhánh/Đối tác";
								madoituong_co = khachhang_fk;
	
								if (TAIKHOANHO_KH.trim().length() <= 0 || TAIKHOANHO_51200000.trim().length() <= 0) 
								{
									msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									rskt.close();
									return msg;
								}
								
								
				
	
								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(chietkhau), Double.toString(chietkhau), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(0), Double.toString(0), tiente_fk, Double.toString(0), Double.toString(tygia), Double.toString(chietkhau), 
										Double.toString(chietkhau), "Hóa đơn - Chiết khấu", Double.toString(chietkhau) , diengiai_ck , sohoadon ,isNPP ,"" , "", "", "", 
										"", "", "", Double.toString(chietkhau),loaiHD);
	
								if(msg.trim().length()>0)
								{
									msg = "6.Lỗi khi chạy kế toán: " + msg;
									rskt.close();
									return msg;
								}
								
								TAIKHOAN_NO = TAIKHOANCN_33610000;
								TAIKHOAN_CO = TAIKHOANCN_GIAVON;
	
								loaidoituong_no = "Chi nhánh/Đối tác";
								madoituong_no = "1";
	
								loaidoituong_co = "";
								madoituong_co = "";
								
								
								
								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(chietkhau), Double.toString(chietkhau), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(0), Double.toString(0), tiente_fk, Double.toString(0), Double.toString(tygia), Double.toString(chietkhau), 
										Double.toString(chietkhau), "Hóa đơn - Tiền khấu", Double.toString(chietkhau) , dienGiai , sohoadon ,"1" ,"" , "", "", "", 
										"", "", "", Double.toString(chietkhau),loaiHD);
	
								if(msg.trim().length()>0)
								{
									msg = "6.Lỗi khi chạy kế toán: " + msg;
									rskt.close();
									return msg;
								}
	
							}
							
							if(vat_chietkhau > 0)
							{ 
								String diengiai_vatck = dienGiai+ " chiết khấu ";
								TAIKHOAN_NO = TAIKHOANHO_33311000;
								TAIKHOAN_CO = TAIKHOANHO_KH;
	
								loaidoituong_no = "";
								madoituong_no = "";
	
								loaidoituong_co = "Chi nhánh/Đối tác";
								madoituong_co = khachhang_fk;
	
	
								if (TAIKHOANHO_33311000.trim().length() <= 0 || TAIKHOANHO_KH.trim().length() <= 0) 
								{
									msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									rskt.close();
									return msg;
								}
								
								
				
	
								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(vat_chietkhau), Double.toString(vat_chietkhau), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(0), Double.toString(0), tiente_fk, Double.toString(0), Double.toString(tygia), Double.toString(chietkhau), 
										Double.toString(vat_chietkhau), "Hóa đơn - VAT Chiết khấu", Double.toString(vat_chietkhau) , diengiai_vatck , sohoadon ,isNPP ,"" , "", "", "", 
										"", "", "", Double.toString(vat_chietkhau),loaiHD);
	
								if(msg.trim().length()>0)
								{
									msg = "6.Lỗi khi chạy kế toán: " + msg;
									rskt.close();
									return msg;
								}
								
								TAIKHOAN_NO = TAIKHOANCN_33610000;
								TAIKHOAN_CO = TAIKHOANCN_13311000;
	
								loaidoituong_no = "Chi nhánh/Đối tác";
								madoituong_no = "1";
	
								loaidoituong_co = "";
								madoituong_co = "";
								
								
								
								
								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(vat_chietkhau), Double.toString(vat_chietkhau), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(0), Double.toString(0), tiente_fk, Double.toString(0), Double.toString(tygia), Double.toString(chietkhau), 
										Double.toString(vat_chietkhau), "Hóa đơn - VAT Chiết khấu", Double.toString(vat_chietkhau) , dienGiai , sohoadon ,"1" ,"" , "", "", "", 
										"", "", "", Double.toString(vat_chietkhau),loaiHD);
	
								if(msg.trim().length()>0)
								{
									msg = "6.Lỗi khi chạy kế toán: " + msg;
									rskt.close();
									return msg;
								}
	
							}

				}
				

			}
			rskt.close();
		}
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			msg = ex.getMessage();
			return msg;
		}
		
		return msg;
	}

	private String TaoHoaDonTaiChinhKM( String id ) 
	{
		String msg = "";
		try
		{
			String kyhieuhoadon="";
			String sohoadon="";
			String ngayhoadon = "";
	
			sohoadon="NA";
			kyhieuhoadon="NA";
			String chuoi="";
			long sohoadontu=0;
			long sohoadonden= 0;
	
			String query_kyhieu = " NV.KYHIEU ";				
			String query_sohdTU = " NV.SOHOADONTU ";	
			String query_sohdDEN = " NV.SOHOADONDEN ";	
			String query_mauhd = "1";
			String query_ngayhd = " NV.NGAYHOADON  ";
			
			String query =  " SELECT ISNULL("+ query_ngayhd +", '') as NGAYHOADON, (CASE WHEN ISNULL("+ query_kyhieu +",'-1') = '' THEN '-1' ELSE ISNULL("+ query_kyhieu +",'-1') END)  as KYHIEU, \n"+
							"        ISNULL("+ query_sohdTU +", -1) AS SOHOADONTU, ISNULL("+ query_sohdDEN +", -1) AS SOHOADONDEN,  \n"+
							"        (select count(hd.pk_seq) as dem  "+
							"         from ERP_HOADON hd               "+
							"         where hd.trangthai != 3 and hd.sohoadon != 'NA'  and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							"               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							"               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_ETC \n" +
							" FROM NHANVIEN NV  \n" +
							" WHERE NV.pk_seq = '" + this.userId + "' \n";
			
			System.out.println("Câu check khai báo SHD " + query);
			ResultSet rsLayDL = db.get(query);
			int check_ETC = 0;
			while(rsLayDL.next())
			{
				kyhieuhoadon= rsLayDL.getString("kyhieu");
				sohoadontu = rsLayDL.getInt("sohoadontu");
				sohoadonden = rsLayDL.getInt("sohoadonden");
				ngayhoadon = rsLayDL.getString("ngayhoadon");
				check_ETC = rsLayDL.getInt("isSd_ETC");
			}
			rsLayDL.close();
			if(kyhieuhoadon.equals("-1") || sohoadontu == -1 || sohoadonden == -1 )
			{
				msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
				return msg;
			}
			if(check_ETC <= 0)
			{
				chuoi = ("000000" + sohoadontu);
				chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
			}
			else
			{
				query= " SELECT  \n"+
						"       (select max(cast(sohoadon as float)) as soin_max "+
						"        from ERP_HOADON hd               "+
						"        where hd.trangthai != 3 and hd.sohoadon != 'NA'  and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						"              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						"              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ) soinMAX_ETC  \n" +
						" FROM NHANVIEN NV  \n" +
						" WHERE NV.pk_seq = '" + this.userId + "' \n";
	
				System.out.println("Câu lấy SHD Max: "+query);
				long soinMAX_ETC = 0;
				ResultSet laySOIN = db.get(query);						     
				while(laySOIN.next())
				{
					soinMAX_ETC = laySOIN.getLong("soinMAX_ETC");
				}
				laySOIN.close();
				chuoi = ("000000" + (soinMAX_ETC > 0 ? (soinMAX_ETC + 1) : "1"));
				chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length()).trim();
			}
			
			if(Integer.parseInt(chuoi) > sohoadonden )
			{ 
				//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
				query= " SELECT  \n"+
						"      (select  max(cast(hd.sohoadon as float)) as soin_max   \n"+
						"       from ERP_HOADON hd                                     \n"+
						"       where hd.trangthai != 3 and hd.sohoadon != 'NA'  and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  \n"+
						"             and cast(hd.sohoadon as float) >= cast(ISNULL("+ query_sohdTU +", -1) as float)                                 \n"+
						"             and cast(hd.sohoadon as float) <= cast(ISNULL("+ query_sohdDEN +", -1) as float)  and hd.nguoisua = NV.PK_SEQ                               \n"+
						"       having max(cast(hd.sohoadon as float)) != ( select  MAX(cast(SOHOADON as float)) as SOIN_MAX  from ERP_HOADON where trangthai!= 3 and  kyhieu = ISNULL("+ query_kyhieu +",'-1')  and nguoisua= NV.PK_SEQ) \n"+
						"       ) soinMAX_OTC 										  \n"+								  
						" FROM NHANVIEN NV   \n" +
						" WHERE NV.pk_seq = '" + this.userId + "' \n";
	
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
			query = " select  " +
					"        (select count(*) from ERP_HOADON where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +") as KtraETC " +
					" from NHANVIEN where pk_seq = '" + userId + "' ";
	
			System.out.println("Câu kiểm tra lại SHD: "+query);
			ResultSet RsRs = db.get(query);
			int KT_OTC = 0;
			int KT_ETC = 0;
			while(RsRs.next())
			{
				KT_ETC = RsRs.getInt("KtraETC") ;
			}
	
			if( KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI TẠO
			{
				msg = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
				return msg;
			}	 
	
			query = "	insert ERP_HOADON(ngayxuatHD, kbh_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt, npp_fk ,chietkhau, tongtienbvat, tongtienavat, vat, ghichu, nguoimua,DonDatHang_FK,LoaiHoaDon, noibo, kho_fk) "+ 
					"	select NgayDonHang, kbh_fk, case a.loaidonhang when 5 then 2 else 1 end, '" + this.getDateTime() + "' NGAYTAO, '" + this.userId + "' NGUOITAO, '" + this.getDateTime() + "' NGAYSUA, '" + this.userId + "' NGUOISUA, "+
					"	'" + kyhieuhoadon + "', '" + sohoadon + "', N'Không thu tiền', NPP_FK as nppId, 0 as CK, 0 as Bvat, 0 as avat, 0 as vat, a.GHICHU, b.TEN, A.PK_SEQ, 1 as LOAIHOADON, case a.loaidonhang when 5 then 1 else 0 end as noibo, a.kho_fk "+
					"	 from ERP_DONDATHANG a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK  "+
					"	 where a.NPP_DACHOT = 1 and a.pk_Seq = '" + this.id + "' ";
	
			System.out.println("---INSERT ERP_HOADON: " + query );
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_HOADON " + query;
				return msg;
			}
	
			String hdId = "";
			ResultSet rs = db.get("select Scope_Identity() as ycxkId");
			if(rs.next())
			{
				hdId = rs.getString("ycxkId");
			}
			rs.close();
	
			//Hang KM TAM KHONG PHAN BIET SCHEME, DE CHECK LAI
			query = "  insert ERP_HOADON_SP( HOADON_FK, SANPHAM_FK, SOLUONG, DONGIA, VAT, DONVITINH, SCHEME, CHIETKHAU, THANHTIEN, thuesuat )  " + 
					"  select '" + hdId + "', b.PK_SEQ, sum(a.SOLUONG) as SOLUONG, 0 as dongia, 0 as VAT, c.DONVI, '' SCHEME, 0 as chietkhau, 0 as thanhtien, 0 as thuasuat " + 
					"  from ERP_DONDATHANG_CTKM_TRAKM a inner join ERP_SANPHAM b on a.SPMA = b.MA " + 
					"  		inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ " + 
					"  		inner join CTKHUYENMAI d on a.CTKMID = d.PK_SEQ " + 
					"  where a.DONDATHANGID = '" + this.id + "' " +
					"  group by b.PK_SEQ,  c.DONVI ";
	
			System.out.println("---INSERT ERP_HOADON_SP: " + query );
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_HOADON_SP " + query;
				return msg;
			}
			
			query = "  insert ERP_HOADON_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SoLuong_Chuan, SoLuong_DatHang, CHIETKHAU, THUEVAT, DONGIA, DonGia_Chuan, SOLO, NGAYHETHAN, NGAYNHAPKHO, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, nsx_fk phieudt, phieueo,IS_KHONGTHUE  ,TIENHANG,TIENVAT,TIENHANGSAUVAT) " + 
					"  select '" + hdId + "', a.DonDatHang_FK, c.KBH_FK, c.Kho_FK, b.MA, b.TEN, d.DONVI, b.DVDL_FK as dvChuan, a.Dvdl_Fk as dvDathang,  " + 
					"  			a.SoLuong, a.SoLuong as soluongChuan, a.SoLuong as SoLuong_DatHang,  " + 
					"  			0 as chietkhau, 0 as THUEVAT, 0 as DONGIA, 0 as DonGia_Chuan, " + 
					"			SOLO, NGAYHETHAN, NGAYNHAPKHO, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, nsx_fk, phieudt, phieueo,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE,ISNULL(a.TIENHANG,0) AS TIENHANG,ISNULL(a.TIENVAT,0) AS TIENVAT,ISNULL(a.TIENHANGSAUVAT,0) AS 	TIENHANGSAUVAT	" +
					"  from ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_SANPHAM b on a.SanPham_fk = b.PK_SEQ " + 
					"  		inner join ERP_DONDATHANG c on a.DonDatHang_FK = c.PK_SEQ " + 
					"  		left join DONVIDOLUONG d on a.Dvdl_Fk = d.PK_SEQ " + 
					//"  		inner join ERP_DONDATHANG_SANPHAM e on a.dondathang_fk = e.dondathang_fk and a.sanpham_fk = e.sanpham_fk " + 
					"  where a.DonDatHang_FK = '" + this.id + "' and a.Scheme != '' ";
			
			System.out.println("---INSERT ERP_HOADON_SP_CHITIET: " + query );
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_HOADON_SP_CHITIET " + query;
				return msg;
			}
	
			query = "select npp_fk from  ERP_HOADON  where pk_seq = '" + hdId + "' ";
			ResultSet rs1=db.get(query);
			String npp="";
			if(rs1.next())
			{
				npp = rs1.getString("npp_fk");
			}
			rs1.close();
			
			/*query = "update a set a.isnhapkhau=0 from "+
					"\n	ERP_HOADON_SP a inner join ufn_sanpham("+npp+",(select NGAYXUATHD from erp_hoadon where pk_seq="+hdId+" )) b  "+
					"\n	on a.sanpham_fk=b.sanpham_fk "+
					"\n	where b.isnhapkhau=1 and a.hoadon_fk="+hdId ;
			System.out.println("---UPDATE ERP_HOADON_SP: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_HOADON_SP " + query;
				return msg;
			}*/
	
			query = "INSERT INTO ERP_HOADON_DDH(HOADON_FK,DDH_FK) "+
					" SELECT '" + hdId + "', '" + this.id + "'  ";
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_HOADON_DDH " + query;
				return msg;
			}
			
			//CẬP NHẬT LẠI TIỀN
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[]{ hdId });
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			msg = ex.getMessage();
			return msg;
		}
		
		return msg;
	}

	private String DuyetDonHang_NoiBo() 
	{
		//Duyệt đơn nội bộ không trừ kho, chỉ tạo hóa đơn tự động
		System.out.println("::: VAO DUYET DON NOI BO ");
		return "";
	}

	public String[] getSpTungay() {
		
		return this.spTungay;
	}

	
	public void setSpTungay(String[] spTungay) {
		
		this.spTungay = spTungay;
	}

	
	public String[] getSpDenngay() {
		
		return this.spDenngay;
	}

	
	public void setSpDenngay(String[] spDenngay) {
		
		this.spDenngay = spDenngay;
	}

	
	public String getMahopdong() {
		
		return this.ma;
	}

	
	public void setMahopdong(String ma) {
		
		this.ma = ma;
	}

	
	public String getGsbhId() {
		
		return this.gsbhId;
	}

	
	public void setGsbhId(String gsbhId) {
		
		this.gsbhId = gsbhId;
	}

	
	public ResultSet getGsbhRs() {
		
		return this.gsbhRs;
	}

	
	public void setGsbhRs(ResultSet gsbhRs) {
		
		this.gsbhRs = gsbhRs;
	}

	
	public String getDdkdId() {
		
		return this.ddkdId;
	}

	
	public void setDdkdId(String ddkdId) {
		
		this.ddkdId = ddkdId;
	}

	
	public ResultSet getDdkdRs() {
		
		return this.ddkdRs;
	}

	
	public void setDddkdRs(ResultSet ddkdRs) {
		
		this.ddkdRs = ddkdRs;
	}
	
	public String[] getSpVat() {
		
		return this.spVAT;
	}

	
	public void setSpVat(String[] spVat) {
		
		this.spVAT = spVat;
	}
	
	
	String iskm;
	public String getIsKm()
  {
  	return iskm;
  }

	public void setIsKm(String iskm)
  {
  	this.iskm = iskm;
  }

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}
	
	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		double sum = Double.parseDouble(tongluong);
		//System.out.println("---TONG LUONG: " + tongluong );
		String kho = this.khoNhanId, scheme = "";
		String soloDACHON = "";
		if( this.sanpham_soluongDAXUAT != null )
		{
			//KEY: MA - SOLO; 
			Enumeration<String> keys = this.sanpham_soluongDAXUAT.keys();
			while( keys.hasMoreElements() )
			{
				String key = keys.nextElement();
				System.out.println("kEY======: " + key );
				if(key.startsWith(spMa))
				{
					String[] arr = this.mySplit(key, "__" );
					scheme = arr[1];
					String solo = arr[2];
					String ngayhethan = arr[3];
					String vitri = arr[4];
					String mathung = arr[5];
					String mame = arr[6];
					String maphieu = arr[7];
					String marq = arr[8];
					String nsx = arr[9];
					String ngaynhapkho= arr[10];
					String daxuat =  this.sanpham_soluongDAXUAT.get(key);
					sum = sum - Double.parseDouble(daxuat);
					if( daxuat.trim().length() > 0 )
						soloDACHON += "select '" + solo + "' as soloCHON, '" + ngayhethan + "' as ngayhethanCHON, '" + ngaynhapkho + "' as ngaynhapkhoCHON, N'" + vitri + "' as vitriCHON, '" + mathung + "' as mathungCHON, '" + mame + "' as mameCHON,'" + maphieu + "' as maphieuCHON, '" + marq + "' as marqCHON,'" + nsx + "' as nsxCHON, " + daxuat + " as soluongDACHON union ";
						
				}
			}
		}

		String query = "";
		
		/*if(scheme.trim().length() > 0)
		{
			kho = "(select kho_fk from erp_dondathang_ctkm_trakm where dondathangid = " + this.id + " and spma = '" + spMa + "' and ctkmid = (select pk_seq from ctkhuyenmai where scheme = '" + scheme + "'))";
		}
		tongluong = sum + "";
		if( soloDACHON.trim().length() > 0 )
		{
			soloDACHON = soloDACHON.substring(0, soloDACHON.length() - 7 );
			soloDACHON = " select soloCHON, ngayhethanCHON, ngaynhapkhoCHON,  vitriCHON, mathungCHON, mameCHON,maphieuCHON, SUM( soluongDACHON ) as soluongDACHON " +
						 " from ( " + soloDACHON + " ) DT group by soloCHON, ngayhethanCHON, vitriCHON, mathungCHON, mameCHON,maphieuCHON ";
		}
		else
			soloDACHON = " select '1' as soloCHON, '' as ngayhethanCHON, '' as ngaynhapkhoCHON, '' as vitriCHON, '' mathungCHON, '' mameCHON, '' maphieuCHON,0 as soluongDACHON ";
		*/
		
		
		if(scheme.trim().length() > 0)
		{
			kho = "(select kho_fk from erp_dondathang_ctkm_trakm where dondathangid = " + this.id + " and spma = '" + spMa + "' and ctkmid = (select pk_seq from ctkhuyenmai where scheme = '" + scheme + "'))";
		}
		tongluong = sum + "";
		if( soloDACHON.trim().length() > 0 )
		{
			soloDACHON = soloDACHON.substring(0, soloDACHON.length() - 7 );
			soloDACHON = " select soloCHON, ngayhethanCHON, ngaynhapkhoCHON, vitriCHON, mathungCHON, mameCHON, maphieuCHON, marqCHON, nsxCHON, SUM( soluongDACHON ) as soluongDACHON " +
						 " from ( " + soloDACHON + " ) DT group by soloCHON, ngayhethanCHON, ngaynhapkhoCHON, vitriCHON, mathungCHON, mameCHON, maphieuCHON, marqCHON, nsxCHON ";
		}
		else
			soloDACHON = " select '1' as soloCHON, '' as ngayhethanCHON,'' as ngaynhapkhoCHON,  '' as vitriCHON, '' mathungCHON, '' mameCHON, '' maphieuCHON, '' marqCHON, '' nsxCHON, 0 as soluongDACHON ";
		
		
		String sqlDONHANG = "";
		if( this.id.trim().length() > 0 )
			sqlDONHANG = " select SUM(soluong) from ERP_DONDATHANG_SANPHAM_CHITIET where dondathang_fk = '" + this.id + "' and SANPHAM_FK = DT.sanpham_fk and solo = DT.solo and ngayhethan = DT.ngayhethan and isnull( bin_fk, 0 ) = isnull( DT.bin_fk, 0 ) and mathung = DT.mathung and mame = DT.mame and maphieu = DT.maphieu   ";
		else
			sqlDONHANG = " select SUM(soluong) from ERP_DONDATHANG_SANPHAM_CHITIET where dondathang_fk = '1' and SANPHAM_FK = DT.sanpham_fk and solo = DT.solo and ngayhethan = DT.ngayhethan and isnull( bin_fk, 0 ) = isnull( DT.bin_fk, 0 ) and mathung = DT.mathung and mame = DT.mame and maphieu = DT.maphieu  ";
		
		String conditionKHONGDUOCPB = " select b.solo from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) " +
							  		  " where a.TRANGTHAI = '1' and a.tungay <= '" + this.ngayyeucau + "' and '" + this.ngayyeucau + "' <= a.denngay and a.PK_SEQ in ( select phanbo_fk from ERP_PHANBODONHANG_DOITUONG where doituong_fk != '" + this.nppId + "' ) ";
		
		String conditionDUOCPB = "  select b.solo from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) " +
				  				 " 	where a.TRANGTHAI = '1' and a.tungay <= '" + this.ngayyeucau + "' and '" + this.ngayyeucau + "' <= a.denngay and a.PK_SEQ in ( select phanbo_fk from ERP_PHANBODONHANG_DOITUONG where doituong_fk = '" + this.nppId + "' ) ";
		
		/*query =  "\n select  distinct isnull(pb.vt,0) vt, DT.SOLO, DT.NGAYHETHAN, DT.MATHUNG, DT.MAME, DT.MAPHIEU,isnull(bin.MA, '') as vitri, " + 
				 //"\n DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) as AVAILABLE, '' as tudexuat  "+
				 "\n DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) as AVAILABLE, '' as tudexuat  "+
				 "\n from "+
				 "\n ( "+
				 "\n 	select ct.sanpham_fk, ct.SOLO, ct.NGAYHETHAN, ct.mathung, ct.mame, ct.maphieu, isnull( ct.bin_fk, 0 ) as bin_fk, sum(ct.AVAILABLE) as AVAILABLE  "+
				 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
				 "\n 	where KHOTT_FK = " + kho + " and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
				 		"  and ngaynhapkho <= '" + this.ngayyeucau + "' "+
				 		"  and ( isnull(sp.batbuockiemdinh, 0) = 0 or ct.KHOTT_FK in (100074, 100078) or ( sp.batbuockiemdinh = 1 and isnull( maphieu, '' ) != ''  ) )		" +
				 "\n	group by ct.sanpham_fk, ct.SOLO, ct.NGAYHETHAN,  ct.mathung, ct.mame, ct.maphieu, isnull( ct.bin_fk, 0 )	" +
				 "\n ) "+
				 "\n DT left join ERP_BIN bin on DT.bin_fk = bin.pk_seq left join  "+
				 "\n ( "+
				 	soloDACHON +
				 "\n ) "+
				 "\n DAXUAT on DT.SOLO = DAXUAT.soloCHON  and DT.NGAYHETHAN = DAXUAT.ngayhethanCHON and isnull( bin.ma, '' ) = DAXUAT.vitriCHON  "+
				 "\n		and DT.MATHUNG = DAXUAT.mathungCHON and DT.MAME = DAXUAT.mameCHON	and DT.MAPHIEU = DAXUAT.maphieuCHON " +
				 "\n left join ( "+
				 "\n		 select '1' as vt,solo,ngayhethan,doituong_fk,sanpham_fk from ERP_PHANBODONHANG_DOITUONG a "+
				 "\n		  inner join ERP_PHANBODONHANG_SANPHAM b "+
				 "\n		on a.phanbo_fk=b.phanbo_fk where "+
				 "\n		a.doituong_fk='" + this.nppId + "' and b.sanpham_fk=( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )  "+
				 "\n		) pb on pb.solo=DT.SOLO  and pb.sanpham_fk=DT.SANPHAM_FK  "+
				 //"\n where DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) > 0 "+
				 "\n where DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) > 0 "+
				 "\n			and DT.SOLO not in ( select solo from ERP_HANGCHOPHANBO where sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) )	" +
				 "\n 			and ( DT.SOLO not in ( " + conditionKHONGDUOCPB + " ) or DT.SOLO in ( " + conditionDUOCPB + " ) )	" +
				 "\n order by isnull(vt,0) DESC ,NGAYHETHAN asc  ";*/
		
		
		query =  "\n select  distinct isnull(pb.vt,0) vt, DT.SOLO, DT.NGAYHETHAN, DT.NGAYNHAPKHO, DT.MATHUNG, DT.MAME, DT.MAPHIEU, isnull(bin.MA, '') as vitri, DT.MARQ, isnull(nsx.MA, '') as nsx, " + 
				 //"\n DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) as AVAILABLE, '' as tudexuat  "+
				 "\n DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) as AVAILABLE, '' as tudexuat  "+
				 "\n from "+
				 "\n ( "+
				 "\n 	select ct.sanpham_fk, ct.SOLO, ct.NGAYHETHAN,ct.NGAYNHAPKHO,  ct.mathung, ct.mame, ct.maphieu, isnull( ct.bin_fk, 0 ) as bin_fk, ct.marq, isnull( ct.nsx_fk, 0 ) as nsx_fk, sum(ct.AVAILABLE) as AVAILABLE  "+
				 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
				 "\n 	where KHOTT_FK = " + kho + " and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
				 		"  and ngaynhapkho <= '" + this.ngayyeucau + "' "+
				 		"  and ( isnull(sp.batbuockiemdinh, 0) = 0 or ct.KHOTT_FK in (100074, 100078) or ( sp.batbuockiemdinh = 1 and isnull( maphieu, '' ) != ''  ) )		" +
				 "\n	group by ct.sanpham_fk, ct.SOLO, ct.NGAYHETHAN,ct.NGAYNHAPKHO,  ct.mathung, ct.mame, ct.maphieu, isnull( ct.bin_fk, 0 ), ct.marq, isnull( ct.nsx_fk, 0 ) " +
				 "\n ) "+
				 "\n DT left join ERP_BIN bin on DT.bin_fk = bin.pk_seq "+
				 "\n left join ERP_NHASANXUAT nsx on DT.nsx_fk = nsx.pk_seq left join  "+
				 "\n ( "+
				 	soloDACHON +
				 "\n ) "+
				 "\n DAXUAT on DT.SOLO = DAXUAT.soloCHON  and DT.NGAYHETHAN = DAXUAT.ngayhethanCHON and DT.NGAYNHAPKHO = DAXUAT.ngaynhapkhoCHON and isnull( bin.ma, '' ) = DAXUAT.vitriCHON  "+
				 "\n		and DT.MATHUNG = DAXUAT.mathungCHON and DT.MAME = DAXUAT.mameCHON and DT.MAPHIEU = DAXUAT.maphieuCHON and DT.MArq = DAXUAT.marqCHON and DT.nsx_fk = DAXUAT.nsxCHON " +
				 "\n left join ( "+
				 "\n		 select '1' as vt,solo,ngayhethan,NGAYNHAPKHO, doituong_fk,sanpham_fk from ERP_PHANBODONHANG_DOITUONG a "+
				 "\n		  inner join ERP_PHANBODONHANG_SANPHAM b "+
				 "\n		on a.phanbo_fk=b.phanbo_fk where "+
				 "\n		a.doituong_fk='" + this.nppId + "' and b.sanpham_fk=( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )  "+
				 "\n		) pb on pb.solo=DT.SOLO  and pb.sanpham_fk=DT.SANPHAM_FK  "+
				 //"\n where DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) > 0 "+
				 "\n where DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) > 0 "+
				 "\n			and DT.SOLO not in ( select solo from ERP_HANGCHOPHANBO where sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) )	" +
				 "\n 			and ( DT.SOLO not in ( " + conditionKHONGDUOCPB + " ) or DT.SOLO in ( " + conditionDUOCPB + " ) )	" +
				 "\n order by isnull(vt,0) DESC ,NGAYHETHAN asc ,NGAYNHAPKHO ASC ";
		
		System.out.println("----LAY SO LO ( " + spMa + " ): " + query );
		String query2 = "";
		ResultSet rs = db.get(query);
		try 
		{
			double total = 0;
			while(rs.next())
			{
				double slg = 0;
				double avai = rs.getDouble("AVAILABLE");
				
				total += avai;
				
				if(total < Double.parseDouble(tongluong))
				{
					slg = avai;
				}
				else
				{
					slg =  Double.parseDouble(tongluong) - ( total - avai );
				}
					
				/*if(slg >= 0)
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '" + slg + "' as tuDEXUAT,'" + rs.getString("MATHUNG") + "' as MATHUNG,'" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MAPHIEU") + "' as maphieu union ALL ";
					
				}
				else
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '' as tuDEXUAT,'" + rs.getString("MATHUNG") + "' as MATHUNG,'" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MAPHIEU") + "' as maphieu union ALL ";
				}*/
				
				if(slg >= 0)
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '" + slg + "' as tuDEXUAT,'" + rs.getString("MATHUNG") + "' as MATHUNG,'" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MAPHIEU") + "' as maphieu,'" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("nsx") + "' as nsx ,'" + rs.getString("ngaynhapkho") + "' as ngaynhapkho  union ALL ";
					
				}
				else
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '' as tuDEXUAT,'" + rs.getString("MATHUNG") + "' as MATHUNG,'" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MAPHIEU") + "' as maphieu,'" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("nsx") + "' as nsx ,'" + rs.getString("ngaynhapkho") + "' as ngaynhapkho union ALL ";
				}
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
		}
		
		/*String query2 = "";
		NodeList rs = WebService.ExecQueryFromERP(query, "geso@@tra@@90123");
		try 
		{
			double total = 0;
			for( int i = 0; i < rs.getLength(); i++ ) 
			{
				Element element = (Element) rs.item(i);
				
				double slg = 0;
				double avai = Double.parseDouble( WebService.getValues( element, "AVAILABLE" ) );
				String solo = WebService.getValues( element, "SOLO" );
				String ngayhethan = WebService.getValues( element, "NGAYHETHAN" );
				String vitri = WebService.getValues( element, "vitri" );
				String mathung = WebService.getValues( element, "MATHUNG" );
				String mame = WebService.getValues( element, "MAME" );
				//String tudexuat =  WebService.getValues( element, "tudexuat" );
				
				if(this.id.trim().length() <= 0 )
				{
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
						query2 += "select '" + avai + "' as AVAILABLE, '" + solo + "' as SOLO, '" + ngayhethan + "' as NGAYHETHAN, '" + vitri + "' as VITRI, '" + mathung + "' as mathung, '" + mame + "' as mame, '" + slg + "' as tuDEXUAT union ALL ";
					}
					else
					{
						query2 += "select '" + avai + "' as AVAILABLE, '" + solo + "' as SOLO, '" + ngayhethan + "' as NGAYHETHAN, '" + vitri + "' as VITRI, '" + mathung + "' as mathung, '" + mame + "' as mame, '' as tuDEXUAT union ALL ";
					}
				}
				else //Khi vào cập nhật thì không đề xuất lại nữa
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + solo + "' as SOLO, '" + ngayhethan + "' as NGAYHETHAN, '" + vitri + "' as VITRI, '" + mathung + "' as mathung, '" + mame + "' as mame, '' as tuDEXUAT union ALL ";
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
		}
		*/
		if(query2.trim().length() > 0)
		{
			query2 = query2.substring(0, query2.length() - 10);
			System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
			return db.get(query2);
		}
		
		return null;
	}
	
	private String[] mySplit(String key, String operator) 
	{
		boolean exit = false;
		if( key.endsWith(operator) )
		{
			key = key + " ";
			exit = true;
		}
		
		String[] arr = key.split(operator);
		if( arr.length > 0 && exit )
			arr[ arr.length - 1 ] = arr[ arr.length - 1 ].trim();
		
		return arr;
	}
	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public Hashtable<String, String> getSanpham_SoluongDAXUAT_THEODN() 
	{
		return this.sanpham_soluongDAXUAT;
	}


	public void setSanpham_SoluongDAXUAT_THEODN(Hashtable<String, String> sp_soluong) 
	{
		this.sanpham_soluongDAXUAT = sp_soluong;
	}
	
	public String getETC()
	{
		return this.ETC;
	}

	public void setETC(String ETC) 
	{
		this.ETC = ETC;
	}
	
	
}

