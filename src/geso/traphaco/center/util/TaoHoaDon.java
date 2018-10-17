package geso.traphaco.center.util;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaoHoaDon
{
		public static void main(String[] arg)
	{
		dbutils db = new dbutils();
		
		/*String query="Select  a.pk_seq as dhId,npp_fk as nppId,b.loainpp ,ngaynhap,khachhang_fk,a.NguoiTao from DonHang a inner join nhaphanphoi b on b.pk_seq=a.npp_fk where npp_Fk='106180' and a.pk_seq not in (select ddh_fk from hoadon_ddh ) and  DATEPART(month, a.NGAYNHAP) in (7,8,9)  and hopdong is null  and a.trangthai=1 ";*/
		
		String query="Select  a.pk_seq as dhId,npp_fk as nppId,b.loainpp ,ngaynhap,khachhang_fk,a.NguoiTao from DonHang a inner join nhaphanphoi b on b.pk_seq=a.npp_fk where a.pk_seq=232523  ";
		ResultSet rs =db.get(query);
		try
    {
	    while(rs.next())
	    {
	    	String dhId= rs.getString("dhId");
	    	String loainpp= rs.getString("loainpp");
	    	String ngaynhap= rs.getString("ngaynhap");
	    	String khId= rs.getString("khachhang_fk");
	    	String nppId= rs.getString("nppId");
	    	String userId= rs.getString("NguoiTao");
	    	
	    	String msg="";
	    	msg=DuyetDonHang_New(db,dhId,"0",userId);
	    	
	    	System.out.println(":::Main:::"+msg);
	    	if(msg.length()>0)
	    	{
	    		
	    	}
	    }
	  	System.out.println("_________________:::XONG__________:::");
    } catch (SQLException e)
    {
	    e.printStackTrace();
    }
		finally
		{
			if(db!=null)db.shutDown();
		}
		
	}
						
		private static String TaoHoaDonTaiChinh_New(dbutils db, String dhId, String nppId, String loainpp, String ngaynhap, String userId, String khId)
		{
			String msg = "";
			try
			{
				String query = "";
				String chuoi="";
				String kyhieuhoadon="";
				String sohoadon="";
				long sohoadontu=0;
				String sohoadonden="";
				String ngayhoadon= "";
				String trangthai= "1";
				int kbDLN=0;
				 	
				if(loainpp.equals("4") || loainpp.equals("5"))	// DOI TAC VA CHI NHANH CUA DOI TAC
				{
					kyhieuhoadon = "NA";
					sohoadon = "NA";
					ngayhoadon = ngaynhap;
					trangthai= "2";
				}
				else
				{
					//KIEM TRA CHI NHANH/DOITAC KBAO KYHIEU HD CHUA
					query= "select count(pk_seq) as dem " +
						   "from NHANVIEN where pk_seq = '" + userId + "' and  isnull(sohoadontu, '') != '' and isnull(sohoadonden, '') != ''  ";
					ResultSet KTDLN = db.get(query);
					if(KTDLN!= null)
					{
						while(KTDLN.next())
						{
							kbDLN = KTDLN.getInt("dem");
						}
						KTDLN.close();
					}
					
					if(kbDLN <= 0 )
					{
						msg = "Vui lòng khai báo Số hóa đơn trong menu Cập nhật nhân viên cho user này ";
						return msg;
					}
					else
					{
						// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
						query= " select (select kyhieuhoadon from NHAPHANPHOI where pk_seq = " + nppId + " ) as kyhieuhoadon, isnull(sohoadontu, -1) as sohoadontu, isnull(sohoadonden, -1) as sohoadonden, " +
								   " isnull(ngayhoadon, '') as ngayhoadon " +
							   " from NHANVIEN where pk_seq = '" + userId + "'";
				
						ResultSet rsLayDL = db.get(query);
						if(rsLayDL != null )
						{
							while(rsLayDL.next())
							{
								kyhieuhoadon= rsLayDL.getString("kyhieuhoadon");
								sohoadontu = rsLayDL.getLong("sohoadontu");
								sohoadonden = rsLayDL.getString("sohoadonden");
								ngayhoadon = rsLayDL.getString("ngayhoadon");
								if(ngayhoadon.trim().length() <= 0)
									ngayhoadon = ngaynhap;
							}
							rsLayDL.close();
						}
						
						if(sohoadontu == -1 || sohoadonden.equals("-1") )
						{
							msg = "Vui lòng thiết lập khoảng số hóa đơn cho USER";
							return msg;
						}
			
						// KIEM TRA SOHOADON CỦA KYHIEUHOADON DA DUOC DUNG CHUA
						query =" select count(pk_seq) as dem " +
							   " from HOADON " +
							   " where kyhieu = '"+ kyhieuhoadon +"' and cast(sohoadon as int) >=  "+ sohoadontu +" and cast(sohoadon as int) <=  "+ Integer.parseInt(sohoadonden) + "  " +
							   "       and trangthai != 3 and nguoisua= "+ userId +"  ";
						System.out.println("Câu kiểm tra SHD " + query);
						ResultSet KiemTra = db.get(query);
						int check = 0;
						if(KiemTra != null)
						{
							while(KiemTra.next())
							{
								check = KiemTra.getInt("dem");
							}
							KiemTra.close();
						}
							
						//ETC 	
						query = " select count(pk_seq) as dem " +
								" from ERP_HOADONNPP " +
								" where kyhieu = '" + kyhieuhoadon + "' and cast(sohoadon as numeric(18,0)) >= " + sohoadontu + " and cast(sohoadon as numeric(18,0)) <= " + Integer.parseInt(sohoadonden) + " " +
								"       and trangthai != 3 and nguoisua= "+ userId +"  ";
						
						System.out.println("Câu kiểm tra SHD "+query);
						ResultSet KiemTraETC = db.get(query);
						int check_ETC = 0;
						if(KiemTraETC != null)
						{
							while(KiemTraETC.next())
							{
								check_ETC = KiemTraETC.getInt("dem");
							}
							KiemTraETC.close();
						}
			
						// LAY SOIN MAX	
						if(check <= 0 && check_ETC <= 0)
						{
							chuoi = ("000000" + sohoadontu);
							chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
						}
						else
						{				
							// LAY SOIN MAX TRONG HOADON: OTC
							query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
									" from HOADON where KYHIEU = '" + kyhieuhoadon + "' and cast(sohoadon as numeric(18, 0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0)) <= " + Integer.parseInt(sohoadonden) + " " +
									"      and trangthai != 3 and nguoisua= "+ userId +" ";
							System.out.println("Câu lấy shd max "+query);
							ResultSet laySOIN = db.get(query);
						    long soinMAX_OTC= 0;
							if(laySOIN!= null)
							{
								while(laySOIN.next())
								{
									soinMAX_OTC = laySOIN.getLong("SOIN_MAX");
								}
								laySOIN.close();
							}
							
							 //ETC
							query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
									" from ERP_HOADONNPP where KYHIEU ='"+ kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"  and nguoisua= "+ userId +" ";
							System.out.println("Câu lấy shd max "+query);
							ResultSet laySOIN_ETC = db.get(query);
						    long soinMAX_ETC= 0;
							if(laySOIN_ETC!= null)
							{
								while(laySOIN_ETC.next())
								{
									soinMAX_ETC = laySOIN_ETC.getLong("SOIN_MAX");
									
								}
								laySOIN_ETC.close();
							}
							
							if( soinMAX_OTC > soinMAX_ETC ) 
							{
								chuoi = ("000000" + (soinMAX_OTC >0 ? (soinMAX_OTC +1) :"1"));
							}
							else
							{
								chuoi = ("000000" + (soinMAX_ETC >0 ? (soinMAX_ETC +1) :"1"));
							}
							
							chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
							
						}
						
						if(Integer.parseInt(chuoi) > Integer.parseInt(sohoadonden))
						{ 
							//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
							query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX  " +
									"from HOADON a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ " +
									"where  a.nguoisua= '"+ userId +"' and a.kyhieu = '"+ kyhieuhoadon +"' and trangthai != 3 " +
									"		and cast(a.SOHOADON as numeric) >= CAST(b.sohoadontu as numeric(18, 0) )   " +
									"		and cast(a.SOHOADON as numeric) <= CAST(b.sohoadonden as numeric(18, 0) )   " +
									"having "+
									" MAX(cast(SOHOADON as numeric)) != ( select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX  from HOADON where  kyhieu = '"+ kyhieuhoadon +"' and nguoisua= '"+ userId +"' )";
							
							System.out.println("Câu lấy shd max "+query);
							ResultSet SoMAX_HD = db.get(query);
							String soinmax= "";
							if(SoMAX_HD!= null)
							{
								while(SoMAX_HD.next())
								{
									soinmax = SoMAX_HD.getString("SOIN_MAX")== null ? "" :SoMAX_HD.getString("SOIN_MAX") ;
									chuoi = ("000000"+ (SoMAX_HD.getLong("SOIN_MAX")));
									
								}
								SoMAX_HD.close();
							}
							chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
					  
							if(soinmax.trim().length() <= 0)
							{
								msg = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến ("+ sohoadonden +")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
								return msg;
							}
						}
						
						sohoadon = chuoi;
				    } 
				}

				int inNguoimua = 1;
				if(nppId.equals("106210")) // NẾU LÀ CN HCM : inNguoimuahang =0 
				{
					inNguoimua = 0;
				}
					
				query = " insert HOADON(khachhang_fk, ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt ," +
				        " 		chietkhau, tongtienbvat, tongtienavat, vat, ghichu, tongtienkm,npp_fk, loaihoadon, nguoimua, innguoimua) " +
					    " select "+ khId +",'" + ngayhoadon + "', '"+ trangthai +"','" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + kyhieuhoadon + "'," +
					    "       '" + sohoadon + "', N'TM/CK' , '0', '0', '0'," +
					    "       '0', N'Phiếu xuất hóa đơn từ động từ đơn hàng " + dhId + " ', '0', " + nppId + " , '0', ( select isnull(chucuahieu,'') as nguoimua from KHACHHANG where pk_seq = '" + khId + "' ) , " + inNguoimua + " ";
				 
				System.out.println("1.Insert HOADON: " + query);
				if(db.updateReturnInt(query) <= 0 )
				{
					msg = "Không thể tạo mới HOADON " + query;
					return msg;
				}
				
				String hdId = "";
				query = "select SCOPE_IDENTITY() as hdId";
				ResultSet rs1 = db.get(query);
				rs1.next();
				hdId = rs1.getString("hdId");
				rs1.close();
				
				query = "Insert HOADON_DDH( hoadon_fk, ddh_fk ) values ( " + hdId + ",  " + dhId + " ) ";
				if(db.updateReturnInt(query) <= 0 )
				{
					msg = "Không thể tạo mới HOADON_DDH " + query;
					return msg;
				}
							
				query = "insert HOADON_SP( hoadon_fk, sanpham_fk, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme, vat ) " +
						" select " + hdId + ", "+
						" b.PK_SEQ, DV.donvi, sum( a.soluong), a.giamua ,sum( a.soluong) * a.giamua , '0', ' ' ,a.thuevat " +
						"from Donhang_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
						" 	  INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK       " +
						" where a.Donhang_FK = " + dhId + " " +
						" group by  b.PK_SEQ, DV.donvi, a.giamua, a.thuevat ";
				System.out.println("---CHAY HOA DON TONG: " + query);
				if(db.updateReturnInt(query) <= 0 )
				{
					msg = "Khong the tao moi ERP_HOADON_SP: " + query;
					return msg;
				}
				
				
				//LUU LAI THONG TIN CHIET KHAU
				query = "insert HOADON_CHIETKHAU(hoadon_fk, donhang_fk, diengiai, chietkhau, thueVAT, STT, tichluyQUY) " +
						"	select '" + hdId + "', donhang_fk, N'CN5' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 1 as STT, 0 as tichluyQUY  " +
						"	from DONHANG_SANPHAM  " +
						"	where thueVAT = '5' and donhang_fk = '" + dhId + "' " +
						"	group by donhang_fk, thueVAT " +
						"	 " +
						"union   " +
						"	select '" + hdId + "', donhang_fk, N'CN10' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 2 as STT, 0 as tichluyQUY   " +
						"	from DONHANG_SANPHAM   " +
						"	where thueVAT = '10' and donhang_fk = '" + dhId + "' " +
						"	group by donhang_fk, thueVAT " +
						"union  " +
						"	select '" + hdId + "', donhang_fk, a.diengiai, a.thanhtoan / ( 1 + ptTHUE / 100 ) as chietkhau, a.ptTHUE as thueVAT, 3 as STT, tichluyQUY   " +
						"	from DUYETTRAKHUYENMAI_DONHANG a inner join TIEUCHITHUONGTL b on a.duyetkm_fk = b.PK_SEQ   " +
						"	where a.thanhtoan > 0 and donhang_fk = '" + dhId + "'  " +
						"union  " +
						"	select '" + hdId + "', donhang_fk, a.maloai as diengiai, a.chietkhau, a.ptVAT as thueVAT, 4 as STT, 0 as tichluyQUY   " +
						"	from DONHANG_CHIETKHAUBOSUNG a   " +
						"	where a.chietkhau > 0 and donhang_fk = '" + dhId + "'  ";
				if( db.updateReturnInt(query) <= 0 )
				{
					//msg = "Khong the tao moi HOADON_CHIETKHAU: " + query;
					return msg;
				}
				
				query = 

				"		update hd set tongtienavat = a.AVAT, tongtienbvat = a.BVAT, vat = a.VAT  \n"+   
				"		from    \n"+
				"		(    \n"+
				"			select hd.PK_SEQ AS HDID,  \n"+  
				"				  ( HOADON_CT.tongtien - HOADON_CT.Chietkhau) as BVAT, ( HOADON_CT.VAT - HOADON_CT. VAT_KM) as VAT,  \n"+  
				"				  round((HOADON_CT.tongtien + HOADON_CT.VAT ) - ( HOADON_CT.Chietkhau +  HOADON_CT.VAT_KM), 0) AS AVAT    \n"+
				"			from HOADON hd inner join    \n"+
				"			(        \n"+
				"				select AA.HOADON_FK, AA.tongtien, AA.VAT, isnull(BB.Chietkhau,0) as ChietKhau , isnull(BB.VAT,0) as VAT_KM   \n"+  
				"				from    \n"+
				"				( \n"+  
				"				   select HOADON_fk, SUM( ROUND(SOLUONG * DONGIA, 0 ) ) as tongtien,  \n"+  
				"						SUM ( ROUND ( ( ROUND(SOLUONG * DONGIA, 0 ) * VAT / 100), 0 ) ) as VAT  \n"+   
				"				   from HOADON_SP where HOADON_fk ='" + dhId + "'    \n"+
				"				   group by HOADON_FK     \n"+
				"				 )  \n"+   
				"				 AA LEFT join  \n"+  
				"				 (  \n"+   
				"					select hoadon_fk, sum(chietkhau) as chietkhau, sum(chietkhau * thueVAT / 100) as VAT  \n"+   
				"					from HOADON_CHIETKHAU where HOADON_fk ='" + dhId + "'  \n"+   
				"					group by hoadon_fk     \n"+
				"				)     \n"+
				"				BB  ON AA. HOADON_FK = BB.HOADON_FK  \n"+   
				"			)     \n"+
				"			HOADON_CT on hd.PK_SEQ = HOADON_CT.HOADON_FK  \n"+  
				"		       left join KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n"+  
				"			where  hd.PK_SEQ = '" + dhId + "'   \n"+
				"		)   \n"+
				"		as a inner join HOADON hd on hd.PK_SEQ= a.HDID \n"+  
				"		where  hd.PK_SEQ = '" + dhId + "' " ; 
				
				if( !db.update(query) )
				{
					msg = "Khong the tao moi HOADON_CHIETKHAU: " + query;
					return msg;
				}
				
			} 
			catch (Exception e) 
			{
				msg = "Lỗi khi duyệt đơn hàng: " + e.getMessage();
				e.printStackTrace();
				return msg;
			}

			return msg;
		}
						
		private  static String TaoHoaDonTaiChinhKM_DHK_New(dbutils db, String dhId, String nppId, String ngaynhap, String userId, String khId) 
		{
			String msg = "";
			try
			{
				String query = "";
				String chuoi="";
				String kyhieuhoadon="";
				String sohoadon="";
				long sohoadontu=0;
				String sohoadonden="";
				String ngayhoadon= "";
				String trangthai = "1";
				int kbDLN=0;
				
				// Kiểm tra npp nếu là Đối tác thì không dùng Số hóa đơn của hệ thống
				query = " select loaiNPP " +
						" from NHAPHANPHOI " +
						" where pk_seq = '" + nppId + "' ";	
				ResultSet rsNpp = db.get(query);
				String loainpp= "";
				if(rsNpp!= null)
				{
					while(rsNpp.next())
					{
						loainpp = rsNpp.getString("loaiNPP");
					}
					rsNpp.close();
				}
					
				if(loainpp.equals("4") || loainpp.equals("5"))	// DOI TAC VA CHI NHANH CUA DOI TAC
				{
					kyhieuhoadon = "NA";
					sohoadon = "NA";
					ngayhoadon = ngaynhap;
					trangthai= "2";
				}
				else
				{
					//KIEM TRA CHI NHANH/DOITAC KBAO KYHIEU HD CHUA
					query= "select count(pk_seq) as dem from NHANVIEN where pk_seq= '"+ userId+"' and  isnull(sohoadontu, '') != '' and isnull(sohoadonden, '') != ''  ";
					ResultSet KTDLN = db.get(query);
					if(KTDLN!= null)
					{
						while(KTDLN.next())
						{
							kbDLN = KTDLN.getInt("dem");
						}
						KTDLN.close();
					}
					
					if(kbDLN <= 0 )
					{
						msg = "Vui lòng khai báo Số hóa đơn trong menu Cập nhật nhân viên cho user này ";
						return msg;
					}
					else
					{
						// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
						query= " select (select kyhieuhoadon from NHAPHANPHOI where pk_seq = " + nppId + " ) as kyhieuhoadon, isnull(sohoadontu, -1) as sohoadontu, isnull(sohoadonden, -1) as sohoadonden, " +
								   " isnull(ngayhoadon, '') as ngayhoadon " +
							   " from NHANVIEN where pk_seq = '" + userId + "'";
				
						ResultSet rsLayDL =  db.get(query);
						if(rsLayDL != null)
						{
							while(rsLayDL.next())
							{
								kyhieuhoadon= rsLayDL.getString("kyhieuhoadon");
								sohoadontu = rsLayDL.getLong("sohoadontu");
								sohoadonden = rsLayDL.getString("sohoadonden");
								ngayhoadon = rsLayDL.getString("ngayhoadon");
								if(ngayhoadon.trim().length() <= 0)
									ngayhoadon = ngaynhap;
							}
							rsLayDL.close();
						}
						
						if(sohoadontu == -1 || sohoadonden.equals("-1") )
						{
							msg = "Vui lòng thiết lập khoảng số hóa đơn cho USER";
							return msg;
						}
			
						// KIEM TRA SOHOADON DA DUOC DUNG CHUA: OTC
						query =" select count(pk_seq) as dem " +
							   " from HOADON" +
							   " where kyhieu = '"+ kyhieuhoadon +"' and cast(sohoadon as int) >=  "+ sohoadontu +" and cast(sohoadon as int) <=  "+ Integer.parseInt(sohoadonden) +"  " +
							   "       and trangthai != 3  and nguoisua= "+ userId +"  ";
						System.out.println("Câu kiểm tra SHD "+query);
						ResultSet KiemTra = db.get(query);
						int check = 0;
						if(KiemTra != null)
						{
							while(KiemTra.next())
							{
								check = KiemTra.getInt("dem");
							}
							KiemTra.close();
						}
			
						//ETC 
						query = " select count(pk_seq) as dem" +
								" from ERP_HOADONNPP" +
								" where kyhieu = '"+ kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
								"       and nguoisua= "+ userId +"  ";
						
						System.out.println("Câu kiểm tra SHD "+query);
						ResultSet KiemTraETC = db.get(query);
						int check_ETC = 0;
						if(KiemTraETC != null)
						{
							while(KiemTraETC.next())
							{
								check_ETC = KiemTraETC.getInt("dem");
							}
							KiemTraETC.close();
						}
							
						// LAY SOIN MAX	
						if(check <= 0 && check_ETC <= 0)
						{
							chuoi = ("000000"+ sohoadontu);
							chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
						}
						else
						{				
							// LAY SOIN MAX TRONG HOADON : OTC
							query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
									" from HOADON where KYHIEU ='"+ kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"      and trangthai != 3 and nguoisua= "+ userId +" ";
							System.out.println("Câu lấy shd max "+query);
							ResultSet laySOIN = db.get(query);
						    long soinMAX_OTC= 0;
							if(laySOIN!= null)
							{
								while(laySOIN.next())
								{
									soinMAX_OTC = laySOIN.getLong("SOIN_MAX");
									
								}laySOIN.close();
							}
							
							//ETC
							query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
									" from ERP_HOADONNPP where KYHIEU ='"+ kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"      and nguoisua= "+ userId +" ";
							System.out.println("Câu lấy shd max "+query);
							ResultSet laySOIN_ETC = db.get(query);
						    long soinMAX_ETC= 0;
							if(laySOIN_ETC!= null)
							{
								while(laySOIN_ETC.next())
								{
									soinMAX_ETC = laySOIN_ETC.getLong("SOIN_MAX");
									
								}laySOIN_ETC.close();
							}
							
							if(soinMAX_OTC > soinMAX_ETC) 
							{
								chuoi = ("000000"+ (soinMAX_OTC >0 ? (soinMAX_OTC +1) :"1"));
							}
							else
							{
								chuoi = ("000000"+ (soinMAX_ETC >0 ? (soinMAX_ETC +1) :"1"));
							}
							
							chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
							
						}
						if(Integer.parseInt(chuoi) > Integer.parseInt(sohoadonden))
						{ 
							//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
							query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX  " +
									"from HOADON a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ " +
									"where  a.nguoisua= '"+ userId +"' and a.kyhieu = '"+ kyhieuhoadon +"' and trangthai != 3 " +
									"		and cast(a.SOHOADON as numeric) >= CAST(b.sohoadontu as numeric(18, 0) )   " +
									"		and cast(a.SOHOADON as numeric) <= CAST(b.sohoadonden as numeric(18, 0) )   " +
									"having "+
									" MAX(cast(SOHOADON as numeric)) != ( select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX  from HOADON where  kyhieu = '"+ kyhieuhoadon +"' and nguoisua= '"+ userId +"' )";
							
							System.out.println("Câu lấy shd max "+query);
							ResultSet SoMAX_HD = db.get(query);
							String soinmax= "";
							if(SoMAX_HD!= null)
							{
								while(SoMAX_HD.next())
								{
									soinmax = SoMAX_HD.getString("SOIN_MAX")== null ? "" :SoMAX_HD.getString("SOIN_MAX") ;
									chuoi = ("000000"+ (SoMAX_HD.getLong("SOIN_MAX")));
									
								}SoMAX_HD.close();
							}
							chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
					  
							if(soinmax.trim().length() <= 0)
							{
								msg = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến ("+ sohoadonden +")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
								return msg;
							}
						}
						sohoadon =  chuoi;
					} 
					
				}	
				
				// LAY TEN NGUOI MUA TRONG DLN
				int inNguoimua = 1;
				if(nppId.equals("106210")) // NẾU LÀ CN HCM : inNguoimuahang =0 
				{
					inNguoimua = 0;
				}
					
				query = "insert HOADON(khachhang_fk, ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt ," +
				       " 	chietkhau, tongtienbvat, tongtienavat, vat, ghichu, tongtienkm,npp_fk, loaihoadon, nguoimua, innguoimua) " +
					   " select "+ khId +",'" + ngayhoadon + "', '"+ trangthai +"','" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + kyhieuhoadon + "'," +
					   "       '" + sohoadon + "', N'TM/CK' , '0', '0', '0'," +
					   "       '0', N'Phiếu xuất hóa đơn từ động từ đơn hàng " + dhId + " ', '0', "+ nppId +" , '1', ( select isnull(chucuahieu,'') as nguoimua from KHACHHANG where pk_seq= '"+ khId +"' ), " + inNguoimua;
				 
				System.out.println("1.Insert HOADON: " + query);
				if(db.updateReturnInt(query) <= 0 )
				{
					msg = "Không thể tạo mới HOADON " + query;
					return msg;
				}
				
				String hdId = "";
				query = "select SCOPE_IDENTITY() as hdId";
				ResultSet rs1 = db.get(query);
				rs1.next();
				hdId = rs1.getString("hdId");
				rs1.close();
				
				query = "Insert HOADON_DDH(hoadon_fk, ddh_fk) values ("+ hdId +",  " + dhId + " ) ";
				if(db.updateReturnInt(query) <= 0 )
				{
					msg = "Không thể tạo mới HOADON_DDH " + query;
					return msg;
				}
							
				query = "insert HOADON_CTKM_TRAKM( hoadon_fk, sanpham_fk, sanphamma, donvi, soluong, ctkm ,dongia, vat) " +
						" select "+ hdId +", "+
						" b.PK_SEQ, b.MA, DV.donvi, sum( a.soluong), ' ' as ctkm, a.giamua , a.thuevat " +
						"from Donhang_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
						" 	  INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK       " +
						" where a.Donhang_FK = " + dhId + " " +
						" group by  b.PK_SEQ, b.MA, DV.donvi, a.giamua, a.thuevat ";
				
				System.out.println("1.1.Insert ERP_HOADON_SP: " + query);
				if(db.updateReturnInt(query) <= 0 )
				{
					msg = "Khong the tao moi ERP_HOADON_SP: " + query;
					return msg;
				}
				
				//TRUOGN HOP SAU NAY MUON IN SOLO
				query = "insert HOADON_CTKM_TRAKM_CHITIET(hoadon_fk, sanpham_fk, soluong, scheme, solo, ngayhethan, kbh_fk, kho_fk, dongia )  " +
						"select '" + hdId + "' hoadon_fk, sanpham_fk, soluong, NULL as scheme, solo, ngayhethan, kbh_fk, kho_fk, " +
						"		ISNULL ( ( select giabanleNPP from BGBANLENPP_SANPHAM where sanpham_fk = a.sanpham_fk and BGBANLENPP_FK in ( select pk_seq from BANGGIABANLENPP where npp_fk = '100002'  ) ), 0) as dongia " +
						"from PHIEUXUATKHO_SANPHAM_CHITIET a " +
						"where PXK_FK = ( select pxk_fk from PHIEUXUATKHO_DONHANG where donhang_fk = '" + dhId + "' ) ";
				System.out.println("1.2.Insert HOADON_CTKM_TRAKM_CHITIET: " + query);
				if(db.updateReturnInt(query) <= 0 )
				{
					msg = "Khong the tao moi ERP_HOADON_SP: " + query;
					return msg;
				}
				
				// CAP NHAT LAI VAT, BVAT, AVAT CHO HOA DON		
				query = "update hd set hd.TONGTIENBVAT = gt.BVAT, hd.TONGTIENAVAT = round( gt.AVAT, 0), VAT = gt.VAT  " +
						"from HOADON hd inner join " +
						"( " +
						"	select a.HOADON_FK, SUM(a.SOLUONG * a.DONGIA) as BVAT, SUM(a.SOLUONG * a.DONGIA * a.VAT / 100) as VAT,  " +
						"		 SUM(a.SOLUONG * a.DONGIA) + SUM(a.SOLUONG * a.DONGIA * a.VAT / 100) as AVAT  " +
						"	from HOADON_CTKM_TRAKM a  " +
						"	where a.hoadon_fk = '" + hdId + "'  " +
						"	group by a.HOADON_FK " +
						") " +
						"gt on hd.pk_seq = gt.hoadon_fk " +
						"where hd.pk_seq = '" + hdId + "' ";
				if(db.updateReturnInt(query) <= 0 )
				{
					msg = "Khong the tao moi HOADON: " + query;
					return msg;
				}	

			} 
			catch (Exception e) 
			{
				msg = "Exception: " + e.getMessage();
				e.printStackTrace();
				return msg;
			}
			
			System.out.println("MSG "+msg);
			return msg;
		
		}
		
		private static String TaoHoaDonTaiChinhKM_New(dbutils db, String dhId, String nppId, String loainpp, String ngaynhap, String userId, String khId) 
		{
			String msg = "";
			try
			{
				String query = "";
				String chuoi="";
				String kyhieuhoadon="";
				String sohoadon="";
				long sohoadontu=0;
				String sohoadonden="";
				String ngayhoadon= "";
				String trangthai = "1";
				int kbDLN=0;
				 	
				if(loainpp.equals("4") || loainpp.equals("5"))	// DOI TAC VA CHI NHANH CUA DOI TAC
				{
					kyhieuhoadon = "NA";
					sohoadon = "NA";
					ngayhoadon = ngaynhap;
					trangthai= "2";
				}
				else
				{
					//KIEM TRA CHI NHANH/DOITAC KBAO KYHIEU HD CHUA
					query= "select count(pk_seq) as dem from NHANVIEN where pk_seq = '"+ userId+"' and  isnull(sohoadontu, '') != '' and isnull(sohoadonden, '') != ''  ";
					ResultSet KTDLN = db.get(query);
					if(KTDLN!= null)
					{
						while(KTDLN.next())
						{
							kbDLN = KTDLN.getInt("dem");
						}
						KTDLN.close();
					}
					
					if(kbDLN <= 0 )
					{
						msg = "Vui lòng khai báo Số hóa đơn trong menu Cập nhật nhân viên cho user này ";
						return msg;
					}
					else
					{
						// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
						query= " select (select kyhieuhoadon from NHAPHANPHOI where pk_seq = " + nppId + " ) as kyhieuhoadon, isnull(sohoadontu, -1) as sohoadontu, isnull(sohoadonden, -1) as sohoadonden, " +
								   " isnull(ngayhoadon, '') as ngayhoadon " +
							   " from NHANVIEN where pk_seq = '" + userId + "'";
				
						ResultSet rsLayDL =  db.get(query);
						if(rsLayDL != null)
						{
							while(rsLayDL.next())
							{
								kyhieuhoadon= rsLayDL.getString("kyhieuhoadon");
								sohoadontu = rsLayDL.getLong("sohoadontu");
								sohoadonden = rsLayDL.getString("sohoadonden");
								ngayhoadon = rsLayDL.getString("ngayhoadon");
							}
							rsLayDL.close();
						}
			
						if(sohoadontu == -1 || sohoadonden.equals("-1") )
						{
							msg = "Vui lòng thiết lập khoảng số hóa đơn cho USER";
							return msg;
						}
						
						// KIEM TRA SOHOADON DA DUOC DUNG CHUA: OTC
					    query =" select count(pk_seq) as dem " +
							   " from HOADON" +
							   " where kyhieu = '"+ kyhieuhoadon +"' and cast(sohoadon as int) >=  "+ sohoadontu +" and cast(sohoadon as int) <=  "+ Integer.parseInt(sohoadonden) +" " +
							   "       and trangthai != 3 and nguoisua= "+ userId +"  ";
						System.out.println("Câu kiểm tra SHD "+query);
						ResultSet KiemTra = db.get(query);
						int check = 0;
						if(KiemTra != null)
						{
							while(KiemTra.next())
							{
								check = KiemTra.getInt("dem");
							}
							KiemTra.close();
						}
						
						//ETC 
						query = " select count(pk_seq) as dem" +
								" from ERP_HOADONNPP" +
								" where kyhieu = '"+ kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
								" and nguoisua= "+ userId +"  ";
						
						System.out.println("Câu kiểm tra SHD "+query);
						ResultSet KiemTraETC = db.get(query);
						int check_ETC = 0;
						if(KiemTraETC != null)
						{
							while(KiemTraETC.next())
							{
								check_ETC = KiemTraETC.getInt("dem");
							}
							KiemTraETC.close();
						}
							
						// LAY SOIN MAX	
						if(check <= 0 && check_ETC <= 0)
						{
							chuoi = ("000000"+ sohoadontu);
							chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
						}
						else
						{
							//OTC
							query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
									" from HOADON where KYHIEU ='"+ kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"      and trangthai != 3 and nguoisua= "+ userId +" ";
							System.out.println("Câu lấy shd max "+query);
							ResultSet laySOIN = db.get(query);
						    long soinMAX_OTC= 0;
							if(laySOIN!= null)
							{
								while(laySOIN.next())
								{
									soinMAX_OTC = laySOIN.getLong("SOIN_MAX");
									
								}laySOIN.close();
							}
							
							 //ETC
							query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
									" from ERP_HOADONNPP where KYHIEU ='"+ kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									" and  nguoisua= "+ userId +" ";
							System.out.println("Câu lấy shd max "+query);
							ResultSet laySOIN_ETC = db.get(query);
						    long soinMAX_ETC= 0;
							if(laySOIN_ETC!= null)
							{
								while(laySOIN_ETC.next())
								{
									soinMAX_ETC = laySOIN_ETC.getLong("SOIN_MAX");
									
								}laySOIN_ETC.close();
							}
							
							if(soinMAX_OTC > soinMAX_ETC) 
							{
								chuoi = ("000000"+ (soinMAX_OTC >0 ? (soinMAX_OTC +1) :"1"));
							}
							else
							{
								chuoi = ("000000"+ (soinMAX_ETC >0 ? (soinMAX_ETC +1) :"1"));
							}
							
							chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
						}
						
						if(Integer.parseInt(chuoi) > Integer.parseInt(sohoadonden))
						{ 
							//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
							query = "select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX  " +
									"from HOADON a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ " +
									"where  a.nguoisua= '"+ userId +"' and a.kyhieu = '"+ kyhieuhoadon +"' and trangthai != 3 " +
									"		and cast(a.SOHOADON as numeric) >= CAST(b.sohoadontu as numeric(18, 0) )   " +
									"		and cast(a.SOHOADON as numeric) <= CAST(b.sohoadonden as numeric(18, 0) )   " +
									"having "+
									" MAX(cast(SOHOADON as numeric)) != ( select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX  from HOADON where  kyhieu = '"+ kyhieuhoadon +"' and nguoisua= '"+ userId +"' )";
							
							System.out.println("Câu lấy shd max "+query);
							ResultSet SoMAX_HD = db.get(query);
							String soinmax= "";
							if(SoMAX_HD!= null)
							{
								while(SoMAX_HD.next())
								{
									soinmax = SoMAX_HD.getString("SOIN_MAX")== null ? "" :SoMAX_HD.getString("SOIN_MAX") ;
									chuoi = ("000000"+ (SoMAX_HD.getLong("SOIN_MAX")));
									
								}SoMAX_HD.close();
							}
							chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
					  
							if(soinmax.trim().length() <= 0)
							{
								msg = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến ("+ sohoadonden +")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
								return msg;
							}
						}
						sohoadon =  chuoi;
					} 
				 }
		
				 int inNguoimua = 1;
				 if(nppId.equals("106210")) // NẾU LÀ CN HCM : inNguoimuahang =0 
				 {
					inNguoimua = 0;
				 }
									
				 query = " insert HOADON(khachhang_fk, ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt ," +
				        " chietkhau, tongtienbvat, tongtienavat, vat, ghichu, tongtienkm,npp_fk, loaihoadon, nguoimua, innguoimua) " +
					    " select "+ khId +",'" + ngayhoadon + "', '"+ trangthai +"','" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + kyhieuhoadon + "'," +
					    "       '" + sohoadon + "', N'TM/CK' , '0', '0', '0'," +
					    "       '0', N'Phiếu xuất hóa đơn từ động từ đơn hàng " + dhId + " ', '0', "+ nppId +" , '1', ( select isnull(chucuahieu,'') as nguoimua from KHACHHANG where pk_seq= '"+ khId +"' ), "+ inNguoimua +" ";
				 
				 System.out.println("1.Insert HOADON: " + query);
				 if(db.updateReturnInt(query) <= 0 )
				 {
					msg = "Không thể tạo mới HOADON " + query;
					return msg;
				 }
		
				 String hdId = "";
				 query = "select SCOPE_IDENTITY() as hdId";
				 ResultSet rs1 = db.get(query);
				 rs1.next();
				 hdId = rs1.getString("hdId");
				 rs1.close();
				
				 query = "Insert HOADON_DDH(hoadon_fk, ddh_fk) values ( " + hdId + ", " + dhId + ")  ";
				 if(db.updateReturnInt(query) <= 0 )
				 {
					msg = "Không thể tạo mới HOADON_DDH " + query;
					return msg;
				 }
						
				 query = "insert HOADON_CTKM_TRAKM( hoadon_fk, sanpham_fk, sanphamma, donvi, soluong, ctkm, dongia, vat) " +
				        " select " + hdId + ", d.pk_seq, d.MA, donvi, a.soluong, b.scheme, " +
					    " 	ISNULL ( ( select giabanleNPP from BGBANLENPP_SANPHAM where sanpham_fk = (select pk_seq from SANPHAM where ma = a.spma) and BGBANLENPP_FK in ( select pk_seq from BANGGIABANLENPP where npp_fk = '" + nppId + "'  ) ), 0) as dongia, "+
			            " 	( select THUEXUAT from NGANHHANG where pk_seq = d.nganhhang_fk ) as VAT  "+
					    "from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq " +
					    "	  inner join sanpham d on a.spMa = d.ma "+
	  				    "     inner join donvidoluong dv on dv.pk_seq = d.dvdl_fk "+
	 				    "where a.donhangId in (" + dhId + ") and a.spMA is not null ";
				
				 System.out.println("1.1.Insert ERP_HOADON_SP: " + query);
				 if(db.updateReturnInt(query) <= 0 )
				 {
				 	msg = "Khong the tao moi ERP_HOADON_SP: " + query;
				 	return msg;
				 }
				 
				 //TRUOGN HOP SAU NAY MUON IN SOLO
				 query = "insert HOADON_CTKM_TRAKM_CHITIET(hoadon_fk, sanpham_fk, soluong, scheme, solo, ngayhethan, kbh_fk, kho_fk, dongia )  " +
						 "select '" + hdId + "' hoadon_fk, sanpham_fk, soluong, scheme, solo, ngayhethan, kbh_fk, kho_fk, " +
						 "		ISNULL ( ( select giabanleNPP from BGBANLENPP_SANPHAM where sanpham_fk = a.sanpham_fk and BGBANLENPP_FK in ( select pk_seq from BANGGIABANLENPP where npp_fk = '" + nppId + "'  ) ), 0) as dongia " +
						 "from PHIEUXUATKHO_SPKM_CHITIET a " +
						 "where PXK_FK = ( select pxk_fk from PHIEUXUATKHO_DONHANG where donhang_fk = '" + dhId + "' ) ";
				 System.out.println("1.2.Insert HOADON_CTKM_TRAKM_CHITIET: " + query);
				 if(db.updateReturnInt(query) <= 0 )
				 {
				 	msg = "Khong the tao moi ERP_HOADON_SP: " + query;
				 	return msg;
				 }
				 
				 // CAP NHAT LAI VAT, BVAT, AVAT CHO HOA DON		
				 query = "update hd set hd.TONGTIENBVAT = gt.BVAT, hd.TONGTIENAVAT = round( gt.AVAT, 0), VAT = gt.VAT  " +
						 "from HOADON hd inner join " +
						 "( " +
						 "	select a.HOADON_FK, SUM(a.SOLUONG * a.DONGIA) as BVAT, SUM(a.SOLUONG * a.DONGIA * a.VAT / 100) as VAT,  " +
						 "		 SUM(a.SOLUONG * a.DONGIA) + SUM(a.SOLUONG * a.DONGIA * a.VAT / 100) as AVAT  " +
						 "	from HOADON_CTKM_TRAKM a  " +
						 "	where a.hoadon_fk = '" + hdId + "'  " +
						 "	group by a.HOADON_FK " +
						 ") " +
						 "gt on hd.pk_seq = gt.hoadon_fk " +
						 "where hd.pk_seq = '" + hdId + "' ";
				if(db.updateReturnInt(query) <= 0 )
				{
					msg = "Khong the tao moi ERP_HOADON_SP: " + query;
					return msg;
				}
			} 
			catch (Exception e) 
			{
				//db.update("rollback");
				msg = "Exception: " + e.getMessage();
				e.printStackTrace();
				return msg;
			}
			System.out.println("MSG "+msg);
			return msg;
		
		}
				
		private static String Chotphieuxuat_New(dbutils db, String pxkId, String dhId, String nppId, String ngaylap)
		{
			String query="";
			try 
			{
				query = " update a set a.tonggiatri = ISNULL( ( ( select sum( (soluong * giamua) - chietkhau ) from donhang_sanpham  where donhang_fk = a.DONHANG_FK  group by donhang_fk )   " +
						" 								- isnull( ( select sum(tonggiatri) as giatriKM from donhang_ctkm_trakm where donhangId = a.DONHANG_FK and SPMA is null ), 0 ) ), 0 )  " +
						" from phieuxuatkho_donhang a  " +
						" where a.pxk_fk = '" + pxkId + "' ";
				if(!db.update(query))
				{
					return "2.Lỗi khi chốt xuất kho: " + query;
				}
				
				//INSERT SAN PHAM
				query = " Insert into phieuxuatkho_sanpham(pxk_fk, sanpham_fk, kho_fk, kbh_fk, soluong ) " +
						" select '" + pxkId + "', b.pk_seq as spId, c.kho_fk as khoId, c.kbh_fk as kbhId, sum(a.soluong) as soluong " +
						" from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq   " +
						" where c.trangthai != 2 and a.donhang_fk in ( " + dhId + " )  " +
						" group by c.kho_fk, c.kbh_fk, b.pk_seq ";
				System.out.println("---4.CHEN SP: " + query);
				if(db.updateReturnInt(query) <= 0 )
				{
					return "4.Lỗi khi chốt xuất kho: " + query;
				}
				
				query=
				" insert into PHIEUXUATKHO_SANPHAM_CHITIET (PXK_FK, KBH_FK, KHO_FK, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN) "+ 
				" select '" + pxkId + "', b.pk_seq as spId, c.kho_fk as khoId, c.kbh_fk as kbhId, sum(a.soluong) as soluong,'NA' as SoLo,'2030-12-31' " +
				" from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq   " +
				" where c.trangthai != 2 and a.donhang_fk in ( " + dhId + " )  " +
				" group by c.kho_fk, c.kbh_fk, b.pk_seq ";
				if(db.updateReturnInt(query) <= 0 )
				{
					return "4.Lỗi khi chốt xuất kho: " + query;
				}
				
				//B1. CAP NHAT KHO TONG
				//CHECK TONG KHO CHI TIET PHAI BANG TONG TRONG KHO TONG
				//INSERT SPKM
				query = " Insert into phieuxuatkho_spkm(pxk_fk, sanpham_fk, kho_fk, kbh_fk, scheme, soluong, khoNVBH, NVBH_FK)  " +
						" select '" + pxkId + "', d.pk_seq as spId,  b.kho_fk as khoId, e.kbh_fk as kbhId, a.ctkmId, sum(a.soluong) as soluong, a.khoNVBH, ( case when khoNVBH = 0 then NULL else e.ddkd_fk end )  " +
						" from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq  " +
						" where e.trangthai != 2 and a.spMa is not null and a.donhangId in ( " + dhId + " )  " +
						" group by b.kho_fk, e.kbh_fk, a.ctkmId, d.pk_seq, khoNVBH, ( case when khoNVBH = 0 then NULL else e.ddkd_fk end )  ";
				if(!db.update(query))
				{
					//db.getConnection().rollback();
					return "4.Lỗi khi chốt xuất kho: " + query;
				}
				query = 
						"insert into PHIEUXUATKHO_SPKM_CHITIET (PXK_FK, KBH_FK, KHO_FK, SANPHAM_FK, SCHEME, SOLUONG, SOLO, NGAYHETHAN) " +
						"		select '"+pxkId+"', e.KBH_FK,b.KHO_FK,d.PK_SEQ,a.CTKMID,sum(a.SOLUONG) as SoLuong,'NA','2030-12-31'     "+
						"		from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq  "+
						"			inner join sanpham d on a.spMa = d.ma  "+
						"		inner join donhang e on a.donhangId = e.pk_seq "+  
						"		where e.trangthai != 2 and a.spMa is not null and a.donhangId in ( " + dhId + " ) "+  
						"		group by b.kho_fk, e.kbh_fk, a.ctkmId, d.pk_seq, khoNVBH, ( case when khoNVBH = 0 then NULL else e.ddkd_fk end )  ";
				if(!db.update(query))
				{
					//db.getConnection().rollback();
					return "4.Lỗi khi chốt xuất kho: " + query;
				}
				//B1. CAP NHAT KHO TONG HANG KHUYEN MAI
				
				query = " Insert into phieuxuatkho_tienkm(pxk_fk, scheme, tonggiatri) " +
						" select '" + pxkId + "', ctkmID, sum(a.tonggiatri) as tonggiatri  " +
						" from donhang_ctkm_trakm a inner join donhang b on a.donhangId = b.pk_seq  " +
						" where b.trangthai != '2' and a.spMa is null and a.donhangId in ( " + dhId + " )  " +
						" group by ctkmID " ;
				if(!db.update(query))
				{
					//db.getConnection().rollback();
					return "5.Lỗi khi chốt xuất kho: " + query;
				}
				
				query = "update phieuxuatkho set trangthai = '1' where pk_seq = '" + pxkId + "' ";
				if(!db.update(query))
				{
					//db.getConnection().rollback();
					return "8.Lỗi khi chốt xuất kho: " + query;
				}			
			} 
			catch (Exception e) 
			{	
				e.printStackTrace();
				return "Lỗi khi chốt PXK: " + e.getMessage();
			}
			return "";
		}
		
		public static String  DuyetDonHang_New(dbutils db ,String dhId, String vitriBAM, String userId)
		{
			String pxkId = "";
			String loaiNPP = "";
			String tuxuatOTC = "";
			
			try 
			{
				db.getConnection().setAutoCommit(false);
				//Kiểm tra trong trường hợp 2 user chốt cùng một lúc
				String query =  "select trangthai,  " +
								"	( select count(*) from HOADON_DDH where ddh_fk = dh.pk_seq  " +
								"			and hoadon_fk in ( select pk_seq from HOADON where trangthai in ( 1, 2, 4 ) and npp_fk = ( select npp_fk from DONHANG where pk_seq = '" + dhId + "' ) and loaihoadon = '0' ) ) as soDongHD, " +
								"   isnull(donhangkhac, 0) as donhangkhac, " +
								"	( select count(*) from DONHANG_CTKM_TRAKM where donhangID = dh.pk_seq and spMA is not null ) as sodongKM, " +
								"   ( select count(pk_seq) from NHAPHANPHOI where pk_seq = dh.npp_fk and TUTAOHOADON = '1' and priandsecond = '1' ) as tuxuatHD   " +
								"from DONHANG dh where pk_seq = '" + dhId + "' ";
				ResultSet checkDH = db.get(query);
				String trangthaiDH = "";
				int isDHK = 0;
				int isKM = 0;
				int soHD = 0;
				int tuxuatHD = 0;
				if(checkDH != null)
				{
					while(checkDH.next())
					{
						trangthaiDH = checkDH.getString("trangthai");
						soHD = checkDH.getInt("soDongHD");
						isDHK = checkDH.getInt("donhangkhac");
						isKM = checkDH.getInt("sodongKM");
						tuxuatHD =1;// checkDH.getInt("tuxuatHD");
					}
					checkDH.close();
				}
				
				
				if(soHD >= 2)
				{
					db.getConnection().rollback();
					return "Đơn hàng này đã có hóa đơn rồi. Vui lòng kiểm tra lại" ;
				}
				
				
				//DON HANG KHONG DUOC VUOT QUA 13 DONG
				int sodongHD = 0;
				query = "select ( select count(*) from DONHANG_SANPHAM where donhang_fk = a.pk_seq ) +  " +
						"	   ( select count(*) from DONHANG_CHIETKHAUBOSUNG where donhang_fk = a.pk_seq ) +  " +
						"	   (  " +
						"			select count(*) " +
						"			from " +
						"			( " +
						"				select N'CN5' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 1 as STT, 0 as tichluyQUY, 0 as cotheXOA from DONHANG_SANPHAM   " +
						"				where donhang_fk = '" + dhId + "' and thueVAT = '5' group by thueVAT  " +
						"			union   " +
						"				select N'CN10' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 2 as STT, 0 as tichluyQUY, 0 as cotheXOA   " +
						"				from DONHANG_SANPHAM   " +
						"				where donhang_fk = '" + dhId + "' and thueVAT = '10' group by thueVAT  " +
						"			union  " +
						"				select a.diengiai, a.thanhtoan / ( 1 + ptTHUE / 100 ) as chietkhau, a.ptTHUE as thueVAT, 3 as STT, tichluyQUY, 1 as cotheXOA   " +
						"				from DUYETTRAKHUYENMAI_DONHANG a inner join TIEUCHITHUONGTL b on a.duyetkm_fk = b.PK_SEQ    " +
						"				where a.donhang_fk = '" + dhId + "' and a.thanhtoan != 0 " +
						"			) " +
						"			TL " +
						"	   )  " +
						"	   as soDONG " +
						"from DONHANG a where pk_seq = '" + dhId + "' ";
				System.out.println("----CHECK 13 DONG: " + query );
				
				ResultSet rsSODONG = db.get(query);
				if(rsSODONG.next())
				{
					sodongHD = rsSODONG.getInt("soDONG");
					rsSODONG.close();
				}
				
				if(sodongHD > 13)
				{
					db.getConnection().rollback();
					return "Số dòng sản phẩm và chiết khấu của đơn hàng không được vượt quá 13 dòng ";
				}
				
				 query = "select b.loaiNPP, a.NPP_FK, a.ngaynhap, b.priandsecond as tuchotOTC, a.khachhang_fk, " +
								"	ISNULL( ( select count(*) from PHIEUXUATKHO_DONHANG where donhang_fk = a.pk_seq and PXK_FK in ( select pk_seq from PHIEUXUATKHO where NPP_FK = b.pk_seq and trangthai != '2' ) ), 0) as exitPXK " +
							   "from DONHANG a inner join NHAPHANPHOI b on a.NPP_FK = b.pk_seq where a.pk_seq = '" + dhId + "' ";
				System.out.println("---DUYET DON HANG: " + query);
				boolean exitPXK = false;
				String nppId = "";
				String ngaynhap = "";
				tuxuatOTC = "";
				String khachhang_fk="";
				
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					if(rs.next())
					{
						loaiNPP = rs.getString("loaiNPP");
						nppId = rs.getString("NPP_FK");
						ngaynhap = rs.getString("ngaynhap");
						tuxuatOTC = rs.getString("tuchotOTC");
						khachhang_fk =rs.getString("khachhang_fk");
						
						if(rs.getInt("exitPXK") > 0 )
							exitPXK = true;
					}
					rs.close();
					
					if(!exitPXK) //Chưa có PXK mới tạo
					{	
						query = "insert Phieuxuatkho(ngaylapphieu, nvgn_fk, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, npp_fk) " +
								"select ngaynhap, isnull(nvgn_fk, ( select top(1) pk_seq from NHANVIENGIAONHAN where NPP_FK = a.NPP_FK ) ), '0', '" + getDateTime() + "', '" + getDateTime() + "', '" + userId + "', '" + userId + "', npp_fk " +
								"from DONHANG a " +
								"where pk_seq = '" + dhId + "' ";
						System.out.println("---INSERT PXK; " + query );
						if(db.updateReturnInt(query) <= 0 )
						{
							db.getConnection().rollback();
							return "1.Lỗi khi duyệt đơn hàng: " + query;
						}
						
						query = "select Scope_Identity() as pxkId";
						rs = db.get(query);
						rs.next();
						pxkId = rs.getString("pxkId");
						rs.close();
						
						query = "Insert into phieuxuatkho_donhang(pxk_fk, donhang_fk, tonggiatri) " +
								"values('" + pxkId + "', '" + dhId + "', null)";
						System.out.println("---INSERT phieuxuatkho_donhang: " + query );
						if(db.updateReturnInt(query) <= 0 )
						{
							db.getConnection().rollback();
							return "2.Lỗi khi duyệt đơn hàng: " + query;
						}
					}else 
					{
						query=" select pxk_fk from phieuxuatkho_donhang where donhang_fk='"+dhId+"' ";
						rs= db.get(query);
						while(rs.next())
						{
							pxkId=rs.getString("pxk_FK");
						}
								
					}
					
					if( tuxuatOTC.equals("1") )
					{
						//CHECK VA TU DONG TRU TON KHO NHA PHAN PHOI --> COPY CHO CHOT PXK QUA
						String msg = Chotphieuxuat_New(db, pxkId, dhId, nppId, ngaynhap);
						if(msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return "4.Lỗi khi duyệt đơn hàng: " + msg;
						}	
					}
				}
				else
				{
					db.getConnection().rollback();
					return "44.Lỗi khi duyệt đơn hàng.";
				}
				
				System.out.println("::"+tuxuatHD);
				
				// KIỂM TRA CN/DOITAC CHECK TUTAOHOADON : TỰ ĐỘNG XUẤT HÓA ĐƠN TÀI CHÍNH
				if(tuxuatHD > 0 && isDHK == 0)
				{
					// TU DONG TAO HOA DON TAI CHINH 			
					String msg = TaoHoaDonTaiChinh_New(db, dhId, nppId, loaiNPP, ngaynhap, userId, khachhang_fk);
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return "4.Lỗi khi tạo hóa đơn tài chính : " + msg;
					}
					
					// TU TAO HD KHUYEN MAI NEU CO
					if( isKM > 0 )
					{
						// TU DONG TAO HOA DON TAI CHINH KM				
						msg = TaoHoaDonTaiChinhKM_New(db, dhId, nppId, loaiNPP, ngaynhap, userId, khachhang_fk);
						if(msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return "4.Lỗi khi tạo hóa đơn tài chính KM  : " + msg;
						}
					}
				}
				else if (tuxuatHD > 0 && isDHK == 1)
				{
					// TU DONG TAO HOA DON TAI CHINH KM				
					String msg = TaoHoaDonTaiChinhKM_DHK_New(db, dhId, nppId, ngaynhap, userId, khachhang_fk);
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return "4.Lỗi khi tạo hóa đơn tài chính KM cua DON HANG KHAC : " + msg;
					}
				}
				
				query="update DonHang set DAXUATHOADON=1 where pk_Seq='"+dhId+"' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "4.Cập nhật trạng thái ĐH(đã in HĐ) : " ;
				}
				
				db.getConnection().commit();
				
				//CAP NHAT LAI CAC COT TRONG DON HANG
				Utility util = new Utility();
				util.Update_GiaTri_DonHang(dhId, db);
			} 
			catch (Exception e) 
			{
				try {
					db.getConnection().rollback();
				}
				catch (Exception e1) { }
				
				e.printStackTrace();
				return "Lỗi khi duyệt đơn hàng: " + e.getMessage();
				
			}
			
			if(vitriBAM.equals("0"))
			{
				if( tuxuatOTC.equals("1") )
					return "Duyệt đơn hàng thành công.";
				else
					return "Duyệt đơn hàng thành công. Số phiếu xuất kho mới tạo là ( " + pxkId + " )";
			}
			else
				return "";
		}

		private static String getDateTime()
				{
			        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			        Date date = new Date();
			        return dateFormat.format(date);	
				}
					
}
