package geso.traphaco.center.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.text.DecimalFormat;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import org.w3c.dom.*;

import geso.traphaco.center.beans.dondathang.imp.ErpThongtinkho;
import geso.traphaco.center.beans.routesumaryreport.IRouteSumaryReport;
import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongTBList;
import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongTLList;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.report.Ireport;
import geso.traphaco.distributor.beans.reports.imp.Reports;
import geso.traphaco.erp.util.DinhDang;
import geso.traphaco.erp.util.UtilitySyn;

public class Utility implements Serializable
{
	private static final long serialVersionUID = 1L;
	String nppId;
	String nppTen;
	String sitecode;
	String phanloai;
	String tructhuoc_fk  ;
	
	//public static final String prefixDMS = "LINK_ERP.TraphacoERP.dbo.";
	//public static final String prefixDMS = "TraphacoDMS..";
	public static final String prefixDMS = "";
	public static final String prefixLinkDMS = "LINK_DMS_THAT_NOIBO";
	public static final String prefixReportDMS = "DataCenter.dbo.";
	//public static final String prefixDMS = "";
	public static final String khoERPMapping = "100023";
	public static final String prefixERP= "LINK_ERP_THAT_NOIBO.TRAPHACOERP.dbo.";
	public static final String tblThuTien_CK = "ERP_THUTIEN";
	public static final String tblPC_UNC = "ERP_THANHTOANHOADON";
	public static final String httt_TM = "100000";
	public static final String httt_CK = "100001";
	
	DecimalFormat format_1 = new DecimalFormat( "##########.#" );
	public HashMap  <String , String > hmSearch  = new HashMap<String, String>();
		
	public String getPhanloai()
    {
		return phanloai;
    }
	public void setPhanloai(String phanloai)
    {
		this.phanloai = phanloai;
    }

	public static final int THEM = 0;
	public static final int XOA = 1;
	public static final int SUA = 2;
	public static final int XEM = 3;
	public static final int CHOT = 4;
	public static final int HUYCHOT = 5;
	public static final int CHUYEN = 6;
	public static final int SMS = 7;
	public static final int FAX = 8;
	public static final int HIENTHIALL = 9;
	public static final int XUATEXCEL = 10;
	
	public String ChietKhau(String nam)
	{
		if(nam.trim().equals("2013"))
			return "1";
			else return  "1";
	}
	
	public String Doisangchuoi(String[] checknpp)
	{
		String str = "";
		if (checknpp != null)
		{
			for (int i = 0; i < checknpp.length; i++)
			{
				if (i == 0)
				{
					str = checknpp[i];
				} else
				{
					str = str + "," + checknpp[i];
				}
			}
		}
		return str;

	}
	/*public String PhatSinhKeToanDMS(String thang,String nam,String tuNgay,String denNgay,String npp_fk,String taikhoan_fk,String taikhoandu_fk,
			String loaiChungTu){
		String sql = " OPENQUERY ("+this.prefixLinkDMS+",' \n"
				+ " SELECT NGAYGHINHAN,LOAICHUNGTU,SOCHUNGTU,TAIKHOAN_FK,TAIKHOANDOIUNG_FK,NO,CO, \n"
				+ " CASE WHEN DOITUONG =N'Khách hàng' AND ISNPP = 1 THEN N'Chi nhánh/Đối tác' \n"
				+ " WHEN DOITUONG = N'Khách hàng' AND ISNPP = 1 THEN N'Khách hàng' ELSE DOITUONG \n"
				+ " MADOITUONG,MACHUNGTU,DIENGIAI,KHOANMUCCHIPHI_FK \n"
				+ " FROM "+this.prefixReportDMS+"ERP_PHATSINHKETOAN PS \n"
				+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ= PS.S "
				+ " WHERE 1 = 1 \n";
		if(thang.length() > 0 )
			sql = sql + " AND MONTH(NGAYGHINHAN) " + thang + " \n";
		if(thang.length() > 0 )
			sql = sql + " AND NAM(NGAYGHINHAN) " + nam + " \n";
		if(thang.length() > 0 )
			sql = sql + " AND NGAYGHINHAN " + tuNgay + " \n";
		if(thang.length() > 0 )
			sql = sql + " AND NGAYGHINHAN " + denNgay + " \n";
		if(npp_fk.length() > 0 )
			sql = sql + " AND NPP_FK IN ( " + npp_fk + ") \n";
		if(taikhoan_fk.length() > 0 )
			sql = sql + " AND TAIKHOAN_FK IN (" + taikhoan_fk + ") \n";
		if(taikhoandu_fk.length() > 0 )
			sql = sql + " AND TAIKHOANDOIUNG_FK IN (" + taikhoandu_fk + ") \n";
		if(loaiChungTu.length() > 0 )
			sql = sql + " AND LOAICHUNGTU IN (" + loaiChungTu + ") \n";
		sql = sql + "')";
		return sql;
	}*/

	public String Check_Huy_NghiepVu_KhoaSo(geso.traphaco.center.db.sql.dbutils db,String table, String id, String column )
	{
		String query = " select DATEPART(MONTH,"+column+") as Thang,DATEPART(YEAR,"+column+") as Nam "+
						"from " + table + "  a  "+
						"where PK_SEQ = '" + id + "' and exists  "+
						"(  "+
						"	select 1 from KHOASOTHANG where THANGKS= DATEPART(MONTH,"+column+") "+
						"		and NAM=DATEPART(YEAR," + column + ") and NPP_FK=a.NPP_FK  "+
						")  ";
		String msg = "";
		System.out.println("SQL CHECK:: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
		    {
			    while(rs.next())
			    {
			    	msg= "Bạn không được thực hiện nghiệp vụ trong tháng đã khóa sổ !";
			    }
			    rs.close();
		    } 
			catch (Exception e)
		    {
			    e.printStackTrace();
			    return "Lỗi phát sinh khi check khóa sổ;";
		    }
		}
		return msg;
	}
	
	public String Check_Huy_NghiepVu_KhoaSo(String table, String id, String column, geso.traphaco.distributor.db.sql.dbutils db)
	{
		String query = " select DATEPART(MONTH,"+column+") as Thang,DATEPART(YEAR,"+column+") as Nam "+
						"from " + table + "  a  "+
						"where PK_SEQ = '" + id + "' and exists  "+
						"(  "+
						"	select 1 from KHOASOTHANG where THANGKS= DATEPART(MONTH,"+column+") "+
						"		and NAM=DATEPART(YEAR," + column + ") and NPP_FK=a.NPP_FK  "+
						")  ";
		String msg = "";
		System.out.println("SQL CHECK:: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
		    {
			    while(rs.next())
			    {
			    	msg= "Bạn không được thực hiện nghiệp vụ trong tháng đã khóa sổ !";
			    }
			    rs.close();
		    } 
			catch (Exception e)
		    {
			    e.printStackTrace();
			    return "Lỗi phát sinh khi check khóa sổ;";
		    }
		}
		return msg;
	}
	
	public String Check_Huy_NghiepVu_KhoaSo(String table,String id,String column,String nppId, geso.traphaco.distributor.db.sql.dbutils db)
	 {
	  String query=
	    "  select DATEPART(MONTH,"+column+") as Thang,DATEPART(YEAR,"+column+") as Nam "+
	    "  from "+table+"  a  "+
	    "  where PK_SEQ='"+id+"' and exists  "+
	    "  (  "+
	    "   select 1 from KHOASOTHANG where THANGKS= DATEPART(MONTH,"+column+") "+
	    "   and NAM=DATEPART(YEAR,"+column+") and NPP_FK='"+nppId+"'  "+
	    "  )  ";
	  
	  System.out.println("Check_Huy_NghiepVu_KhoaSo"+query);
	  String msg="";
	  ResultSet rs =db.get(query);
	  try
	    {
	     while(rs.next())
	     {
	      msg= "Bạn không được thực hiện nghiệp vụ trong tháng đã khóa sổ !";
	     }
	     rs.close();
	    } catch (Exception e)
	    {
	     e.printStackTrace();
	     return "Lỗi phát sinh khi check khóa sổ;";
	    }
	  return msg;
	 }

	public String Check_Huy_NghiepVu_KhoaSo_3010(String table,String id,String column, geso.traphaco.center.db.sql.dbutils db)
	 {
	  String query=
	    "  select DATEPART(MONTH,"+column+") as Thang,DATEPART(YEAR,"+column+") as Nam "+
	    "  from "+table+"  a  "+
	    "  where PK_SEQ='"+id+"' and exists  "+
	    "  (  "+
	    "   select 1 from KHOASOTHANG where THANGKS= DATEPART(MONTH,"+column+") "+
	    "   and NAM=DATEPART(YEAR,"+column+")  "+
	    "  )  ";
	  
	  System.out.println("Check_Huy_NghiepVu_KhoaSo"+query);
	  String msg="";
	  ResultSet rs =db.get(query);
	  try
	    {
	     while(rs.next())
	     {
	      msg= "Bạn không được thực hiện nghiệp vụ trong tháng đã khóa sổ !";
	     }
	     rs.close();
	    } catch (Exception e)
	    {
	     e.printStackTrace();
	     return "Lỗi phát sinh khi check khóa sổ;";
	    }
	  return msg;
	 }
	
	public String Check_Kho_Tong_VS_KhoCT(String nppId, geso.traphaco.distributor.db.sql.dbutils db)
	{
		//CHECK TRƯỚC 1 số kho hàng bán đang dùng
		String query =  " select count( * ) as soDONG  "+
						" from  "+ 
						" ( " +
						"	 select npp_fk, nhomkenh_fk, kho_fk, sanpham_fk, sum(available) as available, sum(soluong) as soluong, sum(booked) as booked_ct  "+
						"	 from nhapp_kho_chitiet where npp_fk = '" + nppId + "' and kho_fk in ( 100000, 100001, 100002, 100007, 100017, 100016, 100018 ) "+
						"	 group by nhomkenh_fk, npp_fk, kho_fk, sanpham_fk	  "+
						" ) " +
						" CT full outer join nhapp_kho total on total.npp_fk=ct.npp_fk and total.nhomkenh_fk=ct.nhomkenh_fk  "+
						"		and total.sanpham_fk=ct.sanpham_fk and total.kho_fk=ct.kho_fk   "+
						" where  total.kho_fk in ( 100000, 100001, 100002, 100007, 100017, 100016, 100018 ) and  "+
						"		( isnull(ct.available,0) + isnull(ct.booked_ct,0) != isnull(total.available,0) + isnull(total.booked ,0)  "+ 
						"			or isnull(total.soluong,0) != isnull(ct.soluong,0)  or isnull(total.BOOKED,0) != isnull(ct.booked_ct,0) " + 
						"		) and  isnull(total.npp_fk, ct.npp_fk) = '" + nppId + "' ";
		
		System.out.println("Check_Kho_Tong_VS_KhoCT " + query);
		String msg = "";
		ResultSet rs = db.get(query);
		try
		{
			if(rs.next())
			{
				if(rs.getInt("soDONG") > 0 )
				{
					//msg = "Lỗi phát sinh do lệch Số lượng kho tổng và chi tiết của sản phẩm trong kho. Vui lòng liên hệ Admin để xử lý ";
					
					//GHI NHAN SO LIEU TAI THOI DIEM LECH
					query = "  insert LOG_LECH_TONG_VS_CHITIET( npp_fk, nhomkenh_fk, kho_fk, sanpham_fk, soluong_total, booked_total, avai_total, soluong_chitiet, booked_chitiet, avai_chitiet ) " + 
							"  select ISNULL( total.NPP_FK, CT.NPP_FK ) as npp_fk, ISNULL( total.nhomkenh_fk, CT.nhomkenh_fk ) as nhomkenh_fk, ISNULL( total.kho_fk, CT.kho_fk ) as kho_fk, ISNULL( total.sanpham_fk, CT.sanpham_fk ) as sanpham_fk,  " + 
							"  	total.SOLUONG, total.BOOKED, total.AVAILABLE, CT.soluong, CT.booked_ct, CT.available " + 
							"  from    " + 
							"  (  " + 
							"  	 select npp_fk, nhomkenh_fk, kho_fk, sanpham_fk, sum(available) as available, sum(soluong) as soluong, sum(booked) as booked_ct   " + 
							"  	 from nhapp_kho_chitiet where npp_fk = '" + nppId + "' and kho_fk in ( 100000, 100002 )  " + 
							"  	 group by nhomkenh_fk, npp_fk, kho_fk, sanpham_fk	   " + 
							"  )  " + 
							"  CT full outer join nhapp_kho total on total.npp_fk=ct.npp_fk and total.nhomkenh_fk=ct.nhomkenh_fk   " + 
							"  		and total.sanpham_fk=ct.sanpham_fk and total.kho_fk=ct.kho_fk    " + 
							"  where  total.kho_fk in ( 100000, 100002 ) and   " + 
							"  		(  " + 
							"  			isnull(ct.available,0) + isnull(ct.booked_ct,0) != isnull(total.available,0) + isnull(total.booked ,0)    " + 
							"  			or isnull(total.soluong,0) != isnull(ct.soluong,0) or isnull(total.BOOKED,0) != isnull(ct.booked_ct,0)    " + 
							"  		)  " + 
							"  		and  isnull(total.npp_fk, ct.npp_fk) = '" + nppId + "' ";
					db.update(query);
					
					//Sendmai để xử lý ngay
					try
					{
						SendMail mail = new SendMail();
						mail.postMailWARNING("luonghv@geso.us,phuctnh@geso.us", "", "Theo dõi tồn kho PHANAM", "Vào thời điểm: " + this.getDateTime() + " nhà phân phối: " + nppId + " đã xuất hiện hiện tượng kho Tổng != Chi tiết. VUi lòng kiểm tra và xử lý gấp." );
					}
					catch(Exception ex1){ ex1.printStackTrace(); }
					
					
					//TẠM THỜI BỊ THÌ XỬ LÝ TRƯỚC ĐỂ RA ĐƠN HÀNG KHÔNG BỊ NGƯNG
					//OTC
					query = "  update kho set kho.BOOKED = ISNULL( book.soluong, 0 ),  " + 
							"  			   kho.AVAILABLE = case when kho.soluong - ISNULL( book.soluong, 0 ) < 0 then 0 else kho.soluong - ISNULL( book.soluong, 0 ) end " + 
							"  from NHAPP_KHO_CHITIET kho left join dbo.ufn_BOOKED_CHITIET( 100000, " + nppId + ", null ) book  " + 
							"  	on kho.KHO_FK = book.kho_fk and kho.NPP_FK = book.npp_fk and kho.SANPHAM_FK = book.sanpham_fk " + 
							"  			and kho.nhomkenh_fk = book.nhomkenh_fk  " + 
							"  			and kho.SOLO = book.solo and kho.NGAYHETHAN = book.ngayhethan " + 
							"  where kho.NPP_FK = '" + nppId + "' and kho.kho_fk = '100000' and kho.BOOKED != ISNULL( book.soluong, 0 ) ";
					db.update(query);
					
					query = "  update kho set kho.BOOKED = ISNULL( book.soluong, 0 ),  " + 
							"  			   kho.AVAILABLE = case when kho.soluong - ISNULL( book.soluong, 0 ) < 0 then 0 else kho.soluong - ISNULL( book.soluong, 0 ) end " + 
							"  from NHAPP_KHO kho left join  " + 
							"  	(	 " + 
							"  		select kho_fk, npp_fk, sanpham_fk, nhomkenh_fk, SUM(soluong) as soluong  " + 
							"  		from dbo.ufn_BOOKED_CHITIET( 100000, " + nppId + ", null ) " + 
							"  		group by kho_fk, npp_fk, sanpham_fk, nhomkenh_fk " + 
							"  	) book  " + 
							"  	on kho.KHO_FK = book.kho_fk and kho.NPP_FK = book.npp_fk and kho.SANPHAM_FK = book.sanpham_fk " + 
							"  			and kho.nhomkenh_fk = book.nhomkenh_fk  " + 
							"  where kho.NPP_FK = '" + nppId + "' and kho.kho_fk = '100000' and kho.BOOKED != ISNULL( book.soluong, 0 ) ";
					db.update(query);
					
					//ETC
					query = "  update kho set kho.BOOKED = ISNULL( book.soluong, 0 ),  " + 
							"  			   kho.AVAILABLE = case when kho.soluong - ISNULL( book.soluong, 0 ) < 0 then 0 else kho.soluong - ISNULL( book.soluong, 0 ) end " + 
							"  from NHAPP_KHO_CHITIET kho left join dbo.ufn_BOOKED_CHITIET( 100002, " + nppId + ", null ) book  " + 
							"  	on kho.KHO_FK = book.kho_fk and kho.NPP_FK = book.npp_fk and kho.SANPHAM_FK = book.sanpham_fk " + 
							"  			and kho.nhomkenh_fk = book.nhomkenh_fk  " + 
							"  			and kho.SOLO = book.solo and kho.NGAYHETHAN = book.ngayhethan " + 
							"  where kho.NPP_FK = '" + nppId + "' and kho.kho_fk = '100002' and kho.BOOKED != ISNULL( book.soluong, 0 ) ";
					db.update(query);
					
					query = "  update kho set kho.BOOKED = ISNULL( book.soluong, 0 ),  " + 
							"  			   kho.AVAILABLE = case when kho.soluong - ISNULL( book.soluong, 0 ) < 0 then 0 else kho.soluong - ISNULL( book.soluong, 0 ) end " + 
							"  from NHAPP_KHO kho left join  " + 
							"  	(	 " + 
							"  		select kho_fk, npp_fk, sanpham_fk, nhomkenh_fk, SUM(soluong) as soluong  " + 
							"  		from dbo.ufn_BOOKED_CHITIET( 100002, " + nppId + ", null ) " + 
							"  		group by kho_fk, npp_fk, sanpham_fk, nhomkenh_fk " + 
							"  	) book  " + 
							"  	on kho.KHO_FK = book.kho_fk and kho.NPP_FK = book.npp_fk and kho.SANPHAM_FK = book.sanpham_fk " + 
							"  			and kho.nhomkenh_fk = book.nhomkenh_fk  " + 
							"  where kho.NPP_FK = '" + nppId + "' and kho.kho_fk = '100002' and kho.BOOKED != ISNULL( book.soluong, 0 ) ";
					db.update(query);
				}
			}
		    rs.close();
		} 
		catch (Exception e)
		{
		    e.printStackTrace();
		    return "Lỗi phát sinh khi check Tồn Kho. Vui lòng liên hệ admin!";
		    //return "";
		}
		
		return msg;
	}

	public String Update_TaiKhoan_FULL_v2 (dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, 
			String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, String DOITUONG_NO, String MADOITUONG_NO, 
			String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, 
			String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, 
			String masp, String tensp, String donvi, String kbh_fk, String kho_fk, String Solo, String Ngayhethan, String tienhang, String loaiHD)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}

		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _TIENHANG = "0";
		if(tienhang.trim().length() > 0)
			_TIENHANG = tienhang;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		String _MASP = "null";
		if(masp.trim().length()>0)
			_MASP = masp;
		
		String _TENSP = "null";
		if(tensp.trim().length()>0)
			_TENSP = tensp;
		
		String _DONVI = "null";
		if(donvi.trim().length()>0)
			_DONVI = donvi;
		
		String _KBH = "null";
		if(kbh_fk.trim().length()>0)
			_KBH = kbh_fk;
		
		String _KHO = "null";
		if(kho_fk.trim().length()>0)
			_KHO = kho_fk;
		
		String _SOLO = "null";
		if(Solo.trim().length()>0)
			_SOLO = Solo;
		
		String _NGAYHETHAN = "null";
		if(Ngayhethan.trim().length()>0)
			_NGAYHETHAN = Ngayhethan;
		
		//String kho_fk, String Solo, String Ngayhethan
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG, MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI, " +
					"MACHUNGTU, ISNPP, MAHANG, TENHANG, DONVI, KBH_FK, SOLO, NGAYHETHAN, KHO_FK, TIENHANG, LOAIHD ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " +
					"" + _ndnhapxuat_fk + ", '0', " + _CO + ", N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " +
					"" + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", " +
					"N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', " +
					""+_KBH+", '"+_SOLO+"', '"+_NGAYHETHAN+"', "+_KHO+", "+_TIENHANG+", '" + loaiHD + "') ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, " +
					"DIENGIAI , MACHUNGTU, ISNPP , MAHANG, TENHANG, DONVI, KBH_FK , SOLO, NGAYHETHAN, KHO_FK, TIENHANG, LOAIHD ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " +
					"" + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", " +
					"N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+", '"+_SOLO+"', " +
					"'"+_NGAYHETHAN+"', "+_KHO+", " + _TIENHANG + ", '" + loaiHD + "' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_FULL_v2_kmcp (dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, 
			String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, String DOITUONG_NO, String MADOITUONG_NO, 
			String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, 
			String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, 
			String masp, String tensp, String donvi, String kbh_fk, String kho_fk, String Solo, String Ngayhethan, String tienhang, String loaiHD, String kmcp)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}

		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _TIENHANG = "0";
		if(tienhang.trim().length() > 0)
			_TIENHANG = tienhang;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		String _MASP = "null";
		if(masp.trim().length()>0)
			_MASP = masp;
		
		String _TENSP = "null";
		if(tensp.trim().length()>0)
			_TENSP = tensp;
		
		String _DONVI = "null";
		if(donvi.trim().length()>0)
			_DONVI = donvi;
		
		String _KBH = "null";
		if(kbh_fk.trim().length()>0)
			_KBH = kbh_fk;
		
		String _KHO = "null";
		if(kho_fk.trim().length()>0)
			_KHO = kho_fk;
		
		String _SOLO = "null";
		if(Solo.trim().length()>0)
			_SOLO = Solo;
		
		String _NGAYHETHAN = "null";
		if(Ngayhethan.trim().length()>0)
			_NGAYHETHAN = Ngayhethan;
		
		String _kmcp = "null";
		if(kmcp.trim().length()>0)
			_kmcp = kmcp;
		
		//String kho_fk, String Solo, String Ngayhethan
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG, MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI, " +
					"MACHUNGTU, ISNPP, MAHANG, TENHANG, DONVI, KBH_FK, SOLO, NGAYHETHAN, KHO_FK, TIENHANG, LOAIHD, KHOANMUCCHIPHI_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " +
					"" + _ndnhapxuat_fk + ", '0', " + _CO + ", N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " +
					"" + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", " +
					"N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', " +
					""+_KBH+", '"+_SOLO+"', '"+_NGAYHETHAN+"', "+_KHO+", "+_TIENHANG+", '" + loaiHD + "', " + _kmcp + ") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, " +
					"DIENGIAI , MACHUNGTU, ISNPP , MAHANG, TENHANG, DONVI, KBH_FK , SOLO, NGAYHETHAN, KHO_FK, TIENHANG, LOAIHD, KHOANMUCCHIPHI_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " +
					"" + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", " +
					"N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+", '"+_SOLO+"', " +
					"'"+_NGAYHETHAN+"', "+_KHO+", " + _TIENHANG + ", '" + loaiHD + "', " + _kmcp + " ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	//Giong V2_3 nhung THÊM QUAY_FK
		public String Update_TaiKhoan_FULL_v2_4 (geso.traphaco.distributor.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, 
				String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, String DOITUONG_NO, String MADOITUONG_NO, 
				String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, 
				String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, 
				String masp, String tensp, String donvi, String kbh_fk, String kho_fk, String Solo, String Ngayhethan, String tienhang, String loaiHD,String quay_fk)
		{
			String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
			if (msg.trim().length() > 0)
			{
				msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
				return msg;
			}
			
			String query;
			
			String _ndnhapxuat_fk = "null";
			if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
				_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
			
			String _sochungtu = "null";
			if(sochungtu.trim().length() > 0)
				_sochungtu = sochungtu;
			
			String _soluong = "null";
			if(SOLUONG.trim().length() > 0)
				_soluong = SOLUONG.trim();
			
			String _dongia = "null";
			if(DONGIA.trim().length() > 0)
				_dongia = DONGIA.trim();
			
			String _thanhtienViet = "null";
			if(TONGGIATRI.trim().length() > 0)
				_thanhtienViet = TONGGIATRI.trim();
			
			String _dongiaNT = "null";
			if(DONGIANT.trim().length() > 0)
				_dongiaNT = DONGIANT.trim();
			
			String _thanhtienNT = "null";
			if(TONGGIATRINT.trim().length() > 0)
				_thanhtienNT = TONGGIATRINT.trim();
					
			String _NO = "0";
			if(NO.trim().length() > 0)
				_NO = NO;
			
			String _CO = "0";
			if(CO.trim().length() > 0)
				_CO = CO;
			
			String _VAT = "0";
			if(VAT.trim().length() > 0)
				_VAT = VAT;
			
			String _TIENHANG = "0";
			if(tienhang.trim().length() > 0)
				_TIENHANG = tienhang;
			
			String _DIENGIAI = "";
			if(DienGiai.trim().length()>0)
				_DIENGIAI = DienGiai;
			
			String _MACHUNGTU = "";
			if(MaChungTu.trim().length()>0)
				_MACHUNGTU = MaChungTu;
			
			String _ISNPP = "null";
			if(isNPP.trim().length()>0)
				_ISNPP = isNPP;
			
			String _MASP = "null";
			if(masp.trim().length()>0)
				_MASP = masp;
			
			String _TENSP = "null";
			if(tensp.trim().length()>0)
				_TENSP = tensp;
			
			String _DONVI = "null";
			if(donvi.trim().length()>0)
				_DONVI = donvi;
			
			String _KBH = "null";
			if(kbh_fk.trim().length()>0)
				_KBH = kbh_fk;
			
			String _KHO = "null";
			if(kho_fk.trim().length()>0)
				_KHO = kho_fk;
			
			String _SOLO = "null";
			if(Solo.trim().length()>0)
				_SOLO = Solo;
			
			String _NGAYHETHAN = "null";
			if(Ngayhethan.trim().length()>0)
				_NGAYHETHAN = Ngayhethan;
			
			String _QUAY_FK = "null";
			if(quay_fk.trim().length() >0)
				_QUAY_FK = quay_fk;
			
			//String kho_fk, String Solo, String Ngayhethan
			
			//GHI CO
			/*if(Float.parseFloat(_CO) != 0) */
			{
				//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
						"DOITUONG, MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI, " +
						"MACHUNGTU, ISNPP, MAHANG, TENHANG, DONVI, KBH_FK, SOLO, NGAYHETHAN, KHO_FK, TIENHANG, LOAIHD,quay_fk ) " +
						"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " +
						"" + _ndnhapxuat_fk + ", '0', " + _CO + ", N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " +
						"" + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", " +
						"N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', " +
						""+_KBH+", '"+_SOLO+"', '"+_NGAYHETHAN+"', "+_KHO+", "+_TIENHANG+", '" + loaiHD + "',"+_QUAY_FK+") ";
				
				System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
				if(!db.update(query))
				{
					msg = "3.Không thể cập nhật tài khoản kế toán " + query;
					return msg;
				}
				
			}
			
			//GHI NO
			/*if(Float.parseFloat(_NO) != 0) */
			{		
				//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
						"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, " +
						"DIENGIAI , MACHUNGTU, ISNPP , MAHANG, TENHANG, DONVI, KBH_FK , SOLO, NGAYHETHAN, KHO_FK, TIENHANG, LOAIHD,quay_fk ) " +
						"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
						" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " +
						"" + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", " +
						"N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+", '"+_SOLO+"', " +
						"'"+_NGAYHETHAN+"', "+_KHO+", " + _TIENHANG + ", '" + loaiHD + "',"+_QUAY_FK+" ) ";
				
				System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
				if(!db.update(query))
				{
					msg = "3.Không thể cập nhật tài khoản kế toán " + query;
					return msg;
				}
				
			}
			
			return msg;
			
		}
	
	public String quyen_khoTT(String userId)
	{	
		//String sql ="( select KHOTT_fK from NHANVIEN_KHOTT where nhanvien_fk ='"+ userId +"')";
		String sql ="( select PK_SEQ from ERP_KHOTT where trangthai ='1')";
		return sql;
	}
	
	public String quyen_kho(String userId)
	{	
		String sql ="( select KHO_fK from NHANVIEN_KHO where nhanvien_fk ='"+ userId +"')";
		return sql;
	}
	
	public boolean coQuyen(int[] quyen, int tacvu) 
	{
		 return quyen != null && quyen.length > tacvu && quyen[tacvu] == 1;
	}
	
	public String getUrl(String servlet,String parametes)
	{
		String query=
		"	select   B.PK_SEQ as DanhMuc_FK,a.PK_SEQ as pk_Seq  , c.ten + ' > '+  b.ten as TENDANHMUC, a.ten , a.servlet,  "+  
		"			a.parameters, c.sott as stt1, b.sott as stt2, a.sott           "+
		"	from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq        "+       
		"	inner join ungdung c on b.ungdung_fk = c.pk_seq               "+
		"	where a.level = '3' and b.level = '2'  and a.TrangThai=1 and a.servlet='"+servlet+"' and a.parameters='"+parametes+"' "+           
		"	union all            "+
		"	select c.PK_SEQ as DanhMuc_FK ,a.PK_SEQ ,  c.ten   as TENDANHMUC, a.ten, a.servlet, a.parameters, c.sott as stt1, a.sott as stt2, a.sott "+ 
		"	from ungdung a  inner join ungdung c on a.ungdung_fk = c.pk_seq               "+
		"	where a.level = '3' and c.level = '1'    and a.TrangThai=1  and a.servlet='"+servlet+"' and a.parameters='"+parametes+"'    "+         
		"	order by stt1 asc, stt2 asc, sott asc  " ;
		dbutils db = new dbutils();
		String url="";
		ResultSet rs =db.get(query);
		try
	    {
		    while(rs.next())
		    {
		    	url=rs.getString("TENDANHMUC")+ " > " + rs.getString("ten");
		    }
		    if(rs!=null)rs.close();
	    } catch (SQLException e)
	    {
		    e.printStackTrace();
	    }
		finally
	    {
	    	db.shutDown();
	    }
		return url;
	}
	
	public int[] Getquyen(String servlet, String parameters, String userId)
	{
		int[] quyen = new int[6];
		int them =0;
		int xoa=0;
		int sua=0;
		int xem=0;
		int chot=0;
		int huychot=0;
		String query =	
				/*		"	select 1 as Them,1 as Xoa,1 as Sua,1 AS Xem,1 as Chot,1 as HuyChot from nhanvien where pk_Seq='"+userId+"'  ";*/
				/*	"	union  select 1 as Them,1 as Xoa,1 as Sua,1 AS Xem,1 as Chot,1 as HuyChot from nhanvien WHERE  PK_SEQ='"+userId+"' and IsAdmin=1  "+*/ 		
				" 	SELECT MAX( ISNULL(THEM,0) ) AS THEM, MAX( ISNULL(XOA,0) ) AS XOA, MAX( ISNULL(SUA,0) ) AS SUA, MAX( ISNULL(XEM,0) ) AS XEM, MAX( ISNULL(CHOT,0) ) AS CHOT, "+
				"	MAX( ISNULL(HUYCHOT,'0') ) AS HUYCHOT, MAX( ISNULL(CHUYEN,'0') ) AS CHUYEN, MAX( ISNULL(SMS,'0') ) AS SMS, MAX( ISNULL(FAX,'0') ) AS FAX, MAX( ISNULL(HIENTHIALL,'0') ) AS HIENTHIALL, MAX( ISNULL(xuatexcel,'0') ) AS xuatexcel "+
				"	FROM NHOMQUYEN  A INNER JOIN PHANQUYEN B ON A.DMQ_FK = B.DMQ_FK  "+ 
				"	INNER JOIN UNGDUNG UD ON UD.PK_SEQ=A.UNGDUNG_FK  "+
				" WHERE B.NHANVIEN_FK='"+userId+"' AND UD.SERVLET='"+servlet+"'  ";
		if( parameters.trim().length() > 0 )
			query += " AND UD.PARAMETERS='" + parameters + "' \n";
		
		if(servlet.equals("ErpChuyenkhoSXSvl")){
			query += " AND UD.PARAMETERS='" + parameters + "' \n";
		}
		System.out.println("Lay quyen: \n" + query + "\n--------------------------------------------------------------");

		dbutils db = new  dbutils();
		ResultSet rscheck= db.get(query);
		
//		String quyenSTR = "";
		try
		{
			while(rscheck.next())
			{
				if(rscheck.getInt("THEM")!=0)
				them = rscheck.getInt("THEM");

				if(rscheck.getInt("XOA")!=0)
				xoa=rscheck.getInt("XOA");

				if(rscheck.getInt("SUA")!=0)
				sua=rscheck.getInt("SUA");

				if(rscheck.getInt("XEM")!=0)
				xem=rscheck.getInt("XEM");

				if(rscheck.getInt("CHOT")!=0)
				chot=rscheck.getInt("CHOT");

				if(rscheck.getInt("HuyChot")!=0)
				huychot=rscheck.getInt("HuyChot");
				
			}
			rscheck.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		
		quyen[THEM]=them;
		quyen[XOA]=xoa;
		quyen[SUA]=sua;
		quyen[XEM]=xem;
		quyen[CHOT]=chot;
		quyen[HUYCHOT]=huychot;

		return quyen;
		
	}
	
	public int[] GetquyenNew(String servlet, String parameters, String userId)
	{
		int[] quyen = new int[11];
		int them =0;
		int xoa=0;
		int sua=0;
		int xem=0;
		int chot=0;
		int huychot=0;
		int chuyen=0;
		
		int sms=0;
		int fax=0;
		int hienthiALL=0;
		int xuatexcel=0;
		
		String query =	
				/*		"	select 1 as Them,1 as Xoa,1 as Sua,1 AS Xem,1 as Chot,1 as HuyChot from nhanvien where pk_Seq='"+userId+"'  ";*/
				/*	"	union  select 1 as Them,1 as Xoa,1 as Sua,1 AS Xem,1 as Chot,1 as HuyChot from nhanvien WHERE  PK_SEQ='"+userId+"' and IsAdmin=1  "+*/ 		
				" 	SELECT MAX( ISNULL(THEM,0) ) AS THEM, MAX( ISNULL(XOA,0) ) AS XOA, MAX( ISNULL(SUA,0) ) AS SUA, MAX( ISNULL(XEM,0) ) AS XEM, MAX( ISNULL(CHOT,0) ) AS CHOT, "+
				"	MAX( ISNULL(HUYCHOT,'0') ) AS HUYCHOT, MAX( ISNULL(CHUYEN,'0') ) AS CHUYEN, MAX( ISNULL(SMS,'0') ) AS SMS, MAX( ISNULL(FAX,'0') ) AS FAX, MAX( ISNULL(HIENTHIALL,'0') ) AS HIENTHIALL, MAX( ISNULL(xuatexcel,'0') ) AS xuatexcel "+
				"	FROM NHOMQUYEN  A INNER JOIN PHANQUYEN B ON A.DMQ_FK = B.DMQ_FK  "+ 
				"	INNER JOIN UNGDUNG UD ON UD.PK_SEQ=A.UNGDUNG_FK  "+
				" WHERE B.NHANVIEN_FK='"+userId+"' AND UD.SERVLET='"+servlet+"'  ";
		if( parameters.trim().length() > 0 )
			query += " AND UD.PARAMETERS='" + parameters + "' ";
		System.out.println("Lay quyen: " + query);
		if(servlet.equals("ErpChuyenkhoSXSvl")){
			   query += " AND UD.PARAMETERS='" + parameters + "' ";
			  }
			  
		dbutils db = new  dbutils();
		ResultSet rscheck= db.get(query);
		
//		String quyenSTR = "";
		try
		{
			while(rscheck.next())
			{
				//if(rscheck.getInt("THEM")!=0)
				them=rscheck.getInt("THEM");

				//if(rscheck.getInt("XOA")!=0)
				xoa=rscheck.getInt("XOA");

				//if(rscheck.getInt("SUA")!=0)
				sua=rscheck.getInt("SUA");

				//if(rscheck.getInt("XEM")!=0)
				xem=rscheck.getInt("XEM");

				//if(rscheck.getInt("CHOT")!=0)
				chot=rscheck.getInt("CHOT");

				//if(rscheck.getInt("HuyChot")!=0)
				huychot=rscheck.getInt("HuyChot");

				chuyen=rscheck.getInt("Chuyen");
				
				sms=rscheck.getInt("SMS");
				fax=rscheck.getInt("FAX");
				hienthiALL=rscheck.getInt("HIENTHIALL");
				xuatexcel=rscheck.getInt("xuatexcel");

			}
			rscheck.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		
		quyen[THEM]=them;
		quyen[XOA]=xoa;
		quyen[SUA]=sua;
		quyen[XEM]=xem;
		quyen[CHOT]=chot;
		quyen[HUYCHOT]=huychot;
		quyen[CHUYEN]=chuyen;
		
		quyen[SMS]=sms;
		quyen[FAX]=fax;
		quyen[HIENTHIALL]=hienthiALL;
		quyen[XUATEXCEL]=xuatexcel;

		return quyen;
		
	}
	
	public int[] Getquyen_Noidungnhapxuat( String noidungnhapid, String userId)
	{
		int[] quyen = new int[11];
		int them =0;
		int xoa=0;
		int sua=0;
		int xem=0;
		int chot=0;
		int huychot=0;
		int chuyen=0;
		
		int sms=0;
		int fax=0;
		int hienthiALL=0;
		int xuatexcel=0;
		
		String query =	" 	SELECT A.*, ISNULL(THEM,0) AS THEM,ISNULL(XOA,0) AS XOA,ISNULL(SUA,0) AS SUA,ISNULL(XEM,0) AS XEM,ISNULL(CHOT,0) AS CHOT,  "+
					" 	ISNULL(HUYCHOT,'0') AS HUYCHOT, ISNULL(CHUYEN,'0') AS CHUYEN, ISNULL(SMS,'0') AS SMS, ISNULL(FAX,'0') AS FAX, ISNULL(HIENTHIALL,'0') AS HIENTHIALL, ISNULL(xuatexcel,'0') AS xuatexcel "+ 
					" 	FROM DANHMUCQUYEN_NOIDUNGNHAP  A   "+
					" 	INNER JOIN PHANQUYEN B ON A.DMQ_FK = B.DMQ_FK   "+
					" 	WHERE B.Nhanvien_fk ="+userId+"  AND A.NOIDUNGNHAP_FK="+noidungnhapid+"  ";
		 
		 System.out.println(query);
		dbutils db = new  dbutils();
		ResultSet rscheck= db.get(query);
		
//		String quyenSTR = "";
		try
		{
			while(rscheck.next())
			{
				//if(rscheck.getInt("THEM")!=0)
				them=rscheck.getInt("THEM");

				//if(rscheck.getInt("XOA")!=0)
				xoa=rscheck.getInt("XOA");

				//if(rscheck.getInt("SUA")!=0)
				sua=rscheck.getInt("SUA");

				//if(rscheck.getInt("XEM")!=0)
				xem=rscheck.getInt("XEM");

				//if(rscheck.getInt("CHOT")!=0)
				chot=rscheck.getInt("CHOT");

				//if(rscheck.getInt("HuyChot")!=0)
				huychot=rscheck.getInt("HuyChot");

				chuyen=rscheck.getInt("Chuyen");
				
				sms=rscheck.getInt("SMS");
				fax=rscheck.getInt("FAX");
				hienthiALL=rscheck.getInt("HIENTHIALL");
				xuatexcel=rscheck.getInt("xuatexcel");

			}
			rscheck.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		
		quyen[THEM]=them;
		quyen[XOA]=xoa;
		quyen[SUA]=sua;
		quyen[XEM]=xem;
		quyen[CHOT]=chot;
		quyen[HUYCHOT]=huychot;
		quyen[CHUYEN]=chuyen;
		
		quyen[SMS]=sms;
		quyen[FAX]=fax;
		quyen[HIENTHIALL]=hienthiALL;
		quyen[XUATEXCEL]=xuatexcel;

		return quyen;
		
	}
	
	public int[] Getquyen(  String parameters, String userId)
	{
		int[] quyen = new int[6];
		int them =1;
		int xoa=1;
		int sua=1;
		int xem=1;
		int chot=1;
		int huychot=1;
		String query =	
			"	select 1 as Them,1 as Xoa,1 as Sua,1 AS Xem,1 as Chot,1 as HuyChot from nhanvien where pk_Seq='"+userId+"'  "+
			"	union  select 1 as Them,1 as Xoa,1 as Sua,1 AS Xem,1 as Chot,1 as HuyChot from nhanvien WHERE  PK_SEQ='"+userId+"' and IsAdmin=1  "+ 		
			" 	SELECT ISNULL(THEM,0) AS THEM,ISNULL(XOA,0) AS XOA,ISNULL(SUA,0) AS SUA,ISNULL(XEM,0) AS XEM,ISNULL(CHOT,0) AS CHOT, "+
			"	ISNULL(HUYCHOT,'0') AS HUYCHOT "+
			"	FROM NHOMQUYEN  A INNER JOIN PHANQUYEN B ON A.DMQ_FK = B.DMQ_FK  "+ 
			"	INNER JOIN UNGDUNG UD ON UD.PK_SEQ=A.UNGDUNG_FK  "+
			" WHERE B.NHANVIEN_FK='"+userId+"'  AND UD.PARAMETERS='"+parameters+"' ";
		System.out.println("[QUERY]"+query);
		
		dbutils db = new  dbutils();
		ResultSet rscheck= db.get(query);
			try
      {
        while(rscheck.next())
        {
        	if(rscheck.getInt("THEM")!=0)
        		them=rscheck.getInt("THEM");
        	
        	if(rscheck.getInt("XOA")!=0)
        		xoa=rscheck.getInt("XOA");
        	
        	if(rscheck.getInt("SUA")!=0)
        		sua=rscheck.getInt("SUA");
        	
        	if(rscheck.getInt("XEM")!=0)
        		xem=rscheck.getInt("XEM");
        	
        	if(rscheck.getInt("CHOT")!=0)
        		chot=rscheck.getInt("CHOT");
        	
        	if(rscheck.getInt("HuyChot")!=0)
        		huychot=rscheck.getInt("HuyChot");
        	
        }
        rscheck.close();
  			
      } catch (SQLException e)
      {
        e.printStackTrace();
      }
			finally
			{
				if(db!=null)db.shutDown();
			}
		
		//TAM FULL QUYEN DE TEST
		quyen[THEM]=them;
		quyen[XOA]=xoa;
		quyen[SUA]=sua;
		quyen[XEM]=xem;
		quyen[CHOT]=chot;
		quyen[HUYCHOT]=huychot;
		
		return quyen;
		
	}
	
	public String getTructhuoc_fk()
    {
		return tructhuoc_fk;
    }
	
	public void setTructhuoc_fk(String tructhuoc_fk)
    {
		this.tructhuoc_fk = tructhuoc_fk;
    }
	
	public String getIdNhapp(String userid)
	{
		String sql=
					"			select nv.dangnhap,npp.LoaiNPP, npp.khosap, npp.pk_seq,npp.sitecode,npp.ten,npp.tructhuoc_fk,nv.PhanLoai  \n"+ 
					"			from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode=sitecode   \n"+
					"			where nv.pk_seq='"+userid+"' and nv.trangthai='1'  and nv.PHANLOAI=1 and npp.isKHACHHANG = '0'  \n"+ 
					"			union all  \n"+
					"			select nv.dangnhap,npp.LoaiNPP, npp.khosap, npp.pk_seq,npp.sitecode,gs.ten,npp.tructhuoc_fk,nv.PhanLoai  \n"+ 
					"			from nhanvien nv inner join GIAMSATBANHANG gs on nv.GSBH_FK=gs.PK_SEQ  \n"+
					"				inner join NHAPHANPHOI npp on npp.SITECODE=nv.CONVSITECODE  \n"+
					"			where nv.pk_seq='"+userid+"' and nv.trangthai='1'  and nv.PHANLOAI=2 and gs.TRANGTHAI=1  \n"+
					"			and npp.TRANGTHAI=1 \n";
		
		System.out.println("___getIdNhapp__\n" + sql + "\n----------------------------------------");

		dbutils db=new dbutils();
		ResultSet rs= db.get(sql);
		try
		{
			if(rs.next())
			{
			 this.nppId=rs.getString("pk_seq");
			 this.nppTen=rs.getString("ten");
			 this.sitecode=rs.getString("sitecode");
			 this.tructhuoc_fk = rs.getString("tructhuoc_fk") == null?"":rs.getString("tructhuoc_fk");
			 this.phanloai=rs.getString("PhanLoai") == null?"":rs.getString("PhanLoai");
			 
			}
			rs.close();
			
		}catch(Exception er){
			er.printStackTrace();
		}
		db.shutDown();
		return this.nppId;
	}
	
	public String getTieuDe(String table,String column,String id ,geso.traphaco.center.db.sql.dbutils db)
	{
		String query=" select  dbo.ftBoDau("+column+")  from "+table+" where pk_seq in ("+id+")";
		ResultSet rs= db.get(query);
		String tieude="";
		
		try
		{
			while(rs.next())
			{
				tieude +=java.net.URLDecoder.decode(rs.getString(1).replaceAll("%(?![0-9a-fA-F]{2})", "%25").replace(" ", "-"), "UTF-8");
			}
			rs.close();
		}catch(Exception er)
		{
			/*System.out.print("[TieuDe]"+query);*/
			er.printStackTrace();
		}
		return tieude;
	}
	 
	public static String replacer(StringBuffer outBuffer) 
	{
	      String data = outBuffer.toString();
	      try {
	         data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
	         data = data.replaceAll("+", "%2B");
	         data = URLDecoder.decode(data, "utf-8");
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return data;
	   }
	
	
	
	public String setTieuDe(IStockintransit obj  )
	{
		String tieude="_";
	    dbutils db = new dbutils();
	   
	    if(obj.getkenhId()!=null && obj.getkenhId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "KenhBanHang","Ten", obj.getkenhId(), db )+ "_"; 
	    }
	    if(obj.getdvkdId()!=null && obj.getdvkdId().length()>0)
	    {
	    	tieude += this.getTieuDe( "DonViKinhDoanh","DienGiai", obj.getdvkdId(),db ) + "_" ;
	    }
	    
	    if(obj.getvungId()!=null && obj.getvungId().length()>0)
	    {
	    	tieude += this.getTieuDe( "Vung","Ten", obj.getvungId(),db ) + "_" ;
	    }
	    
	    if(obj.getkhuvucId()!=null && obj.getkhuvucId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getkhuvucId(),db ) + "_" ;
	    }
	    
	    if(obj.getnppId()!=null && obj.getnppId().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getnppId(), db ) + "_" ;
	    }
	    
	    if(obj.getASMId()!=null && obj.getASMId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "ASM","Ten", obj.getASMId(), db )+ "_"; 
	    }
	    if(obj.getBMId()!=null && obj.getBMId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "BM","Ten", obj.getBMId(), db )+ "_"; 
	    }
	    
	    if(obj.getgsbhId()!=null && obj.getgsbhId().length()>0)
	    {
	    	tieude += this.getTieuDe( "GiamSatBanHang","Ten", obj.getgsbhId(),db ) + "_" ;
	    }
	    if(obj.getDdkd()!=null && obj.getDdkd().length()>0)
	    {
	    	tieude += this.getTieuDe( "DaiDienKinhDoanh","Ten", obj.getDdkd(),db ) + "_" ;
	    }
	    if(obj.getnhanhangId()!=null && obj.getnhanhangId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "NhanHang","Ten", obj.getnhanhangId(),db ) + "_" ;
	    }
	    if(obj.getchungloaiId()!=null && obj.getchungloaiId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "ChungLoai","Ten", obj.getchungloaiId(),db ) + "_" ;
	    }
	    if(obj.getNspId()!=null && obj.getNspId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "NhomSanPham","Ten", obj.getNspId(),db ) + "_" ;
	    }
	    if(obj.getsanphamId()!=null && obj.getsanphamId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "SanPham","Ten", obj.getsanphamId(),db ) + "_" ;
	    }
	    if(obj.getctkmtlId()!=null && obj.getctkmtlId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "CTKHUYENMAI","DienGiai", obj.getctkmtlId(), db )+ "_"; 
	    }
	    if(obj.getPrograms()!=null && obj.getPrograms().length()>0)
	    {
	    	tieude +=this.getTieuDe( "CTKHUYENMAI","DienGiai", obj.getPrograms(), db )+ "_"; 
	    }
	    if(obj.getpromotion()!=null && obj.getpromotion().length()>0)
	    {
	    	tieude +=this.getTieuDe( "CTKHUYENMAI","DienGiai", obj.getpromotion(), db )+ "_"; 
	    }
	    
	    if(obj.getdvdlId()!=null && obj.getdvdlId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "DonViDoLuong","DonVi", obj.getdvdlId(), db )+ "_"; 
	    }
    	 if(obj.gettungay()!=null && obj.gettungay().length()>0)
	    {
	    	tieude += obj.gettungay() + "_";
	    }
    	 if(obj.getdenngay()!=null && obj.getdenngay().length()>0)
	    {
	    	tieude += obj.getdenngay() + "_";
	    }
    	 if(obj.getFromMonth()!=null && obj.getFromMonth().length()>0)
	    {
	    	tieude += obj.getFromMonth() + "_";
	    }
    	 if(obj.getFromYear()!=null && obj.getFromYear().length()>0)
	    {
	    	tieude += obj.getFromYear() + "_";
	    }
    	 if(obj.getToMonth()!=null && obj.getToMonth().length()>0)
	    {
	    	tieude += obj.getToMonth() + "_";
	    }
    	 if(obj.getToYear()!=null && obj.getToYear().length()>0)
	    {
	    	tieude += obj.getToYear() + "_";
	    }
	 
	    db.shutDown();
		return tieude;
	}
	
	public String setTieuDe(Reports obj  )
	{
		 String tieude="";
	    dbutils db = new dbutils();
	   
	    
	    if(obj.getKenhId()!=null && obj.getKenhId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "KenhBanHang","Ten", obj.getKenhId(), db )+ "_"; 
	    }
	    
	    if(obj.getVungId()!=null && obj.getVungId().length()>0)
	    {
	    	tieude += this.getTieuDe( "Vung","Ten", obj.getVungId(),db ) + "_" ;
	    }
	    
	    if(obj.getKhuVucId()!=null && obj.getKhuVucId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getKhuVucId(),db ) + "_" ;
	    }
	    
	    if(obj.getNppId()!=null && obj.getNppId().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getNppId(), db ) + "_" ;
	    }
	    
	    if(obj.getcttbid()!=null && obj.getcttbid().length()>0)
	    {
	    	tieude +=this.getTieuDe( "CTTRUNGBAY","DienGiai", obj.getcttbid(), db )+ " "; 
	    }
	    if(obj.getNhanHang()!=null && obj.getNhanHang().length()>0)
	    {
	    	tieude +=this.getTieuDe( "NhanHang","Ten", obj.getNhanHang(), db )+ "_"; 
	    }
	    
	    if(obj.getSanPhamId()!=null && obj.getSanPhamId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "SanPham","Ten", obj.getSanPhamId(),db ) + "_" ;
	    }
	    if(obj.getSKU()!=null && obj.getSKU().length()>0 )
	    {
	    	tieude += this.getTieuDe( "SanPham","Ten", obj.getSKU(),db ) + "_" ;
	    }		    
    	 if(obj.getTuNgay()!=null && obj.getTuNgay().length()>0)
	    {
	    	tieude += obj.getTuNgay() + "_";
	    }
    	 
	    db.shutDown();
		return tieude;
	}
	
	public String setTieuDe(ITieuchithuongTLList obj  )
	{
		 String tieude="";
	    dbutils db = new dbutils();

	    if(obj.getVungId()!=null && obj.getVungId().length()>0)
	    {
	    	tieude += this.getTieuDe( "Vung","Ten", obj.getVungId(),db ) + "_" ;
	    }
	    
	    if(obj.getKvId()!=null && obj.getKvId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getKvId(),db ) + "_" ;
	    }
	    
	    if(obj.getNppIds()!=null && obj.getNppIds().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getNppIds(), db ) + "_" ;
	    }
	    if(obj.getSchemeIds()!=null && obj.getSchemeIds().length()>0)
	    {
	    	tieude += this.getTieuDe( "CTKhuyenMai","DienGiai", obj.getSchemeIds(), db ) + "_" ;
	    }
	    if(obj.getNam()!=null && obj.getNam().length()>0)
	    {
	    	tieude += obj.getNam() +"_";
	    }
	    if(obj.getThang()!=null && obj.getThang().length()>0)
	    {
	    	tieude += obj.getThang() +"_";
	    }

	    if(obj.getTungay()!=null && obj.getTungay().length()>0)
	    {
	    	tieude += obj.getTungay() +"_";
	    }
	    if(obj.getDenngay()!=null && obj.getDenngay().length()>0)
	    {
	    	tieude += obj.getDenngay() +"_";
	    }
	    db.shutDown();
		return tieude;
	}
	
	public String getTenNhaPP(){
		return this.nppTen;
	}
	
	public String getSitecode(){
		return this.sitecode;
	}
	
	public String ValidateParam(String param){		
		String result;
		if (param == null){
			result="";
		}else{
			if (param.indexOf("=") > 0){
				result = "";
			}else{
				result = param;
			}
		}
		return result;
	}

	public boolean isValidDate(String inDate) {

	    if (inDate == null)
	      return false;

	    //set the format to use as a constructor argument
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	    
	    if (inDate.trim().length() != dateFormat.toPattern().length())
	      return false;

	    dateFormat.setLenient(false);
	    
	    try {
	      //parse the inDate parameter
	      dateFormat.parse(inDate.trim());
	    }
	    catch (ParseException pe) {
	    	pe.printStackTrace();
	      return false;
	    }
	    return true;
	  }
	
	public String getUserId(String querystring){
	    String userId;
	    String tmp;
		if (querystring != null){
	    	if (querystring.contains("&")){
	    		tmp = querystring.split("&")[0];
	    	}else{
	    		tmp = querystring;
	    	}
	    	
	    	userId = tmp.split("=")[1];
	    	if(userId.contains(";"))
	    		userId = userId.split(";")[0];
		}else{
			userId = "";
		}
	    return userId;
	}
	
	public String getAction(String querystring){
	    String action;
	    String tmp;
		if (querystring != null){
	    	if (querystring.contains("&")){
	    		tmp = querystring.split("&")[1];
	    		action = tmp.split("=")[0];
	    	}else{
	    		action = "";
	    	}
		}else{
			action = "";
		}
	    return action;
		
	}

	public String getId(String querystring){
	    String id;
	    String tmp;
		if (querystring != null){
	    	if (querystring.contains("&")){
	    		tmp = querystring.split("&")[1];
	    		id = tmp.split("=")[1];
	    	}else{
	    		id = "";
	    	}
		}else{
			id = "";
		}
	    return id;
		
	}

	public Hashtable<Integer, String>  ArraystringToHashtable(String[] s){
		Hashtable<Integer, String> h = new Hashtable<Integer, String>();
		if(s != null){
			int size = s.length;
			int m = 0;
			while(m < size){
				h.put(new Integer(m), s[m]) ;
				m++;
			}
		}else{
			h.put(new Integer(0), "null");
		}
		return h;
	}

	public String[] ResultSetToArrayString(ResultSet rs)
	{
		String[] s = new String[10];
		try{
			int m = rs.getFetchSize();
			s = new String[m+1];		 	
			while(rs.next()){
				s[1] = rs.getString(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return s;
	}

	// tra ve nhung thanh phan cua s1 khong nam trong s2
	public String[] compareArrayString(String[] s1, String[] s2){
		int i = s1.length;
		int j = s2.length;	
		
		String[] s = new String[i];
		int k = 0;
		for (int m = 0; m < i; m++){
			boolean result = true;
			for (int n = 0; n < j; n++){
				if (s1[m].equals(s2[n])){
					result = false;
				}
				if (result){
					s[k++]=s1[m];
				}
			}
		}
		return s;
	}
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public	boolean isNumeric(String input){ 
		boolean result = true;
		char[] all = input.toCharArray();
		
		for(int i = 0; i < all.length;i++) {
		   if(!(Character.isDigit(all[i]))) {
			   result = false;
		   }
		}
		return result;
	}
	
	public String calSum(String a, String b)
	{
		if(a==null||b==null)
		return "";
		String s = "" + (a.length()+ b.length())/a.length();
		return s;
	}
	
	public boolean check(String a, String b, String c)
	{
		if(a==null||b==null||c==null)
			return false;
		String tmp = calSum(a, b);
		if (tmp.equals(c)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isSessionAlive(HttpSession session){
		String userId = (String)session.getAttribute("userId");
		String userTen = (String)session.getAttribute("userTen");
		String sum = (String)session.getAttribute("sum");
		if(check(userId, userTen, sum)){		
			return true;
		}else{			
			return false;
		}
	}

	public String quyen_sanpham(String userId)
	{
		//String sql ="( select sanpham_fk from nhanvien_sanpham where nhanvien_fk ='"+ userId +"'     union  select PK_sEQ FROM SanPham WHERE 1=(SELECT iSAdmin FROM NHANVIEN WHERE iSAdmin=1 AND PK_SEQ='"+userId+"')   )";
		//String sql ="( select sanpham_fk from nhanvien_sanpham where nhanvien_fk ='"+ userId +"'     )";
		String sql ="( select pk_seq from SANPHAM where trangthai = '1'     )";
		return sql;
	}
	
	public String quyen_npp(String userId)
	{   
		String sql =" ( select npp_fk from phamvihoatdong where nhanvien_fk ='"+ userId +"'   ) ";

		return sql;
	}
	public String quyen_npp_DMS(String userId)
	{   
		String sql =" ( select npp_fk from LINK_DMS_THAT.DATACENTER.dbo.phamvihoatdong   ) ";

		return sql;
	}
	
	
	public String Quyen_Ddkd(String userId)
	{   
		String sql ="( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk in  ( select npp_fk from phamvihoatdong where nhanvien_fk ='"+userId+"'      )) ";
		return sql;
	}
	public String Quyen_Ddkd_DMS(String userId)
	{   
		String sql ="( select ddkd_fk from LINK_DMS_THAT.DATACENTER.dbo.DAIDIENKINHDOANH_NPP ) ";
		return sql;
	}
	
	public String Quyen_KhuVuc(String userId)
	{   
		String sql ="( select pk_Seq from KhuvUC where pk_Seq in  " +
				" ( select khuvuc_Fk from NhapHANphoi where khuvuc_Fk is not null and  pk_Seq in "+quyen_npp(userId)+"   ) )";
		return sql;
	}
	
	public String Quyen_KhuVuc_DMS(String userId)
	{   
		String sql ="( select pk_Seq from LINK_DMS_THAT.DATACENTER.dbo.KhuvUC where pk_Seq in  " +
				" ( select khuvuc_Fk from LINK_DMS_THAT.DATACENTER.dbo.NhapHANphoi where khuvuc_Fk is not null  ) )";
		return sql;
	}
	
	public String Quyen_Vung(String userId)
	{   
		String sql ="( select vung_fk from KhuvUC where pk_Seq in  " +
				" ( select khuvuc_Fk from NhapHANphoi where khuvuc_Fk is not null and  pk_Seq in "+quyen_npp(userId)+"   ) )";
		return sql;
	}
	public String Quyen_Vung_DMS(String userId)
	{   
		String sql ="( select vung_fk from LINK_DMS_THAT.DATACENTER.dbo.KhuvUC where pk_Seq in  " +
				" ( select khuvuc_Fk from LINK_DMS_THAT.DATACENTER.dbo.NhapHANphoi where khuvuc_Fk is not null  ) )";
		return sql;
	}
	
	public String Quyen_TinhThanh(String userId)
	{   
		String sql ="( select pk_Seq from TinhThanh where pk_Seq in  " +
				" ( select tinhthanh_fk from NhapHANphoi where tinhthanh_fk is not null and  pk_Seq in "+quyen_npp(userId)+"   ) )";
		return sql;
	}
	
	public String Quyen_TinhThanh_DMS(String userId)
	{   
		String sql ="( select pk_Seq from LINK_DMS_THAT.DATACENTER.dbo.TinhThanh where pk_Seq in  " +
				" ( select tinhthanh_fk from LINK_DMS_THAT.DATACENTER.dbo.NhapHANphoi where tinhthanh_fk is not null    ) )";
		return sql;
	}
	
	public String quyen_kenh(String userId)
	{
		String sql ="( select kenh_fk as kbh_fk from nhanvien_kenh where nhanvien_fk ='"+ userId +"'    )";
	//	String sql ="( select pk_seq from KENHBANHANG where trangthai ='1' )";
		return sql;
	}
	
	public int[] quyen_ungdung(String userId)
	{  
		int mang[] = new int[300];
		String sql ="select ungdung_fk from nhomquyen where dmq_fk in (select pk_seq from danhmucquyen where pk_seq in (select dmq_fk from phanquyen where nhanvien_fk ='"+ userId +"'))";
		System.out.println("PHAN QUYEN : " + sql);
		dbutils db=new dbutils();
		ResultSet rs= db.get(sql);
		for(int j = 0;j<300;j++)
			mang[j] = 0;
		int i = 0;
		if(rs!=null)
			try {
				while(rs.next())
				{
					i = Integer.parseInt(rs.getString("ungdung_fk"));
					mang[i] = 1;
					
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			db.shutDown();
		return mang;
	}

	public ResultSet getMenuRs(String userId, String loaiMenu, String congtyId, String tdv_dangnhap_id, String npp_duocchon_id, dbutils db )
	{
		String condition = "";
		String condition2 = "";
		String condition3 = "";
		
		String conditionPQ = "";
		
		//System.out.println("::: TDV DANG NHAP: " + tdv_dangnhap_id + "  -- USER OLD: " +  userId );
		/*if( tdv_dangnhap_id.trim().length() > 0 )
		{
			//Neu dang nhap la TDV thi Lay userId mac dinh cua NPP nay de ve menu
			String query = " select top(1) pk_seq from NhanVien where trangthai = '1' and convsitecode = (select sitecode from nhaphanphoi where pk_seq = '" + npp_duocchon_id + "')  ";
			System.out.println(":::: LAY USER MAC DINH CUA NPP: " + query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try
				{
					if(rs.next())
					{
						userId = rs.getString("pk_seq");
					}
					rs.close();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}*/
		//System.out.println("::: NPP DANG NHAP: " + npp_duocchon_id + "  -- USER NEW: " +  userId );
		
		conditionPQ += " ( select ungdung_Fk from NhanVien_UngDung where NhanVien_fk='"+userId+"' ) ";
		
		if(congtyId.trim().length() >= 6) //menu MTV
		{
			condition += " AND ( ud.menuMTV = 1 or ud.menuMTV = 2 ) ";
			condition2 += " AND ( a.menuMTV = 1 or a.menuMTV = 2 ) ";
			condition3 += " AND ( menuMTV = 1 or menuMTV = 2 ) ";
		}
		else //menu NPP
		{
			condition += " AND ( ud.menuMTV = 0 or ud.menuMTV = 2 ) ";
			condition2 += " AND ( a.menuMTV = 0 or a.menuMTV = 2 ) ";
			condition3 += " AND ( menuMTV = 0 or menuMTV = 2 ) ";
		}
		
		/*System.out.println(":: CONDITION: " + condition );
		System.out.println(":: CONDITION 2: " + condition2 );
		System.out.println(":: CONDITION 3: " + condition3 );
		System.out.println(":: CONDITION PQ: " + conditionPQ );*/
		
		String query=
				" select pk_seq as ungdungcha, '' as level1, '' as level2, ten, servlet, parameters, sott as stt1, -1 as stt2, -1 as sott, level,       \n"+   
				"   	isnull((select count(*)  from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq inner join ungdung c on b.ungdung_fk = c.pk_seq   \n"+   
				"   		where  a.trangthai=1 and b.trangthai=1 and c.trangthai=1 and c.pk_seq = ud.pk_seq and a.level = '3' and a.PK_SEQ in  (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk='"+userId+"')),0) as cosubmenu,0 as maxstt, 0 as totalstt, 1 as max_total_stt, 1 as molevel1, 0 as molevel2        \n"+   
				"   from ungdung ud       \n"+   
				"   where level = '1'   and ud.loaiMenu='"+loaiMenu+"' and ud.TrangThai=1    "+ condition +  "\n" +
				"   and ud.PK_SEQ in 	  \n"+   
				"   (  \n"+   
				"   	select PK_SEQ from UNGDUNG where ungdung_fk is null and Level=1  \n"+   
				"   	and PK_SEQ in (select distinct UNGDUNG.ungdung_fk from nhanvien_ungdung inner join UNGDUNG on nhanvien_ungdung.ungdung_fk=UNGDUNG.PK_SEQ  \n"+   
				"   	where nhanvien_ungdung.nhanvien_Fk='"+userId+"' and ungdung.level=3 )  and UNGDUNG.TrangThai=1   \n"+   
				"   union  \n"+    
				"   	select PK_SEQ from UNGDUNG where ungdung_fk is null and Level=1  \n"+   
				"   	and PK_SEQ in (select distinct ud.ungdung_fk from nhanvien_ungdung inner join UNGDUNG on nhanvien_ungdung.ungdung_fk=UNGDUNG.PK_SEQ  \n"+   
				"   	inner join UNGDUNG ud on ud.PK_SEQ=ungdung.ungdung_fk  \n"+   
				"   	where nhanvien_ungdung.nhanvien_Fk='"+userId+"' and ungdung.level=3 ) and UNGDUNG.TrangThai=1  \n"+   
				"    )  \n"+   
				"     \n"+   
			"   union all         \n"+   
				"   select b.pk_seq as ungdungcha, '' as level1, '' as level2, a.ten, a.servlet, a.parameters, b.sott as stt1, a.sott as stt2, -1 as sott, a.level,   \n"+   
				"   0, 0 as		maxstt, 0 as totalstt, 1 as max_total_stt, 0 as molevel1, 1 as molevel2         \n"+   
				"   from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq       \n"+   
				"   where a.level = '2' 	 and a.loaiMenu='"+loaiMenu+"' 	and a.TrangThai=1 and b.TrangThai=1  "+   condition2 + "\n" +
				"   and exists  	  \n"+   
				"   (   \n"+   
				"   	select nvud.ungdung_Fk from NhanVien_UngDung nvud inner join UNGDUNG ud on nvud.ungdung_fk=ud.PK_SEQ  \n"+   
				"   	 where nvud.NhanVien_fk='"+userId+"'    and ud.UNGDUNG_FK=a.PK_SEQ     \n"+   
				"   )  \n"+   
			"   union all         \n"+   
				"   select b.pk_seq as ungdungcha, c.ten as level1, b.ten as level2, a.ten, a.servlet, a.parameters, c.sott as stt1, b.sott as stt2, a.sott, a.level, 0,  \n"+   
				"   ( 	 select max(sott)  from ungdung \n" + 
				"		 where trangthai=1 and ungdung_fk = b.pk_seq " + condition3 + " and PK_SEQ 	in " + conditionPQ + " )   as maxstt, \n" + 
				" 	b.sott + a.sott as totalstt,   \n"+
				"	isnull(( select max(sott) from ungdung where ungdung_fk=b.pk_seq and PK_SEQ IN " + conditionPQ + " ), 0)\n" +
				" + isnull(( select max(sott) from ungdung l1  where trangthai=1 and ungdung_fk =c.PK_SEQ   "+
				"		AND PK_sEQ IN (select b.ungdung_fk from NhanVien_UngDung a inner join UNGDUNG b on b.PK_SEQ=a.UngDung_fk where NhanVien_fk='"+userId+"') ),0)  as max_total_stt, 0 as molevel1, 0 as molevel2	      \n"+   
				"   from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq          \n"+   
				"   inner join ungdung c on b.ungdung_fk = c.pk_seq         \n"+   
				"   where a.level = '3' and b.level = '2'     and a.loaiMenu='"+loaiMenu+"' and a.TrangThai=1 and b.TrangThai=1 and c.TrangThai=1 "+  condition2 + "\n" +
				"   and a.pk_seq    \n"+   
				"   in  (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk='"+userId+"')  \n"+   
			"   union all      \n"+   
				"   select a.pk_seq as ungdungcha, c.ten as level1, '' as level2, a.ten, a.servlet, a.parameters, c.sott as stt1, a.sott as stt2, a.sott, a.level, 0,       \n"+   
				"   ( select max(sott) from ungdung where trangthai=1 " + condition3 + " and ungdung_fk = c.pk_seq  ) as maxstt, a.sott as totalstt, " + 
				" 	( select max(sott) from ungdung where ungdung_fk = c.pk_seq and pk_seq in ( select ungdung_fk from NhanVien_UngDung  where NhanVien_fk = '"+userId+"' ) ) as max_total_stt, " + 
				" 		0 as molevel1, 0 as molevel2	      \n"+   
				"   from ungdung a  inner join ungdung c on a.ungdung_fk = c.pk_seq         \n"+   
				"   where a.level = '3' and c.level = '1'     and a.loaiMenu='"+loaiMenu+"'  and a.TrangThai=1  and c.TrangThai=1     "+  condition2 + "\n" +
				"   and a.pk_seq  in " + conditionPQ + "\n";   
				//"   order by stt1 asc, stt2 asc, sott asc  ";	
		
		//query = " select * from ( " + query + " ) MENU where MENU.stt1 in ( 7, 8 ) order by stt1 asc, stt2 asc, sott asc ";
		query = " select * from ( " + query + " ) MENU  order by stt1 asc, stt2 asc, sott asc ";
		
		ResultSet rs = db.get(query);
		System.out.println("Menu Query: \n" + query + "\n====================================================");
		return rs;
	}
	
	//chuyen tieng viet khong dau
	private static char[] SPECIAL_CHARACTERS = { ' ', '!', '"', '#', '$', '%',
		   '*', '+', ',', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^',
		   '`', '|', '~', 'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò',
		   'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê',
		   'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ', 'đ',
		   'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ',
		   'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ',
		   'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế',
		   'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị',
		   'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ', 'Ỗ', 'ỗ', 'Ộ',
		   'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ',
		   'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự', 'Ỹ', 'Ý', 'Ỳ', 'Ỵ', 'ỳ', 'ỵ', 'ý', 'ỹ'};
		 
	private static char[] REPLACEMENTS = { '-', '\0', '\0', '\0', '\0', '\0',
		   '\0', '_', '\0', '_', '\0', '\0', '\0', '\0', '\0', '\0', '_',
		   '\0', '\0', '\0', '\0', '\0', 'A', 'A', 'A', 'A', 'E', 'E', 'E',
		   'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a', 'a', 'a',
		   'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A',
		   'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a',
		   'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
		   'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e',
		   'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'I',
		   'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
		   'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
		   'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
		   'U', 'u', 'Ỹ', 'Y', 'Y', 'Y', 'y', 'y', 'y', 'y'};
		 
	public String replaceAEIOU(String s) 
	 {
		 int maxLength = Math.min(s.length(), 236);
		  char[] buffer = new char[maxLength];
		  int n = 0;
		  for (int i = 0; i < maxLength; i++) 
		  {
			  char ch = s.charAt(i);
			  int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
			  if (index >= 0) 
			  {
				  buffer[n] = REPLACEMENTS[index];
			  } 
			  else 
			  {
				  buffer[n] = ch;
			  }
			   // skip not printable characters
			   if (buffer[n] > 31) {
			    n++;
			   }
		  }
		  
		  // skip trailing slashes
		  while (n > 0 && buffer[n - 1] == '/') 
		  {
			  n--;
		  }
		  
		  String kq = String.valueOf(buffer, 0, n);
		  kq = kq.replaceAll("---", "-");
		  kq = kq.replaceAll("--", "-");
		  kq = kq.replaceAll("--", "-");
		  
		  return kq;
	 }
	
	public String replaceAEIOUN(String s) 
	 {
		 int maxLength = Math.min(s.length(), 236);
		  char[] buffer = new char[maxLength];
		  int n = 0;
		  for (int i = 0; i < maxLength; i++) 
		  {
			  char ch = s.charAt(i);
			  int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
			  if (index >= 0) 
			  {
				  buffer[n] = REPLACEMENTS[index];
			  } 
			  else 
			  {
				  buffer[n] = ch;
			  }
			   // skip not printable characters
			   if (buffer[n] > 31) {
			    n++;
			   }
		  }
		  
		  // skip trailing slashes
		  while (n > 0 && buffer[n - 1] == '/') 
		  {
			  n--;
		  }
		  
		  String kq = String.valueOf(buffer, 0, n);
		  
		  return kq;
	 }
	
	public String antiSQLInspection(String param)
	{
		String tmp = param;
		//System.out.println("Chuoi moi:" + tmp);
		
		String[] keywords = {" or ","delete","insert","update","create", "alter","drop","=","--", "select","\\(","\\)"};

		boolean trbl = false;
		
		if(tmp != null){
			tmp = tmp.toLowerCase();
			for (int i = 0; i < keywords.length; i++){
				if(tmp.contains(keywords[i])){
					tmp = tmp.replaceAll(keywords[i], "--");
					trbl = true;
					break;
				}
				////System.out.println("Chuoi moi:" + tmp);
			}
			
		}
		
//				//System.out.println("Chuoi moi:" + tmp);
		if(trbl == true)
			return tmp;
		else return param;
	}
	
	public String setTieuDe(ITieuchithuongTBList obj)
	{
		 String tieude="";
		 dbutils db = new dbutils();
		    
	    if(obj.getVungId()!=null && obj.getVungId().length()>0)
	    {
	    	tieude += this.getTieuDe( "Vung","Ten", obj.getVungId(),db ) + "_" ;
	    }
	    
	    if(obj.getKvId()!=null && obj.getKvId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getKvId(),db ) + "_" ;
	    }
	    
	    if(obj.getNppIds()!=null && obj.getNppIds().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getNppIds(), db ) + "_" ;
	    }
	    if(obj.getSchemeIds()!=null && obj.getSchemeIds().length()>0)
	    {
	    	tieude += this.getTieuDe( "CTKhuyenMai","DienGiai", obj.getSchemeIds(), db ) + "_" ;
	    }
	    if(obj.getNam()!=null && obj.getNam().length()>0)
	    {
	    	tieude += obj.getNam() +"_";
	    }
	    if(obj.getThang()!=null && obj.getThang().length()>0)
	    {
	    	tieude += obj.getThang() +"_";
	    }

	    if(obj.getTungay()!=null && obj.getTungay().length()>0)
	    {
	    	tieude += obj.getTungay() +"_";
	    }
	    if(obj.getDenngay()!=null && obj.getDenngay().length()>0)
	    {
	    	tieude += obj.getDenngay() +"_";
	    }
	    db.shutDown();
	   return tieude;
	}

	public String setTieuDe(Ireport obj)
	{
		String tieude="";
	    dbutils db = new dbutils();
	   
	    
	    if(obj.getkenhId()!=null && obj.getkenhId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "KenhBanHang","Ten", obj.getkenhId(), db )+ "_"; 
	    }
	    if(obj.getdvkdId()!=null && obj.getdvkdId().length()>0)
	    {
	    	tieude += this.getTieuDe( "DonViKinhDoanh","DienGiai", obj.getdvkdId(),db ) + "_" ;
	    }
	    
	   

	    if(obj.getkhachhangId()!=null && obj.getkhachhangId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhachHang","Ten", obj.getkhachhangId(),db ) + "_" ;
	    }
	   
	    if(obj.getnhanhangId()!=null && obj.getnhanhangId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "NhanHang","Ten", obj.getnhanhangId(),db ) + "_" ;
	    }
	    if(obj.getchungloaiId()!=null && obj.getchungloaiId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "ChungLoai","Ten", obj.getchungloaiId(),db ) + "_" ;
	    }
	    if(obj.getVungId()!=null && obj.getVungId().length()>0)
	    {
	    	tieude += this.getTieuDe( "Vung","Ten", obj.getVungId(),db ) + "_" ;
	    }
	    
	    if(obj.getKvId()!=null && obj.getKvId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getKvId(),db ) + "_" ;
	    }
	    
	    if(obj.getnppId()!=null && obj.getnppId().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getnppId(), db ) + "_" ;
	    }
	    
    	 if(obj.gettungay()!=null && obj.gettungay().length()>0)
	    {
	    	tieude += obj.gettungay() + "_";
	    }
    	 if(obj.getdenngay()!=null && obj.getdenngay().length()>0)
	    {
	    	tieude += obj.getdenngay() + "_";
	    }
    	
	 

	    db.shutDown();
	   return tieude;
	}

	public String setTieuDe(IRouteSumaryReport obj)
	{
		String tieude="";
	    dbutils db = new dbutils();
	    if(obj.getKhuVuc()!=null && obj.getKhuVuc().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getKhuVuc(), db ) + "_" ;
	    }
	    
	    if(obj.getnppId()!=null && obj.getnppId().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getnppId(), db ) + "_" ;
	    }
	 

	    db.shutDown();
	   return tieude;
	}
	
	public String quyen_donvithuchien(String userId) 
	{
		String sql ="( select donvithuchien_fk from nhanvien_donvithuchien where nhanvien_fk = '" + userId + "')";
		return sql;
	}
	
	public String quyen_nhacungcap(String userId) 
	{
		String sql = "( select NCC.PK_SEQ "+ 
				" from NHANVIEN_NHACUNGCAP NVLNCC "+
				" inner join ERP_NHACUNGCAP NCC on NVLNCC.ncc_fk = NCC.pk_seq  "+
				" where NVLNCC.nhanvien_fk = '" + userId + "' )";
		return sql;
	}
	
	public String quyen_xemdon_mua_ban(String userId) 
	{
		String sql ="( select NHANVIEN_FK from ERP_CHUCDANH_NHANVIEN where CHUCDANH_FK=(select pk_seq from ERP_CHUCDANH a where a.NHANVIEN_FK='" + userId + "') )";
		return sql;
	}
	
	String checkThangKhoaSoHopLe(Idbutils db, String thang, String nam)
	{
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		String query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
		String thangKS = "1";
		String namKS = "2013";
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					thangKS = rsCheck.getString("THANGKS");
					namKS = rsCheck.getString("NAM");
				}
				rsCheck.close();
			} 
			catch (Exception e) {}
		}
		
		String thangHopLe = "";
		String namHopLe = "";
		if(Integer.parseInt(thangKS) == 12 )
		{
			thangHopLe =  "1";
			namHopLe = Integer.toString( Integer.parseInt(namKS)  + 1);
		}
		else
		{
			thangHopLe =  Integer.toString(Integer.parseInt(thangKS) + 1);
			namHopLe = namKS;
		}
		
		System.out.println("namHopLe :"+namHopLe);
		System.out.println("nam :"+nam);
		System.out.println("thangHopLe :"+thangHopLe);
		System.out.println("thang :"+thang);
//		if(( Integer.parseInt(namHopLe) > Integer.parseInt(nam) ) || ( Integer.parseInt(thangHopLe) >= Integer.parseInt(thang) ) && ( Integer.parseInt(namHopLe) == Integer.parseInt(nam) ) )
//		{
//			//TAM THOI CHUA CHECK
//			return "Bạn chỉ có thể đóng nghiệp vụ sau tháng khóa sổ gần nhất ( " + thangKS + "-" + namKS + " ) 1 tháng";
//		}
		
		return "";
	}
	
	public String Update_TaiKhoan_DienGiai(dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String Diengiai, String Machungtu)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		//GHI CO
		if(Float.parseFloat(_CO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}

	public String Update_TaiKhoan_DienGiai_SP_TheoLo(geso.traphaco.center.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String Diengiai, String Machungtu, String masp, String tensp, String dvt,String solo,String Ngayhethan,String kbh_fk,String Khoxuat_fk,String Khonhan_fk)
	{
		
		if(kbh_fk==null){
			kbh_fk="NULL";
		}
		if(Khoxuat_fk==null){
			Khoxuat_fk="NULL";
		}
		if(Khonhan_fk==null){
			Khonhan_fk="NULL";
		}
		
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		//GHI CO
		if(Float.parseFloat(_CO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin!";
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU, MAHANG, TENHANG, DONVI,SOLO,NGAYHETHAN,KBH_FK,KHO_FK,KHONHAN_FK) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ",  " +
											" " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"', N'"+masp+"', N'"+tensp+"', N'"+dvt+"',N'"+solo+"','"+Ngayhethan+"' , "+kbh_fk+","+Khoxuat_fk+","+Khonhan_fk+" ) ";
			
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin!";
				return msg;
			}
			
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU, MAHANG, TENHANG, DONVI,solo,ngayhethan,kbh_fk,kho_fk,khonhan_fk) " +
					" values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " +
							" " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"', N'"+masp+"', N'"+tensp+"', N'"+dvt+"',N'"+solo+"','"+Ngayhethan+"' , "+kbh_fk+","+Khoxuat_fk+","+Khonhan_fk+" ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_DienGiai_SP_TheoLo(geso.traphaco.distributor.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String Diengiai, String Machungtu, String masp, String tensp, String dvt,String solo,String Ngayhethan,String kbh_fk,String Khoxuat_fk,String Khonhan_fk )
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		//GHI CO
		if(Float.parseFloat(_CO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT,  " +
													 " KHOANMUC, DIENGIAI, MACHUNGTU, MAHANG, TENHANG, DONVI,SOLO,NGAYHETHAN,kbh_fk,kho_fk,khonhan_Fk) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", " +
											" '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"', N'"+masp+"', N'"+tensp+"' " +
													" , N'"+dvt+"',N'"+solo+"','"+Ngayhethan+"', "+kbh_fk+","+Khoxuat_fk+","+Khonhan_fk+"  ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU, " +
					" MAHANG, TENHANG, DONVI,solo,ngayhethan ,kbh_fk,kho_fk,khonhan_Fk) " +
					" values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " +
							" " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"', N'"+masp+"', N'"+tensp+"', N'"+dvt+"',N'"+solo+"','"+Ngayhethan+"' , "+kbh_fk+","+Khoxuat_fk+","+Khonhan_fk+" ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_DienGiai_SP(geso.traphaco.distributor.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String Diengiai, String Machungtu, String masp, String tensp, String dvt)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		//GHI CO
		if(Float.parseFloat(_CO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU, MAHANG, TENHANG, DONVI) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"', N'"+masp+"', N'"+tensp+"', N'"+dvt+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU, MAHANG, TENHANG, DONVI) " +
					" values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"', N'"+masp+"', N'"+tensp+"', N'"+dvt+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_DienGiai_SP(geso.traphaco.center.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String Diengiai, String Machungtu, String masp, String tensp, String dvt)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		//GHI CO
		if(Float.parseFloat(_CO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU, MAHANG, TENHANG, DONVI) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"', N'"+masp+"', N'"+tensp+"', N'"+dvt+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU, MAHANG, TENHANG, DONVI) " +
					" values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"', N'"+masp+"', N'"+tensp+"', N'"+dvt+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "Không thể cập nhật tài khoản kế toán. Vui lòng liên hệ admin! ";
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_DienGiai(geso.traphaco.distributor.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String Diengiai, String Machungtu)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}

		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		//GHI CO
		if(Float.parseFloat(_CO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
	}
	
	public String Update_TaiKhoan(Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc,String dienGiai)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _dienGiai = "null";
		_dienGiai = dienGiai;
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,DIENGIAI) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "',N'"+_dienGiai+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
		}
		
		return msg;
	}
	
	public String Update_TaiKhoan_SP(geso.traphaco.distributor.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String masp, String tensp, String donvi)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
	
		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, mahang, tenhang, donvi) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+masp+"', N'"+tensp+"', N'"+donvi+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, mahang, tenhang, donvi) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+masp+"', N'"+tensp+"', N'"+donvi+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_SP(Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String masp, String tensp, String donvi)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, mahang, tenhang, donvi) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+masp+"', N'"+tensp+"', N'"+donvi+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, mahang, tenhang, donvi) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+masp+"', N'"+tensp+"', N'"+donvi+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_Vat_DienGiai_SP_KBH (dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, String masp, String tensp, String donvi, String kbh_fk)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		String _MASP = "";
		if(masp.trim().length()>0)
			_MASP = masp;
		
		String _TENSP = "";
		if(tensp.trim().length()>0)
			_TENSP = tensp;
		
		String _DONVI = "";
		if(donvi.trim().length()>0)
			_DONVI = donvi;
		
		String _KBH = "";
		if(kbh_fk.trim().length()>0)
			_KBH = kbh_fk;
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP, MAHANG, TENHANG, DONVI, KBH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP , MAHANG, TENHANG, DONVI, KBH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_FULL (dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, String masp, String tensp, String donvi, String kbh_fk, String kho_fk, String Solo, String Ngayhethan, String tienhang )
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _TIENHANG = "0";
		if(tienhang.trim().length() > 0)
			_TIENHANG = tienhang;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		String _MASP = "null";
		if(masp.trim().length()>0)
			_MASP = masp;
		
		String _TENSP = "null";
		if(tensp.trim().length()>0)
			_TENSP = tensp;
		
		String _DONVI = "null";
		if(donvi.trim().length()>0)
			_DONVI = donvi;
		
		String _KBH = "null";
		if(kbh_fk.trim().length()>0)
			_KBH = kbh_fk;
		
		String _KHO = "null";
		if(kho_fk.trim().length()>0)
			_KHO = kho_fk;
		
		String _SOLO = "null";
		if(Solo.trim().length()>0)
			_SOLO = Solo;
		
		String _NGAYHETHAN = "null";
		if(Ngayhethan.trim().length()>0)
			_NGAYHETHAN = Ngayhethan;
		
		//String kho_fk, String Solo, String Ngayhethan
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG, MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP, MAHANG, TENHANG, DONVI, KBH_FK, SOLO, NGAYHETHAN, KHO_FK , TIENHANG ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+", '"+_SOLO+"', '"+_NGAYHETHAN+"', "+_KHO+", "+_TIENHANG+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP , MAHANG, TENHANG, DONVI, KBH_FK , SOLO, NGAYHETHAN, KHO_FK , TIENHANG ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+", '"+_SOLO+"', '"+_NGAYHETHAN+"', "+_KHO+", "+_TIENHANG+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_FULL (geso.traphaco.distributor.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, String masp, String tensp, String donvi, String kbh_fk, String kho_fk, String Solo, String Ngayhethan, String tienhang )
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}

		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _TIENHANG = "0";
		if(tienhang.trim().length() > 0)
			_TIENHANG = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		String _MASP = "null";
		if(masp.trim().length()>0)
			_MASP = masp;
		
		String _TENSP = "null";
		if(tensp.trim().length()>0)
			_TENSP = tensp;
		
		String _DONVI = "null";
		if(donvi.trim().length()>0)
			_DONVI = donvi;
		
		String _KBH = "null";
		if(kbh_fk.trim().length()>0)
			_KBH = kbh_fk;
		
		String _KHO = "null";
		if(kho_fk.trim().length()>0)
			_KHO = kho_fk;
		
		String _SOLO = "null";
		if(Solo.trim().length()>0)
			_SOLO = Solo;
		
		String _NGAYHETHAN = "null";
		if(Ngayhethan.trim().length()>0)
			_NGAYHETHAN = Ngayhethan;
		
		//String kho_fk, String Solo, String Ngayhethan
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG, MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP, MAHANG, TENHANG, DONVI, KBH_FK, SOLO, NGAYHETHAN, KHO_FK, TIENHANG ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+", '"+_SOLO+"', '"+_NGAYHETHAN+"', "+_KHO+", "+_TIENHANG+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP , MAHANG, TENHANG, DONVI, KBH_FK , SOLO, NGAYHETHAN, KHO_FK, TIENHANG ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+", '"+_SOLO+"', '"+_NGAYHETHAN+"', "+_KHO+", "+_TIENHANG+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String createTransactionId() 
	{
		String trasactionId = "";

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		trasactionId = dateFormat.format(date).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");	

		while( trasactionId.length() <= 20 )
		{
			Random rd = new Random();
			int intRd = rd.nextInt();

			trasactionId += Integer.toString(intRd);
		}

		return trasactionId;
	}

	public List<ErpThongtinkho> DeXuatKho( Idbutils db, String ngaychungtu, String doituongId, String khott_fk, String spMa, String solo, String ngayhethan, String vitri, String mathung, String mame, String maphieu, String marq, String nsx, double tongluong )
	{
		List<ErpThongtinkho> kq = new ArrayList<ErpThongtinkho>();
		
		/*String conditionDPB = "";
		if( doituongId.trim().length() > 0 )
		{
			conditionDPB = "     select b.solo from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " +
						   " 	where a.TRANGTHAI = '1' and a.tungay <= '" + ngaychungtu + "' and '" + ngaychungtu + "' <= a.denngay and a.PK_SEQ in ( select phanbo_fk from ERP_PHANBODONHANG_DOITUONG where doituong_fk != '" + doituongId + "' ) ";
		}
		
		String conditionDUOCPB = "";
		
		if( doituongId.trim().length() > 0 )
		{
			conditionDUOCPB += "  select b.solo from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " +
	  				 		   " 	where a.TRANGTHAI = '1' and a.tungay <= '" + ngaychungtu + "' and '" + ngaychungtu + "' <= a.denngay and a.PK_SEQ in ( select phanbo_fk from ERP_PHANBODONHANG_DOITUONG where doituong_fk = '" + doituongId + "' ) ";
		}
		
		String query =  " 	select ct.KHOTT_FK, ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, isnull(MAME, '') as MAME, isnull(MATHUNG, '') as MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( bin_fk, 0 ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo  "+
						"\n 	from ERP_KHOTT_SP_CHITIET ct left join ERP_BIN bin on ct.bin_fk = bin.pk_seq  "+
						"\n 	where ct.KHOTT_FK = " + khott_fk + " and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' )   " +
						 "\n			and SOLO not in ( select solo from ERP_HANGCHOPHANBO where sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) )	" +
						 "\n 			and ( SOLO not in ( " + conditionDPB + " ) or SOLO in ( " + conditionDUOCPB + " ) )	" +
						 "\n 			and ngaynhapkho <= '" + ngaychungtu + "' and AVAILABLE > 0 and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and isnull( bin.ma, '' ) = N'" + vitri + "' " +
						 "\n order by ngaynhapkho asc  ";
		*/

		String query =  " 	select ct.KHOTT_FK, ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, isnull(MAME, '') as MAME, isnull(MATHUNG, '') as MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( bin_fk, 0 ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo, isnull( nsx_fk, 0 ) as nsx, isnull( marq, '' ) as marq "+
						"\n 	from ERP_KHOTT_SP_CHITIET ct left join ERP_BIN bin on ct.bin_fk = bin.pk_seq left join ERP_NHASANXUAT nsx on ct.nsx_fk = nsx.pk_seq  "+
						"\n 	where ct.KHOTT_FK = " + khott_fk + " and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
						"\n 			and ngaynhapkho <= '" + ngaychungtu + "' and AVAILABLE > 0 " + 
						"\n 			and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and isnull( bin.ma, '' ) = N'" + vitri + "' and mathung = '" + mathung + "' and mame = '" + mame + "'  and maphieu = N'" + maphieu + "' and isnull( nsx.ma, '' ) = N'" + nsx + "' and marq = '" + marq + "' " +
						"\n order by ngaynhapkho asc  ";
		
		System.out.println("----DE XUAT BIN - LO ( " + spMa + " ): " + query );
		ResultSet rs = db.get(query);
		
		//NodeList rs = WebService.ExecQueryFromERP(query, UtilitySyn.secrect);
		
		//NodeList rs = WebService.DeXuatLo(khott_fk, spMa, ngaychungtu, solo, ngayhethan, vitri, UtilitySyn.secrect);
		try 
		{
			double total = 0;
			double ttotalXUAT = 0;
			while(rs.next())
			{
				//Element element = (Element) rs.item(i);
				
				double slg = 0;
				double avai = rs.getDouble("AVAILABLE");
				//double avai = WebService.getDouble(element, "AVAILABLE");
				total += avai;
				
				//System.out.println("---- TOTAL: " + total + " -- TONG LUONG: " + tongluong + " -- AVAI: " + avai );
				if(total < tongluong)
				{
					slg = avai;
				}
				else
				{
					slg = tongluong - ( total - avai );
				}
					
				//System.out.println("---- TOTAL: " + total + " -- TONG LUONG: " + tongluong + " -- SOLUONG: " + slg );
				if(slg > 0)
				{
					ttotalXUAT += slg;
					
					//query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '" + slg + "' as tuDEXUAT union ALL ";
					ErpThongtinkho ttKho = new ErpThongtinkho();
					
					ttKho.khoId = rs.getString("KHOTT_FK");;
					ttKho.spId = rs.getString("sanpham_fk");
					ttKho.solo = rs.getString("solo");
					ttKho.ngayhethan = rs.getString("ngayhethan");
					ttKho.ngaynhapkho = rs.getString("ngaynhapkho");
					
					ttKho.MARQ = rs.getString("MARQ");
					ttKho.hamluong = rs.getString("hamluong");
					ttKho.hamam = rs.getString("hamam");
					
					ttKho.maphieu = rs.getString("MAPHIEU");
					ttKho.phieudt = rs.getString("phieudt");
					ttKho.phieueo = rs.getString("phieueo");
					
					ttKho.vitriId = rs.getString("vitri");
					ttKho.mame = rs.getString("mame");
					ttKho.mathung = rs.getString("mathung");
					ttKho.maphieu = rs.getString("MAPHIEU");
					ttKho.nsxId = rs.getString("nsx");
					ttKho.soluong = slg;
					
					/*ttKho.khoId = WebService.getValues(element, "KHOTT_FK");
					ttKho.spId = WebService.getValues(element, "sanpham_fk");
					ttKho.solo = WebService.getValues(element, "SOLO");
					ttKho.ngayhethan = WebService.getValues(element, "NGAYHETHAN");
					ttKho.ngaynhapkho = WebService.getValues(element, "ngaynhapkho");
					
					ttKho.MARQ = WebService.getValues(element, "MARQ");
					ttKho.hamluong = WebService.getValues(element, "HAMLUONG");
					ttKho.hamam = WebService.getValues(element, "HAMAM");
					
					ttKho.maphieu = WebService.getValues(element, "MAPHIEU");
					ttKho.phieudt = WebService.getValues(element, "phieudt");
					ttKho.phieueo = WebService.getValues(element, "phieueo");
					
					ttKho.vitriId = WebService.getValues(element, "vitri");
					ttKho.mame = WebService.getValues(element, "MAME");
					ttKho.mathung = WebService.getValues(element, "MATHUNG");
					
					ttKho.soluong = slg;*/
					
					
					kq.add(ttKho);
				}
				else
				{
					break;
				}
			}
			rs.close();
			
			if( ttotalXUAT != tongluong )
			{
				System.out.println("Lỗi đề xuất mã hàng ( " + spMa + " ) số chi tiết " + ttotalXUAT + " đang khác số tổng " + tongluong );
				kq = null;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
			kq = null;
		}
		
		return kq;
	}
	public List<ErpThongtinkho> DeXuatKho_TRAHANG_NEW( Idbutils db, String ngaychungtu, String doituongId, String khott_fk, String spMa, String solo, String ngayhethan, String vitri, String mathung, String mame,String maphieu, String hamluong,String hamam,String ngaynhapkho,String marq,String nsx_fk, double tongluong )
	{
		List<ErpThongtinkho> kq = new ArrayList<ErpThongtinkho>();
		
	

		String query =  " 	select  ISNULL(NSX_FK,0) AS NSX_FK,ct.KHOTT_FK, ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, isnull(MAME, '') as MAME, isnull(MATHUNG, '') as MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( bin_fk, 0 ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo  "+
						"\n 	from ERP_KHOTT_SP_CHITIET ct left join ERP_BIN bin on ct.bin_fk = bin.pk_seq  "+
						"\n 	where ct.KHOTT_FK = " + khott_fk + " and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
						"\n 			and ngaynhapkho <= '" + ngaychungtu + "' and AVAILABLE > 0 " + 
						"\n 			and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and isnull( bin.ma, '' ) = N'" + vitri + "' and mathung = '" + mathung + "' and mame = '" + mame + "'  and maphieu = N'" + maphieu + "' \n "
								+ "	AND ISNULL(ct.MARQ,'') = '"+marq+"' AND ISNULL(ct.NSX_FK,0) ="+nsx_fk+" and isnull(hamluong,'100') ='"+hamluong+"' and isnull(hamam,'100') ='"+hamam+"' and ngaynhapkho = '"+ngaynhapkho+"' " +
						"\n order by ngaynhapkho asc  ";
		
		System.out.println("----DE XUAT BIN - LO ( " + spMa + " ): " + query );
		ResultSet rs = db.get(query);
		
		//NodeList rs = WebService.ExecQueryFromERP(query, UtilitySyn.secrect);
		
		//NodeList rs = WebService.DeXuatLo(khott_fk, spMa, ngaychungtu, solo, ngayhethan, vitri, UtilitySyn.secrect);
		try 
		{
			double total = 0;
			double ttotalXUAT = 0;
			while(rs.next())
			{
				//Element element = (Element) rs.item(i);
				
				double slg = 0;
				double avai = rs.getDouble("AVAILABLE");
				//double avai = WebService.getDouble(element, "AVAILABLE");
				total += avai;
				
				//System.out.println("---- TOTAL: " + total + " -- TONG LUONG: " + tongluong + " -- AVAI: " + avai );
				if(total < tongluong)
				{
					slg = avai;
				}
				else
				{
					slg = tongluong - ( total - avai );
				}
					
				//System.out.println("---- TOTAL: " + total + " -- TONG LUONG: " + tongluong + " -- SOLUONG: " + slg );
				if(slg > 0)
				{
					ttotalXUAT += slg;
					
					//query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '" + slg + "' as tuDEXUAT union ALL ";
					ErpThongtinkho ttKho = new ErpThongtinkho();
					
					ttKho.khoId = rs.getString("KHOTT_FK");;
					ttKho.spId = rs.getString("sanpham_fk");
					ttKho.solo = rs.getString("solo");
					ttKho.ngayhethan = rs.getString("ngayhethan");
					ttKho.ngaynhapkho = rs.getString("ngaynhapkho");
					
					ttKho.MARQ = rs.getString("MARQ");
					ttKho.hamluong = rs.getString("hamluong");
					ttKho.hamam = rs.getString("hamam");
					ttKho.nsxId = rs.getString("NSX_FK");
					ttKho.maphieu = rs.getString("MAPHIEU");
					ttKho.phieudt = rs.getString("phieudt");
					ttKho.phieueo = rs.getString("phieueo");
					
					ttKho.vitriId = rs.getString("vitri");
					ttKho.mame = rs.getString("mame");
					ttKho.mathung = rs.getString("mathung");
					ttKho.maphieu = rs.getString("MAPHIEU");
					ttKho.soluong = slg;
					
				
					
					
					kq.add(ttKho);
				}
				else
				{
					break;
				}
			}
			rs.close();
			
			if( ttotalXUAT != tongluong )
			{
				System.out.println("Lỗi đề xuất mã hàng ( " + spMa + " ) số chi tiết " + ttotalXUAT + " đang khác số tổng " + tongluong );
				kq = null;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
			kq = null;
		}
		
		return kq;
	}
		public List<ErpThongtinkho> DeXuatKho_TRAHANG( Idbutils db, String ngaychungtu, String doituongId, String khott_fk, String spMa, String solo, String ngayhethan, String vitri, String mathung, String mame,String maphieu, String hamluong,String hamam,String ngaynhapkho, double tongluong )
		{
			List<ErpThongtinkho> kq = new ArrayList<ErpThongtinkho>();
			
			/*String conditionDPB = "";
			if( doituongId.trim().length() > 0 )
			{
				conditionDPB = "     select b.solo from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " +
							   " 	where a.TRANGTHAI = '1' and a.tungay <= '" + ngaychungtu + "' and '" + ngaychungtu + "' <= a.denngay and a.PK_SEQ in ( select phanbo_fk from ERP_PHANBODONHANG_DOITUONG where doituong_fk != '" + doituongId + "' ) ";
			}
			
			String conditionDUOCPB = "";
			
			if( doituongId.trim().length() > 0 )
			{
				conditionDUOCPB += "  select b.solo from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " +
		  				 		   " 	where a.TRANGTHAI = '1' and a.tungay <= '" + ngaychungtu + "' and '" + ngaychungtu + "' <= a.denngay and a.PK_SEQ in ( select phanbo_fk from ERP_PHANBODONHANG_DOITUONG where doituong_fk = '" + doituongId + "' ) ";
			}
			
			String query =  " 	select ct.KHOTT_FK, ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, isnull(MAME, '') as MAME, isnull(MATHUNG, '') as MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( bin_fk, 0 ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo  "+
							"\n 	from ERP_KHOTT_SP_CHITIET ct left join ERP_BIN bin on ct.bin_fk = bin.pk_seq  "+
							"\n 	where ct.KHOTT_FK = " + khott_fk + " and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' )   " +
							 "\n			and SOLO not in ( select solo from ERP_HANGCHOPHANBO where sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) )	" +
							 "\n 			and ( SOLO not in ( " + conditionDPB + " ) or SOLO in ( " + conditionDUOCPB + " ) )	" +
							 "\n 			and ngaynhapkho <= '" + ngaychungtu + "' and AVAILABLE > 0 and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and isnull( bin.ma, '' ) = N'" + vitri + "' " +
							 "\n order by ngaynhapkho asc  ";
			*/

			String query =  " 	select ct.KHOTT_FK, ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, isnull(MAME, '') as MAME, isnull(MATHUNG, '') as MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( bin_fk, 0 ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo  "+
							"\n 	from ERP_KHOTT_SP_CHITIET ct left join ERP_BIN bin on ct.bin_fk = bin.pk_seq  "+
							"\n 	where ct.KHOTT_FK = " + khott_fk + " and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
							"\n 			and ngaynhapkho <= '" + ngaychungtu + "' and AVAILABLE > 0 " + 
							"\n 			and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and isnull( bin.ma, '' ) = N'" + vitri + "' and mathung = '" + mathung + "' and mame = '" + mame + "'  and maphieu = N'" + maphieu + "' and isnull(hamluong,'100') ='"+hamluong+"' and isnull(hamam,'100') ='"+hamam+"' and ngaynhapkho = '"+ngaynhapkho+"' " +
							"\n order by ngaynhapkho asc  ";
			
			System.out.println("----DE XUAT BIN - LO ( " + spMa + " ): " + query );
			ResultSet rs = db.get(query);
			
			//NodeList rs = WebService.ExecQueryFromERP(query, UtilitySyn.secrect);
			
			//NodeList rs = WebService.DeXuatLo(khott_fk, spMa, ngaychungtu, solo, ngayhethan, vitri, UtilitySyn.secrect);
			try 
			{
				double total = 0;
				double ttotalXUAT = 0;
				while(rs.next())
				{
					//Element element = (Element) rs.item(i);
					
					double slg = 0;
					double avai = rs.getDouble("AVAILABLE");
					//double avai = WebService.getDouble(element, "AVAILABLE");
					total += avai;
					
					//System.out.println("---- TOTAL: " + total + " -- TONG LUONG: " + tongluong + " -- AVAI: " + avai );
					if(total < tongluong)
					{
						slg = avai;
					}
					else
					{
						slg = tongluong - ( total - avai );
					}
						
					//System.out.println("---- TOTAL: " + total + " -- TONG LUONG: " + tongluong + " -- SOLUONG: " + slg );
					if(slg > 0)
					{
						ttotalXUAT += slg;
						
						//query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '" + slg + "' as tuDEXUAT union ALL ";
						ErpThongtinkho ttKho = new ErpThongtinkho();
						
						ttKho.khoId = rs.getString("KHOTT_FK");;
						ttKho.spId = rs.getString("sanpham_fk");
						ttKho.solo = rs.getString("solo");
						ttKho.ngayhethan = rs.getString("ngayhethan");
						ttKho.ngaynhapkho = rs.getString("ngaynhapkho");
						
						ttKho.MARQ = rs.getString("MARQ");
						ttKho.hamluong = rs.getString("hamluong");
						ttKho.hamam = rs.getString("hamam");
						
						ttKho.maphieu = rs.getString("MAPHIEU");
						ttKho.phieudt = rs.getString("phieudt");
						ttKho.phieueo = rs.getString("phieueo");
						
						ttKho.vitriId = rs.getString("vitri");
						ttKho.mame = rs.getString("mame");
						ttKho.mathung = rs.getString("mathung");
						ttKho.maphieu = rs.getString("MAPHIEU");
						ttKho.soluong = slg;
						
					
						
						
						kq.add(ttKho);
					}
					else
					{
						break;
					}
				}
				rs.close();
				
				if( ttotalXUAT != tongluong )
				{
					System.out.println("Lỗi đề xuất mã hàng ( " + spMa + " ) số chi tiết " + ttotalXUAT + " đang khác số tổng " + tongluong );
					kq = null;
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
				kq = null;
			}
			
			return kq;
		}
		
	public String Update_TaiKhoan_Vat_DienGiai_SP_KBH (geso.traphaco.distributor.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, String masp, String tensp, String donvi, String kbh_fk)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		String _MASP = "";
		if(masp.trim().length()>0)
			_MASP = masp;
		
		String _TENSP = "";
		if(tensp.trim().length()>0)
			_TENSP = tensp;
		
		String _DONVI = "";
		if(donvi.trim().length()>0)
			_DONVI = donvi;
		
		String _KBH = "";
		if(kbh_fk.trim().length()>0)
			_KBH = kbh_fk;
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP, MAHANG, TENHANG, DONVI, KBH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP , MAHANG, TENHANG, DONVI, KBH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_Vat_DienGiai_SP(geso.traphaco.distributor.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, String masp, String tensp, String donvi)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		String _MASP = "";
		if(masp.trim().length()>0)
			_MASP = masp;
		
		String _TENSP = "";
		if(tensp.trim().length()>0)
			_TENSP = tensp;
		
		String _DONVI = "";
		if(donvi.trim().length()>0)
			_DONVI = donvi;
		
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP, MAHANG, TENHANG, DONVI) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"') ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP , MAHANG, TENHANG, DONVI) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"') ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_Vat_DienGiai(Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK != null){
			if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
				_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		}

		String _sochungtu = "null";
		if(sochungtu != null){
			if(sochungtu.trim().length() > 0)
				_sochungtu = sochungtu;
		}

		String _soluong = "null";
		
		if(SOLUONG != null){
			if(SOLUONG.trim().length() > 0)
				_soluong = SOLUONG.trim();
		}

		String _dongia = "null";
		if(DONGIA != null){
			if(DONGIA.trim().length() > 0)
				_dongia = DONGIA.trim();
		}
		
		String _thanhtienViet = "null";
		if(TONGGIATRI != null){
			if(TONGGIATRI.trim().length() > 0)
				_thanhtienViet = TONGGIATRI.trim();
		}

		String _dongiaNT = "null";
		if(DONGIANT != null){
			if(DONGIANT.trim().length() > 0)
				_dongiaNT = DONGIANT.trim();
		}
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT != null){
			if(TONGGIATRINT.trim().length() > 0)
				_thanhtienNT = TONGGIATRINT.trim();
		}

		String _NO = "0";
		
		if(NO != null){
			if(NO.trim().length() > 0)
				_NO = NO;
		}
		
		String _CO = "0";
		if(CO != null){
			if(CO.trim().length() > 0)
				_CO = CO;
		}
		
		String _VAT = "0";
		if(VAT != null){
			if(VAT.trim().length() > 0)
				_VAT = VAT;
		}
		
		String _DIENGIAI = "";
		if(DienGiai != null){
			if(DienGiai.trim().length()>0)
				_DIENGIAI = DienGiai;
		}
		
		String _MACHUNGTU = "";
		if(MaChungTu != null){
			if(MaChungTu.trim().length()>0)
				_MACHUNGTU = MaChungTu;
		}

		String _ISNPP = "null";
		if(isNPP != null){
			if(isNPP.trim().length()>0)
				_ISNPP = isNPP;
		}
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	
	public String Update_TaiKhoan_Vat_DienGiai_Quay(Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP,String quay_fk)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK != null){
			if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
				_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		}

		String _sochungtu = "null";
		if(sochungtu != null){
			if(sochungtu.trim().length() > 0)
				_sochungtu = sochungtu;
		}

		String _soluong = "null";
		
		if(SOLUONG != null){
			if(SOLUONG.trim().length() > 0)
				_soluong = SOLUONG.trim();
		}

		String _dongia = "null";
		if(DONGIA != null){
			if(DONGIA.trim().length() > 0)
				_dongia = DONGIA.trim();
		}
		
		String _thanhtienViet = "null";
		if(TONGGIATRI != null){
			if(TONGGIATRI.trim().length() > 0)
				_thanhtienViet = TONGGIATRI.trim();
		}

		String _dongiaNT = "null";
		if(DONGIANT != null){
			if(DONGIANT.trim().length() > 0)
				_dongiaNT = DONGIANT.trim();
		}
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT != null){
			if(TONGGIATRINT.trim().length() > 0)
				_thanhtienNT = TONGGIATRINT.trim();
		}

		String _NO = "0";
		
		if(NO != null){
			if(NO.trim().length() > 0)
				_NO = NO;
		}
		
		String _CO = "0";
		if(CO != null){
			if(CO.trim().length() > 0)
				_CO = CO;
		}
		
		String _VAT = "0";
		if(VAT != null){
			if(VAT.trim().length() > 0)
				_VAT = VAT;
		}
		
		String _DIENGIAI = "";
		if(DienGiai != null){
			if(DienGiai.trim().length()>0)
				_DIENGIAI = DienGiai;
		}
		
		String _MACHUNGTU = "";
		if(MaChungTu != null){
			if(MaChungTu.trim().length()>0)
				_MACHUNGTU = MaChungTu;
		}

		String _ISNPP = "null";
		if(isNPP != null){
			if(isNPP.trim().length()>0)
				_ISNPP = isNPP;
		}
		
		String _quay_fk = "null";
		if(quay_fk != null){
			if(quay_fk.trim().length()>0)
				_quay_fk = quay_fk;
		}
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP,QUAY_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+","+_quay_fk+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP ,QUAY_FK) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+","+_quay_fk+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String DMS_Update_TaiKhoan_Vat_DienGiai(dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP_No, String isNPP_Co)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from TRAPHACODMS.DBO.ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update TRAPHACODMS.DBO.ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert TRAPHACODMS.DBO.ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert TRAPHACODMS.DBO.ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+ isNPP_Co +") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from TRAPHACODMS.DBO.ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update TRAPHACODMS.DBO.ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert TRAPHACODMS.DBO.ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert TRAPHACODMS.DBO.ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+ isNPP_No +") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
		}
		
		return msg;
		
	}

	public String Update_TaiKhoan_Vat_DienGiai(Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP_No, String isNPP_Co)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK != null){
			if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
				_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		}

		String _sochungtu = "null";
		if(sochungtu != null){
			if(sochungtu.trim().length() > 0)
				_sochungtu = sochungtu;
		}

		String _soluong = "null";
		
		if(SOLUONG != null){
			if(SOLUONG.trim().length() > 0)
				_soluong = SOLUONG.trim();
		}

		String _dongia = "null";
		if(DONGIA != null){
			if(DONGIA.trim().length() > 0)
				_dongia = DONGIA.trim();
		}
		
		String _thanhtienViet = "null";
		if(TONGGIATRI != null){
			if(TONGGIATRI.trim().length() > 0)
				_thanhtienViet = TONGGIATRI.trim();
		}

		String _dongiaNT = "null";
		if(DONGIANT != null){
			if(DONGIANT.trim().length() > 0)
				_dongiaNT = DONGIANT.trim();
		}
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT != null){
			if(TONGGIATRINT.trim().length() > 0)
				_thanhtienNT = TONGGIATRINT.trim();
		}

		String _NO = "0";
		
		if(NO != null){
			if(NO.trim().length() > 0)
				_NO = NO;
		}
		
		String _CO = "0";
		if(CO != null){
			if(CO.trim().length() > 0)
				_CO = CO;
		}
		
		String _VAT = "0";
		if(VAT != null){
			if(VAT.trim().length() > 0)
				_VAT = VAT;
		}
		
		String _DIENGIAI = "";
		if(DienGiai != null){
			if(DienGiai.trim().length()>0)
				_DIENGIAI = DienGiai;
		}
		
		String _MACHUNGTU = "";
		if(MaChungTu != null){
			if(MaChungTu.trim().length()>0)
				_MACHUNGTU = MaChungTu;
		}

		String _ISNPP_No = "null";
		if(isNPP_No != null){
			if(isNPP_No.trim().length()>0)
				_ISNPP_No = isNPP_No;
		}
		
		String _ISNPP_Co = "null";
		if(isNPP_Co != null){
			if(isNPP_Co.trim().length()>0)
				_ISNPP_Co = isNPP_Co;
		}
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP_Co+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP_No+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String CheckKhoaSoKT (Idbutils db, String ngayghinhan, String nppId)
	{
		String msg = "";
		
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		
		String query = "select count(*) dem from ERP_KHOASOKETOAN where THANGKS= DATEPART(MONTH,'"+ngayghinhan+"') and NAM=DATEPART(YEAR,'"+ngayghinhan+"') and NPP_FK="+nppId;
			
		System.out.println("check_khoaso_kt:"+query);
		int count = 0 ;
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					count = rsCheck.getInt("dem");
				}
				rsCheck.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(count > 0)
			return "Bạn không được chỉnh sửa nghiệp vụ trong tháng đã khóa sổ";
				
		return msg;
	}
	
	
	public String CheckKhoaSoKT_bonpp (Idbutils db, String ngayghinhan)
	{
		String msg = "";
		
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		
		String query = "select count(*) dem from ERP_KHOASOKETOAN where THANGKS= DATEPART(MONTH,'"+ngayghinhan+"') and NAM=DATEPART(YEAR,'"+ngayghinhan+"')";
			
		System.out.println("check_khoaso_kt:"+query);
		int count = 0 ;
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					count = rsCheck.getInt("dem");
				}
				rsCheck.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(count > 0)
			return "Bạn không được chỉnh sửa nghiệp vụ trong tháng đã khóa sổ";
				
		return msg;
	}
	
	public String Update_TaiKhoan_Vat_DienGiai_CHIKHAC (Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP,
			String MAHOADON, String MAUHOADON, String KYHIEU, String SOHOADON, String NGAYHOADON, String TENNCC, String MST, String TIENHANG, String THUESUAT, String MAFAST_DT, String TEN_DT, String TEN_PB, String TEN_KBH, String TEN_VV, String TEN_DIABAN, String TEN_TINHTHANH, String TEN_BENHVIEN, String TEN_SANPHAM, String DIENGIAI_CT, String chiPhiNo, String chiPhiCo)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		
	
		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
			
		String _MAHOADON = "";
		if(MAHOADON.trim().length()>0)
			_MAHOADON = MAHOADON;
		
		String _MAUHOADON = "";
		if(MAUHOADON.trim().length()>0)
			_MAUHOADON = MAUHOADON;
		
		String _KYHIEU = "";
		if(KYHIEU.trim().length()>0)
			_KYHIEU = KYHIEU;
		
		String _SOHOADON = "";
		if(SOHOADON.trim().length()>0)
			_SOHOADON = SOHOADON;
		
		String _NGAYHOADON = "";
		if(NGAYHOADON.trim().length()>0)
			_NGAYHOADON = NGAYHOADON;
		
		String _TENNCC = "";
		if(TENNCC.trim().length()>0)
			_TENNCC = TENNCC;
		
		String _MST = "";
		if(MST.trim().length()>0)
			_MST = MST;
		
		String _TIENHANG = "0";
		if(TIENHANG.trim().length()>0)
			_TIENHANG = TIENHANG;
		
		String _THUESUAT = "0";
		if(THUESUAT.trim().length()>0)
			_THUESUAT = THUESUAT;
						
		String _MAFAST_DT = "";
		if(MAFAST_DT.trim().length()>0)
			_MAFAST_DT = MAFAST_DT;
		
		String _TEN_DT = "";
		if(TEN_DT.trim().length()>0)
			_TEN_DT = TEN_DT;
		
		String _TEN_PB = ""; 
		if(TEN_PB.trim().length()>0)
			_TEN_PB = TEN_PB;
		
		String _TEN_KBH = "";
		if(TEN_KBH.trim().length()>0)
			_TEN_KBH = TEN_KBH;
		
		String _TEN_VV = "";
		if(TEN_VV.trim().length()>0)
			_TEN_VV = TEN_VV;
		
		String _TEN_DIABAN = "";
		if(TEN_DIABAN.trim().length()>0)
			_TEN_DIABAN = TEN_DIABAN;
		
		String _TEN_TINHTHANH = "";
		if(TEN_TINHTHANH.trim().length()>0)
			_TEN_TINHTHANH = TEN_TINHTHANH;
		
		String _TEN_BENHVIEN = "";
		if(TEN_BENHVIEN.trim().length()>0)
			_TEN_BENHVIEN = TEN_BENHVIEN; 
			
		String _TEN_SANPHAM = "";
		if(TEN_SANPHAM.trim().length()>0)
			_TEN_SANPHAM = TEN_SANPHAM;
		
		String _DIENGIAI_CT = "";
		if(DIENGIAI_CT.trim().length()>0)
			_DIENGIAI_CT = DIENGIAI_CT;
		
		String _chiPhiCo = "NULL";
		if(chiPhiCo.trim().length()>0)
			_chiPhiCo = chiPhiCo;
		

		String _chiPhiNo = "NULL";
		if(chiPhiNo.trim().length()>0)
			_chiPhiNo = chiPhiNo;
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP , " +
					"MAHOADON, MAUHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT,  MAFAST_DT, TEN_DT, TEN_PB, TEN_KBH, TEN_VV, TEN_DIABAN, TEN_TINHTHANH, TEN_BENHVIEN, TEN_SANPHAM, DIENGIAI_CT, KHOANMUCCHIPHI_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", " +
					" N'"+ _MAHOADON + "', N'"+_MAUHOADON+"' , N'"+_KYHIEU+"', N'"+_SOHOADON+"', N'"+_NGAYHOADON+"', N'"+_TENNCC+"', N'"+_MST+"', "+_TIENHANG+", "+_THUESUAT+", N'"+_MAFAST_DT+"', N'"+_TEN_DT+"', N'"+_TEN_PB+"', N'"+_TEN_KBH+"', N'"+_TEN_VV+"', N'"+_TEN_DIABAN+"', N'"+_TEN_TINHTHANH+"', N'"+_TEN_BENHVIEN+"', N'"+_TEN_SANPHAM+"', N'"+_DIENGIAI_CT+"',"+_chiPhiCo+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP," +
					"MAHOADON, MAUHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, MAFAST_DT, TEN_DT, TEN_PB, TEN_KBH, TEN_VV, TEN_DIABAN, TEN_TINHTHANH, TEN_BENHVIEN, TEN_SANPHAM, DIENGIAI_CT,KHOANMUCCHIPHI_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", " +
					" N'"+ _MAHOADON + "', N'"+_MAUHOADON+"' , N'"+_KYHIEU+"', N'"+_SOHOADON+"', N'"+_NGAYHOADON+"', N'"+_TENNCC+"', N'"+_MST+"', "+_TIENHANG+", "+_THUESUAT+", N'"+_MAFAST_DT+"', N'"+_TEN_DT+"', N'"+_TEN_PB+"', N'"+_TEN_KBH+"', N'"+_TEN_VV+"', N'"+_TEN_DIABAN+"', N'"+_TEN_TINHTHANH+"', N'"+_TEN_BENHVIEN+"', N'"+_TEN_SANPHAM+"', N'"+_DIENGIAI_CT+"', "+_chiPhiNo+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_Vat_DienGiai_CHIKHAC (Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP_No, String isNPP_Co,
			String MAHOADON, String MAUHOADON, String KYHIEU, String SOHOADON, String NGAYHOADON, String TENNCC, String MST, String TIENHANG, String THUESUAT, String MAFAST_DT, String TEN_DT, String TEN_PB, String TEN_KBH, String TEN_VV, String TEN_DIABAN, String TEN_TINHTHANH, String TEN_BENHVIEN, String TEN_SANPHAM, String DIENGIAI_CT, String chiPhiNo, String chiPhiCo)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP_NO = "null";
		if(isNPP_No.trim().length()>0)
			_ISNPP_NO = isNPP_No;
		
		String _ISNPP_CO = "null";
		if(isNPP_Co.trim().length()>0)
			_ISNPP_CO = isNPP_Co;
			
		String _MAHOADON = "";
		if(MAHOADON.trim().length()>0)
			_MAHOADON = MAHOADON;
		
		String _MAUHOADON = "";
		if(MAUHOADON.trim().length()>0)
			_MAUHOADON = MAUHOADON;
		
		String _KYHIEU = "";
		if(KYHIEU.trim().length()>0)
			_KYHIEU = KYHIEU;
		
		String _SOHOADON = "";
		if(SOHOADON.trim().length()>0)
			_SOHOADON = SOHOADON;
		
		String _NGAYHOADON = "";
		if(NGAYHOADON.trim().length()>0)
			_NGAYHOADON = NGAYHOADON;
		
		String _TENNCC = "";
		if(TENNCC.trim().length()>0)
			_TENNCC = TENNCC;
		
		String _MST = "";
		if(MST.trim().length()>0)
			_MST = MST;
		
		String _TIENHANG = "0";
		if(TIENHANG.trim().length()>0)
			_TIENHANG = TIENHANG;
		
		String _THUESUAT = "0";
		if(THUESUAT.trim().length()>0)
			_THUESUAT = THUESUAT;
						
		String _MAFAST_DT = "";
		if(MAFAST_DT.trim().length()>0)
			_MAFAST_DT = MAFAST_DT;
		
		String _TEN_DT = "";
		if(TEN_DT.trim().length()>0)
			_TEN_DT = TEN_DT;
		
		String _TEN_PB = ""; 
		if(TEN_PB.trim().length()>0)
			_TEN_PB = TEN_PB;
		
		String _TEN_KBH = "";
		if(TEN_KBH.trim().length()>0)
			_TEN_KBH = TEN_KBH;
		
		String _TEN_VV = "";
		if(TEN_VV.trim().length()>0)
			_TEN_VV = TEN_VV;
		
		String _TEN_DIABAN = "";
		if(TEN_DIABAN.trim().length()>0)
			_TEN_DIABAN = TEN_DIABAN;
		
		String _TEN_TINHTHANH = "";
		if(TEN_TINHTHANH.trim().length()>0)
			_TEN_TINHTHANH = TEN_TINHTHANH;
		
		String _TEN_BENHVIEN = "";
		if(TEN_BENHVIEN.trim().length()>0)
			_TEN_BENHVIEN = TEN_BENHVIEN; 
			
		String _TEN_SANPHAM = "";
		if(TEN_SANPHAM.trim().length()>0)
			_TEN_SANPHAM = TEN_SANPHAM;
		
		String _DIENGIAI_CT = "";
		if(DIENGIAI_CT.trim().length()>0)
			_DIENGIAI_CT = DIENGIAI_CT;
		
		String _chiPhiCo = "NULL";
		if(chiPhiCo.trim().length()>0)
			_chiPhiCo = chiPhiCo;
		

		String _chiPhiNo = "NULL";
		if(chiPhiNo.trim().length()>0)
			_chiPhiNo = chiPhiNo;
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP , " +
					"MAHOADON, MAUHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT,  MAFAST_DT, TEN_DT, TEN_PB, TEN_KBH, TEN_VV, TEN_DIABAN, TEN_TINHTHANH, TEN_BENHVIEN, TEN_SANPHAM, DIENGIAI_CT, KHOANMUCCHIPHI_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP_CO+", " +
					" N'"+ _MAHOADON + "', N'"+_MAUHOADON+"' , N'"+_KYHIEU+"', N'"+_SOHOADON+"', N'"+_NGAYHOADON+"', N'"+_TENNCC+"', N'"+_MST+"', "+_TIENHANG+", "+_THUESUAT+", N'"+_MAFAST_DT+"', N'"+_TEN_DT+"', N'"+_TEN_PB+"', N'"+_TEN_KBH+"', N'"+_TEN_VV+"', N'"+_TEN_DIABAN+"', N'"+_TEN_TINHTHANH+"', N'"+_TEN_BENHVIEN+"', N'"+_TEN_SANPHAM+"', N'"+_DIENGIAI_CT+"',"+_chiPhiCo+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP," +
					"MAHOADON, MAUHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, MAFAST_DT, TEN_DT, TEN_PB, TEN_KBH, TEN_VV, TEN_DIABAN, TEN_TINHTHANH, TEN_BENHVIEN, TEN_SANPHAM, DIENGIAI_CT,KHOANMUCCHIPHI_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP_NO+", " +
					" N'"+ _MAHOADON + "', N'"+_MAUHOADON+"' , N'"+_KYHIEU+"', N'"+_SOHOADON+"', N'"+_NGAYHOADON+"', N'"+_TENNCC+"', N'"+_MST+"', "+_TIENHANG+", "+_THUESUAT+", N'"+_MAFAST_DT+"', N'"+_TEN_DT+"', N'"+_TEN_PB+"', N'"+_TEN_KBH+"', N'"+_TEN_VV+"', N'"+_TEN_DIABAN+"', N'"+_TEN_TINHTHANH+"', N'"+_TEN_BENHVIEN+"', N'"+_TEN_SANPHAM+"', N'"+_DIENGIAI_CT+"', "+_chiPhiNo+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_Vat_DienGiai_CHIKHAC (Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP,
			String MAHOADON, String MAUHOADON, String KYHIEU, String SOHOADON, String NGAYHOADON, String TENNCC, String MST, String TIENHANG, String THUESUAT, String MAFAST_DT, String TEN_DT, String TEN_PB, String TEN_KBH, String TEN_VV, String TEN_DIABAN, String TEN_TINHTHANH, String TEN_BENHVIEN, String TEN_SANPHAM, String DIENGIAI_CT)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		
		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
			
		String _MAHOADON = "";
		if(MAHOADON.trim().length()>0)
			_MAHOADON = MAHOADON;
		
		String _MAUHOADON = "";
		if(MAUHOADON.trim().length()>0)
			_MAUHOADON = MAUHOADON;
		
		String _KYHIEU = "";
		if(KYHIEU.trim().length()>0)
			_KYHIEU = KYHIEU;
		
		String _SOHOADON = "";
		if(SOHOADON.trim().length()>0)
			_SOHOADON = SOHOADON;
		
		String _NGAYHOADON = "";
		if(NGAYHOADON.trim().length()>0)
			_NGAYHOADON = NGAYHOADON;
		
		String _TENNCC = "";
		if(TENNCC.trim().length()>0)
			_TENNCC = TENNCC;
		
		String _MST = "";
		if(MST.trim().length()>0)
			_MST = MST;
		
		String _TIENHANG = "0";
		if(TIENHANG.trim().length()>0)
			_TIENHANG = TIENHANG;
		
		String _THUESUAT = "0";
		if(THUESUAT.trim().length()>0)
			_THUESUAT = THUESUAT;
						
		String _MAFAST_DT = "";
		if(MAFAST_DT.trim().length()>0)
			_MAFAST_DT = MAFAST_DT;
		
		String _TEN_DT = "";
		if(TEN_DT.trim().length()>0)
			_TEN_DT = TEN_DT;
		
		String _TEN_PB = ""; 
		if(TEN_PB.trim().length()>0)
			_TEN_PB = TEN_PB;
		
		String _TEN_KBH = "";
		if(TEN_KBH.trim().length()>0)
			_TEN_KBH = TEN_KBH;
		
		String _TEN_VV = "";
		if(TEN_VV.trim().length()>0)
			_TEN_VV = TEN_VV;
		
		String _TEN_DIABAN = "";
		if(TEN_DIABAN.trim().length()>0)
			_TEN_DIABAN = TEN_DIABAN;
		
		String _TEN_TINHTHANH = "";
		if(TEN_TINHTHANH.trim().length()>0)
			_TEN_TINHTHANH = TEN_TINHTHANH;
		
		String _TEN_BENHVIEN = "";
		if(TEN_BENHVIEN.trim().length()>0)
			_TEN_BENHVIEN = TEN_BENHVIEN; 
			
		String _TEN_SANPHAM = "";
		if(TEN_SANPHAM.trim().length()>0)
			_TEN_SANPHAM = TEN_SANPHAM;
		
		String _DIENGIAI_CT = "";
		if(DIENGIAI_CT.trim().length()>0)
			_DIENGIAI_CT = DIENGIAI_CT;
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP , " +
					"MAHOADON, MAUHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT,  MAFAST_DT, TEN_DT, TEN_PB, TEN_KBH, TEN_VV, TEN_DIABAN, TEN_TINHTHANH, TEN_BENHVIEN, TEN_SANPHAM, DIENGIAI_CT, KHOANMUCCHIPHI_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", " +
					" N'"+ _MAHOADON + "', N'"+_MAUHOADON+"' , N'"+_KYHIEU+"', N'"+_SOHOADON+"', N'"+_NGAYHOADON+"', N'"+_TENNCC+"', N'"+_MST+"', "+_TIENHANG+", "+_THUESUAT+", N'"+_MAFAST_DT+"', N'"+_TEN_DT+"', N'"+_TEN_PB+"', N'"+_TEN_KBH+"', N'"+_TEN_VV+"', N'"+_TEN_DIABAN+"', N'"+_TEN_TINHTHANH+"', N'"+_TEN_BENHVIEN+"', N'"+_TEN_SANPHAM+"', N'"+_DIENGIAI_CT+"',"+"NULL"+ ") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP," +
					"MAHOADON, MAUHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, MAFAST_DT, TEN_DT, TEN_PB, TEN_KBH, TEN_VV, TEN_DIABAN, TEN_TINHTHANH, TEN_BENHVIEN, TEN_SANPHAM, DIENGIAI_CT,KHOANMUCCHIPHI_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", " +
					" N'"+ _MAHOADON + "', N'"+_MAUHOADON+"' , N'"+_KYHIEU+"', N'"+_SOHOADON+"', N'"+_NGAYHOADON+"', N'"+_TENNCC+"', N'"+_MST+"', "+_TIENHANG+", "+_THUESUAT+", N'"+_MAFAST_DT+"', N'"+_TEN_DT+"', N'"+_TEN_PB+"', N'"+_TEN_KBH+"', N'"+_TEN_VV+"', N'"+_TEN_DIABAN+"', N'"+_TEN_TINHTHANH+"', N'"+_TEN_BENHVIEN+"', N'"+_TEN_SANPHAM+"', N'"+_DIENGIAI_CT+"', "+"NULL"+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_Vat(Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
	
		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+" ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_TheoSoHieu_DienGiai_KhoanMuc(String congTyId ,Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK, String NO, String CO, String DOITUONGNO,  
			String MADOITUONGNO, String LOAIDOITUONGNO,String DOITUONGCO,  
			String MADOITUONGCO, String LOAIDOITUONGCO, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc,String diengiai, String khoanmucchiphi_fk_no,String khoanmucchiphi_fk_co,String maChungTu)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}

		
		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
		_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
		_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
		_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
		_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
		_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
		_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
		_thanhtienNT = TONGGIATRINT.trim();
		
		String _khoanmucchiphi_fk_no = "null";
		if(khoanmucchiphi_fk_no.trim().length() > 0)
		_khoanmucchiphi_fk_no = khoanmucchiphi_fk_no.trim();
		
		
		String _khoanmucchiphi_fk_co = "null";
		if(khoanmucchiphi_fk_co.trim().length() > 0)
		_khoanmucchiphi_fk_co = khoanmucchiphi_fk_co.trim();
		
		
		String _NO = "0";
		if(NO.trim().length() > 0)
		_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
		_CO = CO;
		
		System.out.println("_co: " + _CO);
		System.out.println("_co: " + _NO);
		//GHI CO
		if(Float.parseFloat(_CO) != 0) 
		{
		query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
		"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT \n" +
		"where congTy_FK = " + congTyId + " \n" +
		"and SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'\n";
		
		ResultSet rsTKNo = db.get(query);
		int sodong = 0;
		try 
		{
		if(rsTKNo.next())
		{
		sodong = rsTKNo.getInt("sodong");
		}
		rsTKNo.close();
		} 
		catch (Exception e) { 
		e.printStackTrace();
		}
		
		if(sodong > 0) //daco
		{
		query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", \n" +
			"				 GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", \n" +
			" 				GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + "\n" +
			" where taikhoankt_fk = \n" +
			"				( select pk_seq \n" +
			"				from ERP_TAIKHOANKT \n" +
			"				where congTy_FK = " + congTyId + "\n" +
			" 				and SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ) \n" +
			"				and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'\n";
		}
		else
		{
		query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) \n" +
			"select pk_seq, " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' \n" +
			"from ERP_TAIKHOANKT \n" +
			"where congTy_FK = " + congTyId + "\n " +
			"and SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' \n";
		}
		
		System.out.println("1.Cap nhat tai khoan CO: \n" + query + "\n-----------------------------");
		if(!db.update(query))
		{
		msg = "1.Không thể cập nhật tài khoản kế toán " + query;
		return msg;
		}
		
		
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanNO_SoHieu.trim().length() > 0)
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, \n" +
										 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,DIENGIAI,KHOANMUCCHIPHI_FK,MACHUNGTU) \n" +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", '0', " + _CO + ", \n" +
						" N'" + DOITUONGCO + "', N'" + MADOITUONGCO + "', '" + LOAIDOITUONGCO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "'\n" +
						", " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+diengiai+"',"+_khoanmucchiphi_fk_co+",N'"+maChungTu+"' " +
			"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b  \n" +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' \n" +
			"and a.congTy_FK = " + congTyId + " and b.congTy_FK = " + congTyId;
		}
		else
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, \n" +
							 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,DIENGIAI,KHOANMUCCHIPHI_FK,MACHUNGTU) \n" +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", '0', " + _CO + ", \n" +
			" N'" + DOITUONGCO + "', N'" + MADOITUONGCO + "', '" + LOAIDOITUONGCO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', \n" +
			" N'"+diengiai+"',"+_khoanmucchiphi_fk_co+",N'"+maChungTu+"' \n" +
			"from ERP_TAIKHOANKT a\n" +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "'  \n" +
			"and a.congTy_FK = " + congTyId;
		}
		
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: \n" + query + "\n--------------------------------");
		if(!db.update(query))
		{
		msg = "3.Không thể cập nhật tài khoản kế toán " + query;
		return msg;
		}
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) != 0) 
		{
		query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
		"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where congTy_FK = " + congTyId + "\n " +
		"and SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		
		ResultSet rsTKCo = db.get(query);
		int sodong = 0;
		try 
		{
		if(rsTKCo.next())
		{
		sodong = rsTKCo.getInt("sodong");
		}
		rsTKCo.close();
		} 
		catch (Exception e) { 
		e.printStackTrace();
		}
		
		if(sodong > 0) //daco
		{
		query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", \n" +
									" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", \n" +
									" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + "\n" +
			" where taikhoankt_fk = \n" +
			"	( select pk_seq \n" +
			"	from ERP_TAIKHOANKT \n" +
			"	where congTy_FK = " + congTyId + "\n " +
			"	and SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' )\n" +
			" and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		}
		else
		{
		query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) \n" +
			"select pk_seq, '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' \n" +
			"from ERP_TAIKHOANKT \n" +
			"where congTy_FK = " + congTyId + "\n " +
			"and SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' \n" +
			"and congTy_FK = " + congTyId;
		}
		
		System.out.println("2.Cap nhat tai khoan NO: \n" + query);
		if(!db.update(query))
		{
		msg = "2.Không thể cập nhật tài khoản kế toán \n" + query;
		return msg;
		}
		
		
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanCO_SoHieu.trim().length() > 0)
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, \n" +
										 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,DIENGIAI,KHOANMUCCHIPHI_FK,MACHUNGTU) \n" +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", " + _NO + ", '0', \n" +
						" N'" + DOITUONGNO + "', N'" + MADOITUONGNO + "', '" + LOAIDOITUONGNO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' \n" +
						" , N'"+diengiai+"',"+_khoanmucchiphi_fk_no+",N'"+maChungTu+"' \n" +
			"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b \n" +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' \n" +
			"and a.congTy_FK = " + congTyId + " and b.congTy_FK = " + congTyId;
		}
		else
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, \n" +
							 		"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,DIENGIAI,KHOANMUCCHIPHI_FK,MACHUNGTU) \n" +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", " + _NO + ", '0', \n" +
						" N'" + DOITUONGNO + "', N'" + MADOITUONGNO + "', '" + LOAIDOITUONGNO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' \n" +
						" , N'"+diengiai+"',"+_khoanmucchiphi_fk_no+",N'"+maChungTu+"' \n" +
			"from ERP_TAIKHOANKT a \n" +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' \n" +
			"and a.congTy_FK = " + congTyId;
		}
		
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: \n" + query );
		if(!db.update(query))
		{
		msg = "3.Không thể cập nhật tài khoản kế toán \n" + query;
		return msg;
		}
		}
		
		return msg;
		}

	public String Update_TaiKhoan_TheoSoHieu(Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK, String NO, String CO, String DOITUONG,  
								String MADOITUONG, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc,String nppId)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
	
		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
		
		
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
	
		System.out.println("_co: " + _CO);
		System.out.println("_co: " + _NO);
		//GHI CO
		if(Float.parseFloat(_CO) != 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
					"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT \n" +
					"where NPP_FK = " + nppId + " \n" +
					"and SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'\n";
			
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", \n" +
						"				 GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", \n" +
						" 				GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + "\n" +
						" where taikhoankt_fk = \n" +
						"				( select pk_seq \n" +
						"				from ERP_TAIKHOANKT \n" +
						"				where NPP_FK = " + nppId + "\n" +
						" 				and SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ) \n" +
						"				and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'\n";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) \n" +
						"select pk_seq, " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' \n" +
						"from ERP_TAIKHOANKT \n" +
						"where NPP_FK = " + nppId + "\n " +
						"and SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' \n";
			}
			
			System.out.println("1.Cap nhat tai khoan CO: \n" + query + "\n-----------------------------");
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
			if(taikhoanNO_SoHieu.trim().length() > 0)
			{
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, \n" +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) \n" +
						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", '0', " + _CO + ", \n" +
									" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "'\n" +
									", " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "'" +
						"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b  \n" +
						"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' \n" +
						"and a.NPP_FK = " + nppId + " and b.NPP_FK = " + nppId;
			}
			else
			{
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, \n" +
										 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) \n" +
						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", '0', " + _CO + ", \n" +
						" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "'\n" +
						"from ERP_TAIKHOANKT a\n" +
						"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "'  \n" +
						"and a.NPP_FK = " + nppId;
			}
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: \n" + query + "\n--------------------------------");
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) != 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
					"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where NPP_FK = " +nppId + "\n " +
					"and SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", \n" +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", \n" +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + "\n" +
						" where taikhoankt_fk = \n" +
						"	( select pk_seq \n" +
						"	from ERP_TAIKHOANKT \n" +
						"	where NPP_FK = " + nppId + "\n " +
						"	and SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' )\n" +
						" and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) \n" +
						"select pk_seq, '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' \n" +
						"from ERP_TAIKHOANKT \n" +
						"where NPP_FK = " + nppId + "\n " +
						"and SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' \n" +
						"and NPP_FK = " + nppId;
			}
			
			System.out.println("2.Cap nhat tai khoan NO: \n" + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán \n" + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
			if(taikhoanCO_SoHieu.trim().length() > 0)
			{
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, \n" +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) \n" +
						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", " + _NO + ", '0', \n" +
									" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' \n" +
						"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b \n" +
						"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' \n" +
						"and a.NPP_FK = " + nppId + " and b.NPP_FK = " + nppId;
			}
			else
			{
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, \n" +
										 		"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) \n" +
						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", " + _NO + ", '0', \n" +
									" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' \n" +
						"from ERP_TAIKHOANKT a \n" +
						"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' \n" +
						"and a.NPP_FK = " + nppId;
			}
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: \n" + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán \n" + query;
				return msg;
			}
		}
		
		return msg;
	}
	
	public String quyen_khott(String userId) 
	{
		String sql ="( select khott_fk from NHANVIEN_KHOTT where nhanvien_fk ='"+ userId +"' union select kho_fk from NHANVIEN_KHO where nhanvien_fk ='"+ userId +"' )";
		return sql;
	}
	
	public String getTime() 
	{
		Date date = new Date();
	    SimpleDateFormat simpDate;
	    //format 24h
	    simpDate = new SimpleDateFormat("kk:mm:ss");
	    return simpDate.format(date);
	}
	
	public String checkNgayHopLe(Idbutils db, String ngaychotNV)
	{
		try{
		if(ngaychotNV.equals("")){
			return "Vui lòng chọn ngày chốt";
		}
		 
		int thangtruoc=Integer.parseInt(ngaychotNV.substring(5, 7));
		int namtruoc=Integer.parseInt(ngaychotNV.substring(0, 4));
		
		 
		String sql=" select TOP 1 THANGKS, NAM from ERP_KHOASOTHANG order by NAM desc, THANGKS desc ";
		 
		ResultSet rscheckngay=db.get(sql);
		if(rscheckngay.next()){
			 if(thangtruoc <=  rscheckngay.getInt("THANGKS")  &&  namtruoc <= rscheckngay.getInt("NAM")){
				 return " Vui lòng chỉ được chọn ngày ghi nhận sau tháng khóa sổ gần nhất:Tháng :"+rscheckngay.getString("THANGKS")+",năm :  "+rscheckngay.getString("Nam");
			 }
		}else{ 
				return " Vui lòng kiểm tra khóa sổ tháng,chưa có khóa sổ tháng";
		}

		}catch(Exception er){
			er.printStackTrace();
			return "Vui lòng thông báo với admin để xử lý: Lỗi: "+er.getMessage();
		}
		return "";
	 
	
	}
	
	public String getCat(String querystring, int vitriva, int vitribang)
	{
		String id;
	    String tmp;
		if (querystring != null){
	    	if (querystring.contains("&")){
	    		tmp = querystring.split("&")[vitriva];
	    		id = tmp.split("=")[vitribang];
	    	}else{
	    		id = "";
	    	}
		}else{
			id = "";
		}
	    return id;
	}

	public int getPhongBan(String nhanvien){
		
		String query="select DONVITHUCHIEN_FK  from NHANVIEN_DONVITHUCHIEN WHERE NHANVIEN_FK='"+nhanvien+"'";  
		dbutils db = new  dbutils();
		
		System.out.println(query);
		int pb=0;
		
		ResultSet rscheck= db.get(query);
		if(rscheck!=null){

			try 
			{
				while(rscheck.next())
				{
					int i =rscheck.getInt("DONVITHUCHIEN_FK");
					if(i==100000){
						pb = i;
					}
				}
				rscheck.close();
			} 
			catch 
			(Exception e) 
			{
				e.printStackTrace();
				System.out.println(e.toString());
			}
		
		}
		
		System.out.println("Phong ban:"+pb);
		return pb;
		
	}
	
	public String getSoChungTu(String querystring){
		String sochungtu;
		String tmp;
		if(querystring != null){
			if(querystring.contains("&")){
				tmp = querystring.split("&")[3];
				sochungtu = tmp.split("=")[1];
			}else{
				sochungtu = "";
			}
		}else{
			sochungtu = "";
		}
		return sochungtu;
	}

	public String getLoai(String querystring){
		String loai;
		String tmp;
		if(querystring != null){
			if(querystring.contains("&")){
				tmp = querystring.split("&")[2];
				loai = tmp.split("=")[1];
			}else{
				loai = "";
			}
		}else{
			loai = "";
		}
		return loai;
	}
	
	public String CheckNgayGhiNhanHopLe(geso.traphaco.center.db.sql.dbutils db, String ngaychotNV, String chuoispid,String Chuoikhoid) 
	{
		/*try{
			if(ngaychotNV.equals("")){
				return "Vui lòng chọn ngày chốt";
				
			}
			
			if(chuoispid.equals("")){
				return "Không xác định được sản phẩm chốt";
			}
			
			
			int thangtruoc=Integer.parseInt(ngaychotNV.substring(5, 7));
			int namtruoc=Integer.parseInt(ngaychotNV.substring(0, 4));
			
			if(thangtruoc==1){
				namtruoc=namtruoc-1;
				thangtruoc=12;
				
			}else{
				thangtruoc=thangtruoc-1;
			}

			String sql=" select TOP 1 THANGKS, NAM from ERP_KHOASOTHANG order by NAM desc, THANGKS desc";
			
			 
			ResultSet rscheckngay=db.get(sql);
			if(rscheckngay.next()){
				 if(rscheckngay.getInt("THANGKS") != thangtruoc || rscheckngay.getInt("NAM")!=namtruoc){
					 return " Vui lòng chỉ được chọn ngày ghi nhận sau tháng khóa sổ gần nhất:Tháng :"+rscheckngay.getString("THANGKS")+",năm :  "+rscheckngay.getString("Nam");
				 }
			}else{ 
					return " Vui lòng kiểm tra khóa sổ tháng,chưa có khóa sổ tháng";
			}

			String mangspid[] =chuoispid.split(";");
			String mangkhoid[] =Chuoikhoid.split(";");
			String chuoi="";
			for (int i=0;i<mangspid.length;i++){
				if(i==0){
					chuoi=	" select ngaycapnhat from  ERP_KHOTT_SANPHAM where sanpham_fk='"+mangspid[i]+"' and khott_fk='"+mangkhoid[i]+"' ";
				}else{
					chuoi=chuoi + " union all select ngaycapnhat from  ERP_KHOTT_SANPHAM where sanpham_fk='"+mangspid[i]+"' and khott_fk='"+mangkhoid[i]+"' ";
				}
				
			}
			sql=" select max(ngaycapnhat)  as ngaycapnhat ,CASE  WHEN max(ngaycapnhat) <= '"+ngaychotNV+"' THEN 1 ELSE 0 END AS GIATRI  from ( "+chuoi+") as NGAY  ";
			System.out.println("Check Khoa So : "+sql);
			ResultSet rs=db.get(sql);
			if(rs==null){
				return "Không xác định được ngày ghi nhận cuối cùng của sản phẩm .\n Lỗi dòng lệnh: "+sql;
			}

			if(rs.next()){
				String ngaycapnhat=rs.getString("ngaycapnhat");
				// trường hợp chưa đưa vào kho thì cũng không xet,chi can xet so voi thang ks
				if(ngaycapnhat!=null){
					if(rs.getString("GIATRI").equals("0")){
						rs.close();
						return "Bạn không thể cập nhật ngày ghi nhận nhỏ hơn ngày ghi nhận cuối cùng : "+ngaycapnhat+" ";
					}
				}
			}else{
				return "Không xác định được ngày ghi nhận cuối cùng của sản phẩm";
			}
			rs.close();
			return "";
		}
		catch(Exception err){
			err.printStackTrace();
			return err.getMessage();
		}*/
		
		return "";
	 
	}
	
	public String Update_TaiKhoan(Idbutils db,
			String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc) {
		
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		
	
		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
 
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		  
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
		 
		
		return msg;
		
	}
	
	public String getLoaidonhang(String loaiSELECTED)
	{
		String ldh = "<option value='' ></option>";
		
		if(loaiSELECTED.equals("0"))
			ldh += " <option value='0' selected>Đơn hàng NPP</option> ";
		else
			ldh += " <option value='0' >Đơn hàng NPP</option> ";
		
		if(loaiSELECTED.equals("1"))
			ldh += " <option value='1' selected>Đơn hàng thầu</option> ";
		else
			ldh += " <option value='1' >Đơn hàng thầu</option> ";
		
		if(loaiSELECTED.equals("2"))
			ldh += " <option value='2' selected>Đơn hàng không thầu</option> ";
		else
			ldh += " <option value='2' >Đơn hàng không thầu</option> ";
		
		if(loaiSELECTED.equals("3"))
			ldh += " <option value='3' selected>Đơn hàng nội bộ</option> ";
		else
			ldh += " <option value='3' >Đơn hàng nội bộ</option> ";
			
		return ldh;
	}
	
	public int CheckKhoaSoKinhDoanh( geso.traphaco.distributor.db.sql.dbutils db, String nppId, String ngaychungtu, String tableNAME, String columnNAME, String id )
	{
		// = -1 tháng này chưa khóa sổ kinh doanh
		// = 0  ngày chứng từ trong tháng khóa sổ kinh doanh và trước ngày khóa sổ kinh doanh
		// = 1  ngày chứng từ trong tháng khóa sổ kinh doanh và sau ngày khóa sổ kinh doanh
		
		int chenhLECH = -1;
		
		String query =  "";
		
		//CHECK TỚI PHÚT LUÔN
		query =  "select DATEDIFF(mi, thoidiem, '" + ngaychungtu + "') as chenhLECH  "+
				 "from "+
				 "( "+
				 "	select thoidiem, cast(NAM as varchar(10)) + '-' + case when THANGKS < 10 then '0' + cast(THANGKS as varchar(10)) else cast(THANGKS as varchar(10)) end "+
				 "									+ '-' + case when NGAYKS < 10 then '0' + cast(NGAYKS as varchar(10)) else cast(NGAYKS as varchar(10)) end as ngayks "+
				 "	from ERP_KHOASOKINHDOANH  "+
				 "	where npp_fk = '" + nppId + "' and NAM = SUBSTRING('" + ngaychungtu + "', 0, 5 ) and THANGKS = cast( SUBSTRING('" + ngaychungtu + "', 6, 2 ) as int ) "+
				 ") "+
				 "KS ";
		
		/*if(tableNAME.trim().length() <= 0)
		{
			query =  "select DATEDIFF(dd, ngayks, '" + ngaychungtu + "') as chenhLECH  "+
					 "from "+
					 "( "+
					 "	select cast(NAM as varchar(10)) + '-' + case when THANGKS < 10 then '0' + cast(THANGKS as varchar(10)) else cast(THANGKS as varchar(10)) end "+
					 "									+ '-' + case when NGAYKS < 10 then '0' + cast(NGAYKS as varchar(10)) else cast(NGAYKS as varchar(10)) end as ngayks "+
					 "	from ERP_KHOASOKINHDOANH  "+
					 "	where npp_fk = '" + nppId + "' and NAM = SUBSTRING('" + ngaychungtu + "', 0, 5 ) and THANGKS = cast( SUBSTRING('" + ngaychungtu + "', 6, 2 ) as int ) "+
					 ") "+
					 "KS ";
		}
		else
		{
			query =  "select DATEDIFF(dd, ngayks, ( select " + columnNAME + " from " + tableNAME + " where pk_seq = " + id + "  ) ) as chenhLECH  "+
					 "from "+
					 "( "+
					 "	select cast(NAM as varchar(10)) + '-' + case when THANGKS < 10 then '0' + cast(THANGKS as varchar(10)) else cast(THANGKS as varchar(10)) end "+
					 "									+ '-' + case when NGAYKS < 10 then '0' + cast(NGAYKS as varchar(10)) else cast(NGAYKS as varchar(10)) end as ngayks "+
					 "	from ERP_KHOASOKINHDOANH  "+
					 "	where npp_fk = '" + nppId + "' and NAM = SUBSTRING( ( select " + columnNAME + " from " + tableNAME + " where pk_seq = " + id + "  ) , 0, 5 ) and THANGKS = cast( SUBSTRING( ( select " + columnNAME + " from " + tableNAME + " where pk_seq = " + id + "  ) , 6, 2 ) as int ) "+
					 ") "+
					 "KS ";
		}*/
		
		System.out.println("::: CHECK KSKD: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					if(rs.getInt("chenhLECH") > 0)
						chenhLECH = 1;
					else
						chenhLECH = 0;
				}
				rs.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return chenhLECH;
	}
	
	public String CapNhat_ThanhTien_HoaDon(geso.traphaco.distributor.db.sql.dbutils db, String hdId ) 
	{
		/*String query = "  update ERP_HOADONNPP_SP set THANHTIEN = round( SOLUONG * DONGIA, 0), TIENBVAT = round( SOLUONG * DONGIA, 0 ), CHIETKHAU = ISNULL( CHIETKHAU, 0 ), " + 
					   "  	TIENVAT = SOLUONG * round( DONGIA * VAT / 100.0, 0), TIENAVAT = round( ( SOLUONG * DONGIA ), 0 ) + round( ( SOLUONG * DONGIA * VAT / 100.0 ), 0 ) " +
					   "  where hoadon_fk = '" + hdId + "'";
		if( !db.update(query) )
			return "Lỗi khi cập nhật tiền hóa đơn: " + query;
		
		query = "update hd set hd.tongtienbvat = hd_dh.tongtienBVAT,    "+
				 "			  hd.chietkhau = hd_dh.chietkhau,   "+
				 "			  hd.vat = hd_dh.tienVAT,   "+
				 "			  hd.tongtienavat = hd_dh.tongtienBVAT - hd_dh.chietkhau + hd_dh.tienVAT   "+
				 "from ERP_HOADONNPP hd inner join   "+
				 "(   "+
				 "	select HOADON_FK, SUM( isnull(CHIETKHAU, 0) ) as chietkhau, SUM( TIENBVAT ) as tongtienBVAT,    "+
				 "					  SUM( round( ( TIENBVAT - isnull(CHIETKHAU, 0) ) * VAT / 100.0, 0 ) ) as tienVAT    "+
				 "	from ERP_HOADONNPP_SP where dbo.Trim( SCHEME ) = '' and HOADON_FK = '" + hdId + "'	" +
				 "	group by HOADON_FK   "+
				 ")   "+
				 "hd_dh on hd.PK_SEQ = hd_dh.HOADON_FK  " +
				 "where hd.pk_seq = '" + hdId + "' ";
		
		System.out.println(":::: CAP NHAT TIEN: " + query );
		if( !db.update(query) )
			return "Lỗi khi cập nhật tiền hóa đơn: " + query;*/
		
		//Sử dụng trong tooltip
		return "";
	}
	
	public String CapNhat_MaChungTu(geso.traphaco.distributor.db.sql.dbutils db, String id, String tableNAME, String columnNAME ) 
	{
		String prefix = "";
		if( tableNAME.equals("ERP_DONDATHANGNPP") )
			prefix = "DH";
		else if( tableNAME.equals("ERP_SOXUATHANGNPP") )
			prefix = "SXH";
		else if( tableNAME.equals("ERP_YCXUATKHONPP") )
			prefix = "PGH";
		else if( tableNAME.equals("ERP_XACNHANNHANHANG") )
			prefix = "BBNH";
		
		String query = "" ;
		if( !tableNAME.equals("ERP_XACNHANNHANHANG") )
		{
			query = "update " + tableNAME + " set machungtu = '" + prefix + "' + SUBSTRING(" + columnNAME + ", 6, 2) + SUBSTRING(" + columnNAME + ", 0, 5) + '-' + CAST( PK_SEQ as varchar(10) ) " + 
					" where pk_seq = '" + id + "' ";
		}
		else
		{
			query = "update " + tableNAME + " set machungtu = '" + prefix + "' + SUBSTRING(" + columnNAME + ", 6, 2) + SUBSTRING(" + columnNAME + ", 0, 5) + '-' + dbo.LaySoChungTu( " + id + " ) " + 
					" where pk_seq = '" + id + "' ";
		}
		
		if( !db.update(query) )
			return "Lỗi khi cập nhật mã chứng từ: " + query;
		
		return "";
	}
	
	public String getSelectBox( ResultSet rs, String style, String selectNAME, String selectEVENT, String selectedValue, 
				String valueCOLUMN, String displayCOLUMN, String showALL, boolean isMultiple, boolean closeRs )
	{
		String str = "";
		
		if( style.trim().length() <= 0 )
			style = "width:200px;";
		
		if( isMultiple )
			str = "<SELECT class='select2' name='" + selectNAME + "' id='" + selectNAME + "' onchange = '" + selectEVENT + "' style='" + style + "' multiple='multiple' > ";
		else
			str = "<SELECT class='select2' name='" + selectNAME + "' id='" + selectNAME + "' onchange = '" + selectEVENT + "' style='" + style + "' > ";
		
		if( showALL.equals("1") )
			str += "<option value='' >ALL</option> ";
		else if( showALL.equals("0") )
			str += "<option value='' ></option> ";
		
		try 
		{
			if( rs != null )
			{
				while( rs.next() )
				{
					if( this.checkId(rs.getString(valueCOLUMN), selectedValue, ",") )
					//if( selectedValue.contains( rs.getString(valueCOLUMN) ) )
						str += "<option value='" + rs.getString(valueCOLUMN) + "' selected >" + rs.getString(displayCOLUMN) + "</option> ";
					else
						str += "<option value='" + rs.getString(valueCOLUMN) + "' >" + rs.getString(displayCOLUMN) + "</option> ";
				}
			}
		} 
		catch (Exception e) { 
			e.printStackTrace();
		}
		
		str += "</SELECT>";
		
		if( closeRs )
		{
			if( rs != null )
			{
				try 
				{
					rs.close();
					rs = null;
				} 
				catch (Exception e) { 
					e.printStackTrace();
				}
			}
		}
		
		return str;
	}
	
	public String getPhanQuyen_TheoNhanVien(String tableName, String tableAlias, String columnName, String loainhanvien, String doituongId)
	{
		if( loainhanvien == null || doituongId == null )
			return "";
		
		tableName = tableName.toUpperCase();
		String condition = "";
		if( tableName.equals("ERP_DONDATHANGNPP") || tableName.equals("ERP_HOPDONGNPP") )
		{
			if( loainhanvien.equals("1") ) //BM
			{
				
			}
			else if( loainhanvien.equals("2") ) //ASM
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select DDKD_FK from DDKD_GSBH where GSBH_FK in ( select PK_SEQ from GIAMSATBANHANG where ASM_FK = '" + doituongId + "' ) ) \n";
			}
			else if( loainhanvien.equals("3") ) //GSBH
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select DDKD_FK from DDKD_GSBH where GSBH_FK = '" + doituongId + "' ) \n";
			}
			else if( loainhanvien.equals("4") ) //DDKD
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( " + doituongId + " ) \n";
			}
		}
		else if( tableName.equals("KHACHHANG") )
		{
			if( loainhanvien.equals("1") ) //BM
			{
				
			}
			else if( loainhanvien.equals("2") ) //ASM
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select khachhang_fk from KhachHang_DaiDienKinhDoanh where DDKD_FK in ( select DDKD_FK from DDKD_GSBH where GSBH_FK in ( select PK_SEQ from GIAMSATBANHANG where ASM_FK = '" + doituongId + "' ) ) ) \n";
			}
			else if( loainhanvien.equals("3") ) //GSBH
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select khachhang_fk from KhachHang_DaiDienKinhDoanh where DDKD_FK in ( select DDKD_FK from DDKD_GSBH where GSBH_FK = '" + doituongId + "' ) ) \n";
			}
			else if( loainhanvien.equals("4") ) //DDKD
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select khachhang_fk from KhachHang_DaiDienKinhDoanh where DDKD_FK = '" + doituongId + "' ) \n";
			}
			else if( loainhanvien.equals("6") ) //NVGN
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '" + doituongId + "' ) \n";
			}
			else if( loainhanvien.equals("7") ) //CS - PHAN THEO DIA BAN
			{
				//query += " and a.diaban in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + this.userId + "' ) ";
				
				String condition01 = "  " + tableAlias + "." + columnName + " in ( select pk_seq from KHACHHANG where diaban in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + doituongId + "' ) ) ";
				String condition02 = "  " + tableAlias + "." + columnName + " in ( select pk_seq from KHACHHANG where tinhthanh_fk in ( select tinhthanh_fk from nhanvien_tinhthanh where nhanvien_fk = '" + doituongId + "' ) ) ";
				
				//condition += " and " + tableAlias + "." + columnName + " in ( select pk_seq from KHACHHANG where diaban in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + doituongId + "' ) ) ";
				
				condition += "\n and ( ( " + condition01 + " ) or ( " + condition02 + " ) ) \n";
			}
		}
		else if( tableName.equals("TUYENBANHANG") || tableName.equals("DAIDIENKINHDOANH") )
		{
			if( loainhanvien.equals("1") ) //BM
			{
				
			}
			else if( loainhanvien.equals("2") ) //ASM
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select DDKD_FK from DDKD_GSBH where GSBH_FK in ( select PK_SEQ from GIAMSATBANHANG where ASM_FK = '" + doituongId + "' )  ) \n";
			}
			else if( loainhanvien.equals("3") ) //GSBH
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select DDKD_FK from DDKD_GSBH where GSBH_FK = '" + doituongId + "' ) \n";
			}
			else if( loainhanvien.equals("4") ) //DDKD
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( " + doituongId + " ) \n";
			}
		}
		else if( tableName.equals("GIAMSATBANHANG") )
		{
			if( loainhanvien.equals("1") ) //BM
			{
				
			}
			else if( loainhanvien.equals("2") ) //ASM
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select PK_SEQ from GIAMSATBANHANG where ASM_FK = '" + doituongId + "'  ) \n";
			}
			else if( loainhanvien.equals("3") ) //GSBH
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( " + doituongId + " ) \n";
			}
			else if( loainhanvien.equals("4") ) //DDKD
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select GSBH_FK from DDKD_GSBH where DDKD_FK = '" + doituongId + "' ) \n";
			}
		}
		else if( tableName.equals("NHANVIENGIAONHAN") )
		{
			if( loainhanvien.equals("1") ) //BM
			{
				
			}
			else if( loainhanvien.equals("2") ) //ASM
			{
				
			}
			else if( loainhanvien.equals("3") ) //GSBH
			{
				
			}
			else if( loainhanvien.equals("4") ) //DDKD
			{
				
			}
			else if( loainhanvien.equals("6") ) //NVGN
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( " + doituongId + " ) \n";
			}
		}
		else if( tableName.equals("NHANVIENGIAONHAN_ALL") )
		{
			if( loainhanvien.equals("1") ) //BM
			{
				
			}
			else if( loainhanvien.equals("2") ) //ASM
			{
				
			}
			else if( loainhanvien.equals("3") ) //GSBH
			{
				
			}
			else if( loainhanvien.equals("4") ) //DDKD
			{
				
			}
			else if( loainhanvien.equals("6") ) //NVGN
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select nvgn_fk from NHANVIEN where nvgn_fk is not null " + 
																				" 	and pk_seq in ( select nhanvien_fk from nhanvien_diaban where diaban_fk in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + doituongId + "' ) ) ) \n";
			}
		}
		else if( tableName.equals("DLPP") )
		{
			if( loainhanvien.equals("6") ) //NVGN
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select NPP_FK from NHAPP_DIABAN where NVGN_FK in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + doituongId + "' ) ) \n";
			}
		}
		else if( tableName.equals("CS_BAOCAO") || tableName.equals("CS_BAOCAO_NPP") )
		{
			if( loainhanvien.equals("7") ) //CS - PHAN THEO DIA BAN
			{
				//query += " and a.diaban in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + this.userId + "' ) ";
				
				String condition01 = "  ";
				String condition02 = "  ";
				if( tableName.equals("CS_BAOCAO") )
				{
					condition01 = "  " + tableAlias + "." + columnName + " in ( select pk_seq from KHACHHANG where diaban in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + doituongId + "' ) ) ";
					condition02 = "  " + tableAlias + "." + columnName + " in ( select pk_seq from KHACHHANG where tinhthanh_fk in ( select tinhthanh_fk from nhanvien_tinhthanh where nhanvien_fk = '" + doituongId + "' ) ) ";
				}
				else  //CS_BAOCAO_NPP
				{
					condition01 = "  " + tableAlias + "." + columnName + " in ( select NPP_FK from NHAPP_DIABAN where DIABAN_FK in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + doituongId + "' ) ) ";
					condition02 = "  " + tableAlias + "." + columnName + " in ( select pk_seq from NHAPHANPHOI where tinhthanh_fk in ( select tinhthanh_fk from nhanvien_tinhthanh where nhanvien_fk = '" + doituongId + "' ) ) ";
				}
				
				condition += " and ( ( " + condition01 + " ) or ( " + condition02 + " ) ) \n";
			}
		}
		
		System.out.println("::: CONDITION: " + condition );
		return condition;
	}
	
	public boolean checkId( String id, String ids, String dau )
	{
		String[] _ids = ids.split( dau );
		
		for(int i = 0; i < _ids.length; i++)
		{
			if(_ids[i].trim().equals(id) )
				return true;
		}
		
		return false;
	}
	
	public String Update_TaiKhoan_Vat_DienGiai_KBH( dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, String kbh_fk )
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		String _KBH  = "NULL";
		if(kbh_fk.trim().length()>0)
			_KBH = kbh_fk;
		
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP, KBH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", "+_KBH+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP, KBH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", "+_KBH+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}

	public String quyen_nhamay(String userId)
	{
		String sql ="( select nhamay_fk from nhanvien_nhamay where nhanvien_fk ='"+ userId +"' )";
		return sql;
	}
	
	/********************************* XỬ LÝ TỒN KHO PHANAM ********************************/
	public String KiemTraTonKho(geso.traphaco.distributor.db.sql.dbutils db, String nppId, String khoId, String nhomkenhId, String sanphamId, 
			String solo, String ngayhethan, String soluong, String ngaychungtu)
	{
		String msg = "";
		/*String query = "";
		 
		//THÔNG BÁO CỤ THỂ SỐ LÔ NÀO CÓ THỂ XUẤT BAO NHIÊU
		query = " select tensanpham as ten, tonkho " + 
				" from dbo.ufn_Lay_TonHienTai( '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', '" + sanphamId + "', '" + solo + "', '" + ngayhethan + "', '" + ngaychungtu + "' ) ";
		System.out.println("::: KIEM TRA TON KHO: " + query);
		ResultSet rs = db.get(query);
		msg = "Các sản phẩm sau không đủ tồn kho: ";
		if( rs != null )
		{
			try 
			{
				while( rs.next() )
				{
					if( Double.parseDouble(soluong) < rs.getDouble("tonkho") )
					{
						msg += "+ Sản phẩm (" + rs.getString("ten") + "), với số lô (" + solo + "), ngày hết hạn (" + ngayhethan + "), tới ngày (" + ngaychungtu + "), " + 
							   " chỉ được xuất tối đa (" + rs.getString("tonkho") + "), không đủ số lượng xuất (" + soluong + ") \n" ;
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return "Lỗi khi check kho: " + e.getMessage();
			}
		}
		
		if( msg.equals("Các sản phẩm sau không đủ tồn kho: ") )
			msg = "";*/
		
		return msg;
	}
	
	public String KiemTraTonKho_NgayNhapKho(geso.traphaco.distributor.db.sql.dbutils db, String nppId, String khoId, String nhomkenhId, String sanphamId, 
			String solo, String ngayhethan, String soluong, String ngaynhapkho)
	{
		String msg = "";
		String query = "";
		 
		//THÔNG BÁO CỤ THỂ SỐ LÔ NÀO CÓ THỂ XUẤT BAO NHIÊU
		query = " select tensanpham as ten, tonkho " + 
				" from dbo.ufn_Lay_TonHienTai_NgayNhapKho( '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', '" + sanphamId + "', '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho + "' ) ";
		System.out.println("::: KIEM TRA TON KHO: " + query);
		ResultSet rs = db.get(query);
		msg = "Các sản phẩm sau không đủ tồn kho: ";
		if( rs != null )
		{
			try 
			{
				while( rs.next() )
				{
					if( Double.parseDouble(soluong) < rs.getDouble("tonkho") )
					{
						msg += "+ Sản phẩm (" + rs.getString("ten") + "), với số lô (" + solo + "), ngày hết hạn (" + ngayhethan + "), tới ngày (" + ngaynhapkho + "), " + 
							   " chỉ được xuất tối đa (" + rs.getString("tonkho") + "), không đủ số lượng xuất (" + soluong + ") \n" ;
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return "Lỗi khi check kho: " + e.getMessage();
			}
		}
		
		if( msg.equals("Các sản phẩm sau không đủ tồn kho: ") )
			msg = "";
		
		return msg;
	}
	
	/****************************** SỬ DỤNG NGÀY NHẬP KHO TỰ ĐỀ XUẤT ***************************/
	public String CapNhatKhoNew(geso.traphaco.distributor.db.sql.dbutils db, String nppId, String khoId, String nhomkenhId, String sanphamId, 
			String solo, String ngayhethan, String soluong,
			String ngaychungtu, String sochungtu, String diengiai, String bean_svl, String NHAP_XUAT, boolean ghinhanNXT_TRONGNGAY  )
	{
		String msg = "";
		String query = "";
		
		//CHECK XEM NGÀY HẾT HẠN NÀY CÒN NHỮNG NGÀY NHẬP KHO NÀO CÓ THỂ SỬ DỤNG
		query = " select ngaynhapkho, soluong - booked as AVAILABLE " + 
				" from dbo.ufn_Lay_TonHienTai_NgayNhapKho( '" + nppId + "', '" + khoId + "', '" + sanphamId + "', '" + ngaychungtu + "' ) " + 
				" where ngayhethan = '" + ngayhethan + "' and solo = '" + solo + "'	 " + 
				" order by ngaynhapkho asc ";
		System.out.println("::: LAY TON HIEN TAI NGAYNHAPKHO: " + query);
		ResultSet rs = db.get(query);
		msg = "";
		double total = 0;
		if( rs != null )
		{
			try 
			{
				while( rs.next() )
				{
					double slg = 0;
					double avai = Math.round(rs.getDouble("AVAILABLE") * 100.0 ) / 100.0;
					String ngaynhapkho = rs.getString("ngaynhapkho");

					total += avai;					
					if(total < Double.parseDouble(soluong))
					{
						slg = avai;
					}
					else
					{
						slg =  Double.parseDouble(soluong) - ( total - avai );
					}
					
					if( slg > 0 ) //LƯU LẠI NGÀY NHẬP KHO SẼ SỬ DỤNG
					{
						query = "insert LOG_NGAYNHAPKHO( NGAYCHUNGTU, SOCHUNGTU, DIENGIAI, NPP_FK, KHO_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, NGAYNHAPKHO ) " +
							    "values( '" + ngaychungtu + "', '" + sochungtu + "', N'" + diengiai + "', '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', " + 
							   			" '" + sanphamId + "', '" + soluong + "', '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho + "' )";
						
						System.out.println("::: CHEN LOG NGAY NHAP KHO: " + query);
						if( db.updateReturnInt(query) < 1 )
						{
							msg = "Lỗi khi ghi nhận ngày nhập kho: " + query;
							return msg;
						}
						
						msg = this.CapNhatKho_NgayNhapKho(db, nppId, khoId, nhomkenhId, sanphamId, solo, ngayhethan, soluong, ngaychungtu, sochungtu, diengiai, bean_svl, NHAP_XUAT, ngaynhapkho, ghinhanNXT_TRONGNGAY);
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
				return "Lỗi khi lấy ngày nhập kho: " + e.getMessage();
			}
		}
		
		if(total != Double.parseDouble(soluong))
		{
			msg = "Lỗi lấy tổng lượng theo ngày nhập kho (" + total + ") khác với tổng lượng yêu cầu (" + soluong + ")";
		}
		
		return msg;
	}
	
	/****************************** SỬ DỤNG NGÀY NHẬP KHO ĐÃ BIẾT ***************************/
	public String CapNhatKho_NgayNhapKho(geso.traphaco.distributor.db.sql.dbutils db, String nppId, String khoId, String nhomkenhId, String sanphamId, 
			String solo, String ngayhethan, String soluong,
			String ngaychungtu, String sochungtu, String diengiai, String bean_svl, String NHAP_XUAT, String ngaynhapkho, boolean ghinhanNXT_TRONGNGAY  )
	{
		String msg = "";
		String query = "";
		
		//GHI NHẬN SỐ NHẬP NHẬP XUẤT TRONG NGÀY
		int phanloai = 0;
		if( ghinhanNXT_TRONGNGAY )
		{
			phanloai = 1;
			query = " insert NHAP_XUAT_TON_TRONGNGAY( NGAYCHUNGTU, NPP_FK, KHO_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT ) " +
					" values( '" + ngaychungtu + "', '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', '" + sanphamId + "', '" + soluong + "', '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho + "', '" + NHAP_XUAT + "' )";
			
			System.out.println("::: INSERT NHAP_XUAT_TON_TRONGNGAY: " + query);
			if( db.updateReturnInt(query) < 1 )
			{
				msg = "Lỗi khi ghi nhận NXT trong ngày: " + query;
				return msg;
			}
		}
		
		//SAU KHI CẬP NHẬT NHẬP XUẤT TRONG NGÀY, MỚI CHECK SỐ CÓ ĐỦ ĐỂ LÀM NGHIỆP VỤ HAY KHÔNG
		if( NHAP_XUAT.equals("-1") ) //XUẤT MỚI CHECK
		{
			msg = this.KiemTraTonKho(db, nppId, khoId, nhomkenhId, sanphamId, solo, ngayhethan, soluong, ngaynhapkho);
			if( msg.trim().length() > 0 )
			{
				return msg;
			}
		}
		
		//GHI NHẬN LOG NGHIỆP VỤ TRỪ KHO  PHANLOAI = 1 -> CỘT SỐ LƯỢNG, PHANLOAI = 0 -> CỘT BOOKED
		query = "insert LOG_NGHIEPVUKHO( PHANLOAI, NGAYCHUNGTU, SOCHUNGTU, DIENGIAI, bean_svl, NPP_FK, KHO_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT ) " +
			    "values('" + phanloai + "', '" + ngaychungtu + "', '" + sochungtu + "', N'" + diengiai + "', N'" + bean_svl + "', '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', " + 
			   			" '" + sanphamId + "', '" + soluong + "', '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho + "', '" + NHAP_XUAT + "' )";
		
		System.out.println("::: INSERT LOG_NGHIEPVUKHO: " + query);
		if( db.updateReturnInt(query) < 1 )
		{
			msg = "Lỗi khi ghi nhận LOG tồn kho: " + query;
			return msg;
		}
		
		return msg;
	}
	
	//ĐỀ XUẤT NGÀY NHẬP KHO SỬ DỤNG CHO CÁC NGHIỆP VỤ XUẤT CÓ SỬ DỤNG
	public String DeXuatNgayNhapKho(geso.traphaco.distributor.db.sql.dbutils db, String nppId, String khoId, String nhomkenhId, String sanphamId, 
			String solo, String ngayhethan, String soluong, String ngaychungtu, String sochungtu, String diengiai  )
	{
		String msg = "";
		String query = "";
		
		//CHECK XEM NGÀY HẾT HẠN NÀY CÒN NHỮNG NGÀY NHẬP KHO NÀO CÓ THỂ SỬ DỤNG
		query = " select ngaynhapkho, soluong as AVAILABLE " + 
				" from dbo.ufn_Lay_NgayNhapKho( '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', '" + sanphamId + "', '" + solo + "', '" + ngayhethan + "' ) " + 
				" order by ngaynhapkho asc ";
		ResultSet rs = db.get(query);
		msg = "";
		double total = 0;
		if( rs != null )
		{
			try 
			{
				while( rs.next() )
				{
					double slg = 0;
					double avai = Math.round(rs.getDouble("AVAILABLE") * 100.0 ) / 100.0;
					String ngaynhapkho = rs.getString("ngaynhapkho");

					total += avai;					
					if(total < Double.parseDouble(soluong))
					{
						slg = avai;
					}
					else
					{
						slg =  Double.parseDouble(soluong) - ( total - avai );
					}
					
					if( slg > 0 ) //LƯU LẠI NGÀY NHẬP KHO SẼ SỬ DỤNG
					{
						query = "insert LOG_NGAYNHAPKHO( NGAYCHUNGTU, SOCHUNGTU, DIENGIAI, NPP_FK, KHO_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, NGAYNHAPKHO ) " +
							    "values( '" + ngaychungtu + "', '" + sochungtu + "', N'" + diengiai + "', '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', " + 
							   			" '" + sanphamId + "', '" + soluong + "', '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho + "' )";
						
						System.out.println("::: CHEN LOG NGAY NHAP KHO: " + query);
						if( db.updateReturnInt(query) < 1 )
						{
							msg = "Lỗi khi ghi nhận ngày nhập kho: " + query;
							return msg;
						}
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return "Lỗi khi lấy ngày nhập kho: " + e.getMessage();
			}
		}
		
		if(total != Double.parseDouble(soluong))
		{
			msg = "Lỗi lấy tổng lượng theo ngày nhập kho (" + total + ") khác với tổng lượng yêu cầu (" + soluong + ")";
		}
		
		return msg;
	}
	
	public String GhiNhanLOG(geso.traphaco.distributor.db.sql.dbutils db, String NPPID, String KHOID, String khachhangKG_FK, String NHOMKENHID, String SPID, 
			String SOLO, String NGAYHETHAN, double SOLUONG, double BOOKED, double AVAILABLE,
			String NGAYCHUNGTU, String SOCHUNGTU, String LOAICHUNGTU, String DIENGIAI, String bean_svl, String NHAP_XUAT, String NGAYNHAPKHO, boolean ghinhanNXT_TRONGNGAY  )
	{
		String msg = "";
		String query = "";
		
		if( khachhangKG_FK.trim().length() <= 0 )
			khachhangKG_FK = "NULL";
		
		//GHI NHẬN SỐ NHẬP NHẬP XUẤT TRONG NGÀY
		int phanloai = 0;
		if( ghinhanNXT_TRONGNGAY )
		{
			phanloai = 1;
			/*query = " insert NHAP_XUAT_TON_TRONGNGAY( NGAYCHUNGTU, SOCHUNGTU, LOAICHUNGTU, NPP_FK, KHO_FK, khachhangKG_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT ) " +
					" values( '" + NGAYCHUNGTU + "', '" + SOCHUNGTU + "', N'" + LOAICHUNGTU + "', '" + NPPID + "', '" + KHOID + "', " + khachhangKG_FK + ", '" + NHOMKENHID + "', '" + SPID + "', '" + SOLUONG + "', '" + SOLO + "', '" + NGAYHETHAN + "', '" + NGAYNHAPKHO + "', '" + NHAP_XUAT + "' )";
			
			System.out.println("::: INSERT NHAP_XUAT_TON_TRONGNGAY: " + query);
			if( db.updateReturnInt(query) < 1 )
			{
				msg = "Lỗi khi ghi nhận NXT trong ngày: " + query;
				return msg;
			}*/
		}
		
		//GHI NHẬN LOG NGHIỆP VỤ TRỪ KHO  PHANLOAI = 1 -> TRỪ CỘT SỐ LƯỢNG, PHANLOAI = 0 -> CỘT BOOKED
		String queryCHECK = "";
		boolean exits = true;
		if( khachhangKG_FK.equals("NULL") )
		{
			queryCHECK = "select count(*) as sodong " +
				   		 "from NHAPP_KHO_CHITIET " + 
				   		 "where NPP_FK = '" + NPPID + "' and KHO_FK = '" + KHOID + "' and SANPHAM_FK = '" + SPID + "' and nhomkenh_fk = '" + NHOMKENHID + "' and SOLO = '" + SOLO + "' and NGAYHETHAN = '" + NGAYHETHAN + "' ";
		}
		else
		{
			queryCHECK = "select count(*) as sodong " +
				   		 "from NHAPP_KHO_KYGUI_CHITIET " + 
				   		 "where khachhang_fk = '" + khachhangKG_FK + "' and NPP_FK = '" + NPPID + "' and KHO_FK = '" + KHOID + "' and SANPHAM_FK = '" + SPID + "' and nhomkenh_fk = '" + NHOMKENHID + "' and SOLO = '" + SOLO + "' and NGAYHETHAN = '" + NGAYHETHAN + "' ";
		}
		ResultSet rs = db.get(queryCHECK);
		if( rs != null )
		{
			try
			{
				if( rs.next() )
				{
					if( rs.getInt("sodong") <= 0 )
						exits = false;
				}
				rs.close();
			}
			catch(Exception ex) { 
				ex.printStackTrace();
			}
		}
		
		if( khachhangKG_FK.equals("NULL") )
		{
			if( !exits )
			{
				query = "insert LOG_NGHIEPVUKHO( PHANLOAI, NGAYCHUNGTU, SOCHUNGTU, LOAICHUNGTU, DIENGIAI, bean_svl, NPP_FK, KHO_FK, khachhangKG_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT, SOLUONG_TAITD, BOOKED_TAITD, AVAI_TAITD ) " +
					    "select '" + phanloai + "', '" + NGAYCHUNGTU + "', '" + SOCHUNGTU + "', N'" + LOAICHUNGTU + "', N'" + DIENGIAI + "', N'" + bean_svl + "', '" + NPPID + "', '" + KHOID + "', " + khachhangKG_FK + ", '" + NHOMKENHID + "', " + 
					   			" '" + SPID + "', '" + SOLUONG + "', '" + BOOKED + "', '" + AVAILABLE + "', '" + SOLO + "', '" + NGAYHETHAN + "', '" + NGAYNHAPKHO + "', '" + NHAP_XUAT + "', 0 as SOLUONG, 0 as BOOKED, 0 as AVAILABLE ";
			}
			else
			{
				query = "insert LOG_NGHIEPVUKHO( PHANLOAI, NGAYCHUNGTU, SOCHUNGTU, LOAICHUNGTU, DIENGIAI, bean_svl, NPP_FK, KHO_FK, khachhangKG_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT, SOLUONG_TAITD, BOOKED_TAITD, AVAI_TAITD ) " +
					    "select '" + phanloai + "', '" + NGAYCHUNGTU + "', '" + SOCHUNGTU + "', N'" + LOAICHUNGTU + "', N'" + DIENGIAI + "', N'" + bean_svl + "', '" + NPPID + "', '" + KHOID + "', " + khachhangKG_FK + ", '" + NHOMKENHID + "', " + 
					   			" '" + SPID + "', '" + SOLUONG + "', '" + BOOKED + "', '" + AVAILABLE + "', '" + SOLO + "', '" + NGAYHETHAN + "', '" + NGAYNHAPKHO + "', '" + NHAP_XUAT + "', SOLUONG, BOOKED, AVAILABLE " +
					   	"from NHAPP_KHO_CHITIET " + 
					   	"where NPP_FK = '" + NPPID + "' and KHO_FK = '" + KHOID + "' and SANPHAM_FK = '" + SPID + "' and nhomkenh_fk = '" + NHOMKENHID + "' and SOLO = '" + SOLO + "' and NGAYHETHAN = '" + NGAYHETHAN + "' ";
			}
		}
		else
		{
			if( !exits )
			{
				query = "insert LOG_NGHIEPVUKHO( PHANLOAI, NGAYCHUNGTU, SOCHUNGTU, LOAICHUNGTU, DIENGIAI, bean_svl, NPP_FK, KHO_FK, khachhangKG_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT, SOLUONG_TAITD, BOOKED_TAITD, AVAI_TAITD ) " +
					    "select '" + phanloai + "', '" + NGAYCHUNGTU + "', '" + SOCHUNGTU + "', N'" + LOAICHUNGTU + "', N'" + DIENGIAI + "', N'" + bean_svl + "', '" + NPPID + "', '" + KHOID + "', " + khachhangKG_FK + ", '" + NHOMKENHID + "', " + 
					   			" '" + SPID + "', '" + SOLUONG + "', '" + BOOKED + "', '" + AVAILABLE + "', '" + SOLO + "', '" + NGAYHETHAN + "', '" + NGAYNHAPKHO + "', '" + NHAP_XUAT + "', 0 as SOLUONG, 0 as BOOKED, 0 as AVAILABLE ";
			}
			else
			{
				query = "insert LOG_NGHIEPVUKHO( PHANLOAI, NGAYCHUNGTU, SOCHUNGTU, LOAICHUNGTU, DIENGIAI, bean_svl, NPP_FK, KHO_FK, khachhangKG_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT, SOLUONG_TAITD, BOOKED_TAITD, AVAI_TAITD ) " +
					    "select '" + phanloai + "', '" + NGAYCHUNGTU + "', '" + SOCHUNGTU + "', N'" + LOAICHUNGTU + "', N'" + DIENGIAI + "', N'" + bean_svl + "', '" + NPPID + "', '" + KHOID + "', " + khachhangKG_FK + ", '" + NHOMKENHID + "', " + 
					   			" '" + SPID + "', '" + SOLUONG + "', '" + BOOKED + "', '" + AVAILABLE + "', '" + SOLO + "', '" + NGAYHETHAN + "', '" + NGAYNHAPKHO + "', '" + NHAP_XUAT + "', SOLUONG, BOOKED, AVAILABLE " +
					   	"from NHAPP_KHO_KYGUI_CHITIET " + 
					   	"where khachhang_fk = '" + khachhangKG_FK + "' and NPP_FK = '" + NPPID + "' and KHO_FK = '" + KHOID + "' and SANPHAM_FK = '" + SPID + "' and nhomkenh_fk = '" + NHOMKENHID + "' and SOLO = '" + SOLO + "' and NGAYHETHAN = '" + NGAYHETHAN + "' ";
			}
		}
		
		System.out.println("::: INSERT LOG_NGHIEPVUKHO: " + query);
		if( db.updateReturnInt(query) < 1 )
		{
			msg = "Lỗi khi ghi nhận LOG tồn kho: " + query;
			return msg;
		}
		
		return msg;
	}

	public String Update_TaiKhoan_TheoSoHieu_CAN(Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK, String NO, String CO, String DOITUONG,  
								String MADOITUONG, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
		
		
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
	
		
		//GHI CO
//		if(Float.parseFloat(_CO) != 0) 
//		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN like  '" + taikhoanCO_SoHieu + "%' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) {
				e.printStackTrace();}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
										" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
										" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select pk_seq, " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' " +
						"from ERP_TAIKHOANKT " +
						"where SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%' ";
			}
			
			System.out.println("1.Cap nhat tai khoan CO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
			if(taikhoanNO_SoHieu.trim().length() > 0)
			{
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "'" +
						"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b  " +
						"where a.SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%' and b.SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%' ";
			}
			else
			{
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
										 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
						" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "'" +
						"from ERP_TAIKHOANKT a" +
						"where a.SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%'  ";
			}
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
		
//		}
		
		//GHI NO
//		if(Float.parseFloat(_NO) != 0) 
//		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			
			ResultSet rsTKCo = db.get(query);
			sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select pk_seq, '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' " +
						"from ERP_TAIKHOANKT " +
						"where SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%' ";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
			if(taikhoanCO_SoHieu.trim().length() > 0)
			{
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' " +
						"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b " +
						"where a.SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%' and b.SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%' ";
			}
			else
			{
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
										 		"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' " +
						"from ERP_TAIKHOANKT a " +
						"where a.SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%' ";
			}
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
		
//		}
		
		return msg;
	
	}
	
	public static void GetThangKhoaSoMax(Idbutils db, List<Integer> thangNam)
	{
		 int thangKSMax = 0;
		 int namKSMax = 0;
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		String query = "select top 1 THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					thangKSMax = rsCheck.getInt("THANGKS");
					namKSMax = rsCheck.getInt("NAM");
				}
				rsCheck.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		thangNam.add(thangKSMax);
		thangNam.add(namKSMax);
	}
	
	public String Huy_Update_TaiKhoan_TheoSoHieu(Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK, String NO, String CO, String DOITUONG,  
							String MADOITUONG, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}

		
		String query;
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
		
		
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;

	
		//GHI CO
		if(Float.parseFloat(_CO) != 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
										" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
										" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select pk_seq, " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' " +
						"from ERP_TAIKHOANKT " +
						"where SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan CO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
	//			if(taikhoanNO_SoHieu.trim().length() > 0)
	//			{
	//				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
	//													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
	//						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
	//									" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "'" +
	//						"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b  " +
	//						"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ";
	//			}
	//			else
	//			{
	//				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
	//										 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
	//						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
	//						" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "'" +
	//						"from ERP_TAIKHOANKT a" +
	//						"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "'  ";
	//			}
			
	//			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
	//			if(!db.update(query))
	//			{
	//				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
	//				return msg;
	//			}
		}
	
		//GHI NO
		if(Float.parseFloat(_NO) != 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select pk_seq, '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' " +
						"from ERP_TAIKHOANKT " +
						"where SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
	//			if(taikhoanCO_SoHieu.trim().length() > 0)
	//			{
	//				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
	//													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
	//						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
	//									" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' " +
	//						"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b " +
	//						"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ";
	//			}
	//			else
	//			{
	//				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
	//										 		"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
	//						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
	//									" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' " +
	//						"from ERP_TAIKHOANKT a " +
	//						"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ";
	//			}
			
	//			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
	//			if(!db.update(query))
	//			{
	//				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
	//				return msg;
	//			}
		}
	
		query = 
			"delete ERP_PHATSINHKETOAN \n" +
			"where loaichungtu = N'" + loaichungtu + "' and sochungtu = " + sochungtu + "\n" +
	//			"and ((\n" +
	//			"taikhoan_fk = (select a.pk_seq from ERP_TAIKHOANKT a where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "')\n" +
	//			"and taikhoandoiung_fk = (select a.pk_seq from ERP_TAIKHOANKT a where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "')\n" +
	//			") or (\n" +
	//			"taikhoandoiung_fk = (select a.pk_seq from ERP_TAIKHOANKT a where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "')\n" +
	//			"and taikhoan_fk = (select a.pk_seq from ERP_TAIKHOANKT a where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "')\n" +
	//			"))\n" +
			"and DOITUONG = N'" + DOITUONG + "' \n" +
			"and MADOITUONG = N'" + MADOITUONG + "' and LOAIDOITUONG = '" + LOAIDOITUONG + "'\n";
		
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
		int num = db.updateReturnInt(query);
		System.out.println("num: " + num);
		if(num == 0)
		{
			msg = "3.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
		return msg;
	}

	public String Update_TaiKhoan_TheoSoHieu_DoiTuong_NoCo( Idbutils db, String thang, String nam, String ngaychungtu
			, String ngayghinhan, String loaichungtu, String sochungtu
			, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK
			, String NO, String CO
			, String DOITUONG_NO, String MADOITUONG_NO, String LOAIDOITUONG_NO
			, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG_CO
			, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK
			, String TONGGIATRI, String TONGGIATRINT, String khoanmuc)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		
	
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
		_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
		_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
		_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
		_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
		_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
		_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
		_thanhtienNT = TONGGIATRINT.trim();
		
		
		String _NO = "0";
		if(NO.trim().length() > 0)
		_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
		_CO = CO;
		
		
		//GHI CO
		if(Float.parseFloat(_CO) >= 0) 
		{
		query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
		"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and NPP_FK = " + nppId +") and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		
		ResultSet rsTKNo = db.get(query);
		int sodong = 0;
		try 
		{
		if(rsTKNo.next())
		{
		sodong = rsTKNo.getInt("sodong");
		}
		rsTKNo.close();
		} 
		catch (Exception e) { 
			e.printStackTrace();
		}
		
		if(sodong > 0) //daco
		{
		query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
							" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
							" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
			" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "'  and NPP_FK = " + nppId +") and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		}
		else
		{
		query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
			"select pk_seq, " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' " +
			"from ERP_TAIKHOANKT " +
			"where SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and NPP_FK = " + nppId;
		}
		
		System.out.println("1.Cap nhat tai khoan CO: " + query);
		if(!db.update(query))
		{
		msg = "U_TK_TSHT_DT_NC1 Không thể cập nhật tài khoản kế toán " + query;
		return msg;
		}
		
		
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanNO_SoHieu.trim().length() > 0)
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
										 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
						" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG_CO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "'" +
			"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b  " +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' " +
			" and a.NPP_FK = " + nppId + " and b.NPP_FK = " + nppId +"";
		}
		else
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
							 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
			" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG_CO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "'" +
			"from ERP_TAIKHOANKT a" +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and a.NPP_FK = " + nppId +"";
		}
		
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
		if(!db.update(query))
		{
		msg = "U_TK_TSHT_DT_NC3.Không thể cập nhật tài khoản kế toán " + query;
		return msg;
		}
		
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) >= 0) 
		{
		query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
		"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and NPP_FK = " + nppId + ") and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		
		ResultSet rsTKCo = db.get(query);
		int sodong = 0;
		try 
		{
		if(rsTKCo.next())
		{
		sodong = rsTKCo.getInt("sodong");
		}
		rsTKCo.close();
		} 
		catch (Exception e) { 
			e.printStackTrace();
		}
		
		if(sodong > 0) //daco
		{
		query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
									" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
									" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
			" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and NPP_FK = " + nppId +") and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		}
		else
		{
		query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
			"select pk_seq, '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' " +
			"from ERP_TAIKHOANKT " +
			"where SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and NPP_FK = " + nppId + " ";
		}
		
		System.out.println("U_TK_TSHT_DT_NC2.Cap nhat tai khoan NO: " + query);
		if(!db.update(query))
		{
		msg = "U_TK_TSHT_DT_NC2.Không thể cập nhật tài khoản kế toán " + query;
		return msg;
		}
		
		
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN NO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanCO_SoHieu.trim().length() > 0)
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
										 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
						" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG_NO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' " +
			"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b " +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' " +
			" and a.NPP_FK = " + nppId +" and b.NPP_FK = " + nppId +"";
		}
		else
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
							 		"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
						" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG_NO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' " +
			"from ERP_TAIKHOANKT a " +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "'  and a.NPP_FK = " + nppId +"";
		}
		
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
		if(!db.update(query))
		{
		msg = "U_TK_TSHT_DT_NC3.Không thể cập nhật tài khoản kế toán " + query;
		return msg;
		}
		
		}
		
		return msg;
		
	}
	
	
	
	public String Update_TaiKhoan_TheoSoHieu_DienGiai_KhoanMuc_NO_CO(Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK, String NO, String CO, String DOITUONG_NO,  
			String MADOITUONG_NO, String LOAIDOITUONG_NO,String DOITUONG_CO,String MADOITUONG_CO,String LOAIDOITUONG_CO, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc,String diengiai, 
			String khoanmucchiphi_fk_no,String khoanmucchiphi_fk_co,String maChungTu, String nppId)
	{
		
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		
		msg = "";
		String query="";
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
		_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
		_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
		_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
		_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
		_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
		_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
		_thanhtienNT = TONGGIATRINT.trim();
		
		String _khoanmucchiphi_fk_no = "null";
		if(khoanmucchiphi_fk_no.trim().length() > 0)
		_khoanmucchiphi_fk_no = khoanmucchiphi_fk_no.trim();
		
		
		String _khoanmucchiphi_fk_co = "null";
		if(khoanmucchiphi_fk_co.trim().length() > 0)
		_khoanmucchiphi_fk_co = khoanmucchiphi_fk_co.trim();
		
		
		String _NO = "0";
		if(NO.trim().length() > 0)
		_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
		_CO = CO;
		
		System.out.println("_co: " + _CO);
		System.out.println("_co: " + _NO);
		//GHI CO
		if(Float.parseFloat(_CO) != 0) 
		{
/*			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
			"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT \n" +
			"where congTy_FK = " + congTyId + " \n" +
			"and SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'\n";
			
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
			if(rsTKNo.next())
			{
			sodong = rsTKNo.getInt("sodong");
			}
			rsTKNo.close();
			} 
			catch (Exception e) { 
			e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
			query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", \n" +
				"				 GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", \n" +
				" 				GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + "\n" +
				" where taikhoankt_fk = \n" +
				"				( select pk_seq \n" +
				"				from ERP_TAIKHOANKT \n" +
				"				where congTy_FK = " + congTyId + "\n" +
				" 				and SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ) \n" +
				"				and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'\n";
			}
			else
			{
			query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) \n" +
				"select pk_seq, " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' \n" +
				"from ERP_TAIKHOANKT \n" +
				"where congTy_FK = " + congTyId + "\n " +
				"and SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' \n";
			}
			
			System.out.println("1.Cap nhat tai khoan CO: \n" + query + "\n-----------------------------");
			if(!db.update(query))
			{
			msg = "1.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
			}
*/		
		
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanCO_SoHieu.trim().length() > 0)
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, \n" +
										 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, " +
										 "TONGGIATRINT, KHOANMUC,DIENGIAI,KHOANMUCCHIPHI_FK,MACHUNGTU) \n" +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", '0', " + _CO + ", \n" +
						" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG_CO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "'\n" +
						", " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+diengiai+"',"+_khoanmucchiphi_fk_co+",N'"+maChungTu+"'" +
			"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b  \n" +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' \n" +
			"and a.NPP_FK ="+nppId+" and b.NPP_FK ="+nppId+ " ";
		}
		else
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, \n" +
				"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, " +
				"TONGGIATRINT, KHOANMUC,DIENGIAI,KHOANMUCCHIPHI_FK,MACHUNGTU) \n" +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", '0', " + _CO + ", \n" +
			" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG_CO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', \n" +
			" N'"+diengiai+"',"+_khoanmucchiphi_fk_co+",N'"+maChungTu+"'  \n" +
			"from ERP_TAIKHOANKT a\n" +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "'  \n" +
			"and a.NPP_FK = "+nppId+" ";
		}
		
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: \n" + query + "\n--------------------------------");
		if(!db.update(query))
		{
		msg = "3.Không thể cập nhật tài khoản kế toán " + query;
		return msg;
		}
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) != 0) 
		{
/*		query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
		"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where congTy_FK = " + congTyId + "\n " +
		"and SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		
		ResultSet rsTKCo = db.get(query);
		int sodong = 0;
		try 
		{
		if(rsTKCo.next())
		{
		sodong = rsTKCo.getInt("sodong");
		}
		rsTKCo.close();
		} 
		catch (Exception e) { 
		e.printStackTrace();
		}
		
		if(sodong > 0) //daco
		{
		query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", \n" +
									" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", \n" +
									" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + "\n" +
			" where taikhoankt_fk = \n" +
			"	( select pk_seq \n" +
			"	from ERP_TAIKHOANKT \n" +
			"	where congTy_FK = " + congTyId + "\n " +
			"	and SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' )\n" +
			" and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		}
		else
		{
		query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) \n" +
			"select pk_seq, '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' \n" +
			"from ERP_TAIKHOANKT \n" +
			"where congTy_FK = " + congTyId + "\n " +
			"and SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' \n" +
			"and congTy_FK = " + congTyId;
		}
		
		System.out.println("2.Cap nhat tai khoan NO: \n" + query);
		if(!db.update(query))
		{
		msg = "2.Không thể cập nhật tài khoản kế toán \n" + query;
		return msg;
		}
*/		
		
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanNO_SoHieu.trim().length() > 0)
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, \n" +
				"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, " +
				"TONGGIATRI, TONGGIATRINT, KHOANMUC,DIENGIAI,KHOANMUCCHIPHI_FK,MACHUNGTU) \n" +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", " + _NO + ", '0', \n" +
						" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG_NO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' \n" +
						" , N'"+diengiai+"',"+_khoanmucchiphi_fk_no+",N'"+maChungTu+"' \n" +
			"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b \n" +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' \n" +
			"and a.NPP_FK ="+nppId+" and b.NPP_FK ="+nppId +" ";
		}
		else
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, \n" +
				"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, " +
				"TONGGIATRI, TONGGIATRINT, KHOANMUC,DIENGIAI,KHOANMUCCHIPHI_FK,MACHUNGTU) \n" +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", " + _NO + ", '0', \n" +
						" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG_NO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' \n" +
						" , N'"+diengiai+"',"+_khoanmucchiphi_fk_no+",N'"+maChungTu+"'  \n" +
			"from ERP_TAIKHOANKT a \n" +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' \n" +
			"and a.NPP_FK ="+nppId+" ";
		}
		
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: \n" + query );
		if(!db.update(query))
		{
		msg = "3.Không thể cập nhật tài khoản kế toán \n" + query;
		return msg;
		}
		}
		
		return msg;
		}


	public String Update_TaiKhoan_TheoSoHieu_DoiTuong_NoCo(String congTyId, Idbutils db, String thang, String nam, String ngaychungtu
			, String ngayghinhan, String loaichungtu, String sochungtu
			, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK
			, String NO, String CO
			, String DOITUONG_NO, String MADOITUONG_NO, String LOAIDOITUONG_NO
			, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG_CO
			, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK
			, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String maChungTu
			, String dienGiai,String nppId)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
		_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
		_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
		_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
		_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
		_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
		_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
		_thanhtienNT = TONGGIATRINT.trim();
		
		
		String _NO = "0";
		if(NO.trim().length() > 0)
		_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
		_CO = CO;
		
		
		//GHI CO
		if(Float.parseFloat(_CO) >= 0) 
		{
		query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
		"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and NPP_FK = " + nppId +") and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		
		ResultSet rsTKNo = db.get(query);
		int sodong = 0;
		try 
		{
		if(rsTKNo.next())
		{
		sodong = rsTKNo.getInt("sodong");
		}
		rsTKNo.close();
		} 
		catch (Exception e) { 
			e.printStackTrace();
		}
		
		if(sodong > 0) //daco
		{
		query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
							" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
							" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
			" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "'  and NPP_FK = " + nppId +") and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		}
		else
		{
		query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
			"select pk_seq, " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' " +
			"from ERP_TAIKHOANKT " +
			"where SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and NPP_FK = " + nppId;
		}
		
		System.out.println("1.Cap nhat tai khoan CO: " + query);
		if(!db.update(query))
		{
		msg = "U_TK_TSHT_DT_NC1 Không thể cập nhật tài khoản kế toán " + query;
		return msg;
		}
		
		
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanNO_SoHieu.trim().length() > 0)
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
										 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, maChungTu, dienGiai, dienGiai_ct) " +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
						" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG_CO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', '" + maChungTu + "', N'" + dienGiai + "', N'" + dienGiai + "'" +
			"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b  " +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' " +
			" and a.NPP_FK = " + nppId + " and b.NPP_FK = " + nppId +"";
		}
		else
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
							 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, maChungTu, dienGiai, dienGiai_ct) " +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
			" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG_CO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', '" + maChungTu + "', N'" + dienGiai + "', N'" + dienGiai + "'" +
			" from ERP_TAIKHOANKT a" +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and a.NPP_FK = " + nppId +"";
		}
		
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
		if(!db.update(query))
		{
		msg = "U_TK_TSHT_DT_NC3.Không thể cập nhật tài khoản kế toán " + query;
		return msg;
		}
		
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) >= 0) 
		{
		query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
		"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and NPP_FK = " + nppId + ") and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		
		ResultSet rsTKCo = db.get(query);
		int sodong = 0;
		try 
		{
		if(rsTKCo.next())
		{
		sodong = rsTKCo.getInt("sodong");
		}
		rsTKCo.close();
		} 
		catch (Exception e) { 
			e.printStackTrace();
		}
		
		if(sodong > 0) //daco
		{
		query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
									" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
									" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
			" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and NPP_FK = " + nppId +") and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		}
		else
		{
		query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
			"select pk_seq, '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' " +
			"from ERP_TAIKHOANKT " +
			"where SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and NPP_FK = " + nppId + " ";
		}
		
		System.out.println("U_TK_TSHT_DT_NC2.Cap nhat tai khoan NO: " + query);
		if(!db.update(query))
		{
		msg = "U_TK_TSHT_DT_NC2.Không thể cập nhật tài khoản kế toán " + query;
		return msg;
		}
		
		
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN NO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanCO_SoHieu.trim().length() > 0)
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
										 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, maChungTu, dienGiai, dienGiai_ct) " +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
						" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG_NO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', '" + maChungTu + "', N'" + dienGiai + "', N'" + dienGiai + "'" +
			" from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b " +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' " +
			" and a.NPP_FK = " + nppId +" and b.NPP_FK = " + nppId +"";
		}
		else
		{
		query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
							 		"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, maChungTu, dienGiai, dienGiai_ct) " +
			"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
						" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG_NO + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', '" + maChungTu + "', N'" + dienGiai + "', N'" + dienGiai + "'" +
			"from ERP_TAIKHOANKT a " +
			"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "'  and a.NPP_FK = " + nppId +"";
		}
		
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
		if(!db.update(query))
		{
		msg = "U_TK_TSHT_DT_NC3.Không thể cập nhật tài khoản kế toán " + query;
		return msg;
		}
		
		}
		
		return msg;
		
	}

		//Đối tượng nợ, đối tượng NỢ / có khác nhau
	public String Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(Idbutils db, String congTyId,String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK, String NO, String CO
			, String DOITUONGNO, String MADOITUONGNO, String LOAIDOITUONGNO, String DOITUONGCO, String MADOITUONGCO, String LOAIDOITUONGCO
			, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		
		
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
	
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
		
		
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
		_CO = CO;
		
	
		//GHI CO
		//if(Float.parseFloat(_CO) != 0) 
		//{
		query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
		"where taikhoankt_fk = \n" +
		"		( select pk_seq \n" +
		"		from ERP_TAIKHOANKT \n" +
		"		where congTy_FK = " + congTyId + " \n" +
		"		and SOHIEUTAIKHOAN like  '" + taikhoanCO_SoHieu + "%'  and npp_fk = 1) \n" +
		"		and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		
		ResultSet rsTKNo = db.get(query);
		int sodong = 0;
		try 
		{
			if(rsTKNo.next())
			{
				sodong = rsTKNo.getInt("sodong");
			}
			rsTKNo.close();
		} 
		catch (Exception e) { 
			e.printStackTrace();
		}
	
		if(sodong > 0) //daco
		{
			query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", \n" +
					"			 GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", \n" +
					"			 GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + " \n" +
					" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where congTy_FK = " + congTyId + " \n" +
					"and SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%'  and npp_fk = 1) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		}
		else
		{
			query = 
				"insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK\n" +
				", GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) \n" +
				"select pk_seq, " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "'\n" +
				", " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' \n" +
				"from ERP_TAIKHOANKT \n" +
				"where congTy_FK = " + congTyId + " and SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%'  and npp_fk = 1\n";
		}
		
		System.out.println("1.Cap nhat tai khoan CO: " + query);
		if(!db.update(query))
		{
			msg = "1.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
		
	
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanNO_SoHieu.trim().length() > 0)
		{
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu\n" +
					", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
					", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
					", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
					", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
					"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + "\n" +
					"		, a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", '0'\n" +
					"		, " + _CO + ", N'" + DOITUONGCO+ "', N'" + MADOITUONGCO + "', '" + LOAIDOITUONGCO + "'\n" +
					"		, " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + "\n" +
					"		, '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "'\n" +
					"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b  \n" +
					"where a.SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%' \n" +
					"and b.SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%' \n" +
					"and a.congTy_FK = " + congTyId + "\n" +
					"and b.congTy_FK = " + congTyId + " and a.npp_fk = 1 and b.npp_fk = 1\n";
		}
		else
		{
			query = 
				"insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, soChungTu\n" +
				", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
				", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
				", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
				", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) \n" +
				"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + "\n" +
				"		, a.pk_seq, null, " + _ndnhapxuat_fk + ", '0'\n" +
				"		, " + _CO + ", N'" + DOITUONGCO + "', N'" + MADOITUONGCO + "', '" + LOAIDOITUONGCO + "', " + _soluong + "\n" +
				"		, " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "'\n" +
				"		, " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "'\n" +
				"from ERP_TAIKHOANKT a\n" +
				"where a.SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%'\n" +
				"and a.congTy_FK = " + congTyId + " and a.npp_fk = 1";
		}
			
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
		if(!db.update(query))
		{
			msg = "3.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
	
		//}
		
		//GHI NO
		//if(Float.parseFloat(_NO) != 0) 
		//{
		query = 
			"select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
			"where taikhoankt_fk = \n" +
			"		( select pk_seq \n" +
			"		from ERP_TAIKHOANKT \n" +
			"		where congTy_FK = " + congTyId + " \n" +
			"		and SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%'  and npp_fk = 1) \n" +
			"		and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'\n";
		
		ResultSet rsTKCo = db.get(query);
		sodong = 0;
		try 
		{
			if(rsTKCo.next())
			{
				sodong = rsTKCo.getInt("sodong");
			}
			rsTKCo.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	
		if(sodong > 0) //daco
		{
			query = 
				"update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", \n" +
				"	 GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", \n" +
				"	 GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + "\n" +
				" where taikhoankt_fk = \n" +
				"		( select pk_seq \n" +
				"		from ERP_TAIKHOANKT \n" +
				"		where congTy_FK = " + congTyId + "\n" +
				"		and SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%'  and npp_fk = 1) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		}
		else
		{
		query = 
			"insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK\n" +
			", GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) \n" +
			"select pk_seq, '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "'\n" +
			", " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' \n" +
			"from ERP_TAIKHOANKT \n" +
			"where congTy_FK = " + congTyId + " \n" +
			"and SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%'  and npp_fk = 1";
		}
		
		System.out.println("2.Cap nhat tai khoan NO: " + query);
		if(!db.update(query))
		{
			msg = "2.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
	
		
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN NO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanCO_SoHieu.trim().length() > 0)
		{
			query = 
				"insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu\n" +
				", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
				", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
				", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
				", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
				"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + "\n" +
				"			, a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", " + _NO + "\n" +
				"			, '0', N'" + DOITUONGNO + "', N'" + MADOITUONGNO + "' , '" + LOAIDOITUONGNO + "'\n" +
				"			, " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "' , " + _dongiaNT + "\n" +
				"			, '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + " , N'" + khoanmuc + "' \n" +
				"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b \n" +
				"where a.SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%' \n" +
				"and b.SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%' \n" +
				"and a.congTy_FK = " + congTyId + "\n" +
				"and b.congTy_FK = " + congTyId + " and a.npp_fk = 1 and b.npp_fk = 1\n";
		}
		else
		{
			query = 
				"insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu\n" +
				", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
				", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
				", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
				", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
				"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + "\n" +
				"		, a.pk_seq, null, " + _ndnhapxuat_fk + ", " + _NO + "\n" +
				"		, '0', N'" + DOITUONGNO + "', N'" + MADOITUONGNO + "', '" + LOAIDOITUONGNO + "'\n" +
				"		, " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + "\n" +
				"		, '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' \n" +
				"from ERP_TAIKHOANKT a \n" +
				"where a.SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%'  and a.npp_fk = 1\n" +
				"and a.congTy_FK = " + congTyId;
		}
	
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
		if(!db.update(query))
		{
			msg = "3.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
		
		return msg;
	}
	
	public String Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(Idbutils db, String congTyId,String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK, String NO, String CO
			, String DOITUONGNO, String MADOITUONGNO, String LOAIDOITUONGNO, String DOITUONGCO, String MADOITUONGCO, String LOAIDOITUONGCO
			, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc,String isNPP_NO,String isNPP_CO)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
	
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
	
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
		String _isNPP_NO="null";
		
		if(isNPP_NO.trim().length() > 0)
			_isNPP_NO=isNPP_NO.trim();
		
		
		String _isNPP_CO="null";
		
		if(isNPP_CO.trim().length() > 0)
			_isNPP_CO=isNPP_CO.trim();
		
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
		_CO = CO;
		
	
		//GHI CO
		//if(Float.parseFloat(_CO) != 0) 
		//{
		query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
		"where taikhoankt_fk = \n" +
		"		( select pk_seq \n" +
		"		from ERP_TAIKHOANKT \n" +
		"		where congTy_FK = " + congTyId + " \n" +
		"		and SOHIEUTAIKHOAN like  '" + taikhoanCO_SoHieu + "%'  and npp_fk = 1) \n" +
		"		and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		
		ResultSet rsTKNo = db.get(query);
		int sodong = 0;
		try 
		{
			if(rsTKNo.next())
			{
				sodong = rsTKNo.getInt("sodong");
			}
			rsTKNo.close();
		} 
		catch (Exception e) { 
			e.printStackTrace();
		}
	
		if(sodong > 0) //daco
		{
			query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", \n" +
					"			 GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", \n" +
					"			 GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + " \n" +
					" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where congTy_FK = " + congTyId + " \n" +
					"and SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%'  and npp_fk = 1) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		}
		else
		{
			query = 
				"insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK\n" +
				", GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) \n" +
				"select pk_seq, " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "'\n" +
				", " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' \n" +
				"from ERP_TAIKHOANKT \n" +
				"where congTy_FK = " + congTyId + " and SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%'  and npp_fk = 1\n";
		}
		
		System.out.println("1.Cap nhat tai khoan CO: " + query);
		if(!db.update(query))
		{
			msg = "1.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
		
	
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanNO_SoHieu.trim().length() > 0)
		{
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu\n" +
					", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
					", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
					", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
					", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,ISNPP) " +
					"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + "\n" +
					"		, a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", '0'\n" +
					"		, " + _CO + ", N'" + DOITUONGCO+ "', N'" + MADOITUONGCO + "', '" + LOAIDOITUONGCO + "'\n" +
					"		, " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + "\n" +
					"		, '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' ,"+_isNPP_CO+"\n" +
					"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b  \n" +
					"where a.SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%' \n" +
					"and b.SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%' \n" +
					"and a.congTy_FK = " + congTyId + "\n" +
					"and b.congTy_FK = " + congTyId + " and a.npp_fk = 1 and b.npp_fk = 1\n";
		}
		else
		{
			query = 
				"insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, soChungTu\n" +
				", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
				", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
				", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
				", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,ISNPP) \n" +
				"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + "\n" +
				"		, a.pk_seq, null, " + _ndnhapxuat_fk + ", '0'\n" +
				"		, " + _CO + ", N'" + DOITUONGCO + "', N'" + MADOITUONGCO + "', '" + LOAIDOITUONGCO + "', " + _soluong + "\n" +
				"		, " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "'\n" +
				"		, " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "',"+_isNPP_CO+" \n" +
				"from ERP_TAIKHOANKT a\n" +
				"where a.SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%'\n" +
				"and a.congTy_FK = " + congTyId + " and a.npp_fk = 1";
		}
			
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
		if(!db.update(query))
		{
			msg = "3.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
	
		//}
		
		//GHI NO
		//if(Float.parseFloat(_NO) != 0) 
		//{
		query = 
			"select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
			"where taikhoankt_fk = \n" +
			"		( select pk_seq \n" +
			"		from ERP_TAIKHOANKT \n" +
			"		where congTy_FK = " + congTyId + " \n" +
			"		and SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%'  and npp_fk = 1) \n" +
			"		and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'\n";
		
		ResultSet rsTKCo = db.get(query);
		sodong = 0;
		try 
		{
			if(rsTKCo.next())
			{
				sodong = rsTKCo.getInt("sodong");
			}
			rsTKCo.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	
		if(sodong > 0) //daco
		{
			query = 
				"update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", \n" +
				"	 GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", \n" +
				"	 GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + "\n" +
				" where taikhoankt_fk = \n" +
				"		( select pk_seq \n" +
				"		from ERP_TAIKHOANKT \n" +
				"		where congTy_FK = " + congTyId + "\n" +
				"		and SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%'  and npp_fk = 1) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		}
		else
		{
		query = 
			"insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK\n" +
			", GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) \n" +
			"select pk_seq, '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "'\n" +
			", " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' \n" +
			"from ERP_TAIKHOANKT \n" +
			"where congTy_FK = " + congTyId + " \n" +
			"and SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%'  and npp_fk = 1";
		}
		
		System.out.println("2.Cap nhat tai khoan NO: " + query);
		if(!db.update(query))
		{
			msg = "2.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
	
		
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN NO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanCO_SoHieu.trim().length() > 0)
		{
			query = 
				"insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu\n" +
				", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
				", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
				", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
				", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,ISNPP) " +
				"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + "\n" +
				"			, a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", " + _NO + "\n" +
				"			, '0', N'" + DOITUONGNO + "', N'" + MADOITUONGNO + "' , '" + LOAIDOITUONGNO + "'\n" +
				"			, " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "' , " + _dongiaNT + "\n" +
				"			, '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + " , N'" + khoanmuc + "',"+_isNPP_NO+" \n" +
				"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b \n" +
				"where a.SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%' \n" +
				"and b.SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%' \n" +
				"and a.congTy_FK = " + congTyId + "\n" +
				"and b.congTy_FK = " + congTyId + " and a.npp_fk = 1 and b.npp_fk = 1\n";
		}
		else
		{
			query = 
				"insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu\n" +
				", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
				", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
				", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
				", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,ISNPP) " +
				"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + "\n" +
				"		, a.pk_seq, null, " + _ndnhapxuat_fk + ", " + _NO + "\n" +
				"		, '0', N'" + DOITUONGNO + "', N'" + MADOITUONGNO + "', '" + LOAIDOITUONGNO + "'\n" +
				"		, " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + "\n" +
				"		, '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "',"+_isNPP_NO+" \n" +
				"from ERP_TAIKHOANKT a \n" +
				"where a.SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%'  and a.npp_fk = 1\n" +
				"and a.congTy_FK = " + congTyId;
		}
	
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
		if(!db.update(query))
		{
			msg = "3.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
		
		return msg;
	}
	public String Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(Idbutils db, String congTyId,String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK, String NO, String CO
			, String DOITUONGNO, String MADOITUONGNO, String LOAIDOITUONGNO, String DOITUONGCO, String MADOITUONGCO, String LOAIDOITUONGCO
			, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc,String isNPP_NO,String isNPP_CO,String dienGiai)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
	
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
	
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
		String _isNPP_NO="null";
		
		if(isNPP_NO.trim().length() > 0)
			_isNPP_NO=isNPP_NO.trim();
		
		
		String _isNPP_CO="null";
		
		if(isNPP_CO.trim().length() > 0)
			_isNPP_CO=isNPP_CO.trim();
		
		String _dienGiai = "";
		if(dienGiai.trim().length() >0)
			_dienGiai = dienGiai.trim();
		
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
		_CO = CO;
		
	
		//GHI CO
		//if(Float.parseFloat(_CO) != 0) 
		//{
		query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
		"where taikhoankt_fk = \n" +
		"		( select pk_seq \n" +
		"		from ERP_TAIKHOANKT \n" +
		"		where congTy_FK = " + congTyId + " \n" +
		"		and SOHIEUTAIKHOAN like  '" + taikhoanCO_SoHieu + "%'  and npp_fk = 1) \n" +
		"		and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		
		ResultSet rsTKNo = db.get(query);
		int sodong = 0;
		try 
		{
			if(rsTKNo.next())
			{
				sodong = rsTKNo.getInt("sodong");
			}
			rsTKNo.close();
		} 
		catch (Exception e) { 
			e.printStackTrace();
		}
	
		if(sodong > 0) //daco
		{
			query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", \n" +
					"			 GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", \n" +
					"			 GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + " \n" +
					" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where congTy_FK = " + congTyId + " \n" +
					"and SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%'  and npp_fk = 1) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		}
		else
		{
			query = 
				"insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK\n" +
				", GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) \n" +
				"select pk_seq, " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "'\n" +
				", " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' \n" +
				"from ERP_TAIKHOANKT \n" +
				"where congTy_FK = " + congTyId + " and SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%'  and npp_fk = 1\n";
		}
		
		System.out.println("1.Cap nhat tai khoan CO: " + query);
		if(!db.update(query))
		{
			msg = "1.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
		
	
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanNO_SoHieu.trim().length() > 0)
		{
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu\n" +
					", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
					", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
					", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
					", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,ISNPP,DIENGIAI) " +
					"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + "\n" +
					"		, a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", '0'\n" +
					"		, " + _CO + ", N'" + DOITUONGCO+ "', N'" + MADOITUONGCO + "', '" + LOAIDOITUONGCO + "'\n" +
					"		, " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + "\n" +
					"		, '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' ,"+_isNPP_CO+",N'"+ dienGiai +"'\n" +
					"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b  \n" +
					"where a.SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%' \n" +
					"and b.SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%' \n" +
					"and a.congTy_FK = " + congTyId + "\n" +
					"and b.congTy_FK = " + congTyId + " and a.npp_fk = 1 and b.npp_fk = 1\n";
		}
		else
		{
			query = 
				"insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, soChungTu\n" +
				", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
				", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
				", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
				", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,ISNPP) \n" +
				"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + "\n" +
				"		, a.pk_seq, null, " + _ndnhapxuat_fk + ", '0'\n" +
				"		, " + _CO + ", N'" + DOITUONGCO + "', N'" + MADOITUONGCO + "', '" + LOAIDOITUONGCO + "', " + _soluong + "\n" +
				"		, " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "'\n" +
				"		, " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "',"+_isNPP_CO+",N'"+ dienGiai +"'\n" +
				"from ERP_TAIKHOANKT a\n" +
				"where a.SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%'\n" +
				"and a.congTy_FK = " + congTyId + " and a.npp_fk = 1";
		}
			
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
		if(!db.update(query))
		{
			msg = "3.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
	
		//}
		
		//GHI NO
		//if(Float.parseFloat(_NO) != 0) 
		//{
		query = 
			"select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
			"where taikhoankt_fk = \n" +
			"		( select pk_seq \n" +
			"		from ERP_TAIKHOANKT \n" +
			"		where congTy_FK = " + congTyId + " \n" +
			"		and SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%'  and npp_fk = 1) \n" +
			"		and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'\n";
		
		ResultSet rsTKCo = db.get(query);
		sodong = 0;
		try 
		{
			if(rsTKCo.next())
			{
				sodong = rsTKCo.getInt("sodong");
			}
			rsTKCo.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	
		if(sodong > 0) //daco
		{
			query = 
				"update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", \n" +
				"	 GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", \n" +
				"	 GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + "\n" +
				" where taikhoankt_fk = \n" +
				"		( select pk_seq \n" +
				"		from ERP_TAIKHOANKT \n" +
				"		where congTy_FK = " + congTyId + "\n" +
				"		and SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%'  and npp_fk = 1) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
		}
		else
		{
		query = 
			"insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK\n" +
			", GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) \n" +
			"select pk_seq, '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "'\n" +
			", " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' \n" +
			"from ERP_TAIKHOANKT \n" +
			"where congTy_FK = " + congTyId + " \n" +
			"and SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%'  and npp_fk = 1";
		}
		
		System.out.println("2.Cap nhat tai khoan NO: " + query);
		if(!db.update(query))
		{
			msg = "2.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
	
		
		//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN NO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
		if(taikhoanCO_SoHieu.trim().length() > 0)
		{
			query = 
				"insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu\n" +
				", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
				", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
				", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
				", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,ISNPP) " +
				"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + "\n" +
				"			, a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", " + _NO + "\n" +
				"			, '0', N'" + DOITUONGNO + "', N'" + MADOITUONGNO + "' , '" + LOAIDOITUONGNO + "'\n" +
				"			, " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "' , " + _dongiaNT + "\n" +
				"			, '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + " , N'" + khoanmuc + "',"+_isNPP_NO+" \n" +
				"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b \n" +
				"where a.SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%' \n" +
				"and b.SOHIEUTAIKHOAN like '" + taikhoanCO_SoHieu + "%' \n" +
				"and a.congTy_FK = " + congTyId + "\n" +
				"and b.congTy_FK = " + congTyId + " and a.npp_fk = 1 and b.npp_fk = 1\n";
		}
		else
		{
			query = 
				"insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu\n" +
				", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
				", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
				", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
				", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,ISNPP) " +
				"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + "\n" +
				"		, a.pk_seq, null, " + _ndnhapxuat_fk + ", " + _NO + "\n" +
				"		, '0', N'" + DOITUONGNO + "', N'" + MADOITUONGNO + "', '" + LOAIDOITUONGNO + "'\n" +
				"		, " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + "\n" +
				"		, '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "',"+_isNPP_NO+" \n" +
				"from ERP_TAIKHOANKT a \n" +
				"where a.SOHIEUTAIKHOAN like '" + taikhoanNO_SoHieu + "%'  and a.npp_fk = 1\n" +
				"and a.congTy_FK = " + congTyId;
		}
	
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
		if(!db.update(query))
		{
			msg = "3.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
		
		return msg;
	}
	
	
	public String Update_TaiKhoan_Vat_DienGiai_CHIKHAC_NPP_PHATSINH (Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP_No, String isNPP_Co,
			String MAHOADON, String MAUHOADON, String KYHIEU, String SOHOADON, String NGAYHOADON, String TENNCC, String MST, String TIENHANG, String THUESUAT, String MAFAST_DT, String TEN_DT, String TEN_PB, String TEN_KBH, String TEN_VV, String TEN_DIABAN, String TEN_TINHTHANH, String TEN_BENHVIEN, String TEN_SANPHAM, String DIENGIAI_CT, String chiPhiNo, String chiPhiCo, String nppPhatSinhNo, String nppPhatSinhCo)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		msg="";
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP_NO = "null";
		if(isNPP_No.trim().length()>0)
			_ISNPP_NO = isNPP_No;
		
		String _ISNPP_CO = "null";
		if(isNPP_Co.trim().length()>0)
			_ISNPP_CO = isNPP_Co;
			
		String _MAHOADON = "";
		if(MAHOADON.trim().length()>0)
			_MAHOADON = MAHOADON;
		
		String _MAUHOADON = "";
		if(MAUHOADON.trim().length()>0)
			_MAUHOADON = MAUHOADON;
		
		String _KYHIEU = "";
		if(KYHIEU.trim().length()>0)
			_KYHIEU = KYHIEU;
		
		String _SOHOADON = "";
		if(SOHOADON.trim().length()>0)
			_SOHOADON = SOHOADON;
		
		String _NGAYHOADON = "";
		if(NGAYHOADON.trim().length()>0)
			_NGAYHOADON = NGAYHOADON;
		
		String _TENNCC = "";
		if(TENNCC.trim().length()>0)
			_TENNCC = TENNCC;
		
		String _MST = "";
		if(MST.trim().length()>0)
			_MST = MST;
		
		String _TIENHANG = "0";
		if(TIENHANG.trim().length()>0)
			_TIENHANG = TIENHANG;
		
		String _THUESUAT = "0";
		if(THUESUAT.trim().length()>0)
			_THUESUAT = THUESUAT;
						
		String _MAFAST_DT = "";
		if(MAFAST_DT.trim().length()>0)
			_MAFAST_DT = MAFAST_DT;
		
		String _TEN_DT = "";
		if(TEN_DT.trim().length()>0)
			_TEN_DT = TEN_DT;
		
		String _TEN_PB = ""; 
		if(TEN_PB.trim().length()>0)
			_TEN_PB = TEN_PB;
		
		String _TEN_KBH = "";
		if(TEN_KBH.trim().length()>0)
			_TEN_KBH = TEN_KBH;
		
		String _TEN_VV = "";
		if(TEN_VV.trim().length()>0)
			_TEN_VV = TEN_VV;
		
		String _TEN_DIABAN = "";
		if(TEN_DIABAN.trim().length()>0)
			_TEN_DIABAN = TEN_DIABAN;
		
		String _TEN_TINHTHANH = "";
		if(TEN_TINHTHANH.trim().length()>0)
			_TEN_TINHTHANH = TEN_TINHTHANH;
		
		String _TEN_BENHVIEN = "";
		if(TEN_BENHVIEN.trim().length()>0)
			_TEN_BENHVIEN = TEN_BENHVIEN; 
			
		String _TEN_SANPHAM = "";
		if(TEN_SANPHAM.trim().length()>0)
			_TEN_SANPHAM = TEN_SANPHAM;
		
		String _DIENGIAI_CT = "";
		if(DIENGIAI_CT.trim().length()>0)
			_DIENGIAI_CT = DIENGIAI_CT;
		
		String _chiPhiCo = "NULL";
		if(chiPhiCo.trim().length()>0)
			_chiPhiCo = chiPhiCo;
		

		String _chiPhiNo = "NULL";
		if(chiPhiNo.trim().length()>0)
			_chiPhiNo = chiPhiNo;
		
		String _nppPhatSinhNo = "NULL";
		if(nppPhatSinhNo.trim().length() >0)
			_nppPhatSinhNo = nppPhatSinhNo;
		
		String _nppPhatSinhCo = "NULL";
		if(nppPhatSinhCo.trim().length() >0)
			_nppPhatSinhCo = nppPhatSinhCo;
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP , " +
					"MAHOADON, MAUHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT,  MAFAST_DT, TEN_DT, TEN_PB, TEN_KBH, TEN_VV, TEN_DIABAN, TEN_TINHTHANH, TEN_BENHVIEN, TEN_SANPHAM, DIENGIAI_CT, KHOANMUCCHIPHI_FK,NPP_PHATSINH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP_CO+", " +
					" N'"+ _MAHOADON + "', N'"+_MAUHOADON+"' , N'"+_KYHIEU+"', N'"+_SOHOADON+"', N'"+_NGAYHOADON+"', N'"+_TENNCC+"', N'"+_MST+"', "+_TIENHANG+", "+_THUESUAT+", N'"+_MAFAST_DT+"', N'"+_TEN_DT+"', N'"+_TEN_PB+"', N'"+_TEN_KBH+"', N'"+_TEN_VV+"', N'"+_TEN_DIABAN+"', N'"+_TEN_TINHTHANH+"', N'"+_TEN_BENHVIEN+"', N'"+_TEN_SANPHAM+"', N'"+_DIENGIAI_CT+"',"+_chiPhiCo+","+_nppPhatSinhCo+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{		
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP," +
					"MAHOADON, MAUHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, MAFAST_DT, TEN_DT, TEN_PB, TEN_KBH, TEN_VV, TEN_DIABAN, TEN_TINHTHANH, TEN_BENHVIEN, TEN_SANPHAM, DIENGIAI_CT,KHOANMUCCHIPHI_FK,NPP_PHATSINH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP_NO+", " +
					" N'"+ _MAHOADON + "', N'"+_MAUHOADON+"' , N'"+_KYHIEU+"', N'"+_SOHOADON+"', N'"+_NGAYHOADON+"', N'"+_TENNCC+"', N'"+_MST+"', "+_TIENHANG+", "+_THUESUAT+", N'"+_MAFAST_DT+"', N'"+_TEN_DT+"', N'"+_TEN_PB+"', N'"+_TEN_KBH+"', N'"+_TEN_VV+"', N'"+_TEN_DIABAN+"', N'"+_TEN_TINHTHANH+"', N'"+_TEN_BENHVIEN+"', N'"+_TEN_SANPHAM+"', N'"+_DIENGIAI_CT+"', "+_chiPhiNo+","+_nppPhatSinhNo+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		} 
		
		return msg;
		
	}

	public String Update_TaiKhoan_Diengiai (Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc,String Diengiai)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		//GHI CO
		if(Float.parseFloat(_CO) != 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' " +
					"and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
						" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
						" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = " insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						" select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,DIENGIAI) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "',N'"+Diengiai+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) != 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,diengiai) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "',N'"+Diengiai+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
	}
	
	
	public String Update_TaiKhoan_Diengiai_NPP (Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc,String Diengiai,String isNpp_No,String isNpp_Co,String sohoadon,String machungtu)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		String _isNpp_No = "null";
		if(isNpp_No.trim().length() > 0)
			_isNpp_No = isNpp_No;
		
		String _isNpp_Co = "null";
		if(isNpp_Co.trim().length() > 0)
			_isNpp_Co = isNpp_Co;
		
		
		
		
		
		//GHI CO
		if(Float.parseFloat(_CO) != 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' " +
					"and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
						" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
						" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = " insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						" select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,DIENGIAI,ISNPP,sohoadon,machungtu) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "',N'"+Diengiai+"'," +_isNpp_Co+ " ,'" +sohoadon+ "','" + machungtu+ "')  ";
			
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) != 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,diengiai,ISNPP,sohoadon,machungtu) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "',N'"+Diengiai+"',"+_isNpp_No+",'"+sohoadon+"','"+machungtu+"') ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				System.out.println("aaaaaaaaaaaaaaa"+query);
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
	}
	
	//ham chot kho ct
	public String Update_KhoTT(String ngayyeucau, String ghichu, Idbutils db, String khott_fk, 
			String spId, String solo, String ngayhethan, String ngaynhapkho,
			String MAME, String MATHUNG, String vitri,  
			String MAPHIEU, String phieudt, String phieueo, 
			String MARQ, String HAMLUONG, String HAMAM, 
			String loaidoituong, String doituongId,
			double soluong, double booked, double available) {

		//THỐNG NHẤT LÀM TRÒN 3 CHỮ SỐ SAU SỐ LƯỢNG
		available = this.Round(available, 4);
		soluong = this.Round(soluong, 4);
		booked = this.Round(booked, 4);
		
		if(HAMAM.equals("0.0") )
			HAMAM = "0";
		if( HAMLUONG.equals("100.0") )
			HAMLUONG = "100";
		
		if( HAMAM.trim().length() <= 0 )
			HAMAM = "0";
		if( HAMLUONG.trim().length() <= 0 )
			HAMLUONG = "100";
		
		try
		{
			String query =  "  select sanpham_fk, available, booked,soluong, sp.ma + ' ' + sp.ten as ten   " +
							"  from ERP_KHOTT_SP_CHITIET kho " +
							"  inner join ERP_SANPHAM sp  on kho.sanpham_fk = sp.pk_seq " + 
							" where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and ngaynhapkho = '" + ngaynhapkho + "' ";
			
		 
			if( doituongId.trim().length() > 0  ){
				query += " and kho.loaidoituong = '" + loaidoituong + "' and kho.doituongId = '" + doituongId + "' ";
			}else{
				query +=  "  and kho.doituongId is null   ";
			}
			
			//if( MAME.trim().length() > 0 )
				query += " and isnull(kho.MAME, '') = '" + MAME + "' ";
			//if( HAMLUONG.trim().length() > 0 )
				query += " and isnull(kho.MATHUNG, '') = '" + MATHUNG + "' ";
		//	if( HAMAM.trim().length() > 0 )
				query += " and isnull(kho.MAPHIEU, '') = '" + MAPHIEU + "' ";
			
		//	if( MARQ.trim().length() > 0 )
				query += " and isnull(kho.MARQ, '') = '" + MARQ + "' ";
		//	if( HAMLUONG.trim().length() > 0 )
				query += " and isnull(kho.HAMLUONG, 100) = '" + HAMLUONG + "' ";
		//	if( HAMAM.trim().length() > 0 )
				query += " and isnull(kho.HAMAM, 0) = '" + HAMAM + "' ";
			
			if( vitri.trim().length() > 0 ){
				query += " and isnull(kho.bin_fk, 0 ) = " + vitri;
			}else{
				query += " and isnull(kho.bin_fk, 0 ) = 0";
			}
			//if( phieudt.trim().length() > 0 )
				query += " and isnull(kho.maphieudinhtinh, '') = '" + phieudt + "' ";
			//if( phieueo.trim().length() > 0 )
				query += " and isnull(kho.phieueo, '') = '" + phieueo + "' ";
				
			System.out.println("[UTILITY KHO : QUERY CHECK KHO]" + query);
			double available_ton=0;
//			double giaton = 0;
			double soluongton=0;
			double booked_ton=0;
			boolean daco = false;
			
			ResultSet rsCheck = db.get(query);
			 
				if( rsCheck.next() )
				{
					daco = true;
					soluongton = this.Round(rsCheck.getDouble("soluong"), 4);
					available_ton = this.Round(rsCheck.getDouble("available"), 4);
					booked_ton=rsCheck.getDouble("booked");
					
					//System.out.println("::: SO LUONG: " + soluongton + "  -- BOOKED: " + booked_ton + " -- AVAI TON: " + available_ton );
					
	
					if(available < 0 && available_ton < (-1)*available )
					{
						return "Số lượng tồn hiện tại trong kho của sản phẩm : " + rsCheck.getString("ten") + "  [" + available_ton + "], số lô [" + solo + "], ngày hết hạn [" + ngayhethan + "], ngày nhập kho [" + ngaynhapkho + "], không đủ để trừ kho, vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
					}
	
					if(soluong < 0 && soluongton < (-1) * soluong )
					{
						//System.out.println(":: SO LUONG: " + soluong + " -- SO LUONG TON: " + soluongton);
						return "Số lượng tồn trong kho của sản phẩm : " + rsCheck.getString("ten") + "  [" + soluongton + "] không đủ để trừ kho, vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
					}
	
					/*querylog = "insert into log_kho (nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
							   "select N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",1),round("+booked+",1),round(" + available + ",1) from nhapp_kho where  npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";*/
	
					String querylog =   " INSERT INTO log_erp_khott_sp_chitiet ( KHOTT_FK	,SANPHAM_FK,	NGAYHETHAN,	SOLO	,BIN_FK	,SOLUONG	,BOOKED, " +
					"	AVAILABLE	,NGAYSANXUAT,	  NGAYNHAPKHO	, 	 	MARQ,	HAMLUONG,	HAMAM	,LOAIDOITUONG	,DOITUONGID	,MAME	,MATHUNG, " +
					"	MAPHIEU,	MAPHIEUDINHTINH	,PHIEUEO    ,DIENGIAI	,SOLUONG_CN,	BOOKED_CN,	AVAILABLE_CN ) "+
					"   SELECT "+khott_fk+"	,"+spId+",	'"+ngayhethan+"',	'"+solo+"'	,"+vitri+"	,"+soluongton+"	,"+booked_ton+",	 " +
					"   "+available_ton+"	,'',	  '"+ngaynhapkho+"'	,'"+MARQ+"',	'"+HAMLUONG+"',	'"+HAMAM+"'	,'"+loaidoituong+"'	,'"+doituongId+"'" +
					"	,'"+MAME+"'	,'"+MATHUNG+"',	'"+MAPHIEU+"',	'"+phieudt+"'	,'"+phieueo+"' ,N'"+ghichu+"',"+soluong+","+booked+","+available+"";

					if(!db.update(querylog)){
						return  " Không thể cập nhật log " + query;
					}
					
					
					query = " Update ERP_KHOTT_SP_CHITIET set booked = CAST( round(ISNULL(booked,0), 4) as   numeric(18,4) ) + round(" + booked + ", 4), soluong = CAST( round(ISNULL(soluong,0), 4) as   numeric(18,4) ) + round(" + soluong + ", 4), " +
							" 	AVAILABLE = CAST( round(ISNULL(AVAILABLE,0), 4) as numeric(18,4) ) + round(" + available + ", 4)  "+
							"  where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and ngaynhapkho = '" + ngaynhapkho + "' ";
					

					if( doituongId.trim().length() > 0  ){
						query += " and  loaidoituong = '" + loaidoituong + "' and  doituongId = '" + doituongId + "' ";
					}else{
						query +=  "  and  doituongId is null   ";
					}
					
					//if( MAME.trim().length() > 0 )
						query += " and isnull( MAME, '') = '" + MAME + "' ";
					//if( HAMLUONG.trim().length() > 0 )
						query += " and isnull( MATHUNG, '') = '" + MATHUNG + "' ";
				//	if( HAMAM.trim().length() > 0 )
						query += " and isnull( MAPHIEU, '') = '" + MAPHIEU + "' ";
					
				//	if( MARQ.trim().length() > 0 )
						query += " and isnull( MARQ, '') = '" + MARQ + "' ";
				//	if( HAMLUONG.trim().length() > 0 )
						query += " and isnull( HAMLUONG, 100) = '" + HAMLUONG + "' ";
				//	if( HAMAM.trim().length() > 0 )
						query += " and isnull( HAMAM, 0) = '" + HAMAM + "' ";
					
					if( vitri.trim().length() > 0 ){
						query += " and isnull( bin_fk, 0 ) = " + vitri;
					}else{
						query += " and isnull( bin_fk, 0 ) = 0";
					}
					//if( phieudt.trim().length() > 0 )
						query += " and isnull( maphieudinhtinh, '') = '" + phieudt + "' ";
					//if( phieueo.trim().length() > 0 )
						query += " and isnull( phieueo, '') = '" + phieueo + "' ";
						
					
					System.out.println("::: 1.CAP NHAT KHO CT: " + query);
					int resultInt = db.updateReturnInt(query);
					if(resultInt != 1)
					{
						//System.out.println("::: SO DONG: " + resultInt);
						return  " --- Không thể cập nhật ERP_KHOTT_SP_CHITIET  ( " + resultInt + " ) " + query;
					}
					
					query = " Update ERP_KHOTT_SANPHAM set booked =  CAST( round(ISNULL(booked,0), 4) as numeric(18,4) ) + round(" + booked + ", 4), soluong = CAST( round(ISNULL(soluong,0), 4) as numeric(18,4) ) + round(" + soluong + ", 4), " +
							" AVAILABLE = CAST( round(ISNULL(AVAILABLE,0), 4) as   numeric(18,4) ) + round(" + available + ", 4)  "+
							" where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + "  ";
					
					if( doituongId.trim().length() > 0  ){
						query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
					}else{
						query+=  " and doituongId is null";
					}
					  
					resultInt = db.updateReturnInt(query);
					if(resultInt != 1)
					{
						return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
	
					}
				}
				rsCheck.close();
				
				if( !daco )
				{
					if( soluong < 0 || available < 0 || booked < 0 )
					{
						return "Số lượng tồn trong kho không hợp lệ. Vui lòng liên hệ Admin để xử lý " ;
					}
				}
			 
				if( !daco )  //Trường hợp trong kho chi tiết chưa có SP NÀY
				{
				//Trường hợp kho loại ký gửi, gia công, có thể bên nhận chưa có sản phẩm này
				//if( doituongId.trim().length() > 0 )
				//{
					query = "  select count(*) as sodong  " +
							"  from ERP_KHOTT_SANPHAM kho " +
							" where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + "  ";
					if( doituongId.trim().length() > 0  ){
						query += " and kho.loaidoituong = '" + loaidoituong + "' and kho.doituongId = '" + doituongId + "' ";
					}else{
						query += "   and kho.doituongId  is null ";
					}
					
					rsCheck = db.get(query);
					int count = 0;
				 
					rsCheck.next();
					count = rsCheck.getInt("sodong");
				 
					if( count <= 0 )
					{
						query = "insert ERP_KHOTT_SANPHAM( KHOTT_FK, SANPHAM_FK, soluong, booked, available ";
						if( doituongId.trim().length() > 0  )
							query += " ,loaidoituong, doituongId ";
						query += " )";
						
						query += " values( '" + khott_fk + "', " + spId + ", " + soluong + ", " + booked + ", " + available + "";
						if( doituongId.trim().length() > 0  )
							query += " ,'" + loaidoituong + "', '" + doituongId + "' ";
						query += " ) ";
						
						//System.out.println("::: INSERT KHO TONG: " + query);
						int resultInt = db.updateReturnInt(query);
						if(resultInt != 1)
						{
							return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
						}
					}else{
						query = " Update ERP_KHOTT_SANPHAM set booked = CAST( round(ISNULL(booked,0), 4) as   numeric(18,4) )  + round(" + booked + ", 4), soluong =  CAST( round(ISNULL(soluong,0), 4) as   numeric(18,4) ) + round(" + soluong + ", 4), " +
						" 	AVAILABLE = CAST( round(ISNULL(AVAILABLE,0), 4) as   numeric(18,4) ) + round(" + available + ", 4)  "+
						"  where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + "  ";
						if( doituongId.trim().length() > 0  ){
							query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
						}else{
							query += "   and doituongId  is null  ";
						}
						//System.out.println("::: CAP NHAT KHO TONG: " + query);
						
						
						 int resultInt = db.updateReturnInt(query);
						if(resultInt != 1)
						{
							return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
		
						}
					}
					
				//}
					
					String querylog =   " INSERT INTO log_erp_khott_sp_chitiet ( KHOTT_FK	,SANPHAM_FK,	NGAYHETHAN,	SOLO	,BIN_FK	,SOLUONG	,BOOKED, " +
					"	AVAILABLE	,NGAYSANXUAT,	  NGAYNHAPKHO	, 	 	MARQ,	HAMLUONG,	HAMAM	,LOAIDOITUONG	,DOITUONGID	,MAME	,MATHUNG, " +
					"	MAPHIEU,	MAPHIEUDINHTINH	,PHIEUEO    ,DIENGIAI	,SOLUONG_CN,	BOOKED_CN,	AVAILABLE_CN ) "+
					"   SELECT "+khott_fk+"	,"+spId+",	'"+ngayhethan+"',	'"+solo+"'	,"+vitri+"	,"+soluongton+"	,"+booked_ton+",	 " +
					"   "+available_ton+"	,'',	  '"+ngaynhapkho+"'	,'"+MARQ+"',	'"+HAMLUONG+"',	'"+HAMAM+"'	,'"+loaidoituong+"'	,'"+doituongId+"'" +
					"	,'"+MAME+"'	,'"+MATHUNG+"',	'"+MAPHIEU+"',	'"+phieudt+"'	,'"+phieueo+"' ,N'"+ghichu+"',"+soluong+","+booked+","+available+"";

					if(!db.update(querylog)){
						return  " Không thể cập nhật log " + query;
					}
					
				
				query = "insert ERP_KHOTT_SP_CHITIET( KHOTT_FK, SANPHAM_FK, SOLO, NGAYHETHAN, NGAYNHAPKHO, soluong, booked, available ";
				if( doituongId.trim().length() > 0  )
					query += " ,loaidoituong, doituongId ";
				
				if( MAME.trim().length() > 0 )
					query += " ,MAME ";
				if( MATHUNG.trim().length() > 0 )
					query += " ,MATHUNG ";
				if( MAPHIEU.trim().length() > 0 )
					query += " ,MAPHIEU ";
				
				if( MARQ.trim().length() > 0 )
					query += " ,MARQ ";
				if( HAMLUONG.trim().length() > 0 )
					query += " ,HAMLUONG ";
				if( HAMAM.trim().length() > 0 )
					query += " ,HAMAM ";
				
				if( vitri.trim().length() > 0 )
					query += " ,BIN_FK, KHUVUCKHO_FK ";
				if( phieudt.trim().length() > 0 )
					query += " ,maphieudinhtinh ";
				if( phieueo.trim().length() > 0 )
					query += " ,phieueo ";
				
				query += " )";
				
				query += " select '" + khott_fk + "', " + spId + ", '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho + "', " + soluong + ", " + booked + ", " + available + "";
				if( doituongId.trim().length() > 0  )
					query += " ,'" + loaidoituong + "', '" + doituongId + "' ";
				
				if( MAME.trim().length() > 0 )
					query += " ,'" + MAME + "' ";
				if( MATHUNG.trim().length() > 0 )
					query += " , '" + MATHUNG + "' ";
				if( MAPHIEU.trim().length() > 0 )
					query += " , '" + MAPHIEU + "' ";
				
				if( MARQ.trim().length() > 0 )
					query += " ,'" + MARQ + "' ";
				if( HAMLUONG.trim().length() > 0 )
					query += " , '" + HAMLUONG + "' ";
				if( HAMAM.trim().length() > 0 )
					query += " , '" + HAMAM + "' ";
				
				if( vitri.trim().length() > 0 )
					query += " , " + vitri + ", ( select KHUVUC_FK from ERP_BIN where PK_SEQ = " + vitri + " ) ";
				if( phieudt.trim().length() > 0 )
					query += " , '" + phieudt + "' ";
				if( phieueo.trim().length() > 0 )
					query += " , '" + phieueo + "' ";
				
				query += "  ";
				//System.out.println("::: INSERT KHO CT: " + query);
				int resultInt = db.updateReturnInt(query);
				if(resultInt != 1)
				{
					//System.out.println("::: SO DONG: " + resultInt);
					return  " Không thể thêm mới ERP_KHOTT_SP_CHITIET " + query;
				}
			}
			
			
			// kiểm tra sp lô,ngày nhập kho, có bị âm  kho ko?
			
			if(soluong< 0){
				String sanpham_fk="";
				String cuoiky="";
				String kho_fk="";
			query=  " SELECT * FROM [UFN_NXT_HO_FULL_THEO_SP]('','',"+khott_fk+","+spId+","+vitri+",'"+solo+"','"+ngaynhapkho+"','"+ngayhethan+"','"+MAME+"','"+MATHUNG+"','"+MAPHIEU+"','"+phieudt+"','"+phieueo+"','"+MARQ+"','"+HAMAM+"','"+HAMLUONG+"')  " +
					" WHERE ROUND(CUOIKY,3) <0";
					System.out.println("Du lieu check 1 : "+query);
					ResultSet rs=db.get(query);
					int sodong=rs.getRow();
					System.out.println("so dong  "+ sodong);
					if(rs.next()){
						sanpham_fk=rs.getString("sanpham_fk");
						cuoiky=rs.getString("cuoiky");
						kho_fk=rs.getString("kho_fk");
						System.out.println("san pham "+ sanpham_fk);
						return "Vui lòng thử lại nghiệp vụ,nếu không được vui lòng báo Admin để được trợ giúp. Tổng xuất nhập tồn của sản phẩm : "+spId+" | số lô :"+solo+"  bạn đang cập nhật không hợp lệ";
					}
					rs.close();
			}
			
			
		}
		catch(Exception er)
		{
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Update_KhoTT : "+er.getMessage();
		}
		
		return "";
	}
//2
	
	//ham chot kho ct
		public String Update_KhoTT_MOI(String ngayyeucau, String ghichu, Idbutils db, String khott_fk, 
				String spId, String solo, String ngayhethan, String ngaynhapkho,
				String MAME, String MATHUNG, String vitri,  
				String MAPHIEU, String phieudt, String phieueo, 
				String MARQ, String HAMLUONG, String HAMAM, 
				String loaidoituong, String doituongId,
				double soluong, double booked, double available, String nsx_fk) {

			//THỐNG NHẤT LÀM TRÒN 3 CHỮ SỐ SAU SỐ LƯỢNG
			available = this.Round(available, 4);
			soluong = this.Round(soluong, 4);
			booked = this.Round(booked, 4);
			
			if(HAMAM.equals("0.0") )
				HAMAM = "0";
			if( HAMLUONG.equals("100.0") )
				HAMLUONG = "100";
			
			if( HAMAM.trim().length() <= 0 )
				HAMAM = "0";
			if( HAMLUONG.trim().length() <= 0 )
				HAMLUONG = "100";
			
			try
			{
				String query =  "  select sanpham_fk, available, booked,soluong, sp.ma + ' ' + sp.ten as ten   " +
								"  from ERP_KHOTT_SP_CHITIET kho " +
								"  inner join ERP_SANPHAM sp  on kho.sanpham_fk = sp.pk_seq " + 
								" where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and ngaynhapkho = '" + ngaynhapkho + "' ";
				
			 
				if( doituongId.trim().length() > 0  ){
					query += " and kho.loaidoituong = '" + loaidoituong + "' and kho.doituongId = '" + doituongId + "' ";
				}else{
					query +=  "  and kho.doituongId is null   ";
				}
				
				//if( MAME.trim().length() > 0 )
					query += " and isnull(kho.MAME, '') = '" + MAME + "' ";
				//if( HAMLUONG.trim().length() > 0 )
					query += " and isnull(kho.MATHUNG, '') = '" + MATHUNG + "' ";
			//	if( HAMAM.trim().length() > 0 )
					query += " and isnull(kho.MAPHIEU, '') = '" + MAPHIEU + "' ";
				
			//	if( MARQ.trim().length() > 0 )
					query += " and isnull(kho.MARQ, '') = '" + MARQ + "' ";
			//	if( HAMLUONG.trim().length() > 0 )
					query += " and isnull(kho.HAMLUONG, 100) = '" + HAMLUONG + "' ";
			//	if( HAMAM.trim().length() > 0 )
					query += " and isnull(kho.HAMAM, 0) = '" + HAMAM + "' ";
				
				if( vitri.trim().length() > 0 ){
					query += " and isnull(kho.bin_fk, 0 ) = " + vitri;
				}else{
					query += " and isnull(kho.bin_fk, 0 ) = 0";
				}
				//if( phieudt.trim().length() > 0 )
					query += " and isnull(kho.maphieudinhtinh, '') = '" + phieudt + "' ";
				//if( phieueo.trim().length() > 0 )
					query += " and isnull(kho.phieueo, '') = '" + phieueo + "' ";
					
				if( nsx_fk.trim().length() > 0 && !nsx_fk.equals("0") ){
					query += " and isnull(kho.NSX_FK, 0 ) = " + nsx_fk;
				}else{
					query += " and isnull(kho.NSX_FK, 0 ) = 0";
				}
					
				System.out.println("[UTILITY KHO : QUERY CHECK KHO]" + query);
				double available_ton=0;
//				double giaton = 0;
				double soluongton=0;
				double booked_ton=0;
				boolean daco = false;
				
				ResultSet rsCheck = db.get(query);
				 
					if( rsCheck.next() )
					{
						daco = true;
						soluongton = this.Round(rsCheck.getDouble("soluong"), 4);
						available_ton = this.Round(rsCheck.getDouble("available"), 4);
						booked_ton=rsCheck.getDouble("booked");
						
						//System.out.println("::: SO LUONG: " + soluongton + "  -- BOOKED: " + booked_ton + " -- AVAI TON: " + available_ton );
						
		
						if(available < 0 && available_ton < (-1)*available )
						{
							return "Số lượng tồn hiện tại trong kho của sản phẩm : " + rsCheck.getString("ten") + "  [" + available_ton + "], số lô [" + solo + "], ngày hết hạn [" + ngayhethan + "], ngày nhập kho [" + ngaynhapkho + "], không đủ để trừ kho, vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
						}
		
						if(soluong < 0 && soluongton < (-1) * soluong )
						{
							//System.out.println(":: SO LUONG: " + soluong + " -- SO LUONG TON: " + soluongton);
							return "Số lượng tồn trong kho của sản phẩm : " + rsCheck.getString("ten") + "  [" + soluongton + "] không đủ để trừ kho, vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
						}
		
						/*querylog = "insert into log_kho (nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
								   "select N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",1),round("+booked+",1),round(" + available + ",1) from nhapp_kho where  npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";*/
		
						String querylog =   " INSERT INTO log_erp_khott_sp_chitiet ( KHOTT_FK	,SANPHAM_FK,	NGAYHETHAN,	SOLO	,BIN_FK	,SOLUONG	,BOOKED, " +
						"	AVAILABLE	,NGAYSANXUAT,	  NGAYNHAPKHO	, 	 	MARQ,	HAMLUONG,	HAMAM	,LOAIDOITUONG	,DOITUONGID	,MAME	,MATHUNG, " +
						"	MAPHIEU,	MAPHIEUDINHTINH	,PHIEUEO    ,DIENGIAI	,SOLUONG_CN,	BOOKED_CN,	AVAILABLE_CN, nsx_fk ) "+
						"   SELECT "+khott_fk+"	,"+spId+",	'"+ngayhethan+"',	'"+solo+"'	,"+vitri+"	,"+soluongton+"	,"+booked_ton+",	 " +
						"   "+available_ton+"	,'',	  '"+ngaynhapkho+"'	,'"+MARQ+"',	'"+HAMLUONG+"',	'"+HAMAM+"'	,'"+loaidoituong+"'	,'"+doituongId+"'" +
						"	,'"+MAME+"'	,'"+MATHUNG+"',	'"+MAPHIEU+"',	'"+phieudt+"'	,'"+phieueo+"' ,N'"+ghichu+"',"+soluong+","+booked+","+available+","+(nsx_fk.trim().length() > 0  ?nsx_fk.trim():"NULL")+" ";

						if(!db.update(querylog)){
							return  " Không thể cập nhật log " + query;
						}
						
						
						query = " Update ERP_KHOTT_SP_CHITIET set booked = CAST( round(ISNULL(booked,0), 4) as   numeric(18,4) ) + round(" + booked + ", 4), soluong = CAST( round(ISNULL(soluong,0), 4) as   numeric(18,4) ) + round(" + soluong + ", 4), " +
								" 	AVAILABLE = CAST( round(ISNULL(AVAILABLE,0), 4) as numeric(18,4) ) + round(" + available + ", 4)  "+
								"  where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and ngaynhapkho = '" + ngaynhapkho + "' ";
						

						if( doituongId.trim().length() > 0  ){
							query += " and  loaidoituong = '" + loaidoituong + "' and  doituongId = '" + doituongId + "' ";
						}else{
							query +=  "  and  doituongId is null   ";
						}
						
						//if( MAME.trim().length() > 0 )
							query += " and isnull( MAME, '') = '" + MAME + "' ";
						//if( HAMLUONG.trim().length() > 0 )
							query += " and isnull( MATHUNG, '') = '" + MATHUNG + "' ";
					//	if( HAMAM.trim().length() > 0 )
							query += " and isnull( MAPHIEU, '') = '" + MAPHIEU + "' ";
						
					//	if( MARQ.trim().length() > 0 )
							query += " and isnull( MARQ, '') = '" + MARQ + "' ";
					//	if( HAMLUONG.trim().length() > 0 )
							query += " and isnull( HAMLUONG, 100) = '" + HAMLUONG + "' ";
					//	if( HAMAM.trim().length() > 0 )
							query += " and isnull( HAMAM, 0) = '" + HAMAM + "' ";
						
						if( vitri.trim().length() > 0 ){
							query += " and isnull( bin_fk, 0 ) = " + vitri;
						}else{
							query += " and isnull( bin_fk, 0 ) = 0";
						}
						//if( phieudt.trim().length() > 0 )
							query += " and isnull( maphieudinhtinh, '') = '" + phieudt + "' ";
						//if( phieueo.trim().length() > 0 )
							query += " and isnull( phieueo, '') = '" + phieueo + "' ";
						
						if(nsx_fk.trim().length() > 0 && !nsx_fk.equals("0")){
							query += " and isnull(nsx_fk, 0 ) = " + nsx_fk;
						}else{
							query += " and isnull(nsx_fk, 0 ) = 0";
						}
							
						
						System.out.println("::: 1.CAP NHAT KHO CT: " + query);
						int resultInt = db.updateReturnInt(query);
						if(resultInt != 1)
						{
							//System.out.println("::: SO DONG: " + resultInt);
							return  " --- Không thể cập nhật ERP_KHOTT_SP_CHITIET  ( " + resultInt + " ) " + query;
						}
						
						query = " Update ERP_KHOTT_SANPHAM set booked =  CAST( round(ISNULL(booked,0), 4) as numeric(18,4) ) + round(" + booked + ", 4), soluong = CAST( round(ISNULL(soluong,0), 4) as numeric(18,4) ) + round(" + soluong + ", 4), " +
								" AVAILABLE = CAST( round(ISNULL(AVAILABLE,0), 4) as   numeric(18,4) ) + round(" + available + ", 4)  "+
								" where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + "  ";
						
						if( doituongId.trim().length() > 0  ){
							query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
						}else{
							query+=  " and doituongId is null";
						}
						  
						resultInt = db.updateReturnInt(query);
						if(resultInt != 1)
						{
							return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
		
						}
					}
					rsCheck.close();
					
					if( !daco )
					{
						if( soluong < 0 || available < 0 || booked < 0 )
						{
							return "Số lượng tồn trong kho không hợp lệ. Vui lòng liên hệ Admin để xử lý " ;
						}
					}
				 
					if( !daco )  //Trường hợp trong kho chi tiết chưa có SP NÀY
					{
					//Trường hợp kho loại ký gửi, gia công, có thể bên nhận chưa có sản phẩm này
					//if( doituongId.trim().length() > 0 )
					//{
						query = "  select count(*) as sodong  " +
								"  from ERP_KHOTT_SANPHAM kho " +
								" where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + "  ";
						if( doituongId.trim().length() > 0  ){
							query += " and kho.loaidoituong = '" + loaidoituong + "' and kho.doituongId = '" + doituongId + "' ";
						}else{
							query += "   and kho.doituongId  is null ";
						}
						
						rsCheck = db.get(query);
						int count = 0;
					 
						rsCheck.next();
						count = rsCheck.getInt("sodong");
					 
						if( count <= 0 )
						{
							query = "insert ERP_KHOTT_SANPHAM( KHOTT_FK, SANPHAM_FK, soluong, booked, available ";
							if( doituongId.trim().length() > 0  )
								query += " ,loaidoituong, doituongId ";
							query += " )";
							
							query += " values( '" + khott_fk + "', " + spId + ", " + soluong + ", " + booked + ", " + available + "";
							if( doituongId.trim().length() > 0  )
								query += " ,'" + loaidoituong + "', '" + doituongId + "' ";
							query += " ) ";
							
							//System.out.println("::: INSERT KHO TONG: " + query);
							int resultInt = db.updateReturnInt(query);
							if(resultInt != 1)
							{
								return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
							}
						}else{
							query = " Update ERP_KHOTT_SANPHAM set booked = CAST( round(ISNULL(booked,0), 4) as   numeric(18,4) )  + round(" + booked + ", 4), soluong =  CAST( round(ISNULL(soluong,0), 4) as   numeric(18,4) ) + round(" + soluong + ", 4), " +
							" 	AVAILABLE = CAST( round(ISNULL(AVAILABLE,0), 4) as   numeric(18,4) ) + round(" + available + ", 4)  "+
							"  where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + "  ";
							if( doituongId.trim().length() > 0  ){
								query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
							}else{
								query += "   and doituongId  is null  ";
							}
							//System.out.println("::: CAP NHAT KHO TONG: " + query);
							
							
							 int resultInt = db.updateReturnInt(query);
							if(resultInt != 1)
							{
								return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
			
							}
						}
						
					//}
						
						String querylog =   " INSERT INTO log_erp_khott_sp_chitiet ( KHOTT_FK	,SANPHAM_FK,	NGAYHETHAN,	SOLO	,BIN_FK	,SOLUONG	,BOOKED, " +
						"	AVAILABLE	,NGAYSANXUAT,	  NGAYNHAPKHO	, 	 	MARQ,	HAMLUONG,	HAMAM	,LOAIDOITUONG	,DOITUONGID	,MAME	,MATHUNG, " +
						"	MAPHIEU,	MAPHIEUDINHTINH	,PHIEUEO    ,DIENGIAI	,SOLUONG_CN,	BOOKED_CN,	AVAILABLE_CN, NSX_FK ) "+
						"   SELECT "+khott_fk+"	,"+spId+",	'"+ngayhethan+"',	'"+solo+"'	,"+vitri+"	,"+soluongton+"	,"+booked_ton+",	 " +
						"   "+available_ton+"	,'',	  '"+ngaynhapkho+"'	,'"+MARQ+"',	'"+HAMLUONG+"',	'"+HAMAM+"'	,'"+loaidoituong+"'	,'"+doituongId+"'" +
						"	,'"+MAME+"'	,'"+MATHUNG+"',	'"+MAPHIEU+"',	'"+phieudt+"'	,'"+phieueo+"' ,N'"+ghichu+"',"+soluong+","+booked+","+available+","+(nsx_fk.trim().length() > 0  ?nsx_fk.trim():"NULL")+"";

						if(!db.update(querylog)){
							return  " Không thể cập nhật log " + query;
						}
						
					
					query = "insert ERP_KHOTT_SP_CHITIET( KHOTT_FK, SANPHAM_FK, SOLO, NGAYHETHAN, NGAYNHAPKHO, soluong, booked, available ";
					if( doituongId.trim().length() > 0  )
						query += " ,loaidoituong, doituongId ";
					
					if( MAME.trim().length() > 0 )
						query += " ,MAME ";
					if( MATHUNG.trim().length() > 0 )
						query += " ,MATHUNG ";
					if( MAPHIEU.trim().length() > 0 )
						query += " ,MAPHIEU ";
					
					if( MARQ.trim().length() > 0 )
						query += " ,MARQ ";
					if( HAMLUONG.trim().length() > 0 )
						query += " ,HAMLUONG ";
					if( HAMAM.trim().length() > 0 )
						query += " ,HAMAM ";
					
					if( vitri.trim().length() > 0 )
						query += " ,BIN_FK, KHUVUCKHO_FK ";
					if( phieudt.trim().length() > 0 )
						query += " ,maphieudinhtinh ";
					if( phieueo.trim().length() > 0 )
						query += " ,phieueo ";
					if( nsx_fk.trim().length() > 0 && !nsx_fk.equals("0") )
						query += " ,NSX_FK ";
					
					query += " )";
					
					query += " select '" + khott_fk + "', " + spId + ", '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho + "', " + soluong + ", " + booked + ", " + available + "";
					if( doituongId.trim().length() > 0  )
						query += " ,'" + loaidoituong + "', '" + doituongId + "' ";
					
					if( MAME.trim().length() > 0 )
						query += " ,'" + MAME + "' ";
					if( MATHUNG.trim().length() > 0 )
						query += " , '" + MATHUNG + "' ";
					if( MAPHIEU.trim().length() > 0 )
						query += " , '" + MAPHIEU + "' ";
					
					if( MARQ.trim().length() > 0 )
						query += " ,'" + MARQ + "' ";
					if( HAMLUONG.trim().length() > 0 )
						query += " , '" + HAMLUONG + "' ";
					if( HAMAM.trim().length() > 0 )
						query += " , '" + HAMAM + "' ";
					
					if( vitri.trim().length() > 0 )
						query += " , " + vitri + ", ( select KHUVUC_FK from ERP_BIN where PK_SEQ = " + vitri + " ) ";
					if( phieudt.trim().length() > 0 )
						query += " , '" + phieudt + "' ";
					if( phieueo.trim().length() > 0 )
						query += " , '" + phieueo + "' ";
					if( nsx_fk.trim().length() > 0 && !nsx_fk.equals("0") )
						query += " , '" + nsx_fk + "' ";
					
					query += "  ";
					//System.out.println("::: INSERT KHO CT: " + query);
					int resultInt = db.updateReturnInt(query);
					if(resultInt != 1)
					{
						//System.out.println("::: SO DONG: " + resultInt);
						return  " Không thể thêm mới ERP_KHOTT_SP_CHITIET " + query;
					}
				}
				
				
				// kiểm tra sp lô,ngày nhập kho, có bị âm  kho ko?
				
				if(soluong< 0){
					String sanpham_fk="";
					String cuoiky="";
					String kho_fk="";
//				query=  " SELECT * FROM [UFN_NXT_HO_FULL_THEO_SP_NSX]('','',"+khott_fk+","+spId+","+vitri+",'"+solo+"','"+ngaynhapkho+"','"+ngayhethan+"','"+MAME+"','"+MATHUNG+"','"+MAPHIEU+"','"+phieudt+"','"+phieueo+"','"+MARQ+"','"+HAMAM+"','"+HAMLUONG+"',"+(nsx_fk.trim().length() > 0 && !nsx_fk.equals("0")?nsx_fk:"0")+")  " +
//						" WHERE ROUND(CUOIKY,3) <0";
//						System.out.println("Du lieu check 1 : "+query);
//						ResultSet rs=db.get(query);
//						int sodong=rs.getRow();
//						System.out.println("so dong  "+ sodong);
//						if(rs.next()){
//							sanpham_fk=rs.getString("sanpham_fk");
//							cuoiky=rs.getString("cuoiky");
//							kho_fk=rs.getString("kho_fk");
//							System.out.println("san pham "+ sanpham_fk);
//							return "Vui lòng thử lại nghiệp vụ,nếu không được vui lòng báo Admin để được trợ giúp. Tổng xuất nhập tồn của sản phẩm : "+spId+" | số lô :"+solo+"  bạn đang cập nhật không hợp lệ";
//						}
//						rs.close();
				}
				
				
			}
			catch(Exception er)
			{
				er.printStackTrace();
				return  "không thể thực hiện cập nhật kho  Util.Update_KhoTT_MOI: "+er.getMessage();
			}
			
			return "";
		}


	//Giong V2_3 nhung THÊM QUAY_FK
		public String Update_TaiKhoan_FULL_v2_4 (geso.traphaco.center.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, 
				String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, String DOITUONG_NO, String MADOITUONG_NO, 
				String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, 
				String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, 
				String masp, String tensp, String donvi, String kbh_fk, String kho_fk, String Solo, String Ngayhethan, String tienhang, String loaiHD,String quay_fk)
		{
			String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
			if (msg.trim().length() > 0)
			{
				msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
				return msg;
			}
			
			String query;
			
			String _ndnhapxuat_fk = "null";
			if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
				_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
			
			String _sochungtu = "null";
			if(sochungtu.trim().length() > 0)
				_sochungtu = sochungtu;
			
			String _soluong = "null";
			if(SOLUONG.trim().length() > 0)
				_soluong = SOLUONG.trim();
			
			String _dongia = "null";
			if(DONGIA.trim().length() > 0)
				_dongia = DONGIA.trim();
			
			String _thanhtienViet = "null";
			if(TONGGIATRI.trim().length() > 0)
				_thanhtienViet = TONGGIATRI.trim();
			
			String _dongiaNT = "null";
			if(DONGIANT.trim().length() > 0)
				_dongiaNT = DONGIANT.trim();
			
			String _thanhtienNT = "null";
			if(TONGGIATRINT.trim().length() > 0)
				_thanhtienNT = TONGGIATRINT.trim();
					
			String _NO = "0";
			if(NO.trim().length() > 0)
				_NO = NO;
			
			String _CO = "0";
			if(CO.trim().length() > 0)
				_CO = CO;
			
			String _VAT = "0";
			if(VAT.trim().length() > 0)
				_VAT = VAT;
			
			String _TIENHANG = "0";
			if(tienhang.trim().length() > 0)
				_TIENHANG = tienhang;
			
			String _DIENGIAI = "";
			if(DienGiai.trim().length()>0)
				_DIENGIAI = DienGiai;
			
			String _MACHUNGTU = "";
			if(MaChungTu.trim().length()>0)
				_MACHUNGTU = MaChungTu;
			
			String _ISNPP = "null";
			if(isNPP.trim().length()>0)
				_ISNPP = isNPP;
			
			String _MASP = "null";
			if(masp.trim().length()>0)
				_MASP = masp;
			
			String _TENSP = "null";
			if(tensp.trim().length()>0)
				_TENSP = tensp;
			
			String _DONVI = "null";
			if(donvi.trim().length()>0)
				_DONVI = donvi;
			
			String _KBH = "null";
			if(kbh_fk.trim().length()>0)
				_KBH = kbh_fk;
			
			String _KHO = "null";
			if(kho_fk.trim().length()>0)
				_KHO = kho_fk;
			
			String _SOLO = "null";
			if(Solo.trim().length()>0)
				_SOLO = Solo;
			
			String _NGAYHETHAN = "null";
			if(Ngayhethan.trim().length()>0)
				_NGAYHETHAN = Ngayhethan;
			
			String _QUAY_FK = "null";
			if(quay_fk.trim().length() >0)
				_QUAY_FK = quay_fk;
			
			//String kho_fk, String Solo, String Ngayhethan
			
			//GHI CO
			/*if(Float.parseFloat(_CO) != 0) */
			{
				//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
						"DOITUONG, MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI, " +
						"MACHUNGTU, ISNPP, MAHANG, TENHANG, DONVI, KBH_FK, SOLO, NGAYHETHAN, KHO_FK, TIENHANG, LOAIHD,quay_fk ) " +
						"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " +
						"" + _ndnhapxuat_fk + ", '0', " + _CO + ", N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " +
						"" + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", " +
						"N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', " +
						""+_KBH+", '"+_SOLO+"', '"+_NGAYHETHAN+"', "+_KHO+", "+_TIENHANG+", '" + loaiHD + "',"+_QUAY_FK+") ";
				
				System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
				if(!db.update(query))
				{
					msg = "3.Không thể cập nhật tài khoản kế toán " + query;
					return msg;
				}
				
			}
			
			//GHI NO
			/*if(Float.parseFloat(_NO) != 0) */
			{		
				//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
						"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, " +
						"DIENGIAI , MACHUNGTU, ISNPP , MAHANG, TENHANG, DONVI, KBH_FK , SOLO, NGAYHETHAN, KHO_FK, TIENHANG, LOAIHD,quay_fk ) " +
						"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
						" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " +
						"" + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", " +
						"N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+", '"+_SOLO+"', " +
						"'"+_NGAYHETHAN+"', "+_KHO+", " + _TIENHANG + ", '" + loaiHD + "',"+_QUAY_FK+" ) ";
				
				System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
				if(!db.update(query))
				{
					msg = "3.Không thể cập nhật tài khoản kế toán " + query;
					return msg;
				}
				
			}
			
			return msg;
			
		}
	

	//Ban full giong ben ERP
	public String Update_KhoTT(String ngayyeucau, String ghichu, Idbutils db, String khott_fk, 
			String spId, String solo, String ngayhethan, String ngaynhapkho,
			String MAME, String MATHUNG, String vitri,  
			String MAPHIEU, String phieudt, String phieueo, 
			String MARQ, String HAMLUONG, String HAMAM, 
			String loaidoituong, String doituongId,
			double soluong, double booked, double available,
			String loaichungtu, String chungtuId, String transactionId ) {

		//THỐNG NHẤT LÀM TRÒN 3 CHỮ SỐ SAU SỐ LƯỢNG
		
		if( HAMAM.equals("0.0") )
			HAMAM = "0";
		if( HAMLUONG.equals("100.0") )
			HAMLUONG = "100";

		try
		{
			/*String query =  " Update ERP_KHOTT_SP_CHITIET set booked = round(isnull(booked,0), 3) + round(" + booked + ", 3), soluong = round(ISNULL(soluong,0), 3) + round(" + soluong + ", 3), " +
							" 	AVAILABLE = round(ISNULL(AVAILABLE,0), 3) + round(" + available + ", 3)  "+
							"  where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and ngaynhapkho = '" + ngaynhapkho + "' ";

			if( doituongId.trim().length() > 0  )
				query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";

			if( MAME.trim().length() > 0 )
				query += " and isnull(MAME, '') = '" + MAME + "' ";
			if( HAMLUONG.trim().length() > 0 )
				query += " and isnull(MATHUNG, '') = '" + MATHUNG + "' ";
			if( HAMAM.trim().length() > 0 )
				query += " and isnull(MAPHIEU, '') = '" + MAPHIEU + "' ";

			if( MARQ.trim().length() > 0 )
				query += " and isnull(MARQ, '') = '" + MARQ + "' ";
			if( HAMLUONG.trim().length() > 0 )
				query += " and isnull(HAMLUONG, 100) = '" + HAMLUONG + "' ";
			if( HAMAM.trim().length() > 0 )
				query += " and isnull(HAMAM, 0) = '" + HAMAM + "' ";

			if( vitri.trim().length() > 0 )
				query += " and isnull(bin_fk, 0 ) = " + vitri;
			if( phieudt.trim().length() > 0 )
				query += " and isnull(maphieudinhtinh, '') = '" + phieudt + "' ";
			if( phieueo.trim().length() > 0 )
				query += " and isnull(phieueo, '') = '" + phieueo + "' ";

			System.out.println("::: CAP NHAT KHO CT: " + query);
			
			String msg = WebService.Update_KhoTT(ngayyeucau, ghichu, db, khott_fk, spId, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, vitri, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, loaidoituong, doituongId, 
								soluong, booked, available, 
								loaichungtu, chungtuId, transactionId, UtilitySyn.secrect);
			
			System.out.println("::: KET QUA CAP NHAT KHO: " + msg);
			if( !msg.equals("OK") )
				return msg;*/
				
			String query =  "  select sanpham_fk, available, soluong, booked, sp.ma + ' ' + sp.ten as ten   " +
							"  from ERP_KHOTT_SP_CHITIET kho " +
							"  inner join ERP_SANPHAM sp  on kho.sanpham_fk = sp.pk_seq " + 
							" where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and ngaynhapkho = '" + ngaynhapkho + "' ";

			if( doituongId.trim().length() > 0  )
				query += " and kho.loaidoituong = '" + loaidoituong + "' and kho.doituongId = '" + doituongId + "' ";

			if( MAME.trim().length() > 0 )
				query += " and isnull(kho.MAME, '') = '" + MAME + "' ";
			if( HAMLUONG.trim().length() > 0 )
				query += " and isnull(kho.MATHUNG, '') = '" + MATHUNG + "' ";
			if( HAMAM.trim().length() > 0 )
				query += " and isnull(kho.MAPHIEU, '') = '" + MAPHIEU + "' ";

			if( MARQ.trim().length() > 0 )
				query += " and isnull(kho.MARQ, '') = '" + MARQ + "' ";
			if( HAMLUONG.trim().length() > 0 )
				query += " and isnull(kho.HAMLUONG, 100) = '" + HAMLUONG + "' ";
			if( HAMAM.trim().length() > 0 )
				query += " and isnull(kho.HAMAM, 0) = '" + HAMAM + "' ";

			if( vitri.trim().length() > 0 )
				query += " and isnull(kho.bin_fk, 0 ) = " + vitri;
			if( phieudt.trim().length() > 0 )
				query += " and isnull(kho.maphieudinhtinh, '') = '" + phieudt + "' ";
			if( phieueo.trim().length() > 0 )
				query += " and isnull(kho.phieueo, '') = '" + phieueo + "' ";

			System.out.println("[UTILITY KHO : QUERY CHECK KHO]" + query);
			double available_ton=0;
			double soluongton=0;
			boolean daco = false;
			double booked_ton=0;
			ResultSet rsCheck = db.get(query);

			if( rsCheck.next() )
			{
				daco = true;
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				booked_ton=rsCheck.getDouble("booked");
				if(available < 0 && available_ton < (-1)*available )
				{
					return "Số lượng tồn hiện tại trong kho của sản phẩm : " + rsCheck.getString("ten") + "  [" + available_ton + "], số lô [" + solo + "], ngày hết hạn [" + ngayhethan + "], ngày nhập kho [" + ngaynhapkho + "], không đủ để trừ kho, vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton < (-1) * soluong )
				{
					System.out.println(":: SO LUONG: " + soluong + " -- SO LUONG TON: " + soluongton);
					return "Số lượng tồn trong kho của sản phẩm : " + rsCheck.getString("ten") + "  [" + soluongton + "] không đủ để trừ kho, vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}
				String querylog =   " INSERT INTO log_erp_khott_sp_chitiet ( KHOTT_FK	,SANPHAM_FK,	NGAYHETHAN,	SOLO	,BIN_FK	,SOLUONG	,BOOKED, " +
						"	AVAILABLE	,NGAYSANXUAT,	  NGAYNHAPKHO	, 	 	MARQ,	HAMLUONG,	HAMAM	,LOAIDOITUONG	,DOITUONGID	,MAME	,MATHUNG, " +
						"	MAPHIEU,	MAPHIEUDINHTINH	,PHIEUEO    ,DIENGIAI	,SOLUONG_CN,	BOOKED_CN,	AVAILABLE_CN ) "+
						"   SELECT "+khott_fk+"	,"+spId+",	'"+ngayhethan+"',	'"+solo+"'	,"+vitri+"	,"+soluongton+"	,"+booked_ton+",	 " +
						"   "+available_ton+"	,'',	  '"+ngaynhapkho+"'	,'"+MARQ+"',	'"+HAMLUONG+"',	'"+HAMAM+"'	,'"+loaidoituong+"'	,'"+doituongId+"'" +
						"	,'"+MAME+"'	,'"+MATHUNG+"',	'"+MAPHIEU+"',	'"+phieudt+"'	,'"+phieueo+"' ,N'"+ghichu+"',"+soluong+","+booked+","+available+"";

						if(!db.update(querylog)){
							return  " Không thể cập nhật log " + querylog;
						}
						
				query = " Update ERP_KHOTT_SP_CHITIET set booked = round(isnull(booked,0), 3) + round(" + booked + ", 3), soluong = round(ISNULL(soluong,0), 3) + round(" + soluong + ", 3), " +
						" 	AVAILABLE = round(ISNULL(AVAILABLE,0), 3) + round(" + available + ", 3)  "+
						"  where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and ngaynhapkho = '" + ngaynhapkho + "' ";

				if( doituongId.trim().length() > 0  )
					query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";

				if( MAME.trim().length() > 0 )
					query += " and isnull(MAME, '') = '" + MAME + "' ";
				if( HAMLUONG.trim().length() > 0 )
					query += " and isnull(MATHUNG, '') = '" + MATHUNG + "' ";
				if( HAMAM.trim().length() > 0 )
					query += " and isnull(MAPHIEU, '') = '" + MAPHIEU + "' ";

				if( MARQ.trim().length() > 0 )
					query += " and isnull(MARQ, '') = '" + MARQ + "' ";
				if( HAMLUONG.trim().length() > 0 )
					query += " and isnull(HAMLUONG, 100) = '" + HAMLUONG + "' ";
				if( HAMAM.trim().length() > 0 )
					query += " and isnull(HAMAM, 0) = '" + HAMAM + "' ";

				if( vitri.trim().length() > 0 )
					query += " and isnull(bin_fk, 0 ) = " + vitri;
				if( phieudt.trim().length() > 0 )
					query += " and isnull(maphieudinhtinh, '') = '" + phieudt + "' ";
				if( phieueo.trim().length() > 0 )
					query += " and isnull(phieueo, '') = '" + phieueo + "' ";

				System.out.println("::: CAP NHAT KHO CT: " + query);
				int resultInt = db.updateReturnInt(query);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				}

				//KHÔNG SỬ DỤNG KHO TỔNG
				query = " Update ERP_KHOTT_SANPHAM set booked = round(isnull(booked,0), 3) + round(" + booked + ", 3), soluong = round(ISNULL(soluong,0), 3) + round(" + soluong + ", 3), " +
						" 	AVAILABLE = round(ISNULL(AVAILABLE,0), 3) + round(" + available + ", 3)  "+
						"  where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + "  ";
				if( doituongId.trim().length() > 0  )
					query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
				System.out.println("::: CAP NHAT KHO TONG: " + query);

				resultInt = db.updateReturnInt(query);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				}
			}
			rsCheck.close();

			if( !daco )  //Trường hợp trong kho chi tiết chưa có SP NÀY
			{
				//Bên bán mà kho chưa có thì phải báo lỗi
				return  "Lỗi xác định sản phẩm trong kho. Vui lòng liên hệ Admin để xử lý.";
			}

			if( !daco )
			{
				return "Số lượng tồn trong kho không hợp lệ. Vui lòng liên hệ Admin để xử lý " ;
			}
		}
		catch(Exception er)
		{
			er.printStackTrace();
			return  "Không thể thực hiện cập nhật kho  Util.Update_KhoTT : "+er.getMessage();
		}

		return "";
	}
	//ham check chuan
	
	public String Update_KhoTT(String ngayyeucau, String ghichu, Idbutils db, String khott_fk, 
			String spId, String solo, String ngayhethan, String ngaynhapkho,
			String MAME, String MATHUNG, String vitri,  
			String MAPHIEU, String phieudt, String phieueo, 
			String MARQ, String HAMLUONG, String HAMAM, 
			String loaidoituong, String doituongId,
			double soluong, double booked, double available, double dongia, String ngaysanxuat) {
		
		//THỐNG NHẤT LÀM TRÒN 3 CHỮ SỐ SAU SỐ LƯỢNG
		available = DinhDang.dinhdangso(available);
		soluong =  DinhDang.dinhdangso(soluong);
		booked =DinhDang.dinhdangso(booked);
		if(vitri==null|| vitri==""){
			vitri="0";
		}
		if( HAMAM.equals("0.0") )
			HAMAM = "0";
		if( HAMLUONG.equals("100.0") )
			HAMLUONG = "100";
		
		if( HAMAM.trim().length() <= 0 )
			HAMAM = "0";
		if( HAMLUONG.trim().length() <= 0 )
			HAMLUONG = "100";
		
	 
		try
		{
			String query =  "  select count(*) as sodong  " +
							"  from ERP_KHOTT_SANPHAM kho " +
							" where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + "  ";
							if( doituongId.trim().length() > 0  ) {
								query += " and kho.loaidoituong = '" + loaidoituong + "' and kho.doituongId = '" + doituongId + "' ";
							}else{
								query += " and kho.doituongId  is  null  ";
							}
					
			ResultSet	rsCheck = db.get(query);
			int count = 0;
			 
			if(rsCheck.next()){
				count = rsCheck.getInt("sodong");
			}
	 
	
		if( count <= 0 )
		{
				query = "insert ERP_KHOTT_SANPHAM( KHOTT_FK, SANPHAM_FK, soluong, booked, available ";
				if( doituongId.trim().length() > 0  )
					query += " ,loaidoituong, doituongId ";
				query += " )";
				
				query += " values( '" + khott_fk + "', " + spId + ", " + soluong + ", " + booked + ", " + available + "";
				if( doituongId.trim().length() > 0  )
					query += " ,'" + loaidoituong + "', '" + doituongId + "' ";
				query += " ) ";
		
		 
				int resultInt = db.updateReturnInt(query);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				}
				
				String querylog =   "insert into log_khott_sanpham(khotT_fk,sanpham_fk,bin_fk,maphieu,phieueo,sophieudinhtinh,mathung,mame,ngayhethan,soluong,booked,avai,diengiai)"+
						"   SELECT "+khott_fk+"	,"+spId+",'"+vitri+"'	,'"+MAPHIEU+"'	,'"+phieueo+"',	 " +
						"   '"+phieudt+"'	,'"+MATHUNG+"',	  '"+MAME+"'	,'"+ngayhethan+"',	'"+soluong+"',	'"+booked+"'	,'"+available+"'	,'"+ghichu+"'";

				if(!db.update(querylog)){
					return  " Không thể cập nhật log " + querylog;
				}
		}else{
			
			//kiem tra cap nhat kho 

			query="select (select TEN from ERP_KHOTT where TRANGTHAI='1' and PK_SEQ=a.KHOTT_FK) as TENKHO,(select TEN from ERP_SANPHAM where TRANGTHAI='1' and PK_SEQ=a.SANPHAM_FK) as TENSP, cast (round(ISNULL(booked,0), 4) as numeric(18,4)) + round(" + booked + ", 4) as booked,"+
				  "	cast (round(ISNULL(AVAILABLE,0), 4) as numeric(18,4)) + round(" + available + ", 4) as AVAILABLE "+
				  "	from ERP_KHOTT_SANPHAM a  where KHOTT_FK=" + khott_fk + " and SANPHAM_FK=" + spId + "";
			if( doituongId.trim().length() > 0  ) {
					query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
				}else{
					query += "  and doituongId  is null  ";
				}
			
				String tensp="";
				String tenkho="";
				double book=0;
				double avai=0;
				System.out.println("check kho : "+query);
				ResultSet rskt=db.get(query);
				if(rskt.next())
				{
					tensp=rskt.getString("TENSP");
					tenkho=rskt.getString("TENKHO");
					book=rskt.getDouble("booked");
					avai=rskt.getDouble("AVAILABLE");
				}
				rskt.close();
				
				if(tenkho.trim().length()>0 && tensp.trim().length()>0)
				{
					if(avai<0 || book<0)
					{
						return  " Vui lòng kiểm tra tồn kho sản phẩm "+ tensp + " trong kho "+ tenkho ;
					}
				}
				
			
				query = " Update ERP_KHOTT_SANPHAM set booked = cast (round(ISNULL(booked,0), 4) as numeric(18,4)) + round(" + booked + ", 4), soluong =cast (round(ISNULL(soluong,0), 4) as numeric(18,4)) + round(" + soluong + ", 4), " +
				" 	AVAILABLE = cast (round(ISNULL(AVAILABLE,0), 4) as numeric(18,4)) + round(" + available + ", 4)  "+
				"  where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + "  ";
				if( doituongId.trim().length() > 0  ) {
					query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
				}else{
					query += "  and doituongId  is null  ";
				}
			
			
				int resultInt = db.updateReturnInt(query);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;

				}
				
				String querylog =   "insert into log_khott_sanpham(khotT_fk,sanpham_fk,bin_fk,maphieu,phieueo,sophieudinhtinh,mathung,mame,ngayhethan,soluong,booked,avai,diengiai)"+
						"   SELECT "+khott_fk+"	,"+spId+",'"+vitri+"'	,'"+MAPHIEU+"'	,'"+phieueo+"',	 " +
						"   '"+phieudt+"'	,'"+MATHUNG+"',	  '"+MAME+"'	,'"+ngayhethan+"',	'"+soluong+"',	'"+booked+"'	,'"+available+"'	,'"+ghichu+"'";
				if(!db.update(querylog)){
					return  " Không thể cập nhật log " + querylog;
				}
			}
		
		
			  query =  "  select sanpham_fk,  cast( booked as numeric(18,4)) as booked ,cast( available as numeric(18,4))  as  available , cast(soluong as numeric(18,4)) as soluong " +
							", sp.ma + ' ' + sp.ten as ten   " +
							"  from ERP_KHOTT_SP_CHITIET kho " +
							"  inner join ERP_SANPHAM sp  on kho.sanpham_fk = sp.pk_seq " + 
							" where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and ngaynhapkho = '" + ngaynhapkho + "' ";
			
			if( doituongId.trim().length() > 0  ){
				query += " and kho.loaidoituong = '" + loaidoituong + "' and kho.doituongId = '" + doituongId + "' ";
			}else{
				query +=  "  and kho.doituongId is null   ";
			}
			
			//if( MAME.trim().length() > 0 )
				query += " and isnull(kho.MAME, '') = '" + MAME + "' ";
			//if( HAMLUONG.trim().length() > 0 )
				query += " and isnull(kho.MATHUNG, '') = '" + MATHUNG + "' ";
		//	if( HAMAM.trim().length() > 0 )
				query += " and isnull(kho.MAPHIEU, '') = '" + MAPHIEU + "' ";
			
		//	if( MARQ.trim().length() > 0 )
				query += " and isnull(kho.MARQ, '') = '" + MARQ + "' ";
		//	if( HAMLUONG.trim().length() > 0 )
				query += " and isnull(kho.HAMLUONG, 100) = '" + HAMLUONG + "' ";
		//	if( HAMAM.trim().length() > 0 )
				query += " and isnull(kho.HAMAM, 0) = '" + HAMAM + "' ";
			
			if(  vitri.trim().length() > 0 ){
				query += " and isnull(kho.bin_fk, 0 ) = " + vitri;
			}else{
				query += " and isnull(kho.bin_fk, 0 ) = 0";
			}
			//if( phieudt.trim().length() > 0 )
				query += " and isnull(kho.maphieudinhtinh, '') = '" + phieudt + "' ";
			//if( phieueo.trim().length() > 0 )
				query += " and isnull(kho.phieueo, '') = '" + phieueo + "' ";
				
			/*if(ngaysanxuat.trim().length() >0)
				query +=" and isnull(kho.ngaysanxuat,'')= '"+ ngaysanxuat+"'";*/
				
			
			System.out.println("[UTILITY KHO : QUERY CHECK KHO]" + query);
			double available_ton=0;
//			double giaton = 0;
			double soluongton=0;
			double booked_ton =0 ;
			boolean daco = false;

			  rsCheck = db.get(query);
			 
				if( rsCheck.next() )
				{
					daco = true;
					booked_ton=rsCheck.getDouble("booked");
					
					soluongton= this.Round(rsCheck.getDouble("soluong"), 4);
					available_ton=this.Round(rsCheck.getDouble("available"), 4);
					
					if(available < 0 && available_ton < (-1)*available )
					{
						return "Số lượng tồn hiện tại trong kho "+khott_fk+" của sản phẩm : " +spId+" -  " +rsCheck.getString("ten") + " " +
								" [" + available_ton + "] / ["+available+"], số lô [" + solo + "], ngày hết hạn [" + ngayhethan + "],hàm ẩm: "+HAMAM+" ,hàm lượng : "+HAMLUONG+" " +
										"ngày nhập kho [" + ngaynhapkho + "]," +
												" không đủ để trừ kho, vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
					}
	
					if(soluong < 0 && soluongton < (-1) * soluong )
					{
						//System.out.println(":: SO LUONG: " + soluong + " -- SO LUONG TON: " + soluongton);
						return "Số lượng tồn trong kho của sản phẩm : " + rsCheck.getString("ten") + "  [" + soluongton + "] không đủ để trừ kho, vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
					}
	
					String querylog =   " INSERT INTO log_erp_khott_sp_chitiet ( KHOTT_FK	,SANPHAM_FK,	NGAYHETHAN,	SOLO	,BIN_FK	,SOLUONG	,BOOKED, " +
										"	AVAILABLE	,NGAYSANXUAT,	  NGAYNHAPKHO	, 	 	MARQ,	HAMLUONG,	HAMAM	,LOAIDOITUONG	,DOITUONGID	,MAME	,MATHUNG, " +
										"	MAPHIEU,	MAPHIEUDINHTINH	,PHIEUEO    ,DIENGIAI	,SOLUONG_CN,	BOOKED_CN,	AVAILABLE_CN ) "+
										"   SELECT "+khott_fk+"	,"+spId+",	'"+ngayhethan+"',	'"+solo+"'	,"+vitri+"	,"+soluongton+"	,"+booked_ton+",	 " +
										"   "+available_ton+"	,'"+ngaysanxuat+"',	  '"+ngaynhapkho+"'	,'"+MARQ+"',	'"+HAMLUONG+"',	'"+HAMAM+"'	,'"+loaidoituong+"'	,'"+doituongId+"'" +
										"	,'"+MAME+"'	,'"+MATHUNG+"',	'"+MAPHIEU+"',	'"+phieudt+"'	,'"+phieueo+"' ,N'"+ghichu+"',"+soluong+","+booked+","+available+"";
					
					if(!db.update(querylog)){
						return  " Không thể cập nhật log " + query;
					}
					
					
					query = " Update ERP_KHOTT_SP_CHITIET set booked = CAST( round(ISNULL(booked,0), 4) as   numeric(18,4) ) +  cast( round(" + booked + ", 4)  as   numeric(18,4) ), soluong =CAST( round(ISNULL(soluong,0), 4) as   numeric(18,4) ) +  cast( round(" + soluong + ", 4)  as   numeric(18,4) ), " +
							" 	AVAILABLE = CAST( round(ISNULL(AVAILABLE,0), 4) as   numeric(18,4) ) +  cast( round(" + available + ", 4)  as   numeric(18,4) )   "+
							"  where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan 
							+ "' and ngaynhapkho = '" + ngaynhapkho +"' ";
					
					if( doituongId.trim().length() > 0  ) {
						query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
					}else{
						query +=   "  and doituongId is null  ";
					}
					
					//if( MAME.trim().length() > 0 )
						query += " and isnull(MAME, '') = '" + MAME + "' ";
				//	if( HAMLUONG.trim().length() > 0 )
						query += " and isnull(MATHUNG, '') = '" + MATHUNG + "' ";
					//if( HAMAM.trim().length() > 0 )
						query += " and isnull(MAPHIEU, '') = '" + MAPHIEU + "' ";
					
					//if( MARQ.trim().length() > 0 )
						query += " and isnull(MARQ, '') = '" + MARQ + "' ";
						
					if( HAMLUONG.trim().length() > 0 )
						query += " and isnull(HAMLUONG, 100) = '" + HAMLUONG + "' ";
					if( HAMAM.trim().length() > 0 )
						query += " and isnull(HAMAM, 0) = '" + HAMAM + "' ";
					
					  if( vitri.trim().length() > 0 ){
							query += " and isnull(bin_fk, 0 ) = " + vitri;
					  }else{
						  	query += " and isnull(bin_fk, 0 ) = 0 ";
					  }
					  
					//if( phieudt.trim().length() > 0 )
						query += " and isnull(maphieudinhtinh, '') = '" + phieudt + "' ";
					//if( phieueo.trim().length() > 0 )
						query += " and isnull(phieueo, '') = '" + phieueo + "' ";
						
					/*if(ngaysanxuat.trim().length() >0)
						query +=" and isnull(ngaysanxuat,'')= '"+ ngaysanxuat+"'";*/
					
					
					//System.out.println("::: 2. CAP NHAT KHO CT: " + query);
					int resultInt = db.updateReturnInt(query);
					if(resultInt != 1)
					{
						return  " Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
					}
				 
					
				}
				rsCheck.close();
			 
			
			if( !daco )  //Trường hợp trong kho chi tiết chưa có SP NÀY
			{
				String querylog =   " INSERT INTO log_erp_khott_sp_chitiet ( KHOTT_FK	,SANPHAM_FK,	NGAYHETHAN,	SOLO	,BIN_FK	,SOLUONG	,BOOKED, " +
				"	AVAILABLE	,NGAYSANXUAT,	  NGAYNHAPKHO	, 	 	MARQ,	HAMLUONG,	HAMAM	,LOAIDOITUONG	,DOITUONGID	,MAME	,MATHUNG, " +
				"	MAPHIEU,	MAPHIEUDINHTINH	,PHIEUEO    ,DIENGIAI	,SOLUONG_CN,	BOOKED_CN,	AVAILABLE_CN ) "+
				"   SELECT "+khott_fk+"	,"+spId+",	'"+ngayhethan+"',	'"+solo+"'	,"+vitri+"	,"+soluongton+"	,"+booked_ton+",	 " +
				"   "+available_ton+"	,'"+ngaysanxuat+"',	  '"+ngaynhapkho+"'	,'"+MARQ+"',	'"+HAMLUONG+"',	'"+HAMAM+"'	,'"+loaidoituong+"'	,'"+doituongId+"'" +
				"	,'"+MAME+"'	,'"+MATHUNG+"',	'"+MAPHIEU+"',	'"+phieudt+"'	,'"+phieueo+"' ,N'"+ghichu+"',"+soluong+","+booked+","+available+"";

				if(!db.update(querylog)){
					return  " Không thể cập nhật log " + query;
				}

				  
				query = "insert ERP_KHOTT_SP_CHITIET( KHOTT_FK, SANPHAM_FK, SOLO, NGAYHETHAN, NGAYNHAPKHO, soluong, booked, available ";
				if( doituongId.trim().length() > 0  )
					query += " ,loaidoituong, doituongId ";
				
				if( MAME.trim().length() > 0 )
					query += " ,MAME ";
				if( MATHUNG.trim().length() > 0 )
					query += " ,MATHUNG ";
				if( MAPHIEU.trim().length() > 0 )
					query += " ,MAPHIEU ";
				
				if( MARQ.trim().length() > 0 )
					query += " ,MARQ ";
				if( HAMLUONG.trim().length() > 0 )
					query += " ,HAMLUONG ";
				if( HAMAM.trim().length() > 0 )
					query += " ,HAMAM ";
				
				if( vitri.trim().length() > 0 )
					query += " ,BIN_FK, KHUVUCKHO_FK ";
				if( phieudt.trim().length() > 0 )
					query += " ,maphieudinhtinh ";
				if( phieueo.trim().length() > 0 )
					query += " ,phieueo ";
				if(ngaysanxuat.trim().length() >0)
					query +=" ,ngaysanxuat ";
				
				query += " )";
				
				query += " select '" + khott_fk + "', " + spId + ", '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho 
						+ "', " + soluong + ", " + booked + ", " + available + "";
				if( doituongId.trim().length() > 0  )
					query += " ,'" + loaidoituong + "', '" + doituongId + "' ";
				
				if( MAME.trim().length() > 0 )
					query += " ,'" + MAME + "' ";
				if( MATHUNG.trim().length() > 0 )
					query += " , '" + MATHUNG + "' ";
				if( MAPHIEU.trim().length() > 0 )
					query += " , '" + MAPHIEU + "' ";
				
				if( MARQ.trim().length() > 0 )
					query += " ,'" + MARQ + "' ";
				if( HAMLUONG.trim().length() > 0 )
					query += " , '" + HAMLUONG + "' ";
				if( HAMAM.trim().length() > 0 )
					query += " , '" + HAMAM + "' ";
				
				if( vitri.trim().length() > 0 )
					query += " , " + vitri + ", ( select KHUVUC_FK from ERP_BIN where PK_SEQ = " + vitri + " ) ";
				if( phieudt.trim().length() > 0 )
					query += " , '" + phieudt + "' ";
				if( phieueo.trim().length() > 0 )
					query += " , '" + phieueo + "' ";
				if(ngaysanxuat.trim().length() >0)
					query += " , '" + ngaysanxuat + "' ";
				
				
				query += "  ";
				//System.out.println("::: INSERT KHO CT: " + query);
				int resultInt = db.updateReturnInt(query);
				if(resultInt != 1)
				{
					return  " Không thể thêm mới ERP_KHOTT_SP_CHITIET " + query;
				}
			}
			
			if( !daco )
			{
				if( soluong < 0 || available < 0 || booked < 0 )
				{
					return "Số lượng tồn trong kho không hợp lệ. Vui lòng liên hệ Admin để xử lý " ;
				}
			}
			
			// kiểm tra sp lô,ngày nhập kho, có bị âm  kho ko?
			if(soluong<0){
		query=  " SELECT * FROM [UFN_NXT_HO_FULL_THEO_SP]('','"+ngayyeucau+"',"+khott_fk+","+spId+","+vitri+",'"+solo+"','"+ngaynhapkho+"','"+ngayhethan+"','"+MAME+"','"+MATHUNG+"','"+MAPHIEU+"','"+phieudt+"','"+phieueo+"','"+MARQ+"','"+HAMAM+"','"+HAMLUONG+"')  " +
				" WHERE ROUND(CUOIKY,3) <0";
			 	System.out.println("Du lieu check 2: "+query);
				ResultSet rs=db.get(query);
				if(rs.next()){
					System.out.println(query);
					return "Vui lòng thử lại nghiệp vụ,nếu không được vui lòng báo Admin để được trợ giúp. Tổng xuất nhập tồn của sản phẩm :"+spId+" | số lô :"+solo+" bạn đang cập nhật không hợp lệ";
				}
				rs.close();
			}
			
		}
		catch(Exception er)
		{
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Update_KhoTT : "+er.getMessage();
		}
		
		
		
		
		return "";
	}
	
	public String Update_KhoTT_NEW(String ngayyeucau, String ghichu, Idbutils db, String khott_fk, 
			String spId, String solo, String ngayhethan, String ngaynhapkho,
			String MAME, String MATHUNG, String vitri,  
			String MAPHIEU, String phieudt, String phieueo, 
			String MARQ, String HAMLUONG, String HAMAM, 
			String loaidoituong, String doituongId,
			double soluong, double booked, double available, double dongia, String ngaysanxuat, String nsx_fk) {
		
		//THỐNG NHẤT LÀM TRÒN 3 CHỮ SỐ SAU SỐ LƯỢNG
		available = DinhDang.dinhdangso(available);
		soluong =  DinhDang.dinhdangso(soluong);
		booked =DinhDang.dinhdangso(booked);
		
		if( HAMAM.equals("0.0") )
			HAMAM = "0";
		if( HAMLUONG.equals("100.0") )
			HAMLUONG = "100";
		
		if( HAMAM.trim().length() <= 0 )
			HAMAM = "0";
		if( HAMLUONG.trim().length() <= 0 )
			HAMLUONG = "100";
		
		if(vitri==null ||vitri.trim().equals("")){
			vitri="0";
		}
		if(nsx_fk==null || nsx_fk.trim().equals("")){
			nsx_fk="0";
		}
		
		try
		{
			String query =  "  select count(*) as sodong  " +
							"  from ERP_KHOTT_SANPHAM kho " +
							" where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + "  ";
							if( doituongId.trim().length() > 0  ) {
								query += " and kho.loaidoituong = '" + loaidoituong + "' and kho.doituongId = '" + doituongId + "' ";
							}else{
								query += " and kho.doituongId  is  null  ";
							}
					
			ResultSet	rsCheck = db.get(query);
			int count = 0;
			 
			if(rsCheck.next()){
				count = rsCheck.getInt("sodong");
			}
	 
	
		if( count <= 0 )
		{
				query = "insert ERP_KHOTT_SANPHAM( KHOTT_FK, SANPHAM_FK, soluong, booked, available ";
				if( doituongId.trim().length() > 0  )
					query += " ,loaidoituong, doituongId ";
				query += " )";
				
				query += " values( '" + khott_fk + "', " + spId + ", " + soluong + ", " + booked + ", " + available + "";
				if( doituongId.trim().length() > 0  )
					query += " ,'" + loaidoituong + "', '" + doituongId + "' ";
				query += " ) ";
		
		 
				int resultInt = db.updateReturnInt(query);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				}
				
				String querylog =   "insert into log_khott_sanpham(khotT_fk,sanpham_fk,bin_fk,maphieu,phieueo,sophieudinhtinh,mathung,mame,ngayhethan,soluong,booked,avai,diengiai)"+
						"   SELECT "+khott_fk+"	,"+spId+",'"+vitri+"'	,'"+MAPHIEU+"'	,'"+phieueo+"',	 " +
						"   '"+phieudt+"'	,'"+MATHUNG+"',	  '"+MAME+"'	,'"+ngayhethan+"',	'"+soluong+"',	'"+booked+"'	,'"+available+"'	,'"+ghichu+"'";

				if(!db.update(querylog)){
					return  " Không thể cập nhật log " + querylog;
				}
		}else{
			
			//kiem tra cap nhat kho 

			query="select (select TEN from ERP_KHOTT where TRANGTHAI='1' and PK_SEQ=a.KHOTT_FK) as TENKHO,(select TEN from ERP_SANPHAM where TRANGTHAI='1' and PK_SEQ=a.SANPHAM_FK) as TENSP, cast (round(ISNULL(booked,0), 4) as numeric(18,4)) + round(" + booked + ", 4) as booked,"+
				  "	cast (round(ISNULL(AVAILABLE,0), 4) as numeric(18,4)) + round(" + available + ", 4) as AVAILABLE "+
				  "	from ERP_KHOTT_SANPHAM a  where KHOTT_FK=" + khott_fk + " and SANPHAM_FK=" + spId + "";
			if( doituongId.trim().length() > 0  ) {
					query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
				}else{
					query += "  and doituongId  is null  ";
				}
			
				String tensp="";
				String tenkho="";
				double book=0;
				double avai=0;
				System.out.println("query: "+query);
				ResultSet rskt=db.get(query);
				if(rskt.next())
				{
					tensp=rskt.getString("TENSP");
					tenkho=rskt.getString("TENKHO");
					book=rskt.getDouble("booked");
					avai=rskt.getDouble("AVAILABLE");
				}
				rskt.close();
				
				if(tenkho.trim().length()>0 && tensp.trim().length()>0)
				{
					if(avai<0 || book<0)
					{
						return  " Vui lòng kiểm tra tồn kho sản phẩm "+ tensp + " trong kho "+ tenkho ;
					}
				}
				
				query = " Update ERP_KHOTT_SANPHAM set booked = cast (round(ISNULL(booked,0), 4) as numeric(18,4)) + round(" + booked + ", 4), soluong =cast (round(ISNULL(soluong,0), 4) as numeric(18,4)) + round(" + soluong + ", 4), " +
				" 	AVAILABLE = cast (round(ISNULL(AVAILABLE,0), 4) as numeric(18,4)) + round(" + available + ", 4)  "+
				"  where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + "  ";
				if( doituongId.trim().length() > 0  ) {
					query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
				}else{
					query += "  and doituongId  is null  ";
				}
			
				int resultInt = db.updateReturnInt(query);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;

				}
				
				String querylog =   "insert into log_khott_sanpham(khotT_fk,sanpham_fk,bin_fk,maphieu,phieueo,sophieudinhtinh,mathung,mame,ngayhethan,soluong,booked,avai,diengiai)"+
						"   SELECT "+khott_fk+"	,"+spId+",'"+vitri+"'	,'"+MAPHIEU+"'	,'"+phieueo+"',	 " +
						"   '"+phieudt+"'	,'"+MATHUNG+"',	  '"+MAME+"'	,'"+ngayhethan+"',	'"+soluong+"',	'"+booked+"'	,'"+available+"'	,'"+ghichu+"'";
				if(!db.update(querylog)){
					return  " Không thể cập nhật log " + querylog;
				}
			}
		
			  query =  "  select sanpham_fk,  cast( booked as numeric(18,4)) as booked ,cast( available as numeric(18,4))  as  available , cast(soluong as numeric(18,4)) as soluong " +
							", sp.ma + ' ' + sp.ten as ten   " +
							"  from ERP_KHOTT_SP_CHITIET kho " +
							"  inner join ERP_SANPHAM sp  on kho.sanpham_fk = sp.pk_seq " + 
							" where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and ngaynhapkho = '" + ngaynhapkho + "' ";
			
			if( doituongId.trim().length() > 0  ){
				query += " and kho.loaidoituong = '" + loaidoituong + "' and kho.doituongId = '" + doituongId + "' ";
			}else{
				query +=  "  and kho.doituongId is null   ";
			}
			
			//if( MAME.trim().length() > 0 )
				query += " and isnull(kho.MAME, '') = '" + MAME + "' ";
			//if( HAMLUONG.trim().length() > 0 )
				query += " and isnull(kho.MATHUNG, '') = '" + MATHUNG + "' ";
		//	if( HAMAM.trim().length() > 0 )
				query += " and isnull(kho.MAPHIEU, '') = '" + MAPHIEU + "' ";
			
		//	if( MARQ.trim().length() > 0 )
				query += " and isnull(kho.MARQ, '') = '" + MARQ + "' ";
		//	if( HAMLUONG.trim().length() > 0 )
				query += " and isnull(kho.HAMLUONG, 100) = '" + HAMLUONG + "' ";
		//	if( HAMAM.trim().length() > 0 )
				query += " and isnull(kho.HAMAM, 0) = '" + HAMAM + "' ";
			
			if(  vitri.trim().length() > 0 ){
				query += " and isnull(kho.bin_fk, 0 ) = " + vitri;
			}else{
				query += " and isnull(kho.bin_fk, 0 ) = 0";
			}
			//if( phieudt.trim().length() > 0 )
				query += " and isnull(kho.maphieudinhtinh, '') = '" + phieudt + "' ";
			//if( phieueo.trim().length() > 0 )
				query += " and isnull(kho.phieueo, '') = '" + phieueo + "' ";
				
			/*if(ngaysanxuat.trim().length() >0)
				query +=" and isnull(kho.ngaysanxuat,'')= '"+ ngaysanxuat+"'";*/
				
				if( nsx_fk.trim().length() > 0 ){
					query += " and isnull(kho.NSX_FK, 0 ) = " + nsx_fk;
				}else{
					query += " and isnull(kho.NSX_FK, 0 ) = 0";
				}
				
			System.out.println("[UTILITY KHO : QUERY CHECK KHO]" + query);
			double available_ton=0;
//			double giaton = 0;
			double soluongton=0;
			double booked_ton =0 ;
			boolean daco = false;

			  rsCheck = db.get(query);
			 
				if( rsCheck.next() )
				{
					daco = true;
					booked_ton=rsCheck.getDouble("booked");
					
					soluongton= this.Round(rsCheck.getDouble("soluong"), 4);
					available_ton=this.Round(rsCheck.getDouble("available"), 4);
					
					if(available < 0 && available_ton < (-1)*available )
					{
						return "Số lượng tồn hiện tại trong kho "+khott_fk+" của sản phẩm : " +spId+" -  " +rsCheck.getString("ten") + " " +
								" [" + available_ton + "] / ["+available+"], số lô [" + solo + "], ngày hết hạn [" + ngayhethan + "],hàm ẩm: "+HAMAM+" ,hàm lượng : "+HAMLUONG+" " +
										"ngày nhập kho [" + ngaynhapkho + "]," +
												" không đủ để trừ kho, vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
					}
	
					if(soluong < 0 && soluongton < (-1) * soluong )
					{
						//System.out.println(":: SO LUONG: " + soluong + " -- SO LUONG TON: " + soluongton);
						return "Số lượng tồn trong kho của sản phẩm : " + rsCheck.getString("ten") + "  [" + soluongton + "] không đủ để trừ kho, vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
					}
	
					String querylog =   " INSERT INTO log_erp_khott_sp_chitiet ( KHOTT_FK	,SANPHAM_FK,	NGAYHETHAN,	SOLO	,BIN_FK	,SOLUONG	,BOOKED, " +
										"	AVAILABLE	,NGAYSANXUAT,	  NGAYNHAPKHO	, 	 	MARQ,	HAMLUONG,	HAMAM	,LOAIDOITUONG	,DOITUONGID	,MAME	,MATHUNG, " +
										"	MAPHIEU,	MAPHIEUDINHTINH	,PHIEUEO    ,DIENGIAI	,SOLUONG_CN,	BOOKED_CN,	AVAILABLE_CN , nsx_fk) "+
										"   SELECT "+khott_fk+"	,"+spId+",	'"+ngayhethan+"',	'"+solo+"'	,"+vitri+"	,"+soluongton+"	,"+booked_ton+",	 " +
										"   "+available_ton+"	,'"+ngaysanxuat+"',	  '"+ngaynhapkho+"'	,'"+MARQ+"',	'"+HAMLUONG+"',	'"+HAMAM+"'	,'"+loaidoituong+"'	,'"+doituongId+"'" +
										"	,'"+MAME+"'	,'"+MATHUNG+"',	'"+MAPHIEU+"',	'"+phieudt+"'	,'"+phieueo+"' ,N'"+ghichu+"',"+soluong+","+booked+","+available+","+(nsx_fk.trim().length() > 0  ?nsx_fk.trim():"NULL")+"";
					
					if(!db.update(querylog)){
						return  " Không thể cập nhật log " + query;
					}
					
					
					query = " Update ERP_KHOTT_SP_CHITIET set booked = CAST( round(ISNULL(booked,0), 4) as   numeric(18,4) ) +  cast( round(" + booked + ", 4)  as   numeric(18,4) ), soluong =CAST( round(ISNULL(soluong,0), 4) as   numeric(18,4) ) +  cast( round(" + soluong + ", 4)  as   numeric(18,4) ), " +
							" 	AVAILABLE = CAST( round(ISNULL(AVAILABLE,0), 4) as   numeric(18,4) ) +  cast( round(" + available + ", 4)  as   numeric(18,4) )   "+
							"  where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan 
							+ "' and ngaynhapkho = '" + ngaynhapkho +"' ";
					
					if( doituongId.trim().length() > 0  ) {
						query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
					}else{
						query +=   "  and doituongId is null  ";
					}
					
					//if( MAME.trim().length() > 0 )
						query += " and isnull(MAME, '') = '" + MAME + "' ";
				//	if( HAMLUONG.trim().length() > 0 )
						query += " and isnull(MATHUNG, '') = '" + MATHUNG + "' ";
					//if( HAMAM.trim().length() > 0 )
						query += " and isnull(MAPHIEU, '') = '" + MAPHIEU + "' ";
					
					//if( MARQ.trim().length() > 0 )
						query += " and isnull(MARQ, '') = '" + MARQ + "' ";
						
					if( HAMLUONG.trim().length() > 0 )
						query += " and isnull(HAMLUONG, 100) = '" + HAMLUONG + "' ";
					if( HAMAM.trim().length() > 0 )
						query += " and isnull(HAMAM, 0) = '" + HAMAM + "' ";
					
					  if( vitri.trim().length() > 0 ){
							query += " and isnull(bin_fk, 0 ) = " + vitri;
					  }else{
						  	query += " and isnull(bin_fk, 0 ) = 0 ";
					  }
					  
					//if( phieudt.trim().length() > 0 )
						query += " and isnull(maphieudinhtinh, '') = '" + phieudt + "' ";
					//if( phieueo.trim().length() > 0 )
						query += " and isnull(phieueo, '') = '" + phieueo + "' ";
						
					/*if(ngaysanxuat.trim().length() >0)
						query +=" and isnull(ngaysanxuat,'')= '"+ ngaysanxuat+"'";*/
					
						if(nsx_fk.trim().length() > 0 ){
							query += " and isnull(nsx_fk, 0 ) = " + nsx_fk;
						}else{
							query += " and isnull(nsx_fk, 0 ) = 0";
						}
						
					//System.out.println("::: 2. CAP NHAT KHO CT: " + query);
					int resultInt = db.updateReturnInt(query);
					if(resultInt != 1)
					{
						return  " Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
					}
				 
					
				}
				rsCheck.close();
			 
			
			if( !daco )  //Trường hợp trong kho chi tiết chưa có SP NÀY
			{
				String querylog =   " INSERT INTO log_erp_khott_sp_chitiet ( KHOTT_FK	,SANPHAM_FK,	NGAYHETHAN,	SOLO	,BIN_FK	,SOLUONG	,BOOKED, " +
				"	AVAILABLE	,NGAYSANXUAT,	  NGAYNHAPKHO	, 	 	MARQ,	HAMLUONG,	HAMAM	,LOAIDOITUONG	,DOITUONGID	,MAME	,MATHUNG, " +
				"	MAPHIEU,	MAPHIEUDINHTINH	,PHIEUEO    ,DIENGIAI	,SOLUONG_CN,	BOOKED_CN,	AVAILABLE_CN , nsx_fk) "+
				"   SELECT "+khott_fk+"	,"+spId+",	'"+ngayhethan+"',	'"+solo+"'	,"+vitri+"	,"+soluongton+"	,"+booked_ton+",	 " +
				"   "+available_ton+"	,'"+ngaysanxuat+"',	  '"+ngaynhapkho+"'	,'"+MARQ+"',	'"+HAMLUONG+"',	'"+HAMAM+"'	,'"+loaidoituong+"'	,'"+doituongId+"'" +
				"	,'"+MAME+"'	,'"+MATHUNG+"',	'"+MAPHIEU+"',	'"+phieudt+"'	,'"+phieueo+"' ,N'"+ghichu+"',"+soluong+","+booked+","+available+","+(nsx_fk.trim().length() > 0  ?nsx_fk.trim():"NULL")+"";

				if(!db.update(querylog)){
					return  " Không thể cập nhật log " + query;
				}

				  
				query = "insert ERP_KHOTT_SP_CHITIET( KHOTT_FK, SANPHAM_FK, SOLO, NGAYHETHAN, NGAYNHAPKHO, soluong, booked, available ";
				if( doituongId.trim().length() > 0  )
					query += " ,loaidoituong, doituongId ";
				
				if( MAME.trim().length() > 0 )
					query += " ,MAME ";
				if( MATHUNG.trim().length() > 0 )
					query += " ,MATHUNG ";
				if( MAPHIEU.trim().length() > 0 )
					query += " ,MAPHIEU ";
				
				if( MARQ.trim().length() > 0 )
					query += " ,MARQ ";
				if( HAMLUONG.trim().length() > 0 )
					query += " ,HAMLUONG ";
				if( HAMAM.trim().length() > 0 )
					query += " ,HAMAM ";
				
				if( vitri.trim().length() > 0 )
					query += " ,BIN_FK, KHUVUCKHO_FK ";
				if( phieudt.trim().length() > 0 )
					query += " ,maphieudinhtinh ";
				if( phieueo.trim().length() > 0 )
					query += " ,phieueo ";
				if(ngaysanxuat.trim().length() >0)
					query +=" ,ngaysanxuat ";
				
				if( nsx_fk.trim().length() > 0 )
					query += " ,NSX_FK ";
				
				query += " )";
				
				query += " select '" + khott_fk + "', " + spId + ", '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho 
						+ "', " + soluong + ", " + booked + ", " + available + "";
				if( doituongId.trim().length() > 0  )
					query += " ,'" + loaidoituong + "', '" + doituongId + "' ";
				
				if( MAME.trim().length() > 0 )
					query += " ,'" + MAME + "' ";
				if( MATHUNG.trim().length() > 0 )
					query += " , '" + MATHUNG + "' ";
				if( MAPHIEU.trim().length() > 0 )
					query += " , '" + MAPHIEU + "' ";
				
				if( MARQ.trim().length() > 0 )
					query += " ,'" + MARQ + "' ";
				if( HAMLUONG.trim().length() > 0 )
					query += " , '" + HAMLUONG + "' ";
				if( HAMAM.trim().length() > 0 )
					query += " , '" + HAMAM + "' ";
				
				if( vitri.trim().length() > 0 )
					query += " , " + vitri + ", ( select KHUVUC_FK from ERP_BIN where PK_SEQ = " + vitri + " ) ";
				if( phieudt.trim().length() > 0 )
					query += " , '" + phieudt + "' ";
				if( phieueo.trim().length() > 0 )
					query += " , '" + phieueo + "' ";
				if(ngaysanxuat.trim().length() >0)
					query += " , '" + ngaysanxuat + "' ";
				
				if( nsx_fk.trim().length() > 0 )
					query += " , '" + nsx_fk + "' ";
				
				query += "  ";
				//System.out.println("::: INSERT KHO CT: " + query);
				int resultInt = db.updateReturnInt(query);
				if(resultInt != 1)
				{
					return  " Không thể thêm mới ERP_KHOTT_SP_CHITIET " + query;
				}
			}
			
			if( !daco )
			{
				if( soluong < 0 || available < 0 || booked < 0 )
				{
					return "Số lượng tồn trong kho không hợp lệ. Vui lòng liên hệ Admin để xử lý " ;
				}
			}
			
			// kiểm tra sp lô,ngày nhập kho, có bị âm  kho ko?
			if(soluong<0){
				query=  " SELECT * FROM [UFN_NXT_HO_FULL_THEO_SP_NSX]('','"+ngayyeucau+"',"+khott_fk+","+spId+","+vitri+",'"+solo+"','"+ngaynhapkho+"','"+ngayhethan+"','"+MAME+"','"+MATHUNG+"','"+MAPHIEU+"','"+phieudt+"','"+phieueo+"','"+MARQ+"','"+HAMAM+"','"+HAMLUONG+"',"+(nsx_fk.trim().length()==0?"0":nsx_fk)+")  " +
						" WHERE ROUND(CUOIKY,3) <0";
					 	System.out.println("Du lieu check 2: "+query);
						ResultSet rs=db.get(query);
						if(rs.next()){
							System.out.println(query);
							return "Vui lòng thử lại nghiệp vụ,nếu không được vui lòng báo Admin để được trợ giúp. Tổng xuất nhập tồn của sản phẩm :"+spId+" | số lô :"+solo+" bạn đang cập nhật không hợp lệ";
						}
						rs.close();
			}
			
		}
		catch(Exception er)
		{
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Update_KhoTT : "+er.getMessage();
		}
		
		
		
		
		return "";
	}
	
	
	public String Update_KhoTT_Chitiet(String ngayyeucau, String ghichu, Idbutils db, String khott_fk, 
			String spId, String solo, String ngayhethan, String ngaynhapkho,
			String MAME, String MATHUNG, String vitri,  
			String MAPHIEU, String phieudt, String phieueo, 
			String MARQ, String HAMLUONG, String HAMAM, 
			String loaidoituong, String doituongId,
			double soluong, double booked, double available, double dongia, String ngaysanxuat) {
		
		//THỐNG NHẤT LÀM TRÒN 3 CHỮ SỐ SAU SỐ LƯỢNG
		available = this.Round(available, 3);
		soluong = this.Round(soluong, 3);
		booked = this.Round(booked, 3);
		
		if( HAMAM.equals("0.0") )
			HAMAM = "0";
		if( HAMLUONG.equals("100.0") )
			HAMLUONG = "100";
		
		if( HAMAM.trim().length() <= 0 )
			HAMAM = "0";
		if( HAMLUONG.trim().length() <= 0 )
			HAMLUONG = "100";
		
		try
		{
		 
		
		String	  query =  "  select sanpham_fk,  cast( booked as numeric(18,3)) as booked ,cast( available as numeric(18,3))  as  available , cast(soluong as numeric(18,3)) as soluong " +
							", sp.ma + ' ' + sp.ten as ten   " +
							"  from ERP_KHOTT_SP_CHITIET kho " +
							"  inner join ERP_SANPHAM sp  on kho.sanpham_fk = sp.pk_seq " + 
							" where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and ngaynhapkho = '" + ngaynhapkho + "' ";
			
			if( doituongId.trim().length() > 0  ){
				query += " and kho.loaidoituong = '" + loaidoituong + "' and kho.doituongId = '" + doituongId + "' ";
			}else{
				query +=  "  and kho.doituongId is null   ";
			}
			
			//if( MAME.trim().length() > 0 )
				query += " and isnull(kho.MAME, '') = '" + MAME + "' ";
			//if( HAMLUONG.trim().length() > 0 )
				query += " and isnull(kho.MATHUNG, '') = '" + MATHUNG + "' ";
		//	if( HAMAM.trim().length() > 0 )
				query += " and isnull(kho.MAPHIEU, '') = '" + MAPHIEU + "' ";
			
		//	if( MARQ.trim().length() > 0 )
				query += " and isnull(kho.MARQ, '') = '" + MARQ + "' ";
		//	if( HAMLUONG.trim().length() > 0 )
				query += " and isnull(kho.HAMLUONG, 100) = '" + HAMLUONG + "' ";
		//	if( HAMAM.trim().length() > 0 )
				query += " and isnull(kho.HAMAM, 0) = '" + HAMAM + "' ";
			
			if( vitri.trim().length() > 0 ){
				query += " and isnull(kho.bin_fk, 0 ) = " + vitri;
			}else{
				query += " and isnull(kho.bin_fk, 0 ) = 0";
			}
			//if( phieudt.trim().length() > 0 )
				query += " and isnull(kho.maphieudinhtinh, '') = '" + phieudt + "' ";
			//if( phieueo.trim().length() > 0 )
				query += " and isnull(kho.phieueo, '') = '" + phieueo + "' ";
				
			/*if(ngaysanxuat.trim().length() >0)
				query +=" and isnull(kho.ngaysanxuat,'')= '"+ ngaysanxuat+"'";*/
				
			
			System.out.println("[UTILITY KHO : QUERY CHECK KHO]" + query);
			double available_ton=0;
//			double giaton = 0;
			double soluongton=0;
			double booked_ton =0 ;
			boolean daco = false;

			 ResultSet rsCheck = db.get(query);
			 
				if( rsCheck.next() )
				{
					daco = true;
					booked_ton=rsCheck.getDouble("booked");
					
					soluongton= this.Round(rsCheck.getDouble("soluong"), 3);
					available_ton=this.Round(rsCheck.getDouble("available"), 3);
					
					if(available < 0 && available_ton < (-1)*available )
					{
						return "Số lượng tồn hiện tại trong kho của sản phẩm : " + rsCheck.getString("ten") + "  [" + available_ton + "] / ["+available+"], số lô [" + solo + "], ngày hết hạn [" + ngayhethan + "], ngày nhập kho [" + ngaynhapkho + "], không đủ để trừ kho, vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
					}
	
					if(soluong < 0 && soluongton < (-1) * soluong )
					{
						System.out.println(":: SO LUONG: " + soluong + " -- SO LUONG TON: " + soluongton);
						return "Số lượng tồn trong kho của sản phẩm : " + rsCheck.getString("ten") + "  [" + soluongton + "] không đủ để trừ kho, vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
					}
	
					String querylog =   " INSERT INTO log_erp_khott_sp_chitiet ( KHOTT_FK	,SANPHAM_FK,	NGAYHETHAN,	SOLO	,BIN_FK	,SOLUONG	,BOOKED, " +
										"	AVAILABLE	,NGAYSANXUAT,	  NGAYNHAPKHO	, 	 	MARQ,	HAMLUONG,	HAMAM	,LOAIDOITUONG	,DOITUONGID	,MAME	,MATHUNG, " +
										"	MAPHIEU,	MAPHIEUDINHTINH	,PHIEUEO    ,DIENGIAI	,SOLUONG_CN,	BOOKED_CN,	AVAILABLE_CN ) "+
										"   SELECT "+khott_fk+"	,"+spId+",	'"+ngayhethan+"',	'"+solo+"'	,"+vitri+"	,"+soluongton+"	,"+booked_ton+",	 " +
										"   "+available_ton+"	,'"+ngaysanxuat+"',	  '"+ngaynhapkho+"'	,'"+MARQ+"',	'"+HAMLUONG+"',	'"+HAMAM+"'	,'"+loaidoituong+"'	,'"+doituongId+"'" +
										"	,'"+MAME+"'	,'"+MATHUNG+"',	'"+MAPHIEU+"',	'"+phieudt+"'	,'"+phieueo+"' ,N'"+ghichu+"',"+soluong+","+booked+","+available+"";
					
					if(!db.update(querylog)){
						return  " Không thể cập nhật log " + query;
					}
					
					
					query = " Update ERP_KHOTT_SP_CHITIET set booked = round(isnull(booked,0), 3) + round(" + booked + ", 3), soluong = round(ISNULL(soluong,0), 3) + round(" + soluong + ", 3), " +
							" 	AVAILABLE = CAST( round(ISNULL(AVAILABLE,0), 3) as   numeric(18,3) ) +  cast( round(" + available + ", 3)  as   numeric(18,3) )   "+
							"  where KHOTT_FK = " + khott_fk + " and sanpham_fk = " + spId + " and solo = '" + solo + "' and ngayhethan = '" + ngayhethan 
							+ "' and ngaynhapkho = '" + ngaynhapkho +"' ";
					
					if( doituongId.trim().length() > 0  ) {
						query += " and loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
					}else{
						query +=   "  and doituongId is null  ";
					}
					
					//if( MAME.trim().length() > 0 )
						query += " and isnull(MAME, '') = '" + MAME + "' ";
				//	if( HAMLUONG.trim().length() > 0 )
						query += " and isnull(MATHUNG, '') = '" + MATHUNG + "' ";
					//if( HAMAM.trim().length() > 0 )
						query += " and isnull(MAPHIEU, '') = '" + MAPHIEU + "' ";
					
					//if( MARQ.trim().length() > 0 )
						query += " and isnull(MARQ, '') = '" + MARQ + "' ";
						
					if( HAMLUONG.trim().length() > 0 )
						query += " and isnull(HAMLUONG, 100) = '" + HAMLUONG + "' ";
					if( HAMAM.trim().length() > 0 )
						query += " and isnull(HAMAM, 0) = '" + HAMAM + "' ";
					
					  if( vitri.trim().length() > 0 ){
							query += " and isnull(bin_fk, 0 ) = " + vitri;
					  }else{
						  	query += " and isnull(bin_fk, 0 ) = 0 ";
					  }
					  
					//if( phieudt.trim().length() > 0 )
						query += " and isnull(maphieudinhtinh, '') = '" + phieudt + "' ";
					//if( phieueo.trim().length() > 0 )
						query += " and isnull(phieueo, '') = '" + phieueo + "' ";
						
					/*if(ngaysanxuat.trim().length() >0)
						query +=" and isnull(ngaysanxuat,'')= '"+ ngaysanxuat+"'";*/
					
					
					System.out.println("::: 2. CAP NHAT KHO CT: " + query);
					int resultInt = db.updateReturnInt(query);
					if(resultInt != 1)
					{
						return  " Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
					}
				 
					
				}
				rsCheck.close();
			 
			
			if( !daco )  //Trường hợp trong kho chi tiết chưa có SP NÀY
			{
				String querylog =   " INSERT INTO log_erp_khott_sp_chitiet ( KHOTT_FK	,SANPHAM_FK,	NGAYHETHAN,	SOLO	,BIN_FK	,SOLUONG	,BOOKED, " +
				"	AVAILABLE	,NGAYSANXUAT,	  NGAYNHAPKHO	, 	 	MARQ,	HAMLUONG,	HAMAM	,LOAIDOITUONG	,DOITUONGID	,MAME	,MATHUNG, " +
				"	MAPHIEU,	MAPHIEUDINHTINH	,PHIEUEO    ,DIENGIAI	,SOLUONG_CN,	BOOKED_CN,	AVAILABLE_CN ) "+
				"   SELECT "+khott_fk+"	,"+spId+",	'"+ngayhethan+"',	'"+solo+"'	,"+vitri+"	,"+soluongton+"	,"+booked_ton+",	 " +
				"   "+available_ton+"	,'"+ngaysanxuat+"',	  '"+ngaynhapkho+"'	,'"+MARQ+"',	'"+HAMLUONG+"',	'"+HAMAM+"'	,'"+loaidoituong+"'	,'"+doituongId+"'" +
				"	,'"+MAME+"'	,'"+MATHUNG+"',	'"+MAPHIEU+"',	'"+phieudt+"'	,'"+phieueo+"' ,N'"+ghichu+"',"+soluong+","+booked+","+available+"";

				if(!db.update(querylog)){
					return  " Không thể cập nhật log " + query;
				}

				  
				query = "insert ERP_KHOTT_SP_CHITIET( KHOTT_FK, SANPHAM_FK, SOLO, NGAYHETHAN, NGAYNHAPKHO, soluong, booked, available ";
				if( doituongId.trim().length() > 0  )
					query += " ,loaidoituong, doituongId ";
				
				if( MAME.trim().length() > 0 )
					query += " ,MAME ";
				if( MATHUNG.trim().length() > 0 )
					query += " ,MATHUNG ";
				if( MAPHIEU.trim().length() > 0 )
					query += " ,MAPHIEU ";
				
				if( MARQ.trim().length() > 0 )
					query += " ,MARQ ";
				if( HAMLUONG.trim().length() > 0 )
					query += " ,HAMLUONG ";
				if( HAMAM.trim().length() > 0 )
					query += " ,HAMAM ";
				
				if( vitri.trim().length() > 0 )
					query += " ,BIN_FK, KHUVUCKHO_FK ";
				if( phieudt.trim().length() > 0 )
					query += " ,maphieudinhtinh ";
				if( phieueo.trim().length() > 0 )
					query += " ,phieueo ";
				if(ngaysanxuat.trim().length() >0)
					query +=" ,ngaysanxuat ";
				
				query += " )";
				
				query += " select '" + khott_fk + "', " + spId + ", '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho 
						+ "', '" + soluong + "', '" + booked + "', '" + available + "'";
				if( doituongId.trim().length() > 0  )
					query += " ,'" + loaidoituong + "', '" + doituongId + "' ";
				
				if( MAME.trim().length() > 0 )
					query += " ,'" + MAME + "' ";
				if( MATHUNG.trim().length() > 0 )
					query += " , '" + MATHUNG + "' ";
				if( MAPHIEU.trim().length() > 0 )
					query += " , '" + MAPHIEU + "' ";
				
				if( MARQ.trim().length() > 0 )
					query += " ,'" + MARQ + "' ";
				if( HAMLUONG.trim().length() > 0 )
					query += " , '" + HAMLUONG + "' ";
				if( HAMAM.trim().length() > 0 )
					query += " , '" + HAMAM + "' ";
				
				if( vitri.trim().length() > 0 )
					query += " , " + vitri + ", ( select KHUVUC_FK from ERP_BIN where PK_SEQ = " + vitri + " ) ";
				if( phieudt.trim().length() > 0 )
					query += " , '" + phieudt + "' ";
				if( phieueo.trim().length() > 0 )
					query += " , '" + phieueo + "' ";
				if(ngaysanxuat.trim().length() >0)
					query += " , '" + ngaysanxuat + "' ";
				
				
				query += "  ";
				System.out.println("::: INSERT KHO CT: " + query);
				int resultInt = db.updateReturnInt(query);
				if(resultInt != 1)
				{
					return  " Không thể thêm mới ERP_KHOTT_SP_CHITIET " + query;
				}
			}
			
			if( !daco )
			{
				if( soluong < 0 || available < 0 || booked < 0 )
				{
					return "Số lượng tồn trong kho không hợp lệ. Vui lòng liên hệ Admin để xử lý " ;
				}
			}
			
			// kiểm tra sp lô,ngày nhập kho, có bị âm  kho ko?
			query=  " SELECT * FROM [UFN_NXT_HO_FULL_THEO_SP]('','',"+khott_fk+","+spId+","+vitri+",'"+solo+"','"+ngaynhapkho+"','"+ngayhethan+"','"+MAME+"','"+MATHUNG+"','"+MAPHIEU+"','"+phieudt+"','"+phieueo+"','"+MARQ+"','"+HAMAM+"','"+HAMLUONG+"')  " +
					" WHERE CUOIKY<0";
					System.out.println("Du lieu check 3: "+query);
					ResultSet rs=db.get(query);
					if(rs.next()){
						System.out.println(query);
						return "Vui lòng thử lại nghiệp vụ,nếu không được vui lòng báo Admin để được trợ giúp. Tổng xuất nhập tồn của sản phẩm : "+spId+" | số lô :"+solo+" bạn đang cập nhật không hợp lệ";
					}
					rs.close();
		}
		catch(Exception er)
		{
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Update_KhoTT : "+er.getMessage();
		}
		
		return "";
	}
	
	public double Round( double number, int digit )
	{
		double kq = number;
		
		String heso = "1";
		for( int i = 1; i <= digit; i++ )
			heso += "0";
		
		double _heso = Double.parseDouble(heso);
		
		//System.out.println("*** HE SO LA: " + _heso);
		kq = Math.round( number * _heso ) / _heso;
		return kq;
	}
	
	public String Update_TaiKhoan_KeToan (dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, String masp, String tensp, String donvi, String kbh_fk)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		String _MASP = "";
		if(masp.trim().length()>0)
			_MASP = masp;
		
		String _TENSP = "";
		if(tensp.trim().length()>0)
			_TENSP = tensp;
		
		String _DONVI = "";
		if(donvi.trim().length()>0)
			_DONVI = donvi;
		
		String _KBH = "null";
		if(kbh_fk.trim().length()>0)
			_KBH = kbh_fk;
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP, MAHANG, TENHANG, DONVI, KBH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP , MAHANG, TENHANG, DONVI, KBH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public static String getFirstDayOfMonth(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate;
		Calendar c = null;
		try {
			convertedDate = dateFormat.parse(date);
			c = Calendar.getInstance();
			c.setTime(convertedDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		Date lastDayOfMonth = c.getTime();  
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		return sdf.format(lastDayOfMonth);
	}
	
	public static String getLastDayOfMonth(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate;
		Calendar c = null;
		try {
			convertedDate = dateFormat.parse(date);
			c = Calendar.getInstance();
			c.setTime(convertedDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		Date lastDayOfMonth = c.getTime();  
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		return sdf.format(lastDayOfMonth);
	}
	
	static public String getParameter(String querystring, String paraName){
	    String value = "";
	    if(querystring.indexOf("&" +paraName + "=") >=0)
	    {
		    querystring=querystring.substring(querystring.indexOf("&" +paraName + "="));
		    System.out.println("querystring 1:" +querystring);
		    if(querystring.indexOf("&", 1)>0)
		    {
		    	querystring=querystring.substring(0,querystring.indexOf("&", 1));
		    	System.out.println("querystring 2:" +querystring);
		    }
	    }

	    try {
	    	if (querystring != null){
		    	if (querystring.contains("&")){
		    		String [] tmp = querystring.split("&");
		    		for (int i = 0; i < tmp.length; i++)
		    			if (tmp[i].contains(paraName))
		    				if (tmp[i].split("=").length == 2)
		    				{
		    					value = tmp[i].split("=")[1];
		    					return value;
		    				}
		    	}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	    return value;
	}

	static public int[] stringToIntArr(String str, String splitChar){
		if (str.trim().length() == 0)
			return null;
	    String[] arr = str.split(splitChar);
	    int[] arr1 = null;
	    if (arr != null)
	    {
	    	arr1 = new int [arr.length];
	    	for (int i = 0; i < arr.length; i++)
	    	{
	    		try {
	    			arr1[i] = Integer.parseInt(arr[i]);
	    		} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    }

	    return arr1;
	}

	static public String arrayToString(int[] arr)
	{
		String result = "";
		
		for (int i : arr)
		{
			if (result.length() > 0)
				result += ",";
			result += i;
		}
		
		return result;
	}
	
	public static String[] mySplit(String key, String operator) 
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
	
	public static String getCurrentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String getSoChungTuMax(Idbutils db, String tableName, String httt)
	{
		String curDate = geso.traphaco.center.util.Utility.getCurrentDate();
		return getSoChungTuMax(db, tableName, httt, curDate);
	}
	
	public static String getSoChungTuMax(Idbutils db, String tableName, String httt, String date)
	{
		return getSoChungTuMax(db, tableName, httt, "ngayTao", date);
	}

	public static String getSoChungTuMax(Idbutils db, String tableName, String httt, String colunmName, String date)
	{
		if (date == null || date.trim().length() == 0)
			date = geso.traphaco.center.util.Utility.getCurrentDate();
		String maxDate = "";
		String lastDayOfMonth = Utility.getFirstDayOfMonth(date);
		String firstDayOfMonth = Utility.getLastDayOfMonth(date);
		String query = 
			"select isNull(\n" +
			"	case when (MAX(convert(int, soChungTu_So)) + 1) < 10 then '000' + CONVERT(nvarchar, (MAX(convert(int, soChungTu_So)) + 1))\n" +
			"	when (MAX(convert(int, soChungTu_So)) + 1) < 100 then '00' + CONVERT(nvarchar, (MAX(convert(int, soChungTu_So)) + 1))\n" +
			"	when (MAX(convert(int, soChungTu_So)) + 1) < 1000 then '0' + CONVERT(nvarchar, (MAX(convert(int, soChungTu_So)) + 1)) end\n" +
			", '0001')\n" +
			"from " + tableName + "\n" +
			"where " + colunmName + " >= '" + firstDayOfMonth + "'\n" +
			"and " + colunmName + " <= '" + lastDayOfMonth + "' and trangThai <> 2 AND SOCHUNGTU_SO <1000 \n";
			
		if (null != httt && httt.trim().length() > 0)
			query += "and HTTT_FK in (" + httt + ")\n";
		
		System.out.println("cau lay so hoa don:\n" + query + "\n--------------------------------------------");
		try {
			ResultSet rs = db.get(query);
			
			if (rs != null)
			{
				if (rs.next())
					maxDate = rs.getString(1);
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxDate;
	}

	public static String getChuoiChu(String str)
	{
		String result = "";
		
		int size = str.length();
		for (int i = size - 1; i >= 0 && result.trim().length() == 0; i--)
		{
			char c = str.charAt(i);
			if (!(c >= '0' && c <= '9'))
			{
				System.out.println("i la : " + i + ", c la: " + c);
				result = str.substring(0, i + 1);
				return result;
			}
		}
		return result;
	}
	
	public static String getChuoiSo(String str)
	{
		String so = "";
		
		String chu = geso.traphaco.center.util.Utility.getChuoiChu(str);
		if (chu.trim().length() == 0)
			so = str;
		else
			so = str.substring(chu.length());
		
		return so;
	}
	
	public HashMap<String, String> getHmSearch() {
		return hmSearch;
	}
	
	public void setHmSearch(HashMap<String, String> hmSearch) {
		this.hmSearch = hmSearch;
	}
	
	@SuppressWarnings("unchecked")
	public String getSearchFromHM(String userId,String ServerletName, HttpSession session)   
	{  
		String searchQuery="";
		ServerletName=ServerletName.toLowerCase();
		ServerletName=ServerletName.replaceAll("update", "");
		ServerletName=ServerletName.replaceAll("list", "");
		ServerletName=ServerletName.replaceAll("_giay", "");
	    String keyHM= ServerletName+'_'+userId;
	    System.out.println("keyHM get: " + keyHM);
		this.hmSearch = (HashMap<String,String>) session.getAttribute("hmSearch");
		if(null==this.hmSearch)
		{

			this.hmSearch= new HashMap<String,String>();
		}
	    searchQuery=hmSearch.get(keyHM);
	    System.out.println("CÂY SEARCH LẤY RA TỪ HM LÀ :"+searchQuery);
	    return searchQuery;
	}
	
	@SuppressWarnings("unchecked")
	public void setSearchToHM(String userId,HttpSession session,String ServerletName,String searchQuery)   
	{  
		ServerletName=ServerletName.toLowerCase();
		ServerletName=ServerletName.replaceAll("update", "");
		ServerletName=ServerletName.replaceAll("list", "");
		ServerletName=ServerletName.replaceAll("_giay", "");
	    String keyHM= ServerletName+'_'+userId;
	    System.out.println("keyHM set: " + keyHM);
		this.hmSearch= (HashMap<String,String>) session.getAttribute("hmSearch");
		if(null==this.hmSearch)
		{
			this.hmSearch= new HashMap<String,String>();
		}
	    hmSearch.put(keyHM, searchQuery);
	    session.setAttribute("hmSearch", this.hmSearch);
	}

	@SuppressWarnings("rawtypes")
	public static <T> void closeResultSets(T obj)
	{
		Field[] fieldList = obj.getClass().getDeclaredFields();

        for (Field field : fieldList) {
        	field.setAccessible(true);
			Class type = field.getType();
             String name = field.getName();
             System.out.println("name: " + name + ": " + type.toString());
             if (type.equals(ResultSet.class))
             {
            	 System.out.println("name___RS: " + name + ": " + type.toString());
	             try {
	            	 ResultSet rs = (ResultSet)field.get(obj);
	            	 if (rs != null && !rs.isClosed())
	            	 {
		            	 Statement st = rs.getStatement();
		            	 Connection con = st.getConnection();
		            	 if (!con.isClosed())
		            	 {
		            		 if (!st.isClosed())
		            		 {
//		            			 if (!rs.isClosed())
		            				 rs.close();
				            	 st.close();
		            		 }
		            	 }
	            	 }
				} catch (Exception e) {
					e.printStackTrace();
				}
             }
        }
	}
	
	public static double convertStringToDouble(String str)
	{
		try {
			if (str.trim().length() > 0)
			{
				return Double.parseDouble(str.replaceAll(",", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static String getIdNhapp(Idbutils db, String userId) 
	{
		String sql =
						"			select nv.dangnhap,npp.LoaiNPP, npp.khosap, npp.pk_seq,npp.sitecode,npp.ten,npp.tructhuoc_fk  "+ 
						"			from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode=sitecode   "+
						"			where nv.pk_seq='"+userId+"' and nv.trangthai='1'  and nv.PHANLOAI=1 " + //" and npp.isKHACHHANG = '0'  "+ 
						"			union all  "+
						"			select nv.dangnhap,npp.LoaiNPP, npp.khosap, npp.pk_seq,npp.sitecode,gs.ten,npp.tructhuoc_fk  "+ 
						"			from nhanvien nv inner join GIAMSATBANHANG gs on nv.GSBH_FK=gs.PK_SEQ  "+
						"				inner join NHAPHANPHOI npp on npp.SITECODE=nv.CONVSITECODE  "+
						"			where nv.pk_seq='"+userId+"' and nv.trangthai='1'  and nv.PHANLOAI=2 and gs.TRANGTHAI=1 " + //" and npp.isKHACHHANG = '0'  "+
						"			and npp.TRANGTHAI=1 ";
		
		System.out.println(":::: LAY NPP: " + sql);
		String nppId = null;
		ResultSet rs = db.get(sql);
		try
		{
			if(rs != null)
			{
				while(rs.next())
				{
					 nppId=rs.getString("pk_seq");
				}
				rs.close();
			}
		}
		catch(Exception er)
		{
			er.printStackTrace();
		}
		
		return nppId;
	}
	

	public String Check_NgayNghiepVu_KeToan(Idbutils db,String thang,String nam)
	{

		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		String query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc ";
		String thangKS = "12";
		String namKS = "2016";
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					thangKS = rsCheck.getString("THANGKS");
					namKS = rsCheck.getString("NAM");
				}
				rsCheck.close();
			} 
			catch (Exception e) {}
		}
		System.out.println("nam :"+nam);
		System.out.println("namKS :"+namKS);
		System.out.println("thang :"+thang);
		System.out.println("thangKS :"+thangKS);
		
		if( (Integer.parseInt(nam)<Integer.parseInt(namKS))  || (( Integer.parseInt(nam) == Integer.parseInt(namKS)) && (Integer.parseInt(thang) <= Integer.parseInt(thangKS) )))
		{
			return "Không được thực hiện nghiệp vụ kế toán trong thời gian đã khóa sổ";
		}
	
		return "";
		
	}
	
	public String HuyUpdate_TaiKhoan(Idbutils db, String soChungTu, String loaiChungTu)
	{
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		
		//Tạm thời chỉ xóa phát sinh kế toán
		String query = 
			"delete erp_PhatSinhKeToan\n" +
			"where soChungTu = " + soChungTu + " and loaiChungTu = N'" + loaiChungTu +"'\n";
		System.out.println("xoa hach toan: \n" + query + "\n----------------------------------------");
		try {
			if (!db.update(query))
				return "HUTK1.1 Không hủy được định khoản";
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return "";
	}
	
	
	
	public String HuyUpdate_TaiKhoan_NgayChungTu(Idbutils db, String soChungTu, String loaiChungTu,String columnName,String tableName)
	{
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		try
		{
		String query="SELECT "+columnName+" FROM "+tableName +" WHERE PK_SEQ="+soChungTu+" ";
		
		ResultSet rs=db.get(query);
		String ngayChungTu="";
		if(rs.next())
		{
			ngayChungTu = rs.getString(columnName);
		}
		String thang = ngayChungTu.substring(5,7);
		String nam = ngayChungTu.substring(0,4);
		
		String msg= Check_NgayNghiepVu_KeToan(db, thang, nam);
		if(msg.length()>0)
		{
			return msg;
		}
		//Tạm thời chỉ xóa phát sinh kế toán
		query = 
			"delete erp_PhatSinhKeToan\n" +
			"where soChungTu = " + soChungTu + " and loaiChungTu = N'" + loaiChungTu +"'\n";
		System.out.println("xoa hach toan: \n" + query + "\n----------------------------------------");
		
		if (!db.update(query))
			return "HUTK1.1 Không hủy được định khoản";
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return "";
	}
	
	public static String convertDate(String myDate)
	 {
	  String kq = "";
	  
	  if( myDate.contains("/") )
	   kq = myDate.replaceAll("/", "-");
	  if( myDate.contains("-") )
	  {
	   String[] dt = myDate.split("-");
	   if( dt[0].length() < 4 )
	    kq = dt[2] + "-" + dt[1] + "-" + dt[0];
	   else
	    kq = myDate;
	  }
	  
	  return kq;
	 }
	
	public String getNgayKhoaSo(Idbutils db, String tungay) {

		String query = 
				"select top 1 ps.thang,ps.nam \n" +
				"from ERP_TAIKHOAN_NOCO_KHOASO ps\n" +
				"left join ERP_TAIKHOANKT tk on tk.PK_SEQ = ps.TAIKHOANKT_FK\n" + 
				"where  (NAM< year('"+tungay+"') or (thang<month('"+tungay+"') and nam = year('"+tungay+"'))) order by nam desc, thang desc \n";
		
		System.out.println("query lay ngay dang ky so du:\n" + query + "\n------------------------------------------------");
		String ngayDuaSoDu = "1990-01-01";
		
		String thang="01";
		String nam="1990";
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try {
				if (rs.next())
				{
					nam = rs.getString("nam");
					thang = (rs.getInt("thang")<10?"0"+rs.getString("thang"):rs.getString("thang"));
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
		Calendar cal = new GregorianCalendar(Integer.parseInt(nam), Integer.parseInt(thang), 0);
		Date date = cal.getTime();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ngayDuaSoDu=nam+"-"+thang+"-01"; 
		ngayDuaSoDu= sdf.format(date);
	 
		return ngayDuaSoDu;
	
	}
	
	public String[] getNgayThangKhoaSoGanNhat(Idbutils db, String tungay) {

		String query = 
				"select top 1 ps.thang,ps.nam \n" +
				"from ERP_TAIKHOAN_NOCO_KHOASO ps\n" +
				"left join ERP_TAIKHOANKT tk on tk.PK_SEQ = ps.TAIKHOANKT_FK\n" + 
				"where  (NAM< year('"+tungay+"') or (thang<=month('"+tungay+"') and nam = year('"+tungay+"'))) order by nam desc, thang desc \n";
		
		System.out.println("query lay ngay dang ky so du:\n" + query + "\n------------------------------------------------");
		String ngayDuaSoDu = "1990-01-01";
		
		String [] thangnam= new String[2];
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try {
				if (rs.next())
				{
					thangnam[0] = rs.getString("nam");
					thangnam[1]  = (rs.getInt("thang")<10?"0"+rs.getString("thang"):rs.getString("thang"));
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
	 
		return thangnam;
	}
	

	public String[] getNgayThangKhoaSo_KHO_GanNhat(Idbutils db, String tungay) {

		String query = 
				"select top 1 ps.thangks as thang,ps.nam \n" +
				"from ERP_KHOASOTHANG ps\n" +
				"where  (NAM< year('"+tungay+"') or (thangks<=month('"+tungay+"') and nam = year('"+tungay+"'))) order by nam desc, thangks desc \n";
		
		System.out.println("query lay ngay dang ky so du:\n" + query + "\n------------------------------------------------");
		String ngayDuaSoDu = "1990-01-01";
		
		String [] thangnam= new String[2];
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try {
				if (rs.next())
				{
					thangnam[0] = rs.getString("nam");
					thangnam[1]  = (rs.getInt("thang")<10?"0"+rs.getString("thang"):rs.getString("thang"));
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
	 
		return thangnam;
	
	}

	
	static CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder(); // or "ISO-8859-1" for ISO Latin 1

	public static boolean isPureAscii(String v) 
	{
	    return asciiEncoder.canEncode(v);
	}
	
	public static boolean isContainUnicode( String str )
	{
		boolean flag = asciiEncoder.canEncode( str );
		
		if( flag == true ) //chỉ có ký tự ASII
			return false;
		else
			return true;
	}
	
	public static boolean checkUnicode( String[] str )
	{
		for( int i = 0; i < str.length; i++ )
		{
			if( Utility.isContainUnicode( str[i] ) )
				return true;
		}
		
		return false;
	}
	
	public String CheckKhoaSoKho( Idbutils db, String ngaychungtu, String tableNAME, String columnNAME, String id )
	{
		String query =  "";
		if(tableNAME.trim().length() <= 0)
		{
			query =  "	select count(*) as sodong "+
					 "	from ERP_KHOASOTHANG  "+
					 "	where NAM = SUBSTRING('" + ngaychungtu + "', 0, 5 ) and THANGKS = cast( SUBSTRING('" + ngaychungtu + "', 6, 2 ) as int ) ";				
		}
		else
		{
			query =  "	select count(*) as sodong "+
					 "	from ERP_KHOASOTHANG  "+
					 "	where NAM = SUBSTRING( ( select " + columnNAME + " from " + tableNAME + " where pk_seq = " + id + "  ) , 0, 5 ) and THANGKS = cast( SUBSTRING( ( select " + columnNAME + " from " + tableNAME + " where pk_seq = " + id + "  ) , 6, 2 ) as int ) ";
					 
		}
		
		System.out.println("::: CHECK KHOA SO THANG: " + query);
		ResultSet rs = db.get(query);
		int sodong = 0;
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
				}
				rs.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		if( sodong > 0 )
			return "Nghiệp vụ trong tháng đã khóa sổ, vui lòng liên hệ admin để xử lý.";
		
		return "";
	}

	
	public String HuyUpdate_TaiKhoan_NgayChungTu_LoaiHD(Idbutils db, String soChungTu, String loaiChungTu,String loaiHD,String columnName,String tableName)
	{
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		try
		{
		String query="SELECT "+columnName+" FROM "+tableName +" WHERE PK_SEQ="+soChungTu+" ";
		
		ResultSet rs=db.get(query);
		String ngayChungTu="";
		if(rs.next())
		{
			ngayChungTu = rs.getString(columnName);
		}
		String thang = ngayChungTu.substring(5,7);
		String nam = ngayChungTu.substring(0,4);
		
		String msg= Check_NgayNghiepVu_KeToan(db, thang, nam);
		if(msg.length()>0)
		{
			return msg;
		}
		//Tạm thời chỉ xóa phát sinh kế toán
		query = 
			"delete erp_PhatSinhKeToan\n" +
			"where soChungTu = " + soChungTu + " and loaiChungTu = N'" + loaiChungTu +"' AND LOAIHD like '%"+loaiHD+"%' \n";
		System.out.println("xoa hach toan: \n" + query + "\n----------------------------------------");
		
		if (!db.update(query))
			return "HUTK1.1 Không hủy được định khoản";
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return "";
	}
	

	public String Update_NPP_Kho_Sp_Chitiet(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			String solo, String ngayhethan, String ngaynhapkho,double soluong ,double booked,double available ,double tonthoidiem , double dongia) {
		try{
			
			int flag=0;
			if(ngayhethan==null || ngayhethan.length() != 10){
				return "  Không xác định được ngày hết hạn của ID sản phẩm :"+spId+" và số lô : "+solo;
			}
			String query="";
			String querylog="";
			if(!solo.equals("NA"))
			{
			query="  select tonthoidiem,sanpham_fk ,available,soluong,solo ,ngayhethan, sp.ma+ ' '+ sp.ten as ten , " +
					" ISNULL(KHO.GIAMUA,0) AS GIATON   " +
					"  from NHAPP_KHO_CHITIET kho " +
					"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where KBH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
					"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId +" and  solo = N'"+  solo +"' and ngayhethan = '" +ngayhethan +"' and ngaynhapkho ='"+ngaynhapkho+"' ";
			}
			else if(solo.equals("NA"))
			{
				query="  select tonthoidiem,sanpham_fk ,available,soluong ,solo,ngayhethan, sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
				"  from NHAPP_KHO_CHITIET kho " +
				"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where KBH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
				"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId +" and  solo = N'"+  solo +"' and ngayhethan = '2030-12-31' and ngaynhapkho= '"+ngaynhapkho+"'" ;
			}
			double available_ton=0;
			double giaton=0;
			double soluongton=0;
			double tonthoidiem_=0;
			System.out.println("[UTILITY KHO : QUERY LAY SAN PHAM KHO CHI TIET]" +query);
			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				tonthoidiem_= rsCheck.getDouble("tonthoidiem");
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");
				
				if(available < 0 &&  Double.parseDouble(format_1.format(available_ton))  < (-1)*  Double.parseDouble(format_1.format(available))  ){
					System.out.println("ton hien tai "+available_ton +" luong xuat ban "+ (-1)*available);
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "Số lô :"+solo+" -Ngày nhập kho: "+ngaynhapkho+" - Ngày hết hạn : "+ngayhethan+" "+ 
					"  ["+available_ton+"]  không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}
				
				if(soluong < 0  &&  Double.parseDouble(format_1.format(soluongton)) <(-1)* Double.parseDouble(format_1.format(soluong))  ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "Số lô :"+solo+" -Ngày nhập kho: "+ngaynhapkho+" - Ngày hết hạn : "+ngayhethan+" "+ "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}
					
				if(available <0 || soluong <0){
					int i= this.CompareDATE(ngaynhapkho,ngaychungtu);
					if(i>0){
						System.out.println("ngay nhap kho "+ngaynhapkho +"ngay chung tu "+ngaychungtu);
						return "  Chỉ được xuất những lô có ngày nhập nhỏ hơn ngày làm nghiệp vụ:  Số lô "+solo+" -- sản phẩm  : "+rsCheck.getString("ten") + " - Ngày nhập kho : "+ ngaynhapkho +" --Ngay hết han: "+ngayhethan +" Không hợp lệ. " ;
					}
				}
				
				
				query = " Update NHAPP_KHO_CHITIET set booked=round(isnull(booked,0),1)+ round("+booked+",1) , soluong =round(ISNULL(soluong,0),1) + round(" + soluong + ",1), " +
						" AVAILABLE = round(ISNULL(AVAILABLE,0),1) + round(" + available + ",1) "+
						"  where KBH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
						"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId +"  and  solo = N'"+  solo +"'  and ngayhethan = '" +ngayhethan + "' and ngaynhapkho= '"+ngaynhapkho+"'";

				
				querylog="insert into log_kho_solo (nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,solo,ngayhethan,ngaynhapkho,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
						"select N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,solo,ngayhethan,ngaynhapkho,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",1),round("+booked+",1),round(" + available + ",1) from nhapp_kho_chitiet where solo='"+solo+"' and ngayhethan='"+ngayhethan+"' and ngaynhapkho='"+ngaynhapkho+"' and npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";

				//System.out.println("updare kho la "+query);
				
			}else{
				query="SELECT  sp.ma+ ' '+ sp.ten as ten from sanpham sp where pk_seq="+spId;
				ResultSet rssp1=db.get(query);
				String tensp="";
				
				
				if(rssp1.next()){
					tensp=rssp1.getString("ten");
				}
				rssp1.close();
				
				
				if(available <0 || soluong <0){
					int i= this.CompareDATE(ngaynhapkho,ngaychungtu);
					if(i>0){
						return "  Chỉ được xuất những lô có ngày nhập nhỏ hơn ngày làm nghiệp vụ:  Số lô "+solo+" -- sản phẩm  : "+tensp + " - Ngày nhập kho : "+ ngaynhapkho +" --Ngay hết han: "+ngayhethan +" Không hợp lệ. " ;
					}
				}
				 flag=1;
				query=  " INSERT INTO NHAPP_KHO_CHITIET ( KHO_FK,SANPHAM_FK,NPP_FK,KBH_FK, SOLO, NGAYHETHAN, NGAYNHAPKHO,GIAMUA,SOLUONG,BOOKED,AVAILABLE ,TONTHOIDIEM) VALUES  " +
						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", N'"+ solo +"', '"+ ngayhethan +"', '"+ ngaynhapkho +"',"+dongia+",round("+soluong+",1),round("+booked+",1),round("+available+",1),round("+tonthoidiem+",1))";

				querylog="insert into log_kho_solo (nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,solo,ngayhethan,ngaynhapkho,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
						"select N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,solo,ngayhethan,ngaynhapkho,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",1),round("+booked+",1),round(" + available + ",1) from nhapp_kho_chitiet where   solo='"+solo+"' and ngayhethan='"+ngayhethan+"' and ngaynhapkho='"+ngaynhapkho+"' and npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+tensp+ "Số lô :"+solo+" -Ngày nhập kho: "+ngaynhapkho+" - Ngày hết hạn : "+ngayhethan+" "+   "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+tensp+ "Số lô :"+solo+" -Ngày nhập kho: "+ngaynhapkho+" - Ngày hết hạn : "+ngayhethan+" "+  "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}
 
			}
			rsCheck.close();
			System.out.println( nghiepvu+ " CAP NHAT KHO : "+query);
			
			if(flag==0)
			{	
			int resultInt = db.updateReturnInt(querylog);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật log_kho_solo " + querylog;

				}

			}
			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO_CHITIET " + query;

			}
			
			if(flag==1)
			{	
			 resultInt = db.updateReturnInt(querylog);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật log_kho_solo " + querylog;

				}

			}

			
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}
	
	public String Update_NPP_Kho_Sp(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			double soluong ,double booked,double available , double dongia) {
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			int flag=0;
			String querylog="";
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
					"  from NHAPP_KHO kho " +
					"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where KBH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
					"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId;
			System.out.println("[UTILITY KHO : QUERY LAY SAN PHAM KHO TONG]" +query);
			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}


				querylog="insert into log_kho (nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
						"select N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",1),round("+booked+",1),round(" + available + ",1) from nhapp_kho where  npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";

				query = " Update NHAPP_KHO set booked=round(isnull(booked,0),1)+ round("+booked+",1) , soluong =round(ISNULL(soluong,0),1) + round(" + soluong + ",1), " +
						" AVAILABLE = round(ISNULL(AVAILABLE,0),1) + round(" + available + ",1), GIAMUA="+(giaton >0?giaton:dongia)+"  "+
						"  where KBH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
						"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId;
				
				

			}else{
				query=  " INSERT INTO NHAPP_KHO ( KHO_FK,SANPHAM_FK,NPP_FK,KBH_FK,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+","+dongia+",round("+soluong+",1),round("+booked+",1),round("+available+",1))";
				flag=1;
				querylog="insert into log_kho (nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
						"select N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",1),round("+booked+",1),round(" + available + ",1) from nhapp_kho where npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();
			if(flag==0)
			{
			 int resultInt = db.updateReturnInt(querylog);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật log_kho " + querylog;

				}
			}
			
			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO " + query;

			}
			if(flag==1)
			{
			  resultInt = db.updateReturnInt(querylog);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật log_kho " + querylog;

				}
			}
			

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}

	public int CompareDATE(String _date1, String _date2)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	//Date date1 = sdf.parse("2014-10-01");
	    	//Date date2 = sdf.parse("2014-10-01");
	    	
	    	Date date1 = sdf.parse(_date1);
	    	Date date2 = sdf.parse(_date2);
	
	    	//System.out.println(sdf.format(date1));
	    	//System.out.println(sdf.format(date2));
	
	    	return date1.compareTo(date2);
		}
		catch (Exception e) {
			return 0;
		}
	}

	public static boolean checkSoChungTu(Idbutils db,String sochungtu,  String tableName, String httt , String id)
	{
		boolean check = false;
		if(id == null || id == "" || id.toUpperCase() == "NULL")
			id = "1";
		String query = " select COUNT(*) DEM from "+tableName+" where HTTT_FK = '"+httt+"' and sochungtu = '"+sochungtu+"' and TRANGTHAI != '2' and PK_SEQ != " + id;
		System.out.println("kiem tra " + query);
		ResultSet rs = db.get(query);
		if(rs != null){
			try {
				if(rs.next()){
					
					int r = rs.getInt("DEM");
					if(r > 0)
						check = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return check;
	}
	

	public static String generataSoChungTu(Idbutils db,String sochungtu_Chu,  String tableName, String httt, String id, String ngayct){
		// Mac dinh la 4 ki tu
		// Tạo mới
		String sct = "";
		if(id == null || id.trim() == "" || id.toUpperCase() == "NULL")
		{
			id = "1";
		}
		String sokitu = "0000";
//		String query = " select Right('"+sokitu+"' + cast( isnull(max(cast(soChungTu_So as int)),0) + 1 as varchar), 4) maxSCT "
//				+ "from "+tableName+" where HTTT_FK = '"+httt+"' and soChungTu_Chu = '"+sochungtu_Chu+"'";
//		
//		String query = "SELECT CASE \n" +
//				"			WHEN COUNT(*) = 0 THEN (SELECT RIGHT('0000' + CAST( ISNULL(MAX(CAST(SOCHUNGTU_SO AS INT)),0) + 1 AS VARCHAR), 4) \n"+
//				"			FROM ERP_THUTIEN WHERE HTTT_FK = '"+httt+"' AND SOCHUNGTU_CHU = '"+sochungtu_Chu+"' ) \n" +
//				"			WHEN ( SELECT COUNT(*) DEM FROM ERP_THUTIEN WHERE HTTT_FK = '"+httt+"' \n"+
//				" 					AND CAST(SUBSTRING(SOCHUNGTU_CHU, 3,2)AS INT) = MONTH('"+ngayct+"') \n"+
//				"					AND CAST(SUBSTRING(SOCHUNGTU_CHU, 5,4)AS INT) = YEAR('"+ngayct+"') \n"+
//				"					AND PK_SEQ = "+id+" \n"+
//				"				) = 0 THEN (SELECT RIGHT('0000' + CAST( ISNULL(MAX(CAST(SOCHUNGTU_SO AS INT)),0) + 1 AS VARCHAR), 4) \n"+
//				"			FROM ERP_THUTIEN WHERE HTTT_FK = '"+httt+"' AND SOCHUNGTU_CHU = '"+sochungtu_Chu+"' ) \n"+
//				"			ELSE (SELECT SOCHUNGTU_SO FROM  ERP_THUTIEN WHERE PK_SEQ = "+id+") \n"+
//				"		END AS SCTMAX \n"+
//				"FROM ERP_THUTIEN WHERE PK_SEQ = "+id+" \n";
		
		String query = "SELECT CASE \n"+
					   "WHEN COUNT(*) = 0 THEN ( \n"+
	 					" CASE WHEN ( \n"+
	 					" SELECT COUNT(*) DEM FROM "+tableName+" WHERE HTTT_FK = '"+httt+"' \n"+
	 					" AND SOCHUNGTU_CHU = '"+sochungtu_Chu+"' "+
	 					"   ) != 0 \n"+
	 					" THEN (  SELECT TOP 1 RIGHT('0000' +  \n"+
	 					" CAST( ISNULL(CAST(CASE WHEN SOCHUNGTU_SO = null THEN 0 ELSE SOCHUNGTU_SO END AS INT),0) + 1 AS VARCHAR), 4) \n"+
	 					" FROM "+tableName+" WHERE HTTT_FK = '"+httt+"'  AND SOCHUNGTU_CHU = '"+sochungtu_Chu+"' \n"+
	 					" GROUP BY soChungTu_So  \n"+
	 					" ORDER BY max(PK_SEQ) desc  ) \n"+
	 					" ELSE '0001' \n"+
	 					" END ) \n"+
	 					" ELSE (CASE WHEN ( "+
	 					" SELECT COUNT(*) DEM FROM "+tableName+" WHERE HTTT_FK = '"+httt+"' \n"+
	 					" AND SOCHUNGTU_CHU = '"+sochungtu_Chu+"' "+
	 					" AND PK_SEQ = "+id+") != 1 \n"+
	 					" THEN (  SELECT TOP 1 RIGHT('0000' +  \n"+
	 					" CAST( ISNULL(CAST(CASE WHEN SOCHUNGTU_SO = null THEN 0 ELSE SOCHUNGTU_SO END AS INT),0) + 1 AS VARCHAR), 4) \n"+
	 					" FROM "+tableName+" WHERE HTTT_FK = '"+httt+"'  AND SOCHUNGTU_CHU = '"+sochungtu_Chu+"' \n"+
	 					" GROUP BY soChungTu_So  \n"+
	 					" ORDER BY max(PK_SEQ) desc  ) \n"+
	 					" ELSE (SELECT RIGHT('0000' +  CAST( ISNULL(CAST(SOCHUNGTU_SO AS INT),0) AS VARCHAR), 4) FROM  "+tableName+" WHERE PK_SEQ = "+id+")\n"+
	 					" END \n"+
	 					" ) END AS SCTMAX  \n"+
	 					" FROM "+tableName+" WHERE PK_SEQ = " +id + " and SOCHUNGTU_CHU = '"+sochungtu_Chu+"' ";
		
		System.out.println("generate SCT : " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null){
			try {
				if(rs.next()){
					sct = rs.getString("SCTMAX");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sct;
		
	}

	public String getNppId() {
		return nppId;
	}
	public void setNppId(String nppId) {
		this.nppId = nppId;
	}
	public static void main(String []arg)
	{
		String str = "100.0";
		
		System.out.println( Utility.isPureAscii(str) );
		
		System.out.println( Utility.isContainUnicode(str) );
		
		/*try
		{
			String sql="Bột canh 14% 250grx40 NĐ (lốc)".replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			System.out.println( URLDecoder.decode(sql,"UTF-8"));
		} 
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}*/
	}
	
	
	public String Update_TaiKhoan_Diengiai_NPP_TUOINO (Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc,String Diengiai,String isNpp_No,String isNpp_Co,String sohoadon,String machungtu,String thoihanno)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		String _isNpp_No = "null";
		if(isNpp_No.trim().length() > 0)
			_isNpp_No = isNpp_No;
		
		String _isNpp_Co = "null";
		if(isNpp_Co.trim().length() > 0)
			_isNpp_Co = isNpp_Co;
		
		
		
		
		
		//GHI CO
		if(Float.parseFloat(_CO) != 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' " +
					"and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
						" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
						" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = " insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						" select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,DIENGIAI,ISNPP,sohoadon,machungtu,thoihanno) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "',N'"+Diengiai+"'," +_isNpp_Co+ " ,'" +sohoadon+ "','" + machungtu+ "', "+thoihanno+")  ";
			
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) != 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { 
				e.printStackTrace();
			}
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC,diengiai,ISNPP,sohoadon,machungtu,thoihanno) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "',N'"+Diengiai+"',"+_isNpp_No+",'"+sohoadon+"','"+machungtu+"',"+thoihanno+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				System.out.println("aaaaaaaaaaaaaaa"+query);
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		 
		
		return msg;
	}
}