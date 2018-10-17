<%@page import="geso.traphaco.distributor.beans.huynhaphang.IHuynhaphangList"%>
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
 function clearform()
 {
	 

	
	 document.getElementById("emp").selected = true;
	 document.forms["dhForm"].reset();
 }
 function newform()
 {
	 document.forms["dhForm"].action1.value="taomoi";
	 document.forms["dhForm"].submit();
 }
 function submitform()
 {   
    document.forms["dhForm"].submit();
 }
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dhForm" method="post" action="../../DonhangtraveSvl">
<input type="hidden" name="action1" value="1" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD align="left" class="tbnavigation">
					   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation"> &nbsp;Xử lý đơn hàng> Hủy nhập hàng
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= userTen %>&nbsp;</tr>
						</TABLE>
					</TD>
		  </TR>
			<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								
																
								<TR>
									<TD class="plainlabel">Trạng thái </TD>
									<TD class="plainlabel">
										<select name="trangthai" onChange="submitform();">
											    <% if (obj.getTrangthai().equals("1")){%>
											  	<option id="emp" value=""></option>
											  	<option value="0">Chưa chốt</option>
											  	<option value="1" selected>Đang chờ duyệt</option>
											  	
											  	<option value="2">Đã hủy</option>
											  	<option value="3">Đã duyệt</option>
											  	
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>
											 	<option id="emp" value=""></option>
											 	 <option value="0" selected>Chưa chốt</option>
											  	<option value="1" >Đang chờ duyệt</option>
											 	 <option value="2" >Đã hủy</option>
											 	 <option value="3">Đã duyệt</option>
											  	
											  	
											<%}else if(obj.getTrangthai().equals("2")){%>	
											<option id="emp" value=""></option>										
											 	<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đang chờ duyệt</option>
											 	 <option value="2" selected>Đã hủy</option>										  	
											  	
											  	<option value="3">Đã duyệt</option>
											  	
											  	
											<%}else if(obj.getTrangthai().equals("3")) { %>
												<option id="emp" value=""></option>
												<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đang chờ duyệt</option>											
											  	<option value="2" >Đã hủy</option>
											  	<option value="3" selected>Đã duyệt</option>
											  	
											  	
											<%} else{ %>
												<option id="emp" value="" selected></option>
												<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đang chờ duyệt</option>											
											  	<option value="2" >Đã hủy</option>
											  	<option value="3">Đã duyệt</option>
											<% } %>
									          </select>
									</TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel" >Từ ngày</TD>
									<TD class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
											    <input class="days" type="text" name="tungay" size="11">
											</TD></TR>
										</TABLE>	
								</TR>
								<TR>
								  <TD class="plainlabel" >Đến ngày</TD>
							      <TD width="100%" class="plainlabel">
								  		<TABLE cellpadding="0" cellspacing="0">
										  	<TR><TD>
											  <input class="days" type="text" name="denngay" size="11">
										  	</TD></TR>
										</TABLE>
							  	</TR>
								<TR>
									<TD class="plainlabel" colspan="3">
									<a class="button2" >
	<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;	
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
									
									<!--  
									  <INPUT name="action" type="submit" value="Tim kiem">&nbsp;
									  
                                      <INPUT name="reinitialiser" type="reset" value="Nhap lai"></TD>
                                      -->
								</TR>
							</TABLE>
				  </FIELDSET>
							</TD>	
				</TR>

				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Hủy nhập hàng&nbsp;&nbsp;&nbsp;
					<a class="button3" onclick="newform()">
    	<img style="top: -4px;" src="../images/New.png" >Tạo mới </a>                            
					<!-- <INPUT name="action" type="submit" value="Tao moi"> -->
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="3">
								<TR class="tbheader">
									<TH width="10%" align="center">Mã đơn hàng </TH>
									<TH width="10%" align="center">Số chứng từ </TH>
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
											<TD align="left">Chưa chốt</TD>
										<%}else{ if(trangthai.equals("1")){ %>
											<TD align="left"><i>Đang chờ duyệt</i></TD>
										<%}else{ if(trangthai.equals("2")){ %>
											<TD align="left"><b>Đã hủy</b></TD>
										<%}else{ %>
											<TD align="left"><span style="color: red;">Đã duyệt</span></TD>
										<% }}} %>
										
										<TD align="center"><%= dhtvlist.getString("ngaytao") %></TD>
										<TD align="left"><%= dhtvlist.getString("nguoitao")%></TD>
										<TD align="center"><%= dhtvlist.getString("ngaysua") %></TD>
										<TD align="left"><%= dhtvlist.getString("nguoisua") %></TD>
										<TD align="center">

										<% if(dhtvlist.getString("trangthai").equals("0")){ %>
								
										<A href = "../../DonhangtraveUpdateSvl?userId=<%=userId%>&update=<%= dhtvlist.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
										<A href = "../../DonhangtraveSvl?userId=<%=userId%>&delete=<%= dhtvlist.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa đơn hàng này?')) return false;"></A>&nbsp;
										<A href = "../../DonhangtraveSvl?userId=<%=userId%>&chotdh=<%= dhtvlist.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border = 0></A>
										
										<%}else{ %>
										
										<A href = "../../DonhangtraveUpdateSvl?userId=<%=userId%>&display=<%= dhtvlist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
									
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

