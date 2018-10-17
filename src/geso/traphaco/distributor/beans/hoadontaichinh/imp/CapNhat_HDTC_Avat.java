package geso.traphaco.distributor.beans.hoadontaichinh.imp;

import geso.traphaco.distributor.beans.donhang.ISanpham;
import geso.traphaco.distributor.beans.donhang.imp.Sanpham;
import geso.traphaco.distributor.beans.hoadontaichinh.IHoadontaichinh;
import geso.traphaco.center.db.sql.dbutils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.catalina.connector.Request;

public class CapNhat_HDTC_Avat 
{
	private static final long serialVersionUID = 1L;
		
	dbutils db;
	
	public static void main(String[] arg)
	{
		CapNhat_HDTC_Avat update_HD = new CapNhat_HDTC_Avat();
		
		String msg = update_HD.Thuc_Hien_Update();
		
		 //String msg = update_HD.Thuc_Hien_Update_ETC();
		System.out.println("--KET QUA UPDATE: " + msg);
		
	}

	private String Thuc_Hien_Update_ETC() 
	{
		db= new dbutils();
	  String query = 
		"select a.HOADON_FK AS HDID , SUM(round(a.SOLUONG*a.DONGIA - a.CHIETKHAU , 0))  as BVAT , SUM(round(a.SOLUONG*a.DONGIA - a.CHIETKHAU , 0)* a.VAT/100) as VAT,  \n"+
		"      SUM(round(a.SOLUONG*a.DONGIA - a.CHIETKHAU , 0)) + SUM(round(a.SOLUONG*a.DONGIA - a.CHIETKHAU , 0)* a.VAT/100) as AVAT \n"+
		"from ERP_HOADONNPP_SP a inner join ERP_HOADONNPP b on a.HOADON_FK=b.PK_SEQ  \n"+
		"where b.TRANGTHAI not in (3,5)  \n"+
		"group by a.HOADON_FK ";
		System.out.println("Câu query "+ query);
		ResultSet rs = db.get(query);
		
		if(rs!= null)
		{
			try
			{
				db.getConnection().setAutoCommit(false);
				String msg="";
				while(rs.next())
				{
					String sql = " update ERP_HOADONNPP set TONGTIENBVAT= "+ rs.getDouble("BVAT")  +" , " +
							     "        TONGTIENAVAT= "+ rs.getDouble("AVAT")  +", VAT = "+ rs.getDouble("VAT")  +" " +
							     " where pk_seq = "+ rs.getString("HDID")  +" ";
					System.out.println("Câu cập nhât: "+ sql);
					if(!db.update(sql))
					{
						msg = "Không thể cập nhật ERP_HOADONNPP : " + rs.getString("HDID") + " ";
						db.getConnection().rollback();	
						return msg;
					}
				}
				rs.close();
				db.getConnection().commit();
			}catch(Exception e)
			{
				try {
					
					db.getConnection().rollback();
				} 
				catch (Exception e1) {}
				
				e.printStackTrace();
			}				
			
		}
			
		return "";
	}

	private String Thuc_Hien_Update() 
	{
		db= new dbutils();
		String query = 
			"select hd.PK_SEQ AS HDID , \n"+
			"      (HOADON_CT.tongtien - HOADON_CT.Chietkhau) as BVAT, (HOADON_CT.VAT - HOADON_CT.VAT_KM) as VAT, \n"+
			"      round((HOADON_CT. tongtien + HOADON_CT .VAT ) - (HOADON_CT. Chietkhau  + HOADON_CT.VAT_KM),0) AS AVAT \n"+
			"from \n"+
			"    HOADON hd inner join \n"+
			"(     select AA.HOADON_FK, AA.tongtien ,  AA.VAT , BB.Chietkhau  , BB.VAT as VAT_KM \n"+
			"		from \n"+
			"			(select HOADON_fk ,  SUM( SOLUONG*DONGIA ) as tongtien , SUM (SOLUONG* DONGIA*VAT /100) as VAT   \n"+
			"			from HOADON_SP \n"+
			"			group by HOADON_FK \n"+
			"			) AA \n"+
			"		inner join \n"+
			"			(SELECT HOADON_FK, SUM(ROUND(A.chietkhau,0)) as Chietkhau ,SUM(ROUND(A.VAT,0)) as VAT \n"+
			"			 FROM  HOADON_DDH hd inner join \n"+
			"				(select DONHANG_FK, N'CN5' as diengiai, sum(chietkhau) as chietkhau, sum(chietkhau)*thueVAT/100 as VAT, 1 as STT, 0 as tichluyQUY " +
			"				 from DONHANG_SANPHAM   \n"+
			"				 where  thueVAT = '5' group by thueVAT ,DONHANG_FK  \n"+
			"					union   \n"+
			"						select DONHANG_FK, N'CN10' as diengiai, sum(chietkhau) as chietkhau, sum(chietkhau)*thueVAT/100 as VAT, 2 as STT, 0 as tichluyQUY  \n"+
			"						from DONHANG_SANPHAM  \n"+
			"						where  thueVAT = '10' group by thueVAT ,DONHANG_FK \n"+
			"					union  \n"+
			"						select DONHANG_FK, a.diengiai, a.thanhtoan / ( 1 + ptTHUE / 100 ) as chietkhau, (a.thanhtoan / ( 1 + ptTHUE / 100 ))*a.ptTHUE/100 as VAT, 3 as STT, tichluyQUY \n"+  
			"						from DUYETTRAKHUYENMAI_DONHANG a inner join TIEUCHITHUONGTL b on a.duyetkm_fk = b.PK_SEQ   \n"+
			"						 \n"+
			"					union  \n"+
			"						select DONHANG_FK, a.maloai as diengiai, a.chietkhau, a.chietkhau*a.ptVAT/100 as VAT, 4 as STT, 0 as tichluyQUY   \n"+
			"						from DONHANG_CHIETKHAUBOSUNG a     \n"+
			"					) A on hd.DDH_FK = A.DONHANG_FK \n"+
			"			 GROUP BY HOADON_FK \n"+
			"	\n"+
			"	\n"+
			"			) BB  \n"+
			"		ON AA.HOADON_FK=BB.HOADON_FK  \n"+
			") HOADON_CT on hd.PK_SEQ = HOADON_CT.HOADON_FK \n"+
			"	left join KHACHHANG kh on kh.PK_SEQ= hd.KHACHHANG_FK \n"+
			" \n"+
			"where  hd.LOAIHOADON=0 and hd.NPP_FK =106182  \n"+
			" \n"+
			"UNION ALL  \n"+
			" \n"+
			"select a.HOADON_FK as HDID,  SUM(a.SOLUONG*a.DONGIA) as BVAT ,  SUM(a.SOLUONG*a.DONGIA*a.VAT/100 ) as VAT, \n"+
			"       SUM(a.SOLUONG*a.DONGIA) + SUM(a.SOLUONG*a.DONGIA*a.VAT/100 ) as AVAT \n"+
			"from HOADON_CTKM_TRAKM  a inner join HOADON b on a.HOADON_FK=b.PK_SEQ  \n"+
			"where b.LOAIHOADON=1 and b.NPP_FK =106182   \n"+
			"group by a.HOADON_FK  ";
		System.out.println("Câu query "+ query);
		ResultSet rs = db.get(query);
		
		if(rs!= null)
		{
			try
			{
				db.getConnection().setAutoCommit(false);
				String msg="";
				while(rs.next())
				{
					String sql = " update HOADON set TONGTIENBVAT= "+ rs.getDouble("BVAT")  +" , " +
							     "        TONGTIENAVAT= "+ rs.getDouble("AVAT")  +", VAT = "+ rs.getDouble("VAT")  +" " +
							     " where pk_seq = "+ rs.getString("HDID")  +" ";
					System.out.println("Câu cập nhât: "+ sql);
					if(!db.update(sql))
					{
						msg = "Không thể cập nhật HOADON : " + rs.getString("HDID") + " ";
						db.getConnection().rollback();	
						return msg;
					}
				}
				rs.close();
				db.getConnection().commit();
			}catch(Exception e)
			{
				try {
					
					db.getConnection().rollback();
				} 
				catch (Exception e1) {}
				
				e.printStackTrace();
			}				
			
		}
			
		return "";
	}
	
	
}
