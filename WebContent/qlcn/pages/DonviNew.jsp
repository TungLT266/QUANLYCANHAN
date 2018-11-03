<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.donvi.beans.IDonVi"%>
<%@page import="qlcn.center.util.Utility"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
String userId = (String) session.getAttribute("userId");

IDonVi obj = (IDonVi) session.getAttribute("obj");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Đơn vị</title>
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<script language="javascript" type="text/javascript">
	function save() {
		if (document.getElementById("ten").value.trim() == "") {
			document.getElementById("dataerror").value = "Bạn chưa nhập tên đơn vị.";
			return false;
		}

		document.forms["FormDv"].action.value = "save";
		document.forms["FormDv"].submit();
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="FormDv" method="post" action="/QUANLYCANHAN/DonViUpdateSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="id" value="<%=obj.getID() %>">
		<input type="hidden" name="action" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
							<%if (obj.getID().length() > 0) {%>
								<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Đơn vị > Cập nhật</td>
							<%} else { %>
								<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Đơn vị > Tạo mới</td>
							<%} %>
							<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;&nbsp;</td>
						</tr>
					</table>
					
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="tbdarkrow">
							<td width="30" align="left">
								<a href="/QUANLYCANHAN/DonViSvl?userId=<%=userId %>">
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
									<legend class="legendtitle">Đơn vị</legend>
									<table border="0" width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<td width="15%" class="plainlabel">Tên <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<input type="text" style="width: 300px;" name="ten" id="ten" value="<%=obj.getTen() %>">
											</td>
										</tr>
										
										<tr>
											<td width="15%" class="plainlabel">Diễn giải</td>
											<td class="plainlabel">
												<textarea name="diengiai" rows="5" style="width: 70%; color: black;"><%=obj.getDiengiai()%></textarea>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Trạng thái</td>
											<td class="plainlabel">
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
	if(obj != null)
		obj.DBClose();
	session.removeAttribute("obj");
} catch (Exception e) {}
%>