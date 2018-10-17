package geso.traphaco.distributor.beans.reports.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.reports.IBcDoanhThuSanPham;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;

public class BcDoanhThuSanPham extends Phan_Trang implements IBcDoanhThuSanPham, Serializable 
{
	
	public BcDoanhThuSanPham()
	{
		this.spId="";
		this.nppId="";
		this.userId="";
		this.khId="";
		this.msg="";
		this.ddkdId="";
		this.tuNgay="";
		this.denNgay="";
		this.kbhId="";
		this.ttId="";
		this.nhomId="";
		this.vungId="";
		this.loaiHoaDon="0";
		this.action="";
		this.cndt="0";
		this.kh="0";
		this.action="";
		db = new dbutils();
	}

  private static final long serialVersionUID = 1L;
	String tuNgay,denNgay,spId,nppId,ddkdId,userId,khId;
	public String getKhId()
  {
  	return khId;
  }
	public void setKhId(String khId)
  {
  	this.khId = khId;
  }
	public String getTuNgay()
  {
  	return tuNgay;
  }
	public void setTuNgay(String tuNgay)
  {
  	this.tuNgay = tuNgay;
  }
	public String getDenNgay()
  {
  	return denNgay;
  }
	public void setDenNgay(String denNgay)
  {
  	this.denNgay = denNgay;
  }
	public String getSpId()
  {
  	return spId;
  }
	public void setSpId(String spId)
  {
  	this.spId = spId;
  }
	public String getNppId()
  {
  	return nppId;
  }
	public void setNppId(String nppId)
  {
  	this.nppId = nppId;
  }
	public String getDdkdId()
  {
  	return ddkdId;
  }
	public void setDdkdId(String ddkdId)
  {
  	this.ddkdId = ddkdId;
  }
	public String getUserId()
  {
  	return userId;
  }
	public void setUserId(String userId)
  {
  	this.userId = userId;
  }
	public ResultSet getSpRs()
  {
  	return spRs;
  }
	public void setSpRs(ResultSet spRs)
  {
  	this.spRs = spRs;
  }
	public ResultSet getDdkdRs()
  {
  	return ddkdRs;
  }
	public void setDdkdRs(ResultSet ddkdRs)
  {
  	this.ddkdRs = ddkdRs;
  }
	ResultSet spRs,ddkdRs,khRs,hoadonRs;
	public ResultSet getHoadonRs()
  {
  	return hoadonRs;
  }
	public void setHoadonRs(ResultSet hoadonRs)
  {
  	this.hoadonRs = hoadonRs;
  }
	public ResultSet getKhRs()
  {
  	return khRs;
  }
	public void setKhRs(ResultSet khRs)
  {
  	this.khRs = khRs;
  }
	
	public void closeDB()
	{
		
	}
	
	dbutils db = new dbutils();
	public void createRs()
	{
		
		Utility util = new Utility();
		
		String query="select pk_Seq,ma + '-' + ten as Ten from sanpham where trangthai=1 ";
		
		if(this.nhomId.length()>0)
		{
			query += " and pk_Seq in (select sanpham_fk from nhomhang_sanpham where nhomhang_fk='"+this.nhomId+"' ) ";
		}
		query += "  order by ma  ";
		
		this.spRs = this.db.get(query);
		
		query = "select pk_seq, manhanvien,ten from DaiDienKinhDoanh a where 1=1  ";
		if(this.view.length()>0)
		{
			query += " and pk_seq in  " + util.Quyen_Ddkd(userId) ;
		}
		
		if(this.nppId.length() > 0)
			query += " and a.pk_seq in ( select ddkd_fk from daidienkinhdoanh_npp where npp_fk = '" + nppId + "')  ";
		this.ddkdRs = this.db.get(query);
	
		if(this.nppId.length()>0)
		{
			query="select pk_seq,isnull(mafast,'') +' ' + ten + ' ' + isnull(diachi,'') as Ten from khachhang where 1=1 ";
			
			if(this.view.length()>0)
			{
				query+=" and npp_fk in "+util.quyen_npp(userId);
			}
			
			if(this.nppId.length()>0)
			query+=" and npp_fk='"+this.nppId+"' ";
			
			this.khRs= this.db.get(query);
		}
		
		query="select pk_Seq,ten,DIENGIAI from KENHBANHANG where TRANGTHAI=1 ";
		this.kbhRs = this.db.get(query);	
		
		query="select pk_seq,ten from tinhthanh where 1=1 ";
		
		if(this.vungId.length()>0)
		{
			query+=" and vung_fk='"+this.vungId+"' ";
		}
		this.ttRs=this.db.get(query);
		
		query="select pk_seq,ten from vung where 1=1 ";
		query+=" and pk_Seq in  "+util.Quyen_Vung(userId)+" ";
		
		this.vungRs=this.db.get(query);
		
		
		query="select pk_Seq,ten from NhomHang ";
		this.nhomRs=this.db.get(query);
		
		
		query="select pk_Seq,ten,diachi from Nhaphanphoi where trangthai=1 and iskhachhang=0   ";
		if(nppId.length()>0)
		{
			query+=" and pk_seq ='"+this.nppId+"' ";
		}
	/*	query+= " union all select pk_Seq,ten,diachi from Nhaphanphoi where trangthai=1 and iskhachhang=0  ";
		if(nppId.length()>0)
		{
			query+=" and pk_seq ='"+this.nppId+"' ";
		}
		if(this.view.length()>0)
		{
			query+="and pk_Seq in "+util.quyen_npp(userId)+"" ;
		}*/
		
		if(this.ttId.length()>0)
		query+=" and tinhthanh_Fk='"+this.ttId+"' ";
		
		if(this.vungId.length()>0)
			query+=" and khuvuc_fk in (select pk_seq from khuvuc where vung_fk='"+this.vungId+"' ) ";
		
		System.out.println("_NPP_"+query);
		
		this.nppRs=this.db.get(query);
		
		
	}
	
	String queryHd="";
	public String getQueryHd()
	{
		return queryHd;
	}
	public void setQueryHd(String query)
	{
		this.queryHd=query;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
	}
	
	Utility Ult = new Utility();
	public void init(String search)
	{
		
		if(this.view.length()<=0)
		{
			this.getNppInfo();
		}
		
		String query;
		

		String condition="";   
		
		if(this.loaiHoaDon.length()<=0)
		{
			condition += "and a.LOAIHOADON =0"; 
		}
		else 
		{
			condition += "and a.LOAIHOADON ='"+this.loaiHoaDon+"' ";
		}
			   
		if(this.tuNgay.length()>0)
		{
			condition+=" and a.NgayXuatHD>='"+this.tuNgay+"'";
		}
		if(this.denNgay.length()>0)
		{
			condition+=" and a.NgayXuatHD <='"+this.denNgay+"'";
		}
		if(this.nppId.length()>0)
		{
			condition+=" and a.npp_fk ='"+this.nppId+"'";
		}
		 if(vungId.length()>0)
	   {
			 condition+= " and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in (select pk_seq from KHUVUC WHERE VUNG_FK='"+this.vungId+"' ))";
	   }
		
		if(this.spId.length()>0)
		{
			condition+=" and b.sanpham_fk ='"+this.spId+"'";
		}
		if(this.khId.length()>0)
		{
			condition+=" and a.khachhang_Fk ='"+this.khId+"'";
		}
		
		if(this.ttId.length()>0)
		{
			condition+=" and a.khachHang_fk in (select pk_seq from khachhang  where tinhthanh_fk='"+this.ttId+"' ) ";
		}
		
		if(this.nhomId.length()>0)
		{
			condition+=" and b.SanPham_fk in  (select SANPHAM_FK FROM NHOMHANG_SANPHAM  where NhomHang_fk='"+this.nhomId+"' )  ";
		}
		
		if(this.kbhId.length()>0)
		{
			condition+=" and a.pk_seq in (select hoadon_fk from hoadon_Ddh where ddh_fk in (select pk_seq from DonHang where kbh_fk='"+this.kbhId+"')) ";
		}
		
		if(this.ddkdId.length()>0)
		{
			condition += " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.ddkdId+"'))";
		}
		
		
		String conditionETC="";   
		
		if(this.loaiHoaDon.length()<=0)
		{
			conditionETC+= "and isnull(a.LOAIHOADON,0) =0"; 
		}
		else 
		{
			conditionETC+= "and isnull(a.LOAIHOADON,0)  ='"+this.loaiHoaDon+"' ";
		}
		
		if(this.tuNgay.length()>0)
		{
			conditionETC+=" and a.NgayXuatHD>='"+this.tuNgay+"'";
		}
		if(this.denNgay.length()>0)
		{
			conditionETC+=" and a.NgayXuatHD <='"+this.denNgay+"'";
		}
		if(this.nppId.length()>0)
		{
			conditionETC+=" and a.npp_fk ='"+this.nppId+"'";
		}
		if(vungId.length()>0)
	    {
			conditionETC+= "  and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in (select pk_seq from KHUVUC WHERE VUNG_FK='"+this.vungId+"' ))";
	    }
		
		if(this.spId.length()>0)
		{
			conditionETC+=" and C.sanpham_fk ='"+this.spId+"'";
		}
		if(this.khId.length()>0)
		{
			conditionETC+=" and a.pk_seq in (select hoadonnpp_fk from erp_hoadonnpp_Ddh where ddh_fk in (select pk_seq from ErpDonDatHangNpp where kbh_fk='"+this.kbhId+"')) ";
		}
		
		if(this.ttId.length()>0)
		{
			conditionETC+=	" and	(  a.khachHang_FK  in (select pk_seq	 from KhachHang  where tinhthanh_fk='"+this.ttId+"' ) OR  a.npp_dat_Fk in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' )  ) ";
		}
		
		if(this.nhomId.length()>0)
		{
			conditionETC+=" and C.SanPham_fk in  (select SANPHAM_FK FROM NHOMHANG_SANPHAM  where NHOMHANG_FK='"+this.nhomId+"' )  ";
		}
		
		
		if(this.kbhId.length()>0)
		{
			conditionETC+=" and a.pk_seq in (select hoadonnpp_fk from Erp_hoadonnpp_Ddh where ddh_fk in (select pk_seq from Erp_DonDatHangNpp where kbh_fk='"+this.kbhId+"' )) ";
		}
		
		if(this.ddkdId.length()>0)
		{
			conditionETC += " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.ddkdId+"'))";
		}
	 	
		
		   query="";
	 		   if(this.action.length()>0)
	 		   {
	 					/*query=
	 							 "select spMa,spTen,spDonVi,SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT, STT  \n"+   
	 						   "   from   \n"+   
	 						   "   (  \n"+   
	 						   
	 						   "   	select  sp.ma as spMa,sp.TEN as spTen,spDonVi   	,     \n"+   
	 						   "   		'OTC' as LoaiHD, hdOTC.SoLuong,hdOTC.DonGia ,hdOTC.BVAT, hdOTC.VAT ,hdOTC.AVAT  ,0 as STT            \n"+   
	 						   "   	from                     \n"+   
	 						   "   	(                     \n"+   
								 "			  select b.sanpham_fk,  \n"+
								 "						sum(round(b.SoLuong*b.DONGIA,0)) as BVAT,  \n"+   
								 "						sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100),0) ) as AVAT , \n"+
								 "						sum( round(  round(b.SoLuong*b.DONGIA,0)*(b.vat/100),0 ) ) as VAT,    \n"+
								 "						SUM(b.SoLuong)as SoLuong,AVG(b.DonGia) as DonGia ,  b.DONVITINH   as spDonVi  \n"+                   
								 "		from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ                       \n"+
								 "		where  a.TRANGTHAI not in  (1,3,5) and a.LOAIHOADON ='0' "+condition+"  \n"+     
								 "			group by  b.sanpham_fk ,b.DONVITINH     \n";
	 						   if(this.loaiHoaDon.equals("1"))
	 						   query+=hdKM;
	 						   
	 						   query+=
	 						   "   	)as hdOTC                    \n"+   
	 						   "   	inner join sanpham sp on sp.pk_Seq=hdOTC.sanpham_fk    \n"+   
	 						   "   	  \n"+   
	 						   "   	UNION ALL   \n"+   
	 						   "     \n"+   
	 						   "   	select  spMa,spTen,spDonVi, 'ETC' as LoaiHD,SUM(ETC.SoLuong)as SoLuong,AVG(ETC.DonGia) as DonGia,  \n"+   
	 						   "					sum(ROUND(ETC.SoLuong*ETC.DONGIA,0))-sum(ROUND(ETC.ChietKhau,0)) as BVAT,        "+
	 						   "					sum(round( ROUND(ETC.SoLuong*ETC.DONGIA,0)*(ETC.thuexuat/100),0 ))-sum( round(ETC.ChietKhau,0)*(ETC.thuexuat/100))as VAT ,   "+
	 						   "					sum(round( ROUND(ETC.SoLuong*ETC.DONGIA,0)*(1+ETC.thuexuat/100),0 ))-sum( round(ETC.ChietKhau,0)*(1+ETC.thuexuat/100))  AS AVAT,0 as STT   "+
	 						   "   	from       \n"+   
	 						   "   	(      \n"+   
	 						   "   			select c.sanpham_fk,a.KHACHHANG_FK,    \n"+   
	 						   "   			(   \n"+   
	 						   "   				select bb.DDKD_FK    \n"+   
	 						   "   				from ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK    \n"+   
	 						   "   				where aa.HOADONNPP_FK=a.PK_SEQ   \n"+   
	 						   "   			) as ddkd_fk,c.HOADON_FK,a.npp_fk,   \n"+   
	 						   "   			case     when c.donvitinh = e.donvi then c.soluong          \n"+   
	 						   "   						  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,       \n"+   
	 						   "   			case     when c.donvitinh = e.donvi then c.dongia          \n"+   
	 						   "   						  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia,        \n"+   
	 						   "   			c.vat as thuexuat , round(isnull(c.CHIETKHAU,0),0) as CHIETKHAU ,d.MA as spMa,d.TEN as spTen,e.DONVI as spDonVi      \n"+   
	 						   "   		from ERP_HOADONNPP a            \n"+   
	 						   "   		inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk           \n"+   
	 						   "   		inner join SANPHAM d on c.sanpham_fk = d.pk_seq       \n"+   
	 						   "   		inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq       \n"+   
	 						   "   		where 1=1  and a.trangthai not in ( 1 , 3, 5 )  "+conditionETC+"    \n"+   
	 						   "   	)  ETC     \n"+   
	 						   "   	where soluong>0    \n"+   
	 						   "   	group by  ETC.sanpham_fk  ,spMa,spTen,spDonVi  \n";
	 						   
	 						  "		union all   \n"+
								"		select c.MA as spMa,c.TEN as spTen,d.DONVI as spDonVi,'TRA' as LoaiHD,  \n"+
								"			(-1)* SUM(b.SoLuong)as SoLuong,   \n"+
								"			(-1)* AVG(b.DonGia) as DonGia  ,     \n"+
								"			(-1)*sum(round(b.SoLuong*b.DONGIA,0)) as BVAT,       \n"+
								"			(-1)*sum( round(  round(b.SoLuong*b.DONGIA,0)*(b.ptVat/100),0 ) ) as VAT , \n"+
								"			(-1)*sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.ptVat/100),0) ) as AVAT,0 as STT  \n"+
								"		from DONTRAHANG a inner join DONTRAHANG_SP b on b.DONTRAHANG_FK=a.PK_SEQ  \n"+                                                                           	
								"			inner join SANPHAM c on c.PK_SEQ=b.SANPHAM_FK \n"+
								"			inner join donvidoluong d on d.pk_Seq=c.dvdl_Fk \n"+
								"			where a.TrangThai=2    "+condition_TRA+" \n"+ 
								"		group by c.MA,c.TEN,d.DONVI \n"+
								
								" union all	" +
								
								"select  sp.ma as spMa,sp.TEN as spTen,spDonVi   	,  "+     
								" 'HO' as LoaiHD, HO.SoLuong,HO.DonGia ,HO.BVAT-HO.bvat_ck as BVAT, HO.VAT-HO.vat_ck AS VAT ,HO.AVAT -HO.avat_ck as AVAT,0 as STT  "+
								"	from                      "+
								"	(                      "+
								"			select b.sanpham_fk,  "+ 
								"			sum(round(b.soluong*b.dongia,0)) as bvat,  "+
								"			sum( round( round( b.soluong*b.dongia,0) *(1+ b.thuesuat/100) ,0)) as avat , "+  
								"			sum( round( round( b.soluong*b.dongia,0) *(b.thuesuat/100) ,0)) as vat, sum(b.soluong)as soluong, "+
								"			avg(b.dongia) as dongia,b.donvitinh   as spdonvi , "+
								"			sum(round(b.chietkhau,0)) as bvat_ck, "+
								"			sum(round( round(b.chietkhau,0)*b.thuesuat/100,0)) as vat_ck, "+
								"			sum(round( round(b.chietkhau,0)*(1+b.thuesuat)/100,0)) as avat_ck "+
								"	from erp_hoadon a inner join erp_hoadon_sp b on b.hoadon_fk=a.pk_seq "+                       
								"	where  a.trangthai not in  (1,3,5)   "+conditionHO+" "+  
								"	group by  b.sanpham_fk ,b.donvitinh      "+
								"	)as HO                     "+
								"	inner join sanpham sp on sp.pk_Seq=HO.sanpham_fk ";
	 						   
	 						   
	 							*//********Lọc 1 sản phẩm thì k quan tâm CK********//*
	 					   if(this.spId.length()<=0)
	 						   {
	 						  	 query+=
	 							   "   	UNION ALL  \n"+   
	 							   "   	select a.diengiai as spMa,b.Ten as spTen,'' as spDonVi,'OTC' as Loai,0 as SoLuong,0 as DonGia  \n"+   
	 							   "  ,-1*SUM(ROUND(a.chietkhau,0)) as BVAT,(-1)*SUM(ROUND(    ROUND(a.chietkhau,0)*a.thuevat/100.0,0 )) as VAT,  \n"+      
						  	 	 "  (-1)*SUM(ROUND(  ROUND( a.chietkhau,0)*(1+ a.thuevat/100.0),0 ,0 )) as AVAT  ,1 as STT          \n "+
	 							   "   	from HOADON_CHIETKHAU a left join LoaiCK b on b.Ma=a.diengiai  \n"+   
	 							   "   		where  isnull(a.HienThi,0)=1 and a.hoadon_fk in   \n"+   
	 							   "   		(  \n"+   
	 							   "   			select pk_Seq from HOADON a where LOAIHOADON=0 and a.TRANGTHAI not IN (1,3,5) "+condition_CK+"  \n"+   
	 							   "   		)  \n"+   
	 							   "   	group by a.diengiai,B.Ten  \n";
	 						   }
	 						   query+=
	 						   "     \n"+   
	 						   "   ) as HD   \n";
	 						   query+=
	 						   "   group by STT,spMa,spTen,spDonVi,STT  ";*/
	 		  	 
	 		  	query=
	 		  	"select ddkdTEN,spMa,spTen,spDonVi,SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT,STT "+ 
	 		  	"from"+ 
	 		  	"("+ 
	 		  	"	select spMa,spTen,spDonVi,SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT, STT "+ 
	 		  	"		,(	select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ    "+ 
	 		  	"					inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ   "+ 
	 		  	"			where cc.KHACHHANG_FK = HD.KHACHHANG_FK  "+ 
	 		  	"		) as ddkdTEN"+ 
	 		  	"	from   "+ 
	 		  	"	(  "+ 
	 		  	"		select  sp.ma as spMa,sp.TEN as spTen,spDonVi   	,     "+ 
	 		  	"			'OTC' as LoaiHD, hdOTC.SoLuong,hdOTC.DonGia ,hdOTC.BVAT, hdOTC.VAT ,hdOTC.AVAT  ,0 as STT ,KHACHHANG_FK           "+ 
	 		  	"		from                     "+ 
	 		  	"		(                     "+ 
	 		  	"				  select b.sanpham_fk,  "+ 
	 		  	"							sum(round(b.SoLuong*b.DONGIA,0)) as BVAT,  "+ 
	 		  	"							sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100),0) ) as AVAT , "+ 
	 		  	"							sum( round(  round(b.SoLuong*b.DONGIA,0)*(b.vat/100),0 ) ) as VAT,    "+ 
	 		  	"							SUM(b.SoLuong)as SoLuong,AVG(b.DonGia) as DonGia ,  b.DONVITINH   as spDonVi  ,a.KHACHHANG_FK"+ 
	 		  	"			from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ                       "+ 
	 		  	"			where  a.TRANGTHAI not in  (1,3,5) and a.LOAIHOADON ='0' and a.LOAIHOADON ='0'  "+condition+"  "+ 
	 		  	"				group by  b.sanpham_fk ,b.DONVITINH ,a.KHACHHANG_FK    "+ 
	 		  	"		)as hdOTC                    "+ 
	 		  	"		inner join sanpham sp on sp.pk_Seq=hdOTC.sanpham_fk    "+ 
	 		  	"		  "+ 
	 		  	"		UNION ALL   "+ 
	 		  	"		 "+ 
	 		  	"		select  spMa,spTen,spDonVi, 'ETC' as LoaiHD,SUM(ETC.SoLuong)as SoLuong,AVG(ETC.DonGia) as DonGia,  "+ 
	 		  	"						sum(ROUND(ETC.SoLuong*ETC.DONGIA,0))-sum(ROUND(ETC.ChietKhau,0)) as BVAT, "+ 
	 		  	"						sum(round( ROUND(ETC.SoLuong*ETC.DONGIA,0)*(ETC.thuexuat/100),0 ))-sum( round(ETC.ChietKhau,0)*(ETC.thuexuat/100))as VAT ,  "+ 
	 		  	"						 sum(round( ROUND(ETC.SoLuong*ETC.DONGIA,0)*(1+ETC.thuexuat/100),0 ))-sum( round(ETC.ChietKhau,0)*(1+ETC.thuexuat/100))  AS AVAT,0 as STT ,KHACHHANG_FK"+ 
	 		  	"		from       "+ 
	 		  	"		(      "+ 
	 		  	"				select c.sanpham_fk,a.KHACHHANG_FK,    "+ 
	 		  	"				(   "+ 
	 		  	"					select bb.DDKD_FK    "+ 
	 		  	"					from ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK    "+ 
	 		  	"					where aa.HOADONNPP_FK=a.PK_SEQ   "+ 
	 		  	"				) as ddkd_fk,c.HOADON_FK,a.npp_fk,   "+ 
	 		  	"				case     when c.donvitinh = e.donvi then c.soluong          "+ 
	 		  	"							  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,       "+ 
	 		  	"				case     when c.donvitinh = e.donvi then c.dongia          "+ 
	 		  	"							  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia,        "+ 
	 		  	"				c.vat as thuexuat , round(isnull(c.CHIETKHAU,0),0) as CHIETKHAU ,d.MA as spMa,d.TEN as spTen,e.DONVI as spDonVi      "+ 
	 		  	"			from ERP_HOADONNPP a            "+ 
	 		  	"			inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk           "+ 
	 		  	"			inner join SANPHAM d on c.sanpham_fk = d.pk_seq       "+ 
	 		  	"			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq       "+ 
	 		  	"			where 1=1  and a.trangthai not in ( 1 , 3, 5 )  "+ 
	 		  	"			and isnull(a.LOAIHOADON,0)  ='0'  "+conditionETC+"  "+ 
	 		  	"		)  ETC     "+ 
	 		  	"		where soluong>0    "+ 
	 		  	"		group by  ETC.sanpham_fk  ,spMa,spTen,spDonVi  ,KHACHHANG_FK"+ 
	 		  	"		UNION ALL  "+ 
	 		  	"		select b.diengiai as spMa,c.Ten as spTen,'' as spDonVi,'OTC' as Loai,0 as SoLuong,0 as DonGia  "+ 
	 		  	"		,-1*SUM(ROUND(a.chietkhau,0)) as BVAT,(-1)*SUM(ROUND(    ROUND(a.chietkhau,0)*b.thuevat/100.0,0 )) as VAT,  "+ 
	 		  	"		(-1)*SUM(ROUND(  ROUND( a.chietkhau,0)*(1+ b.thuevat/100.0),0 ,0 )) as AVAT  ,1 as STT,a.KHACHHANG_FK          "+ 
	 		  	"			from HOADON a left join HOADON_CHIETKHAU b on b.hoadon_fk=a.PK_SEQ  "+ 
	 		  	"			inner join LoaiCK c on c.Ma=b.diengiai "+ 
	 		  	"			where  isnull(b.HienThi,0)=1 "+ 
	 		  	"			and a.LOAIHOADON=0 and a.TRANGTHAI not IN (1,3,5) and isnull(a.LOAIHOADON,0)  ='0'  "+ 
	 		  	"				"+condition+ "  "+ 
	 		  	"		group by b.diengiai,c.Ten  ,a.KHACHHANG_FK"+ 
	 		  	"	 "+ 
	 		  	"	) as HD   "+ 
	 		  	"	group by STT,spMa,spTen,spDonVi,STT  ,KHACHHANG_FK"+ 
	 		  	")as hd "+ 
	 		  	"group by spMa,spTen,spDonVi,ddkdTEN,STT " ;
	 						  setTotal_Query(query);
	 					System.out.println("[BcDoanhThuSanPham.java]"+query);
	 					this.queryHd=query+" order by ddkdTen DESC ,STT ,spMa asc ";
	 		   }
	 		  this.hoadonRs=this.createSplittingData(100, super.getSplittings(), "ddkdTen DESC ,STT ,spMa asc ", query);
	 		 	
	 			createRs();
	 	}
	
	String view,msg;
	public String getMsg()
  {
  	return msg;
  }
	public void setMsg(String msg)
  {
  	this.msg = msg;
  }
	public String getView()
  {
  	return view;
  }
	public void setView(String view)
  {
  	this.view = view;
  }
	
	String kbhId;
	ResultSet kbhRs;
	public String getKbhId()
  {
  	return kbhId;
  }
	public void setKbhId(String kbhId)
  {
  	this.kbhId = kbhId;
  }
	public ResultSet getKbhRs()
  {
  	return kbhRs;
  }
	public void setKbhRs(ResultSet kbhRs)
  {
  	this.kbhRs = kbhRs;
  }
	
	public void setTotal_Query(String searchquery) 
	{
			String query;
	 		   query="";
	 		   if(this.action.length()>0)
	 		   {
	 					query=
	 							 "select SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT  \n"+   
	 						   "   from  ("+searchquery+")   as HD   \n";
							   this.totalRs= db.get(query);		
							   /*System.out.println("---QUYRY TOTAL: " + query);*/
							   this.totalRs=this.db.get(query);
	 		   }	 	
	}
	
	
	ResultSet totalRs;
	public ResultSet getTotalRs()
	{
		return totalRs;
	}
	public void setTotalRs(ResultSet totalRs)
	{
		this.totalRs=totalRs;
	}
	
	String vungId,nhomId,ttId;
	ResultSet vungRs,nhomRs,ttRs;
	public String getVungId()
  {
  	return vungId;
  }
	public void setVungId(String vungId)
  {
  	this.vungId = vungId;
  }
	public String getNhomId()
  {
  	return nhomId;
  }
	public void setNhomId(String nhomId)
  {
  	this.nhomId = nhomId;
  }
	public String getTtId()
  {
  	return ttId;
  }
	public void setTtId(String ttId)
  {
  	this.ttId = ttId;
  }
	public ResultSet getVungRs()
  {
  	return vungRs;
  }
	public void setVungRs(ResultSet vungRs)
  {
  	this.vungRs = vungRs;
  }
	public ResultSet getNhomRs()
  {
  	return nhomRs;
  }
	public void setNhomRs(ResultSet nhomRs)
  {
  	this.nhomRs = nhomRs;
  }
	public ResultSet getTtRs()
  {
  	return ttRs;
  }
	public void setTtRs(ResultSet ttRs)
  {
  	this.ttRs = ttRs;
  }
	
	ResultSet nppRs;
	@Override
  public ResultSet getNppRs()
  {
	  // TODO Auto-generated method stub
	  return nppRs;
  }
	@Override
  public void setNppRs(ResultSet nppRs)
  {
		this.nppRs=nppRs;
	  
  }
	
	String loaiHoaDon;
	public String getLoaiHoaDon()
  {
  	return loaiHoaDon;
  }
	public void setLoaiHoaDon(String loaiHoaDon)
  {
  	this.loaiHoaDon = loaiHoaDon;
  }

	String action;
	public String get_Action()
  {
  	return action;
  }
	public void set_Action(String timkiem)
  {
  	this.action = timkiem;
  }
	
	String cndt,kh;
	private String nhomhangId;
	private ResultSet nhomhangRs;
	
	public String 	getMucCN_DT()
	{
	  return 	this.cndt;
	}
	
	public void setMucCN_DT(String cndt)
	{
		this.cndt=cndt;
	}
	
	public String getMuc_KhachHang()
	{
		return 	this.kh;
	}
	
	public void setMuc_KhachHang(String cndt)
	{
		this.kh=cndt;
	}
	@Override
	public String getNhomhangId() {
		return this.nhomhangId;
	}
	@Override
	public void setNhomhangId(String value) {
		this.nhomhangId = value;
	}
	@Override
	public ResultSet getNhomhangRs() {
		return nhomhangRs;
	}
	
}
