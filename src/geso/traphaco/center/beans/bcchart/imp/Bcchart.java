package geso.traphaco.center.beans.bcchart.imp;

import geso.traphaco.center.beans.bcchart.IBcchart;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

import java.sql.ResultSet;
import java.util.Calendar;

public class Bcchart extends Phan_Trang implements IBcchart{

	private static final long serialVersionUID = 1L;
	
	String userId;
	String id;
	String nam;
	String thang;
	
	String[] mangtenDiaban;	
	String[] mangidDiaban;
	String[] mangtenMien;	
	String[] mangidMien;
	String[] mangidNSP;
	String[] mangtenNSP;
	String[] mangtenSPmoi;
	String[] mangidSPmoi;
	String[] mangtenSPChuLuc;
	String[] mangidSPChuLuc;
	
	ResultSet rsTemp;
	ResultSet rsTonKhoDiaBan;
	ResultSet rsTonKhoMien;
	ResultSet rsDoanhSoDiaBan;
	ResultSet rsDoanhSoMien;
	ResultSet rsDoanhThuDiaBan;
	ResultSet rsDoanhThuMien;
	ResultSet rsDoanhSoNSP;
	ResultSet rsDoanhSoSPMoi;
	ResultSet rsDoanhSoSPChuLuc;
	
	String msg;
	String date;
	String ck;
	dbutils db;

	public Bcchart()
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
		this.setDiaBan_VungMien();
		this.setNhomSanPham();
		this.setSanPhamMoi_ChuLuc();
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
	public String setChietKhau(String date)
	{
		
        //if (!date.contains("2014-07"))
        {
        	//System.out.println("Khác tháng 07");
            this.ck =" select hoadon_fk,SUM((ck.chietkhau*( 1+ ck.thueVAT/100)))  as AVAT_CK " +
            " from HOADON_CHIETKHAU ck" +
            " group by hoadon_fk   ";
        }
        /*else
        {
        	System.out.println("Tháng 07");
        	this.ck=  "          	select hoadon_fk,SUM((ck.chietkhau*( 1+ ck.thueVAT/100)))  as AVAT_CK        " +
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
        return this.ck;
	}

	public Boolean CheckDate()
	{
		if(this.nam.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn năm";
			return false;
		}
		
		if(this.thang.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn tháng";
			return false;
		}
		if(Integer.parseInt(thang)<10)
			this.date = nam+"-0"+thang;
		else
			this.date=nam+"-"+thang;
		
		return true;
	}
	//LẤY ĐỊA BÀN & VÙNG MIỀN
	public void setDiaBan_VungMien()
	{
		String query="";
		// LẤY ARR ĐỊA BÀN CÓ DOANH SỐ
		query="select PK_SEQ,TEN "+
				"	from( "+
				"		 SELECT C.PK_SEQ,C.TEN   "+
				"		 FROM HOADON A INNER JOIN NHAPHANPHOI B ON B.PK_SEQ=A.NPP_FK INNER JOIN TINHTHANH C on C.PK_SEQ=B.TINHTHANH_FK "+
				"		 WHERE B.TRANGTHAI=1 "+
				"		 GROUP BY C.PK_SEQ,C.TEN "+
				"	)as SoucerTable "+
				"	group by PK_SEQ,TEN"; 
		rsTemp=this.db.get(query);		
		
		try{
			String tmpIdDiaBan="";
			String tmpTenDiaBan="";
			while (rsTemp.next())
			{
				tmpIdDiaBan+=rsTemp.getString("pk_seq")+"__";
				tmpTenDiaBan+=rsTemp.getString("ten")+"__";
			}

			this.mangidDiaban=tmpIdDiaBan.split("__");
			this.mangtenDiaban=tmpTenDiaBan.split("__");
			
			rsTemp.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy khu vực nào";
		}

		// LẤY ARR VÙNG CÓ NPP HOẠT ĐỘNG
		query="SELECT distinct v.PK_SEQ,v.ten "
				+ "FROM NHAPHANPHOI npp inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK "
				+ "inner join VUNG v on kv.VUNG_FK = v.PK_SEQ WHERE npp.TRANGTHAI = 1";		
		rsTemp=this.db.get(query);		
		
		try{
			String tmpIdMien="";
			String tmpTenMien="";
			while (rsTemp.next())
			{
				tmpIdMien+=rsTemp.getString("pk_seq")+"__";
				tmpTenMien+=rsTemp.getString("ten")+"__";
			}
			
			this.mangidMien=tmpIdMien.split("__");
			this.mangtenMien=tmpTenMien.split("__");
			
			rsTemp.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy Miền nào";
		}
	}
	
	//LẤY NHÓM SẢN PHẨM
	public void setNhomSanPham()
	{
		String query="";
		// LẤY ARR NHÓM SẢN PHẨM HIỆN CÓ
		query="SELECT distinct PK_SEQ,TEN  FROM NHOMSANPHAM";
		rsTemp=this.db.get(query);		
		
		try{
			String tmpidNSP="";
			String tmptenNSP="";
			while (rsTemp.next())
			{
				tmpidNSP+=rsTemp.getString("pk_seq")+"__";
				tmptenNSP+=rsTemp.getString("ten")+"__";
			}

			this.mangidNSP=tmpidNSP.split("__");
			this.mangtenNSP=tmptenNSP.split("__");
			
			rsTemp.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy nhóm sản phẩm nào";
		}
	}
	//LẤY SẢN PHẨM MỚI VÀ CHỦ LỰC
	public void setSanPhamMoi_ChuLuc()
	{
		String query="";
		// LẤY ARR SẢN PHẨM MỚI
		query="SELECT distinct SP.PK_SEQ as PK_SEQ,SP.TEN as TEN  "+
				"	FROM SANPHAM SP inner join HOADON_SP HDSP on SP.PK_SEQ = HDSP.SANPHAM_FK "+
				"	WHERE SP.SPMOI=1";
		rsTemp=this.db.get(query);		
		
		try{
			String tmpIdSPmoi="";
			String tmpTenSPmoi="";
			while (rsTemp.next())
			{
				tmpIdSPmoi+=rsTemp.getString("pk_seq")+"__";
				tmpTenSPmoi+=rsTemp.getString("ten")+"__";
			}

			this.mangidSPmoi=tmpIdSPmoi.split("__");
			this.mangtenSPmoi=tmpTenSPmoi.split("__");
			
			rsTemp.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy sản phẩm mới nào";
		}

		// LẤY ARR SẢN PHẨM CHỦ LỰC 
		query="SELECT distinct SP.PK_SEQ as PK_SEQ,SP.TEN as TEN  "+
				"	FROM SANPHAM SP inner join HOADON_SP HDSP on SP.PK_SEQ = HDSP.SANPHAM_FK "+
				"	WHERE SP.SPCHULUC=1";
		rsTemp=this.db.get(query);		
		
		try{
			String tmpIdSPChuLuc="";
			String tmpTenSPChuLuc="";
			while (rsTemp.next())
			{
				tmpIdSPChuLuc+=rsTemp.getString("pk_seq")+"__";
				tmpTenSPChuLuc+=rsTemp.getString("ten")+"__";
			}
			
			this.mangidSPChuLuc=tmpIdSPChuLuc.split("__");
			this.mangtenSPChuLuc=tmpTenSPChuLuc.split("__");
			
			rsTemp.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy sản phẩm chủ lực nào";
		}
	}
	
	
	//LẤY BÁO CÁO BIỂU ĐỒ
	public void initTonKho(String query) {
		if(query.isEmpty())
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				// LẤY BÁO CÁO TỒN KHO MIỀN
				query="declare @cols varchar(max), @sql nvarchar(max) "+
						"	set @cols = '[' + REPLACE( "+
						"			(SELECT distinct v.PK_SEQ as [data()] "+
						"							FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join "+
						"							 KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK join VUNG v on kv.VUNG_FK = v.PK_SEQ "+
						"							 WHERE npp.TRANGTHAI = 1 and nppkho.AVAILABLE>0 "+
						"			FOR XML PATH('') "+
						"				),' ','],[') + ']' ; "+
						"	set @sql = ' "+
						"		SELECT N''Ton kho'' as type, ' + @cols + ' "+
						"		FROM( "+
						"			SELECT cast(sum(nppkho.AVAILABLE) as numeric(18,0)) as TonKho,v.pk_seq "+
						"			FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join "+
						"			KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK join VUNG v on kv.VUNG_FK = v.PK_SEQ "+
						"			WHERE npp.TRANGTHAI =1 and nppkho.AVAILABLE >0 "+
						"						and npp.pk_seq in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'') "+
						"			Group by v.pk_seq "+
						"			) a "+
						"		 PIVOT "+
						"		( "+
						"			SUM(a.TonKho) FOR a.PK_SEQ  IN ('+@cols +') "+
						"		) AS PIVOTTable ' "+
						"	exec (@sql)"; 
				System.out.println("Lấy báo cáo tồn kho miền: "+query);
				this.rsTonKhoMien=this.db.get(query);
				
				// LẤY BÁO CÁO TỒN KHO ĐỊA BÀN
				query="declare @cols varchar(max), @sql nvarchar(max) "+
						"	set @cols = '[' + REPLACE( "+
						"			(SELECT distinct PK_SEQ AS [data()] FROM TINHTHANH "+
						"			WHERE PK_SEQ in (SELECT distinct TINHTHANH_FK  "+
						"							FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join TINHTHANH tt on npp.TINHTHANH_FK= tt.PK_SEQ "+
						"							WHERE nppkho.AVAILABLE>0 and npp.TRANGTHAI=1) "+
						"			FOR XML PATH('') "+
						"				),' ','],[') + ']' ; "+
						"	set @sql = ' "+
						"		SELECT N''Ton kho'' as type, ' + @cols + ' "+
						"		FROM( "+
						"			SELECT cast(sum(nppkho.AVAILABLE) as numeric(18,0)) as TonKho,tt.pk_seq "+
						"			FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join TINHTHANH tt on npp.TINHTHANH_FK= tt.PK_SEQ "+
						"			WHERE npp.TRANGTHAI=1 and nppkho.AVAILABLE>0 "+
						"						and npp.pk_seq in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'') "+
						"			Group by tt.pk_seq "+
						"			) a "+
						"		 PIVOT "+
						"		( "+
						"			SUM(a.TonKho) FOR a.PK_SEQ  IN ('+@cols +') "+
						"		) AS PIVOTTable ' "+
						" exec (@sql)";
				System.out.println("Lấy tồn kho địa bàn: "+query);
				this.rsTonKhoDiaBan=this.db.get(query);
			}
			else
			{
				return;
			}
		}
		else
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				
			}
			else
			{
				return;
			}
		}	
	}

	public void initDoanhSo(String query) 
	{
		if(query.isEmpty())
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				setChietKhau(this.date);
				// LẤY BÁO CÁO DOANH SỐ ĐỊA BÀN
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
						"									where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"									and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"									group by b.HOADON_FK, a.NPP_FK  " + 
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
				this.rsDoanhSoDiaBan=this.db.get(query);
				
				// LẤY BÁO CÁO DOANH SỐ MIỀN
				query="declare @cols varchar(max), @sql varchar(max) " + 
						"set @cols = '[' + REPLACE(  " + 
						"			(SELECT distinct v.PK_SEQ as [data()]  " + 
						"								FROM NHAPHANPHOI npp inner join KHUVUC kv  " + 
						"								on kv.PK_SEQ = npp.KHUVUC_FK inner join VUNG v on kv.VUNG_FK = v.PK_SEQ " + 
						"								 WHERE npp.TRANGTHAI = 1 " + 
						"			FOR XML PATH('') " + 
						"				),' ','],[') + ']' ; " + 
						"set @sql = ' " + 
						"			SELECT N''Doanh số'' as type, '+@cols+' " + 
						"			FROM( " + 
						"				select VUNG_PK as PK_SEQ, sum(db.doanhso)/1000000 as doanhso " + 
						"			from (   " + 
						"				select v.PK_SEQ as VUNG_PK,db.doanhso, db.chietkhau, db.doanhthu from (  " + 
						"				select 	hdOTC.NPP_FK,  " + 
						"				sum(hdOTC.AVAT) as doanhso, sum(isnull(ck.AVAT_CK,0)) as chietkhau,   " + 
						"				sum(hdOTC.AVAT - isnull(ck.AVAT_CK,0)) as doanhthu  " + 
						"						from   " + 
						"						(  select   " + 
						"							a.NPP_FK,  " + 
						"							b.HOADON_FK ,  " + 
						"							round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
						"							from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 						
						"							where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"							and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"							group by b.HOADON_FK, a.NPP_FK  " + 
						"							)as hdOTC  " + 
						"						left join ( "+ ck + //########################//
						"					)as ck on ck.hoadon_fk = hdOTC.HOADON_FK  " + 
						"					group by hdOTC.NPP_FK  " + 
						"				) as db   " + 
						"				inner join NHAPHANPHOI npp on npp.PK_SEQ = db.NPP_FK    " + 
						"			    inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " + 
						"				inner join VUNG v on kv.VUNG_FK = v.PK_SEQ " + 
						"			    where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''100002'') " + 
						"				union all  " + 
						"				select v.PK_SEQ as PK_SEQ,hdETC.AVAT as doanhso, hdETC.AVAT_CK as chietkhau, (hdETC.AVAT - hdETC.AVAT_CK) as doanhthu				 " + 
						"				from             " + 
						"				(          " + 
						"						select  HOADON_FK,npp_fk,KHACHHANG_fk,ddkd_fk,   " + 
						"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       " + 
						"							sum( soluong * dongia )  as BVAT,( sum( soluong * dongia*thuexuat/100 ) ) as VAT,     " + 
						"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   " + 
						"									sum(isnull(chietkhau,0)*(1+thuexuat/100)) as AVAT_CK,              " + 
						"									sum(isnull(thuexuat,0)) as BVAT_CK           " + 
						"						from (      " + 
						"							select  c.HOADON_FK,a.npp_fk,a.KHACHHANG_fk,c.chietkhau,c.vat,   " + 
						"								(      " + 
						"									select top(1) bb.DDKD_FK       " + 
						"									from ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK       " + 
						"									where aa.HOADONNPP_FK=c.HOADON_FK  " + 
						"								) as ddkd_fk ,   " + 
						"									case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
						"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
						"							from ERP_HOADONNPP a     " + 
						"								inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
						"								inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
						"								inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
						"								where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
						"								 and a.NgayXuatHD like ''%"+date+"%'' " + 
						"						)ETC   " + 
						"						group by NPP_FK,KHACHHANG_FK,ddkd_fk,HOADON_FK   " + 
						"				)as hdETC " + 
						"				inner join ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADON_FK      " + 
						"				inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK " + 
						"				inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " + 
						"				inner join VUNG v on kv.VUNG_FK = v.PK_SEQ     " + 
						"				where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'')  " + 
						"				) as db group by db.VUNG_PK		 " + 
						"		) a " + 
						"			PIVOT " + 
						"		( SUM(a.doanhso) FOR a.PK_SEQ  IN ('+@cols+') ) AS PIVOTTable' " + 
						"exec(@sql) ";
				
				System.out.println("Lấy doanh số theo miền: "+ query);
				this.rsDoanhSoMien=this.db.get(query);
				
				// LẤY DOANH SỐ NHÓM SẢN PHẨM
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
				System.out.println("Lấy báo cáo doanh số theo nhóm sản phẩm: "+query);
				this.rsDoanhSoNSP=this.db.get(query);
			}	
			else
			{
				return;
			}
		}
		else
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				
			}
			else
			{
				return;
			}
		}	
	}

	public void initDoanhThu(String query) {
		if(query.isEmpty())
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				setChietKhau(this.date);
				// LẤY BÁO CÁO DOANH THU THEO ĐỊA BÀN
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
						" set @sql = '	SELECT ''Doanh thu'' as type, '+@cols+'   " + 
						"				FROM(    " + 
						"							select TT_PK as PK_SEQ, sum(db.doanhthu)/1000000 as doanhthu " + 
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
						"									where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"									and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"									group by b.HOADON_FK, a.NPP_FK  " + 
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
						"	( SUM(a.doanhthu) FOR a.PK_SEQ  IN ('+@cols+') ) AS PIVOTTable'    " + 
						"exec(@sql)  " ;
				
				System.out.println("Lấy báo cáo doanh thu địa bàn: "+query);
				this.rsDoanhThuDiaBan=this.db.get(query);
				
				// LẤY BÁO CÁO DOANH THU THEO MIỀN
				query="declare @cols varchar(max), @sql varchar(max) " + 
						"set @cols = '[' + REPLACE(  " + 
						"			(SELECT distinct v.PK_SEQ as [data()]  " + 
						"								FROM NHAPHANPHOI npp inner join KHUVUC kv  " + 
						"								on kv.PK_SEQ = npp.KHUVUC_FK inner join VUNG v on kv.VUNG_FK = v.PK_SEQ " + 
						"								 WHERE npp.TRANGTHAI = 1 " + 
						"			FOR XML PATH('') " + 
						"				),' ','],[') + ']' ; " + 
						"set @sql = ' " + 
						"			SELECT N''Doanh thu'' as type, '+@cols+' " + 
						"			FROM( " + 
						"				select VUNG_PK as PK_SEQ, sum(db.doanhthu)/1000000 as doanhthu " + 
						"			from (   " + 
						"				select v.PK_SEQ as VUNG_PK,db.doanhso, db.chietkhau, db.doanhthu from (  " + 
						"				select 	hdOTC.NPP_FK,  " + 
						"				sum(hdOTC.AVAT) as doanhso, sum(isnull(ck.AVAT_CK,0)) as chietkhau,   " + 
						"				sum(hdOTC.AVAT - isnull(ck.AVAT_CK,0)) as doanhthu  " + 
						"						from   " + 
						"						(  select   " + 
						"							a.NPP_FK,  " + 
						"							b.HOADON_FK ,  " + 
						"							round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
						"							from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 				
						"							where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"							and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"							group by b.HOADON_FK, a.NPP_FK  " + 
						"							)as hdOTC  " + 
						"						left join ( "+ ck + 
						"					)as ck on ck.hoadon_fk = hdOTC.HOADON_FK  " + 
						"					group by hdOTC.NPP_FK  " + 
						"				) as db   " + 
						"				inner join NHAPHANPHOI npp on npp.PK_SEQ = db.NPP_FK    " + 
						"			    inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " + 
						"				inner join VUNG v on kv.VUNG_FK = v.PK_SEQ " + 
						"			    where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''100002'') " + 
						"				union all  " + 
						"				select v.PK_SEQ as PK_SEQ,hdETC.AVAT as doanhso, hdETC.AVAT_CK as chietkhau, (hdETC.AVAT - hdETC.AVAT_CK) as doanhthu				 " + 
						"				from             " + 
						"				(          " + 
						"						select  HOADON_FK,npp_fk,KHACHHANG_fk,ddkd_fk,   " + 
						"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       " + 
						"							sum( soluong * dongia )  as BVAT,( sum( soluong * dongia*thuexuat/100 ) ) as VAT,     " + 
						"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   " + 
						"									sum(isnull(chietkhau,0)*(1+thuexuat/100)) as AVAT_CK,              " + 
						"									sum(isnull(thuexuat,0)) as BVAT_CK           " + 
						"						from (      " + 
						"							select  c.HOADON_FK,a.npp_fk,a.KHACHHANG_fk,c.chietkhau,c.vat,   " + 
						"								(      " + 
						"									select top(1) bb.DDKD_FK       " + 
						"									from ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK       " + 
						"									where aa.HOADONNPP_FK=c.HOADON_FK  " + 
						"								) as ddkd_fk ,   " + 
						"									case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
						"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
						"							from ERP_HOADONNPP a     " + 
						"								inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
						"								inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
						"								inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
						"								where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
						"								 and a.NgayXuatHD like ''%"+date+"%'' " + 
						"			    				   " + 
						"						)ETC   " + 
						"						group by NPP_FK,KHACHHANG_FK,ddkd_fk,HOADON_FK   " + 
						"				)as hdETC " + 
						"				         " + 
						"				inner join ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADON_FK      " + 
						"				inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK " + 
						"				inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " + 
						"				inner join VUNG v on kv.VUNG_FK = v.PK_SEQ     " + 
						"				where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'')  " + 
						"				 " + 
						"				) as db group by db.VUNG_PK		 " + 
						"		) a " + 
						"			PIVOT " + 
						"		( SUM(a.doanhthu) FOR a.PK_SEQ  IN ('+@cols+') ) AS PIVOTTable' " + 
						"exec(@sql) ";
				
				System.out.println("Lấy báo cáo doanh thu theo miền: "+query);
				this.rsDoanhThuMien=this.db.get(query);
			}
			else
			{
				return;
			}
		}
		else
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				
			}
			else
			{
				return;
			}
		}	
	}
	
	public void initSanPham(String query) 
	{
		if(query.isEmpty())
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				// LẤY BÁO CÁO DOANH SỐ SẢN PHẨM MỚI
				query=	"declare @cols varchar(max), @sql nvarchar(max) " + 
						"	set @cols = '[' + REPLACE(  " + 
						"			(SELECT distinct SP.PK_SEQ AS [data()]  FROM SANPHAM SP inner join HOADON_SP HDSP on SP.PK_SEQ = HDSP.SANPHAM_FK  " + 
						"			WHERE SP.SPMOI = 1  " + 
						"			FOR XML PATH('') " + 
						"				),' ','],[') + ']' ; " + 
						"set @sql = ' " + 
						"			SELECT N''Doanh so'' as type,  '+@cols+' " + 
						"			FROM (  " + 
						"					select SP_FK as PK_SEQ, sum(db.doanhso)/1000000 as doanhso " + 
						"						from (   " + 
						"							select db.SP_FK ,db.doanhso from (  " + 
						"							select 	hdOTC.SP_FK,  " + 
						"								hdOTC.AVAT as doanhso " + 
						"									from   " + 
						"									(	select   " + 
						"										sp.PK_SEQ as SP_FK,  " + 
						"										round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
						"										from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 
						"										inner join SANPHAM sp on sp.PK_SEQ=b.SANPHAM_FK " + 
						"										where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"										and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"										and sp.SPMOI = 1 " + 
						"										group by sp.PK_SEQ						     " + 
						"										)as hdOTC  " + 
						"								) as db     " + 
						"							union all  " + 
						"							  " + 
						"							select hdETC.SANPHAM_FK as SP_FK ,sum(hdETC.AVAT) as doanhso		 " + 
						"							from             " + 
						"							(          " + 
						"								select  sanpham_fk, " + 
						"									sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT " + 
						"								from (      " + 
						"									select  c.sanpham_fk,			  " + 
						"									case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
						"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
						"									from ERP_HOADONNPP a     " + 
						"										inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
						"										inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
						"										inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
						"										where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
						"										 and a.NgayXuatHD like ''%"+date+"%'' " + 
						"										and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'' ) " + 
						"										 and d.SPMOI = 1 " + 
						"								)ETC   " + 
						"								group by sanpham_fk " + 
						"							)as hdETC         " + 
						"							group by hdETC.SANPHAM_FK " + 
						"						) as db group by db.SP_FK " + 
						"			) a " + 
						"			PIVOT " + 
						"			( SUM(a.doanhso) FOR a.PK_SEQ IN ('+@cols+') ) as PIVOTTable' " + 
						"exec (@sql)";
				System.out.println("Lấy doanh số theo sản phẩm mới: "+query);
				this.rsDoanhSoSPMoi=this.db.get(query);
				
				// LẤY BÁO CÁO DOANH SỐ SẢN PHẨM CHỦ LỰC
				
				query=	"declare @cols varchar(max), @sql nvarchar(max) " + 
						"	set @cols = '[' + REPLACE(  " + 
						"			(SELECT distinct SP.PK_SEQ AS [data()]  FROM SANPHAM SP inner join HOADON_SP HDSP on SP.PK_SEQ = HDSP.SANPHAM_FK  " + 
						"			WHERE SP.SPCHULUC = 1  " + 
						"			FOR XML PATH('') " + 
						"				),' ','],[') + ']' ; " + 
						"set @sql = ' " + 
						"			SELECT N''Doanh so'' as type,  '+@cols+' " + 
						"			FROM (  " + 
						"					select SP_FK as PK_SEQ, sum(db.doanhso)/1000000 as doanhso " + 
						"						from (   " + 
						"							select db.SP_FK ,db.doanhso from (  " + 
						"							select 	hdOTC.SP_FK,  " + 
						"								hdOTC.AVAT as doanhso " + 
						"									from   " + 
						"									(	select   " + 
						"										sp.PK_SEQ as SP_FK,  " + 
						"										round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
						"										from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 
						"										inner join SANPHAM sp on sp.PK_SEQ=b.SANPHAM_FK " + 
						"										where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"										and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"										 and sp.SPCHULUC = 1 " + 
						"										group by sp.PK_SEQ						     " + 
						"										)as hdOTC  " + 
						"								) as db     " + 
						"							union all  " + 
						"							  " + 
						"							select hdETC.SANPHAM_FK as SP_FK ,sum(hdETC.AVAT) as doanhso		 " + 
						"							from             " + 
						"							(          " + 
						"								select  sanpham_fk, " + 
						"									sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT " + 
						"								from (      " + 
						"									select  c.sanpham_fk,			  " + 
						"									case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
						"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
						"									from ERP_HOADONNPP a     " + 
						"										inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
						"										inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
						"										inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
						"										where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
						"										 and a.NgayXuatHD like ''%"+date+"%'' " + 
						"										and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'' ) " + 
						"										and d.SPCHULUC = 1  " + 
						"								)ETC   " + 
						"								group by sanpham_fk " + 
						"							)as hdETC         " + 
						"							group by hdETC.SANPHAM_FK " + 
						"						) as db group by db.SP_FK " + 
						"			) a " + 
						"			PIVOT " + 
						"			( SUM(a.doanhso) FOR a.PK_SEQ IN ('+@cols+') ) as PIVOTTable' " + 
						"exec (@sql)";
				System.out.println("Lấy doanh số theo sản phẩm chủ lực: "+ query);
				this.rsDoanhSoSPChuLuc=this.db.get(query);
			}
			else
			{
				return;
			}
		}
		else
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				
			}
			else
			{
				return;
			}
		}
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

	public String[] getArrTenDiaBan() {
		return this.mangtenDiaban;
	}

	public String[] getArrIDDiaBan() {
		return this.mangidDiaban;
	}

	public String[] getArrTenMien() {
		return this.mangtenMien;
	}

	public String[] getArrIDMien() {
		return this.mangidMien;
	}
	
	public String[] getArrTenNSP() {
		return this.mangtenNSP;
	}

	public String[] getArrIDNSP() {
		return this.mangidNSP;
	}

	public ResultSet getRsTonKhoDiaBan() {
		return this.rsTonKhoDiaBan;
	}

	public ResultSet getRsTonKhoMien() {
		return this.rsTonKhoMien;
	}

	public ResultSet getRsDoanhSoDiaBan() {
		return this.rsDoanhSoDiaBan;
	}

	public ResultSet getRsDoanhSoMien() {
		return this.rsDoanhSoMien;
	}

	public ResultSet getRsDoanhThuDiaBan() {
		return this.rsDoanhThuDiaBan;
	}

	public ResultSet getRsDoanhThuMien() {
		return this.rsDoanhThuMien;
	}

	public ResultSet getRsDoanhSoNSP() {
		return this.rsDoanhSoNSP; 
	}
	
	public ResultSet getRsDoanhSoSPChuLuc() {		
		return this.rsDoanhSoSPChuLuc;
	}
	
	public ResultSet getRsDoanhSoSPMoi() {		
		return this.rsDoanhSoSPMoi;
	}

	public String[] getArrTenSanPhamMoi() {
		return this.mangtenSPmoi;
	}

	public String[] getArrIDSanPhamMoi() {
		return this.mangidSPmoi;
	}

	public String[] getArrTenSanPhamChuLuc() {
		return this.mangtenSPChuLuc;
	}

	public String[] getArrIDSanPhamChuLuc() {
		return this.mangidSPChuLuc;
	}

}
