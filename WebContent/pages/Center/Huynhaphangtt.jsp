<%@page import="geso.traphaco.center.beans.huynhaphangtt.IHuynhaphangList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% IHuynhaphangList obj = (IHuynhaphangList)session.getAttribute("obj"); %>
<% ResultSet dhtvlist = (ResultSet)obj.getDhtvList(); %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
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
<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout()
 {
    if(confirm("Ban co muon dang xuat?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function thongbao()
	{
		if(document.getElementById("msg").value != '')
			alert(document.getElementById("msg").value);
	}
 function clearform()
 {
	 document.forms["dhForm"].tungay.value="";
	 document.forms["dhForm"].denngay.value="";
	 document.forms["dhForm"].submit();
 }
 function newform()
 {
	 document.forms["dhForm"].action1.value="taomoi";
	 document.forms["dhForm"].submit();
 }
 function submitform()
 {   
	 document.forms["dhForm"].action1.value="tim";
    document.forms["dhForm"].submit();
 }
 function xuatExcel()
 {
 	document.forms['dhForm'].action.value= 'toExcel';
 	document.forms['dhForm'].submit();
 	document.forms['dhForm'].action.value= '';
 }

 
 
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dhForm" method="post" action="../../HuynhaphangttSvl">
<input type="hidden" name="action" value="1" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
<script type="text/javascript">
	thongbao();
</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD align="left" class="tbnavigation">
					   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left"  class="tbnavigation">&nbsp;Quản lý tồn kho >Duyệt hủy hóa đơn </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng nhân viên <%= userTen%>&nbsp;</tr>
						</TABLE>
					</TD>
		  </TR>
			<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								<TR>
									<TD width="20%" class="plainlabel" >Từ ngày</TD>
									<TD class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
											    <input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="11">
											</TD></TR>
										</TABLE>	
								</TR>
								<TR>
								  <TD class="plainlabel" >Đến ngày</TD>
							      <TD width="100%" class="plainlabel">
								  		<TABLE cellpadding="0" cellspacing="0">
										  	<TR><TD>
											  <input class="days" type="text" name="denngay"  value="<%=obj.getDenngay() %>" size="11">
										  	</TD></TR>
										</TABLE>
							  	</TR>
								<TR>
									<TD class="plainlabel" colspan="3">
									<a class="button2"  href="javascript:submitform()">
							<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;	
															<a class="button2" href="javascript:clearform()">
							<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
						<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
								 
								</TR>
							</TABLE>
				  </FIELDSET>
							</TD>	
				</TR>

				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Hủy hóa đơn&nbsp;&nbsp;&nbsp;
					 
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="3">
								<TR class="tbheader">
									<TH width="10%" align="center">Số chứng từ </TH>
									<TH width="10%" align="center">Số hóa đơn </TH>
									<TH width="10%" align="center">Ngày chốt </TH>
									<TH width="10%" align="center">Trạng thái </TH>
									<TH width="10%" align="center">Ngày tạo </TH>
									<TH width="10%" align="center">Người tạo </TH>
									<TH width="10%" align="center">Ngày sửa </TH>
									<TH width="10%" align="center">Người sửa </TH>
									<TH width="10%" align="center">Tác vụ</TH>
								</TR>
								
								<% 
									
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(dhtvlist != null){
									try{
									while (dhtvlist.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												
										<TD align="center"><%= dhtvlist.getString("pk_seq") %></TD>                                   
										<TD align="center"><%= dhtvlist.getString("sochungtu") %></TD>
										
										<TD align="center"><%= dhtvlist.getString("ngaychot") %></TD>
	
										<%
										String trangthai = dhtvlist.getString("trangthai");
										if (trangthai.equals("0")){ %>
											<TD align="left">Đang chờ duyệt</TD>

										<%}else{ if(trangthai.equals("2")){ %>
											<TD align="left"><b>Đã hủy</b></TD>
										<%}else{ %>
											<TD align="left"><span style="color: red;">Đã duyệt</span></TD>
										<% }} %>
										
										<TD align="center"><%= dhtvlist.getString("ngaytao") %></TD>
										<TD align="left"><%= dhtvlist.getString("nguoitao")%></TD>
										<TD align="center"><%= dhtvlist.getString("ngaysua") %></TD>
										<TD align="left"><%= dhtvlist.getString("nguoisua") %></TD>
										<TD align="center">

										<% if(dhtvlist.getString("trangthai").equals("0")){ %>
								
										 
										<A href = "../../HuynhaphangttSvl?userId=<%=userId%>&delete=<%= dhtvlist.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa hủy hóa đơn này?')) return false;"></A>&nbsp;
										<A href = "../../HuynhaphangttSvl?userId=<%=userId%>&chotdh=<%= dhtvlist.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border = 0 onclick="if(!confirm('Bạn muốn duyệt hủy hóa đơn này?')) return false;"></A>
										<A href = "../../HuynhaphangttUpdateSvl?userId=<%=userId%>&display=<%= dhtvlist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
										
										<%}else{ %>
										
										<A href = "../../HuynhaphangttUpdateSvl?userId=<%=userId%>&display=<%= dhtvlist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
									
										<%} %>
								
										</TD>
									</TR>
								<% m++;} dhtvlist.close(); } catch(java.sql.SQLException e){} } %>
								
								<TR>
									<TD align="center" colspan="10" class="tbfooter">
									|<   <   1 to 20 of 100   >   >| </TD>
								</TR>
							</TABLE>
							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<!--end body Dossier--></TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<% 	

	try{
	
	if(dhtvlist != null)
		dhtvlist.close();
	if(obj != null){
		obj.DBclose();
		obj = null;
	}
	session.setAttribute("obj",null);
	}
	catch (SQLException e) {}

%>
<%}%>

