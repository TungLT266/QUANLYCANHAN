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

<link href="../css/select2.css" rel="stylesheet"/>
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function() {
		$(".select2").select2();
	});
</script>

<script language="javascript" type="text/javascript">
	function save() {
		if (document.getElementById("taikhoan").value.trim().length <= 0) {
			document.getElementById("dataerror").value = "Bạn chưa chọn tài khoản.";
			return false;
		}

		document.forms["FormTktt"].action.value = "save";
		document.forms["FormTktt"].submit();
	}
	
	function isChange(s) {
		document.getElementById(s).style.display = "";
		document.getElementById(s+"change").style.display = "none";
		document.getElementById("ischange"+s).value = "1";
	}
	
	/* function showHideByLoai() {
		if(document.getElementById("loai").value == '1'){
			document.getElementById("loaitienmat1").style.display = "";
			document.getElementById("loaittt1").style.display = "none";
			document.getElementById("loaittt2").style.display = "none";
			document.getElementById("loaittt3").style.display = "none";
			document.getElementById("loaittt4").style.display = "none";
		} else {
			document.getElementById("loaitienmat1").style.display = "none";
			document.getElementById("loaittt1").style.display = "";
			document.getElementById("loaittt2").style.display = "";
			document.getElementById("loaittt3").style.display = "";
			document.getElementById("loaittt4").style.display = "";
		}
	} */
</script>
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
							<%if (obj.getID().length() > 0) {%>
								<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Tài khoản thanh toán > Cập nhật</td>
							<%} else { %>
								<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Tài khoản thanh toán > Tạo mới</td>
							<%} %>
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
									<legend class="legendtitle">Tài khoản thanh toán</legend>
									<table border="0" width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<td width="15%" class="plainlabel">Tài khoản <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<%if(obj.getID().length() > 0){ %>
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
												<%} else { %>
													<select id="taikhoan" name="taikhoan" class="select2" style="width: 200px">
														<option value=""></option>
														<%if(TaikhoanRs != null){ %>
															<%while(TaikhoanRs.next()){ %>
																<%if(obj.getTaikhoan().equals(TaikhoanRs.getString("id"))){ %>
																	<option value="<%=TaikhoanRs.getString("id") %>" selected><%=TaikhoanRs.getString("ten") %></option>
																<%} else { %>
																	<option value="<%=TaikhoanRs.getString("id") %>" ><%=TaikhoanRs.getString("ten") %></option>
																<%} %>
															<%} %>
														<%} %>
													</select>
												<%} %>
											</td>
											
											<td width="15%" class="plainlabel">Loại thẻ</td>
											<td class="plainlabel">
												<select id="loaithe" name="loaithe" class="select2" style="width: 200px;">
													<%if(obj.getLoaithe().equals("1")) { %>
														<option value="0"></option>
														<option value="1" selected="selected">ATM</option>
														<option value="2">VISA</option>
														<option value="3">MASTERCARD</option>
														<option value="4">Tín dụng</option>
													<%} else if(obj.getLoaithe().equals("2")) { %>
														<option value="0"></option>
														<option value="1">ATM</option>
														<option value="2" selected="selected">VISA</option>
														<option value="3">MASTERCARD</option>
														<option value="4">Tín dụng</option>
													<%} else if(obj.getLoaithe().equals("3")) { %>
														<option value="0"></option>
														<option value="1">ATM</option>
														<option value="2">VISA</option>
														<option value="3" selected="selected">MASTERCARD</option>
														<option value="4">Tín dụng</option>
													<%} else if(obj.getLoaithe().equals("4")) { %>
														<option value="0"></option>
														<option value="1">ATM</option>
														<option value="2">VISA</option>
														<option value="3">MASTERCARD</option>
														<option value="4" selected="selected">Tín dụng</option>
													<%} else { %>
														<option value="0"></option>
														<option value="1">ATM</option>
														<option value="2">VISA</option>
														<option value="3">MASTERCARD</option>
														<option value="4">Tín dụng</option>
													<%} %>
												</select>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Số thẻ</td>
											<td class="plainlabel">
												<input type="text" name="sothe" id="sothe" value="<%=obj.getSothe() %>">
											</td>
											
											<td class="plainlabel">Tên chủ thẻ</td>
											<td class="plainlabel">
												<input type="text" name="tenchuthe" id="tenchuthe" value="<%=obj.getTenchuthe() %>">
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Thời gian hiệu lực</td>
											<td class="plainlabel">
												<select name="thanghieuluc" class="select2" style="width: 78px">
													<%for(int i = 1; i <= 12; i++) { %>
														<%if(obj.getThanghieuluc().equals(i+"")){ %>
															<option value="<%=i %>" selected="selected"><%=i %></option>
														<%} else { %>
															<option value="<%=i %>"><%=i %></option>
														<%} %>
													<%} %>
												</select>
												<%int yearCurrent = Calendar.getInstance().get(Calendar.YEAR); %>
												<select name="namhieuluc" class="select2" style="width: 120px">
													<%for(int i = yearCurrent-50; i <= yearCurrent+50; i++) { %>
														<%if(obj.getNamhieuluc().equals(i+"")){ %>
															<option value="<%=i %>" selected="selected"><%=i %></option>
														<%} else { %>
															<option value="<%=i %>"><%=i %></option>
														<%} %>
													<%} %>
												</select>
											</td>
											
											<td class="plainlabel">Thời gian hết hạn</td>
											<td class="plainlabel">
												<select name="thanghethan" class="select2" style="width: 78px">
													<%for(int i = 1; i <= 12; i++) { %>
														<%if(obj.getThanghethan().equals(i+"")){ %>
															<option value="<%=i %>" selected="selected"><%=i %></option>
														<%} else { %>
															<option value="<%=i %>"><%=i %></option>
														<%} %>
													<%} %>
												</select>
												<select name="namhethan" class="select2" style="width: 120px">
													<%for(int i = yearCurrent-50; i <= yearCurrent+50; i++) { %>
														<%if(obj.getNamhethan().equals(i+"")){ %>
															<option value="<%=i %>" selected="selected"><%=i %></option>
														<%} else { %>
															<option value="<%=i %>"><%=i %></option>
														<%} %>
													<%} %>
												</select>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Mã PIN</td>
											<td class="plainlabel">
												<input type="hidden" id="ischangemapin" name="ischangemapin" value="<%=obj.getIsChangeMapin() %>">
												<input type="text" id="mapin" name="mapin" value="<%=obj.getMapin() %>" <%=obj.getID().length() > 0 && !obj.getIsChangeMapin().equals("1") ? "style=\"display: none;\"" : "" %>>
												<input type="button" id="mapinchange" value="Thay đổi" <%=obj.getID().length() > 0 && !obj.getIsChangeMapin().equals("1") ? "" : "style=\"display: none;\"" %> onclick="isChange('mapin');" style="width: 200px; border-radius: 5px; padding: 5px 0px;">
											</td>
										
											<td class="plainlabel">Chữ ký</td>
											<td class="plainlabel">
												<input type="hidden" id="ischangechuky" name="ischangechuky" value="<%=obj.getIsChangeChuky() %>">
												<input type="text" id="chuky" name="chuky" value="<%=obj.getChuky() %>" <%=obj.getID().length() > 0 && !obj.getIsChangeChuky().equals("1") ? "style=\"display: none;\"" : "" %>>
												<input type="button" id="chukychange" value="Thay đổi" <%=obj.getID().length() > 0 && !obj.getIsChangeChuky().equals("1") ? "" : "style=\"display: none;\"" %> onclick="isChange('chuky');" style="width: 200px; border-radius: 5px; padding: 5px 0px;">
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
	if(TaikhoanRs != null)
		TaikhoanRs.close();
	if(obj != null)
		obj.DBClose();
	session.removeAttribute("obj");
} catch (Exception e) {}
%>