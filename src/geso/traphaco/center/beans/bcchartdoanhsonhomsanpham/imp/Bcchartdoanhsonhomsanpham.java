package geso.traphaco.center.beans.bcchartdoanhsonhomsanpham.imp;

import geso.traphaco.center.beans.bcchartdoanhsonhomsanpham.IBcchartdoanhsonhomsanpham;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import java.sql.ResultSet;
import java.util.Calendar;


public class Bcchartdoanhsonhomsanpham extends Phan_Trang implements IBcchartdoanhsonhomsanpham{
	private static final long serialVersionUID = 1L;
	String userId;
	String id;
	String nam;
	String thang;
	String mangtenNSP[];	
	String mangidNSP[];
	ResultSet rs;
	String msg;

	dbutils db;

	public Bcchartdoanhsonhomsanpham()
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
			date = nam+"-"+thang;
		
		// LẤY ARR NHÓM SẢN PHẦM
		query="SELECT distinct PK_SEQ,TEN  FROM NHOMSANPHAM";
		
		rs=this.db.get(query);		
		
		mangidNSP=new String[10];
		mangtenNSP=new String[10];
		
		try{
			int i = 0;
			while (rs.next())
			{
				mangidNSP[i]=rs.getString("pk_seq");
				mangtenNSP[i]=rs.getString("ten");
				i++;
			}
			rs.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy nhóm sẩn phẩm nào";
		}
		// LẤY BÁO CÁO
		query="declare @cols varchar(max), @sql nvarchar(max) " + 
				"	set @cols = '[' + REPLACE(  " + 
				"			(SELECT distinct PK_SEQ AS [data()] FROM NHOMSANPHAM " + 
				"			FOR XML PATH('') " + 
				"				),' ','],[') + ']' ; " + 
				"set @sql = N' " + 
				"			SELECT N''Doanh số'' as type,  '+@cols+' " + 
				"			FROM ( " + 
				"			 select NSP_FK as PK_SEQ, sum(db.doanhso)/1000000 as doanhso " + 
				"				from (   " + 
				"					select db.NSP_FK ,db.doanhso from (  " + 
				"					select 	hdOTC.NSP_FK,  " + 
				"						hdOTC.AVAT as doanhso " + 
				"							from   " + 
				"							(	select   " + 
				"								nsp.PK_SEQ as NSP_FK,  " + 
				"								round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
				"								from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 
				"								inner JOIN NHOMSANPHAM_SANPHAM nsp_sp on nsp_sp.SP_FK = b.SANPHAM_FK inner join NHOMSANPHAM nsp on nsp.PK_SEQ = nsp_sp.NSP_FK " + 
				"								where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
				"								and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
				"								group by nsp.PK_SEQ " + 
				"						     " + 
				"								)as hdOTC  " + 
				"						) as db     " + 
				"					union all  " + 
				"					  " + 
				"					select nsp.PK_SEQ as NSP_FK,sum(hdETC.AVAT) as doanhso		 " + 
				"					from             " + 
				"					(          " + 
				"						select  sanpham_fk, " + 
				"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT " + 
				"						from (      " + 
				"							select  c.sanpham_fk,			  " + 
				"							case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
				"							case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
				"							from ERP_HOADONNPP a     " + 
				"								inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
				"								inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
				"								inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
				"								where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
				"								 and a.NgayXuatHD like ''%"+date+"%'' " + 
				"								and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'' ) " + 
				"						)ETC   " + 
				"						group by sanpham_fk " + 
				"					)as hdETC         " + 
				"					inner JOIN NHOMSANPHAM_SANPHAM nsp_sp on nsp_sp.SP_FK = hdETC.SANPHAM_FK inner join NHOMSANPHAM nsp on nsp.PK_SEQ = nsp_sp.NSP_FK " + 
				"					group by nsp.PK_SEQ " + 
				"				) as db group by db.NSP_FK " + 
				"			) a " + 
				"			PIVOT " + 
				"			( SUM(a.doanhso) FOR a.PK_SEQ IN ('+@cols+') ) as PIVOTTable' " + 
				"exec (@sql)";
		System.out.println("Lấy báo cáo theo nhóm sản phẩm: "+query);
		this.rs=this.db.get(query);
	}

	public void createRs()
	{

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

	public String[] getArrTenNSP() {
		return this.mangtenNSP;
	}

	public String[] getArrIDNSP() {
		return this.mangidNSP;
	}

	public ResultSet getRs() {
		return this.rs;
	}



}
