<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.taikhoan.beans.ITaiKhoanLog"%>
<%@page import="qlcn.pages.taikhoan.beans.imp.TaiKhoanLogList"%>
<%@page import="qlcn.center.util.Utility"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
String userId = (String) session.getAttribute("userId");

ITaiKhoanLog obj = (ITaiKhoanLog) session.getAttribute("obj");
List<TaiKhoanLogList> logList = obj.getLogList();

NumberFormat formatter = new DecimalFormat("#,###,###.##");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Tài khoản</title>
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<link href="../css/select2.css" rel="stylesheet"/>
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function() {
		$(".select2").select2();
	});
</script>

<!-- <script language="javascript" type="text/javascript">
	function clearform() { 
	    document.forms['MainForm'].id.value = "";
	    document.forms['MainForm'].ten.value = "";
	    document.forms['MainForm'].trangthai.value = "";
	    document.forms['MainForm'].action.value = 'search';
		document.forms['MainForm'].submit();
	}
	
	function search() {
		document.forms['MainForm'].action.value = 'search';
		document.forms['MainForm'].submit();
	}
	
	function deleterow(id) {
		if(!confirm('Bạn thật sự muốn xóa?')) return false;
		document.forms['MainForm'].idrow.value = id;
		document.forms['MainForm'].action.value = 'delete';
		document.forms['MainForm'].submit();
	}
	
	function newform() {
		document.forms['MainForm'].action.value = 'new';
		document.forms['MainForm'].submit();
	}
	
	function deleteDB() {
		var pin = prompt("Please enter your PIN", "");
		if(pin == null) {
			return false;
		}
		
		document.forms['MainForm'].pinUser.value = pin;
		document.forms['MainForm'].action.value = 'deletedb';
		document.forms['MainForm'].submit();
	}

	//cho phép nhập phím enter, 0->9
	function keypress(e) {
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode;
		else
			keypressed = e.which;
		
		if (keypressed == 13 || (keypressed >= 48 && keypressed <= 57)) {
			return true;
		}
		return false;
	}
</script> -->
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="MainForm" method="post" action="/QUANLYCANHAN/TaiKhoanLogSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="id" value="<%=obj.getID() %>">
		<input type="hidden" name="action" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
							<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Tài khoản > Nhật ký thay đổi</td>
							<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;&nbsp;</td>
						</tr>
					</table>
					
					<table width="100%" cellpadding="0" cellspacing="1">
						<%if(obj.getMsg().trim().length() > 0){ %>
							<tr>
								<td align="left" class="legendtitle">
									<fieldset>
										<legend class="legendtitle">Thông báo </legend>
										<textarea name="dataerror" id="dataerror" readonly="readonly" rows="1" style="width: 99%"><%=obj.getMsg()%></textarea>
									</fieldset>
								</td>
							</tr>
						<%} %>
						
						<tr>
							<td>
								<fieldset>
									<legend class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</legend>
									<table width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<td width="120px" class="plainlabel">Từ ngày</td>
											<td class="plainlabel" width="300px">
												<input type="text" name="tungay" value="<%=obj.getTungay() %>" onchange="search();">
											</td>
											
											<td width="120px" class="plainlabel">Đến ngày</td>
											<td class="plainlabel">
												<input type="text" name="denngay" value="<%=obj.getDenngay() %>" onchange="search();">
											</td>
										</tr>
										
										<tr class="plainlabel">
											<td colspan="4">
												&nbsp;&nbsp;
												<a class="button2" href="javascript:clearform()">
													<img style="top: -4px;" src="../images/Clear32.png" alt="">Clear
												</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>

						<tr>
							<td width="100%">
								<fieldset>
									<legend class="legendtitle">&nbsp;Nhật ký thay đổi (ID: <%=obj.getID() %>) &nbsp;&nbsp;</legend>
									
									<table width="2000px" border="0" cellspacing="1" cellpadding="4">
										<tr class="tbheader">
											<th width="200px">Ngày</th>
											<th width="200px">Tên tài khoản</th>
											<th width="200px">Đơn vị</th>
											<th width="200px">Tài khoản ngân hàng</th>
											<th width="200px">Ngân hàng</th>
											<th width="200px">Tài khoản tín dụng</th>
											<th width="200px">Hạn mức</th>
											<th width="200px">Nợ tín dụng</th>
											<th width="200px">Trạng thái</th>
										</tr>
										<%TaiKhoanLogList log; %>
										<%TaiKhoanLogList log2; %>
										<%int sizelog = logList.size(); %>
                                    	<%for(int i = 0; i < sizelog; i++){ %>
                                    		<%log = logList.get(i); %>
                                    		
                                    		<%
                                    		if(i+1 == sizelog){
                                    			log2 = log;
                                    		} else {
                                    			log2 = logList.get(i+1);
                                    		}
                                    		%>
                                    		
                                        	<%if(i % 2 != 0) { %>
												<tr class="tblightrow">
											<%} else { %>
												<tr class="tbdarkrow">
											<%} %>
											
											<td align="center"><%=log.getNgaylog() %></td>
											
											<%if(log.getTen().equals(log2.getTen())){ %>
												<td><input type="text" value="<%=log.getTen() %>" readonly="readonly" style="width: 100%"></td>
											<%} else { %>
												<td><input type="text" value="<%=log.getTen() %>" readonly="readonly" style="width: 100%; color: red;"></td>
											<%} %>
											
											<%if(log.getDonvi().equals(log2.getDonvi())){ %>
												<td><input type="text" value="<%=log.getDonvi() %>" readonly="readonly" style="width: 100%"></td>
											<%} else { %>
												<td><input type="text" value="<%=log.getDonvi() %>" readonly="readonly" style="width: 100%; color: red;"></td>
											<%} %>
											
											<%if(log.getIsTknganhang().equals(log2.getIsTknganhang())){ %>
												<td align="center"><%=log.getIsTknganhang() %></td>
											<%} else { %>
												<td align="center" style="color: red;"><%=log.getIsTknganhang() %></td>
											<%} %>
											
											<%if(log.getNganhang().equals(log2.getNganhang())){ %>
												<td><input type="text" value="<%=log.getNganhang() %>" readonly="readonly" style="width: 100%"></td>
											<%} else { %>
												<td><input type="text" value="<%=log.getNganhang() %>" readonly="readonly" style="width: 100%; color: red;"></td>
											<%} %>
											
											<%if(log.getIsTktindung().equals(log2.getIsTktindung())){ %>
												<td align="center"><%=log.getIsTktindung() %></td>
											<%} else { %>
												<td align="center" style="color: red;"><%=log.getIsTktindung() %></td>
											<%} %>
											
											<%if(log.getHanmuc().equals(log2.getHanmuc())){ %>
												<td><input type="text" value="<%=log.getHanmuc() %>" readonly="readonly" style="text-align: right; width: 100%"></td>
											<%} else { %>
												<td><input type="text" value="<%=log.getHanmuc() %>" readonly="readonly" style="text-align: right; width: 100%; color: red;"></td>
											<%} %>
											
											<%if(log.getNoTindung().equals(log2.getNoTindung())){ %>
												<td><input type="text" value="<%=log.getNoTindung() %>" readonly="readonly" style="text-align: right; width: 100%"></td>
											<%} else { %>
												<td><input type="text" value="<%=log.getNoTindung() %>" readonly="readonly" style="text-align: right; width: 100%; color: red;"></td>
											<%} %>
											
											<%if(log.getTrangthai().equals(log2.getTrangthai())){ %>
												<td><input type="text" value="<%=log.getTrangthai() %>" readonly="readonly" style="width: 100%"></td>
											<%} else { %>
												<td><input type="text" value="<%=log.getTrangthai() %>" readonly="readonly" style="width: 100%; color: red;"></td>
											<%} %>
											</tr>
										<%} %>
										
										<tr class="tbfooter">
											<td align="center" valign="middle" colspan="10" class="tbfooter">
												<% obj.setNextSplittings(); %>
												<script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
												
												<%if(obj.getNxtApprSplitting() > 1) { %>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%} else { %>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
												<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%int[] listPage = obj.getNextSplittings(); %>
												<%for(int i = 0; i < listPage.length; i++) { %>
													<%if(listPage[i] == obj.getNxtApprSplitting()){ %>
														<a style="color:white;"><%=listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
													<%} else { %>
														<a href="javascript:View(document.forms[0].name, <%=listPage[i] %>, 'view')"><%=listPage[i] %></a>
													<%} %>
													<input type="hidden" name="list" value="<%=listPage[i] %>" />&nbsp;
												<%} %>
												
												<%if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%} else { %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
													<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
												<%} else { %>
													<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
												<%} %>
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
try {
	session.removeAttribute("obj");
} catch (Exception e) {}
%>