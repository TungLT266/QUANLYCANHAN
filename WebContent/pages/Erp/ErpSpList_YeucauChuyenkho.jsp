<%@page import="java.sql.SQLException"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.center.db.sql.dbutils"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %> 
<%@ page import = "java.net.URLDecoder" %>
 
<% 
	String congtyId = (String) session.getAttribute("congtyId");
	String lsxId = (String) session.getAttribute("lsxId");
	String manoidungxuat = (String) session.getAttribute("manoidungxuat");
	

%>
<%
System.out.println("so lenh san xuat " + lsxId);
	dbutils db = new dbutils();
	try
	{
		String khochuyenIds = (String) session.getAttribute("khochuyenIds");  
		
		String vitriId = "";
		if( session.getAttribute("vitriId") != null )
			vitriId = session.getAttribute("vitriId").toString();
		
		String isDctk = "";
		if( session.getAttribute("isDctk") != null )
			isDctk = session.getAttribute("isDctk").toString();
		
		//kiem tra loai kho san xuat
				String sql="select count (PK_SEQ) dem from erp_khott where pk_seq='"+khochuyenIds+"' and loaiKHO=10";
				int dem=0;
				ResultSet spRs=db.get(sql);
				
				try {
					if(spRs.next())
					dem=spRs.getInt("dem");
					spRs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		String loadvitriNHAN = "";
		String loadvitriNHAN_KHONHAN = "";
		String loadnsx = "";
		String loadmarq = "";
		
		response.setHeader("Content-Type", "text/html");
		
		NumberFormat formatter3 = new DecimalFormat("#######.######");
		
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");			
	 	
		String query = (String)request.getQueryString(); 
	   	if( query.contains("loadvitriNHAN") )
	   		loadvitriNHAN = "1";
	   	else if( query.contains("layvitriNHANHANG") )
	   		loadvitriNHAN_KHONHAN = "1";
	   	else if (query.contains("laynhasanxuat"))
	   		loadnsx = "1";
	   	else if (query.contains("laymarq"))
	   		loadmarq = "1";
		
	   	System.out.println(":::QUERY TRUOC: " + query);
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	System.out.println(":::QUERY SAU: " + query);
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
		String command = "";
		
		if( loadvitriNHAN.equals("1") )
		{
			command = "select pk_seq, MA + ', ' + TEN as TEN from ERP_BIN " + 
					  " where KHOTT_FK = '" + khochuyenIds + "' and trangthai = '1' and timkiem like  '%"+query+"%' ";
			if( vitriId.trim().length() > 0 )
				command += " and pk_seq != '" + vitriId + "' ";
		}
		else if( loadvitriNHAN_KHONHAN.equals("1") )
		{
			String khonhanIds = "";  
			if( session.getAttribute("khonhanIds") != null )
				khonhanIds = session.getAttribute("khonhanIds").toString();
			
			command = "select pk_seq, MA + ', ' + TEN as TEN from ERP_BIN " + 
					  " where KHOTT_FK = '" + khonhanIds + "' and trangthai = '1' and timkiem like  '%"+query+"%' ";
			if( vitriId.trim().length() > 0 )
				command += " and pk_seq != '" + vitriId + "' ";
		}
		else if (loadnsx.equals("1")) {
			command = 	"select PK_SEQ, MA, ten from erp_nhasanxuat where 1=1 " ;
			command += 	
						"and (upper(cast(PK_SEQ as nvarchar(10))) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%')) " +
						" or upper(MA) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%')) " +
						" or upper(TEN) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%'))) ";
		}
		else if (loadmarq.equals("1")) {
			command = 	"select PK_SEQ, MA from MARQUETTE where 1=1 " ;
			command += 	
						"and " + 
						" (upper(cast(PK_SEQ as nvarchar(10))) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%')) " +
						" or upper(MA) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%'))) ";
		}
		else if( isDctk.equals("1") )
		{
			if( vitriId.trim().length() <= 0 )
			{
				command = " select  top 50 a.pk_seq, a.MA ,   a.TEN    as spTen,   dvdl.donvi as dvdl, sum( isnull(kho.soluong,0) ) as tonkho \n" +
					      " from ERP_KHOTT_SP_CHITIET  kho inner join ERP_SANPHAM a on    kho.sanpham_fk=a.pk_seq and khott_fk="+ khochuyenIds + "\n" +
					      " left join donvidoluong dvdl on dvdl.pk_seq = a.dvdl_fk \n"+
					      "	where  a.trangthai='1' and  a.timkiem like  '%"+query+"%'  and  a.congty_fk = '" + congtyId + "'   \n" + 
					      "	group by a.pk_seq, a.MA, a.TEN, dvdl.donvi ";
			}
			else
			{
				command = " select  top 50 a.pk_seq, a.MA, a.TEN  as spTen, dvdl.donvi as dvdl, sum(isnull(kho.soluong,0)) as tonkho \n" +
					      " from ERP_KHOTT_SP_CHITIET  kho inner join ERP_SANPHAM a on    kho.sanpham_fk=a.pk_seq and khott_fk="+ khochuyenIds + "\n" +
					      " left join donvidoluong dvdl on dvdl.pk_seq = a.dvdl_fk \n"+
					      "	where  a.trangthai='1' and kho.bin_fk = '" + vitriId + "' and  a.timkiem like  '%"+query+"%'  and  a.congty_fk = '" + congtyId + "'   \n" + 
					      "	group by a.pk_seq, a.MA, a.TEN, dvdl.donvi ";
			}
		}
		else
		{
			if((lsxId==null || lsxId.trim().length()<=0))
			{
				if( vitriId.trim().length() <= 0 )
				{
					command = " select  top 50 a.pk_seq, a.MA ,   a.TEN    as spTen,   dvdl.donvi as dvdl ,isnull(kho.available,0) as tonkho \n" +
						      " from dbo.ufn_tonhientai_full( )  kho inner join ERP_SANPHAM a on    kho.sanpham_fk=a.pk_seq and khott_fk="+ khochuyenIds + "\n" +
						      " left join donvidoluong dvdl on dvdl.pk_seq = a.dvdl_fk \n"+
						      "	where  a.trangthai='1' and  a.timkiem like  '%"+query+"%'  and  a.congty_fk = '" + congtyId + "' and isnull(kho.available,0) > 0  \n";
				}
				else
				{
					command = " select  top 50 a.pk_seq, a.MA,   a.TEN  as spTen,   dvdl.donvi as dvdl, sum(isnull(kho.available,0)) as tonkho \n" +
						      " from ERP_KHOTT_SP_CHITIET  kho inner join ERP_SANPHAM a on    kho.sanpham_fk=a.pk_seq and khott_fk="+ khochuyenIds + "\n" +
						      " left join donvidoluong dvdl on dvdl.pk_seq = a.dvdl_fk \n"+
						      "	where  a.trangthai='1' and kho.bin_fk = '" + vitriId + "' and  a.timkiem like  '%"+query+"%'  and  a.congty_fk = '" + congtyId + "' and isnull(kho.available,0) > 0  \n" +
							  "	group by a.pk_seq, a.MA, a.TEN, dvdl.donvi ";
				}
			}
			else
			{

				
					if(manoidungxuat.trim().equals("XK10") && lsxId.trim().length()>0 && dem>0){
										
						command=" select  top 50 a.pk_seq, a.MA ,   a.TEN    as spTen,   dvdl.donvi as dvdl , "+
								 "(select sum(available) available from  UFN_GETLSX_TON('"+khochuyenIds+"','"+lsxId+"',a.PK_SEQ))as tonkho from ERP_SANPHAM a left join donvidoluong dvdl on dvdl.pk_seq = a.dvdl_fk "+
								 "	where  a.trangthai='1'  and  a.timkiem like  '%"+query+"%'  and  a.congty_fk = '" + congtyId + "'";

					}
					else
					{
						if( vitriId.trim().length() <= 0 )
						{
							command = " select  top 50 a.pk_seq, a.MA ,   a.TEN    as spTen,   dvdl.donvi as dvdl ,isnull(kho.available,0) as tonkho \n" +
								      " from dbo.ufn_tonhientai_full( )  kho inner join ERP_SANPHAM a on    kho.sanpham_fk=a.pk_seq and khott_fk="+ khochuyenIds + "\n" +
								      " left join donvidoluong dvdl on dvdl.pk_seq = a.dvdl_fk \n"+
								      "	where  a.trangthai='1' and  a.timkiem like  '%"+query+"%'  and  a.congty_fk = '" + congtyId + "' and isnull(kho.available,0) > 0  \n";
						}
						else
						{
							command = " select  top 50 a.pk_seq, a.MA,   a.TEN  as spTen,   dvdl.donvi as dvdl, sum(isnull(kho.available,0)) as tonkho \n" +
								      " from ERP_KHOTT_SP_CHITIET  kho inner join ERP_SANPHAM a on    kho.sanpham_fk=a.pk_seq and khott_fk="+ khochuyenIds + "\n" +
								      " left join donvidoluong dvdl on dvdl.pk_seq = a.dvdl_fk \n"+
								      "	where  a.trangthai='1' and kho.bin_fk = '" + vitriId + "' and  a.timkiem like  '%"+query+"%'  and  a.congty_fk = '" + congtyId + "' and isnull(kho.available,0) > 0  \n" +
									  "	group by a.pk_seq, a.MA, a.TEN, dvdl.donvi ";
						}
					}
			
					

			}
			
		}
		
		System.out.println("command:\n" + command + "\n=============================");
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			while(sp.next())
			{
				if( loadvitriNHAN.equals("1") || loadvitriNHAN_KHONHAN.equals("1") )
				{
					out.print("###" + sp.getString("TEN") +  " [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}
				else if ( loadnsx.equals("1")) {
	                String nsx = sp.getString("MA") + "-->[" + sp.getString("TEN") + "][" + sp.getString("PK_SEQ") + "]" ;
					out.println(nsx + "|");
				} 
				else if (loadmarq.equals("1")) {
					out.println(sp.getString("MA") + "-->[" + sp.getString("PK_SEQ") + "]|");
				}
				else
				{
					String ma = Ult.replaceAEIOU(sp.getString("ma"));
					String ten = Ult.replaceAEIOU(sp.getString("spTen"));
					String tensp = sp.getString("spTen");
					 
					String dvdl = Ult.replaceAEIOU(sp.getString("dvdl"));
					 
					String	maSP = sp.getString("ma");
					out.print("###" + maSP + " - " + sp.getString("spTen") +  " [" + sp.getString("pk_seq") + "] [" + dvdl + "] [" +formatter3.format(sp.getDouble("tonkho")) + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}
				 
			}	
			sp.close();
		}		  
 
		db.shutDown();
	}
	catch(Exception ex)
	{ 
		db.shutDown();
		ex.printStackTrace();
		System.out.println("Xay ra exception roi ban..."); 
	}
%>

