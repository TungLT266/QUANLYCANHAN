<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.noidungthuchi.beans.INoiDungThuChi"%>
<%@page import="qlcn.center.util.Utility"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
String userId = (String) session.getAttribute("userId");

INoiDungThuChi obj = (INoiDungThuChi) session.getAttribute("obj");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Nội dung thu chi</title>
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="FormNdtc" method="post" action="/QUANLYCANHAN/NoiDungThuChiUpdateSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="id" value="<%=obj.getID() %>">
		<input type="hidden" name="action" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
							<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Nội dung thu chi > Hiển thị</td>
							<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;&nbsp;</td>
						</tr>
					</table>
					
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="tbdarkrow">
							<td width="30" align="left">
								<a href="/QUANLYCANHAN/NoiDungThuChiSvl?userId=<%=userId %>">
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
									<legend class="legendtitle">Nội dung thu chi</legend>
									<table border="0" width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<td width="15%" class="plainlabel">Loại</td>
											<td class="plainlabel">
												<select name="loai" onchange="search();">
													<%if(obj.getLoai().equals("0")){ %>
														<option value="0" selected="selected">Thu - Chi</option>
														<option value="1" disabled="disabled">Thu</option>
														<option value="2" disabled="disabled">Chi</option>
													<%} else if(obj.getLoai().equals("1")){ %>
														<option value="0" disabled="disabled">Thu - Chi</option>
														<option value="1" selected="selected">Thu</option>
														<option value="2" disabled="disabled">Chi</option>
													<%} else { %>
														<option value="0" disabled="disabled">Thu - Chi</option>
														<option value="1" disabled="disabled">Thu</option>
														<option value="2" selected="selected">Chi</option>
													<%} %>
												</select>
											</td>
											
											<td width="15%" class="plainlabel">Tên</td>
											<td class="plainlabel">
												<input type="text" name="ten" id="ten" value="<%=obj.getTen() %>" readonly="readonly">
											</td>
										</tr>
											
										<tr>
											<td class="plainlabel">Diễn giải</td>
											<td class="plainlabel" colspan="3">
												<textarea name="diengiai" rows="5" style="width: 80%; color: black;" readonly="readonly"><%=obj.getDiengiai()%></textarea>
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
	if(obj != null)
		obj.DBClose();
	session.removeAttribute("obj");
} catch (Exception e) {}
%>