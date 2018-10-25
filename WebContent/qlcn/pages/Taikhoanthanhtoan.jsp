<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.taikhoanthanhtoan.beans.ITaiKhoanThanhToanList"%>
<%@page import="qlcn.center.util.Utility"%>

<%
Utility util = new Utility();
String userId = (String) session.getAttribute("userId");

ITaiKhoanThanhToanList obj = (ITaiKhoanThanhToanList) session.getAttribute("obj");
ResultSet TaikhoanthanhtoanRs = obj.getTaikhoanthanhtoanRs();
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
	    document.forms['FormTktt'].id.value = "";
	    document.forms['FormTktt'].ten.value = "";
	    document.forms['FormTktt'].taikhoan.value = "";
	    document.forms['FormTktt'].trangthai.value = "";
	    document.forms['FormTktt'].action.value = 'search';
		document.forms['FormTktt'].submit();
	}
	
	function search() {
		document.forms['FormTktt'].action.value= 'search';
		document.forms['FormTktt'].submit();
	}
	
	function newform() {
		document.forms['FormTktt'].action.value= 'new';
		document.forms['FormTktt'].submit();
	}
	
	function deleteDB() {
		var pin = prompt("Please enter your PIN", "");
		if(pin != null) {
			if(pin != "1995"){
				alert("Mã PIN bạn nhập không đúng.");
				return false;
			}
		} else {
			return false;
		}
		
		document.forms['FormTktt'].action.value= 'deletedb';
		document.forms['FormTktt'].submit();
	}

	function keypress(e) {
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode;
		else
			keypressed = e.which;
		
		if (keypressed < 13 || (keypressed > 13 && keypressed < 48) || keypressed > 57) {
			return false;
		}
		return true;
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="FormTktt" method="post" action="/QUANLYCANHAN/TaiKhoanThanhToanSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="action" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="2">
						<tr height="22">
							<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Tài khoản thanh toán</td>
							<%-- <td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></td> --%>
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
											
											<td width="10%" class="plainlabel">Tên/Số thẻ</td>
											<td class="plainlabel">
												<input type="text" name="ten" value="<%=obj.getTen() %>" onchange="search();">
											</td>
										</tr>
										<tr>
											<td width="10%" class="plainlabel">Tài khoản</td>
											<td class="plainlabel">
												<input type="text" name="taikhoan" value="<%=obj.getTaikhoan() %>" onchange="search();">
											</td>
										
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
										</tr>
										
										<tr>
											<td class="plainlabel">Số Items</td>
											<td class="plainlabel" colspan="3">
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
														<th width="15%">Tên/Số thẻ</th>
														<th width="15%">Tài khoản</th>
														<th width="10%">Trạng thái</th>
														<th width="10%">Ngày tạo</th>
														<th width="10%">Người tạo</th>
														<th width="10%">Ngày sửa</th>
														<th width="10%">Người sửa</th>
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
															<td><%=TaikhoanthanhtoanRs.getString("NGUOITAO") %></td>
															<td align="center"><%=TaikhoanthanhtoanRs.getString("NGAYSUA") %></td>
															<td><%=TaikhoanthanhtoanRs.getString("NGUOISUA") %></td>
															<td align="center">
																<% if(tt.equals("1") || tt.equals("0")){ %>
																		<a href="/QUANLYCANHAN/TaiKhoanThanhToanUpdateSvl?userId=<%=userId %>&update=<%=TaikhoanthanhtoanRs.getString("ID") %>">
																			<img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border=0>
																		</a> &nbsp;
																		<a href="/QUANLYCANHAN/TaiKhoanThanhToanSvl?userId=<%=userId%>&delete=<%=TaikhoanthanhtoanRs.getString("ID") %>" onclick="if(!confirm('Bạn thật sự muốn xóa?')) return false;">
																			<img title="Delete" src="../images/Delete20.png" alt="Delete" width="20" height="20" longdesc="Xoa" border=0>
																		</a>&nbsp;
																		<a href="/QUANLYCANHAN/TaiKhoanThanhToanUpdateSvl?userId=<%=userId%>&display=<%=TaikhoanthanhtoanRs.getString("ID") %>">
																			<img title="Hiển thị" src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0>
																		</a>
																<%} else { %>
																	<a href="/QUANLYCANHAN/TaiKhoanThanhToanUpdateSvl?userId=<%=userId %>&display=<%=TaikhoanthanhtoanRs.getString("ID") %>">
																		<img title="Hiển thị" src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0>
																	</a>
																<%} %>
															</td>
															</tr>
															<%m++; %>
														<%} %>
			                                    	<%} %>
													<tr class="tbfooter">
														<td align="center" valign="middle" colspan="9" class="tbfooter">
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
	if (TaikhoanthanhtoanRs != null)
		TaikhoanthanhtoanRs.close();
	if(obj != null)
		obj.DBClose();
	session.removeAttribute("obj");
} catch (Exception e) {}
%>