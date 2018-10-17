<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.erp.db.sql.dbutils" %>


<%
	dbutils db = new dbutils();
	
	String loai = (String) session.getAttribute("loai");
	if ((loai==null)||(loai==""))
	loai="1";
	if((loai.equals("1")))
	{
	try
	{
		
		String command = "";
		
		command = " select a.pk_seq as id, a.ma,a.diengiai as diengiai, isnull(a.donvi, 'NA') as donvi, isnull(b.ma, 'NA') as nhomhang, soluong \n" + 
				  "	from erp_taisancodinh a \n" + 
				  " left join erp_nhomtaisan b on a.NhomTaiSan_fk = b.pk_seq \n" +
//				  "	left join DonViDoLuong c on a.dvt = c.pk_seq \n" +
				  "	where a.trangthai = '1' and (isDaThanhLy = 0 or isDaThanhLy is Null)\n" +
				  " AND a.PK_SEQ NOT IN ( \n" + 	
				  " SELECT DISTINCT isnull(TL_TS.TAISAN_FK,0) 	FROM ERP_THANHLYTAISAN TL \n" + 	
			      " INNER JOIN ERP_THANHLYTAISAN_TAISAN TL_TS ON TL_TS.THANHLYTAISAN_FK = TL.PK_SEQ \n" + 	
				  " WHERE TL.TRANGTHAI IN (1, 2, 3)   and TL.LOAI = 1) \n" ;
				  
		
	
		
		response.setHeader("Content-Type", "text/html");
		String query = (String)request.getParameter("letters");
		command += " AND ( a.ma like N'%"+query+"%' )";
		command += " order by a.ma asc\n";
		System.out.println("Lay tai san: " + command);
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			while(sp.next())
			{
				if(sp.getString("id").toUpperCase().startsWith(query.toUpperCase()) || sp.getString("ma").toUpperCase().startsWith(query.toUpperCase()) ||sp.getString("ma").toUpperCase().indexOf(query.toUpperCase()) >= 0 
						|| sp.getString("diengiai").toUpperCase().indexOf(query.toUpperCase()) >= 0 || sp.getString("diengiai").toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					String tensp = sp.getString("diengiai");
					//if(tensp.length() > 30)
						//tensp = tensp.substring(0, 30);
					out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("nhomhang") + "] [" + sp.getFloat("soluong") + "] [" + sp.getString("id") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					
				}	
			}	
			sp.close();
		}
		
		
		
	}
	catch(Exception ex){ 
		ex.printStackTrace();
		System.out.println("Xay ra exception roi ban..."); }
	}
	else
		if(loai.equals("2"))
			try
		{
			
			String command = "";
			
			command = " select a.pk_seq as id, a.ma,a.diengiai , isnull(a.donvi, 'NA') as donvi, isnull(b.ma, 'NA') as nhomhang, soluong \n" + 
					  "	from Erp_congcudungcu a \n" + 
					  " left join ERP_NHOMCCDC b on a.NHOMCCDC_FK = b.pk_seq \n" +
//					  "	left join DonViDoLuong c on a.dvt = c.pk_seq \n" +
					  "	where a.trangthai = '1' \n" + 
					  " AND a.PK_SEQ NOT IN ( \n" + 	
					  " SELECT DISTINCT isnull(TL_TS.TAISAN_FK,0) 	FROM ERP_THANHLYTAISAN TL \n" + 	
				      " INNER JOIN ERP_THANHLYTAISAN_TAISAN TL_TS ON TL_TS.THANHLYTAISAN_FK = TL.PK_SEQ \n" + 	
					  " WHERE TL.TRANGTHAI IN (1, 2, 3) )\n" +
					  " order by a.ma asc\n";
					
			
			System.out.println("Lay tai san: " + command);
			
			response.setHeader("Content-Type", "text/html");
			String query = (String)request.getParameter("letters");
			
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{
					if(sp.getString("id").toUpperCase().startsWith(query.toUpperCase()) || sp.getString("ma").toUpperCase().startsWith(query.toUpperCase()) ||sp.getString("ma").toUpperCase().indexOf(query.toUpperCase()) >= 0 
							|| sp.getString("ten").toUpperCase().indexOf(query.toUpperCase()) >= 0 || sp.getString("diengiai").toUpperCase().indexOf(query.toUpperCase()) >= 0 )
					{
						String tensp = sp.getString("diengiai");
						//if(tensp.length() > 30)
							//tensp = tensp.substring(0, 30);
						out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("nhomhang") + "] [" + sp.getString("soluong") + "] [" + sp.getString("id") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						
					}	
				}	
				sp.close();
			}
		}
		catch(Exception ex){ 
			ex.printStackTrace();
			System.out.println("Xay ra exception roi ban..."); 
		}
			
	session.setAttribute("loai","");
	db.shutDown();
%>