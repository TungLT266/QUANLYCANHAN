package geso.traphaco.center.beans.report.imp;

import geso.traphaco.center.beans.report.IBcBanHangTheoChiTieuList;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BcBanHangTheoChiTieuList extends Phan_Trang implements IBcBanHangTheoChiTieuList, Serializable
{
	
	public BcBanHangTheoChiTieuList()
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
		this.tongcong = "";
		db = new dbutils();
	}
	
  private static final long serialVersionUID = 1L;
	String tuNgay, denNgay, spId, nppId, ddkdId, userId, khId, tongcong;
	public String getTongcong()
	{
		return tongcong;
	}
	public void setTongcong(String tongcong)
	{
		this.tongcong = tongcong;
	}
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
		if(khRs!=null)
	    try
      {
	      khRs.close();
      } catch (SQLException e)
      {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
		
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
		
		if(this.nppId.length()>0||this.ddkdId.length()>0)
		{
			
			query="select pk_seq,isnull(mafast,'') +' - ' + ten + ' - ' + isnull(diachi,'') as Ten from khachhang where 1=1 ";
			if(this.view.length()>0)
			{
				query+=" and npp_fk in "+util.quyen_npp(userId);
			}
			
			if(this.nppId.length()>0)
			query+=" and npp_fk='"+this.nppId+"' ";
			
			
			if(this.ddkdId.length()>0)
				query+=" and pk_Seq in (select khachhang_fk from khachhang_tuyenbh where tbh_Fk in (select pk_Seq from TuyenBanHang where ddkd_Fk in ("+this.ddkdId+"))) ";
			
			
			System.out.println("___"+query);
			
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
		
		
		query="select pk_seq,ma,diachi,ten from nhaphanphoi where trangthai=1  and isnull(isKHACHHANG,0)=0  and loainpp is not null ";
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
	Utility Ult = new Utility();
	public void init(String search)
	{
		String query;
		
		String condition=" and Isnull(a.LoaiHoaDon,0) =0  ";  
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
			condition+=" and b.sanpham_fk in ("+this.spId+") ";
		}
		if(this.khId.length()>0)
		{
			condition+=" and a.khachhang_Fk in ("+this.khId+") ";
		}
		
		if(this.ttId.length()>0)
		{
			condition+=" and a.khachHang_fk in (select pk_seq	 from KhachHang  where tinhthanh_fk in ( "+this.ttId+" ) ) ";
		}
		
		if(this.nhomId.length()>0)
		{
			condition+=" and b.SanPham_fk in  (select sp_FK FROM NHOMSANPHAM_SANPHAM  where nsp_fk in( "+this.nhomId+" ) )  ";
		}
		
		if(this.ddkdId.length()>0)
		{
			condition+=" and a.DDKD_fK in  ( "+ddkdId+" )  ";
		}
		
		
		if(this.kbhId.length()>0)
		{
			condition+=" and a.pk_seq in (select hoadon_fk from HoaDon_DDH where ddh_fk in (select pk_seq from DonHang where kbh_fk in ("+this.kbhId+" )  )) ";
		}
		
	   query="";
	   if(this.action.length()>0)
	   {
				query=
						"select 'OTC' as LoaiHD,npp.MA as nppMA,npp.TEN as nppTEN,ddkd.ten as ddkdTEN,Kh.maFAST  as khMA,KH.TEN as khTEN,KH.DIACHI as khDiaChi,  "+      
						"   	sp.MA as spMA,sp.TEN as spTEN,data.SoLuong as SoLuong,data.AVAT,data.VAT,data.BVAT,kh.mahd as khMaHD  "+      
						"   from  "+      
						"   (  "+      
						"   	select a.KHACHHANG_FK,a.NPP_FK ,a.DDKD_FK,b.SANPHAM_FK ,a.KBH_FK,c.TINHTHANH_FK,  "+      
						"   		round(sum(b.SoLuong*b.DONGIA),0) as BVAT,        "+      
						"   		sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100),0) ) as AVAT ,  "+      
						"   		sum( round(  round(b.SoLuong*b.DONGIA,0)*(b.vat/100),0 ) ) as VAT,     "+      
						"   		SUM(b.SoLuong)as SoLuong,AVG(b.DonGia) as AVG_DonGia     "+      
						"   	from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ    "+      
						"   		inner join KHACHHANG c on c.PK_SEQ=a.KHACHHANG_FK  "+      
						"   	where a.TRANGTHAI not in (1,3,5) and a.LOAIHOADON=0   "+condition+"                                                   "+      
						"   	group by a.NPP_FK ,a.DDKD_FK ,b.SANPHAM_FK,a.KHACHHANG_FK,a.KBH_FK,c.TINHTHANH_FK  "+      
						"   )as data  "+      
						"   inner join NHAPHANPHOI npp on npp.PK_SEQ=data.NPP_FK  "+      
						"   inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ=data.DDKD_FK  "+      
						"   inner join KHACHHANG kh on kh.PK_SEQ=data.KHACHHANG_FK  "+      
						"   inner join SANPHAM sp on sp.PK_SEQ=data.SANPHAM_FK union all  "+      
						"   select 'ETC' as LoaiHD,npp.MA as nppMA,npp.TEN as nppTEN,ddkd.ten as ddkdTEN,Kh.maFAST  as khMA,KH.TEN as khTEN,KH.DIACHI as khDiaChi,  "+      
						"   		data.spMa as spMA,data.spTen as spTEN,  "+      
						"   	SUM(data.SoLuong)as SoLuong,AVG(data.DonGia) as DonGia,     "+      
						"   	sum(ROUND(data.SoLuong*data.DONGIA,0))-sum(data.ChietKhau) as BVAT,        "+      
						"   	sum(round( ROUND(data.SoLuong*data.DONGIA,0)*(1+data.thuexuat/100),0 ))-sum( round(data.ChietKhau,0)*(1+data.thuexuat/100))    as AVAT,KH.MaHD as khMaHD  "+      
						"   from     "+      
						"   (     "+      
						"   	select   "+      
						"   		b.SANPHAM_FK,a.KHACHHANG_FK,a.DDKD_FK,a.npp_fk,isnull(f.tinhthanh_fk,g.tinhthanh_fk) as TinhThanh_fk,     "+      
						"   		case when b.donvitinh = e.donvi then b.soluong     "+      
						"   			else b.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = b.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = b.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     "+      
						"   		case when b.donvitinh = e.donvi then b.dongia     "+      
						"   			else b.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = b.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = b.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia,     "+      
						"   				b.vat as thuexuat ,round( isnull(b.CHIETKHAU,0),0) as ChietKhau ,d.MA as spMa,d.TEN as spTen,e.DONVI as spDonVi     "+      
						"   	from ERP_HOADONNPP a     "+      
						"   		inner join ERP_HOADONNPP_SP b on a.pk_seq = b.hoadon_fk     "+      
						"   		inner join SANPHAM d on b.sanpham_fk = d.pk_seq     "+      
						"   		inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq  			  "+      
						"   		left join KHACHHANG f on f.PK_SEQ=a.KHACHHANG_FK               "+      
						"   		left join NHAPHANPHOI g on g.PK_SEQ=a.NPP_DAT_FK 				  "+      
						"   	where 1=1 and a.trangthai not in ( 1 , 3, 5 )  "+condition+"  "+      
						"   	  "+      
						"   ) data     "+      
						"   	inner join NHAPHANPHOI npp on npp.PK_SEQ=data.NPP_FK  "+      
						"   	LEFT join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ=data.DDKD_FK  "+      
						"   	inner join KHACHHANG kh on kh.PK_SEQ=data.KHACHHANG_FK  "+      
						"   	inner join SANPHAM sp on sp.PK_SEQ=data.SANPHAM_FK 		  "+      
						"   where soluong>0     "+      
						"   group by  npp.MA ,npp.TEN ,ddkd.ten ,Kh.maFAST  ,KH.TEN ,KH.DIACHI ,data.spMa,data.spTen,kh.MaHD ";
					this.queryHd=query;
			System.out.println("query __:" + query);
	   }

	   this.hoadonRs=super.createSplittingData(super.getItems(), super.getSplittings(), " nppTen,ddkdTen,spMa,khTen desc", query);
	   System.out.println(":::::::"+query);
		createRs();
		setTotal_Query("");
		String queryTong="select  sum(AVAT) as tongtien from ("+query+") as total ";
		System.out.println("tong don  :---------------" + queryTong);
		ResultSet tongdonhang=db.get(queryTong);
		if(tongdonhang!=null){
			try
			{
				NumberFormat formatter = new DecimalFormat("#,###,###");
				while (tongdonhang.next())
					this.tongcong = formatter.format(tongdonhang.getDouble("tongtien"));
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// System.out.println("tong don hang :---------------" + this.tongcong);
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
			condition+=" and a.pk_seq in (select hoadon_fk from HoaDon_DDH where ddh_fk in (select pk_seq from DonHang where kbh_fk='"+this.kbhId+"' )) ";
		}
		
		
		condition +=" AND a.npp_fk  in (select pk_Seq from nhaphanphoi where loaiNPP not in (4,5) and loainpp is not null ) ";
		
		
		
		String condition_HO="";   
		condition_HO+= " and a.npp_fk in " + Ult.quyen_npp(userId)+"";	
		if(this.tuNgay.length()>0)
		{
			condition_HO+=" and a.NgayXuatHD>='"+this.tuNgay+"'";
		}
		if(this.denNgay.length()>0)
		{
			condition_HO+=" and a.NgayXuatHD <='"+this.denNgay+"'";
		}
		if(this.nppId.length()>0)
		{
			condition_HO+=" and a.npp_fk ='"+this.nppId+"'";
		}
		 if(vungId.length()>0)
	   {
			 condition_HO+= "  and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in (select pk_seq from KHUVUC WHERE VUNG_FK='"+this.vungId+"' ))";
	   }
		
		if(this.spId.length()>0)
		{
			condition_HO+=" and b.sanpham_fk ='"+this.spId+"'";
		}
		if(this.khId.length()>0)
		{
			condition_HO+=" and a.khachhang_Fk ='"+this.khId+"'";
		}
		
		if(this.ttId.length()>0)
		{
			condition_HO+=" and a.npp_fk in (select pk_seq	 from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
		}
		
		if(this.nhomId.length()>0)
		{
			condition_HO+=" and b.SanPham_fk in  (select sp_FK FROM NHOMSANPHAM_SANPHAM  where nsp_fk='"+this.nhomId+"' )  ";
		}
		
		
		if(this.kbhId.length()>0)
		{
			condition_HO+=" and a.pk_seq in (select hoadon_fk from erp_HoaDon_DDH where ddh_fk in (select pk_seq from Erp_DonDatHang where kbh_fk='"+this.kbhId+"' )) ";
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
			conditionETC+=" and a.npp_fk ='"+this.nppId+"' ";
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
			conditionETC+=" and a.pk_seq in (select hoadonnpp_fk from erp_hoadonnpp_Ddh where ddh_fk in (select pk_seq from erp_dondathangnpp where kbh_fk='"+this.kbhId+"' )) ";
		}
		
		conditionETC +=" AND a.npp_fk  in (select pk_Seq from nhaphanphoi where loaiNPP not in (4,5) and loainpp is not null ) ";
		
		
		
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
	 	condition_CK +=" AND a.npp_fk  in (select pk_Seq from nhaphanphoi where loaiNPP not in (4,5) and loainpp is not null ) ";
	 	
	   query="";
	   if(this.action.length()>0)
	   {
				query=
						"   select  AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT   \n "+      
							   "   from   \n "+      
							   "   (   \n "+      
							   "   	select v.TEN as vTen,kv.TEN as kvTen,tt.TEN as ttTen,sp.ma as nppMa,sp.TEN as nppTen ,   \n "+      
							   "   	'OTC' as LoaiHD, hdOTC.SoLuong,hdOTC.DonGia ,hdOTC.BVAT, hdOTC.VAT ,hdOTC.AVAT   \n "+      
							   "   	from   \n "+      
							   "   	(   \n "+      
							   "   		select a.NPP_FK , round(sum(b.SoLuong*b.DONGIA),0) as BVAT,   \n "+      
							   "   		sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100),0) ) as AVAT ,sum( round(  round(b.SoLuong*b.DONGIA,0)*(b.vat/100),0 ) ) as VAT,   \n "+      
							   "   		SUM(b.SoLuong)as SoLuong,AVG(b.DonGia) as DonGia   \n "+      
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
								 "			sum(round(  round(b.SoLuong*b.DONGIA,0)*(1+ b.thuesuat/100),0) ) as AVAT ,sum( round( round(b.SoLuong*b.DONGIA,0)*(b.thuesuat/100),0 )) as VAT,  "+   
								 "				SUM(b.SoLuong)as SoLuong,AVG(b.DonGia) as DonGia     "+
								 "				from ERP_HOADON a inner join ERP_HOADON_SP b on b.HOADON_FK=a.PK_SEQ  "+   
								 "			where   a.TRANGTHAI not in (1,3,5) "+condition_HO+"    "+
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
							   "			sum(ROUND(ETC.SoLuong*ETC.DONGIA,0))-sum(ETC.ChietKhau) as BVAT,      \n "+
						     "			sum(round( ROUND(ETC.SoLuong*ETC.DONGIA,0)*(ETC.thuexuat/100),0 ))-sum( round(ETC.ChietKhau,0)*(ETC.thuexuat/100))as VAT , \n "+   
						     "			sum(round( ROUND(ETC.SoLuong*ETC.DONGIA,0)*(1+ETC.thuexuat/100),0 ))-sum( round(ETC.ChietKhau,0)*(1+ETC.thuexuat/100))   \n "+
							   "   from   \n "+      
							   "   (   \n "+      
							   "   		select a.npp_fk,   \n "+      
							   "   		case when c.donvitinh = e.donvi then c.soluong   \n "+      
							   "   		else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,   \n "+      
							   "   		case when c.donvitinh = e.donvi then c.dongia   \n "+      
							   "   		else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia,   \n "+      
							   "   		c.vat as thuexuat ,round( isnull(c.CHIETKHAU,0),0) as ChietKhau ,d.MA as spMa,d.TEN as spTen,e.DONVI as spDonVi   \n "+      
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
							   "   		,-1*SUM(round(a.chietkhau,0)) as BVAT,(-1)*SUM( round( round(a.chietkhau,0)*a.thuevat/100.0,0) ) as VAT,   \n "+      
							   "   		(-1)*SUM(round(  round(a.chietkhau,0)*(1+ a.thuevat/100.0),0 ) ) as AVAT   \n "+      
							   "   	from HOADON_CHIETKHAU a left join LoaiCK b on b.Ma=a.diengiai   \n "+      
							   "   		inner join HOADON c on c.PK_SEQ=a.hoadon_fk   \n "+      
							   "   		inner join NHAPHANPHOI npp on npp.pk_Seq=c.NPP_FK   \n "+      
							   "   		left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
							   "   		left join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
							   "   		left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
							   "   	where a.HienThi=1 and  a.hoadon_fk in   \n "+      
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
	
	public String getAction_()
  {
  	return action;
  }
	
	public void setAction_(String timkiem)
  {
  	this.action = timkiem;
  }

	
	
}
