<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.vayno.beans.IVayNo"%>
<%@page import="qlcn.center.util.Utility"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
String userId = (String) session.getAttribute("userId");

IVayNo obj = (IVayNo) session.getAttribute("obj");
ResultSet TaikhoanRs = obj.getTaikhoanRs();
ResultSet TaikhoanNhantraRs = obj.getTaikhoanNhantraRs();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Vay/Nợ</title>
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
	//cho phép nhập phím enter, dấu phẩy, dấu chấm, 0->9
	function keypress(e) {
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode;
		else
			keypressed = e.which;
		
		if (keypressed == 13 || keypressed == 44 || keypressed == 46 || (keypressed >= 48 && keypressed <= 57)) {
			return true;
		}
		return false;
	}
	
	function save() {
		if (document.getElementById("sotien").value.trim() == "") {
			document.getElementById("dataerror").value = "Bạn chưa nhập số tiền.";
			return false;
		}
		
		if (document.getElementById("taikhoanId").value == "") {
			document.getElementById("dataerror").value = "Bạn chưa chọn tài khoản.";
			return false;
		}
		
		if (document.getElementById("nguoivayno").value.trim() == "") {
			document.getElementById("dataerror").value = "Bạn chưa nhập người vay/nợ.";
			return false;
		}

		document.forms["FormVn"].action.value = "save";
		document.forms["FormVn"].submit();
	}
	
	function submitform() {
		document.forms["FormVn"].submit();
	}
	
	function clearDay(s){
		document.getElementById(s).value = "";
	}
	
	function nhantra() {
		if (document.getElementById("taikhoannhantra").value == "") {
			document.getElementById("dataerror").value = "Bạn chưa chọn tài khoản thực hiện.";
			return false;
		}

		document.forms["FormVn"].action.value = "nhantra";
		document.forms["FormVn"].submit();
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="FormVn" method="post" action="/QUANLYCANHAN/VayNoUpdateSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="id" value="<%=obj.getID() %>">
		<input type="hidden" name="action" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
							<%if (obj.getID().length() > 0) {%>
								<td align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý thu chi > Vay/Nợ > Cập nhật</td>
							<%} else { %>
								<td align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý thu chi > Vay/Nợ > Tạo mới</td>
							<%} %>
							<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;&nbsp;</td>
						</tr>
					</table>
					
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="tbdarkrow">
							<td width="30" align="left">
								<a href="/QUANLYCANHAN/VayNoSvl?userId=<%=userId %>">
									<img src="../images/Back30.png" alt="Back" title="Back" border="1" longdesc="Quay ve" style="border-style: outset">
								</a>
							</td>
							<td width="2" align="left"></td>
							<td width="30" align="left">
								<div id="btnSave">
									<A href="javascript:<%=obj.getAction().equals("nhantra") ? "nhantra" : "save" %>()">
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
									<legend class="legendtitle">Vay/Nợ</legend>
									<table border="0" width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<td width="15%" class="plainlabel">Ngày <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<input type="text" <%=obj.getAction().equals("nhantra") ? "" : "class=\"days\"" %> name="ngay" value="<%=obj.getNgay() %>" readonly="readonly">
											</td>
											
											<td width="15%" class="plainlabel">Số tiền <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<input type="text" style="text-align: right;" name="sotien" id="sotien" value="<%=obj.getSotien() %>" onkeypress="return keypress(event);" <%=obj.getAction().equals("nhantra") ? "readonly" : "" %>>
												&nbsp;<%=obj.getDonvi() %>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Loại <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<%if(obj.getAction().equals("nhantra")){ %>
													<select name="loai" style="width: 200px;">
														<%if(obj.getLoai().equals("1")){ %>
															<option value="1" selected="selected">Vay</option>
															<option value="2" disabled="disabled">Cho vay</option>
														<%} else { %>
															<option value="1" disabled="disabled">Vay</option>
															<option value="2" selected="selected">Cho vay</option>
														<%} %>
													</select>
												<%} else { %>
													<select name="loai" class="select2" style="width: 200px;">
														<%if(obj.getLoai().equals("1")){ %>
															<option value="1" selected="selected">Vay</option>
															<option value="2">Cho vay</option>
														<%} else { %>
															<option value="1">Vay</option>
															<option value="2" selected="selected">Cho vay</option>
														<%} %>
													</select>
												<%} %>
											</td>
											
											<td class="plainlabel">Tài khoản <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<%if(obj.getAction().equals("nhantra")){ %>
													<select id="taikhoanId" name="taikhoanId" style="width: 200px">
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
												<%} else { %>
													<select id="taikhoanId" name="taikhoanId" class="select2" style="width: 200px" onchange="submitform();">
														<option value=""></option>
														<%if(TaikhoanRs != null){ %>
															<%while(TaikhoanRs.next()){ %>
																<%if(obj.getTaikhoanId().equals(TaikhoanRs.getString("id"))){ %>
																	<option value="<%=TaikhoanRs.getString("id") %>" selected="selected"><%=TaikhoanRs.getString("ten") %></option>
																<%} else { %>
																	<option value="<%=TaikhoanRs.getString("id") %>"><%=TaikhoanRs.getString("ten") %></option>
																<%} %>
															<%} %>
														<%} %>
													</select>
												<%} %>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Người Vay/Nợ <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<input type="text" id="nguoivayno" name="nguoivayno" value="<%=obj.getNguoivayno() %>" <%=obj.getAction().equals("nhantra") ? "readonly" : "" %>>
											</td>
											
											<td class="plainlabel">Nội dung</td>
											<td class="plainlabel">
												<input type="text" name="noidung" value="<%=obj.getNoidung() %>" <%=obj.getAction().equals("nhantra") ? "readonly" : "" %>>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Ghi chú</td>
											<td class="plainlabel" colspan="3">
												<textarea name="ghichu" rows="5" style="width: 80%; color: black;" <%=obj.getAction().equals("nhantra") ? "readonly" : "" %>><%=obj.getGhichu() %></textarea>
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
						
						<tr>
							<td>
								<fieldset>
									<legend class="legendtitle">Trả</legend>
									<table border="0" width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<td class="plainlabel" width="15%">Ngày trả <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<input type="text" class="days" id="ngaytra" name="ngaytra" value="<%=obj.getNgaytra() %>" readonly="readonly">
												<%if(!obj.getAction().equals("nhantra")){ %>
													<a href="javascript:clearDay('ngaytra')">
														<img title="Clear" src="../images/Delete.png" alt="Clear" width="20" height="20" longdesc="Clear" border=0>
													</a>
												<%} %>
											</td>
											
											<td class="plainlabel">Tài khoản thực hiện <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<select id="taikhoannhantra" name="taikhoannhantra" class="select2" style="width: 200px">
													<option value="0"></option>
													<%if(TaikhoanNhantraRs != null){ %>
														<%while(TaikhoanNhantraRs.next()){ %>
															<%if(obj.getTaikhoannhantra() != null && obj.getTaikhoannhantra().equals(TaikhoanNhantraRs.getString("id"))){ %>
																<option value="<%=TaikhoanNhantraRs.getString("id") %>" selected="selected"><%=TaikhoanNhantraRs.getString("ten") %></option>
															<%} else { %>
																<option value="<%=TaikhoanNhantraRs.getString("id") %>" ><%=TaikhoanNhantraRs.getString("ten") %></option>
															<%} %>
														<%} %>
													<%} %>
												</select>
											</td>
										</tr>
											
										<tr>
											<td class="plainlabel" width="15%">Phí</td>
											<td class="plainlabel">
												<input type="text" style="text-align: right;" name="phi" id="phi" value="<%=obj.getPhi() %>" onkeypress="return keypress(event);">
												&nbsp;<%=obj.getDonvi() %>
											</td>
											
											<td class="plainlabel">Ghi chú</td>
											<td class="plainlabel">
												<input type="text" name="ghichu2" value="<%=obj.getGhichu2() %>">
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
	if(TaikhoanNhantraRs != null)
		TaikhoanNhantraRs.close();
	if(obj != null)
		obj.DBClose();
	session.removeAttribute("obj");
} catch (Exception e) {}
%>