<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_tiente.IErp_tiente" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_tiente.imp.Erp_tiente" %>
<%@ page  import = "java.sql.ResultSet"%>
<%@ page  import = "java.util.Hashtable" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
IErp_tiente ttBean = (IErp_tiente)session.getAttribute("ttBean");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Best - Update Tiền Tệ</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">

</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name = "ttForm" method = "post" action="../../Erp_TienTeUpdateSvl">
<input type="hidden" value="save" name="action">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type = "hidden" value = "<%= ttBean.getID()%>" name = "id">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<table width="100%" cellpadding="0" cellspacing="1">
				<tr>
					<td align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="22">
						<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng &gt; Tiền tệ > Hiển thị
						</td>
						<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;</td> 
					</tr>
					</table>
					</td>
				</tr>
			</table>
				
			<table width="100%" border="0" cellpadding="1" cellspacing="0">
				<tr>
					<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class = "tbdarkrow">
						<td width="30" align="left"> <a href="../../Erp_TienTeSvl" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </a></td>
						<td>&nbsp;</td>						
					</tr>
					</table>
					</td>
				</tr>
			</table>
								  	
			
					<table width="100%" cellspacing="0" cellpadding="2">
					<tr>
					<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin tiền tệ
								</LEGEND>
								<TABLE  width="100%" cellspacing="0" cellpadding="6">
								<tr>
						<td width="15%" class="plainlabel">Mã<font class="erroralert">*</font></td>
					 	<td  class="plainlabel" ><input type = "text" readonly name="ma" id="ma" size="40" value="<%=ttBean.getMA()%>" ></td>
					</tr>

					<tr>
					  	<td class="plainlabel">Tên</td> <td class="plainlabel"> <input type = "text" readonly name = "ten" value="<%=ttBean.getTEN()%>"> </td>
					</tr>
					<tr>
					  	<td class="plainlabel">Cách đọc tiền bằng chữ </td> 
					  	<td class="plainlabel"> 
					  		<input type = "text" name = "doctiensonguyen" value="<%=ttBean.getDoctiensonguyen()%>"> &nbsp;  ,  &nbsp; 
					  		<input type = "text" name = "doctiensole" value="<%=ttBean.getDoctiensole()%>"> </td>
					</tr>
					</table>
				</fieldset>	
				</td>
			</tr>
</table>	
</td></tr></table>			
</form>
</body>
</html>
<%
	ttBean.DBClose();
}%>