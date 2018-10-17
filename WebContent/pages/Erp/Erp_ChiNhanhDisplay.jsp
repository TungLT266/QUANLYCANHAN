<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.erp_chinhanh.IErp_chinhanh" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_chinhanh.imp.Erp_chinhanh" %>
<%@ page  import = "java.sql.ResultSet"%>

<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 

	IErp_chinhanh cnBean = (IErp_chinhanh)session.getAttribute("cnBean");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>TraphacoERP - Update Chi Nhánh</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<script>
	function goBack() {
	    window.history.back();
	}
	</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="cnForm" method="post" action="../../Erp_ChiNhanhUpdateSvl">
<input type="hidden" value="save" name="action">
<INPUT type="hidden" name="userId" value='<%=userId %>' size="30">
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type = "hidden" value = "<%=cnBean.getID()%>" name = "id">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<table width="100%" cellpadding="0" cellspacing="1">
			<tr>
					<td align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="22">
						<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Cơ bản &gt; Chi nhánh &gt; Hiển thị </TD>
						<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
					</tr>
					</table>
					</td>
				</tr>
			</table>
				
			<table width="100%" border="0" cellpadding="3" cellspacing="0">
				<tr>
					<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class = "tbdarkrow">
						<td width="30" align="left"> <a href="javascript:goBack();" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </a></td>
						<td>&nbsp;</td>						
					</tr>
					</table>
					</td>
				</tr>
			</table>
								  				
			<table width="100%" cellspacing="0" cellpadding="6">
					
					<tr>
				<td align="left" colspan="4" class="legendtitle">
				<fieldset>
					<legend class="legendtitle">Thông tin chi nhánh</legend>
					<TABLE  width="100%" cellspacing="0" cellpadding="6">
										
					
					<tr>
						<td width="15%" class="plainlabel">Mã<font class="erroralert">*</font></td>
					 	<td  class="plainlabel" ><input type = "text" readonly  name="ma" size="40" value="<%=cnBean.getMA()%>" ></td>
					</tr>

						<tr>
					  		<td class="plainlabel">Tên</td> <td class="plainlabel"> <input type = "text" name = "ten" readonly  value="<%=cnBean.getTEN()%>"> </td>
						</tr>

					<tr>
						<td class="plainlabel">Hoạt động</td>
						<td class="plainlabel">
							<%if(cnBean.getTRANGTHAI().equals("1")){%>
								<input name = "hoatdong" checked = "checked" disabled type = "checkbox" value = "1">
							<%}
							else { %>
								<input name = "hoatdong" type = "checkbox" disabled value = "1">
								 <%}%>
						</td>
					</tr>
					</table>
				</fieldset>			
				</td>
			</tr>
</table>
</td></tr>
</table>				
</form>
</body>
</html>
<%
	cnBean.DBClose();
	} %>
