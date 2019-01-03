<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.taikhoan.beans.ITaiKhoanList"%>
<%@page import="qlcn.center.util.Utility"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
String userId = (String) session.getAttribute("userId");

ITaiKhoanList obj = (ITaiKhoanList) session.getAttribute("obj");
ResultSet TaikhoanRs = obj.getTaikhoanRs();

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

<script language="javascript" type="text/javascript">
	function clearform() { 
	    document.forms['FormTk'].id.value = "";
	    document.forms['FormTk'].ten.value = "";
	    document.forms['FormTk'].trangthai.value = "";
	    document.forms['FormTk'].action.value = 'search';
		document.forms['FormTk'].submit();
	}
	
	function search() {
		document.forms['FormTk'].action.value = 'search';
		document.forms['FormTk'].submit();
	}
	
	function newform() {
		document.forms['FormTk'].action.value = 'new';
		document.forms['FormTk'].submit();
	}
	
	function deleteDB() {
		var pin = prompt("Please enter your PIN", "");
		if(pin == null) {
			return false;
		}
		
		document.forms['FormTk'].pinUser.value = pin;
		document.forms['FormTk'].action.value = 'deletedb';
		document.forms['FormTk'].submit();
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
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="FormTk" method="post" action="/QUANLYCANHAN/TaiKhoanSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="action" value="">
		<input type="hidden" name="pinUser" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
							<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Tài khoản</td>
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
											
											<td width="10%" class="plainlabel">Tên tài khoản</td>
											<td class="plainlabel">
												<input type="text" name="ten" value="<%=obj.getTen() %>" onchange="search();">
											</td>
										</tr>
										<tr>
											<%-- <td class="plainlabel">Loại</td>
											<td class="plainlabel">
												<select name="loai" onchange="search();">
													<option value=""></option>
													<option value="1" <%if(obj.getLoai().equals("1")){ %>selected<%} %>>TIỀN MẶT</option>
													<option value="2" <%if(obj.getLoai().equals("2")){ %>selected<%} %>>ATM</option>
													<option value="3" <%if(obj.getLoai().equals("3")){ %>selected<%} %>>VISA</option>
												</select>
											</td> --%>
										
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
												<!-- <a class="button2" href="javascript:search();">
													<img style="top: -4px;" src="../Images/button.png" alt="">Tìm kiếm
												</a>&nbsp;&nbsp;&nbsp;&nbsp; -->
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
														<th width="30%">Tên tài khoản</th>
														<th width="20%">Số tiền</th>
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
			                                    	<%if(TaikhoanRs != null){ %>
				                                    	<%while(TaikhoanRs.next()){ %>
				                                    		<%String tt = TaikhoanRs.getString("trangthai").trim(); %>
				                                    	
				                                        	<%if(m % 2 != 0) { %>
																<tr class=<%=lightrow %>>
															<%} else { %>
																<tr class=<%=darkrow %>>
															<%} %>
															
															<td align="center"><%=TaikhoanRs.getString("id") %></td>
															<td><%=TaikhoanRs.getString("TEN") %></td>
															<td align="center"><%=formatter.format(Double.parseDouble(TaikhoanRs.getString("SOTIEN"))) + " " + TaikhoanRs.getString("donvi") %></td>
															
															<%-- <%String loai = TaikhoanRs.getString("loai").trim(); %>
															<%if(loai.equals("1")) { %>
																<td align="center">Tiền mặt</td>
															<%} else if(loai.equals("2")){ %>
																<td align="center">ATM</td>
															<%} else if(loai.equals("3")) { %>
																<td align="center">VISA</td>
															<%} %> --%>
															
															<%if(tt.equals("0")) { %>
																<td align="center" style="color: red;">Ngưng hoạt động</td>
															<%} else if(tt.equals("2")){ %>
																<td align="center" style="color: red;">Đã xóa</td>
															<%} else if(tt.equals("1")) { %>
																<td align="center">Hoạt động</td>
															<%} %>

															<td align="center"><%=TaikhoanRs.getString("NGAYTAO") %></td>
															<td align="center"><%=TaikhoanRs.getString("NGAYSUA") %></td>
															<td align="center">
																<a href="/QUANLYCANHAN/TaiKhoanUpdateSvl?userId=<%=userId %>&action=copy&id=<%=TaikhoanRs.getString("ID") %>">
																	<img title="Copy" src="../images/copy20.png" alt="Copy" width="20" height="20" longdesc="Copy" border=0>
																</a>
																<% if(tt.equals("1") || tt.equals("0")){ %>
																	<a href="/QUANLYCANHAN/TaiKhoanUpdateSvl?userId=<%=userId %>&action=update&id=<%=TaikhoanRs.getString("ID") %>">
																		<img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border=0>
																	</a>
																	<a href="/QUANLYCANHAN/TaiKhoanUpdateSvl?userId=<%=userId%>&action=display&id=<%=TaikhoanRs.getString("ID") %>">
																		<img title="Hiển thị" src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0>
																	</a>
																	<a href="/QUANLYCANHAN/TaiKhoanSvl?userId=<%=userId%>&action=delete&id=<%=TaikhoanRs.getString("ID") %>" onclick="if(!confirm('Bạn thật sự muốn xóa?')) return false;">
																		<img title="Delete" src="../images/Delete20.png" alt="Delete" width="20" height="20" longdesc="Xoa" border=0>
																	</a>
																<%} else { %>
																	<a href="/QUANLYCANHAN/TaiKhoanUpdateSvl?userId=<%=userId %>&action=display&id=<%=TaikhoanRs.getString("ID") %>">
																		<img title="Hiển thị" src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0>
																	</a>
																<%} %>
															</td>
															</tr>
															<%m++; %>
														<%} %>
			                                    	<%} %>
													<tr class="tbfooter">
														<td align="center" valign="middle" colspan="7" class="tbfooter">
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
	if(obj != null)
		obj.DBClose();
	session.removeAttribute("obj");
} catch (Exception e) {}
%>