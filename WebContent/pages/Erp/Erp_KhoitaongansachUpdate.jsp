<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.ILapngansachList" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.imp.LapngansachList" %>
<%@ page  import = "java.sql.ResultSet"%>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 
		ILapngansachList nsList = (ILapngansachList)session.getAttribute("nsList");
		ResultSet ctylist = nsList.getCtylist();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Khởi tạo ngân sách</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript">
function submitform()
{
	document.forms["nsForm"].submit();	
}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name = "nsForm" method = "post" action="../../KhoitaongansachSvl">
<input type="hidden" name="action" value="save" >
<input type="hidden" name="Id" value="<%=nsList.getId() %>" >
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>

<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<table width="100%" cellpadding="0" cellspacing="1">
				<tr>
					<td align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="22">
						<td align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý ngân sách &gt; Khởi tạo ngân sách > Cập nhật
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
						<td width="30" align="left"> <a href="../../KhoitaongansachSvl" > <img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay ve" style="border-style:outset"> </a></td>
					    <td width="2" align="left" ></td>
						<td width="30" align="left" ><a href="javascript: submitform()" ><img src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border = "1"  style="border-style:outset"></a></td>
						<td>&nbsp;</td>						
					</tr>
					</table>
					</td>
				</tr>
			</table>
								  	
			
			<table width="100%" cellspacing="0" cellpadding="2">
				<tr>
					<td align="left" colspan="4" class="legendtitle">
					<fieldset>
						<legend class="legendtitle">Thông báo </legend>
			   			<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="2" ><%= nsList.getMsg()%></textarea>
					</fieldset>
					</td>
				</tr>								
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
							<LEGEND class="legendtitle">Thông tin ngân sách
							</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">

								<tr>
					  				<td class="plainlabel">Diễn giải</td> 
					  				<td class="plainlabel"> <input type = "text" name = "diengiai" value="<%=nsList.getDiengiai() %>"> </td>
								</tr>
					
								<tr>
					  				<td class="plainlabel">Năm </td> <td class="plainlabel"> <input type = "text" name = "nam" value="<%=nsList.getNamUpdate() %>" readonly=readonly ></td>
								</tr>
								<tr>
					  				<td class="plainlabel">Hiệu lực </td> 
					  				<td class="plainlabel">
					  				
					  				<% if(nsList.getHieuluc().equals("1")) {%>
					  				
					  				 	<input type = "checkbox" name = "hieuluc" value="1" checked readonly=readonly></td>
					  				
					  				<%}else{ %>
					  				
					  					<input type = "checkbox" name = "hieuluc" value="1" readonly=readonly ></td>
					  				
					  				<%} %>
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
try{
	if(ctylist != null) ctylist.close();
}catch (Exception ex)
{
	ex.printStackTrace();
}finally{
	nsList.DBClose();
	session.setAttribute("nsList", null) ; 
}
}
%>