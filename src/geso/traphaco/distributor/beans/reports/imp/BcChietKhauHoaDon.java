package geso.traphaco.distributor.beans.reports.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.reports.IBcChietKhauHoaDon;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BcChietKhauHoaDon extends Phan_Trang implements IBcChietKhauHoaDon, Serializable
{
	public BcChietKhauHoaDon()
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
		this.nppId = util.getIdNhapp(this.userId);
		String query="select pk_Seq,ma,ten from sanpham where trangthai=1  ";
		this.spRs=this.db.get(query);
		
		query="select pk_seq,manhanvien,ten from DaiDienKinhDoanh a where a.pk_seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk='"+this.nppId+"') ";
		
		this.ddkdRs = this.db.get(query);
		
		if(this.nppId.length() > 0|| this.ddkdId.length() > 0)
		{
			
			query="select pk_seq,isnull(mafast,'') +' - ' + ten + ' - ' + isnull(diachi,'') as Ten from khachhang where 1=1 ";
						
			if(this.nppId.length()>0)
			query+=" and npp_fk='"+this.nppId+"' ";
			
			
			if(this.ddkdId.length()>0)
				query+=" and pk_Seq in (select khachhang_fk from khachhang_tuyenbh where tbh_Fk in (select pk_Seq from TuyenBanHang where ddkd_Fk in ("+this.ddkdId+"))) ";
			
			
			System.out.println("___"+query);
			this.khRs= this.db.get(query);
		}	
		
		query="select pk_Seq,ten,DIENGIAI from KENHBANHANG where TRANGTHAI=1 ";
		this.kbhRs = this.db.get(query);	
		
		
		query="select PK_SEQ,TEN from tinhthanh "
				+ "where pk_seq in ( select Distinct TINHTHANH_FK from KHACHHANG where npp_fk ='" + this.nppId + "' ) ";
		if(vungId.length()>0)
			query+=" and vung_fk='"+vungId+"'";
		query+= " order by ten ";
		this.ttRs= this.db.get(query);
		
		query="select pk_seq ,ten,VUNG_FK from khuvuc  where 1=1 ";
		this.kvRs=this.db.get(query);
		
		query="select pk_seq,ten from vung where pk_seq in ( select vung_fk from KhuvUC where pk_Seq in  " +
				" ( select khuvuc_Fk from NhapHANphoi where khuvuc_Fk is not null and  pk_Seq ='" + this.nppId + "'  ) )";		
		this.vungRs=this.db.get(query);
		
		
		query="select pk_seq,ma,diachi,ten from nhaphanphoi where pk_seq ='" + this.nppId + "'";
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
		
		String query="";
		String condition="";
		
		if(tuNgay.length()>0)
		{
			condition+=" and a.NgayXuatHD>='"+this.tuNgay+"' ";
		}
		if(denNgay.length()>0)
		{
			condition+=" and a.NgayXuatHD <='"+this.denNgay+"' ";
		}
		if(nppId.length()>0)
		{
			condition+=" and a.npp_fk ='"+this.nppId+"' ";
		}
		if(khId.length()>0)
		{
			condition+=" and a.khachhang_fk ='"+this.khId+"' ";
		}
		if(ddkdId.length()>0)
		{
			condition+=
				" and a.khachhang_fk in (  select cc.khachhang_fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ \n"+    
				"								inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ " +
				"  where aa.pk_seq="+this.ddkdId+"   ) ";
		}
	   query="";
	   if(this.action.length()>0)
	   {
				query=
				"		select  \n"+
				"		(	select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ \n"+    
				"								inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ    \n"+
				"						where cc.KHACHHANG_FK = hd.KHACHHANG_FK and bb.NPP_FK=hd.NPP_FK \n"+
				"		) as ddkdTEN,hd.maFAST,hd.MaHD,hd.khTEN,hd.khDiaChi,hd.NGAYXUATHD,hd.SOHOADON,hd.DoanhThu,ck.CQB5,ck.CQX5 \n"+
				"	from \n"+
				"	( \n"+
				"		select a.tongtienavat as DoanhThu,a.PK_SEQ as hoadon_fk,a.NPP_FK,a.KHACHHANG_FK, \n"+
				"			a.NGAYXUATHD,a.SOHOADON,a.KYHIEU,b.maFAST,b.MaHD,b.DIACHI as khDiaChi,b.TEN as khTEN  \n"+
				"		from HOADON a inner join KHACHHANG b on b.PK_SEQ=a.KHACHHANG_FK  \n"+
				"		where 1=1 "+condition+" \n"+
				"			and a.TRANGTHAI not in (1,3,5) and a.LOAIHOADON=0 \n"+
				"	)as hd   \n"+
				"	left join  "+ 
				"	(  "+
				"		select hoadon_fk,CQB5,CQX5  "+
				"		from  "+
				"		(  "+
				"			select b.hoadon_fk,b.diengiai,sum(round(b.chietkhau*(1+b.thueVAT/100),0)) as ck  "+ 
				"			from HOADON a inner join HOADON_CHIETKHAU b on b.hoadon_fk=a.PK_SEQ  "+
				"		where 1=1 "+condition+" \n"+
				"				and a.TRANGTHAI not in (1,3,5) and a.LOAIHOADON=0 and b.tichluyQUY=1  "+
				"			group by b.hoadon_fk,b.diengiai	  "+
				"		)as p PIVOT ( 		 sum(ck) 	 FOR diengiai IN 	([CQB5],[CQX5] )) AS t  "+
				"	)as ck on ck.hoadon_fk=hd.hoadon_fk  ";										
				this.queryHd= query;
				setTotal_Query(query);
				
				System.out.println(":::::::"+query);
	   }
	   this.hoadonRs=super.createSplittingData(super.getItems(), super.getSplittings(), " ddkdTEN,khTen desc", query);
	   
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
	   if(this.action.length()>0)
	   {
				query=
						 "   select  SUM(CQB5)as CQB5,SUM(CQX5)as CQX5,SUM(DOANHTHU)as DOANHTHU   \n "+      
						 "   from   \n "+      
						 "   (  "+searchquery+" ) as HD   \n ";
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
