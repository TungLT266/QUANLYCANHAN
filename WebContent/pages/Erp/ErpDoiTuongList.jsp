<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<%
	try
	{  
		String loaidoituong = (String) session.getAttribute("loaidoituong");
		loaidoituong = loaidoituong == null ? "" : loaidoituong;
		String id = (((String) session.getAttribute("id") == null) ? "" : (String) session.getAttribute("id")).trim();
	
		
		Utility uti = new Utility();
		String userId = (String)request.getSession().getAttribute("userId");
		String congtyId = (String)request.getSession().getAttribute("congtyId");
		dbutils db = new dbutils();
		
		String nvTen = "";

		   String command = "";
	
		
		request.setCharacterEncoding("UTF-8");
		
		String querystring = request.getQueryString();
		loaidoituong = uti.getParameter(querystring, "loaidoituong") != null ? uti.getParameter(querystring, "loaidoituong") : loaidoituong;
		loaidoituong = loaidoituong == null ? "" : loaidoituong;
		
		
		String   query = new String(querystring.substring(querystring.indexOf("q=") + 2, querystring.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	    query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	    
	    System.out.println("loaidoituong: "+loaidoituong);
		if (loaidoituong.length() > 0) {
			if (loaidoituong.equals("0")){
				if(id.length()==0)
				{
					//command = "Select pk_seq, (convert(varchar, pk_seq) +'--'+ ma +', ' + ten) as Ten From ERP_NHACUNGCAP where TRANGTHAI = '1' and duyet = '1' AND CONGTY_FK ="+congtyId;
					command = "Select pk_seq, (convert(varchar, pk_seq) +'--'+ ma +', ' + ten +'['+isnull(diachi,''))+'['+isnull(masothue,'') as Ten From ERP_NHACUNGCAP where TRANGTHAI = '1' and duyet = '1' AND CONGTY_FK ="+congtyId;
			
				}
				else
				{
					//command = "Select pk_seq, (convert(varchar, pk_seq) +'--'+ ma +', ' + ten) as Ten From ERP_NHACUNGCAP where  duyet = '1' AND CONGTY_FK ="+congtyId;
					command = "Select pk_seq, (convert(varchar, pk_seq) +'--'+ ma +', ' + ten +'['+isnull(diachi,''))+'['+isnull(masothue,'') as Ten From ERP_NHACUNGCAP where  duyet = '1' AND CONGTY_FK ="+congtyId;
				}
				
			}else if (loaidoituong.equals("1")) {
				if(id.length()==0)
				{
					command = "Select pk_seq, (convert(varchar, pk_seq) +'--'+ ma +', ' + ten) as Ten From ERP_NHANVIEN where TRANGTHAI = '1' AND CONGTY_FK ="+congtyId;
				}
				else
				{
					command = "Select pk_seq, (convert(varchar, pk_seq) +'--'+ ma +', ' + ten) as Ten From ERP_NHANVIEN where CONGTY_FK ="+congtyId;
				}
			}else if (loaidoituong.equals("2")) {
				if(id.length()==0)
				{
					command = "Select pk_seq, (convert(varchar, pk_seq) +'--'+ mafast +', ' + ten) as Ten From  nhaPhanPhoi where isKhachHang = 1 and TRANGTHAI = '1'";	
				}
				else
				{
					command = "Select pk_seq, (convert(varchar, pk_seq) +'--'+ mafast +', ' + ten) as Ten From  nhaPhanPhoi where isKhachHang = 1";	
				}
			}else if (loaidoituong.equals("3")) {
				command = "select pk_seq, (convert(varchar, pk_seq) +'--'+ ma +', ' + ten) as ten, 0 as difField from nhaPhanPhoi where trangThai = 1 and isKhachHang = 0 and pk_seq != 1  and ISNULL(CONGNOCHUNG,0) != 1 ";
				
			}else if (loaidoituong.equals("4")) {
				command = 
						"select PK_SEQ, (convert(varchar, pk_seq) +'--'+ madoituong +', ' + tendoituong) ten\n" +
						"from ERP_DoiTuongKhac dt\n" +
						"where dt.TRANGTHAI = 1\n";
			}
			
		} else {
			command = "";
		}

	  /*   query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		 */
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("[Erp_NhanvienList.jsp] nv command = " + command );
	 	System.out.println("[Erp_NhanvienList.jsp] query = " + query );
	 	
	 	ResultSet nv = db.get(command);
	 	
		if(nv != null)
		{
			while(nv.next())
			{   
				//nvTen = nv.getString("ten");
				nvTen =  URLDecoder.decode(nv.getString("ten"), "UTF-8").replace("+", " ");
				if(nvTen.toUpperCase().startsWith(query.toUpperCase()) || nvTen.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					out.println(nvTen + "\n");
				}
			}
			nv.close();
		}
			
		db.shutDown();
	}
	catch(SQLException e){}
		
%>