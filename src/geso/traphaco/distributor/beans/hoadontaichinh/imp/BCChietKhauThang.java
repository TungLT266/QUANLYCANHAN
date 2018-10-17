package geso.traphaco.distributor.beans.hoadontaichinh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.hoadontaichinh.IBCChietKhauThang;
import geso.traphaco.distributor.db.sql.dbutils;

public class BCChietKhauThang implements IBCChietKhauThang
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
	
	public BCChietKhauThang()
	{
		this.tungay = Integer.toString(Integer.parseInt(this.getDateTime().split("-")[1]));
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
	
	
	
	String phanloai="";
	String loaiNv="";
	
	
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
	
	public void init()
	{
		getNppInfo();
		
		String query = "";
		
		if(this.tungay.trim().length() > 0 && this.denngay.trim().length() > 0  )
		{			
			if(this.nppId==null)
				this.nppId="1";
			int thangHIENTAI = Integer.parseInt(this.tungay);
			int namHIENTAI = Integer.parseInt(this.denngay);
			
			int thangSAU =thangHIENTAI+1;
			int namSAU =namHIENTAI;
			if(thangSAU==13)
			{
				thangSAU=1;
				namSAU=namHIENTAI+1;
			}
			if( thangHIENTAI == 6 && namHIENTAI ==2014 )
			{
				query = "select dsthangHIENTAI.*,  " +
						"	case loaikhachhang when 0 then 0 else isnull(TL.tongCHIETKHAU, 0) end as banBUON, " +
						"	case loaikhachhang when 0 then isnull(TL.tongCHIETKHAU, 0) else 0 end as banLE,0 as ck_Thang,0 as CK_QUY,0 AS CK_NGAY " +
						"from " +
						"( " +
						"	select khachhang_fk, a.loaikhachhang, maFAST, ten, tongthucthu as tongtiensauVAT  " +
						"	from TICHLUYQUY_DAUKY a inner join KHACHHANG b on a.khachhang_fk = b.pk_seq " +
						" 	WHERE A.THANG=6 AND A.NAM="+namHIENTAI+"  " +
						") " +
						"dsthangHIENTAI left join   " +
						"(  " +
						"	 select khachhang_fk, sum(thanhtoan) as tongCHIETKHAU   " +
						"	 from DUYETTRAKHUYENMAI_DONHANG  " +
						"	 where tichluyQUY = '0'   " +
						"				and donhang_fk in ( select ddh_fk from HOADON_DDH " +
						"									where hoadon_fk in ( select pk_seq from HOADON where  trangthai not in ( 1 , 3, 5 ) and month(ngayxuatHD) = '" + thangSAU + "' and year(ngayxuatHD) <= '" +namSAU + "' and isnull(loaihoadon, '0') = '0' " ;
				
				if(this.nppId.length()>0)
				{
					query+=" and npp_fk='"+nppId+"'";  
				}
				if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
				{
					query+= " and npp_fk in " + Ult.quyen_npp(userId)+"";
				}
				
				query+=
						
						"  )  ) " +
								"	 group by khachhang_fk  " +
								" )  " +
								" TL on dsthangHIENTAI.khachhang_fk = TL.khachhang_fk  ";
				
				System.out.println("___T6"+query);
			}else 
			if(namHIENTAI==2014 && thangHIENTAI==07)
			{
				
				String condition ="";
				
				if(this.nppId.length()>0)
				{
					condition +=" and  a.npp_fk = '" + this.nppId + "' ";  
				}
				if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
				{
					condition += " and a.npp_fk in " + Ult.quyen_npp(userId)+"";
				}
				
				query=
						" 	 select dsthangHIENTAI.* ,dsthangHIENTAI.tongtiensauVAT/100*"+   
						" 	   ("+   
						" 	    	select sum(a.chietkhau_khle) as CKLE"+   
						" 	    	from"+   
						" 	    	TIEUCHITHUONGTL_TIEUCHI a inner join"+   
						" 	    	TIEUCHITHUONGTL b on b.PK_SEQ=a.thuongtl_fk"+   
						" 	    	where b.pk_seq in"+   
						" 	    	( select thuongtl_fk from TIEUCHITHUONGTL_LOAINPP where loainpp = ( select loaiNPP from NHAPHANPHOI where pk_seq = dsthangHIENTAI.NPP_FK) )and"+   
						" 	    	b.NAM='"+namHIENTAI+"' and b.trangthai = '1' and b.loaict = '0' and LOAIKHACHHANG in (0,3) "+   
						" 	    	AND ISNULL(dsthangHIENTAI.PT_CHIETKHAU,0)=0"+   
						" 	    	"+   
						" 	    	) as BanLE,"+   
						" 	    	dsthangHIENTAI.tongtiensauVAT/100*"+   
						" 	    ("+   
						" 	    	select sum(a.chietkhau) as CKLE"+   
						" 	    	from TIEUCHITHUONGTL_TIEUCHI a inner join TIEUCHITHUONGTL b on b.PK_SEQ=a.thuongtl_fk"+   
						" 	    	where b.pk_seq in ( select thuongtl_fk from TIEUCHITHUONGTL_LOAINPP where loainpp ="+   
						" 	    	( select loaiNPP from NHAPHANPHOI where pk_seq = dsthangHIENTAI.NPP_FK) )and b.NAM='"+namHIENTAI+"' and b.trangthai = '1' and b.loaict = '0' and LOAIKHACHHANG in (1,2)"+   
						" 	    "+   
						" 	    AND ISNULL(dsthangHIENTAI.PT_CHIETKHAU,0)=0"+   
						" 	    "+   
						" 	    	 )as BANBUON,ISNULL(CK_Ngay.CK_Ngay,0) AS CK_Ngay, ISNULL(TL_TG.tongCHIETKHAU,0) AS CK_THANG, ISNULL( TL_QUY.tongCHIETKHAU,0)  AS CK_QUY"+   
						" 	    from"+   
						" 	    ("+   
						" 	    	"+   
						" 	    select a.npp_fk,a.khachhang_fk, c.loaikhachhang, d.maFAST, d.ten ,SUM( a.tongtienavat) as tongtiensauVAT,d.PT_CHIETKHAU"+   
						" 	    from		"+   
						" 	    ("+   
						" 	    	select SUM(AVAT) as tongtienavat  ,SUM(BVAT) AS BVAT,HOADON_FK,KHACHHANG_FK,NPP_FK"+   
						" 	    	from "+   
						" 	    	("+   
						" 	    "+   
						" 	    				select	sum(c.soluong) as soluong,   "+   
						" 	    					cast(avg(c.DONGIA) as numeric(18,4)) as dongia, sum(c.soluong * c.DONGIA) as BVAT,  "+   
						" 	    					sum(c.soluong * c.DONGIA*c.VAT/100) as VAT, sum(c.soluong * c.DONGIA*(1+c.vat/100)) as AVAT  ,a.KHACHHANG_FK,c.HOADON_FK,a.NPP_FK"+   
						" 	    				from HOADON a  "+   
						" 	    					inner join HOADON_SP c on a.pk_seq = c.hoadon_fk  "+   
						" 	 inner join SANPHAM d on c.sanpham_fk = d.pk_seq   "+   
						" 	    					inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   "+   
						" 	    				where c.SoLuong>0 and a.trangthai not in ( 1 , 3, 5 ) and isnull(a.loaihoadon, '0') = '0'  "+   
						" 	    					and month(a.ngayxuatHD) = '"+thangHIENTAI+"' and year(a.ngayxuatHD) = '"+namHIENTAI+"'  "+condition+"  "+   
						" 	    				group by a.KHACHHANG_FK		,c.HOADON_FK,a.NPP_FK"+   
						" 	    			union all  "+   
						" 	 select 0 as SoLuong,0 as DonGia,(-1)* sum(ck.chietkhau) as BVAT, (-1)* sum(ck.chietkhau*ck.THUEVAT/100) AS VAT,  "+   
						" 	    				(-1)* sum(ck.chietkhau*(1+ck.THUEVAT/100)) as AVAT  ,KHACHHANG_FK,HOADON_FK,NPP_FK"+   
						" 	    			from "+   
						" 	    			(   "+   
						" 	    				select b.HOADON_FK,c.KHACHHANG_FK, sum(a.chietkhau) as chietkhau, thueVAT  ,c.NPP_FK "+   
						" 	    				from DONHANG_SANPHAM a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk "+   
						" 	    					inner join HOADON c on c.PK_SEQ=b.HOADON_FK   "+   
						" 	  				group by c.KHACHHANG_FK,b.HOADON_FK,THUEVAT,c.NPP_FK"+   
						" 	   				union all  "+   
						" 	 				select b.hoadon_fk, c.KHACHHANG_FK,sum( a.thanhtoan / ( 1 + ptTHUE / 100 ) ) as chietkhau, ptTHUE as thueVAT ,c.NPP_FK  "+   
						" 	  				from DUYETTRAKHUYENMAI_DONHANG a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk   "+   
						" 	  							inner join HOADON c on c.PK_SEQ=b.HOADON_FK   "+   
						" 	    				group by b.hoadon_fk, a.diengiai, ptTHUE  ,c.KHACHHANG_FK ,c.NPP_FK "+   
						" 	    				union all  "+   
						" 	    				select b.hoadon_fk,c.KHACHHANG_FK , sum(a.chietkhau) as chietkhau, ptVAT as thueVAT   ,c.NPP_FK"+   
						" 	    				from DONHANG_CHIETKHAUBOSUNG a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk  "+   
						" 	    						inner join HOADON c on c.PK_SEQ=b.HOADON_FK    "+   
						" 	    				where a.chietkhau != 0   "+   
						" 	    				group by b.hoadon_fk, a.ptVAT ,c.KHACHHANG_FK,c.NPP_FK"+   
						" 	    				) ck "+   
						" 	    			"+   
						" 	    			WHERE HOADON_FK IN   "+   
						" 	    			(   "+   
						" 	    				SELECT PK_SEQ FROM hoadon a   "+   
						" 	    				where  trangthai not in ( 1 , 3, 5 ) and isnull(loaihoadon, '0') = '0'  "+   
						" 	    				and month(a.ngayxuatHD) = '"+thangHIENTAI+"'     and year(a.ngayxuatHD) = '"+namHIENTAI+"' "+condition+" "+
						" 	    			)"+   
						" 	    				group by KHACHHANG_FK,HOADON_FK,NPP_FK"+   
						" 	    			"+   
						" 	    	)as total"+   
						" 	    	GROUP BY KHACHHANG_FK,HOADON_FK,NPP_FK"+   
						" 	    )as a "+   
						" 	    inner join HOADON_DDH b on a.HOADON_FK = b.hoadon_fk"+   
						" 	    inner join DONHANG c on b.ddh_fk = c.pk_seq"+   
						" 	    inner join KHACHHANG d on a.khachhang_fk = d.pk_seq"+   
						" 	    "+   
						" 	    GROUP BY  a.npp_fk,a.khachhang_fk, c.loaikhachhang, d.maFAST, d.ten,d.PT_CHIETKHAU"+   
						" 	    )as"+   
						" 	    dsthangHIENTAI left join"+   
						" 	    ("+   
						" 	    	select khachhang_fk, sum(thanhtoan) as tongCHIETKHAU"+   
						" 	    	from DUYETTRAKHUYENMAI_DONHANG"+   
						" 	    	where tichluyQUY = '0'"+   
						" 	    	and donhang_fk in"+   
						" 	    	("+   
						" 	    		select ddh_fk from HOADON_DDH"+   
						" 	    		where hoadon_fk in"+   
						" 	    		("+   
						" 	    			select pk_seq from HOADON a where trangthai not in ( 1 , 3, 5 )"+   
						" 	    			and month(ngayxuatHD) = '"+thangHIENTAI+"' and year(ngayxuatHD) = '"+namHIENTAI+"' and isnull(loaihoadon, '0') = '0' "+condition+"  "+   
						" 	    		)"+   
						" 	    	)"+   
						" 	    	group by khachhang_fk"+   
						" 	    )"+   
						" 	    TL_TG on dsthangHIENTAI.khachhang_fk = TL_TG.khachhang_fk"+   
						" 	    left join "+   
						" 	    ("+   
						" 	    	select khachhang_fk, sum(thanhtoan) as tongCHIETKHAU"+   
						" 	    	from DUYETTRAKHUYENMAI_DONHANG"+   
						" 	    	where tichluyQUY = '1'"+   
						" 	    	and donhang_fk in"+   
						" 	    ("+   
						" 	    	select ddh_fk from HOADON_DDH"+   
						" 	    	where hoadon_fk in"+   
						" 	    	("+   
						" 	    		select pk_seq from HOADON  a where trangthai not in ( 1 , 3, 5 )"+   
						" 	    		and month(ngayxuatHD) = '"+thangHIENTAI+"' and year(ngayxuatHD) = '"+namHIENTAI+"' and isnull(loaihoadon, '0') = '0' "+condition+"  "+   
						" 	    		and trangthai not in (1,3,5)"+   
						" 	    	)"+   
						" 	    )"+   
						" 	    group by khachhang_fk"+   
						" 	    )"+   
						" 	    TL_QUY on dsthangHIENTAI.khachhang_fk = TL_QUY.khachhang_fk"+   
						" 	    left join "+   
						" 	    ("+   
						" 	    	select  sum(b.CHIETKHAU*(1+b.THUEVAT/100)) as CK_Ngay,KHACHHANG_FK "+   
						" 	    	from  DONHANG a inner join  DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ"+   
						" 	    	where b.DONHANG_FK"+   
						" 	    	in"+   
						" 	    		("+   
						" 	    			select ddh_fk from HOADON_DDH"+   
						" 	    			where hoadon_fk in"+   
						" 	    			("+   
						" 	    				select pk_seq from HOADON a where trangthai not in ( 1 , 3, 5 )"+   
						" 	    				and month(ngayxuatHD) = '"+thangHIENTAI+"' and year(ngayxuatHD) = '"+namHIENTAI+"' and isnull(loaihoadon, '0') = '0'  "+condition+"   "+   
						" 	    				and trangthai not in (1,3,5)"+   
						" 	    			)"+   
						" 	    		)"+   
						" 	    			"+   
						" 	    	group by a.KHACHHANG_FK"+   
						" 	    	"+   
						" 	    )as CK_NGAY on CK_Ngay.KHACHHANG_FK=dsthangHIENTAI.KHACHHANG_FK ";
				
				
				
				
				System.out.println("___T7"+query);
				
			}else
			{
				
				String condition ="";
				
				if(this.nppId.length()>0)
				{
					condition +=" and  a.npp_fk = '" + this.nppId + "' ";  
				}
				if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
				{
					condition += " and a.npp_fk in " + Ult.quyen_npp(userId)+"";
				}
				if(this.tdvId.length()>0)
				{
					condition +=
								 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
								 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
								 "          where aa.pk_Seq='"+this.tdvId+"'      \n "+      
								 "   	) ";
				}
				String condition_DG="";
				
				if(this.getType().equals("1"))
					condition_DG +=" inner join  (select  KHACHHANG_fk,aa.npp_fk from DUYETBANDUNGGIA aa  inner join DUYETBANDUNGGIA_KHACHHANG bb on bb.duyet_fk=aa.PK_SEQ  where aa.THANG='"+thangHIENTAI+"' and aa.NAM='"+namHIENTAI+"'  ) as dg on dg.khachhang_fk=a.KHACHHANG_FK and dg.npp_fk=a.npp_fk " ;
				
				 query=
						 		 "	select   \n "+      
								 "   	( select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
								 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ where cc.KHACHHANG_FK=dsthangHIENTAI.KHACHHANG_FK   \n "+      
								 "   	) as tdvTEN ,   \n "+      
								 "   	dsthangHIENTAI.* ,dsthangHIENTAI.tongtiensauvat/100*   \n "+      
								 "   	(   \n "+      
								 "   		select sum(a.chietkhau_khle) as ckle from tieuchithuongtl_tieuchi a inner join tieuchithuongtl b on b.pk_seq=a.thuongtl_fk   \n "+      
								 "   		where b.pk_seq in   \n "+      
								 "   			( select thuongtl_fk from tieuchithuongtl_loainpp where loainpp = ( select loainpp from nhaphanphoi where pk_seq = dsthangHIENTAI.npp_fk) )   \n "+      
								 "   				and b.nam='"+namHIENTAI+"' and b.trangthai = '1' and b.loaict = '0' and loaikhachhang in (0,3)   \n "+      
								 "   		and isnull(dsthangHIENTAI.pt_chietkhau,0)=0   \n "+      
								 "   	) as banle,   \n "+      
								 "   	dsthangHIENTAI.tongtiensauvat/100*   \n "+      
								 "   	(   \n "+      
								 "   		select sum(a.chietkhau) as ckle   \n "+      
								 "   		from tieuchithuongtl_tieuchi a inner join tieuchithuongtl b on b.pk_seq=a.thuongtl_fk   \n "+      
								 "   		where b.pk_seq in ( select thuongtl_fk from tieuchithuongtl_loainpp where loainpp =   \n "+      
								 "   		( select loainpp from nhaphanphoi where pk_seq = dsthangHIENTAI.npp_fk) )and b.nam='"+namHIENTAI+"'    \n "+      
								 "   			and b.trangthai = '1' and b.loaict = '0' and loaikhachhang in (1,2)   \n "+      
								 "   			and isnull(dsthangHIENTAI.pt_chietkhau,0)=0   \n "+      
								 "   	)as banbuon   \n "+      
								 "   	,isnull(ck_ngay.ck_ngay,0) as ck_ngay, isnull(tl_tg.tongchietkhau,0) as ck_THANG, isnull( tl_quy.tongchietkhau,0) as ck_quy   \n "+      
								 "   from   \n "+      
								 "   (   \n "+      
								 "   	select a.npp_fk,a.khachhang_fk ,sum( a.tongtienavat) as tongtiensauvat,d.pt_chietkhau,d.ten,d.mafast, d.DIACHI,loaikhachhang   \n "+      
								 "   	from   \n "+      
								 "   	(   \n "+      
								 "   		select sum(avat) as tongtienavat ,sum(bvat) as bvat,hoadon_fk,khachhang_fk,npp_fk,loaikhachhang   \n "+      
								 "   		from   \n "+      
								 "   		(   \n "+      
								 "   			select sum(c.soluong) as soluong,   \n "+      
								 "   			cast(avg(c.DONGIA) as numeric(18,4)) as dongia, sum(c.soluong * c.DONGIA) as bvat,   \n "+      
								 "   			sum(c.soluong * c.DONGIA*c.vat/100) as vat, sum(c.soluong * c.DONGIA*(1+c.vat/100)) as avat ,a.khachhang_fk,c.hoadon_fk,a.npp_fk,   \n "+      
								 "   				(   \n "+      
								 "   					select top(1) bb.loaikhachhang   \n "+      
								 "   					from hoadon_ddh aa inner join donhang bb on bb.PK_SEQ=aa.DDH_FK   \n "+      
								 "   					where aa.HOADON_FK=c.HOADON_FK   \n "+      
								 "   				)as loaikhachhang   \n "+      
								 "   			from hoadon a   \n "+      
								 "   				inner join hoadon_sp c on a.pk_seq = c.hoadon_fk   \n "+      
								 "   				inner join sanpham d on c.sanpham_fk = d.pk_seq   \n "+      
								 "   				inner join donvidoluong e on d.dvdl_fk = e.pk_seq   \n "+      
								 "   			where c.soluong>0 and a.trangthai not in ( 1 , 3, 5 ) and isnull(a.loaihoadon, '0') = '0'   \n "+      
								 "   			and month(a.ngayxuathd) ="+thangHIENTAI+" and year(a.ngayxuathd) = '"+namHIENTAI+"' "+condition+"   \n "+      
								 "   			group by a.khachhang_fk ,c.hoadon_fk,a.npp_fk   \n "+      
								 "   		union all   \n "+      
								 "   		select 0 as soluong,0 as dongia,(-1)*sum((b.chietkhau)) as bvat ,(-1)*sum((b.chietkhau*( b.thuevat/100))) as vat,   \n "+      
								 "   			(-1)*sum((b.chietkhau*( 1+ b.thuevat/100))) as avat,a.khachhang_fk,b.hoadon_fk,a.npp_fk   \n "+      
								 "   			,(   \n "+      
								 "   				select top(1) bb.loaikhachhang   \n "+      
								 "   				from hoadon_ddh aa inner join donhang bb on bb.PK_SEQ=aa.DDH_FK   \n "+      
								 "   				where aa.HOADON_FK=b.HOADON_FK   \n "+      
								 "   			)as loaikhachhang   \n "+      
								 "   		from hoadon a inner join HOADON_CHIETKHAU b on a.pk_seq=b.hoadon_fk   \n "+      
								 "   		where trangthai not in ( 1 , 3, 5 )   \n "+      
								 "   			and month(ngayxuathd) = '"+thangHIENTAI+"' and year(ngayxuathd) = '"+namHIENTAI+"' and isnull(loaihoadon, '0') = '0'    \n "+      
								 "   			"+condition+" and b.HIENTHI=1   \n "+      
								 "   		group by a.khachhang_fk,b.hoadon_fk,a.npp_fk   \n "+      
								 "   		union all   \n "+      
								 "   		select 0 as soluong,0 as dongia,(-1)*sum(ROUND(b.SOTIENCANTRU,0)) as bvat , (-1)*sum(ROUND( ROUND( b.SOTIENCANTRU,0)*( 0/100) ,0)) as vat,   \n "+      
								 "   			(-1)*sum( round( round (b.SOTIENCANTRU,0) *( 1+ 0/100),0)) as avat,a.khachhang_fk,b.hoadon_fk,a.npp_fk,   \n "+      
								 "   			(   \n "+      
								 "   				select top(1) bb.loaikhachhang from hoadon_ddh aa inner join donhang bb on bb.PK_SEQ=aa.DDH_FK where aa.HOADON_FK=b.HOADON_FK   \n "+      
								 "   			)as loaikhachhang   \n "+      
								 "   		from hoadon a inner join CANTRUCONGNO_HOADON b on a.pk_seq=b.hoadon_fk inner join CANTRUCONGNO c on c.pk_seq=b.CANTRUCONGNO_FK   \n "+      
								 "   		where  c.trangthai=1 and  a.trangthai not in ( 1 , 3, 5 )   \n "+      
								 "   			and month(ngayxuathd) = '"+thangHIENTAI+"' and year(ngayxuathd) = '"+namHIENTAI+"' and isnull(loaihoadon, '0') = '0' "+condition+"   \n "+      
								 "   		group by a.khachhang_fk,b.hoadon_fk,a.npp_fk   \n "+      
								 "   		)as total   \n "+      
								 "   		group by khachhang_fk,hoadon_fk,npp_fk,loaikhachhang   \n "+      
								 "   	)as a  "+condition_DG+"    \n "+      
								 "   		inner join khachhang d on a.khachhang_fk = d.pk_seq   \n "+      
								 "   		group by a.npp_fk,a.khachhang_fk, d.mafast, d.ten,d.pt_chietkhau,d.DIACHI,loaikhachhang   \n "+      
								 "   	)as dsthangHIENTAI   \n "+      
								 "   left join   \n "+      
								 "   (   \n "+      
								 "   	select a.khachhang_fk, sum(a.chietkhau*(1+b.thueVAT/100)) as tongchietkhau   \n "+      
								 "   	from HOADON a inner join HOADON_CHIETKHAU b on a.pk_seq=b.hoadon_fk   \n "+      
								 "   	where diengiai in ('CT5','CT10')   \n "+      
								 "   	and a.trangthai not in ( 1 , 3, 5 )   \n "+      
								 "   		and month(ngayxuathd) = '"+thangHIENTAI+"' and year(ngayxuathd) = '"+namHIENTAI+"' and isnull(loaihoadon, '0') = '0' "+condition+"     \n "+      
								 "   	group by a.khachhang_fk   \n "+      
								 "   ) tl_tg on dsthangHIENTAI.khachhang_fk = tl_tg.khachhang_fk   \n "+      
								 "    left join    \n "+      
								 "   (    \n "+      
								 "   	select a.khachhang_fk, sum(a.chietkhau*(1+b.thueVAT/100)) as tongchietkhau   \n "+      
								 "   	from HOADON a inner join HOADON_CHIETKHAU b on a.pk_seq=b.hoadon_fk   \n "+      
								 "   	where tichluyquy=1 and a.trangthai not in ( 1 , 3, 5 )   \n "+      
								 "   		and month(ngayxuathd) = '"+thangHIENTAI+"' and year(ngayxuathd) = '"+namHIENTAI+"' and isnull(loaihoadon, '0') = '0' "+condition+"     \n "+      
								 "   	group by a.khachhang_fk   \n "+      
								 "         \n "+      
								 "      ) tl_quy   \n "+      
								 "       on dsthangHIENTAI.khachhang_fk = tl_quy.khachhang_fk   \n "+      
								 "   left join    \n "+      
								 "   (    \n "+      
								 "   	select a.khachhang_fk, sum(a.chietkhau*(1+thuevat/100)) as ck_ngay   \n "+      
								 "   	from HOADON a inner join HOADON_CHIETKHAU b on a.pk_seq=b.hoadon_fk   \n "+      
								 "   	where diengiai in ('CN5','CN10')   \n "+      
								 "   		and a.trangthai not in ( 1 , 3, 5 )   \n "+      
								 "   		and month(ngayxuathd) = '"+thangHIENTAI+"' and year(ngayxuathd) = '"+namHIENTAI+"' and isnull(loaihoadon, '0') = '0' "+condition+"     \n "+      
								 "   	group by a.khachhang_fk   \n "+      
								 "   )as ck_ngay on ck_ngay.khachhang_fk=dsthangHIENTAI.khachhang_fk ";
				 
			}
			if(namHIENTAI>=2015)
			{
				
				String tableNAME = "NHOMSANPHAM";
				query= "select b.VUNG_FK from NHAPHANPHOI a inner join TINHTHANH b on b.PK_SEQ=a.TINHTHANH_FK  where a.PK_SEQ='"+this.nppId+"' ";
				ResultSet rs=db.get(query);
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
				
				String condition ="";
				
				if(this.nppId.length()>0)
				{
					condition +=" and  a.npp_fk = '" + this.nppId + "' ";  
				}
				if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
				{
					condition += " and a.npp_fk in " + Ult.quyen_npp(userId)+"";
				}
				if(this.tdvId.length()>0)
				{
					condition +=
								 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
								 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
								 "          where aa.pk_Seq='"+this.tdvId+"'      \n "+      
								 "   	) ";
				}
				String condition_DG="";
				
				
				if(this.getType().equals("1"))
					condition_DG +=" inner join  (select  KHACHHANG_fk,aa.npp_fk from DUYETBANDUNGGIA aa  inner join DUYETBANDUNGGIA_KHACHHANG bb on bb.duyet_fk=aa.PK_SEQ  where aa.THANG='"+thangHIENTAI+"' and aa.NAM='"+namHIENTAI+"'  ) as dg on dg.khachhang_fk=a.KHACHHANG_FK and dg.npp_fk=a.npp_fk " ;
				
				query="select \n"+   
								"		( select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n"+ 
									"			inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ where cc.KHACHHANG_FK=dsthangHIENTAI.KHACHHANG_FK   \n"+ 
									"	) as tdvTEN ,khTEN,khMa,khDiaChi,MaHD,dsthangHIENTAI.DoanhThu,ds.DoanhSo as TongDS,dsBOGA.DoanhSo as dsBOGA,\n"+ 
									"		(   \n"+ 
									"			select sum(a.chietkhau) as ckle   \n"+ 
									"			from tieuchithuongtl_tieuchi a inner join tieuchithuongtl b on b.pk_seq=a.thuongtl_fk   \n"+ 
									"			where b.pk_seq in ( select thuongtl_fk from tieuchithuongtl_loainpp where loainpp =   \n"+ 
									"			( select loainpp from nhaphanphoi where pk_seq = dsthangHIENTAI.npp_fk) )and b.nam='2015'    \n"+ 
										"			and b.trangthai = '1' and b.loaict = '0'   and ISNULL(ptChietKhau,0)=0 \n"+ 
									"		)as ptChietKhau \n"+ 
									"	from   \n"+ 
									"	(   \n"+ 
									"		select a.npp_fk,a.khachhang_fk ,sum( a.tongtienavat) as DoanhThu,b.TEN as khTEN,b.maFAST as khMa,b.DIACHI as khDiaChi,b.MaHD,b.pt_chietkhau as		ptChietKhau \n"+ 
									"		from HOADON a \n"+ 
									"		inner join KHACHHANG b on b.PK_SEQ=a.KHACHHANG_FK "+condition_DG+" \n"+ 
									"		where a.NPP_FK="+this.nppId+" and  \n"+ 
									"			month(ngayxuathd) = '"+thangHIENTAI+"' and year(ngayxuathd) = '"+namHIENTAI+"' and isnull(loaihoadon, '0') = '0'  and  a.npp_fk = '"+this.nppId+"' and a.trangthai not in (1,3,5) "+condition+" \n"+       
									"		group by a.NPP_FK,a.KHACHHANG_FK,b.TEN,b.maFAST,b.DIACHI,b.MaHD,pt_ChietKhau \n"+ 
									"	)as dsthangHIENTAI   \n"+ 
									"	left join  \n"+ 
									"	(   \n"+ 
									"		select SUM( ROUND(ROUND( b.soluong*b.dongia,0) * (1+b.vat/100 ),0)) as DoanhSo,a.KHACHHANG_FK  \n"+ 
									"		from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ \n"+ 
									"		where a.trangthai not in ( 1 , 3, 5 )  and month(ngayxuathd) = '"+thangHIENTAI+"' and year(ngayxuathd) = '"+namHIENTAI+"' and isnull(loaihoadon, '0') = '0'  "+condition+" \n"+ 
									"			and  a.npp_fk = '"+this.nppId+"'      \n"+ 
									"		group by a.KHACHHANG_FK	 \n"+ 
									"	)as ds on ds.KHACHHANG_FK=dsthangHIENTAI.KHACHHANG_FK \n"+ 
									"	left join  \n"+ 
									"	( \n"+ 
									"		select SUM( ROUND(ROUND( b.soluong*b.dongia,0) * (1+b.vat/100 ),0)) as DoanhSo,a.KHACHHANG_FK  \n"+ 
									"		from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ \n"+ 
									"			inner join "+tableNAME+"_SANPHAM c on c.SP_FK=b.SANPHAM_FK \n"+ 
									"		where a.trangthai not in ( 1 , 3, 5 )  and month(ngayxuathd) = '"+thangHIENTAI+"' and year(ngayxuathd) = '"+namHIENTAI+"' and isnull(loaihoadon, '0') = '0' "+condition+"  \n"+ 
									"			and  a.npp_fk = '"+this.nppId+"' and c.NSP_FK=100003 \n"+ 
									"		group by a.KHACHHANG_FK	\n"+ 
									"	)as dsBOGA on dsBOGA.KHACHHANG_FK=dsthangHIENTAI.KHACHHANG_FK";
								
									
						 		
				 
			}
		}
		System.out.println("___INIT___ 2015"+query);
		this.hoadonRs = db.get(query);
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
	
	String tdvId,type;
	public String getType()
  {
  	return type;
  }

	public void setType(String type)
  {
  	this.type = type;
  }



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
	private int CompareDATE(String _date1, String _date2)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	//Date date1 = sdf.parse("2014-10-01");
	    	//Date date2 = sdf.parse("2014-10-01");
	    	
	    	Date date1 = sdf.parse(_date1);
	    	Date date2 = sdf.parse(_date2);
	
	    	//System.out.println(sdf.format(date1));
	    	//System.out.println(sdf.format(date2));
	
	    	return date1.compareTo(date2);
		}
		catch (Exception e) {
			return 0;
		}

	}
	private int Songaytrongthang(int month, int year) 
    {
        int ngay = 0;
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                {
                    ngay = 31;
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                {
                    ngay = 30;
                    break;
                }
            case 2:
                {
                    if (year % 4 == 0)
                        ngay = 29;
                    else
                        ngay = 28;
                    break;
                }
        }

        return ngay;
    }
	public String GetTichLuyThangCu(String nppId, String ngay)
    {
        String query = "";
        //B1. Áp tích luy tháng

        String ngaynhap = ngay;
        String today = ngay;
        String NgayThang = ngaynhap.substring(0, 7);
        String[] date = ngaynhap.split("-");
        int thangTRUOC = Integer.parseInt(date[1]);
        int namTRUOC = Integer.parseInt(date[0]);
        if (thangTRUOC == 1)
        {
            thangTRUOC = 12;
            namTRUOC = namTRUOC - 1;
        }
        else
        {
            thangTRUOC = thangTRUOC - 1;
        }

        int songayTRONGTHANG = this.Songaytrongthang(thangTRUOC, namTRUOC);
        String dauthang = namTRUOC + "-" + (thangTRUOC < 10 ? "0" + thangTRUOC : thangTRUOC+"") + "-01";
        String cuoithang = namTRUOC + "-" + (thangTRUOC < 10 ? "0" + thangTRUOC : thangTRUOC+"") + "-" + (songayTRONGTHANG < 10 ? "0" + songayTRONGTHANG : songayTRONGTHANG+"");


        double tienCONGNO_CONLAI = 0;
        int ngay_2015_02_01 = this.CompareDATE(ngaynhap, "2015-02-01");
        System.out.println("______ngay nhap"+ngay_2015_02_01);
        String sql_HHBG = "	(0) as DsHHBG ";
        if (ngay_2015_02_01 >= 0)
        {
            sql_HHBG =
                    "		isnull(	(	select sum(ROUND( round(b.SOLUONG*b.DONGIA,0) *(1+b.VAT/100.0),0))    " +
                    "	from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ  " +
                    "			inner join v_NhomSanPham c on c.sp_fk=b.SANPHAM_FK  " +
                    "			inner join NHAPHANPHOI d on d.PK_SEQ=a.NPP_FK  " +
                    "			inner join TINHTHANH e on e.PK_SEQ=d.TINHTHANH_FK and e.VUNG_FK=c.vung_Fk  " +
                    "	where a.LOAIHOADON=0 and a.NGAYXUATHD>='" + dauthang + "' and c.nsp_fk=100003 and a.trangthai not in (1,3,5)  " +
                    "		and a.NGAYXUATHD<='" + cuoithang + "' and a.KHACHHANG_FK=kh.pk_seq and d.PK_SEQ='" + nppId + "' ),0)as DsHHBG ";
        }
        String sql_CANTRU = "isnull(b.sotiencantru, 0)";
        if (ngay_2015_02_01 >= 0)
        {
            sql_CANTRU = "0";
        }

        query = "select kh.maFAST,kh.TEN ,kh.pk_seq," + sql_HHBG + ",count(*) as soDONG, isnull( PT_CHIETKHAU, 0) as PT_CHIETKHAU, isnull(kh.thanhtoan, 0) as thanhtoan, ( select loaiNPP from NHAPHANPHOI where pk_seq = '" + nppId + "' ) as loaiNPP,  " +
            "\n	ISNULL( ( select sum(ROUND( round(b.SOLUONG*b.DONGIA,0) *(1+b.VAT/100.0),0))  as toTAL  " +
            "\n			  from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk " +
            "\n			  where a.ngayxuatHD >= '" + dauthang + "' and a.ngayxuatHD <= '" + cuoithang + "' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0' and a.khachhang_fk = kh.pk_seq ) ,0 ) as tongDSThangtruoc,  " +
            "\n	ISNULL( (	select sum(tongtien)  " +
            "\n			from " +
            "\n			( " +
            "\n				select sum(tongtienAVAT) - sum(" + sql_CANTRU + ") as tongtien " +
            "\n				from HOADON a left join CANTRUCONGNO_HOADON b on a.pk_seq = b.hoadon_fk  " +
            "\n				where a.ngayxuatHD >= '" + dauthang + "' and a.ngayxuatHD <= '" + cuoithang + "' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0' and a.khachhang_fk = kh.pk_seq " +
            "\n			) " +
            "\n			total ) , 0 ) as tongTHUVEThangtruoc ,  " +
            // "\n	ISNULL( ( select count(*) from DONHANG_SANPHAM where donhang_fk = '" + dhId + "' and thueVAT = '5'  ), 0 ) as exits5Pt, " +
            "\n	ISNULL( ( select top(1) loaikhachhang from DONHANG where khachhang_fk = kh.pk_seq and trangthai != 2 and ngaynhap >= '" + dauthang + "' and ngaynhap <= '" + cuoithang + "' ), 0 ) as xuatkhau, " +
            "\n	ISNULL( ( select sum(thanhtien) from DONHANG_CHIETKHAUBOSUNG where maloai like N'%CT%'   and donhang_fk in ( select pk_seq from DONHANG where ngaynhap >= '" + dauthang + "' and ngaynhap <= '" + cuoithang + "' and trangthai != '2' and khachhang_fk = kh.pk_seq  ) ), 0 ) as chietkhau_bosung,  " +
            "\n	ISNULL( ( select sum( tiencothue + bosung ) from DUYETTRAKHUYENMAI_DUNO where khachhang_fk = kh.pk_seq and tichluyQUY = '0'  ), 0 ) " +
            "\n	-  ISNULL( ( select sum( thanhtoan ) from DUYETTRAKHUYENMAI_DUNO_DONHANG_DATRA where khachhang_fk = kh.pk_seq and tichluyQUY = '0'   ), 0 ) as duno_dauky  " +
            "\n ,(	select COUNT(*) as DaDuyet from DUYETBANDUNGGIA_KHACHHANG where duyet_fk in  ( select pk_seq from DUYETBANDUNGGIA where trangthai = '1'  " +
            "\n																			and loaict = '0' and nam = '" + namTRUOC + "' and thang = '" + thangTRUOC + "' and NPP_FK = '" + nppId + "' ) and khachhang_fk=kh.pk_seq ) as DaDuyet " +
            "\n from KHACHHANG kh " +
            "\n where kh.npp_fk="+nppId+"  and kh.KhongKyHopDong = '0' " +
                "	and ( kh.pk_seq in ( select khachhang_fk from DUYETBANDUNGGIA_KHACHHANG where duyet_fk in ( select pk_seq from DUYETBANDUNGGIA where trangthai = '1' and loaict = '0' and nam = '" + namTRUOC + "' and thang = '" + thangTRUOC + "' and NPP_FK = '" + nppId + "' ) ) " +
                        "	   or (  ROUND (	ISNULL( ( select sum( tiencothue + bosung ) from DUYETTRAKHUYENMAI_DUNO where khachhang_fk = kh.pk_seq and tichluyQUY = '0' ), 0 ) " +
                        "						- ISNULL( ( select sum( thanhtoan ) from DUYETTRAKHUYENMAI_DUNO_DONHANG_DATRA where khachhang_fk = kh.pk_seq and tichluyQUY = '0'   ), 0 ) , 0 ) != 0  ) )			 ";

        if (ngay_2015_02_01 >= 0)
        {
            query += "\n AND	 (select conlai from  [ufn_CongNo](" + nppId + ",kh.pk_seq,'" + ngaynhap + "')) <=0 ";

            query +=
                    " AND	ISNULL( (	select sum(tongtien)  " +
                    "			from " +
                    "			( " +
                    "				select sum(tongtienAVAT) - sum(" + sql_CANTRU + ") as tongtien " +
                    "				from HOADON a left join CANTRUCONGNO_HOADON b on a.pk_seq = b.hoadon_fk  " +
                    "				where a.ngayxuatHD >= '" + dauthang + "' and a.ngayxuatHD <= '" + cuoithang + "' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0' and a.khachhang_fk = kh.pk_seq " +
                    "			) " +
                    "			total ) , 0 ) > ( select isnull(HanMucDoanhThu,0) from NhaPhanPhoi where pk_Seq='" + nppId + "' ) ";


        }
        query += "\n group by kh.xuatkhau, kh.thanhtoan, PT_CHIETKHAU,kh.pk_seq,kh.maFAST,kh.TEN ";

      System.out.println("___query"+query);
        return query;
    }
	public String getngayCUOITHANG(String ngaygiaodich) 
	{
        String[] arr = ngaygiaodich.split("-");
        String ngaycuoithang = "";
        
        switch ( Integer.parseInt(arr[1]) ) {
	        case 1:
	        case 3:
	        case 5:
	        case 7:
	        case 8:
	        case 10:
	        case 12:
	        	ngaycuoithang    = "31";
	            break;
	        case 4:
	        case 6:
	        case 9:
	        case 11:
	        	ngaycuoithang    = "30";
	            break;
	        case 2:
	            if( Integer.parseInt(arr[0]) % 100 != 0 && Integer.parseInt(arr[0]) % 4 == 0 ) {
	            	ngaycuoithang    = "29";
	            } else {
	            	ngaycuoithang    = "28";
	            }
	            break;
	        default: ngaycuoithang    = "28";
	    } 

        return ngaycuoithang;
  
	}
	
	public double condulai(int sodong1,int daduyet1,double tongDSThangtruoc1,double tongTHUVEThangtruoc1,String loaiNPP1,String xuatkhau1,String thanhtoan1,String PT_CHIETKHAU1,String duno_dauky1,Double DsHHBG1,String ngaynhap1,String khid1)
	{
		 
		  String ngaynhap = ngaynhap1;
	        String today = ngaynhap1;
	        String[] date = ngaynhap.split("-");
	        int thangTRUOC = Integer.parseInt(date[1]);
	        int namTRUOC = Integer.parseInt(date[0]);
	        if (thangTRUOC == 1)
	        {
	            thangTRUOC = 12;
	            namTRUOC = namTRUOC - 1;
	        }
	        else
	        {
	            thangTRUOC = thangTRUOC - 1;
	        }

	        int songayTRONGTHANG = this.Songaytrongthang(thangTRUOC, namTRUOC);
	        String dauthang = namTRUOC + "-" + (thangTRUOC < 10 ? "0" + thangTRUOC : thangTRUOC+"") + "-01";
	        String cuoithang = namTRUOC + "-" + (thangTRUOC < 10 ? "0" + thangTRUOC : thangTRUOC+"") + "-" + (songayTRONGTHANG < 10 ? "0" + songayTRONGTHANG : songayTRONGTHANG+"");

	        String NgayThang = ngaynhap.substring(0, 7);
	        
	        boolean daDUYET = false;
	        double tongDSThangtruoc = 0;
	        double tongTHUVEThangtruoc = 0;
	        String loaiNPP = "";
	        String cotLAY = "chietkhau";
	        String tiLE = "1.05";
	        String thanhTOAN = "0";
	        int exit5PT = 0;
	        double pt_chietkhau = 0;
	        String query="";
	        double duno_dauky = 0;
	        double chietkhau_bosung = 0;

	        double dsHHBG = 0;
	        int daDuyet = 0;
	      
	            if (sodong1 > 0)
	            {
	                daDuyet = daduyet1;
	                daDUYET = true;
	                tongDSThangtruoc = tongDSThangtruoc1;
	                if (daDuyet > 0)
	                    tongTHUVEThangtruoc = tongTHUVEThangtruoc1;


	                loaiNPP =loaiNPP1;
	                if (xuatkhau1.equals("0"))
	                    cotLAY = "chietkhau_khle";
	                else
	                    cotLAY = "chietkhau";

	                thanhTOAN = thanhtoan1;

	                if (!loaiNPP.equals("4") && !loaiNPP.equals("5"))
	                    tiLE = "1.05";
	                else
	                    tiLE = "1";
	                //System.out.println("TI LE: " + tiLE + "  -- COT LAY: " + cotLAY + "  -- TONG TIEN TRUOC DO: " +  tongDSThangtruoc);
	                tongTHUVEThangtruoc = tongTHUVEThangtruoc / Double.parseDouble(tiLE);
	                pt_chietkhau = Double.parseDouble(PT_CHIETKHAU1);

	                //DA BAO GOM VAT
	                duno_dauky = Double.parseDouble(duno_dauky1);
	                //chietkhau_bosung =  double.Parse(rsCHECK.Rows[0]["chietkhau_bosung"].ToString());
	                chietkhau_bosung = 0;

	                dsHHBG = DsHHBG1;
	            }

	        
	        /**
	             * 4. Chi?t kh?u tháng: Ràng thêm di?u ki?n n?u Doanh s? HH-BG >70% 
	             * thì không du?c hu?ng CK tháng. Ð?i v?i Ch? Hapu, Qu?n 10, Vinh di?u ki?n này là >40%
	             **/
	        int soDong = 0;
	        int ngay_2015_02_01 = this.CompareDATE(ngaynhap1, "2015-02-01");
	        if (ngay_2015_02_01 >= 0 && tongDSThangtruoc > 0)
	        {
	            query =
	                    "	select COUNT(*) as SoDong from MucDat " +
	                    "	where (" + dsHHBG + "/cast(" + tongDSThangtruoc + " as float) )*100>=TuMuc and (" + dsHHBG + "/cast(" + tongDSThangtruoc + " as float) )*100<=DenMuc  " +
	                    "	and npp_fk='" + nppId + "'";
	            ResultSet rs=this.db.get(query);
	            
	           try {
				if(rs.next())
				    soDong = rs.getInt("sodong");
				  if (soDong <= 0)
		                daDUYET = false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	            
	          
	        }
	        double ketqua = 0;
	        double tienCONGNO_CONLAI = 0;
	        if (daDUYET && (tongDSThangtruoc > 0) && (pt_chietkhau <= 0) && (tienCONGNO_CONLAI <= 0))   //--> ÐÃ duy?t bán dúng giá tháng tru?c dó --> THOA TINH CHIET KHAU THANG, CHIET KHAU QUY
	        {

	            String dauthangHT = today.split("-")[0] + "-" + today.split("-")[1] + "-01";
	            String cuoithangHT = today.split("-")[0] + "-" + today.split("-")[1] + "-" + this.getngayCUOITHANG(today);

	            if (thangTRUOC == 6 && namTRUOC == 2014)
	            {
	                query = "select tct.pk_seq, tct.scheme, '" + khid1 + "' as khId,  " +
	                        "	sum( " + tongTHUVEThangtruoc + " * tc." + cotLAY + " / 100 )  " +
	                        "	- isnull(( select SUM(thanhtoan) from DUYETTRAKHUYENMAI_DONHANG where khachhang_fk = '" + khid1 + "' and duyetkm_fk = tct.pk_seq  ), 0)  as conLAI  " +
	                        "from " +
	                        "( " +
	                        "	select pk_seq as NSP_FK, 100 as tongGiatri, 50  as phantram " +
	                        "	from NHOMSANPHAM " +
	                        ") " +
	                        "PT_NHOM inner join TIEUCHITHUONGTL_TIEUCHI tc on PT_NHOM.NSP_FK = tc.nhomsanpham_fk " +
	                        "		and tc.tumuc <= PT_NHOM.phantram and PT_NHOM.phantram <= tc.denmuc " +
	                        "		inner join TIEUCHITHUONGTL tct on tc.thuongtl_fk = tct.pk_seq " +
	                        "where tct.thang = '" + thangTRUOC + "' and tct.nam = '" + namTRUOC + "' and tct.trangthai = '1' and tct.loaict = '0' " +
	                                " and tct.pk_seq in ( select thuongtl_fk from TIEUCHITHUONGTL_LOAINPP where loainpp = ( select loaiNPP from NHAPHANPHOI where pk_seq = '" + nppId + "' ) )  " +
	                        "group by tct.pk_seq, tct.scheme " +
	                        "having count(NSP_FK) >= 2 " +
	                        "	and sum( " + tongTHUVEThangtruoc + " * tc." + cotLAY + " / 100 )  " +
	                        "		- isnull(( select SUM(thanhtoan) from DUYETTRAKHUYENMAI_DONHANG where khachhang_fk = '" + khid1 + "' and duyetkm_fk = tct.pk_seq ), 0) > 0  ";
	            }
	            else
	            {

	                query = "\n select tct.pk_seq, tct.scheme, '" + khid1 + "' as khId,  " +
	                            "\n	sum( " + tongTHUVEThangtruoc + " * tc." + cotLAY + " / 100 )  " +
	                            "\n	- isnull(( select SUM( thanhtoan / ( 1 + ptTHUE / 100 ) ) from DUYETTRAKHUYENMAI_DONHANG where isnull(NgayThang,(select  SUBString(ngaynhap,0,8) from DONHANG where PK_SEQ = donhang_fk)) ='" + NgayThang + "' and    khachhang_fk = '" + khid1 + "' and duyetkm_fk = tct.pk_seq and donhang_fk in ( select pk_seq from DONHANG where ngaynhap >= '" + dauthangHT + "'  and trangthai != '2' and khachhang_fk = '" + khid1 + "' )  ), 0)  " +
	                            "\n	- ISNULL( ( select sum( thanhtoan / 1.05 ) from DUYETTRAKHUYENMAI_DUNO_DONHANG_DATRA where isnull(NgayThang,(select  SUBString(ngaynhap,0,8) from DONHANG where PK_SEQ = donhang_fk)) ='" + NgayThang + "' and  khachhang_fk = '" + khid1 + "' and tichluyQUY = '0' and donhang_fk in ( select pk_seq from DONHANG where ngaynhap >= '" + dauthangHT + "'  and trangthai != '2' and khachhang_fk = '" + khid1 + "' )  ), 0 )  as conLAI  " +
	                            "\n from " +
	                            "\n ( " +
	                            "\n	select pk_seq as NSP_FK, 100 as tongGiatri, 50  as phantram " +
	                            "\n	from NHOMSANPHAM " +
	                            "\n ) " +
	                            "\n PT_NHOM inner join TIEUCHITHUONGTL_TIEUCHI tc on PT_NHOM.NSP_FK = tc.nhomsanpham_fk " +
	                            "\n		and tc.tumuc <= PT_NHOM.phantram and PT_NHOM.phantram <= tc.denmuc " +
	                            "		inner join TIEUCHITHUONGTL tct on tc.thuongtl_fk = tct.pk_seq " +
	                            "\n where tct.nam = '" + namTRUOC + "' and tct.trangthai = '1' and tct.loaict = '0' " +
	                                    "\n and tct.pk_seq in ( select thuongtl_fk from TIEUCHITHUONGTL_LOAINPP where loainpp = ( select loaiNPP from NHAPHANPHOI where pk_seq = '" + nppId + "' ) )  " +
	                            "\n group by tct.pk_seq, tct.scheme " +
	                            "\n having count(NSP_FK) >= 2 ";

	            }
	          if(khid1.equals("126348"))
	            System.out.println("_________day ne:"+query);
	            ResultSet rsTL = db.get(query);
	         
	    


	            if (rsTL != null)
	            {
	                double total = 0;
	                try {
						if (rsTL.next())
						{
						    //CHIET KHAU THANG THI LUC NAO THUE CUNG BANG 5%
						    double thueVAT = 0;
						    if (!loaiNPP.equals("4") && !loaiNPP.equals("5"))
						        thueVAT = 5;
						    double thuongCL = (rsTL.getDouble("conLai") + (rsTL.getDouble("conLai") * thueVAT / 100));
						    thuongCL -= (chietkhau_bosung + duno_dauky);
						    total += thuongCL;
						    ketqua = total;
						    rsTL.close();
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }


	        }
		return ketqua;
	}
	
}
