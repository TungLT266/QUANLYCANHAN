<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>

<%
	String dvkdId = "100001";
	if(session.getAttribute("dvkdId") != null )
		dvkdId = (String) session.getAttribute("dvkdId");
	
	String kbhId = "";
	if(session.getAttribute("kbhId") != null )
		kbhId = (String) session.getAttribute("kbhId");
	
	String nppId = "-";
	if(session.getAttribute("nppId") != null )
		nppId = (String) session.getAttribute("nppId");
	
	String khId = "";
	if(session.getAttribute("khId") != null )
		khId = (String) session.getAttribute("khId");
	
	String khonhapId = "-1";
	if(session.getAttribute("khoId") != null )
		khonhapId = (String) session.getAttribute("khoId");
	
	String hopdongId = "";
	if(session.getAttribute("hopdongId") != null )
		hopdongId = (String) session.getAttribute("hopdongId");
	
	String loaidonhang = "";
	if(session.getAttribute("loaidonhang") != null )
		loaidonhang = (String) session.getAttribute("loaidonhang");
	

	//System.out.println("--LOC SP..." + dvkdId + "  -- NHOM KENH ID: " + kbhId + "  -- Đối tác ID: " + nppId + " -- HOP DONG ID:  " + hopdongId );
	if( dvkdId.trim().length() > 0 && nppId.trim().length() > 0 && loaidonhang.trim().length() > 0 && kbhId.trim().length() > 0 )
	{
		dbutils db = new dbutils();
		
		/* String sql = " select tructhuoc_fk, loaiNPP, dungchungkenh from NHAPHANPHOI where pk_seq = '" + nppId + "' ";
		ResultSet rs = db.get(sql);
		String tructhuocId = "";
		String loaiNPP = "";
		String dungchungkenh = "";
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					loaiNPP = rs.getString("loaiNPP");
					tructhuocId = rs.getString("tructhuoc_fk");
					dungchungkenh = rs.getString("dungchungkenh");
				}
				rs.close();
				
				if(dungchungkenh.equals("1"))
				{
					nhomkenhId = "100000";
				}
			} 
			catch (Exception e) { }
		} */
		
		try
		{				
			 //BAN DAU LAY GIA CHUAN SAU DO CHON LAI GIA THI CAP NHAT LAI GIA THEO DON VI (BANG GIA BEN NAY KHONG CHIA THEO KENH)
			 String command = "";
			 String nhomkenhId = "100000";
			 
			 
			 String query = (String)request.getQueryString(); 
			   	
		   	 query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
		   	 query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		   	
		   	 Utility Ult = new Utility();
		   	 query = Ult.replaceAEIOU(query);
			 
			 if( hopdongId.trim().length() <= 0 ) //LẤY TỪ BẢNG GIÁ
			 {
				 if( loaidonhang.equals("0") ) //Bán cho ĐLPP
				 {
					 command =   "select top(100) (select kho.available from nhapp_kho kho where kho.sanpham_fk=a.pk_seq and kho.KHO_FK = " + khonhapId + " and NPP_FK='" + nppId + "' and kho.NHOMKENH_FK = " + nhomkenhId + " ) as soluongton, " + 
						 		 " 			a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich,  " + 
								 "	cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 2) ) as qc,	 " + 
								 " 	  isnull( ( select GIAMUA_SAUCK    " + 
								 " 				from BGMUANPP_SANPHAM bg_sp  " + 
								 "			    where SANPHAM_FK = a.pk_seq   " + 
								 "					and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + khId + "' and bg.DVKD_FK = '" + dvkdId + "' and  bg.NHOMKENH_FK = ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = '" + kbhId + "' )  order by bg.TUNGAY desc ) ), 0) as giamua, isnull( a.thuexuat, c.thuexuat) as thuexuat   " + 
								 "from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq  " + 
								 "   inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq  " + 
								 "where a.trangthai = '1' and a.pk_seq > 0 and a.DVKD_FK = '" + dvkdId + "'  ";
				 } 
				 else if( loaidonhang.equals("1") || loaidonhang.equals("2") ) //Bán cho kênh không thầu hoặc đơn hàng mượn kênh không thầu
				 {
					 /* command =  "select (select kho.available from nhapp_kho kho where kho.sanpham_fk = a.pk_seq and kho.KHO_FK = " + khonhapId + " and NPP_FK = '"+nppId+"' and kho.NHOMKENH_FK = " + nhomkenhId + " )as soluongton,a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich, " +
								"	cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 2) ) as qc,	" +
								" 	  ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '" + dvkdId + "' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId +  "' ) ) ), 0) as giamua, isnull( a.thuexuat, c.thuexuat) as thuexuat  " +
								"from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
								"   inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq " +
								"where a.pk_seq > 0 and a.DVKD_FK = '" + dvkdId + "'  "; */
								
					 command =  "select top(100) (select kho.available from nhapp_kho kho where kho.sanpham_fk = a.pk_seq and kho.KHO_FK = " + khonhapId + " and NPP_FK = '"+nppId+"' and kho.NHOMKENH_FK = " + nhomkenhId + " )as soluongton,a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich, " +
								"	cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 2) ) as qc,	" +
								" 	  isnull( dbo.LayDonGia_Search(" + loaidonhang + ", " + nppId + ", " + khId + ", null, " + nhomkenhId + ", a.pk_seq), 0) as giamua, isnull( a.thuexuat, c.thuexuat) as thuexuat, ' ' as ddkd_fk  " +
								"from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
								"   inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq " +
								"where a.trangthai = '1' and a.pk_seq > 0 and a.DVKD_FK = '" + dvkdId + "'  ";
				 }
				 else
				 {
					 command =  "select top(100) (select kho.available from nhapp_kho kho where kho.sanpham_fk = a.pk_seq and kho.KHO_FK = " + khonhapId + " and NPP_FK = '"+nppId+"' and kho.NHOMKENH_FK = '100000' ) as soluongton,a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich, " +
								"	cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 2) ) as qc,	" +
								" 	  0 as giamua, isnull( a.thuexuat, c.thuexuat) as thuexuat  " +
								"from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
								"   inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq " +
								"where a.trangthai = '1' and a.pk_seq > 0 and a.DVKD_FK = '" + dvkdId + "'  ";
				 }
			 }
			 else //LẤY GIÁ TRONG HỢP ĐỒNG - bán hàng kên thầu
			 {
				 /* command =  "select ( select kho.available from nhapp_kho kho where kho.sanpham_fk = a.pk_seq and kho.KHO_FK = " + khonhapId + " and NPP_FK='" + nppId + "' and kho.NHOMKENH_FK = " + nhomkenhId + " )as soluongton,a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong,a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich,  " +
							 "	cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 2) ) as qc,	 " +
							 " 		    isnull( ( select dongia from ERP_HOPDONGNPP_SANPHAM where hopdong_fk = '" + hopdongId + "' and sanpham_fk = a.pk_seq ), 0) as giamua, isnull( a.thuexuat, c.thuexuat) as thuexuat, " + 
							 "			isnull( ( select ddkd_fk from ERP_HOPDONGNPP_SANPHAM where hopdong_fk = '" + hopdongId + "' and sanpham_fk = a.pk_seq ), 0) as ddkd_fk    " +
							 "from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq  " +
							 "   inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq  " +
							 "where a.pk_seq > 0 and a.DVKD_FK = '" + dvkdId + "' and a.pk_seq in ( select sanpham_fk from ERP_HOPDONGNPP_SANPHAM where hopdong_fk = '" + hopdongId + "' )  " ;	 */
			 
			 	command =  "select top(100) 0 as soluongton,a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich,   "+
			 			 "	cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 2) ) as qc,	  "+
			 			 " 		    isnull( ( d.dongia ), 0) as giamua, isnull( a.thuexuat, c.thuexuat) as thuexuat,   "+
			 			 "			isnull( e.ddkd_fk, 0) as ddkd_fk     "+
			 			 "from SANPHAM a  "+
			 			 "   inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq  "+
			 			 "   inner join ERP_HOPDONGNPP_SANPHAM d on a.pk_seq = d.sanpham_fk "+
			 			 "   inner join ERP_HOPDONGNPP e on d.hopdong_fk = e.pk_seq "+
			 			 "   left join DONVIDOLUONG b on d.dvdl_fk = b.pk_seq  "+
			 			 "where a.trangthai = '1' and a.pk_seq > 0 and a.DVKD_FK = '" + dvkdId + "' and e.pk_seq = '" + hopdongId + "' ";
			 }
							 
			System.out.println("Lay san pham ...: " + command);
			
			response.setHeader("Content-Type", "text/html");
			//String query = (String)request.getParameter("letters");
			
			command += " and a.timkiem like N'%" + query + "%' ";
			
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{
					//if(sp.getString("ma").toUpperCase().contains(query.toUpperCase()) 
					//			|| sp.getString("ten").toUpperCase().contains(query.toUpperCase()) )
					//{
						String tensp = sp.getString("ten");
						//out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("trongluong") + "] [" + sp.getString("thetich") + "] [" + sp.getString("qc") + "] [" + sp.getString("giamua") + "] [" + sp.getString("thuexuat") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						
						if( !loaidonhang.equals("1") )
							out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("giamua") + "] [" + sp.getString("thuexuat") + "] [" + sp.getString("soluongton") + "] [ ]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						else
							out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("giamua") + "] [" + sp.getString("thuexuat") + "] [" + sp.getString("soluongton") + "] [" + sp.getString("ddkd_fk") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					//}	
				}	
				sp.close();
			}
			
			db.shutDown();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Xay ra exception roi ban..." + ex.getMessage()); 
		}
	}
%>

