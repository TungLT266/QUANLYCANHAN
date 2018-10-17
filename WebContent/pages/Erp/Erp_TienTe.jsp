<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_tiente.IErp_tienteList" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_tiente.imp.Erp_tienteList" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%	IErp_tienteList ttList = (Erp_tienteList)session.getAttribute("tt");
	ResultSet Rstt = ttList.getRstt();
	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("Erp_TienTeSvl","",userId);
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>Best - Tiền Tệ</title>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>

<script language="javascript" type="text/javascript">

function xoaform()
{
    document.ttForm.ma.value = "";
    document.ttForm.ten.value = "";
    submitform();
}

function submitform()
{
	document.forms['ttForm'].action.value= 'search';
	document.forms['ttForm'].submit();
}

function newform()
{
	document.forms['ttForm'].action.value= 'new';
	document.forms['ttForm'].submit();
}

function thongbao()
{
	 var tt = document.forms["ttForm"].msg.value;
	 if(tt.length>0)
    	alert(document.forms["ttForm"].msg.value);
	}

</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="ttForm" method="post" action="../../Erp_TienTeSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%= ttList.getMsg() %>'>
<input type="hidden" name="chixem" value='<%= ttList.getChixem() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<table width="100%" cellpadding="0" cellspacing="1">		
				<tr>
					<td align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng &gt; Tiền tệ
						   </td>
   
						   <td align = "right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td>
						  </tr>
 					  </table>
					</td>
				</tr>
				<tr>
					<td>
					<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td width="100%" align="center" >
							<fieldset>
							  <legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
							<table width="100%" cellpadding="6" cellspacing="0">
								
								<tr>
								  	<td class="plainlabel">Mã</td>
								    <TD class="plainlabel"><input type="text" name="ma" value="<%= ttList.getMA()%>" onchange="submitform()"></td>
						      	</tr>
						      	
						      	<tr>
								  	<td class="plainlabel">Tên</td>
								    <TD class="plainlabel"><input type="text" name="ten" value="<%= ttList.getTEN() %>" onchange="submitform()"></td>
						      	</tr>

							    <tr>
									<td colspan="2" class="plainlabel">
									<a class="button2" href="javascript:xoaform()">
									<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								
							</table>

							</fieldset>
						</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			<table width="50%" cellpadding="0" cellspacing="1">
				<tr>
					<td >
						<fieldset>
						<legend class="legendtitle">&nbsp;Tiền tệ&nbsp;&nbsp;&nbsp;
						<%if(quyen[0]!=0 && ttList.getChixem().equals("0") ){ %>
						<a class="button3" href="javascript:newform()">
    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a> 
    					<%} %>                           
					 	</legend>
	
		   				<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<tr>
			  	   				<td align="left" colspan="4" class="legendtitle">
									<table width="100%" border="0" cellspacing="1" cellpadding="4">
										<tr class="tbheader">
											<th width="20%">Mã</th>
											<th width="60%">Tên</th>											
											<th width="20%" align="center">&nbsp;Tác vụ</th>					
										</tr>
				
									<%
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									
									while (Rstt.next())
									{
										if(m % 2 != 0) {%>
										<tr class = <%=lightrow%> >
									<%} else {%>
										<tr class = <%=darkrow%> >
									<%}%>
									
										<td align="center"><%= Rstt.getString("ma")%> </td>
										<td align="center"><%= Rstt.getString("ten")%> </td>										
										
										<td align = "center">
											<table border = 0 cellpadding="0" cellspacing="0">
												<tr>
												<td>
												<%if(quyen[2]!=0){ %>
												<a href = "../../Erp_TienTeUpdateSvl?userid=<%=userId %>&update=<%=Rstt.getString("pk_seq")%>"><img title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>&nbsp;
												<%} %>
												</td>
												<td>
												<%if(quyen[1]!=0){ %>
												<a href = ../../Erp_TienTeSvl?userid=<%=userId %>&delete=<%=Rstt.getString("pk_seq")%>><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa mã tiền tệ này?')) return false;"></a>&nbsp;
												<%} %>
												</td>
												<td>
												<!-- QUYEN VIEW DLN --> 
												<a href = "../../Erp_TienTeUpdateSvl?userid=<%=userId %>&display=<%=Rstt.getString("pk_seq")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
												<!-- END QUYEN VIEW DLN --> 
												</td>
												</tr>
											</table>				
										</tr>
									
									<% 	m++;
									}%>									
									
										<tr>
											<td height="26" colspan="11" align="center" class="tbfooter">&nbsp;	</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

					</fieldset>
					</td>
				</tr>
		</table>
		</td>
	</tr>
</table>
		
</form>
</body>
</html>

<%
try
{
	if(Rstt != null)
	{
		Rstt.close();
	}
	ttList.DBClose();	
}
catch(Exception er){}
}%>
