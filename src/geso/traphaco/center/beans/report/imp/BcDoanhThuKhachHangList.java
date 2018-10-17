package geso.traphaco.center.beans.report.imp;

import java.io.Serializable;
import java.sql.ResultSet;


import geso.traphaco.center.beans.report.IBcDoanhThuKhachHangList;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.donhang.IDonhangList;
import geso.traphaco.distributor.db.sql.dbutils;

public class BcDoanhThuKhachHangList  extends Phan_Trang implements IBcDoanhThuKhachHangList, Serializable
{
	
	
	private int num;
	private int[] listPages;
	private int currentPages;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3237541992706452258L;
	public BcDoanhThuKhachHangList()
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
		currentPages = 1;
		num = 1;
		this.xemtheo = 1;
		
		db = new dbutils();
	}
	/**
	 * 
	 */
	
	String tuNgay,denNgay,spId,nppId,ddkdId,userId,khId;
	int xemtheo=1; //=1 xem mac dinh theo khach hang, -1 xem theo trinh duoc vien
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
			
			if(this.ddkdId.length()>0)
				query+= " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.ddkdId+"'))";
			
			this.khRs= this.db.get(query);
		}		
		query="select pk_Seq,ten,DIENGIAI from KENHBANHANG where TRANGTHAI=1 ";
		this.kbhRs = this.db.get(query);	
		
		query="select PK_SEQ,TEN from tinhthanh   where 1=1 ";
		if(vungId.length()>0)
			query+=" and vung_fk='"+vungId+"'";
	if(this.view.length()>0)
			query+=" and pk_Seq in  "+util.Quyen_TinhThanhTheoKhachHang(this.userId)+" ";
		System.out.println("::::::::::::::::: dia ban :"+query);
		this.ttRs= this.db.get(query);
		
		
		query="select pk_seq ,ten,VUNG_FK from khuvuc  where 1=1 ";
		if(this.view.length()>0)
			query+=" and pk_Seq in  "+util.Quyen_KhuVuc(this.userId)+" ";
		this.kvRs=this.db.get(query);
		
		query="select pk_seq,ten from vung where 1=1 ";
		if(this.view.length()>0)
			query+=" and pk_Seq in  "+util.Quyen_Vung(this.userId)+" ";
		this.vungRs=this.db.get(query);
		
		System.out.println(":::::::::::::::;;; vung:");
		
		
		query="select pk_seq,ma,diachi,ten from nhaphanphoi where trangthai=1  and isnull(isKHACHHANG,0)=0";
		if(this.view.length()>0)
		{
			query+="and pk_Seq in "+util.quyen_npp(userId)+"" ;
		}
		
		if(this.ttId.length()>0)
			query+=" and tinhthanh_Fk='"+this.ttId+"' ";
		
		if(this.vungId.length()>0)
			query+=" and khuvuc_fk in (select PK_SEQ from khuvuc where vung_fk='"+this.vungId+"' ) ";		
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
	Utility Ult = new Utility();
	public void init(String search)
	{
		String query;
		
		String condition="";   
		
		condition+= " and a.npp_fk in " + Ult.quyen_npp(userId)+"";		 
		
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
			condition+=" and a.khachHang_fk in (select pk_seq	 from KhachHang  where tinhthanh_fk='"+this.ttId+"' ) ";
		}
		
		if(this.nhomId.length()>0)
		{
			condition+=" and b.SanPham_fk in  (select sp_FK FROM NHOMSANPHAM_SANPHAM  where nsp_fk='"+this.nhomId+"' )  ";
		}
		
		if(this.kbhId.length()>0)
		{
			condition+=" and a.pk_seq in (select hoadon_fk from hoadon_Ddh where ddh_fk in (select pk_seq from DonHang where kbh_fk='"+this.kbhId+"' )) ";
		}
		
		if(this.ddkdId.length()>0)
		{
			condition += " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.ddkdId+"'))";
		}
		
		
		
		
		String conditionETC="";   
		conditionETC+= " and a.npp_fk in " + Ult.quyen_npp(userId)+"";	
		
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
			conditionETC+=
					" and	(  a.khachHang_FK  in (select pk_seq	 from KhachHang  where tinhthanh_fk='"+this.ttId+"' ) OR  a.npp_dat_Fk in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' )  ) ";
		}
		
		if(this.nhomId.length()>0)
		{
			conditionETC+=" and C.SanPham_fk in  (select sp_FK FROM NHOMSANPHAM_SANPHAM  where nsp_fk='"+this.nhomId+"' )  ";
		}
		
		
		if(this.kbhId.length()>0)
		{
			conditionETC+=" and a.pk_seq in (select hoadonNpp_fk from Erp_hoadonNpp_Ddh where ddh_fk in (select pk_seq from Erp_DonDatHangNpp where kbh_fk='"+this.kbhId+"' )) ";
		}
		
		if(this.ddkdId.length()>0)
		{
			conditionETC += " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.ddkdId+"'))";
		}
		
		
		
		String condition_CK="";
		condition_CK+= " and a.npp_fk in " + Ult.quyen_npp(userId)+"";
		if(nppId.length()>0)
		{
			condition_CK+= " and npp_fk='"+this.nppId+"' ";
		}
		
		if(ttId.length()>0)
		{
			condition_CK+=" and a.khachHang_fk in (select pk_seq	 from KhachHang  where tinhthanh_fk='"+this.ttId+"' ) ";
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
			condition_CK+=" and a.pk_seq in (select hoadon_fk from hoadon_Ddh where ddh_fk in (select pk_seq from DonHang where kbh_fk='"+this.kbhId+"' )) ";
		}
		
		if(this.ddkdId.length()>0)
		{
			condition_CK += " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.ddkdId+"'))";
		}
		
		
		query="";
		
		
		if(this.action.length()>0)
		{
			query=
					"	select HD.DDKD,v.TEN as vTen,tt.TEN as ttTEN,npp.MA as nppMa,npp.TEN as nppTEN,   \n "+      
							"   kh.maFAST as khMa,KH.TEN AS khTEN,kh.CHUCUAHIEU as khChuCH,loai.ten as khLOAI,KH.DIACHI as khDIACHI,kh.MaHD as khMaHD ,   \n "+      
							"   SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT   \n "+      
							"   from   \n "+      
							"   (   \n "+      
							"   	select hdOTC.DDKD,NPP_FK,KHACHHANG_FK,'OTC' as LoaiHD, hdOTC.SoLuong,hdOTC.DonGia ,hdOTC.BVAT, hdOTC.VAT ,hdOTC.AVAT   \n "+      
							"   	from   \n "+      
							"   	(   \n "+      
							"	  	   select  ( select top(1)dd.TEN from  KHACHHANG_TUYENBH bb  "+    
							"				inner join TUYENBANHANG cc on bb.TBH_FK=cc.PK_SEQ   "+
							"		inner join DAIDIENKINHDOANH dd on cc.DDKD_FK=dd.PK_SEQ  "+
							"	where bb.KHACHHANG_FK=A.KHACHHANG_FK ) as DDKD ,a.KHACHHANG_FK,a.NPP_FK	,sum(round(b.SoLuong*b.DONGIA,0)) as BVAT,sum( round( round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100) ,0)) as AVAT ,  \n"+
							"			sum( round( round( b.SoLuong*b.DONGIA,0) *(b.vat/100.0) ,0)) as VAT,  \n "+   
							"				SUM(b.SoLuong)as SoLuong,AVG(b.DonGia) as DonGia     \n "+
							"			from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ    \n "+
							"			where a.LOAIHOADON =0 and a.TRANGTHAI not in (1,3,5) "+condition+" \n "+
							"				group by a.NPP_FK ,A.KHACHHANG_FK  "+
							"   	)as hdOTC   \n "+      
							"      \n "+      
							"      \n "+      
							"   UNION ALL   \n "+      
							"      \n "+      
							"   		select ETC.DDKD,npp_fk,KHACHHANG_FK,   \n "+      
							"   		'ETC' as LoaiHD,SUM(ETC.SoLuong)as SoLuong,AVG(ETC.DonGia) as DonGia,   \n "+      
							"		sum( ROUND(soluong * dongia,0 ) - ck )  as BVAT,   "+
							" sum(ROUND(   ROUND ( soluong * dongia- ck,0 )* thuexuat/100.0,0 )) as VAT,  "+ 
							"		sum(ROUND( ( ROUND (soluong * dongia , 0 ) - ck )   * (1+ thuexuat/100.0),0 )) as AVAT   "+  	      
							"   		from   \n "+      
							"   		(   \n "+      
							"   			select " +
							"		( select top(1)dd.TEN from  KHACHHANG_TUYENBH bb  "+    
							"				inner join TUYENBANHANG cc on bb.TBH_FK=cc.PK_SEQ   "+
							"		inner join DAIDIENKINHDOANH dd on cc.DDKD_FK=dd.PK_SEQ  "+
							"	where bb.KHACHHANG_FK=a.KHACHHANG_FK ) as DDKD  \n"+
							",a.npp_fk, a.KHACHHANG_FK,   \n "+      
							"   			case when c.donvitinh = e.donvi then c.soluong   \n "+      
							"   			else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,   \n "+      
							"   			case when c.donvitinh = e.donvi then c.dongia   \n "+      
							"   			else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia,   \n "+      
							"   			c.vat as thuexuat , round(isnull(c.CHIETKHAU,0),0) as ck ,d.MA as spMa,d.TEN as spTen,e.DONVI as spDonVi   \n "+      
							"   			from ERP_HOADONNPP a   \n "+      
							"   			inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk   \n "+      
							"   			inner join SANPHAM d on c.sanpham_fk = d.pk_seq   \n "+      
							"   			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   \n "+      
							"   			where 1=1 and a.trangthai not in ( 1 , 3, 5 ) and a.KHACHHANG_FK is not null "+conditionETC+"  \n "+      
							"   		) ETC   \n "+      
							"   		where soluong>0   \n "+      
							"   		GROUP BY NPP_FK,KHACHHANG_FK,DDKD    \n "+      
							"      \n "+      
							"   	UNION ALL   \n "+      
							"   	   \n "+      
							"   	select ( select top(1)dd.TEN from  KHACHHANG_TUYENBH bb  "+    
							"				inner join TUYENBANHANG cc on bb.TBH_FK=cc.PK_SEQ   "+
							"		inner join DAIDIENKINHDOANH dd on cc.DDKD_FK=dd.PK_SEQ  "+
							"	where bb.KHACHHANG_FK=c.KHACHHANG_FK ) as DDKD  , \n"+
							"c.NPP_FK,c.KHACHHANG_FK,'OTC' as Loai,0 as SoLuong,0 as DonGia   \n "+      
							"   		,-1*SUM(ROUND(a.chietkhau,0)) as BVAT,(-1)*SUM(ROUND(  ROUND( a.chietkhau,0)*(a.thuevat/100.0),0 ,0 )) as VAT,   \n "+      
							"  (-1)*SUM(ROUND(  ROUND( a.chietkhau,0)*(1+ a.thuevat/100.0),0 ,0 )) as AVAT            \n "+
							"   	from HOADON_CHIETKHAU a left join LoaiCK b on b.Ma=a.diengiai   \n "+      
							"   		inner join HOADON c on c.PK_SEQ=a.hoadon_fk   \n "+      
							"   		inner join NHAPHANPHOI npp on npp.pk_Seq= C .NPP_FK   \n "+      
							"   		inner join KHACHHANG kh on kh.PK_SEQ= C.KHACHHANG_FK   \n "+      
							"   		left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
							"   		left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
							"   		left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
							"   		left join LOAIKHACHHANG loai on loai.pk_seq=kh.XuatKhau   \n "+      
							"   	where    isnull(a.HIENTHI,0)=1 and  a.hoadon_fk in   \n "+      
							"   	(   \n "+      
							"   		select pk_Seq from HOADON a where isnull(LOAIHOADON,0)=0 and a.TRANGTHAI not IN (1,3,5)  "+condition_CK+"  \n "+      
							"   	)   \n "+      
							"   	group by c.NPP_FK,c.KHACHHANG_FK    \n "+      
							"      \n "+      
							"   ) as HD   \n "+      
							"   inner join NHAPHANPHOI npp on npp.PK_SEQ=HD.NPP_FK   \n "+      
							"   left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
							"   left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
							"   left join KHACHHANG kh on kh.PK_SEQ=HD.KHACHHANG_FK   \n "+      
							"   left join LOAIKHACHHANG loai on loai.pk_seq=kh.XuatKhau   \n "+      
							"   left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
							"   group by v.TEN ,tt.TEN ,npp.MA,npp.TEN , kh.maFAST ,KH.TEN ,KH.DIACHI,loai.ten ,kh.CHUCUAHIEU ,kh.MaHD,HD.DDKD  ";
			this.queryHd=query;
			setTotal_Query(query);
		}
		System.out.println("init() query: " + query);
		this.hoadonRs= super.createSplittingData(super.getItems(), super.getSplittings(), " khMa ", query); 
		
		createRs();
	}
	
	public void init2(String search) // danh cho ChiSoDoanhThuNV.jsp
	{
		
		String query;
		
		String condition="";   
		
		condition+= " and a.npp_fk in " + Ult.quyen_npp(userId)+"";		 
		
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
			condition+=" and a.pk_seq in (select hoadon_fk from hoadon_Ddh where ddh_fk in (select pk_seq from DonHang where kbh_fk='"+this.kbhId+"' )) ";
		}
		
		if(this.ddkdId.length()>0)
		{
			condition += " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.ddkdId+"'))";
		}
		
		
		
		
		String conditionETC="";   
		conditionETC+= " and a.npp_fk in " + Ult.quyen_npp(userId)+"";	
		
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
			conditionETC+=" and a.pk_seq in (select hoadonNpp_fk from Erp_hoadonNpp_Ddh where ddh_fk in (select pk_seq from Erp_DonDatHangNpp where kbh_fk='"+this.kbhId+"' )) ";
		}
		
		if(this.ddkdId.length()>0)
		{
			conditionETC += " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.ddkdId+"'))";
		}
		
		
		
		String condition_CK="";
		condition_CK+= " and a.npp_fk in " + Ult.quyen_npp(userId)+"";
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
			condition_CK+=" and a.pk_seq in (select hoadon_fk from hoadon_Ddh where ddh_fk in (select pk_seq from DonHang where kbh_fk='"+this.kbhId+"' )) ";
		}
		
		if(this.ddkdId.length()>0)
		{
			condition_CK += " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.ddkdId+"'))";
		}
		
		
		query="";
		
		if(this.action.length()>0)
		{
			if(this.xemtheo==-1) // xem theo trinh duoc vien
			{
				query=
						" select d.tdvTen,d.spMa,d.spTen,SUM(d.SoLuong)as SoLuong,0 as DonGia,SUM(d.VAT) as VAT, SUM(d.BVAT) as BVAT,SUM(d.AVAT)as AVAT" + 
								"		" +
								" from ( \n"+
								"	select v.TEN as vTen,tt.TEN as ttTEN,npp.MA as nppMa,npp.TEN as nppTEN,   \n "+      
								"   kh.maFAST as khMa,KH.TEN AS khTEN,kh.CHUCUAHIEU as khChuCH,loai.ten as khLOAI,KH.DIACHI as khDIACHI,kh.MaHD as khMaHD ,   \n "+      
								"   HD.spMa, HD.spTen,SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT   \n "+  
								
				  	   "	,isnull((	\n	" +
				  	   "		select top(1) c.ten	\n	" +
				  	   "		from KHACHHANG_TUYENBH a inner join TUYENBANHANG b on a.TBH_FK=b.PK_SEQ	\n	" +
				  	   "		inner join DAIDIENKINHDOANH c on c.PK_SEQ=b.DDKD_FK	\n	" +
				  	   "		where a.khachhang_fk=kh.PK_SEQ	\n	" +
				  	   "		),'N/A') as tdvTen	\n	"  +
				  	   
				  	   "   from   \n "+      
				  	   "   (   \n "+      
				  	   "   	select NPP_FK,KHACHHANG_FK,'OTC' as LoaiHD,hdOTC.spMa, hdOTC.spTen, hdOTC.SoLuong,hdOTC.DonGia ,hdOTC.BVAT, hdOTC.VAT ,hdOTC.AVAT   \n "+      
				  	   "   	from   \n "+      
				  	   "   	(   \n "+      
				  	   "	  	   select a.KHACHHANG_FK,a.NPP_FK	,sum(round(b.SoLuong*b.DONGIA,0)) as BVAT,sum( round( round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100) ,0)) as AVAT ,  \n"+
				  	   "			sum( round( round( b.SoLuong*b.DONGIA,0) *(b.vat/100.0) ,0)) as VAT,  \n "+
				  	   " 			sp.MA as spMa, sp.TEN as spTen, \n "+
				  	   "				SUM(b.SoLuong)as SoLuong,AVG(b.DonGia) as DonGia     \n "+
				  	   "			from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ    \n "+
				  	   " 			left join SANPHAM sp on sp.PK_SEQ=b.SANPHAM_FK  \n " +
				  	   "			where a.LOAIHOADON =0 and a.TRANGTHAI not in (1,3,5) "+condition+" \n "+
				  	   "				group by a.NPP_FK ,A.KHACHHANG_FK,sp.MA,sp.TEN  "+
				  	   "   	)as hdOTC   \n "+      
				  	   "      \n "+      
				  	   "      \n "+      
				  	   "   UNION ALL   \n "+      
				  	   "      \n "+      
				  	   "   		select npp_fk,KHACHHANG_FK,   \n "+      
				  	   "   		'ETC' as LoaiHD,ETC.spMa, ETC.spTen,SUM(ETC.SoLuong)as SoLuong,AVG(ETC.DonGia) as DonGia,   \n "+      
				  	   "		sum( ROUND(soluong * dongia,0 ) - ck )  as BVAT,   "+
				  	   "		sum(ROUND(   ROUND ( soluong * dongia,0) - ck * thuexuat/100.0,0 )) as VAT,  "+ 
				  	   "		sum(ROUND( ( ROUND (soluong * dongia , 0 ) - ck )   * (1+ thuexuat/100.0),0 )) as AVAT   "+  	      
				  	   "   		from   \n "+      
				  	   "   		(   \n "+      
				  	   "   			select a.npp_fk, a.KHACHHANG_FK,   \n "+      
				  	   "   			case when c.donvitinh = e.donvi then c.soluong   \n "+      
				  	   "   			else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,   \n "+      
				  	   "   			case when c.donvitinh = e.donvi then c.dongia   \n "+      
				  	   "   			else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia,   \n "+      
				  	   "   			c.vat as thuexuat , round(isnull(c.CHIETKHAU,0),0) as ck ,d.MA as spMa,d.TEN as spTen,e.DONVI as spDonVi   \n "+      
				  	   "   			from ERP_HOADONNPP a   \n "+      
				  	   "   			inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk   \n "+      
				  	   "   			inner join SANPHAM d on c.sanpham_fk = d.pk_seq   \n "+      
				  	   "   			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   \n "+      
				  	   "   			where 1=1 and a.trangthai not in ( 1 , 3, 5 ) and KHACHHANG_FK is not null "+conditionETC+"  \n "+      
				  	   "   		) ETC   \n "+      
				  	   "   		where soluong>0   \n "+      
				  	   "   		GROUP BY NPP_FK,KHACHHANG_FK,ETC.spMa,ETC.spTen   \n "+      
				  	   "      \n "+      
				  	   /* "   	UNION ALL   \n "+      
				  	   "   	   \n "+      
				  	   "   	select c.NPP_FK,c.KHACHHANG_FK,'OTC' as Loai,'' as spMA,'' as spTen,0 as SoLuong,0 as DonGia   \n "+      
				  	   "   		,-1*SUM(ROUND(a.chietkhau,0)) as BVAT,(-1)*SUM(ROUND(  ROUND( a.chietkhau,0)*(a.thuevat/100.0),0 ,0 )) as VAT,   \n "+      
				  	 	 "  (-1)*SUM(ROUND(  ROUND( a.chietkhau,0)*(1+ a.thuevat/100.0),0 ,0 )) as AVAT            \n "+
				  	   "   	from HOADON_CHIETKHAU a left join LoaiCK b on b.Ma=a.diengiai   \n "+      
				  	   "   		inner join HOADON c on c.PK_SEQ=a.hoadon_fk   \n "+      
				  	   "   		inner join NHAPHANPHOI npp on npp.pk_Seq= C .NPP_FK   \n "+      
				  	   "   		inner join KHACHHANG kh on kh.PK_SEQ= C.KHACHHANG_FK   \n "+      
				  	   "   		left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
				  	   "   		left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
				  	   "   		left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
				  	   "   		left join LOAIKHACHHANG loai on loai.pk_seq=kh.XuatKhau   \n "+      
				  	   "   	where    isnull(a.HIENTHI,0)=1 and  a.hoadon_fk in   \n "+      
				  	   "   	(   \n "+      
				  	   "   		select pk_Seq from HOADON a where isnull(LOAIHOADON,0)=0 and a.TRANGTHAI not IN (1,3,5)  "+condition_CK+"  \n "+      
				  	   "   	)   \n "+      
				  	   "   	group by c.NPP_FK,c.KHACHHANG_FK   \n "+   */   
				  	   "      \n "+      
				  	   "   ) as HD   \n "+      
				  	   "   inner join NHAPHANPHOI npp on npp.PK_SEQ=HD.NPP_FK   \n "+      
				  	   "   left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
				  	   "   left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
				  	   "   left join KHACHHANG kh on kh.PK_SEQ=HD.KHACHHANG_FK   \n "+      
				  	   "   left join LOAIKHACHHANG loai on loai.pk_seq=kh.XuatKhau   \n "+      
				  	   "   left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
				  	   "   group by v.TEN ,tt.TEN ,npp.MA,npp.TEN , kh.maFAST ,KH.TEN ,KH.DIACHI,loai.ten ,kh.CHUCUAHIEU ,kh.MaHD, HD.spMa, HD.spTen, kh.PK_SEQ  \n " +
				  	   //"	order by kh.maFAST asc, HD.spMa desc 	";
				  	   "	)as d	" +
				  	   "	group by d.tdvTen,d.spMa,d.spTen	";
				// "	order by d.tdvTen,d.spMa asc ";
				
				this.queryHd=query;
				setTotal_Query("");
				this.hoadonRs= super.createSplittingData(super.getItems(), super.getSplittings(), " tdvTen ", query); 
				
			}
			else if(this.xemtheo==1) // xem theo khach hang
			{
				query=
						"	select v.TEN as vTen,tt.TEN as ttTEN,npp.MA as nppMa,npp.TEN as nppTEN,   \n "+      
								"   kh.maFAST as khMa,KH.TEN AS khTEN,kh.CHUCUAHIEU as khChuCH,loai.ten as khLOAI,KH.DIACHI as khDIACHI,kh.MaHD as khMaHD ,   \n "+      
								"   HD.spMa, HD.spTen,SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT   \n "+  
								
				  	   "	,isnull((	\n	" +
				  	   "		select top(1) c.ten	\n	" +
				  	   "		from KHACHHANG_TUYENBH a inner join TUYENBANHANG b on a.TBH_FK=b.PK_SEQ	\n	" +
				  	   "		inner join DAIDIENKINHDOANH c on c.PK_SEQ=b.DDKD_FK	\n	" +
				  	   "		where a.khachhang_fk=kh.PK_SEQ	\n	" +
				  	   "		),'N/A') as tdvTen	\n	"  +
				  	   
				  	   "   from   \n "+      
				  	   "   (   \n "+      
				  	   "   	select NPP_FK,KHACHHANG_FK,'OTC' as LoaiHD,hdOTC.spMa, hdOTC.spTen, hdOTC.SoLuong,hdOTC.DonGia ,hdOTC.BVAT, hdOTC.VAT ,hdOTC.AVAT   \n "+      
				  	   "   	from   \n "+      
				  	   "   	(   \n "+      
				  	   "	  	   select a.KHACHHANG_FK,a.NPP_FK	,sum(round(b.SoLuong*b.DONGIA,0)) as BVAT,sum( round( round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100) ,0)) as AVAT ,  \n"+
				  	   "			sum( round( round( b.SoLuong*b.DONGIA,0) *(b.vat/100.0) ,0)) as VAT,  \n "+
				  	   " 			sp.MA as spMa, sp.TEN as spTen, \n "+
				  	   "				SUM(b.SoLuong)as SoLuong,AVG(b.DonGia) as DonGia     \n "+
				  	   "			from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ    \n "+
				  	   " 			left join SANPHAM sp on sp.PK_SEQ=b.SANPHAM_FK  \n " +
				  	   "			where a.LOAIHOADON =0 and a.TRANGTHAI not in (1,3,5) "+condition+" \n "+
				  	   "				group by a.NPP_FK ,A.KHACHHANG_FK,sp.MA,sp.TEN  "+
				  	   "   	)as hdOTC   \n "+      
				  	   "      \n "+      
				  	   "      \n "+      
				  	   "   UNION ALL   \n "+      
				  	   "      \n "+      
				  	   "   		select npp_fk,KHACHHANG_FK,   \n "+      
				  	   "   		'ETC' as LoaiHD,ETC.spMa, ETC.spTen,SUM(ETC.SoLuong)as SoLuong,AVG(ETC.DonGia) as DonGia,   \n "+      
				  	   "		sum( ROUND(soluong * dongia,0 ) - ck )  as BVAT,   "+
				  	   "		sum(ROUND(   ROUND ( soluong * dongia,0) - ck * thuexuat/100.0,0 )) as VAT,  "+ 
				  	   "		sum(ROUND( ( ROUND (soluong * dongia , 0 ) - ck )   * (1+ thuexuat/100.0),0 )) as AVAT   "+  	      
				  	   "   		from   \n "+      
				  	   "   		(   \n "+      
				  	   "   			select a.npp_fk, a.KHACHHANG_FK,   \n "+      
				  	   "   			case when c.donvitinh = e.donvi then c.soluong   \n "+      
				  	   "   			else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,   \n "+      
				  	   "   			case when c.donvitinh = e.donvi then c.dongia   \n "+      
				  	   "   			else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia,   \n "+      
				  	   "   			c.vat as thuexuat , round(isnull(c.CHIETKHAU,0),0) as ck ,d.MA as spMa,d.TEN as spTen,e.DONVI as spDonVi   \n "+      
				  	   "   			from ERP_HOADONNPP a   \n "+      
				  	   "   			inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk   \n "+      
				  	   "   			inner join SANPHAM d on c.sanpham_fk = d.pk_seq   \n "+      
				  	   "   			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   \n "+      
				  	   "   			where 1=1 and a.trangthai not in ( 1 , 3, 5 ) and KHACHHANG_FK is not null "+conditionETC+"  \n "+      
				  	   "   		) ETC   \n "+      
				  	   "   		where soluong>0   \n "+      
				  	   "   		GROUP BY NPP_FK,KHACHHANG_FK,ETC.spMa,ETC.spTen   \n "+      
				  	   "      \n "+      
				  	   /* "   	UNION ALL   \n "+      
				  	   "   	   \n "+      
				  	   "   	select c.NPP_FK,c.KHACHHANG_FK,'OTC' as Loai,'' as spMA,'' as spTen,0 as SoLuong,0 as DonGia   \n "+      
				  	   "   		,-1*SUM(ROUND(a.chietkhau,0)) as BVAT,(-1)*SUM(ROUND(  ROUND( a.chietkhau,0)*(a.thuevat/100.0),0 ,0 )) as VAT,   \n "+      
				  	 	 "  (-1)*SUM(ROUND(  ROUND( a.chietkhau,0)*(1+ a.thuevat/100.0),0 ,0 )) as AVAT            \n "+
				  	   "   	from HOADON_CHIETKHAU a left join LoaiCK b on b.Ma=a.diengiai   \n "+      
				  	   "   		inner join HOADON c on c.PK_SEQ=a.hoadon_fk   \n "+      
				  	   "   		inner join NHAPHANPHOI npp on npp.pk_Seq= C .NPP_FK   \n "+      
				  	   "   		inner join KHACHHANG kh on kh.PK_SEQ= C.KHACHHANG_FK   \n "+      
				  	   "   		left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
				  	   "   		left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
				  	   "   		left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
				  	   "   		left join LOAIKHACHHANG loai on loai.pk_seq=kh.XuatKhau   \n "+      
				  	   "   	where    isnull(a.HIENTHI,0)=1 and  a.hoadon_fk in   \n "+      
				  	   "   	(   \n "+      
				  	   "   		select pk_Seq from HOADON a where isnull(LOAIHOADON,0)=0 and a.TRANGTHAI not IN (1,3,5)  "+condition_CK+"  \n "+      
				  	   "   	)   \n "+      
				  	   "   	group by c.NPP_FK,c.KHACHHANG_FK   \n "+  */    
				  	   "      \n "+      
				  	   "   ) as HD   \n "+      
				  	   "   inner join NHAPHANPHOI npp on npp.PK_SEQ=HD.NPP_FK   \n "+      
				  	   "   left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
				  	   "   left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
				  	   "   left join KHACHHANG kh on kh.PK_SEQ=HD.KHACHHANG_FK   \n "+      
				  	   "   left join LOAIKHACHHANG loai on loai.pk_seq=kh.XuatKhau   \n "+      
				  	   "   left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
				  	   "   group by v.TEN ,tt.TEN ,npp.MA,npp.TEN , kh.maFAST ,KH.TEN ,KH.DIACHI,loai.ten ,kh.CHUCUAHIEU ,kh.MaHD, HD.spMa, HD.spTen, kh.PK_SEQ  \n " ;
				//"	order by kh.maFAST asc, HD.spMa desc 	";
				this.queryHd=query;
				setTotal_Query("");
				this.hoadonRs= super.createSplittingData(super.getItems(), super.getSplittings(), " khMa ", query); 
			}
			/*				this.queryHd=query;
				setTotal_Query("");*/
		}
		else
		{
			this.hoadonRs= super.createSplittingData(super.getItems(), super.getSplittings(), " 1 ", query);
		}
		//this.hoadonRs= super.createSplittingData(super.getItems(), super.getSplittings(), " khMa ", query); 
		
		
		createRs();
		
	}
	
	public void xuatexcel(String search)
	{
		String query;
		
		String condition="";   
		
		condition+= " and a.npp_fk in " + Ult.quyen_npp(userId)+"";		 
		
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
			condition+=" and a.pk_seq in (select hoadon_fk from hoadon_Ddh where ddh_fk in (select pk_seq from DonHang where kbh_fk='"+this.kbhId+"' )) ";
		}
		
		if(this.ddkdId.length()>0)
		{
			condition += " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.ddkdId+"'))";
		}
		
		
		
		
		String conditionETC="";   
		conditionETC+= " and a.npp_fk in " + Ult.quyen_npp(userId)+"";	
		
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
			conditionETC+=" and a.pk_seq in (select hoadonNpp_fk from Erp_hoadonNpp_Ddh where ddh_fk in (select pk_seq from Erp_DonDatHangNpp where kbh_fk='"+this.kbhId+"' )) ";
		}
		
		if(this.ddkdId.length()>0)
		{
			conditionETC += " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.ddkdId+"'))";
		}
		
		
		
		String condition_CK="";
		condition_CK+= " and a.npp_fk in " + Ult.quyen_npp(userId)+"";
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
			condition_CK+=" and a.pk_seq in (select hoadon_fk from hoadon_Ddh where ddh_fk in (select pk_seq from DonHang where kbh_fk='"+this.kbhId+"' )) ";
		}
		
		if(this.ddkdId.length()>0)
		{
			condition_CK += " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.ddkdId+"'))";
		}
		
		
		query="";
		
		
		if(this.action.length()>0)
		{
			if(this.xemtheo==-1) // xem theo trinh duoc vien
			{
				query=
						" select d.tdvTen,d.spMa,d.spTen,SUM(d.SoLuong)as SoLuong,0 as DonGia,SUM(d.VAT) as VAT, SUM(d.BVAT) as BVAT,SUM(d.AVAT)as AVAT,  COUNT(d.spTen) OVER (PARTITION BY tdvTen) as sosp  from ( \n"+
								"	select v.TEN as vTen,tt.TEN as ttTEN,npp.MA as nppMa,npp.TEN as nppTEN,   \n "+      
								"   kh.maFAST as khMa,KH.TEN AS khTEN,kh.CHUCUAHIEU as khChuCH,loai.ten as khLOAI,KH.DIACHI as khDIACHI,kh.MaHD as khMaHD ,   \n "+      
								"   HD.spMa, HD.spTen,SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT   \n "+  
								
				  	   "	,isnull((	\n	" +
				  	   "		select top(1) c.ten	\n	" +
				  	   "		from KHACHHANG_TUYENBH a inner join TUYENBANHANG b on a.TBH_FK=b.PK_SEQ	\n	" +
				  	   "		inner join DAIDIENKINHDOANH c on c.PK_SEQ=b.DDKD_FK	\n	" +
				  	   "		where a.khachhang_fk=kh.PK_SEQ	\n	" +
				  	   "		),'N/A') as tdvTen	\n	"  +
				  	   
				  	   "   from   \n "+      
				  	   "   (   \n "+      
				  	   "   	select NPP_FK,KHACHHANG_FK,'OTC' as LoaiHD,hdOTC.spMa, hdOTC.spTen, hdOTC.SoLuong,hdOTC.DonGia ,hdOTC.BVAT, hdOTC.VAT ,hdOTC.AVAT   \n "+      
				  	   "   	from   \n "+      
				  	   "   	(   \n "+      
				  	   "	  	   select a.KHACHHANG_FK,a.NPP_FK	,sum(round(b.SoLuong*b.DONGIA,0)) as BVAT,sum( round( round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100) ,0)) as AVAT ,  \n"+
				  	   "			sum( round( round( b.SoLuong*b.DONGIA,0) *(b.vat/100.0) ,0)) as VAT,  \n "+
				  	   " 			sp.MA as spMa, sp.TEN as spTen, \n "+
				  	   "				SUM(b.SoLuong)as SoLuong,AVG(b.DonGia) as DonGia     \n "+
				  	   "			from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ    \n "+
				  	   " 			left join SANPHAM sp on sp.PK_SEQ=b.SANPHAM_FK  \n " +
				  	   "			where a.LOAIHOADON =0 and a.TRANGTHAI not in (1,3,5) "+condition+" \n "+
				  	   "				group by a.NPP_FK ,A.KHACHHANG_FK,sp.MA,sp.TEN  "+
				  	   "   	)as hdOTC   \n "+      
				  	   "      \n "+      
				  	   "      \n "+      
				  	   "   UNION ALL   \n "+      
				  	   "      \n "+      
				  	   "   		select npp_fk,KHACHHANG_FK,   \n "+      
				  	   "   		'ETC' as LoaiHD,ETC.spMa, ETC.spTen,SUM(ETC.SoLuong)as SoLuong,AVG(ETC.DonGia) as DonGia,   \n "+      
				  	   "		sum( ROUND(soluong * dongia,0 ) - ck )  as BVAT,   "+
				  	   "		sum(ROUND(   ROUND ( soluong * dongia,0) - ck * thuexuat/100.0,0 )) as VAT,  "+ 
				  	   "		sum(ROUND( ( ROUND (soluong * dongia , 0 ) - ck )   * (1+ thuexuat/100.0),0 )) as AVAT   "+  	      
				  	   "   		from   \n "+      
				  	   "   		(   \n "+      
				  	   "   			select a.npp_fk, a.KHACHHANG_FK,   \n "+      
				  	   "   			case when c.donvitinh = e.donvi then c.soluong   \n "+      
				  	   "   			else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,   \n "+      
				  	   "   			case when c.donvitinh = e.donvi then c.dongia   \n "+      
				  	   "   			else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia,   \n "+      
				  	   "   			c.vat as thuexuat , round(isnull(c.CHIETKHAU,0),0) as ck ,d.MA as spMa,d.TEN as spTen,e.DONVI as spDonVi   \n "+      
				  	   "   			from ERP_HOADONNPP a   \n "+      
				  	   "   			inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk   \n "+      
				  	   "   			inner join SANPHAM d on c.sanpham_fk = d.pk_seq   \n "+      
				  	   "   			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   \n "+      
				  	   "   			where 1=1 and a.trangthai not in ( 1 , 3, 5 ) and KHACHHANG_FK is not null "+conditionETC+"  \n "+      
				  	   "   		) ETC   \n "+      
				  	   "   		where soluong>0   \n "+      
				  	   "   		GROUP BY NPP_FK,KHACHHANG_FK,ETC.spMa,ETC.spTen   \n "+      
				  	   "      \n "+      
				  	   /*   "   	UNION ALL   \n "+      
				  	   "   	   \n "+      
				  	   "   	select c.NPP_FK,c.KHACHHANG_FK,'OTC' as Loai,'' as spMA,'' as spTen,0 as SoLuong,0 as DonGia   \n "+      
				  	   "   		,-1*SUM(ROUND(a.chietkhau,0)) as BVAT,(-1)*SUM(ROUND(  ROUND( a.chietkhau,0)*(a.thuevat/100.0),0 ,0 )) as VAT,   \n "+      
				  	 	 "  (-1)*SUM(ROUND(  ROUND( a.chietkhau,0)*(1+ a.thuevat/100.0),0 ,0 )) as AVAT            \n "+
				  	   "   	from HOADON_CHIETKHAU a left join LoaiCK b on b.Ma=a.diengiai   \n "+      
				  	   "   		inner join HOADON c on c.PK_SEQ=a.hoadon_fk   \n "+      
				  	   "   		inner join NHAPHANPHOI npp on npp.pk_Seq= C .NPP_FK   \n "+      
				  	   "   		inner join KHACHHANG kh on kh.PK_SEQ= C.KHACHHANG_FK   \n "+      
				  	   "   		left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
				  	   "   		left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
				  	   "   		left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
				  	   "   		left join LOAIKHACHHANG loai on loai.pk_seq=kh.XuatKhau   \n "+      
				  	   "   	where    isnull(a.HIENTHI,0)=1 and  a.hoadon_fk in   \n "+      
				  	   "   	(   \n "+      
				  	   "   		select pk_Seq from HOADON a where isnull(LOAIHOADON,0)=0 and a.TRANGTHAI not IN (1,3,5)  "+condition_CK+"  \n "+      
				  	   "   	)   \n "+      
				  	   "   	group by c.NPP_FK,c.KHACHHANG_FK   \n "+      */
				  	   "      \n "+      
				  	   "   ) as HD   \n "+      
				  	   "   inner join NHAPHANPHOI npp on npp.PK_SEQ=HD.NPP_FK   \n "+      
				  	   "   left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
				  	   "   left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
				  	   "   left join KHACHHANG kh on kh.PK_SEQ=HD.KHACHHANG_FK   \n "+      
				  	   "   left join LOAIKHACHHANG loai on loai.pk_seq=kh.XuatKhau   \n "+      
				  	   "   left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
				  	   "   group by v.TEN ,tt.TEN ,npp.MA,npp.TEN , kh.maFAST ,KH.TEN ,KH.DIACHI,loai.ten ,kh.CHUCUAHIEU ,kh.MaHD, HD.spMa, HD.spTen, kh.PK_SEQ  \n " +
				  	   //"	order by kh.maFAST asc, HD.spMa desc 	";
				  	   "	)as d	" +
				  	   "	group by d.tdvTen,d.spMa,d.spTen	"+
				  	   "	order by d.tdvTen asc,d.spMa desc ";
				
				this.queryHd=query;
				setTotal_Query("");
				//this.hoadonRs= super.createSplittingData(super.getItems(), super.getSplittings(), " tdvTen ", query); 
				
			}
			else if(this.xemtheo==1) // xem theo khach hang
			{
				query=
						"	select v.TEN as vTen,tt.TEN as ttTEN,npp.MA as nppMa,npp.TEN as nppTEN,   \n "+      
								"   kh.maFAST as khMa,KH.TEN AS khTEN,kh.CHUCUAHIEU as khChuCH,loai.ten as khLOAI,KH.DIACHI as khDIACHI,kh.MaHD as khMaHD ,   \n "+      
								"   HD.spMa, HD.spTen,SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT   \n "+  
								
				  	   "	,isnull((	\n	" +
				  	   "		select top(1) c.ten	\n	" +
				  	   "		from KHACHHANG_TUYENBH a inner join TUYENBANHANG b on a.TBH_FK=b.PK_SEQ	\n	" +
				  	   "		inner join DAIDIENKINHDOANH c on c.PK_SEQ=b.DDKD_FK	\n	" +
				  	   "		where a.khachhang_fk=kh.PK_SEQ	\n	" +
				  	   "		),'N/A') as tdvTen	\n	"  +
				  	   
				  	   "   from   \n "+      
				  	   "   (   \n "+      
				  	   "   	select NPP_FK,KHACHHANG_FK,'OTC' as LoaiHD,hdOTC.spMa, hdOTC.spTen, hdOTC.SoLuong,hdOTC.DonGia ,hdOTC.BVAT, hdOTC.VAT ,hdOTC.AVAT   \n "+      
				  	   "   	from   \n "+      
				  	   "   	(   \n "+      
				  	   "	  	   select a.KHACHHANG_FK,a.NPP_FK	,sum(round(b.SoLuong*b.DONGIA,0)) as BVAT,sum( round( round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100) ,0)) as AVAT ,  \n"+
				  	   "			sum( round( round( b.SoLuong*b.DONGIA,0) *(b.vat/100.0) ,0)) as VAT,  \n "+
				  	   " 			sp.MA as spMa, sp.TEN as spTen, \n "+
				  	   "				SUM(b.SoLuong)as SoLuong,AVG(b.DonGia) as DonGia     \n "+
				  	   "			from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ    \n "+
				  	   " 			left join SANPHAM sp on sp.PK_SEQ=b.SANPHAM_FK  \n " +
				  	   "			where a.LOAIHOADON =0 and a.TRANGTHAI not in (1,3,5) "+condition+" \n "+
				  	   "				group by a.NPP_FK ,A.KHACHHANG_FK,sp.MA,sp.TEN  "+
				  	   "   	)as hdOTC   \n "+      
				  	   "      \n "+      
				  	   "      \n "+      
				  	   "   UNION ALL   \n "+      
				  	   "      \n "+      
				  	   "   		select npp_fk,KHACHHANG_FK,   \n "+      
				  	   "   		'ETC' as LoaiHD,ETC.spMa, ETC.spTen,SUM(ETC.SoLuong)as SoLuong,AVG(ETC.DonGia) as DonGia,   \n "+      
				  	   "		sum( ROUND(soluong * dongia,0 ) - ck )  as BVAT,   "+
				  	   "		sum(ROUND(   ROUND ( soluong * dongia- ck,0 )* thuexuat/100.0,0 )) as VAT,  "+ 
				  	   "		sum(ROUND( ( ROUND (soluong * dongia , 0 ) - ck )   * (1+ thuexuat/100.0),0 )) as AVAT   "+  	      
				  	   "   		from   \n "+      
				  	   "   		(   \n "+      
				  	   "   			select a.npp_fk, a.KHACHHANG_FK,   \n "+      
				  	   "   			case when c.donvitinh = e.donvi then c.soluong   \n "+      
				  	   "   			else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,   \n "+      
				  	   "   			case when c.donvitinh = e.donvi then c.dongia   \n "+      
				  	   "   			else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia,   \n "+      
				  	   "   			c.vat as thuexuat , round(isnull(c.CHIETKHAU,0),0) as ck ,d.MA as spMa,d.TEN as spTen,e.DONVI as spDonVi   \n "+      
				  	   "   			from ERP_HOADONNPP a   \n "+      
				  	   "   			inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk   \n "+      
				  	   "   			inner join SANPHAM d on c.sanpham_fk = d.pk_seq   \n "+      
				  	   "   			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   \n "+      
				  	   "   			where 1=1 and a.trangthai not in ( 1 , 3, 5 ) and KHACHHANG_FK is not null "+conditionETC+"  \n "+      
				  	   "   		) ETC   \n "+      
				  	   "   		where soluong>0   \n "+      
				  	   "   		GROUP BY NPP_FK,KHACHHANG_FK,ETC.spMa,ETC.spTen   \n "+      
				  	   "      \n "+      
				  	   "   	UNION ALL   \n "+      
				  	   "   	   \n "+      
				  	   "   	select c.NPP_FK,c.KHACHHANG_FK,'OTC' as Loai,'' as spMA,'' as spTen,0 as SoLuong,0 as DonGia   \n "+      
				  	   "   		,-1*SUM(ROUND(a.chietkhau,0)) as BVAT,(-1)*SUM(ROUND(  ROUND( a.chietkhau,0)*(a.thuevat/100.0),0 ,0 )) as VAT,   \n "+      
				  	   "  (-1)*SUM(ROUND(  ROUND( a.chietkhau,0)*(1+ a.thuevat/100.0),0 ,0 )) as AVAT            \n "+
				  	   "   	from HOADON_CHIETKHAU a left join LoaiCK b on b.Ma=a.diengiai   \n "+      
				  	   "   		inner join HOADON c on c.PK_SEQ=a.hoadon_fk   \n "+      
				  	   "   		inner join NHAPHANPHOI npp on npp.pk_Seq= C .NPP_FK   \n "+      
				  	   "   		inner join KHACHHANG kh on kh.PK_SEQ= C.KHACHHANG_FK   \n "+      
				  	   "   		left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
				  	   "   		left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
				  	   "   		left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
				  	   "   		left join LOAIKHACHHANG loai on loai.pk_seq=kh.XuatKhau   \n "+      
				  	   "   	where    isnull(a.HIENTHI,0)=1 and  a.hoadon_fk in   \n "+      
				  	   "   	(   \n "+      
				  	   "   		select pk_Seq from HOADON a where isnull(LOAIHOADON,0)=0 and a.TRANGTHAI not IN (1,3,5)  "+condition_CK+"  \n "+      
				  	   "   	)   \n "+      
				  	   "   	group by c.NPP_FK,c.KHACHHANG_FK   \n "+      
				  	   "      \n "+      
				  	   "   ) as HD   \n "+      
				  	   "   inner join NHAPHANPHOI npp on npp.PK_SEQ=HD.NPP_FK   \n "+      
				  	   "   left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
				  	   "   left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
				  	   "   left join KHACHHANG kh on kh.PK_SEQ=HD.KHACHHANG_FK   \n "+      
				  	   "   left join LOAIKHACHHANG loai on loai.pk_seq=kh.XuatKhau   \n "+      
				  	   "   left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
				  	   "   group by v.TEN ,tt.TEN ,npp.MA,npp.TEN , kh.maFAST ,KH.TEN ,KH.DIACHI,loai.ten ,kh.CHUCUAHIEU ,kh.MaHD, HD.spMa, HD.spTen, kh.PK_SEQ  \n " ;
				
				
				setTotal_Query(query);
				query+="	order by kh.maFAST asc, HD.spMa desc 	";
				this.queryHd=query;
				//this.hoadonRs= super.createSplittingData(super.getItems(), super.getSplittings(), " khMa ", query); 
			}
			/*				this.queryHd=query;
				setTotal_Query("");*/
		}
		//this.hoadonRs= super.createSplittingData(super.getItems(), super.getSplittings(), " khMa ", query); 
		
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
		
		if(this.action.length()>0)
		{
			String	query=
					"	select   \n "+      
							"   SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT   \n "+      
							"   from   \n "+      
							"   (   "+searchquery+"   ) as HD   \n ";
			this.totalRs= db.get(query);
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
	public String get_Action()
	{
		return action;
	}
	public void set_Action(String timkiem)
	{
		this.action = timkiem;
	}
	
	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}
	
	public int getCurrentPage()
	{
		return this.currentPages;
	}
	
	@Override
	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}
	
	public int[] getListPages() 
	{
		return this.listPages;
	}
	
	public void setListPages(int[] listPages) 
	{
		this.listPages = listPages;
	}
	
	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as c from ERP_YEUCAUNGUYENLIEU");
		return PhanTrang.getLastPage(rs);
	}
	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	@Override
	public int getxemtheo() {
		return this.xemtheo;
	}
	@Override
	public void setxemtheo(int so) {
		this.xemtheo=so;
	}
	
	
	
}
