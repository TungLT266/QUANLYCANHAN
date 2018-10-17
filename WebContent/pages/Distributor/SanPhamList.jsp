<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.distributor.db.sql.dbutils" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>

<% String nppId = (String) session.getAttribute("nppId"); %>
<% String khId = (String) session.getAttribute("khId"); %>
<% String khoId = (String) session.getAttribute("khoId"); %>
<% String donhangKhac = (String) session.getAttribute("donhangKhac");%>

<%
	System.out.println("::: NPP ID: " + nppId );
	String ngaydonhang = (String)session.getAttribute("ngaydonhang"); 
	if(ngaydonhang == null)
		ngaydonhang = "";
	
	//String nhomkenhId = "";
	if(ngaydonhang.trim().length() > 0)
	{
		dbutils db = new dbutils();

		//XÁC ĐỊNH NHÓM KÊNH VÀ KÊNH BÁN HÀNG
		String sql = " select tructhuoc_fk, loaiNPP, dungchungkenh, " +
					 "	( select top(1) kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + khId + "'  ) as kbhId, " +
					 "	( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = ( select top(1) kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + khId + "' ) ) as nhomkenhId, " +
					 "	( select htbh_fk from hethongbanhang_kenhbanhang where kbh_fk = ( select top(1) kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + khId + "' ) ) as htbhId	" +
					 " from NHAPHANPHOI where pk_seq = '" + nppId + "' ";
		
		//System.out.println("::: THONG TIN KHACH HANG: " + sql);
		ResultSet rs = db.get(sql);
		
		String tructhuocId = "";
		String loaiNPP = "";
		String dungchungkenh = "";
		String nhomkenhId = "";
		String kbhId = "";
		String htbhId = "";
		
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					loaiNPP = rs.getString("loaiNPP");
					tructhuocId = rs.getString("tructhuoc_fk");
					dungchungkenh = rs.getString("dungchungkenh");
					
					nhomkenhId = rs.getString("nhomkenhId");
					kbhId = rs.getString("kbhId");
					htbhId = rs.getString("htbhId");
				}
				rs.close();
				
				//if(dungchungkenh.equals("1"))
					nhomkenhId = "100000";
			} 
			catch (Exception e) { e.printStackTrace(); }
		}
		
		
		try
		{
			String command = "";
					  
			/* command =  "select a.ma, a.ten, b.donvi, d.AVAILABLE  as hienhuu,  " +
						"	( select THUEXUAT from NGANHHANG where pk_seq = a.nganhhang_fk ) as VAT, " +
						"	ISNULL ( ( select giabanleNPP from BGBANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BGBANLENPP_FK in ( select pk_seq from BANGGIABANLENPP where npp_fk = '" + nppId + "'  ) ), 0) as dongia " +
						"from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq   " +
						"	inner join NHAPP_KHO d on a.PK_SEQ = d.SANPHAM_FK " +
						"where a.pk_seq > 0 and a.DVKD_FK = '100001' and d.NPP_FK = '" + nppId + "' and d.kho_fk = '" + khoId + "' and d.KBH_FK = ( select kbh_fk from khachhang where pk_seq='" + khId + "' ) "; */
			
			/* if( htbhId.equals("100001") ) //OTC
			{
				command =  "select ISNULL ( (select kho.available from nhapp_kho kho where kho.sanpham_fk=a.pk_seq and kho.KHO_FK = " + khoId + " and NPP_FK='"+nppId+"' and kho.NHOMKENH_FK = '" + nhomkenhId + "' ), 0 ) as hienhuu,a.ma, a.ten, isnull(b.donvi, '') as donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich, " +
						"	cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 2) ) as qc,	" +
						" 	  ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId +  "' ) ) ), 0) as dongia, a.THUEXUAT as VAT  " +
						"from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
						"   inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq " +
						"where a.pk_seq > 0 and a.DVKD_FK = '100001' and a.trangthaiDAILY = '1'  " + 
						"  and ISNULL ( (select kho.available from nhapp_kho kho where kho.sanpham_fk=a.pk_seq and kho.KHO_FK = " + khoId + " and NPP_FK='"+nppId+"' and kho.NHOMKENH_FK = '" + nhomkenhId + "' ), 0 ) > 0 ";
			}
			else //ETC -> lấy bảng giá mua nhóm kênh thầu
			{ */
				command =  "select ISNULL ( (select kho.available from nhapp_kho kho where kho.sanpham_fk=a.pk_seq and kho.KHO_FK = " + khoId + " and NPP_FK='"+nppId+"' and kho.NHOMKENH_FK = '" + nhomkenhId + "' ), 0 ) as hienhuu,a.ma, a.ten, isnull(b.donvi, '') as donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich, " +
						"	cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 2) ) as qc,	" +
						//" 	  ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId +  "' ) ) ), 0) as dongia, " + 
						" 	  isnull( ( select GIAMUA_SAUCK    " + 
								 " 				from BGMUANPP_SANPHAM bg_sp  " + 
								 "			    where GIAMUA_SAUCK != 0 and SANPHAM_FK = a.pk_seq   " + 
								 "					and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = '100001'   order by bg.TUNGAY desc ) ), 0) as dongia, " +
						"   a.THUEXUAT as VAT  " +
						"from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
						"   inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq " +
						"where a.pk_seq > 0 and a.DVKD_FK = '100001' and a.trangthaiDAILY = '1' " + 
						"	and ISNULL ( (select kho.available from nhapp_kho kho where kho.sanpham_fk=a.pk_seq and kho.KHO_FK = " + khoId + " and NPP_FK='"+nppId+"' and kho.NHOMKENH_FK = '" + nhomkenhId + "' ), 0 ) > 0	";
			//}
						
			System.out.println("Lay San pham: " + command );
			
			response.setHeader("Content-Type", "text/html");
			request.setCharacterEncoding("UTF-8");
			
		   	String query = (String)request.getQueryString(); 
		   	
		   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
		   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		   	
		   	Utility Ult = new Utility();
		   	query = Ult.replaceAEIOU(query);
			
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				NumberFormat format = new DecimalFormat("#,###,###,##0.00");
				while(sp.next())
				{
					double hienhuu = sp.getDouble("hienhuu");

					String MASP = sp.getString("ma");
					String TENSP = sp.getString("ten");
					
					String masp = Ult.replaceAEIOU(sp.getString("ma"));
					String tensp = Ult.replaceAEIOU(sp.getString("ten"));
					String donvi = Ult.replaceAEIOU(sp.getString("donvi"));
					//int soluong1 = sp.getInt("soluong1");
					//int soluong2 = sp.getInt("soluong2");
					
					if(masp.toUpperCase().startsWith(query.toUpperCase()) || masp.toUpperCase().indexOf(query.toUpperCase()) >= 0 
							|| tensp.toUpperCase().indexOf(query.toUpperCase()) >= 0 || donvi.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
					{
						if(TENSP.length() > 50)
							TENSP = TENSP.substring(0, 50);
						//out.print("###" + sp.getString("ma") + " - " + TENSP +" [" + sp.getString("donvi") + "] ["+ sp.getString("dongia") + "] [" + format.format(hienhuu) + "] [" + Integer.toString(soluong1) + "] [" + Integer.toString(soluong2) + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						
						out.print("###" + sp.getString("ma") + " - " + TENSP +" [" + sp.getString("donvi") + "] ["+ sp.getString("dongia") + "] [" + format.format(hienhuu) + "] [" + sp.getString("VAT") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					}
				}	
			}
			sp.close();
			db.shutDown();
			db=null;
			nppId=null;
			khId=null;
			khoId=null;
		
		}	
		catch(Exception ex)
		{
			System.out.println("__EXCEPTION LAY SP: " + ex.getMessage());
			ex.printStackTrace();
			if(db!=null){		
				db.shutDown();
				db=null;
			}
		}
	
	}
%>

