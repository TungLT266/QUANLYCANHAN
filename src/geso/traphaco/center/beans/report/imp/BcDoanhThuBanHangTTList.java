package geso.traphaco.center.beans.report.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import org.w3c.dom.NodeList;

import geso.traphaco.center.beans.report.IBcDoanhThuBanHangTTList;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.WebService;
import geso.traphaco.distributor.db.sql.dbutils;

public class BcDoanhThuBanHangTTList  extends Phan_Trang implements IBcDoanhThuBanHangTTList, Serializable
{
	public BcDoanhThuBanHangTTList()
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
		this.cndt="0";
		this.kh="0";
		this.loainp="";
		this.nhomhangERPid="";
		this.nhomkhERPid="";
		this.kvId="";
		this.userTen = "";
		db = new dbutils();
	}
	/**
   * 
   */
  private static final long serialVersionUID = 1L;
	String tuNgay,denNgay,spId,nppId,ddkdId,userId,khId;
	String loainp;
	String nhomhangERPid,nhomkhERPid;
	ResultSet RsnhomhangERPid,RsnhomkhERPid;
	
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
	String userTen;
	
	public String getUserTen() {
		return userTen;
	}
	public void setUserTen(String userTen) {
		this.userTen = userTen;
	}
	public String getLoainp() {
		return loainp;
	}
	public void setLoainp(String loainp) {
		this.loainp = loainp;
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
	
	public void closeDB()
	{
		
	}
	
	dbutils db = new dbutils();
	public void createRs()
	{
		Utility util = new Utility();
		String query="select pk_Seq,ma,ten from LINK_DMS_THAT.DATACENTER.dbo.sanpham where trangthai=1";
		this.spRs=this.db.get(query);
		
		query="select pk_seq,manhanvien,ten from DaiDienKinhDoanh a where 1=1  ";
		
		if(this.nppId.length()>0)
		{
			query+=" and a.pk_seq in (select ddkd_fk from LINK_DMS_THAT.DATACENTER.dbo.daidienkinhdoanh_npp where npp_fk='"+nppId+"') ";
		}
		/*if(this.view.length()>0)
		{
			query+=" and pk_Seq in  "+util.Quyen_Ddkd_DMS(this.userId)+" ";
		}*/
		this.ddkdRs = this.db.get(query);
		
		if(this.nppId.length()>0)
		{
			
			/*query="select pk_seq,isnull(mafast,'') +' ' + ten + ' ' + isnull(diachi,'') as Ten from LINK_DMS_THAT.DATACENTER.dbo.khachhang where 1=1 ";
			if(this.view.length()>0)
			{
				query+=" and npp_fk in "+util.quyen_npp_DMS(userId);
			}
			
			if(this.nppId.length()>0)
			query+=" and npp_fk='"+this.nppId+"' ";
			
			this.khRs= this.db.get(query);*/
		}
		query="select pk_seq,isnull(mafast,'') +' ' + ten + ' ' + isnull(diachi,'') as Ten from NHAPHANPHOI where TRANGTHAI=1 AND ISKHACHHANG =1 ";
		
		this.khRs= this.db.get(query);
		
	
		query="select pk_Seq,ten from LINK_DMS_THAT.DATACENTER.dbo.NhomHang ";
		this.nhomRs=this.db.get(query);
		
		query="select pk_Seq,ten,DIENGIAI from LINK_DMS_THAT.DATACENTER.dbo.KENHBANHANG where TRANGTHAI=1 ";
		this.kbhRs = this.db.get(query);	
		
		
		query="select PK_SEQ,TEN from LINK_DMS_THAT.DATACENTER.dbo.tinhthanh   where 1=1 ";
		if(vungId.length()>0)
			query+=" and vung_fk='"+vungId+"'";
		/*if(this.view.length()>0)
			query+=" and pk_Seq in  "+util.Quyen_TinhThanh_DMS(this.userId)+" ";
		*/
		this.ttRs= this.db.get(query);
		
		query="select pk_seq ,ten,VUNG_FK from LINK_DMS_THAT.DATACENTER.dbo.khuvuc  where 1=1 ";
		/*if(this.view.length()>0)
			query+=" and pk_Seq in  "+util.Quyen_KhuVuc_DMS(this.userId)+" ";
		*/this.kvRs=this.db.get(query);
		
		query="select pk_seq,ten from LINK_DMS_THAT.DATACENTER.dbo.vung where 1=1 ";
		/*if(this.view.length()>0)
			query+=" and pk_Seq in  "+util.Quyen_Vung_DMS(this.userId)+" ";
	*/	this.vungRs=this.db.get(query);
		
		
		query="select pk_seq,ma,diachi,ten from LINK_DMS_THAT.DATACENTER.dbo.nhaphanphoi where trangthai=1  and isnull(isKHACHHANG,0)=0 ";
		/*if(this.view.length()>0)
		{
			query+="and pk_Seq in "+util.quyen_npp_DMS(userId)+"" ;
		}*/
		
		/*if(this.cndt.equals("0"))
			query+="and loaiNPP not in (4,5) and loainpp is not null"; 
		*/
		if(this.ttId.length()>0)
		query+=" and tinhthanh_Fk='"+this.ttId+"' ";
		
		if(this.vungId.length()>0)
			query+=" and khuvuc_fk in (select pk_seq from LINK_DMS_THAT.DATACENTER.dbo.khuvuc where vung_fk='"+this.vungId+"' ) ";
		if(this.loainp.length()>0)
		{
			if(this.loainp.equals("1"))
				query+=" and loaiNPP  in (0,1)  ";
			if(this.loainp.equals("2"))
				query+=" and loaiNPP  in (4)  ";
			if(this.loainp.equals("3"))
				query+=" and pk_seq=1  ";
		}
		System.out.println("loai npp la "+this.loainp +"---"+query);
		this.nppRs=this.db.get(query);
		this.RsnhomhangERPid=db.get("select pk_seq,Ten from  nhomhang  ");
		this.RsnhomkhERPid=db.get("select pk_seq,Ten from NHOMKHACHHANGNPP  ");
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
		
	
		String condition_HO=" and Isnull(a.LoaiHoaDon,0) =0  ";   
		String condition_hdKhac = "";
		
		if(this.nhomkhERPid.length()>0)
		{
			condition_HO+=" and a.npp_fk  in  (select npp_fk FROM link_erp_that_noibo.traphacoerp.dbo.NHOMKHACHHANGNPP_NPP    where NHOMKHACHHANGNPP_FK='"+nhomkhERPid+"' )  ";
		}
		if(this.tuNgay.length()>0)
		{
			condition_HO+=" and a.NgayXuatHD>='"+this.tuNgay+"'";
			condition_hdKhac += " and a.NGAYHOADON >= '"+this.tuNgay+"'";
		}
		if(this.denNgay.length()>0)
		{
			condition_HO+=" and a.NgayXuatHD <='"+this.denNgay+"'";
			condition_hdKhac += " and a.NGAYHOADON <= '"+this.denNgay+"'";
		}
		if(this.nppId.length()>0)
		{
			condition_HO+=" and a.npp_fk ='"+this.nppId+"'";
			condition_hdKhac += " and a.KHACHHANG_FK <= '"+this.nppId+"'";
		}
		if(kvId.length()>0)
		   {
				 condition_HO+= "  and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in ("+this.kvId+"))";
				 
				 condition_hdKhac += " and a.khachhhang_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in ("+this.kvId+"))";
		   }
		 if(vungId.length()>0)
	   {
			 condition_HO+= "  and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in (select pk_seq from KHUVUC WHERE VUNG_FK='"+this.vungId+"' ))";
			 
			 condition_hdKhac+= "  and a.khachhhang_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in (select pk_seq from KHUVUC WHERE VUNG_FK='"+this.vungId+"' ))";
	   }
		
		 if(this.loainp.length()>0 && this.loainp.equals("1"))
			{
			 condition_HO+=" and a.npp_fk in (    select pk_seq from NHAPHANPHOI where loaiNPP in (0,1)  ) ";
			 condition_hdKhac+=" and a.khachhhang_fk in (    select pk_seq from NHAPHANPHOI where loaiNPP in (0,1)  ) ";
			}
			 
			 if(this.loainp.length()>0 && this.loainp.equals("2"))
			{
				 condition_HO+=" and a.npp_fk in (    select pk_seq from NHAPHANPHOI where loaiNPP in (4)  ) ";
				 condition_hdKhac+=" and a.khachhhang_fk in (    select pk_seq from NHAPHANPHOI where loaiNPP in (4)  ) ";
			}
		 
		 if(this.loainp.length()>0 && this.loainp.equals("3"))
			{
			 	condition_HO+=" and a.npp_fk in (    select pk_Seq from NHAPHANPHOI where isKHACHHANG =1  ) ";
			 	condition_hdKhac+=" and a.khachhhang_fk in (    select pk_Seq from NHAPHANPHOI where isKHACHHANG =1  ) ";
			}
		 
		if(this.spId.length()>0)
		{
			condition_HO+=" and b.sanpham_fk ='"+this.spId+"'";
			condition_hdKhac+=" and b.sanpham_fk ='"+this.spId+"'";
		}
		 if(this.nhomhangERPid.length()>0)
		 {
			 condition_HO+=" and b.SanPham_fk in  (select sanpham_fk FROM NhomHang_ERP_sanpham    where nhomhang_fk='"+nhomhangERPid+"' )  ";
			 condition_hdKhac+=" and b.SanPham_fk in  (select sanpham_fk FROM NhomHang_ERP_sanpham    where nhomhang_fk='"+nhomhangERPid+"' )  ";
		 }
		if(this.khId.length()>0)
		{
			condition_HO+=" and a.npp_fk ='"+this.khId+"'";
			condition_hdKhac+=" and a.khachhang_fk ='"+this.khId+"'";
		}
		
		if(this.ttId.length()>0)
		{
			condition_HO+=" and a.npp_fk in (select pk_seq	 from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
			condition_hdKhac+=" and a.khachhang_fk in (select pk_seq	 from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
		}
		
		if(this.nhomId.length()>0)
		{
			condition_HO+=" and b.SanPham_fk in  (select sanpham_fk FROM nhomhang_sanpham  where nhomhang_fk='"+this.nhomId+"' )  ";
			condition_hdKhac+=" and b.SanPham_fk in  (select sanpham_fk FROM nhomhang_sanpham  where nhomhang_fk='"+this.nhomId+"' )  ";
		}
		
		
		if(this.kbhId.length()>0)
		{
			condition_HO+=" and a.pk_seq in (select hoadon_fk from erp_HoaDon_DDH where ddh_fk in (select pk_seq from Erp_DonDatHang where kbh_fk='"+this.kbhId+"' )) ";
		}
		//
		
		
	 	
	 	
		String condition_TRA="";  
	//	condition+= " and a.npp_fk in " + Ult.quyen_npp(userId)+"";		 
		if(this.tuNgay.length()>0)
		{
			condition_TRA+=" and a.NGAYTRA>='"+this.tuNgay+"'";
		}
		if(this.denNgay.length()>0)
		{
			condition_TRA+=" and a.NGAYTRA <='"+this.denNgay+"'";
		}
		if(this.nppId.length()>0)
		{
			condition_TRA+=" and a.npp_fk ='"+this.nppId+"'";
		}
		 if(vungId.length()>0)
	   {
			 condition_TRA+= "  and a.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in (select pk_seq from KHUVUC WHERE VUNG_FK='"+this.vungId+"' ))";
	   }
		
		if(this.spId.length()>0)
		{
			condition_TRA+=" and b.sanpham_fk ='"+this.spId+"'";
		}		
		if(this.ttId.length()>0)
		{
			condition_TRA+=" and a.npp_fk in (select pk_seq	 from NhaPhanPhoi  where tinhthanh_fk='"+this.ttId+"' ) ";
		}
		
		if(this.nhomId.length()>0)
		{
			condition_TRA+=" and b.SanPham_fk in   (select sanpham_fk FROM NhomHang_SanPham  where nhomhang_fk='"+this.nhomId+"' ) ";
		}
		if(this.nhomhangERPid.length()>0)
		{
			condition_TRA+=" and b.SanPham_fk in   (select sanpham_fk FROM NhomHang_ERP_sanpham    where nhomhang_fk='"+nhomhangERPid+"' ) ";
		}
		
		
		  if(this.loainp.length()>0 && this.loainp.equals("1"))
			{
			  condition_TRA+=" and a.npp_fk in (    select loaiNPP from NHAPHANPHOI where loaiNPP in (0,1)  ) ";
			}
			 
			 if(this.loainp.length()>0 && this.loainp.equals("2"))
			{
				 condition_TRA+=" and a.npp_fk in (    select loaiNPP from NHAPHANPHOI where loaiNPP in (4)  ) ";
			}
			 if(this.loainp.length()>0 && this.loainp.equals("3"))
				{
				 condition_TRA+=" and a.npp_fk in (    select pk_Seq from NHAPHANPHOI where isKHACHHANG =1  ) ";
				}
		if(this.kbhId.length()>0)
		{
			condition_TRA+=" and a.kbh_fk='"+this.kbhId+"' ";
		}
		if(this.khId.length()>0)
		{
			condition_TRA+=" and a.doituongid='"+this.khId+"' ";
		}
		/*if(this.cndt.equals("0"))
			condition_TRA +=" AND a.npp_fk  in (select pk_Seq from nhaphanphoi where loaiNPP  in (4,5) and loainpp is not null ) ";
	 	*/
	   /*
	    * 
	    * Lấy HD Chi nhánh cấp 1,Chi nhánh cấp 2,Quầy bán buôn,Quầy Traphaco
	    * 
	    */
	   query="";
	   if(this.action.length()>0)
	   {
			query=  
					"  select vTen,ttTen,nppMa,nppTen,diachi,SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT      \n "+      
							"   from      \n "+      
							"   (      \n "+      
						
					" select sp.diachi,v.TEN as vTen,kv.TEN as kvTen,tt.TEN as ttTen,sp.ma as nppMa,sp.TEN as nppTen ,      \n "+      
					"   				'TRAHANG' as LoaiHD, hdOTC.SoLuong,hdOTC.DonGia ,hdOTC.BVAT, hdOTC.VAT ,hdOTC.AVAT      \n "+      
					"   	from      \n "+      
					"   	(      \n "+      
					"   		select a.doituongid as NPP_FK ,c.TINHTHANH_FK,    \n "+      
					"   					(-1)*sum(round(b.SoLuong*b.DONGIA,0)) as BVAT,      \n "+      
					"   					(-1)*sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.Vat/100),0) ) as AVAT ,   \n "+      
					"   					(-1)*sum( round(  round(b.SoLuong*b.DONGIA,0)*(b.Vat/100),0 ) ) as VAT,      \n "+      
					"   					(-1)* SUM(b.SoLuong)as SoLuong,AVG(b.DonGia) as DonGia      \n "+      
					"   		from ERP_DONTRAHANG a inner join ERP_DONTRAHANG_SANPHAM b on b.DONTRAHANG_FK=a.PK_SEQ " +
					"          inner join  NHAPHANPHOI c on c.pk_Seq=a.doituongid                                                                  "+      
					"   		where a.TrangThai=1   "+condition_TRA+"   \n "+      
					"   	    \n "+      
					"   		group by a.doituongid ,c.TINHTHANH_FK       \n "+      
					"   	)as hdOTC      \n "+      
					"   	inner join NHAPHANPHOI sp on sp.pk_Seq=hdOTC.NPP_FK      \n "+      
					"   	left join KHUVUC kv on kv.PK_SEQ=sp.KHUVUC_FK      \n "+      
					"   	left join VUNG v on v.PK_SEQ=kv.VUNG_FK      \n "+      
					"   	left join TINHTHANH tt on tt.PK_SEQ=hdOTC.TINHTHANH_FK      \n "+      						
					"   	union all   		   \n "+      
					"   select sp.diachi, v.TEN as vTen,kv.TEN as kvTen,tt.TEN as ttTen,sp.ma as nppMa,sp.TEN as nppTen ,  		   \n "+      
					"   'HO' as LoaiHD, hdOTC.SoLuong,hdOTC.DonGia ,hdOTC.BVAT-hdOTC.bvat_ck as BVAT, hdOTC.VAT-hdOTC.vat_ck AS VAT ,hdOTC.AVAT -hdOTC.avat_ck as AVAT   \n "+      
					"   from     		   \n "+      
					"   (     			   \n "+      
					"   select a.NPP_FK ,    \n "+      
					"   	sum(round(b.soluong*b.dongia,0)) as bvat,  			   \n "+      
					"   	sum( round( round( b.soluong*b.dongia,0) *(1+ b.thuesuat/100) ,0)) as avat , 			   \n "+      
					"   	sum( round( round( b.soluong*b.dongia,0) *(b.thuesuat/100) ,0)) as vat,    \n "+      
					"   	sum(b.soluong)as soluong, 					   \n "+      
					"   	sum(round(b.chietkhau,0)) as bvat_ck, 			   \n "+      
					"   	sum(round( round(b.chietkhau,0)*b.thuesuat/100,0)) as vat_ck, 			   \n "+      
					"   	sum(round( round(b.chietkhau,0)*(1+b.thuesuat)/100,0)) as avat_ck,   \n "+      
					"   	avg(b.dongia) as DonGia   \n "+      
					"   from ERP_HOADON a inner join ERP_HOADON_SP b on b.HOADON_FK=a.PK_SEQ  			   \n "+   
					" inner join erp_hoadon_ddh c on c.HOADON_FK=a.PK_SEQ	"+
					" inner join ERP_DONDATHANG d on d.PK_SEQ=c.DDH_FK "+
					"   where    d.LoaiDonHang in (0,2)  and a.TRANGTHAI not in (1,3,5)   "+condition_HO+" "+    
					"   	group by a.NPP_FK     			   \n "+      
					"      \n "+      
					"   )as hdOTC     		   \n "+      
					"   inner join NHAPHANPHOI sp on sp.pk_Seq=hdOTC.NPP_FK    			   \n "+      
					"   left join KHUVUC kv on kv.PK_SEQ=sp.KHUVUC_FK  		   \n "+      
					"   left join VUNG v on v.PK_SEQ=kv.VUNG_FK     			"
					+ "left join TINHTHANH tt on tt.PK_SEQ=sp.TINHTHANH_FK           \n "+      
					" union all\r\n" + 
					"\r\n" + 
					"	 select sp.diachi, v.TEN as vTen,kv.TEN as kvTen,tt.TEN as ttTen,sp.ma as nppMa,sp.TEN as nppTen ,  		   \r\n" + 
					"    'HD Khác' as LoaiHD, hdKhac.SoLuong,hdKhac.DonGia ,hdKhac.BVAT-hdKhac.bvat_ck as BVAT, hdKhac.VAT-hdKhac.vat_ck AS VAT ,hdKhac.AVAT -hdKhac.avat_ck as AVAT   \r\n" + 
					"    from     		   \r\n" + 
					"    (     			   \r\n" + 
					"    select a.KHACHHANG_FK NPP_FK ,    \r\n" + 
					"    	sum(round(b.thanhtien,0)) as bvat,  			   \r\n" + 
					"    	sum( round( b.thanhtien + isnull(b.tienthue,0),0)) as avat,    \r\n" + 
					"    	isnull(a.tienvat,sum(isnull(b.tienthue,0))) as vat , 			   \r\n" + 
					"    	sum(b.soluong)as soluong, 					   \r\n" + 
					"    	0 as bvat_ck, 			   \r\n" + 
					"    	0 as vat_ck, 			   \r\n" + 
					"    	0 as avat_ck,   \r\n" + 
					"    	avg(b.dongia) as DonGia   \r\n" + 
					"    from ERP_HOADONKHAC a inner join ERP_HOADONKHAC_SANPHAM b on b.HOADONKHAC_FK=a.PK_SEQ  			   \r\n" + 
					"      where    \r\n" + 
					"	  a.TRANGTHAI not in (1,3,5) " +condition_hdKhac  + " \r\n" +
					"	  group by a.KHACHHANG_FK  , a.tienvat   			   \r\n" + 
					"       \r\n" + 
					"    )as hdKhac     		   \r\n" + 
					"    inner join NHAPHANPHOI sp on sp.pk_Seq=hdKhac.NPP_FK    			   \r\n" + 
					"    left join KHUVUC kv on kv.PK_SEQ=sp.KHUVUC_FK  		   \r\n" + 
					"    left join VUNG v on v.PK_SEQ=kv.VUNG_FK     			\r\n" + 
					"	\r\n" + 
					"	left join TINHTHANH tt on tt.PK_SEQ=sp.TINHTHANH_FK "+
					"   ) as HD      \n "+      
					"   group by vTen,ttTen,nppTen,diachi,nppMa ";
					System.out.println("node la "+query);	
				this.queryHd=query;
				this.hoadonRs=db.get(query);
				
	   }
	   System.out.println(":::::::"+query);
	   
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
		setTotal_Query(this.queryHd);
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
	   /*
	    * 
	    * Lấy HD Chi nhánh cấp 1,Chi nhánh cấp 2,Quầy bán buôn,Quầy Traphaco
	    * 
	    */
	   String query="";
	   if(this.action.length()>0)
	   {
						query=
									 " select SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT      \n "+      
										"   from      \n "+      
										"   (      \n "+      
										" "+searchquery+" "+
 										"   ) as HD      \n ";
						System.out.println("toatal "+query);
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
	public String getAction()
  {
  	return action;
  }
	public void setAction(String timkiem)
  {
  	this.action = timkiem;
  }
	
	String cndt,kh;
	
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
	
	
	
}
