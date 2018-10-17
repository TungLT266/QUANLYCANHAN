package geso.traphaco.center.util;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.util.DinhDang;
import geso.traphaco.erp.util.Kho_Lib;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
 
public class Utility_Kho implements Serializable
{ 		
	private static final long serialVersionUID = 1L;

/*	public static void main(String []arg)
	{
		String str = "100.0";
		
		System.out.println("main");
		String  mang[] ={"111998" ,"111876","111997","112206","111520" ,"111894","112002"};
		for(int i=0;i<mang.length;i++)
		{
			XoaDelete(mang[i]);
		}
	
	}*/
	
	public static String XoaDelete(String dmhId)
	{
		dbutils db = new dbutils();
		Utility_Kho util_kho=new Utility_Kho();
		Utility util =new Utility();
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "select COUNT(*) as sodong from ERP_NHANHANG where MUAHANG_FK = '" + dmhId + "'";
			System.out.println("Query mua hang: " + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			}
			
		 
			if(sodong > 0)
			{
				db.getConnection().rollback();
				return "Đơn trả hàng này đã có nhận hàng, bạn phải xóa nhận hàng trước khi xóa đơn trả hàng này";
			}

			//truoc khi delete
			query = " select isnull(MH.LOAIHANGHOA_FK,'1') as LOAIHANGHOA_FK , MHSP.sanpham_fk, MHSP.soluong, MHSP.khott_fk " +
					" from ERP_MUAHANG_SP MHSP " +
					" INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ=MHSP.MUAHANG_FK  where muahang_fk = '" + dmhId + "'";
			 
			ResultSet spRs = db.get(query);
		 
				while(spRs.next())
				{
					String sp_fk = spRs.getString("sanpham_fk");
					 
					String khott_fk = spRs.getString("khott_fk");
					
					if(spRs.getString("LOAIHANGHOA_FK").trim().equals("0")){
					
						 
						double available_= spRs.getDouble("soluong");
						double booked_=(-1)*spRs.getDouble("soluong");
						 
						
						String msg1=util_kho.Update_Kho_Sp(db,  khott_fk, sp_fk, 0,booked_,available_, 0);
						
						if(msg1.length()>0)
						{
							 
							db.getConnection().rollback();
							return msg1;
						}
						
					}
				}
				spRs.close();
				
			query = "update ERP_MUAHANG set trangthai = '4' where pk_seq = '" + dmhId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong the cap nhat ERP_MUAHANG: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			db.shutDown(); 
			return "Khong the xoa don mua hang"; 
		}
		
	}
	
	//SUA HAM NAY THI SUA BEN DUOI LUON NHE, DO 2 CHO DUNG DB KHAC NHAU
	public String[] getGiaChayKT(String ngaychot, Idbutils db,String congtyid, String nppid, String spid, String solo){
		String[] str=new String[]{"0",""};
//		 String chuoi=" nppid : "+nppid+"- spid : "+spid +"-solo : "+solo;

		try{

			String nam = ngaychot.substring(0, 4);
			String thang = ngaychot.substring(5, 7);

			//KE TOAN TU DONG PHAT SINH

			int namOLD = Integer.parseInt(nam);
			int thangOLD = Integer.parseInt(thang);

			if(thangOLD == 1)
			{
				thangOLD = 12;
				namOLD = namOLD - 1;
			}
			else
			{
				thangOLD = thangOLD - 1;
			}


			String query=" SELECT DISTINCT ISNULL(GIATON,0) AS GIATON ,SP.MA +' - '+SP.TEN AS TEN  FROM ERP_TONKHOTHANG_CHITIET " +
						" INNER JOIN SANPHAM SP ON  SP.PK_SEQ=SANPHAM_FK  "+  
						" WHERE  NPP_FK="+nppid+" AND SANPHAM_FK="+spid+" AND SOLO='"+solo+"' and thang ="+thangOLD+" and nam="+namOLD +
						"  UNION  " +
						" SELECT DISTINCT ISNULL(GIATON,0) AS GIATON ,SP.MA +' - '+SP.TEN AS TEN  FROM ERP_TONKHOTHANG_KYGUI_CHITIET " +
						" INNER JOIN SANPHAM SP ON  SP.PK_SEQ=SANPHAM_FK  "+  
						" WHERE    NPP_FK="+nppid+" AND SANPHAM_FK="+spid+" AND SOLO='"+solo+"' and thang ="+thangOLD+" and nam="+namOLD  + 
						" ORDER BY GIATON DESC ";

			ResultSet rs=db.get(query);
			int bien=0;
			double gia=0;
			String ten="";
		if(rs.next()){
				gia=rs.getDouble("GIATON");
				ten=rs.getString("ten");
				bien++;
			}
			rs.close();
			
			str[0]=""+gia;
			str[1]="";
			
 
			
			if(bien==0){
				// chưa có giá tồn thì lấy giá nhập gần nhất

				 

				query = "  SELECT DISTINCT GIATHEOLO, TEN   FROM (  " +
						"  SELECT ISNULL(GIAMUA,0)  as GIATHEOLO ,SP.MA +' - '+SP.TEN AS TEN  FROM NHAPP_KHO_CHITIET KHO " +
						"  INNER JOIN SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK   WHERE NPP_FK = "+nppid+"  AND nhomkenh_fk = '100000' AND SANPHAM_FK = '"+spid+"'  AND SOLO = '"+solo+"' AND KHO_FK in (100000)  " +
						"  union  " +
						"  SELECT ISNULL(GIAMUA,0)  as GIATHEOLO ,SP.MA +' - '+SP.TEN AS TEN  FROM NHAPP_KHO_KYGUI_CHITIET KHO " +
						"  INNER JOIN SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK   WHERE NPP_FK = "+nppid+"  AND nhomkenh_fk = '100000' AND SANPHAM_FK = '"+spid+"'  AND SOLO = '"+solo+"' AND KHO_FK=100003  " +
						"  ) AS A ORDER BY GIATHEOLO DESC  " ;



				rs=db.get(query);
				ten="";

				if(rs.next()){
					gia=rs.getDouble("GIATHEOLO");
					ten="";
					 
				}
				 rs.close();

				 
				str[0]=""+gia;
				str[1]="";
			 
				return str;
			}

		
			
			/*
			String nam = ngaychot.substring(0, 4);
			String thang = ngaychot.substring(5, 7);

			//KE TOAN TU DONG PHAT SINH

			int namOLD = Integer.parseInt(nam);
			int thangOLD = Integer.parseInt(thang);

			if(thangOLD == 1)
			{
				thangOLD = 12;
				namOLD = namOLD - 1;
			}
			else
			{
				thangOLD = thangOLD - 1;
			}


			String query=" SELECT DISTINCT ISNULL(GIATON,0) AS GIATON ,SP.MA +' - '+SP.TEN AS TEN  FROM ERP_TONKHOTHANG_CHITIET " +
						" INNER JOIN SANPHAM SP ON  SP.PK_SEQ=SANPHAM_FK  "+  
						" WHERE  NPP_FK="+nppid+" AND SANPHAM_FK="+spid+" AND SOLO='"+solo+"' and thang ="+thangOLD+" and nam="+namOLD +
						"  UNION  " +
						" SELECT DISTINCT ISNULL(GIATON,0) AS GIATON ,SP.MA +' - '+SP.TEN AS TEN  FROM ERP_TONKHOTHANG_KYGUI_CHITIET " +
						" INNER JOIN SANPHAM SP ON  SP.PK_SEQ=SANPHAM_FK  "+  
						" WHERE    NPP_FK="+nppid+" AND SANPHAM_FK="+spid+" AND SOLO='"+solo+"' and thang ="+thangOLD+" and nam="+namOLD  + 
						" ORDER BY GIATON DESC ";

			ResultSet rs=db.get(query);
			int bien=0;
			double gia=0;
			String ten="";
			while(rs.next()){
				gia=rs.getDouble("GIATON");
				ten=rs.getString("ten");
				bien++;
			}
			rs.close();

			if(bien >1){
				//Có 2 giá tồn theo lô,thì biết lấy giá nào

				str[0]="0";
				str[1]="1 .Có nhiều giá tồn kho theo lô,vui lòng liên lạc với admin để được trợ giúp trường hợp này"+chuoi;
				return str;
			}
			if(bien==1){

				if(gia>0){
					str[0]=""+gia;
					str[1]="";
				}
				else{
					str[0]=""+gia;
					str[1]="1. Gía tồn kho của sản phẩm ["+ten+"] này = 0 , vui lòng vào dữ liệu nền sản phẩm để cập nhật lại giá đầu kỳ" +chuoi;
				}
				return str;
			}
			if(bien==0){
				// chưa có giá tồn thì lấy giá nhập gần nhất

				query=	" SELECT ISNULL(NHCT.GIATHEOLO,0) AS GIATHEOLO FROM ERP_NHANHANG_SP_CHITIET NHCT "+  
						" INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ= NHCT.NHANHANG_FK "+
						" WHERE NH.TRANGTHAI  IN (1,2) AND NH.NPP_FK="+nppid+" "+
						" AND NH.congty_fk="+congtyid+" AND NHCT.SANPHAM_FK ="+spid+" AND NHCT.SOLO='"+solo+"' "+
						" ORDER BY NH.NGAYNHAN DESC";


				query = "  SELECT DISTINCT GIATHEOLO, TEN   FROM (  " +
						"  SELECT ISNULL(GIAMUA,0)  as GIATHEOLO ,SP.MA +' - '+SP.TEN AS TEN  FROM NHAPP_KHO_CHITIET KHO " +
						"  INNER JOIN SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK   WHERE NPP_FK = '"+nppid+"'  AND nhomkenh_fk = '100000' AND SANPHAM_FK = '"+spid+"'  AND SOLO = '"+solo+"' AND KHO_FK in (100000)  " +
						"  union  " +
						"  SELECT ISNULL(GIAMUA,0)  as GIATHEOLO ,SP.MA +' - '+SP.TEN AS TEN  FROM NHAPP_KHO_KYGUI_CHITIET KHO " +
						"  INNER JOIN SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK   WHERE NPP_FK = '"+nppid+"'  AND nhomkenh_fk = '100000' AND SANPHAM_FK = '"+spid+"'  AND SOLO = '"+solo+"' AND KHO_FK=100003  " +
						"  ) AS A ORDER BY GIATHEOLO DESC  " ;



				rs=db.get(query);
				ten="";

				while(rs.next()){
					gia=rs.getDouble("GIATHEOLO");
					ten=rs.getString("ten");
					bien++;
				}
				if(bien >1){
					//Có 2 giá tồn theo lô,thì biết lấy giá nào

					str[0]="0";
					str[1]="2.Có nhiều giá tồn kho theo lô,vui lòng liên lạc với admin để được trợ giúp trường hợp này "+chuoi;
					return str;
				}
				if(bien==1){

					if(gia>0){
						str[0]=""+gia;
						str[1]="";
					}
					else{
						str[0]=""+gia;
						str[1]="2.Gía tồn kho của sản phẩm ["+ten+"] này = 0 , vui lòng vào dữ liệu nền sản phẩm để cập nhật lại giá đầu kỳ" +chuoi;
					}
					return str;
				}
				if(bien==0){
					str[1]="2.Chưa xác định được giá tồn kho của sản phẩm ["+ten+"] ở đầu kỳ và các nghiệp vụ nhập kho , vui lòng vào dữ liệu nền sản phẩm để cập nhật lại giá đầu kỳ" +chuoi;
				}

					if(rs.next()){
							gia=rs.getDouble("GIATHEOLO");
							ten=rs.getString("ten");
							if(gia >0){

							 str[0]=gia+"";
							}
							else{
								str[0]=""+gia;
								str[1]="Gía nhập gần nhất của sản phẩm ["+ten+"]  = 0 , vui lòng kiểm tra lại giá nhập";
							}

						}else{
							str[1]="Sản phẩm không có giá đầu kỳ, và không xác định được giá nhập gần nhất thuộc công ty, vui lòng kiểm tra lại";

						}
				rs.close();

				return str;
			}

		*/}catch(Exception er){
			er.printStackTrace();
			str[0]="0";
			str[1]="Lỗi không xác định giá tồn kho: "+er.getMessage();
			return str;

		}
		return str;
	}


	public String[] getGiaChayKT(String ngaychot, geso.traphaco.distributor.db.sql.dbutils db,String congtyid, String nppid, String spid, String solo){
		 
		String[] str=new String[]{"0",""};
		 String chuoi=" nppid : "+nppid+"- spid : "+spid +"-solo : "+solo;

		try{
			String nam = ngaychot.substring(0, 4);
			String thang = ngaychot.substring(5, 7);

			//KE TOAN TU DONG PHAT SINH

			int namOLD = Integer.parseInt(nam);
			int thangOLD = Integer.parseInt(thang);

			if(thangOLD == 1)
			{
				thangOLD = 12;
				namOLD = namOLD - 1;
			}
			else
			{
				thangOLD = thangOLD - 1;
			}


			String query=" SELECT DISTINCT ISNULL(GIATON,0) AS GIATON ,SP.MA +' - '+SP.TEN AS TEN  FROM ERP_TONKHOTHANG_CHITIET " +
						" INNER JOIN SANPHAM SP ON  SP.PK_SEQ=SANPHAM_FK  "+  
						" WHERE  NPP_FK="+nppid+" AND SANPHAM_FK="+spid+" AND SOLO='"+solo+"' and thang ="+thangOLD+" and nam="+namOLD +
						"  UNION  " +
						" SELECT DISTINCT ISNULL(GIATON,0) AS GIATON ,SP.MA +' - '+SP.TEN AS TEN  FROM ERP_TONKHOTHANG_KYGUI_CHITIET " +
						" INNER JOIN SANPHAM SP ON  SP.PK_SEQ=SANPHAM_FK  "+  
						" WHERE    NPP_FK="+nppid+" AND SANPHAM_FK="+spid+" AND SOLO='"+solo+"' and thang ="+thangOLD+" and nam="+namOLD  + 
						" ORDER BY GIATON DESC ";

			ResultSet rs=db.get(query);
			int bien=0;
			double gia=0;
			String ten="";
			if(rs.next()){
				gia=rs.getDouble("GIATON");
				ten=rs.getString("ten");
				bien++;
			}
			rs.close();
			
			str[0]=""+gia;
			str[1]="";
			

			/*	if(bien >1){
				//Có 2 giá tồn theo lô,thì biết lấy giá nào

				str[0]=""+gia;
				//str[1]="1 .Có nhiều giá tồn kho theo lô,vui lòng liên lạc với admin để được trợ giúp trường hợp này"+chuoi;
				str[1]="";
				return str;
			}
			if(bien==1){

				if(gia>0){
					str[0]=""+gia;
					str[1]="";
				}
				else{
					str[0]=""+gia;
					//str[1]="1. Gía tồn kho của sản phẩm ["+ten+"] này = 0 , vui lòng vào dữ liệu nền sản phẩm để cập nhật lại giá đầu kỳ" +chuoi;
					str[1]="";
				}
				return str;
			}*/
			
			
			if(bien==0){
				// chưa có giá tồn thì lấy giá nhập gần nhất

			  
				query = "  SELECT DISTINCT GIATHEOLO, TEN   FROM (  " +
						"  SELECT ISNULL(GIAMUA,0)  as GIATHEOLO ,SP.MA +' - '+SP.TEN AS TEN  FROM NHAPP_KHO_CHITIET KHO " +
						"  INNER JOIN SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK   WHERE NPP_FK = "+nppid+"  AND nhomkenh_fk = '100000' AND SANPHAM_FK = '"+spid+"'  AND SOLO = '"+solo+"' AND KHO_FK in (100000)  " +
						"  union  " +
						"  SELECT ISNULL(GIAMUA,0)  as GIATHEOLO ,SP.MA +' - '+SP.TEN AS TEN  FROM NHAPP_KHO_KYGUI_CHITIET KHO " +
						"  INNER JOIN SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK   WHERE NPP_FK = "+nppid+"  AND nhomkenh_fk = '100000' AND SANPHAM_FK = '"+spid+"'  AND SOLO = '"+solo+"' AND KHO_FK=100003  " +
						"  ) AS A ORDER BY GIATHEOLO DESC  " ;



				rs=db.get(query);
				ten="";

				if(rs.next()){
					gia=rs.getDouble("GIATHEOLO");
					ten="";
				}
				 rs.close();

				 
				str[0]=""+gia;
				str[1]="";
				
				
				/*if(bien >1){
					//Có 2 giá tồn theo lô,thì biết lấy giá nào

					str[0]="0";
					str[1]="2.Có nhiều giá tồn kho theo lô,vui lòng liên lạc với admin để được trợ giúp trường hợp này "+chuoi;
					return str;
				}
				if(bien==1){

					if(gia>0){
						str[0]=""+gia;
						str[1]="";
					}
					else{
						str[0]=""+gia;
						str[1]="2.Gía tồn kho của sản phẩm ["+ten+"] này = 0 , vui lòng vào dữ liệu nền sản phẩm để cập nhật lại giá đầu kỳ" +chuoi;
					}
					return str;
				}
				if(bien==0){
					str[1]="2.Chưa xác định được giá tồn kho của sản phẩm ["+ten+"] ở đầu kỳ và các nghiệp vụ nhập kho , vui lòng vào dữ liệu nền sản phẩm để cập nhật lại giá đầu kỳ" +chuoi;
				}*/

				
				return str;
			}

		}catch(Exception er){
			er.printStackTrace();
			str[0]="0";
			str[1]="Lỗi không xác định giá tồn kho: "+er.getMessage();
			return str;

		}
		return str;
		 
	}

	public String Update_Kho_Sp(Idbutils db, String khott_fk, String spId,
			double soluong ,double booked,double available , double dongia) {
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIATON,0) AS GIATON   from erp_khott_sanpham kho " +
					"  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where khott_fk="+khott_fk+" and sanpham_fk= "+spId;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				double soluongton=rsCheck.getDouble("soluong");
				double available_ton=rsCheck.getDouble("available");
				double giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(giaton >0){
					if( giaton- dongia !=0) {

						query=" insert into log_sql_khott(ngay,khott_fk,sanpham_fk ,ghichu ) " +
								" values( GETDATE(),"+khott_fk+","+spId+",N'đơn giá khác nhau giữa 2 kho :Giá cũ :"+giaton+". Giá mới :"+dongia+"' )";

						db.update(query);

					}
				} 

				query = " Update ERP_KHOTT_SANPHAM set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ",giaton="+(giaton >0?giaton:dongia)+"  "+
						" where khott_fk =" + khott_fk + " and sanpham_fk = " +   spId + "";
			}else{
				query=  " INSERT INTO ERP_KHOTT_SANPHAM ( KHOTT_FK,SANPHAM_FK,GIATON,SOLUONG,BOOKED,AVAILABLE,THANHTIEN ) VALUES  " +
						" ("+khott_fk+","+ spId+","+dongia+","+soluong+","+booked+","+available+","+soluong+"*"+dongia+")";
			}
			rsCheck.close();
			int resultInt = db.updateReturnInt(query);

			if(resultInt != 1)
			{
				return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
			}
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}


	public String Update_Kho_Sp_Chitiet(Idbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double booked,double available , double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau,String NgayNhapKho ,String NgaySanXuat,String NGAYHETHAN,
			double CPCAPDONG, double CPLUUKHO, double CPNHANHANG,
			double THUENHAPKHAU) {
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET ,cả đạt chất lượng và không đạt chất lượng.
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		try{

			vitri="100000";
			String	query = " select count(*) as sodong from ERP_KHOTT_SP_CHITIET " +
					" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " + SANPHAM_FK + " " +
					" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "' "+ (KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"") +" and NGAYBATDAU='"+NgayBatDau+"' ";
			System.out.println(query);

			ResultSet	rsCheck = db.get(query);
			boolean flag = false;
			if(rsCheck.next())
			{
				if(rsCheck.getInt("sodong") > 0) {
					flag = true;
				}

			}
			rsCheck.close();

			if(flag)
			{
				query = " update ERP_KHOTT_SP_CHITIET set booked=isnull(booked,0) + "+booked+" ,soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
						" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
						" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "' and BIN = '" + vitri + "' "+(KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"")+" and NGAYBATDAU='"+NgayBatDau+"' ";
			}
			else
			{
				query = "  insert ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG , BOOKED, AVAILABLE, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO,NGAYBATDAU " +
						"  ,BIN,KHUVUCKHO_FK ,DONGIAMUA, CPCAPDONG ,CPLUUKHO ,CPNHANHANG,THUENHAPKHAU ) " +
						"  VALUES ( " + khott_fk + " ,  " + SANPHAM_FK + " , " +soluongnhap+ ", "+booked+", " +available + ", '" + solo.trim() + "', '"+NgaySanXuat+"', '"+NGAYHETHAN+"'," +
						"  '"  + NgayNhapKho + "','"+NgayBatDau+"', '" + vitri + "' ,"+(KHUVUCKHO_FK.length() >0?KHUVUCKHO_FK:"NULL")+", " +
						+DONGIAMUA+","+CPCAPDONG+" ,"+CPLUUKHO+","+CPNHANHANG+","+THUENHAPKHAU+") " ;

			}	

			if(db.updateReturnInt(query)!=1)
			{
				return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
			}
			return "";
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet : "+er.getMessage();
		}
	}
	
	
	
	
	


	public String Update_Kho_Sp_Chitiet_TrangThai(Idbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double Booked,double available, double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau,String NgayNhapKho ,String NgaySanXuat,String NGAYHETHAN,
			double CPCAPDONG, double CPLUUKHO, double CPNHANHANG,
			double THUENHAPKHAU,String trangthai) {


		
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET_TRANGTHAI ,TRANGTHAI=-1 LÀ HÀNG KHÔNG ĐẠT,TRANGTHAI=0 LÀ HÀNG ĐẠT ,ĐỂ CHO CÁC BÁC VÀO CHUYỂN KHO CHỈ LẤY SỐ LƯỢNG ĐẠT THÔI
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		try{
			vitri="100000";
			String	query = " select count(*) as sodong from ERP_KHOTT_SP_CHITIET_TRANGTHAI " +
					" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " + SANPHAM_FK + " " +
					" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'   "+(KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"")+" and NGAYBATDAU='"+NgayBatDau+"' and trangthai="+trangthai+" ";
			ResultSet	rsCheck = db.get(query);
			boolean flag = false;
			if(rsCheck.next())
			{
				if(rsCheck.getInt("sodong") > 0) {
					flag = true;
				}

			}
			rsCheck.close();

			if(flag)
			{
				query = " update ERP_KHOTT_SP_CHITIET_TRANGTHAI set booked =isnull(booked,0)+ "+Booked+",soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
						" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
						" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'  "+(KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"")+"  and NGAYBATDAU='"+NgayBatDau+"' AND TRANGTHAI="+trangthai+"";
			}
			else
			{
				query = "  insert ERP_KHOTT_SP_CHITIET_TRANGTHAI(KHOTT_FK, SANPHAM_FK, SOLUONG , BOOKED, AVAILABLE, SOLO, NGAYBATDAU " +
						" ,BIN,KHUVUCKHO_FK , TRANGTHAI ) " +
						"  VALUES ( " + khott_fk + " ,  " + SANPHAM_FK + " , " +soluongnhap+ ","+Booked+", " +available + ", '" + solo.trim() + "', '"+NgayBatDau+"' ," +
						"  '" + vitri + "' ,"+(KHUVUCKHO_FK.length() >0?KHUVUCKHO_FK:"NULL")+", " + trangthai+" ) " ;
			}	

			if(db.updateReturnInt(query)!=1)
			{
				return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
			}
			return "";

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet_TrangThai : "+er.getMessage();
		}
	}


	public boolean IsKhoQuanLyTrangThai(String khoid,  Idbutils db) {
		
		try{
			if(khoid.length() >0){
				String query=" select isnull(quanlytrangthai,'0') as trangthai from KHO WHERE PK_SEQ= "+khoid;
				ResultSet rs=db.get(query);
				if(rs.next()){
					if(rs.getString("trangthai").equals("1")){
						rs.close();
						return true;
					}
					rs.close();
				}
			}
		}catch(Exception er){
			//er.printStackTrace();
			return false;
		}
		return false;
	}


	public String getIsQuanLyKhuVuc(String khoid,
			Idbutils db) {
		
		/*try{
			if(khoid.length()  >0){
				String query="select isnull(QUANLYKHUVUC,'0') as QUANLYKHUVUC from KHO WHERE PK_SEQ= "+khoid;
				ResultSet rs=db.get(query);
				if(rs.next()){
					if(rs.getString("QUANLYKHUVUC").equals("1")){
						rs.close();
						return "1";
					}
					rs.close();
				}
			}
		}catch(Exception er){
			//er.printStackTrace();
			return "0";
		}*/
		return "0";
	}


	public String Update_Kho_Sp_Chitiet(Idbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double Booked,double available, double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String ngaynhapkho ) {
		

		
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET ,cả đạt chất lượng và không đạt chất lượng.
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		try{

			String	 
			query = " update ERP_KHOTT_SP_CHITIET set booked=isnull(booked,0) + "+Booked+" ,soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
					" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
					" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'   "+(vitri.length()>0? " and BIN_FK= "+vitri : " AND  BIN_FK IS NULL " )+ " and NGAYNHAPKHO='"+ngaynhapkho+"'  ";

			if(db.updateReturnInt(query)!=1)
			{
				return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
			}
			return "";
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet : "+er.getMessage();
		}
	}


	public String Update_Kho_Sp_Chitiet_TrangThai(Idbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double Booked,double available, double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau ,String trangthai) {
 
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET_TRANGTHAI ,TRANGTHAI=-1 LÀ HÀNG KHÔNG ĐẠT,TRANGTHAI=0 LÀ HÀNG ĐẠT ,ĐỂ CHO CÁC BÁC VÀO CHUYỂN KHO CHỈ LẤY SỐ LƯỢNG ĐẠT THÔI
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		try{

			String	query = " update ERP_KHOTT_SP_CHITIET_TRANGTHAI set booked =isnull(booked,0)+ "+Booked+",soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
					" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
					" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'  "+(KHUVUCKHO_FK.length()>0? "and khuvuckho_fk="+KHUVUCKHO_FK : "" )+"  and NGAYBATDAU='"+NgayBatDau+"' AND TRANGTHAI="+trangthai+"";


			if(db.updateReturnInt(query)!=1)
			{
				return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
			}
			return "";

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet_TrangThai : "+er.getMessage();
		}
	}


	public boolean IsKhoNhoGiaCong(String khoid, Idbutils db) {
		
		try{
			if(khoid.length() >0){
				String query=" select  ISNULL(LOAI,'') as LOAI from KHO WHERE PK_SEQ= "+khoid;
				ResultSet rs=db.get(query);
				if(rs.next()){
					if(rs.getString("LOAI").equals("8")){
						rs.close();
						return true;
					}
					rs.close();
				}
			}
		}catch(Exception er){
			//er.printStackTrace();
			return false;
		}
		return false;
	}


	public String Update_Kho_Sp_Chitiet_NCC(Idbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double Booked,double available, double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau,String NCC_FK ) {
		

		
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET ,cả đạt chất lượng và không đạt chất lượng.
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		try{

			String	 
			query = " update ERP_KHOTT_SP_CHITIET_NCC set booked=isnull(booked,0) + "+Booked+" ,soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
					" where  NCC_FK="+NCC_FK +" and  KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
					" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'   and khuvuckho_fk="+KHUVUCKHO_FK+" and NGAYBATDAU='"+NgayBatDau+"' ";



			if(db.updateReturnInt(query)!=1)
			{
				return "Không thể cập nhật ERP_KHOTT_SP_CHITIET_NCC " + query;
			}
			return "";
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet : "+er.getMessage();
		}
	}


	public double getChiPhiLuukho(String khott_fk, String ngaybatdau,
			String ngayketthuc, Idbutils db) {
		
		try{
			String query="SELECT LOAIKHO,DONGIA FROM ERP_KHO_CAUHINH_CHIPHI WHERE LOAIKHO='1' AND KHOTT_FK="+khott_fk;
			ResultSet rs=db.get(query);
			if(rs.next()){

				//lấy ra được khoảng thời gian từ ngày tới ngày
				query="SELECT DATEDIFF(day, '"+ngaybatdau+"',  '"+ngayketthuc+"') as songay ";
				ResultSet rsngay=db.get(query);
				if(rsngay==null){
					//-2 không xác định từ ngày tới ngày
					return -2;
				}
				int songay=0;
				if(rsngay.next()){
					songay=rsngay.getInt("songay");
				}
				if(songay >0){
					return songay * rs.getDouble("DONGIA");
				}else{
					return 0;
				}

			}
			rs.close();
		}catch(Exception er){

			return -1;
		}
		return 0;
	}


	public double getChiPhiCapdong(String khott_fk, Idbutils db) {
		
		try{
			String query=" SELECT LOAIKHO,DONGIA FROM ERP_KHO_CAUHINH_CHIPHI  WHERE LOAIKHO='0' AND KHOTT_FK="+khott_fk;
			ResultSet rs=db.get(query);
			if(rs.next()){
				return rs.getDouble("DONGIA");
			}
			rs.close();
		}catch(Exception er){

			return -1;
		}
		return 0;
	}


	public ResultSet getRsKhu(String khottId, Idbutils db) {
		
		if(khottId.length()>0){
			String query="SELECT PK_SEQ,TEN FROM ERP_KHUVUCKHO  WHERE KHOTT_FK="+khottId;
			return db.getScrol(query);
		}
		return null;
	}


	public boolean getIsKhoGiaCong(Idbutils db, String khottId) {
		
		boolean bien=false;
		if(khottId.length()>0){
			// HIỆN TẠI HỆ THỐNG CHƯA CÓ KHO GIA CÔNG
			String query="SELECT PK_SEQ  FROM KHO  WHERE LOAI IN ('10','11') and  PK_SEQ="+khottId;
			ResultSet rs  = db.get(query);
			try{	
				if(rs.next()){
					bien=true;
				}else{
					bien=false;
				}
				rs.close();
			}catch(Exception er){

			}

		}
		return bien;
	}

	public String Update_NPP_Kho_Sp(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			double soluong ,double booked,double available , double dongia) {
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
					"  from NHAPP_KHO kho " +
					"  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
					"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId;
			System.out.println("Câu cập nhật kho "+query);
			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}



				query = " Update NHAPP_KHO set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
						"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId;

			}else{
				query=  " INSERT INTO NHAPP_KHO ( KHO_FK,SANPHAM_FK,NPP_FK,NHOMKENH_FK,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+","+dongia+","+soluong+","+booked+","+available+")";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();

			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO " + query;

			}

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}


//	public String Update_NPP_Kho_Sp(String ngaychungtu ,String nghiepvu ,geso.traphaco.distributor.db.sql.dbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
//			double soluong ,double booked,double available , double dongia) {
//		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
//		try{
//			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
//					"  from NHAPP_KHO kho " +
//					"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
//					"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId;
//			System.out.println("Câu cập nhật kho "+query);
//			double available_ton=0;
//			double giaton=0;
//			double soluongton=0;
//
//			ResultSet rsCheck = db.get(query);
//			if(rsCheck.next()){
//				soluongton=rsCheck.getDouble("soluong");
//				available_ton=rsCheck.getDouble("available");
//				giaton=rsCheck.getDouble("GIATON");
//
//				if(available < 0 && available_ton < (-1)*available ){
//					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
//				}
//
//				if(soluong < 0 && soluongton <(-1)*soluong ){
//					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
//				}
//
//
//
//				query = " Update NHAPP_KHO set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
//						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
//						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
//						"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId;
//
//			}else{
//				
//				query="SELECT  sp.ma+ ' '+ sp.ten as ten from sanpham sp where pk_seq="+spId;
//				ResultSet rssp1=db.get(query);
//				String tensp="";
//				
//				
//				if(rssp1.next()){
//					tensp=rssp1.getString("ten");
//				}
//				rssp1.close();
//				
//				
//				query=  " INSERT INTO NHAPP_KHO ( KHO_FK,SANPHAM_FK,NPP_FK,NHOMKENH_FK,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
//						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+","+dongia+","+soluong+","+booked+","+available+")";
//
//				if(available < 0 && available_ton < (-1)*available ){
//					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+tensp + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
//				}
//
//				if(soluong < 0 && soluongton <(-1)*soluong ){
//					return "Số lượng tồn  trong kho của sản phẩm : "+tensp + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
//				}
//
//			}
//			rsCheck.close();
//
//			int resultInt = db.updateReturnInt(query);
//			if(resultInt != 1)
//			{
//				return  " Không thể cập nhật NHAPP_KHO " + query;
//
//			}
//
//		}catch(Exception er){
//			er.printStackTrace();
//			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
//		}
//		return "";
//	}


	public String Check_NPP_Kho_Sp_Chitiet_Nhohon_0(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			String solo ) {
		try{
			 
			 
				// check giảm kho chi tiết nếu là giảm số lượng 
				//[REPORT_XNT_CT_SP_KHO] 100000,100000,'2016-02-28',106313,100162,'0010116'

				String[] param = new String[6];
			 	param[0] =kbh_fk;
			    param[1] =khott_fk;
			    param[2] =ngaychungtu;
			    param[3] =npp_fk;
			    param[4] =spId;
			    param[5] =solo;
			    System.out.println(param[0]+";"+param[1]+";"+param[2]+";"+param[3]+";"+param[4]+";"+param[5]);
			    
			    ResultSet tonkhongay= db.getRsByPro("REPORT_XNT_CT_SP_KHO", param);
			    double soluongtonngay=0;
			    String SPTEN="";
			   
			    if(tonkhongay.next()){
			    	soluongtonngay=tonkhongay.getDouble("SOLUONGTON") ;
			    	SPTEN=tonkhongay.getString("MA_FAST")+" - "+tonkhongay.getString("ten");
			    	 
			    } 
			     if(soluongtonngay <0){
			    	 return "Xuất nhập tồn tới ngày "+ngaychungtu+" của sản phẩm :"+SPTEN+"không đủ tồn kho để thực hiện  nghiệp vụ"; 
			     }
			     

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}

	
	
	public String Update_NPP_Kho_Sp_Chitiet(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			String solo, String ngaysanxuat, String ngayhethan, String ngaynhapkho,double soluong ,double booked,double available , double dongia) {
		try{
			if(ngayhethan==null || ngayhethan.length() != 10){
				return "Không xác định được ngày hết hạn của ID sản phẩm :"+spId+" và số lô : "+solo;
			}
			
			if(soluong <0){
				// check giảm kho chi tiết nếu là giảm số lượng 
				//[REPORT_XNT_CT_SP_KHO] 100000,100000,'2016-02-28',106313,100162,'0010116'

				String[] param = new String[6];
			 	param[0] =kbh_fk;
			    param[1] =khott_fk;
			    param[2] =ngaychungtu;
			    param[3] =npp_fk;
			    param[4] =spId;
			    param[5] =solo;
			    
			    
			    ResultSet tonkhongay= db.getRsByPro("REPORT_XNT_CT_SP_KHO", param);
			    double soluongtonngay=0;
			    String SPTEN="";
			   
			    if(tonkhongay.next()){
			    	soluongtonngay=tonkhongay.getDouble("SOLUONGTON") ;
			    	SPTEN=tonkhongay.getString("MA_FAST")+" - "+tonkhongay.getString("ten");
			    	 
			    } 
			    System.out.println("[soluong] "+soluong+"; [soluongton] "+soluongtonngay);
			    if(soluong*(-1) > soluongtonngay){
			    	return "Vui lòng kiểm tra báo cáo xuất nhập tồn : Tồn kho ngày của  sản phẩm :"+spId+"- "+SPTEN+" và số lô : "+solo+" tới ngày: "+ngaychungtu+" không còn đủ để trừ kho :tồn  chỉ còn:"+soluongtonngay;
			    }
			  
			}
			
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
					"  from NHAPP_KHO_CHITIET kho " +
					"  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
					"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId +" and  solo = N'"+  solo +"' ";

			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}


				query = " Update NHAPP_KHO_CHITIET set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
						"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId +"  and  solo = N'"+  solo +"'  ";

			}else{
				query="SELECT  sp.ma+ ' '+ sp.ten as ten from sanpham sp where pk_seq="+spId;
				ResultSet rssp1=db.get(query);
				String tensp="";
				
				
				if(rssp1.next()){
					tensp=rssp1.getString("ten");
				}
				rssp1.close();
				
				query=  " INSERT INTO NHAPP_KHO_CHITIET ( KHO_FK,SANPHAM_FK,NPP_FK,NHOMKENH_FK, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", N'"+ solo +"', '"+ ngaysanxuat +"', '"+ ngayhethan +"', '"+ ngaynhapkho +"',"+dongia+","+soluong+","+booked+","+available+")";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+tensp + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+tensp + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();

			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO_CHITIET " + query;

			}

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}

	public String Update_NPP_Kho_Sp_Chitiet_trangthai(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			String solo, String ngaysanxuat, String ngayhethan, String ngaynhapkho,double soluong ,double booked,double available , double dongia,String trangthaisp) {
		try{
			if(ngayhethan==null || ngayhethan.length() != 10){
				return "Không xác định được ngày hết hạn của ID sản phẩm :"+spId+" và số lô : "+solo;
			}
			
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
					"  from NHAPP_KHO_CHITIET_TRANGTHAI kho " +
					"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
					"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId +" and  solo = N'"+  solo +"' ";

			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}


				query = " Update NHAPP_KHO_CHITIET set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
						"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId +"  and  solo = N'"+  solo +"' ";

			}else{
				query=  " INSERT INTO NHAPP_KHO_CHITIET ( KHO_FK,SANPHAM_FK,NPP_FK,NHOMKENH_FK, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", N'"+ solo +"', '"+ ngaysanxuat +"', '"+ ngayhethan +"', '"+ ngaynhapkho +"',"+dongia+","+soluong+","+booked+","+available+")";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();

			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO_CHITIET " + query;

			}

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}


	public String Update_NPP_Kho_Sp_NCC(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			double soluong ,double booked,double available , double dongia, String ncc_fk) {

		try{
			String query="  select sanpham_fk ,available, soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
					"  from NHAPP_KHO_NCC kho " +
					"  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
					"  KHO_FK="+khott_fk+" AND NCC_FK = "+ ncc_fk +" and sanpham_fk= "+spId;

			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}


				query = " Update NHAPP_KHO_NCC set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
						"  KHO_FK="+khott_fk+" and NCC_FK = "+ ncc_fk +" and sanpham_fk= "+spId;

			}else{
				query=  " INSERT INTO NHAPP_KHO_NCC( KHO_FK, SANPHAM_FK, NPP_FK, NHOMKENH_FK, NCC_FK, GIAMUA, SOLUONG, BOOKED, AVAILABLE ) VALUES  " +
						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", "+ ncc_fk +","+dongia+","+soluong+","+booked+","+available+")";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();
			System.out.println("[query] "+query);
			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO_NCC " + query;

			}

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}

	public String Update_NPP_Kho_Sp_Chitiet_NCC(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk, String ncc_fk,
			String solo, String ngaysanxuat, String ngayhethan, String ngaynhapkho,double soluong ,double booked,double available , double dongia) {
		try{
			
			if(ngayhethan==null || ngayhethan.length() != 10){
				return "Không xác định được ngày hết hạn của ID sản phẩm :"+spId+" và số lô : "+solo;
			
			}
			
			
			
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
					"  from NHAPP_KHO_CHITIET_NCC kho " +
					"  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
					"  KHO_FK="+khott_fk+" and NCC_FK = "+ ncc_fk +" and sanpham_fk= "+spId +" and  solo = N'"+  solo +"' AND NGAYHETHAN='"+ngayhethan+"'";
			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}


				query = " Update NHAPP_KHO_CHITIET_NCC set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
						"  KHO_FK="+khott_fk+" and NCC_FK = "+ ncc_fk +" and sanpham_fk= "+spId +"  and  solo = N'"+  solo +"'  AND NGAYHETHAN='"+ngayhethan+"'";

			}else{
				  
				query="SELECT  sp.ma+ ' '+ sp.ten as ten ,(select ten from kho where pk_seq="+khott_fk+")  as kho from sanpham sp where pk_seq="+spId;
				ResultSet rssp1=db.get(query);
				String tensp="";
				String tenkho="";
				
				
				if(rssp1.next()){
					tensp=rssp1.getString("ten");
					tenkho=rssp1.getString("kho");
				}
				rssp1.close();
				
				query=  " INSERT INTO NHAPP_KHO_CHITIET_NCC ( KHO_FK,SANPHAM_FK,NPP_FK,NHOMKENH_FK, NCC_FK, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", "+ ncc_fk +", N'"+ solo +"', '"+ ngaysanxuat +"', '"+ ngayhethan +"', '"+ ngaynhapkho +"',"+dongia+","+soluong+","+booked+","+available+")";
				
				if(available < 0 && available_ton < (-1)*available ){
					
					return "Số lượng tồn hiện tại trong kho ["+tenkho+"] của sản phẩm : "+tensp + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho  ["+tenkho+"] của sản phẩm : "+tensp + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();

			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO_CHITIET_NCC " + query;

			}

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}


	public String Update_NPP_Kho_Sp_DDKD(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			double soluong ,double booked,double available , double dongia, String DDKD_FK) {

		try{
			String query="  select sanpham_fk ,available, soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
					"  from NHAPP_KHO_DDKD kho " +
					"  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
					"  KHO_FK="+khott_fk+" AND DDKD_FK = "+ DDKD_FK +" and sanpham_fk= "+spId;

			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(giaton >0){
					if( giaton- dongia !=0) {

						query=" insert into log_sql_khott(ngay,khott_fk,sanpham_fk ,ghichu ) " +
								" values( GETDATE(),"+khott_fk+","+spId+",N'đơn giá khác nhau giữa 2 kho :Giá cũ :"+giaton+". Giá mới :"+dongia+"' )";

						db.update(query);

					}
				} 

				query = " Update NHAPP_KHO_DDKD set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
						"  KHO_FK="+khott_fk+" and DDKD_FK = "+ DDKD_FK +" and sanpham_fk= "+spId;

			}else{
				query=  " INSERT INTO NHAPP_KHO_DDKD( KHO_FK, SANPHAM_FK, NPP_FK, NHOMKENH_FK, DDKD_FK, GIAMUA, SOLUONG, BOOKED, AVAILABLE ) VALUES  " +
						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", "+ DDKD_FK +","+dongia+","+soluong+","+booked+","+available+")";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();

			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO_DDKD " + query;

			}

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}

	public String Update_NPP_Kho_Sp_Chitiet_DDKD(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk, String DDKD_FK,
			String solo, String ngaysanxuat, String ngayhethan, String ngaynhapkho,double soluong ,double booked,double available , double dongia) {
		try{
			
			if(ngayhethan==null || ngayhethan.length() != 10){
				return "Không xác định được ngày hết hạn của ID sản phẩm :"+spId+" và số lô : "+solo;
			}
			
			
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
					"  from NHAPP_KHO_DDKD_CHITIET kho " +
					"  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
					"  KHO_FK="+khott_fk+" and DDKD_FK = "+ DDKD_FK +" and sanpham_fk= "+spId +" and  solo = N'"+  solo +"' and ngayhethan ='"+ngayhethan+"' ";

			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}


				query = " Update NHAPP_KHO_DDKD_CHITIET set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
						"  KHO_FK="+khott_fk+" and DDKD_FK = "+ DDKD_FK +" and sanpham_fk= "+spId +"  and  solo = N'"+  solo +"' ";

			}else{
				query=  " INSERT INTO NHAPP_KHO_DDKD_CHITIET ( KHO_FK,SANPHAM_FK,NPP_FK,NHOMKENH_FK, DDKD_FK, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", "+ DDKD_FK +", N'"+ solo +"', '"+ ngaysanxuat +"', '"+ ngayhethan +"', '"+ ngaynhapkho +"',"+dongia+","+soluong+","+booked+","+available+")";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();

			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO_DDKD_CHITIET " + query;

			}

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}



	public String Update_NPP_Kho_Sp_Kygui(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			double soluong ,double booked,double available , double dongia, String khachhang_fk) {

		try{
			String query="  select sanpham_fk ,available, soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
					"  from NHAPP_KHO_KYGUI kho " +
					"  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
					"  KHO_FK="+khott_fk+" AND KHACHHANG_FK = "+ khachhang_fk +" and sanpham_fk= "+spId;

			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(giaton >0){
					if( giaton- dongia !=0) {

						query=" insert into log_sql_khott(ngay,khott_fk,sanpham_fk ,ghichu ) " +
								" values( GETDATE(),"+khott_fk+","+spId+",N'đơn giá khác nhau giữa 2 kho :Giá cũ :"+giaton+". Giá mới :"+dongia+"' )";

						db.update(query);

					}
				} 

				query = " Update NHAPP_KHO_KYGUI set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
						"  KHO_FK="+khott_fk+" and KHACHHANG_FK = "+ khachhang_fk +" and sanpham_fk= "+spId;

			}else{
				query=  " INSERT INTO NHAPP_KHO_KYGUI( KHO_FK, SANPHAM_FK, NPP_FK, NHOMKENH_FK,KHACHHANG_FK, GIAMUA, SOLUONG, BOOKED, AVAILABLE ) VALUES  " +
						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", "+ khachhang_fk +","+dongia+","+soluong+","+booked+","+available+")";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();

			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO_KYGUI " + query;

			}

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}

	public String Update_NPP_Kho_Sp_Kygui_Chitiet(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk, String khachhang_fk,
			String solo, String ngaysanxuat, String ngayhethan, String ngaynhapkho,double soluong ,double booked,double available , double dongia) {
		try{
			
			if(ngayhethan==null || ngayhethan.length() != 10){
				return "Không xác định được ngày hết hạn của ID sản phẩm :"+spId+" và số lô : "+solo;
			}
			
			if(soluong <0){
				// check giảm kho chi tiết nếu là giảm số lượng 
				//[REPORT_XNT_CT_SP_KHO] 100000,100000,'2016-02-28',106313,100162,'0010116'

				String[] param = new String[7];
			 	param[0] = kbh_fk;
			    param[1] = khott_fk;
			    param[2] = ngaychungtu;
			    param[3] = npp_fk;
			    param[4] = spId;
			    param[5] = solo;
			    param[6] = khachhang_fk;
			    
			    ResultSet tonkhongay= db.getRsByPro("REPORT_XNT_CT_KYGUI_SP_KHO", param);
			    double soluongtonngay=0;
			    String SPTEN="";
			   
			    if(tonkhongay.next()){
			    	soluongtonngay=tonkhongay.getDouble("SOLUONGTON") ;
			    	SPTEN=tonkhongay.getString("MA_FAST")+" - "+tonkhongay.getString("ten");
			    	 
			    }
			    if(soluong*(-1) > soluongtonngay){
			    	return "Vui lòng kiểm tra báo cáo xuất nhập tồn : Tồn kho ngày của  sản phẩm :"+spId+"- "+SPTEN+" và số lô : "+solo+" tới ngày: "+ngaychungtu+" không còn đủ để trừ kho :tồn  chỉ còn:"+soluongtonngay;
			    }
			  
			}
			
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
					"  from NHAPP_KHO_KYGUI_CHITIET kho " +
					"  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
					"  KHO_FK="+khott_fk+" and khachhang_fk = "+ khachhang_fk +" and sanpham_fk= "+spId +" and  solo = N'"+  solo +"' AND NGAYHETHAN ='"+ngayhethan+"'";

			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}


				query = " Update NHAPP_KHO_KYGUI_CHITIET set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
						"  KHO_FK="+khott_fk+" and khachhang_fk = "+ khachhang_fk +" and sanpham_fk= "+spId +"  and  solo = N'"+  solo +"' and NGAYHETHAN='"+ngayhethan+"'";

			}else{
				query=  " INSERT INTO NHAPP_KHO_KYGUI_CHITIET ( KHO_FK,SANPHAM_FK,NPP_FK,NHOMKENH_FK, khachhang_fk, SOLO,  NGAYHETHAN, NGAYNHAPKHO,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", "+ khachhang_fk +", N'"+ solo +"',  '"+ ngayhethan +"', '"+ ngaynhapkho +"',"+dongia+","+soluong+","+booked+","+available+")";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();

			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO_KYGUI_CHITIET " + query;

			}

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}


//	public String Update_NPP_Kho_Sp_Kygui(String ngaychungtu ,String nghiepvu ,geso.traphaco.distributor.db.sql.dbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
//			double soluong ,double booked,double available , double dongia, String khachhang_fk) {
//
//		try{
//			String query="  select sanpham_fk ,available, soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
//					"  from NHAPP_KHO_KYGUI kho " +
//					"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
//					"  KHO_FK="+khott_fk+" AND KHACHHANG_FK = "+ khachhang_fk +" and sanpham_fk= "+spId;
//
//			double available_ton=0;
//			double giaton=0;
//			double soluongton=0;
//
//			ResultSet rsCheck = db.get(query);
//			if(rsCheck.next()){
//				soluongton=rsCheck.getDouble("soluong");
//				available_ton=rsCheck.getDouble("available");
//				giaton=rsCheck.getDouble("GIATON");
//
//				if(available < 0 && available_ton < (-1)*available ){
//					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
//				}
//
//				if(soluong < 0 && soluongton <(-1)*soluong ){
//					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
//				}
//
//				if(giaton >0){
//					if( giaton- dongia !=0) {
//
//						query=" insert into log_sql_khott(ngay,khott_fk,sanpham_fk ,ghichu ) " +
//								" values( GETDATE(),"+khott_fk+","+spId+",N'đơn giá khác nhau giữa 2 kho :Giá cũ :"+giaton+". Giá mới :"+dongia+"' )";
//
//						db.update(query);
//
//					}
//				} 
//
//				query = " Update NHAPP_KHO_KYGUI set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
//						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
//						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
//						"  KHO_FK="+khott_fk+" and KHACHHANG_FK = "+ khachhang_fk +" and sanpham_fk= "+spId;
//
//			}else{
//				
//				query="SELECT  sp.ma+ ' '+ sp.ten as ten ,(select ten from kho where pk_seq="+khott_fk+")  as kho from sanpham sp where pk_seq="+spId;
//				ResultSet rssp1=db.get(query);
//				String tensp="";
//				String tenkho="";
//				
//				
//				if(rssp1.next()){
//					tensp=rssp1.getString("ten");
//					tenkho=rssp1.getString("kho");
//				}
//				rssp1.close();
//				
//				query=  " INSERT INTO NHAPP_KHO_KYGUI( KHO_FK, SANPHAM_FK, NPP_FK, NHOMKENH_FK,KHACHHANG_FK, GIAMUA, SOLUONG, BOOKED, AVAILABLE ) VALUES  " +
//						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", "+ khachhang_fk +","+dongia+","+soluong+","+booked+","+available+")";
//
//				if(available < 0 && available_ton < (-1)*available ){
//					return "Số lượng tồn hiện tại trong kho ["+tenkho+"] của sản phẩm : "+tensp+ "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
//				}
//
//				if(soluong < 0 && soluongton <(-1)*soluong ){
//					return "Số lượng tồn  trong kho ["+tenkho+"] của sản phẩm : "+tensp + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
//				}
//
//			}
//			rsCheck.close();
//
//			int resultInt = db.updateReturnInt(query);
//			if(resultInt != 1)
//			{
//				return  " Không thể cập nhật NHAPP_KHO_KYGUI " + query;
//
//			}
//
//		}catch(Exception er){
//			er.printStackTrace();
//			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
//		}
//		return "";
//	}

//	public String Update_NPP_Kho_Sp_Kygui_Chitiet(String ngaychungtu ,String nghiepvu ,geso.traphaco.distributor.db.sql.dbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk, String khachhang_fk,
//			String solo, String ngaysanxuat, String ngayhethan, String ngaynhapkho,double soluong ,double booked,double available , double dongia) {
//		try{
//			
//			if(ngayhethan==null || ngayhethan.length() != 10){
//				return "Không xác định được ngày hết hạn của ID sản phẩm :"+spId+" và số lô : "+solo;
//			}
//			
//			
//			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
//					"  from NHAPP_KHO_KYGUI_CHITIET kho " +
//					"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
//					"  KHO_FK="+khott_fk+" and khachhang_fk = "+ khachhang_fk +" and sanpham_fk= "+spId +" and  solo = N'"+  solo +"' AND NGAYHETHAN ='"+ngayhethan+"'";
//
//			double available_ton=0;
//			double giaton=0;
//			double soluongton=0;
//
//			ResultSet rsCheck = db.get(query);
//			if(rsCheck.next()){
//				soluongton=rsCheck.getDouble("soluong");
//				available_ton=rsCheck.getDouble("available");
//				giaton=rsCheck.getDouble("GIATON");
//
//				if(available < 0 && available_ton < (-1)*available ){
//					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
//				}
//
//				if(soluong < 0 && soluongton <(-1)*soluong ){
//					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
//				}
//
//
//				query = " Update NHAPP_KHO_KYGUI_CHITIET set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
//						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
//						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
//						"  KHO_FK="+khott_fk+" and khachhang_fk = "+ khachhang_fk +" and sanpham_fk= "+spId +"  and  solo = N'"+  solo +"' and NGAYHETHAN='"+ngayhethan+"'";
//
//			}else{
//				query=  " INSERT INTO NHAPP_KHO_KYGUI_CHITIET ( KHO_FK,SANPHAM_FK,NPP_FK,NHOMKENH_FK, khachhang_fk, SOLO,  NGAYHETHAN, NGAYNHAPKHO,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
//						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", "+ khachhang_fk +", N'"+ solo +"',  '"+ ngayhethan +"', '"+ ngaynhapkho +"',"+dongia+","+soluong+","+booked+","+available+")";
//
//				if(available < 0 && available_ton < (-1)*available ){
//					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
//				}
//
//				if(soluong < 0 && soluongton <(-1)*soluong ){
//					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
//				}
//
//			}
//			rsCheck.close();
//
//			int resultInt = db.updateReturnInt(query);
//			if(resultInt != 1)
//			{
//				return  " Không thể cập nhật NHAPP_KHO_KYGUI_CHITIET " + query;
//
//			}
//
//		}catch(Exception er){
//			er.printStackTrace();
//			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
//		}
//		return "";
//	}



	public String getIsKhoDuTruKhachHang_Kygui(String khoid, Idbutils db) {
		
		if(khoid.length()>0){
			// HIỆN TẠI HỆ THỐNG CHƯA CÓ KHO GIA CÔNG
			String query="SELECT PK_SEQ  FROM KHO  WHERE LOAI in (8,5) and  PK_SEQ="+khoid;
			ResultSet rs  = db.get(query);
			try{	
				if(rs.next()){
					rs.close();
					return "1";
				}else{
					rs.close();
					return "0";
				}

			}catch(Exception er){
				er.printStackTrace();
				return "";
			}

		}
		return "";
	}
	public String getIsKhoTrinhDuyetVien(String khoid, Idbutils db) {
		
		if(khoid.length()>0){
			// HIỆN TẠI HỆ THỐNG CHƯA CÓ KHO GIA CÔNG
			String query="SELECT PK_SEQ  FROM KHO  WHERE LOAI ='6' and  PK_SEQ="+khoid;
			ResultSet rs  = db.get(query);
			try{	
				if(rs.next()){
					rs.close();
					return "1";
				}else{
					rs.close();
					return "0";
				}

			}catch(Exception er){
				er.printStackTrace();
				return "";
			}

		}
		return "";
	}


	public String getIsKhoCuaNhaCC_Kygui(String khoid, Idbutils db) {
		
		if(khoid!=null && khoid.length()>0){
			// HIỆN TẠI HỆ THỐNG CHƯA CÓ KHO GIA CÔNG
			String query="SELECT PK_SEQ  FROM KHO  WHERE LOAI in (2,7) and  PK_SEQ="+khoid;
			ResultSet rs  = db.get(query);
			try{	
				if(rs.next()){
					rs.close();
					return "1";
				}else{
					rs.close();
					return "0";
				}

			}catch(Exception er){
				er.printStackTrace();
				return "";
			}

		}
		return "";
	}

	public String getIsKhoKyGuiTaiNhaCC(String khoid, Idbutils db) {
		
		if(khoid.length()>0){
			// HIỆN TẠI HỆ THỐNG CHƯA CÓ KHO GIA CÔNG
			String query="SELECT PK_SEQ  FROM KHO  WHERE LOAI ='2' and  PK_SEQ="+khoid;
			ResultSet rs  = db.get(query);
			try{	
				if(rs.next()){
					rs.close();
					return "1";
				}else{
					rs.close();
					return "0";
				}

			}catch(Exception er){
				er.printStackTrace();
				return "";
			}

		}
		return "";
	}


	public String getLoaiKho(Idbutils db, String khoid) {
		
		if(khoid!=null && khoid.length()>0){
			// HIỆN TẠI HỆ THỐNG CHƯA CÓ KHO GIA CÔNG
			String query="SELECT isnull(loai,'') as loai  FROM ERP_KHOTT  WHERE  PK_SEQ="+khoid;
			ResultSet rs  = db.get(query);
			try{	
				if(rs.next()){
					return rs.getString("loai");
				} 

			}catch(Exception er){
				er.printStackTrace();
				return "";
			}

		}
		return "";
	}
	
	public String Update_Kho_Sp(dbutils db, String khott_fk, String spId,
			double soluong ,double booked,double available , double dongia) {
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			String query="  select sanpham_fk ,cast(available as numeric(18,4)) as  available,cast(soluong as numeric(18,4)) as  soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIATON,0) AS GIATON   from erp_khott_sanpham kho " +
						 "  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where khott_fk="+khott_fk+" and sanpham_fk= "+spId;
			 
			double available_ton=0;
			double giaton=0;
			 double soluongton=0;
			
			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				     soluongton=rsCheck.getDouble("soluong");
				     available_ton=rsCheck.getDouble("available");
				     giaton=rsCheck.getDouble("GIATON");
				    
				    if(available < 0 && available_ton < (-1)*available ){
				    	return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				    }
				    
				    if(soluong < 0 && soluongton <(-1)*soluong ){
				    	return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				    }
				    
				    if(giaton >0){
				    	if( giaton- dongia !=0) {
				    		
				    		query=" insert into log_sql_khott(ngay,khott_fk,sanpham_fk ,ghichu ) " +
				    			  " values( GETDATE(),"+khott_fk+","+spId+",N'đơn giá khác nhau giữa 2 kho :Giá cũ :"+giaton+". Giá mới :"+dongia+"' )";
				    		
				    		db.update(query);
				    		
				    	}
				    } 
					
					query = " Update ERP_KHOTT_SANPHAM set booked=isnull(booked,0)+"+booked+" , soluong =cast(ISNULL(soluong,0) as numeric(18,4))  + " + soluong + ", " +
							" AVAILABLE =cast(ISNULL(AVAILABLE,0) as numeric(18,4))  + " + available + ",giaton="+(giaton >0?giaton:dongia)+"  "+
							" where khott_fk =" + khott_fk + " and sanpham_fk = " +   spId + "";
			}else{
					query=  " INSERT INTO ERP_KHOTT_SANPHAM ( KHOTT_FK,SANPHAM_FK,GIATON,SOLUONG,BOOKED,AVAILABLE,THANHTIEN ) VALUES  " +
						    " ("+khott_fk+","+ spId+","+dongia+","+soluong+","+booked+","+available+","+soluong+"*"+dongia+")";
					
					if(available < 0 && available_ton < (-1)*available ){
				    	return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				    }
				    
				    if(soluong < 0 && soluongton <(-1)*soluong ){
				    	return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				    }
					
			}
			rsCheck.close();
			int resultInt = db.updateReturnInt(query);
			
			if(resultInt != 1)
			{
				return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
			}
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}
	
public String Update_Kho_Sp_Check_TonKhoNgay(Idbutils db, String khott_fk, String spId,
	double soluong ,double booked,double available , double dongia,String ngaychungtu ) {
// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
try{
	NumberFormat formatter2 = new DecimalFormat("#######.######");
	if(soluong!=0){
		// thực hiện kiểm tra ngày chứng từ có hợp lệ không?
		int thangtruoc=Integer.parseInt(ngaychungtu.substring(5, 7));
		int namtruoc=Integer.parseInt(ngaychungtu.substring(0, 4));

		String sql=" select TOP 1 THANGKS, NAM from ERP_KHOASOTHANG order by NAM desc, THANGKS desc ";
		 
		ResultSet rscheckngay=db.get(sql);
		if(rscheckngay.next()){
			 if(thangtruoc <=  rscheckngay.getInt("THANGKS")  &&  namtruoc <= rscheckngay.getInt("NAM")){
				 return " Vui lòng chỉ được chọn ngày ghi nhận sau tháng khóa sổ gần nhất:Tháng :"+rscheckngay.getString("THANGKS")+",năm :  "+rscheckngay.getString("Nam");
			 }
		}else{ 
				return " Vui lòng kiểm tra khóa sổ tháng,chưa có khóa sổ tháng";
		}
		rscheckngay.close();

	}
	 
	
	String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIATON,0) AS GIATON   from erp_khott_sanpham kho " +
				 "  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where khott_fk="+khott_fk+" and sanpham_fk= "+spId;
	 
	double available_ton=0;
	double giaton=0;
	 double soluongton=0;
	
	ResultSet rsCheck = db.get(query);
	
	String tensp="";
	
	if(rsCheck.next()){
		     soluongton=rsCheck.getDouble("soluong");
		     available_ton=rsCheck.getDouble("available");
		     giaton=rsCheck.getDouble("GIATON");
		     tensp=rsCheck.getString("ten");
		   
		    if(giaton >0){
		    	if( giaton- dongia !=0) {
		    		
		    		query=" insert into log_sql_khott(ngay,khott_fk,sanpham_fk ,ghichu ) " +
		    			  " values( GETDATE(),"+khott_fk+","+spId+",N'đơn giá khác nhau giữa 2 kho :Giá cũ :"+giaton+". Giá mới :"+dongia+"' )";
		    		
		    		db.update(query);
		    		
		    	}
		    } 
			
			query = " Update ERP_KHOTT_SANPHAM set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
					" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ",giaton="+(giaton >0?giaton:dongia)+"  "+
					" where khott_fk =" + khott_fk + " and sanpham_fk = " +   spId + "";
	}else{
			query=  " INSERT INTO ERP_KHOTT_SANPHAM ( KHOTT_FK,SANPHAM_FK,GIATON,SOLUONG,BOOKED,AVAILABLE,THANHTIEN ) VALUES  " +
				    " ("+khott_fk+","+ spId+","+dongia+","+soluong+","+booked+","+available+","+soluong+"*"+dongia+")";
			
			
			
	}
	rsCheck.close();
	
	if(available < 0 &&  Double.parseDouble(formatter2.format(available_ton))  < (-1)*Double.parseDouble(formatter2.format(available)) ){
    	return "Số lượng tồn hiện tại trong kho của sản phẩm : "+tensp+ "  ["+available_ton+"] < ["+((-1)*Double.parseDouble(formatter2.format(available)))+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
    }
    
    if(soluong < 0 &&  Double.parseDouble(formatter2.format(soluongton)) < (-1)*  Double.parseDouble(formatter2.format(soluong))  ){
    	return "Số lượng tồn  trong kho của sản phẩm : "+tensp + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
    }
    
    // thực hiện tính tồn kho tới ngày 
    
    
	int resultInt = db.updateReturnInt(query);
	
	if(resultInt != 1)
	{
		return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
	}
	
	
}catch(Exception er){
	er.printStackTrace();
	return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
}
return "";
}

	public String Update_Kho_Sp_Chitiet(dbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double booked,double available , double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau,String NgayNhapKho ,String NgaySanXuat,String NGAYHETHAN,
			double CPCAPDONG, double CPLUUKHO, double CPNHANHANG,
			double THUENHAPKHAU) {
		
		//không sử dụng ngày bắt đầu nữa :
		NgayBatDau="2015-01-01";
		// TODO Auto-generated method stub
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET ,cả đạt chất lượng và không đạt chất lượng.
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		try{
			 
			vitri="100000";
			String	query = " select count(*) as sodong from ERP_KHOTT_SP_CHITIET " +
							" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " + SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "' "+ (KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"") +"  ";
			System.out.println(query);
		
			ResultSet	rsCheck = db.get(query);
			boolean flag = false;
				if(rsCheck.next())
				{
					if(rsCheck.getInt("sodong") > 0) {
						flag = true;
					}
					
				}
				rsCheck.close();
				
				if(flag)
				{
					query = " update ERP_KHOTT_SP_CHITIET set booked=isnull(booked,0) + "+booked+" ,soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
							" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "' and BIN = '" + vitri + "' "+(KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"")+" and NGAYBATDAU='"+NgayBatDau+"' ";
				}
				else
				{
					query = "  insert ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG , BOOKED, AVAILABLE, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO,NGAYBATDAU " +
							"  ,BIN,KHUVUCKHO_FK ,DONGIAMUA, CPCAPDONG ,CPLUUKHO ,CPNHANHANG,THUENHAPKHAU ) " +
							"  VALUES ( " + khott_fk + " ,  " + SANPHAM_FK + " , " +soluongnhap+ ", "+booked+", " +available + ", '" + solo.trim() + "', '"+NgaySanXuat+"', '"+NGAYHETHAN+"'," +
							"  '"  + NgayNhapKho + "','"+NgayBatDau+"', '" + vitri + "' ,"+(KHUVUCKHO_FK.length() >0?KHUVUCKHO_FK:"NULL")+", " +
							      +DONGIAMUA+","+CPCAPDONG+" ,"+CPLUUKHO+","+CPNHANHANG+","+THUENHAPKHAU+") " ;
							 
				}	
			 
				if(db.updateReturnInt(query)!=1)
				{
					 return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				}
				return "";
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet : "+er.getMessage();
		}
	}



	public String Update_Kho_Sp_Chitiet_TrangThai(dbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double Booked,double available, double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau,String NgayNhapKho ,String NgaySanXuat,String NGAYHETHAN,
			double CPCAPDONG, double CPLUUKHO, double CPNHANHANG,
			double THUENHAPKHAU,String trangthai) {
		
		NgayBatDau="2015-01-01";
		// TODO Auto-generated method stub
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET_TRANGTHAI ,TRANGTHAI=-1 LÀ HÀNG KHÔNG ĐẠT,TRANGTHAI=0 LÀ HÀNG ĐẠT ,ĐỂ CHO CÁC BÁC VÀO CHUYỂN KHO CHỈ LẤY SỐ LƯỢNG ĐẠT THÔI
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		try{
			vitri="100000";
		String	query = " select count(*) as sodong from ERP_KHOTT_SP_CHITIET_TRANGTHAI " +
						" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " + SANPHAM_FK + " " +
						" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'   "+(KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"")+" and NGAYBATDAU='"+NgayBatDau+"' and trangthai="+trangthai+" ";
			    ResultSet	rsCheck = db.get(query);
				boolean flag = false;
				if(rsCheck.next())
				{
					if(rsCheck.getInt("sodong") > 0) {
						flag = true;
					}
					
				}
				rsCheck.close();
				
				if(flag)
				{
					query = " update ERP_KHOTT_SP_CHITIET_TRANGTHAI set booked =isnull(booked,0)+ "+Booked+",soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
							" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'  "+(KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"")+"  and NGAYBATDAU='"+NgayBatDau+"' AND TRANGTHAI="+trangthai+"";
				}
				else
				{
					query = "  insert ERP_KHOTT_SP_CHITIET_TRANGTHAI(KHOTT_FK, SANPHAM_FK, SOLUONG , BOOKED, AVAILABLE, SOLO, NGAYBATDAU " +
							" ,BIN,KHUVUCKHO_FK , TRANGTHAI ) " +
							"  VALUES ( " + khott_fk + " ,  " + SANPHAM_FK + " , " +soluongnhap+ ","+Booked+", " +available + ", '" + solo.trim() + "', '"+NgayBatDau+"' ," +
							"  '" + vitri + "' ,"+(KHUVUCKHO_FK.length() >0?KHUVUCKHO_FK:"NULL")+", " + trangthai+" ) " ;
				}	
				
				if(db.updateReturnInt(query)!=1)
				{
					 return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				}
				return "";
				
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet_TrangThai : "+er.getMessage();
		}
	}


	public boolean IsKhoQuanLyTrangThai(String khoid,  dbutils db) {
		// TODO Auto-generated method stub
		try{
			if(khoid.length() >0){
			 String query=" select isnull(quanlytrangthai,'0') as trangthai from ERP_KHOTT WHERE PK_SEQ= "+khoid;
			 ResultSet rs=db.get(query);
			 if(rs.next()){
				 if(rs.getString("trangthai").equals("1")){
					 rs.close();
					 return true;
				 }
				
			 }
			 rs.close();
			 
			}
		}catch(Exception er){
			//er.printStackTrace();
			return false;
		}
		return false;
	}


	public String getIsQuanLyKhuVuc(String khoid,
			 dbutils db) {
		// TODO Auto-generated method stub
		try{
			if(khoid.length()  >0){
				 String query="select isnull(QUANLYBIN,'0') as QUANLYBIN from ERP_KHOTT WHERE PK_SEQ= "+khoid;
				 ResultSet rs=db.get(query);
				 if(rs.next()){
					 if(rs.getString("QUANLYBIN").equals("1")){
						 rs.close();
						 return "1";
					 }
					
				 }
				 rs.close();
				 
			}
		}catch(Exception er){
			//er.printStackTrace();
			return "0";
		}
		return "0";
	}


	public String Update_Kho_Sp_Chitiet(dbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double Booked,double available, double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau ) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET ,cả đạt chất lượng và không đạt chất lượng.
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		NgayBatDau="2015-01-01";
		try{
			if(KHUVUCKHO_FK==null){
				KHUVUCKHO_FK="";
			}
			if(KHUVUCKHO_FK.equals("NULL")){
				KHUVUCKHO_FK="";
			}
				String query=" SELECT  SP.MA+ ' - '+ SP.TEN AS TENSP , KHO.SOLUONG,KHO.AVAILABLE ,khu.ten as tenkhu " +
							 " FROM ERP_KHOTT_SP_CHITIET KHO INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= SANPHAM_FK  " +
							 " left join erp_khuvuckho khu on khu.pk_seq= kho.KHUVUCKHO_FK "+
							 " where   kho.KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							 " and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'   "+(KHUVUCKHO_FK.length()>0? "and khuvuckho_fk="+KHUVUCKHO_FK : "" )+ " and NGAYBATDAU='"+NgayBatDau+"'";
				ResultSet rs=db.get(query);
					
				if(rs.next()){
					if(soluongnhap <0){
						// giảm số lượng
						if(rs.getDouble("soluong") < (soluongnhap*-1)){
							return " Số lô : "+solo.trim()+ (KHUVUCKHO_FK.length()>0? ". Khu vực kho: "+ rs.getString("tenkhu"):"" )+"Ngày bắt đầu :"+NgayBatDau +"  của sản phẩm  " + rs.getString("tensp")+" không đủ số lượng tồn kho. Tồn hiện tại : "+rs.getDouble("soluong");
						}
						
					}
					
					if(available <0){
						if(rs.getDouble("AVAILABLE") < (available*-1)){
							return " Số lô : "+solo.trim()+ (KHUVUCKHO_FK.length()>0? ". Khu vực kho: "+ rs.getString("tenkhu"):"" )+"Ngày bắt đầu :"+NgayBatDau +"  của sản phẩm  " + rs.getString("tensp") +" không đủ số lượng trong kho.Tồn hiện tại:"+rs.getDouble("AVAILABLE");
						}
					}
				}else{
					 return "Không thể xác định được : "+query;
				}
				rs.close();
				
				
				query =    " update ERP_KHOTT_SP_CHITIET set booked=isnull(booked,0) + "+Booked+" ,soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
							" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'   "+(KHUVUCKHO_FK.length()>0? "and khuvuckho_fk="+KHUVUCKHO_FK : "" )+ " and NGAYBATDAU='"+NgayBatDau+"' ";

				if(db.updateReturnInt(query)!=1)
				{
					 return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				}
				return "";
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet : "+er.getMessage();
		}
	}


	public String Update_Kho_Sp_Chitiet_TrangThai(dbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double Booked,double available, double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau ,String trangthai) {
		
 
		// TODO Auto-generated method stub
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET_TRANGTHAI ,TRANGTHAI=-1 LÀ HÀNG KHÔNG ĐẠT,TRANGTHAI=0 LÀ HÀNG ĐẠT ,ĐỂ CHO CÁC BÁC VÀO CHUYỂN KHO CHỈ LẤY SỐ LƯỢNG ĐẠT THÔI
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		NgayBatDau="2015-01-01";
		try{
				
				String	query = " update ERP_KHOTT_SP_CHITIET_TRANGTHAI set booked =isnull(booked,0)+ "+Booked+",soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
							" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'  "+(KHUVUCKHO_FK.length()>0? "and khuvuckho_fk="+KHUVUCKHO_FK : "" )+"  and NGAYBATDAU='"+NgayBatDau+"' AND TRANGTHAI="+trangthai+"";
				 
				
				if(db.updateReturnInt(query)!=1)
				{
					 return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				}
				return "";
				
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet_TrangThai : "+er.getMessage();
		}
	}


	public boolean IsKhoNhoGiaCong(String khoid, dbutils db) {
		// TODO Auto-generated method stub
		try{
			if(khoid.length() >0){
			 String query=" select  ISNULL(LOAI,'') as LOAI from ERP_KHOTT WHERE PK_SEQ= "+khoid;
			 ResultSet rs=db.get(query);
			 if(rs.next()){
				 if(rs.getString("LOAI").equals("5")){
					 rs.close();
					 return true;
				 }
				 rs.close();
			 }
			}
		}catch(Exception er){
			//er.printStackTrace();
			return false;
		}
		return false;
	}
	 
	 
	public String Update_Kho_Sp_Chitiet_NCC(dbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double Booked,double available, double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau,String NCC_FK ) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET ,cả đạt chất lượng và không đạt chất lượng.
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		NgayBatDau="2015-01-01";
		try{
			
		String	 
					query = " update ERP_KHOTT_SP_CHITIET_NCC set booked=isnull(booked,0) + "+Booked+" ,soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
							" where  NCC_FK="+NCC_FK +" and  KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'   and khuvuckho_fk="+KHUVUCKHO_FK+" and NGAYBATDAU='"+NgayBatDau+"' ";
				 
				 
			 
				if(db.updateReturnInt(query)!=1)
				{
					 return "Không thể cập nhật ERP_KHOTT_SP_CHITIET_NCC " + query;
				}
				return "";
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet : "+er.getMessage();
		}
	}


	public double getChiPhiLuukho(String khott_fk, String ngaybatdau,
			String ngayketthuc, dbutils db) {
		// TODO Auto-generated method stub
/*				try{
			String query="SELECT LOAIKHO,DONGIA FROM ERP_KHO_CAUHINH_CHIPHI WHERE LOAIKHO='1' AND KHOTT_FK="+khott_fk;
			ResultSet rs=db.get(query);
			if(rs.next()){
				 
					//lấy ra được khoảng thời gian từ ngày tới ngày
					query="SELECT DATEDIFF(day, '"+ngaybatdau+"',  '"+ngayketthuc+"') as songay ";
					ResultSet rsngay=db.get(query);
					if(rsngay==null){
						//-2 không xác định từ ngày tới ngày
						return -2;
					}
					int songay=0;
					if(rsngay.next()){
						songay=rsngay.getInt("songay");
					}
					if(songay >0){
					 return songay * rs.getDouble("DONGIA");
					}else{
						return 0;
					}
			 
			}
			rs.close();
		}catch(Exception er){
			
			return -1;
		}*/
		return 0;
	}


	public double getChiPhiCapdong(String khott_fk, dbutils db) {
		// TODO Auto-generated method stub
		/*try{
			String query=" SELECT LOAIKHO,DONGIA FROM ERP_KHO_CAUHINH_CHIPHI  WHERE LOAIKHO='0' AND KHOTT_FK="+khott_fk;
			ResultSet rs=db.get(query);
			if(rs.next()){
					return rs.getDouble("DONGIA");
			}
			rs.close();
		}catch(Exception er){
			
			return -1;
		}*/
		return 0;
	}


	public ResultSet getRsKhu(String khottId, dbutils db) {
		// TODO Auto-generated method stub
		if(khottId.length()>0){
		String query="SELECT PK_SEQ,TEN FROM ERP_KHUVUCKHO  WHERE KHOTT_FK="+khottId;
		return db.getScrol(query);
		}
		return null;
	}


	public boolean getIsKhoGiaCong(dbutils db, String khottId) {
		// TODO Auto-generated method stub
		boolean bien=false;
			if(khottId.length()>0){
				// HIỆN TẠI HỆ THỐNG CHƯA CÓ KHO GIA CÔNG
				String query="SELECT PK_SEQ  FROM ERP_KHOTT  WHERE LOAI IN ('10','11') and  PK_SEQ="+khottId;
				ResultSet rs  = db.get(query);
				try{	
					if(rs.next()){
						bien=true;
					}else{
						bien=false;
					}
					rs.close();
				}catch(Exception er){
					
				}
					
			}
			return bien;
	}
	
	public String Update_Kho_Sp_Check_TonKhoNgay(dbutils db, String khott_fk, String spId,
			double soluong ,double booked,double available , double dongia,String ngaychungtu ) {
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			NumberFormat formatter2 = new DecimalFormat("#######.######");
			if(soluong!=0){
				// thực hiện kiểm tra ngày chứng từ có hợp lệ không?
				int thangtruoc=Integer.parseInt(ngaychungtu.substring(5, 7));
				int namtruoc=Integer.parseInt(ngaychungtu.substring(0, 4));
 
				String sql=" select TOP 1 THANGKS, NAM from ERP_KHOASOTHANG order by NAM desc, THANGKS desc ";
				 
				ResultSet rscheckngay=db.get(sql);
				if(rscheckngay.next()){
					 if(thangtruoc <=  rscheckngay.getInt("THANGKS")  &&  namtruoc <= rscheckngay.getInt("NAM")){
						 return " Vui lòng chỉ được chọn ngày ghi nhận sau tháng khóa sổ gần nhất:Tháng :"+rscheckngay.getString("THANGKS")+",năm :  "+rscheckngay.getString("Nam");
					 }
				}else{ 
						return " Vui lòng kiểm tra khóa sổ tháng,chưa có khóa sổ tháng";
				}
				rscheckngay.close();
  
			}
			
			if(soluong<0){
				//kiểm tra tồn kho ngày có còn đủ để xuất kho hay không?
				
				
				
				//chỉ check trong trường hợp giảm số lượng
				String[] param = new String[4];
			 	param[0] =khott_fk;
			    param[1] =ngaychungtu;
			    param[2] =ngaychungtu;
			    param[3] =spId;
			    ResultSet tonkhongay= db.getRsByPro("REPORT_XUATNHAPTON_THEKHO_DAUKY", param);
			    double soluongtonngay=0;
			    String SPTEN="";
			    String khoten="";
			    if(tonkhongay.next()){
			    	soluongtonngay=tonkhongay.getDouble("TONDAUKY")+ tonkhongay.getDouble("TONGNHAP")- tonkhongay.getDouble("TONGXUAT");
			    	SPTEN=tonkhongay.getString("SPMA")+" - "+tonkhongay.getString("SPTEN");
			    	khoten=tonkhongay.getString("khoten");
			    } 
			  
			    /*if( Double.parseDouble(formatter2.format(soluongtonngay)) < (soluong *-1)){
			    	return "Không thể thực hiện xuất kho : [  "+ khoten + " ]   cho sản phẩm :"+SPTEN+". Tồn kho  tới ngày "+ngaychungtu +" chỉ còn :"+formatter2.format(soluongtonngay);
			    }*/
			    tonkhongay.close();
			    
			}
			
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIATON,0) AS GIATON   from erp_khott_sanpham kho " +
						 "  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where khott_fk="+khott_fk+" and sanpham_fk= "+spId;
			 
			double available_ton=0;
			double giaton=0;
			 double soluongton=0;
			
			ResultSet rsCheck = db.get(query);
			
			String tensp="";
			
			if(rsCheck.next()){
				     soluongton=rsCheck.getDouble("soluong");
				     available_ton=rsCheck.getDouble("available");
				     giaton=rsCheck.getDouble("GIATON");
				     tensp=rsCheck.getString("ten");
				   
				    if(giaton >0){
				    	if( giaton- dongia !=0) {
				    		
				    		query=" insert into log_sql_khott(ngay,khott_fk,sanpham_fk ,ghichu ) " +
				    			  " values( GETDATE(),"+khott_fk+","+spId+",N'đơn giá khác nhau giữa 2 kho :Giá cũ :"+giaton+". Giá mới :"+dongia+"' )";
				    		
				    		db.update(query);
				    		
				    	}
				    } 
					
					query = " Update ERP_KHOTT_SANPHAM set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
							" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ",giaton="+(giaton >0?giaton:dongia)+"  "+
							" where khott_fk =" + khott_fk + " and sanpham_fk = " +   spId + "";
			}else{
					query=  " INSERT INTO ERP_KHOTT_SANPHAM ( KHOTT_FK,SANPHAM_FK,GIATON,SOLUONG,BOOKED,AVAILABLE,THANHTIEN ) VALUES  " +
						    " ("+khott_fk+","+ spId+","+dongia+","+soluong+","+booked+","+available+","+soluong+"*"+dongia+")";
					
					
					
			}
			rsCheck.close();
			
			if(available < 0 &&  Double.parseDouble(formatter2.format(available_ton))  < (-1)*Double.parseDouble(formatter2.format(available)) ){
		    	return "Số lượng tồn hiện tại trong kho của sản phẩm : "+tensp+ "  ["+available_ton+"] < ["+((-1)*Double.parseDouble(formatter2.format(available)))+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
		    }
		    
		    if(soluong < 0 &&  Double.parseDouble(formatter2.format(soluongton)) < (-1)*  Double.parseDouble(formatter2.format(soluong))  ){
		    	return "Số lượng tồn  trong kho của sản phẩm : "+tensp + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
		    }
		    
		    // thực hiện tính tồn kho tới ngày 
		    
		    
			int resultInt = db.updateReturnInt(query);
			
			if(resultInt != 1)
			{
				return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
			}
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}


	public String Update_Kho_Sp_Tra(Idbutils db,
			Kho_Lib kholib) {
		// TODO Auto-generated method stub
			String ngayyeucau=kholib.getNgaychungtu();
			String ghichu=kholib.getLoaichungtu();
			String sochungtu=kholib.getSochungtu();
			
			String khott_fk =kholib.getKhottId();
			
			String spId= kholib.getSanphamId() ;
			String solo=kholib.getSolo(); 
			String ngayhethan= kholib.getNgayhethan();
			String ngaynhapkho= kholib.getNgaynhapkho();
			String ngaysanxuat=kholib.getNgaysanxuat();
			String MAME =kholib.getMame();
			String MATHUNG=kholib.getMathung();
			String vitri =kholib.getBinId();
			
			String MAPHIEU=kholib.getMaphieu();
			String phieudt=kholib.getMaphieudinhtinh();
			String phieueo =kholib.getPhieuEo();
			
			String MARQ =kholib.getMARQ();
			String HAMLUONG= kholib.getHamluong();
			String HAMAM= kholib.getHamam();
			String nsx_fk=kholib.getNsxId();
			
			String loaidoituong = kholib.getLoaidoituong();
			String doituongId =kholib.getDoituongId();
			if(ngaynhapkho==null || ngaynhapkho.trim().equals("")){
				return "Ngày nhập kho không xác định, vui lòng cập nhật ngày nhập kho";
			}
			double soluong=DinhDang.dinhdangso(kholib.getSoluong()); //  Double.parseDouble(formater_3sole.format(kholib.getSoluong()));
			double booked =DinhDang.dinhdangso(kholib.getBooked());  
			double available =DinhDang.dinhdangso( kholib.getAvailable()); 
			float dongia=kholib.getDongialo();
			Utility util=new Utility();
		 
			String msg1= util.Update_KhoTT_NEW(ngayyeucau, ghichu, db, khott_fk, spId, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, vitri, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, loaidoituong, doituongId, soluong, booked, available, dongia, ngaysanxuat,nsx_fk);
			return msg1;
	}
	
	public String Update_Kho_Sp_Tra_NEW(Idbutils db,
			Kho_Lib kholib) {
		// TODO Auto-generated method stub
			String ngayyeucau=kholib.getNgaychungtu();
			String ghichu=kholib.getLoaichungtu();
		//	String sochungtu=kholib.getSochungtu();
			
			String khott_fk =kholib.getKhottId();
			
			String spId= kholib.getSanphamId() ;
			String solo=kholib.getSolo(); 
			String ngayhethan= kholib.getNgayhethan();
			String ngaynhapkho= kholib.getNgaynhapkho();
			String ngaysanxuat=kholib.getNgaysanxuat();
			String MAME =kholib.getMame();
			String MATHUNG=kholib.getMathung();
			String vitri =kholib.getBinId();
			
			String MAPHIEU=kholib.getMaphieu();
			String phieudt=kholib.getMaphieudinhtinh();
			String phieueo =kholib.getPhieuEo();
			
			String MARQ =kholib.getMARQ();
			String HAMLUONG= kholib.getHamluong();
			String HAMAM= kholib.getHamam();
			String nsx_fk = kholib.getNsxId();
			
			String loaidoituong = kholib.getLoaidoituong();
			String doituongId =kholib.getDoituongId();
			if(ngaynhapkho==null || ngaynhapkho.trim().equals("")){
				return "Ngày nhập kho không xác định, vui lòng cập nhật ngày nhập kho";
			}
			double soluong=DinhDang.dinhdangso(kholib.getSoluong()); //  Double.parseDouble(formater_3sole.format(kholib.getSoluong()));
			double booked =DinhDang.dinhdangso(kholib.getBooked());  
			double available =DinhDang.dinhdangso( kholib.getAvailable()); 
			float dongia=kholib.getDongialo();
			Utility util=new Utility();
		 
			String msg1= util.Update_KhoTT_NEW(ngayyeucau, ghichu, db, khott_fk, spId, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, vitri, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, loaidoituong, doituongId, soluong, booked, available, dongia, ngaysanxuat, nsx_fk);
			return msg1;
	}
	
	
	public String Update_Kho_Sp_ChiTiet_Tra(Idbutils db,
			Kho_Lib kholib) {
		// TODO hàm này chỉ cập nhạt kho chi tiết tới lô, không cập nhật kho tổng
			NumberFormat formater_3sole = new DecimalFormat("#######.###");
			String ngayyeucau=kholib.getNgaychungtu();
			String ghichu=kholib.getLoaichungtu();
			String sochungtu=kholib.getSochungtu();
			
			String khott_fk =kholib.getKhottId();
			
			String spId= kholib.getSanphamId() ;
			String solo=kholib.getSolo(); 
			String ngayhethan= kholib.getNgayhethan();
			String ngaynhapkho= kholib.getNgaynhapkho();
			String ngaysanxuat=kholib.getNgaysanxuat();
			String MAME =kholib.getMame();
			String MATHUNG=kholib.getMathung();
			String vitri =kholib.getBinId();
			
			String MAPHIEU=kholib.getMaphieu();
			String phieudt=kholib.getMaphieudinhtinh();
			String phieueo =kholib.getPhieuEo();
			
			String MARQ =kholib.getMARQ();
			String HAMLUONG= kholib.getHamluong();
			String HAMAM= kholib.getHamam();
			
			String loaidoituong = kholib.getLoaidoituong();
			String doituongId =kholib.getDoituongId();
			
			double soluong=Double.parseDouble(formater_3sole.format(kholib.getSoluong()));
			double booked =Double.parseDouble(formater_3sole.format(kholib.getBooked()));  
			double available =Double.parseDouble(formater_3sole.format( kholib.getAvailable())); 
			float dongia=kholib.getDongialo();
			Utility util=new Utility();
		 
			String msg1= util.Update_KhoTT_Chitiet(ngayyeucau, ghichu, db, khott_fk, spId, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, vitri, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, loaidoituong, doituongId, soluong, booked, available, dongia, ngaysanxuat);
			return msg1;
	}
	
	
}