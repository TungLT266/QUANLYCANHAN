<%@page import="java.awt.Dimension"%>
<%@page import="java.awt.Toolkit"%>
<%@page import="geso.traphaco.center.db.sql.dbutils"%>
<%@page import="java.sql.ResultSet"%>

<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>

<% String msg = (String) session.getAttribute("msg");  
   session.setAttribute("msg", "");
   Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int screenWidth = screenSize.width;
	int screenHeight = screenSize.height;
   if(screenWidth>=1280)
   {
   	 session.putValue("chuoi","../css/style1280.css");
    }
   else if(screenWidth>=1024)
   {
   	 session.putValue("chuoi","../css/style1024.css");
   }
   else if(screenWidth>=800)
   {
   	 session.putValue("chuoi","../css/style800.css");
   }
%>
<html>
<head>
	<title>PhaNam Group</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<link rel="shortcut icon" href="pages/images/salesup.jpg">
	<LINK rel="stylesheet" href="pages/css/style_v2_optimized.css" type="text/css">
	<!-- <LINK rel="stylesheet" href="pages/css/main.css" type="text/css">  -->
	<style type="text/css">
		.blanc 
		{
		  color : #999999;
		  font-family: Arial, Helvetica, sans-serif;
		  letter-spacing : 0pt;
		  text-decoration: none ; 
		  font-weight : 200;
		  font-size: 1.0em;
		}
	</style>
</head>
<body>
<form name="loginForm" method="post" action="/TraphacoHYERP/LoginSvl" >
<input type="hidden" name="login" value = "1"></input>
<input type="hidden" name="chonchucdanh" value = "1"></input>

<table width="350px" align="left" style="margin-left: 40px" cellpadding="0px" cellspacing="0px">
    	<tr height="25px">
        	<td style="background-image:url(pages/images/borderTopLeft.png); width:25px"></td>
            <td style="background-image:url(pages/images/borderTopCenter.png)"></td>
            <td style="background-image:url(pages/images/borderTopRight.png); width:25px"></td>
        </tr>
    <tr>
    <td style="background-image:url(pages/images/borderMiddleLeft.png); width:25px; background-repeat:repeat-y; background-position:left"></td>
    <td style="background-image:url(pages/images/point_make_area_transparent.png)">
    
	    <div style="width: 100%; margin-top: -5" align="center">
	    	<img src="pages/images/salesup.jpg" width="90px" height="40px"/>
	    </div>
	    <table style="width: 100%" cellpadding="1px" cellspacing="4px">
	    	
	    	<tr>
	    		<td width="100%" class="blanc"><span>Chọn nhà phân phối</span></td>
	    	</tr>
	    	
	    	<tr>
	    		<td width="100%" >
			            <select style="width: 100%;  height: 35px; vertical-align:bottom; font-size: 0.9em;"
			            		 class="input-field-login icon password-container" name="chucdanhId">
			            	<% 
			            		dbutils db = new dbutils();  
			            		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString() ;
			            		String sql = "";
			            		
		            			sql = "SELECT NPP.PK_SEQ AS CHUCDANH_FK, NPP.TEN AS DIENGIAI FROM PHAMVIHOATDONG PVHD "+ 
	            		          	  "INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = PVHD.Npp_fk "+
	            					  "WHERE PVHD.Nhanvien_fk = '" + session.getAttribute("userId") + "'";
			            		
			            		System.out.println("::: LAY DS NPP: " + sql );
			            		ResultSet rsChucdanh = db.get(sql);
			            		if(rsChucdanh != null)
			            		{
			            			while(rsChucdanh.next())
			            			{
			            				%>			            				
			            				<option value="<%= rsChucdanh.getString("CHUCDANH_FK") %>" ><%= rsChucdanh.getString("DIENGIAI") %></option>
			            				
			            			<%}
			            			rsChucdanh.close();
			            		}
			            		db.shutDown();
			            	%>
			            </select>
		
	    		</td>
	    	</tr>
	    	
	    	<tr>
	    		<td >
    			<br />
    			<input type="submit" value=" Tiếp tục "></td>
	    	</tr>
	    	
	    </table>

    </td>
    <td style="background-image:url(pages/images/borderMiddleRight.png); width:25px; background-repeat:repeat-y; background-position:right"></td>
</tr>
<tr height="25px">
	<td style="background-image:url(pages/images/borderBottomLeft.png); width:25px"></td>
    <td style="background-image:url(pages/images/borderBottomCenter.png)"></td>
    <td style="background-image:url(pages/images/borderBottomRight.png); width:25px"></td>
</tr>
</table>
</form>
</body>
</html>
