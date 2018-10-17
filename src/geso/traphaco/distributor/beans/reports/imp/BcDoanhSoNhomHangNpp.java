package geso.traphaco.distributor.beans.reports.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.reports.IBcDoanhSoNhomHangNpp;
import geso.traphaco.distributor.db.sql.dbutils;

public class BcDoanhSoNhomHangNpp extends Phan_Trang implements IBcDoanhSoNhomHangNpp, Serializable 
{
	public BcDoanhSoNhomHangNpp()
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
		
		
		query="select pk_Seq,ten,diachi from Nhaphanphoi where trangthai=1 and iskhachhang=0 ";
		
		if(this.nppId.length()>0)
		{
			query +=" and pk_seq='"+this.nppId+"' ";
		}
		if(this.view.length()>0)
		{
			query+="and pk_Seq in "+util.quyen_npp(userId)+"" ;
		}
		
		
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
	
	public void init(String search)
	{
		String query;

		if(this.view.length()<=0)
		{
			this.getNppInfo();
		}
		query=
			"	select pk_seq as nhomId,Ma as nhomMa,Ten as nhomTen from NhomHang  ";
		
		System.out.println("[BcDoanhSoNhomHangList]"+query);
		
		this.nhomRs=  this.db.getScrol(query);
		ResultSet rs =db.get(query);
		String spNhomId__="";
		String spNhomTEN__="";
		
		int i=0;
		try
	{
	    while(rs.next())
	    {
	    	if(i==0)
	    	{
	    		spNhomId__= rs.getString("nhomId");
	    		spNhomTEN__ = rs.getString("nhomTen");
	    	}else 
	    	{
	    		spNhomId__ +="__#__"+ rs.getString("nhomId");
	    		spNhomTEN__ +="__#__" + rs.getString("nhomTen");
	    	}
	    	i++;
	    }
	   if(spNhomId__.length()>0)
	   {
	  	 spNhomId=spNhomId__.split("__#__");
	  	 spNhomTen=spNhomTEN__.split("__#__");
	   }
	   if(rs!=null)rs.close();
    } catch (SQLException e)
    {
	    e.printStackTrace();
    }
		
		String condition="";
		
		if(tuNgay.length()>0)
		{
			condition+=" and dh.NgayXuatHd>= ''"+this.tuNgay+"'' ";
		}
		if(this.denNgay.length()>0)
		{
			condition+=" and dh.NgayXuatHd<= ''"+this.denNgay+"'' ";
		}
		if(this.nppId.length()>0)
		{
			condition+=" and dh.Npp_fk=''"+this.nppId+"'' ";
		}
		if(this.vungId.length()>0)
		{
			condition +=" and dh.npp_fk in (select pk_seq from NHAPHANPHOI where tinhthanh_Fk in (select pk_Seq from tinhthanh where vung_Fk=''"+this.vungId+"'' )) ";
		}
		
		if(this.ttId.length()>0)
		{
			condition+=" and dh.khachHang_fk in (select pk_seq	 from KhachHang  where tinhthanh_fk='"+this.ttId+"' ) ";
		}
		
		if(this.spId.length()>0)
		{
			condition+=" and dhsp.sanpham_fk =''"+this.spId+"''";
		}
		
		if(this.nhomId.length()>0)
		{
			condition+=" and dhsp.sanpham_fk in (select sanpham_fk from nhomhang_Sanpham where nhomhang_fk=''"+this.nhomId+"'')";
		}		
		
		if(this.ddkdId.length()>0)
		{
			condition += " and dh.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk =''"+this.ddkdId+"''))";
		}
		
		if(this.kbhId.length()>0)
		{
			condition+=" and dh.pk_seq in (select hoadon_fk from hoadon_Ddh where ddh_fk in (select pk_seq from DonHang where kbh_fk=''"+this.kbhId+"'')) ";
		}
		
		String condition1="";
		if(tuNgay.length()>0)
		{
			condition1+=" and dh.NgayXuatHd>= ''"+this.tuNgay+"'' ";
		}
		if(this.denNgay.length()>0)
		{
			condition1+=" and dh.NgayXuatHd<= ''"+this.denNgay+"'' ";
		}
		if(this.nppId.length()>0)
		{
			condition1+=" and dh.Npp_fk=''"+this.nppId+"'' ";
		}
		if(this.vungId.length()>0)
		{
			condition1 +=" and dh.npp_fk in (select pk_seq from NHAPHANPHOI where tinhthanh_Fk in (select pk_Seq from tinhthanh where vung_Fk=''"+this.vungId+"'' )) ";
		}
		
		if(this.ttId.length()>0)
		{
			condition1+=" and dh.khachHang_fk in (select pk_seq	 from KhachHang  where tinhthanh_fk='"+this.ttId+"' ) ";
		}
		
		if(this.spId.length()>0)
		{
			condition1+=" and dhsp.sanpham_fk =''"+this.spId+"''";
		}
		
		if(this.nhomId.length()>0)
		{
			condition1+=" and dhsp.sanpham_fk in (select sanpham_fk from nhomhang_Sanpham where nhomhang_fk=''"+this.nhomId+"'')";
		}		
		
		
		if(this.kbhId.length()>0)
		{
			condition1+=" and dh.pk_seq in (select hoadon_fk from hoadon_Ddh where ddh_fk in (select pk_seq from DonHang where kbh_fk=''"+this.kbhId+"'')) ";
		}
		
		/*condition +=" AND dh.npp_fk  in (select pk_Seq from nhaphanphoi where loaiNPP not in (4,5) and loainpp is not null ) ";*/
		
		String conditionETC="";
		
		if(tuNgay.length()>0)
		{
			conditionETC+=" and dh.NgayXuatHd>= ''"+this.tuNgay+"'' ";
		}
		if(this.denNgay.length()>0)
		{
			conditionETC+=" and dh.NgayXuatHd<= ''"+this.denNgay+"'' ";
		}
		if(this.nppId.length()>0)
		{
			conditionETC+=" and dh.Npp_fk=''"+this.nppId+"'' ";
		}
		if(this.vungId.length()>0)
		{
			conditionETC +=" and dh.npp_fk in (select pk_seq from NHAPHANPHOI where tinhthanh_Fk in (select pk_Seq from tinhthanh where vung_Fk=''"+this.vungId+"'' )) ";
		}
		

		if(this.ttId.length()>0)
		{
			conditionETC+=
				" and	(  dh.khachHang_FK  in (select pk_seq	 from KhachHang  where tinhthanh_fk='"+this.ttId+"' ) OR  dh.npp_dat_Fk in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' )  ) ";
		}
		
		if(this.spId.length()>0)
		{
			conditionETC+=" and dhsp.sanpham_fk =''"+this.spId+"''";
		}
		
		if(this.nhomId.length()>0)
		{
			conditionETC+=" and dhsp.sanpham_fk in (select sanpham_fk from nhomhang_Sanpham where nhomhang_fk=''"+this.nhomId+"'')";
		}		
		
		if(this.ddkdId.length()>0)
		{
			conditionETC += " and dh.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk =''"+this.ddkdId+"''))";
		}
		
		if(this.kbhId.length()>0)
		{
			conditionETC+=" and dh.pk_seq in (select hoadonnpp_fk from erp_hoadonnpp_Ddh where ddh_fk in (select pk_seq from Erp_DonDatHangNpp where kbh_fk=''"+this.kbhId+"'')) ";
		}
		/*conditionETC +=" AND dh.npp_fk  in (select pk_Seq from nhaphanphoi where loaiNPP not in (4,5) and loainpp is not null ) ";*/
		
		String conditionHO="";
		
		if(tuNgay.length()>0)
		{
			conditionHO+=" and dh.NgayXuatHd>= ''"+this.tuNgay+"'' ";
		}
		if(this.denNgay.length()>0)
		{
			conditionHO+=" and dh.NgayXuatHd<= ''"+this.denNgay+"'' ";
		}
		if(this.nppId.length()>0)
		{
			conditionHO+=" and dh.Npp_fk=''"+this.nppId+"'' ";
		}
		if(this.vungId.length()>0)
		{
			conditionHO +=" and dh.npp_fk in (select pk_seq from NHAPHANPHOI where tinhthanh_Fk in (select pk_Seq from tinhthanh where vung_Fk=''"+this.vungId+"'' )) ";
		}
		
		if(this.ttId.length()>0)
		{
			conditionHO +=" and dh.npp_fk in (select pk_seq from NHAPHANPHOI where tinhthanh_Fk=''"+this.ttId+"'')";
		}
		if(this.spId.length()>0)
		{
			conditionHO +=" and dhsp.sanpham_fk =''"+this.spId+"''";
		}
		
		if(this.nhomId.length()>0)
		{
			conditionHO +=" and dhsp.sanpham_fk in (select sanpham_fk from nhomhang_Sanpham where nhomhang_fk=''"+this.nhomId+"'')";
		}		
		
		if(this.ddkdId.length()>0)
		{
			conditionHO += " and dh.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk =''"+this.ddkdId+"''))";
		}
		
		if(this.kbhId.length()>0)
		{
			conditionHO+=" and dh.pk_seq in (select hoadon_fk from erp_hoadon_Ddh where ddh_fk in (select pk_seq from erp_dondathang where kbh_fk=''"+this.kbhId+"'')) ";
		}
		
		
   if(action.length()>0)
   {
		/*		query=
				"		declare @cols varchar(max), @sql varchar(MAX) "+
				"		set @cols = '[' + REPLACE(   "+
				"		   (SELECT distinct v.PK_SEQ as [data()] "+ 
				"		    from nhomhang v "+
				"		   FOR XML PATH('')  "+
				"		    ),' ','],[') + ']'  "+  
				"		set @sql =  "+
				"		'select isnull(data.DDKD,''not values'') DDKD,data.NPP_FK,data.kvTEN,data.ttTEN,data.nppMa,data.nppTEN,sum(data.[100000]) as [100000],sum(data.[100001]) as [100001] ,sum(data.[100004]) as [100004],sum(data.[111]) as tongds "+
				 " from ( "+
				"		( select " +
				" (select top(1)d.TEN from  KHACHHANG_TUYENBH b    \n"+
				" 		inner join TUYENBANHANG c on b.TBH_FK=c.PK_SEQ   \n"+
				" 		inner join DAIDIENKINHDOANH d on c.DDKD_FK=d.PK_SEQ \n"+ 	
				" 		where b.KHACHHANG_FK=t.KHACHHANG_FK	) as DDKD, \n"+
				" dh.npp_fk,nsp.NhomHang_FK,  sum( ROUND( ROUND(dhsp.soluong*dhsp.dongia,0)*(1+ dhsp.vat/100.0 ),0 )) as doanhso "+ 
				"			from HOADON dh   "+
				"			 inner join HOADON_SP dhsp on dhsp.hoadon_fk=dh.pk_seq "+         
				"			 inner join sanpham sp on sp.pk_Seq=dhsp.sanpham_fk       "+
				"			 inner join NhomHang_SanPham nsp on nsp.SanPham_FK=dhsp.sanpham_fk "+   
				"			where dh.loaihoadon=0 and  dh.trangthai not in ( 1 , 3, 5 ) "+condition+" "+
				"			group by dh.NPP_FK,nsp.NhomHang_FK  "+
				"			union all "+
				"        select  dh.npp_fk,111 as NhomHang_FK,  sum( ROUND( ROUND(dhsp.soluong*dhsp.dongia,0)*(1+ dhsp.vat/100.0 ),0 )) as doanhso "+ 
				"			from HOADON dh   "+
				"			 inner join HOADON_SP dhsp on dhsp.hoadon_fk=dh.pk_seq "+         
				"			 inner join sanpham sp on sp.pk_Seq=dhsp.sanpham_fk       "+
				"			where dh.loaihoadon=0 and  dh.trangthai not in ( 1 , 3, 5 ) "+condition+" "+
				"			group by dh.NPP_FK  "+
				"		 )as p pivot ( sum(doanhso) for NhomHang_FK IN ('+@cols+',[111]) )as t "+
				"		inner join NHAPHANPHOI npp on npp.PK_SEQ=t.NPP_FK "+
				"		inner join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK "+
				"		inner join vung kv on kv.pk_seq=tt.vung_fk' "+ 
				" ) as data\n"+ 
				"  group by data.DDKD,data.NPP_FK,data.kvTEN,data.ttTEN,data.nppMa,data.nppTEN "+
				"		exec (@sql) ";*/
				
				query=			"		declare @cols varchar(max), @sql varchar(MAX) "+
								"		set @cols = '[' + REPLACE(   "+
								"		   (SELECT distinct v.PK_SEQ as [data()] "+ 
								"		    from nhomhang v "+
								"		   FOR XML PATH('')  "+
								"		    ),' ','],[') + ']'  "+  
								"		set @sql =  "+
								"		'select isnull(data.DDKD,''not values'') DDKD,data.NPP_FK,data.kvTEN,data.ttTEN,data.nppMa,data.nppTEN,sum(data.[100000]) as [100000],sum(data.[100001]) as [100001] ,sum(data.[100004]) as [100004],sum(data.[111]) as tongds from (   " +
								 "  select "+
								" (select top(1)d.TEN from  KHACHHANG_TUYENBH b    \n"+
								" 		inner join TUYENBANHANG c on b.TBH_FK=c.PK_SEQ   \n"+
								" 		inner join DAIDIENKINHDOANH d on c.DDKD_FK=d.PK_SEQ \n"+ 	
								" 		where b.KHACHHANG_FK=t.KHACHHANG_FK	) as DDKD, \n"+
								 "npp_fk, kv.ten as kvTEN,tt.TEN  as ttTEN,npp.MA as nppMa,npp.TEN as nppTEN,'+@cols+',[111]\n"+
								"		from  \n"+
								"		( select  dh.khachhang_fk,dh.npp_fk,nsp.NhomHang_FK,  sum( ROUND( ROUND(dhsp.soluong*dhsp.dongia,0)*(1+ dhsp.vat/100.0 ),0 )) as doanhso \n"+ 
								"			from HOADON dh   "+
								"			 inner join HOADON_SP dhsp on dhsp.hoadon_fk=dh.pk_seq \n"+         
								"			 inner join sanpham sp on sp.pk_Seq=dhsp.sanpham_fk       \n"+
								"			 inner join NhomHang_SanPham nsp on nsp.SanPham_FK=dhsp.sanpham_fk \n"+   
								"			where dh.loaihoadon=0 and  dh.trangthai not in ( 1 , 3, 5 ) "+condition+" \n"+
								"			group by dh.NPP_FK,nsp.NhomHang_FK,dh.khachhang_fk  \n"+
								"			union all \n"+
								"        select  dh.khachhang_fk,dh.npp_fk,111 as NhomHang_FK,  sum( ROUND( ROUND(dhsp.soluong*dhsp.dongia,0)*(1+ dhsp.vat/100.0 ),0 )) as doanhso \n"+ 
								"			from HOADON dh   "+
								"			 inner join HOADON_SP dhsp on dhsp.hoadon_fk=dh.pk_seq \n"+         
								"			 inner join sanpham sp on sp.pk_Seq=dhsp.sanpham_fk      \n"+
								"			where dh.loaihoadon=0 and  dh.trangthai not in ( 1 , 3, 5 ) "+condition+" \n"+
								"			group by dh.NPP_FK,dh.khachhang_fk  "+	
								"		 )as p pivot ( sum(doanhso) for NhomHang_FK IN ('+@cols+',[111]) )as t \n"+
								"		inner join NHAPHANPHOI npp on npp.PK_SEQ=t.NPP_FK "+
								"		inner join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK \n"+
								"		inner join vung kv on kv.pk_seq=tt.vung_fk \n"+
								" ) as data\n"+ 
								"  group by data.DDKD,data.NPP_FK,data.kvTEN,data.ttTEN,data.nppMa,data.nppTEN ";
				if(this.ddkdId.length()<=0)
				{
					query+=
						"  union all \n"+
						" select '''' as DDKD,data.NPP_FK,data.kvTEN,data.ttTEN,data.nppMa,data.nppTEN,sum(data.[100000]) as [100000],sum(data.[100001]) as [100001] ,sum(data.[100004]) as [100004],sum(data.[111]) as tongds from (   \n"+
						"  select "+
						" npp_fk, kv.ten as kvTEN,tt.TEN  as ttTEN,npp.MA as nppMa,npp.TEN as nppTEN,'+@cols+',[111] \n"+
						"		from  "+
						"		( select  dh.khachhang_fk,dh.npp_fk,nsp.NhomHang_FK,  sum( ROUND( ROUND(dhsp.soluong*dhsp.dongia,0)*(1+ dhsp.vat/100.0 ),0 )) as doanhso \n"+ 
						"			from HOADON dh   \n"+
						"			 inner join HOADON_SP dhsp on dhsp.hoadon_fk=dh.pk_seq \n"+        
						"			 inner join sanpham sp on sp.pk_Seq=dhsp.sanpham_fk       \n"+
						"			 inner join NhomHang_SanPham nsp on nsp.SanPham_FK=dhsp.sanpham_fk \n"+  
						"			where dh.loaihoadon=0 and  dh.trangthai not in ( 1 , 3, 5 ) "+condition1+" \n"+
						"			group by dh.NPP_FK,nsp.NhomHang_FK,dh.khachhang_fk  \n"+
						"			union all  \n"+
						"        select  dh.khachhang_fk,dh.npp_fk,111 as NhomHang_FK,  sum( ROUND( ROUND(dhsp.soluong*dhsp.dongia,0)*(1+ dhsp.vat/100.0 ),0 )) as doanhso \n"+ 
						"			from HOADON dh   \n"+
						"			 inner join HOADON_SP dhsp on dhsp.hoadon_fk=dh.pk_seq \n"+         
						"			 inner join sanpham sp on sp.pk_Seq=dhsp.sanpham_fk      \n"+
						"			where dh.loaihoadon=0 and  dh.trangthai not in ( 1 , 3, 5 ) "+condition+" \n"+
						"			group by dh.NPP_FK,dh.khachhang_fk  \n"+
				
						"		 )as p pivot ( sum(doanhso) for NhomHang_FK IN ('+@cols+',[111]) )as t \n"+
						"		inner join NHAPHANPHOI npp on npp.PK_SEQ=t.NPP_FK \n"+
						"		inner join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK \n"+
						"		inner join vung kv on kv.pk_seq=tt.vung_fk \n"+
						" ) as data "+ 
						"  group by data.NPP_FK,data.kvTEN,data.ttTEN,data.nppMa,data.nppTEN ";
				}
			 query+="	' 	exec (@sql) ";
					System.out.println("[BcDoanhSoNhomHangNpp]"+query);
				
	 					this.queryHd=query;
	 					this.hoadonRs=this.db.get(query);
	 		   }
	 		 	setTotal_Query("");
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
		String query = "";				
		this.totalRs=this.db.get(query);
	 	
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
	public String getAction()
  {
  	return action;
  }
	public void setAction(String timkiem)
  {
  	this.action = timkiem;
  }
	
	String[] spNhomId,spNhomTen;
	public String[] getSpNhomId()
  {
  	return spNhomId;
  }
	public void setSpNhomId(String[] spNhomId)
  {
  	this.spNhomId = spNhomId;
  }
	public String[] getSpNhomTen()
  {
  	return spNhomTen;
  }
	public void setSpNhomTen(String[] spNhomTen)
  {
  	this.spNhomTen = spNhomTen;
  }
	
	
}
