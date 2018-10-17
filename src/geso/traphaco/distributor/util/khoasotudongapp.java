package geso.traphaco.distributor.util;

import geso.traphaco.distributor.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class khoasotudongapp {
	
	public static void main(String[] arg) 
	{
		ArrayList<String> arr=new ArrayList<String>();
		arr=getthangkhoaso();
		for(int i=0;i<arr.size();i++)
		{
			String [] items=arr.get(i).split("_");
			String npp=items[0];
			int thang=Integer.parseInt(items[1]);
			int nam=Integer.parseInt(items[2]);
			if(thang==12)
			{
				thang=1;
				nam+=1;
			}
			else
				thang+=1;
				KhoaSoNgayapp(thang, nam, 2, 2015,npp);
		}
	
		System.out.println("DONE...........");
	}
	
	private static ArrayList<String> getthangkhoaso()
	{
		 ArrayList<String> arr=new ArrayList<String>(); 
		String npp_thang_nam="";
		HashMap<String, String> hnpp=new HashMap<String, String>();
		dbutils db = new dbutils();
		String query="   select a.pk_seq as ma, ten, \n"+
					"	 ( select top(1) thangKS from KHOASOTHANG where NPP_FK = a.PK_SEQ order by NAM desc, THANGKS desc ) as thangKS ,  \n"+
					"	 ( select top(1) NAM from KHOASOTHANG where NPP_FK = a.PK_SEQ order by NAM desc, THANGKS desc ) as namKS   \n"+
					"	from NHAPHANPHOI a where   TRANGTHAI = '1'  and (select top(1) thangKS from KHOASOTHANG where NPP_FK = a.PK_SEQ order by NAM desc, THANGKS desc ) is not null  \n"+
					"	order by ( select top(1) NAM from KHOASOTHANG where NPP_FK = a.PK_SEQ order by NAM desc, THANGKS desc )  desc,  \n"+
					"	   ( select top(1) thangKS from KHOASOTHANG where NPP_FK = a.PK_SEQ order by NAM desc, THANGKS desc ) desc ";
		ResultSet rs=db.get(query);
		try {
			while(rs.next())
			{
				String npp=rs.getString("ma");
				String thang=rs.getString("thangks");
				String nam=rs.getString("namKS");
				npp_thang_nam=npp+"_"+thang+"_"+nam;
				arr.add(npp_thang_nam);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return arr;
		
	}
	

		private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	
	private static void KhoaSoNgayapp(int  thangks,int namks,int thangmuonks,int nammuonks,String npp) 
	{	
		dbutils db=new dbutils();
		int thangKsMax = thangmuonks;
		int namKsMax = nammuonks;
		while (namks < nammuonks || (namks <=  nammuonks && thangks <= thangmuonks)) {
			System.out.println(thangks+"-"+namks+"___"+thangmuonks+"-"+nammuonks);
		try 
		{			
			String query="select count(*) as kq from ufn_xnt_total("+npp+") where xnt < 0";
			ResultSet rs1=db.get(query);
			int flag=0;
			if(rs1.next())
				flag=rs1.getInt("kq");
			if(flag>0)
			{
				query="insert into Log_khoasothang (nppId,lydo,thoigian) values ("+npp+",'am xnt kho tong',GETDATE())";
				if(!db.update(query))
				System.out.println("khong the insert Log_khoasothang "+ query);
				break;
			}
			if(flag==0){
			
			db.getConnection().setAutoCommit(false);
			 query=
		   "   declare @ThangKs int ,@NamKs int,@nppId numeric(18,0),@Thang int,@Nam int,@TuNgay char(10),@DenNgay char(10)   \n "+      
		   "   set @ThangKs = '"+thangks+"'   \n "+      
		   "   set @NamKs   =  '"+namks+"'   \n "+      
		   "   set @nppId=('"+npp+"' )   \n "+      
		   "   select top(1) @Thang=THANGKS,@Nam=NAM from KHOASOTHANG where NPP_FK=@nppId order by NAM desc,THANGKS desc   \n "+      
		   "   set @TuNgay=cast(@Nam as CHAR(4))+'-'+case    \n "+      
		   "   	when len(dbo.Trim( cast( @Thang as varchar(2)) )) <2 then '0' +cast( @Thang as varchar(2)) else cast( @Thang as varchar(2)) end +'-01'   \n "+      
		   "      \n "+      
		   "   select @TuNgay =(select convert(varchar(10),DATEADD(month,1,@TuNgay),20) )      \n "+      
		   " select @DenNgay= convert(varchar(10), DATEADD(DD,-1,DATEADD(mm,1,@TuNgay)) ,20)    \n"+      
		   "      \n "+      
		   "   INSERT INTO TONKHOTHANG(NPP_FK,KBH_FK,KHO_FK,SANPHAM_FK,SOLUONG,THANG,NAM)   \n "+      
		   "   SELECT DATA.NPP_FK,DATA.KBH_FK,DATA.KHO_FK,DATA.SANPHAM_FK,DATA.soluong,'"+thangks+"','"+namks+"'   \n "+      
		   "   from    \n "+      
		   "   (   \n "+      
		   "   	select xnt.NPP_FK,xnt.KBH_FK,xnt.KHO_FK,xnt.sanpham_fk,sum(soluong) as soluong    \n "+      
		   "   	from      \n "+      
		   "   	(      \n "+      
		   "   	   \n "+      
		   "   	select  npp_fk,kbh_fk, kho_fk, sanpham_fk, sum(soluong) as soluong ,N'Tồn đầu' as type 			      \n "+      
		   "   	from TONKHOTHANG			      \n "+      
		   "   	where  THANG=@Thang and NAM=@Nam and NPP_FK=@nppId   \n "+      
		   "   	group by kbh_fk, npp_fk, kho_fk, sanpham_fk	      \n "+      
		   "      \n "+      
		   "   	union all      \n "+      
		   "      \n "+      
		   "   	select b.npp_fk,b.kbh_fk,a.khonhan_fk as kho_fk,c.pk_seq as sanpham_fk,sum(cast(soluongnhan as int)) as soluong,N'Nhập hàng' as type       \n "+      
		   "   	from nhaphang_sp a inner join nhaphang b on a.nhaphang_fk = b.pk_seq		      \n "+      
		   "   		inner join sanpham c on c.pk_seq = a.sanpham_fk       \n "+      
		   "   	where   b.trangthai =1 and b.NGAYNHAN>=@TuNgay and b.NGAYNHAN<=@DenNgay   \n "+      
		   "   	group by b.npp_fk,b.kbh_fk,c.pk_seq,a.khonhan_fk 	      \n "+      
		   "      \n "+      
		   "   	union all      \n "+      
		   "      \n "+      
		   "   	select ck.npp_fk, ck.kbh_fk, ck.khoxuat_fk as kho_Fk, ck.sanpham_fk ,(-1)*sum(soluong) as SoLuong,N'Xuất chuyển nội bộ' as Type      \n "+      
		   "   	from       \n "+      
		   "   	(      \n "+      
		   "   	select  c.npp_fk, c.kbh_fk, c.khoxuat_fk, a.sanpham_fk,            \n "+      
		   "   		case when a.dvdl_fk IS null then a.soluongchuyen             \n "+      
		   "   			 when a.dvdl_fk = b.DVDL_FK then a.soluongchuyen            \n "+      
		   "   			 else  a.soluongchuyen * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )             \n "+      
		   "   							 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong          \n "+      
		   "   	from ERP_CHUYENKHONPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ         \n "+      
		   "   	inner join ERP_CHUYENKHONPP c on a.chuyenkho_fk = c.pk_seq         \n "+      
		   "   	where c.trangthai=1 and  c.NgayChuyen>=@TuNgay and c.NgayChuyen<=@DenNgay   \n "+      
		   "   	)as ck      \n "+      
		   "   	group by ck.npp_fk, ck.kbh_fk, ck.khoxuat_fk, ck.sanpham_fk 	      \n "+      
		   "      \n "+      
  
		   "    union all  \n "+      
		   "select  a.NPP_FK, a.kbh_fk, a.kho_fk, b.SANPHAM_FK,-(1)*SUM(b.soluong) as soluong ,'Xuất ETC'  as NghiepVu " + 
		   "from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.PK_SEQ = b.YCXK_FK  " + 
		   "where  b.soluong > 0 " + 
		   "	and a.NPP_FK=@nppId and a.PK_SEQ in (Select ycxk_fk from ERP_YCXUATKHONPP_DDH  where ddh_fk in " + 
		   "	(select ddh_fk from ERP_HOADONNPP_DDH where HOADONNPP_FK in (select PK_SEQ from ERP_HOADONNPP where TRANGTHAI not IN (3,5)" + 
		   "	and NGAYXUATHD>=@TuNgay AND NGAYXUATHD <=@DenNgay )) ) and a.trangthai != '3'" + 
		   "group by a.kho_fk, a.kbh_fk, a.NPP_FK, b.SANPHAM_FK " +
		   "   	union all      \n "+      
		   "      \n "+      
		   "   	SELECT DTH.NPP_FK,DTH.KBH_FK ,DTH.KHO_FK ,THSP.SANPHAM_FK,(-1)* SUM(THSP.SOLUONG) AS SOLUONG ,N'Trả Hàng về NCC' AS TYPE      \n "+      
		   "   	FROM DONTRAHANG_SP THSP INNER JOIN  DONTRAHANG DTH ON  DTH.PK_SEQ = THSP.DONTRAHANG_FK      \n "+      
		   "   	WHERE DTH.TRANGTHAI =2 AND DTH.NGAYTRA >=@TuNgay AND DTH.NGAYTRA <=@DenNgay AND THSP.SOLUONG > 0       \n "+      
		   "   	GROUP BY DTH.NPP_FK,DTH.KBH_FK,THSP.SANPHAM_FK,DTH.KHO_FK	      \n "+      
		   "      \n "+      
		   "   	union all      \n "+      
		   "      \n "+      
		   "   	SELECT  DCTK.NPP_FK,DCTK.KBH_FK,DCTK.KHO_FK,DCTK_SP.SANPHAM_FK ,      \n "+      
		   "   		SUM( CAST( ISNULL(DCTK_SP.DIEUCHINH,0) AS INT) ) AS SOLUONG, N'Kiểm kho' AS TYPE 	        \n "+      
		   "   	FROM	DIEUCHINHTONKHO DCTK  INNER JOIN DIEUCHINHTONKHO_SP DCTK_SP ON DCTK_SP.DIEUCHINHTONKHO_FK = DCTK.PK_SEQ       \n "+      
		   "   	WHERE   DCTK.TRANGTHAI =1 AND DCTK.NGAYDC >= @TuNgay AND DCTK.NGAYDC <= @DenNgay   \n "+      
		   "   	GROUP BY  DCTK.NPP_FK,DCTK.KBH_FK,DCTK.KHO_FK,DCTK_SP.SANPHAM_FK      \n "+      
		   "      \n "+      
		   "      \n "+      
		   "      \n "+      
		  " union all "+
			"	select dh.npp_fk,dh.KBH_FK,dh.KHO_FK,dhsp.sanpham_fk,(-1)*(soluong) as soluong ,N'Xuất hàng bán' as NghiepVu   \n"+       
			"	from hoadon_sp dhsp         \n"+
			"	inner join hoadon dh on dh.pk_seq = dhsp.hoadon_fk \n"+  	      
			"	where dh.trangthai not in (3,5) and isnull(dh.LOAIHOADON,0)=0 and dh.ngayxuathd >= @TuNgay  and dh.ngayxuathd <= @DenNgay \n"+   
			"	and dh.NPP_FK=@nppId   \n"+		   
		   "      \n "+      
		   "   		union all      \n "+      
		   "   		   \n "+      
			 "		select NPP_FK,kbh_fk,kho_Fk,sanpham_fk,(-1)*SUM(SoLuong) as SoLuong,N'Xuất KM' AS NghiepVu  \n "+
			 "		from \n "+ 
			 "		(  \n "+
			 "			select	a.KBH_FK,a.npp_fk,c.pk_Seq as sanpham_Fk,b.kho_Fk,  \n "+
			 "		case when len(dbo.Trim(b.SOLO))=0 then 'NA' ELSE dbo.Trim(b.SOLO) end  as SoLo,  \n "+
			 "				b.SOLUONG as SoLuong,case when len(dbo.Trim(b.SOLO))=0 then '2030-12-31' ELSE dbo.Trim(b.ngayhethan) end  as NgayHetHan \n "+
			 "			from  HOADON a inner join HOADON_CTKM_TRAKM_CHITIET b on b.hoadonID=a.PK_SEQ \n "+
			  "				inner join SANPHAM c on c.PK_SEQ=b.sanpham_fk \n "+
			 "				where a.NPP_FK=@nppId and a.NGAYXUATHD>=@TuNgay and a.NGAYXUATHD<=@DenNgay  \n "+
			 "				and a.TRANGTHAI not in (3,5) and a.LOAIHOADON in (1,2) \n "+
			 "	) hd \n "+
			"		group by hd.NPP_FK,hd.SANPHAM_FK,hd.KHO_FK,hd.SoLo,KBH_FK \n "+
		   "   	   \n "+      
		   "   		   \n "+      
			"		union all    \n "+
			"		select a.NPP_FK,a.KBH_FK,a.KhoXuat_FK, b.sanpham_fk,(-1)* SUM(b.soluong) as tongxuat ,N'L.Đổi kênh(-)' as NghiepVu  \n "+            
			"		from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk              \n "+
			"		where a.trangthai=1 and a.NgayChuyen>=@TuNgay and a.NgayChuyen<=@DenNgay and a.KHONHAN_FK is  null                 \n "+  
			"		group by a.khoxuat_fk, a.KBH_FK,a.npp_fk, b.sanpham_fk             \n "+
					            
			"		union all             \n "+
			"		select a.NPP_FK,a.KBH_FK,a.KhoXuat_FK, b.sanpham_fk,SUM(b.soluong) as tongxuat ,N'M.Đổi kênh(+)' as NghiepVu \n "+            
			"		from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk              \n "+
			"		where a.trangthai=1 and a.NgayChuyen>=@TuNgay and a.NgayChuyen<=@DenNgay and a.KHONHAN_FK is  null             \n "+      
			"		group by a.khoxuat_fk, a.KBH_FK,a.npp_fk, b.sanpham_fk            \n "+
					            
			"		union all             \n "+
			"		select a.NPP_FK,a.KBH_FK,a.KhoXuat_FK, b.sanpham_fk,(-1)* SUM(b.soluong) as tongxuat ,N'N.Chuyển kho(-)' as NghiepVu \n "+            
			"		from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk                        \n "+
			"		where a.trangthai=1 and a.NgayChuyen>=@TuNgay and a.NgayChuyen<=@DenNgay     and a.KHONHAN_FK is not null                   \n "+
			"		group by a.khoxuat_fk, a.KBH_FK,a.npp_fk, b.sanpham_fk                      \n "+
			"		union all                      \n "+
			"		select a.NPP_FK,a.KBH_FK,a.KHONHAN_FK, b.sanpham_fk,SUM(b.soluong) as tongxuat ,N'O.Chuyển kho(+)' as NghiepVu \n "+            
			"		from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk                      \n "+  
			"		where a.trangthai=1 and a.NgayChuyen>=@TuNgay and a.NgayChuyen<=@DenNgay  and a.KHONHAN_FK is not null                  \n "+
			"		group by a.KHONHAN_FK,a.KBH_FK, a.npp_fk, b.sanpham_fk \n "+			 
			 "		union all \n "+
			 "		select  a.npp_fk,a.kbh_fk,a.kho_fk,b.sanpham_Fk,SUM(b.SoLuong) as SoLuong,N'Hàng trả lại' as Type \n "+
			 "	from Erp_HangTraLaiNpp a inner join Erp_HangTraLaiNpp_SanPham b on b.hangtralai_fk=a.pk_Seq \n "+
			 "		where a.trangthai=1  and a.ngaytra>=@TuNgay and a.ngaytra <=@DenNgay   and a.npp_fk=@nppId \n "+
			 "	group by a.npp_fk,b.sanpham_Fk,a.kbh_fk,a.kho_fk 		    \n "+
				"		 union all \n "+
			  "   		select a.tructhuoc_fk as npp_Fk, a.kbh_fk,a.KhoXuat_FK as kho_fk , b.sanpham_fk,SUM(b.soluongchuyen) as tongxuat ,N'Xuất kho khác' as Type   \n "+      
			  "   		from ERP_CHUYENKHO a inner join ERP_CHUYENKHO_SANPHAM b on a.pk_seq = b.chuyenkho_fk    \n "+      
			  "   		where a.trangthai=1 and a.NgayChuyen>=@TuNgay and a.NgayChuyen<=@DenNgay  and a.TrucThuoc_FK=@nppId    \n "+      
			  "   		group by a.khoxuat_fk, a.tructhuoc_fk, a.kbh_fk, b.sanpham_fk   \n "+      
		   "   	)as xnt   \n "+      
		   "   	group by xnt.NPP_FK,xnt.KBH_FK,xnt.KHO_FK,xnt.sanpham_fk   \n "+      
		   "   ) as DATA    \n "+      
		   "   where    \n "+      
		   "   DATA.NPP_FK=@nppId     \n ";
			
			System.out.println("____________"+query);
			
			if(db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback();
			System.out.println( "Không thể thiết lập khóa sổ tháng " + query);
				
			}
			
			query=
			 "   declare @ThangKs int ,@NamKs int,@nppId numeric(18,0),@Thang int,@Nam int,@TuNgay char(10),@DenNgay char(10)   \n "+      
		   "   set @ThangKs = '"+thangks+"'   \n "+      
		   "   set @NamKs   =  '"+namks+"'   \n "+      
		   "   set @nppId=('"+npp+"' )   \n "+      
		   "   select top(1) @Thang=THANGKS,@Nam=NAM from KHOASOTHANG where NPP_FK=@nppId order by NAM desc,THANGKS desc   \n "+      
		   "   set @TuNgay=cast(@Nam as CHAR(4))+'-'+case    \n "+      
		   "   	when len(dbo.Trim( cast( @Thang as varchar(2)) )) <2 then '0' +cast( @Thang as varchar(2)) else cast( @Thang as varchar(2)) end +'-01'   \n "+      
		   "      \n "+      
		   "   select @TuNgay =(select convert(varchar(10),DATEADD(month,1,@TuNgay),20) )      \n "+      
		   " select @DenNgay= convert(varchar(10), DATEADD(DD,-1,DATEADD(mm,1,@TuNgay)) ,20)    \n"+
		   " 	insert into TONKHOTHANG_CHITIET(NPP_FK,KBH_FK,KHO_FK,SANPHAM_FK,SOLO,NGAYHETHAN,THANG,NAM,SOLUONG) "+
			 "	select DATA.NPP_FK,KBH_FK ,DATA.KHO_FK,DATA.SANPHAM_FK,SoLo,NgayHetHan,@ThangKs,@NamKs,DATA.soluong as TonCuoi   \n "+      
			 "   from    \n "+      
			 "   (   \n "+      
			 "   	select xnt.NPP_FK,KBH_FK,xnt.KHO_FK,xnt.sanpham_fk,SUM(soluong) as soluong ,SoLo,NgayHetHan      \n "+      
			 "   	from         \n "+      
			 "   	(         \n "+      
			 "   		select  npp_fk, KBH_FK,kho_fk, SANPHAM_FK,SOLO as SoLo, sum(soluong) as soluong ,N'A.Tồn đầu' as type,NGAYHETHAN as NgayHetHan			       \n "+      
			 "   		from TONKHOTHANG_CHITIET      \n "+      
			 "   		where   NPP_FK=@nppId and THANG=@Thang and NAM=@Nam       \n "+      
			 "   		group by  npp_fk, kho_fk, SANPHAM_FK,SOLO,NGAYHETHAN,KBH_FK      \n "+      
			 "   	      \n "+      
			"   		union all         \n "+      
			"   		      \n "+      
			"   		select b.npp_fk,b.KBH_FK,a.khonhan_fk as kho_fk,c.pk_seq as sanpham_fk,a.SOLO, sum(cast(soluongnhan as int)) as soluong,     \n "+      
			"   			N'B.Nhập hàng' as type  ,NGAYHETHAN        \n "+      
			"   		from nhaphang_sp a inner join nhaphang b on a.nhaphang_fk = b.pk_seq		         \n "+      
			"   			inner join sanpham c on c.pk_seq = a.sanpham_fk          \n "+      
			"   		where   b.trangthai =1 and b.NGAYNHAN>=@TuNgay and b.NGAYNHAN<=@DenNgay   \n "+      
			"   		group by b.npp_fk,b.KBH_FK,c.pk_seq,a.khonhan_fk ,SOLO	   ,NGAYHETHAN      \n "+      
			"         \n "+      
			"   		union all         \n "+      
			"         \n "+      
			"   		select ck.npp_fk, ck.KBH_FK,ck.khoxuat_fk as kho_Fk, ck.sanpham_fk ,ck.solo,(-1)*sum(soluong) as SoLuong,N'C.Xuất chuyển nội bộ' as Type ,      \n "+      
			"   			ngayhethan        \n "+      
			"   		from          \n "+      
			"   		(         \n "+      
			"   			select  c.npp_fk, c.kbh_fk, c.khoxuat_fk, a.sanpham_fk,  a.soluong    ,a.solo,a.ngayhethan      \n "+      
			"   			from ERP_CHUYENKHONPP_SANPHAM_CHITIET a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ            \n "+      
			"   			inner join ERP_CHUYENKHONPP c on a.chuyenkho_fk = c.pk_seq            \n "+      
			"   			where c.trangthai=1 and  c.NgayChuyen>=@TuNgay  and c.NPP_FK=@nppId   and c.NgayChuyen<=@DenNgay    \n "+      
			"   		)as ck         \n "+      
			"   		group by ck.npp_fk, ck.khoxuat_fk, ck.sanpham_fk ,ck.solo	,ck.ngayhethan,ck.KBH_FK      \n "+      
			"   		         \n "+      
			"   		union all      \n "+      
			"   	      \n "+      
			"   		select a.npp_fk,a.kbh_fk,a.kho_fk,b.sanpham_fk,b.solo,SUM(b.Soluong) as SoLuong,N'E.Đổi số lô(+)' as Type,b.ngayhethan      \n "+      
			"   		from ERP_DOISOLONPP a inner join ERP_DOISOLONPP_SANPHAM b on b.doisolo_fk=a.pk_seq      \n "+      
			"   		where a.trangthai=1 and ngaydoi>=@TuNgay     and ngaydoi<=@DenNgay   \n "+      
			"   		group by a.npp_fk,a.kbh_fk,a.kho_fk,b.sanpham_fk,b.solo,b.ngayhethan      \n "+      
			"         \n "+      
			"   		union all 			      \n "+      
			"   			select a.npp_fk,a.kbh_fk,a.kho_fk,b.sanpham_fk,b.soloOLD,(-1)*SUM(b.Soluong) as SoLuong,N'F.Đổi số lô(-)' as Type,NgayHetHanOLD      \n "+      
			"   			from ERP_DOISOLONPP a inner join ERP_DOISOLONPP_SANPHAM b on b.doisolo_fk=a.pk_seq      \n "+      
			"   			where a.trangthai=1 and ngaydoi>=@TuNgay and ngaydoi<=@DenNgay       \n "+      
			"   			group by a.npp_fk,a.kbh_fk,a.kho_fk,b.sanpham_fk,b.soloOLD,NgayHetHanOLD      \n "+      
			"   	      \n "+      
			"   		union all       \n "+      
			"         \n "+      
			"   		select  a.NPP_FK,a.KBH_FK,a.kho_fk, b.SANPHAM_FK, b.solo,-(1)*SUM(b.soluong) as soluong ,'ETC'  as NghiepVu,ngayhethan   \n "+      
			"   		from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.PK_SEQ = b.YCXK_FK     \n "+      
			"   		where  b.soluong > 0    \n "+      
			"   			and a.NPP_FK=@nppId and a.PK_SEQ in (Select ycxk_fk from ERP_YCXUATKHONPP_DDH  where ddh_fk in    \n "+      
			"   			(select ddh_fk from ERP_HOADONNPP_DDH where HOADONNPP_FK in (select PK_SEQ from ERP_HOADONNPP where TRANGTHAI not IN (3,5)   \n "+      
			"   			and NGAYXUATHD>=@TuNgay and NGAYXUATHD<=@DenNgay   \n "+      
			"   			)))and a.TRANGTHAI!=3   \n "+      
			"   		group by a.kho_fk, a.NPP_FK, b.SANPHAM_FK, b.solo,ngayhethan,a.KBH_FK       \n "+      
			"   		union all         \n "+      
			"   	      \n "+      
			"   		SELECT DTH.NPP_FK ,DTH.KBH_FK,DTH.KHO_FK ,THSP.SANPHAM_FK,THSP.SoLo,(-1)* SUM(THSP.SOLUONG) AS SOLUONG ,N'H.Trả hàng' AS TYPE ,NgayHetHan      \n "+      
			"   		FROM DONTRAHANG_SP THSP INNER JOIN  DONTRAHANG DTH ON  DTH.PK_SEQ = THSP.DONTRAHANG_FK         \n "+      
			"   		WHERE DTH.TRANGTHAI =2 AND DTH.NGAYTRA >=@TuNgay  AND THSP.SOLUONG > 0       and DTH.NGAYTRA<=@DenNgay   \n "+      
			"   		GROUP BY DTH.NPP_FK,DTH.KBH_FK,THSP.SANPHAM_FK,DTH.KHO_FK,THSP.SoLo,NgayHetHan	         \n "+      
			"         \n "+      
			"   		union all         \n "+      
			"         \n "+      
			"   		SELECT  DCTK.NPP_FK,DCTK.KBH_FK,DCTK.KHO_FK,DCTK_SP.SANPHAM_FK , DCTK_SP.SOLO,      \n "+      
			"   			SUM( CAST( ISNULL(DCTK_SP.DIEUCHINH,0) AS INT) ) AS SOLUONG, N'I.Kiểm kho' AS TYPE 	     ,NgayHetHan      \n "+      
			"   		FROM	DIEUCHINHTONKHO DCTK  INNER JOIN DIEUCHINHTONKHO_SP DCTK_SP ON DCTK_SP.DIEUCHINHTONKHO_FK = DCTK.PK_SEQ          \n "+      
			"   		WHERE   DCTK.TRANGTHAI =1 AND DCTK.NGAYDC >= @TuNgay   and DCTK.NGAYDC<=@DenNgay   \n "+      
			"   		GROUP BY  DCTK.NPP_FK,DCTK.KBH_FK,DCTK.KHO_FK,DCTK_SP.SANPHAM_FK ,DCTK_SP.SOLO  ,NgayHetHan      \n "+      
			"         \n "+      
			"   		union all         \n "+      
			"   		      \n "+      
			"   		select NPP_FK,hd.KBH_FK,kho_Fk,sanpham_fk,SoLo,(-1)*SUM(SoLuong) as SoLuong,N'J.Xuất bán ' AS Type,NGAYHETHAN as NgayHetHan      \n "+      
			"   		from       \n "+      
			"   		(      \n "+      
			"   			select  a.KBH_FK,a.NPP_FK,c.PK_SEQ as sanpham_fk,      \n "+      
			"   				(      \n "+      
			"   					select top(1) kho_fk from DONHANG aa inner join HOADON_DDH bb on bb.DDH_FK=aa.PK_SEQ      \n "+      
			"   						where bb.HOADON_FK=a.PK_SEQ      \n "+      
			"   				) as kho_Fk,      \n "+      
			"   			case when len(dbo.Trim(b.SOLO))=0 then 'NA' ELSE dbo.Trim(b.SOLO) end  as SoLo,      \n "+      
			"   				b.SOLUONG as SoLuong,      \n "+      
			"   				case when len(dbo.Trim(b.SOLO))=0 then '2030-12-31' ELSE dbo.Trim(b.NGAYHETHAN) end  as NGAYHETHAN      \n "+      
			"   			from  HOADON a inner join HOADON_SP_CHITIET b on b.hoadon_fk=a.PK_SEQ      \n "+      
			"   				inner join SANPHAM c on c.MA=b.MA      \n "+      
			"   				where a.NPP_FK=@nppId and a.NGAYXUATHD>=@TuNgay  and a.NGAYXUATHD<=@DenNgay   \n "+      
			"   				and a.TRANGTHAI not in (3,5) and a.LOAIHOADON=0      \n "+      
			"   		) hd      \n "+      
			"   		group by hd.NPP_FK,hd.SANPHAM_FK,hd.KHO_FK,hd.SOLO	,NGAYHETHAN	,hd.KBH_FK      \n "+      
			"   		      \n "+      
			"   		union all      \n "+      
			"   		      \n "+      
			"   		select NPP_FK,kbh_fk,kho_Fk,sanpham_fk,SoLo as SoLo,(-1)*SUM(SoLuong) as SoLuong,N'Xuất KM' AS NghiepVu,NgayHetHan   \n "+      
			"   		from    \n "+      
			"   		(   \n "+      
			"   			select	a.npp_fk,c.pk_Seq as sanpham_Fk,b.kho_Fk,b.kbh_fk,   \n "+      
			"   			case when len(dbo.Trim(b.SOLO))=0 then 'NA' ELSE dbo.Trim(b.SOLO) end  as SoLo,   \n "+      
			"   				b.SOLUONG as SoLuong,case when len(dbo.Trim(b.SOLO))=0 then '2030-12-31' ELSE dbo.Trim(b.ngayhethan) end  as NgayHetHan   \n "+      
			"   			from  HOADON a inner join HOADON_CTKM_TRAKM_CHITIET b on b.hoadonID=a.PK_SEQ   \n "+      
			"   				inner join SANPHAM c on c.PK_SEQ=b.sanpham_fk   \n "+      
			"   				where a.NPP_FK=@nppId and a.NGAYXUATHD>=@TuNgay and a.NGAYXUATHD<=@DenNgay   \n "+      
			"   				and a.TRANGTHAI not in (3,5) and a.LOAIHOADON in (1,2)   \n "+      
			"   		) hd   \n "+      
			"   		group by hd.NPP_FK,hd.SANPHAM_FK,hd.KHO_FK,hd.SoLo,NgayHetHan,kbh_fk   \n "+      
			"   		      \n "+      
			"   		union all      \n "+      
			"   		      \n "+      
			"   		select a.NPP_FK,a.KBH_FK,a.KhoXuat_FK, b.sanpham_fk,b.solo,(-1)* SUM(b.soluong) as tongxuat ,N'L.Đổi kênh(-)' as Type,b.ngayhethan      \n "+      
			"   		from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk       \n "+      
			"   		where a.trangthai=1 and a.NgayChuyen>=@TuNgay and a.NgayChuyen<=@DenNgay and a.KHONHAN_FK is  null            \n "+      
			"   		group by a.khoxuat_fk, a.KBH_FK,a.npp_fk, b.sanpham_fk,b.solo,b.ngayhethan      \n "+      
			"   		      \n "+      
			"   		union all      \n "+      
			"   		select a.NPP_FK,a.KBH_FK,a.KhoXuat_FK, b.sanpham_fk,b.solo,SUM(b.soluong) as tongxuat ,N'M.Đổi kênh(+)' as Type,b.ngayhethan      \n "+      
			"   		from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk       \n "+      
			"   		where a.trangthai=1 and a.NgayChuyen>=@TuNgay and a.NgayChuyen<=@DenNgay and a.KHONHAN_FK is  null            \n "+      
			"   		group by a.khoxuat_fk, a.KBH_FK,a.npp_fk, b.sanpham_fk,b.solo,b.ngayhethan      \n "+      
			"   		      \n "+      
			"   		union all       \n "+      
			"   		select a.NPP_FK,a.KBH_FK,a.KhoXuat_FK, b.sanpham_fk,b.solo,(-1)* SUM(b.soluong) as tongxuat ,N'N.Chuyển kho(-)' as Type,b.ngayhethan      \n "+      
			"   		from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk                 \n "+      
			"   		where a.trangthai=1 and a.NgayChuyen>=@TuNgay and a.NgayChuyen<=@DenNgay     and a.KHONHAN_FK is not null            \n "+      
			"   		group by a.khoxuat_fk, a.KBH_FK,a.npp_fk, b.sanpham_fk  ,solo,b.ngayhethan              \n "+      
			"   		union all                \n "+      
			"   		select a.NPP_FK,a.KBH_FK,a.KHONHAN_FK, b.sanpham_fk, solo,SUM(b.soluong) as tongxuat ,N'O.Chuyển kho(+)' as Type,b.ngayhethan      \n "+      
			"   		from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk                 \n "+      
			"   		where a.trangthai=1 and a.NgayChuyen>=@TuNgay and a.NgayChuyen<=@DenNgay  and a.KHONHAN_FK is not null            \n "+      
			"   		group by a.KHONHAN_FK,a.KBH_FK, a.npp_fk, b.sanpham_fk,solo ,ngayhethan      \n "+      
			"   		      \n "+      
			"   		union all      \n "+      
			"   		      \n "+      
			"   		select  a.npp_fk,a.kbh_fk,a.kho_fk,b.sanpham_Fk,b.solo ,SUM(b.SoLuong) as SoLuong,N'P.Hàng trả lại' as Type,b.NgayHetHAN          \n "+      
			"   		from Erp_HangTraLaiNpp a inner join Erp_HangTraLaiNpp_SanPham b on b.hangtralai_fk=a.pk_Seq      \n "+      
			"   		where a.trangthai=1  and a.ngaytra>=@TuNgay  and a.ngaytra<=@DenNgay  and a.npp_fk=@nppId      \n "+      
			"   		group by a.npp_fk,a.kbh_fk,b.sanpham_Fk,a.kho_fk,b.SoLo,b.NgayHetHAN      \n "+      
			"	union all  "+
			" select a.TrucThuoc_FK as npp_fk,a.KBH_FK ,a.KhoXuat_FK as Kho_FK,b.sanpham_fk,b.solo,sum(b.soluong) as SoLuong,N'Xuất hàng khác' as NghiepVu,b.NgayHetHan  "+
			" from ERP_CHUYENKHO a inner join ERP_CHUYENKHO_SANPHAM_CHITIET b on b.chuyenkho_fk=a.PK_SEQ  "+
			" where a.TRANGTHAI=1 and a.TrucThuoc_FK=@nppId and a.NgayChuyen>=@TuNgay and a.NgayChuyen<=@DenNgay  "+
			" group by a.KhoXuat_FK,b.sanpham_fk,a.TrucThuoc_FK,a.KBH_FK,b.solo,b.ngayhethan "+
			"   	)as xnt      \n "+      
			"   	group by xnt.NPP_FK,xnt.KHO_FK,xnt.sanpham_fk,xnt.KBH_FK ,SoLo,NgayHetHan     \n "+      
			"   ) as DATA    \n "+      
			"   where DATA.NPP_FK=@nppId ";
			
			System.out.println("XNT_CT_"+query);
			
			if(db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback();
		    	System.out.println( "Không thể thiết lập khóa sổ tháng " + query);
			}			
			
			query = "insert into khoasothang(thangks, nam, ngaytao, nguoitao, npp_fk) " +
					"values('" + thangks + "', '" + namks + "', '2015-27-04', null," + npp + ")";
			System.out.println("Query khoa so thang la: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				System.out.println("Không thể thiết lập khóa sổ tháng " + query);
				query="insert into Log_khoasothang (nppId,lydo,thoigian) values ("+npp+",N'Không thể thiết lập khóa sổ tháng',GETDATE())";
				if(!db.update(query))
				System.out.println("khong the insert Log_khoasothang " +query);
				
			}	
			
			query=
		"	select d.MA as nppMA,d.TEN as nppTEN,c.MA as spMa,c.TEN as spTEN,a.SoLuong as Tong,b.SoLuong as CT  "+
		"	from  "+
		"	(  "+
		"		select a.NPP_FK,a.SANPHAM_FK,SUM(SOLUONG) as SoLuong,KHO_FK  "+
		"		from TONKHOTHANG a " +
		"   where a.npp_fk='"+npp+"' and thang='"+thangks+"' and nam='"+namks+"'        "+
		"		group by NPP_FK,SANPHAM_FK,KHO_FK "+
		"	)as a full outer join  "+ 
		"	(  "+
		"		select a.NPP_FK,a.SANPHAM_FK,SUM(SOLUONG) as SoLuong,KHO_FK  "+
		"		from TONKHOTHANG_CHITIET a   "+
		"   where a.npp_fk='"+npp+"' and thang='"+thangks+"' and nam='"+namks+"'        "+
		"		group by NPP_FK,SANPHAM_FK,KHO_FK   "+
		"	)as b on a.NPP_FK=b.NPP_FK and a.SANPHAM_FK=b.SANPHAM_FK  and a.KHO_FK=b.KHO_FK  "+
		"	left join SANPHAM c on c.PK_SEQ=ISNULL(a.SANPHAM_FK,b.SANPHAM_FK)  "+ 
		"	LEFT join NHAPHANPHOI d on d.PK_SEQ=ISNULL(a.NPP_FK,b.NPP_FK)  "+
		" where cast(ISNULL(a.SoLuong,0) as numeric (18,0)) <> cast (isnull(b.SoLuong,0) as numeric (18,0)) ";
			
			System.out.println("___Query__"+query);
			
			ResultSet rs =db.get(query);
			String msg="";
			while(rs.next())
			{
				msg+=""+rs.getString("spMa") +" - "+rs.getString("spTen")+" \n";
			}
			rs.close();
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				msg +="Lỗi phát sinh do lệch số lượng của sản phẩm theo XNT TỔNG và XNT CT"+msg ;
				query="insert into Log_khoasothang (nppId,lydo,thoigian) values ("+npp+","+msg+",GETDATE())";
				if(!db.update(query))
				System.out.println("khong the insert Log_khoasothang " + query);
		
				
			}
			
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
			
		} 
		catch(Exception e) 
		{
			//this.msg = "Lỗi khi khóa sổ tháng: " + e.getMessage();
			System.out.println(e.getMessage());
			db.update("rollback");
			e.printStackTrace();
			
		}

		
		if(thangks == 12)
		{
			namks=namks+1;
			thangks=1;
		}
		else
		{
			thangks+=1;
		}
		
		}
		if(db!=null)db.shutDown();
	}

	

}