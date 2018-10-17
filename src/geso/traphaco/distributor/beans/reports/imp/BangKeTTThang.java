package geso.traphaco.distributor.beans.reports.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.reports.IBangKeTTThang;
import geso.traphaco.distributor.db.sql.dbutils;

public class BangKeTTThang implements IBangKeTTThang, Serializable {

	private String thang;
	private String nam;
	private String userId;
	private ResultSet rsBangKeTTThang;
	private String nppID;	
	private ResultSet rsTDV , rsChiNhanh;
	
	private String tdvId , chinhanhId;
	
	private String nppTen, tdvTen;

	private String quy, tuthang, denthang , tungay, denngay , t1,t2,t3;
	private String loai , tt;
	private String khtt;
	

	public  BangKeTTThang() {
		thang = "";
		nam = "";
		nppID ="";
		tdvId = "";
		nppTen = "";
		tdvTen = "";
		loai = "";
		quy="";
		tt="";
		chinhanhId="";
		khtt="";
	}
	
	geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
	public void init() {
		// TODO Auto-generated method stub
		
		if(loai.equals("1") || loai.equals("2")){
			if(quy.length() > 0 )
			{
				if(quy.equals("1"))
				{
					tungay = "01";
					denngay = "31";
				}else if(quy.equals("2"))
				{
					tungay = "01";
					denngay = "30";
				}
				else if(quy.equals("3"))
				{
					tungay = "01";
					denngay = "30";
				}	
				else if(quy.equals("4"))
				{
					tungay = "01";
					denngay = "31";
				}
			}
		}
		
		if(tt.equals("1"))
		{
			nppID = chinhanhId;
		}
		
		String sql="";
		dbutils db = new dbutils();
		String tableNAME = "NHOMSANPHAM";
		String query= "select b.VUNG_FK from NHAPHANPHOI a inner join TINHTHANH b on b.PK_SEQ=a.TINHTHANH_FK  where a.PK_SEQ='"+this.nppID+"' ";
		ResultSet			 rs1=db.get(query);
		try
		{
			while(rs1.next())
			{
				if(rs1.getString("Vung_fk").equals("100002"))
				{
					tableNAME = "NHOMSANPHAM_MIENNAM_sanpham";
				}else  if(rs1.getString("Vung_fk").equals("100003"))
				{
					tableNAME = "NHOMSANPHAM_MIENTRUNG_sanpham";
				}
				else 
				{
					tableNAME = "NHOMSANPHAM_SANPHAM";
				}
			}
			rs1.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
		String ck=
		  	"\n          	select hoadon_fk,SUM((ck.chietkhau*( 1+ ck.thueVAT/100)))  as AVAT_CK, sum((ck.chietkhau)) as BVAT_CK,0 as Type         "+   
			  "\n          	from         "+   
			  "\n          	(          "+   
			  "\n          			select b.hoadon_fk, N'CN5' as diengiai, sum(chietkhau) as chietkhau, thueVAT          "+   
			  "\n          			from DONHANG_SANPHAM  a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk          "+   
			  "\n          			where a.thueVAT = '5'         "+   
			  "\n          			group by b.hoadon_fk, thueVAT          "+   
			  "\n          			union    all       "+   
			  "\n          			select b.hoadon_fk, N'CN10' as diengiai, sum(chietkhau) as chietkhau, thueVAT          "+   
			  "\n          			from DONHANG_SANPHAM  a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk          "+   
			  "\n          			where a.thueVAT = '10'         "+   
			  "\n          			group by b.hoadon_fk, thueVAT          "+   
			  "\n          			union  all       "+   
			  "\n          			select b.hoadon_fk, a.diengiai, sum( a.thanhtoan / ( 1  + ptTHUE / 100 )  ) as chietkhau, ptTHUE as thueVAT         "+   
			  "\n          			from DUYETTRAKHUYENMAI_DONHANG a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk             "+   
			  "\n          			group by b.hoadon_fk, a.diengiai, ptTHUE           "+   
			  "\n          		union           "+   
			  "\n          			select b.hoadon_fk, a.maloai as diengiai, sum(a.chietkhau) as chietkhau, ptVAT as thueVAT         "+   
			  "\n          			from DONHANG_CHIETKHAUBOSUNG a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk          "+   
			  "\n          			where chietkhau != 0         "+   
			  "\n          			group by b.hoadon_fk, a.maloai, ptVAT          "+   
			  "\n          	)          "+   
			  "\n          	ck left join LOAICHIETKHAU loai on ck.diengiai = loai.maloai          "+   
			  "\n          	inner join HOADON hd on hd.PK_SEQ=ck.HOADON_FK       "+   
			  "\n          	where hd.LOAIHOADON =0 and hd.TRANGTHAI in (2,4)        "+   
			  "\n          	group by HOADON_FK       ";
		  	
		  	String checkt7 = nam+"-"+thang;
			
		  	if(!checkt7.contains("2014-07"))
		  	{
		  		ck=
		  		" select hoadon_fk,SUM((ck.chietkhau*( 1+ ck.thueVAT/100)))  as AVAT_CK, sum((ck.chietkhau)) as BVAT_CK,0 as Type "+
		  	 	" from HOADON_CHIETKHAU ck" +
		  	 	" group by hoadon_fk   ";
		  	}
		  	
		  	String ck1 = "select a.hoadon_fk , sum(a.AVAT_CK) as AVAT_CK , sum(a.BVAT_CK) as BVAT_CK , a.Type , a.KHACHHANG_FK from (	\n" +
		  	"	select hoadon_fk,SUM((ck.chietkhau*( 1+ ck.thueVAT/100)))  as AVAT_CK, sum((ck.chietkhau)) as BVAT_CK,0 as Type  , KHACHHANG_FK\n" +
		  	"	from HOADON_CHIETKHAU ck \n" +
		  	"	inner join DONHANG d on ck.donhang_fk = d.PK_SEQ\n" +
		  	"	where ck.hoadon_fk in (select PK_Seq from HOADON where NGAYXUATHD >= '2014-08-01'  and NGAYXUATHD <= '2014-09-30' and LOAIHOADON =0 and D.DDKD_FK = "+tdvId+" " +
		  	"	and TRANGTHAI in (2,4)) and  ck.hoadon_fk in (select HOADON_FK from HOADON_SP) \n" +
		  	"	group by hoadon_fk , KHACHHANG_FK\n" +
		  	"	union all 	\n" +
		  	"	select hoadon_fk,SUM((ck.chietkhau*( 1+ ck.thueVAT/100)))  as AVAT_CK, sum((ck.chietkhau)) as BVAT_CK,0 as Type   , KHACHHANG_FK   \n" +
		  	"	from            \n" +
		  	"	(             \n" +
		  	"	select b.hoadon_fk, N'CN5' as diengiai, sum(a.CHIETKHAU) as chietkhau, thueVAT           \n" +
		  	"	from DONHANG_SANPHAM  a \n" +
		  	"	inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk       \n" +
		  	"	inner join DONHANG d on d.PK_SEQ = b.DDH_FK      \n" +
		  	"	where a.thueVAT = '5'      and D.DDKD_FK = "+tdvId+"      \n" +
		  	"	group by b.hoadon_fk, thueVAT             \n" +
		  	"	union    all          \n" +
		  	"	select b.hoadon_fk, N'CN10' as diengiai, sum(a.chietkhau) as chietkhau, thueVAT             \n" +
		  	"	from DONHANG_SANPHAM  a \n" +
		  	"	inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk    \n" +
		  	"	inner join DONHANG d on d.PK_SEQ = b.DDH_FK               \n" +
		  	"	where a.thueVAT = '10'     and D.DDKD_FK = "+tdvId+"       \n" +
		  	"	group by b.hoadon_fk, thueVAT             \n" +
		  	"	union  all          \n" +
		  	"	select b.hoadon_fk, a.diengiai, sum( a.thanhtoan / ( 1  + ptTHUE / 100 )  ) as chietkhau, ptTHUE as thueVAT            \n" +
		  	"	from DUYETTRAKHUYENMAI_DONHANG a \n" +
		  	"	inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk  \n" +
		  	"	inner join DONHANG d on d.PK_SEQ = b.DDH_FK    where  D.DDKD_FK = "+tdvId+"                 \n" +
		  	"	group by b.hoadon_fk, a.diengiai, ptTHUE              \n" +
		  	"	union              \n" +
		  	"	select b.hoadon_fk, a.maloai as diengiai, sum(a.chietkhau) as chietkhau, ptVAT as thueVAT            \n" +
		  	"	from DONHANG_CHIETKHAUBOSUNG a \n" +
		  	"	inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk   \n" +
		  	"	inner join DONHANG d on d.PK_SEQ = b.DDH_FK              \n" +
		  	"	where a.chietkhau != 0     and D.DDKD_FK = "+tdvId+"       \n" +
		  	"	group by b.hoadon_fk, a.maloai, ptVAT             \n" +
		  	"	)             \n" +
		  	"	ck left join LOAICHIETKHAU loai on ck.diengiai = loai.maloai             \n" +
		  	"	inner join HOADON hd on hd.PK_SEQ=ck.HOADON_FK          \n" +
		  	"	where hd.LOAIHOADON =0 and hd.TRANGTHAI in (2,4)  and hd.NgayXuatHD  like  '2014-07%' and hd.PK_SEQ in (select HOADON_FK from HOADON_SP)      \n" +
		  	"	group by HOADON_FK   , KHACHHANG_FK      \n" +
		  	"	) a	\n" +
		  	"	group by a.hoadon_fk , a.Type , a.KHACHHANG_FK";
		  	
		  	String ck1a = 
		  		"\n		select a.hoadon_fk , sum(a.AVAT_CK) as AVAT_CK , sum(a.BVAT_CK) as BVAT_CK , a.Type , a.KHACHHANG_FK from (	" +
		  		"\n		select hoadon_fk,SUM((ck.chietkhau*( 1+ ck.thueVAT/100)))  as AVAT_CK, sum((ck.chietkhau)) as BVAT_CK,0 as Type  , KHACHHANG_FK	" +
		  		"\n		from HOADON_CHIETKHAU ck 	" +
		  		"\n		inner join DONHANG d on ck.donhang_fk = d.PK_SEQ	" +
		  		"\n		where ck.hoadon_fk in (select PK_Seq from HOADON where NGAYXUATHD >= '"+nam+"-"+tuthang+"-"+tungay+"'  and NGAYXUATHD <= '"+nam+"-"+denthang+"-"+denngay+"' and LOAIHOADON =0 and TRANGTHAI in (2,4)) and D.DDKD_FK = "+tdvId+" " +
		  		"\n		group by hoadon_fk , KHACHHANG_FK	";
				
		
		if(loai.equals("0")){
			if(nppID.length() > 0 && tdvId.length() > 0 && nam.length() > 0 && thang.length() > 0 ){
				sql =  "\n select  a.KHACHHANG_FK, a.maFAST, a.tenkh as tenkh , a.diachi,  a.duyet  , a.xuatkhau, round(SUM( a.doanhso - a.AVAT_CK),0)  as doanhso from ( " + 
				 "\n SELECT DH.PK_SEQ, DH.KHACHHANG_FK, kh.maFAST, kh.TEN as tenkh , kh.diachi, case when duyet.pk_seq is null then N'Không Đạt' else N'Đạt' end as duyet  , kh.xuatkhau, " +
				 "\n cast(sum(dh.giamua * dh.soluong*(1+DH.vat/100)) as numeric(18,0) ) as doanhso , ck.AVAT_CK" +
				 "\n FROM  " +
				 "\n (    SELECT distinct HD.PK_SEQ, nsp.NSP_FK as nhomsp,HD.NGAYXUATHD, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, " +
					    "\n  DH.KHO_FK, DH.KBH_FK, HD_SP.SANPHAM_FK, HD_SP.DONGIA as giamua,                  " +
					    "\n   HD_SP.SOLUONG,(cast ((HD_SP.CHIETKHAU*(1+HD_SP.VAT/100)) as numeric(18,0))) as  CHIETKHAU , HD_SP.VAT as vat 	" +
					    "\n             FROM HOADON HD 	" +
					    "\n             inner join HOADON_DDH HD_DH on HD_DH.HOADON_FK = HD.PK_SEQ	" +
						"\n			inner join DONHANG DH on DH.PK_SEQ = HD_DH.DDH_FK	" +
					    "\n            INNER JOIN HOADON_SP  HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK                  " +           
					    "\n             inner join NHOMSANPHAM_SANPHAM nsp on  HD_SP.SANPHAM_FK=nsp.SP_FK                   " +        
					    "\n             WHERE hd.loaihoadon=0 and  hd.trangthai not in ( 1 , 3, 5 )    AND  HD.NGAYXUATHD like '"+nam+"-"+thang+"%' " +
					    "\n  ) DH     " +
					    "\n INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ     " +
						"\n   INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ  " +
					    "\n  INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ      " +
						"\n   LEFT JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ     " +
						"\n  LEFT JOIN KENHBANHANG KBH ON DH.KBH_FK = KBH.PK_SEQ     " +
						"\n   LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ     " +
						"\n   inner join KHACHHANG kh on kh.PK_SEQ= DH.KHACHHANG_FK  " +
						"\n  inner join NHOMSANPHAM ns on ns.PK_SEQ=DH.nhomsp " +
						"\n  left join ( " +
						"\n   select d.PK_SEQ, khachhang_fk, d.npp_fk from DUYETBANDUNGGIA_KHACHHANG dk	" +
						"\n inner join DUYETBANDUNGGIA d on d.PK_SEQ = dk.duyet_fk	" +
						"\n where d.TRANGTHAI = 1 and d.thang = "+thang.substring(1)+" and d.nam = "+nam+"	" +
						"\n  ) as duyet on duyet.khachhang_fk = DH.KHACHHANG_FK and duyet.npp_fk = DH.NPP_FK " +
					    "\n	left join        " +   
		  				"\n          (  "+ck+"" +
		  				"\n          )as ck on ck.HOADON_FK= DH.PK_SEQ       "+   
						"\n   WHERE  DH.NGAYXUATHD like '"+nam+"-"+thang+"%' and DH.NPP_FK = "+nppID+" and DH.DDKD_FK = "+tdvId+" " +
						"\n group by   DH.KHACHHANG_FK,kh.TEN , kh.DIACHI ,  kh.maFAST , kh.XuatKhau  , duyet.PK_SEQ , ck.AVAT_CK , DH.PK_SEQ) a" +
						"\n  group by   a.KHACHHANG_FK, a.maFAST, a.tenkh , a.diachi,  a.duyet  , a.xuatkhau";
			
			System.out.println("Bao cao ds0 :"+sql);
			this.rsBangKeTTThang = db.get(sql);
			}
		}
		else if(loai.equals("1"))
		{ 
			if(nppID.length() > 0 && tdvId.length() > 0 && nam.length() > 0 && quy.length() > 0 )
			{
				int quyHIENTAI = Integer.parseInt(this.quy);
				int namHIENTAI = Integer.parseInt(this.nam);
				
				int thangBATDAU = 0;
				int thangKETTHUC = 0;
				
				int thangBATDAU_QUYTIEPTHEO = 0;
				int thangKETTHUC_QUYTIEPTHEO = 0;
				
				int nam_QUYTIEPTHEO = Integer.parseInt(this.nam);
				
				if( quyHIENTAI == 1 )
				{
					thangBATDAU = 1;
					thangKETTHUC = 3;
					
					thangBATDAU_QUYTIEPTHEO = 4;
					thangKETTHUC_QUYTIEPTHEO = 6;
				}
				else if( quyHIENTAI == 2 )
				{
					thangBATDAU = 4;
					thangKETTHUC = 6;
					
					thangBATDAU_QUYTIEPTHEO = 7;
					thangKETTHUC_QUYTIEPTHEO = 9;
				}
				else if( quyHIENTAI == 3 )
				{
					thangBATDAU = 7;
					thangKETTHUC = 9;
					
					thangBATDAU_QUYTIEPTHEO = 10;
					thangKETTHUC_QUYTIEPTHEO = 12;
				}
				else if( quyHIENTAI == 4 )
				{
					thangBATDAU = 10;
					thangKETTHUC = 12;
					
					thangBATDAU_QUYTIEPTHEO = 1;
					thangKETTHUC_QUYTIEPTHEO = 3;
					
					nam_QUYTIEPTHEO=namHIENTAI+1;
				}
				
				String condition = "";
				if(this.tdvId.trim().length() > 0)
				{
					condition += " and DS.KHACHHANG_FK in ( select khachhang_fk from KHACHHANG_TUYENBH " +
								"								where TBH_FK in ( select pk_seq from TUYENBANHANG where DDKD_FK = '" + this.tdvId + "' ) ) ";
				}
				
				condition += " and DS.KHACHHANG_FK in ( select khachhang_fk from DUYETBANDUNGGIA a inner join DUYETBANDUNGGIA_KHACHHANG_HUONGQUY b on a.pk_seq = b.duyet_fk where a.NPP_FK = '" + this.nppID + "' and a.thang = '" + thangKETTHUC + "' and a.nam = '" + namHIENTAI + "' and a.trangthai = '1'   ) ";
				
				
					query = 
						 "declare @nppId numeric(18,0),@TuThang int,@Nam int ,@DenThang int   \n "+      
					   "   set @nppId="+this.nppID+"   \n "+      
					   "   set @Nam='"+namHIENTAI+"'   \n "+      
					   "   set @DenThang='"+thangKETTHUC+"'   \n "+      
					   "   set @TuThang='"+thangBATDAU+"'   \n "+      
					   "      \n "+      
					   "   select  ddkdTEN, khID, maFAST, TEN as khTEN   \n "+      
					   "   	,isnull( sum([100002]), 0 ) as DS_XANH, isnull( sum([100003]), 0) as DS_HHBG, isnull( sum([100004]), 0) as DS_KHAC,     \n "+      
					   "   	round ( cast (     \n "+      
					   "   		isnull( sum([100002]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )      \n "+      
					   "   				as numeric(18, 1) ) - 0.5 + 0.05, 0 ) as PT_XANH,    \n "+      
					   "   	round ( cast (    \n "+      
					   "   		isnull( sum([100003]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )     \n "+      
					   "   			as numeric(18, 1) ) - 0.5 + 0.05, 0 ) as PT_HHBG,   \n "+      
					   "   ISNULL( (     \n "+      
					   "   	select sum( toTAL )     \n "+      
					   "   	from    \n "+      
					   "   	(    \n "+      
					   "   		select a.khachhang_fk, sum(tongtienAVAT)  as toTAL      \n "+      
					   "   		from HOADON a      \n "+      
					   "   		where month( a.ngayxuatHD ) >= @TuThang     \n "+      
					   "   		and month( a.ngayxuatHD ) <= @DenThang and year( a.ngayxuatHD ) = @Nam and a.trangthai in ( 2, 4 )    \n "+      
					   "   		and a.loaihoadon = '0' and a.NPP_FK = @nppId       \n "+      
					   "   			and a.npp_fk = @nppId      \n "+      
					   "   			and a.khachhang_fk = khID    \n "+      
					   "   			and a.khachhang_fk in (      \n "+      
					   "   									select khachhang_fk    \n "+      
					   "   									from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk       \n "+      
					   "   									where trangthai = '1' and loaict = '0' and cast(thang as int) = month( a.ngayxuatHD )   \n "+      
					   "   									 and nam =@Nam and db.NPP_FK = a.npp_fk and db_kh.khachhang_fk = a.khachhang_fk 	       \n "+      
					   "   								  )       \n "+      
					   "   		group by a.khachhang_fk    \n "+      
					   "   	union ALL    \n "+      
					   "   		select a.khachhang_fk, sum(tongtienAVAT)  as toTAL      \n "+      
					   "   		from HOADON a        \n "+      
					   "   		where month( a.ngayxuatHD ) >= @TuThang  and month( a.ngayxuatHD ) <= @DenThang and year( a.ngayxuatHD ) = @Nam and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0' and a.NPP_FK = @nppId       \n "+      
					   "   			and a.npp_fk = @nppId      \n "+      
					   "   			and a.khachhang_fk = khID    \n "+      
					   "   			and cast( month(a.ngayxuatHD) as varchar(10) ) not in     \n "+      
					   "   			(      \n "+      
					   "   									select thang from DUYETBANDUNGGIA db    \n "+      
					   "   									inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk       \n "+      
					   "   									where trangthai = '1' and loaict = '0'   \n "+      
					   "   									 and cast(thang as int)= month( a.ngayxuatHD )  and nam = year( a.ngayxuatHD )   \n "+      
					   "   									  and db.NPP_FK = @nppId and db_kh.khachhang_fk = a.khachhang_fk 	       \n "+      
					   "   								  )       \n "+      
					   "   			and cast( month(a.ngayxuatHD) as varchar(10) ) in     \n "+      
					   "   			 ( select thang from DUYETBANDUNGGIA db   where trangthai = '1' and loaict = '0'    \n "+      
					   "   				and cast(thang as int) = month( a.ngayxuatHD ) and nam = year( a.ngayxuatHD )   \n "+      
					   "   				and db.NPP_FK = @nppId and  khongtinhtienthuve = '0' )    \n "+      
					   "   		group by a.khachhang_fk    \n "+      
					   "   	)    \n "+      
					   "   	TV    \n "+      
					   "   ), 0 ) as tienthuve    \n "+      
					   "   from    \n "+      
					   "   (    \n "+      
					   "   	select KH.PK_SEQ as khID, kh.MAFAST, kh.TEN,    \n "+      
					   "   			(	select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ        \n "+      
					   "   						inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ       \n "+      
					   "   				where cc.KHACHHANG_FK = KH.pk_seq and bb.npp_fk=@nppId     \n "+      
					   "   			) as ddkdTEN,     \n "+      
					   "   		TOTAL.NSP_FK, TOTAL.maNHOM, TOTAL.tongGiatri as doanhsoNHOM    \n "+      
					   "   	from    \n "+      
					   "   	(    \n "+      
					   "   		select DS.KHACHHANG_FK, NSP_FK, maNHOM, sum(DS.toTAL) as tongGiatri       \n "+      
					   "   		from      \n "+      
					   "   		(      \n "+      
					   "   			select a.khachhang_fk, c.NSP_FK, case c.NSP_FK when 100002 then 'CQX5' when 100002 then 'CQB' else 'KHAC' end as maNHOM,  isnull( cast( sum( ( cast( ( soluong * dongia ) as numeric(18, 0) )  ) * ( 1 + b.VAT / 100 ) ) as numeric(18, 0) ), 0)  as toTAL       \n "+      
					   "   			from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk        \n "+      
					   "   					inner join  "+tableNAME+" c on b.sanpham_fk = c.SP_FK       \n "+      
					   "   			where month( a.ngayxuatHD ) >= @TuThang  and month( a.ngayxuatHD ) <= @DenThang and year( a.ngayxuatHD ) = @Nam and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0'        \n "+      
					   "   				and a.npp_fk = @nppId       \n "+      
					   "   				and a.khachhang_fk in (      \n "+      
					   "   										select khachhang_fk    \n "+      
					   "   										from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk       \n "+      
					   "   										where trangthai = '1' and loaict = '0' and cast(thang as int) >= @TuThang and cast(thang as int) <= @DenThang and nam = '"+namHIENTAI+"' and db.NPP_FK = @nppId 	       \n "+      
					   "   									  )       \n "+      
					   "   				and cast( month(a.ngayxuatHD) as varchar(10) ) in       \n "+      
					   "   					  (      \n "+      
					   "   						select distinct thang from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk       \n "+      
					   "   						where trangthai = '1' and loaict = '0' and cast(thang as int) >= @TuThang and cast(thang as int) <= @DenThang and nam = '2014' and db.NPP_FK = @nppId and db_kh.khachhang_fk = a.khachhang_fk	       \n "+      
					   "   					  )       \n "+      
					   "   			group by a.khachhang_fk, c.NSP_FK     \n "+      
					   "   		union ALL      \n "+      
					   "   			select a.khachhang_fk, c.NSP_FK, case c.NSP_FK when 100002 then 'CQX5' when 100002 then 'CQB' else 'KHAC' end as maNHOM,  isnull( cast( sum( ( cast( ( soluong * dongia ) as numeric(18, 0) )  ) * ( 1+  b.VAT / 100 ) ) as numeric(18, 0) ), 0)  as toTAL       \n "+      
					   "   			from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk        \n "+      
					   "   					inner join  NHOMSANPHAM_SANPHAM c on b.sanpham_fk = c.SP_FK       \n "+      
					   "   			where month( a.ngayxuatHD ) >= @TuThang  and month( a.ngayxuatHD ) <= @DenThang and year( a.ngayxuatHD ) = @Nam and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0'        \n "+      
					   "   				and a.npp_fk = @nppId       \n "+      
					   "   				and cast( month(a.ngayxuatHD) as varchar(10) ) not in        \n "+      
					   "   					  (      \n "+      
					   "   						select distinct thang from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk       \n "+      
					   "   						where trangthai = '1' and loaict = '0' and cast(thang as int) >= @TuThang and cast(thang as int) <= @DenThang and nam =@Nam and db.NPP_FK = @nppId and db_kh.khachhang_fk = a.khachhang_fk	       \n "+      
					   "   					  )       \n "+      
					   "   				and cast( month(a.ngayxuatHD) as varchar(10) ) in     \n "+      
					   "   					  (      \n "+      
					   "   						select distinct thang from DUYETBANDUNGGIA db      \n "+      
					   "   						where trangthai = '1' and loaict = '0' and cast(thang as int) >= @TuThang and cast(thang as int) <= @DenThang and nam =@Nam and db.NPP_FK = @nppId and  khongtinhdoanhso = '0'	       \n "+      
					   "   					  )       \n "+      
					   "   			group by a.khachhang_fk, c.NSP_FK     \n "+      
					   "   		)      \n "+      
					   "   		DS    \n "+      
					   "   		where NSP_FK in ( 100002, 100003, 100004  )  "+condition+"     \n "+      
					   "   		group by DS.KHACHHANG_FK, NSP_FK, maNHOM     \n "+      
					   "   	)    \n "+      
					   "   	TOTAL inner join KHACHHANG kh on TOTAL.khachhang_fk = kh.pk_seq    \n "+      
					   "   )    \n "+      
					   "   as p pivot ( sum(doanhsoNHOM)  for nsp_fk  in  ( [100002], [100003], [100004] ) ) as t    \n "+      
					   "   group by ddkdTEN, khID, maFAST, TEN ";
			
				System.out.println("Bao cao ds1 :"+query);
				this.rsBangKeTTThang = db.get(query);
			}
		}
		else if(loai.equals("2")){ 
			if(nppID.length() > 0  && nam.length() > 0  && tdvId.length() > 0 && quy.length() > 0 ){
			int quyHIENTAI = Integer.parseInt(this.quy);
			int namHIENTAI = Integer.parseInt(this.nam);
			
			int thangBATDAU = 0;
			int thangKETTHUC = 0;
			
			int thangBATDAU_QUYTIEPTHEO = 0;
			int thangKETTHUC_QUYTIEPTHEO = 0;
		
			
			int nam_QUYTIEPTHEO = Integer.parseInt(this.nam);
			
			if( quyHIENTAI == 1 )
			{
				thangBATDAU = 1;
				thangKETTHUC = 3;
				
				thangBATDAU_QUYTIEPTHEO = 4;
				thangKETTHUC_QUYTIEPTHEO = 6;
			}
			else if( quyHIENTAI == 2 )
			{
				thangBATDAU = 4;
				thangKETTHUC = 6;
				
				thangBATDAU_QUYTIEPTHEO = 7;
				thangKETTHUC_QUYTIEPTHEO = 9;
			}
			else if( quyHIENTAI == 3 )
			{
				thangBATDAU = 7;
				thangKETTHUC = 9;
				
				thangBATDAU_QUYTIEPTHEO = 10;
				thangKETTHUC_QUYTIEPTHEO = 12;
			}
			else if( quyHIENTAI == 4 )
			{
				thangBATDAU = 10;
				thangKETTHUC = 12;
				
				thangBATDAU_QUYTIEPTHEO = 1;
				thangKETTHUC_QUYTIEPTHEO = 3;
				
				nam_QUYTIEPTHEO=namHIENTAI+1;
			}
			
			
			String condition = "";
			if(this.tdvId.trim().length() > 0)
			{
				condition += " and DS.KHACHHANG_FK in ( select khachhang_fk from KHACHHANG_TUYENBH " +
						"								where TBH_FK in ( select pk_seq from TUYENBANHANG where DDKD_FK = '" + tdvId + "' ) ) ";
			}
		condition += " and DS.KHACHHANG_FK in ( select khachhang_fk from DUYETBANDUNGGIA a inner join DUYETBANDUNGGIA_KHACHHANG_HUONGQUY b on a.pk_seq = b.duyet_fk where a.NPP_FK = '" + this.nppID + "' and a.thang = '" + thangKETTHUC + "' and a.nam = '" + namHIENTAI + "' and a.trangthai = '1'   ) ";

			
			
			 tableNAME = "";
		
			
			query= "select b.VUNG_FK from NHAPHANPHOI a inner join TINHTHANH b on b.PK_SEQ=a.TINHTHANH_FK  where a.PK_SEQ='"+this.nppID+"' ";
			ResultSet  rs=db.get(query);
			try
	    {
	      while(rs.next())
	      {
	      	if(rs.getString("Vung_fk").equals("100002"))
	      	{
	      		tableNAME = "NHOMSANPHAM_MIENNAM_SANPHAM";
	      	}else  if(rs.getString("Vung_fk").equals("100003"))
	      	{
	      		tableNAME = "NHOMSANPHAM_MIENTRUNG_SANPHAM";
	      	}
	      	else
	      		tableNAME = "NHOMSANPHAM_SANPHAM";
	      	
	      }
	    } catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
			
			query="select ddkdTEN, khID, maFAST,  khTEN , DIACHI,MaHD,cmnd,A1.KHACHHANG_FK,A1.DS_KHAC,A1.DS_XANH,A1.DS_HHBG,A2.[1] t1,A2.[2] t2,A2.[3] t3 from (\n"+    
					"select ddkdTEN, khID, maFAST, TEN as khTEN , DIACHI,MaHD,isnull(cmnd,'') as cmnd,KHACHHANG_FK, \n"+    
					" 	isnull( sum([100002]), 0 ) as DS_XANH, isnull( sum([100003]), 0) as DS_HHBG, isnull( sum([100004]), 0) as DS_KHAC, \n"+    
		/*			" 	round ( cast ( \n"+    
					" 		isnull( sum([100002]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )  \n"+    
					" 				as numeric(18, 1) ) - 0.5 + 0.05, 0 ) as PT_XANH, \n"+    
					"	round ( cast ( \n"+    
					" 		isnull( sum([100003]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )  \n"+    
					" 			as numeric(18, 1) ) - 0.5 + 0.05, 0 ) as PT_HHBG \n"+    
		*/			
					"		isnull( sum([100002]), 0 ) * 100 / ((  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) ))  \n " +
				    "				  as PT_XANH, \n" +
			    	"		isnull( sum([100003]), 0 ) * 100 /( (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) ) ) \n " +
			    	"			 as PT_HHBG \n " +
				
					" \n"+    
					" from \n"+    
					" ( \n"+    
					" 	select KH.PK_SEQ as khID, kh.MAFAST, kh.TEN, kh.DIACHI,kh.MaHD,cmnd,KHACHHANG_FK, \n"+    
					"			(	select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ    \n"+    
					" 						inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ   \n"+    
					" 				where cc.KHACHHANG_FK = KH.pk_seq  \n"+    
					" 			) as ddkdTEN, \n"+    
					"  		TOTAL.NSP_FK, TOTAL.maNHOM, TOTAL.tongGiatri as doanhsoNHOM \n"+    
					" 	from  \n"+    
					" 	( \n"+    
					" 		select DS.KHACHHANG_FK, NSP_FK, maNHOM, sum(DS.toTAL) as tongGiatri   \n"+    
					" 		from  \n"+    
					" 		(  \n"+    
					" 			select a.khachhang_fk, c.NSP_FK, case c.NSP_FK when 100002 then 'CQX5' when 100002 then 'CQB' else 'KHAC' end as maNHOM,  isnull( sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100),0) ), 0)  as toTAL   \n"+    
					" 			from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk    \n"+    
					" 					inner join "+tableNAME+" c on b.sanpham_fk = c.SP_FK   \n"+    
					" 			where a.ddkd_fk="+tdvId+" and month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"' and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0'    \n"+    
					" 				and a.npp_fk = '"+this.nppID+"'   \n"+    
					" 				and a.khachhang_fk in (  \n"+    
					" 										select khachhang_fk from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk   \n"+    
					" 										where trangthai = '"+thangBATDAU+"' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '"+this.nppID+"' 	  \n"+    
					"  									  ) \n"+    
					"  and a.KHACHHANG_FK in (select KHACHHANG_FK from DUYETSUUNGHO dsuh inner join DUYETSUUNGHO_KHACHHANG  dsuhkh on dsuhkh.duyet_fk=dsuh.PK_SEQ \n"+
					"  where dsuhkh.npp_fk="+this.nppID+" and dsuh.QUY="+this.quy+" and dsuh.NAM="+nam+" and dsuh.TRANGTHAI=1 \n"+
					"	)  \n"+
					"   				and cast( month(a.ngayxuatHD) as varchar(10) ) in   \n"+    
					" 					  (  \n"+    
					" 						select distinct thang from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk   \n"+    
					" 						where trangthai = '"+thangBATDAU+"' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '"+this.nppID+"' and db_kh.khachhang_fk = a.khachhang_fk	   \n"+    
					" 					  )   \n"+    
					" 			group by a.khachhang_fk, c.NSP_FK  \n"+    
					" 		union ALL   \n"+    
					" 			select a.khachhang_fk, c.NSP_FK, case c.NSP_FK when 100002 then 'CQX5' when 100002 then 'CQB' else 'KHAC' end as maNHOM, isnull( sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100),0) ), 0)  as toTAL   \n"+    
					" 			from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk    \n"+    
					" 					inner join NHOMSANPHAM_MIENNAM_SANPHAM c on b.sanpham_fk = c.SP_FK   \n"+    
					" 			where  a.ddkd_fk="+tdvId+" and month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"' and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0'    \n"+    
					" 				and a.npp_fk = '"+this.nppID+"'   \n"+    
					" 				and cast( month(a.ngayxuatHD) as varchar(10) ) not in   \n"+    
					"  					  (  \n"+    
					" 						select distinct thang from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk   \n"+    
					" 						where trangthai = '"+thangBATDAU+"' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '"+this.nppID+"' and db_kh.khachhang_fk = a.khachhang_fk	  \n"+    
					"  					  )   \n"+    
					"  and a.KHACHHANG_FK in (select KHACHHANG_FK from DUYETSUUNGHO dsuh inner join DUYETSUUNGHO_KHACHHANG  dsuhkh on dsuhkh.duyet_fk=dsuh.PK_SEQ \n"+
					"  where dsuhkh.npp_fk="+this.nppID+" and dsuh.QUY="+this.quy+" and dsuh.NAM="+nam+" and dsuh.TRANGTHAI=1 \n"+
					" 	)  \n"+
					" 				and cast( month(a.ngayxuatHD) as varchar(10) ) in \n"+    
					"  					  (   \n"+    
					" 						select distinct thang from DUYETBANDUNGGIA db  \n"+    
					" 						where trangthai = '"+thangBATDAU+"' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '"+this.nppID+"' and  khongtinhdoanhso = '0'	)   \n"+    
					" 					    \n"+    
					" 			group by a.khachhang_fk, c.NSP_FK  \n"+    
					"		)  \n"+    
					" 		DS \n"+    
					" 		where NSP_FK in ( 100002, 100003, 100004  )  \n"+    
					"  and DS.KHACHHANG_FK in ( select khachhang_fk from DUYETBANDUNGGIA a inner join DUYETBANDUNGGIA_KHACHHANG_HUONGQUY b on a.pk_seq = b.duyet_fk where a.NPP_FK = '"+this.nppID+"' and a.thang = '"+thangKETTHUC+"' and a.nam = '"+namHIENTAI+"' and a.trangthai = '"+thangBATDAU+"'   ) 		group by DS.KHACHHANG_FK, NSP_FK, maNHOM  \n"+    
					" 	)  \n"+    
					"	TOTAL inner join KHACHHANG kh on TOTAL.khachhang_fk = kh.pk_seq \n"+    
					") \n"+    
					"as p pivot ( sum(doanhsoNHOM)  for nsp_fk  in  ( [100002], [100003], [100004] ) ) as t \n"+    
					"group by ddkdTEN, khID, maFAST, TEN, DIACHI,MaHD,DIACHI,cmnd,KHACHHANG_FK \n"+    
					") A1 \n"+    
					"left join \n"+    
					"(\n"+    
					"select * from(\n"+    
					"select thuve.KHACHHANG_FK,thuve.NPP_FK,sum(thuve.toTAL) as ThuVe,thuve.thang   \n"+    
					"    from    \n"+    
					"    (   \n"+    
					"    	select hd.KHACHHANG_FK,hd.NPP_FK,SUM(hd.tongtienAVat) as toTAL,hd.thang   \n"+    
					"    	from    \n"+    
					"    	(   \n"+    
					"    		select month( a.ngayxuatHD ) as Thang,year( a.ngayxuatHD ) as Nam,   \n"+    
					"    		a.KHACHHANG_FK, a.NPP_FK, sum( isnull((a.tongtienavat),0)) as tongtienAVat    \n"+    
					"    		from HOADON a     \n"+    
					"    		left join     \n"+    
					"    		(    \n"+    
					"    			SELECT b.HOADON_FK, SUM(b.SOTIENCANTRU) as SOTIENCANTRU    \n"+    
					"    			FROM CANTRUCONGNO a inner join CANTRUCONGNO_HOADON b on a.PK_SEQ = b.CANTRUCONGNO_FK    \n"+    
					"    			WHERE a.TRANGTHAI = 1    \n"+    
					"    			GROUP BY b.HOADON_FK    \n"+    
					"    		)  b on a.PK_SEQ = b.HOADON_FK    \n"+    
					"    		where a.ddkd_fk="+tdvId+" and month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"'    \n"+    
					"    			and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 )			   \n"+    
					"    			and a.loaihoadon = '0' and a.NPP_FK = '"+this.nppID+"'    \n"+    
					"    		group by a.KHACHHANG_FK,a.NPP_FK,month( a.ngayxuatHD ),year( a.ngayxuatHD )   \n"+    
					"    	)as hd   \n"+    
					"    	inner join    \n"+    
					"    	(   \n"+    
					"    		select khachhang_fk,a.THANG,a.NAM,a.NPP_FK from DUYETBANDUNGGIA a inner join    \n"+    
					"    			DUYETBANDUNGGIA_KHACHHANG b on a.pk_seq = b.duyet_fk    \n"+    
					"    		where a.NPP_FK="+this.nppID+" and a.THANG>='"+thangBATDAU+"' and a.THANG<='"+thangKETTHUC+"' and a.NAM='"+namHIENTAI+"'   \n"+    
					"    	)as dg on dg.khachhang_fk=hd.KHACHHANG_FK and hd.Nam=dg.NAM and hd.Thang=dg.THANG   \n"+    
					"    	group by hd.KHACHHANG_FK,hd.NPP_FK,hd.thang   \n"+    
					"    union all    \n"+    
					"    	select hd.KHACHHANG_FK,hd.NPP_FK,SUM(hd.tongtienAVat) as toTAL,thang   \n"+    
					"    	from    \n"+    
					"    	(   \n"+    
					"    		select month( a.ngayxuatHD ) as Thang,year( a.ngayxuatHD ) as Nam,   \n"+    
					"    			a.KHACHHANG_FK, a.NPP_FK, sum( isnull((a.tongtienavat),0)) as tongtienAVat    \n"+    
					"    			from HOADON a     \n"+    
					"    			left join     \n"+    
					"    			(    \n"+    
					"    				SELECT b.HOADON_FK, SUM(b.SOTIENCANTRU) as SOTIENCANTRU    \n"+    
					"    				FROM CANTRUCONGNO a inner join CANTRUCONGNO_HOADON b on a.PK_SEQ = b.CANTRUCONGNO_FK    \n"+    
					"    				WHERE a.TRANGTHAI = 1    \n"+    
					"    				GROUP BY b.HOADON_FK    \n"+    
					"    			)  b on a.PK_SEQ = b.HOADON_FK    \n"+    
					"    			where a.ddkd_fk="+tdvId+" and month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"' and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 )			   \n"+    
					"    				and a.loaihoadon = '0' and a.NPP_FK = '"+this.nppID+"'    \n"+    
					"    			group by a.KHACHHANG_FK,a.NPP_FK,month( a.ngayxuatHD ),year( a.ngayxuatHD )   \n"+    
					"    		) as hd    \n"+    
					"    		where 1=1    \n"+    
					"  and   		not exists   \n"+    
					"    		(   \n"+    
					"    			select khachhang_fk   \n"+    
					"    			from DUYETBANDUNGGIA a inner join  DUYETBANDUNGGIA_KHACHHANG b on a.pk_seq = b.duyet_fk    \n"+    
					"    			where a.NPP_FK="+this.nppID+" and a.THANG>='"+thangBATDAU+"' and a.THANG<='"+thangKETTHUC+"' and a.NAM='"+namHIENTAI+"'   \n"+    
					"    			and b.khachhang_fk=hd.KHACHHANG_FK   \n"+    
					"    			 and a.THANG=hd.Thang and a.NAM=hd.Nam   \n"+    
					"    		)   \n"+    
					"    		and     \n"+    
					"    		(          \n"+    
					"    			select COUNT(*)    \n"+    
					"    			from DUYETBANDUNGGIA a     \n"+    
					"    			where a.NPP_FK="+this.nppID+" and a.THANG>='"+thangBATDAU+"' and a.THANG<='"+thangKETTHUC+"' and a.NAM='"+namHIENTAI+"'   \n"+    
					"    			and a.khongtinhtienthuve=0    			 and a.THANG=hd.Thang and a.NAM=hd.Nam   \n"+    
					"    		)>0    \n"+    
					"    	group by hd.KHACHHANG_FK,hd.NPP_FK,thang   \n"+    
					"    )as thuve     \n"+    
					"    group by thuve.KHACHHANG_FK,thuve.NPP_FK,thuve.thang \n"+    
					"  ) dsthang  \n"+    
					"   pivot(sum(thuve) for thang in (["+thangBATDAU+"],["+(thangBATDAU+1)+"],["+thangKETTHUC+"])) as DATA) A2\n"+    
					"   on A1.KHACHHANG_FK=A2.KHACHHANG_FK";
			System.out.println("---INIT CHIET KHAU QUY ung ho: " + query);
			
			this.rsBangKeTTThang = db.getScrol(query);
			}
		}
		else if(loai.equals("3")){ 
			if(nppID.length() > 0  && nam.length() > 0 && thang.length() > 0 ){
			/*	sql = "SELECT  DH.KHACHHANG_FK, kh.maFAST, kh.TEN as tenkh , kh.diachi, case when duyet.pk_seq is null then N'Không Đạt' else N'Đạt' end as duyet  , kh.xuatkhau, " +
						 " cast(sum(dh.giamua * dh.soluong*(1+DH.vat/100)) as numeric(18,0) ) as doanhso " +
						 " FROM  " +
						 " (       " +
						 "        SELECT  nsp.NSP_FK as nhomsp,DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK,    " +
						 "                ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK,      " +
						 "                ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) AS GIAMUA , (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG,     " +
						 "                (-1)*ISNULL(DH_SP1.CHIETKHAU, 0) AS CHIETKHAU  ,0 as vat   " +
						 "          FROM  DONHANGTRAVE DH LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ     " +
						 "        LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  " +
						 "        inner join NHOMSANPHAM_SANPHAM nsp on nsp.SP_FK=   DH_SP.SANPHAM_FK " +
					    "        WHERE DH.TRANGTHAI = 3 AND DH.NGAYNHAP like '"+nam+"-"+thang+"%'   " +
					    "     UNION ALL      " +
					    "         SELECT  nsp.NSP_FK as nhomsp,DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, DH_SP.GIAMUA,   " + 
					    "               DH_SP.SOLUONG,(cast ((DH_SP.CHIETKHAU*(1+DH_SP.THUEVAT/100)) as numeric(18,0))) as  CHIETKHAU , DH_SP.THUEVAT as vat   " +
					    "         FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK  " +
					    "                           inner join NHOMSANPHAM_SANPHAM nsp on  DH_SP.SANPHAM_FK=nsp.SP_FK " +
					    "          WHERE DH.TRANGTHAI  !=2     AND  DH.NGAYNHAP like '"+nam+"-"+thang+"%' " +
					    " ) DH     " +
					    " INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ     " +
						"   INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ  " +
					    "  INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ      " +
						"   LEFT JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ     " +
						"  LEFT JOIN KENHBANHANG KBH ON DH.KBH_FK = KBH.PK_SEQ     " +
						"   LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ     " +
						"   inner join KHACHHANG kh on kh.PK_SEQ= DH.KHACHHANG_FK  " +
						"  inner join NHOMSANPHAM ns on ns.PK_SEQ=DH.nhomsp " +
						" left join ( " +
						" select * from DUYETBANDUNGGIA_KHACHHANG dk	" +
						" inner join DUYETBANDUNGGIA d on d.PK_SEQ = dk.duyet_fk	" +
						" where d.TRANGTHAI = 1 and d.thang = "+thang.substring(1)+" and d.nam = "+nam+"	" +
						" ) as duyet on duyet.khachhang_fk = DH.KHACHHANG_FK and duyet.npp_fk = DH.NPP_FK " +
						"   WHERE  DH.NGAYNHAP like '"+nam+"-"+thang+"%' and DH.NPP_FK = "+nppID + 
						" group by   DH.KHACHHANG_FK,kh.TEN , kh.DIACHI ,  kh.maFAST , kh.XuatKhau , duyet.PK_SEQ";
			*/
				int thang1=Integer.parseInt(thang);
				sql="select "+ 
							"\n		( select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   "+
							"\n			inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ where cc.KHACHHANG_FK=dsthangHIENTAI.KHACHHANG_FK "+   
							"\n	) as tdvTEN ,khTEN,ma as mahd,khMa,khDiaChi,MaHD,dsthangHIENTAI.DoanhThu,ds.DoanhSo as TongDS,dsBOGA.DoanhSo as dsBOGA, "+
							"\n		(   "+
							"\n			select sum(a.chietkhau) as ckle   "+
							"\n			from tieuchithuongtl_tieuchi a inner join tieuchithuongtl b on b.pk_seq=a.thuongtl_fk   "+
							"\n			where b.pk_seq in ( select thuongtl_fk from tieuchithuongtl_loainpp where loainpp =   "+
							"\n			( select loainpp from nhaphanphoi where pk_seq = dsthangHIENTAI.npp_fk) )and b.nam='"+nam+"' "+    
							"\n			and b.trangthai = '1' and b.loaict = '0'   and ISNULL(ptChietKhau,0)=0  "+
							"\n		)as ptChietKhau  "+
							"\n	from   "+
							"\n	(   "+
							"\n		select a.npp_fk,a.khachhang_fk ,sum( a.tongtienavat) as DoanhThu,b.TEN as khTEN,b.mahd as ma,b.maFAST as khMa,b.DIACHI as khDiaChi,b.MaHD,b.pt_chietkhau as		ptChietKhau  "+
							"\n		from HOADON a "+
							"\n		inner join KHACHHANG b on b.PK_SEQ=a.KHACHHANG_FK  inner join  (select  KHACHHANG_fk,aa.npp_fk from DUYETBANDUNGGIA aa  inner join DUYETBANDUNGGIA_KHACHHANG bb on bb.duyet_fk=aa.PK_SEQ  where aa.THANG='"+thang1+"' and aa.NAM='"+nam+"'  ) as dg on dg.khachhang_fk=a.KHACHHANG_FK and dg.npp_fk=a.npp_fk "+  
							"\n		where a.NPP_FK="+this.nppID+" and  "+
							"\n			month(ngayxuathd) = '"+thang1+"' and year(ngayxuathd) = '"+nam+"' and isnull(loaihoadon, '0') = '0'  and  a.npp_fk = '"+this.nppID+"' and a.trangthai not in (1,3,5)  and  a.npp_fk = '"+this.nppID+"'  "+
							"\n		group by a.NPP_FK,a.KHACHHANG_FK,b.TEN,b.maFAST,b.DIACHI,b.MaHD,pt_ChietKhau,b.mahd  "+
							"\n	)as dsthangHIENTAI   "+
							"\n	left join  "+
							"\n	(   "+
							"\n		select SUM( ROUND(ROUND( b.soluong*b.dongia,0) * (1+b.vat/100 ),0)) as DoanhSo,a.KHACHHANG_FK  "+
							"\n		from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ  "+
							"\n		where a.trangthai not in ( 1 , 3, 5 )  and month(ngayxuathd) = '"+thang1+"' and year(ngayxuathd) = '"+nam+"' and isnull(loaihoadon, '0') = '0'   and  a.npp_fk = '"+this.nppID+"' "+  
							"\n			and  a.npp_fk = '"+this.nppID+"' "+      
							"\n		group by a.KHACHHANG_FK	 "+
							"\n	)as ds on ds.KHACHHANG_FK=dsthangHIENTAI.KHACHHANG_FK  "+
							"\n	left join  "+
							"\n	( "+
							"\n		select SUM( ROUND(ROUND( b.soluong*b.dongia,0) * (1+b.vat/100 ),0)) as DoanhSo,a.KHACHHANG_FK  "+
							"\n		from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ  "+
							"\n			inner join "+tableNAME+" c on c.SP_FK=b.SANPHAM_FK  "+
							"\n			where a.trangthai not in ( 1 , 3, 5 )  and month(ngayxuathd) =  '"+thang1+"' and year(ngayxuathd) = '"+nam+"' and isnull(loaihoadon, '0') = '0'  and  a.npp_fk = '"+this.nppID+"' "+   
							"\n			and  a.npp_fk = '"+this.nppID+"' and c.NSP_FK=100003  "+
							"\n		group by a.KHACHHANG_FK	"+
							"\n )as dsBOGA on dsBOGA.KHACHHANG_FK=dsthangHIENTAI.KHACHHANG_FK"+
							"\n	  where dsthangHIENTAI.DoanhThu>(select isnull(HanMucDoanhThu,0) from NhaPhanPhoi where pk_Seq='"+this.nppID+"')  "+
							"\n	  and  (isnull(dsBOGA.DoanhSo,0)/ ds.DoanhSo *100) > (select tumuc from MucDat where npp_fk='"+this.nppID+"')  "+
							"\n   and (isnull(dsBOGA.DoanhSo,0)/ ds.DoanhSo *100)< (select DenMuc from MucDat where npp_fk='"+this.nppID+"')  "; 
							
			System.out.println("Bao cao ds3 :"+sql);
			this.rsBangKeTTThang = db.getScrol(sql);
			}
		}
		else if(loai.equals("4"))
		{ 
			
			if(nppID.length() > 0  && nam.length() > 0 && quy.length() > 0 )
			{	
				int quyHIENTAI = Integer.parseInt(this.quy);
				int namHIENTAI = Integer.parseInt(this.nam);
				
				int thangBATDAU = 0;
				int thangKETTHUC = 0;
				
				int thangBATDAU_QUYTIEPTHEO = 0;
				int thangKETTHUC_QUYTIEPTHEO = 0;
			
				
				int nam_QUYTIEPTHEO = Integer.parseInt(this.nam);
				
				if( quyHIENTAI == 1 )
				{
					thangBATDAU = 1;
					thangKETTHUC = 3;
					
					thangBATDAU_QUYTIEPTHEO = 4;
					thangKETTHUC_QUYTIEPTHEO = 6;
				}
				else if( quyHIENTAI == 2 )
				{
					thangBATDAU = 4;
					thangKETTHUC = 6;
					
					thangBATDAU_QUYTIEPTHEO = 7;
					thangKETTHUC_QUYTIEPTHEO = 9;
				}
				else if( quyHIENTAI == 3 )
				{
					thangBATDAU = 7;
					thangKETTHUC = 9;
					
					thangBATDAU_QUYTIEPTHEO = 10;
					thangKETTHUC_QUYTIEPTHEO = 12;
				}
				else if( quyHIENTAI == 4 )
				{
					thangBATDAU = 10;
					thangKETTHUC = 12;
					
					thangBATDAU_QUYTIEPTHEO = 1;
					thangKETTHUC_QUYTIEPTHEO = 3;
					
					nam_QUYTIEPTHEO=namHIENTAI+1;
				}
				
				
				String condition = "";
				if(this.tdvId.trim().length() > 0)
				{
					condition += " and DS.KHACHHANG_FK in ( select khachhang_fk from KHACHHANG_TUYENBH " +
							"								where TBH_FK in ( select pk_seq from TUYENBANHANG where DDKD_FK = '" + tdvId + "' ) ) ";
				}
			condition += " and DS.KHACHHANG_FK in ( select khachhang_fk from DUYETBANDUNGGIA a inner join DUYETBANDUNGGIA_KHACHHANG_HUONGQUY b on a.pk_seq = b.duyet_fk where a.NPP_FK = '" + this.nppID + "' and a.thang = '" + thangKETTHUC + "' and a.nam = '" + namHIENTAI + "' and a.trangthai = '1'   ) ";

				
				
				 tableNAME = "";
			
				
				query= "select b.VUNG_FK from NHAPHANPHOI a inner join TINHTHANH b on b.PK_SEQ=a.TINHTHANH_FK  where a.PK_SEQ='"+this.nppID+"' ";
				ResultSet  rs=db.get(query);
				try
		    {
		      while(rs.next())
		      {
		      	if(rs.getString("Vung_fk").equals("100002"))
		      	{
		      		tableNAME = "NHOMSANPHAM_MIENNAM_SANPHAM";
		      	}else  if(rs.getString("Vung_fk").equals("100003"))
		      	{
		      		tableNAME = "NHOMSANPHAM_MIENTRUNG_SANPHAM";
		      	}
		      	else
		      		tableNAME = "NHOMSANPHAM_SANPHAM";
		      	
		      }
		    } catch (SQLException e)
		    {
		      e.printStackTrace();
		    }
				
				query="select ddkdTEN, khID, maFAST,  khTEN , DIACHI,MaHD,cmnd,A1.KHACHHANG_FK,A1.DS_KHAC,A1.DS_XANH,A1.DS_HHBG,A2.[1] t1,A2.[2] t2,A2.[3] t3 from (\n"+    
						"select ddkdTEN, khID, maFAST, TEN as khTEN , DIACHI,MaHD,isnull(cmnd,'') as cmnd,KHACHHANG_FK, \n"+    
						" 	isnull( sum([100002]), 0 ) as DS_XANH, isnull( sum([100003]), 0) as DS_HHBG, isnull( sum([100004]), 0) as DS_KHAC, \n"+    
				/*		" 	round ( cast ( \n"+    
						" 		isnull( sum([100002]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )  \n"+    
						" 				as numeric(18, 1) ) - 0.5 + 0.05, 0 ) as PT_XANH, \n"+    
						"	round ( cast ( \n"+    
						" 		isnull( sum([100003]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )  \n"+    
						" 			as numeric(18, 1) ) - 0.5 + 0.05, 0 ) as PT_HHBG \n"+    
				*/		
					/*	" 		isnull( sum([100002]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )  \n"+    
						" 				 as PT_XANH, \n"+    
						" 		isnull( sum([100003]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )  \n"+    
						" 			 as PT_HHBG \n"+  
					*/	"		isnull( sum([100002]), 0 ) * 100 / ((  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) ))  \n " +
					    "				  as PT_XANH, \n" +
				    	"		isnull( sum([100003]), 0 ) * 100 /( (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) ) ) \n " +
				    	"			 as PT_HHBG \n " +
				
						" \n"+    
						" from \n"+    
						" ( \n"+    
						" 	select KH.PK_SEQ as khID, kh.MAFAST, kh.TEN, kh.DIACHI,kh.MaHD,cmnd,KHACHHANG_FK, \n"+    
						"			(	select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ    \n"+    
						" 						inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ   \n"+    
						" 				where cc.KHACHHANG_FK = KH.pk_seq  \n"+    
						" 			) as ddkdTEN, \n"+    
						"  		TOTAL.NSP_FK, TOTAL.maNHOM, TOTAL.tongGiatri as doanhsoNHOM \n"+    
						" 	from  \n"+    
						" 	( \n"+    
						" 		select DS.KHACHHANG_FK, NSP_FK, maNHOM, sum(DS.toTAL) as tongGiatri   \n"+    
						" 		from  \n"+    
						" 		(  \n"+    
						" 			select a.khachhang_fk, c.NSP_FK, case c.NSP_FK when 100002 then 'CQX5' when 100002 then 'CQB' else 'KHAC' end as maNHOM,  isnull( sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100),0) ), 0)  as toTAL  \n"+    
						" 			from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk    \n"+    
						" 					inner join NHOMSANPHAM_MIENNAM_SANPHAM c on b.sanpham_fk = c.SP_FK   \n"+    
						" 			where month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"' and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0'    \n"+    
						" 				and a.npp_fk = '"+this.nppID+"'   \n"+    
						" 				and a.khachhang_fk in (  \n"+    
						" 										select khachhang_fk from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk   \n"+    
						" 										where trangthai = '"+thangBATDAU+"' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '"+this.nppID+"' 	  \n"+    
						"  									  ) \n"+    
						"   				and cast( month(a.ngayxuatHD) as varchar(10) ) in   \n"+    
						" 					  (  \n"+    
						" 						select distinct thang from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk   \n"+    
						" 						where trangthai = '"+thangBATDAU+"' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '"+this.nppID+"' and db_kh.khachhang_fk = a.khachhang_fk	   \n"+    
						" 					  )   \n"+    
						" 			group by a.khachhang_fk, c.NSP_FK  \n"+    
						" 		union ALL   \n"+    
						" 			select a.khachhang_fk, c.NSP_FK, case c.NSP_FK when 100002 then 'CQX5' when 100002 then 'CQB' else 'KHAC' end as maNHOM,  isnull( sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100),0) ), 0)  as toTAL   \n"+    
						" 			from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk    \n"+    
						" 					inner join NHOMSANPHAM_MIENNAM_SANPHAM c on b.sanpham_fk = c.SP_FK   \n"+    
						" 			where month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"' and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0'    \n"+    
						" 				and a.npp_fk = '"+this.nppID+"'   \n"+    
						" 				and cast( month(a.ngayxuatHD) as varchar(10) ) not in   \n"+    
						"  					  (  \n"+    
						" 						select distinct thang from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk   \n"+    
						" 						where trangthai = '"+thangBATDAU+"' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '"+this.nppID+"' and db_kh.khachhang_fk = a.khachhang_fk	  \n"+    
						"  					  )   \n"+    
						" 				and cast( month(a.ngayxuatHD) as varchar(10) ) in \n"+    
						"  					  (   \n"+    
						" 						select distinct thang from DUYETBANDUNGGIA db  \n"+    
						" 						where trangthai = '"+thangBATDAU+"' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '"+this.nppID+"' and  khongtinhdoanhso = '0'	)   \n"+    
						" 					    \n"+    
						" 			group by a.khachhang_fk, c.NSP_FK  \n"+    
						"		)  \n"+    
						" 		DS \n"+    
						" 		where NSP_FK in ( 100002, 100003, 100004  )  \n"+    
						"  and DS.KHACHHANG_FK in ( select khachhang_fk from DUYETBANDUNGGIA a inner join DUYETBANDUNGGIA_KHACHHANG_HUONGQUY b on a.pk_seq = b.duyet_fk where a.NPP_FK = '"+this.nppID+"' and a.thang = '"+thangKETTHUC+"' and a.nam = '"+namHIENTAI+"' and a.trangthai = '"+thangBATDAU+"'   ) 		group by DS.KHACHHANG_FK, NSP_FK, maNHOM  \n"+    
						" 	)  \n"+    
						"	TOTAL inner join KHACHHANG kh on TOTAL.khachhang_fk = kh.pk_seq \n"+    
						") \n"+    
						"as p pivot ( sum(doanhsoNHOM)  for nsp_fk  in  ( [100002], [100003], [100004] ) ) as t \n"+    
						"group by ddkdTEN, khID, maFAST, TEN, DIACHI,MaHD,DIACHI,cmnd,KHACHHANG_FK \n"+    
						") A1 \n"+    
						"left join \n"+    
						"(\n"+    
						"select * from(\n"+    
						"select thuve.KHACHHANG_FK,thuve.NPP_FK,sum(thuve.toTAL) as ThuVe,thuve.thang   \n"+    
						"    from    \n"+    
						"    (   \n"+    
						"    	select hd.KHACHHANG_FK,hd.NPP_FK,SUM(hd.tongtienAVat) as toTAL,hd.thang   \n"+    
						"    	from    \n"+    
						"    	(   \n"+    
						"    		select month( a.ngayxuatHD ) as Thang,year( a.ngayxuatHD ) as Nam,   \n"+    
						"    		a.KHACHHANG_FK, a.NPP_FK, sum( isnull((a.tongtienavat),0)) as tongtienAVat    \n"+    
						"    		from HOADON a     \n"+    
						"    		left join     \n"+    
						"    		(    \n"+    
						"    			SELECT b.HOADON_FK, SUM(b.SOTIENCANTRU) as SOTIENCANTRU    \n"+    
						"    			FROM CANTRUCONGNO a inner join CANTRUCONGNO_HOADON b on a.PK_SEQ = b.CANTRUCONGNO_FK    \n"+    
						"    			WHERE a.TRANGTHAI = 1    \n"+    
						"    			GROUP BY b.HOADON_FK    \n"+    
						"    		)  b on a.PK_SEQ = b.HOADON_FK    \n"+    
						"    		where month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"'    \n"+    
						"    			and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 )			   \n"+    
						"    			and a.loaihoadon = '0' and a.NPP_FK = '"+this.nppID+"'    \n"+    
						"    		group by a.KHACHHANG_FK,a.NPP_FK,month( a.ngayxuatHD ),year( a.ngayxuatHD )   \n"+    
						"    	)as hd   \n"+    
						"    	inner join    \n"+    
						"    	(   \n"+    
						"    		select khachhang_fk,a.THANG,a.NAM,a.NPP_FK from DUYETBANDUNGGIA a inner join    \n"+    
						"    			DUYETBANDUNGGIA_KHACHHANG b on a.pk_seq = b.duyet_fk    \n"+    
						"    		where a.NPP_FK="+this.nppID+" and a.THANG>='"+thangBATDAU+"' and a.THANG<='"+thangKETTHUC+"' and a.NAM='"+namHIENTAI+"'   \n"+    
						"    	)as dg on dg.khachhang_fk=hd.KHACHHANG_FK and hd.Nam=dg.NAM and hd.Thang=dg.THANG   \n"+    
						"    	group by hd.KHACHHANG_FK,hd.NPP_FK,hd.thang   \n"+    
						"    union all    \n"+    
						"    	select hd.KHACHHANG_FK,hd.NPP_FK,SUM(hd.tongtienAVat) as toTAL,thang   \n"+    
						"    	from    \n"+    
						"    	(   \n"+    
						"    		select month( a.ngayxuatHD ) as Thang,year( a.ngayxuatHD ) as Nam,   \n"+    
						"    			a.KHACHHANG_FK, a.NPP_FK, sum( isnull((a.tongtienavat),0)) as tongtienAVat    \n"+    
						"    			from HOADON a     \n"+    
						"    			left join     \n"+    
						"    			(    \n"+    
						"    				SELECT b.HOADON_FK, SUM(b.SOTIENCANTRU) as SOTIENCANTRU    \n"+    
						"    				FROM CANTRUCONGNO a inner join CANTRUCONGNO_HOADON b on a.PK_SEQ = b.CANTRUCONGNO_FK    \n"+    
						"    				WHERE a.TRANGTHAI = 1    \n"+    
						"    				GROUP BY b.HOADON_FK    \n"+    
						"    			)  b on a.PK_SEQ = b.HOADON_FK    \n"+    
						"    			where month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"' and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 )			   \n"+    
						"    				and a.loaihoadon = '0' and a.NPP_FK = '"+this.nppID+"'    \n"+    
						"    			group by a.KHACHHANG_FK,a.NPP_FK,month( a.ngayxuatHD ),year( a.ngayxuatHD )   \n"+    
						"    		) as hd    \n"+    
						"    		where 1=1    \n"+    
						"  and   		not exists   \n"+    
						"    		(   \n"+    
						"    			select khachhang_fk   \n"+    
						"    			from DUYETBANDUNGGIA a inner join  DUYETBANDUNGGIA_KHACHHANG b on a.pk_seq = b.duyet_fk    \n"+    
						"    			where a.NPP_FK="+this.nppID+" and a.THANG>='"+thangBATDAU+"' and a.THANG<='"+thangKETTHUC+"' and a.NAM='"+namHIENTAI+"'   \n"+    
						"    			and b.khachhang_fk=hd.KHACHHANG_FK   \n"+    
						"    			 and a.THANG=hd.Thang and a.NAM=hd.Nam   \n"+    
						"    		)   \n"+    
						"    		and     \n"+    
						"    		(          \n"+    
						"    			select COUNT(*)    \n"+    
						"    			from DUYETBANDUNGGIA a     \n"+    
						"    			where a.NPP_FK="+this.nppID+" and a.THANG>='"+thangBATDAU+"' and a.THANG<='"+thangKETTHUC+"' and a.NAM='"+namHIENTAI+"'   \n"+    
						"    			and a.khongtinhtienthuve=0    			 and a.THANG=hd.Thang and a.NAM=hd.Nam   \n"+    
						"    		)>0    \n"+    
						"    	group by hd.KHACHHANG_FK,hd.NPP_FK,thang   \n"+    
						"    )as thuve     \n"+    
						"    group by thuve.KHACHHANG_FK,thuve.NPP_FK,thuve.thang \n"+    
						"  ) dsthang  \n"+    
						"   pivot(sum(thuve) for thang in (["+thangBATDAU+"],["+(thangBATDAU+1)+"],["+thangKETTHUC+"])) as DATA) A2\n"+    
						"   on A1.KHACHHANG_FK=A2.KHACHHANG_FK";
				System.out.println("---INIT CHIET KHAU QUY: " + query);
				
		System.out.println("___ds4"+query);
		
			this.rsBangKeTTThang = db.getScrol(query);
			
			
			
			}
		}
		else if(loai.equals("5")){ 
			if(nppID.length() > 0  && nam.length() > 0 && thang.length() > 0 ){
				sql = "SELECT  DH.KHACHHANG_FK , kh.MASOTHUE , kh.maFAST, kh.TEN as tenkh , kh.diachi, N'Đạt' as duyet , kh.xuatkhau, " +
						 " cast(sum(dh.giamua * dh.soluong*(1+DH.vat/100)) as numeric(18,0) ) as doanhso " +
						 " FROM  " +
						 " (       " +
						 "        SELECT  nsp.NSP_FK as nhomsp,DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK,    " +
						 "                ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK,      " +
						 "                ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) AS GIAMUA , (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG,     " +
						 "                (-1)*ISNULL(DH_SP1.CHIETKHAU, 0) AS CHIETKHAU  ,0 as vat   " +
						 "          FROM  DONHANGTRAVE DH LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ     " +
						 "        LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  " +
						 "        inner join NHOMSANPHAM_SANPHAM nsp on nsp.SP_FK=   DH_SP.SANPHAM_FK " +
					    "        WHERE DH.TRANGTHAI = 3 AND DH.NGAYNHAP like '"+nam+"-"+thang+"%'   " +
					    "     UNION ALL      " +
					    "         SELECT  nsp.NSP_FK as nhomsp,DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, DH_SP.GIAMUA,   " + 
					    "               DH_SP.SOLUONG,(cast ((DH_SP.CHIETKHAU*(1+DH_SP.THUEVAT/100)) as numeric(18,0))) as  CHIETKHAU , DH_SP.THUEVAT as vat   " +
					    "         FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK  " +
					    "                           inner join NHOMSANPHAM_SANPHAM nsp on  DH_SP.SANPHAM_FK=nsp.SP_FK " +
					    "          WHERE DH.TRANGTHAI  !=2     AND  DH.NGAYNHAP like '"+nam+"-"+thang+"%' " +
					    " ) DH     " +
					    " INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ     " +
						"   INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ  " +
					    "  INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ      " +
						"   LEFT JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ     " +
						"  LEFT JOIN KENHBANHANG KBH ON DH.KBH_FK = KBH.PK_SEQ     " +
						"   LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ     " +
						"   inner join KHACHHANG kh on kh.PK_SEQ= DH.KHACHHANG_FK  " +
						"  inner join NHOMSANPHAM ns on ns.PK_SEQ=DH.nhomsp " +
						"   WHERE  DH.NGAYNHAP like '"+nam+"-"+thang+"%' and DH.NPP_FK = "+nppID + 
						" group by   DH.KHACHHANG_FK,kh.TEN , kh.DIACHI ,  kh.maFAST , kh.XuatKhau , kh.MASOTHUE ";
			
			System.out.println("Bao cao ds5 :"+sql);
			this.rsBangKeTTThang = db.get(sql);
			}
		}
		else if(loai.equals("6")){ 
			if(nppID.length() > 0  && nam.length() > 0 && quy.length() > 0 ){
				sql = "select * " +
					"	from  " +
					"	( " +
					"	select khachhang_fk, TEN, maFAST, MASOTHUE , DIACHI ,npp_fk,sum(["+nam+"-"+t1+"]) as t1,sum(["+nam+"-"+t2+"]) as t2, sum(["+nam+"-"+t3+"]) as t3 " +
					"	from " +
					"	(	" +
					"	select dh.khachhang_fk,dh.npp_fk,dh.kbh_fk,dh.ddkd_fk,dh.gsbh_fk,sp.dvkd_fk,    " +
					"	(dhsp.soluong*dhsp.giamua*(1+dhsp.thuevat/100)) as doanhso  " +
					"	,SUBSTRING( dh.NgayNhap,1,7) as Thang , kh.TEN , kh.maFAST , kh.MASOTHUE , kh.DIACHI	" +
					"	from donhang dh inner join donhang_sanpham dhsp on dhsp.donhang_fk=dh.pk_seq       " +
					"	inner join sanpham sp on sp.pk_Seq=dhsp.sanpham_fk " +
					"	inner join KHACHHANG kh on kh.PK_SEQ = dh.KHACHHANG_FK   " +
					"	where dh.trangthai!=2 and  SUBSTRING( dh.NgayNhap,1,7)   >= '"+nam+"-"+tuthang+"'       " +
					"	and   SUBSTRING( dh.NgayNhap,1,7)  <= '"+nam+"-"+denthang+"'  and dh.NPP_FK =   "+nppID+"   " +
					"   )  p pivot ( sum(doanhso)         " +
					"	for thang   in  (["+nam+"-"+t1+"],["+nam+"-"+t2+"],["+nam+"-"+t3+"]) " +
					"	)as t " +
					"	group by khachhang_fk,npp_fk, TEN, maFAST, MASOTHUE , DIACHI " +
					"	) as bang1 " +
					"	inner join ( " +
					"	select khachhang_fk,npp_fk, 		  " +
					"	sum([100002]) as DsNhomxanh,sum([100003]) as Dsnhomhh,sum([100004]) as dsnhomkhac   " +
					"	from      " +
					"	(        " +
					"	select dh.khachhang_fk,dh.npp_fk,nsp.nsp_fk,dh.kbh_fk,dh.ddkd_fk,dh.gsbh_fk,sp.dvkd_fk,    " +
					"	(dhsp.soluong*dhsp.giamua*(1+dhsp.thuevat/100)) as doanhso,    " +
					"	(dhsp.chietkhau*(1+dhsp.THUEVAT/100)) as ckDonHang,       " +
					"	cast( ( (dhsp.soluong * dhsp.giamua - dhsp.chietkhau ) * ( 1 + dhsp.thueVAT / 100 ) ) as numeric(18, 0) )  as DoanhSoSauCK   " +
					"	from donhang dh inner join donhang_sanpham dhsp on dhsp.donhang_fk=dh.pk_seq       " +
					"	inner join sanpham sp on sp.pk_Seq=dhsp.sanpham_fk    " +
					"	inner join nhomsanpham_sanpham nsp on nsp.sp_fk=dhsp.sanpham_fk   " +
					"	where dh.trangthai!=2 and  SUBSTRING( dh.NgayNhap,1,7)   >= '"+nam+"-"+tuthang+"'       " +
					"	and   SUBSTRING( dh.NgayNhap,1,7)  <= '"+nam+"-"+tuthang+"'   and dh.NPP_FK =   "+nppID+"  " +
					"	)as p pivot ( sum(doanhso)         " +
					"	for nsp_fk  in  ([100002],[100003],[100004]) )as t   " +
					"	group by khachhang_fk,npp_fk	" +
					"	) as bang2 on bang1.KHACHHANG_FK = bang2.KHACHHANG_FK  ";
			
			System.out.println("Bao cao ds6 :"+sql);
			this.rsBangKeTTThang = db.get(sql);
			}
		}else if(loai.equals("7")){
			if(nppID.length() > 0  && nam.length() > 0 && khtt.length() > 0 ){
			
				int thangn1=0;
				int thangn2=0;
				int thangn3=0;
				int thangn4=0;
				int thangn5=0;
				int thangn6=0;
				int thangbatdau=0;
				int thangketthuc=0;
				if(khtt.equals("1"))
				{
					 thangn1=1;
					 thangn2=2;
					 thangn3=3;
					 thangn4=4;
					 thangn5=5;
					 thangn6=6;
					 thangbatdau=1;
					 thangketthuc=6;

				}
				if(khtt.equals("2"))
				{
					 thangn1=7;
					 thangn2=8;
					 thangn3=9;
					 thangn4=10;
					 thangn5=11;
					 thangn6=12;
					 thangbatdau=7;
					 thangketthuc=12;

				}
				
					
					sql="select "+ 
								"\n		( select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   "+
								"\n			inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ where cc.KHACHHANG_FK=dsthangHIENTAI.KHACHHANG_FK "+   
								"\n	) as tdvTEN ,khTEN,ma as mahd,khMa,khDiaChi,MaHD,dsthangHIENTAI.DoanhThu,ds.DoanhSo as TongDS,dsBOGA.DoanhSo as dsBOGA, "+
								"\n		(   "+
								"\n			select sum(a.chietkhau) as ckle   "+
								"\n			from tieuchithuongtl_tieuchi a inner join tieuchithuongtl b on b.pk_seq=a.thuongtl_fk   "+
								"\n			where b.pk_seq in ( select thuongtl_fk from tieuchithuongtl_loainpp where loainpp =   "+
								"\n			( select loainpp from nhaphanphoi where pk_seq = dsthangHIENTAI.npp_fk) )and b.nam='"+nam+"' "+    
								"\n			and b.trangthai = '1' and b.loaict = '0'   and ISNULL(ptChietKhau,0)=0  "+
								"\n		)as ptChietKhau  "+
								"\n	from   "+
								"\n	(   "+
								"\n		select a.npp_fk,a.khachhang_fk ,sum( a.tongtienavat) as DoanhThu,b.TEN as khTEN,b.mahd as ma,b.maFAST as khMa,b.DIACHI as khDiaChi,b.MaHD,b.pt_chietkhau as		ptChietKhau  "+
								"\n		from HOADON a "+
								"\n		inner join KHACHHANG b on b.PK_SEQ=a.KHACHHANG_FK  inner join  (select  KHACHHANG_fk,aa.npp_fk from DUYETBANDUNGGIA aa  inner join DUYETBANDUNGGIA_KHACHHANG bb on bb.duyet_fk=aa.PK_SEQ  where aa.THANG>='"+thangbatdau+"' and aa.thang <= '" + thangketthuc +  "' and aa.NAM='"+nam+"'  ) as dg on dg.khachhang_fk=a.KHACHHANG_FK and dg.npp_fk=a.npp_fk "+  
								"\n		where a.NPP_FK="+this.nppID+" and  "+
								"\n			month(ngayxuathd)>= '"+ thangbatdau +"' and month(ngayxuathd)<= '" + thangketthuc + "' and year(ngayxuathd) = '"+nam+"' and isnull(loaihoadon, '0') = '0'  and  a.npp_fk = '"+this.nppID+"' and a.trangthai not in (1,3,5)  and  a.npp_fk = '"+this.nppID+"'  "+
								"\n		group by a.NPP_FK,a.KHACHHANG_FK,b.TEN,b.maFAST,b.DIACHI,b.MaHD,pt_ChietKhau,b.mahd  "+
								"\n	)as dsthangHIENTAI   "+
								"\n	left join  "+
								"\n	(   "+
								"\n		select SUM( ROUND(ROUND( b.soluong*b.dongia,0) * (1+b.vat/100 ),0)) as DoanhSo,a.KHACHHANG_FK  "+
								"\n		from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ  "+
								"\n		where a.trangthai not in ( 1 , 3, 5 )  and month(ngayxuathd)>= '"+ thangbatdau +"' and month(ngayxuathd)<= '" + thangketthuc + "' and year(ngayxuathd) = '"+nam+"' and isnull(loaihoadon, '0') = '0'   and  a.npp_fk = '"+this.nppID+"' "+  
								"\n			and  a.npp_fk = '"+this.nppID+"' "+      
								"\n		group by a.KHACHHANG_FK	 "+
								"\n	)as ds on ds.KHACHHANG_FK=dsthangHIENTAI.KHACHHANG_FK  "+
								"\n	left join  "+
								"\n	( "+
								"\n		select SUM( ROUND(ROUND( b.soluong*b.dongia,0) * (1+b.vat/100 ),0)) as DoanhSo,a.KHACHHANG_FK  "+
								"\n		from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ  "+
								"\n			inner join "+tableNAME+" c on c.SP_FK=b.SANPHAM_FK  "+
								"\n			where a.trangthai not in ( 1 , 3, 5 )  and month(ngayxuathd)>= '"+ thangbatdau +"' and month(ngayxuathd)<= '" + thangketthuc + "' and year(ngayxuathd) = '"+nam+"' and isnull(loaihoadon, '0') = '0'  and  a.npp_fk = '"+this.nppID+"' "+   
								"\n			and  a.npp_fk = '"+this.nppID+"' and c.NSP_FK=100003  "+
								"\n		group by a.KHACHHANG_FK	"+
								"\n )as dsBOGA on dsBOGA.KHACHHANG_FK=dsthangHIENTAI.KHACHHANG_FK";	
					
				System.out.println("Bao cao ds7 :"+sql);
				return;
				//this.rsBangKeTTThang = db.getScrol(sql);
				}
		}
		
		
		
		if(tt.equals("1"))
		{
			if(loai.equals("0") || loai.equals("1") || loai.equals("2"))
				sql = "select * from NHAPHANPHOI where loaiNPP in (0,1)"+
						" and pk_seq in "+ util.quyen_npp(userId)+ "" ;
			else
				sql = "select * from NHAPHANPHOI where loaiNPP in (4)"+
						" and pk_seq in "+ util.quyen_npp(userId)+ "" ;
			
			this.rsChiNhanh = db.get(sql);	
						
			
			if(chinhanhId.length() > 0 )
			{
				sql = "select * from DAIDIENKINHDOANH where NPP_FK = "+ chinhanhId ;
				System.out.println(sql);
				this.rsTDV= db.get(sql);
			}
			if(chinhanhId.length() > 0){
				sql = "select TEN from NHAPHANPHOI where PK_seq = " + chinhanhId;
				ResultSet rs = db.get(sql);
				System.out.println(sql);
				try
				{
				if(rs != null)
				{
					if(rs.next());
					{
						nppTen = rs.getString("TEN");
						rs.close();
					}
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(tdvId.length() > 0){		
				sql = "select TEN from DAIDIENKINHDOANH where PK_seq = " + tdvId;
				ResultSet rs = db.get(sql);
				System.out.println(sql);
				try
				{
				if(rs != null)
				{
					if(rs.next());
					{
						tdvTen = rs.getString("TEN");
						rs.close();
					}
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else{
		
				
			if(nppID.length() > 0){
			sql = "select * from DAIDIENKINHDOANH where pk_seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk='"+this.nppID+"') " ;
			this.rsTDV= db.get(sql);
			}
			
			if(nppID.length() > 0){
				sql = "select TEN from NHAPHANPHOI where PK_seq = " + nppID;
				ResultSet rs = db.get(sql);
				System.out.println(sql);
				try
				{
				if(rs != null)
				{
					if(rs.next());
					{
						nppTen = rs.getString("TEN");
						rs.close();
					}
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(tdvId.length() > 0){		
				sql = "select TEN from DAIDIENKINHDOANH where PK_seq = " + tdvId;
				ResultSet rs = db.get(sql);
				System.out.println(sql);
				try
				{
				if(rs != null)
				{
					if(rs.next());
					{
						tdvTen = rs.getString("TEN");
						rs.close();
					}
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	

	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		
		try {
			if(rsBangKeTTThang != null)
				rsBangKeTTThang.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getUserId() {
		return userId;
	}


	public String getThang() {
		return thang;
	}


	public void setThang(String thang) {
		this.thang = thang;
	}


	public String getNam() {
		return nam;
	}


	public void setNam(String nam) {
		this.nam = nam;
	}


	public ResultSet getRsBangKeTTThang() {
		return rsBangKeTTThang;
	}


	public void setRsBangKeTTThang(ResultSet rsBangKeTTThang) {
		this.rsBangKeTTThang = rsBangKeTTThang;
	}


	public String getNppID() {
		return nppID;
	}


	public void setNppID(String nppID) {
		this.nppID = nppID;
	}


	public ResultSet getRsTDV() {
		return rsTDV;
	}


	public void setRsTDV(ResultSet rsTDV) {
		this.rsTDV = rsTDV;
	}


	public String getTdvId() {
		return tdvId;
	}

	public void setTdvId(String tdvId) {
		this.tdvId = tdvId;
	}



	public String getNppTen() {
		return nppTen;
	}



	public void setNppTen(String nppTen) {
		this.nppTen = nppTen;
	}



	public String getTdvTen() {
		return tdvTen;
	}



	public void setTdvTen(String tdvTen) {
		this.tdvTen = tdvTen;
	}

	public String getQuy() {
		return quy;
	}

	public void setQuy(String quy) {
		this.quy = quy;
	}

	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public String getTuthang() {
		return tuthang;
	}

	public void setTuthang(String tuthang) {
		this.tuthang = tuthang;
	}

	public String getDenthang() {
		return denthang;
	}

	public void setDenthang(String denthang) {
		this.denthang = denthang;
	}

	public String getT1() {
		return t1;
	}

	public void setT1(String t1) {
		this.t1 = t1;
	}

	public String getT2() {
		return t2;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}

	public String getT3() {
		return t3;
	}

	public void setT3(String t3) {
		this.t3 = t3;
	}

	public String getTt() {
		return tt;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public ResultSet getRsChiNhanh() {
		return rsChiNhanh;
	}

	public void setRsChiNhanh(ResultSet rsChiNhanh) {
		this.rsChiNhanh = rsChiNhanh;
	}

	public String getChinhanhId() {
		return chinhanhId;
	}

	public void setChinhanhId(String chinhanhId) {
		this.chinhanhId = chinhanhId;
	}

	
	

}
