package geso.traphaco.center.beans.report.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.WebService;
import geso.traphaco.center.beans.report.IBangKeHoaDonSpList;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;

import org.w3c.dom.NodeList;

public class BangKeHoaDonSpList extends Phan_Trang implements IBangKeHoaDonSpList, Serializable
{
	public BangKeHoaDonSpList()
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
		this.laynk="0";
		nhomhangERPid="";
		this.kvId = "";
		this.nhomkhERPid="";
		db = new dbutils();
		this.loai="";
		this.querynh="";
		this.userTen = "";
	}

	String userTen ;
	String loai;
	
  public String getLoai() {
		return loai;
	}
	public void setLoai(String loai) {
		this.loai = loai;
	}


private static final long serialVersionUID = 1L;
	String tuNgay,denNgay,spId,nppId,ddkdId,userId,khId;
	String nhomhangERPid, kvId,nhomkhERPid;
	ResultSet RsnhomhangERPid, kvRs,RsnhomkhERPid;
	public String getNhomkhERPid() {
		return nhomkhERPid;
	}
	public void setNhomkhERPid(String nhomkhERPid) {
		this.nhomkhERPid = nhomkhERPid;
	}
	public ResultSet getRsnhomkhERPid() {
		return RsnhomkhERPid;
	}
	public void setRsnhomkhERPid(ResultSet rsnhomkhERPid) {
		RsnhomkhERPid = rsnhomkhERPid;
	}
	public ResultSet getRsnhomhangERPid() {
		return RsnhomhangERPid;
	}
	public void setRsnhomhangERPid(ResultSet rsnhomhangERPid) {
		RsnhomhangERPid = rsnhomhangERPid;
	}
	public String getNhomhangERPid() {
		return nhomhangERPid;
	}
	public void setNhomhangERPid(String nhomhangERPid) {
		this.nhomhangERPid = nhomhangERPid;
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
	ResultSet spRs,ddkdRs,khRs;
	ResultSet hoadonRs;
	ResultSet hoadonrsnhomhang;
	
	String querynh;
	
	public String getQuerynh() {
		return querynh;
	}
	public void setQuerynh(String querynh) {
		this.querynh = querynh;
	}
	public ResultSet getHoadonrsnhomhang() {
		return hoadonrsnhomhang;
	}
	public void setHoadonrsnhomhang(ResultSet hoadonrsnhomhang) {
		this.hoadonrsnhomhang = hoadonrsnhomhang;
	}
	public ResultSet getHoadonRs() {
		return hoadonRs;
	}
	public void setHoadonRs(ResultSet hoadonRs) {
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
	String laynk="";
	
	public String getLaynk() {
		return laynk;
	}
	public void setLaynk(String laynk) {
		this.laynk = laynk;
	}
	public void closeDB()
	{
		
	}
	
	dbutils db = new dbutils();
	public void createRs()
	{
		
		Utility util = new Utility();
		
		String query="select pk_Seq,ma + '-' + ten as Ten from ERP_sanpham where trangthai=1 ";
		
		if(this.nhomId.length()>0)
		{
			query += " and pk_Seq in (select sanpham_fk from nhomhang_sanpham where nhomhang_fk='"+this.nhomId+"' ) ";
		}
		query += "  order by ma  ";
		
		this.spRs = this.db.get(query);
		
		query = "select pk_seq, manhanvien,ten from DaiDienKinhDoanh a where 1=1  ";
		/*if(this.view.length()>0)
		{
			query += " and pk_seq in  " + util.Quyen_Ddkd_DMS(userId) ;
		}*/
		
		if(this.nppId.length() > 0)
			query += " and a.pk_seq in ( select ddkd_fk from Ldaidienkinhdoanh_npp where npp_fk = '" + nppId + "')  ";
		this.ddkdRs = this.db.get(query);
	
		if(this.nppId.length()>0)
		{
			/*query="select pk_seq,isnull(mafast,'') +' ' + ten + ' ' + isnull(diachi,'') as Ten from NHAPHANPHOI where isKHACHHANG = '1'  ";
			
			if(this.view.length()>0)
			{
				query+=" and npp_fk in "+util.quyen_npp_DMS(userId);
			}
			
			if(this.nppId.length()>0)
			query+=" and npp_fk='"+this.nppId+"' ";
			
			this.khRs= this.db.get(query);*/
		}
		query="select pk_seq,isnull(mafast,'') +' ' + ten + ' ' + isnull(diachi,'') as Ten from NHAPHANPHOI where isKHACHHANG = '1'  ";

		this.khRs= this.db.get(query);
		
		query="select pk_Seq,ten,DIENGIAI from KENHBANHANG where TRANGTHAI=1 ";
		this.kbhRs = this.db.get(query);	
		
		query="select pk_seq,ten from tinhthanh where 1=1 ";
		
		if(this.vungId.length()>0)
		{
			query+=" and vung_fk='"+this.vungId+"' ";
		}
		this.ttRs=this.db.get(query);
		
		query="select pk_seq ,ten,VUNG_FK from khuvuc  where 1=1 ";
	/*	if(this.view.length()>0)
			query+=" and pk_Seq in  "+util.Quyen_KhuVuc_DMS(this.userId)+" ";
*/		this.kvRs=this.db.get(query);
		
		query="select pk_seq,ten from vung where 1=1 ";
		/*query+=" and pk_Seq in  "+util.Quyen_Vung_DMS(userId)+" ";
		*/
		this.vungRs=this.db.get(query);
		
		
		query="select pk_Seq,ten from NhomHang ";
		this.nhomRs=this.db.get(query);
		
		
		query="select pk_Seq,ten,diachi from Nhaphanphoi where trangthai=1 and iskhachhang=0  ";
	/*	if(this.cndt.equals("0"))
			query+="and loaiNPP not in (4,5) and loainpp is not null";
		*/
		
	/*	if(this.view.length()>0)
		{
			query+="and pk_Seq in "+util.quyen_npp_DMS(userId)+"" ;
		}
		*/
		
		if(this.ttId.length()>0)
		query+=" and tinhthanh_Fk='"+this.ttId+"' ";
		
		if(this.vungId.length()>0)
			query+=" and khuvuc_fk in (select pk_seq from khuvuc where vung_fk='"+this.vungId+"' ) ";
		
		System.out.println("_NPP_"+query);
		
		this.nppRs=this.db.get(query);
		
		this.RsnhomhangERPid=db.get("select pk_seq,Ten from nhomhang  ");
		this.RsnhomkhERPid=db.get("select pk_seq,Ten from NHOMKHACHHANGNPP  ");
	}
	
	String querytong="";
	public String getQuerytong() {
		return querytong;
	}
	public void setQuerytong(String querytong) {
		this.querytong = querytong;
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
		

		String conditionHO="";   
		
		if(this.loaiHoaDon.length()<=0)
		{
			conditionHO += "and isnull(a.LOAIHOADON,0) =0"; 
		}
		else 
		{
			conditionHO += "and isnull(a.LOAIHOADON,0) ='"+this.loaiHoaDon+"' ";
		}
		if(this.nhomkhERPid.length()>0)
		{
			conditionHO+=" and a.npp_fk  in  (select npp_fk FROM link_erp_that_noibo.traphacoerp.dbo.NHOMKHACHHANGNPP_NPP    where NHOMKHACHHANGNPP_FK='"+nhomkhERPid+"' )  ";
		}
		if(this.tuNgay.length()>0)
		{
			conditionHO+=" and a.NgayXuatHD>='"+this.tuNgay+"'";
		}
		if(this.denNgay.length()>0)
		{
			conditionHO+=" and a.NgayXuatHD <='"+this.denNgay+"'";
		}
		if(this.nppId.length()>0)
		{
			conditionHO+=" and a.npp_fk ='"+this.nppId+"'";
		}
		if(kvId.length()>0)
		   {
				 conditionHO+= " and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in ("+this.kvId+"))";
		   }
		 if(vungId.length()>0)
	   {
			 conditionHO+= "  and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in (select pk_seq from KHUVUC WHERE VUNG_FK='"+this.vungId+"' ))";
	   }
		
		if(this.spId.length()>0)
		{
			conditionHO+=" and b.sanpham_fk ='"+this.spId+"'";
		}
		if(this.khId.length()>0)
		{
			conditionHO+=" and a.npp_fk ='"+this.khId+"'";
		}
		
		if(this.ttId.length()>0)
		{
			conditionHO+=" and a.npp_fk in (select pk_seq	 from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
		}
	
		
		if(this.kbhId.length()>0)
		{
			conditionHO+=" and a.pk_seq in (select hoadon_fk from erp_hoadon_Ddh where ddh_fk in (select pk_seq from erp_dondathang where kbh_fk='"+this.kbhId+"')) ";
		}
		
		
	
		 String condick="";

		if(this.loaiHoaDon.length()<=0)
		{
			//condick+= "and isnull(a.LOAIHOADON,0) =0"; 
		}
		else 
		{
			//condick+= "and isnull(a.LOAIHOADON,0)  ='"+this.loaiHoaDon+"' ";
		}
		
		if(this.spId.length()>0)
		{
			condick+=" and b.sanpham_fk ='"+this.spId+"'";
		}
	  if(nppId.length()>0)
	   {
		  condick+= " and a.npp_fk='"+this.nppId+"' ";
	   }
	  if(ttId.length()>0)
	   {
		  condick+= "  and a.doituongid in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' )";
	   }
	  if(kvId.length()>0)
	   {
			 condick+= " and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in ("+this.kvId+"))";
	   }
	  if(vungId.length()>0)
	   {
		  condick+= "  and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in (select pk_seq from KHUVUC WHERE VUNG_FK='"+this.vungId+"' ))";
	   }
	  
	  if(this.nhomkhERPid.length()>0)
		{
		  condick+=" and a.npp_fk  in  (select npp_fk FROM link_erp_that_noibo.traphacoerp.dbo.NHOMKHACHHANGNPP_NPP    where NHOMKHACHHANGNPP_FK='"+nhomkhERPid+"' )  ";
		}
	   if(khId.length()>0)
	   {
		   condick+= " and doituongid='"+this.khId+"' ";
	   }
	   
	   if(tuNgay.length()>0)
	   {
		   condick+= " and a.Ngaytra >='"+this.tuNgay+"' ";
	   }
	   
	   if(denNgay.length()>0)
	   {
		   condick+= " and a.Ngaytra <='"+this.denNgay+"' ";
	   }

		 String condi_KM="";

		if(this.loaiHoaDon.length()<=0)
		{
			//condick+= "and isnull(a.LOAIHOADON,0) =0"; 
		}
		else 
		{
			//condi_KM+= "and isnull(a.LOAIHOADON,0)  ='"+this.loaiHoaDon+"' ";
		}
		
		if(this.spId.length()>0)
		{
			condi_KM+=" and b.sanpham_fk ='"+this.spId+"'";
		}
	  if(nppId.length()>0)
	   {
		  condi_KM+= " and a.npp_fk='"+this.nppId+"' ";
	   }
	  if(ttId.length()>0)
	   {
		   condi_KM+= "  and a.doituongid in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' )";
	   }
	  if(kvId.length()>0)
	   {
		  condi_KM+= " and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in ("+this.kvId+"))";
	   }
	  if(vungId.length()>0)
	   {
		  condi_KM+= "  and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in (select pk_seq from KHUVUC WHERE VUNG_FK='"+this.vungId+"' ))";
	   }
	  
	  if(this.nhomkhERPid.length()>0)
		{
		  condi_KM+=" and a.npp_fk  in  (select npp_fk FROM link_erp_that_noibo.traphacoerp.dbo.NHOMKHACHHANGNPP_NPP    where NHOMKHACHHANGNPP_FK='"+nhomkhERPid+"' )  ";
		}
	   if(khId.length()>0)
	   {
		   condi_KM+= " and doituongid='"+this.khId+"' ";
	   }
	   
	   if(tuNgay.length()>0)
	   {
		   condi_KM+= " and a.ngayghinhan >='"+this.tuNgay+"' ";
	   }
	   
	   if(denNgay.length()>0)
	   {
		   condi_KM+= " and a.ngayghinhan <='"+this.denNgay+"' ";
	   }
	 
		
	
	
	   
	 	Utility util = new Utility();
	 	String [] thangNamKhoaSo= util.getNgayThangKhoaSo_KHO_GanNhat(db, this.tuNgay);
	 
	 		   
	 		   query="";
	 		   if(this.action.length()>0)
	 		   {
					query=
							" select HD.SANPHAM_FK,LoaiHD,spMa,spTen,spDonVi,SUM(SoLuong)as SoLuong,AVG(HD.DonGia) as DonGia,AVG(HD.DonGiaTra) as DonGiaTra,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT, STT,ISNULL(BG.DONGIA,0) AS GIATON  \n"+   
						   "   from   \n"+   
						   "   (  \n"+   
						   
					
							
							"		select c.pk_seq as SANPHAM_FK,c.MA as spMa,c.TEN as spTen,d.DONVI as spDonVi,'TRA' as LoaiHD,  \n"+
							"			(-1)* SUM(b.SoLuong)as SoLuong, (-1)* AVG(b.DonGia) as DonGia,  \n"+
							"			(-1)* AVG(b.DonGia) as DonGiaTra  ,     \n"+
							"			(-1)*sum(round(b.SoLuong*b.DONGIA,0)) as BVAT,       \n"+
							"			(-1)*sum( round(  round(b.SoLuong*b.DONGIA,0)*(b.Vat/100),0 ) ) as VAT , \n"+
							"			(-1)*sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.Vat/100),0) ) as AVAT,0 as STT  \n"+
							"		from ERP_DONTRAHANG a inner join ERP_DONTRAHANG_SANPHAM b on b.DONTRAHANG_FK=a.PK_SEQ  \n"+                                                                           	
							"			inner join ERP_SANPHAM c on c.PK_SEQ=b.SANPHAM_FK \n"+
							"			inner join donvidoluong d on d.pk_Seq=c.dvdl_Fk \n"+
							 " 		 inner join NHAPHANPHOI NPP on NPP.pk_Seq=a.doituongid  \n"+
							"			where a.TrangThai=1  "+condick+" \n"+ 
							"		group by c.MA,c.TEN,d.DONVI,c.pk_seq \n"+
							
							
						
							" union all	" +
							
							"select  sanpham_fk,sp.ma as spMa,sp.TEN as spTen,spDonVi   	,  "+     
							" 'H0' as LoaiHD, HO.SoLuong,HO.DonGia,HO.DonGiaTra ,HO.BVAT-HO.bvat_ck as BVAT, HO.VAT-HO.vat_ck AS VAT ,HO.AVAT -HO.avat_ck as AVAT,0 as STT  "+
							"	from                      "+
							"	(                      "+
							"			select b.sanpham_fk,  "+ 
							"			sum(round(b.soluong*b.dongia,0)) as bvat,  "+
							"			sum( round( round( b.soluong*b.dongia,0) *(1+ b.thuesuat/100) ,0)) as avat , "+  
							"			sum( round( round( b.soluong*b.dongia,0) *(b.thuesuat/100) ,0)) as vat, sum(b.soluong)as soluong, "+
							"			avg(b.dongia) as dongia,0 as dongiatra,b.donvitinh   as spdonvi , "+
							"			sum(round(b.chietkhau,0)) as bvat_ck, "+
							"			sum(round( round(b.chietkhau,0)*b.thuesuat/100,0)) as vat_ck, "+
							"			sum(round( round(b.chietkhau,0)*(1+b.thuesuat)/100,0)) as avat_ck "+
							"	from erp_hoadon a inner join erp_hoadon_sp b on b.hoadon_fk=a.pk_seq "+ 
							" inner join erp_hoadon_ddh c on c.HOADON_FK=a.PK_SEQ	"+
							
							" inner join ERP_DONDATHANG d on d.PK_SEQ=c.DDH_FK "+
							 " 		 inner join NHAPHANPHOI NPP on NPP.pk_Seq=a.npp_fk  \n"+
							"	where  d.LoaiDonHang in (4,2) and a.trangthai not in  (1,3,5)   "+conditionHO+" "+  
							"	group by  b.sanpham_fk ,b.donvitinh      "+
							"	)as HO                     "+
						
							"	inner join ERP_sanpham sp on sp.pk_Seq=HO.sanpham_fk where 1=1 " ;
						
					 query+=	 " \n"+
						   		"   union all  \n"+
								 "  select c.pk_seq as SANPHAM_FK,c.MA as spMa,c.TEN as spTen,d.DONVI as spDonVi,'KM' as LoaiHD,    \n"+
								 "  	SUM(b.SoLuong)as SoLuong, SUM(b.THANHTIEN) as DonGia,    \n"+
								 "  	0 as DonGiaTra  ,       \n"+
								 "  		SUM(b.THANHTIEN) as BVAT,         \n"+
								 "  	sum( round(  round(b.THANHTIEN,0)*(10/100),0 ) ) as VAT ,   \n"+
								 "  		sum( round(  round( b.THANHTIEN,0) *(1+ 10/100),0) ) as AVAT,0 as STT    \n"+
								 "  		from ERP_XUATKMKHONGHD a inner join ERP_XUATKMKHONGHD_SANPHAM b on b.xuatkm_fk=a.PK_SEQ    \n"+
								 "  		inner join ERP_SANPHAM c on c.PK_SEQ=b.SANPHAM_FK   \n"+
								 " 			 inner join NHAPHANPHOI NPP on NPP.pk_Seq=a.doituongid  \n"+
								 " 			 inner join donvidoluong d on d.pk_Seq=c.dvdl_Fk   \n"+
								 "  		where a.TrangThai=1   "+condi_KM+" \n"+
								 "  group by c.MA,c.TEN,d.DONVI,c.pk_seq \n";
					   
					
						   query+=
						   "     \n"+   
						   "   ) as HD   \n"+
						   " LEFT JOIN  \n"+
						   "(  \n"+
						   "SELECT SANPHAM_FK, DONGIA FROM ERP_BANGGIA_THANHPHAM_CUOIKY \n"+
						   " WHERE "+thangNamKhoaSo[0]+"= NAM AND  "+thangNamKhoaSo[1]+" = THANG \n"+
						   " ) BG ON BG.SANPHAM_FK = HD.SANPHAM_FK    \n";
						   query+=
						   "   group by STT,spMa,spTen,spDonVi,STT,HD.LoaiHD,BG.DONGIA ,HD.SANPHAM_FK ";
						   System.out.println("===================\n"+query);
						   this.hoadonRs=db.get(query);
						   
						 
								   
						  
								   
						 
						 
						   setTotal_Query(query);
	 							this.queryHd=" with DATA as ( "+query+") \n"+
	 										 "\n	select STT,spMa,spTen,spDonVi,SUM(SoLuong) as SoLuong,SUM(Avatb) as Avatb,SUM(AvatT) as AvatT,sum(GIATON) GIATON ,sum(DonGiaTra) DonGiaTra  from ( "+
	 										"\n	 select  STT,AA.spMa,AA.spTen,AA.SoLuong,AA.spDonVi,isnull(AA.HB,0) Avatb,isnull(AA.TRA,0) AvatT,ROUND(GIATON*AA.SoLuong,0) AS GIATON,DonGiaTra from( "+ 
	 										"\n	 	  select  DATA.STT, spMa,spTen,spDonVi,SoLuong ,SUM(AVAT) as avat,DATA.LoaiHD,DATA.GIATON,Data.DonGiaTra "+
	 										"\n			  from DATA where STT=0   "+
	 										"\n		  group by spMa,spTen,spDonVi,DATA.LoaiHD,DATA.STT  ,SoLuong,Data.DonGiaTra,DATA.GIATON  "+
	 										"\n		  ) as AA pivot (sum(AA.avat) for loaihd in ([HB],[TRA],[CK])) as AA ) as data  "+
	 										"\n		  group by STT,spMa,spTen,spDonVi "+
	 										"\n		  order by STT,SpMa ";
	 							
	 							this.querynh=" with DATA as ( "+query+") \n"+
											 "\n	select STT,NhomHang,SUM(SoLuong) as SoLuong,SUM(Avatb) as Avatb,SUM(AvatT) as AvatT,SUM(GIATON) as GIATON,sum(DonGiaTra) DonGiaTra  from ( "+
											 "\n	 select  STT,AA.nhomhang,AA.spMa,AA.spTen,AA.SoLuong,isnull(AA.HB,0) Avatb,isnull(AA.TRA,0) AvatT,ROUND(GIATON*AA.SoLuong,0) as GIATON,DonGiaTra from( "+ 
											 "\n	 	  select  DATA.STT,nh.Ten as nhomhang, spMa,spTen,SUM(SoLuong) SoLuong ,SUM(AVAT) as avat,DATA.LoaiHD,DATA.GIATON,Data.DonGiaTra  "+
											 "\n			  from DATA inner join NhomHang_SanPham nhsp on nhsp.SanPham_FK=DATA.SANPHAM_FK "+
											 "\n			  inner join NhomHang nh on nh.pk_seq=nhsp.NhomHang_FK			   "+
											 "\n			  where STT=0    "+
											 "\n		  group by spMa,spTen,DATA.LoaiHD,DATA.STT  ,Data.DonGiaTra,DATA.GIATON,nh.Ten  "+
											 "\n		  ) as AA pivot (sum(AA.avat) for loaihd in ([HB],[TRA],[CK])) as AA ) as data  "+
											 "\n		  group by STT ,nhomhang "+
											 "\n		  order by STT ";
	 							
	 							
	 							this.queryHd=query+" order by STT,spMa asc ";
	 							this.querytong=" with DATA as ( "+query+") \n"+
 									  	" select SUM(AVAT) as AVATDS,STT from DATA  group by STT ";

	 						
	 						 System.out.println("___oalaoalaoa"+this.querynh);
	 					
	 		   }
	 		try {
				if(this.userId.trim().length()>0)
				{
					query = "select TEN from NHANVIEN WHERE PK_SEQ =  " +this.userId;
					ResultSet rsten = db.get(query);
					if(rsten.next())
					{
						this.userTen = rsten.getString("TEN");
					}
					rsten.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	
	public String getUserTen() {
		return userTen;
	}
	public void setUserTen(String userTen) {
		this.userTen = userTen;
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
	 							 " select isnull(SUM(isnull(SoLuong,0)),0)as SoLuong,isnull(AVG(isnull(DonGia,0)),0) as DonGia,isnull(SUM(isnull(AVAT,0)),0) as AVAT,isnull(SUM(isnull(BVAT,0)),0)as BVAT,isnull(SUM(isnull(VAT,0)),0)as VAT  \n"+   
	 						   "   from  ("+searchquery+")   as HD   \n";
	 					  System.out.println("---QUYRY TOTAL: " + query);
	 					 this.totalRs=db.get(query);
	 		   }	 	
	}
	
	
	ResultSet totalRs;
	
	
	public ResultSet getTotalRs() {
		return totalRs;
	}
	public void setTotalRs(ResultSet totalRs) {
		this.totalRs = totalRs;
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
	public String getAction()
  {
  	return action;
  }
	public void setAction(String timkiem)
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
	@Override
	public String getKvId() {
		return this.kvId;
	}
	@Override
	public void setKvId(String kvId) {
		this.kvId = kvId;
	}
	@Override
	public ResultSet getKvRs() {
		return this.kvRs;
	}
	@Override
	public void setKvRs(ResultSet kvRs) {
		this.kvRs = kvRs;
	}
	
	
}
