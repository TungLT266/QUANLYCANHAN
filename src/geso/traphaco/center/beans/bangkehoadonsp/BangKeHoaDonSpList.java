package geso.traphaco.center.beans.bangkehoadonsp;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public class BangKeHoaDonSpList    extends Phan_Trang implements IBangKeHoaDonSpList, Serializable
{
	
	
	public BangKeHoaDonSpList()
	{
		this.loaiBaoCao="0";
		this.spId="";
		this.nppId="";
		this.userId="";
		this.khId="";
		this.msg="";
		this.ddkdId="";
		this.tuNgay="";
		this.denNgay="";
		this.kbhId="";
		db = new dbutils();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String tuNgay,denNgay,spId,nppId,ddkdId,userId,khId;
	String loaiBaoCao;
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
		String query="select pk_Seq,ma, ma + ' - ' + ten as ten from ERP_SANPHAM where trangthai=1";
		this.spRs=this.db.get(query);
		
		query="select pk_seq,manhanvien,ten from DaiDienKinhDoanh a where a.pk_seq in (select ddkd_fk from daidienkinhdoanh_npp ) ";
		this.ddkdRs = this.db.get(query);
		
		query="select pk_seq,isnull(mafast,'') +' ' + ten + ' ' + isnull(diachi,'') as Ten from nhaphanphoi where trangthai=1 ";
		this.khRs= this.db.get(query);
		
		query="select pk_Seq,ten,DIENGIAI from KENHBANHANG where TRANGTHAI=1 ";
		this.kbhRs = this.db.get(query);	
		
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
	
	public void init(String action)
	{
		this.getNppInfo();
		
		String query="";
		
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
		if(this.spId.length()>0)
		{
			condition+=" and b.sanpham_fk ='"+this.spId+"'";
		}
		if(this.khId.length()>0)
		{
			condition+=" and a.khachhang_Fk ='"+this.khId+"'";
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
		if(this.spId.length()>0)
		{
			conditionETC+=" and c.sanpham_fk ='"+this.spId+"'";
		}
		if(this.khId.length()>0)
		{
			conditionETC+=" and a.npp_fk ='"+this.khId+"'";
		}
		
		
		String sql="";
		if(this.kbhId.length()>0)
		{
			sql+=" and kh.kbh_fk='"+this.kbhId+"' ";
		}
		if(this.ddkdId.length()>0)
		{
			sql +=" and ddkd.pk_seq ='"+this.ddkdId+"'";
			
		}
		
		if (action.length()>0)
		{
			query+= "";
					/*"select  sp.ma as spMa,sp.TEN as spTen,ROW_NUMBER() OVER(PARTITION BY hdOTC.KHACHHANG_FK ORDER BY hdOTC.KHACHHANG_FK,hdOTC.HOADON_FK DESC) AS n_Row,  'OTC' as LoaiHD, v.TEN as vten,kv.TEN as kvTen,tt.TEN as ttTEN,qh.TEN as qhTen  , npp.ma as nppMa, npp.TEN as nppTen,ddkd.TEN as ddkdTen,         "+   
							"   kh.maFAST as khMa,kh.TEN as khTen,kh.DIACHI as khDiaChi,hdOTC.SoLuong,hdOTC.DonGia ,       "+   
							"   hdOTC.BVAT, hdOTC.VAT ,hdOTC.AVAT  ,hd.SOHOADON,hd.KYHIEU,hd.NGAYXUATHD  	         "+   
							"   from               "+   
							"   (              "+   
							"				select  b.sanpham_fk,a.KHACHHANG_FK,    "+
							"			(	select top(1) bb.ddkd_fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ    \n " +
							"						inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ   \n " +
							"				where cc.KHACHHANG_FK = a.KHACHHANG_FK  \n " +
							"			) as DDKD_FK ,b.HOADON_FK ,a.NPP_FK, round(sum(b.SoLuong*b.DonGia),0) as BVAT,  "+                 
							"				round(sum(b.SoLuong*b.DonGia*(1+ b.vat/100) ),0) as AVAT ,round(sum(b.SoLuong*b.DonGia*(b.vat/100) ),0) as VAT,  "+          
							"				SUM(b.SoLuong)as SoLuong,SUM(b.DonGia) as DonGia,0 as Type                    "+
							"			from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ                   "+
							"			where a.LOAIHOADON =0 and a.TRANGTHAI NOT IN  (1,3,5)  "+condition+"  "+
							"			group by b.HOADON_FK ,a.NPP_FK,a.KHACHHANG_FK,b.sanpham_fk   "+            
							"			UNION ALL   "+
							"				select  b.sanpham_fk,a.KHACHHANG_FK,  "+
							"			(	select top(1) bb.ddkd_fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ    \n " +
							"						inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ   \n " +
							"				where cc.KHACHHANG_FK = a.KHACHHANG_FK  \n " +
							"			) as DDKD_FK	,b.HOADON_FK ,a.NPP_FK, round(sum(b.SoLuong*b.DONGIA),0) as BVAT,  "+                 
							"				round(sum(b.SoLuong*b.DONGIA*(1+ b.vat/100) ),0) as AVAT ,round(sum(b.SoLuong*b.DONGIA*(b.vat/100) ),0) as VAT,  "+             
							"				SUM(b.SoLuong)as SoLuong,SUM(b.DonGia) as DonGia,0 as Type                 "+
							"			from HOADON a inner join HOADON_CTKM_TRAKM b on b.HOADON_FK=a.PK_SEQ             "+      
							"				where a.LOAIHOADON =1 and a.TRANGTHAI NOT IN  (1,3,5)   "+condition+"   "+
							" 			group by b.HOADON_FK ,a.NPP_FK,a.KHACHHANG_FK,b.sanpham_fk     "+  
							"   )as hdOTC              "+     
							" left join sanpham sp on sp.pk_Seq=hdOTC.sanpham_fk                                         "+
							"   left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ=hdOTC.DDKD_FK           "+   
							"   inner join KHACHHANG kh on kh.PK_SEQ=hdOTC.KHACHHANG_FK         "+   
							"   inner join NHAPHANPHOI npp on npp.PK_SEQ=hdOTC.NPP_FK           "+   
							"   inner join HOADON hd on hd.PK_SEQ=hdOTC.HOADON_FK          "+   
							"   left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK          "+   
							"   left join QUANHUYEN qh on qh.PK_SEQ=npp.QUANHUYEN_FK          "+   
							"   left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK           "+   
							"   left join VUNG v on v.PK_SEQ=kv.VUNG_FK            "+   
							"   where 1=1  "+sql+"   ";*/
			
			if (this.loaiBaoCao.equals("1")) {
				
				query+=
				/*		"        "+   
								"\n   select   sp.ma as spMa,sp.ten as spTen,ROW_NUMBER() OVER(PARTITION BY hdETC.npp_fk ORDER BY hdETC.npp_fk,hdETC.HOADON_FK DESC) AS n_Row, 'ETC' as LoaiHD, v.TEN as vten,kv.TEN as kvTen,tt.TEN as ttTEN,qh.TEN as qhTen  , '1'as nppMa, 'Tổng công ty Traphaco' as nppTen,'' as ddkdTen,         "+   
								"\n   npp.mafast as khMa,npp.TEN as khTen,npp.DIACHI as khDiaChi,       "+   
								"\n   hdETC.SoLuong,hdETC.DonGia ,       "+   
								"\n   hdETC.BVAT, hdETC.VAT ,hdETC.AVAT  ,hd.SOHOADON,hd.KYHIEU,hd.NGAYXUATHD,hdETC.donvitinh,"
								
								+ "			hdETC.hoadon_fk,hdETC.SOLO,hdETC.NGAYHETHAN,hdETC.THUEVAT,"
								+ "			hdETC.mame, hdETC.mathung, hdETC.MARQ, hdETC.maphieu, hdETC.vitri, hdETC.phieudt,hdETC.phieueo, hdETC.nsxId	  	       "+   
								"\n   from               "+   
								"\n   (              "+   
								"\n			select ETC.mame, ETC.mathung, ETC.MARQ, ETC.maphieu, ETC.vitri, ETC.phieudt,ETC.phieueo, ETC.nsxId,"
								+ "		  ETC.MA,ETC.SOLO,ETC.NGAYHETHAN,ETC.THUEVAT,  "+
								"\n					ETC.sanpham_fk,	ETC.npp_fk,ETC.HOADON_FK , sum(round(ETC.SoLuong*ETC.DONGIA,0)) as BVAT,  "+                 
								"\n						sum(round(round(ETC.SoLuong*ETC.DONGIA,0)*(1+ ETC.thuexuat/100),0) ) as AVAT ,SUM(ETC.SoLuong)as SoLuong,SUM(ETC.DonGia) as DonGia,0 as Type,  "+                 
								"\n						sum(isnull(ETC.chietkhau,0)*(1+ ETC.thuexuat/100)) as AVAT_CK,                   "+
								"\n					sum(isnull(ETC.chietkhau,0)) as BVAT_CK,     "+
								"\n					sum(round(round(ETC.SoLuong*ETC.DONGIA,0)*(ETC.thuexuat/100),0) )   as VAT,ETC.donvitinh  "+  				
								"\n					from     "+
								"\n	 					(    "+
								"\n					select "
								+ "		dd.MA,dd.SOLO,dd.NGAYHETHAN,dd.THUEVAT,dd.mame, dd.mathung, dd.MARQ, dd.maphieu, isnull(bin.ten,'') as vitri, dd.phieudt,"
								+ "		dd.phieueo, isnull(nsx.ten,'') nsxId ,     c.sanpham_fk,a.npp_fk  "+
								"\n							,c.HOADON_FK,c.donvitinh,  "+
								"\n							case     when c.donvitinh = e.donvi then dd.soluong  "+      
								"\n										  else dd.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,  "+   
								"\n							case     when c.donvitinh = e.donvi then c.dongia        "+
								"\n										  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia,  "+    
								"\n							c.vat as thuexuat ,isnull(c.CHIETKHAU,0) as ChietKhau     "+
								"\n					from ERP_HOADON a  "+        
								"\n						inner join ERP_HOADON_SP c on a.pk_seq = c.hoadon_fk  "+       
								"\n						inner join ERP_SANPHAM d on c.sanpham_fk = d.pk_seq     "+
								"\n inner join  ( select mame, mathung, MARQ, maphieu, bin_fk as vitri, phieudt,phieueo, nsx_fk as nsxId,hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ,sum (SOLUONG) as SOLUONG \n"+
								"\n		   from ERP_HOADON_SP_CHITIET  \n"+
								"\n		   group by mame, mathung, MARQ, maphieu, bin_fk, phieudt,phieueo, nsx_fk,hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU,MA,SOLO,NGAYHETHAN,THUEVAT ) as dd on dd.MA=d.MA and dd.hoadon_fk=a.pk_seq \n"+

								"\n						inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq  "
								+ "						left join erp_bin bin on bin.pk_seq = dd.vitri  "
								+ "						left join ERP_NHASANXUAT nsx on nsx.pk_seq=dd.nsxId     "+
								"\n					where 1=1  and a.trangthai not in ( 1 , 3, 5 )  "+conditionETC+"  "+
								"\n					)  ETC   "+
								"\n					where soluong>0  "+
								"\n					group by ETC.mame, ETC.mathung, ETC.MARQ, ETC.maphieu, ETC.vitri, ETC.phieudt,ETC.phieueo, ETC.nsxId,ETC.npp_fk,ETC.HOADON_FK ,ETC.sanpham_fk,ETC.donvitinh,MA,SOLO,NGAYHETHAN,THUEVAT   "+
								"\n   )as hdETC              "+     
								"\n left join ERP_SANPHAM sp on sp.pk_Seq=hdETC.sanpham_fk                                         "+
								"\n   inner join ERP_HOADON hd on hd.PK_SEQ=hdETC.HOADON_FK           "+   
								"\n   inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK           "+   
								"\n   left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK          "+   
								"\n   left join QUANHUYEN qh on qh.PK_SEQ=npp.QUANHUYEN_FK          "+   
								"\n   left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK           "+   
								"\n   left join VUNG v on v.PK_SEQ=kv.VUNG_FK 			            "+
								"\n   where 1=1  "+sql+"   ";*/
				
				query+=
						"        "+   
								"\n   select   sp.ma as spMa,sp.ten as spTen,ROW_NUMBER() OVER(PARTITION BY hdETC.npp_fk ORDER BY hdETC.npp_fk,hdETC.HOADON_FK DESC) AS n_Row, 'ETC' as LoaiHD, v.TEN as vten,kv.TEN as kvTen,tt.TEN as ttTEN,qh.TEN as qhTen  , '1'as nppMa, 'Tổng công ty Traphaco' as nppTen,'' as ddkdTen,         "+   
								"\n   npp.mafast as khMa,npp.TEN as khTen,npp.DIACHI as khDiaChi,       "+   
								"\n   hdETC.SoLuong,hdETC.DonGia ,       "+   
								"\n   hdETC.BVAT, hdETC.VAT ,hdETC.AVAT  ,hd.SOHOADON,hd.KYHIEU,hd.NGAYXUATHD,hdETC.donvitinh ,hdETC.solo 	       "+   
								"\n   from               "+   
								"\n   (              "+   
								"\n			select  ETC.solo, "+
								"\n					ETC.sanpham_fk,	ETC.npp_fk,ETC.HOADON_FK , sum(round(ETC.SoLuong*ETC.DONGIA,0)) as BVAT,  "+                 
								"\n						sum(round(round(ETC.SoLuong*ETC.DONGIA,0)*(1+ ETC.thuexuat/100),0) ) as AVAT ,SUM(ETC.SoLuong)as SoLuong,SUM(ETC.DonGia) as DonGia,0 as Type,  "+                 
								"\n						sum(isnull(ETC.chietkhau,0)*(1+ ETC.thuexuat/100)) as AVAT_CK,                   "+
								"\n					sum(isnull(ETC.chietkhau,0)) as BVAT_CK,     "+
								"\n					sum(round(round(ETC.SoLuong*ETC.DONGIA,0)*(ETC.thuexuat/100),0) )   as VAT,ETC.donvitinh  "+  				
								"\n					from     "+
								"\n	 					(    "+
								"\n					select dd.solo, c.sanpham_fk,a.npp_fk  "+
								"\n							,c.HOADON_FK,c.donvitinh,  "+
								"\n							case     when c.donvitinh = e.donvi then dd.soluong  "+      
								"\n										  else dd.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,  "+   
								"\n							case     when c.donvitinh = e.donvi then c.dongia        "+
								"\n										  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia,  "+    
								"\n							c.vat as thuexuat ,isnull(c.CHIETKHAU,0) as ChietKhau     "+
								"\n					from ERP_HOADON a  "+        
								"\n						inner join ERP_HOADON_SP c on a.pk_seq = c.hoadon_fk  "+       
								"\n						inner join ERP_SANPHAM d on c.sanpham_fk = d.pk_seq     "+
								"\n inner join  ( select hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ,sum (SOLUONG) as SOLUONG \n"+
								"\n		   from ERP_HOADON_SP_CHITIET  \n"+
								"\n		   group by hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ) as dd on dd.MA=d.MA and dd.hoadon_fk=a.pk_seq \n"+
	
								"\n						inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     "+
								"\n					where 1=1  and a.trangthai not in ( 1 , 3, 5 )  "+conditionETC+"  "+
								"\n					)  ETC   "+
								"\n					where soluong>0  "+
								"\n					group by ETC.npp_fk,ETC.HOADON_FK ,ETC.sanpham_fk,ETC.donvitinh,ETC.solo  "+
								"\n   )as hdETC              "+     
								"\n left join ERP_SANPHAM sp on sp.pk_Seq=hdETC.sanpham_fk                                         "+
								"\n   inner join ERP_HOADON hd on hd.PK_SEQ=hdETC.HOADON_FK           "+   
								"\n   inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK           "+   
								"\n   left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK          "+   
								"\n   left join QUANHUYEN qh on qh.PK_SEQ=npp.QUANHUYEN_FK          "+   
								"\n   left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK           "+   
								"\n   left join VUNG v on v.PK_SEQ=kv.VUNG_FK 			            "+
								"\n   where 1=1  "+sql+"   ";
				
				
			} else {
			
				query+=
						"        "+   
								"\n   select   sp.ma as spMa,sp.ten as spTen,ROW_NUMBER() OVER(PARTITION BY hdETC.npp_fk ORDER BY hdETC.npp_fk,hdETC.HOADON_FK DESC) AS n_Row, 'ETC' as LoaiHD, v.TEN as vten,kv.TEN as kvTen,tt.TEN as ttTEN,qh.TEN as qhTen  , '1'as nppMa, 'Tổng công ty Traphaco' as nppTen,'' as ddkdTen,         "+   
								"\n   npp.mafast as khMa,npp.TEN as khTen,npp.DIACHI as khDiaChi,       "+   
								"\n   hdETC.SoLuong,hdETC.DonGia ,       "+   
								"\n   hdETC.BVAT, hdETC.VAT ,hdETC.AVAT  ,hd.SOHOADON,hd.KYHIEU,hd.NGAYXUATHD,hdETC.donvitinh  	       "+   
								"\n   from               "+   
								"\n   (              "+   
								"\n			select   "+
								"\n					ETC.sanpham_fk,	ETC.npp_fk,ETC.HOADON_FK , sum(round(ETC.SoLuong*ETC.DONGIA,0)) as BVAT,  "+                 
								"\n						sum(round(round(ETC.SoLuong*ETC.DONGIA,0)*(1+ ETC.thuexuat/100),0) ) as AVAT ,SUM(ETC.SoLuong)as SoLuong,SUM(ETC.DonGia) as DonGia,0 as Type,  "+                 
								"\n						sum(isnull(ETC.chietkhau,0)*(1+ ETC.thuexuat/100)) as AVAT_CK,                   "+
								"\n					sum(isnull(ETC.chietkhau,0)) as BVAT_CK,     "+
								"\n					sum(round(round(ETC.SoLuong*ETC.DONGIA,0)*(ETC.thuexuat/100),0) )   as VAT,ETC.donvitinh  "+  				
								"\n					from     "+
								"\n	 					(    "+
								"\n					select c.sanpham_fk,a.npp_fk  "+
								"\n							,c.HOADON_FK,c.donvitinh,  "+
								"\n							case     when c.donvitinh = e.donvi then dd.soluong  "+      
								"\n										  else dd.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,  "+   
								"\n							case     when c.donvitinh = e.donvi then c.dongia        "+
								"\n										  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia,  "+    
								"\n							c.vat as thuexuat ,isnull(c.CHIETKHAU,0) as ChietKhau     "+
								"\n					from ERP_HOADON a  "+        
								"\n						inner join ERP_HOADON_SP c on a.pk_seq = c.hoadon_fk  "+       
								"\n						inner join ERP_SANPHAM d on c.sanpham_fk = d.pk_seq     "+
								"\n inner join  ( select hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ,sum (SOLUONG) as SOLUONG \n"+
								"\n		   from ERP_HOADON_SP_CHITIET  \n"+
								"\n		   group by hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ) as dd on dd.MA=d.MA and dd.hoadon_fk=a.pk_seq \n"+
	
								"\n						inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     "+
								"\n					where 1=1  and a.trangthai not in ( 1 , 3, 5 )  "+conditionETC+"  "+
								"\n					)  ETC   "+
								"\n					where soluong>0  "+
								"\n					group by ETC.npp_fk,ETC.HOADON_FK ,ETC.sanpham_fk,ETC.donvitinh  "+
								"\n   )as hdETC              "+     
								"\n left join ERP_SANPHAM sp on sp.pk_Seq=hdETC.sanpham_fk                                         "+
								"\n   inner join ERP_HOADON hd on hd.PK_SEQ=hdETC.HOADON_FK           "+   
								"\n   inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK           "+   
								"\n   left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK          "+   
								"\n   left join QUANHUYEN qh on qh.PK_SEQ=npp.QUANHUYEN_FK          "+   
								"\n   left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK           "+   
								"\n   left join VUNG v on v.PK_SEQ=kv.VUNG_FK 			            "+
								"\n   where 1=1  "+sql+"   ";
			
			}
			
			/*query+=
					"union all "+
							"   select   sp.ma as spMa,sp.ten as spTen,ROW_NUMBER() OVER(PARTITION BY hdETC.KHACHHANG_FK ORDER BY hdETC.KHACHHANG_FK,hdETC.HOADON_FK DESC) AS n_Row, 'ETC' as LoaiHD, v.TEN as vten,kv.TEN as kvTen,tt.TEN as ttTEN,qh.TEN as qhTen  , npp.ma as nppMa, npp.TEN as nppTen,ddkd.TEN as ddkdTen,         "+   
							"   kh.maFAST as khMa,kh.TEN as khTen,kh.DIACHI as khDiaChi,       "+   
							"   hdETC.SoLuong,hdETC.DonGia ,       "+   
							"   hdETC.BVAT, hdETC.VAT ,hdETC.AVAT  ,hd.SOHOADON,hd.KYHIEU,hd.NGAYXUATHD  	       "+   
							"   from               "+   
							"   (              "+   
							"			select   "+
							"					ETC.sanpham_fk,	ETC.KHACHHANG_FK,ETC.DDKD_FK,ETC.HOADON_FK ,ETC.NPP_FK, sum(ETC.SoLuong*ETC.DONGIA) as BVAT,  "+                 
							"						sum(ETC.SoLuong*ETC.DONGIA*(1+ ETC.thuexuat/100) ) as AVAT ,SUM(ETC.SoLuong)as SoLuong,SUM(ETC.DonGia) as DonGia,0 as Type,  "+                 
							"						sum(isnull(ETC.chietkhau,0)*(1+ ETC.thuexuat/100)) as AVAT_CK,                   "+
							"					sum(isnull(ETC.chietkhau,0)) as BVAT_CK,     "+
							"					sum(ETC.SoLuong*ETC.DONGIA*(ETC.thuexuat/100) )   as VAT  "+  				
							"					from     "+
							"	 					(    "+
							"					select c.sanpham_fk,a.NPP_DAT_FK as KHACHHANG_FK,  "+
							"			(	NULL ) as DDKD_FK,c.HOADON_FK,a.npp_fk, "+
							"							case     when c.donvitinh = e.donvi then c.soluong  "+      
							"										  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,  "+   
							"							case     when c.donvitinh = e.donvi then c.dongia        "+
							"										  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia,  "+    
							"							c.vat as thuexuat ,isnull(c.CHIETKHAU,0) as ChietKhau     "+
							"					from ERP_HOADONNPP a  "+        
							"						inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  "+       
							"						inner join SANPHAM d on c.sanpham_fk = d.pk_seq     "+
							"						inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     "+
							"					where 1=1  and a.trangthai not in ( 1 , 3, 5 )  AND ISNULL(A.LOAIHOADON,0)=1  "+conditionETC+"  "+
							"					)  ETC   "+
							"					where soluong>0  "+
							"					group by ETC.KHACHHANG_FK,ETC.DDKD_FK,ETC.HOADON_FK ,ETC.NPP_FK,ETC.sanpham_fk  "+
							"   )as hdETC              "+     
							" left join sanpham sp on sp.pk_Seq=hdETC.sanpham_fk                                         "+
							"   inner join ERP_HOADONNPP hd on hd.PK_SEQ=hdETC.HOADON_FK           "+   
							"   left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ=hdETC.DDKD_FK           "+   
							"   inner join NHAPHANPHOI kh on kh.PK_SEQ=hdETC.KHACHHANG_FK         "+   
							"   inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK           "+   
							"   left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK          "+   
							"   left join QUANHUYEN qh on qh.PK_SEQ=npp.QUANHUYEN_FK          "+   
							"   left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK           "+   
							"   left join VUNG v on v.PK_SEQ=kv.VUNG_FK 			            "+
							"   where 1=1  "+sql+"   ";
			*/
			
			
			
			
			
			this.queryHd=query;
			
			setTotal_Query(query);
			System.out.println("[BangKeHoaDonSpList]"+query);
			
		}
		this.hoadonRs=this.createSplittingData(super.getItems(), super.getSplittings(), "NGAYXUATHD desc", query);
		
		
		createRs();
	}
	
	
	private void getNppInfo()
	{
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId,this.db);
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
		query=
				"select SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT  \n"+   
						"   from  ("+searchquery+")   as HD   \n";
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
	public String getLoaiBaoCao() {
		return loaiBaoCao;
	}
	public void setLoaiBaoCao(String loaiBaoCao) {
		this.loaiBaoCao = loaiBaoCao;
	}
	
	
	
}
