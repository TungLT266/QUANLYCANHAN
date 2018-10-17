package geso.traphaco.distributor.beans.hoadontaichinh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.hoadontaichinh.IBCChietKhauQuy;
import geso.traphaco.distributor.db.sql.dbutils;

public class BCChietKhauQuy implements IBCChietKhauQuy
{ 
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String ptVAT;
	String query;
	String sohoadontu;
	String sohoadonden;

	
	String nppTen;
	String msg;
	String activeTab;
	
	ResultSet nppRs;
	ResultSet hoadonRs;
	ResultSet EtcRs;
	ResultSet OtcRs;
	ResultSet kmRs;
	
	String khTen;
	ResultSet khRs;
	String nppId;
	
	String nvbhId;
	ResultSet nvbhRs;
	String maFAST;
	
	String sohoadon;
	String loaidonhang;
	
	dbutils db;
	
	public BCChietKhauQuy()
	{
		this.tungay = "";
		this.denngay = this.getDateTime().split("-")[0];
		
		/*this.tungay = "";
		this.denngay = "";*/
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "0";
	    this.khTen= "";
	    this.nppId="";
	    this.sohoadon="";
	    this.ptVAT = "";
	    
	    this.activeTab="0";
	    
	    this.nvbhId = "";
	    this.maFAST = "";
	    
	    this.sohoadontu = "";
	    this.sohoadonden = "";
	    this.tdvId="";
	    this.type="1";
		this.db = new dbutils();
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
	
	Utility Ult = new Utility();
	private void getNppInfo()
	{		
		String sql = "";
		try
		{

			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					this.phanloai = rs.getString("phanloai");
					loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");

					if (rs.getString("phanloai").equals("1")||( this.phanloai.equals("2")   && loaiNv.equals("3")   )  )
					{
						this.nppId = Ult.getIdNhapp(this.userId);
						this.nppTen = Ult.getTenNhaPP();
					}
					rs.close();
				}
			}
		} catch (Exception er)
		{

		}
	
	
		
	}
	
	String phanloai="";
	String loaiNv="";
	
	
	public String getPhanloai()
  {
  	return phanloai;
  }

	public void setPhanloai(String phanloai)
  {
  	this.phanloai = phanloai;
  }

	public String getLoaiNv()
  {
  	return loaiNv;
  }

	public void setLoaiNv(String loaiNv)
  {
  	this.loaiNv = loaiNv;
  }

	public void init()
	{
		this.getNppInfo();
		
		String query = "";
       
		if(this.tungay.trim().length() > 0 && this.denngay.trim().length() > 0  )
		{			
			int quyHIENTAI = Integer.parseInt(this.tungay);
			int namHIENTAI = Integer.parseInt(this.denngay);
			
			int thangBATDAU = 0;
			int thangKETTHUC = 0;
			
			int thangBATDAU_QUYTIEPTHEO = 0;
			int thangKETTHUC_QUYTIEPTHEO = 0;
		
			
			int nam_QUYTIEPTHEO = Integer.parseInt(this.denngay);
			
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
			
			
			String condition = "";
			if(this.tdvId.trim().length() > 0)
			{
				condition += " and DS.KHACHHANG_FK in ( select khachhang_fk from KHACHHANG_TUYENBH " +
						"								where TBH_FK in ( select pk_seq from TUYENBANHANG where DDKD_FK = '" + tdvId + "' ) ) ";
			}
			
			if(this.type.equals("0"))
		condition += " and DS.KHACHHANG_FK in ( select khachhang_fk from DUYETBANDUNGGIA a inner join DUYETBANDUNGGIA_KHACHHANG_HUONGQUY b on a.pk_seq = b.duyet_fk where a.NPP_FK = '" + this.nppId + "' and a.thang = '" + thangKETTHUC + "' and a.nam = '" + namHIENTAI + "' and a.trangthai = '1'   ) ";

			
			
			String tableNAME = "NHOMSANPHAM";
		
			
			query= "select b.VUNG_FK from NHAPHANPHOI a inner join TINHTHANH b on b.PK_SEQ=a.TINHTHANH_FK  where a.PK_SEQ='"+this.nppId+"' ";
			ResultSet  rs=db.get(query);
			try
	    {
	      while(rs.next())
	      {
	      	if(rs.getString("Vung_fk").equals("100002"))
	      	{
	      		tableNAME = "NHOMSANPHAM_MIENNAM";
	      	}else  if(rs.getString("Vung_fk").equals("100003"))
	      	{
	      		tableNAME = "NHOMSANPHAM_MIENTRUNG";
	      	}
	      }
	    } catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
			
			query=
				
				"	WITH ThuVe_CTE (KhachHang_FK,NPP_FK,THUVE)  "+
				"	AS  "+
				"	(  "+
				"   select thuve.KHACHHANG_FK,thuve.NPP_FK,sum(thuve.toTAL) as ThuVe   \n "+      
			   "   from    \n "+      
			   "   (   \n "+      
			   "   	select hd.KHACHHANG_FK,hd.NPP_FK,SUM(hd.tongtienAVat) as toTAL   \n "+      
			   "   	from    \n "+      
			   "   	(   \n "+      
			   "   		select month( a.ngayxuatHD ) as Thang,year( a.ngayxuatHD ) as Nam,   \n "+      
			   "   		a.KHACHHANG_FK, a.NPP_FK, sum( isnull((a.tongtienavat - isnull(b.SOTIENCANTRU,0)),0)) as tongtienAVat    \n "+      
			   "   		from HOADON a     \n "+      
			   "   		left join     \n "+      
			   "   		(    \n "+      
			   "   			SELECT b.HOADON_FK, SUM(b.SOTIENCANTRU) as SOTIENCANTRU    \n "+      
			   "   			FROM CANTRUCONGNO a inner join CANTRUCONGNO_HOADON b on a.PK_SEQ = b.CANTRUCONGNO_FK    \n "+      
			   "   			WHERE a.TRANGTHAI = 1    \n "+      
			   "   			GROUP BY b.HOADON_FK    \n "+      
			   "   		)  b on a.PK_SEQ = b.HOADON_FK    \n "+      
			   "   		where month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"'    \n "+      
			   "   			and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 )			   \n "+      
			   "   			and a.loaihoadon = '0' and a.NPP_FK = '" + this.nppId + "'    \n "+      
			   "   		group by a.KHACHHANG_FK,a.NPP_FK,month( a.ngayxuatHD ),year( a.ngayxuatHD )   \n "+      
			   "   	)as hd   \n ";
			
			   if(type.equals("0"))
			  	 query+=
			   "   	inner join    \n "+      
			   "   	(   \n "+      
			   "   		select khachhang_fk,a.THANG,a.NAM,a.NPP_FK from DUYETBANDUNGGIA a inner join    \n "+      
			   "   			DUYETBANDUNGGIA_KHACHHANG b on a.pk_seq = b.duyet_fk    \n "+      
			   "   		where a.NPP_FK=" + this.nppId + " and a.THANG>='"+thangBATDAU+"' and a.THANG<='"+thangKETTHUC+"' and a.NAM='"+namHIENTAI+"'   \n "+      
			   "   	)as dg on dg.khachhang_fk=hd.KHACHHANG_FK and hd.Nam=dg.NAM and hd.Thang=dg.THANG   \n ";
			   
			   query+=
			   "   	group by hd.KHACHHANG_FK,hd.NPP_FK   \n ";     
			   
			  		 
			   if(type.equals("0"))
			   query+=
			   "   union all    \n "+      
			   "   	select hd.KHACHHANG_FK,hd.NPP_FK,SUM(hd.tongtienAVat) as toTAL   \n "+      
			   "   	from    \n "+      
			   "   	(   \n "+      
			   "   		select month( a.ngayxuatHD ) as Thang,year( a.ngayxuatHD ) as Nam,   \n "+      
			   "   			a.KHACHHANG_FK, a.NPP_FK, sum( isnull((a.tongtienavat - isnull(b.SOTIENCANTRU,0)),0)) as tongtienAVat    \n "+      
			   "   			from HOADON a     \n "+      
			   "   			left join     \n "+      
			   "   			(    \n "+      
			   "   				SELECT b.HOADON_FK, SUM(b.SOTIENCANTRU) as SOTIENCANTRU    \n "+      
			   "   				FROM CANTRUCONGNO a inner join CANTRUCONGNO_HOADON b on a.PK_SEQ = b.CANTRUCONGNO_FK    \n "+      
			   "   				WHERE a.TRANGTHAI = 1    \n "+      
			   "   				GROUP BY b.HOADON_FK    \n "+      
			   "   			)  b on a.PK_SEQ = b.HOADON_FK    \n "+      
			   "   			where month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"' and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 )			   \n "+      
			   "   				and a.loaihoadon = '0' and a.NPP_FK = '" + this.nppId + "'    \n "+      
			   "   			group by a.KHACHHANG_FK,a.NPP_FK,month( a.ngayxuatHD ),year( a.ngayxuatHD )   \n "+      
			   "   		) as hd    \n "+      
			   "   		where 1=1    \n "+
			   " and   		not exists   \n "+      
			   "   		(   \n "+      
			   "   			select khachhang_fk   \n "+      
			   "   			from DUYETBANDUNGGIA a inner join  DUYETBANDUNGGIA_KHACHHANG b on a.pk_seq = b.duyet_fk    \n "+      
			   "   			where a.NPP_FK=" + this.nppId + " and a.THANG>='"+thangBATDAU+"' and a.THANG<='"+thangKETTHUC+"' and a.NAM='"+namHIENTAI+"'   \n "+      
			   "   			and b.khachhang_fk=hd.KHACHHANG_FK   \n "+      
			   "   			 and a.THANG=hd.Thang and a.NAM=hd.Nam   \n "+      
			   "   		)   \n "+      
			   "   		and     \n "+      
			   "   		(          \n "+      
			   "   			select COUNT(*)    \n "+      
			   "   			from DUYETBANDUNGGIA a     \n "+      
			   "   			where a.NPP_FK=" + this.nppId + " and a.THANG>='"+thangBATDAU+"' and a.THANG<='"+thangKETTHUC+"' and a.NAM='"+namHIENTAI+"'   \n "+      
			   "   			and a.khongtinhtienthuve=0 " +
			   "   			 and a.THANG=hd.Thang and a.NAM=hd.Nam   \n "+       
			   "   		)>0    \n "+
			   "   	group by hd.KHACHHANG_FK,hd.NPP_FK   \n ";    
			   
			   query+=
			   "   )as thuve    \n "+      
			   "   group by thuve.KHACHHANG_FK,thuve.NPP_FK   \n "+
			   " )"; 
					
			query += "select ddkdTEN, khID, maFAST, TEN as khTEN, DIACHI, \n " +
					"	isnull( sum([100002]), 0 ) as DS_XANH, isnull( sum([100003]), 0) as DS_HHBG, isnull( sum([100004]), 0) as DS_KHAC, \n " +
				/*	"	round ( cast ( \n " +
					"		isnull( sum([100002]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )  \n " +
					"				as numeric(18, 1) ) - 0.5 + 0.05, 0 ) as PT_XANH, \n" +
					"	round ( cast ( \n " +
					"		isnull( sum([100003]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )  \n " +
					"			as numeric(18, 1) ) - 0.5 + 0.05, 0 ) as PT_HHBG, \n " +
				*/
					"		isnull( sum([100002]), 0 ) * 100 / ((  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )  \n " +
					"				  as PT_XANH, \n" +
					"		isnull( sum([100003]), 0 ) * 100 /( (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )  \n " +
					"			 as PT_HHBG, \n " +
					"	ISNULL( (    \n "+
					"			select SUM(ThuVe)from ThuVe_CTE a \n "+
					"			where khID=a.KhachHang_Fk \n "+
					"			group by khachhang_fk  \n "+
					"		), 0 ) as tienthuve  \n "+  
					"from \n " +
					"( \n " +
					"	select KH.PK_SEQ as khID, kh.MAFAST, kh.TEN, kh.DIACHI, \n" +
					"			(	select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ    \n " +
					"						inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ   \n " +
					"				where cc.KHACHHANG_FK = KH.pk_seq  \n " +
					"			) as ddkdTEN, \n  " +
					"		TOTAL.NSP_FK, TOTAL.maNHOM, TOTAL.tongGiatri as doanhsoNHOM \n " +
					"	from  \n " +
					"	( \n " +
					"		select DS.KHACHHANG_FK, NSP_FK, maNHOM, sum(DS.toTAL) as tongGiatri   \n " +
					"		from  \n " +
					"		(  \n " +
					"			select a.khachhang_fk, c.NSP_FK, case c.NSP_FK when 100002 then 'CQX5' when 100002 then 'CQB' else 'KHAC' end as maNHOM,  isnull( cast( sum( ( cast( ( soluong * dongia ) as numeric(18, 0) )  ) * ( 1 + b.VAT / 100 ) ) as numeric(18, 0) ), 0)  as toTAL   \n " +
					"			from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk    \n " +
					"					inner join " + tableNAME + "_SANPHAM c on b.sanpham_fk = c.SP_FK   \n " +
					"			where month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"' and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0'    \n " +
					"				and a.npp_fk = '" + this.nppId + "'   \n " ;
					
			if(this.type.equals("0"))		
		query+="				and a.khachhang_fk in (  \n " +
					"										select khachhang_fk from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk   \n " +
					"										where trangthai = '1' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '" + this.nppId + "' 	  \n  " +
					"									  ) \n   " +
					"				and cast( month(a.ngayxuatHD) as varchar(10) ) in   \n " +
					"					  (  \n " +
					"						select distinct thang from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk   \n " +
					"						where trangthai = '1' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '" + this.nppId + "' and db_kh.khachhang_fk = a.khachhang_fk	   \n " +
					"					  )   \n " +
					"			group by a.khachhang_fk, c.NSP_FK  \n " +
					"		union ALL   \n " +
					"			select a.khachhang_fk, c.NSP_FK, case c.NSP_FK when 100002 then 'CQX5' when 100002 then 'CQB' else 'KHAC' end as maNHOM,  isnull( cast( sum( ( cast( ( soluong * dongia ) as numeric(18, 0) )  ) * ( 1 + b.VAT / 100 ) ) as numeric(18, 0) ), 0)  as toTAL   \n " +
					"			from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk    \n " +
					"					inner join " + tableNAME + "_SANPHAM c on b.sanpham_fk = c.SP_FK   \n " +
					"			where month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"' and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0'    \n " +
					"				and a.npp_fk = '" + this.nppId + "'   \n " +
					"				and cast( month(a.ngayxuatHD) as varchar(10) ) not in   \n  " +
					"					  (  \n " +
					"						select distinct thang from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk   \n " +
					"						where trangthai = '1' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '" + this.nppId + "' and db_kh.khachhang_fk = a.khachhang_fk	  \n  " +
					"					  )   \n " +
					"				and cast( month(a.ngayxuatHD) as varchar(10) ) in \n  " +
					"					  (   \n " +
					"						select distinct thang from DUYETBANDUNGGIA db  \n " +
					"						where trangthai = '1' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '" + this.nppId + "' and  khongtinhdoanhso = '0'	   \n " +
					"					  )   \n " ;
					query+=
					"			group by a.khachhang_fk, c.NSP_FK  \n" +
					"		)  \n " +
					"		DS \n " +
					"		where NSP_FK in ( 100002, 100003, 100004  )  \n " + condition +
					"		group by DS.KHACHHANG_FK, NSP_FK, maNHOM  \n " +
					"	)  \n" +
					"	TOTAL inner join KHACHHANG kh on TOTAL.khachhang_fk = kh.pk_seq \n" +
					") \n" +
					"as p pivot ( sum(doanhsoNHOM)  for nsp_fk  in  ( [100002], [100003], [100004] ) ) as t \n" +
					"group by ddkdTEN, khID, maFAST, TEN, DIACHI \n";
						
			System.out.println("---INIT CHIET KHAU QUY: " + query);
			this.hoadonRs = db.get(query);

		}
	}
	public void init2015()
	{
		this.getNppInfo();
		
		String query = "";
       
		if(this.tungay.trim().length() > 0 && this.denngay.trim().length() > 0  )
		{			
			int quyHIENTAI = Integer.parseInt(this.tungay);
			int namHIENTAI = Integer.parseInt(this.denngay);
			
			int thangBATDAU = 0;
			int thangKETTHUC = 0;
			
			int thangBATDAU_QUYTIEPTHEO = 0;
			int thangKETTHUC_QUYTIEPTHEO = 0;
		
			
			int nam_QUYTIEPTHEO = Integer.parseInt(this.denngay);
			
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
			
			
			String condition = "";
			if(this.tdvId.trim().length() > 0)
			{
				condition += " and DS.KHACHHANG_FK in ( select khachhang_fk from KHACHHANG_TUYENBH " +
						"								where TBH_FK in ( select pk_seq from TUYENBANHANG where DDKD_FK = '" + tdvId + "' ) ) ";
			}
			
			if(this.type.equals("0"))
		condition += " and DS.KHACHHANG_FK in ( select khachhang_fk from DUYETBANDUNGGIA a inner join DUYETBANDUNGGIA_KHACHHANG_HUONGQUY b on a.pk_seq = b.duyet_fk where a.NPP_FK = '" + this.nppId + "' and a.thang = '" + thangKETTHUC + "' and a.nam = '" + namHIENTAI + "' and a.trangthai = '1'   ) ";

			
			
			String tableNAME = "NHOMSANPHAM";
		
			
			query= "select b.VUNG_FK from NHAPHANPHOI a inner join TINHTHANH b on b.PK_SEQ=a.TINHTHANH_FK  where a.PK_SEQ='"+this.nppId+"' ";
			ResultSet  rs=db.get(query);
			try
	    {
	      while(rs.next())
	      {
	      	if(rs.getString("Vung_fk").equals("100002"))
	      	{
	      		tableNAME = "NHOMSANPHAM_MIENNAM";
	      	}else  if(rs.getString("Vung_fk").equals("100003"))
	      	{
	      		tableNAME = "NHOMSANPHAM_MIENTRUNG";
	      	}
	      }
	    } catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
			
			query=
				
				"	WITH ThuVe_CTE (KhachHang_FK,NPP_FK,THUVE,thang)  "+
				"	AS  "+
				"	(  "+
				"   select thuve.KHACHHANG_FK,thuve.NPP_FK,sum(thuve.toTAL) as ThuVe,thuve.thang   \n "+      
			   "   from    \n "+      
			   "   (   \n "+      
			   "   	select hd.KHACHHANG_FK,hd.NPP_FK,SUM(hd.tongtienAVat) as toTAL,hd.thang   \n "+      
			   "   	from    \n "+      
			   "   	(   \n "+      
			   "   		select month( a.ngayxuatHD ) as Thang,year( a.ngayxuatHD ) as Nam,   \n "+      
			   "   		a.KHACHHANG_FK, a.NPP_FK, sum( isnull((a.tongtienavat),0)) as tongtienAVat    \n "+      
			   "   		from HOADON a     \n "+      
			   "   		left join     \n "+      
			   "   		(    \n "+      
			   "   			SELECT b.HOADON_FK, SUM(b.SOTIENCANTRU) as SOTIENCANTRU    \n "+      
			   "   			FROM CANTRUCONGNO a inner join CANTRUCONGNO_HOADON b on a.PK_SEQ = b.CANTRUCONGNO_FK    \n "+      
			   "   			WHERE a.TRANGTHAI = 1    \n "+      
			   "   			GROUP BY b.HOADON_FK    \n "+      
			   "   		)  b on a.PK_SEQ = b.HOADON_FK    \n "+      
			   "   		where month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"'    \n "+      
			   "   			and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 )			   \n "+      
			   "   			and a.loaihoadon = '0' and a.NPP_FK = '" + this.nppId + "'    \n "+      
			   "   		group by a.KHACHHANG_FK,a.NPP_FK,month( a.ngayxuatHD ),year( a.ngayxuatHD )   \n "+      
			   "   	)as hd   \n ";
			
			   if(type.equals("0"))
			  	 query+=
			   "   	inner join    \n "+      
			   "   	(   \n "+      
			   "   		select khachhang_fk,a.THANG,a.NAM,a.NPP_FK from DUYETBANDUNGGIA a inner join    \n "+      
			   "   			DUYETBANDUNGGIA_KHACHHANG b on a.pk_seq = b.duyet_fk    \n "+      
			   "   		where a.NPP_FK=" + this.nppId + " and a.THANG>='"+thangBATDAU+"' and a.THANG<='"+thangKETTHUC+"' and a.NAM='"+namHIENTAI+"'   \n "+      
			   "   	)as dg on dg.khachhang_fk=hd.KHACHHANG_FK and hd.Nam=dg.NAM and hd.Thang=dg.THANG   \n ";
			   
			   query+=
			   "   	group by hd.KHACHHANG_FK,hd.NPP_FK,hd.thang   \n ";     
			   
			  		 
			   if(type.equals("0"))
			   query+=
			   "   union all    \n "+      
			   "   	select hd.KHACHHANG_FK,hd.NPP_FK,SUM(hd.tongtienAVat) as toTAL,thang   \n "+      
			   "   	from    \n "+      
			   "   	(   \n "+      
			   "   		select month( a.ngayxuatHD ) as Thang,year( a.ngayxuatHD ) as Nam,   \n "+      
			   "   			a.KHACHHANG_FK, a.NPP_FK, sum( isnull((a.tongtienavat),0)) as tongtienAVat    \n "+      
			   "   			from HOADON a     \n "+      
			   "   			left join     \n "+      
			   "   			(    \n "+      
			   "   				SELECT b.HOADON_FK, SUM(b.SOTIENCANTRU) as SOTIENCANTRU    \n "+      
			   "   				FROM CANTRUCONGNO a inner join CANTRUCONGNO_HOADON b on a.PK_SEQ = b.CANTRUCONGNO_FK    \n "+      
			   "   				WHERE a.TRANGTHAI = 1    \n "+      
			   "   				GROUP BY b.HOADON_FK    \n "+      
			   "   			)  b on a.PK_SEQ = b.HOADON_FK    \n "+      
			   "   			where month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"' and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 )			   \n "+      
			   "   				and a.loaihoadon = '0' and a.NPP_FK = '" + this.nppId + "'    \n "+      
			   "   			group by a.KHACHHANG_FK,a.NPP_FK,month( a.ngayxuatHD ),year( a.ngayxuatHD )   \n "+      
			   "   		) as hd    \n "+      
			   "   		where 1=1    \n "+
			   " and   		not exists   \n "+      
			   "   		(   \n "+      
			   "   			select khachhang_fk   \n "+      
			   "   			from DUYETBANDUNGGIA a inner join  DUYETBANDUNGGIA_KHACHHANG b on a.pk_seq = b.duyet_fk    \n "+      
			   "   			where a.NPP_FK=" + this.nppId + " and a.THANG>='"+thangBATDAU+"' and a.THANG<='"+thangKETTHUC+"' and a.NAM='"+namHIENTAI+"'   \n "+      
			   "   			and b.khachhang_fk=hd.KHACHHANG_FK   \n "+      
			   "   			 and a.THANG=hd.Thang and a.NAM=hd.Nam   \n "+      
			   "   		)   \n "+      
			   "   		and     \n "+      
			   "   		(          \n "+      
			   "   			select COUNT(*)    \n "+      
			   "   			from DUYETBANDUNGGIA a     \n "+      
			   "   			where a.NPP_FK=" + this.nppId + " and a.THANG>='"+thangBATDAU+"' and a.THANG<='"+thangKETTHUC+"' and a.NAM='"+namHIENTAI+"'   \n "+      
			   "   			and a.khongtinhtienthuve=0 " +
			   "   			 and a.THANG=hd.Thang and a.NAM=hd.Nam   \n "+       
			   "   		)>0    \n "+
			   "   	group by hd.KHACHHANG_FK,hd.NPP_FK,thang   \n ";    
			   
			   query+=
			   "   )as thuve    \n "+      
			   "   group by thuve.KHACHHANG_FK,thuve.NPP_FK,thuve.thang   \n "+
			   " )"; 
					
			query += "select ddkdTEN, khID, maFAST, TEN as khTEN, DIACHI,MaHD, \n " +
					"	isnull( sum([100002]), 0 ) as DS_XANH, isnull( sum([100003]), 0) as DS_HHBG, isnull( sum([100004]), 0) as DS_KHAC, \n " +
				/*	"	round ( cast ( \n " +
					"		isnull( sum([100002]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )  \n " +
					"				as numeric(18, 1) ), 2 ) as PT_XANH, \n" +
					"	round ( cast ( \n " +
					"		isnull( sum([100003]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )  \n " +
					"			as numeric(18, 1) ) , 2 ) as PT_HHBG, \n " +*/
					"Cast(round( (isnull( sum([100002]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) )),2,1) as numeric(18,2))	\n"+ 
			 		"as PT_XANH, \n"+
			 		" Cast(round( (isnull( sum([100003]), 0 ) * 100 / (  isnull( sum([100002]), 0 ) + isnull( sum([100003]), 0) + isnull( sum([100004]), 0) ) ),2,1) as numeric(18,2))	 \n"+ 
		 			" as PT_HHBG,\n"+
		 			 "	ISNULL( (    \n "+
					"			select SUM(ThuVe)from ThuVe_CTE a \n "+
					"			where khID=a.KhachHang_Fk \n "+
					"			group by khachhang_fk  \n "+
					"		), 0 ) as tienthuve , \n "+ 
					" ISNULL( (    \n "+
				 	"		select SUM(ThuVe)from ThuVe_CTE a \n "+ 
				 	"		where khID=a.KhachHang_Fk and thang="+thangBATDAU+" \n "+
				 	"		group by khachhang_fk  \n "+
				 	"	), 0 ) as t1, \n "+
				 	"		ISNULL( (    \n "+
				 	"		select SUM(ThuVe)from ThuVe_CTE a  \n "+
				 	"		where khID=a.KhachHang_Fk and thang="+(thangBATDAU+1)+" \n "+
				 	"		group by khachhang_fk  \n "+
				 	"	), 0 ) as t2, \n "+
				 	"		ISNULL( (    \n "+
				 	"		select SUM(ThuVe)from ThuVe_CTE a  \n "+
				 	"		where khID=a.KhachHang_Fk and thang="+thangKETTHUC+" \n "+
				 	"		group by khachhang_fk  \n "+
				 	"	), 0 ) as t3 \n "+
				 	
					"from \n " +
					"( \n " +
					"	select KH.PK_SEQ as khID, kh.MAFAST, kh.TEN, kh.DIACHI,kh.MaHD, \n" +
					"			(	select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ    \n " +
					"						inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ   \n " +
					"				where cc.KHACHHANG_FK = KH.pk_seq  \n " +
					"			) as ddkdTEN, \n  " +
					"		TOTAL.NSP_FK, TOTAL.maNHOM, TOTAL.tongGiatri as doanhsoNHOM \n " +
					"	from  \n " +
					"	( \n " +
					"		select DS.KHACHHANG_FK, NSP_FK, maNHOM, sum(DS.toTAL) as tongGiatri   \n " +
					"		from  \n " +
					"		(  \n " +
					"			select a.khachhang_fk, c.NSP_FK, case c.NSP_FK when 100002 then 'CQX5' when 100002 then 'CQB' else 'KHAC' end as maNHOM,  sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100),0) )  as toTAL   \n " +
					"			from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk    \n " +
					"          inner join khachhang kh on kh.pk_Seq=a.khachhang_Fk                        " +
					"					inner join " + tableNAME + "_SANPHAM c on b.sanpham_fk = c.SP_FK   \n " +
					"			where  a.NgayXuatHD >=isnull(kh.NgayKyHD,'') and  month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"' and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0'    \n " +
					"				and a.npp_fk = '" + this.nppId + "'   \n " ;
			if(this.type.equals("0"))		
		query+="				and a.khachhang_fk in (  \n " +
					"										select khachhang_fk from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk   \n " +
					"										where trangthai = '1' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '" + this.nppId + "' 	  \n  " +
					"									  ) \n   " +
					"				and cast( month(a.ngayxuatHD) as varchar(10) ) in   \n " +
					"					  (  \n " +
					"						select distinct thang from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk   \n " +
					"						where trangthai = '1' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '" + this.nppId + "' and db_kh.khachhang_fk = a.khachhang_fk	   \n " +
					"					  )   \n " +
					"			group by a.khachhang_fk, c.NSP_FK  \n " +
					"		union ALL   \n " +
					"			select a.khachhang_fk, c.NSP_FK, case c.NSP_FK when 100002 then 'CQX5' when 100002 then 'CQB' else 'KHAC' end as maNHOM,  sum( round(  round( b.SoLuong*b.DONGIA,0) *(1+ b.vat/100),0) )   as toTAL   \n " +
					"			from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk    \n " +
					"          inner join khachhang kh on kh.pk_Seq=a.khachhang_Fk                        " +
					"					inner join " + tableNAME + "_SANPHAM c on b.sanpham_fk = c.SP_FK   \n " +
					"			where  a.NgayXuatHD >=isnull(kh.NgayKyHD,'') and month( a.ngayxuatHD ) >= '"+thangBATDAU+"'  and month( a.ngayxuatHD ) <= '"+thangKETTHUC+"' and year( a.ngayxuatHD ) = '"+namHIENTAI+"' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0'    \n " +
					"				and a.npp_fk = '" + this.nppId + "'   \n " +
					"				and cast( month(a.ngayxuatHD) as varchar(10) ) not in   \n  " +
					"					  (  \n " +
					"						select distinct thang from DUYETBANDUNGGIA db inner join DUYETBANDUNGGIA_KHACHHANG db_kh on db.pk_seq = db_kh.duyet_fk   \n " +
					"						where trangthai = '1' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '" + this.nppId + "' and db_kh.khachhang_fk = a.khachhang_fk	  \n  " +
					"					  )   \n " +
					"				and cast( month(a.ngayxuatHD) as varchar(10) ) in \n  " +
					"					  (   \n " +
					"						select distinct thang from DUYETBANDUNGGIA db  \n " +
					"						where trangthai = '1' and loaict = '0' and cast(thang as int) >= '"+thangBATDAU+"' and cast(thang as int) <= '"+thangKETTHUC+"' and nam = '"+namHIENTAI+"' and db.NPP_FK = '" + this.nppId + "' and  khongtinhdoanhso = '0'	)   \n " +
					"					    \n " ;
					query+=
					"			group by a.khachhang_fk, c.NSP_FK  \n" +
					"		)  \n " +
					"		DS \n " +
					"		where NSP_FK in ( 100002, 100003, 100004  )  \n " + condition +
					"		group by DS.KHACHHANG_FK, NSP_FK, maNHOM  \n " +
					"	)  \n" +
					"	TOTAL inner join KHACHHANG kh on TOTAL.khachhang_fk = kh.pk_seq \n" +
					") \n" +
					"as p pivot ( sum(doanhsoNHOM)  for nsp_fk  in  ( [100002], [100003], [100004] ) ) as t \n" +
					"group by ddkdTEN, khID, maFAST, TEN, DIACHI,MaHD \n";
						
			System.out.println("---INIT CHIET KHAU QUY: " + query);
			this.hoadonRs = db.get(query);

		}
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public ResultSet getHoadonRs() 
	{
		return this.hoadonRs;
	}

	public void setHoadonRs(ResultSet hdRs) 
	{
		this.hoadonRs = hdRs;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getNppTen() {
		
		return this.nppTen;
	}

	
	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getKhTen() {
		return this.khTen;
	}

	
	public void setKhTen(String KhTen) {
		this.khTen = KhTen;
		
	}

	
	public ResultSet getKhRs() {
		return this.khRs;
	}

	
	public void setKhRs(ResultSet KhRs) {
		this.khRs = KhRs;
		
	}
	
	public String getSoHoaDon()
	{
		return this.sohoadon;
	}
	public void setSoHoaDon(String sohoadon)
	{
		this.sohoadon =sohoadon;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public String getPtVat() {

		return this.ptVAT;
	}


	public void setPtVat(String ptVat) {
		
		this.ptVAT = ptVat;
	}

	
	public String getNvbhId() {
		
		return this.nvbhId;
	}

	
	public void setNvbhId(String nvbhId) {
		
		this.nvbhId = nvbhId;
	}

	
	public ResultSet getNvbhRs() {
		
		return this.nvbhRs;
	}

	
	public void setNvbhRs(ResultSet nvbhRs) {
		
		this.nvbhRs = nvbhRs;
	}

	
	public String getMaFast() {
		
		return this.maFAST;
	}

	
	public void setMaFast(String maFAST) {
		
		this.maFAST = maFAST;
	}


	public void createRs() 
	{
		this.getNppInfo();
		
		System.out.println("--- LAY NVBH: " + "select  PK_SEQ, TEN AS TEN from DaiDienKinhDoanh where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ");
		this.nvbhRs = db.get("select  PK_SEQ, TEN AS TEN from DaiDienKinhDoanh where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ");
		
		this.khRs = db.get("select  PK_SEQ, MAFAST + ', ' + TEN AS TEN from KHACHHANG where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ");
		
		
		String query="select pk_Seq,ten,ma from nhaphanphoi where iskhachhang=0 and trangthai=1 ";

		if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
		{
			query+= " and pk_Seq in " + Ult.quyen_npp(userId)+"";
		}
		this.nppRs = this.db.get(query);
		
		query="select pk_seq,ten from daidienkinhdoanh where trangthai=1 ";
		if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
		{
			query+= " and pk_Seq in " + Ult.Quyen_Ddkd(userId)+"";
		}
		if(this.nppId.length()>0)
		{
			query+=" and pk_Seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk='"+this.nppId+"' )" ;
		}
		
		this.tdvRs=this.db.get(query);
		
		
	}

	
	public String getFormatDate(String date) 
	{
		String[] arr = date.split("-");
		
		return arr[2] + "-" + arr[1] + "-" + arr[0];
	}


	public String getActiveTab() {
	
		return this.activeTab;
	}

	
	public void setActiveTab(String active) {
		this.activeTab= active ;
		
	}

	@Override
	public void setQuery(String searchQuery) {
	 this.query = searchQuery;
		
	}


	public void searchQuery_ETC(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.EtcRs= db.get(sql);
		System.out.println("[Danh sach ETC] :" + sql);
	
		
	}
	

	public void searchQuery_OTC(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.OtcRs= db.get(sql);
		System.out.println("[Danh sach OTC] :" + sql);
		
	}

	public void searchQuery_KM(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.kmRs= db.get(sql);
		System.out.println("[Danh sach KM] :" + sql);
		
	}


	public ResultSet getETCRs() {
	
		return this.EtcRs;
	}


	public void setETCRS(ResultSet ETCRs) {
		this.EtcRs= ETCRs;
		
	}


	public ResultSet getOTCRs() {
		
		return this.OtcRs;
	}

	public void setOTCRS(ResultSet OTCRs) {
		this.OtcRs = OTCRs;
	}


	public ResultSet getKMRs() {

		return this.kmRs;
	}


	public void setKMRS(ResultSet KMRs) {
		this.kmRs= KMRs;
		
	}
	String tdvId;
	ResultSet tdvRs;

	public String getTdvId()
  {
  	return tdvId;
  }

	public void setTdvId(String tdvId)
  {
  	this.tdvId = tdvId;
  }

	public ResultSet getTdvRs()
  {
  	return tdvRs;
  }

	public void setTdvRs(ResultSet tdvRs)
  {
  	this.tdvRs = tdvRs;
  }
	String type;
	public String getType()
  {
  	return type;
  }

	public void setType(String type)
  {
  	this.type = type;
  }

}
