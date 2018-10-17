<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.chuyentuyen.IBCSoHoaDon" %>
<%@ page  import = "java.sql.ResultSet" %>
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
	IBCSoHoaDon bean = (IBCSoHoaDon)session.getAttribute("ctuyenBean");
	String[] nvId = bean.getNvId();
	String[] nvMa = bean.getNvMa();
	String[] nvTen = bean.getNvTen();
	String[] nvMauHD = bean.getNvMauHD();
	String[] nvKyhieu = bean.getNvKyhieu();
	String[] nvSohoadontu = bean.getNvSotu();
	String[] nvSohoadonden = bean.getNvSoden();
	String[] nvNgayHD = bean.getNvNgayHD();
	
	String[] nvMauHD2 = bean.getNvMauHD2();
	String[] nvKyhieu2 = bean.getNvKyhieu2();
	String[] nvSohoadontu2 = bean.getNvSotu2();
	String[] nvSohoadonden2 = bean.getNvSoden2();
	String[] nvNgayHD2 = bean.getNvNgayHD2();
	String[] nvSohoadonused = bean.getSoHoaDonUsed();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Phanam - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
		$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
           
        }); 
		
		
    </script>
<style type="text/css">
	.plainlabelNew {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 10pt;
	color: #000000;
	line-height: 15pt;
}
.plainlabelNew2 {
	display: none;
}
</style>
<SCRIPT language="javascript" type="text/javascript">
 function saveform()
 {
	document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
 	document.forms['tbhForm'].action.value='saveSOHOADON';
    document.forms['tbhForm'].submit();
 }
 
 function xuatExcel()
 {
 	document.forms['tbhForm'].action.value= 'excel';
 	document.forms['tbhForm'].submit();
 	
 }
 
</SCRIPT>
 
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tbhForm" method="post" action="../../BCSoHoaDonSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="nppId" id="nppId" value='<%= bean.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
	  <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <TR height="22">
						  	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền kinh doanh &gt; Số hóa đơn 
						  	<TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= bean.getNppTen()%></TD>
						 </TR>
					  </table>
					</TD>
			  </TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="2">
				<TR >
					<TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class = "tbdarkrow">
								<TD width="30" align="left"></TD>
								<TD width="2" align="left" ></TD>
				    			<TD width="30" align="left" >				    			
				    			</TD>
								<TD align="left" >&nbsp;</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
					
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>			
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  cols="139%" rows="1" ><%= bean.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>
				<!-- <TR>
					<TD align="left" >
				  		<a class="button2" href="javascript:xuatExcel()"> 
				  		<img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a> &nbsp;&nbsp;&nbsp;&nbsp;
				  	</TD>
				</TR> -->
				<TR>
				  
				  <TD height="100%" width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Thông tin số hóa đơn &nbsp;</LEGEND>
						<TABLE  width="100%" cellspacing="1" cellpadding="1">
							
							<TR class="tbheader">
								<TH width="15%">Mã nhân viên</TH>
								<TH width="20%">Họ tên</TH>
								<!-- <TH width="5%"> Mẫu hóa đơn</TH> -->
								<TH width="10%">Ký hiệu</TH>
								<TH width="10%">Số hóa đơn từ</TH>
								<TH width="10%">Số hóa đơn đến</TH>
								<TH width="10%">Ngày hóa đơn</TH>
								<TH width="20%">Số hóa đơn đã dùng</TH>
							</TR>
							
							 <%
								if(nvId != null){ 
									for(int i = 0; i < nvId.length; i++)
									{
										%>
										
										<TR>
											<td>
												<input type="hidden" name="nvId" value="<%= nvId[i] %>" style="width: 100%" >
												<input type="text" name="nvMA" value="<%= nvMa[i] %>" style="width: 100%" readonly="readonly" >
											</td>
											<td>
												<input type="text" name="nvTEN" value="<%= nvTen[i] %>" style="width: 100%" readonly="readonly" >
											</td>
											<!-- <td>
												<select name = "nvMauhoadon">
													<option value= "1" selected="selected">Mẫu 1</option>													
												</select>
											</td> -->
											<td>
												<input type="text" name="nvKyhieuhoadon" value="<%= nvKyhieu[i].trim() %>" style="width: 100%"  >
											</td>
											<td>
												<input type="text" name="nvSohoadontu" value="<%= nvSohoadontu[i].trim() %>" style="width: 100%"  >
											</td>
											<td>
												<input type="text" name="nvSohoadonden" value="<%= nvSohoadonden[i].trim() %>" style="width: 100%"  >
											</td>
											<td>
												<input type="text" class= "days" name="nvNgayhd" value="<%= nvNgayHD[i].trim() %>" style="width: 100%"  >
											</td>
											<td>
												<input type="text" name="ShdUsed" value="<%= nvSohoadonused[i].trim() %>" style="width: 100%"  >
											</td>
																						
										</TR>
										
									<% 
										}
								}
							%> 
							
						</TABLE>
				    </FIELDSET>
				  </TD>	
				</TR>
			</TABLE>
	    </TD>
	</TR>
	
</TABLE>
</form>
</BODY>


<%
session.setAttribute("ctuyenBean",null);
}%>
</HTML>
