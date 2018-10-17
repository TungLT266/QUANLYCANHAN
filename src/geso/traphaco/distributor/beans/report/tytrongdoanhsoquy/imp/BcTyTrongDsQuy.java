package geso.traphaco.distributor.beans.report.tytrongdoanhsoquy.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.report.tytrongdoanhsoquy.IBcTyTrongDsQuy;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;

public class BcTyTrongDsQuy extends Phan_Trang implements IBcTyTrongDsQuy, Serializable
{
	public BcTyTrongDsQuy()
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
								"select npp.TEN as nppTEN,kh.maFAST as khMA,kh.TEN as khTEN,kh.DIACHI as khDIACHI,kh.MASOTHUE,ds.*,hd.ThuVe   \n "+      
								"   from   \n "+      
								"   (   \n "+      
								"   	select KHACHHANG_FK,NPP_FK,SUM([100002]) as DsNhomxanh ,sum([100003]) as Dsnhomhh,SUM([100004]) as dsnhomkhac   \n "+      
								"   	from   \n "+      
								"   	(   \n "+      
								"   			select (dhsp.soluong*dhsp.dongia*(1+dhsp.vat/100)) as doanhso,hd.KHACHHANG_FK,hd.NPP_FK,nsp.NSP_FK   \n "+      
								"   			from HOADON hd   \n "+      
								"   				inner join HOADON_SP dhsp on dhsp.hoadon_fk=hd.pk_seq   \n "+      
								"   				inner join sanpham sp on sp.pk_Seq=dhsp.sanpham_fk   \n "+      
								"   				inner join nhomsanpham_sanpham nsp on nsp.sp_fk=dhsp.sanpham_fk   \n "+      
								"   			where hd.trangthai not in ( 1 , 3, 5 ) and month(hd.ngayxuatHD) >= '"+thangBATDAU+"' and month(ngayxuatHD) <= '"+thangKETTHUC+"'   \n "+      
								"   			and year(hd.ngayxuatHD) = '"+namHIENTAI+"' and isnull(hd.loaihoadon, '0') = '0' AND hd.NPP_FK='"+this.nppId+"'   \n "+      
								"   		and hd.KHACHHANG_FK in   \n "+      
								"   			(   \n "+      
								"   				select PK_SEQ   \n "+      
								"   				FROM KHACHHANG   \n "+      
								"   				WHERE NPP_FK=HD.NPP_FK AND   \n "+      
								"   				(   \n "+      
								"   					select COUNT(*)   \n "+      
								"   					from DUYETBANDUNGGIA b   \n "+      
								"   					WHERE   \n "+      
								"   					DATEPART(month,HD.NGAYXUATHD)=b.THANG and DATEPART(YEAR,HD.NGAYXUATHD)=b.NAM and b.NPP_FK=HD.NPP_FK   \n "+      
								"   				)=0   \n "+      
								"   				   \n "+      
								"   				UNION ALL   \n "+      
								"      \n "+      
								"   				select PK_SEQ   \n "+      
								"   				FROM KHACHHANG   \n "+      
								"   				WHERE KHACHHANG.PK_SEQ NOT IN   \n "+      
								"   				(   \n "+      
								"   					select khachhang_fk   \n "+      
								"   					from DUYETBANDUNGGIA_KHACHHANG a   \n "+      
								"   						inner join DUYETBANDUNGGIA b on b.PK_SEQ=a.duyet_fk   \n "+      
								"   					where b.NPP_FK=hd.npp_fk and DATEPART(month,hd.NGAYXUATHD)=b.THANG and DATEPART(YEAR,hd.NGAYXUATHD)=b.NAM AND b.khongtinhdoanhso=0   \n "+      
								"   				)   \n "+      
								"   				AND   \n "+      
								"   				(   \n "+      
								"   					select COUNT(*)   \n "+      
								"   					from DUYETBANDUNGGIA b   \n "+      
								"   					WHERE DATEPART(month,hd.NGAYXUATHD)=b.THANG and DATEPART(YEAR,hd.NGAYXUATHD)=b.NAM AND b.khongtinhdoanhso=0 and b.NPP_FK=hd.NPP_FK   \n "+      
								"   				)>0   \n "+      
								"   				union all   \n "+      
								"   				select a.khachhang_fk   \n "+      
								"   				from DUYETBANDUNGGIA_KHACHHANG a   \n "+      
								"   					inner join DUYETBANDUNGGIA b on b.PK_SEQ=a.duyet_fk   \n "+      
								"   				where b.NPP_FK=hd.npp_fk   \n "+      
								"   				and DATEPART(month,hd.NGAYXUATHD)=b.THANG and DATEPART(YEAR,hd.NGAYXUATHD)=b.NAM   \n "+      
								"   			)   \n "+      
								"   	)as p   \n "+      
								"   	pivot ( sum(doanhso) for nsp_fk in ([100002],[100003],[100004]) )as ds   \n "+      
								"   	group by KHACHHANG_FK,NPP_FK   \n "+      
								"   )as ds   \n "+      
								"   FULL OUTER join   \n "+      
								"   (   \n "+      
								"   		select sum(hd.tongtienavat) as ThuVe,hd.KHACHHANG_FK,hd.NPP_FK   \n "+      
								"   		from HOADON hd   \n "+      
								"   		where hd.trangthai not in ( 1 , 3, 5 ) and month(hd.ngayxuatHD) >= '"+thangBATDAU+"' and month(ngayxuatHD) <= '"+thangKETTHUC+"'   \n "+      
								"   			and year(hd.ngayxuatHD) = '"+namHIENTAI+"' and isnull(hd.loaihoadon, '0') = '0' AND hd.NPP_FK='"+this.nppId+"'   \n "+   
								"   		AND HD.KHACHHANG_FK IN   \n "+      
								"   	(   \n "+      
								"   		select PK_SEQ   \n "+      
								"   		FROM KHACHHANG   \n "+      
								"   		WHERE NPP_FK=HD.NPP_FK AND   \n "+      
								"   			(   \n "+      
								"   				select COUNT(*)   \n "+      
								"   				from DUYETBANDUNGGIA b   \n "+      
								"   				WHERE DATEPART(month,HD.NGAYXUATHD)=b.THANG and DATEPART(YEAR,HD.NGAYXUATHD)=b.NAM and b.NPP_FK=HD.NPP_FK    \n "+      
								"   			)=0   \n "+      
								"   		UNION ALL   \n "+      
								"   		select PK_SEQ   \n "+      
								"   		FROM KHACHHANG   \n "+      
								"   		WHERE KHACHHANG.PK_SEQ NOT IN   \n "+      
								"   		(   \n "+      
								"   			select khachhang_fk   \n "+      
								"   			from DUYETBANDUNGGIA_KHACHHANG a   \n "+      
								"   			inner join DUYETBANDUNGGIA b on b.PK_SEQ=a.duyet_fk   \n "+      
								"   			where b.NPP_FK=hd.npp_fk   \n "+      
								"   			and DATEPART(month,hd.NGAYXUATHD)=b.THANG and DATEPART(YEAR,hd.NGAYXUATHD)=b.NAM AND b.khongtinhtienthuve=0   \n "+      
								"   		)   \n "+      
								"   		AND   \n "+      
								"   		(   \n "+      
								"   			select COUNT(*)   \n "+      
								"   			from DUYETBANDUNGGIA b   \n "+      
								"   			WHERE   \n "+      
								"   			DATEPART(month,hd.NGAYXUATHD)=b.THANG and DATEPART(YEAR,hd.NGAYXUATHD)=b.NAM AND b.khongtinhtienthuve=0   \n "+      
								"   			and b.NPP_FK=hd.NPP_FK   \n "+      
								"   		)>0   \n "+      
								"   			union all   \n "+      
								"   		select a.khachhang_fk   \n "+      
								"   		from DUYETBANDUNGGIA_KHACHHANG a   \n "+      
								"   			inner join DUYETBANDUNGGIA b on b.PK_SEQ=a.duyet_fk   \n "+      
								"   		where b.NPP_FK=hd.npp_fk   \n "+      
								"   		and DATEPART(month,hd.NGAYXUATHD)=b.THANG and DATEPART(YEAR,hd.NGAYXUATHD)=b.NAM   \n "+      
								"   	)   \n "+      
								"   	group by hd.KHACHHANG_FK,hd.NPP_FK   \n "+      
								"   )as hd on hd.KHACHHANG_FK=ds.KHACHHANG_FK and hd.NPP_FK=ds.NPP_FK   \n "+      
								"   inner join KHACHHANG kh on kh.PK_SEQ=ISNULL(hd.khachhang_fk,ds.khachhang_fk)    \n "+      
								"   inner join NHAPHANPHOI npp on npp.PK_SEQ=ISNULL(hd.NPP_FK,ds.NPP_FK)    \n ";
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
	   query="";
	   if(this.action.length()>0)
	   {
				query="";
					
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
