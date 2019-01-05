<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.taikhoanthanhtoan.beans.ITaiKhoanThanhToanList"%>
<%@page import="qlcn.center.util.Utility"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
String userId = (String) session.getAttribute("userId");

ITaiKhoanThanhToanList obj = (ITaiKhoanThanhToanList) session.getAttribute("obj");
ResultSet TaikhoanthanhtoanRs = obj.getTaikhoanthanhtoanRs();
ResultSet TaikhoanRs = obj.getTaikhoanRs();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Tài khoản thanh toán</title>
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

<script language="javascript" type="text/javascript">
	function clearform() { 
	    document.forms['MainForm'].id.value = "";
	    document.forms['MainForm'].loaithe.value = "";
	    document.forms['MainForm'].ten.value = "";
	    document.forms['MainForm'].taikhoan.value = "";
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
	
	function Hienthi(id) {
		var pin = prompt("Please enter your PIN", "");
		if(pin == null) {
			return false;
		}
		
		document.location.href = "/QUANLYCANHAN/TaiKhoanThanhToanUpdateSvl?userId="+<%=userId %>+"&action=display&id="+id+"&pinUser="+pin;
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="MainForm" method="post" action="/QUANLYCANHAN/TaiKhoanThanhToanSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="idrow" value="">
		<input type="hidden" name="action" value="">
		<input type="hidden" name="pinUser" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
							<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Tài khoản thanh toán</td>
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
											<td width="10%" class="plainlabel">ID</td>
											<td class="plainlabel" width="30%">
												<input type="text" name="id" value="<%=obj.getID() %>" onchange="search();">
											</td>
											
											<td width="10%" class="plainlabel">Loại thẻ</td>
											<td class="plainlabel">
												<select name="loaithe" class="select2" style="width: 200px;" onchange="search();">
													<%if(obj.getLoaithe().equals("1")) { %>
														<option value=""></option>
														<option value="1" selected="selected">ATM</option>
														<option value="2">VISA</option>
														<option value="3">MASTERCARD</option>
													<%} else if(obj.getLoaithe().equals("2")) { %>
														<option value=""></option>
														<option value="1">ATM</option>
														<option value="2" selected="selected">VISA</option>
														<option value="3">MASTERCARD</option>
													<%} else if(obj.getLoaithe().equals("3")) { %>
														<option value=""></option>
														<option value="1">ATM</option>
														<option value="2">VISA</option>
														<option value="3" selected="selected">MASTERCARD</option>
													<%} else { %>
														<option value=""></option>
														<option value="1">ATM</option>
														<option value="2">VISA</option>
														<option value="3">MASTERCARD</option>
													<%} %>
												</select>
											</td>
										</tr>
										
										<tr>
											<td width="10%" class="plainlabel">Số thẻ</td>
											<td class="plainlabel">
												<input type="text" name="ten" value="<%=obj.getTen() %>" onchange="search();">
											</td>
										
											<td width="10%" class="plainlabel">Tài khoản</td>
											<td class="plainlabel">
												<select name="taikhoan" class="select2" style="width: 200px" onchange="search();">
													<option value=""></option>
													<%if(TaikhoanRs != null){ %>
														<%while(TaikhoanRs.next()){ %>
															<%if(obj.getTaikhoan().equals(TaikhoanRs.getString("id"))){ %>
																<option value="<%=TaikhoanRs.getString("id") %>" selected><%=TaikhoanRs.getString("ten") %></option>
															<%} else { %>
																<option value="<%=TaikhoanRs.getString("id") %>" ><%=TaikhoanRs.getString("ten") %></option>
															<%} %>
														<%} %>
													<%} %>
												</select>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Trạng thái</td>
											<td class="plainlabel">
												<select name="trangthai" class="select2" style="width: 200px;" onchange="search();">
													<option value=""></option>
													<%if(obj.getTrangthai().equals("0")){ %>
														<option value="0" selected="selected">Ngưng hoạt động</option>
														<option value="1">Hoạt động</option>
														<option value="2">Đã xóa</option>
													<%} else if(obj.getTrangthai().equals("1")){ %>
														<option value="0">Ngưng hoạt động</option>
														<option value="1" selected="selected">Hoạt động</option>
														<option value="2">Đã xóa</option>
													<%} else if(obj.getTrangthai().equals("2")){ %>
														<option value="0">Ngưng hoạt động</option>
														<option value="1">Hoạt động</option>
														<option value="2" selected="selected">Đã xóa</option>
													<%} else { %>
														<option value="0">Ngưng hoạt động</option>
														<option value="1">Hoạt động</option>
														<option value="2">Đã xóa</option>
													<%} %>
												</select>
											</td>
										
											<td class="plainlabel">Số Items</td>
											<td class="plainlabel">
												<input type="text" style="text-align: right;" name="soitems" value="<%=obj.getSoItems() %>" onchange="search();" onkeypress="return keypress(event);">
											</td>
										</tr>

										<tr class="plainlabel">
											<td colspan="4">
												&nbsp;&nbsp;
												<a class="button2" href="javascript:clearform()">
													<img style="top: -4px;" src="../images/Clear32.png" alt="">Clear
												</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<a class="button2" href="javascript:deleteDB()">
													<img style="top: -4px;" src="../images/resetdb32.png" alt="">Refresh Database
												</a>&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>

						<tr>
							<td width="100%">
								<fieldset>
									<legend class="legendtitle">&nbsp;Loại tài khoản &nbsp;&nbsp;
										<a class="button3" href="javascript:newform()">
											<img style="top: -4px;" src="../images/New.png" alt="">New
										</a>
									</legend>

									<table class="" width="100%">
										<tr>
											<td width="98%">
												<table width="100%" border="0" cellspacing="1" cellpadding="4">
													<tr class="tbheader">
														<th width="10%">ID</th>
														<th width="10%">Loại thẻ</th>
														<th width="15%">Số thẻ</th>
														<th width="25%">Tài khoản</th>
														<th width="10%">Trạng thái</th>
														<th width="10%">Ngày tạo</th>
														<th width="10%">Ngày sửa</th>
														<th width="10%">Tác vụ</th>
													</tr>
													<%
													int m = 0;
			                                    	String lightrow = "tblightrow";
			                                    	String darkrow = "tbdarkrow";
			                                    	%>
			                                    	<%if(TaikhoanthanhtoanRs != null){ %>
				                                    	<%while(TaikhoanthanhtoanRs.next()){ %>
				                                    		<%String tt = TaikhoanthanhtoanRs.getString("trangthai").trim(); %>
				                                    	
				                                        	<%if(m % 2 != 0) { %>
																<tr class=<%=lightrow %>>
															<%} else { %>
																<tr class=<%=darkrow %>>
															<%} %>
															
															<td align="center"><%=TaikhoanthanhtoanRs.getString("id") %></td>
															<td align="center"><%=TaikhoanthanhtoanRs.getString("loaithe") %></td>
															<td><%=TaikhoanthanhtoanRs.getString("TEN") %></td>
															<td><%=TaikhoanthanhtoanRs.getString("taikhoan") %></td>
															
															<%if(tt.equals("0")) { %>
																<td align="center" style="color: red;">Ngưng hoạt động</td>
															<%} else if(tt.equals("2")){ %>
																<td align="center" style="color: red;">Đã xóa</td>
															<%} else if(tt.equals("1")) { %>
																<td align="center">Hoạt động</td>
															<%} %>

															<td align="center"><%=TaikhoanthanhtoanRs.getString("NGAYTAO") %></td>
															<td align="center"><%=TaikhoanthanhtoanRs.getString("NGAYSUA") %></td>
															<td align="center">
																<% if(tt.equals("1") || tt.equals("0")){ %>
																	<a href="/QUANLYCANHAN/TaiKhoanThanhToanUpdateSvl?userId=<%=userId %>&action=update&id=<%=TaikhoanthanhtoanRs.getString("ID") %>">
																		<img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border=0>
																	</a>
																	<a href="javascript:deleterow('<%=TaikhoanthanhtoanRs.getString("ID") %>')">
																		<img title="Delete" src="../images/Delete20.png" alt="Delete" width="20" height="20" longdesc="Xoa" border=0>
																	</a>
																<%} %>
																<a href="javascript:Hienthi(<%=TaikhoanthanhtoanRs.getString("ID") %>)" >
																	<img title="Hiển thị" src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0>
																</a>
															</td>
															</tr>
															<%m++; %>
														<%} %>
			                                    	<%} %>
													<tr class="tbfooter">
														<td align="center" valign="middle" colspan="8" class="tbfooter">
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
	if (TaikhoanRs != null)
		TaikhoanRs.close();
	if (TaikhoanthanhtoanRs != null)
		TaikhoanthanhtoanRs.close();
	if(obj != null)
		obj.DBClose();
	session.removeAttribute("obj");
} catch (Exception e) {}
%>