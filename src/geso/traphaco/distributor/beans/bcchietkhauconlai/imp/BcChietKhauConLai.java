package geso.traphaco.distributor.beans.bcchietkhauconlai.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.bcchietkhauconlai.IBcChietKhauConLai;
import geso.traphaco.distributor.db.sql.dbutils;

import java.sql.ResultSet;

public class BcChietKhauConLai extends Phan_Trang implements IBcChietKhauConLai
{
	
	
	public BcChietKhauConLai()
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
		this.loaiHoaDon="";
		this.action="";
		db = new dbutils();
	}
	/**
   * 
   */
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
		String query="select pk_Seq,ma,ten from sanpham where trangthai=1";
		this.spRs=this.db.get(query);
		
		query="select pk_seq,manhanvien,ten from DaiDienKinhDoanh a where 1=1  ";
		
		if(this.nppId.length()>0)
		{
			query+=" and a.pk_seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk='"+nppId+"') ";
		}
		if(this.view.length()>0)
		{
			query+=" and pk_Seq in  "+util.Quyen_Ddkd(this.userId)+" ";
		}
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
		
		
		query="select PK_SEQ,TEN from tinhthanh   where 1=1 ";
		if(vungId.length()>0)
			query+=" and vung_fk='"+vungId+"'";
		if(this.view.length()>0)
			query+=" and pk_Seq in  "+util.Quyen_TinhThanh(this.userId)+" ";
		
		this.ttRs= this.db.get(query);
		
		query="select pk_seq ,ten,VUNG_FK from khuvuc  where 1=1 ";
		if(this.view.length()>0)
			query+=" and pk_Seq in  "+util.Quyen_KhuVuc(this.userId)+" ";
		this.kvRs=this.db.get(query);
		
		query="select pk_seq,ten from vung where 1=1 ";
		if(this.view.length()>0)
			query+=" and pk_Seq in  "+util.Quyen_Vung(this.userId)+" ";
		this.vungRs=this.db.get(query);
		
		
		query="select pk_seq,ma,diachi,ten from nhaphanphoi where trangthai=1  and isnull(isKHACHHANG,0)=0";
		if(this.view.length()>0)
		{
			query+="and pk_Seq in "+util.quyen_npp(userId)+"" ;
		}
		
		if(this.ttId.length()>0)
		query+=" and tinhthanh_Fk='"+this.ttId+"' ";
		
		if(this.vungId.length()>0)
			query+=" and khuvuc_fk in (select pk_seq from khuvuc where vung_fk='"+this.vungId+"' ) ";		
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
	
	public void init(String search)
	{
		String query;
		
		String condition="";   
	
		if(this.nppId.length()>0)
		{
			condition+=" AND DH.NPP_FK='"+this.nppId+"' ";
		}
		
		
		if(this.tuNgay.length()>0&&this.denNgay.length()>0)
		{
			int quyHIENTAI = Integer.parseInt(this.tuNgay);
			int namHIENTAI = Integer.parseInt(this.denNgay);
			
			int thangBATDAU = 0;
			int thangKETTHUC = 0;
			
			int thangBATDAU_QUYTIEPTHEO = 0;
			int thangKETTHUC_QUYTIEPTHEO = 0;
			
			int nam_QUYTIEPTHEO = Integer.parseInt(this.denNgay);
			
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
			
	   /*
	    * 
	    * Lấy HD Chi nhánh cấp 1,Chi nhánh cấp 2,Quầy bán buôn,Quầy Traphaco
	    * 
	    */
	   query="";
	   if(this.action.length()>0)
	   {
				query=
						 "select TOP(1) *, "+      
					   "   	case when xuatkhau = 0 then 8 * thucthu / 100 else 6 * thucthu / 100 end as chietkhau_chuavat,"+      
					   "   	case when xuatkhau = 0 then 1.05 * 8 * thucthu / 100 else 1.05 * 6 * thucthu / 100 end as chietkhau_covat,"+      
					   "   	ISNULL( ( select sum(thanhtoan) from DUYETTRAKHUYENMAI_DONHANG where khachhang_fk = TL2.pk_seq and tichluyQUY = '0'"+      
					   "   				and donhang_fk in ( select pk_seq from DONHANG where npp_fk = '"+this.nppId+"' and ngaynhap <= '"+this.denNgay+"' ) ), 0 ) as daHUONG	"+      
					   "   from"+      
					   "   ("+      
					   "   	select *, case when TL.thanhtoan = '0' then tongTHUVEThangtruoc else tongTHUVEThangtruoc / 1.05 end as thucthu"+      
					   "   	from"+      
					   "   	("+      
					   "   		select kh.pk_seq, kh.maFAST, kh.ten,kh.diachi, isnull( PT_CHIETKHAU, 0) as PT_CHIETKHAU, isnull(kh.thanhtoan, 0) as thanhtoan,  "+      
					   "   				( select loaiNPP from NHAPHANPHOI where pk_seq = '"+this.nppId+"' ) as loaiNPP, kh.xuatkhau,"+      
					   "   			ISNULL( ( select sum( soluong * dongiaGOC )  as toTAL   "+      
					   "   					  from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk  " +
					   "	where and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0'																		";  
					   
					   /*if(this.nppId.equals("111") || this.nppId.equals("111") )
					   {
					  	 query=
					  	  " a.ngayxuatHD >= '2014-01-01' and a.ngayxuatHD <= '2014-08-31'   ";
					   }*/
					   
					   query+=
					   "   							and a.khachhang_fk = kh.pk_seq  "+      
					   "   							and cast( month(a.ngayxuatHD) as varchar(10) ) + cast ( year(a.ngayxuatHD) as varchar(10) ) in  "+      
					   "   									( select thang + nam from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk where khachhang_fk = kh.pk_seq and trangthai = '1' and loaict = '0' and nam = '2014' and db.NPP_FK = '106210' ) ) ,0 ) as tongDSThangtruoc,"+      
					   "   				ISNULL( (	select sum(tongtien)   "+      
					   "   						from  "+      
					   "   						(  "+      
					   "   							select sum(tongtienAVAT) as tongtien  "+      
					   "   							from HOADON a    "+      
					   "   							where a.ngayxuatHD >= '2014-01-01' and a.ngayxuatHD <= '2014-08-31' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0'  "+      
					   "   								and a.khachhang_fk = kh.pk_seq   "+      
					   "   								and cast( month(a.ngayxuatHD) as varchar(10) ) + cast ( year(a.ngayxuatHD) as varchar(10) ) in  "+      
					   "   										( select thang + nam from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk where khachhang_fk = kh.pk_seq and trangthai = '1' and loaict = '0' and nam = '2014' and db.NPP_FK = '106210' ) "+      
					   "   						)  "+      
					   "   						total ) , 0 ) as tongTHUVEThangtruoc    "+      
					   "   		from KHACHHANG kh  "+      
					   "   		where kh.npp_fk = '106210' and kh.KhongKyHopDong = '0' and kh.maFAST = 'K1NT149'"+      
					   "   				and kh.pk_seq in ( select khachhang_fk from DUYETBANDUNGGIA_KHACHHANG where duyet_fk in ( select pk_seq from DUYETBANDUNGGIA where trangthai = '1' and loaict = '0' and nam = '2014' and NPP_FK = '106210' ) )  "+      
					   "   		group by kh.maFAST, kh.ten, kh.pk_seq, kh.xuatkhau, kh.thanhtoan, PT_CHIETKHAU  ,DIACHI"+      
					   "   	)"+      
					   "   	TL"+      
					   "   )"+      
					   "   TL2 ";
					this.queryHd=query;
	   }
	   System.out.println(":::::::"+query);
		createRs();
		setTotal_Query("");
		}
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
		
		String condition="";   
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
			 condition+= "  and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in (select pk_seq from KHUVUC WHERE VUNG_FK='"+this.vungId+"' ))";
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
			condition+=" and a.npp_fk in (select pk_seq	 from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
		}
		
		if(this.nhomId.length()>0)
		{
			condition+=" and b.SanPham_fk in  (select sp_FK FROM NHOMSANPHAM_SANPHAM  where nsp_fk='"+this.nhomId+"' )  ";
		}
		
		if(this.kbhId.length()>0)
		{
			condition+=" and a.khachhang_fk in ( select pk_Seq from KHACHHANG WHERE KBH_FK='"+this.kbhId+"' ) ";
		}
	
		
		
		String conditionETC="";   
		
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
			conditionETC+=" and a.khachhang_Fk ='"+this.khId+"'";
		}
		
		if(this.ttId.length()>0)
		{
			conditionETC+=" and a.npp_fk in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
		}
		
		if(this.nhomId.length()>0)
		{
			conditionETC+=" and C.SanPham_fk in  (select sp_FK FROM NHOMSANPHAM_SANPHAM  where nsp_fk='"+this.nhomId+"' )  ";
		}
		
		
		if(this.kbhId.length()>0)
		{
			conditionETC+=" and a.khachhang_fk in ( select pk_Seq from KHACHHANG WHERE KBH_FK='"+this.kbhId+"' ) ";
		}
		
		
		String condition_CK="";
	  if(nppId.length()>0)
	   {
	  	 condition_CK+= " and npp_fk='"+this.nppId+"' ";
	   }
	   
	  if(ttId.length()>0)
	   {
	  	 condition_CK+= "  and a.npp_fk in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' )";
	   }
	  
	  if(vungId.length()>0)
	   {
	  	 condition_CK+= "  and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in (select pk_seq from KHUVUC WHERE VUNG_FK='"+this.vungId+"' ))";
	   }
	  
	  
	   if(khId.length()>0)
	   {
	  	 condition_CK+= " and khachhang_fk='"+this.khId+"' ";
	   }
	   
	   if(tuNgay.length()>0)
	   {
	  	 condition_CK+= " and a.NgayXuatHD >='"+this.tuNgay+"' ";
	   }
	   
	   if(denNgay.length()>0)
	   {
	  	 condition_CK+= " and a.NgayXuatHD <='"+this.denNgay+"' ";
	   }
	   
	   if(spId.length()>0)
	   {
	  	 condition_CK+= " and a.pk_seq in  (select hoadon_fk from HOADON_SP where sanpham_fk='"+spId+"'  ) ";
	   }
	   
	   if(nhomId.length()>0)
	   {
	  	 condition_CK+= " and a.pk_seq in  (select hoadon_fk from HOADON_SP where sanpham_fk in   (select sp_FK FROM NHOMSANPHAM_SANPHAM  where nsp_fk='"+this.nhomId+"' )   )  ";
	   }
	   
	 	if(this.kbhId.length()>0)
		{
	 		condition_CK+=" and a.khachhang_fk in ( select pk_Seq from KHACHHANG WHERE KBH_FK='"+this.kbhId+"' ) ";
		}
	   /*
	    * 
	    * Lấy HD Chi nhánh cấp 1,Chi nhánh cấp 2,Quầy bán buôn,Quầy Traphaco
	    * 
	    */
	   query="";
	   if(this.action.length()>0)
	   {
				query=
					   "   select SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT   \n "+      
					   "   from   \n "+      
					   "   (   \n "+      
					   "   	select v.TEN as vTen,kv.TEN as kvTen,tt.TEN as ttTen,sp.ma as nppMa,sp.TEN as nppTen ,   \n "+      
					   "   	'OTC' as LoaiHD, hdOTC.SoLuong,hdOTC.DonGia ,hdOTC.BVAT, hdOTC.VAT ,hdOTC.AVAT   \n "+      
					   "   	from   \n "+      
					   "   	(   \n "+      
					   "   		select a.NPP_FK , round(sum(b.SoLuong*b.dongiaGOC),0) as BVAT,   \n "+      
					   "   		round(sum(b.SoLuong*b.dongiaGOC*(1+ b.vat/100) ),0) as AVAT ,round(sum(b.SoLuong*b.dongiaGOC*(b.vat/100) ),0) as VAT,   \n "+      
					   "   		SUM(b.SoLuong)as SoLuong,AVG(b.dongiaGOC) as DonGia   \n "+      
					   "   		from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ   \n "+      
					   "   		where  a.npp_fk  in (select pk_Seq from nhaphanphoi where loaiNPP not in (4,5) and loainpp is not null ) and  a.LOAIHOADON =0 and a.TRANGTHAI not in (1,3,5) "+condition+"  \n "+      
					   "   		group by a.NPP_FK   \n "+      
					   "   	)as hdOTC   \n "+      
					   "   	inner join NHAPHANPHOI sp on sp.pk_Seq=hdOTC.NPP_FK   \n "+      
					   "   	left join KHUVUC kv on kv.PK_SEQ=sp.KHUVUC_FK   \n "+      
					   "   	left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
					   "   	left join TINHTHANH tt on tt.PK_SEQ=sp.TINHTHANH_FK   \n "+      
						 "		union all   "+
						 "		select v.TEN as vTen,kv.TEN as kvTen,tt.TEN as ttTen,sp.ma as nppMa,sp.TEN as nppTen ,  "+   
						 "		'HO' as LoaiHD, hdOTC.SoLuong,hdOTC.DonGia ,hdOTC.BVAT, hdOTC.VAT ,hdOTC.AVAT     "+
						 "		from     "+
						 "		(     "+
						 "			select a.NPP_FK , round(sum(b.SoLuong*b.DONGIA),0) as BVAT,  "+   
						 "			round(sum(b.SoLuong*b.DONGIA*(1+ b.thuesuat/100) ),0) as AVAT ,round(sum(b.SoLuong*b.DONGIA*(b.thuesuat/100) ),0) as VAT,  "+   
						 "				SUM(b.SoLuong)as SoLuong,AVG(b.DonGia) as DonGia     "+
						 "				from ERP_HOADON a inner join ERP_HOADON_SP b on b.HOADON_FK=a.PK_SEQ  "+   
						 "			where   a.TRANGTHAI not in (1,3,5) "+condition+"    "+
						 "				group by a.NPP_FK     "+
						 "			)as hdOTC     "+
						 "		inner join NHAPHANPHOI sp on sp.pk_Seq=hdOTC.NPP_FK    "+
						 "			left join KHUVUC kv on kv.PK_SEQ=sp.KHUVUC_FK  "+   
						 "		left join VUNG v on v.PK_SEQ=kv.VUNG_FK     "+
						 "			left join TINHTHANH tt on tt.PK_SEQ=sp.TINHTHANH_FK  "+   					   
					   "      \n "+      
					   "   	UNION ALL   \n "+      
					   "      \n "+      
					   "   	select   \n "+      
					   "   	v.TEN as vTen,kv.TEN as kvTen,tt.TEN as ttTen,   \n "+      
					   "   	npp.MA as nppMa,npp.TEN as nppTen, 'ETC' as LoaiHD,SUM(ETC.SoLuong)as SoLuong,AVG(ETC.DonGia) as DonGia,   \n "+      
					   "   	sum(ETC.SoLuong*ETC.DONGIA) as BVAT,   \n "+      
					   "   	sum(ETC.SoLuong*ETC.DONGIA*(ETC.thuexuat/100) ) as VAT ,   \n "+      
					   "   	sum(ETC.SoLuong*ETC.DONGIA*(1+ ETC.thuexuat/100) ) as AVAT   \n "+      
					   "   from   \n "+      
					   "   (   \n "+      
					   "   		select a.npp_fk,   \n "+      
					   "   		case when c.donvitinh = e.donvi then c.soluong   \n "+      
					   "   		else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,   \n "+      
					   "   		case when c.donvitinh = e.donvi then c.dongia   \n "+      
					   "   		else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia,   \n "+      
					   "   		c.vat as thuexuat ,isnull(c.CHIETKHAU,0) as ChietKhau ,d.MA as spMa,d.TEN as spTen,e.DONVI as spDonVi   \n "+      
					   "   		from ERP_HOADONNPP a   \n "+      
					   "   		inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk   \n "+      
					   "   		inner join SANPHAM d on c.sanpham_fk = d.pk_seq   \n "+      
					   "   		inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   \n "+      
					   "   		where 1=1 and a.trangthai not in ( 1 , 3, 5 ) "+conditionETC+"   \n "+      
					   "   ) ETC   \n "+      
					   "   	inner join NHAPHANPHOI npp on npp.pk_Seq=ETC.NPP_FK   \n "+      
					   "   	left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
					   "   	left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
					   "   	left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
					   "   	where soluong>0   \n "+      
					   "   	group by v.TEN,kv.TEN,tt.TEN,npp.MA,npp.TEN   \n "+      
					   "      \n "+      
					   "   	UNION ALL   \n "+      
					   "   	select v.TEN as vTen,kv.TEN as kvTen,tt.TEN as ttTen,npp.MA as nppMa,npp.TEN as nppTen ,'OTC' as Loai,0 as SoLuong,0 as DonGia   \n "+      
					   "   		,-1*SUM(a.chietkhau) as BVAT,(-1)*SUM(a.chietkhau*a.thuevat/100.0) as VAT,   \n "+      
					   "   		(-1)*SUM(a.chietkhau*(1+ a.thuevat/100.0)) as AVAT   \n "+      
					   "   	from HOADON_CHIETKHAU a left join LoaiCK b on b.Ma=a.diengiai   \n "+      
					   "   		inner join HOADON c on c.PK_SEQ=a.hoadon_fk   \n "+      
					   "   		inner join NHAPHANPHOI npp on npp.pk_Seq=c.NPP_FK   \n "+      
					   "   		left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
					   "   		left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
					   "   		left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
					   "   	where a.hoadon_fk in   \n "+      
					   "   	(   \n "+      
					   "   	select pk_Seq from HOADON a where    a.npp_fk  in (select pk_Seq from nhaphanphoi where loaiNPP not in (4,5) and loainpp is not null ) and LOAIHOADON=0 and a.TRANGTHAI not IN (1,3,5)   "+condition_CK+"   \n "+      
					   "   	)   \n "+      
					   "   	group by v.TEN,kv.TEN,tt.TEN,npp.MA,npp.TEN   \n "+      
					   "      \n "+      
					   "   ) as HD   \n ";
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
	
	String vungId,nhomId,ttId,kvId;
	ResultSet vungRs,nhomRs,ttRs,kvRs;
	public String getKvId()
  {
  	return kvId;
  }
	public void setKvId(String kvId)
  {
  	this.kvId = kvId;
  }
	public ResultSet getKvRs()
  {
  	return kvRs;
  }
	public void setKvRs(ResultSet kvRs)
  {
  	this.kvRs = kvRs;
  }
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
	public String getAction()
  {
  	return action;
  }
	public void setAction(String timkiem)
  {
  	this.action = timkiem;
  }

	
	
}
