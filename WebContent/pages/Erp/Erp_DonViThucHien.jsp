<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_donvithuchien.IErp_donvithuchienList" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_donvithuchien.imp.Erp_donvithuchienList" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%  
    IErp_donvithuchienList dvthList = (IErp_donvithuchienList)session.getAttribute("dvthList");
	ResultSet Rsdvth = dvthList.getRsdvth();	
	int[] quyen = new  int[5];
	quyen = util.Getquyen("Erp_DonViThucHienSvl","",userId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>SalesUp - Đơn Vị Thực Hiện</title>
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
    //document.dvthForm.dvkdId.value = "";
    document.dvthForm.ten.value = "";
    submitform();
}

function submitform()
{
	document.forms['dvthForm'].action.value= 'search';
	document.forms['dvthForm'].submit();
}

function newform()
{
	document.forms['dvthForm'].action.value= 'new';
	document.forms['dvthForm'].submit();
}
function thongbao()
{
	 var tt = document.forms["dvthForm"].msg.value;
	 if(tt.length>0)
    	alert(document.forms["dvthForm"].msg.value);
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dvthForm" method="post" action="../../Erp_DonViThucHienSvl">
<input type="hidden" name="userId" value='<%=userId%>'> 
<input type="hidden" name="userTen" value='<%=userTen%>'> 
<input type="hidden" name="action" value='1'>

<input type="hidden" name="msg" value='<%= dvthList.getMsg() %>'>
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
						   <td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Cơ bản &gt; Phòng ban
						   </td>
   					
						   <td align = "right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;</td>
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
								  	<td class="plainlabel">Phòng ban</td>
								    <TD class="plainlabel" width = "80%">
								    	<input type="text" name="ten" value="<%= dvthList.getTEN() %>" onchange="submitform()"></td>
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


				<tr>
					<td width="100%">
						<fieldset>
						<legend class="legendtitle">&nbsp;Phòng ban &nbsp;&nbsp;&nbsp;
						
						<%if(quyen[0]!=0){ %>
						<a class="button3" href="javascript:newform()">
    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>  
    					<%} %>                          
					 	</legend>
	
		   				<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<tr>
			  	   				<td align="left" colspan="4" class="legendtitle">
									<table width="100%" border="0" cellspacing="1" cellpadding="4">
										<tr class="tbheader">
										<th width="10%">Mã</th>
											<th width="30%">Tên</th>
											<th width="5%">Prefix</th>
											<th width="10%">Trạng thái</th>
											<th width ="9%">Ngày sửa</th>											
											<th width ="9%">Người sửa</th>
															
											<th width="7%" align="center">&nbsp;Tác vụ</th>					
										</tr>
				
									<%
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									
									if(Rsdvth!=null)
									while (Rsdvth.next())
									{
										if(m % 2 != 0) {%>
										<tr class = <%=lightrow%> >
									<%} else {%>
										<tr class = <%=darkrow%> >
									<%}%>
										<td align="left"><%= Rsdvth.getString("ma")%> </td>
										<td align="left"><%= Rsdvth.getString("ten")%> </td>
										<td align="center"><%= Rsdvth.getString("prefix")%> </td>				
										<td align="center"><%= Rsdvth.getString("TrangThai")%> </td>

										<td align="center"><%= Rsdvth.getString("ngaysua")%> </td>										
										<td align="center"><%= Rsdvth.getString("nguoisua")%> </td>
										
											
										<td align = "center">
											<table border = 0 cellpadding="0" cellspacing="0">
												<tr>
												<td>
													<%if(quyen[2]!=0){ %>
													<a href = "../../Erp_DonViThucHienUpdateSvl?userid=<%=userId %>&update=<%=Rsdvth.getString("PK_SEQ")%>"><img title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>&nbsp;
													<%} %>
												</td>
												<%if(quyen[1]!=0){ %>
												<td>
												<a href = "../../Erp_DonViThucHienSvl?userid=<%=userId %>&delete=<%=Rsdvth.getString("PK_SEQ")%>"><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa phòng ban này?')) return false;"></a>&nbsp;
												</td>
											 	<%} %>
											 	<td>
													<!-- QUYEN VIEW DLN --> 
													<a href = "../../Erp_DonViThucHienUpdateSvl?userid=<%=userId %>&display=<%=Rsdvth.getString("PK_SEQ")%>"><img title="Hiển thị" src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
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
	if(Rsdvth != null)
	{
		Rsdvth.close();
	}
	dvthList.DBClose();	
	session.setAttribute("dvthList",null);
}
catch(Exception er){}
}%>
