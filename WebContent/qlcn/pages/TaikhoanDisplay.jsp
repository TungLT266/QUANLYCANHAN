<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.taikhoan.beans.ITaiKhoan"%>
<%@page import="qlcn.center.util.Utility"%>

<%
Utility util = new Utility();
String userId = (String) session.getAttribute("userId");
ITaiKhoan obj = (ITaiKhoan) session.getAttribute("obj");
ResultSet DonviRs = obj.getDonviRs();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Tài khoản</title>
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="FormTk" method="post" action="/QUANLYCANHAN/TaiKhoanUpdateSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="id" value="<%=obj.getID() %>">
		<input type="hidden" name="action" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="2">
						<tr height="22">
							<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Tài khoản > Hiển thị</td>
							<%-- <td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></td> --%>
						</tr>
					</table>
					
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="tbdarkrow">
							<td width="30" align="left">
								<a href="/QUANLYCANHAN/TaiKhoanSvl?userId=<%=userId %>">
									<img src="../images/Back30.png" alt="Back" title="Back" border="1" longdesc="Quay ve" style="border-style: outset">
								</a>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>

					<table width="100%" cellpadding="0" cellspacing="1">
						<%-- <tr>
							<td align="left" colspan="4" class="legendtitle">
								<fieldset>
									<legend class="legendtitle">Thông báo </legend>
									<textarea name="dataerror" id="dataerror" readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
								</fieldset>
							</td>
						</tr> --%>

						<tr>
							<td>
								<fieldset>
									<legend class="legendtitle">Tài khoản</legend>
									<table border="0" width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<td width="15%" class="plainlabel">Tên tài khoản</td>
											<td class="plainlabel"><input type="text" name="ten" id="ten" value="<%=obj.getTen() %>" readonly="readonly"></td>
											
											<td width="15%" class="plainlabel">Số tiền</td>
											<td class="plainlabel">
												<input type="text" name="sotien" id="sotien" value="<%=obj.getSotien() %>" style="text-align: right;" readonly="readonly">
												<select name="donvi" style="width: 70px;" disabled="disabled">
													<%if(DonviRs != null){ %>
														<%while(DonviRs.next()){ %>
															<%if(obj.getDonvi().equals(DonviRs.getString("id"))){ %>
																<option value="<%=DonviRs.getString("id") %>" selected><%=DonviRs.getString("ten") %></option>
															<%} else { %>
																<option value="<%=DonviRs.getString("id") %>" ><%=DonviRs.getString("ten") %></option>
															<%} %>
														<%} %>
													<%} %>
												</select>
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
	if(DonviRs != null)
		DonviRs.close();
	if(obj != null)
		obj.DBClose();
	session.removeAttribute("obj");
} catch (Exception e) {}
%>