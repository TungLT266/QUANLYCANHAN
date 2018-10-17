<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.erp_donvithuchien.IErp_donvithuchien" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_donvithuchien.imp.Erp_donvithuchien" %>
<%@ page  import = "java.sql.ResultSet"%>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IErp_donvithuchien dvthBean = (IErp_donvithuchien)session.getAttribute("dvthBean");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Cập nhật Phòng ban</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript">
function submitform()
{
	var ma = document.getElementById('ma');
	if (ma.value == "") {
		alert("Vui lòng nhập vào Mã của Phòng ban");
		ma.focus();
		return;
	}

	document.forms["dvthForm"].submit();	
}

function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
{    
	var keypressed = null;
	if (window.event)
		keypressed = window.event.keyCode; //IE
	else
		keypressed = e.which; //NON-IE, Standard
	
	if (keypressed < 48 || keypressed > 57)
	{ 
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
		{//Phím Delete và Phím Back
			return;
		}
		return false;
	}
}

</script>

</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name = "dvthForm" method = "post" action="/TraphacoHYERP/Erp_DonViThucHienUpdateSvl">
<input type="hidden" value="save" name="action">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type = "hidden" value = "<%= dvthBean.getID()%>" name = "id">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<table width="100%" cellpadding="0" cellspacing="1">
				<tr>
					<td align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="22">
						<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; &nbsp;Cơ bản &gt; Phòng ban &gt; Cập nhật
						</td>
						<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;</td> 
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
						<td width="30" align="left"> <a href="/TraphacoHYERP/Erp_DonViThucHienSvl" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </a></td>
					    <td width="2" align="left" ></td>
						<td width="30" align="left" ><a href="javascript: submitform()" ><img src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></a></td>
						<td>&nbsp;</td>						
					</tr>
					</table>
					</td>
				</tr>
			</table>
			
			<tr>
				<td align="left" colspan="4" class="legendtitle">
				<fieldset>
					<legend class="legendtitle">Thông báo </legend>
	    			<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1" ><%=dvthBean.getMsg()%></textarea>
				</fieldset>
				</td>
			</tr>								
								  	
			<tr>
				<td align="left" colspan="4" class="legendtitle">
				<fieldset>
					<legend class="legendtitle">Thông tin Phòng ban</legend>
					<table width="100%" cellspacing="0" cellpadding="6">
					
					<tr>
						<td width="15%" class="plainlabel">Mã<font class="erroralert">*</font></td>
					 	<td  class="plainlabel" ><input type = "text" id = "ma" name="ma" size="40" value="<%=dvthBean.getMA()%>" ></td>
					</tr>

					<tr>
					  	<td class="plainlabel">Tên</td> <td class="plainlabel"> <input type = "text" name = "ten" value="<%=dvthBean.getTEN()%>"> </td>
					</tr>
				    
					<tr>
					  	<td class="plainlabel">Chứng từ bắt đầu bằng số (Prefix)</td> <td class="plainlabel"> <input type = "text" name = "prefix" value="<%=dvthBean.getPrefix() %>"  onkeypress="return keypress(event);"> </td>
					</tr>
					
					<TR>
						<TD class="plainlabel">Hoạt động</TD>
							<TD class="plainlabel">
								<%if (dvthBean.getTrangThai().equals("1")) {%> 
									<input type="checkbox" name="trangthai" value="1" checked="checked"> 
								<%} else {%> 
									<input type="checkbox" name="trangthai" value="0"> <%}%>
							</td>
						</TR>
					
					
					
					</table>
				</fieldset>			
				</td>
			</tr>
</table>				
</form>
</body>
</html>
<%
	dvthBean.DBClose();
	session.setAttribute("dvthBean", null) ;
	
}%>