<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.taikhoan.beans.ITaiKhoan"%>
<%@page import="qlcn.center.util.Utility"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
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
	function save() {
		if (document.getElementById("ten").value.trim().length <= 0) {
			document.getElementById("dataerror").value = "Bạn chưa nhập tên loại tài khoản.";
			return false;
		}
		
		if (document.getElementById("sotien").value.trim().length <= 0) {
			document.getElementById("dataerror").value = "Bạn chưa nhập số tiền trong tài khoản.";
			return false;
		}

		document.forms["FormTk"].action.value = "save";
		document.forms["FormTk"].submit();
	}
	
	function keypress(e) {
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode;
		else
			keypressed = e.which;
		
		if (keypressed < 13 || (keypressed > 13 && keypressed < 44) || keypressed == 45  || (keypressed > 46 && keypressed < 48) || keypressed > 57) {
			return false;
		}
		return true;
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="FormTk" method="post" action="/QUANLYCANHAN/TaiKhoanUpdateSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="id" value="<%=obj.getID() %>">
		<input type="hidden" name="action" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
							<%if (obj.getID().length() > 0) {%>
								<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Tài khoản > Cập nhật</td>
							<%} else { %>
								<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Tài khoản > Tạo mới</td>
							<%} %>
							<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;&nbsp;</td>
						</tr>
					</table>
					
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="tbdarkrow">
							<td width="30" align="left">
								<a href="/QUANLYCANHAN/TaiKhoanSvl?userId=<%=userId %>">
									<img src="../images/Back30.png" alt="Back" title="Back" border="1" longdesc="Quay ve" style="border-style: outset">
								</a>
							</td>
							<td width="2" align="left"></td>
							<td width="30" align="left">
								<div id="btnSave">
									<A href="javascript: save()">
										<img src="../images/Save30.png" title="Save" alt="Save" border="1" style="border-style: outset">
									</A>
								</div>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>

					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left" colspan="4" class="legendtitle">
								<fieldset>
									<legend class="legendtitle">Thông báo </legend>
									<textarea name="dataerror" id="dataerror" readonly="readonly" rows="1" style="width: 99%"><%=obj.getMsg()%></textarea>
								</fieldset>
							</td>
						</tr>

						<tr>
							<td>
								<fieldset>
									<legend class="legendtitle">Tài khoản</legend>
									<table border="0" width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<%-- <td width="15%" class="plainlabel">Loại <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<select id="loai" name="loai" style="width: 250px">
													<option value=""></option>
													<%if(obj.getLoai().equals("1")) { %>
														<option value="1" selected="selected">Tiền mặt</option>
					                        			<option value="2">ATM</option>
					                        			<option value="3">VISA</option>
													<%} else if(obj.getLoai().equals("2")) { %>
														<option value="1">Tiền mặt</option>
					                        			<option value="2" selected="selected">ATM</option>
					                        			<option value="3">VISA</option>
													<%} else if(obj.getLoai().equals("3")) { %>
														<option value="1">Tiền mặt</option>
					                        			<option value="2">ATM</option>
					                        			<option value="3" selected="selected">VISA</option>
													<%} else { %>
														<option value="1">Tiền mặt</option>
					                        			<option value="2">ATM</option>
					                        			<option value="3">VISA</option>
													<%} %>
												</select>
											</td> --%>
											
											<td width="15%" class="plainlabel">Tên tài khoản <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<input type="text" name="ten" id="ten" value="<%=obj.getTen() %>">
											</td>
											
											<td width="15%" class="plainlabel">Số tiền <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<%if(obj.getID().length() > 0){ %>
													<input type="text" name="sotien" id="sotien" value="<%=obj.getSotien() %>" style="text-align: right;" readonly="readonly" onkeypress="return keypress(event);">
												<%} else { %>
													<input type="text" name="sotien" id="sotien" value="<%=obj.getSotien() %>" style="text-align: right;" onkeypress="return keypress(event);">
												<%} %>
												
												<select name="donvi" class="select2" style="width: 70px;">
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
			                            			<input type="checkbox" name="trangthai" value="1" checked><i> Hoạt động</i>
					                            <%} else { %>
					                            	<input type="checkbox" name="trangthai" value="1" ><i> Hoạt động</i>
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