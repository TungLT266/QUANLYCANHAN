<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.nganhangcongty.*"%>
<%@page import="geso.traphaco.erp.beans.nganhangcongty.imp.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IErpNganHangCongTyList nhct = (IErpNganHangCongTyList) session.getAttribute("nhct");
	ResultSet rsCongTy = nhct.getCongTyRs();
	ResultSet rsNganHang = nhct.getNganHangRs();
	ResultSet rsChiNhanh = nhct.getChiNhanhRs();
	ResultSet rsLoaiTien = nhct.getLoaiTienRs();
	ResultSet Rsnhct = nhct.getNganHangCongTy();
	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("ErpNganHangCongTySvl","",userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Ngân hàng công ty</title>
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	function xoaform() {
		document.nhctForm.congty.value = "";
		document.nhctForm.nganhang.value = "";
		document.nhctForm.chinhanh.value = "";
		document.nhctForm.loaitien.value = "";
		document.nhctForm.submit();
	}

	function Submit() {

		document.forms['nhctForm'].submit();
	}

	function newform() {
		document.forms['nhctForm'].action.value = 'New';
		document.forms['nhctForm'].submit();
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="nhctForm" method="post" action="../../ErpNganHangCongTySvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="userTen" value='<%=userTen%>'> <input
			type="hidden" name="action" value='1'>
		<input type="hidden" name="chixem" value='<%= nhct.getChixem() %>'>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Ngân hàng của công ty</td>
										<td align="right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td width="100%" align="center">
											<fieldset>
												<legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
												<table width="100%" cellpadding="6" cellspacing="0">
																									
													<tr>
														<td class="plainlabel">Ngân hàng</td>
														
														 <TD class="plainlabel" ><input type="text" class="txtsearch"  name="nganhang" size="20" value="<%= nhct.getNganHang() %>" onchange="Submit()"> 
														 </TD>	
													</tr>
													
													
													<tr>
														<td class="plainlabel">Chi Nhánh</td>
														
														 <TD class="plainlabel" ><input type="text" class="txtsearch"  name="chinhanh" size="20" value="<%= nhct.getChiNhanh() %>" onchange="Submit()"> 
														 </TD>	
													</tr>
													
													
													
													<tr>
														<td class="plainlabel">Loại tiền</td>
														<TD class="plainlabel"><select name="loaitien" onchange="Submit()">
																<option value=""></option>
																<%if (rsLoaiTien != null)
																while (rsLoaiTien.next()) {
																	if (nhct.getLoaiTien().equals(
																			rsLoaiTien.getString("PK_SEQ"))) {%>
															<option value="<%=rsLoaiTien.getString("PK_SEQ")%>" selected="selected"><%=rsLoaiTien.getString("Ten")%></option>
															<%} else {%>
															<option value="<%=rsLoaiTien.getString("PK_SEQ")%>"><%=rsLoaiTien.getString("Ten")%></option>
															<%}
																}%>
														</select></td>
													</tr>
													<tr>
														<td colspan="2" class="plainlabel">
														<a class="button2" href="javascript:xoaform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
														</a></td>
													</tr>
												</table>
											</fieldset>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="100%">
								<fieldset>
									<legend class="legendtitle">Ngân hàng của công ty&nbsp;&nbsp;&nbsp;
											<%if(quyen[0]!=0 ){ %> 
										<a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
										</a>
										<%} %> 
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" colspan="4">
												<table width="100%" border="0" cellspacing="1" cellpadding="4">
													<tr class="tbheader">
														<!-- <th width="15%" align = "center" >Công ty</th> -->
														<th width="15%" align = "center" >Số tài khoản</th>
														<th width="15%" align = "center" >Ngân hàng</th>
														<th width="15%" align = "center" >Chi nhánh</th>
														<th width="15%" align = "center" >Loại tiền</th>
														<th width="10%" align = "center" >Trạng thái</th>
														<th width="10%" align="center">&nbsp;Tác vụ</th>
													</tr>
													<%int m = 0;
			String lightrow = "tblightrow";
			String darkrow = "tbdarkrow";
			if (Rsnhct != null)
				while (Rsnhct.next()) {
					String trangthai = Rsnhct.getString("TrangThai");
					if (trangthai.equals("0"))
						trangthai = "Ngưng hoạt động";
					else
						trangthai = "Hoạt động";
					if (m % 2 != 0) {%>
													<tr class=<%=lightrow%>>
														<%} else {%>
													
													<tr class=<%=darkrow%>>
														<%}%>
														<%-- <td align="center"><%=Rsnhct.getString("CongTy")%></td> --%>
														<td align="center"><%=Rsnhct.getString("SoTaiKhoan")%></td>
														<td align="center"><%=Rsnhct.getString("NganHang")%></td>
														<td align="center"><%=Rsnhct.getString("ChiNhanh")%></td>
														<td align="center"><%=Rsnhct.getString("LoaiTien")%></td>													
														<td align="center"><%=trangthai%></td>
														<td align="center">
															<table border=0 cellpadding="0" cellspacing="0">
																<tr>
																	<td>
																	<%if(quyen[2]!=0 ){ %> 
																	<a href="../../ErpNganHangCongTyUpdateSvl?userid=<%=userId%>&update=<%=Rsnhct.getString("PK_SEQ")%>"><img
																		title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A>&nbsp; 
																	<%} %>
																	</td>
																	<td>
																		<%if(quyen[1]!=0 ){ %> 
																	<a href="../../ErpNganHangCongTySvl?userid=<%=userId%>&delete=<%=Rsnhct.getString("PK_SEQ")%>"><img
																		title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
																		onclick="if(!confirm('Bạn muốn xóa ngân hàng công ty  này?')) return false;"> </a>&nbsp; 
																	<%} %>
																	</td>
																	<td>
																	<!-- QUYEN VIEW DLN --> 
																	<a href="../../ErpNganHangCongTyUpdateSvl?userid=<%=userId%>&display=<%=Rsnhct.getString("PK_SEQ")%>">
																	<img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A> 
																	<!-- END QUYEN VIEW DLN -->
																	</td>
																</tr>
															</table>
													</tr>
													<%m++;
				}%>
													<tr>
														<td height="26" colspan="11" align="center" class="tbfooter">&nbsp;</td>
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

if (rsCongTy != null) rsCongTy.close();
if (rsNganHang != null) rsNganHang.close();
if (rsLoaiTien != null) rsLoaiTien.close();
if (rsChiNhanh != null) rsChiNhanh.close();
if (Rsnhct != null) Rsnhct.close();
nhct.closeDB();
}%>
