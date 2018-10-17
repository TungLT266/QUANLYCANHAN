<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="geso.traphaco.erp.beans.nganhangcongty.*"%>
<%@ page import="geso.traphaco.erp.beans.nganhangcongty.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IErpNganHangCongTy nhctBean = (ErpNganHangCongTy) session
			.getAttribute("nhctBean");
	ResultSet rsCongTy = nhctBean.getCongTyRs();
	ResultSet rsNganHang = nhctBean.getNganHangRs();
	ResultSet rsLoaiTien = nhctBean.getLoaiTienRs();
	ResultSet rsChiNhanh = nhctBean.getChiNhanhRs();
	ResultSet rsTaiKhoan = nhctBean.getTaiKhoanRs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Tạo tài khoản ngân hàng công ty</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script>
	function goBack() {
	    window.history.back();
	}
	</script>
	
	
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="nhctForm" method="post" action="../../ErpNganHangCongTyUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>' /> <input type="hidden" name="action" value='1' />
		<input type="hidden" name="id" value="<%=nhctBean.getId() %>"/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Ngân hàng của công ty > Hiển thị</TD>
										<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%> &nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="3" cellspacing="0">
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr class="tbdarkrow">
										<td width="30" align="left"><a href="javascript:goBack();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset">
										</a></td>
										<td>&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table width="100%" cellspacing="0" cellpadding="6">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<fieldset>
									<LEGEND class="legendtitle">Ngân hàng của công ty</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">										
										<tr>
											<td class="plainlabel">Chủ tài khoản</td>
											<td class="plainlabel" width = "80%" ><input type="text" readonly style="width:400px" name="chutaikhoan" id="chutaikhoan" value="<%=nhctBean.getChuTaiKhoan()%>" /></td>
										</tr>
										<tr>
											<td class="plainlabel">Số tài khoản</td>
											<td class="plainlabel"><input type="text" readonly style="width:400px" name="sotaikhoan" id="sotaikhoan" value="<%=nhctBean.getSoTaiKhoan()%>" /></td>
										</tr>
										<tr>
											<td class="plainlabel">Mã số thuế</td>
											<td class="plainlabel"><input type="text" readonly style="width:400px" name="masothue" id="masothue" value="<%=nhctBean.getMasothue()%>" /></td>
										</tr>
										
										<tr>
											<td class="plainlabel">Thuộc Ngân hàng</td>
											<TD class="plainlabel"><select disabled style="width:400px" name="nganhang"  id="nganhang" class="select2">
													<option value=""></option>
													<%if (rsNganHang != null)
				while (rsNganHang.next()) {
					if (nhctBean.getNganHang().equals(
							rsNganHang.getString("PK_SEQ"))) {%>
													<option value="<%=rsNganHang.getString("PK_SEQ")%>" selected="selected"><%=rsNganHang.getString("Ten")%></option>
													<%} else {%>
													<option value="<%=rsNganHang.getString("PK_SEQ")%>"><%=rsNganHang.getString("Ten")%></option>
													<%}
				}%>
											</select></td>
										</tr>
											<TR>
											<TD class="plainlabel">Thuộc chi nhánh</TD>
											<TD class="plainlabel">
												
													<select disabled style="width:400px" name="chinhanh" id="chinhanh" class="select2">
														
														<option value=""></option>
														<%if (rsChiNhanh != null)
				while (rsChiNhanh.next()) {
					if (nhctBean.getChiNhanh().equals(
							rsChiNhanh.getString("PK_SEQ"))) {%>
														<option value="<%=rsChiNhanh.getString("PK_SEQ")%>" selected="selected"><%=rsChiNhanh.getString("Ten")%></option>
														<%} else {%>
														<option value="<%=rsChiNhanh.getString("PK_SEQ")%>"><%=rsChiNhanh.getString("Ten")%></option>
														<%}}
				%>
												</select>
											</td>
										</tr>
										
										<TR>
											<TD class="plainlabel">Thuộc loại tiền</TD>
											<TD class="plainlabel"><select disabled style="width:400px" name="loaitien" id="loaitien" class="select2">
													<option value=""></option>
													<%if (rsLoaiTien != null)
				while (rsLoaiTien.next()) {
					if (nhctBean.getLoaiTien().equals(
							rsLoaiTien.getString("PK_SEQ"))) {%>
													<option value="<%=rsLoaiTien.getString("PK_SEQ")%>" selected="selected"><%=rsLoaiTien.getString("Ten")%></option>
													<%} else {%>
													<option value="<%=rsLoaiTien.getString("PK_SEQ")%>"><%=rsLoaiTien.getString("Ten")%></option>
													<%}
				}%>
											</select></td>
										</TR>
										<TR>
											<TD class="plainlabel">Thuộc tài khoàn kế toán</TD>
											<TD class="plainlabel"><select disabled style="width:400px" name="taikhoan" id="taikhoan" class="select2">
													<option value=""></option>
													<%if (rsTaiKhoan != null)
														while (rsTaiKhoan.next()) {
															if (nhctBean.getTaiKhoanKeToan().equals(
																	rsTaiKhoan.getString("PK_SEQ"))) {%>
																							<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>" selected="selected"><%=rsTaiKhoan.getString("Ma")%> - <%=rsTaiKhoan.getString("Ten")%></option>
																							<%} else {%>
																							<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>"> <%=rsTaiKhoan.getString("Ma")%> - <%=rsTaiKhoan.getString("Ten")%>  </option>
																							<%}
														}%>
											</select></td>
										</TR>
										<TR>
											<TD class="plainlabel">Hoạt động</TD>
											<TD class="plainlabel">
												<%if (nhctBean.getTrangThai().equals("1")) {%> <input type="checkbox" disabled  name="trangthai" value="<%=nhctBean.getTrangThai()%>" checked="checked">
												<%} else {%> <input type="checkbox" disabled  name="trangthai" value="<%=nhctBean.getTrangThai()%>"> <%}%>
											</td>
										</TR>
									</table>
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
	if (rsTaiKhoan != null) rsTaiKhoan.close();
	nhctBean.closeDB();
}%>