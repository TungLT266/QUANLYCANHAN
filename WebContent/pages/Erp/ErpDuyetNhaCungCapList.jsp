<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.duyetnhacungcap.IErpDuyetNhaCungCapList"%>
<%@page import="geso.traphaco.erp.beans.duyetnhacungcap.imp.ErpDuyetNhaCungCapList"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IErpDuyetNhaCungCapList lhmnk = (IErpDuyetNhaCungCapList) session.getAttribute("obj");
	ResultSet Rslistsp = lhmnk.getListSanPham();
	ResultSet Rslistncc = lhmnk.getListNhacungcap();
	ResultSet Rslistduyetncc = lhmnk.getListDuyetNcc();
	 int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpDuyetNhaCungCapSvl","",userId);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Loại tài khoản kế toán</title>
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>
		 <link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true,
						yearRange: "2015:2030"
				});            
	        }); 		
		$(document).ready(function()
				{
				 $("#listsanpham").select2();
				 $("#listnhacungcap").select2();
				});
	</script>
<script language="javascript" type="text/javascript">
	function xoaform() {
		document.DuyetNhaCungCapForm.listsanpham.value = "";
		document.DuyetNhaCungCapForm.listnhacungcap.value = "";
		Search();
	}

	function Search() {
		document.forms['DuyetNhaCungCapForm'].action.value = 'Search';
		document.forms['DuyetNhaCungCapForm'].submit();
	}

	function newform() {
		document.forms['DuyetNhaCungCapForm'].action.value = 'New';
		document.forms['DuyetNhaCungCapForm'].submit();

	}
	function msg(){
		if (!msg.equal("")){
			alert(msg);
		}
	}
	
	function thongbao()
	{
		if(document.getElementById("msg").value != ''&& document.getElementById("msg").value!=null)
		{alert(document.getElementById("msg").value);
		}
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="DuyetNhaCungCapForm" method="post" action="../../ErpDuyetNhaCungCapSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="userTen" value='<%=userTen%>'> <input
			type="hidden" name="action" value='1'>
			
			<input type="hidden" id="msg" value="<%=lhmnk.getMsg() %>">
			
			<script language="javascript" type="text/javascript">
			    thongbao();
			</script> 
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="22">
						<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng &gt; Duyệt nhà cung cấp </td>
						<td align="right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
						</td>
					</tr>
				</table>	
				<fieldset>
					<legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
					<table width="100%" cellpadding="6" cellspacing="0" style="text-align: left">
						<tr>
							<td class="plainlabel">Nhà cung cấp</td>
							<TD class="plainlabel" >
		                      <select name="listnhacungcap" id="listnhacungcap" style="width:200px" onchange="Search()" class="select2" >
		                      <option value=""></option>
		                       <%
		                        if(Rslistncc!= null)
		                        {   	 
		                         while(Rslistncc.next())
		                         {
			                          if(Rslistncc.getString("PK_SEQ").equals(lhmnk.getNhacungcap_Fk()))
			                          {
			                         %>
			                         <option selected="selected" value="<%= Rslistncc.getString("PK_SEQ") %>"><%=Rslistncc.getString("TEN") %></option>
			                         <%} else { 
			                         %>
			                          	<option value="<%= Rslistncc.getString("PK_SEQ") %>"><%=Rslistncc.getString("TEN") %></option>
			                          <%} %>
			                         <%} %>
			                        <% Rslistncc.close(); }%>
		                                          
		                  		 </select>     
	                        </TD>
						</tr>
						<tr>
							<td class="plainlabel">Sản phẩm</td>
							<TD class="plainlabel" >
		                      <select name="listsanpham" id="listsanpham" style="width:200px" onchange="Search()" class="select2" >
		                      <option value=""></option>
		                       <%
		                        if(Rslistsp!= null)
		                        {   	 
		                         while(Rslistsp.next())
		                         {
			                          if(Rslistsp.getString("PK_SEQ").equals(lhmnk.getSanPham_Fk()))
			                          {
			                         %>
			                         <option selected="selected" value="<%= Rslistsp.getString("PK_SEQ") %>"><%= Rslistsp.getString("MA")+"-"+Rslistsp.getString("TEN") %></option>
			                         <%} else { 
			                         %>
			                          	<option value="<%= Rslistsp.getString("PK_SEQ") %>"><%= Rslistsp.getString("MA")+"-"+Rslistsp.getString("TEN") %></option>
			                          <%} %>
			                         <%} %>
			                        <% Rslistsp.close(); }%>
		                                          
		                  		 </select>     
	                        </TD>
						</tr>
						<tr>
							<td colspan="2" class="plainlabel">
								<a class="button2" href="javascript:xoaform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
								</a>
							</td>
						</tr>
					</table>
				</fieldset>				
				<fieldset>
					<legend class="legendtitle">
						&nbsp;Duyệt nhà cung cấp &nbsp;&nbsp;&nbsp; 
				
						 <a class="button3" href="javascript:newform()"> <img style="top: -4px;"
							src="../images/New.png" alt="">Tạo mới
						</a>
		
					</legend>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" colspan="5">
								<table width="100%" border="0" cellspacing="1" cellpadding="4">
									<tr class="tbheader">
										<th width="20%" align="center">Nhà cung cấp</th>
										<th width="10%" align="center">Trạng thái</th>
										<th width="10%" align="center">Từ ngày</th>
										<th width="10%" align="center">Đến ngày</th>
						
										<th width="10%" align="center">&nbsp;Tác vụ</th>
									</tr>
									<%
										int m = 0;
										String lightrow = "tblightrow";
										String darkrow = "tbdarkrow";
										if (Rslistduyetncc != null)
											while (Rslistduyetncc.next())
											{
												String trangthai = Rslistduyetncc.getString("TRANGTHAI");
												if (trangthai.equals("0"))
													trangthai = "Chưa chốt";
												else if(trangthai.equals("1"))
													trangthai = "Đã chốt";
												else if(trangthai.equals("2"))
													trangthai="Đã xóa";
												if (m % 2 != 0) {
									%>
									<tr class=<%=lightrow%>>
										<%
											} else {
										%>
									
									<tr class=<%=darkrow%>>
										<%
											}
										%>
										<td align="center"><%=Rslistduyetncc.getString("tennhacc")%></td>
										<td align="center"><%=trangthai%></td>
										<td align="center"><%=Rslistduyetncc.getString("TUNGAY")%></td>
										<td align="center"><%=Rslistduyetncc.getString("DENNGAY")%></td>
							
										<td align="center">
											<table border=0 cellpadding="0" cellspacing="0">
												<tr>
												<% String tt=Rslistduyetncc.getString("TRANGTHAI"); %>
												<td> 
													<a href="../../ErpDuyetNhaCungCapUpdateSvl?userid=<%=userId%>&update=<%=Rslistduyetncc.getString("PK_SEQ")%>"><img
													title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A>&nbsp;
												</td>
												<%if(!tt.equals("2")) {%>
													<td>   
														
												        <a href="../../ErpDuyetNhaCungCapSvl?userid=<%=userId%>&delete=<%=Rslistduyetncc.getString("PK_SEQ")%>"><img
														title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
														onclick="if(!confirm('Bạn muốn xóa duyệt nhà cung cấp này?')) return false;"> </a>&nbsp;  
														
													</td>
													<%if(tt.equals("0")) {%>
													<td>   
														
												        <a href="../../ErpDuyetNhaCungCapSvl?userid=<%=userId%>&chot=<%=Rslistduyetncc.getString("PK_SEQ")%>"><img
														title="Duyệt" src="../images/Chot.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
														onclick="if(!confirm('Bạn muốn chốt duyệt nhà cung cấp này?')) return false;"> </a>&nbsp;  
														
													</td>
													<%} %>
												<%} %>
												</tr>
											</table>
									</tr>
									<%
										m++;
											}
									%>
									<tr class="tbfooter" > 
									 <TD align="center" valign="middle" colspan="13" class="tbfooter">
									 	<%if(lhmnk.getNxtApprSplitting() >1) {%>
											<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpDmhForm', 1, 'view')"> &nbsp;
										<%}else {%>
											<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
											<%} %>
										<% if(lhmnk.getNxtApprSplitting() > 1){ %>
											<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpDmhForm', 'prev')"> &nbsp;
										<%}else{ %>
											<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
										<%} %>
										
										<%
											int[] listPage = lhmnk.getNextSplittings();
										 if(listPage!=null){
											for(int i = 0; i < listPage.length; i++){
										%>
										
										<% 							
										
										if(listPage[i] == lhmnk.getNxtApprSplitting()){ %>
										
											<a  style="color:white;"><%= listPage[i] %>/ <%=lhmnk.getTheLastSplitting() %></a>
										<%}else{ %>
											<a href="javascript:View('erpDmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
										<%} %>
											<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
										<%}
											}%>
										
										<% if(lhmnk.getNxtApprSplitting() < lhmnk.getTheLastSplitting()){ %>
											&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpDmhForm', 'next')"> &nbsp;
										<%}else{ %>
											&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
										<%} %>
										<%if(lhmnk.getNxtApprSplitting() == lhmnk.getTheLastSplitting()) {%>
									   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
								   		<%}else{ %>
								   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpDmhForm', -1, 'view')"> &nbsp;
								   		<%} %>
									</TD>
								 </tr> 
								</table>
							</td>
						</tr>
					</table>
				</fieldset>							
	</form>
</body>
</html>
<%

lhmnk.closeDB();
	}
%>
