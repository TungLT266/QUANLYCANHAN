package geso.traphaco.center.beans.bcchartdoanhsodiaban.imp;

import geso.traphaco.center.beans.bcchartdoanhsodiaban.IBcchartdoanhsodiaban;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

import java.sql.ResultSet;
import java.util.Calendar;

public class Bcchartdoanhsodiaban extends Phan_Trang implements IBcchartdoanhsodiaban{

	private static final long serialVersionUID = 1L;
	
	String userId;
	String id;
	String nam;
	String thang;
	String mangtenDiaban[];	
	String mangidDiaban[];
	ResultSet rs;
	String msg;

	dbutils db;

	public Bcchartdoanhsodiaban()
	{
		this.userId = "";
		this.id = "";
		this.msg = "";
		Calendar cal = Calendar.getInstance();
		int year_ = cal.get(Calendar.YEAR);
		this.nam=year_+"";
		int thang_=cal.get(Calendar.MONTH)+1;		
		this.thang=thang_+"";
		this.db = new dbutils();
	}	

	public String getId()
	{
		return this.id;
	}

	public void setId(String Id)
	{
		this.id = Id;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public void init()
	{
		String query="";
		if(this.nam.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn năm";
			return;
		}
		
		if(this.thang.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn tháng";
			return;
		}
		String date;
		if(Integer.parseInt(thang)<10)
			date = nam+"-0"+thang;
		else
			date=nam+"-"+thang;
		
		// LẤY ARR ĐỊA BÀN CÓ DOANH SỐ
		query="select PK_SEQ,TEN "+
				"	from( "+
				"		 SELECT C.PK_SEQ,C.TEN   "+
				"		 FROM HOADON A INNER JOIN NHAPHANPHOI B ON B.PK_SEQ=A.NPP_FK INNER JOIN TINHTHANH C on C.PK_SEQ=B.TINHTHANH_FK "+
				"		 WHERE B.TRANGTHAI=1 "+
				"		 GROUP BY C.PK_SEQ,C.TEN "+
				"	)as SoucerTable "+
				"	group by PK_SEQ,TEN"; 
		System.out.println("List Địa bàn: "+query);
		rs=this.db.get(query);		
		
		mangidDiaban=new String[64];
		mangtenDiaban=new String[64];
		
		try{
			int i = 0;
			while (rs.next())
			{
				mangidDiaban[i]=rs.getString("pk_seq");
				mangtenDiaban[i]=rs.getString("ten");
				i++;
			}
			rs.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy khu vực nào";
		}

		// LẤY BÁO CÁO
		String ck = "";

        //if (!date.contains("2014-07"))
        {
        	System.out.println("Khác tháng 07");
            ck =" select hoadon_fk,SUM((ck.chietkhau*( 1+ ck.thueVAT/100)))  as AVAT_CK " +
            " from HOADON_CHIETKHAU ck" +
            " group by hoadon_fk   ";
        }
        /*else
        {
        	System.out.println("Tháng 07");
        	ck=  "          	select hoadon_fk,SUM((ck.chietkhau*( 1+ ck.thueVAT/100)))  as AVAT_CK        " +
                    "          	from         " +
                    "          	(          " +
                    "          			select b.hoadon_fk, N''CN5'' as diengiai, sum(chietkhau) as chietkhau, thueVAT          " +
                    "          			from DONHANG_SANPHAM  a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk          " +
                    "          			where a.thueVAT = ''5''         " +
                    "          			group by b.hoadon_fk, thueVAT          " +
                    "          			union    all       " +
                    "          			select b.hoadon_fk, N''CN10'' as diengiai, sum(chietkhau) as chietkhau, thueVAT          " +
                    "          			from DONHANG_SANPHAM  a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk          " +
                    "          			where a.thueVAT = ''10''         " +
                    "          			group by b.hoadon_fk, thueVAT          " +
                    "          			union  all       " +
                    "          			select b.hoadon_fk, a.diengiai, sum( a.thanhtoan / ( 1  + ptTHUE / 100 )  ) as chietkhau, ptTHUE as thueVAT         " +
                    "          			from DUYETTRAKHUYENMAI_DONHANG a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk             " +
                    "          			group by b.hoadon_fk, a.diengiai, ptTHUE           " +
                    "          		union           " +
                    "          			select b.hoadon_fk, a.maloai as diengiai, sum(a.chietkhau) as chietkhau, ptVAT as thueVAT         " +
                    "          			from DONHANG_CHIETKHAUBOSUNG a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk          " +
                    "          			where chietkhau != 0         " +
                    "          			group by b.hoadon_fk, a.maloai, ptVAT          " +
                    "          	)          " +
                    "          	ck left join LOAICHIETKHAU loai on ck.diengiai = loai.maloai          " +
                    "          	inner join HOADON hd on hd.PK_SEQ=ck.HOADON_FK       " +
                    "          	where hd.LOAIHOADON =0 and hd.TRANGTHAI in (2,4)  and hd.NgayXuatHD like ''%" + date + "%'' " +
                    "          	group by HOADON_FK       ";
        }*/
        
		query=	" declare @cols varchar(max), @sql nvarchar(max) " + 
				"	set @cols = '[' + REPLACE(  " + 
				"			(SELECT PK_SEQ AS [data()] " + 
				"				from( " + 
				"					 SELECT C.PK_SEQ,C.TEN    " + 
				"					 FROM HOADON A INNER JOIN NHAPHANPHOI B ON B.PK_SEQ=A.NPP_FK INNER JOIN TINHTHANH C on C.PK_SEQ=B.TINHTHANH_FK " + 
				"					 WHERE B.TRANGTHAI=1 " + 
				"					 GROUP BY C.PK_SEQ,C.TEN				 " + 
				"				)as SoucerTable " + 
				"				group by PK_SEQ,TEN  " + 
				"			FOR XML PATH('') " + 
				"				),' ','],[') + ']' ; " + 
				" set @sql = '	SELECT ''Doanh so'' as type, '+@cols+'   " + 
				"				FROM(    " + 
				"							select TT_PK as PK_SEQ, sum(db.doanhso)/1000000 as doanhso " + 
				"					from (   " + 
				"						select npp.TINHTHANH_FK as TT_PK,db.doanhso, db.chietkhau, db.doanhthu from (  " + 
				"						select 	hdOTC.NPP_FK,  " + 
				"						sum(hdOTC.AVAT) as doanhso, sum(isnull(ck.AVAT_CK,0)) as chietkhau,   " + 
				"						sum(hdOTC.AVAT - isnull(ck.AVAT_CK,0)) as doanhthu  " + 
				"								from   " + 
				"								(  select   " + 
				"									a.NPP_FK,  " + 
				"									b.HOADON_FK ,  " + 
				"									round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
				"									from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 
				"							     " + 
				"									where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
				"									and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
				"									group by b.HOADON_FK, a.NPP_FK  " + 
				"							     " + 
				"									)as hdOTC  " + 
				"					left join ( "+ ck + //CHIẾT KHẤU Ở ĐÂY NÀY
				"								)as ck on ck.hoadon_fk = hdOTC.HOADON_FK  " + 
				"											group by hdOTC.NPP_FK  " + 
				"							) as db   " + 
				"							inner join NHAPHANPHOI npp on npp.PK_SEQ = db.NPP_FK    " + 
				"							where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
				"							group by npp.TINHTHANH_FK,db.doanhso, db.chietkhau, db.doanhthu " + 
				"				union all  " + 
				"				select npp.TINHTHANH_FK as TT_PK,hdETC.AVAT as doanhso, hdETC.AVAT_CK as chietkhau, (hdETC.AVAT - hdETC.AVAT_CK) as doanhthu 					 " + 
				"					from             " + 
				"					(          " + 
				"							select  HOADON_FK,npp_fk,KHACHHANG_fk,ddkd_fk,  " + 
				"								sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       " + 
				"								sum( soluong * dongia )  as BVAT,( sum( soluong * dongia*thuexuat/100 ) ) as VAT,     " + 
				"								sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   " + 
				"										sum(isnull(chietkhau,0)*(1+thuexuat/100)) as AVAT_CK,              " + 
				"										sum(isnull(thuexuat,0)) as BVAT_CK           " + 
				"							from (      " + 
				"								select  c.HOADON_FK,a.npp_fk,a.KHACHHANG_fk,c.chietkhau,c.vat,   " + 
				"									(      " + 
				"										select top(1) bb.DDKD_FK       " + 
				"										from ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK       " + 
				"										where aa.HOADONNPP_FK=c.HOADON_FK  " + 
				"									) as ddkd_fk ,   " + 
				"										case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
				"										case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
				"								from ERP_HOADONNPP a     " + 
				"									inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
				"									inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
				"									inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
				"									where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
				"									 and a.NgayXuatHD like ''%"+date+"%'' " + 
				"				    				   " + 
				"							)ETC   " + 
				"							group by NPP_FK,KHACHHANG_FK,ddkd_fk,HOADON_FK   " + 
				"					)as hdETC         " + 
				"					inner join ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADON_FK      " + 
				"					inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK					  " + 
				"					where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'' ) " + 
				"					group by npp.TINHTHANH_FK,hdETC.AVAT, hdETC.AVAT_CK " + 
				"				) as db group by db.TT_PK " + 
				"	) a   " + 
				"		PIVOT   " + 
				"	( SUM(a.doanhso) FOR a.PK_SEQ  IN ('+@cols+') ) AS PIVOTTable'    " + 
				"exec(@sql)  " ;
		
		System.out.println("Lấy doanh số theo địa bàn: "+query);
		this.rs=this.db.get(query);
	}

	public void DbClose()
	{
		try
		{
			this.db.shutDown();
		}
		catch (Exception e) {}
	}
	
	public String getThang() {		
		return this.thang;
	}
	
	public void setThang(String thang) {		
		this.thang=thang;
	}
	
	public String getNam() {
		return this.nam;
	}
	
	public void setnam(String nam) {
		this.nam=nam;		
	}

	public String[] getArrTenDiaban() {
		return this.mangtenDiaban;
	}

	public String[] getArrIDDiaban() {
		return this.mangidDiaban;
	}

	public ResultSet getRs() {
		return this.rs;
	}

}
