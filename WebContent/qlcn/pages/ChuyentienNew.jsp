<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.chuyentien.beans.IChuyenTien"%>
<%@page import="qlcn.center.util.Utility"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
String userId = (String) session.getAttribute("userId");

IChuyenTien obj = (IChuyenTien) session.getAttribute("obj");
ResultSet TaikhoanRs = obj.getTaikhoanRs();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Chuyển tiền</title>
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

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$( ".days" ).datepicker({
			changeMonth: true,
			changeYear: true
		});
	});
</script>

<script language="javascript" type="text/javascript">
	//cho phép nhập số từ 0->9 và dấu , .
	function keypress(e) {
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode;
		else
			keypressed = e.which;
		
		if (keypressed < 13 || (keypressed > 13 && keypressed < 44) || keypressed == 45 || keypressed == 47 || keypressed > 57) {
			return false;
		}
		return true;
	}
	
	function save() {
		if (document.getElementById("noidung").value.trim() == "") {
			document.getElementById("dataerror").value = "Bạn chưa nhập nội dung.";
			return false;
		}
		
		if (document.getElementById("taikhoanchuyenId").value == "") {
			document.getElementById("dataerror").value = "Bạn chưa chọn tài khoản chuyển.";
			return false;
		}
		
		if (document.getElementById("sotienchuyen").value.trim() == "") {
			document.getElementById("dataerror").value = "Bạn chưa nhập số tiền chuyển.";
			return false;
		}
		
		if (document.getElementById("taikhoannhanId").value == "") {
			document.getElementById("dataerror").value = "Bạn chưa chọn tài khoản nhận.";
			return false;
		}
		
		if (<%=obj.getDonvinhan().length() %> > 0) {
			if (document.getElementById("sotiennhan").value.trim() == "") {
				document.getElementById("dataerror").value = "Bạn chưa nhập số tiền nhận.";
				return false;
			}
		}
		
		document.forms["FormCt"].action.value = "save";
		document.forms["FormCt"].submit();
	}
	
	function submitform() {
		document.forms["FormCt"].submit();
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="FormCt" method="post" action="/QUANLYCANHAN/ChuyenTienUpdateSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="id" value="<%=obj.getID() %>">
		<input type="hidden" name="action" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
							<%if (obj.getID().length() > 0) {%>
								<td align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tài khoản > Chuyển tiền > Cập nhật</td>
							<%} else { %>
								<td align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tài khoản > Chuyển tiền > Tạo mới</td>
							<%} %>
							<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;&nbsp;</td>
						</tr>
					</table>
					
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="tbdarkrow">
							<td width="30" align="left">
								<a href="/QUANLYCANHAN/ChuyenTienSvl?userId=<%=userId %>">
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
									<legend class="legendtitle">Chuyển tiền</legend>
									<table border="0" width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<td width="20%" class="plainlabel">Ngày <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<input type="text" class="days" name="ngay" value="<%=obj.getNgay() %>" readonly="readonly">
											</td>
											
											<td width="20%" class="plainlabel">Nội dung <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<input type="text" name="noidung" id="noidung" value="<%=obj.getNoidung() %>">
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Tài khoản chuyển <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<select name="taikhoanchuyenId" id="taikhoanchuyenId" class="select2" style="width: 200px" onchange="submitform();">
													<option value=""></option>
													<%if(TaikhoanRs != null){ %>
														<%while(TaikhoanRs.next()){ %>
															<%if(obj.getTaikhoanchuyenId().equals(TaikhoanRs.getString("id"))){ %>
																<option value="<%=TaikhoanRs.getString("id") %>" selected="selected"><%=TaikhoanRs.getString("ten") %></option>
															<%} else { %>
																<option value="<%=TaikhoanRs.getString("id") %>" ><%=TaikhoanRs.getString("ten") %></option>
															<%} %>
														<%} %>
													<%} %>
												</select>
											</td>
											
											<td class="plainlabel">Số tiền chuyển <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<input type="text" style="text-align: right;" name="sotienchuyen" id="sotienchuyen" value="<%=obj.getSotienchuyen() %>" onkeypress="return keypress(event);">
												&nbsp;<%=obj.getDonvichuyen() %>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Tài khoản nhận <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<select name="taikhoannhanId" id="taikhoannhanId" class="select2" style="width: 200px" onchange="submitform();">
													<option value=""></option>
													<%TaikhoanRs.beforeFirst(); %>
													<%if(TaikhoanRs != null){ %>
														<%while(TaikhoanRs.next()){ %>
															<%if(obj.getTaikhoannhanId().equals(TaikhoanRs.getString("id"))){ %>
																<option value="<%=TaikhoanRs.getString("id") %>" selected="selected"><%=TaikhoanRs.getString("ten") %></option>
															<%} else { %>
																<option value="<%=TaikhoanRs.getString("id") %>" ><%=TaikhoanRs.getString("ten") %></option>
															<%} %>
														<%} %>
													<%} %>
												</select>
											</td>
											
											<%if(obj.getDonvinhan().length() > 0){ %>
												<td class="plainlabel">Số tiền nhận <FONT class="erroralert">*</FONT></td>
												<td class="plainlabel">
													<input type="text" style="text-align: right;" name="sotiennhan" id="sotiennhan" value="<%=obj.getSotiennhan() %>" onkeypress="return keypress(event);">
													&nbsp;<%=obj.getDonvinhan() %>
												</td>
											<%} else { %>
												<td class="plainlabel" colspan="2"></td>
											<%} %>
										</tr>
										
										<tr>
											<td class="plainlabel">Tính phí chuyển cho tài khoản</td>
											<td class="plainlabel">
												<select name="tkphi" class="select2" style="width: 200px;">
													<option value="0" selected="selected"></option>
													<%if(obj.getTkphi().equals("1")){ %>
														<option value="1" selected="selected">Tài khoản chuyển</option>
														<option value="2">Tài khoản nhận</option>
													<%} else if(obj.getTkphi().equals("2")) { %>
														<option value="1">Tài khoản chuyển</option>
														<option value="2" selected="selected">Tài khoản nhận</option>
													<%} else { %>
														<option value="1">Tài khoản chuyển</option>
														<option value="2">Tài khoản nhận</option>
													<%} %>
												</select>
											</td>
											
											<td class="plainlabel">Phí</td>
											<td class="plainlabel">
												<input type="text" style="text-align: right;" name="phi" value="<%=obj.getPhi() %>" onkeypress="return keypress(event);">
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