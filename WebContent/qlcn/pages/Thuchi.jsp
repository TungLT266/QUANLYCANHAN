<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="qlcn.pages.thuchi.beans.IThuChiList"%>
<%@page import="qlcn.center.util.Utility"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
String userId = (String) session.getAttribute("userId");

IThuChiList obj = (IThuChiList) session.getAttribute("obj");
ResultSet ThuchiRs = obj.getThuchiRs();
ResultSet NoidungthuchiRs = obj.getNoidungthuchiRs();
ResultSet TaikhoanRs = obj.getTaikhoanRs();

NumberFormat formatter = new DecimalFormat("#,###,###.##");
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
	function clearform() { 
	    document.forms['MainForm'].id.value = "";
	    document.forms['MainForm'].tungay.value = "";
	    document.forms['MainForm'].denngay.value = "";
	    document.forms['MainForm'].taikhoan.value = "";
	    document.forms['MainForm'].sotientu.value = "";
	    document.forms['MainForm'].sotienden.value = "";
	    document.forms['MainForm'].loai.value = "";
	    document.forms['MainForm'].noidungthuchiId.value = "";
	    document.forms['MainForm'].noidung.value = "";
	    document.forms['MainForm'].trangthai.value = "";
	    document.forms['MainForm'].action.value = 'search';
		document.forms['MainForm'].submit();
	}
	
	function search() {
		document.forms['MainForm'].action.value = 'search';
		document.forms['MainForm'].submit();
	}
	
	function deleterow(id) {
		if(!confirm('Bạn thật sự muốn xóa?')) return false;
		document.forms['MainForm'].idrow.value = id;
		document.forms['MainForm'].action.value = 'delete';
		document.forms['MainForm'].submit();
	}
	
	function chot(id) {
		document.forms['MainForm'].idrow.value = id;
		document.forms['MainForm'].action.value = 'chot';
		document.forms['MainForm'].submit();
	}
	
	function unchot(id) {
		if(!confirm('Bạn thật sự muốn bỏ chốt?')) return false;
		document.forms['MainForm'].idrow.value = id;
		document.forms['MainForm'].action.value = 'unchot';
		document.forms['MainForm'].submit();
	}
	
	function newform() {
		document.forms['MainForm'].action.value = 'new';
		document.forms['MainForm'].submit();
	}
	
	function deleteDB() {
		var pin = prompt("Please enter your PIN", "");
		if(pin == null) {
			return false;
		}
		
		document.forms['MainForm'].pinUser.value = pin;
		document.forms['MainForm'].action.value = 'deletedb';
		document.forms['MainForm'].submit();
	}

	//cho phép nhập phím enter, 0->9
	function keypress(e) {
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode;
		else
			keypressed = e.which;
		
		if (keypressed == 13 || (keypressed >= 48 && keypressed <= 57)) {
			return true;
		}
		return false;
	}
	
	// cho phép nhập phím enter, dấu chấm, 0->9
	function keypress2(e) {
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
	function DinhDangTien2(id){
		var num = document.getElementById(id).value;
		num = num.replace(/\,/g,''); // xóa dấu phẩy
		
		var sole = '';
		if(num.indexOf(".") >= 0){
			sole = num.substring(num.indexOf('.')); // lấy từ dấu chấm
			num = num.substring(0, num.indexOf('.')); // lấy số trước dấu chấm
			
			if(num == ''){
				num = "0";
			}
			
			if(isNaN(num))
				num = "0";
		}
		
		// Định dạng thêm dấu phẩy
		//begin{
		if(num.length > 3){
			num = Math.floor(num*100);
			num = Math.floor(num/100).toString();
			for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++){
				num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));
			}
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
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="MainForm" method="post" action="/QUANLYCANHAN/ThuChiSvl">
		<input type="hidden" name="userId" value="<%=userId %>">
		<input type="hidden" name="idrow" value="">
		<input type="hidden" name="action" value="">
		<input type="hidden" name="pinUser" value="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
							<td align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý thu chi > Thu/Chi</td>
							<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;&nbsp;</td>
						</tr>
					</table>
					
					<table width="100%" cellpadding="0" cellspacing="1">
						<%if(obj.getMsg().trim().length() > 0){ %>
							<tr>
								<td align="left" class="legendtitle">
									<fieldset>
										<legend class="legendtitle">Thông báo </legend>
										<textarea name="dataerror" id="dataerror" readonly="readonly" rows="1" style="width: 99%"><%=obj.getMsg()%></textarea>
									</fieldset>
								</td>
							</tr>
						<%} %>
						
						<tr>
							<td>
								<fieldset>
									<legend class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</legend>
									<table width="100%" cellpadding="6" cellspacing="0">
										<tr>
											<td width="10%" class="plainlabel">Từ ngày</td>
											<td class="plainlabel" width="30%">
												<input type="text" name="tungay" class="days" value="<%=obj.getTungay() %>" onchange="search();">
											</td>
											
											<td width="10%" class="plainlabel">Đến ngày</td>
											<td class="plainlabel">
												<input type="text" name="denngay" class="days" value="<%=obj.getDenngay() %>" onchange="search();">
											</td>
										</tr>
									
										<tr>
											<td class="plainlabel">ID</td>
											<td class="plainlabel">
												<input type="text" name="id" value="<%=obj.getID() %>" onchange="search();">
											</td>
											
											<td class="plainlabel">Nội dung</td>
											<td class="plainlabel">
												<input type="text" name="noidung" value="<%=obj.getNoidung() %>" onchange="search();">
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Tài khoản</td>
											<td class="plainlabel">
												<select name="taikhoan" class="select2" style="width: 200px" onchange="search();">
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
											
											<td class="plainlabel">Số tiền</td>
											<td class="plainlabel">
												<input type="text" id="sotientu" name="sotientu" value="<%=obj.getSotientu() %>" onchange="search();" onkeypress="return keypress2(event);" onkeyup="DinhDangTien2('sotientu')" style="width: 90px; text-align: right;">&nbsp;&nbsp;-&nbsp;
												<input type="text" id="sotienden" name="sotienden" value="<%=obj.getSotienden() %>" onchange="search();" onkeypress="return keypress2(event);" onkeyup="DinhDangTien2('sotienden')" style="width: 90px; text-align: right;">
											</td>
										</tr>
										
										<tr>
											<td class="plainlabel">Loại</td>
											<td class="plainlabel">
												<select name="loai" class="select2" style="width: 200px;" onchange="search();">
													<option value=""></option>
													<%if(obj.getLoai().equals("1")){ %>
														<option value="1" selected="selected">Thu</option>
														<option value="2">Chi</option>
													<%} else if(obj.getLoai().equals("2")){ %>
														<option value="1">Thu</option>
														<option value="2" selected="selected">Chi</option>
													<%} else { %>
														<option value="1">Thu</option>
														<option value="2">Chi</option>
													<%} %>
												</select>
											</td>
											
											<td class="plainlabel">Nội dung thu chi</td>
											<td class="plainlabel">
												<select name="noidungthuchiId" class="select2" style="width: 200px" onchange="search();">
													<option value=""></option>
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
											<td class="plainlabel">Trạng thái</td>
											<td class="plainlabel">
												<select name="trangthai" class="select2" style="width: 200px;" onchange="search();">
													<option value=""></option>
													<%if(obj.getTrangthai().equals("0")){ %>
														<option value="0" selected="selected">Chưa chốt</option>
														<option value="1">Đã thực hiện</option>
														<option value="2">Đã xóa</option>
													<%} else if(obj.getTrangthai().equals("1")){ %>
														<option value="0">Chưa chốt</option>
														<option value="1" selected="selected">Đã thực hiện</option>
														<option value="2">Đã xóa</option>
													<%} else if(obj.getTrangthai().equals("2")){ %>
														<option value="0">Chưa chốt</option>
														<option value="1">Đã thực hiện</option>
														<option value="2" selected="selected">Đã xóa</option>
													<%} else { %>
														<option value="0">Chưa chốt</option>
														<option value="1">Đã thực hiện</option>
														<option value="2">Đã xóa</option>
													<%} %>
												</select>
											</td>
											
											<td class="plainlabel">Số Items</td>
											<td class="plainlabel">
												<input type="text" style="text-align: right;" name="soitems" value="<%=obj.getSoItems() %>" onchange="search();" onkeypress="return keypress(event);">
											</td>
										</tr>

										<tr class="plainlabel">
											<td colspan="4">
												&nbsp;&nbsp;
												<a class="button2" href="javascript:clearform()">
													<img style="top: -4px;" src="../images/Clear32.png" alt="">Clear
												</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<a class="button2" href="javascript:deleteDB()">
													<img style="top: -4px;" src="../images/resetdb32.png" alt="">Refresh Database
												</a>&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>

						<tr>
							<td width="100%">
								<fieldset>
									<legend class="legendtitle">&nbsp;Thu/Chi &nbsp;&nbsp;
										<a class="button3" href="javascript:newform()">
											<img style="top: -4px;" src="../images/New.png" alt="">New
										</a>
									</legend>

									<table class="" width="100%">
										<tr>
											<td width="98%">
												<table width="100%" border="0" cellspacing="1" cellpadding="4">
													<tr class="tbheader">
														<th width="6%">ID</th>
														<th width="7%">Ngày</th>
														<th width="14%">Tài khoản</th>
														<th width="10%">Số tiền</th>
														<th width="5%">Loại</th>
														<th width="14%">Nội dung thu chi</th>
														<th width="12%">Nội dung</th>
														<th width="8%">Trạng thái</th>
														<th width="7%">Ngày tạo</th>
														<th width="7%">Ngày sửa</th>
														<th width="10%">Tác vụ</th>
													</tr>
													<%
													int m = 0;
			                                    	String lightrow = "tblightrow";
			                                    	String darkrow = "tbdarkrow";
			                                    	%>
			                                    	<%if(ThuchiRs != null){ %>
				                                    	<%while(ThuchiRs.next()){ %>
				                                    		<%String tt = ThuchiRs.getString("trangthai").trim(); %>
				                                    	
				                                        	<%if(m % 2 != 0) { %>
																<tr class=<%=lightrow %>>
															<%} else { %>
																<tr class=<%=darkrow %>>
															<%} %>
															
															<td align="center"><%=ThuchiRs.getString("id") %></td>
															<td align="center"><%=ThuchiRs.getString("ngay") %></td>
															<td><%=ThuchiRs.getString("tentk") %></td>
															<td align="center"><%=formatter.format(Double.parseDouble(ThuchiRs.getString("sotien"))) + " " + ThuchiRs.getString("donvi") %></td>
															<td align="center"><%=ThuchiRs.getString("loai") %></td>
															<td><%=ThuchiRs.getString("tenndtc") %></td>
															<td><%=ThuchiRs.getString("diengiai") %></td>
															
															<%if(tt.equals("0")) { %>
																<td align="center">Chưa chốt</td>
															<%} else if(tt.equals("1")) { %>
																<td align="center">Đã thực hiện</td>
															<%} else { %>
																<td align="center" style="color: red;">Đã xóa</td>
															<%} %>

															<td align="center"><%=ThuchiRs.getString("NGAYTAO") %></td>
															<td align="center"><%=ThuchiRs.getString("NGAYSUA") %></td>
															<td align="center">
																<% if(tt.equals("0")){ %>
																	<a href="javascript:chot('<%=ThuchiRs.getString("ID") %>')">
																		<img title="Chốt" src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border=0>
																	</a>
																	<a href="/QUANLYCANHAN/ThuChiUpdateSvl?userId=<%=userId %>&action=update&id=<%=ThuchiRs.getString("ID") %>">
																		<img title="Edit" src="../images/Edit20.png" alt="Edit" width="20" height="20" longdesc="Edit" border=0>
																	</a>
																	<a href="javascript:deleterow('<%=ThuchiRs.getString("ID") %>')">
																		<img title="Delete" src="../images/Delete20.png" alt="Delete" width="20" height="20" longdesc="Xoa" border=0>
																	</a>
																<%} else if(tt.equals("1")){ %>
																	<a href="javascript:unchot('<%=ThuchiRs.getString("ID") %>')">
																		<img title="Bỏ chốt" src="../images/unChot.png" alt="Bo chot" width="20" height="20" longdesc="Bo chot" border=0>
																	</a>
																<%} %>
																<a href="/QUANLYCANHAN/ThuChiUpdateSvl?userId=<%=userId %>&action=display&id=<%=ThuchiRs.getString("ID") %>">
																	<img title="Hiển thị" src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0>
																</a>
															</td>
															</tr>
															<%m++; %>
														<%} %>
			                                    	<%} %>
													<tr class="tbfooter">
														<td align="center" valign="middle" colspan="11" class="tbfooter">
															<% obj.setNextSplittings(); %>
															<script type="text/javascript" src="../scripts/phanTrang.js"></script>
															<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
															<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
															
															<%if(obj.getNxtApprSplitting() > 1) { %>
																<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
															<%} else { %>
																<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
															<%} %>
															<% if(obj.getNxtApprSplitting() > 1){ %>
																<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
															<%}else{ %>
																<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
															<%} %>
															
															<%int[] listPage = obj.getNextSplittings(); %>
															<%for(int i = 0; i < listPage.length; i++) { %>
																<%if(listPage[i] == obj.getNxtApprSplitting()){ %>
																	<a style="color:white;"><%=listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
																<%} else { %>
																	<a href="javascript:View(document.forms[0].name, <%=listPage[i] %>, 'view')"><%=listPage[i] %></a>
																<%} %>
																<input type="hidden" name="list" value="<%=listPage[i] %>" />&nbsp;
															<%} %>
															
															<%if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
																&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
															<%} else { %>
																&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
															<%} %>
															<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
																<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
															<%} else { %>
																<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
															<%} %>
														</td>
													</tr>
												</table>
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
	if (ThuchiRs != null)
		ThuchiRs.close();
	if (NoidungthuchiRs != null)
		NoidungthuchiRs.close();
	if (TaikhoanRs != null)
		TaikhoanRs.close();
	if(obj != null)
		obj.DBClose();
	session.removeAttribute("obj");
} catch (Exception e) {}
%>