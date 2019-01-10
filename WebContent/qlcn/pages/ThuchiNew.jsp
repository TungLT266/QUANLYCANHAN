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
	//cho phép nhập phím enter, dấu chấm, 0->9
	function keypress(e) {
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode;
		else
			keypressed = e.which;
		
		if (keypressed == 13 || keypressed == 46 || (keypressed >= 48 && keypressed <= 57)) {
			return true;
		}
		return false;
	}
	
	// Hàm định dạng tiền
	function DinhDangTien(id){
		var num = document.getElementById(id).value;
		num = num.replace(/\,/g,''); // xóa dấu phẩy
		
		var sole = '';
		if(num.indexOf(".") >= 0){
			sole = num.substring(num.indexOf('.')); // lấy từ dấu chấm
			num = num.substring(0, num.indexOf('.')); // lấy số trước dấu chấm
			
			if(num == ''){
				num = "0";
			}
		}
		
		if(isNaN(num))
			num = "0";
		
		// Định dạng thêm dấu phẩy
		//begin{
		num = Math.floor(num*100);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++){
			num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));
		}
		//end}
		
		if(sole.length >= 2){
			var phanle = sole.substring(1); // lấy phần sau dấu chấm
			if(phanle.indexOf(".") >= 0){ // nếu có thêm 1 dấu chấm nữa thì bỏ
				phanle = phanle.substring(0, phanle.indexOf('.'));
			}
			if(isNaN(phanle))
				phanle = '';
			
			sole = "." + phanle;
			
			if(sole.length > 3){ // chỉ cho nhập sau dấu chấm tối đa 2 số
				sole = sole.substring(0, 3);
			}
		}
		
		var kq = num + sole;
		
		document.getElementById(id).value = kq;
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

		document.forms["FormTc"].action.value = "save";
		document.forms["FormTc"].submit();
	}
	
	function submitform() {
		document.forms["FormTc"].submit();
	}
</script>
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
							<%if (obj.getID().length() > 0) {%>
								<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Thu/Chi > Cập nhật</td>
							<%} else { %>
								<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Thu/Chi > Tạo mới</td>
							<%} %>
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
									<legend class="legendtitle">Thu/Chi</legend>
									<table border="0" width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<td width="15%" class="plainlabel">Ngày <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<input type="text" class="days" name="ngay" value="<%=obj.getNgay() %>" readonly="readonly">
											</td>
											
											<td class="plainlabel" width="15%">Số tiền <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<input type="text" style="text-align: right;" name="sotien" id="sotien" value="<%=obj.getSotien() %>" onkeypress="return keypress(event);" onkeyup="DinhDangTien('sotien')">
												&nbsp;<%=obj.getDonvi() %>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Loại <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<select name="loai" class="select2" style="width: 200px;" onchange="submitform();">
													<%if(obj.getLoai().equals("2")){ %>
														<option value="1">Thu</option>
														<option value="2" selected="selected">Chi</option>
													<%} else { %>
														<option value="1" selected="selected">Thu</option>
														<option value="2">Chi</option>
													<%} %>
												</select>
											</td>
											
											<td class="plainlabel">Nội dung thu chi</td>
											<td class="plainlabel">
												<select name="noidungthuchiId" class="select2" style="width: 200px">
													<option value="0"></option>
													<%if(NoidungthuchiRs != null){ %>
														<%while(NoidungthuchiRs.next()){ %>
															<%if(obj.getNoidungthuchiId().equals(NoidungthuchiRs.getString("id"))){ %>
																<option value="<%=NoidungthuchiRs.getString("id") %>" selected="selected"><%=NoidungthuchiRs.getString("ten") %></option>
															<%} else { %>
																<option value="<%=NoidungthuchiRs.getString("id") %>" ><%=NoidungthuchiRs.getString("ten") %></option>
															<%} %>
														<%} %>
													<%} %>
												</select>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Tài khoản <FONT class="erroralert">*</FONT></td>
											<td class="plainlabel">
												<select name="taikhoanId" id="taikhoanId" class="select2" style="width: 200px" onchange="submitform();">
													<option value=""></option>
													<%if(TaikhoanRs != null){ %>
														<%while(TaikhoanRs.next()){ %>
															<%if(obj.getTaikhoanId().equals(TaikhoanRs.getString("id"))){ %>
																<option value="<%=TaikhoanRs.getString("id") %>" selected="selected"><%=TaikhoanRs.getString("ten") %></option>
															<%} else { %>
																<option value="<%=TaikhoanRs.getString("id") %>" ><%=TaikhoanRs.getString("ten") %></option>
															<%} %>
														<%} %>
													<%} %>
												</select>
											</td>
											
											<td class="plainlabel">Tài khoản thanh toán</td>
											<td class="plainlabel">
												<select name="taikhoanthanhtoanId" class="select2" style="width: 200px">
													<option value="0"></option>
													<%if(TaikhoanthanhtoanRs != null){ %>
														<%while(TaikhoanthanhtoanRs.next()){ %>
															<%if(obj.getTaikhoanthanhtoanId().equals(TaikhoanthanhtoanRs.getString("id"))){ %>
																<option value="<%=TaikhoanthanhtoanRs.getString("id") %>" selected="selected"><%=TaikhoanthanhtoanRs.getString("ten") %></option>
															<%} else { %>
																<option value="<%=TaikhoanthanhtoanRs.getString("id") %>" ><%=TaikhoanthanhtoanRs.getString("ten") %></option>
															<%} %>
														<%} %>
													<%} %>
												</select>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel" width="15%">Nội dung</td>
											<td class="plainlabel">
												<input type="text" name="diengiai" id="diengiai" value="<%=obj.getDiengiai() %>">
											</td>
											
											<td class="plainlabel">Phí</td>
											<td class="plainlabel">
												<input type="text" style="text-align: right;" id="phi" name="phi" value="<%=obj.getPhi() %>" onkeypress="return keypress(event);" onkeyup="DinhDangTien('phi')">
												&nbsp;<%=obj.getDonvi() %>
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Ghi chú</td>
											<td class="plainlabel" colspan="3">
												<textarea name="ghichuphi" rows="5" style="width: 80%; color: black;"><%=obj.getGhichuphi() %></textarea>
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