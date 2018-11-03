<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.thuchi.beans.IThuChi"%>
<%@page import="qlcn.center.util.Utility"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
String userId = (String) session.getAttribute("userId");

IThuChi obj = (IThuChi) session.getAttribute("obj");
ResultSet NoidungthuchiRs = obj.getNoidungthuchiRs();
ResultSet TaikhoanRs = obj.getTaikhoanRs();
ResultSet TaikhoanthanhtoanRs = obj.getTaikhoanthanhtoanRs();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Thu chi</title>
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="FormTc" method="post" action="/QUANLYCANHAN/ThuChiUpdateSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="id" value="<%=obj.getID() %>">
		<input type="hidden" name="action" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
							<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Thu/Chi > Hiển thị</td>
							<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;&nbsp;</td>
						</tr>
					</table>
					
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="tbdarkrow">
							<td width="30" align="left">
								<a href="/QUANLYCANHAN/ThuChiSvl?userId=<%=userId %>">
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
									<legend class="legendtitle">Thu/Chi</legend>
									<table border="0" width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<td width="15%" class="plainlabel">Ngày</td>
											<td class="plainlabel">
												<input type="text" name="ngay" value="<%=obj.getNgay() %>" readonly="readonly">
											</td>
											
											<td width="15%" class="plainlabel">Số tiền</td>
											<td class="plainlabel">
												<input type="text" style="text-align: right;" name="sotien" id="sotien" value="<%=obj.getSotien() %>" readonly="readonly">
												&nbsp;<%=obj.getDonvi() %>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Loại</td>
											<td class="plainlabel">
												<select name="loai" style="width: 200px;">
													<%if(obj.getLoai().equals("2")){ %>
														<option value="1" disabled="disabled">Thu</option>
														<option value="2" selected="selected">Chi</option>
													<%} else { %>
														<option value="1" selected="selected">Thu</option>
														<option value="2" disabled="disabled">Chi</option>
													<%} %>
												</select>
											</td>
											
											<td class="plainlabel">Nội dung thu chi</td>
											<td class="plainlabel">
												<select name="noidungthuchiId" style="width: 200px">
													<option value="0" disabled="disabled"></option>
													<%if(NoidungthuchiRs != null){ %>
														<%while(NoidungthuchiRs.next()){ %>
															<%if(obj.getNoidungthuchiId().equals(NoidungthuchiRs.getString("id"))){ %>
																<option value="<%=NoidungthuchiRs.getString("id") %>" selected="selected"><%=NoidungthuchiRs.getString("ten") %></option>
															<%} else { %>
																<option value="<%=NoidungthuchiRs.getString("id") %>" disabled="disabled"><%=NoidungthuchiRs.getString("ten") %></option>
															<%} %>
														<%} %>
													<%} %>
												</select>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Tài khoản</td>
											<td class="plainlabel">
												<select name="taikhoanId" id="taikhoanId" style="width: 200px">
													<option value="" disabled="disabled"></option>
													<%if(TaikhoanRs != null){ %>
														<%while(TaikhoanRs.next()){ %>
															<%if(obj.getTaikhoanId().equals(TaikhoanRs.getString("id"))){ %>
																<option value="<%=TaikhoanRs.getString("id") %>" selected="selected"><%=TaikhoanRs.getString("ten") %></option>
															<%} else { %>
																<option value="<%=TaikhoanRs.getString("id") %>" disabled="disabled"><%=TaikhoanRs.getString("ten") %></option>
															<%} %>
														<%} %>
													<%} %>
												</select>
											</td>
											
											<td class="plainlabel">Tài khoản thanh toán</td>
											<td class="plainlabel">
												<select name="taikhoanthanhtoanId" style="width: 200px">
													<option value="0" disabled="disabled"></option>
													<%if(TaikhoanthanhtoanRs != null){ %>
														<%while(TaikhoanthanhtoanRs.next()){ %>
															<%if(obj.getTaikhoanthanhtoanId().equals(TaikhoanthanhtoanRs.getString("id"))){ %>
																<option value="<%=TaikhoanthanhtoanRs.getString("id") %>" selected="selected"><%=TaikhoanthanhtoanRs.getString("ten") %></option>
															<%} else { %>
																<option value="<%=TaikhoanthanhtoanRs.getString("id") %>" disabled="disabled"><%=TaikhoanthanhtoanRs.getString("ten") %></option>
															<%} %>
														<%} %>
													<%} %>
												</select>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Diễn giải</td>
											<td class="plainlabel" colspan="3">
												<textarea name="diengiai" rows="5" style="width: 80%; color: black;" readonly="readonly"><%=obj.getDiengiai()%></textarea>
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
	if(NoidungthuchiRs != null)
		NoidungthuchiRs.close();
	if(TaikhoanRs != null)
		TaikhoanRs.close();
	if(TaikhoanthanhtoanRs != null)
		TaikhoanthanhtoanRs.close();
	if(obj != null)
		obj.DBClose();
	session.removeAttribute("obj");
} catch (Exception e) {}
%>