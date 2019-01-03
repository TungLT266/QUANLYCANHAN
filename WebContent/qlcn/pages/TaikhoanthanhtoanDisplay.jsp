<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.taikhoanthanhtoan.beans.ITaiKhoanThanhToan"%>
<%@page import="qlcn.center.util.Utility"%>
<%@page import="java.util.Calendar"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
String userId = (String) session.getAttribute("userId");

ITaiKhoanThanhToan obj = (ITaiKhoanThanhToan) session.getAttribute("obj");
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
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="FormTktt" method="post" action="/QUANLYCANHAN/TaiKhoanThanhToanUpdateSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="id" value="<%=obj.getID() %>">
		<input type="hidden" name="action" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
							<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Tài khoản thanh toán > Hiển thị</td>
							<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;&nbsp;</td>
						</tr>
					</table>
					
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="tbdarkrow">
							<td width="30" align="left">
								<a href="/QUANLYCANHAN/TaiKhoanThanhToanSvl?userId=<%=userId %>">
									<img src="../images/Back30.png" alt="Back" title="Back" border="1" longdesc="Quay ve" style="border-style: outset">
								</a>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>

					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td>
								<fieldset>
									<legend class="legendtitle">Tài khoản thanh toán</legend>
									<table border="0" width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<td width="15%" class="plainlabel">Tài khoản</td>
											<td class="plainlabel">
												<select id="taikhoan" name="taikhoan" style="width: 200px">
													<option value="" disabled="disabled"></option>
													<%if(TaikhoanRs != null){ %>
														<%while(TaikhoanRs.next()){ %>
															<%if(obj.getTaikhoan().equals(TaikhoanRs.getString("id"))){ %>
																<option value="<%=TaikhoanRs.getString("id") %>" selected><%=TaikhoanRs.getString("ten") %></option>
															<%} else { %>
																<option value="<%=TaikhoanRs.getString("id") %>" disabled="disabled"><%=TaikhoanRs.getString("ten") %></option>
															<%} %>
														<%} %>
													<%} %>
												</select>
											</td>
											
											<td class="plainlabel" width="15%">Loại thẻ</td>
											<td class="plainlabel">
												<select id="loaithe" name="loaithe">
													<%if(obj.getLoaithe().equals("1")) { %>
														<option value="0" disabled="disabled"></option>
														<option value="1" selected="selected">ATM</option>
														<option value="2" disabled="disabled">VISA</option>
														<option value="3" disabled="disabled">MASTERCARD</option>
														<option value="4" disabled="disabled">Tín dụng</option>
													<%} else if(obj.getLoaithe().equals("2")) { %>
														<option value="0" disabled="disabled"></option>
														<option value="1" disabled="disabled">ATM</option>
														<option value="2" selected="selected">VISA</option>
														<option value="3" disabled="disabled">MASTERCARD</option>
														<option value="4" disabled="disabled">Tín dụng</option>
													<%} else if(obj.getLoaithe().equals("3")) { %>
														<option value="0" disabled="disabled"></option>
														<option value="1" disabled="disabled">ATM</option>
														<option value="2" disabled="disabled">VISA</option>
														<option value="3" selected="selected">MASTERCARD</option>
														<option value="4" disabled="disabled">Tín dụng</option>
													<%} else if(obj.getLoaithe().equals("3")) { %>
														<option value="0" disabled="disabled"></option>
														<option value="1" disabled="disabled">ATM</option>
														<option value="2" disabled="disabled">VISA</option>
														<option value="3" disabled="disabled">MASTERCARD</option>
														<option value="4" selected="selected">Tín dụng</option>
													<%} else { %>
														<option value="0" selected="selected"></option>
														<option value="1" disabled="disabled">ATM</option>
														<option value="2" disabled="disabled">VISA</option>
														<option value="3" disabled="disabled">MASTERCARD</option>
														<option value="4" disabled="disabled">Tín dụng</option>
													<%} %>
												</select>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Số thẻ</td>
											<td class="plainlabel">
												<input type="text" name="sothe" id="sothe" value="<%=obj.getSothe() %>" readonly="readonly">
											</td>
											
											<td class="plainlabel">Tên chủ thẻ</td>
											<td class="plainlabel">
												<input type="text" name="tenchuthe" id="tenchuthe" value="<%=obj.getTenchuthe() %>" readonly="readonly">
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Thời gian hiệu lực</td>
											<td class="plainlabel">
												<select name="thanghieuluc" style="width: 78px">
													<%for(int i = 1; i <= 12; i++) { %>
														<%if(obj.getThanghieuluc().equals(i+"")){ %>
															<option value="<%=i %>" selected="selected"><%=i %></option>
														<%} else { %>
															<option value="<%=i %>" disabled="disabled"><%=i %></option>
														<%} %>
													<%} %>
												</select>
												<%int yearCurrent = Calendar.getInstance().get(Calendar.YEAR); %>
												<select name="namhieuluc" style="width: 120px">
													<%for(int i = yearCurrent-50; i <= yearCurrent+50; i++) { %>
														<%if(obj.getNamhieuluc().equals(i+"")){ %>
															<option value="<%=i %>" selected="selected"><%=i %></option>
														<%} else { %>
															<option value="<%=i %>" disabled="disabled"><%=i %></option>
														<%} %>
													<%} %>
												</select>
											</td>
											
											<td class="plainlabel">Thời gian hết hạn</td>
											<td class="plainlabel">
												<select name="thanghethan" style="width: 78px">
													<%for(int i = 1; i <= 12; i++) { %>
														<%if(obj.getThanghethan().equals(i+"")){ %>
															<option value="<%=i %>" selected="selected"><%=i %></option>
														<%} else { %>
															<option value="<%=i %>" disabled="disabled"><%=i %></option>
														<%} %>
													<%} %>
												</select>
												<select name="namhethan" style="width: 120px">
													<%for(int i = yearCurrent-50; i <= yearCurrent+50; i++) { %>
														<%if(obj.getNamhethan().equals(i+"")){ %>
															<option value="<%=i %>" selected="selected"><%=i %></option>
														<%} else { %>
															<option value="<%=i %>" disabled="disabled"><%=i %></option>
														<%} %>
													<%} %>
												</select>
											</td>
										</tr>
										<tr>
											<td class="plainlabel">Mã PIN</td>
											<td class="plainlabel">
												<input type="text" style="text-align: right;" id="mapin" name="mapin" value="<%=obj.getMapin() %>" readonly="readonly">
											</td>
										
											<td class="plainlabel">Chữ ký</td>
											<td class="plainlabel">
												<input type="text" style="text-align: right;" name="chuky" value="<%=obj.getChuky() %>" readonly="readonly">
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Trạng thái</td>
											<td class="plainlabel" colspan="3">
												<%if(obj.getTrangthai().equals("1")){ %>
			                            			<input type="checkbox" name="trangthai" value="1" checked disabled="disabled"><i> Hoạt động</i>
					                            <%} else { %>
					                            	<input type="checkbox" name="trangthai" value="1" disabled="disabled"><i> Hoạt động</i>
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
	if(TaikhoanRs != null)
		TaikhoanRs.close();
	if(obj != null)
		obj.DBClose();
	session.removeAttribute("obj");
} catch (Exception e) {}
%>